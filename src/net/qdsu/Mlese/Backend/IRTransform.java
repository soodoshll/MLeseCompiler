package net.qdsu.Mlese.Backend;

import net.qdsu.Mlese.AST.BinaryExpr;
import net.qdsu.Mlese.IR.BasicBlock;
import net.qdsu.Mlese.IR.Function;
import net.qdsu.Mlese.IR.IRRoot;
import net.qdsu.Mlese.IR.Instruction.*;
import net.qdsu.Mlese.IR.Operand.*;
import org.stringtemplate.v4.compiler.Bytecode;

import java.util.HashSet;


import static net.qdsu.Mlese.AST.UnaryExpr.Operand.*;

/*
* Transform IR into X86 (NASM) format
* */
public class IRTransform {
    private IRRoot root;
    private Function curFunc;
    private IRInstruction curIns;
    private HashSet<BasicBlock> visited = new HashSet<>();
    public IRTransform(IRRoot root) {
        this.root = root;
    }


    private static HashSet<BasicBlock> comb_v ;
    private void _BBCombine(BasicBlock bb){
        if (bb == null) return;
        if (comb_v.contains(bb)) return;
        //bb.func.addBB(bb);
        comb_v.add(bb);
        IRInstruction last = bb.getIrEnd();

        if (last instanceof Jump) {
            BasicBlock nextbb = ((Jump) last).getDest();


            if (nextbb.getIrStart() == nextbb.getIrEnd() && nextbb.getIrEnd() instanceof Jump){
                //System.out.println();
                BasicBlock new_dest = ((Jump)nextbb.getIrStart()).getDest();
                new_dest.addFrom(last);
                ((Jump) last).setDest(new_dest);
                _BBCombine(new_dest);
            }
            else
                if (nextbb.getInDeg() == 1) {
                //Combine
                last.remove();
                for (IRInstruction ins = nextbb.getIrStart(); ins != null ; ins = ins.getNext())
                    ins.setBb(bb);
                if (bb.empty()) {
                    bb.setIrStart(nextbb.getIrStart());
                    bb.setIrEnd(nextbb.getIrEnd());
                } else {
                    System.err.println(nextbb);
                    bb.getIrEnd().setNext(nextbb.getIrStart());
                    nextbb.getIrStart().setPrev(bb.getIrEnd());
                    bb.setIrEnd(nextbb.getIrEnd());
                }
                comb_v.remove(bb);
                _BBCombine(bb);
            }
            else {
                _BBCombine(nextbb);
            }
        }else if (last instanceof Branch) {

            if (((Branch) last).getCond() instanceof Immediate){
//                System.out.println(last);
                int v = ((Immediate) ((Branch) last).getCond()).getValue();
                Jump new_ins;
                if (v == 0){
                    new_ins = new Jump(((Branch) last).getIfFalse());
                }else{
                    new_ins = new Jump(((Branch) last).getIfTrue());
                }
                last.replace(new_ins);
//                new_ins.getDest().getFrom().remove(last);
                new_ins.getDest().addFrom(new_ins);
                comb_v.remove(bb);
                _BBCombine(bb);
                return;
            }

            BasicBlock if_true = ((Branch) last).getIfTrue();
            if (if_true.getIrStart() == if_true.getIrEnd() && if_true.getIrEnd() instanceof Jump){
                BasicBlock new_dest = ((Jump) if_true.getIrEnd()).getDest();
                ((Branch) last).setIfTrue(new_dest);
                new_dest.addFrom(last);
            }

            BasicBlock if_false = ((Branch) last).getIfFalse();
            if (if_false.getIrStart() == if_false.getIrEnd() && if_false.getIrEnd() instanceof Jump){
                BasicBlock new_dest = ((Jump) if_false.getIrEnd()).getDest();
                ((Branch) last).setIfFalse(new_dest);
                new_dest.addFrom(last);
            }

            _BBCombine(((Branch) last).getIfTrue());
            _BBCombine(((Branch) last).getIfFalse());
        }
    }

