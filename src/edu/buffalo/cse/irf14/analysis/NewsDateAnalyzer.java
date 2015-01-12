package edu.buffalo.cse.irf14.analysis;

import java.util.ArrayList;

public class NewsDateAnalyzer extends TokenAnalyzer {

	public NewsDateAnalyzer(TokenStream stream) {
		
		super(stream);
		tokenFilterList = new ArrayList<TokenFilter>();
		//tokenFilterList.add(TokenFilterFactory.getInstance().getFilterByType(TokenFilterType.CAPITALIZATION, stream));
		//tokenFilterList.add(TokenFilterFactory.getInstance().getFilterByType(TokenFilterType.ACCENT, stream));
		
		//tokenFilterList.add(TokenFilterFactory.getInstance().getFilterByType(TokenFilterType.STEMMER, stream));
		//tokenFilterList.add(TokenFilterFactory.getInstance().getFilterByType(TokenFilterType.STOPWORD, stream));
		
		//tokenFilterList.add(TokenFilterFactory.getInstance().getFilterByType(TokenFilterType.NUMERIC, stream));

		tokenFilterList.add(TokenFilterFactory.getInstance().getFilterByType(TokenFilterType.DATE, stream));
		tokenFilterList.add(TokenFilterFactory.getInstance().getFilterByType(TokenFilterType.SYMBOL, stream));
		tokenFilterList.add(TokenFilterFactory.getInstance().getFilterByType(TokenFilterType.SPECIALCHARS, stream));
		
			
	}

	

}
