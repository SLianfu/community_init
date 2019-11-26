package life.majiang.community.community.controller;

import life.majiang.community.community.Service.QuestionService;
import life.majiang.community.community.dto.QuestionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                Model model ){
        //拿到id 先到数据库中查询id,同时希望返回QuestionDTO对象，（方便封装）
        QuestionDTO questionDTO =  questionService.getById(id);
        //要把questionDTO传到页面上去，用到model
        model.addAttribute("question",questionDTO);//一个question

        return "question";
    }
}
