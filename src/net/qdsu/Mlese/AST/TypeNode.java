package net.qdsu.Mlese.AST;

//import net.qdsu.Mlese.Type.Type;

import net.qdsu.Mlese.Type.Type;
import net.qdsu.Mlese.Utility.SourcePos;

public class TypeNode extends ASTNode {

    private Type type;
    private String typename;
    private int dim;

    public TypeNode(String typename, SourcePos pos) {
        this.typename = typename;
        this.dim = 0 ;
        this.pos = pos;
    }

    public TypeNode(String typename, int dim, SourcePos pos) {
        this.typename = typename;
        this.dim = dim;
        this.pos = pos;
    }

    public int getDim() {
        return dim;
    }

    public String getTypename() {
        return typename;
    }

    public static TypeNode array(TypeNode base, SourcePos pos){
        TypeNode newnode = new TypeNode(base.getTypename(), base.getDim() + 1, pos);
        return newnode;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
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
