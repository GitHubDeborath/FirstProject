package com.company.service.impl;

import com.company.action.commons.ServerResponse;
import com.company.dao.Emall_userDao;
import com.company.dao.pojo.Emall_user;
import com.company.dao.pojo.User;
import com.company.service.iservice.Emall_userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class Emall_userServiceImpl implements Emall_userService {
    @Autowired
    @Qualifier("emall_userDao")
    Emall_userDao emall_userDao;

    @Override
    public String deleteByPrimaryKey(Integer id) {
        emall_userDao.deleteByPrimaryKey(id);
        return "success";
    }

    @Override
    public String insert(Emall_user record) {
        return (emall_userDao.insert(record)==1)?"success":"error";
    }

    @Override
    public String insertSelective(Emall_user record) {
        return (emall_userDao.insertSelective(record)==1)?"success":"error";
    }

    @Override
    public Emall_user selectByPrimaryKey(Integer id) {
        Emall_user user = emall_userDao.selectByPrimaryKey(id);
        return user;
    }

    @Override
    public String updateByPrimaryKeySelective(Emall_user record) {
        emall_userDao.updateByPrimaryKeySelective(record);
        return "success";
    }

    @Override
    public String updateByPrimaryKey(Emall_user record) {
        emall_userDao.updateByPrimaryKey(record);
        return "success";
    }

    @Override
    public ServerResponse<User> login(String username, String password) {



        return null;
    }
}
