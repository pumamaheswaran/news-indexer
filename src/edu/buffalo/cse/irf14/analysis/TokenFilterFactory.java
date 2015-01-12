/**
 * 
 */
package edu.buffalo.cse.irf14.analysis;



/**
 * Factory class for instantiating a given TokenFilter
 * @author nikhillo
 *
 */
public class TokenFilterFactory {
	
	private static TokenFilterFactory instance = null;
	/*private DateTokenFilter dateTokenFilter= null;
	private NumericTokenFilter numericTokenFilter = null;
	private SpecialCharacterTokenFilter specialCharacterTokenFilter = null;
	private StemmerTokenFilter stemmerTokenFilter = null;
	private StopWordTokenFilter stopWordTokenFilter = null;
	private CapitalizationTokenFilter capitalizationTokenFilter = null;
	private AccentTokenFilter accentTokenFilter = null;
	private SymbolTokenFilter symbolTokenFilter =null;*/
	
	
	private TokenFilterFactory()
	{
		
	}
	
	/**
	 * Static method to return an instance of the factory class.
	 * Usually factory classes are defined as singletons, i.e. 
	 * only one instance of the class exists at any instance.
	 * This is usually achieved by defining a private static instance
	 * that is initialized by the "private" constructor.
	 * On the method being called, you return the static instance.
	 * This allows you to reuse expensive objects that you may create
	 * during instantiation
	 * @return An instance of the factory
	 */
	public static TokenFilterFactory getInstance() {
		
		if(instance==null)
		{
			instance = new TokenFilterFactory();
		}
		return instance;		
	}
	
	/**
	 * Returns a fully constructed {@link TokenFilter} instance
	 * for a given {@link TokenFilterType} type
	 * @param type: The {@link TokenFilterType} for which the {@link TokenFilter}
	 * is requested
	 * @param stream: The TokenStream instance to be wrapped
	 * @return The built {@link TokenFilter} instance
	 */
	public TokenFilter getFilterByType(TokenFilterType type, TokenStream stream) {
		
		TokenFilter tokenFilter = null;
			
		if(type!=null)
		{
			if(type == TokenFilterType.ACCENT)
			{
				/*if(accentTokenFilter==null)
				{
				accentTokenFilter =  new AccentTokenFilter(stream);
				tokenFilter = accentTokenFilter;
				}
				else
				{
					accentTokenFilter.reset(stream);
					return accentTokenFilter;
				}*/
				tokenFilter = new AccentTokenFilter(stream);
			}
			else 
				if(type == TokenFilterType.CAPITALIZATION)
				{
					/*if(capitalizationTokenFilter==null)
					{
						capitalizationTokenFilter =  new CapitalizationTokenFilter(stream);
						tokenFilter = capitalizationTokenFilter;
					}
					else
					{
						capitalizationTokenFilter.reset(stream);
						return capitalizationTokenFilter;
					}*/
					tokenFilter = new CapitalizationTokenFilter(stream);
				}
				else
					if(type == TokenFilterType.DATE)
					{
						/*if(dateTokenFilter==null)
						{
							dateTokenFilter =  new DateTokenFilter(stream);
							tokenFilter = dateTokenFilter;
						}
						else
						{
							dateTokenFilter.reset(stream);
							tokenFilter = dateTokenFilter;
						}*/
						tokenFilter = new DateTokenFilter(stream);
						
					}
					else
						if(type == TokenFilterType.NUMERIC)
						{
							/*if(numericTokenFilter==null)
							{
								numericTokenFilter =  new NumericTokenFilter(stream);
								tokenFilter = numericTokenFilter;
							}
							else
							{
								numericTokenFilter.reset(stream);
								tokenFilter = numericTokenFilter;
							}*/
							tokenFilter = new NumericTokenFilter(stream);
						}
						else
							if(type == TokenFilterType.SPECIALCHARS)
							{
								/*if(specialCharacterTokenFilter==null)
								{
									specialCharacterTokenFilter =  new SpecialCharacterTokenFilter(stream);
									tokenFilter = specialCharacterTokenFilter;
								}
								else
								{
									specialCharacterTokenFilter.reset(stream);
									tokenFilter = specialCharacterTokenFilter;
								}*/
								tokenFilter = new SpecialCharacterTokenFilter(stream);
							}
							else
								if(type == TokenFilterType.STEMMER)
								{
									/*if(stemmerTokenFilter==null)
									{
										stemmerTokenFilter =  new StemmerTokenFilter(stream);
										tokenFilter = stemmerTokenFilter;
									}
									else
									{
										stemmerTokenFilter.reset(stream);
										tokenFilter = stemmerTokenFilter;
									}*/
									tokenFilter = new StemmerTokenFilter(stream);
								}
								else
									if(type == TokenFilterType.STOPWORD)
									{/*
										if(stopWordTokenFilter==null)
										{
											stopWordTokenFilter =  new StopWordTokenFilter(stream);
											tokenFilter = stopWordTokenFilter;
										}
										else
										{
											stopWordTokenFilter.reset(stream);
											tokenFilter = stopWordTokenFilter;
										}*/
										tokenFilter = new StopWordTokenFilter(stream);
									}
									else
										if(type == TokenFilterType.SYMBOL)
										{
											/*if(symbolTokenFilter==null)
											{
												symbolTokenFilter =  new SymbolTokenFilter(stream);
												tokenFilter = symbolTokenFilter;
											}
											else
											{
												symbolTokenFilter.reset(stream);
												tokenFilter = symbolTokenFilter;
											}*/
											tokenFilter = new SymbolTokenFilter(stream);
										}
		}
		
		return tokenFilter;
	}
}
