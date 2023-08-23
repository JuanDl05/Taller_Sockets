package co.edu.unbosque.controller;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
		
		private Socket socket; 
		private Socket socketR;
		private ServerSocket server;
		private DataInputStream in;
		private DataOutputStream out;
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

			String word = "";
			while (!word.equalsIgnoreCase("Terminado")) {
				try {
					this.server = new ServerSocket(this.port);
					System.out.println("Servidor iniciado");
					System.out.println("Esperando por un cliente...");
					this.socket = server.accept();
					System.out.println("- Cliente aceptado!");
					this.in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

					word = in.readUTF();
					System.out.println("El cliente envio: " + word + "\n");

					this.socketR = new Socket(this.socket.getInetAddress(), this.port + 1);
					this.out = new DataOutputStream(socketR.getOutputStream());
					this.out.writeUTF("Usted envio: " + word);
					this.out.close();
					this.socketR.close();

					this.in.close();
					this.server.close();
				} catch (IOException i) {
					System.out.println(i);
				}
			}
			System.out.println("Conexion terminada.");

			try {
				socket.close();
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
}
