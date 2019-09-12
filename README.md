# MLeseCompiler

This repo is for my compiler 2019 class.

MLeseCompiler is a toy compiler translating a C and java like language `Mx*` into x86_64 NASM assembly. You can find the document of this language in this directory.

It is not a software out of the box, but a code for my compiler assignment. So this version is specially made for submission.

Due to the limited time(about two months), this compiler seems quite naive, and some of the code is ugly. Unfortunately, I have no plan to refactor it.

The backend part is far more complicated than the frontend. My IR is based on CFG(control flow graph), which can be exported as a DOT file and compiled as an image file. In different stages, the IR is represented by a same java class but has different formats, which I think should have better design and implementation.

All optimization methods are mature and well-known solution, including inlining, SCCP, value numbering and aggressive dead code elimination. Most of them are base on SSA form, which in my opinion is one of the most important techniques in optimization.

This compiler has been deeply affected by the **ACM-class** style. I have learnt a lot from senior students' work. Maybe you can hardly find my personal character. Even though, I think this project is extremely beneficial ,valuable and unforgettable.

In fact, I focus on the basic backend optimization techniques in this compiler class, but there do exist other interesting aspects of compilers, programming language theory and systems. I hope I will have the chance to have a deeper knowledge of it.

## Plan

  * [x] ASTBuilder
  * [x] ASTPrinter
  * [x] Semantic Check
  * [x] IR
  * [x] Optimization
  * [x] Codegen
  
## The Workflow

```
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
    |    Inline     | 
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

```