package net.qdsu.Mlese.AST;

import net.qdsu.Mlese.Utility.SourcePos;

public class ForStmt extends Loop {
    private Expr init;
    private Expr cond;
    private Expr step;
    private Stmt stmt;
    public ForStmt(Expr init, Expr cond, Expr step, Stmt stmt, SourcePos pos){
        this.init = (init == null) ? EmptyExpr.getInstance() : init;
        this.cond = (cond == null) ? EmptyExpr.getInstance() : cond;
        this.step = (step == null) ? EmptyExpr.getInstance() : step;
        this.stmt = (stmt == null) ? EmptyExpr.getInstance() : stmt;

        this.pos = pos;
    }

    public Expr getCond() {
        return cond;
    }

    public Expr getInit() {
        return init;
    }

    public Expr getStep() {
        return step;
    }

    public Stmt getStmt() {
        return stmt;
    }

    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }
}
