package net.qdsu.Mlese.Type;

import net.qdsu.Mlese.Entity.ClassEntity;

public class ClassType extends BasicType{
    //private String name;
    private ClassEntity entity;

    public ClassEntity getEntity() {
        return entity;
    }

    //public void setEntity(ClassEntity entity) {
    //    this.entity = entity;
    //}

    public ClassType(ClassEntity entity){
        this.entity = entity;
    }

    //public String getName() {
    //    return name;
    //}

    @Override
    public int getDim() {
        return 0;
    }

    @Override
    public Types getType() {
        return Type.Types.CLASS;
    }

    @Override
    public BasicType getBaseType() {
        return this;
    }

    @Override
    public boolean isSame(Type other) {
        return (this==other)
                || other.getType() == Types.VOID
                || other.getType() == Types.CLASS &&
                ((ClassType)other).getEntity() == entity ;
    }

    //private int size;

    @Override
    public int getSize() {
        return entity.getSize();
    }
}
