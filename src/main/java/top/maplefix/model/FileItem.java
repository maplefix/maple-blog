package top.maplefix.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;
import top.maplefix.annotation.Excel;
import top.maplefix.component.UuIdGenId;
import top.maplefix.utils.UuidUtils;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author : Maple
 * @description : 文件相关属性
 * @date : Created in 2020/1/15 14:47
 */
@Data
@Table(name = "t_file_item")
public class FileItem implements Serializable {

    @Id
    @KeySql(genId = UuIdGenId.class)
    @Excel(name = "编号")
    private String fileId;
    /**
     * 文件名
     */
    @Excel(name = "文件名")
    private String fileName;
    /**
     * 文件的HASH值，使用hash值算法计算
     */
    @Excel(name = "文件hash")
    private String fileHash;
    /**
     * 文件大小
     */
    @Excel(name = "文件大小")
    private Long fileSize;
    /**
     * 上传时间
     */
    @Excel(name = "上传日期")
    private String uploadDate;
    /**
     * 图床保存的文件的类型（1表示在本地存储，2表示存储在七牛云）
     */
    @Excel(name = "保存类型",readConverterExp = "1=本地存储,0=七牛云存储")
    private Integer serverType;
    /**
     * 访问路径
     */
    @Excel(name = "文件地址")
    private String filePath;

    public FileItem(){}

    public FileItem(String fileName, String fileHash, Long fileSize, String uploadDate, Integer serverType, String filePath) {
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
