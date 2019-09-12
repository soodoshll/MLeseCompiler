package net.qdsu.Mlese.AST;

import net.qdsu.Mlese.Utility.SourcePos;

public class BinaryExpr extends Expr{
    public enum Op {
        ERR,ADD, SUB, MUL, DIV, MOD,
        LT, GT, EQ, NE, GE, LE,
        LAND, LOR,
        SHL, SHR,
        AND, OR, XOR,
        ASSIGN
    };
    private Op op;
    private Expr left;
    private Expr right;

    public Expr getLeft() {
        return left;
    }

    public Expr getRight() {
        return right;
    }

    public Op getOp() {
        return op;
    }

    public BinaryExpr(Op op, Expr left, Expr right, SourcePos pos){
        this.op = op;
        this.left = left;
        this.right = right;

        this.pos = pos;
        //System.out.println("left:"+left);
        //System.out.println("right:"+right);
    }
    public String toString(){
        return "("+op+" "+left.toString() + " " + right.toString()+")";
    }

    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }
}
