package com.imnoob.community.Utils;

import com.imnoob.community.dto.GithubUser;
import com.imnoob.community.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class CommonUtils {


//对象转换
    public static User GithubUserToUser(GithubUser guser){
        User user = new User();
        user.setAvatarUrl(guser.getAvatarUrl());
        user.setName(guser.getName());
        user.setBio(guser.getBio());
        user.setAccountId(String.valueOf(guser.getId()));
        return user;
    }

//    获取IP地址
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
    //
    public static int calDayInYear(Date date){
        int year = date.getYear();
        int month = date.getMonth();
        int day = date.getDay();
        int sumDay = 0;
        switch(month) {
            case 12:
                sumDay += 30;
            case 11:
                sumDay += 31;
            case 10:
                sumDay += 30;
            case 9:
                sumDay += 31;
            case 8:
                sumDay += 31;
            case 7:
                sumDay += 30;
            case 6:
                sumDay += 31;
            case 5:
                sumDay += 30;
            case 4:
                sumDay += 31;
            case 3:
                if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                    sumDay += 29;
                } else
                    sumDay += 28;
            case 2:
                sumDay += 31;
            case 1:
                sumDay += day;
        }
        return sumDay;
    }
}
