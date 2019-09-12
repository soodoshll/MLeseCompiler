package net.qdsu.Mlese.Frontend;
import net.qdsu.Mlese.AST.*;
import net.qdsu.Mlese.Entity.*;
import net.qdsu.Mlese.Type.*;
import net.qdsu.Mlese.Parser.MleseLexer;
import net.qdsu.Mlese.Parser.MleseParser;
import net.qdsu.Mlese.Utility.SourcePos;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import net.qdsu.Mlese.Utility.SemanticError;

public class SemanticChecker implements ASTVisitor{
    private Scope globalScope ;
    private Scope localScope ;

    private FunDef   upperFunc;
    private Stmt     upperLoop;
    private ClassDef upperClass;

    @Override
    public void visit(ASTNode node) throws SemanticError{
        throw new SemanticError(node.getPos(),"Unknown AST Node.");
    }

    private void checkGlobalRead(Expr node){
        //
        if (node instanceof Identifier && ((Identifier) node).getEntity() instanceof VariableEntity){
            VariableEntity var = (VariableEntity)((Identifier) node).getEntity();
            //System.out.println(upperFunc.getName()+" "+var.isGlobal());
            if (var.isGlobal() && upperFunc!=null){
                upperFunc.getEntity().globalRead.add(var);
            }
        }
    }

    private void checkGlobalWrite(Expr node){
        if (node instanceof Identifier){
            Entity ent = ((Identifier) node).getEntity();
            if (ent instanceof VariableEntity){
                VariableEntity var = (VariableEntity)ent;
                if (var.isGlobal() && upperFunc!=null)
                    upperFunc.getEntity().globalWrite.add(var);
            }
        }
    }

    @Override
    public void visit(BinaryExpr node) throws SemanticError {
        node.getLeft().accept(this);
        node.getRight().accept(this);

        Type ltype = node.getLeft().getType();
        Type rtype = node.getRight().getType();

        node.all_id = node.getLeft().all_id && node.getRight().all_id &&
                node.getOp() != BinaryExpr.Op.DIV && node.getOp() != BinaryExpr.Op.MOD;


        switch(node.getOp()){

            case ADD:            //For both string and int
                if (ltype.isInt() &&
                    rtype.isInt()
                )
                    node.setType(
                            PrimitiveType.getInt()
                    );

                else if (ltype.isString()
                &&  rtype.isString()
                )
                        node.setType(
                                PrimitiveType.getString()
                        );
                else throw new SemanticError(node.getPos(),
                    "Type Error");

                node.getLeft().setLvalue(false);
                node.getRight().setLvalue(false);

                checkGlobalRead(node.getLeft());
                checkGlobalRead(node.getRight());
                break;
            case LT: case GT: case GE: case LE:                 //For int and string
                if ((ltype.isInt() &&
                        rtype.isInt())||
                        (ltype.isString() &&
                                rtype.isString())
                ) {
                    node.setType(PrimitiveType.getBool());
                }else
                    throw new SemanticError(node.getPos(),
                            "Type Error");

                node.getLeft().setLvalue(false);
                node.getRight().setLvalue(false);

                checkGlobalRead(node.getLeft());
                checkGlobalRead(node.getRight());
                break;
            case EQ: case NE:
                if (rtype.isSame(ltype) || ltype.isSame(rtype)
                ){
                    node.setType(PrimitiveType.getBool());
                }else
                    throw new SemanticError(node.getPos(),
                            "Type Error");

                node.getLeft().setLvalue(false);
                node.getRight().setLvalue(false);

                checkGlobalRead(node.getLeft());
                checkGlobalRead(node.getRight());
                break;
            case SUB: case MUL: case DIV: case MOD: case SHL: case SHR: //Int only
            case AND: case OR: case XOR:
                if (ltype.isInt() &&
                        rtype.isInt())
                    node.setType(PrimitiveType.getInt());
                else
                    throw new SemanticError(node.getPos(),
                            "Type Error");

                node.getLeft().setLvalue(false);
                node.getRight().setLvalue(false);

                checkGlobalRead(node.getLeft());
                checkGlobalRead(node.getRight());

                break;
            case LAND: case LOR:    //Bool only
                if (ltype.isBool() &&
                rtype.isBool())
                    node.setType(PrimitiveType.getBool());
                else
                    throw new SemanticError(node.getPos(),
                            "Type Error");

                node.getLeft().setLvalue(false);
                node.getRight().setLvalue(false);

                checkGlobalRead(node.getLeft());
                checkGlobalRead(node.getRight());

                break;
            case ASSIGN:
                //judge whether the lhs is lvalue
                if (!node.getLeft().getLvalue()){
                    throw new SemanticError(node.getPos(), "Can't assign to a non-lvalue");
                }
                if (!ltype.isSame(rtype)){
                    throw new SemanticError(node.getPos(), "Type Error");
                }
                node.setType(PrimitiveType.getAssign());
                node.getRight().setLvalue(false);

                checkGlobalWrite(node.getLeft());
                checkGlobalRead(node.getRight());
                break;
            default:
                throw new SemanticError(node.getPos(),
                        "Unknown Binary operand");
        }
    }

