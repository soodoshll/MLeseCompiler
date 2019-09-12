package net.qdsu.Mlese.Backend;

import net.qdsu.Mlese.IR.BasicBlock;
import net.qdsu.Mlese.IR.Function;
import net.qdsu.Mlese.IR.IRRoot;
import net.qdsu.Mlese.IR.Instruction.*;
import net.qdsu.Mlese.IR.Operand.Operand;
import net.qdsu.Mlese.IR.Operand.Register;
import net.qdsu.Mlese.IR.Operand.VirtualRegister;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;

public class SSATransform {
    public static class Renamer implements IRVisitor {
        private IRRoot root;

        public Renamer(IRRoot root) {
            this.root = root;
        }


        private Scope curScope;

        public void run(){
            for (Function fun: root.getFunctions()){
                curScope = new Scope();
                for (Register par : fun.getParameters())
                {
                    Register new_par = getNewName(par);
                    updateName(new_par);
                }
                doBB(fun.getBBStart());
            }
        }

        public static class Scope {
            public Scope father = null;
            public Hashtable<VirtualRegister, VirtualRegister> table = new Hashtable<>();
            public Scope(){

            }
            public Scope(Scope father){
                this();
                this.father = father;
            }
            public VirtualRegister get(VirtualRegister reg){
                VirtualRegister r = table.get(reg);
                if (father == null) return r;
                if (r == null) return father.get(reg);
                else return r;
            }
            public void set(VirtualRegister reg, VirtualRegister rename){
                table.put(reg, rename);
            }
        }

        public HashSet<BasicBlock> visited = new HashSet<>();

        public void doBB(BasicBlock bb){
            if (visited.contains(bb)) return ;
            bb.rename_table = curScope;
            visited.add(bb);
            for (IRInstruction ins = bb.getIrStart(); ins != null ; ins = ins.getNext()){
                ins.accept(this);
            }

            for (BasicBlock next_bb : bb.nextBB()){
                for (VirtualRegister reg : next_bb.phiTable.keySet()){
                    next_bb.fillPhi(reg, getName(reg), bb.getIrEnd());
                }
            }
            Scope old_scope = curScope;
            for (BasicBlock next_bb : bb.nextBB()){
                curScope = new Scope(old_scope);
                doBB(next_bb);
            }
            curScope = old_scope;


        }

        private VirtualRegister getName(VirtualRegister reg){
            VirtualRegister r = curScope.get(reg);
            if (r == null) {
                curScope.set(reg, reg.getNewName());
                return reg;
            }else return r;
        }

        private Operand getOperandName(Operand op){
            if (op instanceof VirtualRegister) return getName((VirtualRegister)op);
            else return op;
        }

        private void updateName(Register reg){
            VirtualRegister vreg = (VirtualRegister)reg;
            curScope.set(vreg.getBaseReg(), vreg);
        }

        private VirtualRegister getNewName(Register reg){
            assert (reg instanceof VirtualRegister);
            VirtualRegister vreg = (VirtualRegister)reg;
            return vreg.getNewName();
        }

        private void addUsedIns(Operand op, IRInstruction ins){
            if (op instanceof VirtualRegister)
                ((VirtualRegister)op).usedIns.add(ins);
        }

        @Override
        public void visit(IRInstruction inst) {

        }


        @Override
        public void visit(BinaryOp inst) {
            inst.setLhs(getOperandName(inst.getLhs()));
            inst.setRhs(getOperandName(inst.getRhs()));
            inst.setDest(getNewName(inst.getDest()));
            updateName(inst.getDest());
            addUsedIns(inst.getLhs(), inst);
            addUsedIns(inst.getRhs(), inst);
            ((VirtualRegister)inst.getDest()).ssadef = inst;

        }

        @Override
        public void visit(Branch inst) {
            inst.setCond(getOperandName(inst.getCond()));
            addUsedIns(inst.getCond(), inst);
        }

        @Override
        public void visit(Call inst) {
            //System.out.printf("!!!%s\n",inst);
            for (int i = 0 ; i < inst.getArguments().size(); i++){
                Operand op = getOperandName(inst.getArguments().get(i));
                inst.setArgument(i, op);
                addUsedIns(op, inst);
            }
            inst.setDest(getNewName(inst.getDest()));
            updateName(inst.getDest());
            ((VirtualRegister)inst.getDest()).ssadef = inst;
        }

