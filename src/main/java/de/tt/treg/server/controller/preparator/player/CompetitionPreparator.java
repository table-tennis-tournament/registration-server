package de.tt.treg.server.controller.preparator.player;

import de.tt.treg.server.controller.preparator.ISinglePreparator;
import de.tt.treg.server.domain.Competition;
import de.tt.treg.server.domain.Player;
import de.tt.treg.server.domain.PlayerCompetition;
import de.tt.treg.server.service.impl.helper.CompetitionCache;

public class CompetitionPreparator implements ISinglePreparator<Player> {

	@Override
	public Player prepare(Player typeToPrepare) {
		for (PlayerCompetition playCompetition : typeToPrepare
				.getCompetitions()) {
			Competition competition = CompetitionCache.getInstance()
					.getCompetitionById(
							playCompetition.getPlayerCompetitionPk()
									.getCompetitionId());
			playCompetition.setName(competition.getName());
		}
		return typeToPrepare;
	}
}
