package net.qdsu.Mlese.AST;

public class EmptyExpr extends Expr {
    private static EmptyExpr instance = new EmptyExpr();
    private EmptyExpr(){}

    public static EmptyExpr getInstance() {
        //System.out.println(instance);
        //if (instance == null) instance = new EmptyExpr();
        return instance;
    }


    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }
}
