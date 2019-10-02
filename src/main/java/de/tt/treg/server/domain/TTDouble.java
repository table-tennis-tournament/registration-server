package de.tt.treg.server.domain;

public class TTDouble implements IIdentable {

	public static final int SEARCH_PLAYER_CONST = -20;

	public TTDouble() {

	}

	public TTDouble(int competitionId, int play1Id, int play2Id,
			String play1FirstName, String play2FirstName, String play1LastName,
			String play2LastName, User user) {
		super();
		this.competitionId = competitionId;
		this.play1Id = play1Id;
		this.play2Id = play2Id;
		this.play1FirstName = play1FirstName;
		this.play2FirstName = play2FirstName;
		this.play1LastName = play1LastName;
		this.play2LastName = play2LastName;
		this.user = user;
	}

	private int id;
	private int competitionId;
	private int play1Id;
	private int play2Id;
	private String play1FirstName;
	private String play2FirstName;
	private String play1LastName;
	private String play2LastName;
	private int doubleKind = 2;
	private int doublePaid = 1;
	private User user;

	@Override
	public int getId() {
		return id;
	}

	public int getCompetitionId() {
		return competitionId;
	}

	public void setCompetitionId(int competitionId) {
		this.competitionId = competitionId;
	}

	public int getPlay1Id() {
		return play1Id;
	}

	public void setPlay1Id(int play1Id) {
		this.play1Id = play1Id;
	}

	public int getPlay2Id() {
		return play2Id;
	}

	public void setPlay2Id(int play2Id) {
		this.play2Id = play2Id;
	}

	public String getPlay1FirstName() {
		return play1FirstName;
	}

	public void setPlay1FirstName(String play1FirstName) {
		this.play1FirstName = play1FirstName;
	}

	public String getPlay2FirstName() {
		return play2FirstName;
	}

	public void setPlay2FirstName(String play2FirstName) {
		this.play2FirstName = play2FirstName;
	}

	public String getPlay1LastName() {
		return play1LastName;
	}

	public void setPlay1LastName(String play1LastName) {
		this.play1LastName = play1LastName;
	}

	public String getPlay2LastName() {
		return play2LastName;
	}

	public void setPlay2LastName(String play2LastName) {
		this.play2LastName = play2LastName;
	}

	public int getDoubleKind() {
		return doubleKind;
	}

	public void setDoubleKind(int doubleKind) {
		this.doubleKind = doubleKind;
	}

	public int getDoublePaid() {
		return doublePaid;
	}

	public void setDoublePaid(int doublePaid) {
		this.doublePaid = doublePaid;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
