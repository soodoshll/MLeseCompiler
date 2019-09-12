package net.qdsu.Mlese.AST;

import net.qdsu.Mlese.Entity.VariableEntity;
import net.qdsu.Mlese.Utility.SourcePos;

public class VarStmt extends Stmt {
    private TypeNode type;
    private String name;
    private Expr init;

    public VarStmt(TypeNode type, String name, Expr init, SourcePos pos){
        this.type = type;
        this.name = name;
        this.init = (init == null) ? EmptyExpr.getInstance() : init;
        //System.out.println("??"+EmptyExpr.getInstance());
        this.pos = pos;
    }

    public String getName() {
        return name;
    }

    public Expr getInit() {
        return init;
    }

    public TypeNode getType() {
        return type;
    }

    private VariableEntity varEntity;

    public void setVarEntity(VariableEntity varEntity) {
        this.varEntity = varEntity;
    }

    public VariableEntity getVarEntity() {
        return varEntity;
    }

    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }
}
