package life.majiang.community.community.dto;

import life.majiang.community.community.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
    private Integer id;//database column QUESTION.ID
    private String title;//database column QUESTION.TITLE
    private Long gmtCreate;//database column QUESTION.GMT_CREATE
    private Long gmtModified;//column QUESTION.GMT_MODIFIED
    private Long creator ;// database column QUESTION.CREATOR
    private Integer commentCount ;//database column QUESTION.COMMENT_COUNT
    private Integer viewCount ;//database column QUESTION.VIEW_COUNT
    private Integer likeCount ; //喜欢的人数database column QUESTION.LIKE_COUNT
    private String tag ;//database column QUESTION.TAG
    private String description;// database column QUESTION.DESCRIPTION

    //用private Long creator ; 关联user
    private User user;//拿到头像
}

