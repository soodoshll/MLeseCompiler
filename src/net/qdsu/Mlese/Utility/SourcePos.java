package net.qdsu.Mlese.Utility;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;

public class SourcePos {
    private int line;
    private int column;
    public SourcePos(int line, int column){
        this.line = line;
        this.column = column;
    }

    public SourcePos(Token token) {
        this.line = token.getLine();
        this.column = token.getCharPositionInLine();
    }

    public SourcePos(ParserRuleContext ctx) {
        this(ctx.start);
    }

    public SourcePos(TerminalNode terminal) {
        this(terminal.getSymbol());
    }

    @Override
    public String toString() {
        return "("+line+":"+column+")";
    }
}
