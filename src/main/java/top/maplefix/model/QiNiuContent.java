package top.maplefix.model;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author Maple
 * @description 七牛云文件存储
 * @date 2020/3/20 13:44
 */
@Data
public class QiNiuContent extends BaseEntity implements Serializable {
    @Id
    private String fileId;
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

