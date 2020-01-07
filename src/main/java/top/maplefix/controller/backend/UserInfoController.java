package top.maplefix.controller.backend;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import top.maplefix.annotation.OLog;
import top.maplefix.constant.Constant;
import top.maplefix.constant.DictConstant;
import top.maplefix.controller.BaseController;
import top.maplefix.enums.OperationType;
import top.maplefix.model.User;
import top.maplefix.service.IDictService;
import top.maplefix.service.IFileItemService;
import top.maplefix.service.IUserService;
import top.maplefix.utils.DateUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : Maple
 * @description : 后端登录授权操作
 * @date : Created in 2019/7/27 14:43
 * @version : v1.0
 */
@Controller
@RequestMapping("/api/admin/userInfo")
@Slf4j
public class UserInfoController extends BaseController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IFileItemService fileItemService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private IDictService dictService;

    /**
     * 修改用户信息页面
     * @param request
     * @return
     */
    @GetMapping({"","/"})
    public String userInfo(HttpServletRequest request) {
        String loginUserId = (String) request.getSession().getAttribute(Constant.LOGIN_USER_ID);
        User user = new User();
        user.setUserId(loginUserId);
        User loginUser = userService.getUserDetail(user);
        if (loginUser == null) {
            return "admin/login";
        }
        request.setAttribute("path", "profile");
        request.setAttribute("loginUserAvatar", loginUser.getAvatar());
        request.setAttribute(Constant.LOGIN_USER, loginUser);
        return "admin/user-info";
    }

    /**
     * 修改登录密码
     * @param request
     * @param originalPassword
     * @param newPassword
     * @return
     */
    @PostMapping("/password")
    @OLog(module = "用户管理", businessType = OperationType.UPDATE)
    @ResponseBody
    public String passwordUpdate( @RequestParam("originalPassword") String originalPassword,
                                  @RequestParam("newPassword") String newPassword,HttpServletRequest request) {
        log.info("修改密码操作开始...");
        if (StringUtils.isEmpty(originalPassword) || StringUtils.isEmpty(newPassword)) {
            log.error("修改密码失败,原因为原密码或者新密码为空...");
            return "参数不能为空";
        }
        String loginUserId = (String) request.getSession().getAttribute(Constant.LOGIN_USER_ID);
        if (userService.updatePassword(loginUserId, originalPassword, newPassword)) {
            //修改成功后清空session中的数据，前端控制跳转至登录页
            request.getSession().removeAttribute(Constant.LOGIN_USER_ID);
            request.getSession().removeAttribute(Constant.LOGIN_USER);
            request.getSession().removeAttribute("errorMsg");
            redisTemplate.delete(Constant.LOGIN_USER + ":" + loginUserId);
            log.error("修改密码成功...");
            return Constant.SUCCESS_MSG;
        } else {
            log.error("修改密码失败...");
            return Constant.FAIL_MSG;
        }
    }

    /**
     * 更新头像
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/updateAvatar")
    @OLog(module = "用户管理", businessType = OperationType.UPLOAD)
    @ResponseBody
    public JSONObject updateAvatar(HttpServletRequest request, HttpServletResponse response) {
        log.error("修改头像操作开始...");
        JSONObject result = new JSONObject();
        //多部分httpRquest对象MultipartHttpServletRequest是HttpServletRequest类的一个子类接口,支持文件分段上传对象
        MultipartHttpServletRequest mtRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = mtRequest.getFile("avatarFile");
        try {
            String fileName = file.getOriginalFilename();
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            String path = fileItemService.insertQiNiuYunImageFile(file);
            result.put("code", Constant.SUCCESS);
            result.put("message","上传成功");
            result.put("url", path);
            String loginUserId = (String) request.getSession().getAttribute(Constant.LOGIN_USER_ID);
            User user = new User();
            user.setUserId(loginUserId);
            user.setAvatar(path);
            userService.updateUser(user);
            //头像更新完后更新字典表中的数据
            dictService.updateByKeywordAndDictName(DictConstant.AVATAR,DictConstant.AVATAR,path);
            log.error("修改头像操作成功...");
        } catch (Exception e) {
            log.error("更新头像失败，异常信息：{}，异常堆栈：{} ：", e.getMessage(),e);
            result.put("code",Constant.FAIL);
            result.put("message","更新头像失败，原因为 ：" + e.getMessage());
        }
        response.setHeader("Content-Type","application/json;charset=UTF-8");
        response.addHeader("Access-Control-Allow-Origin", "*");
        return result;
    }
    /**
     * 修改基本信息
     * @param request
     * @param loginName
     * @param userName
     * @return
     */
    @PostMapping("/update")
    @OLog(module = "用户管理", businessType = OperationType.UPDATE)
    @ResponseBody
    public String nameUpdate(HttpServletRequest request,
                             @RequestParam("loginName") String loginName,
                             @RequestParam("avatar") String avatar,
                             @RequestParam("userName") String userName,
                             @RequestParam(name = "email", required = false) String email,
                             @RequestParam(name = "phone", required = false ) String phone) {
        log.error("修改用户信息操作开始...");
        String loginUserId = (String) request.getSession().getAttribute(Constant.LOGIN_USER_ID);
        User user = new User();
        user.setUserId(loginUserId);
        user.setAvatar(avatar);
        user.setLoginName(loginName);
        user.setUserName(userName);
        if(!StringUtils.isEmpty(email)){
            user.setEmail(email);
        }
        if(!StringUtils.isEmpty(phone)){
            user.setPhone(phone);
        }
        user.setUpdateDate(DateUtils.getCurrDate());
        if (userService.updateUser(user)) {
            log.error("修改用户信息操作成功...");
            return "success";
        } else {
            log.error("修改用户信息操作失败...");
            return "fail";
        }
    }

}
