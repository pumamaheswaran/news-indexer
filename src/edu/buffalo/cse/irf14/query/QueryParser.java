/**
 * 
 */
package edu.buffalo.cse.irf14.query;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author nikhillo
 * Static parser that converts raw text to Query objects
 */
public class QueryParser {
	
	public static final String AND = "AND";
	public static final String OR = "OR";
	
	public static String defaultOperator = OR;
	
	/**
	 * MEthod to parse the given user query into a Query object
	 * @param userQuery : The query to parse
	 * @param defaultOperator : The default operator to use, one amongst (AND|OR)
	 * @return Query object if successfully parsed, null otherwise
	 */
	public static Query parse(String userQuery, String defaultOperator) {		
		
		//Check Starts
				
		if(userQuery == null || userQuery.equals(""))
			return null;
		
		if(defaultOperator == null || defaultOperator.equals(""))
			return null;
		
		if(!(defaultOperator.equalsIgnoreCase(OR) || defaultOperator.equalsIgnoreCase(AND)))
			return null;
		
		QueryParser.defaultOperator = defaultOperator;
		//Check Ends	
		
		//Pre-Processing For Tree Starts Starts
		String tempQuery = putSpacesBetweenParenthesis(userQuery);
		ArrayList<Node> nodeTokenList = splitInTokens(tempQuery);
		ArrayList<Node> bracketProcessedList = addBrackets(nodeTokenList);
		ArrayList<Node> annotatedNodeList = annotateTerms(bracketProcessedList);
		ArrayList<Node> processedNodeList = putDefaultOperator(annotatedNodeList);
		
		//Pre-Processing Ends
		
		//Parse For ToString
		StringBuilder sb = null;
		
		
		for(Node s: processedNodeList)
		{
			if(sb == null)
			{
				sb = new StringBuilder();
				sb.append("{ ");
				sb.append(s);
			}
			else
			sb = sb.append(" "+s.getText());
		}
		sb.append(" }");
		
		for(int i = 0; i<sb.length();i++)
		{
			if(sb.charAt(i) == '(')
			{
				sb.setCharAt(i, '[');
			}
			else
				if(sb.charAt(i) == ')')
				{
					sb.setCharAt(i, ']');
				}
		}
		//Parse ends
		
		LinkedList<Node> ll = new LinkedList<Node>(processedNodeList);
		
		Node n = null;
		n = parseString(ll);
		
		
		Query query = new Query(sb.toString());
		query.setRootNode(n);
		query.setUnparsedQuery(userQuery);		
		return query;
		
	}
	
	private static ArrayList<Node> addBrackets(ArrayList<Node> nodeTokenList) {
		
		LinkedList<Node> ll = new LinkedList<Node>(nodeTokenList);
		ArrayList<Node> returnList = new ArrayList<Node>();
		if(nodeTokenList.size() == 1)
			return nodeTokenList;
		while(true && ll.size() > 0)
		{
			Node n = ll.remove();
			returnList.add(n);
						
			if(n.getText().equals("AND") || n.getText().equals("OR"))
			{
				ArrayList<Node> tempNodeList = new ArrayList<Node>();
				while(true && ll.size() > 0)
				{					
					Node nod = ll.peek();
					if(nod.getText().equals("(") || nod.getText().equals("OR") || nod.getText().equals("AND") || nod.getText().equals(")"))
					{
						break;
					}
					tempNodeList.add(nod);
					ll.remove();
				}
				if(tempNodeList.size() > 1)
				{
					returnList.add(new Node("("));
					
					for(Node e : tempNodeList)
					{
						returnList.add(e);
					}
					
					returnList.add(new Node(")"));
				}
				else
					if(tempNodeList.size() == 1)
				{
					returnList.add(tempNodeList.get(0));
				}
			}
		}
		
		return returnList;
	}

