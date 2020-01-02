package top.maplefix.utils;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author : Maple
 * @description : 文件处理工具类
 * @date : Created in 2019/3/31 21:25
 * @version : v2.1
 */
@Slf4j
public class FileUtil {

    public static String FILENAME_PATTERN = "[a-zA-Z0-9_\\-\\|\\.\\u4e00-\\u9fa5]+";

    /**
     * 输出指定文件的byte数组
     *
     * @param filePath 文件路径
     * @param os       输出流
     * @return
     */
    public static void writeBytes(String filePath, OutputStream os) throws IOException {
        FileInputStream fis = null;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                throw new FileNotFoundException("要下载的文件文件不存在["+filePath+"]");
            }
            fis = new FileInputStream(file);
            byte[] b = new byte[1024];
            int length;
            while ((length = fis.read(b)) > 0) {
                os.write(b, 0, length);
            }
        } catch (IOException e) {
            throw e;
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
    /**
     * 下载文件
     * @param path 文件全路径
     * @param response
     * @return
     */
    public static void downloadFile(HttpServletResponse response, String path) {

        String fileName = path.substring(path.lastIndexOf(File.separator)+1);
        try {
            fileName = new String(fileName.getBytes("ISO-8859-1"), "utf-8");
            fileName = new String(fileName.getBytes("GBK"), "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setCharacterEncoding("utf-8");
        //response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);


        response.setHeader("Connection", "close");
        //设置传输的类型
        response.setHeader("Content-Type", "application/octet-stream");
        response.setHeader("Content-Transfer-Encoding", "chunked");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/OCTET-STREAM");

        InputStream inputStream = null;
        OutputStream os = null;
        try {
            inputStream = new FileInputStream(new File(path));
            response.setHeader("Content-Length","" + inputStream.available());
            os = response.getOutputStream();
            byte[] b = new byte[2048];
            int length;
            while ((length = inputStream.read(b)) > 0) {
                os.write(b, 0, length);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("要下载的文件文件不存在["+path+"]");
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            try {
                if(os != null) {
                    os.close();
                }
                if(inputStream != null) {
                    inputStream.close();
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 删除文件
     *
     * @param filePath
     * @return
     */
    public static boolean deleteFile(String filePath) {
        if (StringUtils.isEmpty(filePath)) {
            return true;
        }
        File curFile = new File(filePath);
        if (!curFile.isFile()) {
            return true;
        }
        log.info("=======清除文件======" + filePath);
        int i = 0;
        for (; i < 20 && !curFile.delete(); i++) {
            try {
                System.gc();
                Thread.sleep(10);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
        }
        if (i < 20) {
            log.info("成功删除文件：" + filePath);
        }
        return i < 20;
    }


    /**
     * 文件名称验证
     *
     * @param filename 文件名称
     * @return true 正常 false 非法
     */
    public static boolean isValidFilename(String filename) {
        return filename.matches(FILENAME_PATTERN);
    }
}
