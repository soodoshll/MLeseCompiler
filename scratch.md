
# Scratch

## Front End

### Stages
  + Pass 1 
  
    * Build AST
    
    * Build Scope
        1. add all class to global "returnType table"
            (class returnType can be declared
            ; class returnType OK, class entity not
            )
            
        2. ? add functions to ... the return value is not determined.
            no. it doesnt make sense.
            
        Until Now, All Types are determined.
  
  + Pass 2 Semantic Check
    * Build Scope
       1. add all functions/methods to scope

    
  + Pass 3  
    * Build Scope
      1. add all variables to scope
    * Type Check
    
    
  * Build IR
    
    
### Java Classes

+ Type - used in returnType check
+ Entity - the member of scopes
+ ASTNode - semantic

### Lvalue
    
  1. Parameters 
  1. Variables
  1. Members of a class
  1. Members of arrays
  
### 关于命名空间的一些思考
其实是不够优雅
    
    class foo{
        foo foo;  //Is it correct??
        foo foo2; //Is it correct??
    }    

### Declaration in a loop
    for (;;) int i;
    if (xxx) int i;
    
### Why IR?

In fact, what I need is SSA(So I can do a lot of optimizations).

I know nothing about the optimizations. Anyway get the SSA first.


### IR Design

Does IR need a type system?
At least, we have to deal with two types: int(include pointer) bool.
If we combine them together, it will be a waste of memory space.

Besides this, everything just follows LLVM IR.

BasicBlock/CFG

The IR program consists of several parts:

    global variables
    functions --- parameters
               |- basicblocks
               
Considering the procedure of calling a function:

Where are the variables come from?

 + Global variables 
 + Class Members
 + Arguments
 + Local variables
 
Upon entering a function:

 1. Parameters?  -> Do nothing.
 1. Class Member? -> GEP
 1. Global Variables -> Load
 
 
Block in the code

### IR is confusing

苟了这么多天之后事情终于变得清晰起来

### GetElementPtr参数设计

其实就是move

    move dest base offset SCALE
    
### 跳转指令和布尔表达式的设计

脑壳疼……

### 关系一塌糊涂

重新设计流程

 - PASS 1. 生成AST，并且把类型声明添加到Scope中，使所有类型准备就绪
 - PASS 2. 将所有函数、方法添加到Scope中，使之支持前向引用
 - PASS 3. 语义检查，主要是类型检查。此时应该确定每个表达式的类型、是否是左值。
 
 左值，有可能是一个对象的成员，这时候应该特殊处理。（添加 ismember 属性？）
 
    ++(a.b)         =     ++(a.c)         +     3;
    ref/lvalue            ref/non-lvalue
    adress of a.b         value of a.c
    no load               load
    
    a               =     d
    non-ref/lvalue        non-ref/non-lvalue
    value                 value
    

### Initialization of array
    
    a[x]    ->    malloc(x)
    a[x][y] ->    
    a = malloc(x);
    for (i=0;i<y;i++) a[i] = malloc(y);
    
    a[x][y][z] ->
    a = malloc(x);
    for (i=0;i<x;i++){
        a[i] = malloc(y);
        b    = a[i];
        for (j=0;j<y;j++)
            b[j] = malloc(z);
        }
        
    n重寻址……好烦
    
## Back End

 - SSA/Optimization 
 - Transform to x86 NASM format
    + arithmetic
    + load/store
    + conditional jump
    
 - Register Alloc
 - end
 
### Plan

 - Liveness Analysis
 - Instruction select
 - Register alloc  