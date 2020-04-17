package top.maplefix.secrrity.handle;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import top.maplefix.common.BaseResult;
import top.maplefix.common.ResultCode;
import top.maplefix.utils.IpUtils;
import top.maplefix.utils.ServletUtils;
import top.maplefix.utils.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * @author Maple
 * @description 身份验证失败处理类未授权返回
 * @date 2020/1/21 20:42
 */
@Component
@Slf4j
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint, Serializable {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {

        log.error("Unauthorized access: \ncurrent IP:{}\nrequest URI :{}\n", IpUtils.getIpAddr(httpServletRequest), httpServletRequest.getRequestURI());
        String msg = StringUtils.format("Unauthorized access：{}，Authentication failed, unable to access system resources ", httpServletRequest.getRequestURI());
        ServletUtils.renderString(httpServletResponse, JSON.toJSONString(BaseResult.error(ResultCode.UNAUTHORIZED, msg)));
    }
}
