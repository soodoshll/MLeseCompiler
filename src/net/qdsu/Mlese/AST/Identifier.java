package net.qdsu.Mlese.AST;

import net.qdsu.Mlese.Entity.Entity;
import net.qdsu.Mlese.Utility.SourcePos;

public class Identifier extends Expr{
    private String name;
    private Entity entity;

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public Identifier(String name, SourcePos pos){
        this.name = name;
        this.pos = pos;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "(ID "+name+")";
    }

    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }
}
