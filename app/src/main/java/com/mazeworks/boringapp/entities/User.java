package com.mazeworks.boringapp.entities;

/**
 * Created by mambisiz on 3/24/17.
 */

public class User {
    private String id;
    private String phone;
    private String name;
    private String profileUrl;
    private boolean activated = false;
    public User() {
    }

    public User(String id, String phone, String name) {
        this.id = id;
        this.phone = phone;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }
}
