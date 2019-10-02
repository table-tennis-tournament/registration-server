package de.tt.treg.server.controller.validation;

import java.util.List;

import org.apache.log4j.Logger;

import de.tt.treg.server.domain.Player;
import de.tt.treg.server.domain.User;

/**
 * This class validates the user which is given from json in controller
 * interface. We do basic null checks and check if email is there.
 * 
 * @author Dane
 * 
 */
public class UserValidator implements IPlayerDataValidator {

	private Logger log = Logger.getLogger(UserValidator.class);

	private boolean checkUser(User userToCheck) {
		if (userToCheck == null) {
			log.info("no user is set null");
			return false;
		}
		if (userToCheck.getUserName() == null) {
			log.info("no username (email) is set");
			return false;
		}
		if (userToCheck.getUserName().equals("")) {
			log.info("no username (email) is set, empty string");
			return false;
		}
		return true;
	}

	@Override
	public boolean isDataValid(List<Player> playersToCheck) {
		Player firstPlayer = playersToCheck.get(0);
		if (firstPlayer == null) {
			log.info("first player is null");
			return false;
		}
		return checkUser(firstPlayer.getUser());
	}

}
