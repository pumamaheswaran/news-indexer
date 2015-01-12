/**
 * 
 */
package edu.buffalo.cse.irf14.analysis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * @author nikhillo
 * Class that represents a stream of Tokens. All {@link Analyzer} and
 * {@link TokenFilter} instances operate on this to implement their
 * behavior
 */
public class TokenStream implements Iterator<Token> {
	
	private int index = -1;
	private List<Token> tokenList = null;
	
	public List<Token> getTokenList() {
		return tokenList;
	}

	public void setTokenList(List<Token> tokenList) {
		this.tokenList = tokenList;
	}

	/**
	 * Method that checks if there is any Token left in the stream
	 * with regards to the current pointer.
	 * DOES NOT ADVANCE THE POINTER
	 * @return true if at least one Token exists, false otherwise
	 */
	
	@Override
	public boolean hasNext() {
		
		boolean flag = false;
		try
		{
			for(int i = index + 1 ; i < tokenList.size(); i++)
			{
				if(tokenList.get(i)!=null)
				{
					flag = true;
					break;
				}
			}
		}
		catch (IndexOutOfBoundsException e)
		{
			flag = false;
		}
		return flag;		
	}

	/**
	 * Method to return the next Token in the stream. If a previous
	 * hasNext() call returned true, this method must return a non-null
	 * Token.
	 * If for any reason, it is called at the end of the stream, when all
	 * tokens have already been iterated, return null
	 */
	@Override
	public Token next() {
		
		if(!hasNext())
		{
			index++;
			return null;
		}
		Token token = null;
		for(int i = index + 1 ; i < tokenList.size(); i++)
		{
			if(tokenList.get(i)!=null)
			{
				token = tokenList.get(i);
				index = i;
				break;
			}
		}
		return token;
	}
	
	/**
	 * Method to remove the current Token from the stream.
	 * Note that "current" token refers to the Token just returned
	 * by the next method. 
	 * Must thus be NO-OP when at the beginning of the stream or at the end
	 */
	@Override
	public void remove() {
		if(!(index < 0))
		{
			tokenList.set(index, null);
		}
		
	}
	
	/**
	 * Method to reset the stream to bring the iterator back to the beginning
	 * of the stream. Unless the stream has no tokens, hasNext() after calling
	 * reset() must always return true.
	 */
	public void reset() {
		index = -1;
	}
	
	/**
	 * Method to append the given TokenStream to the end of the current stream
	 * The append must always occur at the end irrespective of where the iterator
	 * currently stands. After appending, the iterator position must be unchanged
	 * Of course this means if the iterator was at the end of the stream and a 
	 * new stream was appended, the iterator hasn't moved but that is no longer
	 * the end of the stream.
	 * @param stream : The stream to be appended
	 */
	public void append(TokenStream stream) {
		if(stream!=null)
		tokenList.addAll(stream.getTokenList());
	}
	
	/**
	 * Method to get the current Token from the stream without iteration.
	 * The only difference between this method and {@link TokenStream#next()} is that
	 * the latter moves the stream forward, this one does not.
	 * Calling this method multiple times would not alter the return value of {@link TokenStream#hasNext()}
	 * @return The current {@link Token} if one exists, null if end of stream
	 * has been reached or the current Token was removed
	 */
	public Token getCurrent() {
		if(index <0 || index >= tokenList.size())
		{
			return null;
		}
		return tokenList.get(index);
	}
	
	/**
	 * Function that appends the supplied token after the current token.
	 * @param token
	 */
	public void appendAfterCurrent(Token token)
	{
		tokenList.add(index+1, token);
	}
	
	protected List<Token> getTokenSentence()
	{
		Token token = getCurrent();
		List<Token> list = null;
		if(token!=null && token.isBeginningofSentence())
		{
			int i = index;
			
			while(true)
			{
				if(i == tokenList.size())
				{
					i = i-1;
					break;
				}
								
				if(token!=null && token.isEndofSentence())
				{
					break;
				}				
				i = i+1;				
			}
			list =  constructTokenList(index, i);
		}
		else
			if(token!=null && token.isEndofSentence())
			{
				int i = index;
				
				while(true)
				{
					if(i < 0)
					{
						i = i + 1;
						break;
					}
					
					if(token!=null && token.isBeginningofSentence())
					{
						break;
					}				
					i = i-1;				
				}
				
				list =  constructTokenList(i, index);
				
				
			}
			else
			{
				//PROCESS ITEMS FROM CURRENT POSITION TO THE BEGINNING OF THE SENTENCE
				int endIndex = index;
				
				while(true)
				{
					if(endIndex == tokenList.size())
					{
						endIndex = endIndex-1;
						break;
					}
									
					if(token!=null && token.isEndofSentence())
					{
						break;
					}				
					endIndex = endIndex+1;				
				}
				
				int beginIndex = index;
				
				while(true)
				{
					if(beginIndex < 0)
					{
						beginIndex = beginIndex + 1;
						break;
					}
					
					if(token!=null && token.isBeginningofSentence())
					{
						break;
					}				
					beginIndex = beginIndex-1;				
				}
				
				list =  constructTokenList(beginIndex, endIndex);
				
			}
		
		return list;
	}
	
	/**
	 * Helper function for getting a List of tokens starting from beginIndex and ending at endIndex
	 * @param beginIndex
	 * @param endIndex
	 * @return
	 */
	private List<Token> constructTokenList(int beginIndex, int endIndex)
	{
		ArrayList<Token> list = new ArrayList<Token>();
		
		for(int i = beginIndex;i<=endIndex;i++)
		{
			
			try
			{
			tokenList.get(i).setCaptitalizationProcessed(true);
			list.add(tokenList.get(i));
			}
			catch(ArrayIndexOutOfBoundsException e)
			{
				e.printStackTrace();
				break;
			}
			catch(NullPointerException e)
			{
				
			}
			
		}
		
		return list;
	}
	
