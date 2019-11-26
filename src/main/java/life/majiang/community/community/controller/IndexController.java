package life.majiang.community.community.controller;

import life.majiang.community.community.Service.QuestionService;
import life.majiang.community.community.dto.PaginationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;
    //@GetMapping("/hello")       //之前这里多敲了一个空格：ame="name "【不过可以在访问路径上加个空格，name值才可以有效果】
    @GetMapping("/")//相当于不输入任何路径
    public String index(HttpServletRequest request ,
                            Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "5") Integer size

    ){
        //把浏览器中传过来的值，放到model里
        //model.addAttribute("name" , name);
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null && cookies.length != 0){
//            //这里用数据库检验的方式，成本高（小用户量还行）【以后可以用redis方式去做】
//            for (Cookie cookie : cookies){
//                if (cookie.getName().equals("token")){
//                    String token = cookie.getValue();
//                    User user = userMapper.findByToken(token);//token怎么回去呢？传入httpservletresponse
//                    if (user != null){  //验证前端的工作情况
//                        request.getSession().setAttribute("user",user);
//                        //前端就可以通过会话级的（页面级）数据去判断登录
//                    }
//                    break;
//                } /*else {
//                    System.out.println("登陆失败，没有传response，就不响应页面了");//这里 每次寻找cookie 如果没有找到正确的 令牌 就打印一次这个
//                }*/
//            }
//        }
        //分页处理：
        PaginationDTO pagination  = questionService.list(page,size);

        //List<QuestionDTO> questionList = questionService.list();//现在返回的是带有user对象的questionlist
        //List<QuestionDTO> questionList1 = questionMapper.list();
        //questionMapper是针对question这张表的，并不去依赖user这张表
        //所以说他不能返回QuestionDTO(里面包含user对象)
        //所以这个时候就提炼出一个模型：service层

        /*这里修改一个QuestionDTO的描述，用于测试
        for (QuestionDTO questionDTO : questionList) {
            questionDTO.setDescription("resetsdfaa");
        }*/
        model.addAttribute("pagination",pagination);

        return "index";
    }
}
