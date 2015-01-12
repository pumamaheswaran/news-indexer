package edu.buffalo.cse.irf14.analysis;



public class SpecialCharacterTokenFilter extends TokenFilter {
	
	/*private static Pattern pattern = null;
	private static Pattern pattern1 = null;
	private static Pattern pattern3 = null;
	private static Pattern pattern4 = null;
	
	private static Matcher matcher = null;
	private static Matcher matcher1 = null;
	private static Matcher matcher2 = null;
	private static Matcher matcher3 = null;
	
	static
	{
		//pattern = Pattern.compile("[a-zA-Z0-9_.+]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9-.]+");
		pattern = Pattern.compile("(?<=\\w)[\\.!\\?@#\\$%^&\\*\\+\\{\\}\\(\\):;\"\\[\\]\\|\\\\~`,\\/\\=_]+");
		pattern1 = Pattern.compile("\\w*\\d*-\\w*\\d*");
		pattern3 = Pattern.compile("[\\.!\\?@#\\$%^&\\*\\+\\{\\}\\(\\):;\"\\[\\]\\|\\\\~`,\\/\\=_]+(?=\\w)");
		pattern4 = Pattern.compile("\\d+-\\d+");
	}
*/
	public SpecialCharacterTokenFilter(TokenStream stream) {
		super(stream);
		// TODO Auto-generated constructor stub
	}

	/*
	@Override
	public boolean increment() throws TokenizerException {
		
		boolean flag = false;
		TokenStream stream  = getStream();
		if(stream.hasNext())
		{
			stream = getStream();
			
			Token token = stream.next();
			
			Matcher matcher = pattern.matcher(token.getTermText());
			
			char[] temp = token.getTermBuffer();
			char[] temp1 = new char[temp.length];
			int index = 0;
			if(!matcher.find())
			{
				for(int i=0; i< temp.length; i++)
				{
					//if((temp[i] >= '!' && temp[i] <= '/') || (temp[i] >= ':' && temp[i] <= '?') || (temp[i] >= '{' && temp[i] <='~'))
					if(!((temp[i] >= 'A' && temp[i] <= 'Z') || (temp[i] >= 'a' && temp[i] <= 'z') || (temp[i] >= '0' && temp[i] <='9')))
					{
						//do Nothing
					}
					else
					{
						temp1[index++] = temp[i];
					}
				}
			}
			
			token.setTermText(String.valueOf(temp1).trim());
			flag = true;
		}
		else
			flag = false;		
		
		return flag;		
}*/
	
	@Override
		/*public boolean increment() throws TokenizerException {
			
			boolean flag = false;
			TokenStream stream = getStream();
			
			if(stream.hasNext())
			{
				Token token = stream.next();
				String tokenText = token.getTermText();
				
				//Process tokens with only special characters in it.
				boolean specialFlag = true;
				for(int i = 0; i<tokenText.length(); i++)
				{
					char c = tokenText.charAt(i);
					
					if((c >='A' && c<='Z')||(c >='a' && c<='z')||(c >='0' && c<='9'))
					{
						specialFlag = false;
						break;
					}
				}
				
				if(specialFlag == true)
				{
					stream.remove();
					return true;
				}
				
				//End process
				
				//Process words ending with special characters.
				
				Matcher matcher = pattern.matcher(tokenText);
				tokenText = matcher.replaceAll("");
				
				matcher = pattern3.matcher(tokenText);
				tokenText = matcher.replaceAll("");
				
				//End of process
				
				//Process Common Contractions
				
				if(FilterDatabase.contractionsMap.containsKey(tokenText))
				{
					tokenText = FilterDatabase.contractionsMap.get(tokenText);
				}				
				//End of Process
				
				//Process Hyphens
				if(tokenText.indexOf('-') != -1)
				{
				matcher = pattern1.matcher(tokenText);
				StringBuilder temp = new StringBuilder();
				if(!matcher.find())
				{
					for(int i = 0; i<tokenText.length();i++)
					{
						if(!(tokenText.charAt(i)=='-'))
						{
							temp.append(tokenText.charAt(i));
						}
					}
					tokenText = temp.toString();
				}
				else
				{
					String[] a = tokenText.split("-");
					tokenText = a[0];
					if(a.length>1)
					{
						Token k = new Token();
						k.setTermText("-"+a[1]);
						k.setTokenBefore(token);
						k.setTokenAfter(token.getTokenAfter());
						token.setTokenAfter(k);
						stream.appendAfterCurrent(k);
						stream.next();
					}
					
				}				
				}
				//End of Process			
				token.setTermText(tokenText);
				flag= true;
			}
			else
				flag = false;
			
			return flag;				
			
		}*/
	
	public boolean increment() throws TokenizerException {
		
		boolean flag = false;
		TokenStream stream = getStream();
		
		if(stream.hasNext() /*|| isChained()*/)
		{
			Token token;
			/*if(!isChained())*/	
			token = stream.next();
			/*else
				token = stream.getCurrent();*/
			if(token!=null)
			{
			String tokenText = token.getTermText();
			StringBuilder sb = new StringBuilder(tokenText);
			
			for(int i=sb.length()-1; i >= 0; i--)
			{
				char c = sb.charAt(i);
				
				if(!((c >='A' && c<='Z')||(c >='a' && c<='z')||(c >='0' && c<='9')||(c=='-')||(c=='.') ||(c==' ')))
				{
					sb.deleteCharAt(i);
				}
			}
			
			
			if(sb.toString().contains("-"))
			{
				char a[] = sb.toString().toCharArray();
				boolean flagNoDigit = true;
				for(int m = 0; m<a.length; m++)
				{
					if(Character.isDigit(a[m]))
					{
						flagNoDigit = false;
						break;
					}
				}
				if(flagNoDigit == true)
				{	
					int t = sb.indexOf("-");
					sb.deleteCharAt(t);
				}
				
			}
			if(sb.equals(""))
			{
				token.setMarkForDeletion(true);
				stream.deleteMarkedTokens();
			}
			else
			token.setTermText(sb.toString());
			}
			flag= true;
		}
		else
			flag = false;
		
		return flag;				
		
	}
	
}
