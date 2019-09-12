package net.qdsu.Mlese.Optim;

import net.qdsu.Mlese.IR.BasicBlock;
import net.qdsu.Mlese.IR.Function;
import net.qdsu.Mlese.IR.Instruction.*;
import net.qdsu.Mlese.IR.Operand.Register;

import java.util.*;

public class DCE {
    public static class RDF{
        public void runFunc(Function func){
            calIDom(func);
            calDF(func);
        }

        private boolean dom_changed;


        private void updateBBDom(BasicBlock bb){
            HashSet<BasicBlock> temp = null;
            for (BasicBlock next_bb: bb.nextBB()){
                if (temp == null) temp = new HashSet<>(next_bb.RDom);
                else temp.retainAll(next_bb.RDom);
            }
            if (temp == null) temp = new HashSet<>();
            temp.add(bb);
            if (!temp.equals(bb.RDom)){
                bb.RDom = temp;
                dom_changed = true;
            }
        }

        public void calIDom(Function func){


            func.calRPO();
            List<BasicBlock> bb_list = func.rpo;
            Collections.reverse(bb_list);

            bb_list.forEach(x->x.RDom = new HashSet<>(bb_list));

            for (BasicBlock bb : func.getBbList())
                if (!(bb.getIrEnd() instanceof Branch) && !(bb.getIrEnd() instanceof Jump)) {
                    bb.RDom = new HashSet<>();
                    bb.RDom.add(bb);
                }


            dom_changed = true;
            while (dom_changed){
                dom_changed = false;
                for (BasicBlock bb : bb_list) {
                    updateBBDom(bb);
                }
            }

            bb_list.forEach(x->x.rdom_pre_cnt = x.RDom.size());
            for (BasicBlock bb:bb_list){
                bb.RDom.forEach(x->x.RDomNext.add(bb));
            }

            Queue<BasicBlock> one_pre = new LinkedList<>();
            for (BasicBlock bb: bb_list) {
                if (bb.rdom_pre_cnt == 1){
                    one_pre.offer(bb);
                }
                while (!one_pre.isEmpty()){
                    BasicBlock cur_bb = one_pre.poll();
                    for (BasicBlock next_bb: cur_bb.RDomNext){
                        if (next_bb.rdom_pre_cnt > 0)
                            next_bb.rdom_pre_cnt--;
                        if (next_bb.rdom_pre_cnt == 1) {
                            one_pre.offer(next_bb);
                            next_bb.RIDom = cur_bb;
                        }
                    }
                }
            }
        }

        public void calDF(Function func){
            for (BasicBlock bb: func.getBbList()){
                if (bb.nextBB().size() > 1){
                    for (BasicBlock next: bb.nextBB()){
                        BasicBlock runner = next;
                        while (runner != bb.RIDom && runner != null){
                            //System.out.println(runner);
                            runner.RDF.add(bb);
                            runner = runner.RIDom;
                        }
                    }
                }
            }
        }

    }

    HashSet<IRInstruction> mark;
    HashSet<BasicBlock>    useful;
    Queue<IRInstruction> worklist;


    private HashSet<BasicBlock> enabled_bb;
    private void calEnabled(BasicBlock bb){
        if (enabled_bb.contains(bb)) return;
        enabled_bb.add(bb);
        bb.nextBB().forEach(x->calEnabled(x));
    }

    private BasicBlock NearestPostDom(BasicBlock bb){
        BasicBlock pd = bb.RIDom;
        while (pd != null && !useful.contains(pd)) pd = pd.RIDom;
        return pd;
    }

    private void removeBB(BasicBlock bb){
        System.err.println("remove "+bb);
        for (BasicBlock next_bb : bb.nextBB())
            next_bb.SSARemoveFrom(bb.getIrEnd());
        for (IRInstruction ins=bb.getIrStart(); ins!=null; ins=ins.getNext()) {
            ins.remove();
            for (Register r : ins.ssause())
                r.usedIns.remove(ins);
        }
    }

    public void runFunc(Function func){
        RDF rdf = new RDF();
        rdf.runFunc(func);

        mark = new HashSet<>();
        useful = new HashSet<>();
        worklist = new LinkedList<>();

//        System.out.println("!!!! "+func);

        //add all critical
//        useful.add(func.getBBStart());
//        mark.add(func.getBBStart().);
        for (BasicBlock bb : func.getBbList())
            for (IRInstruction ins = bb.getIrStart() ; ins != null ; ins = ins.getNext()){
                if (ins instanceof Return || ins instanceof Store || ins instanceof Call){
//                    System.out.println(ins);
                    mark.add(ins);
                    useful.add(ins.getBb());
                    worklist.offer(ins);
                }
            }

        //mark
        while (!worklist.isEmpty()) {
            IRInstruction ins = worklist.poll();
//            System.out.println(ins+" "+ins.ssause());
            for (Register r : ins.ssause()){
                if (r.ssadef != null && !mark.contains(r.ssadef)){
                    mark.add(r.ssadef);
                    useful.add(r.ssadef.getBb());
                    worklist.offer(r.ssadef);
                }
            }

            if (ins instanceof Phi){
//                        System.out.println("FUCK");
//                System.out.println(ins.getBb().getFrom());
                for (IRInstruction prev : ins.getBb().getFrom()){
//                    System.out.println(prev.getBb());
                    if (!mark.contains(prev)){
                        mark.add(prev);
                        useful.add(prev.getBb());
                        worklist.add(prev);
                    }
                }
            }

            for (BasicBlock rbb : ins.getBb().RDF){
                if (rbb.getIrEnd() instanceof Branch && !mark.contains(rbb.getIrEnd())){
                    mark.add(rbb.getIrEnd());
                    useful.add(rbb);
                    worklist.offer(rbb.getIrEnd());
                }
            }
        }

        //remove
        for (BasicBlock bb: func.getBbList()){
            IRInstruction ins = bb.getIrStart();
            IRInstruction tmp;
            while (ins != null){
                if (!mark.contains(ins)){
                    if (ins instanceof Branch){
                        BasicBlock pd = NearestPostDom(ins.getBb());
                        if (pd == null){
                            ins.replace(new Return());
                        }else{
                            Jump new_ins = new Jump(pd);
                            pd.getFrom().remove(pd.getFrom().size() - 1);
                            ins.replace(new_ins);
                            int index = pd.getFrom().indexOf(ins);
                            if (index >= 0) pd.getFrom().set(index, new_ins);
                        }
                        for (Register r : ins.ssause()) r.usedIns.remove(ins);
                        ins = null;
                    }else if (!(ins instanceof Jump)){
                        ins.remove();
                        for (Register r : ins.ssause()) r.usedIns.remove(ins);
                        ins = ins.getNext();
                    }else ins = null;
                }else ins = ins.getNext();
            }
        }

        HashSet <BasicBlock> del_bb =new HashSet<>(func.getBbList());

        enabled_bb = new HashSet<>();
        calEnabled(func.getBBStart());

        del_bb.removeAll(enabled_bb);
        func.getBbList().removeAll(del_bb);
        del_bb.forEach(x->removeBB(x));

    }


}
