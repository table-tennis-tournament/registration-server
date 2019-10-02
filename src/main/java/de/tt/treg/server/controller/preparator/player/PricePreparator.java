package de.tt.treg.server.controller.preparator.player;

import de.tt.treg.server.controller.calculation.EttlingerPriceCalculation;
import de.tt.treg.server.controller.calculation.ITournamentPriceCalculator;
import de.tt.treg.server.controller.preparator.ISinglePreparator;
import de.tt.treg.server.domain.Player;

/**
 * @author Dane This preparer sets all prices for every player object. The
 *         prices includes the prepayment and payment attributes. The price for
 *         competitions where the player is on waiting list is skipped.
 * 
 */
public class PricePreparator implements ISinglePreparator<Player> {

	private ITournamentPriceCalculator calculator = new EttlingerPriceCalculation();
	
	public PricePreparator() {
		
	}

	@Override
	public Player prepare(Player player) {
		return calculator.calculatePaymentAndPrepaymentForSinglePlayer(player);
	}

}
