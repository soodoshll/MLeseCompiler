package net.qdsu.Mlese.AST;

import net.qdsu.Mlese.Utility.SourcePos;

import java.util.Vector;

public class FunCall extends Expr {
    private Expr       name;
    private Vector<Expr> arguments = new Vector<>();

    public FunCall(Expr name, SourcePos pos){
        this.name = name;
        this.pos = pos;
    }


    public void addArgument(Expr arg){
        arguments.add(arg);
    }

    public Expr getName() {
        return name;
    }

    public Vector<Expr> getArguments() {
        return arguments;
    }

    @Override
    public String toString() {
        return name.toString();
    }

    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }
}
