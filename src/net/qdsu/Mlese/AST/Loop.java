package net.qdsu.Mlese.AST;

import net.qdsu.Mlese.IR.BasicBlock;

public abstract class Loop extends Stmt{
    public BasicBlock condBB;
    public BasicBlock afterBB;
    public BasicBlock stepBB;
}
