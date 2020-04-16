package top.maplefix.controller.oms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.maplefix.common.BaseResult;
import top.maplefix.constant.Constant;
import top.maplefix.redis.RedisCacheService;
import top.maplefix.utils.Base64;
import top.maplefix.utils.UuidUtils;
import top.maplefix.utils.VerifyCodeUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author : Maple
 * @description : 验证码接口
 * @date : 2020/1/24 19:42
 */
@RestController
@RequestMapping("/captcha")
@Slf4j
public class CaptchaController {

    @Autowired
    private RedisCacheService redisCacheService;

    /**
     * 生成验证码
     */
    @GetMapping("/captchaImage")
    public BaseResult getCode(HttpServletResponse response) throws IOException {
        // 生成随机字串
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
        // 唯一标识
        String uuid = UuidUtils.getRandomUuidWithoutSeparator();
        String verifyKey = Constant.CAPTCHA_CODE_KEY + uuid;

        redisCacheService.setCacheObject(verifyKey, verifyCode, Constant.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        // 生成图片
        int w = 111;
        int h = 36;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        VerifyCodeUtils.outputImage(w, h, stream, verifyCode);
        try {
            BaseResult baseResult = BaseResult.success();
            baseResult.put("uuid", uuid);
            baseResult.put("img", Base64.encode(stream.toByteArray()));
            return baseResult;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return BaseResult.error(e.getMessage());
        } finally {
            stream.close();
        }
    }
}
