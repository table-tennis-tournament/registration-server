package de.tt.treg.server.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.tt.treg.server.domain.Player;
import de.tt.treg.server.domain.PlayerCompetition;
import de.tt.treg.server.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml",
		"classpath:spring/hibernateTestContext.xml" })
public class PlayerServiceReadTest {

	@Autowired
	private PlayerService playerService;

	@Test
	public void testGetDummyPlayerNameAndBirthyear() {
		Player playerToTest = getDummyPlayerFromService();
		assertEquals("fritz", playerToTest.getFirstName());
		assertEquals("froh", playerToTest.getLastName());

		int birthyear = Integer.valueOf(new SimpleDateFormat("yyyy")
				.format(playerToTest.getBirthDate()));
		assertEquals(1995, birthyear);
	}

	@Test
	public void testGetDummyPlayerCompetitions() {
		Player playerToTest = getDummyPlayerFromService();
		Set<PlayerCompetition> competitions = playerToTest.getCompetitions();
		assertTrue(competitions.size() == 2);
		Iterator<PlayerCompetition> iterator = competitions.iterator();
		List<Integer> resultList = new ArrayList<Integer>();
		resultList.add(1);
		resultList.add(2);
		while (iterator.hasNext()) {
			PlayerCompetition competition = iterator.next();
			int competitionId = competition.getPlayerCompetitionPk()
					.getCompetitionId();
			if (resultList.contains(competitionId)) {
				resultList.remove(new Integer(competitionId));
				continue;
			}
			fail();
		}
	}

	@Test
	public void testClubOfDummyPlayer() {
		Player playerToTest = getDummyPlayerFromService();
		assertEquals("Eierberg", playerToTest.getTeam().getName());
	}

	@Test
	public void testUserOfDummyPlayer() {
		Player playerToTest = getDummyPlayerFromService();
		assertEquals("arsch", playerToTest.getUser().getUserName());
		assertEquals("test", playerToTest.getUser().getPasswort());
		int registrationYear = Integer.valueOf(new SimpleDateFormat("yyyy")
				.format(playerToTest.getUser().getRegistrationDate()));
		assertEquals(2013, registrationYear);
	}

	@Test
	public void testGetPlayersByUser() {
		List<Player> players = playerService.getPlayersByUser(new User(
				"Passwort", "geheim", new Date(System.currentTimeMillis())));
		assertNotNull(players);
		assertEquals(2, players.size());
		checkPlayer(players.get(0), 1, "test1", "Testverein");
		checkPlayer(players.get(1), 2, "test2", "Testverein");
	}

	@Test
	public void testGetNoPlayersByWrongPasswort() {
		List<Player> players = playerService.getPlayersByUser(new User(
				"Passwort", "", new Date(System.currentTimeMillis())));
		assertEquals(0, players.size());
	}

	@Test
	public void testGetNoPlayersByWrongUsername() {
		List<Player> players = playerService.getPlayersByUser(new User(
				"falsch", "geheim", new Date(System.currentTimeMillis())));
		assertEquals(0, players.size());
	}

	@Test
	public void testGetPlayersByCompetiton() {
		List<Player> result = playerService.getRegisteredPlayersByCompetitionId(1);
		assertTrue(result.size() > 0);
	}

	@Test
	public void testGetEmptyListWhenGetPlayersByCompetitionWithNull() {
		List<Player> result = playerService.getRegisteredPlayersByCompetitionId(30);
		assertTrue(result.size() == 0);
	}

	private void checkPlayer(Player playerToTest, int countOfCompetitions,
			String expectedLastName, String nameOfClub) {
		assertEquals(countOfCompetitions, playerToTest.getCompetitions().size());
		assertEquals(expectedLastName, playerToTest.getLastName());
		assertEquals(nameOfClub, playerToTest.getTeam().getName());
	}

	private Player getDummyPlayerFromService() {
		return playerService.getPlayerById(2);
	}

}
