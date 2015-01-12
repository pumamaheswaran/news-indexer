package edu.buffalo.cse.irf14.analysis;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.buffalo.cse.irf14.utility.DateUtil;


public class DateTokenFilter extends TokenFilter {	
	
	private static Pattern patternForYearRange1 = null;
	private static Pattern patternForYearRange2 = null;
	private static Pattern patternForYear = null;
	private static Pattern patternForMonth = null;
	private static Pattern patternForDate = null;
	private static Pattern patternForEra = null;
	private static Pattern patternForEra2 = null;
	private static Pattern patternForTime = null;
	private static Pattern patternForEraYear = null;
	private static Pattern patternEntryPoint = null;
	
	private static Matcher	matcherForYearRange1 = null;
	private static Matcher	matcherForYearRange2 = null;
	private static Matcher	matcherForYear	= null;
	private static Matcher	matcherForMonth = null;
	private static Matcher	matcherForDate	= null;
	private static Matcher	matcherForEra	= null;
	private static Matcher	matcherForEra2	= null;
	private static Matcher	matcherForTime	= null;
	private static Matcher	matcherForEraYear = null;
	private static Matcher  matcherEntryPoint = null;
		
	static
	{
		patternForYearRange1 = Pattern.compile("(\\d{4})([-/])(\\d{4})");
		patternForYearRange2 = Pattern.compile("(\\d{2})(\\d{2})([-/])(\\d{2})");
		patternForYear = Pattern.compile("\\d{4}");
		patternForMonth = Pattern.compile("(?:January|February|March|April|May|June|July|August|September|October|November|December|Jan|Feb|Mar|Apr|Jun|Jul|Aug|Sept|Oct|Nov|Dec)",Pattern.CASE_INSENSITIVE);
		patternForDate = Pattern.compile("\\b\\d{1,2}\\b");
		patternForEra = Pattern.compile("(\\d+)(BC|AD)");
		patternForEra2 = Pattern.compile("(?:BC|AD)");
		patternForTime = Pattern.compile("(\\d{1,2})(?::)(\\d{1,2})(?:\\s)*(AM|PM|am|pm)*");
		patternForEraYear = Pattern.compile("\\d+");
		patternEntryPoint = Pattern.compile("(?:\\d|January|February|March|April|May|June|July|August|September|October|November|December|Jan|Feb|Mar|Apr|Jun|Jul|Aug|Sept|Oct|Nov|Dec)");
		
		matcherForYearRange1 = patternForYearRange1.matcher("");
		matcherForYearRange2 = patternForYearRange2.matcher("");
		matcherForYear = patternForYear.matcher("");
		matcherForMonth = patternForMonth.matcher("");
		matcherForDate = patternForDate.matcher("");
		matcherForEra = patternForEra.matcher("");
		matcherForEra2 = patternForEra2.matcher("");
		matcherForEraYear = patternForEraYear.matcher("");
		matcherForTime = patternForTime.matcher("");
		matcherEntryPoint = patternEntryPoint.matcher("");		
	}

