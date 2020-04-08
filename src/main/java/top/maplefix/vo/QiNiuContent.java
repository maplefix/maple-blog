package top.maplefix.vo;

import lombok.Data;
import top.maplefix.model.BaseEntity;

import java.io.Serializable;

/**
 * @author Maple
 * @description 七牛云存储文件内容
 * @date 2020/3/18 17:23
 */
@Data
public class QiNiuContent extends BaseEntity implements Serializable {

    private String id;
    /**
     * 文件名
     */
    private String name;
    /**
     * 空间名
     */
    private String bucket;
    /**
     * 大小,eg:5kb
     */
    private String size;
    /**
     * 访问地址
     */
    private String url;
    /**
     * 文件后缀
     */
    private String suffix;
    /**
     * 空间类型
     */
    private String type = "公开";
}
