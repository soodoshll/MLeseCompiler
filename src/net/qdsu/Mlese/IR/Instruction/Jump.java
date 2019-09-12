package net.qdsu.Mlese.IR.Instruction;

import net.qdsu.Mlese.IR.BasicBlock;
import net.qdsu.Mlese.IR.Operand.Register;

import java.util.HashSet;
import java.util.Set;

public class Jump extends IRInstruction {
    private BasicBlock dest;

    public Jump(BasicBlock dest) {
        this.dest = dest;
        //dest.incInDeg();

        dest.addFrom(this);
    }

    public BasicBlock getDest() {
        return dest;
    }

    public void setDest(BasicBlock dest) {
        this.dest = dest;
    }

    @Override
    public void accept(IRVisitor visitor){
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "jump "+dest;
    }

    @Override
    public void updateUseAndDef() {
        use = new HashSet<>();
        def = new HashSet<>();
    }

    @Override
    public Set<IRInstruction> nextToRun() {
        return new HashSet<IRInstruction>(){{add(dest.getIrStart());}};
    }

    @Override
    public Register ssadef() {
        return null;
    }

    @Override
    public HashSet<Register> ssause() {
        return new HashSet<>();
    }
}
