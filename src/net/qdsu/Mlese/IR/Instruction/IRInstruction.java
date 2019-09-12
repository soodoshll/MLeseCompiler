package net.qdsu.Mlese.IR.Instruction;

import net.qdsu.Mlese.IR.BasicBlock;
import net.qdsu.Mlese.IR.Operand.Operand;
import net.qdsu.Mlese.IR.Operand.Register;

import java.util.HashSet;
import java.util.Set;

public abstract class IRInstruction  {
    private IRInstruction prev;
    private IRInstruction next;
    private BasicBlock bb;

    public HashSet<Register> LiveNow;

    public void addLiveNow(Operand op){
        if (op instanceof Register) LiveNow.add((Register)op);
    }

    public void delLiveNow(Operand op){
        LiveNow.remove(op);
    }


    public void setBb(BasicBlock bb) {
        this.bb = bb;
    }

    public BasicBlock getBb() {
        return bb;
    }

    public void setNext(IRInstruction next) {
        this.next = next;
    }

    public void setPrev(IRInstruction prev) {
        this.prev = prev;
    }

    public IRInstruction getNext() {
        return next;
    }

    public IRInstruction getPrev() {
        return prev;
    }

    public void insertBefore(IRInstruction ins){
        //System.out.println("insert "+ ins +" BEFORE " + this);
        if (bb.getIrStart() == this)
            bb.setIrStart(ins);
        ins.prev = prev;
        ins.next = this;
        if (prev != null) prev.next = ins;
        prev = ins;
        ins.setBb(bb);
    }

    public void insertAfter (IRInstruction ins){
        //System.out.println("insert "+ ins +" AFTER " + this);
        if (bb.getIrEnd() == this)
            bb.setIrEnd(ins);
        ins.prev = this;
        ins.next = next;
        if (next != null) next.prev = ins;
        next = ins;
        ins.setBb(bb);
    }

    public void replace(IRInstruction ins){
        //System.out.println("replace "+this+" -> "+ins);
        ins.prev = prev;
        ins.next = next;
        if (ins.prev == null){
            bb.setIrStart(ins);
            if (next != null)
                next.setPrev(ins);
            else bb.setIrEnd(ins);
        }else if (ins.next == null){
            bb.setIrEnd(ins);
            prev.setNext(ins);
        }else{
            prev.setNext(ins);
            next.setPrev(ins);
        }
        ins.setBb(bb);

    }

    public void remove(){
//        System.err.println("removed ins"+this);
        if (prev == null){
            bb.setIrStart(next);
        }else{
            prev.next = next;
        }
        if (next == null){
            bb.setIrEnd(prev);
        }else{
            next.prev = prev;
        }
    }

    public HashSet<Register> use;
    public HashSet<Register> def;

    public abstract Register ssadef();
    public abstract HashSet<Register> ssause();


    public abstract void updateUseAndDef();
    //public abstract HashSet<Register> getUse();
    //public abstract HashSet<Register> getDef();

    public Set<IRInstruction> nextToRun(){
        return new HashSet<IRInstruction>(){{if (next!=null) add(next);}};
    }

    public HashSet<Register> livein;
    public HashSet<Register> liveout;

    //    @Override
    public void accept(IRVisitor visitor){
        visitor.visit(this);
    }


    //For inlining
    public BasicBlock splitAfter(){
        BasicBlock bb = new BasicBlock(getBb().func);
        IRInstruction ins = next;
        IRInstruction tmp;
        while (ins != null){
            tmp = ins.getNext();
            bb.addInstruction(ins);
            ins = tmp;
        }
        getBb().setIrEnd(this);
        next = null;
//        insertAfter(new Jump(bb));
        return bb;
    }
}
