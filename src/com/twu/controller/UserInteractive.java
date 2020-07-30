package com.twu.controller;

import com.twu.bean.User;
import com.twu.util.InputError;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class UserInteractive {
    private static final String ADMIN = "admin";
    private static final String ADMIN_PASSWORD = "admin";
    private static final int USER_VOTES = 10;
    private static Scanner scanner = new Scanner(System.in);
    /**
     * 实现登录操作
     * @param op
     * op = 1: 用户登录
     * op = 2：管理员登录
     * @return
     */
    public HashMap<String, Object> login(int op, HashMap<String, Object> session) {
        List<User> users = (List<User>) session.get("users");
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
                    return session;
                }
                System.out.println("请输入管理员密码：");
                userPassword = scanner.next();
                if (!ADMIN_PASSWORD.equals(userPassword)){
                    InputError.errorAdminPassword();
                    return session;
                }
                break;
            default:
                return session;
        }
        List<User> userList = users.stream().filter(it -> it.getName().equals(userName)).collect(Collectors.toList());
        int cnt = userList.size();
        User user = userList.stream().findAny().orElse(new User(userName, USER_VOTES, op != 1));
        if(cnt == 0) {
            users.add(user);
        }
        session.put("user", user);
        return session;
    }

    /**
     * 退出登录时，更新假象数据库list中的信息：
     *      messages：保存热搜的相关信息
     *      users: 保存用户相关信息
     *      messageMaxMoney: 保存购买排名需要的最低金额
     *      session: 用户保存程序运行过程中的所有信息
     * @return
     */
    public HashMap<String, Object> logout(HashMap<String, Object> session) {
        User user1 = (User) session.get("user");
        List<User> users = (List<User>) session.get("users");
        users = users.stream().filter(it -> {
            if (it.getName().equals(user1.getName())) {
                it.setVotes(user1.getVotes());
            }
            return true;
        }).collect(Collectors.toList());
        session.put("users", users);
        session.remove("user");
        return session;
    }

}
