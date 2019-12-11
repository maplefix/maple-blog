package top.maplefix.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import top.maplefix.constant.Constant;
import top.maplefix.mapper.UserMapper;
import top.maplefix.model.User;
import top.maplefix.service.IUserService;
import top.maplefix.utils.Md5Utils;

/**
 * @author : Maple
 * @description :用户操作接口实现类
 * @date : Created in 2019/7/24 23:00
 * @editor:
 * @version: v2.1
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(String loginName, String password) {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("delFlag", Constant.NORMAL);
        criteria.andEqualTo("loginName", loginName);
        criteria.andEqualTo("password", Md5Utils.MD5Encode(password, "UTF-8"));
        return userMapper.selectOneByExample(example);
    }

    @Override
    public User getUserDetail(User user) {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("delFlag", Constant.NORMAL);
        criteria.andEqualTo("userId", user.getUserId());
        return userMapper.selectOneByExample(example);
    }

    @Override
    public boolean updatePassword(String userId, String originalPassword, String newPassword) {
        User tmp = new User();
        tmp.setUserId(userId);
        User user = userMapper.selectByPrimaryKey(tmp);
        //当前用户非空才可以进行更改
        if (user != null) {
            String originalPasswordMd5 = Md5Utils.MD5Encode(originalPassword, "UTF-8");
            String newPasswordMd5 = Md5Utils.MD5Encode(newPassword, "UTF-8");
            //比较原密码是否正确
            if (originalPasswordMd5.equals(user.getPassword())) {
                //设置新密码并修改
                user.setPassword(newPasswordMd5);
                if (userMapper.updateByPrimaryKeySelective(user) > 0) {
                    //修改成功则返回true
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean updateUser(User user) {
        User user1 = userMapper.selectByPrimaryKey(user);
        //当前用户非空才可以进行更改
        if (user1 != null) {
            if (userMapper.updateByPrimaryKeySelective(user) > 0) {
                //修改成功则返回true
                return true;
            }
        }
        return false;
    }
}
