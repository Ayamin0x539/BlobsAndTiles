package server.communication;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientConnection extends Thread {
	private Socket socket;
	private String clientName;
	private BufferedReader receiver;
	private DataOutputStream sender;
	
	public ClientConnection(Socket socket, String name) {
		this.socket = socket;
		this.clientName = name;
	}
	
	public void run() {
		System.out.println("Client connection thread spawned: " + clientName); //
		String clientInput = "";
		try {
			receiver = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			sender = new DataOutputStream(socket.getOutputStream());
			do {
				clientInput = receiver.readLine();
				System.out.println("[Client " + clientName + "] : " + clientInput);
				sender.writeBytes("Received: " + clientInput + '\n');
				sender.flush(); // is this necessary? may not be efficient.
			} while (!clientInput.equals(""));
		} catch (IOException e) {
			System.out.println("Client: " + clientName + " must have disconnected.");
		}
	}
}
