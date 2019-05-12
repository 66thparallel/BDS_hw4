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
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class ClassifyTextualData {

	public static void main(String[] args) {

		int k = 5;	// the k-value for KNN
		TreeMap<String, Integer> labels_map = new TreeMap<String, Integer>();
		
		labels_map = getLabels();
		
		List<double[]> corpus_matrix = new ArrayList<double[]>();
		List<String> topics = new ArrayList<String>();

		Preprocess prep_data = new Preprocess();
		corpus_matrix = prep_data.start();
		topics = prep_data.get_ngrams();

		KNN _knn = new KNN(topics, corpus_matrix, k);				// output the k nearest neighbors
		_knn.findNearestNeighbors(labels_map);						// pass a treemap of categories and number of articles in each category

	}
	
	public static TreeMap<String, Integer> getLabels() {
		
		TreeMap<String, Integer> labels_map = new TreeMap<String, Integer>();
		
		// get folder names (labels) and the number of documents in each folder. Place in a treemap of labels and file counts.
		BufferedReader reader1;
		try {
			reader1 = new BufferedReader(new FileReader("data/corpus/known_docs.txt"));
			String line = reader1.readLine();

			while (line != null) {
				String[] temp = line.split("/");
				String key = temp[0];
				int value = 0;
				if(labels_map.containsKey(key)) {
					value = (int)labels_map.get(key) + 1;
					labels_map.put(key, value);						
				} else {
					value = 1;
					labels_map.put(key, value);
				}
				
				line = reader1.readLine();
			}
			reader1.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return labels_map;
	}
	
}






