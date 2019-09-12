package net.qdsu.Mlese.Entity;
import java.util.*;

import net.qdsu.Mlese.IR.Function;
import net.qdsu.Mlese.Type.*;

public class ClassEntity extends Entity{
    private FunctionEntity constructor;
    private ClassDefType type;
    private Scope scope;

    //private Function constructor;


    public Scope getScope() {
        return scope;
    }

    public String getName() {
        return name;
    }


    public FunctionEntity getConstructor() {
        return constructor;
    }

    public void setConstructor(FunctionEntity constructor) {
        this.constructor = constructor;
    }

    public Type getType() {
        return type;
    }
    public Type getInstanceType(){
        return type.getInstanceType();
    }
    public ClassEntity(String name, Scope scope){
        this.name = name;
        //this.type = type;
        this.scope = scope;
        this.type = new ClassDefType(this);
    }

    private Hashtable<VariableEntity, Integer> elementPos = new Hashtable<>();


    public void calElementPos(){
        int pos = 0;
        for (VariableEntity var: scope.getVarlist()){
            elementPos.put(var, pos);
            pos += 8;
        }
    }

    public int getElementPos(VariableEntity var){
//        Integer size = elementPos.get(var);
//        if (size == null)
//            throw new RuntimeException("member not found");
//        else
//            return size;
        return scope.getVarlist().indexOf(var) * 8;
    }

    private Integer size;

    public void calSize() {
        size = scope.getVarlist().size() * 8;
        //for (VariableEntity var : scope.getVarlist()){
        //    size += 8;  //All type of elements are in size 8(bytes)
        //}
        //System.out.println("cal the size " + size);
    }

    @Override
    public int getSize() {
        //if (size == null) calSize();
        return scope.getVarlist().size() * 8;
    }
}
