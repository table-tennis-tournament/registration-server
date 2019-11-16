package de.tt.treg.server.controller;

import javax.servlet.http.HttpServletRequest;

import de.tt.treg.server.domain.HealthState;
import de.tt.treg.server.service.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class StartController {

	@Autowired
	private CompetitionService competitionService;

    @RequestMapping("/")
    public String index(HttpServletRequest request) {
        return "index";
    }

    @RequestMapping("/health")
    public @ResponseBody HealthState health(HttpServletRequest request) {
        return new HealthState("UP");
    }


}
