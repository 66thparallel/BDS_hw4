package hw4;
	
/**
 * @author Jane Liu
 * Homework 4
 * 
 * Class:
 * 	DocMatrix:
 * 		Generates a TF-idf document term matrix. Depending on parameters passed the generate function can create 
 * 		and return a TF-idf matrix for the corpus or for a single unknown document.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class DocMatrix {

	private List<double[]> matrix = new ArrayList<double[]>();
	private int total_docs = 24;


	public void generate(List<String> ngramList, boolean unknowndoc, String docname) {

		List<String> docs = new ArrayList<String>();
		String filename = "data/known_docs.txt";
		int topics_size = ngramList.size();

		// checks if generating a document matrix for the entire corpus
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

		List<String> tempwords = new ArrayList<String>();
		List<String> tokens = new ArrayList<String>();
		List<Double> document = new ArrayList<Double>();
		double[] row = new double[topics_size];
		int[] topic_count = new int[topics_size];
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

				for (String t : tokens) { if (t==null || t=="") {} else {text += t + " ";} }
				tokens_count = tokens.size();

				for (int i=0; i<ngramList.size(); i++) {
					count = countMatches(text, ngramList.get(i));
					document.add((double)count);

					if (count>0) {
						topic_count[i]++;
					}
				}

				for(int i=0; i<topics_size; i++) {
					term_frequency = document.get(i) / tokens_count;
					if (term_frequency > (double)0.001) {
						row[i] = term_frequency * (Math.log(total_docs/topic_count[i]));
					} else {
						row[i] = 0f;
					}
				}

				this.matrix.add(row);

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

	public static boolean isEmpty(String str) {
	      return str == null || str.length() == 0;
	}

}