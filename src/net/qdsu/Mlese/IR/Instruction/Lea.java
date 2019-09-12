package net.qdsu.Mlese.IR.Instruction;

import net.qdsu.Mlese.IR.Operand.Immediate;
import net.qdsu.Mlese.IR.Operand.Operand;
import net.qdsu.Mlese.IR.Operand.Register;

import java.util.HashSet;

public class Lea extends IRInstruction {
    private Register dest;
    private Operand base;
    private int offset;
    private Operand index;
    private int     size;

    public Lea(Register dest, Operand base, int offset, Operand index, int size) {
        this.dest = dest;
        this.base = base;
        this.offset = offset;
        this.index = index;
        this.size = size;
    }


    public Lea(Register dest, Operand base, Operand index) {
        this.dest = dest;
        this.base = base;
        this.index = index;
        this.size  = 8;
        this.offset = 0;
    }

    public Register getDest() {
        return dest;
    }

    public Operand getBase() {
        return base;
    }

    public int getOffset() {
        return offset;
    }

    public Operand getIndex() {
        return index;
    }

    public int getSize() {
        return size;
    }

    public void setDest(Register dest) {
        this.dest = dest;
    }

    public void setBase(Operand base) {
        this.base = base;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setIndex(Operand index) {
        this.index = index;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public void accept(IRVisitor visitor){
        visitor.visit(this);
    }


    @Override
    public String toString() {
        return dest + " = " + base + " + " + size + " * " + index + " + " + offset;
    }

    @Override
    public void updateUseAndDef() {
        def = new HashSet<Register>(){{add(dest);}};
        use = new HashSet<Register>(){{
            if (base instanceof Register) add((Register)base);
            if (index instanceof Register) add((Register)index);
//            if (offset instanceof Register) add((Register)offset);
        }};
    }

    @Override
    public Register ssadef() {
        return dest;
    }

    @Override
    public HashSet<Register> ssause() {
        return new HashSet<Register>(){{
            if (base instanceof Register) add((Register)base);
            if (index instanceof Register) add((Register)index);
//            if (offset instanceof Register) add((Register)offset);
        }};
    }
}
