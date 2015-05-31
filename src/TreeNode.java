import java.util.*;
public class TreeNode {
	String element="";
	ArrayList<String> data = new ArrayList<String>();//Done in the case of something like words</node>morewords.
	ArrayList<TreeNode> subNodes = new ArrayList<TreeNode>();
	TreeNode parentNode = null;
	public TreeNode(ArrayList<String> inputList){
		processList(inputList);
	}
	public TreeNode(ArrayList<String> inputList, TreeNode parent){
		parentNode = parent;
		processList(inputList);
	}
	
	
	
	private void processList(ArrayList<String> input){
		element = input.get(0);
		String endingElement = "</"+element.substring(1);
		input.remove(0);
		while (!(input.isEmpty())){
			if (!(input.get(0).contains("<"))){
				data.add(input.get(0));
				input.remove(0);
			}
			else if (input.get(0).matches(endingElement)){
				return;
			}
			else {
				int matchLine = matchNextLine(input);
				ArrayList<String> subList = new ArrayList<String>();
				for (int counter = 0; counter<matchLine+1;counter++){
					subList.add(input.get(0));
					input.remove(0);
				}
				subNodes.add(new TreeNode(subList, this));
			}
		}
				
	}
	private int matchNextLine(ArrayList<String> input){
		String temp = "</"+input.get(0).substring(1);
		for (int counter = 0; counter<input.size();counter++){
			if (input.get(counter).contains(temp)){
				return counter;
			}
		}
		return -1;
	}
	public void printOut(){
		System.out.println("My element is:" + element);
		if (!(parentNode ==null)){
			System.out.println("My parent node is:"+parentNode.getElement());
		}
		System.out.println("My data includes:");
		for (String s : data){
			System.out.println(s);
		}
		System.out.println("I have " + subNodes.size() +" sub nodes.");
		System.out.println("Listing subnodes.");
		for (TreeNode tn : subNodes){
			tn.printOut();
		}
	}
	public TreeNode getParent(){
		return parentNode;
	}
	public String getElement(){
		return element;
	}
	public ArrayList<String> getData(){
		return data;
	}
	public ArrayList<TreeNode> getSubNodes(){
		return subNodes;
	}
	public TreeNode getFirstNodeWith(String element){
		if (element.matches(this.element)){
			return this;
		}
		if (subNodes.size() == 0){
			return null;
		}
		for (TreeNode tn : subNodes){
			TreeNode temp = tn.getFirstNodeWith(element);
			if (!(temp==null)){
				return temp;
			}
		}
		return null;
	}
	public ArrayList<TreeNode> getNodesWithElement(String s){
		ArrayList<TreeNode> tempNodes= new ArrayList<TreeNode>();
		if (s.matches(element)){
			tempNodes.add(this);
			return tempNodes;
		}
		if (subNodes.size() == 0){
			return null;
		}
		for (TreeNode tn : subNodes){
			ArrayList<TreeNode> unstableTempNodes = tn.getNodesWithElement(s);
			if (!(unstableTempNodes == null)){
				for (TreeNode tn1 : unstableTempNodes){
					tempNodes.add(tn1);
				}
			}
		}
		return tempNodes;
	}
}
