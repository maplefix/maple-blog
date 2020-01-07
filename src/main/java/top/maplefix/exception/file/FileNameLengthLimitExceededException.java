package top.maplefix.exception.file;

/**
 * @author : Maple
 * @description : 文件名称超长限制异常
 * @date : Created in 2019/4/01 11:11
 * @version : v1.0
 */
public class FileNameLengthLimitExceededException extends FileException {
    private static final long serialVersionUID = 1L;

    public FileNameLengthLimitExceededException(int defaultFileNameLength) {
        super("upload.filename.exceed.length", new Object[]{defaultFileNameLength});
    }
}
