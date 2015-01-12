package edu.buffalo.cse.irf14;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import edu.buffalo.cse.irf14.analysis.InputTokenAnalyzer;
import edu.buffalo.cse.irf14.analysis.TokenStream;
import edu.buffalo.cse.irf14.analysis.Tokenizer;
import edu.buffalo.cse.irf14.analysis.TokenizerException;
import edu.buffalo.cse.irf14.index.IndexReader;
import edu.buffalo.cse.irf14.index.IndexType;
import edu.buffalo.cse.irf14.query.Node;
import edu.buffalo.cse.irf14.query.Query;
import edu.buffalo.cse.irf14.query.QueryParser;

/**
 * Main class to run the searcher.
 * As before implement all TODO methods unless marked for bonus
 * @author nikhillo
 *
 */
public class SearchRunner {
	
	public enum ScoringModel {TFIDF, OKAPI};
	
	private PrintStream stream;
	private char mode;
	private String indexDir;
	private String corpusDir;
	private IndexReader indexReader = null;
	private static final int SNIPPETLENGTH = 30;
	List<String> termsList = new ArrayList<String>();
	DecimalFormat dFormat = new DecimalFormat("#.#####");
	
	/**
	 * Default (and only public) constuctor
	 * @param indexDir : The directory where the index resides
	 * @param corpusDir : Directory where the (flattened) corpus resides
	 * @param mode : Mode, one of Q or E
	 * @param stream: Stream to write output to
	 * @throws Exception 
	 */
	public SearchRunner(String indexDir, String corpusDir, 
			char mode, PrintStream stream) {	
		
		this.indexDir = indexDir;
		this.corpusDir = corpusDir;
		this.mode = mode;
		this.stream = stream;
		indexReader = new IndexReader(indexDir, IndexType.TERM);
	}
	
	public void sanityCheck() throws Exception
	{
		if(stream == null || corpusDir == null || indexDir == null)
			throw new Exception("Initialization Parameters Invalid");
		
		if(indexDir.equals("") || corpusDir.equals("") || mode!='Q' || mode!='E')
			throw new Exception("Initialization Parameters Invalid.");
		
		File indexFile = new File(indexDir);
		File corpusFile = new File(corpusDir);
		if(!(indexFile.exists() || corpusFile.exists()))
			throw new Exception("Initialization Parameters Invalid.");		
	}
	
