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
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class KNN {
	
	private String raw_doc = "";
	private List<String> topics = new ArrayList<String>();
	private Preprocess tfidf_matrix = new Preprocess();
	private DocMatrix temp_matrix = new DocMatrix();
	private double dist = 0.0;
	private int k = 0;
	
	public KNN(List<String> ngrams, String raw_text, Preprocess corpus_matrix, int k) {
		this.topics = ngrams;
		this.raw_doc = raw_text;
		this.tfidf_matrix = corpus_matrix;		
	}
	
	public void findNearestNeighbors() {
		

		
	}
	
	public void find_euclid_dist(double[] doc1, double[] doc2) {

		double[] eucdist = new double[doc1.length];
		
		for(int i=0; i<doc1.length; i++) {
			
		}
	}
	
}
