package net.qdsu.Mlese.Optim;

import net.qdsu.Mlese.AST.BinaryExpr;
import net.qdsu.Mlese.IR.BasicBlock;
import net.qdsu.Mlese.IR.Function;
import net.qdsu.Mlese.IR.IRRoot;
import net.qdsu.Mlese.IR.Instruction.*;
import net.qdsu.Mlese.IR.Operand.Immediate;
import net.qdsu.Mlese.IR.Operand.Operand;
import net.qdsu.Mlese.IR.Operand.Register;

import java.util.*;

public class ConstantPropagation implements IRVisitor {
    public static class Value{
        public  enum Type {
            UNDEF, NAC, C
        };

        Type type;
        int value;

        public Value(Type type) {
            this.type = type;
        }

        public Value(Type type, int value) {
            this.type = type;
            this.value = value;
        }

        public static Value undef = new Value(Type.UNDEF);
        public static Value nac   = new Value(Type.NAC);
        public static Value c(int v){
            return new Value(Type.C, v);
        }
        public Value lower(Value o){
            if (this.type == Type.NAC || o.type == Type.NAC) return nac;
            else if (this.type == Type.UNDEF) return o;
            else if (o.type == Type.UNDEF) return this;
            else {
                assert(type == Type.C && o.type == Type.C);
                if (value == o.value) return this;
                else return nac;
            }
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Value)) return false;
            Value o = (Value)obj;
            if (this.type != o.type) return false;
            if (this.type == Type.C) return this.value == o.value;
            else return true;
        }
    }

    public static class Rewriter implements IRVisitor{
        private Hashtable<Register, Value> value;

        public Rewriter(Hashtable<Register, Value> value) {
            this.value = value;
        }

        public Value getValue(Operand op){
            if (op instanceof Immediate) return new Value(Value.Type.C, ((Immediate) op).getValue());
            else if (op instanceof Register) return value.get(op);
            else return Value.nac;
        }

        @Override
        public void visit(IRInstruction inst) {
            throw new RuntimeException();
        }

        private void removeUse(Operand o, IRInstruction inst){
            if (o instanceof Register) ((Register) o).usedIns.remove(inst);
        }

        @Override
        public void visit(BinaryOp inst) {
            Value dest = getValue(inst.getDest());
            if (dest.type == Value.Type.C){
                IRInstruction new_ins = new Move(inst.getDest(), new Immediate(dest.value));
                inst.getDest().ssadef = new_ins;
                removeUse(inst.getLhs(), inst);
                removeUse(inst.getRhs(), inst);
                inst.replace(new_ins);
                return;
            }

            Value lhs = getValue(inst.getLhs());
            Value rhs = getValue(inst.getRhs());
            if (lhs.type == Value.Type.C){
                removeUse(inst.getLhs(), inst);
                inst.setLhs(new Immediate(lhs.value));
            }
            if (rhs.type == Value.Type.C){
                removeUse(inst.getRhs(), inst);
                inst.setRhs(new Immediate(rhs.value));
            }
        }

        @Override
        public void visit(Branch inst) {
            Value cond = getValue(inst.getCond());
            if (cond.type == Value.Type.C){
                removeUse(inst.getCond(), inst);
                inst.setCond(new Immediate(cond.value));
                if (cond.value == 0) {
                    inst.getIfTrue().SSARemoveFrom(inst);
                    IRInstruction new_ins = new Jump(inst.getIfFalse());
                    inst.replace(new_ins);
                    inst.getIfFalse().SSAReplaceFrom(inst, new_ins);
                }
                if (cond.value == 1) {
                    inst.getIfFalse().SSARemoveFrom(inst);
                    IRInstruction new_ins = new Jump(inst.getIfTrue());
                    inst.replace(new_ins);
                    inst.getIfTrue().SSAReplaceFrom(inst, new_ins);
                }
            }
        }

        @Override
        public void visit(Call inst) {
            for (int i = 0 ; i < inst.getArguments().size() ; i++){
                Operand par = inst.getArguments().get(i);
                Value par_v = getValue(par);
                if (par_v.type == Value.Type.C){
                    removeUse(par, inst);
                    inst.getArguments().set(i, new Immediate(par_v.value));
                }
            }
        }

        @Override
        public void visit(IntCmp inst) {
            Value dest = getValue(inst.getDest());
            if (dest.type == Value.Type.C){
                IRInstruction new_ins = new Move(inst.getDest(), new Immediate(dest.value));
                inst.getDest().ssadef = new_ins;
                removeUse(inst.getLhs(), inst);
                removeUse(inst.getRhs(), inst);
                inst.replace(new_ins);
                return;
            }

            Value lhs = getValue(inst.getLhs());
            Value rhs = getValue(inst.getRhs());
            if (lhs.type == Value.Type.C){
                removeUse(inst.getLhs(), inst);
                inst.setLhs(new Immediate(lhs.value));
            }
            if (rhs.type == Value.Type.C){
                removeUse(inst.getRhs(), inst);
                inst.setRhs(new Immediate(rhs.value));
            }
        }

        @Override
        public void visit(Jump inst) {

        }

        @Override
        public void visit(Load inst) {
            Value src = getValue(inst.getSrc1());
            if (src.type == Value.Type.C){
                removeUse(inst.getSrc1(), inst);
                inst.setSrc1(new Immediate(src.value));
            }


        }

        @Override
        public void visit(Malloc inst) {
            throw new RuntimeException();
        }

        @Override
        public void visit(Move inst) {
            Value src = getValue(inst.getSrc());
            if (src.type == Value.Type.C){
                removeUse(inst.getSrc(), inst);
                inst.setSrc(new Immediate(src.value));
            }
        }

        @Override
        public void visit(Store inst) {
            Value src = getValue(inst.getSrc());
            if (src.type == Value.Type.C){
                removeUse(inst.getSrc(), inst);
                inst.setSrc(new Immediate(src.value));
            }

            Value dest = getValue(inst.getDest1());
            if (dest.type == Value.Type.C){
                removeUse(inst.getDest1(), inst);
                inst.setDest1(new Immediate(dest.value));
            }
        }

        @Override
        public void visit(UnaryOp inst) {
            Value dest = getValue(inst.getDest());
            if (dest.type == Value.Type.C){
                IRInstruction new_ins = new Move(inst.getDest(), new Immediate(dest.value));
                inst.getDest().ssadef = new_ins;
                removeUse(inst.getOperand(), inst);
                inst.replace(new_ins);
                return;
            }

            Value op = getValue(inst.getOperand());
            if (op.type == Value.Type.C){
                removeUse(inst.getOperand(), inst);
                inst.setOperand(new Immediate(op.value));
            }

        }

        @Override
        public void visit(Return inst) {
            Value v = getValue(inst.getValue());
            if (v.type == Value.Type.C){
                removeUse(inst.getValue(), inst);
                inst.setValue(new Immediate(v.value));
            }
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
            for (int i=0; i < inst.getSlotNum(); i++){
                Value v = getValue(inst.getArg(i));
                if (v.type == Value.Type.C){
                    removeUse(inst.getArg(i), inst);
                    inst.setSlot(i, new Immediate(v.value));
                }
            }
        }

        @Override
        public void visit(Lea inst) {
            Value v = getValue(inst.getDest());
            if (v.type == Value.Type.C){
                removeUse(inst.getBase(), inst);
                removeUse(inst.getIndex(), inst);
                IRInstruction new_ins = new Move(inst.getDest(), new Immediate(v.value));
                inst.getDest().ssadef = new_ins;
                inst.replace(new_ins);
                return;
            }

            Value base = getValue(inst.getBase());
            if (base.type == Value.Type.C){
                removeUse(inst.getBase(), inst);
                inst.setBase(new Immediate(base.value));
            }

            Value index = getValue(inst.getIndex());
            if (index.type == Value.Type.C){
                removeUse(inst.getIndex(), inst);
                inst.setIndex(new Immediate(index.value));
            }

        }
    }

    private HashSet<BasicBlock> exec;
    private Hashtable<Register, Value> value;
    private Queue<IRInstruction> swl;
    private Queue<BasicBlock> fwl;

    private IRRoot root;

    public ConstantPropagation(IRRoot root) {
        this.root = root;
    }

    public void run(){
        for (Function func:root.getFunctions()) {
//            System.out.printf("start %s\n", func);
            doFunc(func);

//            System.out.printf("finished %s\n", func);
        }
//        System.out.println("FINISH ALL FUNCTIONS");
        NaiveDCE dce = new NaiveDCE(root);
        dce.run();
//        System.out.println("FINISH DCE");
    }


    private void delUselessBlock(Function func){
        HashSet<BasicBlock> del_bb = new HashSet<>(func.getBbList());
        del_bb.removeAll(exec);
        func.getBbList().removeAll(del_bb);
        System.err.println(del_bb);

        for (BasicBlock bb : del_bb){
            for (BasicBlock next_bb : bb.nextBB())
                next_bb.SSARemoveFrom(bb.getIrEnd());
            for (IRInstruction ins = bb.getIrStart(); ins!= null; ins=ins.getNext()) {
                ins.remove();
                for (Register r : ins.ssause())
                    r.usedIns.remove(ins);
            }
        }
    }

    private static HashSet<BasicBlock> init_visited;
    private static HashSet<BasicBlock> fwl_visited;
    public void doFunc(Function func){
        exec = new HashSet<>();
        value = new Hashtable<>();
        swl = new LinkedList<>();
        fwl = new LinkedList<>();

        init_visited = new HashSet<>();
        fwl_visited = new HashSet<>();


        init(func.getBBStart());

        fwl.add(func.getBBStart());

        for (Register par : func.getParameters()) {
            value.put(par, Value.nac);
            swl.addAll(par.usedIns);
        }


        while (!fwl.isEmpty() || !swl.isEmpty()){
            if (!fwl.isEmpty()) doFwl();
            else if (!swl.isEmpty()) doSwl();
        }


        delUselessBlock(func);
        Rewriter rewriter = new Rewriter(value);

        //remove not executable
//        HashSet<BasicBlock> unexec = new HashSet<>();
//        for (BasicBlock bb : func.getBbList())
//            if (!exec.contains(bb)) {
//                for (IRInstruction ins = bb.getIrStart(); ins != null; ins = ins.getNext()) {
//                    for (Register r : ins.ssause())
//                        r.usedIns.remove(ins);
//                    unexec.add(bb);
//                }
//            }
//
//        func.getBbList().removeAll(unexec);

        for (BasicBlock bb : func.getBbList()) {
            for (IRInstruction ins = bb.getIrStart(); ins != null; ins = ins.getNext()) {
                ins.accept(rewriter);
            }
        }



    }



    public void doFwl(){
        BasicBlock bb = fwl.poll();
//        System.out.println("FWL "+bb);
        exec.add(bb);
        for (IRInstruction ins = bb.getIrStart(); ins!=null; ins=ins.getNext()) {
            if (ins instanceof Phi)
                ins.accept(this);
        }
//        if (!fwl_visited.contains(bb)){
//            fwl_visited.add(bb);
            for (IRInstruction ins = bb.getIrStart(); ins!=null; ins=ins.getNext()) {
//                System.out.println("VISIT EXP "+ins);
                ins.accept(this);
            }
//        }
        bb.getIrEnd().accept(this);
    }


    public Value getValue(Operand op){
        if (op instanceof Immediate) return new Value(Value.Type.C, ((Immediate) op).getValue());
        else if (op instanceof Register) return value.get(op);
        else return Value.nac;
    }



