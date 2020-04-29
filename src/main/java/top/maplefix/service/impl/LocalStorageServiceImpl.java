package top.maplefix.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.maplefix.config.MapleBlogConfig;
import top.maplefix.exception.CustomException;
import top.maplefix.mapper.LocalStorageMapper;
import top.maplefix.model.LocalStorage;
import top.maplefix.service.LocalStorageService;
import top.maplefix.utils.DateUtils;
import top.maplefix.utils.StringUtils;
import top.maplefix.utils.file.FileUtils;

import java.io.File;
import java.util.List;
import java.util.Objects;

/**
 * @author Maple
 * @description 本地存储service实现类
 * @date 2020/3/20 10:59
 */
@Service
public class LocalStorageServiceImpl implements LocalStorageService {

    @Autowired
    private LocalStorageMapper localStorageMapper;

    @Override
    public List<LocalStorage> selectLocalStorageList(LocalStorage localStorage) {
        return localStorageMapper.selectLocalStorageList(localStorage);
    }

    @Override
    public int upload(String name, MultipartFile multipartFile) {
        //检查大小
        //获取后缀
        String suffix = FileUtils.getExtensionName(multipartFile.getOriginalFilename());
        String type = FileUtils.getFileType(suffix);
        File file = FileUtils.upload(multipartFile, MapleBlogConfig.getProfile() + type + File.separator);
        if (Objects.isNull(file)) {
            throw new CustomException("上传失败");
        }
        //防止异常出错
        try {
            name = StringUtils.isBlank(name) ? FileUtils.getFileNameNoExtension(multipartFile.getOriginalFilename()) : name;
            LocalStorage localStorage = new LocalStorage(file.getName(), name, suffix, file.getPath(), type, FileUtils.getSizeString(multipartFile.getSize()));
            localStorage.setCreateDate(DateUtils.getTime());
            return localStorageMapper.insertLocalStorage(localStorage);
        } catch (Exception e) {
            FileUtils.del(file);
            throw e;
        }
    }

    @Override
    public int updateLocalStorage(LocalStorage localStorage) {
        localStorage.setUpdateDate(DateUtils.getTime());
        return localStorageMapper.updateLocalStorage(localStorage);
    }

    @Override
    public int deleteLocalStorage(String  id) {
        LocalStorage localStorage = localStorageMapper.selectLocalStorageById(id);
        if (Objects.isNull(localStorage)) {
            throw new CustomException("文件不存在");
        }
        //删除文件
        String path = localStorage.getPath();
        FileUtils.del(path);
        return localStorageMapper.deleteLocalStorageById(id);
    }
}
