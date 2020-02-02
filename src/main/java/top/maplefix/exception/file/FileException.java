package top.maplefix.exception.file;


import top.maplefix.exception.BaseException;

/**
 * @author : Maple
 * @description : 文件信息异常
 * @date : 2019/4/01 11:01
 * @version : v1.0
 */
public class FileException extends BaseException {
    private static final long serialVersionUID = 1L;

    public FileException(String code, Object[] args) {
        super("file", code, args, null);
    }

}