	/**
	 * Method to execute given query in the Q mode
	 * @param userQuery : Query to be parsed and executed
	 * @param model : Scoring Model to use for ranking results
	 */
	public void query(String userQuery, ScoringModel model) {
		
		long startTime = new Date().getTime();
		Query query = QueryParser.parse(userQuery, QueryParser.OR);
				
		LinkedList<Node> ll = query.traverseInOrder();
		ArrayList<String> keySet = null;
		String op = null;
		
		while(true && ll.size() > 0)
		{
			Node n = ll.remove();
			String x = n.getText();
			if(n.getText().contains("Term:"))
			{
				if(n.getText().contains("<Term:"))
				{
					x = x.substring(6);
				}
				else
				{
					x = x.substring(5);	
				}
				indexReader.setIndexType(IndexType.TERM);
				termsList.add(x);
			}
			else
				if(n.getText().contains("Place:"))
				{
					if(n.getText().contains("<Place:"))
					{
						x =x.substring(7);
					}
					else
					{					
					x = x.substring(6);
					}
					indexReader.setIndexType(IndexType.PLACE);
				}
				else
					if(n.getText().contains("Author:"))
					{
						if(n.getText().contains("<Author:"))
						{ 
							x =x.substring(8);
						}
						else
						{
						
						x = x.substring(7);
						}
						indexReader.setIndexType(IndexType.AUTHOR);
					}
					else
						if(n.getText().contains("Category:"))
						{
							if(n.getText().contains("<Category:"))
							{
								x =x.substring(10);
							}
							else
							{
							
							x = x.substring(9);
							}
							indexReader.setIndexType(IndexType.CATEGORY);
						}
						else
							if(n.getText().equals("OR") || n.getText().equals("AND"))
							{
								op = n.getText();
								continue;
							}
			
			if(x.charAt(x.length()-1) == '>')
			{
				x = x.substring(0, x.length()-1);
				op = "NOT";
			}
			
			//Apply Filters
			
			try {
				Tokenizer t = new Tokenizer();
				TokenStream stream = t.consume(x);
				InputTokenAnalyzer ita = new InputTokenAnalyzer(stream);
				
				while(ita.increment())
				{
					
				}	
				
				if(stream.getTokenList().get(0) == null)
					continue;
				else
					x = stream.getTokenList().get(0).toString();
			}
			catch(Exception e) {
				//e.printStackTrace();
			}
			
			
			Map<String, Integer> map = indexReader.getPostings(x);
			keySet = doOp(keySet,op,new ArrayList<String>(map.keySet()));			
		}
		
		//get query terms
		HashMap<String,Integer> terms = getQueryTerms(query);
		
		//pass terms and docs to scorer
		HashMap<String,Double> results = null;
		
		if(terms.size()==0)
		{
			results = new HashMap<String,Double>();
			for(String doc:keySet)
			{				
				results.put(doc, 1.0);
			}
		}
		else
		{
			if(model==ScoringModel.TFIDF)
			{
				results = tfidf(terms,keySet);
			}
			else
			{
				results = okapi(terms,keySet);
			}
		}
		
		//sort results
		ValueComparator comp =  new ValueComparator(results);
		TreeMap<String,Double> ranked = new TreeMap<String,Double>(comp);

        ranked.putAll(results);
        
        long endTime = new Date().getTime();

        //System.out.println("results: "+ranked);
        
             	
		//Print Results		
		stream.println("Query:" + userQuery);
		stream.println("Query Time:" + (endTime - startTime) + "ms");
		stream.println();
		int i = 1;
		for(String s : ranked.keySet())
		{
			stream.println("Result Rank:" + i);
			stream.println("Title:" + getTitle(getProperDocName(s)));
			stream.println("Snippet:" + getSnippet(termsList,s));
			stream.println("Result Relevancy:" + ranked.get(s));
			i++;
			if(i>10)
				break;
			stream.println();
		}		
	}
	
