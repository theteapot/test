package transferFileOverLan;

import java.io.*;
import java.net.*;
import java.util.Collections;
import java.util.Enumeration;
import java.util.zip.GZIPInputStream;


public class Server {

	public static String getFilePath() throws IOException, URISyntaxException {
		System.out.print("Enter filepath: ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String pathString = br.readLine();
		pathString = pathString.replace('\\', '/');
		return pathString;
	}
	
	public static void displayInetAddresses() throws UnknownHostException {
		try {
			  InetAddress inet = InetAddress.getLocalHost();
			  InetAddress[] ips = InetAddress.getAllByName(inet.getCanonicalHostName());
			  if (ips  != null ) {
			    for (int i = 0; i < ips.length; i++) {
			      System.out.println(ips[i]);
			    }
			  }
			} catch (UnknownHostException e) {

			};
	}

	@SuppressWarnings("resource")
	public static void main (String [] args) throws IOException, URISyntaxException {
		
		File transferFile = new File(getFilePath());
		String fileName = transferFile.getName();
		StringReader nameReader = new StringReader(fileName);
		
		ServerSocket serverSocket = new ServerSocket(0);
		System.out.format("Your port is %d\n",serverSocket.getLocalPort());
		
		displayInetAddresses();
		
		boolean moreConnections = true;
		while (moreConnections) {
			Socket socket = serverSocket.accept();
			System.out.println("Accepted connection : " + socket);
					    
		    byte [] bytearray  = new byte [1024];
		    FileInputStream fin = new FileInputStream(transferFile);
		    BufferedInputStream bin = new BufferedInputStream(fin);   
		    
		    System.out.println("Attempting to start transfer...");
		    OutputStream os = socket.getOutputStream();
		    int read = -1;
		    try {
		    	System.out.println("Transfer started...");
	            while( (read = bin.read(bytearray)) != -1) {
	                os.write(bytearray, 0, read);
	            }
	        } finally {
	        	socket.close();
	        	bin.close();
	        	os.close();
	        	System.out.println("File transfer complete");
	        }
		    System.out.print("Are there more connections y/n : ");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String response = br.readLine();
			if (response != "y") {
				moreConnections = false;
			}
		    
		}
	    /*os.write(bytearray,0,bytearray.length);
	    os.flush();
	    socket.close();*/

	}
	
}
