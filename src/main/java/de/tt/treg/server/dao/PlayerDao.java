package de.tt.treg.server.dao;

import java.util.Date;
import java.util.List;

import de.tt.treg.server.domain.Competition;
import de.tt.treg.server.domain.Player;
import de.tt.treg.server.domain.User;

public interface PlayerDao extends AbstractDao<Player, Integer> {

	List<Player> getPlayersByUser(User completeUser);

	List<Player> getAllPlayersByCompetitionId(int id);

	List<Player> getRegisteredPlayersByCompetitionId(int id);

	List<Player> getPlayersOnWaitingListByCompetitionId(int id);

	List<Player> findPlayerByNameAndBirthyear(String firstName,
			String lastName, Date birthDate);
	
	List<Player> findFreeDoublePlayers(
			List<Competition> singleCompetitions, int doubleCompetitionId);

	List<Player> getPlayersByInvoiceNumber(int invoiceNumber);

	List<Player> getAllPlayersLight();

	List<Player> getPlayersByUserId(int userId);

}
 