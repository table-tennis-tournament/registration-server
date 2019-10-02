package de.tt.treg.server.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import de.tt.treg.server.controller.helper.DateHelper;

public class CompetitionInformation implements IIdentable {
	
	private int id;

	private String name;

	private int maxParticipants;
	
	private String startDay;
	
	private int registrationCount;
	
	private int openSlots;
	
	private int waitingListCount;
	
	private String startTime;
	
	private Date competitionDay;
	
	private int kind;
	
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public int getWaitingListCount() {
		return waitingListCount;
	}

	public void setWaitingListCount(int waitingListCount) {
		this.waitingListCount = waitingListCount;
	}

	public Date getCompetitionDay() {
		return competitionDay;
	}

	public void setCompetitionDay(Date competitionDay) {
		this.competitionDay = competitionDay;
	}

	private Set<PlayerCompetition> playerCompetitions = new HashSet<PlayerCompetition>();

	public String getStartDay() {
		return startDay;
	}

	public void setStartDay(String startDay) {
		this.startDay = startDay;
	}

	public int getRegistrationCount() {
		return registrationCount;
	}

	public void setRegistrationCount(int registrationCount) {
		this.registrationCount = registrationCount;
	}

	public int getOpenSlots() {
		return openSlots;
	}

	public void setOpenSlots(int openSlots) {
		this.openSlots = openSlots;
	}

	public Set<PlayerCompetition> getPlayerCompetitions() {
		return playerCompetitions;
	}

	public void setPlayerCompetitions(Set<PlayerCompetition> playerCompetitions) {
		this.playerCompetitions = playerCompetitions;
	}

	public void calculate() {
		registrationCount = playerCompetitions.size();
		setOpenSlotsAndWaitingListCount();
		startDay = DateHelper.getDayAndDate(competitionDay);
		startTime = DateHelper.getDateAndTime(competitionDay);
	}

	private void setOpenSlotsAndWaitingListCount() {
		if(maxParticipants < 1){
			openSlots = 0;
			waitingListCount = 0;
			return;
		}
		openSlots = maxParticipants - registrationCount;
		if(openSlots < 0 ){
			waitingListCount = - openSlots;
			openSlots = 0;
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMaxParticipants() {
		return maxParticipants;
	}

	public void setMaxParticipants(int maxParticipants) {
		this.maxParticipants = maxParticipants;
	}

	public int getKind() {
		return kind;
	}

	public void setKind(int kind) {
		this.kind = kind;
	}

}
