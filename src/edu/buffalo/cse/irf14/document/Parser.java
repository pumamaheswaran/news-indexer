/**
 * 
 */
package edu.buffalo.cse.irf14.document;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author nikhillo
 * Class that parses a given file into a Document
 */
public class Parser {
	
	private static Pattern patternForDate = null; 
	private static Pattern patternForPlace = null; 
	private static Pattern patternForFirstLine = null;
	private static Pattern patternForDualAuthor = null;
	private static Pattern patternForAuthor = null;
	//private static int count = 1;
	private static Matcher matcherForDate = null;
	private static Matcher matcherForPlace = null;
	private static Matcher matcherForFirstLine = null;	
	private static Matcher matcherForDualAuthor =null;	
	private static Matcher matcherForAuthor =null;
	
	static
	{			
		patternForDate = Pattern.compile("(?:January|February|March|April|May|June|July|August|September|October|November|December|Jan|Feb|Mar|Apr|Jun|Jul|Aug|Sept|Oct|Nov|Dec).*?\\d+.*?(?=-)",Pattern.CASE_INSENSITIVE);
		String patternForPlaceString="(.+)(?=(,.*)(?:Jan|January|Feb|February|Mar|March|Apr|April|May|Jun|June|Jul|July|Aug|August|Sept|September|Oct|October|Nov|November|Dec|December))";
		patternForPlace = Pattern.compile(patternForPlaceString,Pattern.CASE_INSENSITIVE);
		patternForDualAuthor = Pattern.compile(".*(?:AUTHOR>).*(?:by)(.*)(?:and)(.*),(.*)(?:<\\/AUTHOR>)",Pattern.CASE_INSENSITIVE);
		patternForFirstLine = Pattern.compile("(?<=-).*");
		patternForAuthor = Pattern.compile(".*(?:<AUTHOR>).*(?:by)(.*),(.*)(?:<\\/AUTHOR>)",Pattern.CASE_INSENSITIVE);
		matcherForDate = patternForDate.matcher("");
		matcherForPlace = patternForPlace.matcher("");
		matcherForFirstLine = patternForFirstLine.matcher("");
		matcherForDualAuthor = patternForDualAuthor.matcher("");
		matcherForAuthor = patternForAuthor.matcher("");
	}
	
	/**
	 * Static method to parse the given file into the Document object
	 * @param filename : The fully qualified filename to be parsed
	 * @return The parsed and fully loaded Document object
	 * @throws ParserException In case any error occurs during parsing
	 */
	public static Document parse(String filename) throws ParserException {
		
		
		if(filename == null || filename.equals(""))
		{
			throw new ParserException();
		}
		File file = new File(filename);
		if(!file.exists())
		{
			throw new ParserException();
		}
		
		Document document = null;
		BufferedReader bReader = null;
		FileReader fReader = null;
		String text = null;
		boolean titleVisited = false;
		boolean placeVisited = false;
		boolean authorVisited = false;
		StringBuilder bodyText = null;	
		
		try {
			
		fReader = new FileReader(filename);
		bReader = new BufferedReader(fReader);
		bodyText = new StringBuilder();
		document = new Document();
		while((text=bReader.readLine())!=null)
		{
			if(titleVisited == false)
			{
				if(text != null && text.equals(""))
				{
					continue;
				}
				else
				{
					document.setField(FieldNames.TITLE, text);
					titleVisited = true;
				}				
			}
			else
				if(placeVisited == false || authorVisited == false)
				{
					if(text!=null && text.equals(""))
					{
						continue;
					}
					
					if(text.contains("<AUTHOR>"))
					{
						/*String temp = text.replace("<AUTHOR>", "").replace("</AUTHOR>", "");
						String[] temp1 = temp.split(",");
						
						if (temp1[0].contains("By") || temp1[0].contains("by") || temp1[0].contains("BY"))
						{
							temp1[0] = temp1[0].replace("by", "");
							temp1[0] = temp1[0].replace("By", "");
							temp1[0] = temp1[0].replace("BY", "");
						}
						document.setField(FieldNames.AUTHOR, temp1[0].trim());
						
						if(temp1.length > 1)*/
						boolean processed = false;
						matcherForDualAuthor.reset(text);
						String author1 = null, author2 = null, authorOrg = null;
						String[] author = null;
						if(matcherForDualAuthor.find())
						{
							 author1 = matcherForDualAuthor.group(1);
							 author2 = matcherForDualAuthor.group(2);
							 authorOrg = matcherForDualAuthor.group(3);
							 author = new String[2];
							 author[0] = author1.trim();
							 author[1] = author2.trim();
							 processed = true;
						}
						
						if(!processed)
						{
						matcherForAuthor.reset(text);
						
						if(matcherForAuthor.find())
						{
							author1 = matcherForAuthor.group(1);
							authorOrg = matcherForAuthor.group(2);
							 author = new String[1];
							 author[0] = author1.trim();							 
						}
						
						}
						
						document.setField(FieldNames.AUTHOR, author);
						document.setField(FieldNames.AUTHORORG, authorOrg!=null?authorOrg.trim():authorOrg);
						authorVisited = true;
						continue;
					}
					else
					{
						authorVisited = true;
					}
					
					matcherForPlace.reset(text);
					boolean placeFlg = true;
					
					if (matcherForPlace.find())
					{
					  String place= matcherForPlace.group();
					  place = place.trim();
					  document.setField(FieldNames.PLACE, place);					 
					}
					else
						placeFlg = false;
					
					matcherForDate.reset(text);
					boolean dateFl = true;
					if (matcherForDate.find())
					{
					  String date= matcherForDate.group();
					  date = date.trim();
					  document.setField(FieldNames.NEWSDATE, date);					 
					}
					else
						dateFl = false;
										
					
					matcherForFirstLine.reset(text);
					
					if(dateFl && placeFlg)
					{
					if(matcherForFirstLine.find())
					{
						String temp = matcherForFirstLine.group();
						bodyText.append(temp);
					}
					}
					else
						bodyText.append(text);
					
					
					
					
					/*String place = text.substring(0, text.lastIndexOf(','));
					document.setField(FieldNames.PLACE, place);
					String date = text.substring(text.lastIndexOf(',')+1, text.indexOf('-'));
					document.setField(FieldNames.NEWSDATE, date);
					bodyText.append(text.substring(text.indexOf('-')+1));*/
					
					placeVisited = true;
				}
				else
				{
					bodyText.append("\n");
					bodyText.append(text);
				
				}
			
		}
		
			document.setField(FieldNames.CONTENT, bodyText == null?"":bodyText.toString());
			document.setField(FieldNames.FILEID, filename.substring(filename.lastIndexOf(File.separator)+1));
			
			String tempField = filename.substring(0, filename.lastIndexOf(File.separatorChar));
			document.setField(FieldNames.CATEGORY, filename.substring(tempField.lastIndexOf(File.separator) + 1, filename.lastIndexOf(File.separator)));
			
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		finally
		{
			try {
				fReader.close();
				bReader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
		
		return document;	
		
	}
		
}
