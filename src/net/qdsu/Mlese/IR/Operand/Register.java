package net.qdsu.Mlese.IR.Operand;

import net.qdsu.Mlese.IR.Instruction.IRInstruction;
import net.qdsu.Mlese.IR.Instruction.Move;

import java.util.ArrayList;
import java.util.HashSet;

public abstract class Register extends Operand{
    /* For Graph Coloring */
    public HashSet<Register> edges = new HashSet<>();
    public HashSet<Move> moves = new HashSet<>();

    public IRInstruction ssadef;
    public ArrayList<IRInstruction> usedIns = new ArrayList<>();

    public HashSet<IRInstruction> def;
    public HashSet<IRInstruction> use;

    public abstract int getDegree();
    public boolean moveRelated(){
        return moves.size() >= 1;
    }
}
