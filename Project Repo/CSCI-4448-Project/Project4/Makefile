javac = javac  # the compiler
Flags =   # flags for the compiler

# runs for "make all"
all: main

# note that lines below targets must be
# TABBED in (not spaces!)
clean:
	-rm *.class

reset:
	-rm -r *.class logs

run: main 
	mkdir logs && java Project4Part2Driver | tee logs/Output.txt

main: reset
	$(javac) $(Flags) *.java
