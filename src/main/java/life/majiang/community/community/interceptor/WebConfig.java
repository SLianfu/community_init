package life.majiang.community.community.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//@EnableWebMvc
class WebConfig implements WebMvcConfigurer {

    @Autowired
    private SessionIntercetor sessionIntercetor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //addPathPatterns:把哪些地址拦截，excludePathPatterns：把哪些地址略过
        registry.addInterceptor(sessionIntercetor).addPathPatterns("/**");
        // registry.addInterceptor(new SecurityInterceptor()).addPathPatterns("/secure/*");
    }
}