grammar ILPMLgrammar3;

// Import de la grammaire à enrichir
import ILPMLgrammar1;

// Redéfinition des programmes
prog returns [com.paracamplus.ilp3.interfaces.IASTprogram node] 
    : (defs+=globalFunDef ';'?)*  (exprs+=expr ';'?) * EOF
    ;

// Fonction globale
globalFunDef returns [com.paracamplus.ilp2.interfaces.IASTfunctionDefinition node]
    : 'function' name=IDENT '(' vars+=IDENT? (',' vars+=IDENT)* ')'
        body=expr
    ;

// fonction locale nommée
localFunDef returns [com.paracamplus.ilp3.interfaces.IASTnamedLambda node]
    : 'function' name=IDENT '(' vars+=IDENT? (',' vars+=IDENT)* ')'
        body=expr
    ;
 
// Expressions enrichies
expr returns [com.paracamplus.ilp1.interfaces.IASTexpression node]
// expressions de la grammaire précédente
    : '(' exprs+=expr (';'? exprs+=expr)* ';'? ')' # Sequence
    | fun=expr '(' args+=expr? (',' args+=expr)* ')' # Invocation
    | op=('-' | '!') arg=expr # Unary
    | arg1=expr op=('*' | '/' | '%') arg2=expr # Binary
    | arg1=expr op=('+' | '-') arg2=expr # Binary
    | arg1=expr op=('<' | '<=' | '>' | '>=') arg2=expr # Binary
    | arg1=expr op=('==' | '!=') arg2=expr # Binary
    | arg1=expr op='&' arg2=expr # Binary
    | arg1=expr op=('|' | '^') arg2=expr # Binary
    | 'true' # ConstTrue
    | 'false' # ConstFalse
    | intConst=INT # ConstInteger
    | floatConst=FLOAT # ConstFloat
    | stringConst=STRING # ConstString
    | var=IDENT # Variable
    | 'let' vars+=IDENT '=' vals+=expr ('and' vars+=IDENT '=' vals+=expr)*
      'in' body=expr # Binding
    | 'if' condition=expr 'then' consequence=expr 
        ('else' alternant=expr)? # Alternative
    | var=IDENT '=' val=expr # VariableAssign
    | 'while' condition=expr 'do' body=expr # Loop
    
    // ajouts
 
 // exceptions
    | 'try' body=expr
      ('catch' '(' var=IDENT  ')' catcher=expr)? 
      ('finally' finallyer=expr)? # Try
      
// fonctions anonymes
    | 'lambda' '(' vars+=IDENT? (',' vars+=IDENT)* ')'
      body=expr # Lambda
    
// fonctions locales
    | defs+=localFunDef ('and' defs+=localFunDef)* 'in' body=expr 
    # Codefinitions       
    ;
