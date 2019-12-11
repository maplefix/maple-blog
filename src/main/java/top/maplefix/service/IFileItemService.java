package top.maplefix.service;

import com.qiniu.common.QiniuException;
import org.springframework.web.multipart.MultipartFile;
import top.maplefix.vo.FileItem;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author : Maple
 * @description : 文件操作service接口
 * @date : Created in 2019/8/22 22:31
 * @editor:
 * @version: v2.1
 */
public interface IFileItemService {
    /**
     * 刷新七牛云数据到数据库保存
     * @return 报错到数据库的条数
     * @throws QiniuException
     */
    int syncQiNiuYunImage() throws QiniuException;

    /**
     * 删除七牛云图片
     *
     * @param name
     * @throws QiniuException
     * @return 受影响的行数
     */
    int deleteQiNiuYunImageFile(String name) throws QiniuException;

    /**
     * 上传文件到七牛云
     *
     * @param file 需要上传的文件
     *             @throws QiniuException
     * @return 文件路径
     */
    String insertQiNiuYunImageFile(MultipartFile file);

    /**
     * 查询列表
     * @param fileItemVo
     * @return
     */
    List<FileItem> selectFileItemImageList(Map<String, Object> params);

    /**
     * 上传文件到本地
     *
     * @param file 需要上传的文件
     * @throws IOException
     * @return 文件路径
     */
    String insertLocalImageFile(MultipartFile file) throws IOException;

    /**
     * 刷新本地图片到数据库
     *
     * @return 受影响的行数
     */
    int syncLocalImage();

    /**
     * 删除本地图片
     *
     * @param name 图片名称
     * @return 受影响的行数
     */
    int deleteLocalImageFile(String name);
}
