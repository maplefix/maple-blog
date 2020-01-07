package top.maplefix.exception.file;

/**
 * @author : Maple
 * @description : 文件名大小限制异常
 * @date : Created in 2019/4/01 11:01
 * @version : v1.0
 */
public class FileSizeLimitExceededException extends FileException {
    private static final long serialVersionUID = 1L;

    public FileSizeLimitExceededException(long defaultMaxSize) {
        super("upload.exceed.maxSize", new Object[]{defaultMaxSize});
    }
}
