package net.qdsu.Mlese.Optim;

import net.qdsu.Mlese.Backend.IRTransform;
import net.qdsu.Mlese.IR.BasicBlock;
import net.qdsu.Mlese.IR.Function;
import net.qdsu.Mlese.IR.IRRoot;
import net.qdsu.Mlese.IR.Instruction.*;
import net.qdsu.Mlese.IR.Operand.Operand;
import net.qdsu.Mlese.IR.Operand.Register;
import net.qdsu.Mlese.IR.Operand.VirtualRegister;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;

public class Inlining implements IRVisitor {
    private Hashtable<Function, Integer> instCnt;
    private IRRoot root;

    public Inlining(IRRoot root) {
        this.root = root;
    }

    private void countInst(){
        instCnt = new Hashtable<>();
        for (Function fun: root.getFunctions()){
            int cnt = 0;
            for (BasicBlock bb : fun.getBbList()) {
                for (IRInstruction ins = bb.getIrStart(); ins != null; ins = ins.getNext())
                    cnt++;
            }
            instCnt.put(fun, cnt);
        }
    }

    private HashSet<Function> used = new HashSet<>();

    private Function selectInline(){
        countInst();
        int min_inst = Integer.MAX_VALUE;
        Function selected = null;
        for (Function func: root.getFunctions()) {
            Integer t = inline_cnt.get(func);
            t = t==null ? 0 : t;
//            System.out.println()
            if (func != root.getMainFun() && !used.contains(func) && t < inline_times && !func.contain_loop) {
                int cnt = instCnt.get(func);
                if (cnt < min_inst) {
                    min_inst = cnt;
                    selected = func;
                }
            }
        }
        return selected;
    }

    private int threshold = 48;

    private Hashtable<Function, Integer> inline_cnt = new Hashtable<>();

    private int inline_times = 4;

    private void _run(){
//        System.out.println("INLINE START");
        Function selected = selectInline();//        System.out.println(selected);
//        System.out.println("SELECT FINISH "+selected+" "+ instCnt.get(selected) );

        if (selected != null && instCnt.get(selected) <= threshold ){
//            used.add(selected);
            boolean changed = false;

//            System.out.println("INLINE "+selected);

            if (inline_cnt.get(selected) == null){
                inline_cnt.put(selected, 1);
            }else{
                inline_cnt.put(selected, inline_cnt.get(selected) + 1);
            }

//            System.out.println(selected);

            System.err.println("inlining "+selected);

            for (Function func : root.getFunctions()){
//                if (func != root.getMainFun()){
                    HashSet<IRInstruction> inst_list = new HashSet<>();
                    for (BasicBlock bb : func.getBbList()) {
                        for (IRInstruction ins = bb.getIrStart(); ins != null; ins = ins.getNext()) {
                            inst_list.add(ins);
                        }
                    }
//                    System.out.println(func+" "+inst_list);
                    for (IRInstruction ins : inst_list){
                        if (ins instanceof Call && ((Call) ins).getCallee() == selected ){
//                            System.out.println("inline "+ins.getBb());
                            changed = true;
                            inline((Call)ins);
                        }
                    }
            }
//            System.out.println("INLINE FINISH");
            if (!changed) inline_cnt.put(selected, inline_times + 1);
            _run();
        }
//        else{
//            System.out.println("FUCK");
//        }

    }

    public void run(){
        _run();
        IRTransform bbcombine = new IRTransform(root);
        bbcombine.BBCombine();
//        System.out.println("COMBINE");
    }

    private Hashtable<Register, Register> rename_table;
    private Hashtable<BasicBlock, BasicBlock> bb_table;
    private Register return_value;
    private BasicBlock return_bb;
    private BasicBlock cur_bb;
    private HashSet<BasicBlock> visited;


    private Register getRegister(Register o){
        Register r  = rename_table.get(o);
        if (r == null){
            VirtualRegister new_reg = new VirtualRegister();
            rename_table.put(o, new_reg);
            return new_reg;
        }else return r;
    }

    private BasicBlock getBB(BasicBlock bb){
        BasicBlock r = bb_table.get(bb);
        if (r == null) {
            BasicBlock new_bb = new BasicBlock(cur_bb.func);
            bb_table.put(bb, new_bb);
            return new_bb;
        }else return r;
    }

    private Operand getOp(Operand o){
        if (o instanceof Register) return getRegister((Register)o);
        else return o;
    }


