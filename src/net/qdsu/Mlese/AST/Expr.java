package net.qdsu.Mlese.AST;

import net.qdsu.Mlese.IR.BasicBlock;
import net.qdsu.Mlese.Type.Type;
import net.qdsu.Mlese.IR.Operand.Operand;

public abstract class Expr extends Stmt {

    protected Type type;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }


    protected boolean lvalue = false;
    public boolean getLvalue() {
        return lvalue;
    }

    public void setLvalue(boolean lvalue) {
        this.lvalue = lvalue;
    }

    protected Operand IROperand;

    public void setIROperand(Operand IROperand) {
        this.IROperand = IROperand;
    }

    public Operand getIROperand() {
        return IROperand;
    }

    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }

    public BasicBlock ifTrue;
    public BasicBlock ifFalse;

    public boolean isRef = false;

    public boolean all_id = false;
}