        @Override
        public void visit(IntCmp inst) {
            inst.setLhs(getOperandName(inst.getLhs()));
            inst.setRhs(getOperandName(inst.getRhs()));
            addUsedIns(inst.getLhs(), inst);
            addUsedIns(inst.getRhs(), inst);
            inst.setDest(getNewName(inst.getDest()));
            updateName(inst.getDest());
            ((VirtualRegister)inst.getDest()).ssadef = inst;
        }

        @Override
        public void visit(Jump inst) {

        }

        @Override
        public void visit(Load inst) {
            inst.setSrc1(getOperandName(inst.getSrc1()));
            inst.setDest(getNewName(inst.getDest()));
            addUsedIns(inst.getSrc1(), inst);
            updateName(inst.getDest());
            ((VirtualRegister)inst.getDest()).ssadef = inst;
        }

        @Override
        public void visit(Malloc inst) {
            inst.setSize(getOperandName(inst.getSize()));
            addUsedIns(inst.getSize(), inst);
            inst.setDest(getNewName(inst.getDest()));
            updateName(inst.getDest());
            ((VirtualRegister)inst.getDest()).ssadef = inst;
        }

        @Override
        public void visit(Move inst) {
            if (inst.getSrc() instanceof VirtualRegister) {
                curScope.set((VirtualRegister) inst.getDest(), getName((VirtualRegister) inst.getSrc()));
                inst.remove();
            }else{
                inst.setSrc(getOperandName(inst.getSrc()));
                addUsedIns(inst.getSrc(), inst);
                inst.setDest(getNewName(inst.getDest()));
                updateName(inst.getDest());
                ((VirtualRegister)inst.getDest()).ssadef = inst;
            }
        }

        @Override
        public void visit(Store inst) {
            inst.setDest1(getOperandName(inst.getDest1()));
            inst.setSrc(getOperandName(inst.getSrc()));
            addUsedIns(inst.getDest1(), inst);
            addUsedIns(inst.getSrc(), inst);

        }

        @Override
        public void visit(UnaryOp inst) {
            inst.setOperand(getOperandName(inst.getOperand()));
            inst.setDest(getNewName(inst.getDest()));
            addUsedIns(inst.getOperand(), inst);
            updateName(inst.getDest());
            ((VirtualRegister)inst.getDest()).ssadef = inst;
        }

        @Override
        public void visit(Return inst) {
            inst.setValue(getOperandName(inst.getValue()));
            addUsedIns(inst.getValue(), inst);
        }

        @Override
        public void visit(Pop inst) {

        }

        @Override
        public void visit(Push inst) {

        }

        @Override
        public void visit(Phi inst) {
            Register old_reg = inst.getDest();
//            inst.getBb().phiTable.remove(old_reg);
            inst.setDest(getNewName(inst.getDest()));
            updateName(inst.getDest());
//            inst.getBb().phiTable.put((VirtualRegister)inst.getDest(), inst);
            ((VirtualRegister)inst.getDest()).ssadef = inst;
        }

        @Override
        public void visit(Lea inst) {
            inst.setBase(getOperandName(inst.getBase()));
            inst.setIndex(getOperandName(inst.getIndex()));
            //inst.setOffset(getOperandName(inst.getOffset()));

            addUsedIns(inst.getBase(), inst);
            addUsedIns(inst.getIndex(), inst);
            //addUsedIns(inst.getOffset(), inst);

            inst.setDest(getNewName(inst.getDest()));
            updateName(inst.getDest());
            ((VirtualRegister)inst.getDest()).ssadef = inst;
        }
    }


    private IRRoot root;

    public SSATransform(IRRoot root) {
        this.root = root;
    }

    public void build(){
        IRTransform bb_combine = new IRTransform(root);
        bb_combine.BBCombine();

        root.getFunctions().forEach(x->x.calIDom());
        root.getFunctions().forEach(x->x.calDF());
        insertPhi();
        Renamer renamer = new Renamer(root);
        renamer.run();
    }


