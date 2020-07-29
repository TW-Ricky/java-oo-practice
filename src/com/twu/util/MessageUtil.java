package com.twu.util;

import com.twu.bean.Message;
import com.twu.bean.User;
import org.omg.CORBA.INTERNAL;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MessageUtil {
    private static final int SUPPER_MULTIPLE = 2;
    private static final int MULTIPLE = 1;
    private static Scanner scanner = new Scanner(System.in);

    /**
     * 将购买的热搜添加到未被购买的热搜列表里
     * @param buyMessages
     * @param ordinaryMessage
     * @return
     */
    public static List<Message> merage(List<Message> buyMessages, List<Message> ordinaryMessage){
        buyMessages.stream().forEach(it -> ordinaryMessage.add(it.getBuyRanking() - 1, it));
        return ordinaryMessage;
    }

    /**
     * 查看热搜排行榜
     * @param session
     * @return
     */
    public static HashMap<String, Object> display(HashMap<String, Object> session){
        List<Message> messages = (List<Message>) session.get("messages");
        HashMap<Integer, Integer> messageMaxMoney = (HashMap<Integer, Integer>) session.get("messageMaxMoney");
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
        List<Message> finalMessages = merage(buyMessages, ordinaryMessage);
        Stream.iterate(0, index -> index + 1).limit(finalMessages.size()).forEach(index -> {
            Message it = finalMessages.get(index);
            System.out.println((index + 1) + " " + it.getName() + " " + it.getVotes());
        });
        session.put("messages", messages);
        return session;
    }

    /**
     * 添加热搜
     * @param session
     * @param superMessage true为超级热搜
     * @return
     */
    public static HashMap<String, Object> add(HashMap<String, Object> session, boolean superMessage){
        List<Message> messages = (List<Message>) session.get("messages");
        System.out.println("请输入添加的" + (superMessage ? "超级热搜：" : "热搜："));
        String messageName = scanner.next();
        long cnt = messages.stream().filter(it -> it.getName().equals(messageName)).count();
        if (cnt != 0) {
            System.out.println("当前热搜已经存在。");
            return session;
        }
        messages.add(new Message(messageName, 0, superMessage ? SUPPER_MULTIPLE : MULTIPLE, 0, 0));
        session.put("messages", messages);
        System.out.println("添加成功。");
        return session;
    }

    /**
     * 用户投票
     * @param session
     * @return
     */
    public static HashMap<String, Object> vote(HashMap<String, Object> session){
        List<Message> messages = (List<Message>) session.get("messages");
        User user = (User) session.get("user");
        System.out.println("请输入您要投票的热搜:");
        String messageName = scanner.next();
        if (messages.stream().filter(it -> it.getName().equals(messageName)).count() == 0){
            InputError.errorMessage();
            return session;
        }
        System.out.println("请输入您要投几票：（您目前还剩" + user.getVotes() + "票）");
        int number = scanner.nextInt();
        while(number < 0 || number > user.getVotes()){
            InputError.overflowInput();
            return session;
        }
        messages = messages.stream().filter(it -> {
            if (it.getName().equals(messageName)) {
                it.setVotes(it.getVotes() + number * it.getMultiple());
            } else {
                it.setVotes(it.getVotes());
            }
            return true;
        }).collect(Collectors.toList());
        System.out.println("投票成功。");
        user.setVotes(user.getVotes() - number);
        session.put("user", user);
        session.put("messages", messages);
        return session;
    }

    /**
     * 购买热搜
     * @param session
     * @return
     */
    public static HashMap<String, Object> buy(HashMap<String, Object> session) {
        List<Message> messages = (List<Message>) session.get("messages");
        HashMap<Integer, Integer> messageMaxMoney = (HashMap<Integer, Integer>) session.get("messageMaxMoney");
        System.out.println("请输入您要购买的热搜：");
        String massageName = scanner.next();
        long cnt = messages.stream().filter(it -> it.getName().equals(massageName)).count();
        if (cnt == 0) {
            InputError.errorMessage();
            return session;
        }
        System.out.println("请输入您为其购买的名次：");
        Integer rank = Integer.valueOf(scanner.next());
        if (rank < 0 || rank > messages.size()) {
            InputError.overflowInput();
            return session;
        }
        if (!messageMaxMoney.containsKey(rank)) {
            messageMaxMoney.put(rank, 0);
        }
        Integer minMoney = messageMaxMoney.get(Integer.valueOf(rank));
        System.out.println("请输入您为其购买的金额：(购买此排名最少需要" + minMoney + "元）");
        Integer money = Integer.valueOf(scanner.next());
        if (money < minMoney) {
            System.out.println("金额不足，购买失败。");
            return session;
        }
        messages = messages.stream().filter(it -> {
            if (it.getName().equals(massageName)) {
                it.setBuyRanking(rank);
                it.setBuyMoney(money);
            }
            return true;
        }).collect(Collectors.toList());
        messageMaxMoney.forEach((key, value) -> {
            if (key.equals(rank)) {
                messageMaxMoney.put(key, money);
            }
        });
        System.out.println("购买成功。");
        session.put("messages", messages);
        session.put("messageMaxMoney", messageMaxMoney);
        return session;
    }
}
