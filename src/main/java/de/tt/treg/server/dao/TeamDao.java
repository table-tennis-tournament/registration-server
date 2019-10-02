package de.tt.treg.server.dao;

import java.util.List;

import de.tt.treg.server.domain.Team;

public interface TeamDao extends AbstractDao<Team, Integer> {

	public List<Team> readAll();

	public Team findByName(String name);

}
