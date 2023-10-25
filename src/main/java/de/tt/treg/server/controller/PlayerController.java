package de.tt.treg.server.controller;

import de.tt.treg.server.controller.domain.TournamentRegistrationObject;
import de.tt.treg.server.controller.exceptions.WrongUsernameOrPasswordException;
import de.tt.treg.server.controller.helper.EmailSender;
import de.tt.treg.server.controller.helper.UserBuilder;
import de.tt.treg.server.controller.preparator.IPreparator;
import de.tt.treg.server.controller.preparator.PreparerFactory;
import de.tt.treg.server.controller.validation.IPlayerDataValidator;
import de.tt.treg.server.controller.validation.PlayerValidator;
import de.tt.treg.server.controller.validation.UserValidator;
import de.tt.treg.server.domain.Player;
import de.tt.treg.server.domain.PlayerCompetition;
import de.tt.treg.server.domain.PlayerLight;
import de.tt.treg.server.domain.User;
import de.tt.treg.server.service.CompetitionService;
import de.tt.treg.server.service.DoubleService;
import de.tt.treg.server.service.PlayerService;
import de.tt.treg.server.service.UserService;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/player")
public class PlayerController {

	@Autowired
	private PlayerService playerService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private CompetitionService competitionService;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private DoubleService doubleService;

	@Autowired
	private VelocityEngine velocityEngine;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody List<Player> getAllPlayersByUsernameAndPasswort(
			@RequestParam("user") String username,
			@RequestParam("password") String password)
			throws WrongUsernameOrPasswordException {
		List<Player> result = playerService.getPlayersByUser(new User(username,
				password, new Date(System.currentTimeMillis())));
		if (result.size() == 0) {
			throw new WrongUsernameOrPasswordException();
		}
		return getBasicPreparer().prepare(result);
	}
	
	@RequestMapping(value = "/searchByInvoice",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody List<Player> getPlayersByInvoiceNumber(
			@RequestParam("user") String username,
			@RequestParam("password") String password,
			@RequestParam("invoiceNumber") int number)
			throws WrongUsernameOrPasswordException {
		if(!userService.isUserAdmin(username, password)) {
			throw new WrongUsernameOrPasswordException();
		}
		List<Player> result = playerService.getPlayersByInvoiceNumber(number);
		
		return getCompletePreparer().prepare(result);
	}
	
	@RequestMapping(value = "/searchByUserId",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody List<Player> getPlayersByUserId(
			@RequestParam("user") String username,
			@RequestParam("password") String password,
			@RequestParam("invoiceNumber") int userId)
			throws WrongUsernameOrPasswordException {
		if(!userService.isUserAdmin(username, password)) {
			throw new WrongUsernameOrPasswordException();
		}
		List<Player> result = playerService.getPlayersByUserId(userId);
		return getBasicPreparer().prepare(result);
	}
	
	@RequestMapping(value = "/light",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody List<PlayerLight> getPlayersLight(
			@RequestParam("user") String username,
			@RequestParam("password") String password)
			throws WrongUsernameOrPasswordException {
		if(!userService.isUserAdmin(username, password)) {
			throw new WrongUsernameOrPasswordException();
		}
		List<Player> result = playerService.getAllPlayersLight();
		List<PlayerLight> result2 = new ArrayList<PlayerLight>();
		for (Player player : result) {
			result2.add(new PlayerLight(player.getFirstName() + " " + player.getLastName(), player.getUser().getId()));
		}
		return result2;
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody List<Player> getFreeDoublePlayers(
			@RequestParam("competitionId") int competitionId) {
		if(competitionId < 1){
			return new ArrayList<Player>();
		}
		List<Player> result = playerService
				.getFreeDoublePlayersForCompetitionId(competitionId);
		List<Player> players = getBasicPreparer().prepare(result);
		players = getDoubleFilteredPlayers(players, competitionId);
		return players;
	}

	private List<Player> getDoubleFilteredPlayers(List<Player> players,
			int doubleId) {
		List<Player> playerResult = new ArrayList<Player>();
		for (Player player : players) {
			boolean hasNoDouble = true;
			for (PlayerCompetition competition : player.getCompetitions()) {
				if (competition.getPlayerCompetitionPk().getCompetitionId() == doubleId) {
					hasNoDouble = false;
				}
			}
			if (hasNoDouble) {
				playerResult.add(player);
			}
		}
		return playerResult;
	}

	@RequestMapping(value = "/comp", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody List<Player> getAllPlayersByCompetitionId(
			@RequestParam("competitionId") int competitionId) {
		List<Player> result = playerService
				.getRegisteredPlayersByCompetitionId(competitionId);
		return getBasicPreparer().prepare(result);
	}

	@RequestMapping(value = "/waitlist", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody List<Player> getAllPlayersOnWaitlistByCompetitionId(
			@RequestParam("competitionId") int competitionId) {
		List<Player> result = new ArrayList<Player>();
		result = playerService.getWaitingListForCompetitionId(competitionId);
		return result;
	}

	@RequestMapping(value = "/new", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody TournamentRegistrationObject insertNewPlayers(
			@RequestBody TournamentRegistrationObject insertObject) {
		// precondition
		List<Player> players = insertObject.getPlayers();
		if (!isInputDataValid(players)) {
			return null;
		}
		User newUser = createUser(players);
		String clearTextPasswort = newUser.getPasswort();
		
		setUserAndInsertPlayers(players, newUser);
		
		TournamentRegistrationObject tournamentObject = doubleService
				.insertDoubles(insertObject.getDoubles(), players);
		newUser.setPasswort(clearTextPasswort);
		players = getCompletePreparer().prepare(players);

		new EmailSender().sendEmail(players, mailSender, velocityEngine,
				newUser, tournamentObject.getDoubles());
		return new TournamentRegistrationObject(players,
				tournamentObject.getDoubles());
	}
	
	@RequestMapping(value = "/pay", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody int payForPlayer(
			@RequestParam("user") String username,
			@RequestParam("password") String password,
			@RequestBody List<Integer> _result) throws WrongUsernameOrPasswordException {
		if(!userService.isUserAdmin(username, password)) {
			throw new WrongUsernameOrPasswordException();
		}
		return playerService.setPayedByPlayerIds(_result);
	}

	private void setUserAndInsertPlayers(List<Player> players, User newUser) {
		for (Player player : players) {
			player.setUser(newUser);
			player.setFirstName(StringUtils.capitalizeFirstLetter(player.getFirstName()));
			player.setLastName(StringUtils.capitalizeFirstLetter(player.getLastName()));
			playerService.insertPlayer(player);
		}
	}

	private User createUser(List<Player> players) {
		return new UserBuilder().getCreatedUserFromUsername(players
				.get(0).getUser().getUserName());
	}

	private IPreparator<Player> getCompletePreparer() {
		return new PreparerFactory().createPlayerPreparator();
	}

	private IPreparator<Player> getBasicPreparer() {
		return new PreparerFactory().createPlayerPreparatorLight();
	}


	private boolean isInputDataValid(List<Player> players) {
		for (IPlayerDataValidator validator : getPlayerValidators()) {
			if (validator.isDataValid(players)) {
				continue;
			}
			return false;
		}
		return true;
	}

	private List<IPlayerDataValidator> getPlayerValidators() {
		List<IPlayerDataValidator> validators = new ArrayList<IPlayerDataValidator>();
		validators.add(new PlayerValidator());
		validators.add(new UserValidator());
		return validators;
	}

}
