package de.tt.treg.server.controller.calculation;

import java.util.List;

import de.tt.treg.server.domain.Player;

public interface IPriceObject {
	
	List<Player> getPlayers();
	
	double getPaymentSum();
	
	double getPrepaymentSum();
	

}
