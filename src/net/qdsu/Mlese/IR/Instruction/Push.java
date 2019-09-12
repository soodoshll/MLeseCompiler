package net.qdsu.Mlese.IR.Instruction;

import net.qdsu.Mlese.IR.Operand.Operand;
import net.qdsu.Mlese.IR.Operand.Register;

import java.util.HashSet;

public class Push extends IRInstruction {
    private Operand src;

    public Push(Operand src) {
        this.src = src;
    }

    @Override
    public void accept(IRVisitor visitor){
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "push "+src;
    }

    public Operand getSrc() {
        return src;
    }

    @Override
    public void updateUseAndDef() {
        def = new HashSet<Register>();
        use = new HashSet<Register>(){{
            if (src instanceof Register)
                add((Register)src);
        }};
    }

    public void setSrc(Operand src) {
        this.src = src;
    }


    @Override
    public Register ssadef() {
        throw new RuntimeException("SSA NO PUSH");
    }

    @Override
    public HashSet<Register> ssause() {
        throw new RuntimeException("SSA NO PUSH");
    }
}
