Jane Liu
Big Data Science Homework 4 - Classifying Textual Data


Files and folder in this project:
hw4.jar
hw4.s
/data/
/lib/


Requirements:

Unpack the folder in the working directory.

The /data/ folder and /lb/ folder should be in the same directory as hw4.jar.

The /lib/ folder contains the Stanford Core NLP jar files, which are not included. It is assumed a copy of Stanford Core NLP jar files can be copied locally to the /lib/ folder.

This program was written in Java 8. It is unknown if the program will run correctly for other versions of Java.


Instructions:

Once inside the working directory on Prince, please type the following:

$ module purge
$ module load jdk/1.8.0_111
$ srun --mem=10GB --time=00:15:00 --cpus-per-task 1 --pty $SHELL
$ java -cp hw4.jar:./lib/* hw4.ClassifyTextualData

A batch file, hw4.s, was included for convenience.


Expected Output:

The expected output is 10 groups of k Euclidean distances and labels that look something like this:

Distance: 0.44447399012429895, Label: C4
Distance: 0.4189597995384406, Label: C7
Distance: 0.33137550199164795, Label: C1
Distance: 0.3295694017469299, Label: C7
Distance: 0.3057275970291126, Label: C7

The 10 groups represent the 10 unknown documents. The number of entries per group depends on the k value and represent the k nearest neighbors of the unknown document.


