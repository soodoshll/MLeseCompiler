package net.qdsu.Mlese;

/*

    +---------------+
    |      AST      |
    +------+--------+
           |
           v
    +---------------+                   [Graphviz]
    |   IR (CFG)    +---------> DOT ----------------> svg
    +------+--------+
           |
           v
    +---------------+
    |    Inline     | (Create a new copy of the function(Basic Block, Instruction and Virtual Register) )
    +------+--------+
           |
           v
    +---------------+
    |   SSA Build   +--------> [ Sparse Conditional Constant Propagation ]
    +---------------+                             |
                                                  v
                           [ Value Numbering (Common Subexpression Elimination)]
                                                  |
    +---------------+                             v
    |  SSA Destruct |<-------------[ Aggressive Dead Code Elimination ]
    +------+--------+
           |
           v
    +---------------+
    |  Machine Inst | (Instruction Selection)
    +------+--------+
           |
           v
    +---------------+
    | GraphColoring +-----------> [ Dead Code Elimination (by liveness analysis) ]
    +---------------+                             |
                                                  |
    +---------------+                             |
    |     NASM      |<----------------------------+
    +---------------+


    ### Some Problems
        + Prolog and Epilog : store/load all callee-save registers
        + No memory address operand

 */

import net.qdsu.Mlese.Backend.GraphAllocator;
import net.qdsu.Mlese.Backend.LivenessAnalysis;
import net.qdsu.Mlese.Backend.NASMPrinter;
import net.qdsu.Mlese.Backend.SSATransform;
import net.qdsu.Mlese.Frontend.*;
import net.qdsu.Mlese.IR.BasicBlock;
import net.qdsu.Mlese.IR.Operand.PhysicalRegister;
import net.qdsu.Mlese.Optim.*;
import net.qdsu.Mlese.Parser.MleseLexer;
import net.qdsu.Mlese.Parser.MleseParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class Main {
    public static void main(String [] args) {
        CharStream input;
        try {
            if (args.length > 0)
                input = CharStreams.fromFileName(args[0]);
            else
                input = CharStreams.fromFileName(
                        "program.txt");
        } catch (Exception e) {
            throw new RuntimeException("cant read file");
        }
        MleseLexer lexer = new MleseLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MleseParser parser = new MleseParser(tokens);

        parser.removeErrorListeners();
        parser.addErrorListener(new SemanticChecker.SyntaxErrorListener());

        ParseTree tree = parser.program();

        // create a standard ANTLR parse tree walker
        ParseTreeWalker walker = new ParseTreeWalker();
        // create listener then feed to walker
        ASTBuilder loader = new ASTBuilder();
        walker.walk(loader, tree);        // walk parse tree

//        ASTPrinter printer = new ASTPrinter();

        PreSemanticScanner prescanner = new PreSemanticScanner();
        prescanner.visit(loader.getRoot());
        SemanticChecker semanticcheck = new SemanticChecker();
        semanticcheck.visit(loader.getRoot());
        IRBuilder irBuilder = new IRBuilder(loader.getRoot());
        irBuilder.visit(loader.getRoot());


        SSATransform ssa = new SSATransform(irBuilder.getRoot());


        PhysicalRegister.initRegs();

        Inlining inline = new Inlining(irBuilder.getRoot());
        inline.run();






        ssa.build();

        ConstantPropagation cp = new ConstantPropagation(irBuilder.getRoot());
        cp.run();

        DCE dce = new DCE();
        NaiveDCE ndce = new NaiveDCE(irBuilder.getRoot());


        for (int i = 0 ; i < 10 ; i++) {
            ValueNumbering vn = new ValueNumbering(irBuilder.getRoot());
            vn.run();
            irBuilder.getRoot().getFunctions().forEach(x -> dce.runFunc(x));
            ndce.uselessPar();
        }


        irBuilder.getRoot().getFunctions().forEach(x -> dce.runFunc(x));

        ssa.destruct();

//        File ir_out = new File("ir_out.gv");
//        IRPrinter irPrinter;
//        PrintStream out_stream;
//        try {
//            out_stream = new PrintStream(ir_out);
//            irPrinter = new IRPrinter(irBuilder.getRoot(), out_stream);
//        }catch (FileNotFoundException e){
//            throw new RuntimeException("file not open");
//        }
//        irPrinter.run();

//        System.out.println("============ Start Running ============");
//        IRInterpreter irint = new IRInterpreter(irBuilder.getRoot());
//        System.out.println(irBuilder.getRoot());
//        irint.run();
//        System.out.println("============ Finished ============");
////////


        ssa.afterSSA();




        GraphAllocator graph_allocator = new GraphAllocator(irBuilder.getRoot());
        graph_allocator.run();



        boolean TO_FILE = false;


//        ir_out = new File("ir_out_nasm.gv");
//
//        try {
//            out_stream = new PrintStream(ir_out);
//            irPrinter = new IRPrinter(irBuilder.getRoot(), out_stream);
//        }catch (FileNotFoundException e){
//            throw new RuntimeException("file not open");
//        }
//        irPrinter.run();

        NASMPrinter nasmPrinter;
        if (TO_FILE){
            try {
                File nasm_out_file = new File("test.asm");
                PrintStream nasm_out = new PrintStream(nasm_out_file);
                nasmPrinter = new NASMPrinter(irBuilder.getRoot(), nasm_out);
            }catch (FileNotFoundException e){
                throw new RuntimeException("file not open");
            }
        }else {
            nasmPrinter = new NASMPrinter(irBuilder.getRoot(), System.out);
        }
        nasmPrinter.run();




    }
}
