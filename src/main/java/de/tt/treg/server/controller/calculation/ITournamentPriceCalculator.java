package de.tt.treg.server.controller.calculation;

import de.tt.treg.server.domain.Player;

/**
 * 
 * This interface holds some methods for calculation prices for each player.
 * 
 * @author Dane
 * 
 */
public interface ITournamentPriceCalculator {

	Player calculatePaymentAndPrepaymentForSinglePlayer(
			Player player);

}
