grammar Mlese;

basicTypeSpecifier
    : returnType = BOOL
    | returnType = INT
    | returnType = VOID
    | returnType = STRING
    | returnType = ID
    ;

typeSpecifier
    : typeSpecifier (LBRACK RBRACK)    #arrayTypeSp
    | basicTypeSpecifier    #basicTypeSp
    ;

argumentList
    : expression (',' expression)*;

parameterList
    : varDeclaration (',' varDeclaration)*;




creator
    : wrongCreator
    | basicTypeSpecifier '(' ')'
    | basicTypeSpecifier (LBRACK expression RBRACK)+ (LBRACK RBRACK)*
    | basicTypeSpecifier
    ;

wrongCreator
    :   basicTypeSpecifier (LBRACK expression RBRACK)* (LBRACK RBRACK)+ (LBRACK expression RBRACK)+
    ;

expression
    : expression op=('++'|'--')                             #suffixExpr
    | <assoc=right>'new' creator                            #newExp
    | expression '.' ID                                     #memberExp

    | expression '(' argumentList?')'                       #funcallExp

    | expression '[' expression ']'                         #subscriptExp

    | <assoc=right>op=('++'|'--') expression                #prefixExpr
    | <assoc=right>op=('+' | '-') expression                #prefixExpr
    | <assoc=right>op=('!' | '~') expression                #prefixExpr


//    | expression op
    | expression op=('*'|'/'|'%') expression                #binaryExp
    | expression op=('+'|'-') expression                    #binaryExp
    | expression op=('<<'|'>>') expression                  #binaryExp
    | expression op=('<' | '<=' | '>' | '>=') expression    #binaryExp
    | expression op=('==' | '!=') expression                #binaryExp
    | expression op='&' expression                          #binaryExp
    | expression op='^' expression                          #binaryExp
    | expression op='|' expression                          #binaryExp
    | expression op='&&' expression                         #binaryExp
    | expression op='||' expression                         #binaryExp
    | <assoc=right>expression op='=' expression             #binaryExp

    | '(' expression ')'                                    #subExp
    | constant                                              #constExp
    | THIS                                                  #thisExp
    | ID                                                    #idExp
    ;

constant
    : type=TRUE         #boolConst
    | type=FALSE        #boolConst
    | type=INTCONST     #intConst
    | type=STRINGCONST  #stringConst
    | type=NULLCONST    #nullConst
    ;



varDeclaration
    : typeSpecifier ID
;

varStatement
    : varDeclaration ('=' expression)? ';'
//    | varDeclaration ';'
;


block
    : '{' statement* '}'
    ;

nonDeclStatement
    : block                                                         #blockStmt

    | expression ';'                                                #expStmt
    | IF '('expression')' nonDeclStatement (ELSE nonDeclStatement)? #ifStmt
    | WHILE '('expression')' nonDeclStatement                       #whileStmt
    | FOR '(' init = expression? ';'
              cond = expression? ';'
              step = expression?  ')' nonDeclStatement               #forStmt
    | RETURN expression? ';'                                        #returnStmt
    | BREAK ';'                                                     #breakStmt
    | CONTINUE ';'                                                  #continueStmt
    | ';'                                                           #emptyStmt
    ;

statement
    : nonDeclStatement                                              #nonVarStmt
    | varStatement                                                  #varStmt
    ;

functionDefinition
    : typeSpecifier ID '(' parameterList? ')' block;

constructorDefinition
    : ID '(' parameterList? ')' block;

classDefinition
    : CLASS ID '{'
        memberDeclaration*
    '}'
    ;

memberDeclaration
    : varStatement
    | functionDefinition
    | constructorDefinition
    ;

program
    : programSection* EOF
    ;

programSection
    : classDefinition
    | functionDefinition
    | varStatement
    ;
//    :expression;
//      :statement;
LBRACK: '[';
RBRACK: ']';

//Keywords
BOOL: 'bool';
INT : 'int';
STRING
    : 'string';
//NULL: 'null';
VOID: 'void';
TRUE: 'true';
FALSE
    : 'false';
IF  : 'if';
ELSE: 'else';
FOR : 'for';
WHILE
    : 'while';
BREAK
    : 'break';
CONTINUE
    : 'continue';
RETURN
    : 'return';
NEW : 'new';
CLASS
    : 'class';
THIS: 'this';



//const
//BOOLCONST
//    : TRUE
//    | FALSE;



INTCONST
    :  NonzeroDigit Digit*
    | '-2147483648'
    | '0'
    ;

STRINGCONST
    : '"' (ESC|.)*? '"'
    //: '"' .*? '"'
    ;

NULLCONST
//    : NULL;
    : 'null';
//oprands
PLUS:  '+';
MINUS: '-';
STAR: '*';
DIV: '/';
MOD: '%';

LT: '<';
GT: '>';
EQ: '==';
NE:'!=';
GE: '>=';
LE: '<=';

ANDAND: '&&';
OROR: '||';
NOT: '!';

LSHIFT: '<<';
RSHIFT: '>>';
TILDE: '~';
OR: '|';
CARET:'^';
AND:'&';

ASSIGN: '=';

PLUSPLUS: '++';
MINUSMINUS: '--';

ID  : IdentifierNondigit(IdentifierNondigit|Digit)*;

fragment
ESC : '\\"' | '\\\\' | '\\n';

fragment
IdentifierNondigit
    :   [a-zA-Z_]
    ;

fragment
NonzeroDigit
    :   [1-9]
    ;

fragment
Digit
    :   [0-9]
    ;

//------ Ignore
Whitespace
    :   [ \t]+ -> skip
    ;

Newline
    :   '\r'? '\n' -> skip
    ;

BlockComment
    :   '/*' .*? '*/' -> skip
    ;

LineComment
    :   '//' ~[\r\n]* -> skip
    ;
