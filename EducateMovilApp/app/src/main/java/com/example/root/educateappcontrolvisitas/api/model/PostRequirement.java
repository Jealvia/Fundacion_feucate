package com.example.root.educateappcontrolvisitas.api.model;

import java.util.HashMap;
import java.util.Map;

public class PostRequirement {
    private String user;
    private String reason;

    private String school;

    private int type;

    public PostRequirement(String user, String reason, String school, int type) {
        this.user = user;
        this.reason = reason;
        this.school = school;
        this.type = type;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
