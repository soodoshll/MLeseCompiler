package net.qdsu.Mlese.AST;

public interface ASTVisitor {
    public void visit(ASTNode node);
    public void visit(BinaryExpr node);
    public void visit(BoolConst node);
    public void visit(Expr node);
    public void visit(FunCall node);
    public void visit(Identifier node);
    public void visit(IntConst node);
    public void visit(MemberExpr node);
    public void visit(NewExpr node);
    public void visit(NullConst node);
    public void visit(StringConst node);
    public void visit(Subscript node);
    public void visit(ThisExpr node);
    public void visit(TypeNode node);
    public void visit(UnaryExpr node);
    public void visit(EmptyExpr node);

    public void visit(Stmt node);
    public void visit(Block node);
    public void visit(VarStmt node);
    public void visit(IfStmt node);
    public void visit(WhileStmt node);
    public void visit(ForStmt node);
    public void visit(ReturnStmt node);
    public void visit(BreakStmt node);
    public void visit(ContinueStmt node);

    public void visit(FunDef node);
    public void visit(ClassDef node);

    public void visit(Program node);
}
