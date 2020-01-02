package top.maplefix.utils.file;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import top.maplefix.config.ServerConfig;
import top.maplefix.config.SystemConfig;
import top.maplefix.constant.FileItemConstant;
import top.maplefix.exception.file.FileNameLengthLimitExceededException;
import top.maplefix.utils.DateUtils;
import top.maplefix.utils.SpringUtils;
import top.maplefix.vo.FileItem;

import java.io.File;
import java.io.IOException;

/**
 * @author : Maple
 * @description : 文件上传工具类
 * @date : Created in 2019/3/31 21:23
 * @version : v2.1
 */
@Slf4j
public class FileUploadUtils {
    /**
     * 默认大小 50M
     */
    public static final long DEFAULT_MAX_SIZE = 50 * 1024 * 1024;

    /**
     * 默认的文件名最大长度 100
     */
    public static final int DEFAULT_FILE_NAME_LENGTH = 100;

    /**
     * 默认上传的地址
     */
    private static String defaultBaseDir = SystemConfig.getProfile();

    /**
     * 系统配置：用于获取系统的绝对访问逻辑
     */
    private static ServerConfig serverConfig = SpringUtils.getBean(ServerConfig.class);

    private static int counter = 0;

    public static void setDefaultBaseDir(String defaultBaseDir) {
        FileUploadUtils.defaultBaseDir = defaultBaseDir;
    }

    public static String getDefaultBaseDir() {
        return defaultBaseDir;
    }

    /**
     * 以默认配置进行文件上传
     *
     * @param file 上传的文件
     * @return 文件
     * @throws Exception
     */
    public static final FileItem upload(MultipartFile file) throws IOException {
        try {
            return upload(getDefaultBaseDir(), file);
        } catch (Exception e) {
            throw new IOException(e.getMessage(), e);
        }
    }


    /**
     * 文件上传
     *
     * @param baseDir 相对应用的基目录
     * @param file    上传的文件
     * @return 返回上传成功的文件
     * @throws FileNameLengthLimitExceededException 文件名太长
     * @throws IOException                          比如读写文件出错时
     */
    public static final FileItem upload(String baseDir, MultipartFile file)
            throws IOException, FileNameLengthLimitExceededException {

        int fileNameLength = file.getOriginalFilename().length();
        if (fileNameLength > FileUploadUtils.DEFAULT_FILE_NAME_LENGTH) {
            throw new FileNameLengthLimitExceededException(FileUploadUtils.DEFAULT_FILE_NAME_LENGTH);
        }

        String fileName = FileUtils.generateFileName(file);

        File desc = getAbsoluteFile(baseDir, baseDir + fileName);
        file.transferTo(desc);

        FileItem FileItem = new FileItem(fileName, String.valueOf(file.hashCode()), file.getSize(), DateUtils.getCurrDate(), FileItemConstant.LOCAL_STORE,baseDir + "/" + fileName);

        return FileItem;
    }


    private static final File getAbsoluteFile(String uploadDir, String filename) throws IOException {
        File desc = new File(File.separator + filename);

        if (!desc.getParentFile().exists()) {
            desc.getParentFile().mkdirs();
        }
        if (!desc.exists()) {
            desc.createNewFile();
        }
        return desc;
    }



}
