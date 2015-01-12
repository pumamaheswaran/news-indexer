package edu.buffalo.cse.irf14.analysis;

import java.util.ArrayList;

public class TitleAnalyzer extends TokenAnalyzer {
	
		
	public TitleAnalyzer(TokenStream stream) {
		
		super(stream);
		tokenFilterList = new ArrayList<TokenFilter>();
		tokenFilterList.add(TokenFilterFactory.getInstance().getFilterByType(TokenFilterType.CAPITALIZATION, stream));
		
		tokenFilterList.add(TokenFilterFactory.getInstance().getFilterByType(TokenFilterType.STOPWORD, stream));
		
		
		tokenFilterList.add(TokenFilterFactory.getInstance().getFilterByType(TokenFilterType.STEMMER, stream));
		tokenFilterList.add(TokenFilterFactory.getInstance().getFilterByType(TokenFilterType.DATE, this.stream));
		tokenFilterList.add(TokenFilterFactory.getInstance().getFilterByType(TokenFilterType.NUMERIC, stream));
		tokenFilterList.add(TokenFilterFactory.getInstance().getFilterByType(TokenFilterType.SYMBOL, stream));
		tokenFilterList.add(TokenFilterFactory.getInstance().getFilterByType(TokenFilterType.SPECIALCHARS, stream));
		
		
		
		tokenFilterList.add(TokenFilterFactory.getInstance().getFilterByType(TokenFilterType.ACCENT, stream));
				
	}
	
}
