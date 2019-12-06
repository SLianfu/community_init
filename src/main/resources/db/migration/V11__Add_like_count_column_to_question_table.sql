alter table QUESTION
    add like_count INTEGER default 0;

comment on column QUESTION.like_count is '点赞数';