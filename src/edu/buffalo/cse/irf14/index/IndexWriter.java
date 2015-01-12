/**
 * 
 */
package edu.buffalo.cse.irf14.index;

import java.io.File;

import edu.buffalo.cse.irf14.analysis.Analyzer;
import edu.buffalo.cse.irf14.analysis.AnalyzerFactory;
import edu.buffalo.cse.irf14.analysis.Token;
import edu.buffalo.cse.irf14.analysis.TokenStream;
import edu.buffalo.cse.irf14.analysis.Tokenizer;
import edu.buffalo.cse.irf14.analysis.TokenizerException;
import edu.buffalo.cse.irf14.document.Document;
import edu.buffalo.cse.irf14.document.FieldNames;

/**
 * @author nikhillo
 * Class responsible for writing indexes to disk
 */
public class IndexWriter {	
	
	private static String indexDir = null;
	private Writer writer = null;
	private static String category = null;
	private static int count = 0;
	
	/**
	 * Default constructor
	 * @param indexDir : The root directory to be used for indexing
	 */
	public IndexWriter(String indexDir) {
		
		IndexWriter.indexDir = indexDir;
		WriterFactory.setIndexDir(indexDir);
				
		try {
			writer = WriterFactory.getInstance().getWriter(WriterFactory.SERIALIZATIONWRITER);
			//writer.loadFiles();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
					
	}
	
	/**
	 * Method to add the given Document to the index
	 * This method should take care of reading the field values, passing
	 * them through corresponding analyzers and then indexing the results
	 * for each indexable field within the document. 
	 * @param d : The Document to be added
	 * @throws IndexerException : In case any error occurs
	 */
	public void addDocument(Document d) throws IndexerException {
		
		int docLength = 0;
		int position = 0;
		
		String categoryTemp = d.getField(FieldNames.CATEGORY)!=null?d.getField(FieldNames.CATEGORY)[0]:null;
		
		/*if(category == null)
		{
			category = categoryTemp;
		}
		else
		{
			if(categoryTemp!=null && !categoryTemp.equals(category))
			{
				category = categoryTemp;
				writer.persistOnDisk();
			}
		}*/
		
		count++;
		if(count >=2000)
		{
			count = 0;
			writer.persistOnDisk();
			System.gc();
		}
		
		Tokenizer tokenizer = new Tokenizer();
		TokenStream stream = null;
		AnalyzerFactory aFactory = null;
		Analyzer analyzer = null;
		//System.out.println(d.getField(FieldNames.CATEGORY)[0] + "::::::" + d.getField(FieldNames.FILEID)[0]);
		
		
		int docID = writer.processDocumentDictionary((d.getField(FieldNames.CATEGORY)==null?"":d.getField(FieldNames.CATEGORY)[0])+File.separator + d.getField(FieldNames.FILEID)[0]);
		
		
		//INSERT CATEGORY IN CATEGORY DICTIONARY
		if(d.getField(FieldNames.CATEGORY) != null)
		writer.processCategoryIndex(d.getField(FieldNames.CATEGORY)[0],docID);
		
		
		//For TITLE
			try
			{
				stream = tokenizer.consume(d.getField(FieldNames.TITLE)==null?"":d.getField(FieldNames.TITLE)[0]);
				if(stream!=null) {
					
					for(Token e : stream.getTokenList())
					{
						e.setPosition(position++);
					}
					
					aFactory = AnalyzerFactory.getInstance();
					analyzer = aFactory.getAnalyzerForField(FieldNames.TITLE, stream);
					while(analyzer.increment())
					{
						
					}
					//docLength += stream.getTokenList().size();
					writer.processTermIndex(stream, docID);	
				}
			}
			catch(TokenizerException e)
			{
				//System.out.println("No title for file" + String.valueOf(d.getField(FieldNames.CATEGORY)) + "   " + String.valueOf(d.getField(FieldNames.FILEID)));
			}
			
	
		
		StringBuilder authorOrg = new StringBuilder();
		
		//For AUTHOR ORG
		try{
			
		TokenStream stream1 = tokenizer.consume(d.getField(FieldNames.AUTHORORG)==null?"":d.getField(FieldNames.AUTHORORG)[0]);
		if(stream1!=null) {
			aFactory = AnalyzerFactory.getInstance();
			analyzer = aFactory.getAnalyzerForField(FieldNames.AUTHORORG, stream1);
			while(analyzer.increment())
			{
				
			}
			docLength += stream1.getTokenList().size();
		}
		
		while(stream1.hasNext())
		{
			Token k = stream1.next();
			authorOrg = authorOrg.append(k.toString());
		}
		
		}
		catch(TokenizerException e)
		{
			//System.out.println("No author organization for file" + d.getField(FieldNames.CATEGORY)[0] + "   " + d.getField(FieldNames.FILEID)[0]);
		}
		

		//For AUTHOR
		try {
		
		String[] a = d.getField(FieldNames.AUTHOR);
		if(a!=null)
		for(String x : a)
		{
			//x = convertToInitCap(x);
			Tokenizer k = new Tokenizer();
			stream = k.consume(x==null?"":x);
			if(stream!=null) {
				for(Token e : stream.getTokenList())
				{
					e.setPosition(position++);
				}
				aFactory = AnalyzerFactory.getInstance();
				analyzer = aFactory.getAnalyzerForField(FieldNames.AUTHOR, stream);
				while(analyzer.increment())
				{
					
				}
				docLength += stream.getTokenList().size();
				writer.processAuthorIndex(stream,docID);
			}
			
		}	
		
		}
		catch(TokenizerException e)
		{
			//System.out.println("No authors for file" +d.getField(FieldNames.CATEGORY)[0] + "   " + d.getField(FieldNames.FILEID)[0]);
		}
		
		
		//For DATE
		try {
		stream = tokenizer.consume(d.getField(FieldNames.NEWSDATE)==null?"":d.getField(FieldNames.NEWSDATE)[0]);
		if(stream!=null) {
			for(Token e : stream.getTokenList())
			{
				e.setPosition(position++);
			}
			aFactory = AnalyzerFactory.getInstance();
			analyzer = aFactory.getAnalyzerForField(FieldNames.NEWSDATE, stream);
			while(analyzer.increment())
			{
				
			}
			docLength += stream.getTokenList().size();
			//processTermIndex(stream, docID);
		}
		}
		catch(TokenizerException e)
		{
			//System.out.println("No date for file" + d.getField(FieldNames.CATEGORY)[0] + "   " + d.getField(FieldNames.FILEID)[0]);
		}		
		
		
		//For PLACE
		try {
			Tokenizer tkzr = new Tokenizer();
			tkzr.dontTokenize();
			stream = tkzr.consume(d.getField(FieldNames.PLACE)==null?"":d.getField(FieldNames.PLACE)[0]);
			if(stream!=null) {
				for(Token e : stream.getTokenList())
				{
					e.setPosition(position++);
				}
			aFactory = AnalyzerFactory.getInstance();
			analyzer = aFactory.getAnalyzerForField(FieldNames.PLACE, stream);
			while(analyzer.increment())
			{
				
			}
			docLength += stream.getTokenList().size();
			writer.processPlaceIndex(stream, docID);
		}
		}
		catch(TokenizerException e)
		{
			//System.out.println("No place for file" + d.getField(FieldNames.CATEGORY)[0] + "   " + d.getField(FieldNames.FILEID)[0]);
		}		
		
		
		//For CONTENT
		try {
		stream = tokenizer.consume(d.getField(FieldNames.CONTENT)==null?"":d.getField(FieldNames.CONTENT)[0]);
		if(stream!=null) {
			for(Token e : stream.getTokenList())
			{
				e.setPosition(position++);
			}
			aFactory = AnalyzerFactory.getInstance();
			analyzer = aFactory.getAnalyzerForField(FieldNames.CONTENT, stream);
			while(analyzer.increment())
			{
				
			}
			docLength += stream.getTokenList().size();
			writer.processTermIndex(stream, docID);
		}
		}
		catch(TokenizerException e)
		{
			//System.out.println("No content for file" +d.getField(FieldNames.CATEGORY)[0] + "   " + d.getField(FieldNames.FILEID)[0]);
		}
		
		//for doc length
		writer.processDocLength(docID, Integer.valueOf(docLength));		
	}

	
	/**
	 * Method that indicates that all open resources must be closed
	 * and cleaned and that the entire indexing operation has been completed.
	 * @throws IndexerException : In case any error occurs
	 */
	public void close() throws IndexerException {
		
		writer.processAndSaveIDF();
		writer.persistOnDisk();
		
	}
	
	
	public static String getIndexDir() {
		return indexDir;
	}		
}
