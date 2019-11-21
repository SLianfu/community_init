package life.majiang.community.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    //@GetMapping("/hello")       //之前这里多敲了一个空格：name="name "【不过可以在访问路径上加个空格，name值才可以有效果】
    @GetMapping("/")//相当于不输入任何路径
    public String index(){
        //把浏览器中传过来的值，放到model里
        //model.addAttribute("name" , name);
        return "index";
    }
}
