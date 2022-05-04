javac = javac  # the compiler
Flags =   # flags for the compiler

# runs for "make all"
all: main

# note that lines below targets must be
# TABBED in (not spaces!)
clean:
	rm *.class

run: main
	clear && java Project2Part2Driver

main:
	$(javac) $(Flags) *.java
