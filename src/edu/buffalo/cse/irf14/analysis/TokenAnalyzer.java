package edu.buffalo.cse.irf14.analysis;

import java.util.ArrayList;

public abstract class TokenAnalyzer implements Analyzer {
	
	protected TokenStream stream;
	protected ArrayList<TokenFilter> tokenFilterList;
	private int index = -1;
	
	public TokenAnalyzer(TokenStream stream)
	{
		this.stream = stream;
	}
	
	public boolean hasNext()
	{
		boolean flag = false;
		if(index + 1 < tokenFilterList.size() && tokenFilterList.size()!=0)
		{
			flag = true;
		}
		return flag;		
	}
	
	public TokenFilter next()
	{
		TokenFilter tf = null;
		try {
		tf = tokenFilterList.get(++index);
		}
		catch(IndexOutOfBoundsException e)
		{
			e.printStackTrace();
		}
		
		return tf;
	}
	
	@Override
	public TokenStream getStream() {
		return stream;
	}
	
	@Override
	public boolean increment() throws TokenizerException {
		
		boolean flag = false;
		//String className = null;
		if(hasNext())
		{
			flag = true;
			TokenFilter tf = next();
			//className = tf.getClass().getSimpleName();
			while(stream.hasNext())
			{
				tf.increment();
			}
			stream.reset();
			/*stream.printStream();
			System.out.print("		***********" + className);
			System.out.println();*/
		}
		
		return flag;
	}
	
	/*public void reset(TokenStream stream)
	{
		this.stream = stream;
		//isChained = false;
		
	}*/

}
