Jane Liu
Big Data Science Homework 4 - Classifying Textual Data


Files and folder in this project:
hw4.jar
hw4.s
/src/
/data/
/lib/


Requirements:

The /src/ folder contains all the source files: 
ClassifyTextualData.java (main)
Preprocess.java
DocMatrix.java
KNN.java.

All the folders should be in the same directory as hw4.jar.

The /lib/ folder contains the Stanford Core NLP jar files, which are not included. It is assumed a copy of Stanford Core NLP jar files can be copied locally to a /lib/ folder.

This program was written in Java 8. It is unknown if the program will run correctly for other versions of Java.


Instructions:

Once inside the working directory on Prince, please type the following:

$ module purge
$ module load jdk/1.8.0_111
$ srun --mem=20GB --time=00:15:00 --cpus-per-task 1 --pty $SHELL
$ java -cp hw4.jar:./lib/* hw4.ClassifyTextualData

It’s not necessary to use the batch file, hw4.s (the program doesn’t take too long), but I included it just in case.


Expected Output:

The expected output is 10 groups of k Euclidean distances and labels that look something like this:

Distance: 0.17500186940478288, Label: C1
Distance: 0.20198030865794894, Label: C1
Distance: 0.21051414800163237, Label: C1
Distance: 0.23159688389393868, Label: C1
Distance: 0.24655365133076582, Label: C4

The 10 groups represent the 10 unknown documents. The number of entries per group depends on the k value and represent the k nearest neighbors of the unknown document. The results are also output to a text file called knn.txt in the same directory as hw4.jar


