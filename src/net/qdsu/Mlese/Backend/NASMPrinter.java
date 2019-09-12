package net.qdsu.Mlese.Backend;

import net.qdsu.Mlese.AST.BinaryExpr;
import net.qdsu.Mlese.IR.BasicBlock;
import net.qdsu.Mlese.IR.BuiltinFunction;
import net.qdsu.Mlese.IR.Function;
import net.qdsu.Mlese.IR.IRRoot;
import net.qdsu.Mlese.IR.Instruction.*;
import net.qdsu.Mlese.IR.Operand.*;

import java.io.*;
import java.util.ArrayList;

import static net.qdsu.Mlese.AST.BinaryExpr.Op.DIV;

public class NASMPrinter implements IRVisitor {
    private PrintStream out;
    private IRRoot root;

    private String [] externFunctions = {"puts", "printf", "gets", "strlen", "malloc","atoi","_GLOBAL_OFFSET_TABLE_"};

    public NASMPrinter(IRRoot root, PrintStream out) {
        this.out = out;
        this.root = root;
    }

    private ArrayList<BasicBlock> Bblist;
    private int bblist_ptr;

    private void _placeBlock(BasicBlock bb){
        if (Bblist.contains(bb)) return;
        Bblist.add(bb);
        if (bb.getIrEnd() instanceof Branch){
            _placeBlock(((Branch) bb.getIrEnd()).getIfTrue());
            _placeBlock(((Branch) bb.getIrEnd()).getIfFalse());
        }else if (bb.getIrEnd() instanceof Jump){
            _placeBlock(((Jump) bb.getIrEnd()).getDest());
        }
    }

    public void placeBlock(Function func){
        Bblist = new ArrayList<>();
        _placeBlock(func.getBBStart());
    }

    public void run(){

        out.println("default rel\n");
        initMain();
        initExtern();
        out.println("\nSECTION .text\n");
        for (Function fun: root.getFunctions()){
            placeBlock(fun);
            for (int i = 0 ; i < Bblist.size(); ++i){
                bblist_ptr = i;
                doBB(Bblist.get(i));
            }
        }
        initGlobal();
        initStrings();
        includeBuiltinFun();

    }

    private void initGlobal(){
        out.printf("SECTION .bss\t align=8\n\n");
        for (GlobalVar g : root.getGlobal_var()){
            out.printf("%s:\n\tresq 1\n\n",g);
        }
    }

    private void initStrings(){
        out.printf("SECTION .rodata\talign=8\n\n");
        for (StaticString ss: root.strings){
            char [] chararray = ss.getValue().toCharArray();
            out.printf("%s:\n", ss);
            out.printf("\tdb");
            for (int i = 0 ; i < chararray.length ; ++i)
                out.printf(" %02XH,",(int)chararray[i]);
            out.printf(" 00H\n\n");
        }
    }

