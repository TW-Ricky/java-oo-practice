package com.twu.service;

import com.twu.bean.Message;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MessageService {
    private static final int SUPPER_MULTIPLE = 2;
    private static final int MULTIPLE = 1;

    /**
     * 向 messages 中加入一个新的 message 并返回
     * @param messages
     * @param messageName
     * @param superMessage
     * @return
     */
    public List<Message> add(List<Message> messages, String messageName, boolean superMessage) {
        messages.add(new Message(messageName, 0, superMessage ? SUPPER_MULTIPLE : MULTIPLE, 0, 0));
        return messages;
    }

    /**
     * 向 messages 中的指定 message 投 number 票
     * @param messages
     * @param messageName
     * @param number
     * @return
     */
    public List<Message> vote(List<Message> messages, String messageName, Integer number) {
        return messages.stream().filter(it -> {
            if (it.getName().equals(messageName)) {
                it.setVotes(it.getVotes() + number * it.getMultiple());
            } else {
                it.setVotes(it.getVotes());
            }
            return true;
        }).collect(Collectors.toList());
    }

    /**
     * 给 messages 中的指定 message 购买指定 rank名次
     * @param messages
     * @param messageName
     * @param rank
     * @param money
     * @return
     */
    public List<Message> buy(List<Message> messages, String messageName, Integer rank, Integer money) {
        return messages.stream().filter(it -> {
            if (it.getName().equals(messageName)) {
                it.setBuyRanking(rank);
                it.setBuyMoney(money);
            }
            return true;
        }).collect(Collectors.toList());
    }

    /**
     * 更新购买对应名次需要的最少钱数
     * @param messageMaxMoney
     * @param rank
     * @param money
     * @return
     */
    public HashMap<Integer, Integer> updateMax(HashMap<Integer, Integer> messageMaxMoney, Integer rank, Integer money){
        messageMaxMoney.put(rank, money);
        return messageMaxMoney;
    }

    /**
     * 输出 message 排名
     * @param messages
     */
    public void display(List<Message> messages){
        Stream.iterate(0, index -> index + 1).limit(messages.size()).forEach(index -> {
            Message it = messages.get(index);
            System.out.println((index + 1) + " " + it.getName() + " " + it.getVotes());
        });
    }

    /**
     * 对 messages 排序：
     *      1、先将购买的热搜，并且购买的金额大于等于最低金额的热搜提取出来（ 删除被顶掉的热搜 ）
     *      2、提取出未被购买的热搜，按热度排序
     *      3、将购买名次的热搜列表和未购买名次的热搜列表合并（直接插入到未购买的名次列表中）
     * @param messageMaxMoney
     * @param messages
     * @return
     */
    public List<Message> sort(List<Message> messages, HashMap<Integer, Integer> messageMaxMoney){
        List<Message> buyMessages = messages.stream().filter(it -> !it.getBuyRanking().equals(0) && it.getBuyMoney() >= messageMaxMoney.get(it.getBuyRanking()))
                .sorted(new Comparator<Message>() {
                    @Override
                    public int compare(Message o1, Message o2) {
                        return o1.getBuyRanking() - o2.getBuyRanking();
                    }
                }).collect(Collectors.toList());
        List<Message> ordinaryMessage = messages.stream().filter(it -> it.getBuyRanking().equals(0))
                .sorted(new Comparator<Message>() {
                    @Override
                    public int compare(Message o1, Message o2) {
                        return (int) (o2.getVotes() - o1.getVotes());
                    }
                }).collect(Collectors.toList());
        return merage(buyMessages, ordinaryMessage);
    }

    /**
     * 将购买的热搜添加到未被购买的热搜列表里
     * @param buyMessages
     * @param ordinaryMessage
     * @return
     */
    private List<Message> merage(List<Message> buyMessages, List<Message> ordinaryMessage){
        buyMessages.stream().forEach(it -> ordinaryMessage.add(it.getBuyRanking() - 1, it));
        return ordinaryMessage;
    }
}
