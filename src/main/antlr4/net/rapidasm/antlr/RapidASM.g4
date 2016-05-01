grammar RapidASM;

module : directiveDeclaration* section+ EOF ;

// For explaining how the compiler behaves
// #define
directiveDeclaration : directiveIfDefault
                     | directiveCfunc
                     ;

directiveIfDefault : DIRECTIVE_IFDEF LIKELYHOOD '\n'* ;
directiveCfunc : DIRECTIVE_CFUNC ALPHANUM varSizeList? '\n'* ;

varSizeList : VARSIZE (',' VARSIZE)* ;

DIRECTIVE_IFDEF : '%ifdefault' ; 
DIRECTIVE_CFUNC : '%cfunc' ; 

// Sections can only have alphanumeric names.
section : SECTION ALPHANUM '{' sectionPopulant* '}' '\n'*;

sectionPopulant : subroutine
                | sectionSymbol
                ;

statementBlock : '{' statementEntry* '}' ;
statementBlockStatement : statementBlock ;
statementEntry : labelSymbol* statement '\n'*;
statement : movStatement
          | conditionalBlock
          | whileBlock
          | statementBlockStatement
          | instruction
          | subroutineInvocation
          | returnStatement
          ;

movStatement : numericValue movOperator numericValue ; // TODO Make this more specific. 

conditionalBlock : conditionalHeader statementBlock ;
conditionalHeader : LIKELYHOOD? IF booleanParen ;

whileBlock : whileBlockBefore
           | whileBlockAfter
           ;

whileBlockBefore : whileHeader statementBlock ;
whileBlockAfter : DO statementBlock whileHeader ;
whileHeader : LIKELYHOOD? WHILE booleanParen ;

booleanParen : OPENPAREN booleanExpression CLOSEPAREN ;
booleanExpression : TRUE
                  | FALSE
                  | operand cmpOperator operand
                  ; // TODO Add more to this.

returnStatement : RETURN numericValue? ;

// Regular old instructions.
// int 0x80
instruction : ALPHANUM operands? ;

// For defining out of the ordinary stuff the compiler generates.
// @value
symbol : sectionSymbol
       | labelSymbol
       ;

sectionSymbol : valueSymbol
              | storeSymbol
              | skipSymbol
              ;

valueSymbol : SYMB_VALUE VARSIZE ALPHANUM EQUALS quantity ;
storeSymbol : SYMB_STORE VARSIZE NUMBER ;
skipSymbol : SYMB_SKIP NUMBER ;
labelSymbol : SYMB_LABEL ALPHANUM ;

SYMB_VALUE : '@value' ;
SYMB_STORE : '@store' ;
SYMB_SKIP : '@skip' ;
SYMB_LABEL : '@label' ;

subroutine : SUBROUTINE_NOCALL ALPHANUM (OPENPAREN CLOSEPAREN)? statementBlock
           | SUBROUTINE convDeclaration? ALPHANUM varargs? statementBlock
           ;

convDeclaration : '__' ALPHANUM ;

varargs : OPENPAREN CLOSEPAREN
        | OPENPAREN vararg (',' vararg)* CLOSEPAREN
        ;

vararg : ALPHANUM ':' VARSIZE ;

quantity : numericValue
         | STRING
         ;

// TODO Make sure the pointer stuff doesn't allow spaces.
numericValue : numericImmediate
             | numericDereference          // C-style pointer dereferencing
             | numericSubroutineInvocation // Return values
             | numericRelativeDereference  // Relative addressing
             | numericValueSymbol
             | numericValueSymbolDereference
             ;

numericDereference : ASTERISK numericImmediate ;
numericSubroutineInvocation : subroutineInvocation ;
numericRelativeDereference : ASTERISK OPENPAREN numericImmediate plusMinus NUMBER CLOSEPAREN ;
numericImmediate : NUMBER      // Literals
                 | register    // Values of registers
                 | ALPHANUM
                 | EXCLAMATION // Address of instruction
                 ;

numericValueSymbol : PERCENT ALPHANUM ;
numericValueSymbolDereference : ASTERISK PERCENT ALPHANUM ;

subroutineInvocation : SQUIGGLE ALPHANUM OPENPAREN operands? CLOSEPAREN;
operands : operand (',' operand)*;
operand : numericValue ; // No string literals here.

register : DOLLARSIGN ALPHANUM ;

NUMBER : MINUS? INT
       | HEX
       | OCT
       | BIN
       ;

plusMinus : PLUS | MINUS ;
MINUS : '-' ;
PLUS : '+' ;
fragment INT : [0-9]+ ;
fragment HEX : OH_EX [0-9a-fA-F]+ ;
fragment OCT : OH_OH [0-7]+ ;
fragment BIN : OH_BE [01]+ ; // Restrict to multiples of 8?
fragment OH_EX : '0x' ;
fragment OH_OH : '0o' ;
fragment OH_BE : '0b' ;

STRING : QUOT STRING_CHARS? QUOT ;
fragment STRING_CHARS : STRING_CHAR+ ;
fragment STRING_CHAR : ~["\\\n]
                     | ESCAPE_SEQ
                     ;
fragment ESCAPE_SEQ : '\\' [btnfr"'\\] ;

// General stuff.
SECTION : 'section' ;
WHILE : 'while' ;
DO : 'do' ;
IF : 'if' ;
DOLLARSIGN : '$' ;
EXCLAMATION : '!' ;
ANDPERSEAND : '&' ;
PERCENT : '%' ;
ASTERISK : '*' ;
SUBROUTINE : 'sub' ;
SUBROUTINE_NOCALL : 'sub!' ;
SQUIGGLE : '~' ;
TRUE : 'true' ;
FALSE : 'false' ;
OPENPAREN : '(' ;
CLOSEPAREN : ')' ;
QUOT : '"' ;

// Changes the way the conditional is handled.
LIKELYHOOD : 'likely'
           | 'unlikely'
           ;

RETURN : 'return' ;

ALPHANUM : [a-zA-Z][a-zA-Z0-9]+ ;

// int/long/short/etc.
VARSIZE : '_' INT // _ means width
        | '_ptr'
        | '_str'
        | '_string'
        ;

// Moving and comparison operators.
EQUALS : '=' ;
movOperator : LARROW | RARROW | DARROW | LARROWEQ | RARROWEQ | DARROWEQ ;
cmpOperator : LESSTHAN | GREATERTHAN | EQUALCMP | INEQUALCMP | LARROWEQ | RARROWEQ;

LARROW : '<-' ;
RARROW : '->' ;
DARROW : '<->' ;
LARROWEQ : '<=' ;
RARROWEQ : '=>' ;
DARROWEQ : '<=>' ;
LESSTHAN : '<' ;
GREATERTHAN : '>' ;
EQUALCMP : '==' ;
INEQUALCMP : '!=' ;
GTETCMP : '>=' ;

WS : [ \t\r\n\u000C]+ -> skip ;

// Comments
MULTI_COMMENT : '/*' .*? '*/' -> skip ;
LINE_COMMENT : '//' ~[\r\n]* '\n' -> skip ;
