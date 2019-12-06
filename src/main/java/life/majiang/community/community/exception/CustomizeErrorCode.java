package life.majiang.community.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUND(2001,"您的问题不存在了，要不要换个试试"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何问题或者评论进行回复"),//现在要重新定义一个ErrorCode
    NO_LOGIN(2003,"当前操作需要登录，请先登录后重试"),
    SYS_ERROR(2004,"服务器冒烟了，要不然你稍后再试试！"),
    TYPE_PARAM_WRONG(2005,"评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006,"回复的评论不存在了，要不要换个试试"),
    CONTENT_IS_EMPTY(2007,"输入的内容不能为空")
        ;

    private String message;
    private Integer code;

    CustomizeErrorCode(Integer code, String message) {//ctrl+f6：替换。。
        //先试用他自己的，后面再调回来
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
