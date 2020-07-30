package com.twu.controller;

import com.twu.bean.User;
import com.twu.util.InputError;

import java.util.HashMap;
import java.util.Scanner;

public class IndexInteractive {
    private static Scanner scanner = new Scanner(System.in);
    private MessageInteractive messageInteractive = new MessageInteractive();
    private UserInteractive userInteractive = new UserInteractive();
    /**
     * 登录之后的操作界面，根据用户输入的操作数执行对应的操作
     * @return
     */
    public  HashMap<String, Object> index(HashMap<String, Object> session){
        User user = (User) session.get("user");
        while (true) {
            System.out.println("你好，" + user.getName() + "，你可以：");
            System.out.println("1.查看热搜排行榜");
            if (!user.isAdmin()) {
                System.out.println("2.给热搜事件投票");
                System.out.println("3.购买热搜");
                System.out.println("4.添加热搜");
                System.out.println("5.退出");
            } else {
                System.out.println("2.添加热搜");
                System.out.println("3.添加超级热搜");
                System.out.println("4.退出");
            }
            String op = scanner.next();
            switch (op) {
                case "1":
                    session = messageInteractive.displayMessage(session);
                    break;
                case "2":
                    session = user.isAdmin() ? messageInteractive.addMessage(session, false) : messageInteractive.voteMessage(session);
                    break;
                case "3":
                    session = user.isAdmin() ? messageInteractive.addMessage(session, true) : messageInteractive.buyMessage(session);
                    break;
                case "4":
                    if(user.isAdmin()) {
                        session = userInteractive.logout(session);
                        return session;
                    } else {
                        session = messageInteractive.addMessage(session, false);
                    }
                    break;
                case "5":
                    session = userInteractive.logout(session);
                    return session;
                default:
                    InputError.overflowInput();
            }
        }
    }

}
