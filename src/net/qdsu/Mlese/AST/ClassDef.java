package net.qdsu.Mlese.AST;
import net.qdsu.Mlese.Entity.ClassEntity;
import net.qdsu.Mlese.Utility.SourcePos;

import java.util.ArrayList;


public class ClassDef extends ASTNode {
    private ArrayList<VarStmt> variables = new ArrayList<>();
    private ArrayList<FunDef>  functions = new ArrayList<>();
    //private ArrayList<FunDef> constructors = new ArrayList<>();
    private ArrayList<ASTNode> members = new ArrayList<>();
    private String name;

    //private Type type;
    private ClassEntity entity;

    public ClassEntity getEntity() {
        return entity;
    }

    public void setEntity(ClassEntity entity) {
        this.entity = entity;
    }

    public ClassDef(SourcePos pos){
        //this.name = name;
        this.pos = pos;
    }

    public void setName(String name) {
        this.name = name;
    }

    //    public ArrayList<FunDef> getConstructors() {
//        return constructors;
//    }
//
    public ArrayList<FunDef> getFunctions() {
        return functions;
    }

    public ArrayList<VarStmt> getVariables() {
        return variables;
    }

    public String getName() {
        return name;
    }

    public void addVar(VarStmt var){
        variables.add(var);
        members.add(var);
    }

    public void addFun(FunDef fun){
        functions.add(fun);
        members.add(fun);
    }
//    public void addConstructor(FunDef creator){
//        constructors.add(creator);
//        members.add(creator);
//    }

    public void addMember(ASTNode m){
        if (m instanceof FunDef)
            addFun((FunDef)m);
        else if (m instanceof VarStmt)
            addVar((VarStmt)m);
        else throw new RuntimeException("Unknown member in ASTBuilder");
        //members.add(m);
    }

    public ArrayList<ASTNode> getMembers() {
        return members;
    }

    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }
}
