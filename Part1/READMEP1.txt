Wecome to use LISP Interpreter Part I-- Zuanxu Gong

*********** Prerequisite ***********
1. Put all files in the same directory, including Interpreter.java, SExp.java, Parser.java, TokenOperation.java, Makefile, l1input.txt, correctTests.txt, incorrectTests.txt, READMEP1.txt, designP1.txt
2. >> subscribe JDK-CURRENT 
NOTE: after this command, you need to log out and log back in to activate it. 

*********** Compile ***********
>> make

*********** Run ***********
1.Using existing test file provided by professor
>> java Interpreter < l1input.txt
or
>> make runwithfile

2.Using existing test file shared by student on piazza
>> java Interpreter < correctTests.txt
or
>> java Interpreter < incorrectTests.txt

3.Using customized test case
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

*********** Samples ***********
Example1: l1input.txt
( 2 . (3 4))
$
> (2 . (3 . (4 . NIL)))
( 2 . (3 4) . 5)
$
> ** Error: unexpected dot. **
( 2 . ((3 4) . 5))
$
> (2 . ((3 . (4 . NIL)) . 5))
( 2 . (3 4) $ 5)
$
> ** Error: unexpected dollar. **
( 2 (3 . 4) (5 . 6))
$
> (2 . ((3 . 4) . ((5 . 6) . NIL)))
(CAR (QUOTE (A . B)))
$
> (CAR . ((QUOTE . ((A . B) . NIL)) . NIL))
(CONS 4 (QUOTE (A . B)))
$
> (CONS . (4 . ((QUOTE . ((A . B) . NIL)) . NIL)))
(CONS 4 (A . B))
$
> (CONS . (4 . ((A . B) . NIL)))
(DEFUN SILLY (A B) (PLUS A B))
$
> (DEFUN . (SILLY . ((A . (B . NIL)) . ((PLUS . (A . (B . NIL))) . NIL))))
(SILLY 5 6)
$
> (SILLY . (5 . (6 . NIL)))
(SILLY (CAR (QUOTE (5 . 6))) (CDR (QUOTE (5 . 6))) )
$
> (SILLY . ((CAR . ((QUOTE . ((5 . 6) . NIL)) . NIL)) . ((CDR . ((QUOTE . ((5 . 6) . NIL)) . NIL)) . NIL)))
(DEFUN MINUS2 (A B) (MINUS A B))
$
> (DEFUN . (MINUS2 . ((A . (B . NIL)) . ((MINUS . (A . (B . NIL))) . NIL))))
(DEFUN NOTSOSILLY (A B) 
      (COND
          ((EQ A 0) (PLUS B 1))
          ((EQ B 0) (NOTSOSILLY (MINUS2 A 1) 1))
          (T (NOTSOSILLY (MINUS2 A 1) (NOTSOSILLY A (MINUS2 B 1))))
      ))
$
> (DEFUN . (NOTSOSILLY . ((A . (B . NIL)) . ((COND . (((EQ . (A . (0 . NIL))) . ((PLUS . (B . (1 . NIL))) . NIL)) . (((EQ . (B . (0 . NIL))) . ((NOTSOSILLY . ((MINUS2 . (A . (1 . NIL))) . (1 . NIL))) . NIL)) . ((T . ((NOTSOSILLY . ((MINUS2 . (A . (1 . NIL))) . ((NOTSOSILLY . (A . ((MINUS2 . (B . (1 . NIL))) . NIL))) . NIL))) . NIL)) . NIL)))) . NIL))))
(NOTSOSILLY 0 0)
$$
> (NOTSOSILLY . (0 . (0 . NIL)))

Example2: correctTests.txt
()
$
> NIL
1
$
> 1
B
$
> B
b
$
> B
b1
$
> B1
(1)
$
> (1 . NIL)
(b1)
$
> (B1 . NIL)
(b1.1)
$
> (B1 . 1)
(1.b1)
$
> (1 . B1)
(1 bb)
$
> (1 . (BB . NIL))
(bb 1)
$
> (BB . (1 . NIL))
(() ())
$
> (NIL . (NIL . NIL))
((1 2 3) . (a b c))
$
> ((1 . (2 . (3 . NIL))) . (A . (B . (C . NIL))))
((1.2) (3 a) (b c))
$
> ((1 . 2) . ((3 . (A . NIL)) . ((B . (C . NIL)) . NIL)))
((1.2) a)
$
> ((1 . 2) . (A . NIL))
(a (1.2))
$
> (A . ((1 . 2) . NIL))
(1.(3.(4.NIL)))
$
> (1 . (3 . (4 . NIL)))
((((()))))
$
> ((((NIL . NIL) . NIL) . NIL) . NIL)
(((((a)))))
$
> (((((A . NIL) . NIL) . NIL) . NIL) . NIL)
(((((2)))))
$
> (((((2 . NIL) . NIL) . NIL) . NIL) . NIL)
(((((2.3)))))
$
> (((((2 . 3) . NIL) . NIL) . NIL) . NIL)
(((((2 3)))))
$
> (((((2 . (3 . NIL)) . NIL) . NIL) . NIL) . NIL)
(((((a 2)))))
$
> (((((A . (2 . NIL)) . NIL) . NIL) . NIL) . NIL)
(((((a b)))))
$
> (((((A . (B . NIL)) . NIL) . NIL) . NIL) . NIL)
(() (a b (c (c.3))) (4 c) )
$
> (NIL . ((A . (B . ((C . ((C . 3) . NIL)) . NIL))) . ((4 . (C . NIL)) . NIL)))

