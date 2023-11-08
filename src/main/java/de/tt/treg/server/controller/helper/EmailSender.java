package de.tt.treg.server.controller.helper;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

import de.tt.treg.server.controller.domain.TournamentRegistrationDoubleObject;
import de.tt.treg.server.domain.Competition;
import de.tt.treg.server.domain.Player;
import de.tt.treg.server.domain.PlayerCompetition;
import de.tt.treg.server.domain.TTDouble;
import de.tt.treg.server.domain.User;
import de.tt.treg.server.service.impl.helper.CompetitionCache;

/**
 * This class is responsible for sending email after successful registration.
 * The mail contains username, password and the player data.
 * 
 */
public class EmailSender {

	private static final String TR_CLOSE = "</tr>";
	private static final String TR = "<tr>";
	private static final String PLAYER_TABLE_HEAD = "<th>Vorname</th><th>Nachname</th><th>Geburtsjahr</th><th>Konkurrenzen</th>"
			+ "<th>Startgebühr</th>";
	private static final String WAITING_LIST_TABLE_HEAD = "<th>Vorname</th><th>Nachname</th><th>Geburtsjahr</th><th>Konkurrenzen</th>";
	private static final String DOUBLE_LIST_TABLE_HEAD = "<th>Spieler 1</th><th>Spieler 2</th><th>Konkurrenz</th>";

	private HashMap<String, Object> params;

	public void sendEmail(final List<Player> players,
			JavaMailSender mailSender, final VelocityEngine velocityEngine,
			final User newUserCopy,
			final List<TournamentRegistrationDoubleObject> doubles) {
		initParams();
		params.put("doubles", getDoublesTable(doubles));
		sendEmail(players, mailSender, velocityEngine, newUserCopy);
	}
	
	public void sendEmail(final List<Player> players,
			JavaMailSender mailSender, final VelocityEngine velocityEngine,
			final User newUserCopy) {

		if(players == null){
			return;
		}
		final Map<String, List<Player>> playersMap = new WaitingListPlayerSeperator()
				.getPlayersAndWaitingListLists(players);

		System.setProperty("mail.mime.charset", "utf8");
		MimeMessagePreparator preparator = new MimeMessagePreparator() {

			public void prepare(MimeMessage mimeMessage) throws Exception {

				MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,
						true, "UTF-8");
				messageHelper = setEmailHeaderInformation(newUserCopy, messageHelper);

				setAllMailParameters(players, newUserCopy, playersMap);
				mergeParamsWithBody(velocityEngine, messageHelper);
			}
			
		};
		try{
			mailSender.send(preparator);
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}
	
	private MimeMessageHelper setEmailHeaderInformation(final User newUserCopy,
			MimeMessageHelper message) throws MessagingException {
		message.setTo(newUserCopy.getUserName());
		message.setFrom(new InternetAddress("turnier@ttvettlingen.de"));
		message.setBcc(new InternetAddress(
						"turnieranmeldung@ttvettlingen.de"));
		message.setSubject("Albgauturnier 2024 Anmeldung " + newUserCopy.getUserName());
		return message;
	}
	
	private void setAllMailParameters(final List<Player> players,
			final User newUserCopy,
			final Map<String, List<Player>> playersMap) {
		initParams();
		params.put("cssHead", getCSSHead());
		params.put("title", getTitleFromPlayers(players));
		params.put("username", newUserCopy.getUserName());
		params.put("password", newUserCopy.getPasswort());
		params.put("players", getPlayersAsHTMLTable(playersMap
				.get(WaitingListPlayerSeperator.PLAYER_LIST)));
		params.put("waitingList", getWaitingListAsHTMLTable(playersMap
				.get(WaitingListPlayerSeperator.WAITING_LIST)));
		params.put("verwendungsnummer", newUserCopy.getId());
		params.put("vereinsname", players.get(0).getTeam().getName());
	}

	

	private Object getCSSHead() {
		return "<style type='text/css'>"
				+ "table.myTable { border-collapse:collapse; }"
				+ "table.myTable td, table.myTable th { border:1px solid black;padding:5px; }"
				+ "</style>";
	}

	private Object getPlayersAsHTMLTable(List<Player> players) {
		if (players == null) {
			return "";
		}
		
		StringBuilder builder = getStringBuilderWithTableHead(PLAYER_TABLE_HEAD);
		double paymentSum = 0;
		for (Player player : players) {
			builder.append(TR);
			builder = setPlayerSpecificTableDataCells(builder, player);
			String competitionString = getCompetitionData(player
					.getCompetitions());
			builder.append(getTableData(competitionString));
			builder.append(getPrice(player.getPayment()));
			builder.append(TR_CLOSE);
			paymentSum += player.getPayment();
		}
		params.put("betrag", getPriceAsString(paymentSum));
		builder = addPaymentRow(paymentSum, builder);
		builder = setTableBodyAndTableEnd(builder);
		return builder.toString();
	}
	
