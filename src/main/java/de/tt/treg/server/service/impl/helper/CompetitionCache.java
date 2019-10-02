package de.tt.treg.server.service.impl.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.tt.treg.server.domain.Competition;

public class CompetitionCache {

	private static CompetitionCache competitionCache;

	public static CompetitionCache getInstance() {
		if (competitionCache == null) {
			competitionCache = new CompetitionCache();
		}
		return competitionCache;

	}

	private CompetitionCache() {

	}

	private Map<Integer, Competition> competitionMap;
	
	private Map<Integer, int[]> doubleIdToSingleCompetitionMap;

	public Competition getCompetitionById(int id) {
		if (competitionMap.containsKey(id)) {
			return competitionMap.get(id);
		}
		return null;
	}

	public void setCompetitions(List<Competition> allCompetitions) {
		competitionMap = new HashMap<Integer, Competition>();
		for (Competition competition : allCompetitions) {
			competitionMap.put(competition.getId(), competition);
		}
	}

	public List<Competition> getSingleCompetitionToDouble(int competitionId) {
		if(doubleIdToSingleCompetitionMap == null){
			doubleIdToSingleCompetitionMap = createDoubleIdToSingleIdMap();
		}
		List<Competition> result = new ArrayList<Competition>();
		int[] singleCompetitionIds = doubleIdToSingleCompetitionMap.get(competitionId);
		if(singleCompetitionIds == null){
			return result;
		}
		for(int i = 0; i<singleCompetitionIds.length; i++){
			result.add(getCompetitionById(singleCompetitionIds[i]));
		}
		return result;
	}

	private Map<Integer, int[]> createDoubleIdToSingleIdMap() {
		Map<Integer, int[]> result = new HashMap<Integer, int[]>();
		result.put(3, new int[]{2,5});
		result.put(4, new int[]{6,1});
		result.put(8, new int[]{8,7});
		result.put(10, new int[]{9});
		result.put(13, new int[]{12,14});
		result.put(16, new int[]{15});
		result.put(19, new int[]{17,20});
		result.put(21, new int[]{22});
		
		result.put(23, new int[]{27, 15});
		
		return result;
	}

}
