package co.edu.unbosque.controller;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Scanner;

import co.edu.unbosque.model.ChatDTO;

public class ServerClient extends Thread{
	
	private Socket socket;
	private ServerSocket server;
	private Scanner sc;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private String address;
	private int port;

	public ServerClient(String address, int port) {
		this.socket = null;
		this.server = null;
		this.sc = new Scanner(System.in);
		this.out = null;
		this.address = address;
		this.port = port;

	}

	@Override
	public void run() {
		String line = "";

		while (!line.equals("Over")) {

			try {
				this.socket = new Socket(this.address, this.port);
				System.out.println("Connected");

				this.socket.setSoTimeout(2000);
				this.out = new ObjectOutputStream(socket.getOutputStream());

				ChatDTO chat = new ChatDTO("");
				line = sc.next();
				this.out.writeUTF(line);
				
				this.out.close();
				this.socket.close();
				this.server = new ServerSocket(this.port + 1);
				this.socket = server.accept();
				System.out.println("Received message:");
				this.in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
				System.out.println(in.readUTF());
				this.in.close();
				this.server.close();
			} catch (SocketTimeoutException ste) {
                System.out.println("Server is not responding. Closing connection.");
               line = "Over"; 
                try {
                    this.socket.close();
                } catch (IOException e) {
                    System.out.println("Error closing socket: " + e.getMessage());
                }
            } catch (IOException i) {
                System.out.println("Error: " + i.getMessage());
            }
		}
		try {
			out.close();
			socket.close();
		} catch (IOException i) {
			System.out.println(i);
		}

	}
	public static void main(String[] args) {
		ServerClient client = new ServerClient("127.0.0.1", 8000);
		client.start();

	}

}
