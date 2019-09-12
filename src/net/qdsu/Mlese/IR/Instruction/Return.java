package net.qdsu.Mlese.IR.Instruction;

import net.qdsu.Mlese.IR.Operand.Operand;
import net.qdsu.Mlese.IR.Operand.PhysicalRegister;
import net.qdsu.Mlese.IR.Operand.Register;

import java.util.HashSet;

public class Return extends IRInstruction {
    private Operand value;
    public Return(){

    }
    public Return(Operand value){
        this.value = value;
    }

    @Override
    public String toString() {
        return "ret "+value;
    }

    @Override
    public void accept(IRVisitor visitor){
        visitor.visit(this);
    }

    public Operand getValue() {
        return value;
    }

    public void setValue(Operand value) {
        this.value = value;
    }

    @Override
    public void updateUseAndDef() {
        def = new HashSet<Register>();
        use = new HashSet<Register>(){{
//            add(PhysicalRegister.rax);
        }};
    }

    @Override
    public Register ssadef() {
        return null;
    }

    @Override
    public HashSet<Register> ssause() {
        return new HashSet<Register>(){{
           if (value instanceof Register) add((Register)value);
        }};
    }
}