	public DateTokenFilter(TokenStream stream) {
		super(stream);
		// TODO Auto-generated constructor stub
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
			String tokenText = token.getTermText();
			boolean visited = false;
			
			
			matcherEntryPoint.reset(tokenText);
			if(matcherEntryPoint.find())
			{
			
			//CHECK FOR YEAR			
			//YYYY-YYYY
			matcherForYearRange1.reset(tokenText);
			if(matcherForYearRange1.find())
			{
				Calendar calendar = DateUtil.getCalendar();
				Calendar calendar1 = DateUtil.getCalendar();
				String a = matcherForYearRange1.group(1);
				String b = matcherForYearRange1.group(3);
				try
				{
				calendar.set(Calendar.YEAR, Integer.parseInt(a));
				calendar1.set(Calendar.YEAR, Integer.parseInt(b));
				}
				catch(NumberFormatException e)
				{
					e.printStackTrace();
				}
				
				int index = matcherForYearRange1.end(3);
				String appendAfter = "";
				if(tokenText.length()-index > 0)
				{
					appendAfter = tokenText.substring(index);
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String temp = sdf.format(calendar.getTime()) + matcherForYearRange1.group(2) + sdf.format(calendar1.getTime())+appendAfter;
				
				token.setTermText(temp);
				visited = true;
			}
			
			if(!visited)
			{
				matcherForYearRange2.reset(tokenText);
				//CHECK FOR YYYY-YY
				if(matcherForYearRange2.find())
				{
					Calendar calendar = DateUtil.getCalendar();
					Calendar calendar1 = DateUtil.getCalendar();
					String a = matcherForYearRange2.group(1) + matcherForYearRange2.group(2);
					String b = matcherForYearRange2.group(1) + matcherForYearRange2.group(4);
					try
					{
					calendar.set(Calendar.YEAR, Integer.parseInt(a));
					calendar1.set(Calendar.YEAR, Integer.parseInt(b));
					}
					catch(NumberFormatException e)
					{
						e.printStackTrace();
					}
					
					int index = matcherForYearRange2.end(4);
					String appendAfter = "";
					if(tokenText.length()-index > 0)
					{
						appendAfter = tokenText.substring(index);
					}
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
					String temp = sdf.format(calendar.getTime()) + matcherForYearRange2.group(3) + sdf.format(calendar1.getTime())+appendAfter;
					
					token.setTermText(temp);
					visited = true;
				}
			}
			
			if(!visited)
			{
				matcherForYear.reset(tokenText);
				Token nextToken = token.getTokenAfter();
				boolean skip=false;
				
				if(nextToken!=null)
				{
					if(nextToken.getTermText().equals("AD") || nextToken.getTermText().equals("BC"))
						skip=true;
				}
				
				//CHECK FOR YYYY
				if(matcherForYear.find() && skip!=true)
				{
					Calendar calendar = DateUtil.getCalendar();
					String a = matcherForYear.group();
					try
					{
					calendar.set(Calendar.YEAR, Integer.parseInt(a));
					}
					catch(NumberFormatException e)
					{
						e.printStackTrace();
					}
					
					int index = matcherForYear.end();
					String appendAfter = "";
					if(tokenText.length()-index > 0)
					{
						appendAfter = tokenText.substring(index);
					}
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
					String temp = sdf.format(calendar.getTime())+appendAfter;
					
					token.setTermText(temp);
					visited = true;
				}
			}
			
						
			//Process dd Month [yyyy]
			if(!visited)
			{
				matcherForDate.reset(tokenText);
				Token nextToken = token.getTokenAfter();
				
				//check for date
				if(matcherForDate.find())
				{
					String d = matcherForDate.group();
					
					if(token.getTokenAfter()!=null)
					{
						matcherForMonth.reset(nextToken.getTermText());
						
						//check for month after date
						if(matcherForMonth.find())
						{
							String m = matcherForMonth.group();
							
							try
							{
								Calendar calendar = DateUtil.getCalendar();
								calendar.set(Calendar.DATE, Integer.parseInt(d));
								calendar.set(Calendar.MONTH, monthHelper(m));	
								nextToken.setMarkForDeletion(true);
								
								if(nextToken.getTokenAfter()!=null)
								{
									matcherForYear.reset(nextToken.getTokenAfter().getTermText());
									
									//search for year
									if(matcherForYear.find())
									{
										String y = matcherForYear.group();
										calendar.set(Calendar.YEAR, Integer.parseInt(y));
										nextToken.getTokenAfter().setMarkForDeletion(true);
										nextToken = nextToken.getTokenAfter();
									}
									else
									{
										matcherForMonth.reset(nextToken.getTermText());
										matcherForMonth.find();
									}
								}
								
								int index = matcherForMonth.end();
								String appendAfter = "";
								if(nextToken.getTermText().length()-index > 0)
								{
									appendAfter = nextToken.getTermText().substring(index);
								}
								
								SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
								String temp = sdf.format(calendar.getTime()) + appendAfter;
								
								token.setTermText(temp);
								visited = true;
								
								stream.deleteMarkedTokens();								
								
							}
							catch(NumberFormatException e)
							{
								e.printStackTrace();
							}
						}
					}
					
				}
			}
			
			//Process Month dd [yyyy]
			if(!visited)
			{
				matcherForMonth.reset(tokenText);
				Token nextToken = token.getTokenAfter();
				
				//check for month
				if(matcherForMonth.find())
				{
					String m = matcherForMonth.group();
					
					if(token.getTokenAfter()!=null)
					{
						matcherForDate.reset(nextToken.getTermText());
						
						//check for date after month
						if(matcherForDate.find())
						{
							String d = matcherForDate.group();
							
							try
							{
								Calendar calendar = DateUtil.getCalendar();
								calendar.set(Calendar.DATE, Integer.parseInt(d));
								int month = monthHelper(m);
								calendar.set(Calendar.MONTH, month);	
								nextToken.setMarkForDeletion(true);
								
								Matcher matcher = matcherForDate;
								if(nextToken.getTokenAfter()!=null)
								{
									matcherForYear.reset(nextToken.getTokenAfter().getTermText());
									
									//search for year
									
									if(matcherForYear.find())
									{
										String y = matcherForYear.group();
										calendar.set(Calendar.YEAR, Integer.parseInt(y));
										nextToken.getTokenAfter().setMarkForDeletion(true);
										nextToken = nextToken.getTokenAfter();
										matcher = matcherForYear;
									}
									else
									{
										matcherForDate.reset(nextToken.getTermText());
										matcherForDate.find();
										matcher = matcherForDate;
									}
								}
								
								int index = matcher.end();
								String appendAfter = "";
								if(nextToken.getTermText().length()-index > 0)
								{
									appendAfter = nextToken.getTermText().substring(index);
								}
								
								SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
								String temp = sdf.format(calendar.getTime()) + appendAfter;
								
								token.setTermText(temp);
								visited = true;
								
								stream.deleteMarkedTokens();								
								
							}
							catch(NumberFormatException e)
							{
								e.printStackTrace();
							}
						}
						else
						{
						matcherForYear.reset(nextToken.getTermText());
						if(matcherForYear.find())
						{
							String y = matcherForYear.group();
							
							try
							{
								Calendar calendar = DateUtil.getCalendar();
								calendar.set(Calendar.YEAR, Integer.parseInt(y));
								int month = monthHelper(m);
								calendar.set(Calendar.MONTH, month);	
								nextToken.setMarkForDeletion(true);
								
								int index = matcherForYear.end();
								String appendAfter = "";
								if(nextToken.getTermText().length()-index > 0)
								{
									appendAfter = nextToken.getTermText().substring(index);
								}
								
								SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
								String temp = sdf.format(calendar.getTime()) + appendAfter;
								
								token.setTermText(temp);
								visited = true;
								
								stream.deleteMarkedTokens();								
								
							}
							catch(NumberFormatException e)
							{
								e.printStackTrace();
							}
						}
					}
					}
				}
			}
			
			
			//PROCESS ERA xxxAD|BC
			
			if(!visited)
			{
				matcherForEra.reset(tokenText);
				//CHECK FOR YYYY-YY
				if(matcherForEra.find())
				{
					Calendar calendar = DateUtil.getCalendar();
					String a = matcherForEra.group(1);
					String b = matcherForEra.group(2);
					try
					{
					calendar.set(Calendar.YEAR, Integer.parseInt(a));
					if(b.contains("AD"))
					{
						calendar.set(Calendar.ERA, GregorianCalendar.AD);
					}
					else
						if(b.contains("BC"))
						{
							calendar.set(Calendar.ERA, GregorianCalendar.BC);
						}
					}
					catch(NumberFormatException e)
					{
						e.printStackTrace();
					}
					
					int index = matcherForEra.end(2);
					String appendAfter = "";
					if(tokenText.length()-index > 0)
					{
						appendAfter = tokenText.substring(index);
					}
					
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
					String temp = sdf.format(calendar.getTime()) + appendAfter;
					
					token.setTermText(temp);
					visited = true;
				}
			}
			
			//XXX AD|BC
			if(!visited)
			{
				matcherForEraYear.reset(tokenText);
				//CHECK FOR YYYY-YY
				if(matcherForEraYear.find())
				{
					String a = matcherForEraYear.group();
					Token k = token.getTokenAfter();
					boolean bcFlag = false;
					matcherForEra2.reset(k==null?"":k.getTermText());
					if(matcherForEra2.find())
					{
						
					Calendar calendar = DateUtil.getCalendar();
					
					String b = matcherForEra2.group();
					try
					{
					calendar.set(Calendar.YEAR, Integer.parseInt(a));
					if(b.contains("AD"))
					{
						calendar.set(Calendar.ERA, GregorianCalendar.AD);
					}
					else
						if(b.contains("BC"))
						{
							bcFlag = true;
							calendar.set(Calendar.ERA, GregorianCalendar.BC);
						}
					}
					catch(NumberFormatException e)
					{
						e.printStackTrace();
					}
					
					int index = matcherForEra2.end();
					String appendAfter = "";
					if(tokenText.length()-index > 0)
					{
						appendAfter = tokenText.substring(index);
					}
					
					SimpleDateFormat sdf = new SimpleDateFormat(bcFlag?"-yyyyMMdd":"yyyyMMdd");
					String temp = sdf.format(calendar.getTime()) + appendAfter;
					
					token.setTermText(temp);
					visited = true;
					
					token.getTokenAfter().setMarkForDeletion(true);
					stream.deleteMarkedTokens();
					}
				}
			}
			
			//END PROCESS ERA
			
			//PROCESS TIME
			
			if(!visited)
			{
				Calendar calendar = DateUtil.getCalendar();	
				matcherForTime.reset(tokenText);
				Token nextToken = token.getTokenAfter();
				calendar.set(Calendar.SECOND, 0);
				
				try
				{
					
					if(matcherForTime.find())
					{

						String h = matcherForTime.group(1);
						String m = matcherForTime.group(2);
						String x = matcherForTime.group(3);
						
						if(x!=null)
						{
							if(x.equalsIgnoreCase("PM"))
							{
								calendar.set(Calendar.AM_PM, Calendar.PM);
							}
							else
							{
								calendar.set(Calendar.AM_PM, Calendar.AM);
							}
							
							calendar.set(Calendar.HOUR, Integer.parseInt(h));
							calendar.set(Calendar.MINUTE, Integer.parseInt(m));
														
							int index = matcherForTime.end();
							String appendAfter = "";
							if(tokenText.length()-index > 0)
							{
								appendAfter = tokenText.substring(index);
							}
							SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
							String temp = sdf.format(calendar.getTime())+appendAfter;
							
							token.setTermText(temp);
							visited = true;
						}
						else if (nextToken!=null && (nextToken.getTermText().contains("pm") || nextToken.getTermText().contains("am") || nextToken.getTermText().contains("AM") || nextToken.getTermText().contains("PM")))
						{
							if(nextToken.getTermText().contains("pm") || nextToken.getTermText().contains("PM"))
								calendar.set(Calendar.AM_PM, Calendar.PM);
							else if(nextToken.getTermText().contains("am") || nextToken.getTermText().contains("AM"))
								calendar.set(Calendar.AM_PM, Calendar.AM);
							
							calendar.set(Calendar.HOUR, Integer.parseInt(h));
							calendar.set(Calendar.MINUTE, Integer.parseInt(m));
							
							nextToken.setMarkForDeletion(true);
							
							int index = 2;
							String appendAfter = "";
							if(tokenText.length()-index > 0)
							{
								appendAfter = nextToken.getTermText().substring(index);
							}
							SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
							String temp = sdf.format(calendar.getTime())+appendAfter;
							
							stream.deleteMarkedTokens();
							
							token.setTermText(temp);
							visited = true;	
						}
					
					}
					
				}
				catch(NumberFormatException e)
				{
					e.printStackTrace();
				}
				
				
			}
			
			//END OF PROCESS
			
			//PROCESS ERA
			
			//END PROCESS ERA
			
			
			}
			}
			flag = true;
		
		}
		else
			flag = false;
		
