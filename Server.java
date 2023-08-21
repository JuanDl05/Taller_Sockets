package co.edu.unbosque.controller;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import co.edu.unbosque.model.ChatBotDTO;

public class Server extends Thread {

	private Socket socket;
	private Socket socketR;
	private ServerSocket server;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private int port;
	private String addressClient;

	public Server(int port) {
		this.socket = null;
		this.socketR = null;
		this.server = null;
		this.in = null;
		this.out = null;
		this.port = port;
		this.addressClient = addressClient;
	}

	@Override
	public void run() {
		ChatBotDTO cb;

		try {
			this.server = new ServerSocket(this.port);
			System.out.println("Servidor abierto!");
			System.out.println("Esperando usuario ...");
			this.socket = server.accept();
			System.out.println("Usuario aceptado!");
			// takes input from the client socket
			this.in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));

			cb = (ChatBotDTO) in.readObject();

			this.socketR = new Socket(this.socket.getInetAddress(), this.port + 1);
			this.out = new ObjectOutputStream(socketR.getOutputStream());
			this.out.writeUTF("You send this: " + cb.toString() + " :D");
			this.out.close();
			this.socketR.close();

			this.in.close();
			this.server.close();
		} catch (IOException e) {
			System.out.println("jueputa");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Closing connection");

		// close connection
		try {
			socket.close();
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String args[]) {

		Server server = new Server(5000);
		server.start();
	}

}
