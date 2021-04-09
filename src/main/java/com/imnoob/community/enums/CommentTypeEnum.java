package com.imnoob.community.enums;

public enum CommentTypeEnum {
    QUESTION_TYPE(1),
    COMMENT_TYPE(2);

    private Integer type;

    CommentTypeEnum(Integer type) {
        this.type = type;
    }
    CommentTypeEnum() {
    }

    public Integer getType(){
        return type;
    }
    public static boolean isExitType(Integer item){
        for (CommentTypeEnum value : CommentTypeEnum.values()) {
            if (value.getType().equals(item)) return true;
        }
        return false;

    }
}
