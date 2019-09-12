package net.qdsu.Mlese.AST;

import net.qdsu.Mlese.Utility.SourcePos;

import java.util.ArrayList;


public class Program extends ASTNode{
    private ArrayList<ASTNode> declarations = new ArrayList<>();

    public Program(SourcePos pos){
        this.pos = pos;
    }

    public ArrayList<ASTNode> getDeclarations( ) {
        return declarations;
    }

    //public void addDeclaration(ASTNode decl){
    //    declarations.add(decl);
    //}

    private ArrayList<VarStmt> variables = new ArrayList<>();
    private ArrayList<FunDef> functions = new ArrayList<>();
    private ArrayList<ClassDef> classes = new ArrayList<>();

    public void addVar(VarStmt node){
        variables.add(node);
        declarations.add(node);
    }

    public void addFunc(FunDef node){
        functions.add(node);
        declarations.add(node);
        //System.out.println(entities);
    }

    public void addClass(ClassDef node){
        classes.add(node);
        declarations.add(node);
    }

    public ArrayList<FunDef> getFunctions() {
        return functions;
    }

    public ArrayList<VarStmt> getVariables() {
        return variables;
    }

    public ArrayList<ClassDef> getClasses() {
        return classes;
    }

    public String toString(){
        return "";
    }
    @Override
    public void accept(ASTVisitor visitor){
        visitor.visit(this);
    }
}
