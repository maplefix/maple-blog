package top.maplefix.controller.backend;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.qiniu.common.QiniuException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import top.maplefix.annotation.OLog;
import top.maplefix.common.BaseResult;
import top.maplefix.constant.Constant;
import top.maplefix.constant.FileItemConstant;
import top.maplefix.controller.BaseController;
import top.maplefix.enums.OperationType;
import top.maplefix.enums.ResultCode;
import top.maplefix.service.IFileItemService;
import top.maplefix.utils.StringUtils;
import top.maplefix.vo.FileItem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author : Maple
 * @description : 文件相关操作接口
 * @date : Created in 2019/8/18 9:47
 * @editor:
 * @version: v2.1
 */
@RestController
@RequestMapping("/api/admin/file")
@Slf4j
public class FileItemController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(FileItemController.class);

    @Autowired
    private IFileItemService fileItemService;

    /**
     * 文件上传接口，可以根据serverType上传文件到七牛云或者服务器本地。
     * @param request
     * @param response
     * @param serverType
     * @return
     */
    @PostMapping(value = "/upload",produces= MediaType.APPLICATION_JSON_VALUE +";charset=utf-8")
    @OLog(module = "文件管理", businessType = OperationType.UPLOAD)
    public JSONObject  uploadFile(HttpServletRequest request, HttpServletResponse response,
                                  @RequestParam(value = "serverType", required = false) String serverType){
        log.info("文件上次操作开始...");
        JSONObject result = new JSONObject();
        if(StringUtils.isEmpty(serverType)){
            serverType = FileItemConstant.QINIU_STORE;
        }
        //多部分httpRquest对象MultipartHttpServletRequest是HttpServletRequest类的一个子类接口,支持文件分段上传对象
        MultipartHttpServletRequest mtRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = mtRequest.getFile("file");
        try {
            String fileName = file.getOriginalFilename();
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            Objects.requireNonNull(serverType, "上传服务器未选定，请重试！");
            String path = null;
            if (FileItemConstant.QINIU_STORE == serverType) {
                path = fileItemService.insertQiNiuYunImageFile(file);
            } else if (FileItemConstant.LOCAL_STORE == serverType) {
                path = fileItemService.insertLocalImageFile(file);
            }
            result.put("code",Constant.SUCCESS);
            result.put("message","上传成功");
            result.put("url", path);
            log.info("文件上次操作成功...");
        } catch (Exception e) {
            logger.error("上传文件失败，原因为 ：" + e.getMessage());
            result.put("code",Constant.FAIL);
            result.put("message","上传文件失败，原因为 ：" + e.getMessage());
            log.info("文件上次操作异常,异常信息：{}，异常堆栈:{}",e.getMessage(),e);
        }
        System.out.println(result.toString());
        response.setHeader("Content-Type","application/json;charset=UTF-8");
        response.addHeader("Access-Control-Allow-Origin", "*");
        return result;
    }

    /**
     *  查询文件列表
     * @param params
     * @return
     */
    @GetMapping(value = "/list")
    @ResponseBody
    public BaseResult list(@RequestParam Map<String, Object> params) {
        log.info("分页查询文件列表开始...");
        BaseResult baseResult = new BaseResult();
        if (!checkPageParam(params)) {
            baseResult.setCode(ResultCode.LACK_PARAM_CODE.getCode());
            baseResult.setMsg(Constant.SUCCESS_MSG);
            return BaseResult.failResult(ResultCode.LACK_PARAM_CODE.getCode());
        }
        List<FileItem> fileItemList = fileItemService.selectFileItemImageList(params);
        PageInfo<FileItem> pageInfo = new PageInfo<>(fileItemList);
        baseResult.setMsg(Constant.SUCCESS_MSG);
        baseResult.setCode(ResultCode.SUCCESS_CODE.getCode());
        baseResult.setData(pageInfo);
        log.info("分页查询文件列表成功...");
        return baseResult;
    }

    /**
     * 七牛云与数据库文件同步
     * @param serverType
     * @return
     * @throws QiniuException
     */
    @PutMapping("/sync")
    @OLog(module = "文件管理", businessType = OperationType.OTHER)
    @ResponseBody
    public BaseResult sync(String  serverType) throws QiniuException {
        log.info("文件同步操作开始...");
        BaseResult baseResult = new BaseResult();
        Objects.requireNonNull(serverType, "未选定刷新的服务器，请重试！");
        int i = 0;
        if (FileItemConstant.QINIU_STORE == serverType) {
            i = fileItemService.syncQiNiuYunImage();
        } else if (FileItemConstant.LOCAL_STORE == serverType) {
            i = fileItemService.syncLocalImage();
        }
        baseResult.setCode(0);
        baseResult.setMsg(Constant.SUCCESS_MSG);
        log.info("分页查询文件列表成功...");
        return baseResult;
    }

    /**
     * 根据条件删除文件
     * @param name
     * @param serverType
     * @return
     * @throws QiniuException
     */
    @DeleteMapping()
    @OLog(module = "文件管理", businessType = OperationType.DELETE)
    @ResponseBody
    public BaseResult deleteImage(String name, String  serverType) throws QiniuException {
        log.info("文件删除操作开始...");
        BaseResult baseResult = new BaseResult();
        Objects.requireNonNull(serverType, "未选定服务器，请重试！");
        int i = 0;
        if (FileItemConstant.QINIU_STORE == serverType) {
            i = fileItemService.deleteQiNiuYunImageFile(name);
        } else if (FileItemConstant.LOCAL_STORE == serverType) {
            i = fileItemService.deleteLocalImageFile(name);
        }
        baseResult.setCode(0);
        baseResult.setMsg(Constant.SUCCESS_MSG);
        log.info("文件删除操作成功...");
        return baseResult;
    }
}
