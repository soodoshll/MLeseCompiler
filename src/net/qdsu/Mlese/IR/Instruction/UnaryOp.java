package net.qdsu.Mlese.IR.Instruction;

import net.qdsu.Mlese.AST.UnaryExpr;
import net.qdsu.Mlese.IR.Operand.Operand;
import net.qdsu.Mlese.IR.Operand.Register;
import net.qdsu.Mlese.IR.Operand.VirtualRegister;

import java.util.HashSet;

public class UnaryOp extends IRInstruction{
    UnaryExpr.Operand op;
    private Register dest;
    private Operand operand;

    public UnaryOp(UnaryExpr.Operand op, Register dest, Operand operand) {
        this.op = op;
        this.dest = dest;
        this.operand = operand;
    }

    @Override
    public void accept(IRVisitor visitor){
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return dest + " = " + op + " " + operand;
    }

    public UnaryExpr.Operand getOp() {
        return op;
    }

    public Register getDest() {
        return dest;
    }

    public Operand getOperand() {
        return operand;
    }

    public void setDest(Register dest) {
        this.dest = dest;
    }

    public void setOperand(Operand operand) {
        this.operand = operand;
    }

    @Override
    public void updateUseAndDef() {
        def = new HashSet<Register>(){{add(dest);}};
        use = new HashSet<Register>(){{
            if (operand instanceof Register)
                add((Register)operand);
        }};
    }

    @Override
    public Register ssadef() {
        return dest;
    }

    @Override
    public HashSet<Register> ssause() {
        return new HashSet<Register>(){{
            if (operand instanceof Register)
                add((Register)operand);
        }};
    }
}
