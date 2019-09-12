package net.qdsu.Mlese.AST;
import java.util.ArrayList;
public class Block extends Stmt{
    private ArrayList<Stmt> statements = new ArrayList<>();

    public ArrayList<Stmt> getStatements() {
        return statements;
    }

    public void addStmt(Stmt s){
        statements.add(s);
    }

    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }
}
