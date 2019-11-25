package life.majiang.community.community.controller;

import life.majiang.community.community.dto.AccessTokenDTO;
import life.majiang.community.community.dto.GithubUser;
import life.majiang.community.community.mapper.UserMapper;
import life.majiang.community.community.model.User;
import life.majiang.community.community.provoder.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 *点击登录；先访问：https://github.com/login/oauth/authorize?client_id=2e11ca1282f427624e3e&redirect_uri=http://localhost:8887/callback&scope=user&state=1（得到code）
 * ->访问：http://localhost:8887/callback?code=7846229aeaf950fa4611&state=1
 * 路径上得到code，（每次code应该都不一样）：变成访问/callback
 * 【AccessTokenDTO对象：是访问令牌对象？】
 */
@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private  String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                            HttpServletRequest request,
                            HttpServletResponse response){//自动在形参里面注入response
                            //把HttpServletRequest作为参数传入到方法中，都快忘了HttpServletRequest这些了，springboot可以干嘛干嘛的
        //Response{protocol=http/1.1, code=200, message=OK, url=https://github.com/login/oauth/access_token}

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        //设置accessTokenDTO
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        System.out.println("code:"+code);
        accessTokenDTO.setCode(code);//code:fc08f9c69393bd14fa76
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);

        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
//        System.out.println(user.getName()+" "+user.getId());没用的打印
        if(githubUser!=null && githubUser.getId() != null){
            User user = new User();
            user.setAccountId(String.valueOf(githubUser.getId()));//Long 转成String类型:还支持其他平台所以用String
            String token = UUID.randomUUID().toString();
            user.setToken(token);//用来唯一标识cookie
            user.setName(githubUser.getName());
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setAvatarUrl(githubUser.getAvatarUrl());

            userMapper.insert(user);//把user插入数据库
            //以token作为依据（令牌）来绑定前端和后端登陆的依据,用它代替session
            //写入cookie（在response里面）即把令牌放入cookie，再次登录时，检查cookie里面的“token”
            //token每次随机生成，要怎么判断数据库里的token与重复登录者呢（每次登陆都录取用户信息）
            response.addCookie(new Cookie("token",token));


            //登录成功，写cookie 和session【服务器端是银行，和session是银行账户，cookie是银行卡】
            //request.getSession().setAttribute("user",githubUser);//注册账户
            return "redirect:/";//"redirect:index";//这样是重定向到/index
        } else{
            //登录失败
            return "redirect:/";

        }
    }
}
