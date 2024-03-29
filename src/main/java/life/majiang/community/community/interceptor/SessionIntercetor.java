package life.majiang.community.community.interceptor;

import life.majiang.community.community.mapper.UserMapper;
import life.majiang.community.community.model.User;
import life.majiang.community.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class SessionIntercetor implements HandlerInterceptor {

    @Autowired
    private UserMapper userMapper;



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {//如果cookies为空，那user也为空
            //这里用数据库检验的方式，成本高（小用户量还行）【以后可以用redis方式去做】
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    UserExample userExample = new UserExample();
                    userExample.createCriteria()
                            .andTokenEqualTo(token);

                    List<User> users = userMapper.selectByExample(userExample);
                    //User user = userMapper.findByToken(token);//token怎么回去呢？传入httpservletresponse
                    if (users.size() != 0) {  //验证前端的工作情况
                        request.getSession().setAttribute("user", users.get(0));
                        User user = (User) request.getSession().getAttribute("user");
                        //前端就可以通过会话级的（页面级）数据去判断登录
                    }
                    break;
                }

            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
