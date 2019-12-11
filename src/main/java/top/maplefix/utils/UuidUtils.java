package top.maplefix.utils;

import java.util.UUID;

/**
 * @author : Maple
 * @description : uuid生成工具类
 * @date : Created in 2019/7/24 14:05
 * @editor:
 * @version: v2.1
 */
public class UuidUtils {

    /**
     * 获取随机的UUID字符串
     * @param
     * @return uuid
     * @throws
     */
    private static String getRandomUuid() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().toLowerCase();
    }

    /**
     * 从UUID字符串中去除“-”分隔符
     * @param uuid
     * @return 不含"-"的uuid
     * @throws
     */
    private static String removeSeparatorFromUuid(String uuid) {
        uuid = uuid.replaceAll("-","");
        return uuid;
    }

    /**
     * 获取不包含“-”分隔符的uuid字符串
     * @param
     * @return 不含"-"的uuid
     * @throws
     */
    public static String getRandomUuidWithoutSeparator() {
        String uuid = getRandomUuid();
        return removeSeparatorFromUuid(uuid);
    }

}
