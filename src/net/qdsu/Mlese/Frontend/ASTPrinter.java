package net.qdsu.Mlese.Frontend;
import net.qdsu.Mlese.AST.*;
import java.io.PrintStream;


/**
 * Print the AST
 *
 * @author Qidong Su
 */
public class ASTPrinter implements ASTVisitor{
    private PrintStream out;
    private StringBuilder ind = new StringBuilder();
    public ASTPrinter(){
        this.out = System.out;
    }
    public ASTPrinter(PrintStream out){
        this.out = out;
    }
    private void indent(){
        ind.append("  ");
    }
    private void dedent(){
        ind.delete(ind.length()-2, ind.length());
    }

    @Override
    public void visit(ASTNode node) {
        out.printf("%sundefined node!  %s\n", ind, node.getPos());
    }

    @Override
    public void visit(BinaryExpr node) {
        out.printf("%s@ BinaryExpression %s  %s\n", ind, node.getOp(), node.getPos());
        indent();
        node.getLeft().accept(this);
        node.getRight().accept(this);
        dedent();
    }

    @Override
    public void visit(BoolConst node) {
        out.printf("%s@ Boolean %s  %s\n", ind, node.getValue(), node.getPos());
    }

    @Override
    public void visit(Expr node) {
        out.printf("%sundefined expression!  %s\n", ind, node.getPos());
    }

    @Override
    public void visit(FunCall node) {
        out.printf("%s@ FunctionCall  %s\n", ind, node.getPos());
        out.printf("%s>> FunctionName\n", ind);
        indent();
        node.getName().accept(this);
        dedent();
        out.printf("%s>> Arguments\n", ind);
        indent();
        for (Expr x : node.getArguments()){
            x.accept(this);
        }
        dedent();
    }

    @Override
    public void visit(Identifier node) {
        out.printf("%s@ Identifier %s  %s\n", ind, node.getName(), node.getPos());
    }

    @Override
    public void visit(IntConst node) {
        out.printf("%s@ Integer %d  %s\n", ind, node.getValue(), node.getPos());
    }

    @Override
    public void visit(MemberExpr node) {
        out.printf("%s@ GetMember %s  %s\n", ind, node.getName(), node.getPos());
        indent();
        node.getPrefix().accept(this);
        dedent();
    }

    @Override
    public void visit(NewExpr node) {
        out.printf("%s@ NewExpression %s - %dD  %s\n", ind,
                node.getTypename(),
                node.getDim(),
                node.getPos());
        if (node.getDim() > 0) {
            out.printf("%s>> Known Dims\n", ind);
            indent();
            for (Expr d : node.getDims()) {
                d.accept(this);
            }
            dedent();
        }
    }

    @Override
    public void visit(NullConst node) {
        out.printf("%s@ NullLiteral  %s\n", ind, node.getPos());
    }



    @Override
    public void visit(StringConst node) {
        out.printf("%s@ StringConstant %s  %s\n", ind, node.getValue(), node.getPos());
    }

    @Override
    public void visit(Subscript node) {
        out.printf("%s@ Subscript  %s\n", ind, node.getPos());
        out.printf("%s>> ArrayName:\n", ind);
        indent();
        node.getName().accept(this);
        dedent();
        out.printf("%s>> Index:\n", ind);
        indent();
        node.getIndex().accept(this);
        dedent();

    }

    @Override
    public void visit(ThisExpr node) {
        out.printf("%s@ This  %s:\n", ind, node.getPos());
    }

    @Override
    public void visit(TypeNode node) {
        out.printf("%s@ Type: %s - %dD  %s\n",
                ind, node.getTypename(), node.getDim(),
                node.getPos());
    }

    @Override
    public void visit(UnaryExpr node) {
        out.printf("%s@ UnaryExpression %s  %s\n",
                ind, node.getOp(), node.getPos());
        indent();
        node.getSubexp().accept(this);
        dedent();
    }

