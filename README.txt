Jane Liu
Big Data Science Homework 4 - Classifying Textual Data


Files and folder in this project:
hw4.jar
README.txt
/src/
/data/
/lib/


Requirements and Info:

All included folders should be in the same directory as hw4.jar.

The /src/ folder contains all the source files: 
ClassifyTextualData.java
Preprocess.java
DocMatrix.java
KNN.java.

The /data/ folder contains the unknown documents and /data/corpus/ contains the known documents of the corpus.

The /lib/ folder contains the Stanford Core NLP jar files, which are not included. It is assumed a copy of Stanford Core NLP jar files can be copied locally to a /lib/ folder.

This program was written in Java 8. It is unknown if the program will run correctly for other versions of Java.


Instructions:

From the Prince command line please type the following:

$ module purge
$ module load jdk/1.8.0_111
$ srun --mem=20GB --time=00:30:00 --cpus-per-task 1 --pty $SHELL
$ java -cp hw4.jar:./lib/* hw4.ClassifyTextualData


Test the program with a new corpus:

To test this program with a new corpus (or to add additional folders and documents for testing) copy the new folders to /data/corpus/. New folders and files must be placed in alphabetical or chronological order (eg, ‘C1’ folder comes before ‘C7’ folder, ‘article02.txt’ comes before ‘article03.txt’). Update known_docs.txt located in /data/corpus/ with a list of file paths for the new documents.

To test this program with a new set of unknown documents replace the current unknown files with new ones. Update unknown_docs.txt located in /data/ with a list of new unknown file names.


Expected Output:

The expected output is 10 groups of k Euclidean distances and labels that look something like this:

Distance: 0.1415694403813542, Label: C1
Distance: 0.2290081589964407, Label: C1
Distance: 0.24961935490587853, Label: C4
Distance: 0.25221912628546383, Label: C1
Distance: 0.2680219139122532, Label: C4

The 10 groups represent the 10 unknown documents. The number of entries per group represent the k nearest neighbors of the unknown document. The results are also output to a text file called knn.txt in the same directory as hw4.jar.


