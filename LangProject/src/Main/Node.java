package Main;

import java.util.ArrayList;

public class Node {
	private Node parent;
	private ArrayList<Node> children;
	
	public Node() {
		children = new ArrayList<Node>();
	}
	
	public Node(Node parent) {
		this.parent = parent;
	}
	
	public void setParent(Node newParent) {
		if (parent != null) parent.getChildren().remove(this);
		parent = newParent;
		if (parent != null) parent.getChildren().add(this);
	}
	
	public ArrayList<Node> getChildren() {
		return children;
	}
	
	public void add(Node node) {
		node.setParent(this);
	}
	
}
