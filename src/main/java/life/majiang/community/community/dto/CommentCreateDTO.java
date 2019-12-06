package life.majiang.community.community.dto;

import lombok.Data;

@Data//这个是你传递给我的dto,和返回页面的dto是不一样的
public class CommentCreateDTO {
    private Long parentId;
    private String content;
    private Integer type;
}