	private void queryForFiles(HashMap<String,String> queryStList)
	{
	
		HashMap<String,TreeMap<String,Double>> resultList = new HashMap<String,TreeMap<String,Double>>();
		int noofresults = 0;
		for(String queryID : queryStList.keySet())
		{
			
		Query query = QueryParser.parse(queryStList.get(queryID), QueryParser.OR);
				
		LinkedList<Node> ll = query.traverseInOrder();
		ArrayList<String> keySet = null;
		String op = null;
		
		
		while(true && ll.size() > 0)
		{
			Node n = ll.remove();
			String x = n.getText();
			if(n.getText().contains("Term:"))
			{
				if(n.getText().contains("<Term:"))
				{
					x = x.substring(6);
				}
				else
				{
					x = x.substring(5);	
				}
				indexReader.setIndexType(IndexType.TERM);
				termsList.add(x);
			}
			else
				if(n.getText().contains("Place:"))
				{
					if(n.getText().contains("<Place:"))
					{
						x =x.substring(7);
					}
					else
					{					
					x = x.substring(6);
					}
					indexReader.setIndexType(IndexType.PLACE);
				}
				else
					if(n.getText().contains("Author:"))
					{
						if(n.getText().contains("<Author:"))
						{ 
							x =x.substring(8);
						}
						else
						{
						
						x = x.substring(7);
						}
						indexReader.setIndexType(IndexType.AUTHOR);
					}
					else
						if(n.getText().contains("Category:"))
						{
							if(n.getText().contains("<Category:"))
							{
								x =x.substring(10);
							}
							else
							{
							
							x = x.substring(9);
							}
							indexReader.setIndexType(IndexType.CATEGORY);
						}
						else
							if(n.getText().equals("OR") || n.getText().equals("AND"))
							{
								op = n.getText();
								continue;
							}
			
			if(x.charAt(x.length()-1) == '>')
			{
				x = x.substring(0, x.length()-1);
				op = "NOT";
			}
			
			
			//Apply Filters
			
			try {
				Tokenizer t = new Tokenizer();
				TokenStream stream = t.consume(x);
				InputTokenAnalyzer ita = new InputTokenAnalyzer(stream);
				
				while(ita.increment())
				{
					
				}	
				
				if(stream.getTokenList().get(0) == null)
					continue;
				else
					x = stream.getTokenList().get(0).toString();
			}
			catch(Exception e) {
				//e.printStackTrace();
			}
			
			
			Map<String, Integer> map = indexReader.getPostings(x);
			keySet = doOp(keySet,op,new ArrayList<String>(map.keySet()));			
		}
		
		//get query terms
		HashMap<String,Integer> terms = getQueryTerms(query);
		
		//pass terms and docs to scorer
		HashMap<String,Double> results = null;
		
		if(terms.size()==0)
		{
			results = new HashMap<String,Double>();
			for(String doc:keySet)
			{				
				results.put(doc, 1.0);
			}
		}
		else
		{		
			results = tfidf(terms,keySet);		
		}
		
		//sort results
		ValueComparator comp =  new ValueComparator(results);
		TreeMap<String,Double> ranked = new TreeMap<String,Double>(comp);

        ranked.putAll(results);
        noofresults += ranked.size();
        resultList.put(queryID, ranked);
	}
		
		stream.println("noOfResults:" + noofresults);
		for(String s : resultList.keySet())
		{
			stream.print(s + ":{");
			
			TreeMap<String,Double> map = resultList.get(s);
			for(String st : map.keySet())
			{
				stream.print(getProperDocName(st) + "#" + dFormat.format(map.get(st)));
			}			
			stream.println("}");
		}		
	}
	private String getTitle(String documentName)
	{
		try {
		File file = new File(corpusDir+File.separator+documentName);
		FileReader fReader = new FileReader(file);
		BufferedReader bReader = new BufferedReader(fReader);
		String text = null;
		
		while((text = bReader.readLine()) != null)
		{
			if(text.equals(""))
			{
				continue;
			}
			else
			{
				break;
			}
		}
		
		fReader.close();
		bReader.close();
				
		return text;
		}
		catch(IOException e )
		{
			e.printStackTrace();
		}
		return "";
	}
	
