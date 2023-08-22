package co.edu.unbosque.controller;

public class Main {

	public static void main(String[] args) {
		ServerClient client = new ServerClient("127.0.0.1", 9000);
		client.start();
	}

}
