package net.qdsu.Mlese.AST;

import net.qdsu.Mlese.Utility.SourcePos;

public class WhileStmt extends Loop{
    private Expr cond;
    private Stmt stmt;
    public WhileStmt(Expr cond, Stmt stmt, SourcePos pos){
        this.cond = cond;
        this.stmt = stmt;

        this.pos = pos;
    }

    public Expr getCond() {
        return cond;
    }

    public Stmt getStmt() {
        return stmt;
    }

    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }
}
