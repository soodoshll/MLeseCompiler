package net.qdsu.Mlese.Type;

public class ArrayType extends Type {
    private BasicType basetype;
    private int dim = 0;
    public ArrayType(Type other){
        //To generate a new array type whose base type is other
        this.basetype = other.getBaseType();
        this.dim = other.getDim() + 1;
    }

    public ArrayType(BasicType basetype, int dim){
        this.basetype = basetype;
        this.dim = dim;
    }

    @Override
    public BasicType getBaseType() {
        return basetype;
    }

    @Override
    public int getDim() {
        return dim;
    }

    @Override
    public Types getType() {
        return Type.Types.ARRAY;
    }

    @Override
    public boolean isSame(Type other) {

        return other.isVoid() ||
                dim == other.getDim() && basetype.isSame(other.getBaseType());
    }

    @Override
    public int getSize() {
        return 8;
    }
}
