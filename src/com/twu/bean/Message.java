package com.twu.bean;

public class Message {
    private String name;
    private Integer votes;
    private Integer multiple;
    private Integer buyRanking;
    private Integer buyMoney;

    public Message() {
    }

    public Message(String name, Integer votes, Integer multiple, Integer buyRanking, Integer buyMoney) {
        this.name = name;
        this.votes = votes;
        this.multiple = multiple;
        this.buyRanking = buyRanking;
        this.buyMoney = buyMoney;
    }

    @Override
    public String toString() {
        return "Message{" +
                "name='" + name + '\'' +
                ", buyRanking=" + buyRanking +
                ", votes=" + votes +
                ", multiple=" + multiple +
                ", buyMoney=" + buyMoney +
                '}';
    }

    public Integer getBuyMoney() {
        return buyMoney;
    }

    public void setBuyMoney(Integer buyMoney) {
        this.buyMoney = buyMoney;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBuyRanking() {
        return buyRanking;
    }

    public void setBuyRanking(Integer buyRanking) {
        this.buyRanking = buyRanking;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    public Integer getMultiple() {
        return multiple;
    }

    public void setMultiple(Integer multiple) {
        this.multiple = multiple;
    }
}
