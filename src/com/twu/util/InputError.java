package com.twu.util;

public class InputError {
    /**
     * 输入操作超限
     */
    public static void overflowInput() {
        System.out.println("输入超出小白能力范围，请重新输入:");
    }

    /**
     * 管理员昵称错误
     */
    public static void errorAdmin() {
        System.out.println("管理员昵称错误。");
    }

    /**
     * 管理员密码错误
     */
    public static void errorAdminPassword() {
        System.out.println("管理员密码错误。");
    }

    /**
     * 热搜不存在
     */
    public static void errorMessage() {
        System.out.println("热搜名不存在。");
    }
}
