package net.qdsu.Mlese.Optim;

import net.qdsu.Mlese.IR.BasicBlock;
import net.qdsu.Mlese.IR.Function;
import net.qdsu.Mlese.IR.IRRoot;
import net.qdsu.Mlese.IR.Instruction.*;
import net.qdsu.Mlese.IR.Operand.Immediate;
import net.qdsu.Mlese.IR.Operand.Operand;
import net.qdsu.Mlese.IR.Operand.Register;
import net.qdsu.Mlese.Type.BasicType;

import java.util.Hashtable;

public class CopyElimination implements IRVisitor {
    public IRRoot root;

    public Hashtable<Register, Register> name = new Hashtable<>();

    public CopyElimination(IRRoot root) {
        this.root = root;
    }

    public void runFunc(Function fun) {
        for (BasicBlock bb : fun.rpo){
            for (IRInstruction ins = bb.getIrStart(); ins!=null; ins=ins.getNext()){
                ins.accept(this);
            }
        }
    }

    public Register getName(Register n){
        Register r = name.get(n);
        if (r==null) return n;
        else return r;
    }

    public Operand checkRead(Operand o, IRInstruction ins){
        if (o instanceof Register){
            Register n = name.get(o);
            if (n != null){
                n.usedIns.add(ins);
                return n;
            }else return o;

        }else return o;
    }

    @Override
    public void visit(IRInstruction inst) {
        throw new RuntimeException();
    }

    @Override
    public void visit(BinaryOp inst) {
        inst.setLhs(checkRead(inst.getLhs(), inst));
        inst.setRhs(checkRead(inst.getRhs(), inst));
    }

    @Override
    public void visit(Branch inst) {
        inst.setCond(checkRead(inst.getCond(), inst));
    }

    @Override
    public void visit(Call inst) {
        for (int i = 0 ; i < inst.getArguments().size() ; i++){
            inst.setArgument(i, checkRead(inst.getArguments().get(i), inst));
        }
    }

    @Override
    public void visit(IntCmp inst) {
        inst.setLhs(checkRead(inst.getLhs(), inst));
        inst.setRhs(checkRead(inst.getRhs(), inst));
    }

    @Override
    public void visit(Jump inst) {

    }

    @Override
    public void visit(Load inst) {
        inst.setSrc1(checkRead(inst.getSrc1(), inst));
    }

    @Override
    public void visit(Malloc inst) {

    }

    @Override
    public void visit(Move inst) {
        if (inst.getSrc() instanceof Register){
            Register n = getName((Register)inst.getSrc());
            name.put(inst.getDest(), n);
//            System.out.println("remove "+inst.getDest());
            inst.remove();
            n.usedIns.remove(inst);
            inst.getDest().usedIns.forEach(x->x.accept(this));
        }
    }

    @Override
    public void visit(Store inst) {
        inst.setDest1(checkRead(inst.getDest1(), inst));
        inst.setSrc(checkRead(inst.getSrc(), inst));
    }

    @Override
    public void visit(UnaryOp inst) {
        inst.setOperand(checkRead(inst.getOperand(), inst));
    }

    @Override
    public void visit(Return inst) {
        inst.setValue(checkRead(inst.getValue(), inst));
    }

    @Override
    public void visit(Pop inst) {

    }

    @Override
    public void visit(Push inst) {

    }

    @Override
    public void visit(Phi inst) {
        for (int i = 0 ; i < inst.getSlotNum() ; i++) {
            //System.out.println("FUCK"+inst.getArg(i));
            inst.setSlot(i, checkRead(inst.getArg(i), inst));

        }
    }

    @Override
    public void visit(Lea inst) {
        inst.setIndex(checkRead(inst.getIndex(), inst));
        inst.setBase(checkRead(inst.getBase(), inst));
    }
}
