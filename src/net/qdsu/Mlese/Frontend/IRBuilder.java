package net.qdsu.Mlese.Frontend;

import net.qdsu.Mlese.AST.*;
import net.qdsu.Mlese.Entity.*;
import net.qdsu.Mlese.IR.BasicBlock;
import net.qdsu.Mlese.IR.BuiltinFunction;
import net.qdsu.Mlese.IR.Function;
import net.qdsu.Mlese.IR.IRRoot;
import net.qdsu.Mlese.IR.Instruction.*;
import net.qdsu.Mlese.IR.Operand.*;
import net.qdsu.Mlese.Type.ClassType;
import net.qdsu.Mlese.Type.FunctionType;
import net.qdsu.Mlese.Type.Type;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class IRBuilder implements ASTVisitor {

    private IRRoot root = new IRRoot();

    public IRRoot getRoot() {
        return root;
    }

    private Program program;

    private FunDef upperFunc;
    private Function upperIrFunc;
    private ClassDef upperClass;
    private Loop upperLoop;
    private BasicBlock curBB;

    private final static int NULLPTR = 0;


    private boolean bool_shortpath = true;

    public IRBuilder(Program program) {
        this.program = program;
    }

    @Override
    public void visit(ASTNode node) {
        throw new RuntimeException("Unexpected ASTNode in IRBuilder");
    }

    private void doIntBinary(BinaryExpr node) {
        node.getLeft().accept(this);
        node.getRight().accept(this);
        Operand lhs = node.getLeft().getIROperand();
        Operand rhs = node.getRight().getIROperand();
        VirtualRegister dest = new VirtualRegister();
        switch (node.getOp()) {
            case ADD:
            case SUB:
            case MUL:
            case DIV:
            case MOD:
            case SHL:
            case SHR:
            case AND:
            case OR:
            case XOR:
                curBB.addInstruction(new BinaryOp(node.getOp(), dest, lhs, rhs));
                node.setIROperand(dest);
                break;
            case EQ:
            case NE:
            case LT:
            case GT:
            case LE:
            case GE:
                curBB.addInstruction(new IntCmp(dest, node.getOp(),
                        node.getLeft().getIROperand(), node.getRight().getIROperand()));
//                System.out.println(bool_shortpath);
                if (bool_shortpath) {
                    setLogicBranch(dest, node);
                }else{
                    node.setIROperand(dest);
                }
                break;
        }

    }

    private void setLogicBranch(Operand cond, Expr node) {
        if (node.ifTrue == null || node.ifFalse == null) return;
        else curBB.addInstruction(new Branch(cond, node.ifTrue, node.ifFalse));
    }

    private void doStringBinary(BinaryExpr node) {
        node.getLeft().accept(this);
        node.getRight().accept(this);
        Operand lhs = node.getLeft().getIROperand();
        Operand rhs = node.getRight().getIROperand();
        VirtualRegister dest = new VirtualRegister();
        switch (node.getOp()) {
            case ADD:
                curBB.addInstruction(new Call(BuiltinFunction.stringConcat, dest, lhs, rhs));
                break;
            case EQ:
                curBB.addInstruction(new Call(BuiltinFunction.stringEQ, dest, lhs, rhs));
                curBB.addInstruction(new Branch(dest, node.ifTrue, node.ifFalse));
                break;
            case NE:
                curBB.addInstruction(new Call(BuiltinFunction.stringEQ, dest, lhs, rhs));
                curBB.addInstruction(new Branch(dest, node.ifFalse, node.ifTrue));
                break;
            case LT:
                curBB.addInstruction(new Call(BuiltinFunction.stringLT, dest, lhs, rhs));
                curBB.addInstruction(new Branch(dest, node.ifTrue, node.ifFalse));
                break;
            case LE:
                curBB.addInstruction(new Call(BuiltinFunction.stringLT, dest, rhs, lhs));
                curBB.addInstruction(new Branch(dest, node.ifFalse, node.ifTrue));
                break;
            case GE:
                curBB.addInstruction(new Call(BuiltinFunction.stringLT, dest, lhs, rhs));
                curBB.addInstruction(new Branch(dest, node.ifFalse, node.ifTrue));
                break;
            case GT:
                curBB.addInstruction(new Call(BuiltinFunction.stringLT, dest, rhs, lhs));
                curBB.addInstruction(new Branch(dest, node.ifTrue, node.ifFalse));
                break;
        }
        node.setIROperand(dest);
    }


    private boolean F = true;
    /**
     * @param dest The destination register
     * @param node The value ASTNode
     *
     * curBB ----- branch ----+---> Assign True  -------+--> After
     *                        |                         ^
     *                        |                         |
     *                        +---> Assign False -------+
     */
    private void assignLogic(VirtualRegister dest, Expr node) {
        if (node.all_id && F){
//            System.out.println("SIMPLE BOOLEAN "+node);
            bool_shortpath = false;
            node.accept(this);
            curBB.addInstruction(new Move(dest, node.getIROperand()));
            bool_shortpath = true;
        }else {
            bool_shortpath = true;
            node.ifTrue = new BasicBlock(upperIrFunc, "assign true to " + dest);
            node.ifFalse = new BasicBlock(upperIrFunc, "assign false to " + dest);
            BasicBlock after = new BasicBlock(upperIrFunc, "after assign");
            node.ifTrue.addInstruction(new Move(dest, new Immediate(1)));
            node.ifFalse.addInstruction(new Move(dest, new Immediate(0)));
            node.ifTrue.addInstruction(new Jump(after));
            node.ifFalse.addInstruction(new Jump(after));
            node.accept(this);
            curBB = after;
        }
    }


    /**
     * @param dest The address of the destination
     * @param rhs  The expression ASTNode of the value
     *
     */
    private void assignReference(Operand dest, Expr rhs) {
        Type nodetype = rhs.getType();
        VirtualRegister from;
        if (nodetype.isBool()) {
            from = new VirtualRegister();
            assignLogic(from, rhs);
        } else if (rhs.getIROperand() instanceof VirtualRegister) {
            rhs.accept(this);
            from = (VirtualRegister) rhs.getIROperand();
        } else {
            rhs.accept(this);
            from = new VirtualRegister();
            curBB.addInstruction(new Move(from, rhs.getIROperand()));
        }
        curBB.addInstruction(new Store(dest, from));
    }

    /**
     * @param node the assignment binary expression
     *
     * Process assignment
     */
    private void doAssign(BinaryExpr node) {
        node.getLeft().accept(this);

        if (node.getLeft().isRef) {
            assignReference(node.getLeft().getIROperand(), node.getRight());

        } else {
            VirtualRegister dest = (VirtualRegister) node.getLeft().getIROperand();
            if (node.getLeft().getType().isBool()) {
                assignLogic(dest, node.getRight());
            } else {
                node.getRight().accept(this);
                Operand rop = node.getRight().getIROperand();
                curBB.addInstruction(new Move(dest, rop));
            }
        }

    }

    /**
     * There are two kinds of logic expressions: atomic and compound
     * Atomic expressions include : Boolean variable / function / object compare / ....
     *
     * @param node ASTNode of the logic expression
     *
     */
    private void doLogicBinary(BinaryExpr node) {
        VirtualRegister lhs = new VirtualRegister();
        VirtualRegister rhs = new VirtualRegister();
        VirtualRegister dest = new VirtualRegister();
        switch (node.getOp()) {
            case LOR:
                /*
                *                true?
                *  -----[LHS]-+-----------------------------+-->  TRUE
                *             |                             ^
                *             |  false?              true?  |
                *             +---------[RHS]-----+---------+
                *                                 |
                *                                 |  false?
                *                                 +------------>  FALSE
                *
                * */
                if (bool_shortpath) {
                    node.getLeft().ifTrue = node.ifTrue;
                    node.getLeft().ifFalse = new BasicBlock(upperIrFunc);
                    node.getRight().ifTrue = node.ifTrue;
                    node.getRight().ifFalse = node.ifFalse;
                    node.getLeft().accept(this);
                    curBB = node.getLeft().ifFalse;
                    node.getRight().accept(this);
                }else{
                    node.getLeft().accept(this);
                    node.getRight().accept(this);
                    curBB.addInstruction(new BinaryOp(BinaryExpr.Op.OR,dest, node.getLeft().getIROperand(),
                            node.getRight().getIROperand()));
                    node.setIROperand(dest);
                }
                break;
            case LAND:
                /*
                *               false?
                *  -----[LHS]-+---------------------------+----->  FALSE
                *             |                           ^
                *             | true?               false?|
                *             +---------[RHS]-----+-------+
                *                                 |
                *                                 | true?
                *                                 +------------->  TRUE
                *
                * */
                if (bool_shortpath) {
                    node.getLeft().ifTrue = new BasicBlock(upperIrFunc);
                    node.getLeft().ifFalse = node.ifFalse;
                    node.getRight().ifTrue = node.ifTrue;
                    node.getRight().ifFalse = node.ifFalse;
                    node.getLeft().accept(this);
                    curBB = node.getLeft().ifTrue;
                    node.getRight().accept(this);
                }else{
                    node.getLeft().accept(this);
                    node.getRight().accept(this);
                    curBB.addInstruction(new BinaryOp(BinaryExpr.Op.AND,dest, node.getLeft().getIROperand(),
                            node.getRight().getIROperand()));
                    node.setIROperand(dest);
                }
                break;
            /* Atomic logic expression */
            case EQ:
                if (bool_shortpath) {
                    assignLogic(lhs, node.getLeft());
                    assignLogic(rhs, node.getRight());
                    curBB.addInstruction(new IntCmp(dest, BinaryExpr.Op.EQ, lhs, rhs));
                    curBB.addInstruction(new Branch(dest, node.ifTrue, node.ifFalse));
                }else{
                    node.getLeft().accept(this);
                    node.getRight().accept(this);
                    curBB.addInstruction(new IntCmp(dest, BinaryExpr.Op.EQ, node.getLeft().getIROperand(),
                            node.getRight().getIROperand()));
                    node.setIROperand(dest);
                }
                break;
            case NE:
                if (bool_shortpath) {
                    assignLogic(lhs, node.getLeft());
                    assignLogic(rhs, node.getRight());
                    curBB.addInstruction(new IntCmp(dest, BinaryExpr.Op.NE, lhs, rhs));
                    curBB.addInstruction(new Branch(dest, node.ifTrue, node.ifFalse));
                }else{
                    node.getLeft().accept(this);
                    node.getRight().accept(this);
                    curBB.addInstruction(new IntCmp(dest, BinaryExpr.Op.NE, node.getLeft().getIROperand(),
                            node.getRight().getIROperand()));
                    node.setIROperand(dest);
                }
                break;
        }

    }

    @Override
    public void visit(BinaryExpr node) {

        Type type = node.getLeft().getType();

        switch (node.getOp()) {
            case SUB:
            case MUL:
            case DIV:
            case MOD:
            case SHL:
            case SHR:
            case AND:
            case OR:
            case XOR:
                doIntBinary(node);
                break;
            case EQ:
            case NE:
            case ADD:
            case LT:
            case GT:
            case LE:
            case GE:
                if (type.isString())
                    doStringBinary(node);
                else if (type.isBool())
                    doLogicBinary(node);
                else
                    doIntBinary(node);
                break;
            case ASSIGN:
                doAssign(node);
                break;
            case LAND:
            case LOR:
                doLogicBinary(node);
                break;
        }
    }

    @Override
    public void visit(BoolConst node) {
        if (bool_shortpath) {
            if (node.getValue())
                curBB.addInstruction(new Jump(node.ifTrue));
            else
                curBB.addInstruction(new Jump(node.ifFalse));
        }else{
            node.setIROperand(new Immediate(node.getValue() ? 1: 0));
        }

    }

    @Override
    public void visit(Expr node) {
        throw new RuntimeException("Unexpected Expression in IRBuilder");
    }

    public void doPrint(Function func, Expr expr){
//        System.err.println("# "+expr);
        if (expr instanceof BinaryExpr && ((BinaryExpr) expr).getOp() == BinaryExpr.Op.ADD){
//            System.out.println("!!!");
            doPrint(BuiltinFunction.print, ((BinaryExpr) expr).getLeft());
            doPrint(func, ((BinaryExpr) expr).getRight());
        }else{
            VirtualRegister dest = new VirtualRegister();
//            if (expr instanceof FunCall) ((FunCall) expr).getName().accept(this);
            if (expr instanceof FunCall &&
                    ((FunctionType)((FunCall) expr).getName().getType()).getEntity().builtin &&
                    ((FunctionType)((FunCall) expr).getName().getType()).getEntity().getName().equals("toString")
                ){
//                System.err.println("! "+upperIrFunc);
//                ((FunCall) expr).getName().accept(this);
                Expr arg = ((FunCall) expr).getArguments().get(0);
                arg.accept(this);
                curBB.addInstruction(new Call(
                        func == BuiltinFunction.print ? BuiltinFunction.printInt : BuiltinFunction.printlnInt,
                        dest,
                        arg.getIROperand()
                ));
            }else{
//                System.out.println("@ "+expr);
                expr.accept(this);
                curBB.addInstruction(new Call(func, dest, expr.getIROperand()));
            }
        }
    }

    @Override
    public void visit(FunCall node) {


        node.getName().accept(this);
        Function callee = ((FunPtr) node.getName().getIROperand()).getFun();

        if (callee == upperIrFunc) upperIrFunc.recursive = true;

        if (callee == BuiltinFunction.println || callee == BuiltinFunction.print){
            doPrint(callee, node.getArguments().get(0));
            return;
        }

        VirtualRegister dest = new VirtualRegister();
        Call call = new Call(callee, dest);



        /* Calculate the arguments*/
        for (Expr arg : node.getArguments()) {
            if (arg.getType().isBool()) {
                VirtualRegister d = new VirtualRegister();
                assignLogic(d, arg);
                call.addArgument(d);
            } else {
                arg.accept(this);
                call.addArgument(arg.getIROperand());
            }
        }

        /* If the function is a member of a class, pass the "this" pointer to it */
        if (callee.isMember()) {
            if (node.getName() instanceof MemberExpr) {
                call.addArgument(((MemberExpr) node.getName()).getPrefix().getIROperand());
            } else if (node.getName() instanceof Identifier) {
                call.addArgument(upperFunc.getIR().getThis());
            }

            /* If it is a arraySize call, reduce it */
            if (callee == BuiltinFunction.arraySize) {
                curBB.addInstruction(new Load(dest, call.getArguments().get(0)));
                node.setIROperand(dest);
                return;
            }
        }

        /* Store all used global variables */
        if (!(callee instanceof BuiltinFunction)) {
            HashSet<VariableEntity> v;

            if (upperFunc == null) {
                v = new HashSet<>(global_var_ent);
            }else {
                v = new HashSet<>(upperFunc.getEntity().globalWrite);
            }
            v.retainAll(((FunctionType)node.getName().getType()).getEntity().globalReadClosure);
            storeGlobal(v);

 //           Set<FunctionEntity> z = ((FunctionType)node.getName().getType()).getEntity().calledFun;
 //           System.out.print(callee+" ");
//            z.forEach(x->System.out.print(x.getName()+" "));
//            System.out.println();
        }

        curBB.addInstruction(call);
        node.setIROperand(dest);

        /* Load all used global variables */
        if (!(callee instanceof BuiltinFunction)) {
            HashSet<VariableEntity> v;
//            System.out.println(upperFunc.getEntity().globalRead);
            if (upperFunc == null) {
                v = new HashSet<>(global_var_ent);
            }else {
                v = new HashSet<>(upperFunc.getEntity().globalRead);
            }
//            if (upperFunc != null)
//                v.addAll(upperFunc.getEntity().globalWrite);
            v.retainAll(((FunctionType)node.getName().getType()).getEntity().globalWriteClosure);
            loadGlobal(v);
        }

        /* If it is a atomic bool expression */
        if (node.getType().isBool()) {
            if (node.ifTrue != null && node.ifFalse != null)
                curBB.addInstruction(new Branch(dest, node.ifTrue, node.ifFalse));
        }


    }

    @Override
    public void visit(Identifier node) {
        Entity entity = node.getEntity();
        if (entity instanceof VariableEntity) {

            VariableEntity var = (VariableEntity) entity;

            if (var.isGlobal()) {
                node.setIROperand(((GlobalVar) var.getIR()).getLocalReg());
//                System.out.printf("%s %s\n", node , node.getIROperand());
            }

            else if (var.isMember()) {
                VirtualRegister src = new VirtualRegister();
                Operand this_ptr = upperFunc.getIR().getThis();
                int offset = upperClass.getEntity().getElementPos(var);
                curBB.addInstruction(new BinaryOp(BinaryExpr.Op.ADD, src, this_ptr,
                        new Immediate(offset)));

                /* Judge whether it need dereference
                *  If it is a r-value and it is a reference, then it need dereference.
                * */
                if (!node.isRef || node.getLvalue()) {
                    node.setIROperand(src);
                } else {
                    VirtualRegister dest = new VirtualRegister();
                    curBB.addInstruction(new Load(dest, src));
                    node.setIROperand(dest);
                }

            } else
                /* Local variables or parameters*/
                node.setIROperand(var.getIR());


            /* If it is atomic boolean expression */
//            if (var.getType().isBool())
//                System.out.println(node.getName()+" "+node.getLvalue());
            if (var.getType().isBool() && !node.getLvalue() && bool_shortpath) {

                curBB.addInstruction(new Branch(node.getIROperand(), node.ifTrue, node.ifFalse));
            }

        } else if (entity instanceof FunctionEntity) {
            node.setIROperand(new FunPtr(((FunctionEntity) entity).getIR()));
        }
    }

    @Override
    public void visit(IntConst node) {
        Immediate int_imm = new Immediate(node.getValue());
        node.setIROperand(int_imm);
    }

    @Override
    public void visit(MemberExpr node) {
        node.getPrefix().accept(this);

        if (node.entity instanceof VariableEntity) {
            VirtualRegister dest = new VirtualRegister();
            Operand addr = node.getPrefix().getIROperand();

            /* To get the position of the member variable in the class*/
            VariableEntity var = (VariableEntity) node.entity;
            ClassEntity classent = ((ClassType) node.getPrefix().getType()).getEntity();
            Operand offset = new Immediate(classent.getElementPos(var));

            /* To calculate the source address*/
            VirtualRegister src = new VirtualRegister();
            curBB.addInstruction(new BinaryOp(BinaryExpr.Op.ADD, src, addr, offset));


            if (node.getLvalue()) {
                node.setIROperand(src);
            } else {
                curBB.addInstruction(new Load(dest, src));
                node.setIROperand(dest);
            }

        } else if (node.entity instanceof FunctionEntity) {
            node.setIROperand(new FunPtr(((FunctionEntity) node.entity).getIR()));
        }

        if (node.getType().isBool() && !node.getLvalue())
            curBB.addInstruction(new Branch(node.getIROperand(), node.ifTrue, node.ifFalse));
    }



    /**
     *
     * To allocate a new 1-D array
     *
     * @param len The length of the array
     * @return The address of the newly-allocated array
     */
    private VirtualRegister allocArray(Operand len) {
        VirtualRegister tmp_0 = new VirtualRegister();

        curBB.addInstruction(new BinaryOp(BinaryExpr.Op.ADD, tmp_0, len, new Immediate(1)));
        /* The first unit of the allocated space is used to store the length of array*/
        VirtualRegister tmp = new VirtualRegister();
        curBB.addInstruction(new BinaryOp(BinaryExpr.Op.MUL, tmp, tmp_0, new Immediate(8)));
//        curBB.addInstruction(new Lea(tmp,
//                new Immediate(0),
//                8,
//                len,8));

        VirtualRegister addr = new VirtualRegister();


        curBB.addInstruction(new Call(BuiltinFunction.malloc, addr, tmp));
        curBB.addInstruction(new Store(addr, len));
        return addr;
    }

    private void allocMultiArray(Operand base, ArrayList<Operand> dims, int d_ptr) {
        /*
        *  for i = 1 to dims[d_ptr] do
        *     addr = allocArray( dims[d_ptr + 1] )
        *     *(base + i * 8) = addr
        *     allocMultiArray(addr, dims, d_ptr + 1)
        * */

        upperIrFunc.contain_loop = true;

        if (d_ptr == dims.size() - 1) return;
        VirtualRegister index = new VirtualRegister();
        curBB.addInstruction(new Move(index, new Immediate(1)));

        BasicBlock cond = new BasicBlock(upperIrFunc);
        BasicBlock loop = new BasicBlock(upperIrFunc);
        BasicBlock after = new BasicBlock(upperIrFunc);

        curBB.addInstruction(new Jump(cond));
        VirtualRegister cmp = new VirtualRegister();
        cond.addInstruction(new IntCmp(cmp, BinaryExpr.Op.LE, index, dims.get(d_ptr)));
        cond.addInstruction(new Branch(cmp, loop, after));

        VirtualRegister offset = new VirtualRegister();
        loop.addInstruction(new BinaryOp(BinaryExpr.Op.MUL, offset, index , new Immediate(8)));

        VirtualRegister addr = new VirtualRegister();
        loop.addInstruction(new BinaryOp(BinaryExpr.Op.ADD, addr, base, offset));
        VirtualRegister new_array = new VirtualRegister();
        curBB = loop;
        new_array = allocArray(dims.get(d_ptr + 1));
        curBB.addInstruction(new Store(addr, new_array));
        allocMultiArray(new_array, dims, d_ptr + 1);
        curBB.addInstruction(new UnaryOp(UnaryExpr.Operand.LINC, index, index));
        curBB.addInstruction(new Jump(cond));
        curBB = after;
    }

    @Override
    public void visit(NewExpr node) {
        if (node.getDim() > 0) {
            ArrayList<Operand> dims = new ArrayList<>();
            for (Expr dim_expr : node.getDims()) {
                dim_expr.accept(this);
                dims.add(dim_expr.getIROperand());
            }
            node.setIROperand(allocArray(dims.get(0)));

            if (dims.size() > 1) {
                allocMultiArray(node.getIROperand(), dims, 0);
            }
        } else {
            //Class
            VirtualRegister dest = new VirtualRegister();
            ClassEntity node_class = ((ClassType) node.getType()).getEntity();
            curBB.addInstruction(new Call(BuiltinFunction.malloc, dest,new Immediate(node_class.getSize())));
//            curBB.addInstruction(new Malloc(dest, new Immediate(node_class.getSize())));
            node.setIROperand(dest);
            if (node_class.getConstructor() != null) {
                VirtualRegister tmp = new VirtualRegister();
                curBB.addInstruction(new Call(node_class.getConstructor().getIR(), tmp, dest));
            }
        }
    }

    @Override
    public void visit(NullConst node) {
        Immediate null_imm = new Immediate(NULLPTR);
        node.setIROperand(null_imm);
    }

    @Override
    public void visit(StringConst node) {
        StaticString ss = new StaticString(node.getValue());
        root.strings.add(ss);
        node.setIROperand(ss);
    }

    @Override
    public void visit(Subscript node) {
        node.getName().accept(this);
        node.getIndex().accept(this);
        VirtualRegister addr = new VirtualRegister();
        curBB.addInstruction(new Lea(addr,
                node.getName().getIROperand(),
                8,
                node.getIndex().getIROperand(),
                8));
        if (node.isRef && node.getLvalue()) {
            node.setIROperand(addr);
        } else {
            VirtualRegister dest = new VirtualRegister();
            curBB.addInstruction(new Load(dest, addr));
            node.setIROperand(dest);
            if (!node.getLvalue() && node.getType().isBool())
                curBB.addInstruction(new Branch(dest, node.ifTrue, node.ifFalse));
        }

    }

    @Override
    public void visit(ThisExpr node) {
        node.setIROperand(upperFunc.getIR().getThis());
    }

    @Override
    public void visit(TypeNode node) {

    }

    @Override
    public void visit(UnaryExpr node) {
        VirtualRegister dest = new VirtualRegister();
        switch (node.getOp()) {
            case LDEC:
            case LINC:
                node.getSubexp().accept(this);
                if (node.getSubexp().isRef) {
                    VirtualRegister tmp = new VirtualRegister();
                    curBB.addInstruction(new Load(tmp, node.getSubexp().getIROperand()));
                    curBB.addInstruction(new UnaryOp(node.getOp(), tmp, tmp));
                    curBB.addInstruction(new Store(node.getSubexp().getIROperand(), tmp));
                    if (node.getLvalue())
                        node.setIROperand(node.getSubexp().getIROperand());
                    else
                        node.setIROperand(tmp);
                } else {
                    VirtualRegister reg = (VirtualRegister) node.getSubexp().getIROperand();
                    curBB.addInstruction(new UnaryOp(node.getOp(), reg, reg));
                    node.setIROperand(reg);
                }
                break;
            case RDEC:
            case RINC:
                node.getSubexp().accept(this);
                if (node.getSubexp().isRef) {
                    VirtualRegister new_value = new VirtualRegister();
                    VirtualRegister old_value = new VirtualRegister();
                    curBB.addInstruction(new Load(old_value, node.getSubexp().getIROperand()));
                    curBB.addInstruction(new UnaryOp(node.getOp(), new_value, old_value));
                    curBB.addInstruction(new Store(node.getSubexp().getIROperand(), new_value));
                    node.setIROperand(old_value);
                } else {
                    VirtualRegister reg = (VirtualRegister) node.getSubexp().getIROperand();
                    VirtualRegister old_value = new VirtualRegister();
                    curBB.addInstruction(new Move(old_value, reg));
                    curBB.addInstruction(new UnaryOp(node.getOp(), reg, reg));
                    node.setIROperand(old_value);
                }
                break;
            case POS:
                node.getSubexp().setLvalue(false);
                break;
            case NEG:
            case NOT:
                node.getSubexp().setLvalue(false);
                node.getSubexp().accept(this);
                curBB.addInstruction(new UnaryOp(node.getOp(), dest, node.getSubexp().getIROperand()));
                node.setIROperand(dest);
                break;
            case LNOT:
                if (bool_shortpath) {
                    node.getSubexp().setLvalue(false);
                    node.getSubexp().ifTrue = node.ifFalse;
                    node.getSubexp().ifFalse = node.ifTrue;
                    node.getSubexp().accept(this);
                }else{
                    node.getSubexp().accept(this);
                    curBB.addInstruction(new UnaryOp(UnaryExpr.Operand.NOT, dest, node.getSubexp().getIROperand()));
                    node.setIROperand(dest);
                }
        }
    }

    @Override
    public void visit(EmptyExpr node) {

    }

    @Override
    public void visit(Stmt node) {
        throw new RuntimeException("Unknown statement in IR build");
    }

    @Override
    public void visit(Block node) {
        for (Stmt stmt : node.getStatements()) {
            stmt.accept(this);
        }
    }

    @Override
    public void visit(VarStmt node) {
        VariableEntity entity = node.getVarEntity();
        if (entity.isGlobal() || entity.isMember()) {
            //Global or member, do nothing
        } else {
            VirtualRegister new_reg = new VirtualRegister(node.getName());
            entity.bindIR(new_reg);
            if (node.getInit() != EmptyExpr.getInstance()) {
                //Add a virtual register for it
                if (entity.getType().isBool())
                    assignLogic(new_reg, node.getInit());
                else {
                    node.getInit().accept(this);
                    curBB.addInstruction(new Move(new_reg, node.getInit().getIROperand()));
                }
            }else{
                curBB.addInstruction(new Move(new_reg, new Immediate(0)));
            }
        }
    }

    @Override
    public void visit(IfStmt node) {
        boolean has_else = node.getOtherwise() != EmptyExpr.getInstance();
        BasicBlock then = new BasicBlock(upperIrFunc,"If then");
        BasicBlock otherwise = has_else ? new BasicBlock(upperIrFunc,"If else") : null;
        BasicBlock after = new BasicBlock(upperIrFunc,"After If");

//        bool_shortpath = true;
        node.getCond().ifTrue = then;
        node.getCond().ifFalse = has_else ? otherwise : after;
        node.getCond().accept(this);
        //BasicBlock cond = curBB;

        curBB = then;
        node.getThen().accept(this);
        curBB.addInstruction(new Jump(after));

        if (has_else) {
            curBB = otherwise;
            node.getOtherwise().accept(this);
            curBB.addInstruction(new Jump(after));
        }
        curBB = after;

    }

    @Override
    public void visit(WhileStmt node) {

        /*
        *
        *   +-------------------------------+
        *   |                               |
        *   \/         true?                |
        * --+--[cond]-+------->[loop]-------+
        *             |
        *             | false?
        *             +------------------>[after]
        *
        * */
        upperIrFunc.contain_loop = true;

        Loop old_upperLoop = upperLoop;
//        bool_shortpath = true;
        BasicBlock cond = new BasicBlock(upperIrFunc, "While cond");
        curBB.addInstruction(new Jump(cond));
        BasicBlock loop = new BasicBlock(upperIrFunc, "While loop");
        BasicBlock after = new BasicBlock(upperIrFunc, "After while");

        upperLoop = node;
        node.condBB = cond;
        node.afterBB = after;

        node.getCond().ifTrue = loop;
        node.getCond().ifFalse = after;
        curBB = cond;
        node.getCond().accept(this);
        curBB = loop;
        node.getStmt().accept(this);
        curBB.addInstruction(new Jump(cond));
        curBB = after;
        upperLoop = old_upperLoop;
    }

    @Override
    public void visit(ForStmt node) {

        /*
        *            +------------------------------------------+
        *            |                                          |
        *            \/         true?                           |
        * ---[init]--+---[cond]-+---->[loop]------>[step]-------+
        *                       |
        *                       | false?
        *                       +----------------------->[after]
        *
        * */

        upperIrFunc.contain_loop = true;

        Loop old_upperLoop = upperLoop;

        node.getInit().accept(this);
//        bool_shortpath = true;
        BasicBlock cond = new BasicBlock(upperIrFunc, "For cond");
        curBB.addInstruction(new Jump(cond));

        upperLoop = node;
        BasicBlock loop = new BasicBlock(upperIrFunc, "For loop");

        BasicBlock step = new BasicBlock(upperIrFunc, "For step");

        BasicBlock after = new BasicBlock(upperIrFunc, "After For");

        node.condBB = cond;
        node.afterBB = after;
        node.stepBB = step;

        node.getCond().ifTrue = loop;
        node.getCond().ifFalse = after;

        curBB = cond;
        if (node.getCond() != EmptyExpr.getInstance())
            node.getCond().accept(this);
        else
            curBB.addInstruction(new Jump(loop));

        curBB = loop;
        node.getStmt().accept(this);
        curBB.addInstruction(new Jump(step));

        curBB = step;
        node.getStep().accept(this);
        curBB.addInstruction(new Jump(cond));

        curBB = after;
        upperLoop = old_upperLoop;
    }

    @Override
    public void visit(ReturnStmt node) {
        if (node.getValue() == EmptyExpr.getInstance()) {
            if (upperIrFunc != root.getMainFun())
                storeGlobal(upperFunc.getEntity().globalWrite);
            curBB.addInstruction(new Return());
        } else {
            if (node.getValue().getType().isBool()) {
                VirtualRegister ret = new VirtualRegister();
                assignLogic(ret, node.getValue());
                if (upperIrFunc != root.getMainFun())
                    storeGlobal(upperFunc.getEntity().globalWrite);
                curBB.addInstruction(new Return(ret));
            } else {
                node.getValue().accept(this);
                if (upperIrFunc != root.getMainFun())
                    storeGlobal(upperFunc.getEntity().globalWrite);
                curBB.addInstruction(new Return(node.getValue().getIROperand()));
            }
        }
        curBB = new BasicBlock(upperIrFunc, "trash");
    }

    @Override
    public void visit(BreakStmt node) {
        curBB.addInstruction(new Jump(upperLoop.afterBB));
        curBB = new BasicBlock(upperIrFunc, "Trash");
    }

    @Override
    public void visit(ContinueStmt node) {
        curBB.addInstruction(new Jump(upperLoop instanceof ForStmt ? upperLoop.stepBB : upperLoop.condBB));
        curBB = new BasicBlock(upperIrFunc, "Trash");
    }

    private void allocGlobalWrite(Set<VariableEntity> vars) {
        for (VariableEntity var : vars) {
            VirtualRegister new_reg = new VirtualRegister();
            ((GlobalVar) var.getIR()).setLocalReg(new_reg);
        }
    }

    private void loadGlobalInit(Set<VariableEntity> vars) {
        for (VariableEntity var : vars) {
            VirtualRegister new_reg = new VirtualRegister();
            ((GlobalVar) var.getIR()).setLocalReg(new_reg);
            curBB.addInstruction(new Load(new_reg, var.getIR()));
        }
    }

    private void loadGlobal(Set<VariableEntity> vars) {
        for (VariableEntity var : vars) {
            VirtualRegister dest = ((GlobalVar) var.getIR()).getLocalReg();
            curBB.addInstruction(new Load(dest, var.getIR()));
        }
    }

    private void storeGlobal(Set<VariableEntity> vars) {
        for (VariableEntity var : vars) {
            VirtualRegister src_reg = ((GlobalVar) var.getIR()).getLocalReg();
            curBB.addInstruction(new Store(var.getIR(), src_reg));
        }
    }

    @Override
    public void visit(FunDef node) {

//        System.out.printf("GR: %s\n", node.getName());
//        for (VariableEntity v : node.getEntity().globalRead)
//            System.out.printf(" %s", v.getIR());
//        System.out.println();

        Function fun = node.getIR();
        upperIrFunc = fun;
        BasicBlock init_bb = new BasicBlock(upperIrFunc);

        fun.setBBStart(init_bb);
        curBB = init_bb;

        /*Load used global variables*/
        //allocGlobalWrite(node.getEntity().globalWrite);
        loadGlobalInit(node.getEntity().globalRead);
//        loadGlobalInit(global_var_ent);
        upperFunc = node;
        node.getStmt().accept(this);
        upperFunc = null;

        /*Store used global variables*/
//        System.out.println(root.getMainFun());
        if (upperIrFunc != root.getMainFun()) {
            storeGlobal(node.getEntity().globalWrite);
        }
//        storeGlobal(global_var_ent);

        if (upperIrFunc == root.getMainFun())
            curBB.addInstruction(new Return(new Immediate(0)));
        else
            curBB.addInstruction(new Return());

    }

    @Override
    public void visit(ClassDef node) {

    }


    private void scanFunction(FunDef node) {
        Function fun = new Function((upperClass == null ? "" : (upperClass.getName()) + '.') + node.getName());
        /*Add parameter*/
        for (FunDef.parameter par : node.getParameters()) {
            VirtualRegister parreg = new VirtualRegister();
            fun.addParameter(parreg);
            par.varEntity.bindIR(parreg);
        }
        root.addFunction(fun);
        node.getEntity().bindIR(fun);
        node.bindIR(fun);

        if (fun.getName().equals("main") && upperClass == null)
            root.setMainFun(fun);
    }

    private void scanMethod(FunDef node) {
        scanFunction(node);
        VirtualRegister this_ptr = new VirtualRegister();
        node.getIR().addParameter(this_ptr);
        node.getIR().setThis(this_ptr);
    }

    private void doGlobalInit() {


        /*
        *  function __init__
        *      [initialization is finished here]
        *
        *  function main
        *      >>>>>  insert a call to __init__  <<<<<
        *      [the code of main function]
        *
        *
        * */

        Function init_fun = new Function("__init__");

        root.addFunction(init_fun);
        upperIrFunc = init_fun;
        curBB = new BasicBlock(init_fun);
        init_fun.setBBStart(curBB);
        for (VarStmt var_decl : program.getVariables()) {
            if (var_decl.getInit() != EmptyExpr.getInstance() &&
                !(var_decl.getInit() instanceof IntConst && ((IntConst) var_decl.getInit()).getValue() == 0)
            ) {
                VirtualRegister tmp = new VirtualRegister();
                GlobalVar global_var = (GlobalVar) var_decl.getVarEntity().getIR();
                global_var.setLocalReg(tmp);
                if (var_decl.getVarEntity().getType().isBool()) {
                    assignLogic(tmp, var_decl.getInit());
                } else {
                    var_decl.getInit().accept(this);
                    curBB.addInstruction(new Move(tmp, var_decl.getInit().getIROperand()));
                }
                curBB.addInstruction(new Store(global_var, tmp));
            }
        }
        curBB.addInstruction(new Return());

        VirtualRegister tmp = new VirtualRegister();
        curBB = new BasicBlock(root.getMainFun(), "init");
        curBB.addInstruction(new Call(init_fun, tmp));
        curBB.addInstruction(new Jump(root.getMainFun().getBBStart()));
        root.getMainFun().setBBStart(curBB);
    }

    private boolean updateFunGlobalVar(FunDef fun){


        HashSet<VariableEntity> global_write = fun.getEntity().globalWriteClosure;
        HashSet<VariableEntity> new_global_write = new HashSet<>(global_write);
        for (FunctionEntity callee: fun.getEntity().calledFun){
            if (callee.globalWriteClosure != null )
                new_global_write.addAll(callee.globalWriteClosure);
        }
        fun.getEntity().globalWriteClosure = new_global_write;



        HashSet<VariableEntity> global_read = fun.getEntity().globalReadClosure;
        HashSet<VariableEntity> new_global_read = new HashSet<>(global_read);
        for (FunctionEntity callee: fun.getEntity().calledFun){
            if (callee.globalReadClosure != null )
                new_global_read.addAll(callee.globalReadClosure);
        }
        fun.getEntity().globalReadClosure = new_global_read;
        return !new_global_write.equals(global_write) || !new_global_read.equals(global_read);
    }

    private HashSet<VariableEntity> global_var_ent = new HashSet<>();

    @Override
    public void visit(Program node) {
        //Create IRRoot

        //Calculate the closure of the write of global variables



        for (FunDef func : node.getFunctions()){
            func.getEntity().globalRead.addAll(func.getEntity().globalWrite);
            func.getEntity().globalWriteClosure = new HashSet<>(func.getEntity().globalWrite);
            func.getEntity().globalReadClosure = new HashSet<>(func.getEntity().globalRead);
            //System.out.println(func.getName()+" "+func.getEntity().globalRead);
        }
        for (ClassDef c : node.getClasses()){
            for (FunDef func: c.getFunctions()){
                func.getEntity().globalRead.addAll(func.getEntity().globalWrite);
                func.getEntity().globalWriteClosure = new HashSet<>(func.getEntity().globalWrite);
                func.getEntity().globalReadClosure = new HashSet<>(func.getEntity().globalRead);
            }
        }



        boolean changed = true;
        while (changed){
            changed = false;
            for (FunDef func : node.getFunctions()){
                changed = changed || updateFunGlobalVar(func);
            }
            for (ClassDef c : node.getClasses()){
                for (FunDef m: c.getFunctions()){
                    changed = changed || updateFunGlobalVar(m);
                }
            }
        }



        //Add global variables to IRRoot
        for (VarStmt var : program.getVariables()) {
            VariableEntity var_ent = var.getVarEntity();
            GlobalVar new_var = new GlobalVar(var.getInit());
            var_ent.bindIR(new_var);
            root.addGlobalVar(new_var);
            global_var_ent.add(var_ent);
        }

//        for (FunDef func : node.getFunctions()){
//            System.out.print(func.getName()+" ");
//            func.getEntity().globalReadClosure.forEach(x->System.out.print(x.getIR()+" "));
//            System.out.println();
//        }

        //Scan all static functions and methods, create a IRFunction for each
        for (FunDef fun : program.getFunctions()) {
            scanFunction(fun);
        }

        for (ClassDef classdef : program.getClasses()) {
            upperClass = classdef;
            for (FunDef fun : classdef.getFunctions()) {
                scanMethod(fun);
            }
        }


        upperClass = null;
        //Use the visitor to visit each (static) function
        for (FunDef fun : program.getFunctions()) {
            fun.accept(this);
        }
        for (ClassDef classdef : program.getClasses()) {
            upperClass = classdef;
            for (FunDef fun : classdef.getFunctions()) {
                fun.accept(this);
            }
        }

        //Add initialization to main function
        doGlobalInit();

    }

}
