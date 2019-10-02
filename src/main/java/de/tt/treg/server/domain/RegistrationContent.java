package de.tt.treg.server.domain;

public class RegistrationContent implements IIdentable{
	private int id;
	private String text;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String content) {
		this.text = content;
	}
}
