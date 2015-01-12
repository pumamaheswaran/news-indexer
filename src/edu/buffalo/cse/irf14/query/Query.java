package edu.buffalo.cse.irf14.query;

import java.util.LinkedList;

/**
 * Class that represents a parsed query
 * @author nikhillo
 *
 */
public class Query {
	
	private String queryID;
	private String query;
	private String unparsedQuery;
	private Node   rootNode;
	
	public Query()
	{
		this.queryID = "Default";
	}
	
	public Query(String query)
	{
		this.query = query;
	}
	
	public Query(String queryID, String query)
	{
		this.queryID = queryID;
		this.query = query;
	}
	
	/**
	 * Method to convert given parsed query into string
	 */
	public String toString() {
		//TODO: YOU MUST IMPLEMENT THIS
		return query;
	}
	
	public String getQueryID() {
		return queryID;
	}
	public void setQueryID(String queryID) {
		this.queryID = queryID;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getUnparsedQuery() {
		return unparsedQuery;
	}

	public void setUnparsedQuery(String unparsedQuery) {
		this.unparsedQuery = unparsedQuery;
	}

	public Node getRootNode() {
		return rootNode;
	}

	public void setRootNode(Node rootNode) {
		this.rootNode = rootNode;
	}
	
	public LinkedList<Node> traverseInOrder()
	{
		LinkedList<Node> ll = new LinkedList<Node>();
		inOrder(this.rootNode,ll);
		return  ll;
	}
	
	private void inOrder(Node root,LinkedList<Node> ll)
	{
		if(root == null)
			return;
		inOrder(root.getLeftNode(),ll);
		ll.add(root);
		inOrder(root.getRightNode(),ll);
	}
	
}
