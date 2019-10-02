package de.tt.treg.server.controller.preparator;

import java.util.ArrayList;
import java.util.List;

import de.tt.treg.server.controller.calculation.EttlingerPriceCalculation;
import de.tt.treg.server.controller.preparator.player.CompetitionPreparator;
import de.tt.treg.server.controller.preparator.player.PlayerPreparator;
import de.tt.treg.server.controller.preparator.player.PricePreparator;
import de.tt.treg.server.controller.preparator.player.UserPreparator;
import de.tt.treg.server.domain.Player;

public class PreparerFactory {

	public IPreparator<Player> createPlayerPreparator() {
		List<ISinglePreparator<Player>> preparators = getBasicPreparers();
		preparators.add(new PricePreparator());
		return getNewPreparator(preparators);
	}

	public IPreparator<Player> createPlayerPreparatorLight() {
		return getNewPreparator(getBasicPreparers());
	}
	

	private List<ISinglePreparator<Player>> getBasicPreparers() {
		List<ISinglePreparator<Player>> preparators = new ArrayList<ISinglePreparator<Player>>();
		preparators.add(new UserPreparator());
		preparators.add(new CompetitionPreparator());
		return preparators;
	}

	private IPreparator<Player> getNewPreparator(
			List<ISinglePreparator<Player>> preparators) {
		IPreparator<Player> preparator = new PlayerPreparator();
		preparator.setSinglePreparators(preparators);
		return preparator;
	}

}