    @Override
    public void visit(EmptyExpr node) {
        out.printf("%s@ EmptyExpression \n", ind);
    }

    @Override
    public void visit(Stmt node) {
        out.printf("%s@ Unknown Statement  %s\n", ind, node.getPos());
    }

    @Override
    public void visit(Block node) {
        out.printf("%s@ Block \n", ind);
        indent();
        for (Stmt stmt : node.getStatements()){
            stmt.accept(this);
        }
        dedent();
    }

    @Override
    public void visit(VarStmt node) {
        out.printf("%s@ Variable Declaration - %s  %s\n",
                ind, node.getName(), node.getPos());
        out.printf("%s>> Type:\n", ind);
        indent();
        node.getType().accept(this);
        dedent();
        out.printf("%s>> Init:\n", ind);
        indent();
        node.getInit().accept(this);
        dedent();
    }

    @Override
    public void visit(IfStmt node) {
        out.printf("%s@ IfStatement  %s\n", ind, node.getPos());
        out.printf("%s>> Condition:\n", ind);
        indent();
        node.getCond().accept(this);
        dedent();
        out.printf("%s>> Then:\n", ind);
        indent();
        node.getThen().accept(this);
        dedent();
        out.printf("%s>> Else:\n", ind);
        indent();
        node.getOtherwise().accept(this);
        dedent();
    }

    @Override
    public void visit(WhileStmt node) {
        out.printf("%s@ WhileStatement  %s\n", ind, node.getPos());
        out.printf("%s>> Condition:\n", ind);
        indent();
        node.getCond().accept(this);
        dedent();
        out.printf("%s>> Statement:\n", ind);
        indent();
        node.getStmt().accept(this);
        dedent();
    }

    @Override
    public void visit(ForStmt node) {
        out.printf("%s@ ForStatement  %s\n", ind, node.getPos());
        out.printf("%s>> Initialization:\n", ind);
        indent();
        node.getInit().accept(this);
        dedent();
        out.printf("%s>> Condition:\n", ind);
        indent();
        node.getCond().accept(this);
        dedent();
        out.printf("%s>> IncrementStep:\n", ind);
        indent();
        node.getStep().accept(this);
        dedent();
        out.printf("%s>> Statement:\n", ind);
        indent();
        node.getStmt().accept(this);
        dedent();
    }

    @Override
    public void visit(ReturnStmt node) {
        out.printf("%s@ ReturnStatement  %s\n", ind, node.getPos());
        indent();
        node.getValue().accept(this);
        dedent();
    }

    @Override
    public void visit(BreakStmt node) {
        out.printf("%s@ BreakStatement  %s\n", ind, node.getPos());
    }

    @Override
    public void visit(ContinueStmt node) {
        out.printf("%s@ ContinueStatement %s\n", ind, node.getPos());
    }

    @Override
    public void visit(FunDef node) {
        out.printf("%s@ FunctionDefinition %s  %s\n",
                ind, node.getName(), node.getPos());
        if (node.getType() == null){
            out.printf("%s>> Creator\n", ind);
        }else {
            out.printf("%s>> Return type:\n", ind);
            indent();
            node.getType().accept(this);
            dedent();
        }
        for (FunDef.parameter par: node.getParameters()) {
            out.printf("%s>> Parameter %s Type:\n", ind, par.name);
            indent();
            par.type.accept(this);
            dedent();
        }
        out.printf("%s>> Statement: \n", ind);
        indent();
        node.getStmt().accept(this);
        dedent();

    }

    @Override
    public void visit(ClassDef node) {
        out.printf("%s@ Class Definition: %s  %s\n",
                ind, node.getName(), node.getPos());
        indent();
        for (ASTNode var: node.getMembers()){
            var.accept(this);
        }
        dedent();

    }

    @Override
    public void visit(Program node) {
        out.printf("%s@ Program\n", ind);
        indent();
        for (ASTNode var: node.getDeclarations()){
            var.accept(this);
        }
        dedent();
    }
}
