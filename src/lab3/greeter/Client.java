package lab3.greeter;
import java.net.*;
import java.io.*;

public class Client {
	public static void main(String[] args) {
		InetAddress host;
		int serverPort = 1024;
		Socket socket = null;
		BufferedReader consoleInput;
		String name = "";
		OutputStream os = null;
		InputStream is = null;
		try {
			host = InetAddress.getLocalHost();
			socket = new Socket(host, serverPort);
			do {
				// read in a name
				System.out.println("Enter a name : ");
				consoleInput = new BufferedReader(new InputStreamReader(
						System.in));
				name = consoleInput.readLine();

				if (!(name.equals("stop"))) {
					// send the name
					byte[] messageBytes = name.getBytes();
					os = socket.getOutputStream();
					os.write(messageBytes);
					// receive reply
					byte[] buffer = new byte[50];
					is = socket.getInputStream();
					is.read(buffer);

					// print the greeting
					System.out.println("Reply from server = "
							+ (new String(buffer)));
				}
			} while (!(name.equals("stop")));
		} catch (UnknownHostException e) {
			System.err.println(e);
		} catch (SocketException e) {
			System.err.println(e);
		} catch (IOException e) {
			System.err.println(e);
		} finally {
			if (socket != null) {
				try {
					socket.close();
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

