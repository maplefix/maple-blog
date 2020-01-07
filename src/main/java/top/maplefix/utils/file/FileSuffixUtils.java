package top.maplefix.utils.file;

/**
 * @author : Maple
 * @description : 文件后缀判断工具类
 * @date : Created in 2019/8/31 19:47
 * @version : v1.0
 */
public class FileSuffixUtils {

    /**
     * 判断该类型是否有效
     * @param contentType
     * @param allowTypes
     * @return
     */
    public static boolean isValid(String contentType, String... allowTypes) {
        if (null == contentType || "".equals(contentType)) {
            return false;
        }
        for (String type : allowTypes) {
            if (contentType.indexOf(type) > -1) {
                return true;
            }
        }
        return false;
    }
}
