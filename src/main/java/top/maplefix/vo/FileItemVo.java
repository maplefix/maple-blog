package top.maplefix.vo;

import lombok.Data;
import top.maplefix.model.FileItem;

/**
 * @author : Maple
 * @description : 文件表实体vo
 * @date : 2020/1/15 16:28
 */
@Data
public class FileItemVo extends FileItem {
    /**
     * 搜索关键字
     */
    private String search;
    /**
     * 分页信息-当前页数
     */
    private Integer currPage;
    /**
     * 分页信息-每页大小
     */
    private Integer pageSize;
    /**
     * 开始时间
     */
    private String beginDate;
    /**
     * 结束时间
     */
    private String endDate;

    public FileItemVo(String fileName, String fileHash, Long fileSize, String uploadDate, Integer serverType, String filePath) {
        super(fileName, fileHash, fileSize, uploadDate, serverType, filePath);
    }
}
