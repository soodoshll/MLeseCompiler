package net.qdsu.Mlese.AST;

import net.qdsu.Mlese.Utility.SourcePos;

public class ReturnStmt extends Stmt {
    private Expr value;
    public ReturnStmt(Expr value, SourcePos pos){
        this.value = value;
        this.pos = pos;
    }

    public Expr getValue() {
        return value;
    }

    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }
}
