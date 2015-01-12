package edu.buffalo.cse.irf14.query;

public class Node {
	
	private String text;
	private Node leftNode;
	private Node rightNode;
	
	public Node(String text)
	{
		this.text = text;
		leftNode = null;
		rightNode = null;
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Node getLeftNode() {
		return leftNode;
	}
	public void setLeftNode(Node leftNode) {
		this.leftNode = leftNode;
	}
	public Node getRightNode() {
		return rightNode;
	}
	public void setRightNode(Node rightNode) {
		this.rightNode = rightNode;
	}
	
	@Override
	public String toString() {
		return text;
	}
	
	@Override
	public boolean equals(Object obj) {
				
		if(obj!=null && obj instanceof Node)
		{
			return this.getText().equals(((Node)obj).getText());
		}
		else
			return false;
	}
}
