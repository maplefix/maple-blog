package top.maplefix.common;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.apache.poi.ss.formula.functions.T;
import top.maplefix.constant.Constant;
import top.maplefix.enums.ResultCode;
import top.maplefix.utils.PageData;

/**
 * @author : Maple
 * @description : 通用数据返回对象
 * @date : Created in 2019/7/24 23:13
 * @version : v2.1
 */
@Data
public class BaseResult {
    /**
     * 返回应答码 成功："success"，失败："fail"
     */
    private Integer code;
    /**
     * 返回应答信息
     */
    private String msg;
    /**
     * 返回数据域
     */
    private Object data;


    public void setData(Object data){
        this.data = data;
    }

    /**
     * jqGrid分页封装
     * @param pageInfo
     */
    public void setData(com.github.pagehelper.PageInfo pageInfo){
        PageData data = new PageData(pageInfo);
        JSONObject meta = new JSONObject();
        meta.put("currPage",data.getCurrPage());
        meta.put("pageSize",data.getPageSize());
        meta.put("totalPage",data.getTotalPage());
        meta.put("totalCount",data.getTotalCount());
        meta.put("list",data.getList());
        this.data = meta;
    }

    /**
     * bootstrap表格分页封装
     * @param pageInfo
     * @param flag
     */
    public void setData(com.github.pagehelper.PageInfo pageInfo,boolean flag){
        PageData data = new PageData(pageInfo);
        JSONObject meta = new JSONObject();
        meta.put("currPage",data.getCurrPage());
        meta.put("pageSize",data.getPageSize());
        meta.put("totalPage",data.getTotalPage());
        meta.put("total",data.getTotalCount());
        meta.put("rows",data.getList());
        this.data = meta;    }

    public BaseResult(){
        this.code = ResultCode.SUCCESS_CODE.getCode();
        this.msg = Constant.SUCCESS_MSG;
    }
    public BaseResult(T data){
        this.code = ResultCode.SUCCESS_CODE.getCode();
        this.msg = Constant.SUCCESS_MSG;
        this.data = data;
    }
    public BaseResult(String msg){
        this.code = ResultCode.SUCCESS_CODE.getCode();
        this.msg = msg;
    }

    /**
     * 根据错误码返回错误包装信息
     * @param code
     * @return
     */
    public static BaseResult failResult(int code){
        BaseResult baseResult = new BaseResult();
        baseResult.setCode(code);
        baseResult.setMsg(getMsgByErrorCode(code));
        return baseResult;
    }
    public static BaseResult failResult(int code, String msg){
        BaseResult baseResult = new BaseResult();
        baseResult.setCode(code);
        baseResult.setMsg(msg);
        return baseResult;
    }


    /**
     * 根据错误码获取错误信息
     * @param code
     * @return
     */
    public static String getMsgByErrorCode(int code){
        String msg;
        switch (code){
            case 00:
                msg = "请求成功";
                break;
            case 01:
                msg = "参数缺失";
                break;
            case 03:
                msg = "请求超时";
                break;
            case 04:
                msg = "系统内部错误";
                break;
            default:
                msg = "未知异常";
        }
        return msg;
    }
}
