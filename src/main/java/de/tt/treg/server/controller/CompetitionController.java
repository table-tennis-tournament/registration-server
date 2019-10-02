package de.tt.treg.server.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import de.tt.treg.server.domain.Competition;
import de.tt.treg.server.domain.CompetitionInformation;
import de.tt.treg.server.domain.RegistrationContent;
import de.tt.treg.server.service.CompetitionService;
import de.tt.treg.server.service.RegistrationContentService;
import de.tt.treg.server.service.impl.helper.CompetitionCache;

/**
 * This controller is responsible for handling rest calls for competition data.
 * 
 * Currently there is one interface for getting all competitions.
 * 
 * @author Dane
 * 
 */
@Controller
@RequestMapping("/competition")
public class CompetitionController {

	@Autowired
	private CompetitionService competitionService;
	
	@Autowired
	private RegistrationContentService registrationContentService;

	@RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody
	List<Competition> allCompetitions() {
		return competitionService.getAllCompetitions();
	}
	
	@RequestMapping(value = "/info", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody
	RegistrationContent getInformation() {
		return registrationContentService.getRegistrationInformation();
	}


	@PostConstruct
	public void initCompMap() {
		CompetitionCache.getInstance().setCompetitions(
				competitionService.getAllCompetitions());
	}

	@RequestMapping(value = "/single/all", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody
	List<Competition> allSingleCompetitions() {
		return competitionService.getAllSingleCompetitions();
	}

	@RequestMapping(value = "/double/all", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody
	List<Competition> allDoubleCompetitions() {
		return competitionService.getAllDoubleCompetitions();
	}
	
	@RequestMapping(value = "/information", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody
	List<CompetitionInformation> getAllCompetitionInformation() {
		List<CompetitionInformation> result = competitionService.getAllCompetitionInformation();
		return result;
	}
	
	

	@RequestMapping(value = "/team/all", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody
	List<Competition> allTeamCompetitions() {
		return competitionService.getAllTeamCompetitions();
	}

}
