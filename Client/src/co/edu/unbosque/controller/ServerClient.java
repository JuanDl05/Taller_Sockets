package co.edu.unbosque.controller;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

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
		String message = "";

		while (!word.equalsIgnoreCase("salir")) {
			try {
				this.socket = new Socket(this.address, this.port);
				System.out.println("Conectando al servidor");
				System.out.println("Digite su nombre: ");

				this.out = new DataOutputStream(socket.getOutputStream());
				this.in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

				word = sc.nextLine();
				this.out.writeUTF(word);

				int option = 0;
				boolean over = false;

				while (!over) {

					System.out.println("Bienvenido a: <<Hamburguesas El Establo>>");
					System.out.println("1. Menu de hamburguesas");
					System.out.println("2. Menu de bebidas");
					System.out.println("3. Menu de papas");
					System.out.println("4. Salir");
					System.out.println("Que opcion desea elegir?");
					System.out.println("-------------------------------------------");

					option = sc.nextInt();
					out.writeInt(option);

					switch (option) {
					case 1: {
						message = in.readUTF();
						System.out.println(message);
						break;
					}
					case 2: {
						message = in.readUTF();
						System.out.println(message);
						break;
					}
					case 3: {
						message = in.readUTF();
						System.out.println(message);
						break;
					}
					case 4: {
						message = in.readUTF();
						System.out.println(message);
						over = true;
						break;
					}
					default:
						message = in.readUTF();
						System.out.println(message);
						break;
					}
				}

				this.out.close();
				this.socket.close();
				this.server = new ServerSocket(this.port + 1);
				this.socket = server.accept();
				System.out.println("Mensaje recibido! \n");
				this.in.close();
				this.out.close();
				this.server.close();

			} catch (IOException i) {
				System.out.println(i);
			}
		}
		try {
			in.close();
			out.close();
			socket.close();
		} catch (IOException i) {
			System.out.println(i);
		}

	}
	public static void main(String[] args) {
		ServerClient client = new ServerClient("127.0.0.1", 9100);
		client.start();
	}

}
