package life.majiang.community.community.exception;

import org.springframework.util.StringUtils;

public interface ICustomizeErrorCode {
//    String message = null;
    String getMessage();
    //定义一个唯一标识码
    Integer getCode();
}