    private void _copy(BasicBlock bb){
        if (visited.contains(bb)) return;
        visited.add(bb);
        cur_bb = getBB(bb);
//        System.out.println(cur_bb);
        for (IRInstruction ins = bb.getIrStart() ; ins != null; ins = ins.getNext()) {
            ins.accept(this);
        }
    }


//    private HashSet<Phi> phi_table;
    private void inline(Call ins){
        rename_table = new Hashtable<>();
        return_value = ins.getDest();
        visited = new HashSet<>();
        bb_table = new Hashtable<>();
//        phi_table = new HashSet<>();

        Function callee = ins.getCallee();
        return_bb = new BasicBlock(ins.getBb().func);
//        ins.replace(new Jump(return_bb));

        //pass the Parameters 1
        for (int i = 0 ; i < ins.getArguments().size(); i++){
            VirtualRegister arg = new VirtualRegister();
            rename_table.put(callee.getParameters().get(i), arg);
        }


        BasicBlock entry = new BasicBlock(ins.getBb().func, "inline "+callee.getName());

        cur_bb = entry;
        bb_table.put(callee.getBBStart(), entry);
        _copy(callee.getBBStart());

//        phi_table.forEach(x->fixPhi(x));

        return_bb.addInstruction(new Jump(ins.splitAfter()));
        ins.getBb().addInstruction(new Jump(entry));

        //pass the parameters 2
        for (int i = 0 ; i < ins.getArguments().size(); i++) {
            Register arg = rename_table.get(callee.getParameters().get(i));
            ins.insertAfter(new Move(arg, ins.getArguments().get(i)));
        }

        ins.remove();
    }

//    private void fixPhi(Phi ins){
//
//    }

    @Override
    public void visit(IRInstruction inst) {
        throw new RuntimeException();
    }

    @Override
    public void visit(BinaryOp inst) {
        cur_bb.addInstruction(new BinaryOp(inst.getOp(), getRegister(inst.getDest()), getOp(inst.getLhs()), getOp(inst.getRhs())));
    }

    @Override
    public void visit(Branch inst) {
        cur_bb.addInstruction(new Branch(getOp(inst.getCond()), getBB(inst.getIfTrue()), getBB(inst.getIfFalse())));
        _copy(inst.getIfTrue());
        _copy(inst.getIfFalse());
    }

    @Override
    public void visit(Call inst) {
        ArrayList<Operand> args = new ArrayList<>();
        for (Operand o: inst.getArguments())
            args.add(getOp(o));
        Call new_ins = new Call(inst.getCallee(), getRegister(inst.getDest()));
        new_ins.arguments = args;
        cur_bb.addInstruction(new_ins);
    }

    @Override
    public void visit(IntCmp inst) {
        cur_bb.addInstruction(new IntCmp(getRegister(inst.getDest()), inst.getOp(), getOp(inst.getLhs()), getOp(inst.getRhs())));
    }

    @Override
    public void visit(Jump inst) {
        cur_bb.addInstruction(new Jump(getBB(inst.getDest())));
        _copy(inst.getDest());
    }

    @Override
    public void visit(Load inst) {
        cur_bb.addInstruction(new Load(getRegister(inst.getDest()), getOp(inst.getSrc1())));
    }

    @Override
    public void visit(Malloc inst) {
        throw new RuntimeException();
    }

    @Override
    public void visit(Move inst) {
        cur_bb.addInstruction(new Move(getRegister(inst.getDest()), getOp(inst.getSrc())));
    }

    @Override
    public void visit(Store inst) {
        cur_bb.addInstruction(new Store(getOp(inst.getDest1()), getOp(inst.getSrc())));
    }

    @Override
    public void visit(UnaryOp inst) {
        cur_bb.addInstruction(new UnaryOp(inst.getOp(), getRegister(inst.getDest()), getOp(inst.getOperand())));
    }

    @Override
    public void visit(Return inst) {
        if (inst.getValue() != null)
            cur_bb.addInstruction(new Move(return_value, getOp(inst.getValue())));
        cur_bb.addInstruction(new Jump(return_bb));
    }

    @Override
    public void visit(Pop inst) {
        throw new RuntimeException();
    }

    @Override
    public void visit(Push inst) {
        throw new RuntimeException();
    }

    @Override
    public void visit(Phi inst) {
        Phi new_ins = new Phi(getRegister(inst.getDest()), inst.getSlotNum());
//        phi_table.add(new_ins);
        cur_bb.addInstruction(new_ins);
        cur_bb.phiTable.put((VirtualRegister)new_ins.getDest(), new_ins);
    }

    @Override
    public void visit(Lea inst) {
        cur_bb.addInstruction(new Lea(getRegister(inst.getDest()),
                    getOp(inst.getBase()),
                inst.getOffset(),
                getOp(inst.getIndex()),
                inst.getOffset()
                ));
    }
}
