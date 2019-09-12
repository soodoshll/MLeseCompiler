package net.qdsu.Mlese.AST;

import net.qdsu.Mlese.Utility.SourcePos;

public class StringConst extends Expr {
    private String value;
    private String literal;

    public StringConst(String value, SourcePos pos) {
        this.value = value;
        this.pos = pos;
    }

    public String getValue() {
        return value;
    }
    public String toString(){
        return "(string "+value+")";
    }
    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }
}
