package net.qdsu.Mlese.Utility;



public class SemanticError extends Error{
    private SourcePos pos;
    private String    msg;
    public SemanticError(SourcePos pos, String msg){
        this.pos = pos;
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return "Error : "+ msg + pos;
    }
}
