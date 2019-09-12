package net.qdsu.Mlese.AST;

import net.qdsu.Mlese.Utility.SourcePos;

public class IntConst extends Expr {
    private int value;

    public IntConst(int value, SourcePos pos) {
        this.value = value;
        this.pos = pos;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString(){
        return "(int " + value + ")";
    }

    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }
}
