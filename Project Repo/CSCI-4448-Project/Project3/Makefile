javac = javac  # the compiler
Flags =   # flags for the compiler

# runs for "make all"
all: main

# note that lines below targets must be
# TABBED in (not spaces!)
clean:
	-rm *.class

reset:
	-rm *.class *.txt

run: main 
	clear && java Project3Part2Driver >> Output.txt

main: reset
	$(javac) $(Flags) *.java
