package com.imnoob.community.Utils;

import com.imnoob.community.dto.GithubUser;
import com.imnoob.community.model.User;

public class CommonUtils {

    public static User GithubUserToUser(GithubUser guser){
        User user = new User();
        user.setAvatarUrl(guser.getAvatarUrl());
        user.setName(guser.getName());
        user.setBio(guser.getBio());
        user.setAccountId(String.valueOf(guser.getId()));
        return user;
    }
}
