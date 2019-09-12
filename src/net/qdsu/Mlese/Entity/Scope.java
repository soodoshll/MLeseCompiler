package net.qdsu.Mlese.Entity;

import net.qdsu.Mlese.AST.*;
import net.qdsu.Mlese.IR.BuiltinFunction;
import net.qdsu.Mlese.Type.*;
import net.qdsu.Mlese.Utility.SemanticError;
import net.qdsu.Mlese.Utility.SourcePos;
//import net.qdsu.Mlese.Type.Type;

import java.util.*;

public class Scope {
    private Scope father;
    //private ArrayList<Scope> children;

    //public ArrayList<Scope> getChildren() {
    //    return children;
    //}

    public Scope getFather() {
        return father;
    }

    //public void addChild(Scope c){
    //    children.add(c);
    //}

    private Hashtable<String, VariableEntity> variables = new Hashtable<>();
    private Hashtable<String, FunctionEntity> functions = new Hashtable<>();
    private Hashtable<String, ClassEntity>    classes = new Hashtable<>();

    private ArrayList<VariableEntity> varlist = new ArrayList<>();

    public void addVar(VariableEntity var){
        variables.put(var.getName(), var);
        varlist.add(var);
    }

    public ArrayList<VariableEntity> getVarlist() {
        return varlist;
    }

    public void addFun(FunctionEntity fun){
        functions.put(fun.getName(), fun);
    }

    public void addClass(ClassEntity classdef){
        classes.put(classdef.getName(), classdef);
    }

    public Collection<VariableEntity> getVarEntities(){
        return variables.values();
    }

    public Entity getLocalEntity(String name){
        ClassEntity classresult = classes.get(name);
        if (classresult != null) return classresult;
        VariableEntity varresult = variables.get(name);
        if (varresult != null) return varresult;
        FunctionEntity funresult = functions.get(name);
        if (funresult != null) return funresult;

        return null;
    }

    public Entity getEntity(String name){
        Entity local = getLocalEntity(name);
        if (local != null) return local;
        if (father == null) return null;
        else return father.getEntity(name);
    }
    public Scope(){

    }

    public Scope(Scope father){
        this.father = father;
    }



    public void initBuiltinFunc(){
        addFun(FunctionEntity.builtinFunc(Type.Types.VOID, "print",
                BuiltinFunction.print, Type.Types.STRING));
        addFun(FunctionEntity.builtinFunc(Type.Types.VOID, "println",
                BuiltinFunction.println, Type.Types.STRING));
        addFun(FunctionEntity.builtinFunc(Type.Types.STRING, "getString",
                BuiltinFunction.getString));
        addFun(FunctionEntity.builtinFunc(Type.Types.INT, "getInt",
                BuiltinFunction.getInt));
        addFun(FunctionEntity.builtinFunc(Type.Types.STRING, "toString",
                BuiltinFunction.toString, Type.Types.INT));
    }

    public boolean isNameValid(String name){
        Entity local_result = getLocalEntity(name);
        return local_result == null;
    }

    public BasicType getBasicTypeByName(String name){
        switch(name){
            case "bool": return PrimitiveType.getPrimitiveType(Type.Types.BOOL);
            case "int" : return PrimitiveType.getPrimitiveType(Type.Types.INT);
            case "string" : return PrimitiveType.getPrimitiveType(Type.Types.STRING);
            case "void" : return PrimitiveType.getPrimitiveType(Type.Types.VOID);
            default:
                Entity result = getEntity(name);
                if (result == null)
                    return null;
                if (result.getType().getType() == Type.Types.CLASSDEF)
                    return (ClassDefType)result.getType();
        }
        return null;
    }

    public Type getTypeByNode(TypeNode node){
        BasicType bt = getBasicTypeByName(node.getTypename());
        if (bt == null) throw new SemanticError(node.getPos(), "No such type");
        BasicType basictype = bt.getInstanceType();
        if (basictype == null) return null;
        if (node.getDim() == 0) return basictype;
        else return new ArrayType(basictype, node.getDim());
    }

    public void print(){
        System.out.println("===== SCOPE =====");

        Enumeration name;
        String str;

        System.out.println(">> Variable:");
        name = variables.keys();
        while (name.hasMoreElements()){
            str = (String) name.nextElement();
            System.out.printf("    %s\n", str);
        }
        System.out.println(">> Function:");
        name = functions.keys();
        while (name.hasMoreElements()){
            str = (String) name.nextElement();
            System.out.printf("    %s\n", str);
        }
        System.out.println(">> Class:");
        name = classes.keys();
        while (name.hasMoreElements()){
            str = (String) name.nextElement();
            System.out.printf("    %s\n", str);
        }
        if (father !=null)
            father.print();
    }

}
