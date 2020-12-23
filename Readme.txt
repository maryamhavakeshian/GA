##Introduction
TSPSolver is the implementation of genetic algorithm for solving TSP

##Implementation
TSPSolver is implemented in JAVA. 

##Main class
The main class of TSPSolver  is TSPSolver.java in the TSP package

## Compile and exectuion 

This project cannot be compiled by javac version 15!

There is TSPSolver.jar in the TSPGA folder, which can be used to execute the project. However, the project files are available for standalone execution
The project can be complied through either IntelliJ IDEA or Eclipse IDE.
If there is no IDE available the following steps are required to compile and execute the project.

####Inorder to compile and execute via command prompt , follow this steps

Step#1 move to project directory: the name of project folder is TSPGA

Step#2 execute the below command to compile the project and move .class files in the bin folder
javac -d bin -cp lib/commons-cli-1.4.jar src/TSP/*.java

Step#3 move to the outputTSP folder
cd ./bin

step#4 execute the below command to execute main class
java -cp "../lib/commons-cli-1.4.jar;" TSP.TSPSolver


## output

the distance of best individual is printed in the console. Also, the permuation of the best individual is printed in "solution.csv" file

