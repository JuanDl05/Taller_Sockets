package co.edu.unbosque.controller;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.InputMismatchException;

public class Server extends Thread {

	private Socket socket;
	private Socket socketR;
	private ServerSocket server;
	private DataInputStream in;
	private DataOutputStream out;
	private int port;

	public Server(int port) {

		this.socket = null;
		this.socketR = null;
		this.server = null;
		this.in = null;
		this.out = null;
		this.port = port;

	}

	@Override
	public void run() {

		String word = "";
		while (!word.equalsIgnoreCase(" ")) {
			try {

				this.server = new ServerSocket(this.port);
				System.out.println("Servidor iniciado");
				System.out.println("Esperando por un cliente...");
				this.socket = server.accept();
				System.out.println("- Cliente aceptado!");

				this.in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
				this.out = new DataOutputStream(socket.getOutputStream());

				word = in.readUTF();
				System.out.println("El nombre del cliente es: " + word + "\n");

				int option;
				boolean over = false;

				while (!over) {

					option = in.readInt();
					switch (option) {
					case 1:{
						out.writeUTF(" Hamburguesa sencilla: $13.000 \n Hamburguesa especial: $15.500 \n"
								+ " Hamburguesa crispy onion bbq: $20.000 \n Hamburguesa pollo: $12.000 \n");
						break;
					}
					case 2:{
						out.writeUTF(" Coca-Cola: $4.000 \n Sprite: $3.000 \n Colombiana: $3.000 \n Hit: $2.500 \n");
						break;
					}
					case 3:{
						out.writeUTF(" Papas pequenas: $5.500 \n Papas medianas: $7.000 \n Papas grandes: $8.000 \n");
						break;
					}
					case 4:{
						out.writeUTF("Gracias por ver el menu!. Que tenga un buen dia!");
						over = true;
						break;
					}
					default:{
						out.writeUTF("Solo digite numeros del 1 al 4! \n");
					}
					}
				}
				
			} catch (IOException i) {
				System.out.println(i);
				break;
			}

			try {
				this.socketR = new Socket(this.socket.getInetAddress(), this.port + 1);
				this.out = new DataOutputStream(socketR.getOutputStream());
				this.out.close();
				this.socketR.close();
				this.in.close();
				this.server.close();
				
				System.out.println("Conexion cerrada.");
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
