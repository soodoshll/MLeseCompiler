package net.qdsu.Mlese.Entity;
import net.qdsu.Mlese.Type.*;
import net.qdsu.Mlese.IR.Operand.Operand;

public class VariableEntity extends Entity {
    private Type type;

    public enum ScopeType{
        GLOBAL, MEMBER, PARAMETER, LOCAL
    }

    private ScopeType scope_type;
    public VariableEntity(String name, Type type, ScopeType scope_type){
        this.name = name;
        this.type = type;
        this.scope_type = scope_type;
    }


    @Override
    public Type getType() {
        return type;
    }

    public boolean isGlobal() {return scope_type == ScopeType.GLOBAL;}
    public boolean isMember() {return scope_type == ScopeType.MEMBER;}
    public boolean isParameter() {return scope_type == ScopeType.PARAMETER;}
    public boolean isLocal() {return scope_type == ScopeType.LOCAL;}

    private Operand irvalue;
    public void bindIR(Operand irvalue){
        this.irvalue = irvalue;
    }

    public Operand getIR(){
        return irvalue;
    }

    @Override
    public int getSize() {
        if (type.isBool()) return 1;
        else if (type.isClass()) return ((ClassType)type).getEntity().getSize();
        else return 1;
    }
}
