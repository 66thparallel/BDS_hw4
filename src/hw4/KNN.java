package hw4;

/**
 * @author Jane
 * Homework 4
 * 
 * Class:
 * 	KNN:
 * 		Takes a raw document and a similarity measure as input, and returns as output the k most similar 
 * 		(“nearest”) documents in the TF-IDF matrix, as well as their labels/topics.
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class KNN {
	
	private List<String> topics = new ArrayList<String>();					// top n ngrams in corpus
	private List<String> raw_texts = new ArrayList<String>();				// raw text from an unknown document
	private List<double[]> corpus_matrix = new ArrayList<double[]>();  		// tf-idf matrix of the corpus
	private List<double[]> matrix_of_unknowns = new ArrayList<double[]>();  // list of tf-idf values for each unknown document (one row per document)  						// a row of tf-idf values for an unknown document
	private List<double[]> temprow = new ArrayList<double[]>();
	private DocMatrix unknown_row = new DocMatrix();
	private int k = 0;														// the k value of KNN
	private int doc_count = 0;
	private int row_count = 0;
	boolean unknown_flag = true;  											// a flag to tell DocMatrix's generate function that the parameter raw_text is an unknown document
	
	public KNN(List<String> ngrams, List<double[]> corpus_matrix, int k) {
		this.topics = ngrams;
		this.corpus_matrix = corpus_matrix;		
		this.k = k;		
	}
		
	public void findNearestNeighbors() {
		
		List<String> docs = new ArrayList<String>();	// contains file names of all unknown documents
		String filename = "data/unknown_docs.txt";
		BufferedReader reader1;
		try {
			reader1 = new BufferedReader(new FileReader(filename));
			String line = reader1.readLine();

			while (line != null) {
				docs.add(line);
				line = reader1.readLine();
			}
			reader1.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		double[] row = new double[topics.size()];
		
		for (String docname : docs) {
			doc_count++;
			unknown_row.generate(topics, unknown_flag, docname);	// generate the tf-idf values for an unknown document
			temprow = unknown_row.get();
			row = temprow.get(0);
			matrix_of_unknowns.add(row);	// contains a list of tf-idf values for each unknown document
			unknown_row = new DocMatrix();
		}
		temprow.clear();
		
		// convert matrix_of_unknowns to double[][] matrix_unknowns for easier handling
		final double[][] matrix_unknowns = new double[matrix_of_unknowns.size()][topics.size()];
		for(int i=0; i<matrix_of_unknowns.size(); i++) {
			double[] mrow = new double[topics.size()];
			mrow = matrix_of_unknowns.get(i);
			for(int j=0; j<topics.size(); j++) {
				matrix_unknowns[i][j] = mrow[j];
			}
		}
		
		// convert corpus_matrix to double[][] matrix_corpus for easier handling
		final double[][] matrix_corpus = new double[corpus_matrix.size()][topics.size()];		
		for(int i=0; i<corpus_matrix.size(); i++) {
			double[] mrow = new double[topics.size()];
			mrow = corpus_matrix.get(i);
			for(int j=0; j<topics.size(); j++) {
				matrix_corpus[i][j] = mrow[j];
			}
		}

		// find the k nearest neighbors
		double[] distances = new double[doc_count];
		List<Double> neighbors = new ArrayList<Double>();
		Map<Double, String> dist_and_labels = new HashMap<Double, String>();	// map of all distances and their labels
		TreeMap<Double, String> nearest_neighbors = new TreeMap<Double, String>();
		
		for(int i=0; i<matrix_unknowns.length; i++) {  						// length is number of unknown documents
			
			distances = getEuclidDist(matrix_unknowns[i], matrix_corpus);	// getEuclidDist() accepts only one row at a time from matrix_unknowns
			
			// associate the Euclidean distances with their labels
			for(int j=0; j<distances.length; j++) {
				if (j >= 0 && j <= 8) {
					dist_and_labels.put(distances[j], "C1");
				}else if(j>=9 && j<=16) {
					dist_and_labels.put(distances[j], "C4");
				}else {
					dist_and_labels.put(distances[j], "C7");
				}
			}
			
			// sort  by distance
			nearest_neighbors = sortByKey(dist_and_labels);
		
			int k_count = k;
		    for(Double key : nearest_neighbors.descendingKeySet()){ 
		    	if(k_count>0) {
		    		neighbors.add(key);
		    	}
		    	k_count--;
		    }
		    
		    // output the kNN values to a text file
		    try {
	            FileWriter writer = new FileWriter("knn.txt", true);
	            BufferedWriter bufferedWriter = new BufferedWriter(writer);
	            bufferedWriter.write("KNN Results:");
	            bufferedWriter.newLine();
	            
			    for (Double key : neighbors) {
			    	String val = nearest_neighbors.get(key);
			    	System.out.print("Distance: " + key + " Label: " + val + ", ");
			    	bufferedWriter.write("Distance: " + key + " Label: " + val + "\n");
			    }
			    System.out.println("\n");
	            bufferedWriter.newLine();
	            bufferedWriter.newLine();
	            bufferedWriter.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		       
			dist_and_labels.clear();
			neighbors.clear();
		}
		
		
	}
	
	public static double[] getEuclidDist(double[] row_unknown, double[][] matrix_corpus) {

		double[] distances = new double[matrix_corpus.length];
		double unknown_mval = 0;
		double known_mval = 0;
		double curr_dist = 0;	
			
		for(int i=0; i<matrix_corpus.length; i++) {				// number of known documents is 24
			for(int j=0; j<matrix_corpus[i].length; j++) {		// number of topics is 30
				unknown_mval = row_unknown[j];
				known_mval = matrix_corpus[i][j];
				curr_dist += Math.sqrt(Math.pow((unknown_mval - known_mval), 2));
			}
			distances[i] = curr_dist;
			curr_dist = 0;
		}
		
		return distances;
	}
	
    public static TreeMap<Double, String> sortByKey(Map<Double, String> map) { 
    	
        TreeMap<Double, String> sorted = new TreeMap<>();
        sorted.putAll(map); 
        return sorted;
    }	

}
