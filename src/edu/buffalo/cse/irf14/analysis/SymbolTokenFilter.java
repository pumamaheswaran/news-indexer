package edu.buffalo.cse.irf14.analysis;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.buffalo.cse.irf14.utility.FilterDatabase;


public class SymbolTokenFilter extends TokenFilter {
	
	private static Pattern patternForS = null;
	private static Matcher matcherForS = null;
	private static Pattern patternForQuotes = null;
	private static Matcher matcherForQuotes = null;
	private static Pattern patternWordHyphenWord = null;
	private static Matcher matcherWordHyphenWord = null;
	private static Pattern patternAlphaHyphens = null;
	private static Matcher matcherAlphaHyphens = null;
	private static Pattern patternLeadingHyphens = null;
	private static Pattern patternTrailingHyphens = null;
	private static Matcher matcherLeadingHyphens = null;
	private static Matcher matcherTrailingHyphens = null;
	
	static
	{
		patternForS = Pattern.compile("'s\\b");
		matcherForS = patternForS.matcher("");
		patternForQuotes = Pattern.compile("'");
		matcherForQuotes = patternForQuotes.matcher("");
		patternWordHyphenWord = Pattern.compile("\\b[A-Za-z]+-[A-Za-z]+\\b");
		matcherWordHyphenWord = patternWordHyphenWord.matcher("");
		patternAlphaHyphens = Pattern.compile("\\w+-\\w+");
		matcherAlphaHyphens = patternAlphaHyphens.matcher("");
		patternLeadingHyphens = Pattern.compile("-+(?=\\w)");
		matcherLeadingHyphens = patternLeadingHyphens.matcher("");
		patternTrailingHyphens = Pattern.compile("(?<=\\w)-+");
		matcherTrailingHyphens = patternTrailingHyphens.matcher("");
		
	}
	
	
	
	public SymbolTokenFilter(TokenStream stream) {
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
		if(token.isSymbolProcessed())
		{
			return true;
		}
		String termText = token.getTermText();
		/*char temp = termText.charAt(termText.length()-1);
		//if((temp >='!' && temp<='/') ||	(temp >=':' && temp<='?') || (temp >='[' && temp<='`') && temp!='=')
		if(temp == '.' || temp =='?' || temp == '!')
		{
			token.setTermText(termText.substring(0, termText.length()-1));
			
			
		}*/
		
		//PROCESS PUNCTUATIONS
		
		for(int i = termText.length()-1;i>=0;i--)
		{
			char temp = termText.charAt(i);
			if(temp == '.' || temp =='?' || temp == '!')
			{
				termText = termText.substring(0, i);			
			}
			else
				break;
		}
		
		if(termText.equals(""))
		{
			stream.remove();
			return true;
		}
		token.setTermText(termText);
		
		
		//END OF PROCESS
		
		
		//Process Common Contractions
		
				if(FilterDatabase.contractionsMap.containsKey(token.getTermText().toLowerCase()))
				{
					if(Character.isLowerCase(Character.isLetter(token.getTermText().charAt(0))?token.getTermText().charAt(0):token.getTermText().charAt(1)))
					token.setTermText(FilterDatabase.contractionsMap.get(token.getTermText()).toLowerCase());
					else
					{
						token.setTermText(FilterDatabase.contractionsMap.get(token.getTermText().toLowerCase()).substring(0, 1).toUpperCase() + FilterDatabase.contractionsMap.get(token.getTermText().toLowerCase()).substring(1));
					}
				}				
		//End of Process
		
		//PROCESS 'S
		
		matcherForS.reset(token.getTermText());
		if(matcherForS.find())
			token.setTermText(matcherForS.replaceAll(""));
		//END OF PROCESS
		
		//PROCESS ' QUOTES
		
				
		matcherForQuotes.reset(token.getTermText());
		if (matcherForQuotes.find())
			token.setTermText(matcherForQuotes.replaceAll(""));
	
		//END OF PROCESS
		
		
		//PROCESS HYPHENS
		
		termText = token.getTermText();
		boolean isOnlyHyphen = true;
		for(int i = 0; i<termText.length();i++)
		{
			if(termText.charAt(i)=='-')
			{
				
			}
			else
			{
				isOnlyHyphen = false;
				break;
			}
		}
		
		if(isOnlyHyphen)
		{
			stream.remove();
			return true;
		}
		
		matcherWordHyphenWord.reset(termText);
		if(matcherWordHyphenWord.find())
		{
			String[] a = termText.split("-");
			StringBuilder x = new StringBuilder();
			
			for(String ie : a)
			{
				x.append(" ");
				x.append(ie);
			}
			
			String x1 = x.toString().trim();
			
			token.setTermText(x1);
			
			
			
			/*boolean isFirstElement = true;
			List<Token> tokenList = new ArrayList<Token>();
			for(String x : a)
			{
				if(isFirstElement)
				{
					token.setTermText(x);
					isFirstElement = false;
				}
				else
				{
					Token k = new Token();
					k.setTermText("-" + x);
					k.setTokenBefore(token);
					Token q = token.getTokenAfter();
					token.setTokenAfter(k);
					k.setTokenAfter(q);
					k.setSymbolProcessed(true);
					tokenList.add(k);
				}				
			}
			
			if(tokenList.size() >=1)
			{
				stream.appendAfterCurrent(tokenList);
			}*/
			//token.setTermText(text);
			return true;
		}
		
		//ALPHANUMERIC HYPHENS PROCESS
		termText = token.getTermText();
		matcherAlphaHyphens.reset(termText);
		if(matcherAlphaHyphens.find())
		{
			return true;
		}
		//END PROCESS
		
		
		//TRIM LEADING AND TRAILING HYPHENS
		
		termText = token.getTermText();
		
		matcherLeadingHyphens.reset(termText);
		if(matcherLeadingHyphens.find())
			termText = matcherLeadingHyphens.replaceAll("");
		
		
		matcherTrailingHyphens.reset(termText);
		if(matcherTrailingHyphens.find())
			termText = matcherTrailingHyphens.replaceAll("");
		
		token.setTermText(termText);
		
		//END PROCESS	
		}
		flag =  true;
	}
		else
			flag = false;
		return flag;

}
}
