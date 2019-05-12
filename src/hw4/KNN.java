package hw4;
	
import java.io.BufferedReader;

/**
 * @author Jane Liu
 * Homework 4
 * 
 * Class:
 * 	KNN:
 * 		Accepts a list of topics, the tf-idf matrix of the corpus, and the k-value for the KNN algorithm. 
 * 		It traverses the subfolders of /data/ and reads each unknown document, calculates the Euclidean 
 * 		distances, and outputs the k nearest neighbors to the console and to a file in the main  
 * 		directory called knn.txt.
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
/**
 * @author Jane Liu
 * Homework 4
 * 
 * Class:
 * 	KNN:
 * 		Accepts a list of topics, the tf-idf matrix of the corpus, and the k-value for the KNN algorithm. It opens a document 
 * 		file with the file names and paths of all the unknown documents, calculates the Euclidean distances, and outputs the k nearest
 * 		neighbors to the console and to a file in the working directory called knn.txt.
 */

public class KNN {

	private List<String> topics = new ArrayList<String>();
	private List<double[]> corpus_matrix = new ArrayList<double[]>();
	private List<double[]> matrix_of_unknowns = new ArrayList<double[]>();
	private List<double[]> temprow = new ArrayList<double[]>();
	private DocMatrix unknown_row = new DocMatrix();
	private int k = 0;
	private int doc_count = 0;
	boolean unknown_flag = true;

	public KNN(List<String> ngrams, List<double[]> corpus_matrix, int k) {
		this.topics = ngrams;
		this.corpus_matrix = corpus_matrix;		
		this.k = k;		
	}

	public void findNearestNeighbors(TreeMap<String, Integer> labels_map) {

		List<String> docs = new ArrayList<String>();
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
			unknown_row.generate(topics, unknown_flag, docname);
			temprow = unknown_row.get();
			row = temprow.get(0);
			matrix_of_unknowns.add(row);
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
		List<Double> neighbors = new ArrayList<Double>();
		TreeMap<Double, String> nearest_neighbors = new TreeMap<Double, String>();
		
		// delete output file knn.txt if it already exists
		File f = new File("knn.txt");
		if(f.exists()){f.delete();}

		for(int i=0; i<matrix_unknowns.length; i++) {
			
			double[] distances = new double[doc_count];

			// array of doubles containing the distance between each document of the corpus to the unknown document.
			distances = getEuclidDist(matrix_unknowns[i], matrix_corpus);
			
			int index = 0;
			int num_of_articles = 0;
			
			for(Map.Entry<String, Integer> key : labels_map.entrySet()){
			
	    		String label = key.getKey();
				num_of_articles += (int)key.getValue();
				
				for(int j=index; j<(num_of_articles); j++) {
					nearest_neighbors.put(distances[j], label);
				}
				index = num_of_articles;
			
	    	}

		    // output the k nearest neighbors to knn.txt
		    try {
	            FileWriter writer = new FileWriter("knn.txt", true);
	            BufferedWriter bufferedWriter = new BufferedWriter(writer);

				int k_count = k;
			    for(Map.Entry<Double,String> key : nearest_neighbors.entrySet()){ 
			    	if(k_count>0) {
			    		System.out.println("Distance: " + key.getKey() + ", Label: " + key.getValue());
			    		bufferedWriter.write("Distance: " + key.getKey() + ", Label: " + key.getValue() + "\n");
			    	}
			    	k_count--;
			    }
			    System.out.println("\n");
	            bufferedWriter.newLine();
	            bufferedWriter.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
			neighbors.clear();
			nearest_neighbors.clear();
		}
	}

	public static double[] getEuclidDist(double[] row_unknown, double[][] matrix_corpus) {
		
		double[] distances = new double[matrix_corpus.length];
		double unknown_mval = 0;
		double known_mval = 0;
		double curr_dist = 0;	

		for(int i=0; i<matrix_corpus.length; i++) {
			for(int j=0; j<matrix_corpus[i].length; j++) {
				unknown_mval = row_unknown[j];
				known_mval = matrix_corpus[i][j];
				curr_dist += Math.sqrt(Math.pow((unknown_mval - known_mval), 2));
			}
			distances[i] = curr_dist;
			curr_dist = 0;
		}
		return distances;
	}	

}