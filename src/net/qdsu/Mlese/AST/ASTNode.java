package net.qdsu.Mlese.AST;

import net.qdsu.Mlese.Utility.SourcePos;
import net.qdsu.Mlese.Entity.Scope;

public abstract class ASTNode {
    //The base class of all ASTNodes
    protected SourcePos pos;
    protected Scope scope;

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public SourcePos getPos() {
        return pos;
    }

    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }
}
