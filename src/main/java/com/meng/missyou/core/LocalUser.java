package com.meng.missyou.core;

import com.meng.missyou.model.User;

public class LocalUser {
    private static User user;

    public static void setUser(User user, Integer scope) {
        LocalUser.user = user;
    }

    public static User getUser() {
        return LocalUser.user;
    }
}