    @Override
    public void visit(BoolConst node) throws SemanticError {
        node.setType(PrimitiveType.getPrimitiveType(Type.Types.BOOL));
        node.all_id = true;
    }

    @Override
    public void visit(Expr node) throws SemanticError {
        throw new SemanticError(node.getPos(),"Unknown Expression.");
    }

    @Override
    public void visit(FunCall node) throws SemanticError {
        //Check Funcall
        node.getName().accept(this);

        if (!node.getName().getType().isFunc())
            throw new SemanticError(node.getName().getPos(),"This object is not callable");

        FunctionEntity func = ((FunctionType)node.getName().getType()).getEntity();

        if (upperFunc != null ) upperFunc.getEntity().calledFun.add(func);

        //Check Arguments
        if (node.getArguments().size() != func.getParameters().size())
            throw new SemanticError(node.getPos(), "The wrong number of arguments");

        for (int i = 0 ; i < node.getArguments().size() ; i++){
            Expr arg = node.getArguments().get(i);
            Type partype = func.getParameters().get(i).getType();

            arg.accept(this);
            arg.setLvalue(false);

            if (!partype.isSame(arg.getType()))
                throw new SemanticError(arg.getPos(), "The wrong type of the argument");

            checkGlobalRead(arg);
        }
        node.setType(func.getReturnType());
    }

    @Override
    public void visit(Identifier node) throws SemanticError {
        Entity entity = localScope.getEntity(node.getName());

        if (entity == null) throw new SemanticError(node.getPos(), "Undefined symbol");
        node.setEntity(entity);
        node.setType(entity.getType());

        checkGlobalRead(node);

        if (node.getType().isBool() || node.getType().isInt())
            node.all_id = true;

        switch(entity.getType().getType()) {
            case FUNC:
                break;
            case CLASSDEF:
                throw new SemanticError(node.getPos(), "Unexpected Class name");
            default:
                node.setLvalue(true);
                if (((VariableEntity)entity).isGlobal() && upperFunc != null
                        && !upperFunc.getUsedGlobalVar().contains((VariableEntity)entity))
                    upperFunc.addUsedGlobalVar((VariableEntity)entity);
                if (((VariableEntity) entity).isMember()){
                    node.isRef = true;
                    //If the variable is a member clearly it is a reference
                }
        }
    }

    @Override
    public void visit(IntConst node) throws SemanticError {
        node.all_id = true;
        node.setType(PrimitiveType.getPrimitiveType(Type.Types.INT));
    }

