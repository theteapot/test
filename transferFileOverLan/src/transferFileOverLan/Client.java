package transferFileOverLan;

import java.io.*;
import java.net.*;
import java.util.zip.GZIPOutputStream;

public class Client {
	
	public static String getSocket() throws IOException {
		System.out.print("enter ip: ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String socketString = br.readLine();
		return socketString;
	}
	
	public static int getPort() throws IOException {
		System.out.print("enter port number: ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String portString = br.readLine();
		return Integer.parseInt(portString);
	}
	
	public static String getName() throws IOException {
		System.out.print("enter desired name: ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String stringName = br.readLine();
		return stringName;
	}
	
	public static void main (String [] args) throws IOException {
		int fileSize = 2022386;
		
		Socket socket = new Socket(getSocket(), getPort());
				
	    byte [] bytearray  = new byte [fileSize];
		InputStream is = socket.getInputStream();
	    FileOutputStream fos = new FileOutputStream(getName());
	    BufferedOutputStream bos = new BufferedOutputStream(fos);
	    
	    int read = -1;
	    long totalBytes = 0;
	    try {
	    	while ( (read = is.read(bytearray)) != -1) {
	    		bos.write(bytearray, 0, read);
	    		totalBytes += read;
	    		System.out.format("\r%d",totalBytes/(1000000));	
	    	}
	    } finally {
	    	socket.close();
	    	bos.close();
	    	is.close();
	    	System.out.println("\n Socket closed");
	    }
	    
	    
	    /*bytesRead = is.read(bytearray,0,bytearray.length);
	    currentTot = bytesRead;

	    do {
	       bytesRead =
	          is.read(bytearray, currentTot, (bytearray.length-currentTot));
	       if(bytesRead >= 0) currentTot += bytesRead;
	    } while(bytesRead > -1);

	    bos.write(bytearray, 0 , currentTot);
	    bos.flush();
	    socket.close();*/
	}  
}

