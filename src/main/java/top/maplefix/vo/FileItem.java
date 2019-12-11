package top.maplefix.vo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import top.maplefix.component.UuIdGenId;
import top.maplefix.utils.UuidUtils;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author : 16558
 * @description : 文件相关属性
 * @date : Created in 2019/8/8 16:55
 * @editor:
 * @version: v2.1
 */
@Data
@Table(name = "t_file_item")
@NameStyle
public class FileItem implements Serializable {

    @Id
    @KeySql(genId = UuIdGenId.class)
    private String fileId;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件的HASH值，使用hash值算法计算
     */
    private String fileHash;
    /**
     * 文件大小
     */
    private Long fileSize;
    /**
     * 上传时间
     */
    private String uploadDate;
    /**
     * 图床保存的文件的类型（1表示在本地存储，2表示存储在七牛云）
     */
    private String serverType;
    /**
     * 访问路径
     */
    private String filePath;

    public FileItem(){}

    public FileItem(String fileName, String fileHash, Long fileSize, String uploadDate, String  serverType, String filePath) {
        this.fileId = UuidUtils.getRandomUuidWithoutSeparator();
        this.fileName = fileName;
        this.fileHash = fileHash;
        this.fileSize = fileSize;
        this.uploadDate = uploadDate;
        this.serverType = serverType;
        this.filePath = filePath;
    }

    /**
     * 图床保存的文件的类型（1表示在本地存储，2表示存储在七牛云）
     */
    /*public enum ServerType {
        *//**
         * 本地存储
         *//*
        LOCAL(1),
        *//**
         * 七牛云存储
         *//*
        QI_NIU_YUN(2);

        private Integer serverType;

        ServerType(Integer serverType) {
            this.serverType = serverType;
        }

        public Integer getServerType() {
            return serverType;
        }
    }*/
}
