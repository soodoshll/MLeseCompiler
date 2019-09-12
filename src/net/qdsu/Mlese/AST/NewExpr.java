package net.qdsu.Mlese.AST;
import net.qdsu.Mlese.Utility.SourcePos;

import java.util.ArrayList;
public class NewExpr extends Expr {

    private String typename;
    private ArrayList<Expr> dims = new ArrayList<>();
    private int dim;

    public NewExpr(String typename, int dim, SourcePos pos){
        this.typename = typename;
        this.dim = dim;
        this.pos = pos;
    }

    public void addDim(Expr d){
        dims.add(d);
    }

    public String getTypename() {
        return typename;
    }

    public int getDim() {
        return dim;
    }

    public ArrayList<Expr> getDims() {
        return dims;
    }

    @Override
    public String toString() {
        return null;
    }

    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }
}
