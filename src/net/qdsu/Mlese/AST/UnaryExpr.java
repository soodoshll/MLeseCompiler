package net.qdsu.Mlese.AST;

import net.qdsu.Mlese.Utility.SourcePos;

public class UnaryExpr extends Expr {
    public enum Operand{
        ERR, LINC, LDEC, RINC, RDEC,
        POS, NEG,
        LNOT, NOT
    }
    private Operand op;
    private Expr subexp;
    public UnaryExpr(Operand op, Expr subexp, SourcePos pos){
        this.op     = op;
        this.subexp = subexp;

        this.pos = pos;
    }

    public Operand getOp() {
        return op;
    }

    public Expr getSubexp() {
        return subexp;
    }

    public String toString(){
        return "("+op+" "+subexp.toString()+")";
    }

    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }
}
