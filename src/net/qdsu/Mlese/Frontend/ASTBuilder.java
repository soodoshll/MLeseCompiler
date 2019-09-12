package net.qdsu.Mlese.Frontend;

import net.qdsu.Mlese.AST.*;
import net.qdsu.Mlese.Entity.ClassEntity;
import net.qdsu.Mlese.Entity.Scope;
import net.qdsu.Mlese.Parser.*;


import net.qdsu.Mlese.Utility.SemanticError;
import net.qdsu.Mlese.Utility.SourcePos;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

/**
 * A class used to
 *   1. Build AST of the program.
 *   2. Add all classes to the global scope, so all types are defined.
 *   3. Create a scope for each class.
 *
 * @author Qidong Su
 */

public class ASTBuilder extends MleseBaseListener {
    private Program program;
    private ParseTreeProperty<ASTNode> nodes = new ParseTreeProperty<>();
    private Scope globalScope = new Scope();

    public Program getRoot(){
        return program;
    }

    public Scope getGlobalScope() {
        return globalScope;
    }


    @Override
    public void enterProgram(MleseParser.ProgramContext ctx) {
        program = new Program(new SourcePos(ctx.getStart()));
        program.setScope(globalScope);
    }

    @Override
    public void exitProgram(MleseParser.ProgramContext ctx) {
        for (MleseParser.ProgramSectionContext decl: ctx.programSection()){
            ASTNode declnode = nodes.get(decl);
            if (declnode instanceof VarStmt)
                program.addVar((VarStmt)declnode);
            else if(declnode instanceof FunDef)
                program.addFunc((FunDef)declnode);
            else if(declnode instanceof ClassDef)
                program.addClass((ClassDef)declnode);
            else
                throw new RuntimeException("Unknown declaration type");
        }
    }


    @Override
    public void exitEmptyStmt(MleseParser.EmptyStmtContext ctx) {
        nodes.put(ctx, EmptyExpr.getInstance());
    }

    @Override
    public void exitBasicTypeSp(MleseParser.BasicTypeSpContext ctx) {
        TypeNode node = new TypeNode(ctx.getText(), new SourcePos(ctx));
        nodes.put(ctx, node);
    }

    @Override
    public void exitArrayTypeSp(MleseParser.ArrayTypeSpContext ctx) {
        TypeNode basetype = (TypeNode)nodes.get(ctx.typeSpecifier());
        TypeNode node = TypeNode.array(basetype, new SourcePos(ctx));
        nodes.put(ctx, node);
    }

    @Override
    public void exitMemberExp(MleseParser.MemberExpContext ctx) {
        Expr prefix = (Expr)nodes.get(ctx.expression());
        MemberExpr node = new MemberExpr(prefix, ctx.ID().getText(), new SourcePos(ctx));
        nodes.put(ctx, node);
    }

    @Override
    public void exitFuncallExp(MleseParser.FuncallExpContext ctx) {
        Expr fun_name = (Expr)nodes.get(ctx.expression());
        FunCall node = new FunCall(fun_name, new SourcePos(ctx));
        if (ctx.argumentList() != null){
            for ( MleseParser.ExpressionContext arg : ctx.argumentList().expression()){
                node.addArgument((Expr)nodes.get(arg));
            }
        }
        nodes.put(ctx, node);
    }

    @Override
    public void exitSubscriptExp(MleseParser.SubscriptExpContext ctx) {
        Expr name = (Expr)nodes.get(ctx.expression(0));
        Expr index = (Expr)nodes.get(ctx.expression(1));
        Subscript node = new Subscript(name, index, new SourcePos(ctx));
        nodes.put(ctx, node);
    }

    @Override
    public void enterWrongCreator(MleseParser.WrongCreatorContext ctx) {
        throw new SemanticError(new SourcePos(ctx.start),
                "Wrong creator format");
    }

    @Override
    public void exitNewExp(MleseParser.NewExpContext ctx) {

        String typename = ctx.creator().basicTypeSpecifier().getText();
        int dim = ctx.creator().LBRACK().size();
        NewExpr node = new NewExpr(typename, dim, new SourcePos(ctx));
        for (MleseParser.ExpressionContext d : ctx.creator().expression()) {
            node.addDim((Expr)nodes.get(d));
        }
        nodes.put(ctx, node);
    }

