package de.tt.treg.server.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class PlayerCompetition implements IIdentable, Serializable {

    private static final long serialVersionUID = 9059690284397543546L;
    private int id;
    private int paid;
    private int isWaitingList = 0;
    private Timestamp registrationDate;
    private PlayerCompetitionPk playerCompetitionPk;
    private String name;
    private int seed = 0;

    public PlayerCompetition() {

    }

    public PlayerCompetition(int id, int paid, Timestamp registrationDate) {
        this(paid, registrationDate, 0, 0);
        this.id = id;
    }

    public PlayerCompetition(int paid, Timestamp registrationDate,
                             int competitionId, int playerId) {
        super();
        this.paid = paid;
        this.registrationDate = registrationDate;
        this.playerCompetitionPk = new PlayerCompetitionPk(competitionId,
                playerId);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPaid() {
        return paid;
    }

    public void setPaid(int paid) {
        this.paid = paid;
    }

    public Timestamp getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Timestamp registrationDate) {
        this.registrationDate = registrationDate;
    }

    public PlayerCompetitionPk getPlayerCompetitionPk() {
        return playerCompetitionPk;
    }

    public void setPlayerCompetitionPk(PlayerCompetitionPk playerCompetitionPk) {
        this.playerCompetitionPk = playerCompetitionPk;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIsWaitingList() {
        return isWaitingList;
    }

    public void setIsWaitingList(int isWaitingList) {
        this.isWaitingList = isWaitingList;
    }

    public int getSeed() {
        return seed;
    }

    public void setSeed(int seed) {
        this.seed = seed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerCompetition that = (PlayerCompetition) o;
        return id == that.id && paid == that.paid && isWaitingList == that.isWaitingList && seed == that.seed && Objects.equals(registrationDate, that.registrationDate) && Objects.equals(playerCompetitionPk, that.playerCompetitionPk) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, paid, isWaitingList, registrationDate, playerCompetitionPk, name, seed);
    }
}
