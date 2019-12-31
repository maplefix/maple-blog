package top.maplefix.controller.backend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.maplefix.config.SystemConfig;
import top.maplefix.constant.Constant;
import top.maplefix.utils.FileUtil;
import top.maplefix.utils.StringUtils;
import top.maplefix.utils.file.FileUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author wangjg
 * @description 通用控制类，处理文件下载
 * @date 2019/12/25 14:46
 */
@RestController
@RequestMapping("/api/admin")
@Slf4j
public class CommonController {

    /**
     * 通用下载接口
     * @param fileName 文件名称
     */
    @GetMapping("/common/download")
    public void fileDownload(String fileName, String deleteFlag, HttpServletResponse response, HttpServletRequest request) {
        try {
            String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
            String filePath = SystemConfig.getProfile() + fileName;

            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition",
                    "attachment;fileName=" + setFileDownloadHeader(request, realFileName));
            FileUtils.writeBytes(filePath, response.getOutputStream());
            //如需删除文件，则在下载操作完成后删除源文件
            if(StringUtils.isNotEmpty(deleteFlag) && Constant.TRUE.equals(deleteFlag)){
                //删除源文件
                FileUtil.deleteFile(filePath);
            }
        } catch (Exception e) {
            log.error("下载文件失败,异常原因为:{},异常堆栈为：{}", e.getMessage(),e);
        }
    }

    /**
     * 设置文件下载头
     * @param request 源请求
     * @param fileName 文件名
     * @return 文件名
     * @throws UnsupportedEncodingException 编码不支持异常
     */
    public String setFileDownloadHeader(HttpServletRequest request, String fileName) throws UnsupportedEncodingException {
        final String agent = request.getHeader("USER-AGENT");
        String filename = fileName;
        if (agent.contains(Constant.MSIE)) {
            // IE浏览器
            filename = URLEncoder.encode(filename, "utf-8");
            filename = filename.replace("+", " ");
        } else if (agent.contains(Constant.FIREFOX)) {
            // 火狐浏览器
            filename = new String(fileName.getBytes(), "ISO8859-1");
        } else if (agent.contains(Constant.CHROME)) {
            // google浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        } else {
            // 其它浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        }
        return filename;
    }
}
