package com.example.root.educateappcontrolvisitas.api.model;

public class PatchVisita {

    private String ambassador;
    private String checkIn;
    private String checkOut;
    private String coordinatesLatIn;
    private String coordinatesLatOut;
    private String coordinatesLonIn;
    private String coordinatesLonOut;
    private int state;

    public PatchVisita(String ambassador, String checkIn, String checkOut, String coordinatesLatIn, String coordinatesLatOut, String coordinatesLonIn, String coordinatesLonOut, int state) {
        this.ambassador = ambassador;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.coordinatesLatIn = coordinatesLatIn;
        this.coordinatesLatOut = coordinatesLatOut;
        this.coordinatesLonIn = coordinatesLonIn;
        this.coordinatesLonOut = coordinatesLonOut;
        this.state = state;
    }

    public String getAmbassador() {
        return ambassador;
    }

    public void setAmbassador(String ambassador) {
        this.ambassador = ambassador;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public String getCoordinatesLatIn() {
        return coordinatesLatIn;
    }

    public void setCoordinatesLatIn(String coordinatesLatIn) {
        this.coordinatesLatIn = coordinatesLatIn;
    }

    public String getCoordinatesLatOut() {
        return coordinatesLatOut;
    }

    public void setCoordinatesLatOut(String coordinatesLatOut) {
        this.coordinatesLatOut = coordinatesLatOut;
    }

    public String getCoordinatesLonIn() {
        return coordinatesLonIn;
    }

    public void setCoordinatesLonIn(String coordinatesLonIn) {
        this.coordinatesLonIn = coordinatesLonIn;
    }

    public String getCoordinatesLonOut() {
        return coordinatesLonOut;
    }

    public void setCoordinatesLonOut(String coordinatesLonOut) {
        this.coordinatesLonOut = coordinatesLonOut;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

}
