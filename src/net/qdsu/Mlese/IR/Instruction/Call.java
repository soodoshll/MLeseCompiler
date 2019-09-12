package net.qdsu.Mlese.IR.Instruction;

import net.qdsu.Mlese.IR.Function;
import net.qdsu.Mlese.IR.Operand.Operand;
import net.qdsu.Mlese.IR.Operand.PhysicalRegister;
import net.qdsu.Mlese.IR.Operand.Register;
import net.qdsu.Mlese.IR.Operand.VirtualRegister;

import java.util.ArrayList;
import java.util.HashSet;

public class Call extends IRInstruction{
    private Function callee;
    public ArrayList<Operand> arguments = new ArrayList<>();
    private Register dest;
    public Call(Function callee, Register dest, Operand ... args){
        this.callee = callee;
        this.dest = dest;
        for (Operand arg : args){
            arguments.add(arg);
            //System.out.println(arg);
        }
    }
    public void addArgument(Operand arg){
        arguments.add(arg);
    }

    public void setArgument(int i, Operand arg){
        arguments.set(i, arg);
    }

    public void setDest(Register dest) {
        this.dest = dest;
    }

    @Override
    public void accept(IRVisitor visitor){
        visitor.visit(this);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder(dest+" = call "+callee);
        for (Operand arg : arguments){
            //System.out.println(arg);
            str.append(" ");
            str.append(arg.toString());
        }
        return str.toString();
    }

    public Function getCallee() {
        return callee;
    }

    public ArrayList<Operand> getArguments() {
        return arguments;
    }

    public Register getDest() {
        return dest;
    }

    @Override
    public void updateUseAndDef() {
        def = new HashSet<Register>(){{add(dest);}};
        def.addAll(PhysicalRegister.caller_saved);
        use = new HashSet<Register>(){{
            for (Operand op:arguments){
                if (op instanceof Register)
                    add((Register)op);
            }
        }};
//        use = new HashSet<>(PhysicalRegister.caller_saved);
    }

    @Override
    public Register ssadef() {
        return dest;
    }

    @Override
    public HashSet<Register> ssause() {
        HashSet<Register> r = new HashSet<>();
        for (Operand o : arguments){
            if (o instanceof Register)
                r.add((Register)o);
        }
        return r;
    }
}
