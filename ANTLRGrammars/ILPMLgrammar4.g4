grammar ILPMLgrammar4;

// Import de la grammaire à enrichir
import ILPMLgrammar1;

// Redéfinition des programmes
prog returns [com.paracamplus.ilp4.interfaces.IASTprogram node] 
    : (defs+=globalDef ';'?)*  (exprs+=expr ';'?) * EOF
    ;

// Declarations globales (classes et fonctions)
globalDef returns [com.paracamplus.ilp2.interfaces.IASTdeclaration node]
    : def=globalFunDef # GlobalFunctionDefinition
    | def=classDef # ClassDefinition
    ;
    
// classe
classDef returns [com.paracamplus.ilp4.interfaces.IASTclassDefinition node]
    : 'class' name=IDENT ('extends' parent=IDENT)? '{'
        ('var' fields+=IDENT (',' fields+=IDENT)* ';')*
        (methods+=methodDef ';'?)*
        '}'
    ;
    
// méthode
methodDef returns [com.paracamplus.ilp4.interfaces.IASTmethodDefinition node]
    : 'method' name=IDENT '(' vars+=IDENT? (',' vars+=IDENT)* ')' 
        body=expr
    ;

// fonction globale
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

    // ajouts (avec priorité élevée)

// objets spéciaux
    | 'self'    # Self
    | 'super' # Super

// affectation d'un attribut d'objet
    | obj=expr '.' field=IDENT '=' val=expr # WriteField

// appel de méthode sur un objet
    | obj=expr '.' field=IDENT  '(' args+=expr? (',' args+=expr)* ')' # Send
  
// accès à un attribut d'objet
    | obj=expr '.' field=IDENT # ReadField
  
// construction d'objets
    | 'new' className=IDENT  '(' args+=expr? (',' args+=expr)* ')' # New    


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
    | 'try' body=expr
      ('catch' '(' var=IDENT  ')' catcher=expr)? 
      ('finally' finallyer=expr)? # Try
    | 'lambda' '(' vars+=IDENT? (',' vars+=IDENT)* ')'
      body=expr # Lambda
    | defs+=localFunDef ('and' defs+=localFunDef)* 'in' body=expr 
    # Codefinitions       
    
    ;
