JFLAGS = -g

JAVAC = javac

COMPILE = $(JAVAC) $(JFLAGS)

CLASS_FILES = SExp.class TokenOperation.class Parser.class Interpreter.class

all: $(CLASS_FILES)

%.class : %.java
	$(COMPILE) $<

run:
	java Interpreter
	
runwithfile:
	java Interpreter < l1input.txt

clean:
	rm *.class
