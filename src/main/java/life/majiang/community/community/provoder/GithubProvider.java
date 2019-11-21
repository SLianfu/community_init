package life.majiang.community.community.provoder;

import com.alibaba.fastjson.JSON;
import life.majiang.community.community.dto.AccessTokenDTO;
import life.majiang.community.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {

    public  String  getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token")
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                //返回access_token
                String string = response.body().string();
                String token = string.split("&")[0].split("=")[1];

                /*String[] split = string.split("&");
                String tokenstr = split[0];
                String token = tokenstr.split("=")[1];*/
                System.out.println("查看string："+string+" 查看token："+token);
                return token;
            } catch (IOException e) {
            }
        return null;
    }

    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+ accessToken)
                .build();
       /* 怎么会这样？？try (Response response = client.newCall(request).execute()) {
        }*/
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);//可以把String 直接转换成java类对象
            return githubUser;
        } catch (IOException e) {
        }
        return null;
    }


}



