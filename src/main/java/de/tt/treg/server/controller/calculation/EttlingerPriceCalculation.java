package de.tt.treg.server.controller.calculation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import de.tt.treg.server.domain.Competition;
import de.tt.treg.server.domain.Player;
import de.tt.treg.server.domain.PlayerCompetition;
import de.tt.treg.server.service.impl.helper.CompetitionCache;

public class EttlingerPriceCalculation implements ITournamentPriceCalculator {


    public EttlingerPriceCalculation() {
    }

    @Override
    public Player calculatePaymentAndPrepaymentForSinglePlayer(
            Player player) {
        double calculatedPrice = 0;
        calculatedPrice = calculatePrice(player, calculatedPrice);
        player.setPayment(calculatedPrice);
        player.setPrepayment(calculatedPrice - (player.getCompetitions().size() * 2));
        return player;
    }

    private double calculatePrice(Player player, double calculatedPrice) {
        List<Integer> competitionIdList = getCompetitionIdList(player.getCompetitions());
        for (Integer singleDisciplineId : competitionIdList) {
            Competition completeCompetition = CompetitionCache.getInstance().getCompetitionById(singleDisciplineId);
            calculatedPrice += completeCompetition.getPrice();
        }
        return calculatedPrice;
    }

    private List<Integer> getCompetitionIdList(
            Set<PlayerCompetition> competitions) {
        List<Integer> result = new ArrayList<Integer>();
        for (PlayerCompetition competition : competitions) {
            if (competition.getIsWaitingList() != 0) {
                continue;
            }
            result.add(competition.getPlayerCompetitionPk().getCompetitionId());
        }
        return result;
    }

}
