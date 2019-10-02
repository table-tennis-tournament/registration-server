package de.tt.treg.server.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.tt.treg.server.controller.helper.DateHelper;
import de.tt.treg.server.dao.PlayerCompetitionDao;
import de.tt.treg.server.domain.Player;
import de.tt.treg.server.domain.PlayerCompetition;
import de.tt.treg.server.domain.TTDouble;
import de.tt.treg.server.domain.Team;
import de.tt.treg.server.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml",
		"classpath:spring/hibernateTestContext.xml" })
public class DoubleServiceTest {

	@Autowired
	private DoubleService doubleService;

	@Autowired
	private PlayerCompetitionDao competitionDao;

	@Test
	public void testGetDoublesById() {
		TTDouble ttDouble = doubleService.getDoubleById(3);
		checkDouble(ttDouble, 437, 438, "Spieler 1", "Spieler 2", "Doppel 1",
				"Doppel 2", 33, 2);
	}

	@Test
	public void testInsertDouble() {
		TTDouble doubleToInsert = getTestDouble();
		doubleService.insertDouble(doubleToInsert);
		TTDouble result = doubleService.getDoubleById(doubleToInsert.getId());
		checkDouble(result, doubleToInsert.getPlay1Id(),
				doubleToInsert.getPlay2Id(), doubleToInsert.getPlay1LastName(),
				doubleToInsert.getPlay2LastName(),
				doubleToInsert.getPlay1FirstName(),
				doubleToInsert.getPlay2FirstName(),
				doubleToInsert.getCompetitionId(), doubleToInsert.getUser()
						.getId());
		doubleService.deleteDouble(result);
	}

	@Test
	public void testGetDoubleByUserId() {
		List<TTDouble> result = doubleService.getDoublesByUserId(2);
		assertEquals(1, result.size());
	}

//	@Test
//	public void testInsertTournamentDouble() {
//		List<Player> players = getDummyPlayers();
//		TournamentRegistrationDoubleObject doubleObject = new TournamentRegistrationDoubleObject(
//				players.get(0), players.get(1), 33);
//		List<TournamentRegistrationDoubleObject> doubles = new ArrayList<TournamentRegistrationDoubleObject>();
//		doubles.add(doubleObject);
//		TournamentRegistrationObject result = doubleService.insertDoubles(
//				doubles, players);
//		for (TournamentRegistrationDoubleObject singleDouble : result
//				.getDoubles()) {
//			assertEquals("Doppel Disziplin", singleDouble.getDisciplineName());
//			assertEquals(33, singleDouble.getDoubleId());
//			assertEquals(1, singleDouble.getFirstPlayer().getCompetitions()
//					.size());
//			assertEquals(1, singleDouble.getSecondPlayer().getCompetitions()
//					.size());
//			competitionDao.delete(singleDouble.getFirstPlayer()
//					.getCompetitions().iterator().next());
//			competitionDao.delete(singleDouble.getSecondPlayer()
//					.getCompetitions().iterator().next());
//		}
//	}

	private List<Player> getDummyPlayers() {
		List<Player> result = new ArrayList<Player>();
		result.add(getDummyPlayer("Fritz", "Froh", 2));
		result.add(getDummyPlayer("user", "test1", 3));
		return result;
	}

	private Player getDummyPlayer(String firstName, String lastName, int id) {
		Player player = new Player(firstName, lastName,
				DateHelper.getDateFromYear(1997),
				new HashSet<PlayerCompetition>(), getDummyUser(), new Team());
		player.setId(id);
		return player;
	}

	private User getDummyUser() {
		User user = new User("arsch", "test", new Date());
		user.setId(1);
		return user;
	}

	private void checkDouble(TTDouble ttDouble, int firstPlayerId,
			int secondPlayerId, String firstPlayerLastName,
			String secondPlayerLastName, String firstPlayerFirstName,
			String secondPlayerFirstName, int competitionId, int userId) {
		assertEquals(firstPlayerId, ttDouble.getPlay1Id());
		assertEquals(secondPlayerId, ttDouble.getPlay2Id());
		assertEquals(firstPlayerLastName, ttDouble.getPlay1LastName());
		assertEquals(secondPlayerLastName, ttDouble.getPlay2LastName());
		assertEquals(firstPlayerFirstName, ttDouble.getPlay1FirstName());
		assertEquals(secondPlayerFirstName, ttDouble.getPlay2FirstName());
		assertEquals(33, ttDouble.getCompetitionId());
		assertEquals(2, ttDouble.getDoubleKind());
		assertEquals(1, ttDouble.getDoublePaid());
	}

	private TTDouble getTestDouble() {
		User testUser = new User("hinz", "", new Date());
		testUser.setId(2);
		return new TTDouble(33, 2, 3, "fritz", "user", "froh", "test1",
				testUser);
	}
}
