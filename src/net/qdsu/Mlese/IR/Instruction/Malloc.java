package net.qdsu.Mlese.IR.Instruction;

import net.qdsu.Mlese.IR.Operand.Operand;
import net.qdsu.Mlese.IR.Operand.Register;
import net.qdsu.Mlese.IR.Operand.VirtualRegister;

import java.util.HashSet;

public class Malloc extends IRInstruction{
    @Override
    public void accept(IRVisitor visitor){
        visitor.visit(this);
    }

    private Register dest;
    private Operand size;

    public Malloc(Register dest, Operand size) {
        this.dest = dest;
        this.size = size;
    }

    public Register getDest() {
        return dest;
    }

    public Operand getSize() {
        return size;
    }

    public void setDest(Register dest) {
        this.dest = dest;
    }

    public void setSize(Operand size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return dest + " = malloc " + size;
    }

    @Override
    public void updateUseAndDef() {
        def = new HashSet<Register>(){{add(dest);}};
        use = new HashSet<Register>(){{
            if (size instanceof Register)
                add((Register)size);
        }};
    }

    @Override
    public Register ssadef() {
        return null;
    }

    @Override
    public HashSet<Register> ssause() {
        return null;
    }
}
