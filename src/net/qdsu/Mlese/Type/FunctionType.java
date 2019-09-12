package net.qdsu.Mlese.Type;

import net.qdsu.Mlese.Entity.FunctionEntity;

public class FunctionType extends BasicType {

    private FunctionEntity entity;

    public FunctionEntity getEntity() {
        return entity;
    }

    public FunctionType(FunctionEntity entity){
        this.entity = entity;
    }

    @Override
    public int getDim() {
        return 0;
    }

    @Override
    public Types getType() {
        return Types.FUNC;
    }

    @Override
    public BasicType getBaseType() {
        return this;
    }

    @Override
    public boolean isSame(Type other) {
        return other.getType() == Types.FUNC && entity == ((FunctionType)other).getEntity();
    }

    @Override
    public int getSize() {
        return 0;
    }
}