    @Override
    public void visit(MemberExpr node) throws SemanticError {

        node.getPrefix().accept(this);

        Expr prefix = (Expr)node.getPrefix();
        node.isRef = true;
        node.getPrefix().setLvalue(false);

        checkGlobalRead(node.getPrefix());


        if (prefix.getType().isClass()) {
            ClassEntity entity = ((ClassType) prefix.getType()).getEntity();
            String membername = node.getName();
            Entity result = entity.getScope().getLocalEntity(membername);
            if (result == null){
                throw new SemanticError(node.getPos(), "No such member.");

            }
            if (!result.getType().isFunc()) {
                node.setLvalue(true);
            }
            node.setType(result.getType());
            node.entity = result;
        }else if (prefix.getType().isString()){
            FunctionEntity entity = BuiltinMethod.stringMethod(node.getName());
            if (entity == null)
                throw new SemanticError(node.getPos(), "No such member.");
            node.setType(new FunctionType(entity));
            node.entity = entity;
        }else if (prefix.getType().isArray()){
            if (node.getName().equals("size")){
                node.setType(new FunctionType(BuiltinMethod.getArraySize()));
                node.entity = BuiltinMethod.getArraySize();}
            else
                throw new SemanticError(node.getPos(), "No such member.");
        }else
            throw new SemanticError(node.getPos(), "unexpected '.'");

    }

    @Override
    public void visit(NewExpr node) throws SemanticError {
        BasicType basictype = localScope.getBasicTypeByName(node.getTypename());
        if (basictype == null)
            throw new SemanticError(node.getPos(), "No such type");
        else if (basictype.isVoid() || basictype.isAssign())
            throw new RuntimeException();
        Type type;
        if (node.getDim() > 0){
            type = new ArrayType(basictype.getInstanceType(), node.getDim());
        }else {

            if (!basictype.isClassDef() && !basictype.isString())
                throw new SemanticError(node.getPos(), "Can't new a primitive type object");


            //type = ((ClassDefType)basictype).getInstanceType();
            //System.out.println(type);
            type = basictype.getInstanceType();
        }

        for (Expr dimexp : node.getDims()){
            dimexp.accept(this);
            if (!dimexp.getType().isInt())
                throw new SemanticError(dimexp.getPos(), "Index must be int");
            checkGlobalRead(dimexp);
        }

        node.setType(type);

    }

    @Override
    public void visit(NullConst node) throws SemanticError {
        node.all_id = true;
        node.setType(PrimitiveType.getPrimitiveType(Type.Types.VOID));
    }

    @Override
    public void visit(StringConst node) throws SemanticError {
        node.all_id = true;
        node.setType(PrimitiveType.getPrimitiveType(Type.Types.STRING));
    }

    @Override
    public void visit(Subscript node) throws SemanticError {
        node.getName().accept(this);
        node.getIndex().accept(this);

        checkGlobalRead(node.getName());
        checkGlobalRead(node.getIndex());

        Type type = node.getName().getType();

        if (type.getDim() == 0)
            throw new SemanticError(node.getPos(), "Array expected");
        if (!node.getIndex().getType().isInt())
            throw new SemanticError(node.getIndex().getPos(), "The index should be int");
        BasicType basictype = node.getName().getType().getBaseType();

        if (type.getDim() == 1)
            node.setType(basictype);
        else
            node.setType(new ArrayType(basictype, type.getDim() - 1));

        node.setLvalue(true);
        node.getName().setLvalue(false);
        node.isRef = true;

        node.getIndex().setLvalue(false);
    }

    @Override
    public void visit(ThisExpr node) throws SemanticError {
        if (upperClass == null)
            throw new SemanticError(node.getPos(), "'this' out of a class");
        node.setType(upperClass.getEntity().getInstanceType());
    }

    @Override
    public void visit(TypeNode node) throws SemanticError {

    }

    @Override
    public void visit(UnaryExpr node) throws SemanticError {

        node.getSubexp().accept(this);

        checkGlobalRead(node.getSubexp());

        Type type = node.getSubexp().getType();
        switch(node.getOp()){
            case LINC: case LDEC: case RINC: case RDEC:
                if (!type.isInt())
                    throw new SemanticError(node.getPos(),"Type Error");
                if (!node.getSubexp().getLvalue())
                    throw new SemanticError(node.getPos(), "Lvalue expected");
                node.setType(PrimitiveType.getInt());
                if (node.getOp() == UnaryExpr.Operand.LINC ||
                node.getOp() == UnaryExpr.Operand.LDEC) {
                    node.setLvalue(true);
                    node.isRef = node.getSubexp().isRef;
                }

                checkGlobalWrite(node.getSubexp());

                break;
            case POS: case NEG: case NOT:
                if (!type.isInt())
                    throw new SemanticError(node.getPos(), "Type Error");
                node.setType(PrimitiveType.getInt());

                node.all_id = node.getSubexp().all_id;

                break;
            case LNOT:
                if (!type.isBool())
                    throw new SemanticError(node.getPos(), "Type Error");
                node.setType(PrimitiveType.getBool());

                node.all_id = node.getSubexp().all_id;

                break;
            default:
                throw new SemanticError(node.getPos(), "Unknown Unary Op");
        }
    }

