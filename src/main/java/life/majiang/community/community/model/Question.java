package life.majiang.community.community.model;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Long getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