    @Override
    public void exitIdExp(MleseParser.IdExpContext ctx) {
        Identifier node = new Identifier(ctx.ID().getText(), new SourcePos(ctx));
        nodes.put(ctx, node);
    }

    @Override
    public void exitBoolConst(MleseParser.BoolConstContext ctx) {
        BoolConst node = new BoolConst(ctx.type.getType() == MleseParser.TRUE,
                                        new SourcePos(ctx));
        nodes.put(ctx, node);
    }

    @Override
    public void exitIntConst(MleseParser.IntConstContext ctx) {
        IntConst node = new IntConst(Integer.parseInt(ctx.INTCONST().getText()),
                new SourcePos(ctx));
        nodes.put(ctx, node);
    }

    @Override
    public void exitStringConst(MleseParser.StringConstContext ctx) {
        String value = ctx.STRINGCONST().getText();
        value = value.substring(1,value.length()-1);

        // Deal with escape characters
        value = value.replace("\\n","\n");
        value = value.replace("\\\"","\"");
        value = value.replace("\\\\","\\");

        StringConst node = new StringConst(value, new SourcePos(ctx));

        nodes.put(ctx, node);
    }

    @Override
    public void exitNullConst(MleseParser.NullConstContext ctx) {
        NullConst node = new NullConst(new SourcePos(ctx));
        nodes.put(ctx, node);
    }

    @Override
    public void exitConstExp(MleseParser.ConstExpContext ctx) {
        nodes.put(ctx, nodes.get(ctx.constant()));
    }

    @Override
    public void exitSubExp(MleseParser.SubExpContext ctx) {
        nodes.put(ctx, nodes.get(ctx.expression()));
    }

    @Override
    public void exitThisExp(MleseParser.ThisExpContext ctx) {
        ThisExpr node = new ThisExpr(new SourcePos(ctx));
        nodes.put(ctx, node);
    }

    @Override
    public void exitPrefixExpr(MleseParser.PrefixExprContext ctx) {
        UnaryExpr.Operand op;
        switch (ctx.op.getType()){
            case MleseParser.PLUSPLUS:      op = UnaryExpr.Operand.LINC; break;
            case MleseParser.MINUSMINUS:    op = UnaryExpr.Operand.LDEC; break;
            case MleseParser.PLUS:          op = UnaryExpr.Operand.POS; break;
            case MleseParser.MINUS:         op = UnaryExpr.Operand.NEG; break;
            case MleseParser.NOT:           op = UnaryExpr.Operand.LNOT;break;
            case MleseParser.TILDE:         op = UnaryExpr.Operand.NOT; break;
            default:                        op = UnaryExpr.Operand.ERR;
        }
        UnaryExpr node = new UnaryExpr(op, (Expr)nodes.get(ctx.expression()),
                new SourcePos(ctx));
        nodes.put(ctx, node);
    }

    @Override
    public void exitSuffixExpr(MleseParser.SuffixExprContext ctx) {
        UnaryExpr.Operand op;
        switch (ctx.op.getType()){
            case MleseParser.PLUSPLUS:      op = UnaryExpr.Operand.RINC; break;
            case MleseParser.MINUSMINUS:    op = UnaryExpr.Operand.RDEC; break;
            default:                        op = UnaryExpr.Operand.ERR;
        }
        UnaryExpr node = new UnaryExpr(op, (Expr)nodes.get(ctx.expression()),
                new SourcePos(ctx));
        nodes.put(ctx, node);
    }