    @Override
    public void visit(EmptyExpr node) throws SemanticError {
        node.setType(PrimitiveType.getPrimitiveType(Type.Types.VOID));
    }

    @Override
    public void visit(Stmt node) throws SemanticError {
        throw new SemanticError(node.getPos(), "Unknown statement");
    }

    @Override
    public void visit(Block node) throws SemanticError {
        if (node.getScope() == null)
            node.setScope(new Scope(localScope));

        for (Stmt stmt: node.getStatements()){
            localScope = node.getScope();
            stmt.accept(this);
        }
    }

    @Override
    public void visit(VarStmt node) throws SemanticError {
        if (upperFunc == null && upperClass != null)
            //Member, which has been added to the scope in PreSemanticScanner
            return;

        Type type = localScope.getTypeByNode(node.getType()).getInstanceType();
        //check existed
        if (localScope.getLocalEntity(node.getName()) != null)
            throw new SemanticError(node.getPos(),"Multiple definition of a existed entity");

        //check type of initialization
        if (node.getInit() != EmptyExpr.getInstance()){
            node.getInit().accept(this);
            if (!type.isSame(node.getInit().getType()))
                throw new SemanticError(node.getInit().getPos(),
                        "The type of variable initialization conflicts with the declaration");
            node.getInit().setLvalue(false);
            checkGlobalRead(node.getInit());
        }

        VariableEntity newvar;
        if (upperClass == null && upperFunc == null)
            newvar = new VariableEntity(node.getName(), type, VariableEntity.ScopeType.GLOBAL);
        else {
            newvar = new VariableEntity(node.getName(), type, VariableEntity.ScopeType.LOCAL);
        }
        localScope.addVar(newvar);
        node.setVarEntity(newvar);

    }

    @Override
    public void visit(IfStmt node) throws SemanticError {
        node.getCond().accept(this);
        node.getCond().setLvalue(false);
        checkGlobalRead(node.getCond());

        if (!node.getCond().getType().isBool())
            throw new SemanticError(node.getCond().getPos(),"condition must be boolean");

        node.getThen().accept(this);
        node.getOtherwise().accept(this);
    }

    @Override
    public void visit(WhileStmt node) throws SemanticError {
        Stmt old_loop = upperLoop;
        upperLoop = node;

        node.getCond().accept(this);
        node.getCond().setLvalue(false);
        checkGlobalRead(node.getCond());


        if (!node.getCond().getType().isBool())
            throw new SemanticError(node.getCond().getPos(), "condition must be boolean");
        node.getStmt().accept(this);

        upperLoop = old_loop;
    }

    @Override
    public void visit(ForStmt node) throws SemanticError {
        Stmt old_loop = upperLoop;
        upperLoop = node;
        if (node.getInit() != EmptyExpr.getInstance()){
            node.getInit().accept(this);
        }
        node.getInit().setLvalue(false);
        checkGlobalRead(node.getInit());


        if (node.getCond() != EmptyExpr.getInstance()){
            node.getCond().accept(this);
            if (!PrimitiveType.getPrimitiveType(Type.Types.BOOL).isSame(
                node.getCond().getType()))
            throw new SemanticError(node.getCond().getPos(),
                    "The terminate condition must be a boolean expression.");
        }
        node.getCond().setLvalue(false);
        checkGlobalRead(node.getCond());


        if (node.getStep() != EmptyExpr.getInstance())
            node.getStep().accept(this);
        node.getStep().setLvalue(false);
        checkGlobalRead(node.getStep());


        if (node.getStmt() != EmptyExpr.getInstance())
            node.getStmt().accept(this);

        upperLoop = old_loop;
    }

