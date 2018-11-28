package com.fmall.service.impl;

import com.fmall.common.ServerResponse;
import com.fmall.dao.UserMapper;
import com.fmall.pojo.User;
import com.fmall.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by fxx028 on 2018/11/5.
 */
@Service("iUserService")
public class UserServiceImpl implements IUserService{
    @Autowired
    private UserMapper userMapper;

    @Override
    public ServerResponse<User> login(String username, String password) {
        int resultCount = userMapper.checkUsername(username);
        if (resultCount==0){
            return ServerResponse.createByErrorMessage("用户名不存在！");
        }

        // TODO: 2018/11/5 密码登陆 MD5


        User user = userMapper.selectLogin(username,password);
        if (user==null){
            return ServerResponse.createByErrorMessage("密码错误！");
        }


        user.setPassword(StringUtils.EMPTY);



        return ServerResponse.createBySuccess("登陆成功",user);
    }
}
