package net.qdsu.Mlese.Entity;
import net.qdsu.Mlese.IR.BuiltinFunction;
import net.qdsu.Mlese.IR.Function;
import net.qdsu.Mlese.Type.Type;

import java.util.*;

public class BuiltinMethod {
    private static Hashtable<String, FunctionEntity> stringmethods;
    private static FunctionEntity arraySize =
            FunctionEntity.builtinFunc(Type.Types.INT, "size", BuiltinFunction.arraySize);
    private static void addFunction(Type.Types returnType, String name,
                                    Function ir, Type.Types ... paramters){
        stringmethods.put(name,FunctionEntity.builtinFunc(returnType, name, ir, paramters));
    }

    public static FunctionEntity stringMethod(String name){
        if (stringmethods == null){
            stringmethods = new Hashtable<>();
            addFunction(Type.Types.INT, "length", BuiltinFunction.stringLen);
            //System.out.println(BuiltinFunction.stringLen);
            addFunction(Type.Types.STRING,"substring", BuiltinFunction.subString,
                    Type.Types.INT, Type.Types.INT);
            addFunction(Type.Types.INT,"parseInt", BuiltinFunction.parseInt);
            addFunction(Type.Types.INT,"ord", BuiltinFunction.ord,
                    Type.Types.INT);
        }
        return stringmethods.get(name);
    }

    public static FunctionEntity getArraySize(){
        return arraySize;
    }
}
