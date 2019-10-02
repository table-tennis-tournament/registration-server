package de.tt.treg.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import de.tt.treg.server.domain.User;
import de.tt.treg.server.service.UserService;

/**
 * This controller is the interface for user information. It is used by login to
 * check if the user exists and if he has the correct rights.
 * 
 * @author Dane
 * 
 */
@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody
	User getUserByNameAndPassword(@RequestParam("username") String username,
			@RequestParam("password") String password) {
		return userService.getUserByNameAndPasswort(username, password);
	}
}
