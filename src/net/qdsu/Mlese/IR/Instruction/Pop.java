package net.qdsu.Mlese.IR.Instruction;

import net.qdsu.Mlese.IR.Operand.Register;

import java.util.HashSet;

public class Pop extends IRInstruction {
    private Register dest;

    public Pop(Register dest) {
        this.dest = dest;
    }

    @Override
    public void accept(IRVisitor visitor){
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return dest + " = pop";
    }

    public Register getDest() {
        return dest;
    }

    @Override
    public void updateUseAndDef() {
        def = new HashSet<Register>(){{add(dest);}};
        use = new HashSet<Register>();
    }

    public void setDest(Register dest) {
        this.dest = dest;
    }

    @Override
    public Register ssadef() {
        throw new RuntimeException("SSA NO POP");
    }

    @Override
    public HashSet<Register> ssause() {
        throw new RuntimeException("SSA NO POP");
    }
}
