package de.tt.treg.server.controller.preparator.player;

import de.tt.treg.server.controller.preparator.ISinglePreparator;
import de.tt.treg.server.domain.Player;

/**
 * @author Dane This preparerer removes the user before returning the data to
 *         client. You shouldnt see the user in json result, only in Email you
 *         get the access.
 * 
 */
public class UserPreparator implements ISinglePreparator<Player> {

	@Override
	public Player prepare(Player typeToPrepare) {
		typeToPrepare.setUser(null);
		return typeToPrepare;
	}

}
