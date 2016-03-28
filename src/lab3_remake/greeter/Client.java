package lab3_remake.greeter;
import java.net.*;
import java.io.*;

public class Client {
	public static void main(String[] args) {
		InetAddress host = null;
		int serverPort = 1024;
		Socket socket = null;
		InputStream in = null;
		OutputStream out = null;
		BufferedReader consoleInput = new BufferedReader(new InputStreamReader(
				System.in));
		String name = "";
		try {
			host = InetAddress.getLocalHost();
			socket = new Socket(host,serverPort);
			out = socket.getOutputStream();
			in = socket.getInputStream();
			do {
				// read in a name
				System.out.println("Enter a name : ");
				consoleInput = new BufferedReader(new InputStreamReader(
						System.in));
				name = consoleInput.readLine();

				if (!(name.equals("stop"))) {
					// send the name
					out.write(name.getBytes());

					// receive reply
					byte[] buffer = new byte[50];
					int length = in.read(buffer,0,50);

					// print the greeting
					System.out.println("Reply from server = "
							+ (new String(buffer,0,length)));
				}
			} while (!(name.equals("stop")));
		} catch (UnknownHostException e) {
			System.err.println(e);
		} catch (SocketException e) {
			e.printStackTrace();
			System.err.println(e);
		} catch (IOException e) {
			System.err.println(e);
		} finally {
			if (socket != null) {
				try {
					socket.close();
					out.close();
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