    public void BBCombine(){
        comb_v = new HashSet<>();
        for (Function func : root.getFunctions()) {
        //    func.getBbList().clear();
            _BBCombine(func.getBBStart());
        }
        cleanBBList();
    }

    private void _cleanBBList(BasicBlock bb) {
        if (bb.func.getBbList().contains(bb)) return;
        bb.func.getBbList().add(bb);
        IRInstruction last = bb.getIrEnd();
        if (last instanceof Jump) {
            BasicBlock dest = ((Jump) last).getDest();
            dest.addFrom(last);
            _cleanBBList(dest);
        }
        if (last instanceof Branch) {
            BasicBlock if_true = ((Branch) last).getIfTrue();
            BasicBlock if_false = ((Branch) last).getIfFalse();

            if_true.addFrom(last);
            if_false.addFrom(last);


            _cleanBBList(if_true);
            _cleanBBList(if_false);

        }
    }

    public void cleanBBList(){
        for (Function func : root.getFunctions()) {
            func.getBbList().forEach(x->x.getFrom().clear());
            //func.getBbList().forEach(x->x.inDeg = 0);
            func.getBbList().clear();
            //System.out.println(func.getBbList());
            _cleanBBList(func.getBBStart());
        }
    }

    public void toMachineIns(){

        for (Function fun : root.getFunctions()){
            funcLoadArgsX64(fun);
            for (BasicBlock bb : fun.getBbList()){
                for (IRInstruction ins = bb.getIrStart() ; ins != null ; ins = ins.getNext()){
                    if (ins instanceof IntCmp){
                        checkCmp((IntCmp)ins);
                    }
                    if (ins instanceof Branch)
                        combineBranchX64((Branch)ins);
                    if (ins instanceof BinaryOp) {
                        ArithmX64((BinaryOp) ins);
                    }
                    if (ins instanceof Call) {
                        passArgsX64((Call)ins);
                    }
                    if (ins instanceof Return) {
                        returnX64((Return)ins);
                    }
                    if (ins instanceof UnaryOp){
                        UnaryX64((UnaryOp)ins);
                    }
                }
            }

            fun.getBbList().clear();
            _cleanBBList(fun.getBBStart());
        }
    }

    private void checkCmp(IntCmp ins){
        if (ins.getLhs() instanceof Immediate && ins.getRhs() instanceof Immediate){
            int lhs = ((Immediate) ins.getLhs()).getValue();
            int rhs = ((Immediate) ins.getRhs()).getValue();
            boolean v = false;
            switch (ins.getOp()){
                case EQ:
                    v = lhs == rhs;
                    break;
                case NE:
                    v = lhs != rhs;
                    break;
                case GT:
                    v = lhs > rhs;
                    break;
                case LT:
                    v = lhs < rhs;
                    break;
                case GE:
                    v = lhs >= rhs;
                    break;
                case LE:
                    v = lhs <= rhs;
                    break;
            }
            IRInstruction new_ins = new Move(ins.getDest(), new Immediate(v ? 1: 0));
            ((VirtualRegister)ins.getDest()).ssadef = new_ins;
            ins.replace(new_ins);

        }else if (ins.getLhs() instanceof Immediate){
            IRInstruction new_ins = ins;
            switch (ins.getOp()){
                case EQ:
                    new_ins = new IntCmp(ins.getDest(), BinaryExpr.Op.EQ, ins.getRhs(), ins.getLhs());
                    break;
                case NE:
                    new_ins = new IntCmp(ins.getDest(), BinaryExpr.Op.NE, ins.getRhs(), ins.getLhs());
                    break;
                case GT:
                    new_ins = new IntCmp(ins.getDest(), BinaryExpr.Op.LT, ins.getRhs(), ins.getLhs());
                    break;
                case LT:
                    new_ins = new IntCmp(ins.getDest(), BinaryExpr.Op.GT, ins.getRhs(), ins.getLhs());
                    break;
                case GE:
                    new_ins = new IntCmp(ins.getDest(), BinaryExpr.Op.LE, ins.getRhs(), ins.getLhs());
                    break;
                case LE:
                    new_ins = new IntCmp(ins.getDest(), BinaryExpr.Op.GE, ins.getRhs(), ins.getLhs());
                    break;
            }
            ((VirtualRegister)ins.getDest()).ssadef = new_ins;
            ((VirtualRegister)ins.getRhs()).usedIns.remove(ins);
            ((VirtualRegister)ins.getRhs()).usedIns.add(new_ins);
            ins.replace(new_ins);
        }
        //System.out.println(ins);
    }

