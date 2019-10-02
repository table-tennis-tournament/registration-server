package de.tt.treg.server.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.tt.treg.server.domain.Player;
import de.tt.treg.server.domain.PlayerCompetition;
import de.tt.treg.server.domain.Team;
import de.tt.treg.server.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml",
		"classpath:spring/hibernateTestContext.xml" })
public class PlayerCompetitionServiceTest {

	@Autowired
	private PlayerService playerService;
	private int currentPlayerId;

	@Test
	public void testGetPlayersByCompetition() {
		List<Player> result = playerService
				.getRegisteredPlayersByCompetitionId(1);
		assertEquals(3, result.size());
		checkPlayer(result.get(0), "fritz", "froh", 1);
		checkPlayer(result.get(1), "user", "test1", 1);
		checkPlayer(result.get(2), "user", "test2", 1);
	}

	@Test
	public void testGetPlayersBySecondTestCase() {
		List<Player> result = playerService
				.getRegisteredPlayersByCompetitionId(2);
		assertTrue(result.size() >= 2);
		checkPlayer(result.get(0), "fritz", "froh", 2);
		checkPlayer(result.get(1), "user", "test2", 2);
	}

	@Test
	public void testEmptyPlayerListForEmptyCompetition() {
		List<Player> result = playerService
				.getRegisteredPlayersByCompetitionId(3);
		assertEquals(0, result.size());
	}

	@Test
	public void testEmptyWaitingListForEmptyCompetition() {
		List<Player> result = playerService.getWaitingListForCompetitionId(3);
		assertEquals(0, result.size());
	}

	@Test
	public void testWaitingListEmpty() {
		List<Player> result = playerService.getWaitingListForCompetitionId(2);
		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testWaitingListSizeOneWhenAddingNewPlayerToSecondCompetition() {
		Player testPlayer = getNewPlayerWithSecondCompetition();
		playerService.insertPlayer(testPlayer);
		currentPlayerId = testPlayer.getId();
		List<Player> result = playerService.getWaitingListForCompetitionId(2);
		Assert.assertEquals(1, result.size());
		Assert.assertEquals(testPlayer.getId(), result.get(0).getId());

		playerService.deletePlayerById(currentPlayerId);
	}

	private Player getNewPlayerWithSecondCompetition() {
		Set<PlayerCompetition> competitions = new HashSet<PlayerCompetition>();
		PlayerCompetition playCompetition = new PlayerCompetition(0, new Date(
				System.currentTimeMillis()), 2, 0);

		competitions.add(playCompetition);
		User user = new User("arsch", "test", new Date(
				System.currentTimeMillis()));
		user.setId(1);
		Team team = new Team("Eierberg", null, "BaTTV");
		team.setId(2);
		return new Player("franz", "xaver",
				new Date(System.currentTimeMillis()), competitions, user, team);
	}

	private void checkPlayer(Player player, String firstname, String lastname,
			int competitionId) {
		assertEquals(firstname, player.getFirstName());
		assertEquals(lastname, player.getLastName());
		Iterator<PlayerCompetition> competitionIterator = player
				.getCompetitions().iterator();
		boolean hasCompetitionIdOne = false;
		while (competitionIterator.hasNext()) {
			PlayerCompetition competition = competitionIterator.next();
			if (competition.getPlayerCompetitionPk().getCompetitionId() == competitionId) {
				hasCompetitionIdOne = true;
				break;
			}
		}
		assertTrue(hasCompetitionIdOne);
	}

}
