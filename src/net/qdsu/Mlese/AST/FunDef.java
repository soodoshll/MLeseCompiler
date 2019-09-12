package net.qdsu.Mlese.AST;
import net.qdsu.Mlese.Entity.FunctionEntity;
import net.qdsu.Mlese.Entity.VariableEntity;
import net.qdsu.Mlese.IR.Function;
import net.qdsu.Mlese.Utility.SourcePos;

import java.util.ArrayList;
import java.util.HashSet;

public class FunDef extends ASTNode{

    public class parameter{
        public TypeNode type;
        public String name;
        public VariableEntity varEntity;
        public parameter(TypeNode type, String name){
            this.type = type;
            this.name = name;
        }
    }
    private TypeNode type;
    private FunctionEntity entity;

    public FunctionEntity getEntity() {
        return entity;
    }

    public void setEntity(FunctionEntity entity) {
        this.entity = entity;
    }

    private String name;
    private ArrayList<parameter> parameters = new ArrayList<>();
    private Stmt stmt;

    public FunDef(TypeNode type, String name, Stmt stmt, SourcePos pos){
        this.type = type;
        this.name = name;
        this.stmt = stmt;

        this.pos = pos;
    }

    public void addParameter(TypeNode type, String name){
        parameters.add(new parameter(type, name));
    }

    public Stmt getStmt() {
        return stmt;
    }

    public TypeNode getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public ArrayList<parameter> getParameters() {
        return parameters;
    }


    public void setType(TypeNode type) {
        this.type = type;
    }

    private ArrayList<VariableEntity> usedGlobalVar = new ArrayList<>();

    public ArrayList<VariableEntity> getUsedGlobalVar() {
        return usedGlobalVar;
    }

    public void addUsedGlobalVar(VariableEntity var){
        usedGlobalVar.add(var);
    }

    private Function irfunction;
    public void bindIR(Function irfunction){
        this.irfunction = irfunction;
    }

    public Function getIR() {
        return irfunction;
    }


    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }


}
