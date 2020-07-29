package com.twu.bean;

public class Message {
    private String name;
    private int buyRanking;
    private long votes;

    public Message(String name, long votes) {
        this.name = name;
        this.votes = votes;
    }

    @Override
    public String toString() {
        return "Message{" +
                "name='" + name + '\'' +
                ", buyRanking=" + buyRanking +
                ", votes=" + votes +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBuyRanking() {
        return buyRanking;
    }

    public void setBuyRanking(int buyRanking) {
        this.buyRanking = buyRanking;
    }

    public long getVotes() {
        return votes;
    }

    public void setVotes(long votes) {
        this.votes = votes;
    }
}
