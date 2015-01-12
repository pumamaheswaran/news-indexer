/**
 * 
 */
package edu.buffalo.cse.irf14.index;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author nikhillo
 * Class that emulates reading data back from a written index
 */
public class IndexReader {	
	
	
	private IndexType indexType = null;
	private String indexDir;
	
	//Index File Names
		private static final String  categoryIndexFileName = "CategoryIndex.ser";
		private static final String  authorIndexFileName = "AuthorIndex.ser";
		private static final String  termIndexFileName = "TermIndex.ser";
		private static final String  placeIndexFileName = "PlaceIndex.ser";
		private static final String  idfIndexFileName = "IDFIndex.ser";
		//private static final String  tfIndexFileName = "TFIndex.ser";
		private static final String  docLengthIndexFileName = "docLengthIndex.ser";
		private static final String  positionalIndexFileName = "PositionalIndex.ser";
		
		//Dictionary File Names
		private static final String  categoryDictionaryFileName = "CategoryDictionary.ser";
		private static final String  authorDictionaryFileName = "AuthorDictionary.ser";
		private static final String  documentDictionaryFileName = "DocumentDictionary.ser";
		private static final String  placeDictionaryFileName = "PlaceDictionary.ser";
		private static final String  termDictionaryFileName = "TermDictionary.ser";
		
		
		//DataStructures for Dictionary
		DualTreeMap<String,Integer> categoryDictionary = null;
		DualTreeMap<String,Integer> documentDictionary = null;
		DualTreeMap<String,Integer> placeDictionary = null;
		DualTreeMap<String,Integer> termDictionary = null;
		DualTreeMap<String,Integer> authorDictionary = null;
		
		//Datastructures for Index
		TreeMap<Integer,HashMap<Integer,Integer>> categoryIndex = null;
		TreeMap<Integer,HashMap<Integer,Integer>> placeIndex = null;
		TreeMap<Integer,HashMap<Integer,Integer>> termIndex = null;
		TreeMap<Integer,ArrayList<Integer>> authorIndex = null;
		
		HashMap<Integer,HashMap<Integer,ArrayList<Integer>>> positionalIndex = null;
		//HashMap<Integer,HashMap<Integer,Integer>> tfindex = null;
		HashMap<Integer,Float> idfIndex = null;
		HashMap<Integer,Integer> docLengthIndex = null;
		
	
	/**
	 * Default constructor
	 * @param indexDir : The root directory from which the index is to be read.
	 * This will be exactly the same directory as passed on IndexWriter. In case 
	 * you make subdirectories etc., you will have to handle it accordingly.
	 * @param type The {@link IndexType} to read from
	 */
	public IndexReader(String indexDir, IndexType type) {
		this.indexDir = indexDir;
		this.indexType = type;
		 loadFiles();
		
	}
	
	/**
	 * Get total number of terms from the "key" dictionary associated with this 
	 * index. A postings list is always created against the "key" dictionary
	 * @return The total number of terms
	 */
	public int getTotalKeyTerms() {	
		int i = 0;
		if(indexType == IndexType.AUTHOR)
		{
			i = authorDictionary.size();
		}
		else
			if(indexType == IndexType.CATEGORY)
			{
				i = categoryDictionary.size();
			}
			else
				if(indexType == IndexType.PLACE)
				{
					i = placeDictionary.size();
				}
				else
					if(indexType == IndexType.TERM)
					{
						i = termDictionary.size();
					}
						return i;
	}
	
	
	/**
	 * Get total number of terms from the "value" dictionary associated with this 
	 * index. A postings list is always created with the "value" dictionary
	 * @return The total number of terms
	 */
	public int getTotalValueTerms() {
		
		return documentDictionary.size();		
	}
	
