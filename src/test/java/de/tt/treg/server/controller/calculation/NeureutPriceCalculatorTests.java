package de.tt.treg.server.controller.calculation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import de.tt.treg.server.domain.Player;
import de.tt.treg.server.domain.PlayerCompetition;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml",
		"classpath:spring/hibernateContext.xml" })
@WebAppConfiguration
public class NeureutPriceCalculatorTests {
	
	ITournamentPriceCalculator calculator;
	
	@Before
	public void setupPreparer() {
		calculator = new EttlingerPriceCalculation();
	}

	@Test
	public void testOneDisziplin() {
		doCombinationTest(new Integer[]{1}, 5.0);	
	}

	@Test
	public void testOneSingleAndOneDouble(){
		doCombinationTest(new Integer[]{1, 4}, 5.0);	
	}
	
	@Test
	public void testDamen(){
		doCombinationTest(new Integer[]{20, 17, 19}, 12.0);	
	}
	
	@Test
	public void testJungenU18(){
		doCombinationTest(new Integer[]{2, 3 ,5}, 9.0);	
	}
	
	@Test
	public void testSenioren(){
		doCombinationTest(new Integer[]{12, 13, 14}, 12.0);	
	}
	
	@Test
	public void testHerrenHandHerrenD(){
		doCombinationTest(new Integer[]{9, 10 , 11}, 12.0);	

	}
	
	@Test
	public void testHerrenAandHerrenB(){
		doCombinationTest(new Integer[]{22, 18, 21}, 12.0);
	}
	
	@Test
	public void testTwoDisciplinesJugendAndTwoErwachsene(){
		doCombinationTest(new Integer[]{22, 18, 21, 7, 5, 8, 3}, 22.0);
	}
	
	@Test
	public void testDamenAndMaedchen(){
		doCombinationTest(new Integer[]{1, 4, 6, 17, 19, 20}, 21.0);
	}
	@Test
	public void testHerrenBandC(){
		doCombinationTest(new Integer[]{18, 15, 21, 16}, 14.0);
	}
	

	private void doCombinationTest(Integer[] combination, double expectedValue) {
		List<Integer> competitionIds= getCombinationAsList(combination);
		Player player = getTestPlayers(competitionIds, 1);
		Player result = calculator.calculatePaymentAndPrepaymentForSinglePlayer(player);
		Assert.assertEquals(expectedValue, result.getPayment());
	}

	private List<Integer> getCombinationAsList(Integer[] combination) {
		List<Integer> competitionIds= new ArrayList<Integer>();
		competitionIds.addAll(Arrays.asList(combination));
		return competitionIds;
	}
	

	private Player getTestPlayers(List<Integer> competitionIds, int playerId) {
		Player player = new Player();
		Set<PlayerCompetition> competitions = new HashSet<PlayerCompetition>();
		for(int competitionId : competitionIds){
			competitions.add(new PlayerCompetition(0, new Date(), competitionId, playerId));
		}
		player.setCompetitions(competitions);
		return player;
	}
}
