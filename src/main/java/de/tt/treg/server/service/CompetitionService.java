package de.tt.treg.server.service;

import java.util.List;

import de.tt.treg.server.domain.Competition;
import de.tt.treg.server.domain.CompetitionInformation;

public interface CompetitionService {

	public Competition getCompetitionById(int id);

	public List<Competition> getAllCompetitions();

	public List<Competition> getAllSingleCompetitions();

	public List<Competition> getAllDoubleCompetitions();

	public List<Competition> getAllTeamCompetitions();

	public List<CompetitionInformation> getAllCompetitionInformation();
}
