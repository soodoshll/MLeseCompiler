package net.qdsu.Mlese.IR.Operand;

import net.qdsu.Mlese.IR.Instruction.IRInstruction;

import java.util.ArrayList;

public class VirtualRegister extends Register {
    private static int globalID = 0;
    private int id;



    public VirtualRegister(){
        globalID++;
        id = globalID;
    }


    //For SSA Renaming
    private VirtualRegister baseReg = this;
    private int offset = 0;
    private int baseRegCnt = 0;
    public VirtualRegister getNewName(){
        if (baseReg.baseRegCnt == 0){
            baseReg.baseRegCnt++;
            return this;
        }else{
            VirtualRegister r = new VirtualRegister(this.name);
            r.offset = baseReg.baseRegCnt;
            r.baseReg = baseReg;
            baseReg.baseRegCnt++;
            return r;
        }
    }

    public VirtualRegister getBaseReg() {
        return baseReg;
    }

    public VirtualRegister(String name){
        this();
        this.name = name;
    }

    private String name;


    @Override
    public String toString() {
        StringBuilder r = new StringBuilder("%"+baseReg.id+"_"+offset);
        if (name != null) r.append("("+name+")");
        return r.toString();
    }

    @Override
    public int getDegree() {
        return edges.size();
    }
}
