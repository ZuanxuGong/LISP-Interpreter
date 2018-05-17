# Wecome to use LISP Interpreter Part II-- Zuanxu Gong

## Prerequisite
1. Put all files in the same directory, including Interpreter.java, SExp.java, Parser.java, Func.java, TokenOperation.java, Makefile, l1input.txt, correctTests.txt, incorrectTests.txt, READMEP1.txt, designP1.txt
2. ``` subscribe JDK-CURRENT ```
NOTE: after this command, you need to log out and log back in to activate it. 

## Compile
``` make```</br>

## Run
1.Using existing test file provided by professor</br>
``` java Interpreter < l2input.txt```</br>
or</br>
``` make runwithfile```</br>

2.Using customized test case</br>
```java Interpreter```</br>
or</br>
``` make run```</br>

Then you can enter:</br>
```SExp such as (3.4)```</br>
```$```</br>

3.Exit method</br>
``` SExp such as (3.4)```</br>
``` $$```</br>

## Clean class file </br>
```make clean```</br>

## Input 
1. Each input S-Expression can be over one or more lines.
2. Blank character, tabs, and carriage return/newline are used as white-space characters.
3. Each complete s-expression will be followed by a line containing a single “$” sign. 
4. The last s-expression will be followed by a line containing “$$”. 
5. An identifier will consist of a letter of the alphabet followed by one or more letters or digits;
6. Both lowercase and uppercase input letters are allowed. And I will transform them into the uppercase letters in the output.
7. An integer atom may be signed or unsigned; thus, 42, -42, +42, are all valid. In addition, there should be no space between +/- and number: thus - 42, + 42 are invalid. The largest interger should be smaller than Interger.MAX_VALUE.

## Output
1. Showed on the screen and followed by a ">"
2. No matter inputs are lowercase letters or uppercase letters, the output will always be uppercase letters.
2. If S-Exp is correct, it will show the result. To show dot notation more clearly, there will be a white-space before and after the dot sign ".".
3. If S-Exp is incorrect, it will show the appropriate error message.
Error messages include:</br>
 ** Error: unequal parentheses **</br>
 ** Error: input SExp should be a list or an atom **</br>
 ** empty input **</br>
 ** Error: unexpected dot. **</br>
 ** Error: unexpected atom: XXX **</br>
 ** Error: sign + and - should be followed by an integer immediately. **</br>
 ** Error: exists Illegal character: XXX **</br>
 ** Error: unbound atom: X **</br>
 ** Error: trying to evaluate (X.Y) **</br>
 ** Error: cannot apply integer atom X as a function **</br>
 ** Error: cannot apply a non-atom as a function **</br>
 ** Error: function name should be a symbolic atom. **</br>
 ** Error: function XXX is not defined. **</br>
 ** Error: two many arguments for XXX **</br>
 ** Error: two few arguments for XXX **</br>
 ** Error: all conditions are false. **</br>
 ** Error: CAR cannot be applied to the atom X. **</br>
 ** Error: CDR cannot be applied to the atom X. **</br>
 ** Error: eq() must be applied to atoms. **</br>
 ** Error: wrong argument type for XXX. **</br>
 ** Error: illegal parameter name for function XXX(). **</br>
 ** Error: non-symbolic parameter name for function XXX(). **</br>
 ** Error: duplicate parameter names for function XXX(). **</br>
 ** Error: illegal function name XXX() **</br>
 ** All clauses of COND must be a list of size 2 **</br>
 ** Error: Invalid list since the final atom we reach by applying cdr repeatedly should be NIL. **</br>
 ** Error: Parameters for a function definition must be a list. **</br>
