package life.majiang.community.community.controller;

import life.majiang.community.community.Service.QuestionService;
import life.majiang.community.community.cache.TagCache;
import life.majiang.community.community.dto.QuestionDTO;
import life.majiang.community.community.model.Question;
import life.majiang.community.community.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {


    @Autowired
    private QuestionService questionService;

    //修改发布的问题：先得到question的id，（在路径上）
    @GetMapping("/publish/{question_id}")
    public String edit(@PathVariable(name = "question_id") Long question_id,Model model){
        //通过id拿到question
        QuestionDTO question = questionService.getById(question_id);//这里只需要拿出数据来就可以了
        //通过question拿到 要修改的question对象，后面可以通过模型去绑定表单
        model.addAttribute("title", question.getTitle());//为了回显页面
        model.addAttribute("description", question.getDescription());
        model.addAttribute("tag", question.getTag());
        //然后把这个对象放回到数据库，或者其他(放回到页面)
        //发布【发布之前要传入一个唯一标识】之后跳转@PostMapping("/publish")要根据原来的id，把数据传给原来的question
        model.addAttribute("question_id",question.getId());//把question_id传递给页面publish.html
        //要怎么传递回来呢？【在@PostMapping("/publish")接收question_id】

        model.addAttribute("tags", TagCache.get());
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value = "title",required = false) String title,//这里用请求域参数传递形参RequestParam
            @RequestParam(value = "description",required = false) String description,
            @RequestParam(value = "tag",required = false) String tag,
            @RequestParam(value = "question_id",required = false) Long question_id,//这个id是可以为空的，空就创建question
            HttpServletRequest request,
            Model model) {
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);

        model.addAttribute("tags", TagCache.get());

        //数据校验，是否为空，前后端都要校验
        if (title == null || title == "") {
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if (description == null || description == "") {
            model.addAttribute("error", "问题补充不能为空");
            return "publish";
        }
        if (tag == null || tag == "") {
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }

        //invalid是过滤的非法标签
        String invalid = TagCache.filterInvalid(tag);
        if (StringUtils.isNoneBlank(invalid)) {
            model.addAttribute("error", "输入非法标签:" + invalid);
            return "publish";
        }

        //拦截器那里已经把user 放入 session作用域，这里获取user来验证user是否为空【即访问/publish时】
        User user = (User) request.getSession().getAttribute("user");

        if (user == null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);

        question.setCreator(user.getId());
        question.setId(question_id);

        //更新或创建question     【要有一个唯一标识，这里传入question_id】
        questionService.createOrUpdadte(question);
        return "redirect:/";

    }


    @GetMapping("/publish")
    public String publish(Model model){
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }
}