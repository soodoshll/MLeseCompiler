package net.qdsu.Mlese.IR.Instruction;

import net.qdsu.Mlese.AST.BinaryExpr;
import net.qdsu.Mlese.IR.Operand.*;

import java.util.HashSet;

public class BinaryOp extends IRInstruction {

    private Operand lhs;
    private Operand rhs;
    private BinaryExpr.Op op;
    private Register dest;

    public BinaryOp(BinaryExpr.Op op, Register dest, Operand lhs, Operand rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
        this.op = op;
        this.dest = dest;
    }

    @Override
    public void accept(IRVisitor visitor){
        visitor.visit(this);
    }

    public Operand getLhs() {
        return lhs;
    }

    public Operand getRhs() {
        return rhs;
    }

    public BinaryExpr.Op getOp() {
        return op;
    }

    public Register getDest() {
        return dest;
    }

    public void setLhs(Operand lhs) {
        this.lhs = lhs;
    }

    public void setRhs(Operand rhs) {
        this.rhs = rhs;
    }

    public void setDest(Register dest) {
        this.dest = dest;
    }

    public void swapOperands(){
        Operand t = lhs;
        lhs = rhs;
        rhs = t;
    }

    @Override
    public String toString() {
        return dest+" = "+lhs+" "+op+" "+rhs;
    }

    @Override
    public void updateUseAndDef() {
        use = new HashSet<Register>(){{
            if (lhs instanceof Register) add((Register)lhs);
            if (rhs instanceof Register) add((Register)rhs);
            if (op == BinaryExpr.Op.DIV || op == BinaryExpr.Op.MOD)  {
                add(PhysicalRegister.rdx);
                add(PhysicalRegister.rax);
            }
        }};
        def = new HashSet<Register>(){{
            add(dest);
            if (op == BinaryExpr.Op.DIV || op == BinaryExpr.Op.MOD)  {
                add(PhysicalRegister.rdx);
                add(PhysicalRegister.rax);
            }
        }};
    }

    @Override
    public Register ssadef() {
        return dest;
    }

    @Override
    public HashSet<Register> ssause() {
        return new HashSet<Register>(){{
            if (lhs instanceof Register) add((Register)lhs);
            if (rhs instanceof Register) add((Register)rhs);
        }};
    }
}
