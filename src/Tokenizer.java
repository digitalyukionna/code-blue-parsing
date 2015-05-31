import java.util.*;
/* Breaks the XML tokens apart into individual lines.
 * Such that <note>forget this</note><note/><note words="this too"/>
 * looks like: 
 * <note>
 * forget this
 * </note>
 * <note/>
 * <note>
 * <words>
 * "this too"
 * </words>
 */
public class Tokenizer {
	private ArrayList<String> tokens = new ArrayList<String>();
	private ArrayList<String> buffer;
	public Tokenizer(ArrayList<String> buffer){
		this.buffer = buffer;
		breaker();
	}
	private void breaker(){
		if (buffer.get(0).startsWith("<?xml version=")){
			tokens.add(buffer.get(0));
			buffer.remove(0);
		}
		while (!(buffer.isEmpty())){
			String tempFull = buffer.get(0);
			String workingTemp="";
			int lastIndex= 0;
			for (int counter = 0; counter<tempFull.length(); counter++){
				char c = tempFull.charAt(counter);
				if (c == '>'){
					workingTemp = tempFull.substring(lastIndex, counter+1);
					lastIndex = counter+1;
				}
				if (c== '<'&&counter>lastIndex){
					workingTemp = tempFull.substring(lastIndex, counter);
					lastIndex = counter;
					
				}
				if (workingTemp.startsWith("<")&& workingTemp.endsWith(" ")){
					workingTemp.concat(">");//
					tokens.add(workingTemp);
				}
				else if (workingTemp.contains("=")){
					String temp = workingTemp;
					String tempElement = temp.substring(1, temp.indexOf(' '));
					tokens.add("<"+tempElement+">");
					while (temp.contains("=")){
						temp = internalData(temp);
					}
					
				
				}
				else if (workingTemp.endsWith("/>")){
					String element = workingTemp.substring(1, workingTemp.length()-2);
					tokens.add("<"+element+">");
					tokens.add("</"+element+">");
				}
				else if (workingTemp ==""){
					//Do nothing.
				}
				else{
					tokens.add(workingTemp);
				}
				workingTemp = "";
			}
			buffer.remove(0);
		}
	}
	/* For that annoying case where there's a <note id="words"> situation.
	 * Can be run multiple times per line to get every bit of data.
	 */
	private String internalData(String s){

		if (s.startsWith("\"")){
			s = s.substring(1);
		}
			String element2 = s.substring(s.indexOf(' ')+1, s.indexOf('='));
			int dataEnd = getDataEnd(s);
			String data = s.substring(s.indexOf('=')+2, dataEnd);
			tokens.add("<"+element2+">");
			tokens.add(data);
			tokens.add("</"+element2+">");
			return s.substring(dataEnd);
	}
	private int getDataEnd(String s){
		int internalCounter=0;
		for (int counter = 0; counter<s.length();counter++){
			if (s.charAt(counter)=='"'){
				internalCounter++;
			}
			if (internalCounter==2){
					return counter;
			}
		}
		return s.length();
	}
	
	public ArrayList<String> getTokens(){
		return tokens;
	}
}
