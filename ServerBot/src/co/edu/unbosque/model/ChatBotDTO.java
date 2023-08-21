package co.edu.unbosque.model;

import java.io.Serializable;

public class ChatBotDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6049969485056706711L;

	private String text;

	public ChatBotDTO() {
		// TODO Auto-generated constructor stub
	}

	public ChatBotDTO(String text) {
		super();
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "ChatBotDTO [msg=" + text + "]";
	}
}
