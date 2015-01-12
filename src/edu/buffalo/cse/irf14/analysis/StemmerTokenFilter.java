package edu.buffalo.cse.irf14.analysis;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StemmerTokenFilter extends TokenFilter {
	
	private static Pattern pattern = null;
	private static Pattern patterny = null;
	private static Pattern patternalpha = null;
	
	private static Matcher matcher = null;
	private static Matcher matchery = null;
	private static Matcher matcheralpha = null;
	
	static
	{
		pattern = Pattern.compile("(?:ed|ing(?:s?)|ly|tion(?:s?)|ion(?:s?)|i(?:ve|c|onal)|cal|(?:e?m?)ent|ness|ator|(?:e?)s)\\b");
		patterny = Pattern.compile("y\\b");
		patternalpha = Pattern.compile("(?:[0-9]|\\.|\\?|\\*|!|@|#|\\$|%|\\^|&|\'|\"|<|>|~|\\+|\\-)");
		
		matcher = pattern.matcher("");
		matchery = patterny.matcher("");
		matcheralpha = patternalpha.matcher("");
	}

	public StemmerTokenFilter(TokenStream stream) {
		super(stream);
		
	}

	@Override
	public boolean increment() throws TokenizerException {
		
		
		boolean flag = false;
		TokenStream stream  = getStream();
		if(stream.hasNext() /*|| isChained()*/)
		{
			Token token;
			/*if(!isChained())*/	
			token = stream.next();
			/*else
				token = stream.getCurrent();*/
			if(token!=null)
			{
			String string = token.getTermText();
			
			matcher.reset(string);
			matchery.reset(string);
			matcheralpha.reset(string);
			
			if(!matcheralpha.find())
			{
				if(matcher.find())
				{
					token.setTermText(matcher.replaceAll(""));
				}
				else if(matchery.find())
				{
					token.setTermText(matchery.replaceAll("i"));
				}
			}							
				
			}
			flag =  true;
		}
		else
			flag = false;		
		
		return flag;

	}
}
