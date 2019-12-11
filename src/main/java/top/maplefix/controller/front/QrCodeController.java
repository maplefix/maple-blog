package top.maplefix.controller.front;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.maplefix.config.SystemConfig;
import top.maplefix.constant.Constant;
import top.maplefix.utils.QrCodeUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

/**
 * @author : Maple
 * @description : 前端文章链接二维码生成接口
 *  可以返回BufferedImage或者base64
 * @date : Created in 2019/11/15 14:16
 * @editor:
 * @version: v2.1
 */
@Controller
@Slf4j
public class QrCodeController {

    @Autowired
    private SystemConfig systemConfig;

    /**根据内容生成带logo二维码
     * 返回BufferedImage
     */
    @RequestMapping(value = "/createBufferedImage")
    @ResponseBody
    public void createBufferedImage (HttpServletResponse response, String content,String needLogo){
        log.info("开始访问二维码生成(BufferedImage)...");
        try {
            String projectName = systemConfig.getName();
            String logoPath = System.getProperty("user.dir") + "/" + projectName + "/src/main/resources/static/blog/amaze/images/maple-logo.png";
            System.out.println(logoPath);
            BufferedImage bufferedImage;
            if(needLogo.equals(Constant.TRUE)){
                bufferedImage = QrCodeUtils.encode(content,logoPath,true);
            }else {
                bufferedImage = QrCodeUtils.encode(content,null,true);
            }

            ImageIO.write(bufferedImage, "png", response.getOutputStream());
            response.setContentType("image/png");
            response.flushBuffer();
            log.info("二维码生成(BufferedImage)成功...");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("二维码生成(BufferedImage)异常，异常信息:{},异常堆栈：{}",e.getMessage(),e);
        }
    }

    /**
     * 返回带有前缀的Base64编码内容
     * @param content 二维码内容
     * @param needLogo 二维码是否带logo
     * @return base64码
     * @throws Exception io异常
     */
    @RequestMapping(value = "/createBase64String")
    @ResponseBody
    public String createBase64String(String content,String needLogo) throws Exception {
        log.info("开始访问二维码生成(base64)...");
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            String logoPath = System.getProperty("user.dir") + "/" + systemConfig.getName() + "/src/main/resources/static/blog/amaze/images/maple-logo.png";
            BufferedImage bufferedImage;
            if(needLogo.equals(Constant.TRUE)){
                bufferedImage = QrCodeUtils.encode(content,logoPath,true);
            }else {
                bufferedImage = QrCodeUtils.encode(content,null,true);
            }
            ImageIO.write(bufferedImage, "png", os);
            //原生转码前面没有 data:image/png;base64 这些信息
            String base64 = "data:image/png;base64," + Base64.encode(os.toByteArray());
            log.info("二维码生成(base64)成功...");
            return base64;
        }catch (Exception e){
            log.error("二维码生成(base64)异常,异常信息：{},异常堆栈:{}",e.getMessage(),e);
            e.printStackTrace();
        }finally {
            if(null != os){
                os.flush();
                os.close();
            }
        }
        return null;
    }
}
