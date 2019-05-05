package hw4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jane Liu
 *
 */


public class DocMatrix {
	
	public static void generate(List<String> freq_ngrams) {
		
		List<String> ngramList = freq_ngrams;
		List<String> docs = new ArrayList<String>();
		List<String> tempwords = new ArrayList<String>();
		String filename = "data/documents.txt";

		// read list of file names
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
		
		// create TF-IDF matrix (size is 10 x 30)
		List<String> matrix = new ArrayList<String>();
		List<Float> documents = new ArrayList<Float>();
		List<Float> term_count = new ArrayList<Float>();
				
		BufferedReader reader3;
		
		for (String doc : docs) {
			String file = "data/" + doc;
			try {
				reader3 = new BufferedReader(new FileReader(file));
				String line = reader3.readLine();

				while (line != null) {
					System.out.println(line);
					for (String ngram : ngramList ) {
						boolean isFound = line.contains(ngram);
						if (isFound==false) {
							documents.add(0f);
						}
					}
					
//					String[] temp = line.split("\\s+");
										
//					// clean and remove punctuation, weird characters, etc. 
//					for (String word : tempwords) {
//						if(word=="" || word==null || word=="–") {
//						}else {
//							word = word.replaceAll("[!@#$%^&+=.,:;’'\"�()?]+", "");
//							word = word.replaceAll("[0-9]+", "");
//							word = word.replaceAll("^-", "");
//							word = word.replaceAll("-$", "");
//							word = word.replaceAll("\\]", "");
//							word = word.replaceAll("\\[", "");
//							word = word.replaceAll(".", "");
//						}
//					}
					
					
					
					tempwords.clear();
					line = reader3.readLine();
					
				}
				reader3.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}
