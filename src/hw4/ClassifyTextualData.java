package hw4;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jane Liu
 * Homework 4
 * 
 * Class:
 * 	ClassifyTextualData:
 * 		Main class of this project. Calls the preprocessor class and KNN class.
 */


public class ClassifyTextualData {

	public static void main(String[] args) {
		/*
		 * TODO:
		 * 1. Create class method for KNN that takes a raw document and similarity measure
		 * 2. Output the classification of the unknown document.
		 */

		int k = 5;													// the k-value for KNN
		List<double[]> corpus_matrix = new ArrayList<double[]>();
		List<String> topics = new ArrayList<String>();
		
		Preprocess prep_data = new Preprocess();
		corpus_matrix = prep_data.start();							// tf-idf matrix of the corpus
		topics = prep_data.get_ngrams();							// most frequent n ngram topics
		
		// classify the unknown documents
		KNN _knn = new KNN(topics, corpus_matrix, k);
		_knn.findNearestNeighbors();
		
		
		
	}

}