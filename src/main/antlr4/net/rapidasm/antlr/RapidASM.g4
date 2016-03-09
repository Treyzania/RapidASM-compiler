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
statementEntry : labelSymbol* statement '\n'*;
statement : movStatement
          | conditionalBlock
          | whileBlock
          | statementBlock
          | instruction
          ;

movStatement : register MOVOPTOKEN numericValue ; // TODO Make this more specific. 

conditionalBlock : LIKELYHOOD? IF booleanParen statementBlock ;

whileBlock : whileBlockBefore
           | whileBlockAfter
           ;

whileBlockBefore : whileHeader statementBlock ;
whileBlockAfter : DO statementBlock whileHeader ;
whileHeader : LIKELYHOOD? WHILE booleanParen ;

// Changes the way the conditional is handled.
LIKELYHOOD : 'likely'
           | 'unlikely'
           ;

booleanParen : OPENPAREN booleanExpression CLOSEPAREN ;
OPENPAREN : '(' ;
CLOSEPAREN : ')' ;

booleanExpression : TRUE
                  | FALSE
                  | numericValue CMPOPTOKEN numericValue
                  ; // TODO Add more to this.

TRUE : 'true' ;
FALSE : 'false' ;

// Regular old instructions.
// int 0x80
instruction : ALPHANUM
            | ALPHANUM argument (',' argument)*
            ;

argument : numericValue
         | register
         ;

// For defining out of the ordinary stuff the compiler generates.
// @value
symbol : sectionSymbol
       | labelSymbol
       ;

register : DOLLARSIGN ALPHANUM ;

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

subroutine : SUBROUTINE_NOCALL ALPHANUM ('(' ')')? statementBlock
           | SUBROUTINE convDeclaration? ALPHANUM varargs? statementBlock
           ;

convDeclaration : '__' ALPHANUM ;

varargs : '(' ')'
        | '(' vararg (',' vararg)* ')'
        ;
        
vararg : ALPHANUM ':' VARSIZE ;

// TODO Make sure the pointer stuff doesn't allow spaces.
numericValue : NUMBER
             | register
             | ASTERISK* ALPHANUM      // C-style pointer dereferencing
             | ANDPERSEAND ALPHANUM    // C-style pointer referencing
             | ANDPERSEAND NUMBER      // Direct addressing
             | EXCLAMATION             // Address of instruction
             ;

NUMBER : NEGATIVE? INT
       | HEX
       | OCT
       | BIN
       ;

fragment INT : [0-9]+ ;
fragment HEX : OH_EX [0-9a-fA-F]+ ;
fragment OCT : OH_OH [0-7]+ ;
fragment BIN : OH_BE [01]+ ; // Restrict to multiples of 8?
fragment NEGATIVE : '-' ;
fragment OH_EX : '0x' ;
fragment OH_OH : '0o' ;
fragment OH_BE : '0b' ;


quantity : numericValue
         | STRING
         ;

STRING : '"' STRING_CHARS? '"' ;
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
ASTERISK : '*' ;
SUBROUTINE : 'sub' ;
SUBROUTINE_NOCALL : 'sub!' ;

ALPHANUM : [a-zA-Z][a-zA-Z0-9]+ ;

// int/long/short/etc.
VARSIZE : '_' INT // _ means width
        | '_ptr'
        | '_str'
        ;

// Moving and comparison operators.
EQUALS : '=' ;
MOVOPTOKEN : MOVLEFT | MOVRIGHT | XCHG | COERCEMOVLEFT | COERCEMOVRIGHT | COERCEXCHG ;
CMPOPTOKEN : LESSTHAN | CMPLTET | CMPEQUAL | CMPINEQUAL | CMPGTET | GREATERTHAN ;

fragment MOVLEFT : '<-' ;
fragment MOVRIGHT : '->' ;
fragment XCHG : '<->' ;
fragment COERCEMOVLEFT : '<<-' ;
fragment COERCEMOVRIGHT : '->>' ;
fragment COERCEXCHG : '<-->' ;
fragment LESSTHAN : '<' ;
fragment GREATERTHAN : '>' ;
fragment CMPLTET : '<=' ;
fragment CMPEQUAL : '==' ;
fragment CMPINEQUAL : '!=' ;
fragment CMPGTET : '>=' ;

WS : [ \t\r\n\u000C]+ -> skip ;

// Comments
MULTI_COMMENT : '/*' .*? '*/' -> skip ;
LINE_COMMENT : '//' ~[\r\n]* '\n' -> skip ;
