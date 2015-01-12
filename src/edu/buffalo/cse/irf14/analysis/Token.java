/**
 * 
 */
package edu.buffalo.cse.irf14.analysis;

/**
 * @author nikhillo
 * This class represents the smallest indexable unit of text.
 * At the very least it is backed by a string representation that
 * can be interchangeably used with the backing char array.
 * 
 * You are encouraged to add more data structures and fields as you deem fit. 
 */
public class Token {
	
	//The backing string representation -- can contain extraneous information
	private String termText;
	//The char array backing termText
	private char[] termBuffer;
	private Token tokenBefore;
	private Token tokenAfter;
	
	private int position;
	
	private boolean isEndofSentence;
	
	private boolean isBeginningofSentence;
	
	private boolean isCaptitalizationProcessed;
	
	private boolean markForDeletion;
	private boolean isSymbolProcessed;
		
	public Token()
	{
		tokenBefore = null;
		tokenAfter = null;
		isEndofSentence = false;
		isBeginningofSentence = false;
		markForDeletion = false;
	}
	/**
	 * Method to set the termText to given text.
	 * This is a sample implementation and you CAN change this
	 * to suit your class definition and data structure needs.
	 * @param text
	 */
	protected void setTermText(String text) {
		termText = text;
		termBuffer = (termText != null) ? termText.toCharArray() : null;
	}
	
	/**
	 * Getter for termText
	 * This is a sample implementation and you CAN change this
	 * to suit your class definition and data structure needs.
	 * @return the underlying termText
	 */
	protected String getTermText() {
		return termText;
	}
	
	/**
	 * Method to set the termBuffer to the given buffer.
	 * This is a sample implementation and you CAN change this
	 * to suit your class definition and data structure needs.
	 * @param buffer: The buffer to be set
	 */
	protected void setTermBuffer(char[] buffer) {
		termBuffer = buffer;
		termText = new String(buffer);
	}
	
	/**
	 * Getter for the field termBuffer
	 * @return The termBuffer
	 */
	protected char[] getTermBuffer() {
		return termBuffer;
	}
	
	/**
	 * Method to merge this token with the given array of Tokens
	 * You are free to update termText and termBuffer as you please
	 * based upon your Token implementation. But the toString() method
	 * below must return whitespace separated value for all tokens combined
	 * Also the token order must be maintained.
	 * @param tokens The token array to be merged
	 */
	protected void merge(Token...tokens) {
		
		/*String temp = "";
		
		for(int i = 0; i<token.length; i++)
		{
			temp = temp + token[i] + " ";
			token[i].setMarkForDeletion(true);
		}
		
		token[0].setTermText(temp.trim());*/
		
		if(tokens != null)
		{
			for(Token k : tokens){
				if(k != null)
				{
					this.setTermText(this.getTermText()+" "+k.getTermText());
				}
			
			}
		}
		
	}
	
	/**
	 * Returns the string representation of this token. It must adhere to the
	 * following rules:
	 * 1. Return only the associated "text" with the token. Any other information 
	 * must be suppressed.
	 * 2. Must return a non-empty value only for tokens that are going to be indexed
	 * If you introduce special token types (sentence boundary for example), return
	 * an empty string
	 * 3. IF the original token A (with string as "a") was merged with tokens B ("b"),
	 * C ("c") and D ("d"), toString should return "a b c d"
	 * @return The raw string representation of the token
	 */
	@Override
	public String toString() {
		return termText;
	}

	public Token getTokenBefore() {
		return tokenBefore;
	}

	public void setTokenBefore(Token tokenBefore) {
		this.tokenBefore = tokenBefore;
	}

	public Token getTokenAfter() {
		return tokenAfter;
	}

	public void setTokenAfter(Token tokenAfter) {
		this.tokenAfter = tokenAfter;
	}

	public boolean isEndofSentence() {
		return isEndofSentence;
	}

	public void setEndofSentence(boolean isEndofSentence) {
		this.isEndofSentence = isEndofSentence;
	}

	public boolean isBeginningofSentence() {
		return isBeginningofSentence;
	}

	public void setBeginningofSentence(boolean isBeginningofSentence) {
		this.isBeginningofSentence = isBeginningofSentence;
	}
	public boolean isCaptitalizationProcessed() {
		return isCaptitalizationProcessed;
	}
	public void setCaptitalizationProcessed(boolean isCaptitalizationProcessed) {
		this.isCaptitalizationProcessed = isCaptitalizationProcessed;
	}
	public boolean isMarkForDeletion() {
		return markForDeletion;
	}
	public void setMarkForDeletion(boolean markForDeletion) {
		this.markForDeletion = markForDeletion;
	}
	public boolean isSymbolProcessed() {
		return isSymbolProcessed;
	}
	public void setSymbolProcessed(boolean isSymbolProcessed) {
		this.isSymbolProcessed = isSymbolProcessed;
	}
	public void setPosition(int n){
		this.position=n;
	}
	public int getPosition(){
		return this.position;
	}
}
