package net.qdsu.Mlese.Entity;

import java.util.*;
import net.qdsu.Mlese.AST.*;
import net.qdsu.Mlese.Type.*;
import net.qdsu.Mlese.IR.Function;

public class FunctionEntity extends Entity{

    private Vector<VariableEntity> parameters = new Vector<>();
    private Stmt stmtnode;
    private Type returnType;
    private FunctionType type;

    public boolean builtin = false;


    public FunctionEntity(Type returnType, String name, Stmt stmtnode, Scope scope){
        this.name = name;
        this.stmtnode = stmtnode;
        this.type = new FunctionType(this);
        this.returnType = returnType;
        this.scope = scope;
    }
    //It may seems useless to have the parameter List because we have a scope for every function definition

    public String getName() {
        return name;
    }

    public Vector<VariableEntity> getParameters() {
        return parameters;
    }

    public void addParameter(VariableEntity par){
        parameters.add(par);
    }

    public Stmt getStmtnode() {
        return stmtnode;
    }

    public Type getReturnType() {
        return returnType;
    }

    //public void setReturnType(Type returnType) {
    //    this.returnType = returnType;
    //}

    private Scope scope;

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }


    @Override
    public Type getType() {
        return type;
    }


    public static FunctionEntity builtinFunc(Type.Types returnType, String name, Function ir,
                                             Type.Types ... parameters){
        // To generate a built-in function whose return type and parameters are all primitive type.
        FunctionEntity entity = new FunctionEntity(PrimitiveType.getPrimitiveType(returnType),
                name, null, null);
        entity.bindIR(ir);
        for (Type.Types par: parameters) {
            entity.addParameter(new VariableEntity(null,
                    PrimitiveType.getPrimitiveType(par), VariableEntity.ScopeType.PARAMETER));
        }
        entity.builtin = true;
        return entity;
    }

    private Function irfunction;
    public void bindIR(Function irFunction){
        this.irfunction = irFunction;
    }

    public Function getIR() {
        return irfunction;
    }

    @Override
    public int getSize() {
        return 0;
    }

    public HashSet<VariableEntity> globalWrite = new HashSet<>();
    public HashSet<VariableEntity> globalRead = new HashSet<>();
    public HashSet<FunctionEntity> calledFun  = new HashSet<>();

    public HashSet<VariableEntity> globalWriteClosure = null;
    public HashSet<VariableEntity> globalReadClosure = null;
}