	private String getSnippet(List<String> terms,String documentName)
	{
		
		File file = new File(corpusDir + File.separator + getProperDocName(documentName));
		try {
			
			FileReader fReader = new FileReader(file);
			BufferedReader bReader = new BufferedReader(fReader);
			String text = null;
			StringBuilder sb = new StringBuilder();
			
			while((text = bReader.readLine())!=null)
			{
				sb.append(text+" ");
			}			
			
			fReader.close();
			bReader.close();
			
			String[] arr = sb.toString().split("\\s");
			
			if(terms == null || terms.size() == 0)
			{
				StringBuilder sba = new StringBuilder();
				int i = 0;
				for(String s : arr)
				{
					if(i == SNIPPETLENGTH)
					{
						return sba.toString();
					}
					sba.append(s + " " );
					i++;
					if(i == 30)
						break;
				}
				return sba.toString();
			}
			
			Integer docID = indexReader.getDocID(documentName);
			Map<Integer,String> result = new TreeMap<Integer,String>(Collections.reverseOrder());
			Map<String,List<Integer>> position = new HashMap<String,List<Integer>>();
			
			for(String s : terms)
			{
				Integer termID = indexReader.getTermID(s);
				Integer i = indexReader.getTermFrequency(termID, docID);
				result.put(i, s);
				
				List<Integer> posList = indexReader.getPositionWithinDocument(docID, termID);
				position.put(s, posList);
			}
			
			int possibilities = (int) Math.ceil(arr.length /5);
			
			int[] countofTerms = new int[possibilities];
			int startIndex = 0;
			int endIndex = 30;
			
			for(int i = 0 ; i < possibilities; i++)
			{				
				for(String s : position.keySet())
				{
					List<Integer> list = position.get(s);
					if(list == null)
						continue;
					for(Integer it : list)
					{
						if(it >= startIndex && it <endIndex)
						{
							countofTerms[i] = countofTerms[i] + 1;
						}
					}					
				}
				startIndex += 5;
				endIndex += 5;
			}
			
			int max = 0;
			int maxIndex = -1;
			for(int i = 0 ; i<countofTerms.length ; i++)
			{
				if(countofTerms[i] > 0)
				{
					maxIndex = i;
					max = countofTerms[i];
				}
			}
			
			startIndex = 0 + maxIndex*5;
			
			StringBuilder sbuilder = new StringBuilder();
			
			for(int i = startIndex ; i <startIndex+SNIPPETLENGTH || i!=arr.length ; i++)
			{
				sbuilder.append(arr[i]);
				sbuilder.append(" ");
			}
			return sbuilder.toString();
			
			 
					
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	private String getProperDocName(String docName)
	{
		String[] arr = docName.split("\\" + File.separator);
		return arr.length == 2?arr[1]:arr[0];
	}
	
	private ArrayList<String> doOp(ArrayList<String> existingKeySet, String operation, ArrayList<String> newKeySet)
	{
		if(existingKeySet == null || operation == null)
			return newKeySet;
		
		if(newKeySet == null)
			return existingKeySet;
		
		if(operation.equals("AND"))
		{
			existingKeySet.retainAll(newKeySet);
			return existingKeySet;
		}
		
		if(operation.equals("OR"))
		{
			newKeySet.removeAll(existingKeySet);
			existingKeySet.addAll(newKeySet);
			return existingKeySet;
		}
		
		if(operation.equals("NOT"))
		{
			existingKeySet.removeAll(newKeySet);
			return existingKeySet;
		}
		
		return null;
	}
	
	/**
	 * Method to execute queries in E mode
	 * @param queryFile : The file from which queries are to be read and executed
	 * @throws Exception 
	 */
	
	public void query(File queryFile) {
		
		FileReader fReader;
		try {
			fReader = new FileReader(queryFile);
			BufferedReader bReader = new BufferedReader(fReader);
			
			String text = null;
			boolean isFirstLine = true;
			int noOfQueries = 0;
			HashMap<String,String> map = new HashMap<String,String>();
			while((text=bReader.readLine())!=null)
			{
				if(text.equals(""))
					continue;
				if(isFirstLine)
				{
					isFirstLine = false;
					String[] srr = text.split("=");
					if(srr.length == 2)
					{
						try {
						noOfQueries = Integer.parseInt(srr[1]); 
						}
						catch(NumberFormatException e)
						{
							e.printStackTrace();
						}
					}
					 
				}
				else 
				{
					String[] srr = text.split(":");
					if(srr.length == 2)
					{
						String queryId = srr[0];
						String queryString = srr[1];
						StringBuilder sb = new StringBuilder(queryString);
						
						for(int i = 0; i<sb.length(); i++)
						{
							if(sb.charAt(i) == '{' || sb.charAt(i) == '}')
							sb.setCharAt(i, ' ');
						}
						
						queryString = sb.toString().trim();		
						map.put(queryId, queryString);
						
						
					}
				}
			}		
			
			
			queryForFiles(map);
			bReader.close();
			fReader.close();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
	}
	
	/**
	 * General cleanup method
	 */
	public void close() {
		
		this.stream.close();
	}
	
	/**
	 * Method to indicate if wildcard queries are supported
	 * @return true if supported, false otherwise
	 */
	public static boolean wildcardSupported() {
		//TODO: CHANGE THIS TO TRUE ONLY IF WILDCARD BONUS ATTEMPTED
		return false;
	}
	
	/**
	 * Method to get substituted query terms for a given term with wildcards
	 * @return A Map containing the original query term as key and list of
	 * possible expansions as values if exist, null otherwise
	 */
	public Map<String, List<String>> getQueryTerms() {
		//TODO:IMPLEMENT THIS METHOD IFF WILDCARD BONUS ATTEMPTED
		return null;
		
	}
	
	/**
	 * Method to indicate if speel correct queries are supported
	 * @return true if supported, false otherwise
	 */
	public static boolean spellCorrectSupported() {
		//TODO: CHANGE THIS TO TRUE ONLY IF SPELLCHECK BONUS ATTEMPTED
		return false;
	}
	
	/**
	 * Method to get ordered "full query" substitutions for a given misspelt query
	 * @return : Ordered list of full corrections (null if none present) for the given query
	 */
	public List<String> getCorrections() {
		//TODO: IMPLEMENT THIS METHOD IFF SPELLCHECK EXECUTED
		return null;
	}
	
	
	
	public HashMap<String,Double> tfidf(HashMap<String,Integer> terms, ArrayList<String> docList){
		
		HashMap<String,Double> results = new HashMap<String,Double>();
		double w = 0;
		
		if(docList!=null)
		{		
			for(String doc:docList)
			{
				w=0;
				double Nd = 0;
				double Nq = 0;
				int docID = indexReader.getDocID(doc);
				
				for(Map.Entry<String, Integer> term:terms.entrySet())
				{
					int termID = indexReader.getTermID(term.getKey());
					int tfq = term.getValue();
					int tf = indexReader.getTermFrequency(termID, docID);
					double idf = indexReader.getIDF(termID);
					double wd = Math.log10(1+tf) * idf;
					double wq = tfq * idf;
					
					Nd = Nd + Math.pow(wd, 2);
					Nq = Nq + Math.pow(wq, 2);					
					
					w = w + wd * wq;
				}
				
				w = w / (Math.sqrt(Nd) * Math.sqrt(Nq));
				results.put(doc, new Double(w));
			}
			
			/*for (Map.Entry<String, Double> docs: results.entrySet())
			{
				String doc = docs.getKey();
				double weight = docs.getValue();
				int docID = indexReader.getDocID(doc);
				
				int docLength = indexReader.getDocLength(docID);
				
				docs.setValue(weight/docLength);
			}*/
		}
		
		return results;
	}
	
	public HashMap<String,Integer> getQueryTerms(Query query){
		
		LinkedList<Node> ll = query.traverseInOrder();
		HashMap<String,Integer> queryTerms = new HashMap<String,Integer>();
		
		while(true && ll.size() > 0)
		{
			Node n = ll.remove();
			String x = n.getText();
						
			if(n.getText().contains("Term:"))
			{
				if(! n.getText().contains("<Term:"))
				{
					String temp = x.substring(5);
					
					if(queryTerms.containsKey(temp))
					{
						int tfq = queryTerms.get(temp);
						queryTerms.put(temp, tfq + 1);
					}
					else
					{
						queryTerms.put(temp,1);
					}
				}
			}	
		}
		
		return queryTerms;
	}
	
public HashMap<String,Double> okapi(HashMap<String,Integer> terms, ArrayList<String> docList){
	
		HashMap<String,Double> results = new HashMap<String,Double>();
		double w = 0;
		double k1=1.2, k3=8, b=0.75;
		double Lavg = indexReader.getAvgDocLength();
		double max= 0;
		
		if(docList!=null)
		{		
			for(String doc:docList)
			{	w=0;
				int docID = indexReader.getDocID(doc);
				int Ld = indexReader.getDocLength(docID);
								
				for(Map.Entry<String, Integer> term:terms.entrySet())
				{
					int termID = indexReader.getTermID(term.getKey());
					int tfq = term.getValue();
					float idf = indexReader.getIDF(termID);
					int tf = indexReader.getTermFrequency(termID, docID);
					
					w = w + idf * (((k1+1)*tf)/(k1*((1-b)+b*(Ld/Lavg))+tf)) * ((k3+1)*tfq/(k3+tfq));
					
				}
				
				results.put(doc, w);
				
				if(w>max)
					max=w;
				
			}
			
			for(Map.Entry<String, Double> res:results.entrySet())
			{
				String docID = res.getKey();
				double weight = res.getValue();
				
				weight = weight/max;
				
				results.put(docID, weight);
				
			}
		}
		
		return results;
	}
	

}
