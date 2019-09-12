package net.qdsu.Mlese.IR;

public class BuiltinFunction extends Function{

    private boolean is_member;

    public BuiltinFunction(String name, boolean is_member){
        super(name);
        this.is_member = is_member;
    }

    @Override
    public String toString() {
        return "."+name;
    }

    @Override
    public boolean isMember() {
        return is_member;
    }

    public static BuiltinFunction arraySize = new BuiltinFunction("arraySize", true);
    public static BuiltinFunction print = new BuiltinFunction("_print", false);
    public static BuiltinFunction println = new BuiltinFunction("puts", false);
    public static BuiltinFunction getString = new BuiltinFunction("_getString", false);
    public static BuiltinFunction getInt = new BuiltinFunction("_getInt", false);
    public static BuiltinFunction toString = new BuiltinFunction("_toString", false);
    public static BuiltinFunction stringEQ = new BuiltinFunction("_stringEQ", false);
    public static BuiltinFunction stringLT = new BuiltinFunction("_stringLT", false);
    public static BuiltinFunction stringConcat = new BuiltinFunction("_stringConcat", false);
    public static BuiltinFunction stringLen = new BuiltinFunction("strlen", true);
    public static BuiltinFunction subString = new BuiltinFunction("_subString", true);
    public static BuiltinFunction parseInt = new BuiltinFunction("atoi", true);
    public static BuiltinFunction ord = new BuiltinFunction("_ord", true);

    public static BuiltinFunction malloc = new BuiltinFunction("malloc", false);
    public static BuiltinFunction printInt = new BuiltinFunction("_printInt", false);
    public static BuiltinFunction printlnInt = new BuiltinFunction("_printlnInt", false);

}
