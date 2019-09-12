package net.qdsu.Mlese.Entity;

import net.qdsu.Mlese.AST.FunDef;
import net.qdsu.Mlese.Type.Type;

import java.util.HashSet;

// Entities are components of scopes
// There are three types of entities: class, variable and function

public abstract class Entity {
    protected String name;
    public String getName() {
        return name;
    }

    public abstract Type getType();

    public abstract int getSize();



}
