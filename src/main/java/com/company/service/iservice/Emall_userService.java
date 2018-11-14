package com.company.service.iservice;

import com.company.action.commons.ServerResponse;
import com.company.dao.pojo.Emall_user;
import com.company.dao.pojo.User;

public interface Emall_userService {
    String deleteByPrimaryKey(Integer id);

    String insert(Emall_user record);

    String insertSelective(Emall_user record);

    Emall_user selectByPrimaryKey(Integer id);

    String updateByPrimaryKeySelective(Emall_user record);

    String updateByPrimaryKey(Emall_user record);

    public ServerResponse<User> login(String username,String password);
}
