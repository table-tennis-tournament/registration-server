package de.tt.treg.server.domain;

public class PlayerLight implements IIdentable{
	private String name;
	private int id;
	
	public PlayerLight() {
		
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public PlayerLight(String name, int id) {
		super();
		this.name = name;
		this.id = id;
	}
}
