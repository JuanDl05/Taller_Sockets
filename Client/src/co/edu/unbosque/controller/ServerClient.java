package co.edu.unbosque.controller;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import co.edu.unbosque.model.ChatDTO;

public class ServerClient extends Thread {

	private Socket socket;
	private ServerSocket server;
	private Scanner sc;
	private DataOutputStream out;
	private DataInputStream in;
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

		String word = "";

		while (!word.equalsIgnoreCase("Terminado")) {
			try {
				this.socket = new Socket(this.address, this.port);
				System.out.println("Conectado al servidor");
				System.out.println("Digite un mensaje:");

				this.out = new DataOutputStream(socket.getOutputStream());

				word = sc.nextLine();
				this.out.writeUTF(word);
				this.out.close();
				this.socket.close();
				this.server = new ServerSocket(this.port + 1);
				this.socket = server.accept();
				System.out.println("Mensaje recibido! \n");
				this.in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
				System.out.println(in.readUTF());
				this.in.close();
				this.out.close();
				this.server.close();
			} catch (IOException i) {
				System.out.println(i);
			}
		}
		try {
			out.close();
			socket.close();
		} catch (IOException i) {
			System.out.println(i);
		}

	}

}
