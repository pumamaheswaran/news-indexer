# news-indexer
An indexer built using Java to parse and index a repository of unstructured news articles.
## About
This project was developed as a part of CSE535 (Information Retrieval) course at University at Buffalo.
The goal of the project is to create a parser for parsing and indexing unstructured news articles. The news articles can be found under the news_corpus directory in this repository.

## Parsing goals

1. Build a tokenizer that creates a stream of tokens from a given unstructured news article.
2. Implement different types of token filters that filters tokens from the final token stream so that the resulting index built from these tokens is of less size.
  1. Accent Filter 
  2. Capitalization Filter 
  3. Date Filter 
  4. Numeric Filter 
  5. Special Character Filter 
  6. Stemmer Filter 
  7. Stop Word Fitler 
  8. Symbol Filter 
3. Chain different tokens together using Analyzer classes. Implemented analyzers like:

  1. Author Analyzer
  2. Author Organization Analyzer 
  3. Content Analyzer 
  4. News Date Analyzer
  5. Place Analyzer
  6. Title Analyzer

## Indexing
There are four types of indexes that are to be maintained:
1. Term index
2. Author index
3. Category index
4. Place index

## Querying
Given a query, the application should be able to return a list of documents where the term appears, or the author has written, or that falls in a particular category, or that pertains to a specific place. Term frequency, term/author/category/place to document and reverse mapping makes the query functionality possible.


  
  
