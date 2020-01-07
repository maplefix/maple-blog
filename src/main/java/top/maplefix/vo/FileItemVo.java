package top.maplefix.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author : Maple
 * @description :
 * @date : Created in 2019/8/22 22:55
 * @version : v1.0
 */
@Data
@EqualsAndHashCode
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

    public FileItemVo(String fileName, String fileHash, Long fileSize, String uploadDate, String serverType, String filePath) {
        super(fileName, fileHash, fileSize, uploadDate, serverType, filePath);
    }
}
