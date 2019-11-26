package life.majiang.community.community.controller;

import life.majiang.community.community.Service.QuestionService;
import life.majiang.community.community.dto.PaginationDTO;
import life.majiang.community.community.mapper.UserMapper;
import life.majiang.community.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;
    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action, //通过路径参数动态切换【传递的形参】
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(name = "page",defaultValue = "1") Integer page,
                          @RequestParam(name = "size",defaultValue = "5") Integer size
                            ){

        //这里要进行身份验证
        User user = (User) request.getSession().getAttribute("user");
        if (user == null){
            return "redirect:/";
        }
        if ("questions".equals(action)){//访问路径为questions，显示我的提问
            model.addAttribute("section","questions");//section部分的意思
            model.addAttribute("sectionName","我的提问");
        } else if ("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
        }

        //查找我的问题，根据用户id（就是question对象的creator）
        PaginationDTO paginationDTO = questionService.list(user.getId(), page, size);
        model.addAttribute("pagination",paginationDTO);

        return "profile";
    }

}
