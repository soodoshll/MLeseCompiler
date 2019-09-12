package net.qdsu.Mlese.AST;

import net.qdsu.Mlese.Utility.SourcePos;

public class NullConst extends Expr{
    public NullConst(SourcePos pos){
        this.pos = pos;
    }

    public String toString() {
        return "null";
    }

    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }
}