(
	)
$
> NIL
1 
$
> 1
	B
$
> B
b

$
> B
b1
$
> B1
(
1)
$
> (1 . NIL)
(

	b1)
$
> (B1 . NIL)
(b1.1)	
$
> (B1 . 1)
(1.	b1)
$
> (1 . B1)
(	1  bb)
$
> (1 . (BB . NIL))
( bb 1 ) 
$
> (BB . (1 . NIL))
(()

())
$
> (NIL . (NIL . NIL))
((1
	2 3) . (a
b c))
$
> ((1 . (2 . (3 . NIL))) . (A . (B . (C . NIL))))
((1.2) (

	3 a) (b c))
$
> ((1 . 2) . ((3 . (A . NIL)) . ((B . (C . NIL)) . NIL)))
((1.
	2) a)
$
> ((1 . 2) . (A . NIL))
(
a (1 .2)	 )
$
> (A . ((1 . 2) . NIL))
(
 1.(3.(4.
 NIL)))
$
> (1 . (3 . (4 . NIL)))
((
 ((())))
 )
$
> ((((NIL . NIL) . NIL) . NIL) . NIL)
(((((
 a
  )))
))
$
> (((((A . NIL) . NIL) . NIL) . NIL) . NIL)
(((
 ((2)))))
$
> (((((2 . NIL) . NIL) . NIL) . NIL) . NIL)
((
   (((2.3))))
 	)
$
> (((((2 . 3) . NIL) . NIL) . NIL) . NIL)
((
 (((2 3
 )))))
$
> (((((2 . (3 . NIL)) . NIL) . NIL) . NIL) . NIL)
(((		((a 2)))))
$
> (((((A . (2 . NIL)) . NIL) . NIL) . NIL) . NIL)
((  (((a   b))  )))
$
> (((((A . (B . NIL)) . NIL) . NIL) . NIL) . NIL)
(() (a

  b (c   (c.3))) (4 c) )
$
> (NIL . ((A . (B . ((C . ((C . 3) . NIL)) . NIL))) . ((4 . (C . NIL)) . NIL)))


(defun f (pa pb) (cons 1 2))
$$
> (DEFUN . (F . ((PA . (PB . NIL)) . ((CONS . (1 . (2 . NIL))) . NIL))))

Example3: incorrectTests.txt
(
$
> ** Error: unequal parentheses **
)
$
> ** Error: unequal parentheses **
.
$
> ** Error: unexpected atom: . **
1a
$
> ** Error: unexpected atom: 1A **
())
$
> ** Error: unequal parentheses **
$
> ** empty input **
(()
$
> ** Error: unequal parentheses **
((
$
> ** Error: unequal parentheses **
))
$
> ** Error: unequal parentheses **
(.)
$
> ** Error: unexpected atom: . **
(.
$
> ** Error: unequal parentheses **
).
$
> ** Error: unequal parentheses **
_
$
> ** Error: exists Illegal character: _ **
+
$
> ** Error: sign + and - should be followed by an integer immediately. **
-
$
> ** Error: sign + and - should be followed by an integer immediately. **
1+
$
> ** Error: sign + and - should be followed by an integer immediately. **
(+)
$
> ** Error: sign + and - should be followed by an integer immediately. **
(-)
$
> ** Error: sign + and - should be followed by an integer immediately. **
.)
$
> ** Error: unequal parentheses **
(1 2 ))
$
> ** Error: unequal parentheses **
(( 1 2)
$
> ** Error: unequal parentheses **
(a b 1b)
$
> ** Error: unexpected atom: 1B **
(1.2.3)
$
> ** Error: unexpected dot. **
(1.(1.2.3))
$
> ** Error: unexpected dot. **
((((())))
$
> ** Error: unequal parentheses **
(((4))))
$
> ** Error: unequal parentheses **
((((((3
$
> ** Error: unequal parentheses **
))))))3
$
> ** Error: unequal parentheses **
3)))))
$
> ** Error: unequal parentheses **
a(((()
$
> ** Error: unequal parentheses **
()()
$
> ** Error: unexpected dot. **
().()
$
> ** Error: unexpected dot. **
(().()
$
> ** Error: unequal parentheses **
().())
$
> ** Error: unequal parentheses **
abcd)
$
> ** Error: unequal parentheses **
(a b c d
$
> ** Error: unequal parentheses **
a b c d)
$
> ** Error: unequal parentheses **
(.3)
$
> ** Error: unexpected atom: . **
(1.)
$
> ** Error: unexpected dot. **
(1. 1 2 3)
$
> ** Error: unexpected dot. **
(1.3).(2.3)
$
> ** Error: unexpected dot. **
((3 4 5) (3 4 5) (1)))
$$
> ** Error: unequal parentheses **