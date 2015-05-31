import java.util.*;
import java.io.*;
import java.net.*;
public class XMLParser {
	private ArrayList<String> buffer;
	private ArrayList<String> tokens;
	private String version="";
	private TreeNode nodeRoot;
	
	public XMLParser()throws IOException, MalformedURLException{
			URLConnection connection = new URL("http://api.eve-central.com/api/quicklook?regionlimit=10000068").openConnection();
			InputReader reader = new InputReader(new InputStreamReader(connection.getInputStream()));
			buffer = reader.getBuffer(); //This gets raw XML.
			tokens = new Tokenizer(buffer).getTokens();
			//for (String s:tokens) System.out.println(s); //Troubleshooting code.
			if (tokens.get(0).contains("<?xml version")){
				version = tokens.get(0);
				tokens.remove(0);
			}
			nodeRoot = new TreeNode(tokens);
			//nodeRoot.printOut();
			
	}
	public String getVersion(){
		return version;
	}
	public TreeNode getRootNode(){
		return nodeRoot;
	}
	public static void main(String[] args)throws Exception{
		new XMLParser();
	}
}
