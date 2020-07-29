package com.twu.bean;

public class User {
    private String name;
    private String password;
    private Integer votes;

    public User(String name, String password, Integer votes) {
        this.name = name;
        this.password = password;
        this.votes = votes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }
}
