package com.twu.controller;

import com.twu.bean.Message;
import com.twu.bean.User;
import com.twu.service.MessageService;
import com.twu.util.InputError;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class MessageInteractive {
    private static Scanner scanner = new Scanner(System.in);

    MessageService messageService = new MessageService();

    /**
     * 添加热搜
     * @param session
     * @param superMessage true为超级热搜
     * @return
     */
    public HashMap<String, Object> addMessage(HashMap<String, Object> session, boolean superMessage){
        List<Message> messages = (List<Message>) session.get("messages");
        System.out.println("请输入添加的" + (superMessage ? "超级热搜：" : "热搜："));
        String messageName = scanner.next();
        long cnt = messages.stream().filter(it -> it.getName().equals(messageName)).count();
        if (cnt != 0) {
            System.out.println("当前热搜已经存在。");
            return session;
        }
        session.put("messages", messageService.add(messages, messageName, superMessage));
        System.out.println("添加成功。");
        return session;
    }

    /**
     * 用户投票
     * @param session
     * @return
     */
    public HashMap<String, Object> voteMessage(HashMap<String, Object> session){
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
        if (number < 0 || number > user.getVotes()){
            InputError.overflowInput();
            return session;
        }
        user.setVotes(user.getVotes() - number);
        session.put("user", user);
        session.put("messages", messageService.vote(messages, messageName, number));
        System.out.println("投票成功。");
        return session;
    }

    /**
     * 购买热搜
     * @param session
     * @return
     */
    public HashMap<String, Object> buyMessage(HashMap<String, Object> session) {
        List<Message> messages = (List<Message>) session.get("messages");
        HashMap<Integer, Integer> messageMaxMoney = (HashMap<Integer, Integer>) session.get("messageMaxMoney");
        System.out.println("请输入您要购买的热搜：");
        String messageName = scanner.next();
        long cnt = messages.stream().filter(it -> it.getName().equals(messageName)).count();
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
        System.out.println("请输入您为其购买的金额：(购买此排名最少需要" + (minMoney + 1) + "元）");
        Integer money = Integer.valueOf(scanner.next());
        if (money <= minMoney) {
            System.out.println("金额不足，购买失败。");
            return session;
        }
        session.put("messages", messageService.buy(messages, messageName, rank, money));
        session.put("messageMaxMoney", messageService.updateMax(messageMaxMoney, rank, money));
        System.out.println("购买成功。");
        return session;
    }


    /**
     * 查看 message 排行榜
     * @param session
     * @return
     */
    public HashMap<String, Object> displayMessage(HashMap<String, Object> session) {
        List<Message> messages = (List<Message>) session.get("messages");
        HashMap<Integer, Integer> messageMaxMoney = (HashMap<Integer, Integer>) session.get("messageMaxMoney");
        messages = messageService.sort(messages, messageMaxMoney);
        messageService.display(messages);
        session.put("messages", messages);
        return session;
    }

}
