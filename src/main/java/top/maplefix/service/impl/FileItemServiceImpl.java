package top.maplefix.service.impl;

import com.github.pagehelper.PageHelper;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.model.FileListing;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;
import top.maplefix.config.SystemConfig;
import top.maplefix.constant.FileItemConstant;
import top.maplefix.constant.PageConstant;
import top.maplefix.mapper.FileItemMapper;
import top.maplefix.service.IFileItemService;
import top.maplefix.utils.DateUtils;
import top.maplefix.utils.StringUtils;
import top.maplefix.utils.file.FileUploadUtils;
import top.maplefix.utils.file.FileUtils;
import top.maplefix.utils.file.QiNiuUtils;
import top.maplefix.vo.FileItem;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author : Maple
 * @description :
 * @date : Created in 2019/8/22 22:33
 * @version : v1.0
 */
@Service
@Slf4j
public class FileItemServiceImpl implements IFileItemService {
    
    @Autowired
    FileItemMapper fileItemMapper;

    @Override
    @Transactional(rollbackFor = QiniuException.class)
    public int syncQiNiuYunImage() throws QiniuException {
        BucketManager bucketManager = QiNiuUtils.getBucketManager();
        FileListing fileListing = bucketManager.listFiles(QiNiuUtils.getBucket(), "", "", 10000, "");
        if (Objects.isNull(fileListing) || fileListing.items.length == 0) {
            return 0;
        }

        //将fileListing.item转换为FileItem类型
        List<FileItem> fileItemsQiNiuYunInfo = Arrays.stream(fileListing.items).map(
                item -> new FileItem(
                        item.key, item.hash, item.fsize,DateUtils.longToString(item.putTime,DateUtils.YYYY_MM_DD_HH_MM_SS),
                        FileItemConstant.QINIU_STORE, QiNiuUtils.getPathByName(item.key))).collect(Collectors.toList());

        //删除数据库现有的在七牛云上的记录
        FileItem fileItem = new FileItem();
        fileItem.setServerType(FileItemConstant.QINIU_STORE);
        fileItemMapper.delete(fileItem);
        //将新记录插入
        int count = 0;
        for (FileItem FileItem : fileItemsQiNiuYunInfo) {
            count += fileItemMapper.insertSelective(FileItem);
        }
        return count;
    }

    @Override
    public int deleteQiNiuYunImageFile(String name) throws QiniuException {
        BucketManager bucketManager = QiNiuUtils.getBucketManager();
        Response response = bucketManager.delete(QiNiuUtils.getBucket(), name);
        log.info("删除七牛云文件{}", name);
        FileItem fileItem = new FileItem();
        fileItem.setFileName(name);
        fileItem.setServerType(FileItemConstant.QINIU_STORE);
        return response.isOK() ? fileItemMapper.delete(fileItem) : -1;
    }

    @Override
    public String insertQiNiuYunImageFile(MultipartFile file) {
        Optional<FileItem> optionalS = QiNiuUtils.uploadFile(file);
        if (optionalS.isPresent()) {
            log.info("上传七牛云图片{}成功", file.toString());
            fileItemMapper.insertSelective(optionalS.get());
            return optionalS.get().getFilePath();
        }
        return "";
    }

    @Override
    public List<FileItem> selectFileItemImageList(Map<String, Object> params) {
        int currPage = top.maplefix.utils.StringUtils.getObjInt(params.get(PageConstant.PAGENUM));
        int pageSize = top.maplefix.utils.StringUtils.getObjInt(params.get(PageConstant.PAGESIZE));
        String keyword = top.maplefix.utils.StringUtils.getObjStr(params.get("keyword"));
        String serverType = top.maplefix.utils.StringUtils.getObjStr(params.get("serverType"));
        Example example = new Example(FileItem.class);
        example.setOrderByClause("uploadDate desc");
        Example.Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(keyword)){
            criteria.andEqualTo("fileName", keyword);
        }
        if(!StringUtils.isEmpty(serverType)){
            criteria.andEqualTo("serverType", serverType);
        }
        PageHelper.startPage(currPage, pageSize);
        return fileItemMapper.selectByExample(example);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String insertLocalImageFile(MultipartFile file) throws IOException {
        FileItem fileItem = FileUploadUtils.upload(SystemConfig.getImagePath(), file);
        fileItemMapper.insertSelective(fileItem);
        return fileItem.getFilePath();
    }

    @Override
    public int syncLocalImage() {
        List<FileItem> imageFileItemList = FileUtils.getImageFileItemList();
        FileItem fileItem = new FileItem();
        fileItem.setServerType(FileItemConstant.LOCAL_STORE);
        int i = fileItemMapper.delete(fileItem);
        log.info("删除文件{}个", i);
        int count = 0;
        for (FileItem FileItem : imageFileItemList) {
            count += fileItemMapper.insertSelective(FileItem);
        }
        return count;
    }

    @Override
    public int deleteLocalImageFile(String name) {
        if (FileUtils.deleteImageFile(name)) {
            FileItem fileItem = new FileItem();
            fileItem.setServerType(FileItemConstant.LOCAL_STORE);
            return fileItemMapper.delete(fileItem);
        }
        return 0;
    }

    @Override
    public List<FileItem> selectFileItemByIds(String[] ids) {
        //如果前端没选中列表数据则全部导出
        if(null == ids || ids.length == 0){
            Example example = new Example(FileItem.class);
            example.setOrderByClause("uploadDate desc");
            return fileItemMapper.selectByExample(example);
        }
        //将数组转成字符串，用逗号隔开
        String idsStr = StringUtils.join(ids,",");
        return fileItemMapper.selectByIds(idsStr);
    }
}
