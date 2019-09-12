package net.qdsu.Mlese.IR.Operand;

import java.util.ArrayList;

public class StaticString extends Operand{
    private String value;

    private static int cnt = 0;

    //private static ArrayList<StaticString> strings = new ArrayList<>();

    private int id;

    public StaticString(String value) {
        this.value = value;
        this.id = cnt++;
        //strings.add(this);
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "str"+id;
    }
}