    @Override
    public void visit(ReturnStmt node) throws SemanticError {
        if (upperFunc == null)
            throw new SemanticError(node.getPos(),
                    "'return' out of a function");
        if (upperFunc.getEntity().getReturnType().isVoid() &&
            node.getValue() != EmptyExpr.getInstance()
        )
            throw new SemanticError(node.getPos(),"Can't return a value in a void function");
        node.getValue().accept(this);
        if (!upperFunc.getEntity().getReturnType().isSame(node.getValue().getType()))
            throw new SemanticError(node.getPos(),
                    "The type of return value conflicts with the function declaration."
                    );
        node.getValue().setLvalue(false);
        checkGlobalRead(node.getValue());

    }

    @Override
    public void visit(BreakStmt node) throws SemanticError {
        if (upperLoop == null)
            throw new SemanticError(node.getPos(),
                    "'break' out of a loop");
    }

    @Override
    public void visit(ContinueStmt node) throws SemanticError {
        if (upperLoop == null)
            throw new SemanticError(node.getPos(),
                    "'continue' out of a loop");
    }

    @Override
    public void visit(FunDef node) throws SemanticError {
        localScope = node.getScope();
        upperFunc = node;
        upperLoop = null;
        node.getStmt().accept(this);
    }

    @Override
    public void visit(ClassDef node) throws SemanticError {
        ClassEntity entity = node.getEntity();
        for (ASTNode member : node.getMembers()){
            upperClass = node;
            upperFunc = null;
            upperLoop = null;

            localScope = node.getScope();
            member.accept(this);
        }
        entity.calElementPos();
    }


    @Override
    public void visit(Program node) throws SemanticError {
        globalScope = node.getScope();
        localScope = globalScope;
        Entity mainfun = globalScope.getLocalEntity("main");
        if (mainfun == null || mainfun.getType().getType() != Type.Types.FUNC){
            throw new SemanticError(node.getPos(),"Function main not found");
        }
        if (!((FunctionEntity)mainfun).getReturnType().isInt()){
            throw new SemanticError(node.getPos(), "The return type of main must be int");
        }

        if (((FunctionEntity) mainfun).getParameters().size() > 0)
            throw new SemanticError(node.getPos(), "main has no parameters");

        for (ASTNode decl: node.getDeclarations()){

            upperClass = null;
            upperFunc = null;
            upperLoop = null;
            localScope = globalScope;

            decl.accept(this);
        }
    }

    public static class SyntaxErrorListener extends BaseErrorListener{
        @Override
        public void syntaxError(Recognizer<?, ?> recognizer,
                                Object offendingSymbol, int line, int charPositionInLine,
                                String msg, RecognitionException e) {
            throw new SemanticError(new SourcePos(line, charPositionInLine),
                    "Syntax Error");
        }
    }

    public static void main(String [] args) {
        CharStream input;
        try {
            input = CharStreams.fromFileName("program.txt");
        } catch (Exception e) {
            throw new RuntimeException("cant read file");
        }
        MleseLexer lexer = new MleseLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MleseParser parser = new MleseParser(tokens);

        parser.removeErrorListeners();
        parser.addErrorListener(new SemanticChecker.SyntaxErrorListener());

        ParseTree tree = parser.program();

        // create a standard ANTLR parse tree walker
        ParseTreeWalker walker = new ParseTreeWalker();
        // create listener then feed to walker
        ASTBuilder loader = new ASTBuilder();
        walker.walk(loader, tree);        // walk parse tree
//        ASTPrinter printer = new ASTPrinter( );
//        printer.visit(loader.getRoot());

        PreSemanticScanner prescanner = new PreSemanticScanner();
        prescanner.visit(loader.getRoot());
//        System.out.println("Pre-Semantic Check finished");
        SemanticChecker semanticcheck = new SemanticChecker();
        semanticcheck.visit(loader.getRoot());
//        System.out.println("Semantic Check finished");

    }
}
