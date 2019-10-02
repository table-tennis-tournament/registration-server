package de.tt.treg.server.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.tt.treg.server.dao.TeamDao;
import de.tt.treg.server.domain.Team;
import de.tt.treg.server.service.TeamService;

@Service("teamService")
@Transactional(readOnly = true)
public class TeamServiceImpl implements TeamService {

	@Autowired
	private TeamDao teamDao;

	@Transactional(readOnly = false)
	public List<Team> getAllTeams() {
		return teamDao.readAll();
	}

	@Transactional(readOnly = false)
	public Team saveTeam(Team teamToUpdateSave) {
		teamDao.save(teamToUpdateSave);
		return teamToUpdateSave;
	}

	@Transactional(readOnly = false)
	public Team getTeamById(int id) {
		return teamDao.findById(id);
	}

	@Override
	public void removeTeamById(int id) {
		Team teamToDelete = teamDao.findById(id);
		if (teamToDelete != null) {
			teamDao.delete(teamToDelete);
		}

	}
}
