package edu.buffalo.cse.irf14.analysis;

import java.util.Arrays;

import edu.buffalo.cse.irf14.utility.FilterDatabase;


public class StopWordTokenFilter extends TokenFilter {

	public StopWordTokenFilter(TokenStream stream) {
		super(stream);
	}

	@Override
	public boolean increment() throws TokenizerException {
		
		boolean flag = false;

		TokenStream stream = getStream();
		
		if(stream.hasNext() /*|| isChained()*/)
		{
		
			Token token;
			/*if(!isChained())	*/
			token = stream.next();
			/*else
				token = stream.getCurrent();*/
		if(token!=null)
		{
		int index = Arrays.binarySearch(FilterDatabase.stopWordList, token.getTermText().toLowerCase());
		
		if(index >= 0)
		{
			stream.remove();
		}
		}
		flag =  true;
		
		}
		else
			flag = false;
		
		return flag;
	}

}
