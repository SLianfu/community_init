package life.majiang.community.community.model;

import lombok.Data;

//这里也是个bean 这个是数据库里的（model）
//加@Data:自动生成get set tostring hashcode RequiredArgsConstructor【lombok支持】
@Data
public class User {
    private Long  id;//用户id
    private String name;//名称
    private String accountId;//第三方登录的id
    private String token;//第三方登录验证的令牌
    private Long gmtCreate;//生成user的时间
    private Long gmtModified;//修改时间
    private String  avatarUrl;//头像链接？

}
