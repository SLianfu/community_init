package life.majiang.community.community.controller;

import life.majiang.community.community.Service.CommentService;
import life.majiang.community.community.Service.QuestionService;
import life.majiang.community.community.dto.CommentDTO;
import life.majiang.community.community.dto.QuestionDTO;
import life.majiang.community.community.enums.CommentTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;


    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,  Model model ){
        //拿到id 先到数据库中查询id,同时希望返回QuestionDTO对象，（方便封装）
        QuestionDTO questionDTO =  questionService.getById(id);
        List<QuestionDTO> relatedQuestions = questionService.selectRelated(questionDTO);

        //通过question的id和评论问题的enum,那到评论的列表
        List<CommentDTO> comments = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);

        //累加阅读数
        questionService.incView(id);

        //要把questionDTO传到页面上去，用到model
        model.addAttribute("question",questionDTO);//一个question
        model.addAttribute("commnets",comments);
        model.addAttribute("relatedQuestions",relatedQuestions);

        return "question";
    }
}
