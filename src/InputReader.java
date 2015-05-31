import java.util.*;
import java.io.*;

/* Takes either an InputStreamReader or FileReader and gathers the resulting Strings
 * into an output buffer, accessed by getBuffer().
 */
public class InputReader {
	BufferedReader reader;
	ArrayList<String> buffer = new ArrayList<String>();
	public InputReader(InputStreamReader isr)throws IOException{
		reader = new BufferedReader(isr);
		fillBuffer();
		reader.close();
	}
	public InputReader(FileReader fr) throws IOException{
		reader = new BufferedReader(fr);
		fillBuffer();
		reader.close();
	}
	private void fillBuffer()throws IOException{
		while (reader.ready()){
			String temp = reader.readLine().trim();
			//System.out.println(temp);
			buffer.add(temp);
		}
	}
	public ArrayList<String> getBuffer(){
		return buffer;
	}
			
}	
