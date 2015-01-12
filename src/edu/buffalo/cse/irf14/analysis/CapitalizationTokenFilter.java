package edu.buffalo.cse.irf14.analysis;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CapitalizationTokenFilter extends TokenFilter {
	
	/*private static Pattern patternCapsCamel = null;
	private static Pattern patternCombine = null;*/
	private static Pattern allCaps = null;
	private static Pattern patternCamelCase = null;
	private static Matcher matcherAllCaps = null;
	private static Matcher matcherCamelCase = null;
	
	static
	{
		/*patternCapsCamel = Pattern.compile("\\b([a-zA-Z])*([a-z]*[A-Z][a-z]*)+\\b");
		patternCombine = Pattern.compile("\\b[A-Z]+[a-z]*\\b");*/
		allCaps = Pattern.compile("^[^a-z]+$",Pattern.MULTILINE);
		patternCamelCase= Pattern.compile("\\b[A-Z][^A-Z]+\\b");
		matcherAllCaps = allCaps.matcher("");
		matcherCamelCase = patternCamelCase.matcher("");				
	}

	public CapitalizationTokenFilter(TokenStream stream) {
		super(stream);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean increment() throws TokenizerException {
		
		boolean flag = false;
		boolean visited = false;
		TokenStream stream  = getStream();
		if(stream.hasNext()/* || isChained()*/)
		{
				Token token;
				/*if(!isChained())*/	
				token = stream.next();
				/*else
					token = stream.getCurrent();*/
				if(token!=null)
				{
			try
			{
				
				boolean allSentenceCaps = true;
				if(!token.isCaptitalizationProcessed())
				{
							
				//PROCESS CAPTIAL SENTENCES:
				List<Token> tokenList = stream.getTokenSentence();
				
				for(Token e : tokenList)
				{
					matcherAllCaps.reset(e.getTermText());
					//e.setCaptitalizationProcessedFL(true);
					if(!matcherAllCaps.find())
					{
						allSentenceCaps = false;
						break;
					}
				}
				
				if(allSentenceCaps)
				{
					stream.processCurrentSentenceForCapitalization();
					visited = true;
				}
				
				
				//END OF PROCESS
				}
				
				if(!visited)
				{
									
					//Check for camel case
					if(!(token.isBeginningofSentence() || token.isEndofSentence()))
					{
						matcherCamelCase.reset(token.getTermText());
						if(matcherCamelCase.find())
						{
							Token nextToken = token.getTokenAfter();
							String nextText = nextToken!=null?nextToken.getTermText():"";
							while(nextToken!=null)
							{			
								
								matcherCamelCase.reset(nextText);
								
								if(matcherCamelCase.find())
								{
									token.setTermText(token.getTermText() + " " + nextText);
									nextToken.setMarkForDeletion(true);
									stream.deleteMarkedTokens();
									visited=true;
								}
								else
								break;		
								
								if(!nextToken.isEndofSentence())
								{
								nextToken = token.getTokenAfter();
								nextText = nextToken!=null?nextToken.getTermText():"";
								}
								else
									break;
							}
							
						}
					}
				}
				
				if(!visited)
				{
					//Lowercase start of sentence
					matcherCamelCase.reset(token.getTermText());
					if(token.isBeginningofSentence() && (matcherCamelCase.find() || token.getTermText().length() == 1))
						token.setTermText(token.getTermText().toLowerCase());
					
					//End Process
				}
				
				
				
				
				
				
				
				/*Matcher matcherCapsCamel = patternCapsCamel.matcher(token.getTermText());
				Matcher matcherCombine = patternCombine.matcher(token.getTermText());
				
				if(matcherCombine.find())
				{
					if(token.getTokenAfter()!=null)
					{
						matcherCombine = patternCombine.matcher(token.getTokenAfter().getTermText());
						
						if(matcherCombine.find())
						{
							token.setTermText(token.getTermText()+" "+token.getTokenAfter().getTermText());
							
							if(stream.hasNext())
							{
								stream.next();
								stream.remove();
							}
							
							token.getTokenAfter().setMarkForDeletion(true);
							stream.deleteMarkedTokens();
						}
					}
					
				}
				
				if(!token.getTermText().contains(" "))
				{
					if(!matcherCapsCamel.find())
					{
						token.setTermText(token.getTermText().toLowerCase());
					}
				}	*/
				
			}
			catch(NumberFormatException e)
			{
				e.printStackTrace();
			}
			}
			flag = true;
		}
		else
			flag = false;		
		
		return flag;
	}
}
