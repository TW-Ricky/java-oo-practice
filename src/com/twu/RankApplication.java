package com.twu;

import com.twu.bean.Message;
import com.twu.bean.User;
import com.twu.controller.IndexInteractive;
import com.twu.controller.UserInteractive;
import com.twu.util.InputError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class RankApplication {
    private static Scanner scanner = new Scanner(System.in);
    /**
     * 以下三个容器模拟项目数据库中的数据表
     */
    private static List<Message> messages = new ArrayList<>();
    private static List<User> users = new ArrayList<>();
    private static HashMap<Integer, Integer> messageMaxMoney = new HashMap<>();

    private static UserInteractive userInteractive = new UserInteractive();
    private static IndexInteractive indexInteractive = new IndexInteractive();

    public static void start(){
        HashMap<String, Object> session = new HashMap<>();
        session.put("messages", messages);
        session.put("messageMaxMoney", messageMaxMoney);
        session.put("users", users);
        running(session);
    }

    private static void running(HashMap<String, Object> session){
        while (true) {
            System.out.println("欢迎来到小白热搜排行榜，你是？");
            System.out.println("1.用户");
            System.out.println("2.管理员");
            System.out.println("3.退出");
            String op = scanner.next();
            switch (op) {
                case "1":
                case "2":
                    session = userInteractive.login(Integer.valueOf(op), session);
                    if(session.containsKey("user")) session = indexInteractive.index(session);
                    break;
                case "3":
                    users = (List<User>) session.get("users");
                    messages = (List<Message>) session.get("messages");
                    messageMaxMoney = (HashMap<Integer, Integer>) session.get("messageMaxMoney");
                    return;
                default:
                    InputError.overflowInput();
            }
        }
    }
}