		return flag;
		
	}
	
	private int monthHelper(String month)
	{
		int returnMonth = 0;
		if(month!=null)
		{
			if(month.equalsIgnoreCase("jan") || month.equalsIgnoreCase("january")){ returnMonth = GregorianCalendar.JANUARY;}
			else
				if(month.equalsIgnoreCase("feb") || month.equalsIgnoreCase("february")){returnMonth = GregorianCalendar.FEBRUARY;}
				else
					if(month.equalsIgnoreCase("mar") || month.equalsIgnoreCase("march")){returnMonth = GregorianCalendar.MARCH;}
					else
						if(month.equalsIgnoreCase("apr") || month.equalsIgnoreCase("april")){returnMonth = GregorianCalendar.APRIL;}
						else
							if(month.equalsIgnoreCase("may")){returnMonth = GregorianCalendar.MAY;}
							else
								if(month.equalsIgnoreCase("jun") || month.equalsIgnoreCase("june")){returnMonth = GregorianCalendar.JUNE;}
								else
									if(month.equalsIgnoreCase("jul") || month.equalsIgnoreCase("july")){returnMonth = GregorianCalendar.JULY;}
									else
										if(month.equalsIgnoreCase("aug") || month.equalsIgnoreCase("august")){returnMonth = GregorianCalendar.AUGUST;}
										else
											if(month.equalsIgnoreCase("sep") || month.equalsIgnoreCase("september") || month.equalsIgnoreCase("sept")){returnMonth = GregorianCalendar.SEPTEMBER;}
											else
												if(month.equalsIgnoreCase("oct") || month.equalsIgnoreCase("october")){returnMonth = GregorianCalendar.OCTOBER;}
												else
													if(month.equalsIgnoreCase("nov") || month.equalsIgnoreCase("november")){returnMonth = GregorianCalendar.NOVEMBER;}
													else
														if(month.equalsIgnoreCase("dec") || month.equalsIgnoreCase("december")){returnMonth = GregorianCalendar.DECEMBER;}
				
			
			
		}
		return returnMonth;
	}

}
