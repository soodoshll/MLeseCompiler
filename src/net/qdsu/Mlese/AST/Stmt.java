package net.qdsu.Mlese.AST;

public class Stmt extends ASTNode {
    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }
}
