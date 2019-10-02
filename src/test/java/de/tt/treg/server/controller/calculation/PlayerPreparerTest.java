package de.tt.treg.server.controller.calculation;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import de.tt.treg.server.controller.preparator.IPreparator;
import de.tt.treg.server.controller.preparator.PreparerFactory;
import de.tt.treg.server.domain.Player;
import de.tt.treg.server.domain.PlayerCompetition;
import de.tt.treg.server.service.CompetitionService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml",
		"classpath:spring/hibernateTestContext.xml" })
@WebAppConfiguration
public class PlayerPreparerTest {

	@Autowired
	CompetitionService competitionService;

	IPreparator<Player> preparator;

	@Before
	public void setupPreparer() {
		preparator = new PreparerFactory().createPlayerPreparator();

	}

	@Test
	public void testPositiveCaseWithTwoPlayers() {
		List<PlayerCompetition> competitions = createPlayerCompetition();
		List<Player> players = getDummyPlayers();
		players.get(0).setCompetitions(
				new HashSet<PlayerCompetition>(competitions));
		competitions.remove(2);
		competitions.remove(1);
		players.get(1).setCompetitions(
				new HashSet<PlayerCompetition>(competitions));
		List<Player> result = preparator.prepare(players);
		Assert.assertEquals(2, result.size());
//		checkPlayer(result.get(0), 26, 32);
//		checkPlayer(result.get(1), 7, 9);
	}

	private List<PlayerCompetition> createPlayerCompetition() {
		List<PlayerCompetition> competitions = new ArrayList<PlayerCompetition>();
		competitions.add(new PlayerCompetition(0, new java.util.Date(System
				.currentTimeMillis()), 1, 0));
		competitions.add(new PlayerCompetition(0, new java.util.Date(System
				.currentTimeMillis()), 2, 0));
		competitions.add(new PlayerCompetition(0, new java.util.Date(System
				.currentTimeMillis()), 3, 0));
		return competitions;
	}

	private void checkPlayer(Player player, double prepayment, double payment) {
		Assert.assertEquals(prepayment, player.getPrepayment());
		Assert.assertEquals(payment, player.getPayment());
	}

	public List<Player> getDummyPlayers() {
		List<Player> players = new ArrayList<Player>();
		players.add(new Player("heinz", "test", new Date(23423), null, null,
				null));
		players.add(new Player("heisdfnz", "fsfd", new Date(23423), null, null,
				null));
		return players;
	}
}
