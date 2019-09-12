package net.qdsu.Mlese.IR.Operand;

import net.qdsu.Mlese.AST.Expr;

public class GlobalVar extends Operand {
    private static int globalID = 0;
    private int id;

    private Expr init;
    public GlobalVar(Expr init){
        this.init = init;
        globalID++;
        id = globalID;
    }

    private VirtualRegister localReg;

    public void setLocalReg(VirtualRegister localReg) {
        this.localReg = localReg;
    }

    public VirtualRegister getLocalReg() {
        return localReg;
    }

    @Override
    public String toString() {
        return "g_"+id;
    }
}
