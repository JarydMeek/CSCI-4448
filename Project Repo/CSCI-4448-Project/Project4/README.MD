# CSCI-4448-Project3
Members: Isabel Anaya, Jaryd Meek, Noah Nguyen

Java Version: 17

*To Run Code: generates output files, prints nothing to terminal*

`make run`

*To clean up 100000000 class files:*

`make clean`


*To clean up 100000000 class files and delete txt files:*

`make reset`

### Project 4 Notes:
Make sure there is a "logs" directory before running. This is where the Logger files and Output will be located. `Output.txt` in this repo contains output from the simulation including example user input. 

CLI assumes that buying and selling uses random items.

CLI also starts on the North store and switches to the other when told to change store.

The Abstract Factory uses generic components with specific names instead of multiple identical classes, for simplicity.

Number of days that the stores run for is generated randomly.

Folder `ECLIPSE JUNIT TESTS` contains code restructured to work with JUnit in Eclipse and thus contains both the unit tests and the output from them. For further details see folder. 