    private void insertPhi(){
        LivenessAnalysis.GlobalBuilder global_builder = new LivenessAnalysis.GlobalBuilder(root);
        global_builder.run();
        for (Function fun: root.getFunctions()) {
            HashSet<Register> global = fun.global;
            for (Register reg : global) {
                HashSet<BasicBlock> blocks = global_builder.getBlocks(reg);
                if (blocks == null) continue;
                ArrayList<BasicBlock> work_list = new ArrayList<>(blocks);
                for (int i = 0; i < work_list.size(); ++i) {
                    BasicBlock b = work_list.get(i);
                    for (BasicBlock d : b.DF) {
                        if (!d.phiTable.keySet().contains(reg)) {
                            Phi new_phi = new Phi(reg, d.getFrom().size());
                            d.phiTable.put((VirtualRegister)reg, new_phi);
                            System.err.println("add phi "+d+" "+reg + " "+new_phi);
                            d.getIrStart().insertBefore(new_phi);
                            work_list.add(d);
                        }
                    }
                }
            }
        }
    }

    private HashSet<BasicBlock> des_visited = new HashSet<>();

    private void insertCopy(IRInstruction last, BasicBlock cur_bb, BasicBlock next_bb){
        if (!next_bb.getFrom().contains(cur_bb.getIrEnd())) return;
//        System.out.printf("%s -> %s : %s\n",cur_bb,  next_bb, next_bb.getFrom());
        HashSet<Register> phi_vars = new HashSet<>();
        for (VirtualRegister reg : next_bb.phiTable.keySet()){
            phi_vars.add(next_bb.phiTable.get(reg).getDest());
        }
        for (VirtualRegister reg : next_bb.phiTable.keySet()){
//            System.out.println(next_bb+" "+next_bb.getFrom());
            int index = next_bb.getFrom().indexOf(cur_bb.getIrEnd());
            System.err.println("insert copy "+next_bb+" "+next_bb.phiTable);
            Operand v = next_bb.phiTable.get(reg).getArg(index);
            if (v instanceof Register && phi_vars.contains(v) && v != next_bb.phiTable.get(reg).getDest()){
                VirtualRegister dup = new VirtualRegister();
                next_bb.phiTable.get(reg).setSlot(index, dup);
                last.insertBefore(new Move(dup, v));
                phi_vars.remove(v);
            }
        }
        for (VirtualRegister reg : next_bb.phiTable.keySet()){
            int index = next_bb.getFrom().indexOf(cur_bb.getIrEnd());
            Operand v = next_bb.phiTable.get(reg).getArg(index);
            if (v != next_bb.phiTable.get(reg).getDest())
                last.insertBefore(new Move(next_bb.phiTable.get(reg).getDest(), v));
        }
    }

    private void destructBB(BasicBlock bb){
        System.err.println("destruct "+bb+" "+bb.phiTable);
        IRInstruction last = bb.getIrEnd();
        if (last instanceof Jump){
            BasicBlock next_bb = ((Jump) last).getDest();
            insertCopy(last, bb, next_bb);
        }else if (last instanceof Branch){
            BasicBlock if_true = ((Branch) last).getIfTrue();
            if (if_true.phiTable.size() > 0) {
                BasicBlock copy_true = new BasicBlock(bb.func, "Copy True");
                IRInstruction jump_true = new Jump(if_true);
                copy_true.addInstruction(jump_true);
                ((Branch) last).setIfTrue(copy_true);
                insertCopy(jump_true, bb, if_true);
            }

            BasicBlock if_false = ((Branch) last).getIfFalse();
            if (if_false.phiTable.size() > 0) {
                BasicBlock copy_false = new BasicBlock(bb.func, "Copy False");
                IRInstruction jump_false = new Jump(if_false);
                copy_false.addInstruction(jump_false);
                ((Branch) last).setIfFalse(copy_false);
                insertCopy(jump_false, bb, if_false);
            }
        }
    }

    public void destruct(){
        for (Function fun : root.getFunctions()){
            ArrayList<BasicBlock> old_list = new ArrayList<>(fun.getBbList());
            for (BasicBlock bb : old_list)
                destructBB(bb);
            for (BasicBlock bb: fun.getBbList()){
                for (IRInstruction ins = bb.getIrStart(); ins != null ; ){
                    if (ins instanceof Phi){
                        IRInstruction next = ins.getNext();
                        ins.remove();
                        ins = next;
                    }else {
                        ins = ins.getNext();
                    }
                }
            }
        }


    }

    public void afterSSA(){
        IRTransform after_ssa = new IRTransform(root);
        after_ssa.toMachineIns();
        //System.out.println("After SSA combine");
        after_ssa.BBCombine();
    }



}
