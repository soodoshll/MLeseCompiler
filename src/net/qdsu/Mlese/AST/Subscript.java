package net.qdsu.Mlese.AST;

import net.qdsu.Mlese.Utility.SourcePos;

public class Subscript extends Expr {
    private Expr name;
    private Expr index;
    public Subscript(Expr name, Expr index, SourcePos pos){
        this.name  = name;
        this.index = index;

        this.pos = pos;
    }

    public Expr getName() {
        return name;
    }

    public Expr getIndex() {
        return index;
    }

    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }
}
