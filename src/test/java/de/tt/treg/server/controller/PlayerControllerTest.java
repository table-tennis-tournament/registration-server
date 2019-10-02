package de.tt.treg.server.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Calendar;
import java.util.Date;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import de.tt.treg.server.domain.PlayerCompetition;
import de.tt.treg.server.domain.PlayerCompetitionPk;
import de.tt.treg.server.service.PlayerService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml",
		"classpath:spring/hibernateTestContext.xml" })
@WebAppConfiguration
public class PlayerControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext ctx;

	@Autowired
	private PlayerService playerService;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}

//	@Test
//	public void testInsertPlayerWithoutDouble() throws Exception {
//		Team team = new Team("DC Dreamteam", "DCDR", "WTTV");
//		Set<PlayerCompetition> competitions = new HashSet<PlayerCompetition>();
//		PlayerCompetition playerCompetition = getPlayerCompetition();
//		competitions.add(playerCompetition);
//		User user = new User("wang_liquin@web.de", "fritz", new java.util.Date());
//		Player playerToInsert = new Player("alfons", "derzweite", new Date(),
//				competitions, user, team);
//
//		List<Player> newPlayers = new ArrayList<Player>();
//		newPlayers.add(playerToInsert);
//		TournamentRegistrationObject tournamentRegistrationObject = new TournamentRegistrationObject(
//				newPlayers, new ArrayList<TournamentRegistrationDoubleObject>());
//		MvcResult resultAction = mockMvc
//				.perform(
//						post("/player/new")
//								.contentType(
//										de.tt.treg.server.integration.testtools.IntegrationTestUtil.APPLICATION_JSON_UTF8)
//								.content(
//										de.tt.treg.server.integration.testtools.IntegrationTestUtil
//												.convertObjectToJsonBytes(tournamentRegistrationObject)))
//				.andExpect(jsonPath("$players[0].firstName", is("alfons")))
//				.andExpect(jsonPath("$players[0].user", nullValue()))
//				.andExpect(jsonPath("$doubles", hasSize(0))).andReturn();
//		String test = resultAction.getResponse().getContentAsString();
//		deleteFirstPlayerWithPlayerService(test);
//	}

	// @Test
	// public void testInsertPlayerWithDouble() throws Exception {
	// Team team = new Team("DC Dreamteam", "DCDR", "WTTV");
	// Set<PlayerCompetition> competitions = new HashSet<PlayerCompetition>();
	// PlayerCompetition playerCompetition = getPlayerCompetition();
	// competitions.add(playerCompetition);
	// User user = new User("test@web.de", "fritz", getDateFromCalender(1966));
	// Player playerToInsert = new Player("alfons", "derzweite", new Date(),
	// competitions, user, team);
	//
	// List<Player> newPlayers = new ArrayList<Player>();
	// newPlayers.add(playerToInsert);
	// List<TournamentRegistrationDoubleObject> doubles =
	// getDoubles(playerToInsert);
	// TournamentRegistrationObject tournamentRegistrationObject = new
	// TournamentRegistrationObject(
	// newPlayers, doubles);
	// MvcResult resultAction = mockMvc
	// .perform(
	// post("/player/new")
	// .contentType(
	// de.tt.treg.server.integration.testtools.IntegrationTestUtil.APPLICATION_JSON_UTF8)
	// .content(
	// de.tt.treg.server.integration.testtools.IntegrationTestUtil
	// .convertObjectToJsonBytes(tournamentRegistrationObject)))
	// .andExpect(jsonPath("$players[0].firstName", is("alfons")))
	// .andExpect(jsonPath("$players[0].user", nullValue()))
	// .andExpect(jsonPath("$doubles", hasSize(1)))
	// .andExpect(
	// jsonPath("$doubles[0].firstPlayer.firstName",
	// is("alfons")))
	// .andExpect(
	// jsonPath("$doubles[0].secondPlayer.lastName",
	// is("Spieler 2")))
	// .andExpect(jsonPath("$doubles[0].secondPlayer.id", is(437)))
	// .andReturn();
	// String test = resultAction.getResponse().getContentAsString();
	// deleteFirstPlayerWithPlayerService(test);
	// }

//	private List<TournamentRegistrationDoubleObject> getDoubles(
//			Player firstPlayer) {
//		Date date = getDateFromCalender(1995);
//		Player secondPlayer = new Player("Fritz", "Froh", date,
//				new HashSet<PlayerCompetition>(), new User(), new Team());
//		List<TournamentRegistrationDoubleObject> result = new ArrayList<TournamentRegistrationDoubleObject>();
//		result.add(new TournamentRegistrationDoubleObject(firstPlayer,
//				secondPlayer, 33));
//		return result;
//	}

	private Date getDateFromCalender(int year) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, Calendar.JANUARY, 1);
		Date date = cal.getTime();
		return date;
	}

	private void deleteFirstPlayerWithPlayerService(String test)
			throws ParseException {
		JSONParser parser = new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE);
		Object result = parser.parse(test);
		JSONObject tournamentObject = (JSONObject) result;
		JSONArray array = (JSONArray) tournamentObject.get("players");
		JSONObject playerObject = (JSONObject) array.get(0);
		int value = (int) playerObject.get("id");
		playerService.deletePlayerById(value);
	}

	private PlayerCompetition getPlayerCompetition() {
		PlayerCompetition playerComp = new PlayerCompetition();
		PlayerCompetitionPk playerCompPk = new PlayerCompetitionPk();
		playerCompPk.setCompetitionId(2);
		playerComp.setPaid(1);
		playerComp.setPlayerCompetitionPk(playerCompPk);
		return playerComp;
	}

	@Test
	public void testGetAllTeams() throws Exception {
		mockMvc.perform(get("/team/all").accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].id", is(2)));
	}
}
