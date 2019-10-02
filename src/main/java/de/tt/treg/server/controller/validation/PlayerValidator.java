package de.tt.treg.server.controller.validation;

import java.util.List;

import org.apache.log4j.Logger;

import de.tt.treg.server.domain.Player;

/**
 * This class validates the player data. It does some basic checks like the
 * count of players not null and are all necessary values set.
 * 
 * @author Dane
 * 
 */
public class PlayerValidator implements IPlayerDataValidator {

	private Logger logger = Logger.getLogger(PlayerValidator.class);

	@Override
	public boolean isDataValid(List<Player> playersToCheck) {
		if (playersToCheck.size() < 1) {
			logger.info("no players were set to insertNewPlayers method");
			return false;
		}
		return true;
	}

}
