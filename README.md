Jane Liu
Big Data Science Homework 4 - Classifying Textual Data


Files and folder in this project:
hw4.jar
hw4.s
/data/
/lib/


Requirements:

The /data/ folder sits in the same directory as hw4.jar.

The /lib/ folder also sits in the same directory as hw4.jar and contains all the Stanford Core NLP jar files.

This program was written in Java 8. It is unknown if the program will run correctly for other versions of Java.


Instructions:

Once inside the working directory on Prince, please type the following:

$ module purge
$ module load jdk/1.8.0_111
$ srun --mem=5GB --time=00:15:00 --cpus-per-task 1 --pty $SHELL
$ java -cp hw4.jar:./lib/* hw4.ClassifyTextualData

A batch file, hw4.s, was included in case the program takes a while to run.


