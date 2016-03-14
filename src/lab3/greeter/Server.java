package lab3.greeter;
import java.net.*;
import java.io.*;

public class Server {
	 public static void main(String[] args) {
	      ServerSocket listen=null;
	      int serverPort=1024;
	      int bufferSize=100;
		  OutputStream os = null;
		  InputStream is = null;
	      
	      try {
	    	  listen = new ServerSocket(serverPort);
			  Socket socket = listen.accept();
	    	  while(true) {
	    		  byte[] buffer = new byte[bufferSize];
				  is = socket.getInputStream();
				  int length = is.read(buffer);
				  os = socket.getOutputStream();
	    		  String rMess = "Hello, " + (new String(buffer, 0, length));
				  os.write(rMess.getBytes());
			  }
	      } catch(SocketException e) {
	    	  System.err.println(e);
	      } catch(IOException e) {
	    	  System.err.println(e);
	      } finally {
	    	  if(listen!=null) {
				  try {
					  listen.close();
				  } catch (IOException e) {
					  e.printStackTrace();
				  }
			  }
			  if (os != null) {
				  try {
					  os.close();
				  } catch (IOException e) {
					  e.printStackTrace();
				  }
			  }
			  if (is != null) {
				  try {
					  is.close();
				  } catch (IOException e) {
					  e.printStackTrace();
				  }
			  }
	      }
	   }

}
