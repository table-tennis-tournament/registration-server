package de.tt.treg.server.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.tt.treg.server.dao.CompetitionDao;
import de.tt.treg.server.domain.Competition;
import de.tt.treg.server.domain.CompetitionInformation;
import de.tt.treg.server.service.CompetitionService;

@Service("competitionService")
@Transactional(readOnly = true)
public class CompetitionServiceImpl implements CompetitionService {

	@Autowired
	private CompetitionDao competitionDao;

	private Map<Integer, Competition> competitionMap;

	@Override
	@Transactional(readOnly = true)
	public Competition getCompetitionById(int id) {
		if (competitionMap == null) {
			initCompetitionMap();
		}
		return competitionMap.get(id);
	}

	private void initCompetitionMap() {
		competitionMap = new HashMap<Integer, Competition>();
		List<Competition> competitions = competitionDao.readAll();
		for (Competition competition : competitions) {
			competitionMap.put(competition.getId(), competition);
		}

	}

	@Override
	@Transactional(readOnly = false)
	public List<Competition> getAllCompetitions() {
		return competitionDao.readAll();
	}

	@Override
	public List<Competition> getAllSingleCompetitions() {
		return competitionDao.getCompetitionByKind(1);
	}

	@Override
	public List<Competition> getAllDoubleCompetitions() {
		return competitionDao.getCompetitionByKind(2);
	}

	@Override
	public List<Competition> getAllTeamCompetitions() {
		return competitionDao.getCompetitionByKind(4);
	}

	@Override
	public List<CompetitionInformation> getAllCompetitionInformation() {
		List<CompetitionInformation> competitionInformation = competitionDao.getAllCompetitionInformation();
		return calculateAll(competitionInformation);
	}

	private List<CompetitionInformation> calculateAll(
			List<CompetitionInformation> competitionInformation) {
		for(CompetitionInformation info : competitionInformation){
			info.calculate();
		}
		return competitionInformation;
	}

}
