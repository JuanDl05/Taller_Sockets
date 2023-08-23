package co.edu.unbosque.controller;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
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
	private DataInputStream in;
	private DataOutputStream out;
	private int port;
	private String addressClient;
	private String clientName;

	public Server(int port) {
		
		this.socket = null;
		this.socketR = null;
		this.server = null;
		this.in = null;
		this.out = null;
		this.port = port;
		this.addressClient = addressClient;
		this.clientName = clientName;
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
				
				int opcion;
				
				while(true) {
					try {
						opcion = in.readInt();
						
						switch(opcion) {
						case 1:
							out.writeUTF("Hamburguesa sencilla: 13000 \n Hamburguesa especial: 15500 \n"
									+ " Hamburguesa crispy onion bbq: 20000 \n Hamburguesa pollo: 12000" );
						break;

						case 2:
							out.writeUTF("Coca-Cola: 4000 \n Sprite: 3000 \n Colombiana: 3000 \n Hit: 2500");
						break;
						
						case 3:
							out.writeUTF("Papas pequeñas: 5500 \n Papas medianas: 7000 \n Papas grandes: 8000");
						break;
						
						case 4:
							out.writeUTF("Gracias por ver el menú!. Que tenga un buen día");
						break;
						
						default:
							out.writeUTF("Solo digite números del 1 al 4!");
						}
					}catch(IOException e) {
						
					}
				}
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
	public static void main(String[] args) {
		Server server = new Server(5000);
		server.start();
	}

}
