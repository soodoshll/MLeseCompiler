package net.qdsu.Mlese.IR.Instruction;

import net.qdsu.Mlese.IR.Operand.Operand;
import net.qdsu.Mlese.IR.Operand.Register;
import net.qdsu.Mlese.IR.Operand.VirtualRegister;

import java.util.HashSet;

public class Load extends IRInstruction{
    private Register dest;
    private Operand src1;
    //private Op src2;
    //private int scale;
    //private int size;
    //All size are 8
    /* Address = src1 + src2 * scale*/

//    public Load(VirtualRegister dest, Op src1, Op src2, int scale , int size) {
//        this.dest = dest;
//        this.src1 = src1;
//        this.src2 = src2;
//        this.scale = scale;
//        //this.size = size;
//    }

    public Load(Register dest, Operand src1){
        this.dest = dest;
        this.src1 = src1;
        //this.size = size;
    }


    public Register getDest() {
        return dest;
    }

    public Operand getSrc1() {
        return src1;
    }

    public void setDest(Register dest) {
        this.dest = dest;
    }

    public void setSrc1(Operand src1) {
        this.src1 = src1;
    }

    @Override
    public void accept(IRVisitor visitor){
        visitor.visit(this);
    }

    @Override
    public String toString() {
        //if (src2 !=  null)
        //    return "load " + dest + " " + src1 + " + " + src2 + " * " + scale;
        //else
            return "load " + dest + " " + src1;
    }

    @Override
    public void updateUseAndDef() {
        def = new HashSet<Register>(){{add(dest);}};
        use = new HashSet<Register>(){{
           if (src1 instanceof Register) add((Register)src1);
        }};
    }

    @Override
    public Register ssadef() {
        return dest;
    }

    @Override
    public HashSet<Register> ssause() {
        return new HashSet<Register>(){{
            if (src1 instanceof Register) add((Register)src1);
        }};
    }
}
