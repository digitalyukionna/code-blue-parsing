import java.io.*;
import java.net.*;
public class Provider {
	public Provider () throws Exception{
		URLConnection connection = new URL("http://api.eve-central.com/api/quicklook?regionlimit=10000068").openConnection();
		InputStreamReader isr = new InputStreamReader(connection.getInputStream());
		BufferedReader reader = new BufferedReader(isr);
		new TestParser(reader);
	}
	public static void main(String[] args)throws Exception{
		new Provider();
	}
}
