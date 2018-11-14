package com.company.service.impl;

import com.company.action.commons.ServerResponse;
import com.company.dao.pojo.User;
import com.company.service.iservice.UserService;
import commons.SpringTestCase;
import junit.framework.TestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import static org.junit.Assert.*;

public class UserServiceImplTest extends SpringTestCase {
    @Autowired
    @Qualifier("userService")
    UserService userService;
    @Test
    public void deleteByPrimaryKey() {
        userService.deleteByPrimaryKey(13);
        System.out.println("************************************");
    }

    @Test
    public void insert() {
        User user = new User("test", "test", "aaa.@qq.com", "100", "aaa", "bbb", 0);
        String msg = userService.insert(user);
        System.out.println(msg + "+++++++++");
    }

    @Test
    public void insertSelective() {
        User user = new User();
        user.setUsername("baobao");
        user.setPassword("test");
        user.setRole(0);
        String msg = userService.insertSelective(user);
        System.out.println(msg + "************************************");
    }

    @Test
    public void selectByPrimaryKey() {
        User user = userService.selectByPrimaryKey(1);
        System.out.println("************************************" + user.toString());
        System.out.println("************************************" + user.getUsername());
    }

    @Test
    public void updateByPrimaryKeySelective() {
        User user = new User();
        user.setId(21);
        user.setUsername("scott1");
        userService.updateByPrimaryKeySelective(user);
        System.out.println("************************************");
    }

    @Test
    public void updateByPrimaryKey() {
    }

    @Test
    public void login() {
        ServerResponse responseResult =userService.login("baobao","test");
        TestCase.assertTrue(responseResult.isSuccess());
        System.out.println("***************************"+responseResult.getData());
    }
}
