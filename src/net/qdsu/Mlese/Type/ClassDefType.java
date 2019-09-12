package net.qdsu.Mlese.Type;

import net.qdsu.Mlese.Entity.ClassEntity;

public class ClassDefType extends BasicType {
    private ClassEntity entity;

    public ClassDefType(ClassEntity entity){
        this.entity = entity;
    }

    public ClassEntity getEntity() {
        return entity;
    }

    @Override
    public BasicType getBaseType() {
        return this;
    }

    @Override
    public int getDim() {
        return 0;
    }

    @Override
    public Types getType() {
        return Types.CLASSDEF;
    }

    @Override
    public boolean isSame(Type other) {
        return false;
    }

    public ClassType getInstanceType(){
        return new ClassType(entity);
    }

    @Override
    public int getSize() {
        return entity.getSize();
    }
}
