package top.maplefix.kisso;

import com.baomidou.kisso.security.token.SSOToken;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.jsonwebtoken.JwtBuilder;
import lombok.Data;
import top.maplefix.secrrity.LoginUser;

import java.io.Serializable;

/**
 * @author Maple
 * @description kisso的token类,继承SSOToken,加入OnlineUser
 * @date 2020/1/16 10:28
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MapleBlogSsoToken extends SSOToken implements Serializable {

    /**
     * 在线用户
     */
    private LoginUser loginUser;


    public MapleBlogSsoToken(JwtBuilder jwtBuilder){
        super(jwtBuilder);
    }

}
