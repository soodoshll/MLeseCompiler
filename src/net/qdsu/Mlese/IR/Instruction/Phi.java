package net.qdsu.Mlese.IR.Instruction;

import net.qdsu.Mlese.IR.BasicBlock;
import net.qdsu.Mlese.IR.Operand.Operand;
import net.qdsu.Mlese.IR.Operand.Register;
import net.qdsu.Mlese.IR.Operand.VirtualRegister;

import java.util.ArrayList;
import java.util.HashSet;

public class Phi extends IRInstruction{
    private Register dest;
    private ArrayList<Operand> values;

    public Phi(Register dest, int slot_num){
        this.dest = dest;
        values = new ArrayList<>();
        for (int i = 0 ; i < slot_num ; i++) values.add(null);
    }

    public int getSlotNum(){
        return values.size();
    }

    public void setSlot(int i, Operand reg){
        values.set(i, reg);
    }

    public void setDest(Register dest) {
        this.dest = dest;
    }

    public Register getDest() {
        return dest;
    }


    public Operand getArg(int index){
        return values.get(index);
    }

    @Override
    public String toString() {
        StringBuilder r = new StringBuilder(dest+" = Phi");
        for (Operand reg: values){
            r.append(" " + reg);
        }
        return r.toString();
    }

    @Override
    public void accept(IRVisitor visitor){
        visitor.visit(this);
    }

    @Override
    public void updateUseAndDef() {
        throw new RuntimeException("PHI NO USE DEF");
    }

    @Override
    public Register ssadef() {
        return dest;
    }

    @Override
    public HashSet<Register> ssause() {
        HashSet<Register> r = new HashSet<>();
        for (Operand o : values){
            if (o instanceof Register) r.add((Register)o);
        }
        return r;

    }

    @Override
    public void remove() {
        super.remove();
        getBb().phiTable.remove(dest);
    }

    public void removeFrom(int index){
        values.remove(index);
    }
}
