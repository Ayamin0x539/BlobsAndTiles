package client.communication;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.function.Supplier;

public class Client {
	private final String HOSTNAME = "localhost";
	private final int PORT_NO = 1337;
	private Socket socket = null;
	
	private static Supplier<Client> clientSupplier = () -> new Client();
	
	public static Client getInstance() {
		return clientSupplier.get();
	}

	private Client() {
		try {
			socket = new Socket(HOSTNAME, PORT_NO);
		} catch (IOException e) {
			System.out.println("Could not create socket.");
		}
	}
	
	/**
	 * Connects to a host, sends and receives.
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public void run() throws UnknownHostException, IOException {
		String dataOut = "";
		String dataIn = "";
		BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
		DataOutputStream sender = new DataOutputStream(socket.getOutputStream());
		BufferedReader receiver = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		do {
			System.out.print("Enter your command: ");
			dataOut = inputReader.readLine();
			sender.writeBytes(dataOut + '\n');
			sender.flush(); // is this necessary? may not be efficient.
			dataIn = receiver.readLine();
			System.out.println("Response: " + dataIn + '\n');
		} while (!dataOut.equals(""));
		socket.close();
	}
	
	public static void main(String args[]) {
		Client client = Client.getInstance();
		try {
			client.run();
		} catch (IOException e) {
			System.out.println("Lost connection to the server!");
		}
	}
}
