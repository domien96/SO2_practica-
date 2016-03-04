package lab3.greeter;
import java.net.*;
import java.io.*;

public class Server {
	 public static void main(String[] args) {
	      ServerSocket listen=null;
	      int serverPort=1024;
	      int bufferSize=100;
	      
	      try {
	    	  listen = new ServerSocket(serverPort);
	    	  while(true) {
	    		  byte[] buffer = new byte[bufferSize];
	    		  Socket socket = listen.accept();
				  InputStream is = socket.getInputStream();
				  OutputStream os = socket.getOutputStream();
				  is.read(buffer);
	    		  String rMess="Hello, "+ (new String(buffer));
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
	      }
	   }

}
