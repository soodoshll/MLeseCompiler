package net.qdsu.Mlese.IR.Instruction;

import net.qdsu.Mlese.IR.Operand.Operand;
import net.qdsu.Mlese.IR.Operand.Register;
import net.qdsu.Mlese.IR.Operand.VirtualRegister;

import java.util.HashSet;

public class Store extends IRInstruction{
    private Operand dest1;
    //private Op dest2;
    //private int     scale;
    private Operand src;
    //private int size;

    public Store(Operand dest1, Operand src) {
        this.dest1 = dest1;
        this.src = src;
       // this.size = size;
    }

//    public Store(Op dest1, Op dest2, int scale, VirtualRegister src, int size) {
//        this.dest1 = dest1;
//        this.dest2 = dest2;
//        this.scale = scale;
//        this.src = src;
//        this.size = size;
//    }



    @Override
    public void accept(IRVisitor visitor){
        visitor.visit(this);
    }

    public Operand getDest1() {
        return dest1;
    }

    public Operand getSrc() {
        return src;
    }

    @Override
    public String toString() {
        //if (dest2 == null)
            return "store " + dest1 + " " + src;
        //else
        //    return "store [" + dest1 + " + " + dest2 + " * " + scale + "] " + src;
    }

    public void setDest1(Operand dest1) {
        this.dest1 = dest1;
    }

    public void setSrc(Operand src) {
        this.src = src;
    }

    @Override
    public void updateUseAndDef() {
        def = new HashSet<Register>();
        use = new HashSet<Register>(){{
           if (dest1 instanceof Register)
               add((Register)dest1);
           if (src instanceof Register)
               add((Register)src);
        }};
    }

    @Override
    public Register ssadef() {
        return null;
    }

    @Override
    public HashSet<Register> ssause() {
        return new HashSet<Register>(){{
            if (dest1 instanceof Register)
                add((Register)dest1);
            if (src instanceof Register)
                add((Register)src);
        }};
    }
}
