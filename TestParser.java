/*
 * Test parser try 2 for XML.  Still trying to save the incoming XML stream 
 * as a data structure.  Last attempt hit a complication based on a bad 
 * character in the stream (I'm guessing; I haven't tested it).
 */
import java.util.*;
import java.io.*;
public class TestParser {

	
	XMLDataHolder holder;
	BufferedReader reader;
	ArrayList<String> input = new ArrayList<String>();
	public TestParser (BufferedReader reader){
		this.reader = reader;
		holder = new XMLDataHolder();
		this.readIn();
	}
	private void readIn(){
		String temp;
		try{
			temp = reader.readLine();
			if (temp.contains("xml version=")){
				//Do nothing for now.
			}
			else{
				input.add(temp);
			}
			while (reader.ready()){
				temp = reader.readLine().trim();
					input.add(temp);
			}
			reader.close();
		}catch(IOException e){
			System.out.println("We have a problem!");
		}
		if (holder.dataIn(input)){//We're fine, no problems!
			System.out.println("Data read in successfully.");
		}
		else{
			System.out.println("Problem parsing XML data.");
		}
	}

}
