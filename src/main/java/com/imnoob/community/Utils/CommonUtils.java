package com.imnoob.community.Utils;

import com.imnoob.community.dto.GithubUser;
import com.imnoob.community.model.User;

import javax.servlet.http.HttpServletRequest;

public class CommonUtils {



    public static User GithubUserToUser(GithubUser guser){
        User user = new User();
        user.setAvatarUrl(guser.getAvatarUrl());
        user.setName(guser.getName());
        user.setBio(guser.getBio());
        user.setAccountId(String.valueOf(guser.getId()));
        return user;
    }

    public static String getIpAddr(HttpServletRequest request){
        String ipAddress = null;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
            }
            // 通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null) {
                if (ipAddress.contains(",")) {
                    return ipAddress.split(",")[0];
                } else {
                    return ipAddress;
                }
            } else {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }


    }
}
