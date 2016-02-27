grammar RapidASM;

module : modulePopulant+ ;

modulePopulant : directive
               | section
               ;

// Sections can only have alphanumeric names.
section : 'section' ALPHANUM '{' sectionPopulant '}' ;

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
          | statementBlock
          ;

movStatement : ALPHANUM MOVOPTOKEN ALPHANUM ;
assignmentStatement : ALPHANUM '=' numericValue ; 

conditionalBlock : LIKELYHOOD? 'if' '(' booleanExpression ')' statementBlock ;

whileBlock : LIKELYHOOD? 'while' '(' booleanExpression ')' statementBlock
           | 'do' statementBlock LIKELYHOOD? 'while' '(' booleanExpression ')'
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

// For defining out of the ordinary stuff the compiler generates.
// @value
symbol : sectionSymbol
          | labelSymbol
          ;

sectionSymbol : valueSymbol
              | storeSymbol
              | skipSymbol
              ;

valueSymbol : '@value' WORD '=' VALUE ;
storeSymbol : '@store' NUMBER ;
skipSymbol : '@skip' NUMBER ;
labelSymbol : '@label' WORD ;

sectionPopulant : subroutine
                | sectionSymbol
                ;

subroutine : 'sub!' ALPHANUM ('(' ')')? statementBlock
           | 'sub' ('<' ALPHANUM '>')? ALPHANUM varargs? statementBlock
           ;

varargs : '(' ')'
        | '(' (WORD ':' VARSIZE)* ')'
        ;

// TODO Make sure the pointer stuff doesn't allow spaces.
numericValue : NUMBER
             | ALPHANUM
             | '*'* ALPHANUM   // C-style pointer dereferencing
             | '&' ALPHANUM    // C-style pointer referencing
             | '&' NUMBER      // Direct addressing
             | '!'             // Address of instruction
             ;

VALUE : NUMBER
      | STRING
      ;

STRING : '"' STRING_CHARS? '"' ;
fragment STRING_CHARS : STRING_CHAR+ ;
fragment STRING_CHAR : ~["\\]
                     | ESCAPE_SEQ
                     ;
fragment ESCAPE_SEQ : '\\' [btnfr"'\\] ;

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
        | '_str'
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
ANYCHAR : [a-zA-Z0-9\.\ ] ; // FIXME Not complete.

WS : [ \t\r\n\u000C]+ -> skip ;

// Comments
MULTI_COMMENT : '/*' .*? '*/' -> skip ;
LINE_COMMENT : '//' ~[\r\n]* -> skip ;

