package net.qdsu.Mlese.IR.Operand;

import net.qdsu.Mlese.IR.Function;

public class FunPtr extends Operand{
    private Function fun;
    public FunPtr(Function fun){
        this.fun = fun;
    }

    public Function getFun() {
        return fun;
    }
}
