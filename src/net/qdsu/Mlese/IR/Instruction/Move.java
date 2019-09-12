package net.qdsu.Mlese.IR.Instruction;

import net.qdsu.Mlese.IR.Operand.Operand;
import net.qdsu.Mlese.IR.Operand.Register;
import net.qdsu.Mlese.IR.Operand.VirtualRegister;

import java.util.HashSet;

public class Move extends IRInstruction {
    private Operand src;
    private Register dest;

    public Move(Register dest,Operand src) {
        this.src = src;
        this.dest = dest;
    }

    @Override
    public void accept(IRVisitor visitor){
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return dest+" = "+src;
    }

    public Operand getSrc() {
        return src;
    }

    public Register getDest() {
        return dest;
    }

    public void setSrc(Operand src) {
        this.src = src;
    }

    public void setDest(Register dest) {
        this.dest = dest;
    }

    @Override
    public void updateUseAndDef() {
        def = new HashSet<Register>(){{add(dest);}};
        use = new HashSet<Register>(){{
            if (src instanceof Register)
                add((Register)src);
        }};
    }

    @Override
    public Register ssadef() {
        return dest;
    }

    @Override
    public HashSet<Register> ssause() {
        return new HashSet<Register>(){{
            if (src instanceof Register)
                add((Register)src);
        }};
    }
}
