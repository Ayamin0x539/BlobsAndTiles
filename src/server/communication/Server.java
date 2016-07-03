package server.communication;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Supplier;

public class Server {
	private ServerSocket serverSocket;
	private final int PORT_NO = 1337;
	private final int MAX_CLIENTS = 5;
	private int numClients;

	private Server() {
		numClients = 0;
		try {
			serverSocket = new ServerSocket(PORT_NO);
		} catch (IOException e) {
			System.out.println("Can't listen on port " + PORT_NO);
		}
	}
	
	private static Supplier<Server> serverSupplier =
			() -> new Server();
			
	private static Server getInstance() {
		return serverSupplier.get();
	}
	
	public void run() throws IOException {
		InetAddress ip = InetAddress.getLocalHost();
		System.out.println("Server starting on IP " + ip.getHostAddress()); //
		do {
			Socket clientSocket = serverSocket.accept();
			ClientConnection threadedConnection = 
					new ClientConnection(clientSocket, Integer.toString(numClients++));
			threadedConnection.start();
		} while (numClients < MAX_CLIENTS);
		System.out.println("Maximum number of clients reached!");
		serverSocket.close();
	}
	
	public static void main(String args[]) throws IOException {
		Server server = Server.getInstance();
		server.run();
	}
}
