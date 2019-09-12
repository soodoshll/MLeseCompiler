package net.qdsu.Mlese.Backend;

import net.qdsu.Mlese.IR.BasicBlock;
import net.qdsu.Mlese.IR.Function;
import net.qdsu.Mlese.IR.IRRoot;
import net.qdsu.Mlese.IR.Instruction.*;
import net.qdsu.Mlese.IR.Operand.Operand;
import net.qdsu.Mlese.IR.Operand.Register;

import java.util.HashSet;
import java.util.Hashtable;

/*
*
* Combine the bb as more as possible.
* Calculate set Global for each function .
* Calculate set LiveNow.
*
* */
public class LivenessAnalysis {
    public static class GlobalBuilder implements IRVisitor {
        private IRRoot root;
        private HashSet<Register> varKill;
        private HashSet<Register> global;
        private HashSet<BasicBlock> visited = new HashSet<>();
        private Hashtable<Register, HashSet<BasicBlock> > block = new Hashtable<>();

        public HashSet<BasicBlock> getBlocks(Register reg){
            return block.get(reg);
        }

        private void addBlock(Register reg, BasicBlock bb){
            HashSet<BasicBlock> bb_set = block.get(reg);
            if (bb_set == null){
                HashSet<BasicBlock> new_set = new HashSet<>();
                new_set.add(bb);
                block.put(reg, new_set);
            }else{
                bb_set.add(bb);
            }
        }

        public GlobalBuilder(IRRoot root) {
            this.root = root;
        }

        //public HashSet<Register> getGlobal() {
        //    return global;
       // }

        public void run(){
            for (Function fun: root.getFunctions()){
                fun.global = new HashSet<>();
                global = fun.global;
                doBB(fun.getBBStart());
            }
            //System.out.println(global);
        }



        public void doBB(BasicBlock bb){
            if (bb == null) return;
            if (visited.contains(bb)) return;
            visited.add(bb);
            varKill = new HashSet<>();
            //System.out.println(bb.getIrStart());
            bb.getIrStart().accept(this);
        }

        private void checkReg(Operand reg){
            if (reg instanceof Register && !varKill.contains(reg)) global.add((Register)reg);
        }

        @Override
        public void visit(IRInstruction inst) {

        }

        @Override
        public void visit(BinaryOp inst) {
            Operand lhs = inst.getLhs();
            Operand rhs = inst.getRhs();
            checkReg(lhs);
            checkReg(rhs);
            varKill.add(inst.getDest());
            addBlock(inst.getDest(), inst.getBb());
            if (inst.getNext() != null) inst.getNext().accept(this);
        }

        @Override
        public void visit(Branch inst) {
            checkReg(inst.getCond());
            doBB(inst.getIfTrue());
            doBB(inst.getIfFalse());

        }

        @Override
        public void visit(Call inst) {
            for (Operand arg: inst.getArguments()){
                checkReg(arg);

            }
            varKill.add(inst.getDest());
            addBlock(inst.getDest(), inst.getBb());
            if (inst.getNext() != null) inst.getNext().accept(this);
        }

        @Override
        public void visit(IntCmp inst) {
            checkReg(inst.getLhs());
            checkReg(inst.getRhs());

            varKill.add(inst.getDest());
            addBlock(inst.getDest(), inst.getBb());
            if (inst.getNext() != null) inst.getNext().accept(this);
        }

        @Override
        public void visit(Jump inst) {
            if (inst.getDest() != null) doBB(inst.getDest());
        }

        @Override
        public void visit(Load inst) {
            checkReg(inst.getSrc1());
            varKill.add(inst.getDest());
            addBlock(inst.getDest(), inst.getBb());

            if (inst.getNext() != null) inst.getNext().accept(this);
        }

        @Override
        public void visit(Malloc inst) {
            checkReg(inst.getSize());
            varKill.add(inst.getDest());
            addBlock(inst.getDest(), inst.getBb());

            if (inst.getNext() != null) inst.getNext().accept(this);
        }