//    public boolean visitPhi(Phi ins){
//
//    }

    private boolean CheckExec(BasicBlock bb){
        if (bb.getFrom().isEmpty()) return true;
        for (IRInstruction from : bb.getFrom()){
            if (exec.contains(from.getBb())) return true;
        }
        return false;
    }

    public void doSwl(){
        IRInstruction ins = swl.poll();
        if (ins instanceof Phi) ins.accept(this);
        if (CheckExec(ins.getBb())) ins.accept(this);
    }

    public void init(BasicBlock bb){
        if (init_visited.contains(bb)) return;
        init_visited.add(bb);
//        System.out.println("FUCK "+bb.func);
        for (IRInstruction ins = bb.getIrStart() ; ins != null ; ins = ins.getNext()){
//            System.out.println(ins);
            for (Register r: ins.ssause()) value.put(r, Value.undef);
            if (ins.ssadef()!=null) value.put(ins.ssadef(), Value.undef);
        }
        for (BasicBlock next : bb.nextBB()){
            init(next);
        }
    }

    private boolean changed = false;

    @Override
    public void visit(IRInstruction inst) {
        throw new RuntimeException();
    }

    @Override
    public void visit(BinaryOp inst) {
        Value lhs = getValue(inst.getLhs());
        Value rhs = getValue(inst.getRhs());
        Value r;
        Value old = getValue(inst.getDest());

//        if (lhs.type == Value.Type.C)
//            inst.setLhs(new Immediate(lhs.value));
//        if (rhs.type == Value.Type.C)
//            inst.setRhs(new Immediate(rhs.value));

//        if (inst.getOp() == BinaryExpr.Op.AND)
//            System.out.printf("%s %s %s\n",inst,lhs.type,rhs.type);

        if (lhs.type == Value.Type.NAC || rhs.type== Value.Type.NAC){
            r = Value.nac;
        }else if (lhs.type == Value.Type.C && rhs.type == Value.Type.C){
            int r_value = 0;
//            System.out.println(inst);
            switch (inst.getOp()){
                case ADD: r_value = lhs.value + rhs.value; break;
                case SUB: r_value = lhs.value - rhs.value; break;
                case MUL: r_value = lhs.value * rhs.value; break;
                case DIV: r_value = rhs.value == 0 ? 0 : lhs.value / rhs.value; break;
                case MOD: r_value = rhs.value == 0 ? 0 : lhs.value % rhs.value; break;
                case SHL: r_value = lhs.value << rhs.value; break;
                case SHR: r_value = lhs.value >> rhs.value; break;
                case OR:  r_value = lhs.value | rhs.value; break;
                case AND: r_value = lhs.value & rhs.value; break;
                case XOR: r_value = lhs.value ^ rhs.value; break;
                default: throw new RuntimeException();
            }
            r = new Value(Value.Type.C, r_value);
//            IRInstruction new_inst = new Move(inst.getDest(), new Immediate(r_value));
//            inst.replace(new_inst);
//            inst.getDest().ssadef = new_inst;
        }else{
            r = Value.undef;
        }
//        changed = r.equals(old);
        if (!r.equals(old)) {
            value.put(inst.getDest(), r);
            swl.addAll(inst.getDest().usedIns);
        }
    }

    private void addFwl(BasicBlock from, BasicBlock to){
        if (!to.visited_from.contains(from)){
            to.visited_from.add(from);
            fwl.add(to);
        }
    }

    @Override
    public void visit(Branch inst) {
        Value cond = getValue(inst.getCond());
//        System.out.println(inst);
//        System.out.println(cond.type);
//        System.out.println(inst);
        if (cond.type == Value.Type.C){
//            inst.setCond(new Immediate(cond.value));
            if (cond.value == 0) addFwl(inst.getBb(), inst.getIfFalse());
            else addFwl(inst.getBb(), inst.getIfTrue());
        }else if (cond.type == Value.Type.NAC){
            addFwl(inst.getBb(), inst.getIfTrue());
            addFwl(inst.getBb(), inst.getIfFalse());
        }else{
            throw new RuntimeException();
        }
    }

    @Override
    public void visit(Call inst) {
        for (int i = 0 ; i < inst.getArguments().size(); i++){
            Value v = getValue(inst.getArguments().get(i));
//            if (v.type == Value.Type.C) inst.getArguments().set(i, new Immediate(v.value));
        }
        Value old = getValue(inst.getDest());
        if (!Value.nac.equals(old)){
            value.put(inst.getDest(), Value.nac);
            swl.addAll(inst.getDest().usedIns);
        }
    }

    @Override
    public void visit(IntCmp inst) {
        Value lhs = getValue(inst.getLhs());
        Value rhs = getValue(inst.getRhs());
        Value r;
        Value old = getValue(inst.getDest());
//        System.out.println(inst);
//        System.out.println(lhs.type +" " + rhs.type);
//        if (lhs.type == Value.Type.C)
//            inst.setLhs(new Immediate(lhs.value));
//        if (rhs.type == Value.Type.C)
//            inst.setRhs(new Immediate(rhs.value));

        if (lhs.type == Value.Type.NAC || rhs.type== Value.Type.NAC){
            r = Value.nac;
        }else if (lhs.type == Value.Type.C && rhs.type == Value.Type.C){


            int r_value = 0;
            switch (inst.getOp()){
                case EQ: r_value = lhs.value == rhs.value ? 1:0; break;
                case NE: r_value = lhs.value != rhs.value ? 1:0; break;
                case LT: r_value = lhs.value <  rhs.value ? 1:0; break;
                case GT: r_value = lhs.value >  rhs.value ? 1:0; break;
                case LE: r_value = lhs.value <= rhs.value ? 1:0; break;
                case GE: r_value = lhs.value >= rhs.value ? 1:0; break;
                default: throw new RuntimeException();
            }
            r = new Value(Value.Type.C, r_value);
//            IRInstruction new_inst = new Move(inst.getDest(), new Immediate(r_value));
//            inst.replace(new_inst);
//            inst.getDest().ssadef = new_inst;
        }else{
            r = Value.undef;
        }
//        changed = r.equals(old);
        if (!r.equals(old)) {
            value.put(inst.getDest(), r);
            swl.addAll(inst.getDest().usedIns);
        }
    }

    @Override
    public void visit(Jump inst) {
        addFwl(inst.getBb(), inst.getDest());
    }

    @Override
    public void visit(Load inst) {
        Value src = getValue(inst.getSrc1());
//        if (src.type == Value.Type.C){
//            inst.setSrc1(new Immediate(src.value));
//        }
        Value old = getValue(inst.getDest());
        if (!Value.nac.equals(old)){
            value.put(inst.getDest(), Value.nac);
            swl.addAll(inst.getDest().usedIns);
        }
    }

    @Override
    public void visit(Malloc inst) {
        throw new RuntimeException();
    }

    @Override
    public void visit(Move inst) {
        Value src = getValue(inst.getSrc());
        Value old = getValue(inst.getDest());
//        if (src.type == Value.Type.C) inst.setSrc(new Immediate(src.value));
        if (!src.equals(old)){
            value.put(inst.getDest(), src);
            swl.addAll(inst.getDest().usedIns);
        }
    }

    @Override
    public void visit(Store inst) {
        Value src = getValue(inst.getSrc());
        Value dest = getValue(inst.getDest1());
//        if (src.type == Value.Type.C) inst.setSrc(new Immediate(src.value));
//        if (dest.type == Value.Type.C) inst.setDest1(new Immediate(dest.value));
    }

    @Override
    public void visit(UnaryOp inst) {
        Value op = getValue(inst.getOperand());
        Value old = getValue(inst.getDest());
        Value r;
        if (op.type == Value.Type.NAC){
            r = Value.nac;
        }else if (op.type == Value.Type.C){
            int r_value = 0;
            switch (inst.getOp()){
                case LINC: case RINC:
                    r_value = op.value + 1;
                    break;
                case LDEC: case RDEC:
                    r_value = op.value - 1;
                    break;
                case POS:
                    r_value = op.value;
                    break;
                case NOT:
                    r_value = ~op.value;
                    break;
                case NEG:
                    r_value = -op.value;
                    break;
                default: throw new RuntimeException();
            }
            r = new Value(Value.Type.C, r_value);
//            IRInstruction new_inst = new Move(inst.getDest(), new Immediate(r_value));
//            inst.getDest().ssadef = new_inst;
//            inst.replace(new_inst);
        }else {
            r = Value.undef;
        }
        if (!r.equals(old)){
            value.put(inst.getDest(), r);
            swl.addAll(inst.getDest().usedIns);
        }
    }

    @Override
    public void visit(Return inst) {
        Value v = getValue(inst.getValue());
//        if (v.type == Value.Type.C)
//            inst.setValue(new Immediate(v.value));
    }

    @Override
    public void visit(Pop inst) {
        throw new RuntimeException();
    }

    @Override
    public void visit(Push inst) {
        throw new RuntimeException();
    }

    public Value getPhiValue(Phi ins, int i){
        if (exec.contains(ins.getBb().getFrom().get(i).getBb())) return getValue(ins.getArg(i));
        else return Value.undef;
    }

    @Override
    public void visit(Phi inst) {
        Value r = Value.undef;
        Value va;
        for (int i = 0 ; i < inst.getSlotNum() ; ++i){
            va = getPhiValue(inst, i);
//            System.out.printf("PHI %d %s %s\n",i,inst.getBb().getFrom(),va.type);
            r = r.lower(va);
//            if (va.type == Value.Type.C)
//                inst.setSlot(i, new Immediate(va.value));
        }
        Value old = getValue(inst.getDest());
        if (!r.equals(old)) {
            value.put(inst.getDest(), r);
            swl.addAll(inst.getDest().usedIns);
        }
    }

    @Override
    public void visit(Lea inst) {
        Value r;
        Value base  = getValue(inst.getBase());
        Value index = getValue(inst.getIndex());
//
//        if (base.type == Value.Type.C) inst.setBase(new Immediate(base.value));
//        if (index.type == Value.Type.C) inst.setIndex(new Immediate(index.value));

        Value old = getValue(inst.getDest());
        if (base.type == Value.Type.NAC || index.type == Value.Type.NAC){
            r = Value.nac;
        }else if (base.type == Value.Type.C && index.type == Value.Type.C){
            r = new Value(Value.Type.C, base.value + index.value * inst.getSize() + inst.getOffset());
        }else{
            r = Value.undef;
        }

        if (!r.equals(old)){
            value.put(inst.getDest(), r);
            swl.addAll(inst.getDest().usedIns);
        }
    }
}
