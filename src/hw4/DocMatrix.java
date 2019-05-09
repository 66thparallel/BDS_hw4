package hw4;

import java.io.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.lang.*; 

/**
 * @author Jane Liu
 * 
 * Class:
 * 	DocMatrix:
 * 		Generates a TF-idf document term matrix. Depending on parameters passed the generate function can create 
 * 		and return a TF-idf matrix for the entire corpus or for one unknown document.
 */


public class DocMatrix {
	
	private List<double[]> matrix = new ArrayList<double[]>();
	private int total_docs = 24; // total number of documents in corpus

	
	public void generate(List<String> ngramList, boolean unknowndoc, String docname) {
		
		List<String> docs = new ArrayList<String>();
		String filename = "data/known_docs.txt";
		int topics_size = ngramList.size();
		
		// if generating a document matrix for the entire corpus
		if (unknowndoc==false) {	
			BufferedReader reader;
			try {
				reader = new BufferedReader(new FileReader(filename));
				String line = reader.readLine();
	
				while (line != null) {
					docs.add(line);
					line = reader.readLine();
				}
				reader.close();
	
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			docs.add(docname);
		}
		
		// create tf-idf matrix
		List<String> tempwords = new ArrayList<String>();
		List<String> tokens = new ArrayList<String>();
		List<Double> document = new ArrayList<Double>();
		double[] row = new double[topics_size];
		int[] topic_count = new int[topics_size]; // number of documents that contain the topic
		String text = "";
		int count = 0;
		int tokens_count = 0;
		double term_frequency = 0f;
				
		BufferedReader reader2;
		
		for (String doc : docs) {
			String file = "data/" + doc;
			try {
				reader2 = new BufferedReader(new FileReader(file));
				String line = reader2.readLine();

				while (line != null) {
					String[] temp = line.split("\\s+");
					for (String t : temp) { tempwords.add(t); }
					line = reader2.readLine();
				}
				reader2.close();
								
				// clean and remove punctuation, weird characters, etc. 
				for (String word : tempwords) {
					if(word=="" || word==null || word=="–") {
					}else {
						word = word.replaceAll("[!@#$%^&+=.,:;’'\"�()?]+", "");
						word = word.replaceAll("[0-9]+", "");
						word = word.replaceAll("^-", "");
						word = word.replaceAll("-$", "");
						word = word.replaceAll("\\]", "");
						word = word.replaceAll("\\[", "");
						tokens.add(word);
					}
				}
				
				// put tokens of corpus into a string object (for countMatches function)
				for (String t : tokens) { if (t==null || t=="") {} else {text += t + " ";} }
				tokens_count = tokens.size();
				
				// count number of times a term appears in the text
				for (int i=0; i<ngramList.size(); i++) {
					count = countMatches(text, ngramList.get(i));
					document.add((double)count);
					
					// increment the topic_count array if the topic is found in a document (for idf)
					if (count>0) {
						topic_count[i]++;
					}
				}
				
				// calculate the tf-idf value of each topic in the matrix
				for(int i=0; i<topics_size; i++) {
					term_frequency = document.get(i) / tokens_count;
					if (term_frequency > (double)0.001) {
						row[i] = term_frequency * (Math.log(total_docs/topic_count[i]));
					} else {
						row[i] = 0f;
					}
				}
				
				// add row of tf-idf values to the document term matrix
				this.matrix.add(row);
				
				// resets all arrays, lists, counters for the next document to be read				
				tempwords.clear();
				tokens.clear();
				document.clear();
				row = new double[topics_size];
				text = "";
				count = 0;
				tokens_count = 0;

			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
	}
	
	public List<double[]> get() {
		return matrix;
	}
	
	// counts the number of times a word appears in a string of text
	public static int countMatches(String str, String sub) {
	      if (isEmpty(str) || isEmpty(sub)) { return 0; }
	      int count = 0;
	      int idx = 0;
	      while ((idx = str.indexOf(sub, idx)) != -1) {
	          count++;
	          idx += sub.length();
	      }
	      return count;
	}
	// checks if a string is empty (for countMatches function)
	public static boolean isEmpty(String str) {
	      return str == null || str.length() == 0;
	}

}