	/**
	 * Method to get the postings for a given term. You can assume that
	 * the raw string that is used to query would be passed through the same
	 * Analyzer as the original field would have been.
	 * @param term : The "analyzed" term to get postings for
	 * @return A Map containing the corresponding fileid as the key and the 
	 * number of occurrences as values if the given term was found, null otherwise.
	 */
	public Map<String, Integer> getPostings(String term) {
		
		Map<String, Integer> docList = null;
		if(indexType == IndexType.AUTHOR)
		{
			Integer authorID = authorDictionary.getForwardReference(term);
			
			if(authorID!=null)
			{
				ArrayList<Integer> list = authorIndex.get(authorID);
				if(list!=null)
				{
					docList = new HashMap<String,Integer>();
					for(Integer i : list)
					{
						String fileName = documentDictionary.getBackWardReference(i);
						Integer in = 1;
						docList.put(fileName, in);
					}
				}
			}
		}
		else
			if(indexType == IndexType.CATEGORY)
			{
				Integer termID = categoryDictionary.getForwardReference(term);
				if(termID!=null)
				{
					HashMap<Integer,Integer> map = categoryIndex.get(termID);
					if(map!=null)
					{
						docList = new HashMap<String,Integer>();
						Set<Integer> keySet = map.keySet();
						
						for(Integer i : keySet)
						{
							String docName = documentDictionary.getBackWardReference(i);
							Integer occurances = map.get(i);
							docList.put(docName, occurances);
						}
					}
				
				}
			}
			else
				if(indexType == IndexType.PLACE)
				{

					Integer termID = placeDictionary.getForwardReference(term);
					if(termID!=null)
					{
						HashMap<Integer,Integer> map = placeIndex.get(termID);
						if(map!=null)
						{
							docList = new HashMap<String,Integer>();
							Set<Integer> keySet = map.keySet();
							
							for(Integer i : keySet)
							{
								String docName = documentDictionary.getBackWardReference(i);
								Integer occurances = map.get(i);
								docList.put(docName, occurances);
							}
						}
					
					}
				}
				else
					if(indexType == IndexType.TERM)
					{
						Integer termID = termDictionary.getForwardReference(term);
						if(termID!=null)
						{
							HashMap<Integer,Integer> map = termIndex.get(termID);
							if(map!=null)
							{
								docList = new HashMap<String,Integer>();
								Set<Integer> keySet = map.keySet();
								
								for(Integer i : keySet)
								{
									String docName = documentDictionary.getBackWardReference(i);
									Integer occurances = map.get(i);
									docList.put(docName, occurances);
								}
							}
						
						}
					}
		
		return docList;				
	}
	
	/**
	 * Method to get the top k terms from the index in terms of the total number
	 * of occurrences.
	 * @param k : The number of terms to fetch
	 * @return : An ordered list of results. Must be <=k fr valid k values
	 * null for invalid k values
	 */
	public List<String> getTopK(int k) {
		
		TreeMap<Integer,Integer> list = new TreeMap<Integer,Integer>(Collections.reverseOrder());
		List<String> returnList = new ArrayList<String>();
		
		for(Integer i : termIndex.keySet())
		{
			HashMap<Integer,Integer> map = termIndex.get(i);
			Integer in = 0;
			for(Integer j : map.keySet())
			{
				in = in + (map.get(j)==null?0:map.get(j));
			}
			list.put(in, i);
		}
		
		int count = 0;
		for(Integer i : list.keySet())
		{
			if(count == k)
			{
				break;
			}
			
			Integer termID = list.get(i);
			String term = termDictionary.getBackWardReference(termID);
			
			returnList.add(term);
			count++;
		}
		
		return returnList;
		
	}
	
	/**
	 * Method to implement a simple boolean AND query on the given index
	 * @param terms The ordered set of terms to AND, similar to getPostings()
	 * the terms would be passed through the necessary Analyzer.
	 * @return A Map (if all terms are found) containing FileId as the key 
	 * and number of occurrences as the value, the number of occurrences 
	 * would be the sum of occurrences for each participating term. return null
	 * if the given term list returns no results
	 * BONUS ONLY
	 */
	public Map<String, Integer> query(String...terms) {		
		
		ArrayList<Integer> docList = null;
		Map<String,Integer> returnMap = null;
		
		for(String s : terms)
		{
			Map<String, Integer> map = getPostings(s);
			if(returnMap==null)
			{
				returnMap = map;
			}
			else
			{
				returnMap = mergeTwoLists(returnMap,map);				
			}
			
		}
		
		return returnMap;		
	}
	
