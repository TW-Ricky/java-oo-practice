package com.twu.bean;

public class User {
    private String name;
    private Integer votes;
    private boolean admin;

    public User() {
    }

    public User(String name, Integer votes, boolean admin) {
        this.name = name;
        this.votes = votes;
        this.admin = admin;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", votes=" + votes +
                ", admin=" + admin +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
