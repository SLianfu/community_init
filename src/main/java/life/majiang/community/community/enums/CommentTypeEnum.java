package life.majiang.community.community.enums;

public enum CommentTypeEnum {
    QUESTION(1),//类型为1
    COMMENT(2)//类型为2
    ;
    private Integer type;

    public static boolean isExits(Integer type) {
        for (CommentTypeEnum commentTypeEnum : CommentTypeEnum.values()) {//CommentTypeEnum.values()遍历type类型
            if (commentTypeEnum.getType() == type){
                return true;
            }
        }
        return false;
    }

    //定义一个得到type的方法
    public Integer getType() {
        return type;
    }

    CommentTypeEnum(Integer type) {
        this.type = type;
    }
}