    @Override
    public void exitBinaryExp(MleseParser.BinaryExpContext ctx) {
        BinaryExpr node;
        BinaryExpr.Op op;
        switch (ctx.op.getType()){
            case MleseParser.PLUS:      op = BinaryExpr.Op.ADD;   break;
            case MleseParser.MINUS:     op = BinaryExpr.Op.SUB;   break;
            case MleseParser.STAR:      op = BinaryExpr.Op.MUL;   break;
            case MleseParser.DIV:       op = BinaryExpr.Op.DIV;   break;
            case MleseParser.MOD:       op = BinaryExpr.Op.MOD;   break;
            case MleseParser.LT:        op = BinaryExpr.Op.LT;    break;
            case MleseParser.GT:        op = BinaryExpr.Op.GT;    break;
            case MleseParser.EQ:        op = BinaryExpr.Op.EQ;    break;
            case MleseParser.NE:        op = BinaryExpr.Op.NE;    break;
            case MleseParser.GE:        op = BinaryExpr.Op.GE;    break;
            case MleseParser.LE:        op = BinaryExpr.Op.LE;    break;
            case MleseParser.ANDAND:    op = BinaryExpr.Op.LAND;  break;
            case MleseParser.OROR:      op = BinaryExpr.Op.LOR;   break;
            case MleseParser.LSHIFT:    op = BinaryExpr.Op.SHL;   break;
            case MleseParser.RSHIFT:    op = BinaryExpr.Op.SHR;   break;
            case MleseParser.OR:        op = BinaryExpr.Op.OR;    break;
            case MleseParser.CARET:     op = BinaryExpr.Op.XOR;   break;
            case MleseParser.AND:       op = BinaryExpr.Op.AND;   break;
            case MleseParser.ASSIGN:    op = BinaryExpr.Op.ASSIGN;break;
            default:                    op = BinaryExpr.Op.ERR;
        }
        node = new BinaryExpr(op,
                (Expr)nodes.get(ctx.expression(0)),
                (Expr)nodes.get(ctx.expression(1)),
                new SourcePos(ctx));
        nodes.put(ctx, node);
    }

    @Override
    public void exitBlock(MleseParser.BlockContext ctx) {
        Block node = new Block();
        for (MleseParser.StatementContext stmt: ctx.statement()){
            node.addStmt((Stmt)nodes.get(stmt));
        }
        nodes.put(ctx, node);
    }

    @Override
    public void exitVarStatement(MleseParser.VarStatementContext ctx) {
        String   name = ctx.varDeclaration().ID().getText();
        TypeNode type = (TypeNode)nodes.get(ctx.varDeclaration().typeSpecifier());
        Expr     init = (Expr)nodes.get(ctx.expression());
        VarStmt  node = new VarStmt(type, name, init, new SourcePos(ctx));
        if (type.getTypename().equals("void"))
            throw new SemanticError(type.getPos(), "Can't declare a void variable");
        nodes.put(ctx,node);
    }

    @Override
    public void exitBlockStmt(MleseParser.BlockStmtContext ctx) {
        nodes.put(ctx, nodes.get(ctx.block()));
    }

    @Override
    public void exitVarStmt(MleseParser.VarStmtContext ctx) {
        nodes.put(ctx, nodes.get(ctx.varStatement()));
    }

    @Override
    public void exitIfStmt(MleseParser.IfStmtContext ctx) {
        Expr cond  = (Expr)nodes.get(ctx.expression());
        Stmt then  = (Stmt)nodes.get(ctx.nonDeclStatement(0));
        Stmt otherwise  = (Stmt)nodes.get(ctx.nonDeclStatement(1));
        IfStmt node = new IfStmt(cond, then, otherwise, new SourcePos(ctx));
        nodes.put(ctx, node);
    }

    @Override
    public void exitExpStmt(MleseParser.ExpStmtContext ctx) {
        nodes.put(ctx, nodes.get(ctx.expression()));
    }

    @Override
    public void exitWhileStmt(MleseParser.WhileStmtContext ctx) {
        Expr cond = (Expr)nodes.get(ctx.expression());
        Stmt stmt = (Stmt)nodes.get(ctx.nonDeclStatement());
        WhileStmt node = new WhileStmt(cond, stmt, new SourcePos(ctx));
        nodes.put(ctx, node);
    }

    @Override
    public void exitForStmt(MleseParser.ForStmtContext ctx) {
        Expr init = (Expr)nodes.get(ctx.init);
        Expr cond = (Expr)nodes.get(ctx.cond);
        Expr step = (Expr)nodes.get(ctx.step);
        Stmt stmt = (Stmt)nodes.get(ctx.nonDeclStatement());
        nodes.put(ctx, new ForStmt(init, cond, step, stmt, new SourcePos(ctx)));
    }

