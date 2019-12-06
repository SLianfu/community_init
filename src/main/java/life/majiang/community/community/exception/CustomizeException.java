package life.majiang.community.community.exception;

public class CustomizeException extends RuntimeException {
    private String message;
    private Integer code;


    public CustomizeException(ICustomizeErrorCode errorCode) {
        this.code = errorCode.getCode();//这样就把ICustomizeErrorCode的code赋值进来了
        this.message = errorCode.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
