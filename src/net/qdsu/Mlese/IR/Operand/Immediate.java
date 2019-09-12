package net.qdsu.Mlese.IR.Operand;

public class Immediate extends Operand {
    private int value;

    public Immediate(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return ""+value;
    }
}
