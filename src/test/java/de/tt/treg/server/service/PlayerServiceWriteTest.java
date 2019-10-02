package de.tt.treg.server.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

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
public class PlayerServiceWriteTest {

	@Autowired
	private PlayerService playerService;

	@Test
	public void testWritePlayerComplete() {
		Player player = getCompletePlayerWithExistingDatabaseValues();
		InsertAndCheck(player);
	}

	@Test
	public void testWritePlayerWithNewClub() {
		Player player = getCompletePlayerWithExistingDatabaseValues();
		player.setTeam(new Team("testTeam", "tt", "myVerband"));
		InsertAndCheck(player);
	}

	@Test
	public void testWritePlayerWithNewUser() {
		Player player = getCompletePlayerWithExistingDatabaseValues();
		player.setUser(new User("newUsser", "geheim", new Date(System
				.currentTimeMillis())));
		InsertAndCheck(player);
	}

	@Test
	public void testWritePlayerWithNewUserAndNewTeam() {
		Player player = getCompletePlayerWithExistingDatabaseValues();
		player.setUser(new User("newUsser", "geheim", new Date(System
				.currentTimeMillis())));
		player.setTeam(new Team("testTeam", "tt", "myVerband"));
		InsertAndCheck(player);
	}

	@Test
	public void testWritePlayerWithNewCompetitons() {
		Player player = getCompletePlayerWithExistingDatabaseValues();
		Set<PlayerCompetition> competitions = new HashSet<PlayerCompetition>();
		competitions.add(getPlayerCompetition(50));
		player.setCompetitions(competitions);
		playerService.insertPlayer(player);
		assertNull(playerService.getPlayerById(player.getId()));
	}

	private PlayerCompetition getPlayerCompetition(int competitionId) {
		PlayerCompetition playerComp = new PlayerCompetition(1, new Date(
				System.currentTimeMillis()), competitionId, 0);
		return playerComp;

	}

	@Test
	public void testWritePlayerWithNewCompetitonAndExisting() {
		Player player = getCompletePlayerWithExistingDatabaseValues();
		Set<PlayerCompetition> competitions = new HashSet<PlayerCompetition>();
		competitions.add(getPlayerCompetition(50));
		competitions.add(getPlayerCompetition(1));
		player.setCompetitions(competitions);
		InsertAndCheck(player);
	}

	private void InsertAndCheck(Player player) {
		playerService.insertPlayer(player);
		Player playerToTest = playerService.getPlayerById(player.getId());
		assertEquals(player.getFirstName(), playerToTest.getFirstName());
		assertEquals(player.getLastName(), playerToTest.getLastName());
		assertEquals(player.getId(), playerToTest.getId());
		assertEquals(player.getCompetitions().size(), playerToTest
				.getCompetitions().size());
		assertEquals(player.getUser().getUserName(), playerToTest.getUser()
				.getUserName());
		assertEquals(player.getTeam().getName(), playerToTest.getTeam()
				.getName());
		playerService.deletePlayerById(playerToTest.getId());
	}

	private Player getCompletePlayerWithExistingDatabaseValues() {
		Set<PlayerCompetition> competitions = new HashSet<PlayerCompetition>();
		PlayerCompetition playerComp = new PlayerCompetition(1, new Date(
				System.currentTimeMillis()), 1, 0);
		competitions.add(playerComp);
		User user = new User("arsch", "test", new Date(
				System.currentTimeMillis()));
		user.setId(1);
		Team team = new Team("Eierberg", null, "BaTTV");
		team.setId(2);
		return new Player("hans", "maier",
				new Date(System.currentTimeMillis()), competitions, user, team);
	}
}
