package de.tt.treg.server.domain;

public class HealthState {
    private String state;

    public HealthState(String up) {
        this.state = up;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