    public void ArithmX64(BinaryOp inst) {
        //System.out.println(inst);
        IRInstruction next_ins = inst.getNext();

        switch (inst.getOp()){
            case ADD: case SUB:
                if (inst.getLhs() instanceof Immediate && inst.getRhs() instanceof Immediate) { // dest = imm + imm
                    int lhs = ((Immediate) inst.getLhs()).getValue();
                    int rhs = ((Immediate) inst.getRhs()).getValue();
                    int result = inst.getOp() == BinaryExpr.Op.ADD ? lhs + rhs : lhs - rhs;
                    inst.replace(new Move(inst.getDest(), new Immediate(result)));
                }else if (inst.getLhs() == inst.getDest()) { // dest = dest + reg/imm
                    //do Nothing
                }else if (inst.getRhs() == inst.getDest()) {
                    if (inst.getOp() == BinaryExpr.Op.ADD)
                        inst.swapOperands();
                    else{  // a = b - a
                        VirtualRegister tmp = new VirtualRegister();
                        inst.insertBefore(new Move(tmp, inst.getRhs()));
                        inst.insertBefore(new Move(inst.getDest(), inst.getLhs()));
                        inst.setRhs(tmp);
                        inst.setLhs(inst.getDest());
                    }
                }else if (inst.getOp() == BinaryExpr.Op.ADD && inst.getLhs() instanceof Register && inst.getRhs() instanceof Register) {
                    inst.replace(new Lea(inst.getDest(), inst.getLhs(), 0, inst.getRhs(), 1));
                }
                else{
                    if (inst.getRhs() instanceof Immediate){
                        inst.replace(new Lea(inst.getDest(), inst.getLhs(),
                                (inst.getOp() == BinaryExpr.Op.SUB ? -1 : 1) * ((Immediate) inst.getRhs()).getValue(),
                                new Immediate(0), 0));
                    }else {
                        inst.insertBefore(new Move(inst.getDest(), inst.getLhs()));
                        inst.setLhs(inst.getDest());
                    }
                }
                break;
            case MUL:
                if (inst.getLhs() instanceof Immediate && inst.getRhs() instanceof Immediate)
                { // dest = imm * imm
                    int lhs = ((Immediate) inst.getLhs()).getValue();
                    int rhs = ((Immediate) inst.getRhs()).getValue();
                    inst.replace(new Move(inst.getDest(), new Immediate(lhs * rhs)));
                }else if (inst.getRhs() instanceof Immediate){ // dest = reg * imm
                    //Do nothing
                }else if (inst.getLhs() instanceof Immediate){ // dest = imm * reg
                    inst.swapOperands();
                }else if (inst.getLhs() == inst.getDest()) { // dest = dest * reg
                    //Do nothing
                }else if (inst.getRhs() == inst.getDest()) { // dest = reg * dest
                    inst.swapOperands();
                }else { //dest = reg * reg
                    inst.insertBefore(new Move(inst.getDest(), inst.getLhs()));
                    inst.setLhs(inst.getDest());
                }
                break;
            case DIV: case MOD:
                if (inst.getLhs() instanceof Immediate && inst.getRhs() instanceof Immediate)
                { // dest = imm / imm
                    int lhs = ((Immediate) inst.getLhs()).getValue();
                    int rhs = ((Immediate) inst.getRhs()).getValue();
                    int result = (rhs != 0) ? (inst.getOp() == BinaryExpr.Op.DIV) ? lhs / rhs : lhs % rhs : 0;
                    inst.replace(new Move(inst.getDest(), new Immediate(result)));
                }
//                else if (inst.getRhs() instanceof Immediate) {// dest = reg / imm
//                    //Dest doesn't matter
//                    inst.insertBefore();
//                    inst.setLhs(PhysicalRegister.rax);
//                    inst.insertBefore(new Move(PhysicalRegister.rdx, new Immediate(0)));
//                    if (inst.getOp() == BinaryExpr.Op.DIV) {
//                        inst.insertAfter(new Move(inst.getDest(), PhysicalRegister.rax));
//                        inst.setDest(PhysicalRegister.rax);
//                    } else {
//                        inst.insertAfter(new Move(inst.getDest(), PhysicalRegister.rdx));
//                        inst.setDest(PhysicalRegister.rdx);
//                    }
//                }
                else { // dest = imm / reg ; dest = reg /reg ; dest = reg / imm
                    if (inst.getRhs() instanceof Immediate){
                        VirtualRegister tmp = new VirtualRegister();
                        inst.insertBefore(new Move(tmp, inst.getRhs()));
                        inst.setRhs(tmp);
                    }
//                    inst.insertBefore(new Move(PhysicalRegister.rdx, new Immediate(0)));
                    inst.insertBefore(new Move(PhysicalRegister.rax, inst.getLhs()));
                    inst.setLhs(PhysicalRegister.rax);
                    if (inst.getOp() == BinaryExpr.Op.DIV) {
                        inst.insertAfter(new Move(inst.getDest(), PhysicalRegister.rax));
                        inst.setDest(PhysicalRegister.rax);
                    } else {
                        inst.insertAfter(new Move(inst.getDest(), PhysicalRegister.rdx));
                        inst.setDest(PhysicalRegister.rdx);
                    }
                }
                break;
            case SHL:case SHR:
                if (inst.getLhs() instanceof Immediate && inst.getRhs() instanceof Immediate){
                    int lhs = ((Immediate) inst.getLhs()).getValue();
                    int rhs = ((Immediate) inst.getRhs()).getValue();
                    int v = (inst.getOp() == BinaryExpr.Op.SHL)? lhs << rhs : lhs >> rhs;
                    inst.replace(new Move(inst.getDest(), new Immediate(v)));
                }else {
                    if (inst.getLhs() instanceof Immediate){
                        //VirtualRegister tmp = new VirtualRegister();
                        //inst.insertBefore(new Move(tmp, inst.getLhs()));
                        //inst.setLhs(tmp);
                        inst.insertBefore(new Move(inst.getDest(), inst.getLhs()));
                        inst.setLhs(inst.getDest());
                    }
                    if (inst.getDest() != inst.getLhs() ){
                        inst.insertBefore(new Move(inst.getDest(), inst.getLhs()));
                        inst.setLhs(inst.getDest());
                    }
                    if (!(inst.getRhs() instanceof Immediate) ||
                            (inst.getRhs() instanceof Immediate )){
                        inst.insertBefore(new Move(PhysicalRegister.rcx, inst.getRhs()));
                        inst.setRhs(PhysicalRegister.rcx);
                    }
                }
                break;
            case XOR:case OR:case AND:
                if (inst.getLhs() instanceof Immediate && inst.getRhs() instanceof Immediate){
                    int lhs = ((Immediate) inst.getLhs()).getValue();
                    int rhs = ((Immediate) inst.getRhs()).getValue();
                    int v = (inst.getOp() == BinaryExpr.Op.AND ) ? lhs & rhs :
                            (inst.getOp() == BinaryExpr.Op.OR) ? lhs | rhs :
                                    lhs ^ rhs;
                    inst.replace(new Move(inst.getDest(), new Immediate(v)));
                }else {
                    if (inst.getLhs() instanceof Immediate){
                        // dest = imm * reg
                        Operand tmp = inst.getLhs();
                        inst.setLhs(inst.getRhs());
                        inst.setRhs(tmp);
                    }
                    //dest = reg * imm/reg
                    if (inst.getDest() != inst.getLhs()){
                        //VirtualRegister tmp = new VirtualRegister();
                        inst.insertBefore(new Move(inst.getDest(),inst.getLhs()));
                        inst.setLhs(inst.getDest());
                    }
                }
                break;
            default:
                throw new RuntimeException("unreachable");
        }
    }
    public void UnaryX64(UnaryOp inst){
        switch(inst.getOp()){
            case NOT: case NEG:
                if (inst.getOperand() instanceof Immediate){
                    int o = ((Immediate) inst.getOperand()).getValue();
                    int v = inst.getOp() == NOT ? ~o : -o;
                    inst.replace(new Move(inst.getDest(), new Immediate(v)));
                }else if (inst.getDest() != inst.getOperand()){
                    //VirtualRegister tmp = new VirtualRegister();
                    //inst.insertAfter(new Move(inst.getDest(), tmp));
                    inst.insertBefore(new Move(inst.getDest(), inst.getOperand()));
//                    inst.setDest((Register)inst.getOperand());
                    inst.setOperand(inst.getDest());
                }
                break;
            case LINC: case RINC:
            case LDEC: case RDEC:
                if (inst.getOperand() instanceof Immediate){
                    int o = ((Immediate) inst.getOperand()).getValue();
                    int v = inst.getOp() == LINC || inst.getOp() == RINC ? o+1 : o-1;
                    inst.replace(new Move(inst.getDest(), new Immediate(v)));
                }else if (inst.getDest() != inst.getOperand()){
                    //VirtualRegister tmp = new VirtualRegister();
                    inst.insertBefore(new Move(inst.getDest(), inst.getOperand()));
                    //inst.insertAfter(new Move(inst.getDest(), tmp));
                    //inst.setDest(tmp);
                    inst.setOperand(inst.getDest());
                }
                break;
        }
    }
    public void combineBranchX64(Branch inst) {

        if (!(inst.getCond() instanceof VirtualRegister)) return;
        VirtualRegister cond = (VirtualRegister)inst.getCond();
        //System.out.println("combineBranch triggered "+cond+ " "+cond.ssadef);
        if (!(cond.ssadef instanceof IntCmp)) return;
        if (cond.usedIns.size() > 1) return;

        IntCmp cmp = (IntCmp)cond.ssadef;
        inst.setCmp(cmp);
        cmp.remove();
    }
    public void funcLoadArgsX64(Function fun) {
        curFunc = fun;
        IRInstruction first = fun.getBBStart().getIrStart();
        int i;
        for (i = 0 ; i < fun.getParameters().size() && i < PhysicalRegister.callingRegsNum; ++i)
            first.insertBefore(new Move(fun.getParameters().get(i), PhysicalRegister.callingReg(i)));

        for (i = PhysicalRegister.callingRegsNum ; i < fun.getParameters().size(); ++i){
            //System.out.println("FUCK");
            VirtualRegister tmp = new VirtualRegister();
            first.insertBefore(new Lea(tmp, PhysicalRegister.rbp,
                    (i+2-PhysicalRegister.callingRegsNum)*8,
                    new Immediate(0),
                    0
            ));
            first.insertBefore(new Load(fun.getParameters().get(i), tmp));
        }
        //first.accept(this);
        //doBB(fun.getBBStart());
    }
    public void returnX64(Return inst){
        if (inst.getValue() != null) {
            inst.insertBefore(new Move(PhysicalRegister.rax, inst.getValue()));
            inst.setValue(null);
        }
    }
    public void passArgsX64(Call inst) {
        int i;
        for (i = 0 ;
            i < PhysicalRegister.callingRegsNum &&
            i < inst.getArguments().size();
            ++i){
            inst.insertBefore(new Move(PhysicalRegister.callingReg(i),
                    inst.getArguments().get(i)));
            }
        int argc = inst.getArguments().size();

        int rest = inst.getArguments().size() - PhysicalRegister.callingRegsNum;

        for (i = inst.getArguments().size() - 1;
            i >= PhysicalRegister.callingRegsNum;
            --i){
//            System.out.println("FUCK");
            inst.insertBefore(new Push(inst.getArguments().get(i)));
        }

        inst.getArguments().clear();
        for (i = 0 ;
             i < PhysicalRegister.callingRegsNum &&
                     i < argc;
             ++i){
            inst.addArgument(PhysicalRegister.callingReg(i));
        }
        //System.out.println(inst);
        //System.out.println(((VirtualRegister)inst.getDest()).usedIns);
        if (!((VirtualRegister)inst.getDest()).usedIns.isEmpty())
            inst.insertAfter(new Move(inst.getDest(), PhysicalRegister.rax));
        inst.setDest(PhysicalRegister.rax);
        if (rest >0)
            inst.insertAfter(new BinaryOp(BinaryExpr.Op.ADD, PhysicalRegister.rsp, PhysicalRegister.rsp, new Immediate(rest * 8)));
    }

