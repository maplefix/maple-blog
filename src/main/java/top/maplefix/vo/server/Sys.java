package top.maplefix.vo.server;

import lombok.Data;

/**
 * @author : Maple
 * @description : 系统相关信息
 * @date : Created in 2019/9/15 16:16
 * @editor:
 * @version: v2.1
 */
@Data
public class Sys {
    /**
     * 服务器名称
     */
    private String computerName;

    /**
     * 服务器Ip
     */
    private String computerIp;

    /**
     * 项目路径
     */
    private String userDir;

    /**
     * 操作系统
     */
    private String osName;

    /**
     * 系统架构
     */
    private String osArch;
}
