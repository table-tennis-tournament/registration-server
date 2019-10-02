package de.tt.treg.server.controller.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = org.springframework.http.HttpStatus.FORBIDDEN)
public class WrongUsernameOrPasswordException extends Exception {
	private static final long serialVersionUID = 5682885469929278262L;

}
