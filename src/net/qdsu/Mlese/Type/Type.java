package net.qdsu.Mlese.Type;
// This  is the base class of all types.
// Type --- BasicType --- PrimitiveType
//       |             |- FunctionType
//       |             |- ClassType
//       |             |- ClassDefType
//       |- ArrayType


public abstract class Type {
    public enum Types {
        VOID, BOOL, INT, STRING, ARRAY, FUNC, CLASS, ASSIGN, CLASSDEF
    }
    public abstract int getDim();
    public abstract Types getType();
    public abstract BasicType getBaseType();
    public abstract boolean isSame(Type other);
    //public int getSize(){
   //     return 0;
    //
    // }

    public abstract int getSize();

    public boolean isVoid(){return getType() == Types.VOID;}
    public boolean isBool(){return getType() == Types.BOOL;}
    public boolean isInt() {return getType() == Types.INT; }
    public boolean isString() {return getType() == Types.STRING; }
    public boolean isArray() {return getType() == Types.ARRAY; }
    public boolean isFunc() {return getType() == Types.FUNC; }
    public boolean isClass() {return getType() == Types.CLASS; }
    public boolean isAssign() {return getType() == Types.ASSIGN; }
    public boolean isClassDef() {return getType() == Types.CLASSDEF;}
    public Type getInstanceType(){
        return this;
    }

}
