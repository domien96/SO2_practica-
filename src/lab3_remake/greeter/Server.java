package lab3_remake.greeter;
import java.net.*;
import java.io.*;

public class Server {
	 public static void main(String[] args) {
	      ServerSocket ssocket=null;
	      int serverPort=1024;
	      int bufferSize=100;
	      
	      try {
	    	  ssocket=new ServerSocket(serverPort);
			  Socket socket = ssocket.accept();
	    	  while(true) {
	    		  byte[] buffer=new byte[bufferSize];
	    		  int length = socket.getInputStream().read(buffer);
				  if (length>0) {
					  String rMess="Hello, "+ (new String(buffer,0,length));
					  socket.getOutputStream().write(rMess.getBytes());
				  } else {
					  break;
				  }
			  }
	      } catch(SocketException e) {
	    	  System.err.println(e);
	      } catch(IOException e) {
	    	  System.err.println(e);
	      } finally {
	    	  if(ssocket!=null)
				  try {
					  ssocket.close();
				  } catch (IOException e) {
					  e.printStackTrace();
				  }
		  }
	   }

}
