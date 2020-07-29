package com.twu;
import com.sun.org.apache.bcel.internal.generic.ARETURN;
import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;
import com.twu.bean.*;
import com.twu.util.InputError;
import com.twu.util.MessageUtil;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    private static final String ADMIN = "admin";
    private static final String ADMIN_PASSWORD = "admin";
    private static final int USER_VOTES = 10;
    private static List<Message> messages = new ArrayList<>();
    private static List<User> users = new ArrayList<>();
    private static HashMap<String, Object> session = new HashMap<>();
    private static HashMap<String, Object> mySql = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);

    static void login(int op) {
        String userName, userPassword;
        switch (op) {
            case 1:
                System.out.println("请输入您的昵称：");
                userName = scanner.next();
                break;
            case 2:
                System.out.println("请输入管理员昵称：");
                userName = scanner.next();
                if (!ADMIN.equals(userName)){
                    InputError.errorAdmin();
                    return;
                }
                System.out.println("请输入管理员密码：");
                userPassword = scanner.next();
                if (!ADMIN_PASSWORD.equals(userPassword)){
                    InputError.errorAdminPassword();
                    return;
                }
                break;
            default:
                return;
        }
        List<User> userList = users.stream().filter(it -> it.getName().equals(userName)).collect(Collectors.toList());
        int cnt = userList.size();
        User user = userList.stream().findAny().orElse(new User(userName, USER_VOTES, op != 1));
        if(cnt == 0) {
            users.add(user);
        }
        session.put("messages", messages);
        session.put("user", user);
    }

    static void index(){
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
                    session = MessageUtil.display(session);
                    break;
                case "2":
                    session = user.isAdmin() ? MessageUtil.add(session, false) : MessageUtil.vote(session);
                    break;
                case "3":
                    session = user.isAdmin() ? MessageUtil.add(session, true) : MessageUtil.buy(session);
                    break;
                case "4":
                    if(user.isAdmin()) {
                        User user1 = (User) session.get("user");
                        users = users.stream().filter(it -> {
                            if (it.getName().equals(user1.getName())) {
                                it.setVotes(user1.getVotes());
                            }
                            return true;
                        }).collect(Collectors.toList());
                        messages = (List<Message>) session.get("messages");
                        session.remove("messages");
                        session.remove("user");
                        return;
                    } else {
                        session = MessageUtil.add(session, false);
                    }
                    break;
                case "5":
                    User user1 = (User) session.get("user");
                    users = users.stream().filter(it -> {
                        if (it.getName().equals(user1.getName())) {
                           it.setVotes(user1.getVotes());
                        }
                        return true;
                    }).collect(Collectors.toList());
                    messages = (List<Message>) session.get("messages");
                    session.remove("messages");
                    session.remove("user");
                    return;
                default:
                    InputError.overflowInput();
            }
        }
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println("欢迎来到小白热搜排行榜，你是？");
            System.out.println("1.用户");
            System.out.println("2.管理员");
            System.out.println("3.退出");
            String op = scanner.next();
            switch (op) {
                case "1":
                case "2":
                    login(Integer.valueOf(op));
                    if(session.containsKey("user")) index();
                    break;
                case "3":
                    System.exit(0);
                    break;
                default:
                    InputError.overflowInput();
            }
        }
    }
}
