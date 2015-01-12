package edu.buffalo.cse.irf14.index;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import edu.buffalo.cse.irf14.analysis.Token;
import edu.buffalo.cse.irf14.analysis.TokenStream;

public class SerializationWriter extends Writer {
	
	//Counters
	private static Integer categoryCount = 1;
	private static Integer authorCount = 1;
	private static Integer documentCount = 1;
	private static Integer termCount =1;
	private static Integer placeCount = 1;
	
	//Index File Names
	private static final String  categoryIndexFileName = "CategoryIndex.ser";
	private static final String  authorIndexFileName = "AuthorIndex.ser";
	private static final String  termIndexFileName = "TermIndex.ser";
	private static final String  placeIndexFileName = "PlaceIndex.ser";
	private static final String  positionalIndexFileName = "PositionalIndex.ser";
	private static final String  tfindexFileName = "TFIndex.ser";
	private static final String  idfindexFileName = "IDFIndex.ser";
	private static final String  docLengthIndexFileName = "docLengthIndex.ser";
	
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
	HashMap<Integer,Float> idfindex = null;
	HashMap<Integer,Integer> docLengthIndex = null;
	
	private static SerializationWriter instance = null;
	
	private static String indexDir = null;
	
	
	private SerializationWriter()
	{
		documentDictionary = new DualTreeMap<String,Integer>();
		categoryDictionary = new DualTreeMap<String,Integer>();
		placeDictionary = new DualTreeMap<String,Integer>();
		termDictionary = new DualTreeMap<String,Integer>();
		authorDictionary = new DualTreeMap<String,Integer>();
		
		categoryIndex = new TreeMap<Integer,HashMap<Integer,Integer>>();
		placeIndex = new TreeMap<Integer,HashMap<Integer,Integer>>();
		termIndex = new TreeMap<Integer,HashMap<Integer,Integer>>();
		authorIndex = new TreeMap<Integer,ArrayList<Integer>>();
		positionalIndex = new HashMap<Integer,HashMap<Integer,ArrayList<Integer>>>();
		//tfindex = new HashMap<Integer,HashMap<Integer,Integer>>();
		idfindex = new HashMap<Integer,Float>();
		docLengthIndex = new HashMap<Integer,Integer>();
	}
	
	public static SerializationWriter getInstance(String indexDir)
	{
		SerializationWriter.indexDir = indexDir;
		if(instance == null)
		{
			instance = new SerializationWriter();
		}
		return instance;
	}	
	
	@Override
	public int processDocumentDictionary(String fileName) {
		
		Integer i = -1;
		
		if(documentDictionary.containsKey(fileName))
		{
			i = documentDictionary.getForwardReference(fileName);
		}
		else
		{
			i = SerializationWriter.documentCount++;
			documentDictionary.add(fileName, i);
		}
		return i.intValue();
	}

	@Override
	public void processTermIndex(TokenStream stream, Integer documentID) {
		
		stream.reset();
		while(stream.hasNext())
		{
			Token token = stream.next();
			String tokenText = token.toString();
			Integer termID = null;
			
		if(termDictionary.containsKey(tokenText))
		{
			termID = termDictionary.getForwardReference(tokenText);						
		}
		else
		{
			Integer i = SerializationWriter.termCount++;
			termDictionary.add(tokenText, i);
			termID = i;
		}
		
		
		if(termIndex.containsKey(termID))
		{
			HashMap<Integer,Integer> map = termIndex.get(termID);
			if(map.containsKey(documentID))
			{
				map.put(documentID, map.get(documentID) + 1);
			}
			else
			{
				map.put(documentID, 1);
			}
			
			termIndex.put(termID, map);
		}
		else
		{
			HashMap<Integer,Integer> map = new HashMap<Integer,Integer>();
			map.put(documentID, 1);
			termIndex.put(termID,map);
		}
		
		if(positionalIndex.containsKey(termID))
		{
			HashMap<Integer,ArrayList<Integer>> term = positionalIndex.get(termID);
			
			if(term.containsKey(documentID))
			{
				term.get(documentID).add(token.getPosition());
			}
			else
			{
				Integer pos = Integer.valueOf(token.getPosition());
				ArrayList<Integer> list = new ArrayList<Integer>();
				list.add(pos);
				term.put(documentID, list);
			}
		}
		else
		{
			HashMap<Integer,ArrayList<Integer>> term = new HashMap<Integer,ArrayList<Integer>>();
			Integer pos = Integer.valueOf(token.getPosition());
			ArrayList<Integer> list = new ArrayList<Integer>();
			list.add(pos);
			term.put(documentID, list);
			positionalIndex.put(termID, term);
		}
		
		/*if(tfindex.containsKey(termID))
		{
			HashMap<Integer,Integer> term = tfindex.get(termID);
			
			if(term.containsKey(documentID))
			{
				term.put(documentID, term.get(documentID) + 1);
			}
			else
			{
				term.put(String.valueOf(documentID), 1);
			}			
		}
		else
		{
			HashMap<String,Integer> term = new HashMap<String,Integer>();
			term.put(String.valueOf(documentID), 1);
			tfindex.put(termID, term);
		}*/
		
		
		}
	}

	@Override
	public void processCategoryIndex(String categoryName, Integer docID) {
		
		Integer catID = null;
		if(categoryDictionary.containsKey(categoryName))
		{
			catID = categoryDictionary.getForwardReference(categoryName);						
		}
		else
		{
			Integer i = SerializationWriter.categoryCount++;
			categoryDictionary.add(categoryName,i);
			catID = i;
		}
		
		
		if(categoryIndex.containsKey(catID))
		{
			HashMap<Integer,Integer> map = categoryIndex.get(catID);
			if(map.containsKey(docID))
			{
				map.put(docID, map.get(docID)+1);
			}
			else
				map.put(docID, 1);
			
			categoryIndex.put(catID,map);
		}
		else
		{
			HashMap<Integer,Integer> map = new HashMap<Integer,Integer>();
			map.put(docID, 1);
			categoryIndex.put(catID,map);
		}

	}

