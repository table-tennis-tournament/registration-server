package de.tt.treg.server.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import de.tt.treg.server.domain.Team;
import de.tt.treg.server.service.TeamService;

/**
 * This controller represents the interface for the client to get team relevant
 * data. Currently you can create new teams and get all available teams.
 * 
 * @author Dane
 * 
 */
@Controller
@RequestMapping("/team")
public class TeamController {

	@Autowired
	private TeamService teamService;

	@RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody
	List<Team> randomTeam() {
		return teamService.getAllTeams();
	}

	@RequestMapping(value = "/organizations", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody
	List<String> getOrganizations() {
		List<String> organizationStringList = new ArrayList<String>();
		organizationStringList.add("BaTTV");
		organizationStringList.add("BETTV");
		organizationStringList.add("ByTTV");
		organizationStringList.add("FTTB");
		organizationStringList.add("HATTV");
		organizationStringList.add("PTTV");
		organizationStringList.add("RTTV");
		organizationStringList.add("SBTTV");
		organizationStringList.add("STTB");
		organizationStringList.add("SÄTTV");
		organizationStringList.add("TTVWH");
		organizationStringList.add("TTVN");
		organizationStringList.add("TTVSA");
		organizationStringList.add("TTVB");
		organizationStringList.add("TTVMV");
		organizationStringList.add("TTVSH");
		organizationStringList.add("TTTV");
		organizationStringList.add("WTTV");
		return organizationStringList;
	}

}
