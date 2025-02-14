package com.peakyapi.utils;

import com.peakyapi.models.User;

public class Utils {
    public static boolean tokenValidate(String token, User user ) {
        return token.equals(user.getToken());
    }
}