	public static ArrayList<Node> putDefaultOperator(ArrayList<Node> nodeList) {
		
		ArrayList<Node> returnList = null;
		LinkedList<Node> ll = new LinkedList<Node>(nodeList);
		if(nodeList!=null)
		{
			returnList = new ArrayList<Node>();
			while(ll.size()!=0)
			{
				
				Node s = ll.remove();
				returnList.add(s);
				if(s.getText().contains("Place:") || s.getText().contains("Category:") || s.getText().contains("Author:") || s.getText().contains("Term:"))
				{
					if(ll.size() == 0)
						break;
					
					Node p = ll.peek();
					
					if(p.getText().equals("AND") || p.getText().equals("OR"))
					{
						continue;
					}
					else
						if(p.getText().equals(")"))
						{
							returnList.add(p);
							ll.remove();
							while(true && ll.size()>0)
							{
								Node t = ll.peek();
								if(t.getText().equals(")"))
								{
									returnList.add(t);
									ll.remove();
								}
								else
									if(t.getText().equals("AND") || t.getText().equals("OR"))
									{
										break;
									}
									else
									{
										if(t.getText().contains("<Term:") || t.getText().contains("<Place:") || t.getText().contains("<Author:") || t.getText().contains("<Category:"))
										ll.add(0,new Node("AND"));
										else
										ll.add(0, new Node(defaultOperator));
										break;
									}
							}
						}
						else							
						{
							if(p.getText().contains("<Term:") || p.getText().contains("<Place:") ||p.getText().contains("<Author:") || p.getText().contains("<Category:"))
								ll.add(0,new Node("AND"));
							else
								ll.add(0, new Node(defaultOperator));
						}
				}				
			}
		}
		return returnList;
	}

	public static Node parseString(LinkedList<Node> stringQueryList)
	{
		Node returnNode = null;
		while(stringQueryList.size()>0)
		{
		
		Node operand1 = getOperand(stringQueryList);
		Node operator = getOperator(stringQueryList);
		Node operator2 = getOperand(stringQueryList);
		
		if(operator == null && operator2 == null)
		{
			return operand1;
		}
		
		operator.setLeftNode(operand1);
		operator.setRightNode(operator2);
		
		returnNode = operator;
		
		
		
		Node peekNode = stringQueryList.peek();
		if(stringQueryList.size()==0)
		{
			break;
		}
		else
			if(peekNode.getText().equals(")"))
		{
				stringQueryList.remove();
			break;
		}
			
		else
		{
			stringQueryList.add(0, operator);
		}		
		
		}
		
		return returnNode;
	}
	
	
	public static Node getOperand(LinkedList<Node> stringQueryList)
	{
		if(!(stringQueryList.size()<=0))
		{
		String peekString = stringQueryList.peek().getText();
		if(peekString.contains("Author:") || peekString.contains("Place:") || peekString.contains("Category:") || peekString.contains("Term:"))
		{
			Node s = stringQueryList.remove();			
			return s;
		}
		else
			if(peekString.equals("("))
			{
				stringQueryList.remove();
				return parseString(stringQueryList);
			}
			else
				if((peekString.equals("AND") || peekString.equals("OR")) &&
						(stringQueryList.peek().getLeftNode()!=null && stringQueryList.peek().getRightNode()!=null) )
				{
					return stringQueryList.remove();
				}
				else
					if(peekString.equals(")"))
				{
					stringQueryList.remove();
				}
		}
		return null;
	}
	
	public static Node getOperator(LinkedList<Node> stringQueryList)
	{
		while(true && stringQueryList.size()!=0)
		{			
				Node st = stringQueryList.remove();
				String s = st.getText();
				if(s.equals(")"))
				{
					continue;
				}
				else
				{
					return st;
				}			
		}
		
		return null;
	}
	