	private Object getWaitingListAsHTMLTable(Collection<Player> players) {
		if (players == null) {
			return "";
		}
		StringBuilder builder = new StringBuilder();
		builder.append("Warteliste: <br/>");
		builder = getStringBuilderWithTableHead(WAITING_LIST_TABLE_HEAD, builder);
		for (Player player : players) {
			builder.append(TR);
			builder = setPlayerSpecificTableDataCells(builder, player);
			String competitionString = getCompetitionData(player
					.getCompetitions());
			builder.append(getTableData(competitionString));
			builder.append(TR_CLOSE);
		}
		builder = setTableBodyAndTableEnd(builder);
		return builder.toString();
	}

	private StringBuilder setPlayerSpecificTableDataCells(
			StringBuilder builder, Player player) {
		builder.append(getTableData(player.getFirstName()));
		builder.append(getTableData(player.getLastName()));
		builder.append(getTableData(DateHelper
				.getYearFromDateAsString(player.getBirthDate())));
		return builder;
	}

	private String getPrice(double price) {
		String priceAsString = getPriceAsString(price);
		return getTableData(priceAsString);
	}

	private String getPriceAsString(double price) {
		return new DecimalFormat("#.##").format(price) + " €";
	}

	private StringBuilder addPaymentRow(
			double paymentSum, StringBuilder builder) {
		builder.append(TR);
		builder.append(getTableDataWithRowspan("Gesamtsumme: "));
		builder.append(getPrice(paymentSum));
		builder.append(TR_CLOSE);
		return builder;
	}

	private Object getTitleFromPlayers(List<Player> players) {
		if (players.size() == 1) {
			return players.get(0).getFirstName();
		}
		return "Turnierteilnehmer";
	};

	private String getDoublesTable(
			List<TournamentRegistrationDoubleObject> doubles) {
		StringBuilder builder = getStringBuilderWithTableHead(DOUBLE_LIST_TABLE_HEAD);
		for (TournamentRegistrationDoubleObject ttDouble : doubles) {
			builder.append(TR);
			builder.append(getDoublePlayerName(ttDouble.getFirstPlayer()));
			builder.append(getDoublePlayerName(ttDouble.getSecondPlayer()));
			builder.append(getTableData(ttDouble.getDisciplineName()));
			builder.append(TR_CLOSE);
		}
		builder = setTableBodyAndTableEnd(builder);
		return builder.toString();
	}

	private String getDoublePlayerName(Player firstPlayer) {
		if (firstPlayer.getId() == TTDouble.SEARCH_PLAYER_CONST) {
			return getTableData("Suchend");
		}
		String name = firstPlayer.getFirstName() + " "
				+ firstPlayer.getLastName();
		return getTableData(name);
	}

	

	private String getCompetitionData(Set<PlayerCompetition> competitions) {
		String competitionString = "";
		for (PlayerCompetition competition : competitions) {
			Competition comp = CompetitionCache.getInstance()
					.getCompetitionById(
							competition.getPlayerCompetitionPk()
									.getCompetitionId());
			competitionString += comp.getName();
			competitionString += ", ";
		}
		competitionString = competitionString.substring(0,
				competitionString.length() - 2);
		return competitionString;
	}

	private StringBuilder getStringBuilderWithTableHead(String tableHead, StringBuilder builder) {
		builder.append("<table class='myTable'>");
		builder.append("<thead> <tr>" + tableHead + "</tr></thead><tbody>");
		return builder;
	}
	
	private StringBuilder getStringBuilderWithTableHead(String tableHead) {
		return getStringBuilderWithTableHead(tableHead, new StringBuilder());
	}

	private StringBuilder setTableBodyAndTableEnd(StringBuilder builder) {
		builder.append("</tbody></table>");
		return builder;
	}

	private String getTableData(String dataToInsert) {
		return String.format("<td>%s</td>", dataToInsert);
	}

	private String getTableDataWithRowspan(String dataToInsert) {
		return String.format("<td colspan='4'>%s</td>", dataToInsert);
	}

	private void initParams() {
		if (params == null) {
			params = new HashMap<String, Object>();
		}
	}

	private void mergeParamsWithBody(final VelocityEngine velocityEngine,
			MimeMessageHelper message) throws MessagingException {
		String body = VelocityEngineUtils.mergeTemplateIntoString(
				velocityEngine, "templates/email-template.vm", "UTF-8",
				params);
		message.setText(body, true);
	}

	
}