        @Override
        public void visit(Move inst) {
            checkReg(inst.getSrc());
            varKill.add(inst.getDest());
            addBlock(inst.getDest(), inst.getBb());

            if (inst.getNext() != null) inst.getNext().accept(this);
        }

        @Override
        public void visit(Store inst) {
            checkReg(inst.getSrc());
            checkReg(inst.getDest1());
            if (inst.getNext() != null) inst.getNext().accept(this);
        }

        @Override
        public void visit(UnaryOp inst) {
            checkReg(inst.getOperand());
            varKill.add(inst.getDest());
            addBlock(inst.getDest(), inst.getBb());

            if (inst.getNext() != null) inst.getNext().accept(this);
        }

        @Override
        public void visit(Return inst) {
            checkReg(inst.getValue());
        }

        @Override
        public void visit(Pop inst) {
            varKill.add(inst.getDest());
            addBlock(inst.getDest(), inst.getBb());
            if (inst.getNext() != null) inst.getNext().accept(this);
        }

        @Override
        public void visit(Push inst) {
            checkReg(inst.getSrc());
            if (inst.getNext() != null) inst.getNext().accept(this);

        }

        @Override
        public void visit(Phi inst) {

        }


        @Override
        public void visit(Lea inst) {
            checkReg(inst.getBase());
            checkReg(inst.getIndex());
            //checkReg(inst.getOffset());

            varKill.add(inst.getDest());
            addBlock(inst.getDest(), inst.getBb());
            if (inst.getNext() != null) inst.getNext().accept(this);
        }
    }

    private IRRoot root;
    private GlobalBuilder globalBuilder;
    private HashSet<Register> global;
    private HashSet<BasicBlock> visited = new HashSet<>();
    public LivenessAnalysis(IRRoot root) {
        this.root = root;
    }

    public void run(Function fun){
        for (BasicBlock bb : fun.getBbList()){
            bb.gen = new HashSet();
            bb.kill = new HashSet<>();

            for (IRInstruction ins = bb.getIrStart() ; ins != null ; ins = ins.getNext()){
//                System.out.println("###"+ins);
                ins.updateUseAndDef();
                bb.gen.addAll(ins.use);
                bb.gen.removeAll(bb.kill);
                HashSet<Register> def_without_use = new HashSet<>(ins.def);
                def_without_use.removeAll(bb.gen);
                bb.kill.addAll(def_without_use);
            }

            bb.livein = new HashSet<>();
            bb.liveout = new HashSet<>();
        }
        boolean changed = true;
        while (changed){
            changed = false;
            for (BasicBlock bb : fun.getBbList()){
                HashSet<Register> old_in = new HashSet<>(bb.livein);
                HashSet<Register> old_out = new HashSet<>(bb.liveout);

                HashSet<Register> out_without_def = new HashSet<>(bb.liveout);
                out_without_def.removeAll(bb.kill);
                bb.livein = new HashSet<>(bb.gen);
                bb.livein.addAll(out_without_def);

                bb.liveout = new HashSet<>();
                bb.nextBB().forEach(x -> bb.liveout.addAll(x.livein));

                changed = changed || (!bb.livein.equals(old_in) || !bb.liveout.equals(old_out));
            } // end of for bb
        } // end of while
    }

    public void run(){
        for (Function fun: root.getFunctions()){
            run(fun);
        }

    }

    public void deadCodeEliminate(){
        run();
        for (Function fun: root.getFunctions()){
            for (BasicBlock bb : fun.getBbList()){
                HashSet<Register> live = new HashSet<>(bb.liveout);
                for (IRInstruction ins = bb.getIrEnd(); ins != null; ins=ins.getPrev()){
                    if (!(ins instanceof Call) && !ins.def.isEmpty() && !(ins instanceof Pop)){
                        HashSet<Register> remain = new HashSet<>(live);
                        remain.retainAll(ins.def);
                        if (remain.isEmpty()) {
                            ins.remove();
                            System.err.println("remove "+bb+" "+ins);
                        }
                    }
                    live.removeAll(ins.def);
                    live.addAll(ins.use);
                }
            }
        }
    }

}
