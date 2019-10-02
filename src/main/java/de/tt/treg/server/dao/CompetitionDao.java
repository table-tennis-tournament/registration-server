package de.tt.treg.server.dao;

import java.util.List;

import de.tt.treg.server.domain.Competition;
import de.tt.treg.server.domain.CompetitionInformation;

public interface CompetitionDao extends AbstractDao<Competition, Integer> {

	public List<Competition> readAll();

	public List<Competition> getCompetitionByKind(int i);

	public List<CompetitionInformation> getAllCompetitionInformation();
}