	/**
	 * Takes the whole sentence and converts it to lower case. Useful for converting title to lowercase.
	 */
	public void processCurrentSentenceForCapitalization() {
		
		Token token = getCurrent();
		if(token!=null && token.isBeginningofSentence())
		{
			int i = index;
			
			while(true)
			{
				if(i == tokenList.size())
				{
					i = i-1;
					break;
				}
								
				if(token!=null && token.isEndofSentence())
				{
					break;
				}				
				i = i+1;				
			}
			
			for(int j=index;j<=i;j++)
			{
				Token t = tokenList.get(j);
				if(t!=null)
				t.setTermText(t.getTermText().toLowerCase());
			}
					
		}
		else
			if(token!=null && token.isEndofSentence())
			{
				int i = index;
				
				while(true)
				{
					if(i < 0)
					{
						i = i + 1;
						break;
					}
					
					if(token!=null && token.isBeginningofSentence())
					{
						break;
					}				
					i = i-1;				
				}
				
				for(int j=i;j<=index;j++)
				{
					Token t = tokenList.get(j);
					if(t!=null)
					t.setTermText(t.getTermText().toLowerCase());
				}
				
				
			}
			else
			{
				//PROCESS ITEMS FROM CURRENT POSITION TO THE BEGINNING OF THE SENTENCE
				int endIndex = index;
				
				while(true)
				{
					if(endIndex == tokenList.size())
					{
						endIndex = endIndex-1;
						break;
					}
									
					if(token!=null && token.isEndofSentence())
					{
						break;
					}				
					endIndex = endIndex+1;				
				}
				
				int beginIndex = index -1;
				
				while(true)
				{
					if(beginIndex < 0)
					{
						beginIndex = beginIndex + 1;
						break;
					}
					
					if(token!=null && token.isBeginningofSentence())
					{
						break;
					}				
					beginIndex = beginIndex-1;				
				}
				
				for(int j=beginIndex;j<=endIndex;j++)
				{
					Token t = tokenList.get(j);
					if(token!=null)
					t.setTermText(t.getTermText().toLowerCase());
				}
				
			}	
	}
	
	/**
	 * Deletes token marked for deletion. Replaces with null.
	 */
	public void deleteMarkedTokens()
	{	
		for(int i = 0 ; i<tokenList.size();i++)
		{
			if(tokenList.get(i)!=null && tokenList.get(i).isMarkForDeletion())
			{
				/*if(i==0)
				{
					tokenList.get(i+1).setTokenBefore(null);
					
				}
				else
					if(i==tokenList.size()-1)
				{
					tokenList.get(i-1).setTokenAfter(null);
				}
					else
					{
						Token k = tokenList.get(i);
						tokenList.get(i+1).setTokenBefore(k.getTokenBefore());
						tokenList.get(i-1).setTokenAfter(k.getTokenAfter());
					}
				
				*/
				tokenList.set(i, null);
				
				Token tokenBefore = getPreviousNotNullToken(i);
				Token tokenAfter = getNextNotNullToken(i);
				if(tokenAfter!=null)
				{
					tokenAfter.setTokenBefore(tokenBefore);
				}
				if(tokenBefore!=null)
				{
					tokenBefore.setTokenAfter(tokenAfter);
				}
			}
		}
		
		
		
		/*for(Token k : tokenList)
		{
			if(k.isMarkForDeletion())
				tokenList.remove(k);
		}*/
	}
	
	private Token getNextNotNullToken(int startIndex)
	{
		for(int i = startIndex + 1;i<tokenList.size() && i >=0;i++)
		{
			if(tokenList.get(i)!=null)
				return tokenList.get(i);
		}
		return null;
	}
	
	private Token getPreviousNotNullToken(int startIndex)
	{
		for(int i = startIndex-1;i<tokenList.size() && i >=0;i--)
		{
			if(tokenList.get(i)!=null)
				return tokenList.get(i);
		}
		return null;
	}
	
	/**
	 * Rebuilds before and after token references. Think Linked List.
	 */
	public void reconstructTokenReferences() {
		
		Token tokenBefore = null;
		Token tokenAfter = null;
		for(Token e : tokenList)
		{
			if(e!=null)
			{
				e.setTokenBefore(tokenBefore);
				e.setTokenAfter(tokenAfter);
				if(tokenBefore!=null)
				{
					tokenBefore.setTokenAfter(e);
				}
				tokenBefore = e;
				
			}
		}
		
	}

	/**
	 * Function that appends the supplied list of tokens after the current token.
	 * @param tokenList
	 */
	public void appendAfterCurrent(List<Token> tokenList)
	{
		
		int curent = index+1;
		for(Token k : tokenList)
		{
			tokenList.add(curent++,k);
		}
	}
	
	public void sortArrayList()
	{
		deleteNullTokens();
		Collections.sort(tokenList, new Comparator<Token>() {
	        @Override
	        public int compare(Token  t1, Token  t2)
	        {

	            return  t1.getTermText().compareTo(t2.getTermText());
	        }
	    });
	}
	
	private void deleteNullTokens()
	{
		
		for(Token k : tokenList)
		{
			if(k == null)
				tokenList.remove(index);			
		}
	}
	
	public void printStream()
	{
		for(Token k : tokenList)
		{
			if(k!=null)
			{
				System.out.print(k.toString() + " ");
			}
			
		}
	}
	
			
}
