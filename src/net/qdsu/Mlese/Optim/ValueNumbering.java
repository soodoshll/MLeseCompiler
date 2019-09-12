package net.qdsu.Mlese.Optim;

import net.qdsu.Mlese.IR.BasicBlock;
import net.qdsu.Mlese.IR.Function;
import net.qdsu.Mlese.IR.IRRoot;
import net.qdsu.Mlese.IR.Instruction.*;
import net.qdsu.Mlese.IR.Operand.Operand;
import net.qdsu.Mlese.IR.Operand.Register;

import java.util.Hashtable;

//Global value numbering (pessimistic)
public class ValueNumbering {
    private Hashtable<Operand, String> value;
    private IRRoot root;

    public static class Bimap{
        private Hashtable<String, Operand> str2value = new Hashtable<>();
        private Hashtable<Operand, String> value2str = new Hashtable<>();

        public void set(Operand op, String str){
//            System.out.println(str.length());
            str2value.put(str, op);
            value2str.put(op, str);
        }

        public Operand getOp(String str){
            return str2value.get(str);
        }

        public String getStr(Operand op){
            return value2str.get(op);
        }
    }

    public static class Scope{
        private BasicBlock bb;
        private Bimap bimap = new Bimap();

        public Scope(BasicBlock bb) {
            this.bb = bb;
        }

        public void set(Operand op, String str){
            bimap.set(op, str);
        }

        public Operand getOp(String str){
            Operand r = bimap.getOp(str);
            if (r == null) {
                if (bb.IDom == null || bb.IDom.vn_scope == null) return null;
                else return bb.IDom.vn_scope.getOp(str);
            }else return r;
        }

        public String getStr(Operand op){
            String r = bimap.getStr(op);
            if (r == null) {
                if (bb.IDom == null || bb.IDom.vn_scope == null) return null;
                else return bb.IDom.vn_scope.getStr(op);
            }else return r;
        }

    }

    public static class VNVisitor implements IRVisitor{
        public Scope scope;
        public boolean changed;
        public VNVisitor() {
        }


        public String getNum(Operand x){
            String r = scope.getStr(x);
            if (r == null){
                scope.set(x, x.toString());
                return x.toString();
            }else{
                return r;
            }
        }

        @Override
        public void visit(IRInstruction inst) {
            throw new RuntimeException();
        }

        public void removeUse(Operand o, IRInstruction ins){
            if (o instanceof Register) ((Register) o).usedIns.remove(ins);
        }

        public void addUse(Operand o, IRInstruction ins){
            if (o instanceof Register) ((Register) o).usedIns.add(ins);
        }

        public int threshold = 1000;

        @Override
        public void visit(BinaryOp inst) {
            changed = false;

            String lhs = getNum(inst.getLhs());
            String rhs = getNum(inst.getRhs());

            String exp_s = String.format("(%s %s %s)", inst.getOp(), lhs, rhs);
            Operand v = inst.getBb().vn_scope.getOp(exp_s);
            if (exp_s.length() > threshold) exp_s = inst.getDest().toString();


            if (v == null){
                inst.getBb().vn_scope.set(inst.getDest(), exp_s);
            }else{
//                System.out.println("FOUND "+exp_s+" "+v);
                changed = true;

                removeUse(inst.getLhs(), inst);
                removeUse(inst.getRhs(), inst);

                Move new_ins = new Move(inst.getDest(), v);
                addUse(v, new_ins);

                inst.getDest().ssadef = new_ins;
                inst.replace(new_ins);
            }
        }

        @Override
        public void visit(Branch inst) {
            changed = false;

        }

        @Override
        public void visit(Call inst) {
            changed = false;

        }

        @Override
        public void visit(IntCmp inst) {
            changed = false;

            String lhs = getNum(inst.getLhs());
            String rhs = getNum(inst.getRhs());

            String exp_s = String.format("(%s %s %s)", inst.getOp(), lhs, rhs);
            Operand v = inst.getBb().vn_scope.getOp(exp_s);
            if (exp_s.length() > threshold) exp_s = inst.getDest().toString();


            if (v == null){
                inst.getBb().vn_scope.set(inst.getDest(), exp_s);
            }else{
                changed = true;

//                System.out.println("FOUND "+exp_s+" "+v);

                removeUse(inst.getLhs(), inst);
                removeUse(inst.getRhs(), inst);

                Move new_ins = new Move(inst.getDest(), v);
                addUse(v, new_ins);

                inst.getDest().ssadef = new_ins;
                inst.replace(new_ins);
            }
        }

        @Override
        public void visit(Jump inst) {
            changed = false;

        }

        @Override
        public void visit(Load inst) {
            changed = false;

        }

        @Override
        public void visit(Malloc inst) {
            changed = false;

        }

        @Override
        public void visit(Move inst) {
            changed = false;
        }

        @Override
        public void visit(Store inst) {
            changed = false;

        }

        @Override
        public void visit(UnaryOp inst) {
            changed = false;

            String op = getNum(inst.getOperand());

            String exp_s = String.format("(%s %s)", inst.getOp(), op);
            Operand v = inst.getBb().vn_scope.getOp(exp_s);
            if (exp_s.length() > threshold) exp_s = inst.getDest().toString();


            if (v == null){
                inst.getBb().vn_scope.set(inst.getDest(), exp_s);
            }else{
                changed = true;


                removeUse(inst.getOperand(), inst);

                Move new_ins = new Move(inst.getDest(), v);
                addUse(v, new_ins);

                inst.getDest().ssadef = new_ins;
                inst.replace(new_ins);
            }
        }

        @Override
        public void visit(Return inst) {
            changed = false;

        }

        @Override
        public void visit(Pop inst) {
            changed = false;

        }

        @Override
        public void visit(Push inst) {
            changed = false;

        }

        @Override
        public void visit(Phi inst) {
            changed = false;

        }

        @Override
        public void visit(Lea inst) {
            changed = false;

            String index = getNum(inst.getIndex());
            String base = getNum(inst.getBase());

            String exp_s = String.format("(LEA %s %s %d %d)", base, index, inst.getOffset(), inst.getSize());
            Operand v = inst.getBb().vn_scope.getOp(exp_s);
            if (exp_s.length() > threshold) exp_s = inst.getDest().toString();


            if (v == null){
                inst.getBb().vn_scope.set(inst.getDest(), exp_s);
            }else{
                changed = true;

                removeUse(inst.getBase(), inst);
                removeUse(inst.getIndex(), inst);

                Move new_ins = new Move(inst.getDest(), v);
                addUse(v, new_ins);

                inst.getDest().ssadef = new_ins;
                inst.replace(new_ins);
            }
        }
    }

    public ValueNumbering(IRRoot root) {
        this.root = root;
    }

    public void run(){
        root.getFunctions().forEach(x->runFunc(x));
        NaiveDCE dce = new NaiveDCE(root);
        dce.run();
    }

    public void runFunc(Function func){
        VNVisitor visitor = new VNVisitor();
        func.calRPO();
        boolean changed = true;
        while (changed) {
            changed = false;
            for (BasicBlock bb : func.rpo) {
                bb.vn_scope = new Scope(bb);
                visitor.scope = bb.vn_scope;
                for (IRInstruction ins = bb.getIrStart(); ins != null; ins = ins.getNext()) {
                    ins.accept(visitor);
                    changed = changed || visitor.changed;
                }
            }
        CopyElimination ce = new CopyElimination(root);
        ce.runFunc(func);

        }

    }


}
