package hw4;

/**
 * @author Jane Liu
 * Homework 4
 * 
 * Class:
 * 	ClassifyTextualData:
 * 		Main class of this project. Calls the preprocessor class and KNN class. The KNN findNearestNeighbors method will display the 
 * 		k nearest neighbors to the console for each unknown document. It also prints the results to a file in the main directory 
 * 		called knn.txt.
 */

import java.util.ArrayList;
import java.util.List;


public class ClassifyTextualData {

	public static void main(String[] args) {

		int k = 5;													// the k-value for KNN
		List<double[]> corpus_matrix = new ArrayList<double[]>();
		List<String> topics = new ArrayList<String>();
		
		Preprocess prep_data = new Preprocess();
		corpus_matrix = prep_data.start();							// tf-idf matrix of the corpus
		topics = prep_data.get_ngrams();							// most frequent ngram topics
		
		// classify the unknown documents
		KNN _knn = new KNN(topics, corpus_matrix, k);
		_knn.findNearestNeighbors();
		
		
		
	}

}