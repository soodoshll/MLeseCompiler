package net.qdsu.Mlese.AST;

import net.qdsu.Mlese.Utility.SourcePos;

public class IfStmt extends Stmt{
    private Expr cond;
    private Stmt then;
    private Stmt otherwise;

    public IfStmt(Expr cond, Stmt then, Stmt otherwise, SourcePos pos){
        this.cond = (cond == null)? EmptyExpr.getInstance() : cond;
        this.then = then;
        //System.out.println(otherwise);
        this.otherwise = (otherwise == null) ? EmptyExpr.getInstance() : otherwise;

        this.pos = pos;
    }

    public Expr getCond() {
        return cond;
    }

    public Stmt getThen() {
        return then;
    }

    public Stmt getOtherwise() {
        return otherwise;
    }

    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }
}
