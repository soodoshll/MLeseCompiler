package net.qdsu.Mlese.Frontend;

import net.qdsu.Mlese.Backend.GraphAllocator;
import net.qdsu.Mlese.IR.BasicBlock;
import net.qdsu.Mlese.IR.Function;
import net.qdsu.Mlese.IR.IRRoot;
import net.qdsu.Mlese.IR.Instruction.*;

import java.io.PrintStream;
import java.util.*;


/**
 *  The format of the IRPrinter is DOT language, which can be easily transformed into an image by using Graphviz.
 *
 *  graphviz command like this (under PowerShell):
 *     dot -Tsvg .\ir_out.gv -o .\ir.svg ; .\ir.svg
 *
 */
public class IRPrinter{
//  Configurations
    public boolean PRINT_IDOM     = true;
    public boolean PRINT_DF       = false;
    public boolean PRINT_PHI_VARS = false;
    public boolean PRINT_DOM      = false;
    public boolean PRINT_PREV     = false;
    public boolean PRINT_LIVE     = false;
    public boolean PRINT_GRAPH    = false;
    public boolean PRINT_USEDEF   = false;
    public boolean PRINT_RIDOM    = false;

    private IRRoot root;
    private PrintStream out;

    private Set<BasicBlock> visited = new HashSet<BasicBlock>();
    private static class edge{
        public BasicBlock from;
        public BasicBlock to;
        public String hint;
        public edge(BasicBlock from, int index, BasicBlock to) {
            this.from = from;
            this.to = to;
        }
        public edge(BasicBlock from, int index, BasicBlock to, String hint) {
            this.from = from;
            this.to = to;
            this.hint = hint;
        }

    }
    private ArrayList<edge> edges = new ArrayList<>();
    private GraphAllocator graph;
    public IRPrinter(IRRoot root, PrintStream out) {
        this.root = root;
        this.out = out;
    }

    public IRPrinter(IRRoot root, PrintStream out, GraphAllocator graph) {
        this.root = root;
        this.out = out;
        this.graph = graph;
    }

    public Queue<BasicBlock> q = new LinkedList<>();
    public void run() {
        out.println("digraph g {\n" +
                "graph [\n" +
                "rankdir = \"LR\"\n" +
                //"splines = ortho \n"+
                "];\n" +
                "node [\n" +
                "fontsize = \"16\"\n" +
                //"shape = \"ellipse\"\n" +
                "];");

        root.getFunctions().forEach(x -> doFunction(x));
        if (PRINT_GRAPH)
            graph.print(out);
        out.println("}");
    }
    private int fun_index = 0;
    private void doFunction(Function fun){

        edges = new ArrayList<>();

        out.printf("subgraph cluster%d\n{\n", fun_index);
        fun_index++;
        out.printf("label=\"%s\";\n", fun.getName());

        System.err.println(fun.getBbList());

        q.add(fun.getBBStart());
        while (!q.isEmpty()){
            BasicBlock top = q.poll();
            printBB(top);
        }
        for (edge e : edges){
            if (e.hint == null)
                out.printf("\"%s\" -> \"%s\";\n",e.from,e.to);
            else
                out.printf("\"%s\" -> \"%s\" [label = %s];\n",e.from,e.to,e.hint);
        }
        out.println("}");
    }

    private void printBB(BasicBlock bb){
        if (visited.contains(bb)) return;
        else visited.add(bb);
        if (bb == null) return;
        IRInstruction ins = bb.getIrStart();
        out.printf("\"%s\"[\n label=<",bb);
        out.println( bb + " : "+bb.hint+" : "+ bb.func);
        if (PRINT_PHI_VARS)
            out.printf(" | %s \n",bb.phiTable.keySet());
        if (PRINT_DOM)
            out.printf(" | DOM: %s \n", bb.Dom);
        if (PRINT_PREV){
            for (IRInstruction prev : bb.getFrom()){
                out.printf(" | <B>%s</B> \n", prev.getBb());
            }
        }
        if (PRINT_USEDEF){
            out.printf(" | GEN: %s\n",bb.gen);
            out.printf(" | KILL: %s\n",bb.kill);
        }
        if (PRINT_LIVE) {
            out.printf("| GEN: %s \n", bb.gen);
            out.printf("| KILL: %s \n", bb.kill);
            out.printf("| IN: %s \n", bb.livein);
            out.printf("| OUT: %s \n", bb.liveout);
        }
        int index = 1;
        for (;ins != null ; ins = ins.getNext()){
            //if (PRINT_LIVE)
            //    out.printf("| <B>%s</B>  * IN: %s * OUT: %s \n", ins, ins.livein, ins.liveout);
            //else
            if (PRINT_USEDEF)
                out.printf("| %s USE:%s DEF:%s\n ", ins, ins.use, ins.def);
            else
                out.printf("| %s \n ", ins);
            ++index;
            if (ins instanceof Jump){
                q.add(((Jump) ins).getDest());
                edges.add(new edge(bb, index, ((Jump) ins).getDest()));
            }else if (ins instanceof Branch){
                q.add(((Branch) ins).getIfTrue());
                q.add(((Branch) ins).getIfFalse());
                edges.add(new edge(bb, index, ((Branch) ins).getIfTrue(),"true"));
                edges.add(new edge(bb, index, ((Branch) ins).getIfFalse(),"false"));

            }
        }
        out.print("> \nshape = \"record\"\n" +
                "];\n");
        if (PRINT_IDOM)
            if (bb.IDom != null)
                out.printf("\"%s\" -> \"%s\" [color = red \n label=IDom]\n", bb, bb.IDom);

        if (PRINT_RIDOM)
            if (bb.RIDom != null)
                out.printf("\"%s\" -> \"%s\" [color = blue \n label=RIDom]\n", bb, bb.RIDom);

        if (PRINT_DF)
            for (BasicBlock df : bb.DF){
                out.printf("\"%s\" -> \"%s\" [style = dotted \n label=DF]\n", bb, df);
            }


    }



}
