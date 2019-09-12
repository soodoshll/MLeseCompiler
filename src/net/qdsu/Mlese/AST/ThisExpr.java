package net.qdsu.Mlese.AST;

import net.qdsu.Mlese.Utility.SourcePos;

public class ThisExpr extends Expr {

    public ThisExpr(SourcePos pos){
        this.pos = pos;
    }

    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }
}
