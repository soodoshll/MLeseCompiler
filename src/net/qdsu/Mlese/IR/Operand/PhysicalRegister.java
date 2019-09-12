package net.qdsu.Mlese.IR.Operand;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;

public class PhysicalRegister extends Register{
    private String name;
    public String name_32;

    public static PhysicalRegister[] reg;

    public PhysicalRegister(String name, String name_32)
    {
        this.name = name;
        this.name_32 = name_32;
    }

    public static PhysicalRegister rax = new PhysicalRegister("rax", "eax");
    public static PhysicalRegister rbx = new PhysicalRegister("rbx", "ebx");
    public static PhysicalRegister rcx = new PhysicalRegister("rcx", "ecx");
    public static PhysicalRegister rdx = new PhysicalRegister("rdx", "edx");
    public static PhysicalRegister rdi = new PhysicalRegister("rdi", "edi");
    public static PhysicalRegister rsi = new PhysicalRegister("rsi", "esi");
    public static PhysicalRegister rbp = new PhysicalRegister("rbp", "ebp");
    public static PhysicalRegister rsp = new PhysicalRegister("rsp", "esp");

    public static HashSet<PhysicalRegister> unusableRegs = new HashSet<PhysicalRegister>(){{
        add(rsp); add(rbp);
    }};

    public static ArrayList<PhysicalRegister> callee_saved;
    public static ArrayList<PhysicalRegister> caller_saved;

    //private static PhysicalRegister[] usableRegs;
    public static int K = 16 - unusableRegs.size();

//    public static int K = 4;

    public static HashSet<PhysicalRegister> getUsableRegs(){
        if (reg == null) initRegs();
        HashSet<PhysicalRegister> result = new HashSet<>();
        for (PhysicalRegister r : reg)
            if (!unusableRegs.contains(r))
                result.add(r);

        return result;
    }

    public static void initRegs(){
        reg = new PhysicalRegister[16];
        reg[0] = rax;
        reg[1] = rbx;
        reg[2] = rcx;
        reg[3] = rdx;
        reg[4] = rdi;
        reg[5] = rsi;
        reg[6] = rbp;
        reg[7] = rsp;
        for (int i = 8; i < 16; ++i)
            reg[i] = new PhysicalRegister("r" + i,"r"+i+"d");

        callee_saved = new ArrayList<PhysicalRegister>(){{
            add(rbx); add(reg[12]); add(reg[13]); add(reg[14]); add(reg[15]);
        }};

        caller_saved = new ArrayList<PhysicalRegister>(){{
            add(rax); add(rcx); add(rdx); add(rsi); add(rdi);
            add(reg[8]); add(reg[9]); add(reg[10]); add(reg[11]);
        }};
    }

    public static  PhysicalRegister R(int index){
        if (index > 15) throw new RuntimeException("index of physical register out of range");
        if (reg == null) {
            initRegs();
        }
        return reg[index];
    }

    private static PhysicalRegister[] calling_regs;
    public final static int callingRegsNum = 6;
    public static PhysicalRegister callingReg(int index){
        if (index >= callingRegsNum)
            throw new RuntimeException("Calling Reg out of range");
        if (reg == null) initRegs();
        if (calling_regs == null) {
            calling_regs = new PhysicalRegister[callingRegsNum];
            calling_regs[0] = rdi;
            calling_regs[1] = rsi;
            calling_regs[2] = rdx;
            calling_regs[3] = rcx;
            calling_regs[4] = reg[8];
            calling_regs[5] = reg[9];
        }
        return calling_regs[index];
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int getDegree() {
        return 100; //A very big value
    }


}
