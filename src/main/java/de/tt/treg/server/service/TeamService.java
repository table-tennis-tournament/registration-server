package de.tt.treg.server.service;

import java.util.List;

import de.tt.treg.server.domain.Team;

public interface TeamService {

	public List<Team> getAllTeams();

	public Team saveTeam(Team teamToUpdateSave);

	public Team getTeamById(int id);

	public void removeTeamById(int id);

}
