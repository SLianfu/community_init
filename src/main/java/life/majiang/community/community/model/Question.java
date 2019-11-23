package life.majiang.community.community.model;
//这个包是 数据库模型，与数据库一一对应
import lombok.Data;

@Data
public class Question {
    /**
     * id int auto_increment,
     * 	title varchar(50),
     * 	description text,
     * 	gmt_create BIGINT,
     * 	gmt_modified bigint,
     * 	creator int,
     * 	comment_count int default 0,
     * 	view_count int default 0,
     * 	tag varchar(255),
     * 	constraint question_pk
     * 		primary key (id)
     */
    private Long id;//database column QUESTION.ID
    private String title;//database column QUESTION.TITLE
    private Long gmtCreate;//database column QUESTION.GMT_CREATE
    private Long gmtModified;//column QUESTION.GMT_MODIFIED
    private Long creator ;// database column QUESTION.CREATOR
    private Integer commentCount ;//database column QUESTION.COMMENT_COUNT
    private Integer viewCount ;//database column QUESTION.VIEW_COUNT
    private Integer likeCount ; //喜欢的人数database column QUESTION.LIKE_COUNT
    private String tag ;//database column QUESTION.TAG
    private String description;// database column QUESTION.DESCRIPTION

    }
