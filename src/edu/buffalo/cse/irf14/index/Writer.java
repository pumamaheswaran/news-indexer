package edu.buffalo.cse.irf14.index;

import edu.buffalo.cse.irf14.analysis.TokenStream;

public abstract class Writer {	
	
	public abstract int processDocumentDictionary(String fileName);
	public abstract void processTermIndex(TokenStream stream, Integer documentID);
	public abstract void processCategoryIndex(String categoryID, Integer docID);
	public abstract void processAuthorIndex(TokenStream stream,Integer documentID);
	public abstract void processPlaceIndex(TokenStream stream, Integer documentID);
	public abstract void persistOnDisk();
	public abstract void loadFiles();
	public abstract void processDocLength(Integer documentID, Integer docLength);
	public abstract void processAndSaveIDF();
}
