package life.majiang.community.community.dto;
//这个包是存放从网络别的地方 存进了的临时的bean (传输层的一个东西)
import lombok.Data;

@Data
public class GithubUser {


    private String name;
    private Long id; //这个是用来存放用户人数的？人数可能多 用long
    private String bio;
    private String avatarUrl;//头像地址avatar_url;【数据库中用avatar_url命名，json.object自动把他设置成avatarUrl映射到javabean中】
    //所以javabean命名一般用驼峰法


}