    public void proAndEpi(){
        root.getFunctions().forEach(x->proAndEpiFun(x));
    }

    public void proAndEpiFun(Function fun){
        //prolog
        IRInstruction head = fun.getBBStart().getIrStart();
        /*  push rbp
            mov  rbp, rsp
            sub  rsp, [stack]
            ...
            mov  rsp, rbp
            pop  rbp
        */
        head.insertBefore(new Push(PhysicalRegister.rbp));
        head.insertBefore(new Move(PhysicalRegister.rbp, PhysicalRegister.rsp));
        head.insertBefore(new BinaryOp(BinaryExpr.Op.ADD, PhysicalRegister.rsp, PhysicalRegister.rsp, new Immediate(
                (fun.stack_ptr / 16 - 1) * 16 - 8
        )));
//        if (fun == root.getMainFun()) return;
        //System.out.printf("%s %d\n", fun , fun.stack_ptr);
        /*push all callee-saved */
        for (int i = PhysicalRegister.callee_saved.size() -1  ; i >= 0 ; --i){
            head.insertBefore(new Push(PhysicalRegister.callee_saved.get(i)));
        }

        for (BasicBlock bb : fun.getBbList())
            if (bb.getIrEnd() instanceof Return){
                IRInstruction end = bb.getIrEnd();
                /*pop all callee-saved*/
                for (PhysicalRegister pr: PhysicalRegister.callee_saved){
                    end.insertBefore(new Pop(pr));
                }
//                end.insertBefore(new Move(PhysicalRegister.rsp, PhysicalRegister.rbp));
//                end.insertBefore(new Pop(PhysicalRegister.rbp));
            }

    }

//    public void calUsedReg(){
//        boolean changed = true;
//        while (changed){
//            changed = false;
//            for (Function fun : root.getFunctions()){
//                HashSet<Register> old = new HashSet<>(fun.used_regs);
//                for (BasicBlock bb : fun.getBbList())
//                    for (IRInstruction ins = bb.getIrStart(); ins != null; ins = ins.getNext()){
//                        ins.updateUseAndDef();
//                        if (ins instanceof Call){
//
//                        }else{
//                            fun.used_regs.addAll(ins.use);
//                            fun.used_regs.addAll(ins.def);
//                        }
//                    }
//                if (!old.equals(fun.used_regs)) changed = true;
//            }
//        }
//    }

}
