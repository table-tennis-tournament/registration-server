package de.tt.treg.server.service;

import java.util.List;

import de.tt.treg.server.controller.domain.TournamentRegistrationDoubleObject;
import de.tt.treg.server.controller.domain.TournamentRegistrationObject;
import de.tt.treg.server.domain.Player;
import de.tt.treg.server.domain.TTDouble;

public interface DoubleService {

	TTDouble getDoubleById(int doubleId);

	void insertDouble(TTDouble doubleToInsert);

	void deleteDouble(TTDouble doubleToDelete);

	List<TTDouble> getDoublesByUserId(int userId);

	TournamentRegistrationObject insertDoubles(
			List<TournamentRegistrationDoubleObject> list, List<Player> players);

}
