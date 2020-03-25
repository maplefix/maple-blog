package top.maplefix.utils.file;

import com.qiniu.storage.Region;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author : Maple
 * @description : 七牛云操作工具类
 * @date : 2019/8/8 16:01
 * @version : v1.0
 */
@Slf4j
public class QiNiuUtils {
    private QiNiuUtils() {
    }

    private static final String HUAD = "华东";

    private static final String HUAB = "华北";

    private static final String HUAN = "华南";

    private static final String BEIM = "北美";

    /**
     * 得到机房的对应关系
     *
     * @param zone 机房名称
     * @return Region
     */
    public static Region getRegion(String zone) {
        if (HUAD.equals(zone)) {
            return Region.huadong();
        } else if (HUAB.equals(zone)) {
            return Region.huabei();
        } else if (HUAN.equals(zone)) {
            return Region.huanan();
        } else if (BEIM.equals(zone)) {
            return Region.beimei();
            // 否则就是东南亚
        } else {
            return Region.qvmHuadong();
        }
    }

    /**
     * 默认不指定key的情况下，以文件内容的hash值作为文件名
     *
     * @param file 文件名
     * @return String
     */
    public static String getKey(String file) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        return FileUtils.getFileNameNoExtension(file) + "-" +
                sdf.format(date) +
                "." +
                FileUtils.getExtensionName(file);
    }
}
