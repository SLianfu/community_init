package life.majiang.community.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUND("您的问题不存在了，要不要换个试试");

    private String message;

    CustomizeErrorCode(String message) {
        //先试用他自己的，后面再调回来
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
