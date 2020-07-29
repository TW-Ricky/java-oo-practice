package com.twu.bean;

public class Message {
    private String name;
    private int buyRanking;
    private long votes;
    private int multiple;

    public Message() {
    }

    public Message(String name, long votes, int multiple) {
        this.name = name;
        this.votes = votes;
        this.multiple = multiple;
    }

    @Override
    public String toString() {
        return "Message{" +
                "name='" + name + '\'' +
                ", buyRanking=" + buyRanking +
                ", votes=" + votes +
                ", multiple=" + multiple +
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

    public int getMultiple() {
        return multiple;
    }

    public void setMultiple(int multiple) {
        this.multiple = multiple;
    }
}
