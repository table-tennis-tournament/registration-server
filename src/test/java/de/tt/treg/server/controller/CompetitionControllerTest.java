package de.tt.treg.server.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml",
		"classpath:spring/hibernateTestContext.xml" })
@WebAppConfiguration
public class CompetitionControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext ctx;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}

	@Test
	public void testGetAllCompetitions() throws Exception {
		mockMvc.perform(
				get("/competition/all").accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(4)))
				.andExpect(jsonPath("$[0].id", is(33)))
				.andExpect(jsonPath("$[0].name", is("Doppel Disziplin")));
	}

	@Test
	public void testGetAllDoubleCompetitions() throws Exception {
		mockMvc.perform(
				get("/competition/double/all").accept(
						MediaType.APPLICATION_JSON)).andExpect(
				jsonPath("$", hasSize(1)));
	}

	@Test
	public void testGetAllSingleCompetitions() throws Exception {
		mockMvc.perform(
				get("/competition/single/all").accept(
						MediaType.APPLICATION_JSON)).andExpect(
				jsonPath("$", hasSize(3)));
	}

}
