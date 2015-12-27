grammar RapidASM;

// For explaining how the compiler behaves
// #define
directive : '#' WORD
          | '#' WORD ALPHANUM*
          | '#' '<' ANYCHAR '>'
          ;

statementBlock : '{' statement* '}' ;

statement : instruction
          | conditionalBlock
          | whileBlock
          | movStatement
          | assignmentStatement
          ;


movStatement : ALPHANUM MOVOPTOKEN ALPHANUM ;
assignmentStatement : ALPHANUM '=' NUMBER ; 

conditionalBlock : LIKELYHOOD? 'if' '(' booleanExpression ')' statementBlock ;

whileBlock : 'while' '(' booleanExpression ')' statementBlock
           | 'do' statementBlock 'while' '(' booleanExpression ')'
           ;

booleanExpression : 'true'
                  | 'false'
                  | '0'
                  | '1'
                  | numericValue CMPOPTOKEN numericValue
                  ; // TODO Add more to this.

// Regular old expressions.
// mov ax, bx
instruction : WORD
            | WORD ALPHANUM (',' ALPHANUM)*
            ;


// Sections can only have alphanumeric names.  "Varargs" used here for align.
section : 'section' varargs? ALPHANUM '{' sectionPopulant '}' ;

sectionPopulant : subroutine
                | object
                ;

// For defining out of the ordinary stuff the compiler generates.
// @define
object : '@' WORD
       | '@' WORD '='? ALPHANUM
       ;


subroutine : 'sub!' ALPHANUM ('(' ')')? statementBlock
           | 'sub' ('<' ALPHANUM '>')? ALPHANUM varargs? statementBlock
           ;

varargs : '(' ')'
        | '(' (WORD ':' VARSIZE)* ')'
        ;

numericValue : NUMBER
             | ALPHANUM
             ;

NUMBER : '-'? INT
       | '-'? INT EXP
       | HEX
       | OCT
       | BIN
       ;

fragment INT : [0-9]+ ;
fragment EXP : [Ee] [+\-]? INT ;
fragment HEX : '0x' [0-9a-fA-F] ;
fragment OCT : '0o' [0-7]* ;
fragment BIN : '0b' [01] ; // Restrict to multiples of 8?

// int/long/short/etc.
VARSIZE : '_' INT // _ means width
        | '_ptr'
        ;

// Changes the way the conditional is handled.
LIKELYHOOD : 'likely'
           | 'unlikely'
           ;

// Moving and comparison operators.
MOVOPTOKEN : '=' | '<-' | '->' | '<->' | '<=>' ;
CMPOPTOKEN : '<' | '<=' | '==' | '!=' | '=>' | '>' ;

// General stuff.
NEWLINE : [\r\n]+ ;
WORD : [a-zA-Z]+ ;
ALPHANUM : [a-zA-Z0-9]+ ;
ANYCHAR : [a-zA-Z0-9\.] ; // FIXME Not complete.

WS : [ \t]+ -> skip ;
