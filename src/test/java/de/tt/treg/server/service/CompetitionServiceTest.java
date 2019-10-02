package de.tt.treg.server.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.tt.treg.server.domain.Competition;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml",
		"classpath:spring/hibernateTestContext.xml" })
public class CompetitionServiceTest {

	@Autowired
	private CompetitionService competitionService;

	@Test
	public void testGetAllCompetitions() {
		List<Competition> result = competitionService.getAllCompetitions();
		assertEquals(4, result.size());
	}

	@Test
	public void testGetAllSingleCompetitions() {
		List<Competition> result = competitionService
				.getAllSingleCompetitions();
		assertEquals(3, result.size());
	}

	@Test
	public void testGetAllDoubleCompetitions() {
		List<Competition> result = competitionService
				.getAllDoubleCompetitions();
		assertEquals(1, result.size());
	}

	@Test
	public void testGetSingleTestCompetitionById() {
		Competition competition = competitionService.getCompetitionById(1);
		assertEquals("Konkurrenz 1", competition.getName());
		assertEquals(1, competition.getId());
	}

}
