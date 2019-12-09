package life.majiang.community.community.dto;

import life.majiang.community.community.exception.CustomizeErrorCode;
import life.majiang.community.community.exception.CustomizeException;
import lombok.Data;

@Data
public class ResultDTO<T> {
    private Integer code;//增强message:返回唯一标识码，去判断成功了还是失败了
    private String message;
    //有时要求传入list,user,引入泛型
    private T data;

    //更高效的扩展，提供一些静态方法
    public  static ResultDTO errorOf(Integer code,String message){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static ResultDTO errorOf(CustomizeErrorCode errorCode) {
        return errorOf(errorCode.getCode(),errorCode.getMessage());
    }

    public static ResultDTO errorOf(CustomizeException e) {
        return errorOf(e.getCode(),e.getMessage());
    }

    public static ResultDTO okOf(){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("成功");
        return resultDTO;
    }

    public static <T> ResultDTO okOf(T t){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        resultDTO.setData(t);
        return resultDTO;
    }
}
