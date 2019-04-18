package com.example.root.educateappcontrolvisitas.api.model;

public class PostVisita {

    private String requirement;
    private String user;
    private String date_planned;

    public PostVisita(String requirement, String user, String date_planned) {
        this.requirement = requirement;
        this.user = user;
        this.date_planned = date_planned;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDate_planned() {
        return date_planned;
    }

    public void setDate_planned(String date_planned) {
        this.date_planned = date_planned;
    }
}
