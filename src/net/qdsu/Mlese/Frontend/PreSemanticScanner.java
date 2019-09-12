package net.qdsu.Mlese.Frontend;

import net.qdsu.Mlese.AST.*;
import net.qdsu.Mlese.Entity.ClassEntity;
import net.qdsu.Mlese.Entity.FunctionEntity;
import net.qdsu.Mlese.Entity.Scope;
import net.qdsu.Mlese.Entity.VariableEntity;
import net.qdsu.Mlese.Type.ClassDefType;
import net.qdsu.Mlese.Type.PrimitiveType;
import net.qdsu.Mlese.Type.Type;
import net.qdsu.Mlese.Utility.SemanticError;


// This class is to add all functions (and methods) to scope, for sake of pre-reference.
public class PreSemanticScanner implements ASTVisitor {
    private Scope globalScope;
    private Scope localScope;
    private ClassEntity upperClass;



    @Override
    public void visit(ASTNode node) {

    }

    @Override
    public void visit(BinaryExpr node) {

    }

    @Override
    public void visit(BoolConst node) {

    }

    @Override
    public void visit(Expr node) {

    }

    @Override
    public void visit(FunCall node) {

    }

    @Override
    public void visit(Identifier node) {

    }

    @Override
    public void visit(IntConst node) {

    }

    @Override
    public void visit(MemberExpr node) {

    }

    @Override
    public void visit(NewExpr node) {

    }

    @Override
    public void visit(NullConst node) {

    }

    @Override
    public void visit(StringConst node) {

    }

    @Override
    public void visit(Subscript node) {

    }

    @Override
    public void visit(ThisExpr node) {

    }

    @Override
    public void visit(TypeNode node) {

    }

    @Override
    public void visit(UnaryExpr node) {

    }

    @Override
    public void visit(EmptyExpr node) {

    }

    @Override
    public void visit(Stmt node) {

    }

    @Override
    public void visit(Block node) {

    }

    @Override
    public void visit(VarStmt node) {
        //Scope scope = node.getScope();
        if (upperClass != null){
            if (node.getInit() != EmptyExpr.getInstance()){
                throw new SemanticError(node.getPos(),
                        "Class member can't be initialized");
            }
            if (localScope.isNameValid(node.getName())){
                Type type = localScope.getTypeByNode(node.getType()).getInstanceType();
                if (type == null)
                    throw new SemanticError(node.getPos(),
                            "No such Type");
                localScope.addVar(new VariableEntity(node.getName(), type, VariableEntity.ScopeType.MEMBER));
            }else{
                throw new SemanticError(node.getPos(), "The name is used");
            }
        }
        //It means it is a member variable
    }

    @Override
    public void visit(IfStmt node) {

    }

    @Override
    public void visit(WhileStmt node) {

    }

    @Override
    public void visit(ForStmt node) {

    }

    @Override
    public void visit(ReturnStmt node) {

    }

    @Override
    public void visit(BreakStmt node) {

    }

    @Override
    public void visit(ContinueStmt node) {

    }

    @Override
    public void visit(FunDef node) {
        //Get the type
        Type return_type;
        if (node.getType() == null){
            //Constructor
            if (!node.getName().equals(upperClass.getName()))
                throw new SemanticError(node.getPos(),
                        "Constructor should have the same name as the class");
            return_type = PrimitiveType.getVoid();
        }else{
            return_type = localScope.getTypeByNode(node.getType()).getInstanceType();
            if (return_type == null){
                throw new SemanticError(node.getPos(),
                        "No such type");
            }
        }

        FunctionEntity new_fun;
        //Add the new function entity to the scope
        if (localScope.isNameValid(node.getName())){
            new_fun = new FunctionEntity(return_type, node.getName(), node.getStmt(), new Scope(localScope));
            if (upperClass != null && node.getType() == null){
                if (!node.getName().equals(upperClass.getName()))
                    throw new RuntimeException();
                if (upperClass.getConstructor() == null )
                    upperClass.setConstructor(new_fun);
                else
                    throw new SemanticError(node.getPos(),
                            "Multiple definition of the constructor");
            }else{
                localScope.addFun(new_fun);
            }
        }else{
            throw new SemanticError(node.getPos(), "The name is used");
        }


        node.setScope(new_fun.getScope());
        node.setEntity(new_fun);

        //Analyze the parameters and add them to the scope
        localScope = new_fun.getScope();
        for (FunDef.parameter par: node.getParameters()){
            if (localScope.isNameValid(par.name)){
                Type type = localScope.getTypeByNode(par.type).getInstanceType();
                if (type == null)
                    throw new SemanticError(
                            node.getPos(),
                            "No such type"
                    );
                VariableEntity parentity = new VariableEntity(par.name, type, VariableEntity.ScopeType.PARAMETER);
                localScope.addVar(parentity);
                node.getEntity().addParameter(parentity);
                par.varEntity = parentity;
            }else
                throw new SemanticError(node.getPos(),
                        "Parameters with a same name");
        }

    }

    @Override
    public void visit(ClassDef node) {
        for (ASTNode member : node.getMembers()){
            upperClass = node.getEntity();
            localScope = node.getScope();
            member.accept(this);
        }
        ((ClassDefType)node.getEntity().getType()).getEntity().calSize();
    }

    @Override
    public void visit(Program node) {
        this.globalScope = node.getScope();
        this.localScope = this.globalScope;
        globalScope.initBuiltinFunc();
        for (ASTNode decl : node.getDeclarations()){
            upperClass = null;
            localScope = globalScope;
            decl.accept(this);
        }
    }
}
