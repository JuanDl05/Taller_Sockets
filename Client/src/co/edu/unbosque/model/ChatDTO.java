package co.edu.unbosque.model;

import java.io.Serializable;

public class ChatDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1864676271689711159L;
	private String rta;

	public ChatDTO() {	
	}

	public ChatDTO(String rta) {
		super();
		this.rta = rta;
	}

	public String getRta() {
		return rta;
	}

	public void setRta(String rta) {
		this.rta = rta;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "ChatBot [Opcion: " + rta + "]";
	}
	
	
	
}
