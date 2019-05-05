package hw4;

/**
 * @author Jane Liu
 * Homework 4
 * 
 * Preprocess class:
 * 	Reads the unknown files, cleans the data, tokenizes, removes stopwords, lemmatizes, finds the most frequent 
 * 	unigrams and bigrams, creates the document term matrix and transforms with TF-IDF. Returns the processed corpus.
 *
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

import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.patterns.surface.Token;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;


public class Preprocess {
	
	public static void start() {
		
		List<String> docs = new ArrayList<String>();
		List<String> stopwords = new ArrayList<String>();
		List<String> tempwords = new ArrayList<String>();
		List<String> tokens = new ArrayList<String>();
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
		
		// tokenize
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

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
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
		}tempwords.clear();
		
		// remove stopwords
		try {
			reader = new BufferedReader(new FileReader("data/stopwords.txt"));
			String line2 = reader.readLine();

			while (line2 != null) {
				String[] temp2 = line2.split(",");
				for (String t : temp2) {
					stopwords.add(t);
				} 
				line2 = reader.readLine();
			}
			reader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		for (String word : stopwords) {
			String str = word;
			String capit = str.substring(0, 1).toUpperCase() + str.substring(1);
			if(tokens.contains(word) || tokens.contains(word.toUpperCase()) || tokens.contains(capit)) { 
				tokens.removeAll(Collections.singleton(word));
				tokens.removeAll(Collections.singleton(word.toUpperCase()));
				tokens.removeAll(Collections.singleton(capit)); 
			}
		}
		tokens.removeAll(Collections.singleton(""));

//		// lemmatize the corpus with Stanford NLP
//        String text = "";
//        for (String token : tokens) {
//        	text += token + " ";
//        }
//        tokens.clear();
//        
//        List<String> lems = new ArrayList<String>();
//        lems = lemmatize(text);
//        
//        for (String l : lems) { 
//        	if (l.matches("/s")){
//        	} else {
//        		tokens.add(l);
//        	} 
//        }
		
		// Find ngrams
		List<String> ngramList = new ArrayList<String>();
		Map<String, Integer> unigrams = new HashMap<String, Integer>();
		Map<String, Integer> bigrams = new HashMap<String, Integer>();
		
		// unigrams
		for (String grams: tokens) {
			if (unigrams.containsKey(grams)) { unigrams.put(grams, unigrams.get(grams) + 1); } 
			else { unigrams.put(grams, 1); }
		}
				
		// bigrams
		int k = 1;
		int n = 2;
		String ngramstr = "";
		for (int j=0; j<tokens.size()-n; j++) {
			ngramstr = tokens.get(j);			
			while (k < n) {
				ngramstr += " " + tokens.get(j+k);
				k++;
			}
			k = 1;
			ngramList.add(ngramstr);
		}
		for (String grams: ngramList) {
			if (bigrams.containsKey(grams)) { bigrams.put(grams, bigrams.get(grams) + 1); } 
			else { bigrams.put(grams, 1); }
		}ngramList.clear();
		
		// get top 30 most frequent unigrams and bigrams
		Map<String, Integer> ngrams_temp = new HashMap<String, Integer>();
		n = 30;
		
		// extract top 30 unigrams
		List<Entry<String, Integer>> max_one = getMax(unigrams, n);
		for (Entry<String, Integer> entry : max_one) { 
			String key = entry.getKey();
			int val = entry.getValue();
			ngrams_temp.put(key, val);
		} 
		// extract top 30 bigrams
		List<Entry<String, Integer>> max_two = getMax(bigrams, n);
		for (Entry<String, Integer> entry : max_two) {
			String key = entry.getKey();
			int val = entry.getValue();
			ngrams_temp.put(key, val);
		}

		// find the top 30 out of 60 most frequent ngrams
		List<Entry<String, Integer>> top_ngrams = getMax(ngrams_temp, n);
		System.out.println("Top " + n + " unigrams and bigrams: ");
		for (Entry<String, Integer> entry : top_ngrams) {
			String key = entry.getKey();
			int val = entry.getValue();
			ngramList.add(key);
			System.out.println(key + ": " + val);
		}

		DocMatrix docmatrix = new DocMatrix();
		docmatrix.generate(ngramList);
		
	}
	
	private static List<String> lemmatize(String documentText) {

		// set up pipeline properties
	    Properties props = new Properties();
	    props.setProperty("annotators", "tokenize, ssplit, pos, lemma,ner");
	
	    // set a property for an annotator, in this case the coref annotator is being set to use the neural algorithm
	    props.setProperty("coref.algorithm", "neural");
	    
	    // build pipeline
	    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
	
	    List<String> lemmas = new LinkedList<String>();
	    
	    // Create an empty Annotation just with the given text
	    Annotation document = new Annotation(documentText);
	    
	    // run all Annotators on this text
	    pipeline.annotate(document);
	    
	    // Iterate over all of the sentences found
	    List<CoreMap> sentences = document.get(SentencesAnnotation.class);
	    
	    for(CoreMap sentence: sentences) {
	        // Iterate over all tokens in a sentence
	        for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
	            // Retrieve and add the lemma for each word into the
	            // list of lemmas
	            lemmas.add(token.get(LemmaAnnotation.class));
	        }
	    }
	    return lemmas;
	}
	
	// get top unigrams and bigrams
	private static <K, V extends Comparable<? super V>> List<Entry<K, V>> getMax(Map<K, V> map, int n) {
		Comparator<? super Entry<K, V>> comparator = new Comparator<Entry<K, V>>() {
			@Override
			public int compare(Entry<K, V> e0, Entry<K, V> e1) {
				V v0 = e0.getValue();
				V v1 = e1.getValue();
				return v0.compareTo(v1);
			}
		};
		PriorityQueue<Entry<K, V>> highest = new PriorityQueue<Entry<K, V>>(n, comparator);
		for (Entry<K, V> entry : map.entrySet()) {
			highest.offer(entry);
			while (highest.size() > n) {
				highest.poll();
			}
		}
		List<Entry<K, V>> result = new ArrayList<Map.Entry<K, V>>();
		while (highest.size() > 0) {
			result.add(highest.poll());
		}
		return result;
	}

}
