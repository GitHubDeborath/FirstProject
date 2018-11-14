package com.company.service.iservice;

import com.company.action.commons.ServerResponse;
import com.company.dao.pojo.User;

public interface UserService {
    String deleteByPrimaryKey(Integer id);

    String insert(User record);

    String insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    String updateByPrimaryKeySelective(User record);

    String updateByPrimaryKey(User record);

    public ServerResponse<User> login(String username, String password);

    ServerResponse<String> checkValid(String str, String type);

    ServerResponse<String> registry(User user);

    ServerResponse<String> getQuestionByUsername(String username);

    ServerResponse<String> checkAnswer(String username, String question, String answer);

    ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken);

    ServerResponse<String> resetPassword(String passwordOld, String passwordNew, User user);

    ServerResponse<User> updateInformation(User user);

    ServerResponse<User> getInformation(Integer id);

}