	private Map<String,Integer> mergeTwoLists(Map<String,Integer> a , Map<String,Integer> b)
	{
		Set<String> set1 = a.keySet();
		Set<String> set2 = b.keySet();
		Map<String,Integer> map = new HashMap<String,Integer>();
		ArrayList<String> list1 = new ArrayList<String>(set1);
		ArrayList<String> list2 = new ArrayList<String>(set2);
		
		list1.retainAll(list2);
		
		
		for(String s : list1)
		{
			Integer i = a.get(s) + b.get(s);
			map.put(s, i);
		}
		
		if(map.size() == 0)
			return null;
		
		return map;
		
	}
	
	
	
	
	private Object loadingHelper(String filename) throws IOException, ClassNotFoundException
	{
		File file = new File(filename);
		Object obj = null;
		if(file.exists())
		{
		FileInputStream f = new FileInputStream(file);
        ObjectInputStream o = new ObjectInputStream(f);
        obj =  o.readObject();
        o.close();
        f.close();
		}	
		return obj;
	}
	
	public void loadFiles() {
		try {
			categoryIndex = (TreeMap<Integer, HashMap<Integer, Integer>>) loadingHelper(indexDir+File.separator+categoryIndexFileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			termIndex = (TreeMap<Integer, HashMap<Integer, Integer>>) loadingHelper(indexDir+File.separator+termIndexFileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			placeIndex = (TreeMap<Integer, HashMap<Integer, Integer>>) loadingHelper(indexDir+File.separator+placeIndexFileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			authorIndex = (TreeMap<Integer, ArrayList<Integer>>) loadingHelper(indexDir+File.separator+authorIndexFileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			positionalIndex = (HashMap<Integer, HashMap<Integer, ArrayList<Integer>>>) loadingHelper(indexDir+File.separator+positionalIndexFileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			idfIndex = (HashMap<Integer, Float>) loadingHelper(indexDir+File.separator+idfIndexFileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			docLengthIndex = (HashMap<Integer, Integer>) loadingHelper(indexDir+File.separator+docLengthIndexFileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*try {
			tfIndex = (HashMap<String,HashMap<String,Integer>>) loadingHelper(indexDir+File.separator+tfIndexFileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		try {
			categoryDictionary = (DualTreeMap<String, Integer>) loadingHelper(indexDir+File.separator+categoryDictionaryFileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			authorDictionary = (DualTreeMap<String, Integer>) loadingHelper(indexDir+File.separator+authorDictionaryFileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			placeDictionary = (DualTreeMap<String, Integer>) loadingHelper(indexDir+File.separator+placeDictionaryFileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			documentDictionary = (DualTreeMap<String, Integer>) loadingHelper(indexDir+File.separator+documentDictionaryFileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			termDictionary = (DualTreeMap<String, Integer>) loadingHelper(indexDir+File.separator+termDictionaryFileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setIndexType(IndexType indexType) {
		this.indexType = indexType;
	}	
	
	public Integer getTermID(String term){		
		Integer id = termDictionary.getForwardReference(term);
		return id;
	}
	
	public Integer getDocID(String doc){		
		Integer id = documentDictionary.getForwardReference(doc);
		return id;
	}
	
	public Float getIDF(Integer termID){		
		if(idfIndex.get(termID)==null)
			return (float)0;
		else
		{
			Float idf = idfIndex.get(termID);
			return idf;
		}
		
	}
	
	public int getTermFrequency(Integer termID, Integer DocID){
		int freq = 0;
		try {
		HashMap<Integer,Integer> docList = null;
		docList = termIndex.get(termID);
		freq = docList.get(DocID);
		}
		catch(NullPointerException e)
		{
			freq = 0;
		}
		return freq;
	}
	
	public int getDocLength(Integer DocID){
		int docLength = docLengthIndex.get(DocID);
		return docLength;
	}
	
	public double getAvgDocLength(){
		long sum = 0;
		
		for(Map.Entry<Integer, Integer> doc: docLengthIndex.entrySet())
		{
			sum = sum + doc.getValue();
		}
		int N = documentDictionary.size();
		double avg = sum/N;
		return avg;
	}
	
		public List<Integer> getPositionWithinDocument(Integer documentID , Integer termID)
	{
		HashMap<Integer, ArrayList<Integer>> map = positionalIndex.get(termID);
		return map==null?null:map.get(documentID);
	}
}
