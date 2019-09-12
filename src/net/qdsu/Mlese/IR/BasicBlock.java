package net.qdsu.Mlese.IR;

import net.qdsu.Mlese.Backend.SSATransform;
import net.qdsu.Mlese.IR.Instruction.*;
import net.qdsu.Mlese.IR.Operand.Register;
import net.qdsu.Mlese.IR.Operand.VirtualRegister;
import net.qdsu.Mlese.Optim.ValueNumbering;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;

public class BasicBlock {



    private IRInstruction irStart;
    private IRInstruction irEnd;

    private static int globalID = 0;
    private int id;

    //public int inDeg = 0;
    //public void incInDeg(){
    //    ++inDeg;
    //}

    public int getInDeg() {
        return fromlist.size();
    }

    private ArrayList<IRInstruction> fromlist = new ArrayList<>();

    public void addFrom(IRInstruction pre_ins){
        if (!fromlist.contains(pre_ins))
            fromlist.add(pre_ins);
    }

    public ArrayList<IRInstruction> getFrom() {
        return fromlist;
    }

    public BasicBlock IDom;
    public HashSet<BasicBlock> Dom = new HashSet<>();
    public HashSet<BasicBlock> DomNext = new HashSet<>();
    public HashSet<BasicBlock> DF = new HashSet<>();
    public int dom_pre_cnt;

    public Hashtable<VirtualRegister, Phi> phiTable = new Hashtable<>();

    public boolean empty(){
        return irStart == null;
    }

    public Function func;

    public BasicBlock(Function func) {
        globalID++;
        id = globalID;
        this.func = func;
        func.addBB(this);
    }

    public BasicBlock(Function func, String hint){
        this(func);
        this.hint = hint;
    }

    public void addInstruction(IRInstruction ins){
        //System.out.printf("Add Ins: %s  <- %s\n", this, ins);
        if (irStart == null){
            irStart = ins;
            irEnd = ins;
            ins.setPrev(null);
            ins.setNext(null);
        }else {
            irEnd.setNext(ins);
            ins.setPrev(irEnd);
            ins.setNext(null);
            irEnd = ins;
        }
        ins.setBb(this);
        if (ins instanceof Call)
            if (((Call) ins).getCallee() == func) {
                func.recursive = true;
//                System.out.println("GOULA");
            }
//        if (ins instanceof Jump)
//            ((Jump) ins).getDest().addFrom(this);
//        else if (ins instanceof Branch){
//            ((Branch) ins).getIfTrue().addFrom(this);
//            ((Branch) ins).getIfFalse().addFrom(this);
//        }
    }

    public IRInstruction getIrEnd() {
        return irEnd;
    }

    public IRInstruction getIrStart() {
        return irStart;
    }

    public void setIrStart(IRInstruction irStart) {
        this.irStart = irStart;
    }

    public void setIrEnd(IRInstruction irEnd) {
        this.irEnd = irEnd;
    }

    public ArrayList<BasicBlock> nextBB(){
        IRInstruction last = irEnd;
        ArrayList<BasicBlock> result= new ArrayList<>();
        if (last instanceof Jump){
            result.add(((Jump) last).getDest());
        }else if (last instanceof Branch){
            result.add(((Branch) last).getIfTrue());
            result.add(((Branch) last).getIfFalse());
        }
        //System.out.println(this + " "+result);
        return result;
    }

    public void fillPhi(VirtualRegister reg, VirtualRegister arg, IRInstruction from){
        //Get the phi instruction
        Phi ins = phiTable.get(reg);
        if (ins == null) throw new RuntimeException("No phi instruction inserted for "+reg);
        int index = fromlist.indexOf(from);
        if (index == -1) throw new RuntimeException("No phi slot for "+ from);
        //System.out.printf("%s insert phi %s at %d\n", reg, arg, index);
        ins.setSlot(index, arg);
        arg.usedIns.add(ins);
    }

    public SSATransform.Renamer.Scope rename_table;

    @Override
    public String toString() {
        if (this == func.getBBStart()) return func.getName();
        return "label_"+id;
    }

    public String hint ="";

    public HashSet<Register> gen;
    public HashSet<Register> kill;
    public HashSet<Register> livein;
    public HashSet<Register> liveout;


    //For Constant Propagation
    public HashSet<BasicBlock> visited_from = new HashSet<>();

    //For Global Value Numbering
    public ValueNumbering.Scope vn_scope;

    //For dead code elimination
    public BasicBlock RIDom;
    public HashSet<BasicBlock> RDom = new HashSet<>();
    public HashSet<BasicBlock> RDF = new HashSet<>();
    public HashSet<BasicBlock> RDomNext = new HashSet<>();
    public int rdom_pre_cnt;

    public void SSARemoveFrom(IRInstruction from){
        int index = fromlist.indexOf(from);
        if (index < 0) return;
        for (IRInstruction ins = irStart; ins !=null; ins = ins.getNext()){
            if (ins instanceof Phi)
                ((Phi) ins).removeFrom(index);
        }
        fromlist.remove(index);
    }

    public void SSAReplaceFrom(IRInstruction old, IRInstruction nova){
        int index = fromlist.indexOf(old);
        fromlist.set(index, nova);
    }
}
