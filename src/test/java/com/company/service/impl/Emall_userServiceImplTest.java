package com.company.service.impl;

import com.company.dao.pojo.Emall_user;
import com.company.service.iservice.Emall_userService;
import commons.SpringTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import static org.junit.Assert.*;

public class Emall_userServiceImplTest extends SpringTestCase {
    @Autowired

    Emall_userService emall_userService;
    @Test
    public void deleteByPrimaryKey() {
        emall_userService.deleteByPrimaryKey(13);
        System.out.println("************************************");
    }

    @Test
    public void insert() {
        Emall_user user = new Emall_user("test","test","aaa.@qq.com","100","aaa","bbb",0);
        String msg = emall_userService.insert(user);
        System.out.println(msg+"+++++++++");
    }

    @Test
    public void insertSelective() {
        Emall_user user=new Emall_user();
        user.setUsername("baobao");
        user.setPassword("test");
        user.setRole(0);
        String msg = emall_userService.insertSelective(user);
        System.out.println(msg+"************************************");


    }

    @Test
    public void selectByPrimaryKey() {
        Emall_user user=emall_userService.selectByPrimaryKey(1);
        System.out.println("************************************"+user.toString());
        System.out.println("************************************"+user.getUsername());
    }

    @Test
    public void updateByPrimaryKeySelective() {
        Emall_user user=new Emall_user();
        user.setId(21);
        user.setUsername("scott1");
                emall_userService.updateByPrimaryKeySelective(user);
        System.out.println("************************************");
    }

    @Test
    public void updateByPrimaryKey() {
    }
}
