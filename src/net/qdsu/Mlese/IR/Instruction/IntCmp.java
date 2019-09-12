package net.qdsu.Mlese.IR.Instruction;

import net.qdsu.Mlese.AST.BinaryExpr;
import net.qdsu.Mlese.IR.BasicBlock;
import net.qdsu.Mlese.IR.Operand.Operand;
import net.qdsu.Mlese.IR.Operand.PhysicalRegister;
import net.qdsu.Mlese.IR.Operand.Register;
import net.qdsu.Mlese.IR.Operand.VirtualRegister;

import java.util.HashSet;

public class IntCmp extends IRInstruction {

    private Register dest;
    private Operand lhs;
    private Operand rhs;

    private BinaryExpr.Op op;

    public IntCmp(Register dest, BinaryExpr.Op op, Operand lhs, Operand rhs) {
        this.dest = dest;
        this.op = op;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public void accept(IRVisitor visitor){
        visitor.visit(this);
    }

    public void convert(){
        BinaryExpr.Op new_op;
        switch (op){
            case EQ: new_op = BinaryExpr.Op.NE; break;
            case NE: new_op = BinaryExpr.Op.EQ; break;
            case GT: new_op = BinaryExpr.Op.LE; break;
            case GE: new_op = BinaryExpr.Op.LT; break;
            case LT: new_op = BinaryExpr.Op.GE; break;
            case LE: new_op = BinaryExpr.Op.GT; break;
            default: throw new RuntimeException();
        }
        op = new_op;
    }

    @Override
    public String toString() {
        return dest + " = intcmp " + lhs + " " + op + " " + rhs;
    }

    public Register getDest() {
        return dest;
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

    public void setDest(Register dest) {
        this.dest = dest;
    }

    public void setLhs(Operand lhs) {
        this.lhs = lhs;
    }

    public void setRhs(Operand rhs) {
        this.rhs = rhs;
    }

    @Override
    public void updateUseAndDef() {
        def = new HashSet<Register>(){{
            add(dest);
            add(PhysicalRegister.rax); //for cmov
        }};
        use = new HashSet<Register>(){{
            if (lhs instanceof Register) add((Register)lhs);
            if (rhs instanceof Register) add((Register)rhs);
//            add(PhysicalRegister.rax); //for cmov
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
