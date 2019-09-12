package net.qdsu.Mlese.Type;

public abstract class BasicType extends Type{
    //For class and primitive type

    @Override
    public BasicType getInstanceType() {
        return this;
    }
}
