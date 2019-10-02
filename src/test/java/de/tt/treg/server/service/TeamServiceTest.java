package de.tt.treg.server.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.tt.treg.server.domain.Team;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml",
		"classpath:spring/hibernateTestContext.xml" })
public class TeamServiceTest {

	@Autowired
	private TeamService teamService;

	@Test
	public void testGetAllTeamsNotNull() {
		List<Team> result = getAllTeams();
		assertNotNull(result);
	}

	@Test
	public void testGetAllTeamsSizeBiggerThanOne() {
		List<Team> result = getAllTeams();
		assertTrue(result.size() > 1);
	}

	@Test
	public void testFirstEntryOfAllTeams() {
		List<Team> result = getAllTeams();
		assertEquals("Eierberg", result.get(0).getName());
	}

	@Test
	public void testGetTeamById() {
		Team result = teamService.getTeamById(2);
		assertEquals("Eierberg", result.getName());
	}

	@Test
	public void testGetTeamByIdNull() {
		Team result = teamService.getTeamById(1);
		assertNull(result);
	}

	@Test
	public void testInsertAndGet() {
		Team teamToInsert = getTeamToInsert();
		Team teamToTest = teamService.saveTeam(teamToInsert);
		Team resultTeam = teamService.getTeamById(teamToTest.getId());
		assertEquals(teamToTest.getId(), resultTeam.getId());
		assertEquals(teamToTest.getName(), resultTeam.getName());
	}

	private Team getTeamToInsert() {
		return new Team("dummyTeam", "dumm", "BaTTV");
	}

	private List<Team> getAllTeams() {
		return teamService.getAllTeams();
	}
}
