package life.majiang.community.community.controller;

import life.majiang.community.community.Service.CommentService;
import life.majiang.community.community.dto.CommentCreateDTO;
import life.majiang.community.community.dto.CommentDTO;
import life.majiang.community.community.dto.ResultDTO;
import life.majiang.community.community.enums.CommentTypeEnum;
import life.majiang.community.community.exception.CustomizeErrorCode;
import life.majiang.community.community.model.Comment;
import life.majiang.community.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

//通过注解ResponseBody，会自动序列化成json,并发到前端
//@RequestBody就能接收json格式的数据，并封装成对象


@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

//    @Autowired
//    static User user;//为了测试，这里把user设置成静态

    @ResponseBody//返回的是json,要加这个注解
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest request){
        //通过注入request拿到session 然后拿到user
        User user = (User)request.getSession().getAttribute("user");
        if (user == null){
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        //判断回复内容是否为空，后端校验
//        if (commentCreateDTO == null || commentCreateDTO.getContent() == null || commentCreateDTO.getContent() ==""){
        if (commentCreateDTO == null || commentCreateDTO.getContent().isEmpty()){
            return ResultDTO.errorOf(CustomizeErrorCode.CONTENT_IS_EMPTY);
        }

        //@RequestBody CommentDTO commentDTO自动把传入过来的json的key-value:赋值到commentDTO
        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());//可能不存在（已经被删除）
        //然后要判断ParentId是否为空，而ParentId依赖getType（1是回复问题，2是回复评论）
        //这里要新创建一个枚举类型，去判断type

        comment.setContent(commentCreateDTO.getContent());
        comment.setType(commentCreateDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        //这里要得到session
        comment.setCommentator(user.getId());//user.getId()评论人id，通过session得到（如果已经登陆的话）
        comment.setLikeCount(0L);
        commentService.insert(comment);//这里可能出现异常，这里希望异常也给页面返回一个json结构
        //而不是直接跳转错误页面，不然的话，我们一点击，没有办法实时的去改，这样对用户比较友好
        //怎么做：在insert内层做返回jsond的处理：advice有一个同一的操作：如果：if( e instanceof CustomizeException)出现这个异常
        //（访问页面异常）就直接返回一个错误页面或者（我们希望在请求接口的时候）返回message信息
        //这两者的区别在：content-Type:text/html和application/json


 /*       Map<Object, Object> objectObjectMap = new HashMap<>();
        objectObjectMap.put("message","成功");*/
        //objectObjectMap,是一个对象，通过注解ResponseBody，会自动序列化成json,并发到前端
        //@RequestBody就能接收json格式的数据，并封装成对象；objectObjectMap
        return ResultDTO.okOf();
    }

    //点击回复评论时，传入commentId
    @ResponseBody//返回的是json,要加这个注解
    @RequestMapping(value = "/comment/{id}",method = RequestMethod.GET)
    public ResultDTO<List<CommentDTO>> comments(@PathVariable(name = "id") Long id){
        List<CommentDTO> commentDTOS = commentService.listByTargetId(id, CommentTypeEnum.COMMENT);
        return ResultDTO.okOf(commentDTOS);
    }
}