    private void includeBuiltinFun(){
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("lib/builtin_functions.asm"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                out.println(line);
            }
        } catch (IOException e) {
            throw new RuntimeException("IO exception when reading builtin functions from file");
        }
    }

    private void initExtern(){
        out.println();
        for (String fun : externFunctions){
            out.printf("extern %s\n",fun);
        }
    }

    private void doBB(BasicBlock bb){
        out.printf("%s:     ;%s\n", bb, bb.hint);
        for (IRInstruction ins = bb.getIrStart(); ins != null; ins=ins.getNext()){
            ins.accept(this);
            out.printf("\t;%s\n",ins);
        }
        out.printf("\n");
    }

    public void initMain(){
        //for (Function fun : root.getFunctions()){
        //    for (BasicBlock bb : fun.getBbList()){
        //        if (bb == root.getMainFun().getBBStart())
        out.printf("global main\n");
        //        else
        //            out.printf("global %s\n", bb);
        //    }
        //}
    }

    @Override
    public void visit(IRInstruction inst) {
        throw new RuntimeException("unreachable");
    }

    @Override
    public void visit(BinaryOp inst) {
        switch (inst.getOp()){
            case ADD:
                assert(inst.getDest() == inst.getLhs());
                assert(inst.getLhs() instanceof PhysicalRegister);
                assert(inst.getRhs() instanceof Immediate || inst.getRhs() instanceof PhysicalRegister);
//                if (inst.getRhs() instanceof )
                out.printf("\tadd  %s, %s", inst.getDest(), inst.getRhs());
                break;
            case SUB:
                assert(inst.getDest() == inst.getLhs());
                assert(inst.getLhs() instanceof PhysicalRegister);
                assert(inst.getRhs() instanceof Immediate || inst.getRhs() instanceof PhysicalRegister);
                out.printf("\tsub  %s, %s", inst.getDest(), inst.getRhs());
                break;
            case MUL:
                assert(inst.getDest() instanceof PhysicalRegister);
                if (inst.getRhs() instanceof Immediate){
                    assert(inst.getLhs() instanceof PhysicalRegister);
                    if (inst.getDest() == inst.getLhs())
                        out.printf("\timul %s, %s", inst.getDest(), inst.getRhs());
                    else
                        out.printf("\timul %s, %s, %s", inst.getDest(), inst.getLhs(), inst.getRhs());
                }else{
                    assert(inst.getLhs() == inst.getDest());
                    out.printf("\timul %s, %s", inst.getDest(), inst.getRhs());
                }
                break;
            case DIV: case MOD:
//                assert(inst.getDest() == PhysicalRegister.rax);
//                assert(inst.getLhs() == PhysicalRegister.rax);
                assert(inst.getRhs() instanceof PhysicalRegister);
                out.printf("\tcqo\n");
                out.printf("\tidiv %s\n", ((PhysicalRegister)inst.getRhs()).name_32);
                if(inst.getOp() == DIV)
                    out.printf("\tmovsx rax, eax");
                else
                    out.printf("\tmovsx rdx, edx");
                break;
            case SHL: case SHR:
//                System.out.println(inst);
                assert(inst.getDest() instanceof PhysicalRegister);
                assert(inst.getLhs() == inst.getDest());
                assert(inst.getRhs() instanceof Immediate || inst.getRhs() == PhysicalRegister.rcx);
                if (inst.getRhs() == PhysicalRegister.rcx)
                    out.printf((inst.getOp() == BinaryExpr.Op.SHL) ? "\tshl  %s, cl" : "\tsar  %s, cl", inst.getLhs());
                else
                    out.printf((inst.getOp() == BinaryExpr.Op.SHL) ? "\tshl  %s, %s" : "\tsar  %s, %s", inst.getLhs(), inst.getRhs());
                break;
            case XOR:
                assert(inst.getDest() == inst.getLhs());
                out.printf("\txor  %s, %s", inst.getDest(), inst.getRhs());
                break;
            case OR:
                assert(inst.getDest() == inst.getLhs());
                out.printf("\tor   %s, %s", inst.getDest(), inst.getRhs());
                break;
            case AND:
                assert(inst.getDest() == inst.getLhs());
                out.printf("\tand  %s, %s", inst.getDest(), inst.getRhs());
                break;
        }
    }

    @Override
    public void visit(Branch inst) {
        if (inst.getCmp() != null){
            assert(inst.getCmp().getLhs() instanceof Register);

            if (nextBB() == inst.getIfTrue()) {
                inst.getCmp().convert();
                inst.Swap();
            }

            out.printf("\tcmp  %s, %s\n", inst.getCmp().getLhs(), inst.getCmp().getRhs());
            switch (inst.getCmp().getOp()){
                case EQ:
                    out.printf("\tje   %s\n", inst.getIfTrue());
                    break;
                case NE:
                    out.printf("\tjne  %s\n", inst.getIfTrue());
                    break;
                case GT:
                    out.printf("\tjg   %s\n", inst.getIfTrue());
                    break;
                case LT:
                    out.printf("\tjl   %s\n", inst.getIfTrue());
                    break;
                case GE:
                    out.printf("\tjge  %s\n", inst.getIfTrue());
                    break;
                case LE:
                    out.printf("\tjle  %s\n", inst.getIfTrue());
                    break;
            }

            if (nextBB() != inst.getIfFalse())
                out.printf("\tjmp  %s", inst.getIfFalse());
        }else{
            if (inst.getCond() instanceof Immediate){
                if (((Immediate) inst.getCond()).getValue() == 0) {
                    if (nextBB() != inst.getIfFalse())
                        out.printf("\tjmp  %s", inst.getIfFalse());
                }
                else {
                    if (nextBB() != inst.getIfTrue())
                        out.printf("\tjmp  %s", inst.getIfTrue());
                }
            }else {
                out.printf("\tcmp  %s, 0\n", inst.getCond());
                if (nextBB() == inst.getIfFalse()) {
                    out.printf("\tjne  %s\n", inst.getIfTrue());
                }else if (nextBB() == inst.getIfTrue()){
                    out.printf("\tje   %s", inst.getIfFalse());
                }else{
                    out.printf("\tje   %s", inst.getIfFalse());
                    out.printf("\tjmp  %s\n", inst.getIfTrue());
                }
            }
        }
    }

    @Override
    public void visit(Call inst) {
        if (inst.getCallee() instanceof BuiltinFunction)
            out.printf("\tcall %s",inst.getCallee().getName());
        else
            out.printf("\tcall %s",inst.getCallee().getBBStart());
    }

    @Override
    public void visit(IntCmp inst) {
//        System.out.println("LONELY INTCMP "+inst);
        //out.printf("\tmov  %s, 0\n", inst.getDest());
        out.printf("\tcmp  %s, %s\n", inst.getLhs(), inst.getRhs());
        switch (inst.getOp()){
            case EQ:
                out.printf("\tsete al\n");
                break;
            case NE:
                out.printf("\tsetne al\n");
                break;
            case GT:
                out.printf("\tsetg al\n");
                break;
            case LT:
                out.printf("\tsetl al\n", inst.getDest());
                break;
            case GE:
                out.printf("\tsetge al\n", inst.getDest());
                break;
            case LE:
                out.printf("\tsetle al\n", inst.getDest());
                break;
        }
        out.printf("\tmovzx %s, al", inst.getDest());
    }

    public BasicBlock nextBB(){
        return bblist_ptr < Bblist.size() - 1? Bblist.get(bblist_ptr + 1) : null;
    }

    @Override
    public void visit(Jump inst) {
//        System.out.println(inst);
//        System.out.println(nextBB());
        if (nextBB() == inst.getDest()) return;
        out.printf("\tjmp  %s", inst.getDest());
    }

    @Override
    public void visit(Load inst) {
        if (inst.getSrc1() instanceof GlobalVar)
            out.printf("\tmov  %s, qword[rel %s]", inst.getDest(), inst.getSrc1());
        else
            out.printf("\tmov  %s, qword[%s]", inst.getDest(), inst.getSrc1());
    }

    @Override
    public void visit(Malloc inst) {

    }

    @Override
    public void visit(Move inst) {
        if (inst.getNext() != null && inst.getNext() instanceof Move
                && ((Move) inst.getNext()).getDest() == inst.getDest()) return;
        if (inst.getSrc() instanceof StaticString)
            out.printf("\tlea  %s, [rel %s]", inst.getDest(), inst.getSrc());
        else if (inst.getSrc() instanceof Immediate) {
            if (((Immediate) inst.getSrc()).getValue() == 0)
                out.printf("\txor  %s, %s", inst.getDest(), inst.getDest());
            else
                out.printf("\tmov  %s, qword %s", inst.getDest(), inst.getSrc());
        }
        else
            out.printf("\tmov  %s, %s", inst.getDest(), inst.getSrc());
    }

    @Override
    public void visit(Store inst) {
        if (inst.getDest1() instanceof GlobalVar) {
            out.printf("\tmov  qword[rel %s], %s", inst.getDest1(), inst.getSrc());
            //System.out.println("FUCK");
        }else
            out.printf("\tmov  qword[%s], %s", inst.getDest1(), inst.getSrc());
    }

    @Override
    public void visit(UnaryOp inst) {
        assert(inst.getDest() == inst.getOperand());
        switch(inst.getOp()){
            case LINC: case RINC:
                out.printf("\tinc  %s", inst.getDest());
                break;
            case LDEC: case RDEC:
                out.printf("\tdec  %s", inst.getDest());
                break;
            case NOT:
                out.printf("\tnot  %s", inst.getDest());
                break;
            case NEG:
                out.printf("\tneg  %s", inst.getDest());
                break;
        }
    }

    @Override
    public void visit(Return inst) {
        assert(inst.getValue() == null);
        out.printf("\tleave\n\tret");
    }

    @Override
    public void visit(Pop inst) {
        out.printf("\tpop  qword %s", inst.getDest());
    }

    @Override
    public void visit(Push inst) {
        out.printf("\tpush qword %s", inst.getSrc());
    }

    @Override
    public void visit(Phi inst) {
        throw new RuntimeException("unreachable");
    }

    @Override
    public void visit(Lea inst) {
        out.printf("\tlea  %s, [%s + %s * %d + %d]", inst.getDest(), inst.getBase(),
                inst.getIndex(), inst.getSize(), inst.getOffset());
    }
}
