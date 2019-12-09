package life.majiang.community.community.Service;

import life.majiang.community.community.dto.CommentDTO;
import life.majiang.community.community.enums.CommentTypeEnum;
import life.majiang.community.community.exception.CustomizeErrorCode;
import life.majiang.community.community.exception.CustomizeException;
import life.majiang.community.community.mapper.*;
import life.majiang.community.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: slf
 * @date 2019/12/5 14:03
 */
@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CommentExtMapper commentExtMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Autowired
    private UserMapper userMapper;

    @Transactional//增加事物
    public void insert(Comment comment) {

        if (comment.getParentId() == null || comment.getParentId() == 0){//ParentId是指要评论的问题id 或者 第一层评论的id
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);//这里怎么要把这个消息回到controller里面呢？
            //我们可以用exception，抛出去
        }
        //验证其他错误
        if (comment.getType() == null || !CommentTypeEnum.isExits(comment.getType())){
            //!CommentTypeEnum.isExits(comment.getType()没有这个类型的评论
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }

        if (comment.getType() == CommentTypeEnum.COMMENT.getType()){//上面已经校验了type了
            //回复评论
            //先查找评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComment == null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }

            commentMapper.insert(comment);//不为空就插入这条评论进入数据库
            //增加评论数,之前的评论数就不管了
            //
            Comment parentComment = new Comment();
            parentComment.setId(comment.getParentId());

            parentComment.setCommentCount(1);
            commentExtMapper.incCommentCount(parentComment);

        }else {
            //回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            question.setCommentCount(1);
            questionExtMapper.incCommentCount(question);
        }
    }

    public List<CommentDTO> listByTargetId(Long id, CommentTypeEnum type) {
        //拿到所有的usermapper
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andParentIdEqualTo(id)
                .andTypeEqualTo(type.getType());
        //怎么让时间倒叙？,这里应该是按照数据库拼接上去的
        commentExample.setOrderByClause("gmt_create desc");

        List<Comment> comments = commentMapper.selectByExample(commentExample);

        //拿到comments里面的commentator,去查用户信息
        if (comments.size() == 0){
            return  new ArrayList<>();
        }
        Set<Long> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Long> userIds = new ArrayList<>();
        userIds.addAll(commentators);

        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);//拿到所有user

//        进行匹配，暴力的话是两个循环
        //把uses做成一个map
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));//值是userid,和user

        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            //这里将comment的属性拷贝到commentDTO
            BeanUtils.copyProperties(comment,commentDTO);
            //把user属性加上
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());


        return commentDTOS;
    }
}
