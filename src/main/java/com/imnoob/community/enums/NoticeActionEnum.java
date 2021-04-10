package com.imnoob.community.enums;

import com.imnoob.community.exception.CustomizeException;

public enum NoticeActionEnum {
    NOTICE_REPLY(1,"回复了"),
    NOTICE_LIKE(2,"点赞了"),
    NOTICE_COLLECT(3,"评论了");
    private Integer type;
    private String actionName;

    public Integer getType() {
        return type;
    }

    public String getActionName() {
        return actionName;
    }

    NoticeActionEnum(Integer type, String actionName) {
        this.type = type;
        this.actionName = actionName;
    }

    public static String getNameByType(Integer type){
        for (NoticeActionEnum value : NoticeActionEnum.values()) {
            if (value.getType() == type) return value.getActionName();
        }
        throw new CustomizeException(ExceptionEnum.TYPE_PARAM_WRONG);
    }
}
