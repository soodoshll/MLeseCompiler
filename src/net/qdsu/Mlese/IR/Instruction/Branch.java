package net.qdsu.Mlese.IR.Instruction;

import net.qdsu.Mlese.AST.BinaryExpr;
import net.qdsu.Mlese.IR.BasicBlock;
import net.qdsu.Mlese.IR.Operand.Operand;
import net.qdsu.Mlese.IR.Operand.Register;

import java.util.HashSet;
import java.util.Set;

public class Branch extends IRInstruction{
    private Operand cond;
    private BasicBlock ifTrue;
    private BasicBlock ifFalse;
    private IntCmp cmp;

    public void setCmp(IntCmp cmp) {
        this.cmp = cmp;
    }

    public IntCmp getCmp() {
        return cmp;
    }

    public void setCond(Operand cond) {
        this.cond = cond;
    }

    public Branch(Operand cond, BasicBlock ifTrue, BasicBlock ifFalse) {
        this.cond = cond;
        this.ifTrue = ifTrue;
        this.ifFalse = ifFalse;
        //ifTrue.incInDeg();
        //ifFalse.incInDeg();

        ifTrue.addFrom(this);
        ifFalse.addFrom(this);
    }

    public BasicBlock getIfFalse() {
        return ifFalse;
    }

    public BasicBlock getIfTrue() {
        return ifTrue;
    }

    public Operand getCond() {
        return cond;
    }

    public void setIfTrue(BasicBlock ifTrue) {
        this.ifTrue = ifTrue;
    }

    public void setIfFalse(BasicBlock ifFalse) {
        this.ifFalse = ifFalse;
    }

    @Override
    public void accept(IRVisitor visitor){
        visitor.visit(this);
    }

    @Override
    public String toString() {
        if (cmp == null)
            return "br "+cond+" " +ifTrue +" "+ ifFalse;
        else
            return "br ["+cmp.getLhs()+" "+cmp.getOp()+" "+cmp.getRhs()+"] "+ifTrue+" "+ifFalse;
    }

    @Override
    public void updateUseAndDef() {
        def = new HashSet<Register>();
        use = new HashSet<Register>(){{
            if (cmp == null) {
                if (cond instanceof Register)
                    add((Register)cond);
            }
            else {
                if (cmp.getLhs() instanceof Register)
                    add((Register)cmp.getLhs());
                if (cmp.getRhs() instanceof Register)
                    add((Register)cmp.getRhs());
            }
        }};
    }

    @Override
    public Set<IRInstruction> nextToRun() {
        return new HashSet<IRInstruction>(){{add(ifTrue.getIrStart());add(ifFalse.getIrStart());}};
    }

    @Override
    public Register ssadef() {
        return null;
    }

    @Override
    public HashSet<Register> ssause() {
        return new HashSet<Register>(){{
            if (cond instanceof Register) add((Register)cond);
        }};
    }

    public void Swap(){
        BasicBlock tmp = ifFalse;
        ifFalse = ifTrue;
        ifTrue = tmp;
    }
}