	@Override
	public void processAuthorIndex(TokenStream stream, Integer documentID) {
		stream.reset();
		while(stream.hasNext())
		{
			Token token = stream.next();
			String author = token.toString();
			Integer authorID = null;
			if(authorDictionary.containsKey(author))
			{
				authorID = authorDictionary.getForwardReference(author);
			}
			else
			{
				Integer i = SerializationWriter.authorCount++;
				authorDictionary.add(author, i);
				authorID = i;
			}
			
			
			if(authorIndex.containsKey(authorID))
			{
				ArrayList<Integer> list = authorIndex.get(authorID);
				if(!list.contains(documentID))
				{
					list.add(documentID);
				}
				authorIndex.put(authorID, list);
			}
			else
			{
				ArrayList<Integer> list = new ArrayList<Integer>();
				list.add(documentID);
				authorIndex.put(authorID,list);
			}
		}

	}

	@Override
	public void processPlaceIndex(TokenStream stream, Integer documentID) {
		
		stream.reset();
		while(stream.hasNext())
		{
			Token token = stream.next();
			String placeName = token.toString();
		Integer placeID = null;
		if(placeDictionary.containsKey(placeName))
		{
			placeID = placeDictionary.getForwardReference(placeName);						
		}
		else
		{
			Integer i = SerializationWriter.placeCount++;
			placeDictionary.add(placeName, i);
			placeID = i;
		}
		
		
		if(placeIndex.containsKey(placeID))
		{
			HashMap<Integer,Integer> map = placeIndex.get(placeID);
			if(map.containsKey(documentID))
			{
				map.put(documentID, map.get(documentID) + 1);
			}
			else
			{
				map.put(documentID, 1);
			}
			
			placeIndex.put(placeID, map);
		}
		else
		{
			HashMap<Integer,Integer> map = new HashMap<Integer,Integer>();
			map.put(documentID, 1);
			placeIndex.put(placeID,map);
		}
		
		}
	}

	@Override
	public void persistOnDisk() {	
		
		//Write and Close
		try {
			writeHelper(indexDir+File.separator+categoryIndexFileName,categoryIndex);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			writeHelper(indexDir+File.separator+termIndexFileName,termIndex);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			writeHelper(indexDir+File.separator+placeIndexFileName,placeIndex);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*	try {
			writeHelper(indexDir+File.separator+tfindexFileName,tfindex);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		try {
			writeHelper(indexDir+File.separator+idfindexFileName,idfindex);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			writeHelper(indexDir+File.separator+docLengthIndexFileName,docLengthIndex);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			writeHelper(indexDir+File.separator+authorIndexFileName,authorIndex);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			writeHelper(indexDir+File.separator+categoryDictionaryFileName,categoryDictionary);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			writeHelper(indexDir+File.separator+authorDictionaryFileName,authorDictionary);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			writeHelper(indexDir+File.separator+placeDictionaryFileName,placeDictionary);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			writeHelper(indexDir+File.separator+documentDictionaryFileName,documentDictionary);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			writeHelper(indexDir+File.separator+termDictionaryFileName,termDictionary);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void loadFiles() {
		try {
			loadingHelper(indexDir+File.separator+categoryIndexFileName,categoryIndex);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			loadingHelper(indexDir+File.separator+termIndexFileName,termIndex);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			loadingHelper(indexDir+File.separator+placeIndexFileName,placeIndex);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			loadingHelper(indexDir+File.separator+authorIndexFileName,authorIndex);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			loadingHelper(indexDir+File.separator+categoryDictionaryFileName,categoryDictionary);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			loadingHelper(indexDir+File.separator+authorDictionaryFileName,authorDictionary);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			loadingHelper(indexDir+File.separator+placeDictionaryFileName,placeDictionary);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			loadingHelper(indexDir+File.separator+documentDictionaryFileName,documentDictionary);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			loadingHelper(indexDir+File.separator+termDictionaryFileName,termDictionary);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void loadingHelper(String filename, Object obj) throws IOException, ClassNotFoundException
	{
		File file = new File(filename);
		if(file.exists())
		{
		FileInputStream f = new FileInputStream(file);
        ObjectInputStream o = new ObjectInputStream(f);
        obj =  o.readObject();
        o.close();
        f.close();
		}	
	}
	
	private void writeHelper(String filename, Object obj) throws IOException
	{
		FileOutputStream f = new FileOutputStream(filename);
		ObjectOutputStream o = new ObjectOutputStream(f);
		o.writeObject(obj);
		o.close();
		f.close();		
	}
	
	@Override
	public void processDocLength(Integer docID, Integer docLength) {
		
		docLengthIndex.put(docID,docLength);		
	}
	@Override	
	public void processAndSaveIDF() { 
		
		int N = documentDictionary.size();
		
		for(Integer i : termIndex.keySet()) {
			
		    Integer termID = i;
		    HashMap<Integer,Integer> docList = termIndex.get(i);
		    
		    int docFreq = docList.size();
		    
		    float idf = (float)Math.log10(N/docFreq);
		    //System.out.println("docFreq: "+docFreq+"\t\tIDF: "+idf);
		    
		    idfindex.put(termID, idf);
		}
		
		try {
			writeHelper(indexDir+File.separator+positionalIndexFileName,positionalIndex);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}