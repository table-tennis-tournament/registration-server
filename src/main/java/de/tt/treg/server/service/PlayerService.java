package de.tt.treg.server.service;

import java.util.List;

import de.tt.treg.server.domain.Player;
import de.tt.treg.server.domain.User;

public interface PlayerService {

	public Player getPlayerById(int id);

	public void insertPlayer(Player playerToInsert);

	public List<Player> getPlayersByUser(User user);
	
	public List<Player> getPlayersByInvoiceNumber(int invoiceNumber);

	public List<Player> getRegisteredPlayersByCompetitionId(int competitionId);

	public List<Player> getWaitingListForCompetitionId(int competitionId);

	public void deletePlayerById(int id);

	public List<Player> getFreeDoublePlayersForCompetitionId(int competitionId);

	public int setPayedByPlayerIds(List<Integer> _result);

	public List<Player> getAllPlayersLight();

	public List<Player> getPlayersByUserId(int userId);


}
