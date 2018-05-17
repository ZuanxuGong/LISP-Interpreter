JFLAGS = -g

JAVAC = javac

COMPILE = $(JAVAC) $(JFLAGS)

CLASS_FILES = SExp.class TokenOperation.class Parser.class Func.class Interpreter.class

all: $(CLASS_FILES)

%.class : %.java
	$(COMPILE) $<

run:
	java Interpreter
	
runwithfile:
	java Interpreter < l2Input.txt

clean:
	rm *.class
