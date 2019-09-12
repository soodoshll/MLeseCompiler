package net.qdsu.Mlese.AST;

import net.qdsu.Mlese.Utility.SourcePos;

public class BoolConst extends Expr {
    private boolean value;

    public boolean getValue() {
        return value;
    }

    public BoolConst(boolean value, SourcePos pos) {
        this.value = value;
        this.pos = pos;
    }

    public String toString(){
        return "(bool "+value+")";
    }

    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }
}
