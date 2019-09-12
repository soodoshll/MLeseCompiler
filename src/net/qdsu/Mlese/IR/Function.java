package net.qdsu.Mlese.IR;

import net.qdsu.Mlese.Backend.GraphAllocator;
import net.qdsu.Mlese.IR.Instruction.Branch;
import net.qdsu.Mlese.IR.Instruction.IRInstruction;
import net.qdsu.Mlese.IR.Instruction.Jump;
import net.qdsu.Mlese.IR.Operand.Operand;
import net.qdsu.Mlese.IR.Operand.PhysicalRegister;
import net.qdsu.Mlese.IR.Operand.Register;
import net.qdsu.Mlese.IR.Operand.VirtualRegister;

import java.util.*;

public class Function {
    protected String name;
    private BasicBlock BBStart;

    protected ArrayList<VirtualRegister> parameters = new ArrayList<>();

    public HashSet<Register> global;

    private Operand this_ptr;

    private ArrayList<BasicBlock> bbList = new ArrayList<>();

    public void addBB(BasicBlock bb){
        bbList.add(bb);
    }

    public ArrayList<BasicBlock> getBbList() {
        return bbList;
    }

    public boolean isMember(){
        return this_ptr != null;
    }

    public void addParameter(VirtualRegister par){
        parameters.add(par);
    }

    public ArrayList<VirtualRegister> getParameters() {
        return parameters;
    }

    public void setThis(Operand this_ptr) {
        this.this_ptr = this_ptr;
    }

    public Operand getThis() {
        return this_ptr;
    }

    public Function(String name) {
        this.name = name;
    }

    public BasicBlock getBBStart() {
        return BBStart;
    }

    public void setBBStart(BasicBlock BBStart) {
        this.BBStart = BBStart;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "__"+name;
    }

    private boolean dom_changed;
    private HashSet<BasicBlock> dom_visited;

    public ArrayList<BasicBlock> rpo;

    private void updateBBDom(BasicBlock bb){
        HashSet<BasicBlock> temp = null;
        //System.out.println
        for (IRInstruction pre: bb.getFrom()){
            //System.out.printf("%s from %s\n", bb, pre.getBb());
            if (temp == null) temp = new HashSet<>(pre.getBb().Dom);
            else temp.retainAll(pre.getBb().Dom);
        }
        if (temp == null) temp = new HashSet<>();
        temp.add(bb);
        if (!temp.equals(bb.Dom)){
            bb.Dom = temp;
            dom_changed = true;
        }
    }

    private HashSet<BasicBlock> rpo_visited;

    private void _calRPO(BasicBlock bb){
        if (rpo_visited.contains(bb)) return;
        rpo_visited.add(bb);
        for (BasicBlock next_bb : bb.nextBB()){
            _calRPO(next_bb);
        }
        rpo.add(bb);
    }

    public void calRPO(){
        rpo = new ArrayList<>();
        rpo_visited = new HashSet<>();
        _calRPO(BBStart);
        Collections.reverse(rpo);
    }

    public void calIDom(){

    //    System.out.println("IDOM "+this);

        calRPO();

//        System.out.println(rpo);

        BBStart.Dom.add(BBStart);
        for (BasicBlock bb: bbList){
            if (bb != BBStart){
                bb.Dom = new HashSet<>(bbList);
            }
        }


        dom_changed = true;
        while (dom_changed){
            dom_changed = false;
            for (BasicBlock bb : rpo)
                updateBBDom(bb);
            //for (BasicBlock bb:rpo){
            //    System.out.printf("%s : %s \n", bb, bb.Dom);
            //}
        }
        bbList.forEach(x->x.dom_pre_cnt = x.Dom.size());
        for (BasicBlock bb:bbList){
            bb.Dom.forEach(x->x.DomNext.add(bb));
        }
        Queue<BasicBlock> one_pre = new LinkedList<>();
        for (BasicBlock bb: bbList) {
            if (bb.dom_pre_cnt == 1){
                one_pre.offer(bb);
            }
            while (!one_pre.isEmpty()){
                BasicBlock cur_bb = one_pre.poll();
                for (BasicBlock next_bb: cur_bb.DomNext){
                    if (next_bb.dom_pre_cnt > 0)
                        next_bb.dom_pre_cnt--;
                    if (next_bb.dom_pre_cnt == 1) {
                        one_pre.offer(next_bb);
                        next_bb.IDom = cur_bb;
                    }
                }
            }
        }
    }

    public void calDF(){
        for (BasicBlock bb: bbList){
            if (bb.getFrom().size() > 1){
                for (IRInstruction from: bb.getFrom()){
                    BasicBlock runner = from.getBb();
                    while (runner != bb.IDom && runner != null){
                        //System.out.println(runner);
                        runner.DF.add(bb);
                        runner = runner.IDom;
                    }
                }
            }
        }
    }

    public GraphAllocator.Graph graph;

    public int stack_ptr = -8;
    public int alloca(){
        stack_ptr -= 8;
//        System.out.printf("ALLOCA %s %d\n", this, stack_ptr);
        return stack_ptr;
    }

    public boolean recursive = false;
    public HashSet<Function> called = new HashSet<>();
    public boolean contain_loop = false;

    public HashSet<Register> used_regs = new HashSet<>();
}
