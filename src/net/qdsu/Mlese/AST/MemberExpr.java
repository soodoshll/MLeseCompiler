package net.qdsu.Mlese.AST;

import net.qdsu.Mlese.Entity.Entity;
import net.qdsu.Mlese.IR.Operand.Operand;
import net.qdsu.Mlese.Utility.SourcePos;

public class MemberExpr extends Expr {
    private String name;
    private Expr prefix;

    public Entity entity;

    public MemberExpr(Expr prefix, String name, SourcePos pos){
        this.prefix = prefix;
        this.name = name;
        this.pos = pos;
    }

    public String getName() {
        return name;
    }

    public Expr getPrefix() {
        return prefix;
    }

    @Override
    public String toString() {
        return "(member " + prefix.toString() + " " + name + ")";
    }

    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }

    //public Op memoryAddr;
    //Used in IRBuilder, assigning to a member of a class
}
