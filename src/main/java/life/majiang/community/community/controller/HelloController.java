package life.majiang.community.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {

    @GetMapping("/hello")       //之前这里多敲了一个空格：name="name "【不过可以在访问路径上加个空格，name值才可以有效果】
    public String hello(@RequestParam(name="name" /*,required=false ,defaultValue="world"*/) String name, Model model){
        //把浏览器中传过来的值，放到model里
        model.addAttribute("name" , name);
        return "hello";
    }
}
