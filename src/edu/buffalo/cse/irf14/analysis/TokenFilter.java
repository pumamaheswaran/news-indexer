/**
 * 
 */
package edu.buffalo.cse.irf14.analysis;


/**
 * The abstract class that you must extend when implementing your 
 * TokenFilter rule implementations.
 * Apart from the inherited Analyzer methods, we would use the 
 * inherited constructor (as defined here) to test your code.
 * @author nikhillo
 *
 */
public abstract class TokenFilter implements Analyzer {
	
	private TokenStream tokenStream = null;
	//protected List<TokenFilter> tokenFilterList = null; 
	//private boolean isChained;
	
	/**
	 * Default constructor, creates an instance over the given
	 * TokenStream
	 * @param stream : The given TokenStream instance
	 */
	public TokenFilter(TokenStream stream) {
		tokenStream = stream;
		//isChained = false;
	}
	
	@Override
	public TokenStream getStream() {
		return tokenStream;
	}
	
	

	/*public boolean isChained() {
		return isChained;
	}

	public void setChained(boolean isChained) {
		this.isChained = isChained;
	}*/
	
	public void reset(TokenStream stream)
	{
		this.tokenStream = stream;
		//isChained = false;
		
	}
	
	
}
