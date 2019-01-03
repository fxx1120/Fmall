package com.fmall.service;

import com.fmall.common.ServerResponse;
import com.fmall.pojo.User;

/**
 * Created by fxx028 on 2018/11/5.
 */
public interface IUserService {
    ServerResponse<User> login(String username, String password);

    ServerResponse<String> register(User user);

    ServerResponse<String> checkValid(String str, String type);

    ServerResponse selsectQuestion(String username);

    ServerResponse<String> CheckAnswer(String username, String question, String answer);

    ServerResponse<String> forgetRestPassword(String username, String passwordNew, String forgetToken);

    ServerResponse<String> resetPassword(String passwordOld, String passwordNew, User user);

}
