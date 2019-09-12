package net.qdsu.Mlese.AST;

import net.qdsu.Mlese.Utility.SourcePos;

public class ContinueStmt extends Stmt {

    public ContinueStmt(SourcePos pos){
        this.pos = pos;
    }

    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }
}
