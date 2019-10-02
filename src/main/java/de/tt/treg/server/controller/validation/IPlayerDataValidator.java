package de.tt.treg.server.controller.validation;

import java.util.List;

import de.tt.treg.server.domain.Player;

public interface IPlayerDataValidator {

	boolean isDataValid(List<Player> playersToCheck);

}
