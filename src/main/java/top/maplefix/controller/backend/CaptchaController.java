package top.maplefix.controller.backend;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import top.maplefix.constant.Constant;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author : Maple
 * @description : 验证码接口
 * @date : Created in 2019/7/24 19:42
 * @version : v2.1
 */
@RestController
@RequestMapping("/api/admin/captcha")
@Slf4j
public class CaptchaController {

    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    @Value("${captcha.captchaType}")
    private String captchaType;

    /**
     * 验证码生成
     * @param request
     * @param response
     * @return
     */
    @GetMapping
    public ModelAndView getCaptchaImage(HttpServletRequest request, HttpServletResponse response) {
        log.info("开始请求二维码...");
        ServletOutputStream out = null;
        try {
            HttpSession session = request.getSession();
            response.setDateHeader("Expires", 0);
            response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            response.addHeader("Cache-Control", "post-check=0, pre-check=0");
            response.setHeader("Pragma", "no-cache");
            response.setContentType("image/jpeg");

            String capStr = null;
            String code = null;
            BufferedImage bi = null;
            if (Constant.math.equals(captchaType)) {
                String capText = captchaProducerMath.createText();
                capStr = capText.substring(0, capText.lastIndexOf("@"));
                code = capText.substring(capText.lastIndexOf("@") + 1);
                bi = captchaProducerMath.createImage(capStr);
            } else if (Constant.charC.equals(captchaType)) {
                capStr = code = captchaProducer.createText();
                bi = captchaProducer.createImage(capStr);
            }
            //将计算结果放入到session中
            session.setAttribute(Constants.KAPTCHA_SESSION_KEY, code);
            out = response.getOutputStream();
            ImageIO.write(bi, "jpg", out);
            out.flush();
            log.info("二维码生成成功...");
        } catch (Exception e) {
            log.error("二维码生成异常,异常信息:{},异常堆栈:{}",e.getMessage(),e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                log.error("二维码生成异常,异常信息:{},异常堆栈:{}",e.getMessage(),e);
            }
        }
        return null;
    }
}