	public static ArrayList<Node> annotateTerms(ArrayList<Node> stringList)
	{
		if(stringList!=null)
		{
		Object[] stringArray = stringList.toArray();
			   	    
		for(int i = 0; i<stringArray.length ; i++)
		{
			Node n = (Node) stringArray[i];
			String s = n.getText();
			if(!(s.equalsIgnoreCase("AND") || s.equalsIgnoreCase("OR") || s.equals("(") || s.equals(")")))
			{
				if(s.equals("Category:") || s.equals("Place:") || s.equals("Author:") || s.equals("Term:"))
				{	
					stringArray[i] = null;
					while(true)
					{
						Node y = (Node) stringArray[++i];
						if(y.getText().equals("("))
						{
						  continue;
						}
						else
							if(y.getText().equals(")"))
							{
							  break;
							}									
							else
							{
								if(!(y.getText().equals("AND") || y.getText().equals("OR")))
								{
									if(y.getText().contains("NOT"))
									{
										stringArray[i] = new Node("<" + s+y.getText().substring(4) + ">");
									}
									else
									stringArray[i] = new Node(s+y.getText());
								}								
							}								
					}
				}
				else
					if(s.contains("Category:") || s.contains("Place:") || s.contains("Author:") || s.contains("Term:"))
					{
						
					}
					else {
						
						if(((Node)stringArray[i]).getText().contains("NOT"))
						{
							stringArray[i] = new Node("<Term:"+ ((Node)stringArray[i]).getText().substring(4) + ">");
						}
						else
						stringArray[i] = new Node("Term:"+ ((Node)stringArray[i]).getText());
					}				
			}
			
		}
		
		ArrayList<Node> stringL = new ArrayList<Node>();
		 
		for(Object s : stringArray)
		{
			if(s!=null)
			stringL.add((Node)s);
		}
		
		return stringL;
	}
		return null;
	}
	
	public static ArrayList<Node> splitInTokens(String userQuery)
	{
		if(userQuery != null)
		{
		String[] stringArray = userQuery.split(" ");
		ArrayList<Node> stringList = new ArrayList<Node>();
		for(int i = 0 ; i<stringArray.length ; i++)
		{
			String s = stringArray[i];
			
			if(s.contains("\""))
			{
				String temp = new String();
				temp = temp + s + " ";
				
				while(i<stringArray.length-1)
				{
					String y = stringArray[++i];
					if(y.contains("\""))
					{
						temp = temp + y + " ";
						break;
					}
					else
					{
						temp = temp + y + " ";
					}
				}
				stringList.add(new Node(temp.trim()));
			}
			else
				if(s.equals("NOT"))
				{
					String temp  = s;
					String y = stringArray[++i];
					temp = temp + " " + y;
					stringList.add(new Node(temp));
				}
			else
			{
				stringList.add(new Node(s));
			}
		}
		
		return stringList;
		}
		return null;
	}
	
	public static String putSpacesBetweenParenthesis(String userQuery)
	{
		if(userQuery != null)
		{
		Pattern pattern = Pattern.compile("(\\()(?=\\w|\\()");
		Matcher matcher = pattern.matcher(userQuery);
		String s = null;
			
		if(matcher.find())
		{
			s = matcher.replaceAll("( ");
		}
		
		pattern = Pattern.compile("(?<=\\w|\\))(\\))");
		matcher = pattern.matcher(s==null?userQuery:s);
		if(matcher.find())
		{
			s = matcher.replaceAll(" )");
		}		
			
		pattern = Pattern.compile("Category:(?=\\()");
		matcher = pattern.matcher(s==null?userQuery:s);
		if(matcher.find())
		{
			s = matcher.replaceAll("Category: ");
		}	
		
		pattern = Pattern.compile("Place:(?=\\()");
		matcher = pattern.matcher(s==null?userQuery:s);
		if(matcher.find())
		{
			s = matcher.replaceAll("Place: ");
		}	
		
		pattern = Pattern.compile("Author:(?=\\()");
		matcher = pattern.matcher(s==null?userQuery:s);
		if(matcher.find())
		{
			s = matcher.replaceAll("Author: ");
		}	
		
		pattern = Pattern.compile("Term:(?=\\()");
		matcher = pattern.matcher(s==null?userQuery:s);
		if(matcher.find())
		{
			s = matcher.replaceAll("Term: ");
		}	
		return s==null?userQuery:s;
		}
		return null;
	}
	
	public static void main (String[] args) throws IOException
	{
		String[] query = {"hello","hello world","\"hello world\"","orange and yellow",
				"(black OR blue) AND bruises","Author:rushdie NOT jihad","Category:War AND Author:Dutt And Place:Baghdad AND prisoners detainees rebels",
				"(Love NOT War) AND Category:(movies NOT crime)"};
		
		for(String s : query)
		QueryParser.parse(s, "OR");
	}
}
