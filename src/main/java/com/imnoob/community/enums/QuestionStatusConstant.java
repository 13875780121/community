package com.imnoob.community.enums;

public class QuestionStatusConstant {

    static public Integer PUBLISHED = 1;
    static public Integer AUDITING = 2;
    static public Integer AUDIT_PASS = 3;
    static public Integer AUDIT_FAILED = 4;

    static String getQuestionInfo(Integer status){
        if (status == 1) return "已发布";
        else if (status == 2) return "审核中";
        else if (status == 3) return "审核通过";
        else if (status == 4) return "审核失败";
        else return "";
    }
}
