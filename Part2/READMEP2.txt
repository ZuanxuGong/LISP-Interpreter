Wecome to use LISP Interpreter Part II-- Zuanxu Gong

*********** Prerequisite ***********
1. Put all files in the same directory, including Interpreter.java, SExp.java, Parser.java, Func.java, TokenOperation.java, Makefile, l1input.txt, correctTests.txt, incorrectTests.txt, READMEP1.txt, designP1.txt
2. >> subscribe JDK-CURRENT 
NOTE: after this command, you need to log out and log back in to activate it. 

*********** Compile ***********
>> make

*********** Run ***********
1.Using existing test file provided by professor
>> java Interpreter < l2input.txt
or
>> make runwithfile

2.Using customized test case
>> java Interpreter
or
>> make run

Then you can enter:
>> SExp such as (3.4)
>> $

3.Exit method
>> SExp such as (3.4)
>> $$

*********** Clean class file ***********
>> make clean

*********** Input ***********
1. Each input S-Expression can be over one or more lines.
2. Blank character, tabs, and carriage return/newline are used as white-space characters.
3. Each complete s-expression will be followed by a line containing a single “$” sign. 
4. The last s-expression will be followed by a line containing “$$”. 
5. An identifier will consist of a letter of the alphabet followed by one or more letters or digits;
6. Both lowercase and uppercase input letters are allowed. And I will transform them into the uppercase letters in the output.
7. An integer atom may be signed or unsigned; thus, 42, -42, +42, are all valid. In addition, there should be no space between +/- and number: thus - 42, + 42 are invalid. The largest interger should be smaller than Interger.MAX_VALUE.

*********** Output ***********
1. Showed on the screen and followed by a ">"
2. No matter inputs are lowercase letters or uppercase letters, the output will always be uppercase letters.
2. If S-Exp is correct, it will show the result. To show dot notation more clearly, there will be a white-space before and after the dot sign ".".
3. If S-Exp is incorrect, it will show the appropriate error message.
Error messages include:
> ** Error: unequal parentheses **
> ** Error: input SExp should be a list or an atom **
> ** empty input **
> ** Error: unexpected dot. **
> ** Error: unexpected atom: XXX **
> ** Error: sign + and - should be followed by an integer immediately. **
> ** Error: exists Illegal character: XXX **
> ** Error: unbound atom: X **
> ** Error: trying to evaluate (X.Y) **
> ** Error: cannot apply integer atom X as a function **
> ** Error: cannot apply a non-atom as a function **
> ** Error: function name should be a symbolic atom. **
> ** Error: function XXX is not defined. **
> ** Error: two many arguments for XXX **
> ** Error: two few arguments for XXX **
> ** Error: all conditions are false. **
> ** Error: CAR cannot be applied to the atom X. **
> ** Error: CDR cannot be applied to the atom X. **
> ** Error: eq() must be applied to atoms. **
> ** Error: wrong argument type for XXX. **
> ** Error: illegal parameter name for function XXX(). **
> ** Error: non-symbolic parameter name for function XXX(). **
> ** Error: duplicate parameter names for function XXX(). **
> ** Error: illegal function name XXX() **
> ** All clauses of COND must be a list of size 2 **
> ** Error: Invalid list since the final atom we reach by applying cdr repeatedly should be NIL. **;
> ** Error: Parameters for a function definition must be a list. **
                                
*********** Samples ***********
Example1: l2input.txt
> A

> (4 . (A . B))

> ** Error: trying to evaluate (A.B) **

> SILLY

> 11

> 11

> MINUS2

> NOTSOSILLY

> 1

> 3

> ** Error: wrong argument type for PLUS(). **

> ** Error: function SILY() is not defined. **

> ** Error: two many arguments for SILLY **

> ** Error: two few arguments for SILLY **
