package net.qdsu.Mlese.IR.Instruction;

public interface IRVisitor {
    public void visit(IRInstruction inst);
    public void visit(BinaryOp inst);
    public void visit(Branch inst);
    public void visit(Call inst);
    public void visit(IntCmp inst);
    public void visit(Jump inst);
    public void visit(Load inst);
    public void visit(Malloc inst);
    public void visit(Move inst);
    public void visit(Store inst);
    public void visit(UnaryOp inst);
    public void visit(Return inst);

    public void visit(Pop inst);
    public void visit(Push inst);
    public void visit(Phi inst);
    public void visit(Lea inst);

}
