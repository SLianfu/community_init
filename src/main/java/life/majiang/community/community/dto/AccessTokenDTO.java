package life.majiang.community.community.dto;

import lombok.Data;

/**
 * 这个包应该是存放bean对象的
 */
@Data
public class AccessTokenDTO {

    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;


}
