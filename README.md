# ILP: Implementation of a Programming Language 
This material was introduced in [the DLP class](https://www-master.ufr-info-p6.jussieu.fr/2018/DLP) for the [software engineering](http://www-master.ufr-info-p6.jussieu.fr/parcours/stl/) students within the [Sorbonne University: Pierre and Marie Curie University](https://www.sorbonne-universite.fr) in Paris.

## ILP features: 
- high level language, type Javascript / Smalltalk.
- ML syntax (OCaml, etc.)
- integer, floating, string and boolean values
- expressions with unary and binary operators (+, -, ==, <=, ...)
- control structures: alternatives, loops, blocks
- global variables and block local variables
- dynamic typing (no static type associated with a variable)
- primitives (print, newline)
- global and local functions, first-class functions (lambdas)
- exceptions
- objects, class system with inheritance
- automatic management of the memory (garbarge collector)

## Insights: 
ILP is implemented using a variety of languages and tools: Java 8, C, ANTLR 4.
- The parser: grammar, in ANTLR 4
- The interpreter is written using Java 8
- The compile is also written in Java 8 and which generates C code
- Execution Library C for Generic Code
- Automated test JUnit 4

### The interpreter: 
The ILP langauge is interpreted using a Java 8 intepreter.
basically the interpreter is an implemntations of the vistor pattern which visits each AST node and evaluate it.
The interpreter includes some primitives written in java like printing and the math functions( PI, sin etc...) 

### The compiler:
The ILP language can also be compiled to the C langauge.
The compiler is also written in Java which is also an implementations of the visitor pattern, the compiler will write convert each AST node to its appropriate C equivalent. 
The compiler includes an ILP lib written in C which includes all the primitives like types, printing, classes, exceptions etc.thus adding features to the compiler may require updating this ILP lib.

## How to add a feature 
- extend the grammar using the ANTLR 4, then generate its code.
- implement the listener that will create the AST nodes.
- create the necessary AST nodes.
- update the ASTFactory.
- update the Interpreter and the compiler to deal with the new AST node.


### The following topics has been a part of [the DLP class](https://www-master.ufr-info-p6.jussieu.fr/2018/DLP) assignements:
* Implemeting MakeVector, VectorGet and VectorLength as three primitives.
  - There is no need to update the grammar since we re not introducing any syntax.
  - Interpreter: adding MakeVector, VectorGet, VectorLength as sub classes to the Primitive class.
  - Compiler: adding MakeVector, VectorGet, VectorLength as function in the ILP library in C.
  
* Adding the Unless keyword:
    ```
    body unless condition
    // which is equivalent to 
    if(!condition) body
    ```
  - add the ```body=expr 'unless' condition=expr # Unless ``` to the ANTLR 4 Grammar.
  - add an AST node class for Unless and implement the ANTLR generated listener to create a new Unless AST node.
  - update the interpreter and the compiler to visit this new node and evalute it as ```if(!condition) body```.
  
* Adding the break and continue keyword

* Analyze and optimisation: 
  - Goal: The goal of this asseignement is to add a simple optimization: the integration of functions.
  - Consider the following example, containing a global function f of argument x, and a body with a local variable y and a    call to f:
    ```
    1 function f (x) (2 * x + 1);
    2 let y = 99 in
    3 z * f (y)
    ```
  - Function integration is used to avoid the call cost by replacing it with the body of the function, which gives the following optimized program:
    ```
    1 function f (x) (2 * x + 1);
    2 let y = 99 in
    3 z * (2 * y + 1)
    ```
- Coroutines:
  Coroutines are an extended form to call functions, allowing a more complex control flow. During a typical function call, the caller's execution is suspended until called function is terminated. However, using coroutines allows to the called coroutine  to pause temporarily, before its normal end, and to return to the caller, the caller can then continue executing his code for a moment, then return to the coroutine to resume its execution at the place where it had stopped, until its
next suspension, and so on. Here is an example of coroutine:
```
1 function f()
2 (
3   print("b1 ");
4   yield();
5   print("b2 ");
6   yield();
7   print("b3 ")
8 );
9
10
11
12 let x = costart f() in (
13  print("a1 ");
14  resume(x);
15  print("a2 ");
16  resume(x);
17  print("a3 ");
18  resume(x);
19  print("a4 ");
20  resume(x);
21  print("a5 ")
22 )
```
 - The result would be: a1 b1 a2 b2 a3 b3 a4 a5.
