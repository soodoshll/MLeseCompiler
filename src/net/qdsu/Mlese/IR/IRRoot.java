package net.qdsu.Mlese.IR;


import net.qdsu.Mlese.IR.Operand.GlobalVar;
import net.qdsu.Mlese.IR.Operand.Operand;
import net.qdsu.Mlese.IR.Operand.StaticString;

import java.util.ArrayList;

public class IRRoot {
    private ArrayList<GlobalVar>    global_var = new ArrayList<>();
    private ArrayList<Function> functions = new ArrayList<>();

    public void addFunction(Function fun){
        functions.add(fun);
    }

    public void addGlobalVar(GlobalVar var){
        global_var.add(var);
    }

    public ArrayList<Function> getFunctions() {
        return functions;
    }

    public ArrayList<GlobalVar> getGlobal_var() {
        return global_var;
    }

    public ArrayList<StaticString> strings = new ArrayList<>();

    private Function mainFun;

    public void setMainFun(Function mainFun) {
        this.mainFun = mainFun;
    }

    public Function getMainFun() {
        return mainFun;
    }
}
