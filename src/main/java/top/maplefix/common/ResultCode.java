package top.maplefix.common;

import org.springframework.http.HttpStatus;

/**
 * @author : Maple
 * @description : 统一返回码
 * @date : 2020/1/16 17:11
 */
public class ResultCode{

    /**
     *  200
     */
    public static  final int OK = HttpStatus.OK.value();
    /**
     * 400
     */
    public static  final int BAD_REQUEST = HttpStatus.BAD_REQUEST.value();
    /**
     *  401
     */
    public static  final int UNAUTHORIZED = HttpStatus.UNAUTHORIZED.value();
    /**
     * 403
     */
    public static  final int FORBIDDEN = HttpStatus.FORBIDDEN.value();
    /**
     * 404
     */
    public static  final int NOT_FOUND = HttpStatus.NOT_FOUND.value();
    /**
     *  500
     */
    public static  final int INTERNAL_SERVER_ERROR = HttpStatus.INTERNAL_SERVER_ERROR.value();
}
