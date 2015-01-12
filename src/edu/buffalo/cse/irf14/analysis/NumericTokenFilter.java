package edu.buffalo.cse.irf14.analysis;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class NumericTokenFilter extends TokenFilter {
	
	private static Pattern patternx = null;
	private static Pattern patternForMonth = null;
	private static Pattern patternForDate = null;
	private static Pattern patternForYear =null;
	private static Pattern patternForYearRange1 = null;
	private static Pattern patternForYearRange2 = null;
	private static Pattern patternEntry = null;
	private static Pattern patternEightDigit = null;
	private static Pattern patternForTime = null;
	
	private static Matcher matcherx = null;
	private static Matcher matcherForMonth = null;
	private static Matcher matcherForDate = null;
	private static Matcher matcherForYear = null;
	private static Matcher matcherForYearRange1 = null;
	private static Matcher matcherForYearRange2 = null;
	private static Matcher matcherEntry = null;
	private static Matcher matcherEightDigit = null;
	private static Matcher matcherForTime = null;
	
	static
	{
		String patternString ="\\d+[,.]*\\d*";
		patternx = Pattern.compile(patternString);
		patternForMonth = Pattern.compile("(?:January|February|March|April|May|June|July|August|September|October|November|December|Jan|Feb|Mar|Apr|Jun|Jul|Aug|Sept|Oct|Nov|Dec)");
		patternForDate = Pattern.compile("\\d?\\d");
		patternForYear = Pattern.compile("d{4}");
		patternForYearRange2 = Pattern.compile("\\d{4}[-/]\\d{2}");
		patternForYearRange1 = Pattern.compile("\\d{4}[-/]\\d{4}");
		patternEntry = Pattern.compile("\\d");
		patternEightDigit = Pattern.compile("\\d{8}");
		patternForTime = Pattern.compile("\\d{2}:\\d{2}:\\d{2}.?");
		
		matcherx = patternx.matcher("");
		matcherForMonth = patternForMonth.matcher("");
		matcherForDate = patternForDate.matcher("");
		matcherForYear = patternForYear.matcher("");
		matcherForYearRange1 = patternForYearRange1.matcher("");
		matcherForYearRange2 = patternForYearRange2.matcher("");
		matcherEntry = patternEntry.matcher("");		
		matcherEightDigit = patternEightDigit.matcher("");
		matcherForTime = patternForTime.matcher("");
	}

	public NumericTokenFilter(TokenStream stream) {
		super(stream);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean increment() throws TokenizerException {
		boolean flag = false;
		TokenStream stream  = getStream();
		if(stream.hasNext() /*|| isChained()*/)
		{
			Token token;
			/*if(!isChained())	*/
			token = stream.next();
			/*else
				token = stream.getCurrent();*/
			
			if(token != null)
			{
				
			matcherEntry.reset(token.getTermText());
			if(matcherEntry.find())
			{
				matcherEightDigit.reset(token.getTermText());
				matcherForTime.reset(token.getTermText());
			if(!(matcherForTime.find() || matcherEightDigit.find()))
			{
				boolean isDate = false;
			String tokenText = token.getTermText();
			
			//CHECK IF YEAR
			
			matcherForYearRange1.reset(tokenText);
			if(matcherForYearRange1.find() && !isDate)
			{
				isDate = true;
			}
			matcherForYearRange2.reset(tokenText);
			if(matcherForYearRange2.find()  && !isDate)
			{
				isDate = true;
			}
			matcherForYear.reset(tokenText);
			if(matcherForYear.find()  && !isDate)
			{
				matcherForDate.reset(token.getTokenBefore().getTermText());
				if(matcherForDate.find()  && !isDate)
				{
					isDate = true;
				}
				matcherForMonth.reset(token.getTokenBefore().getTermText());
				if(matcherForMonth.find()  && !isDate)
				{
					isDate = true;
				}
			}
			
			//CHECK IF DATE
			
			matcherForDate.reset(tokenText);
			if(matcherForDate.find()  && !isDate)
			{
				String k = token.getTokenAfter()!=null?token.getTokenAfter().getTermText():"";
				String k1 = token.getTokenBefore()!=null?token.getTokenBefore().getTermText():"";
				matcherForMonth.reset(k);
				if(matcherForMonth.find() && !isDate)
				{
					isDate = true;
				}
				matcherForMonth.reset(k1);
				if(matcherForMonth.find() && !isDate)
				{
					isDate = true;
				}
			}		
			
			if(!isDate) {
				
			matcherx.reset(tokenText);
			tokenText = matcherx.replaceAll("");
			
			if(tokenText.equals(""))
			{
				token.setMarkForDeletion(true);
				stream.deleteMarkedTokens();
			}
			else
			token.setTermText(tokenText);
			}
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
