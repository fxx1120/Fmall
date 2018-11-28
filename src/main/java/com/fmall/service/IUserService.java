package com.fmall.service;

import com.fmall.common.ServerResponse;
import com.fmall.pojo.User;

/**
 * Created by fxx028 on 2018/11/5.
 */
public interface IUserService {
    ServerResponse<User> login(String username, String password);
}
