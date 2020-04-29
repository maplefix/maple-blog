package top.maplefix.service;

import org.springframework.web.multipart.MultipartFile;
import top.maplefix.vo.QiNiuConfig;
import top.maplefix.vo.QiNiuContent;

import java.util.List;

/**
 * @author Maple
 * @description 七牛云存储service
 * @date 2020/3/20 11:10
 */
public interface QiNiuService {

    /**
     * 获取QiNiu的配置信息
     *
     * @return 配置文件
     */
    QiNiuConfig getQiNiuConfig();

    /**
     * 更新七牛云配置文件
     *
     * @param qiNiuConfig 七牛云配置文件
     * @return 受影响的行数
     */
    int updateQiNiuConfig(QiNiuConfig qiNiuConfig);

    /**
     * 上传文件到七牛云
     *
     * @param file 要上传的文件
     * @return 七牛云信息
     */
    QiNiuContent upload(MultipartFile file);

    /**
     * 刷新七牛云数据
     */
    int synchronize();

    /**
     * 删除文件
     *
     * @param id 文件的Id
     * @return 受影响的行数
     */
    int deleteQiNiuContent(String id);

    /**
     * 获取下载文件的Url地址
     *
     * @param id 文件id
     * @return 下载地址
     */
    String getDownloadUrl(String id);

    /**
     * 获取七牛云文件集合
     *
     * @param qiNiuContent 查询条件
     * @return list
     */
    List<QiNiuContent> selectContentList(QiNiuContent qiNiuContent);
}
