package com.twu.util;

import com.twu.bean.Message;
import com.twu.bean.User;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MessageUtil {
    private static final int SUPPER_MULTIPLE = 2;
    private static final int MULTIPLE = 1;
    private static Scanner scanner = new Scanner(System.in);

    /**
     * 查看热搜排行榜
     * @param session
     * @return
     */
    public static HashMap<String, Object> display(HashMap<String, Object> session){
        List<Message> messages = (List<Message>) session.get("messages");
        messages = messages.stream().sorted(new Comparator<Message>() {
            @Override
            public int compare(Message o1, Message o2) {
                return (int) (o2.getVotes() - o1.getVotes());
            }
        }).collect(Collectors.toList());
        List<Message> finalMessages = messages;
        Stream.iterate(0, index -> index + 1).limit(messages.size()).forEach(index -> {
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
        messages.add(new Message(messageName, 0, superMessage ? SUPPER_MULTIPLE : MULTIPLE));
        session.put("messages", messages);
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
        session.put("message", messages);
        return session;
    }

    /**
     * 购买热搜
     * @param messages
     * @return
     */
    public static HashMap<String, Object> buy(HashMap<String, Object> messages){
        return messages;
    }
}
