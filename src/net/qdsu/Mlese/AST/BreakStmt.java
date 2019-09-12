package net.qdsu.Mlese.AST;

import net.qdsu.Mlese.Utility.SourcePos;

public class BreakStmt extends Stmt{

    public BreakStmt(SourcePos pos){
        this.pos = pos;
    }

    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }
}
