package edu.buffalo.cse.irf14.analysis;

import edu.buffalo.cse.irf14.utility.FilterDatabase;

public class AccentTokenFilter extends TokenFilter {
	
	public AccentTokenFilter(TokenStream stream) {
		super(stream);		
	}

	@Override
	public boolean increment() throws TokenizerException {
		
		boolean flag = false;
		TokenStream stream  = getStream();
		if(stream.hasNext()/* || isChained()*/)
		{
			Token token;
			/*if(!isChained())	*/
			token = stream.next();
			/*else
				token = stream.getCurrent();*/
			if(token != null)
			{	
			String string = token.getTermText();
			
			StringBuilder str = new StringBuilder(string);
			
			for(int i = 0; i<str.length(); i++)
			{
				char temp = str.charAt(i);
				
				if(temp >='¿' && temp<='ӿ')
				{
					str.deleteCharAt(i);
					str.insert(i, FilterDatabase.accentMap.get(Character.toString(temp)));
				}
			}
			
			token.setTermText(str.toString());
			}
			flag =  true;
		}
		else
			flag = false;		
		
		return flag;		
	}

	
}
