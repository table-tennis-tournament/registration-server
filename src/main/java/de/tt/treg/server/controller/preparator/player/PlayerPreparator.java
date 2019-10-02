package de.tt.treg.server.controller.preparator.player;

import java.util.ArrayList;
import java.util.List;

import de.tt.treg.server.controller.preparator.IPreparator;
import de.tt.treg.server.controller.preparator.ISinglePreparator;
import de.tt.treg.server.domain.Player;

public class PlayerPreparator implements IPreparator<Player> {

	private List<ISinglePreparator<Player>> preparators;

	@Override
	public List<Player> prepare(List<Player> typesToPrepare) {
		List<Player> result = new ArrayList<Player>();
		if (typesToPrepare == null) {
			return result;
		}
		for (Player player : typesToPrepare) {
			for (ISinglePreparator<Player> preparator : preparators) {
				player = preparator.prepare(player);
			}
			result.add(player);
		}
		return result;
	}

	@Override
	public void setSinglePreparators(List<ISinglePreparator<Player>> preparators) {
		this.preparators = preparators;
	}

}