    @Override
    public void exitReturnStmt(MleseParser.ReturnStmtContext ctx) {
        Expr value = (Expr)nodes.get(ctx.expression());
        if (value == null) value = EmptyExpr.getInstance();
        nodes.put(ctx, new ReturnStmt(value, new SourcePos(ctx)));
    }

    @Override
    public void exitBreakStmt(MleseParser.BreakStmtContext ctx) {
        nodes.put(ctx, new BreakStmt(new SourcePos(ctx)));
    }

    @Override
    public void exitContinueStmt(MleseParser.ContinueStmtContext ctx) {
        nodes.put(ctx, new ContinueStmt(new SourcePos(ctx)));
    }

    private void addParameter(FunDef node, MleseParser.ParameterListContext ctx){
        for (MleseParser.VarDeclarationContext par :
                ctx.varDeclaration()){
            node.addParameter(
                    (TypeNode)nodes.get(par.typeSpecifier()),
                    par.ID().getText()
            );
        }
    }



    @Override
    public void exitNonVarStmt(MleseParser.NonVarStmtContext ctx) {
        nodes.put(ctx,nodes.get(ctx.nonDeclStatement()));
    }

    @Override
    public void exitFunctionDefinition(MleseParser.FunctionDefinitionContext ctx) {
        TypeNode type = (TypeNode)nodes.get(ctx.typeSpecifier());
        String   name = ctx.ID().getText();
        Stmt     stmt = (Stmt)nodes.get(ctx.block());
        FunDef   node = new FunDef(type, name, stmt, new SourcePos(ctx));
        if (ctx.parameterList() != null){
            addParameter(node, ctx.parameterList());
        }
        nodes.put(ctx, node);
    }

    @Override
    public void exitConstructorDefinition(MleseParser.ConstructorDefinitionContext ctx) {
        String   name = ctx.ID().getText();
        Stmt     stmt = (Stmt)nodes.get(ctx.block());
        FunDef   node = new FunDef(null, name, stmt, new SourcePos(ctx));
        if (ctx.parameterList() != null){
            throw new SemanticError(node.getPos(), "Constructor may not have parameters");
        }
        nodes.put(ctx, node);
    }

    @Override
    public void enterClassDefinition(MleseParser.ClassDefinitionContext ctx) {
        ClassDef node = new ClassDef(new SourcePos(ctx));
        nodes.put(ctx, node);
    }

    @Override
    public void exitClassDefinition(MleseParser.ClassDefinitionContext ctx) throws SemanticError{
        String name = ctx.ID().getText();
        ClassDef node = (ClassDef)(nodes.get(ctx));
        node.setName(name);

        //Check whether the class already exists

        if (!globalScope.isNameValid(name)){
            throw new SemanticError(node.getPos(), "Multiple Class Definitions");
        }

        //Create a new class entity and bind it to the node
        ClassEntity newclass = new ClassEntity(name, new Scope(globalScope));
        node.setScope(newclass.getScope());
        node.setEntity(newclass);

        //Add the class to scope
        globalScope.addClass(newclass);
        for (MleseParser.MemberDeclarationContext member : ctx.memberDeclaration()){
            node.addMember(nodes.get(member));
        }

    }

    @Override
    public void exitMemberDeclaration(MleseParser.MemberDeclarationContext ctx) {
        if (ctx.varStatement() != null){
            nodes.put(ctx, nodes.get(ctx.varStatement()));
        }else if (ctx.functionDefinition() != null){
            nodes.put(ctx, nodes.get(ctx.functionDefinition()));
        }else if (ctx.constructorDefinition() != null){
            nodes.put(ctx, nodes.get(ctx.constructorDefinition()));
        }else{
            throw new RuntimeException("Unknown member declaration");
        }
    }

    @Override
    public void exitProgramSection(MleseParser.ProgramSectionContext ctx) {
        if (ctx.varStatement() != null){
            nodes.put(ctx, nodes.get(ctx.varStatement()));
        }else if (ctx.functionDefinition() != null){
            nodes.put(ctx, nodes.get(ctx.functionDefinition()));
        }else if (ctx.classDefinition() != null){
            nodes.put(ctx, nodes.get(ctx.classDefinition()));
        }else {
            throw new RuntimeException("Unknown declaration");
        }
    }
}
