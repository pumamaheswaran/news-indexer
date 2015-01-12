/**
 * 
 */
package edu.buffalo.cse.irf14.analysis;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author nikhillo
 * Class that a given string into a {@link TokenStream} instance
 */
public class Tokenizer {
	
	private StringTokenizer tokenizer = null;
	private String delim = null;
	private List<Token> tokenList = null;
	private TokenStream tokenStream = null;
	private Token tokenBefore = null;
	private Token tokenAfter = null;
	private boolean isDontTokenize = false;
	/**
	 * Default constructor. Assumes tokens are whitespace delimited
	 */
	public Tokenizer() {
		
	}
	
	/**
	 * Overloaded constructor. Creates the tokenizer with the given delimiter
	 * @param delim : The delimiter to be used
	 */
	public Tokenizer(String delim) {
		setDelim(delim);
	}
	
	/**
	 * Method to convert the given string into a TokenStream instance.
	 * This must only break it into tokens and initialize the stream.
	 * No other processing must be performed. Also the number of tokens
	 * would be determined by the string and the delimiter.
	 * So if the string were "hello world" with a whitespace delimited
	 * tokenizer, you would get two tokens in the stream. But for the same
	 * text used with lets say "~" as a delimiter would return just one
	 * token in the stream.
	 * @param str : The string to be consumed
	 * @return : The converted TokenStream as defined above
	 * @throws TokenizerException : In case any exception occurs during
	 * tokenization
	 */
	public TokenStream consume(String str) throws TokenizerException {
		
		boolean isStartOfTheSentenceFlag = true;
		
		if(str==null || str.equals(""))
		{
			throw new TokenizerException();
		}
		
		setTokenList(new ArrayList<Token>());
		
		if(getDelim() == null)
		{
			setTokenizer(new StringTokenizer(str," \n\r\t"));
		}
		else
		{
			setTokenizer(new StringTokenizer(str, getDelim()));
		}
		
		while(tokenizer.hasMoreElements())
		{
			
			Token token = new Token();
			String termText = tokenizer.nextToken();
			token.setTermText(termText);
			
			if(isStartOfTheSentenceFlag == true)
			{
				isStartOfTheSentenceFlag = false;
				token.setBeginningofSentence(true);
			}
			
			if(!tokenizer.hasMoreElements())
			{
				token.setEndofSentence(true);
			}
			
			if((termText.length()!=0) && (termText.charAt(termText.length()-1) == '?' || termText.charAt(termText.length()-1) == '!' || termText.charAt(termText.length()-1) == '.'))
			{
				token.setEndofSentence(true);
				isStartOfTheSentenceFlag = true;
			}			
			
			token.setTokenBefore(tokenBefore);
			token.setTokenAfter(tokenAfter);
			if(tokenBefore != null)
			{
				tokenBefore.setTokenAfter(token);
			}
			tokenList.add(token);
			
			tokenBefore = token;
									
			//DATE PROCESSING
		}
		
		if(!isDontTokenize)
		{
			tokenStream = new TokenStream();
			tokenStream.setTokenList(tokenList);
		}
		else
		{
			tokenStream = new TokenStream();
			Token k = new Token();
			k.setTermText(str);
			ArrayList<Token> arrayList = new ArrayList<Token>();
			arrayList.add(k);
			tokenStream.setTokenList(arrayList);
		}
		return tokenStream;
	}
	
	private void setTokenizer(StringTokenizer tokenizer) {
		this.tokenizer = tokenizer;
	}

	private String getDelim() {
		return delim;
	}

	private void setDelim(String delim) {
		this.delim = delim;
	}

	private void setTokenList(List<Token> tokenList) {
		this.tokenList = tokenList;
	}
	
	public void dontTokenize()
	{
		isDontTokenize = true;
	}

}
