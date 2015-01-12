package edu.buffalo.cse.irf14.analysis;

import java.util.ArrayList;

public class InputTokenAnalyzer extends TokenAnalyzer {

	public InputTokenAnalyzer(TokenStream stream) {
		super(stream);
		tokenFilterList = new ArrayList<TokenFilter>();
		//tokenFilterList.add(TokenFilterFactory.getInstance().getFilterByType(TokenFilterType.CAPITALIZATION, this.stream));		
		tokenFilterList.add(TokenFilterFactory.getInstance().getFilterByType(TokenFilterType.STOPWORD, this.stream));
		
		//tokenFilterList.add(TokenFilterFactory.getInstance().getFilterByType(TokenFilterType.DATE, this.stream));
		tokenFilterList.add(TokenFilterFactory.getInstance().getFilterByType(TokenFilterType.NUMERIC, this.stream));
		tokenFilterList.add(TokenFilterFactory.getInstance().getFilterByType(TokenFilterType.SYMBOL, this.stream));
		tokenFilterList.add(TokenFilterFactory.getInstance().getFilterByType(TokenFilterType.SPECIALCHARS, this.stream));			
		tokenFilterList.add(TokenFilterFactory.getInstance().getFilterByType(TokenFilterType.STEMMER, this.stream));
		tokenFilterList.add(TokenFilterFactory.getInstance().getFilterByType(TokenFilterType.ACCENT, this.stream));	
	}

}
