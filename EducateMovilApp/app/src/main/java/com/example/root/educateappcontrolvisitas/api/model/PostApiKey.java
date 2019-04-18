package com.example.root.educateappcontrolvisitas.api.model;

public class PostApiKey {

    private String device_id;

    public PostApiKey(String device_id) {
        this.device_id = device_id;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }
}
