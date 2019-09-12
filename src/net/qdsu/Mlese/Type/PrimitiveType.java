package net.qdsu.Mlese.Type;

public class PrimitiveType extends BasicType {
    private Types type;
    //@Override
    //private PrimitiveType basetype;
    private PrimitiveType(){
        //basetype = this;
    }

    private PrimitiveType(Types type){
        //basetype = this;
        this.type = type;
    }
    private static PrimitiveType boolins   = new PrimitiveType(Types.BOOL);
    private static PrimitiveType intins    = new PrimitiveType(Types.INT);
    private static PrimitiveType voidins   = new PrimitiveType(Types.VOID);
    private static PrimitiveType stringins = new PrimitiveType(Types.STRING);
    private static PrimitiveType funins    = new PrimitiveType(Types.FUNC);
    private static PrimitiveType assignins = new PrimitiveType(Types.ASSIGN);
    private static PrimitiveType classdefins = new PrimitiveType(Types.CLASSDEF);

    public static PrimitiveType getPrimitiveType(Types type){
        switch(type){
            case BOOL:   return boolins;
            case INT:    return intins;
            case VOID:   return voidins;
            case STRING: return stringins;
            //case FUNC:   return funins;
            default: return null;
        }
    }



//    public static boolean isString(Expr type){
//        return type.getReturnType().isSame(stringins);
//    }
//
//    public static boolean isFun(Expr type){
//        return type.getReturnType().isSame(funins);
//    }
//
//    public static boolean isBool(Expr type){
//        return type.getReturnType().isSame(boolins);
//    }
//
//    public static boolean isInt(Expr type){
//        return type.getReturnType().isSame(intins);
//    }
//
//    public static boolean isVoid(Expr type){
//        return type.getReturnType().isSame(voidins);
//    }
//
//    public static boolean isBool(Type type){
//        return type.isSame(boolins);
//    }
//
//    public static boolean isInt(Type type){
//        return type.isSame(intins);
//    }
//
//    public static boolean isVoid(Type type){
//        return type.isSame(voidins);
//    }
//
//    public static boolean isString(Type type){
//        return type.isSame(stringins);
//    }
//
//    public static boolean isFun(Type type){
//        return type.isSame(funins);
//    }

    public static PrimitiveType getBool() {
        return boolins;
    }

    public static PrimitiveType getFun() {
        return funins;
    }

    public static PrimitiveType getInt() {
        return intins;
    }

    public static PrimitiveType getString() {
        return stringins;
    }

    public static PrimitiveType getVoid() {
        return voidins;
    }

    public static PrimitiveType getAssign(){
        return assignins;
    }

    public static PrimitiveType getClassDef() {return classdefins;}

    @Override
    public PrimitiveType getBaseType() {
        return this;
    }

    @Override
    public int getDim(){
        return 0;
    }

    @Override
    public Types getType(){
        return type;
    }

    @Override
    public boolean isSame(Type other){
        return type == other.getType() && other.getDim() == 0;
    }

    @Override
    public int getSize() {
        //if (isBool()) return 1;
        //else return 1;
        return 8;
    }
}
