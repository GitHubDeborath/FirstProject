package com.company.dao;

import com.company.action.commons.ServerResponse;
import com.company.dao.pojo.Emall_user;
import com.company.dao.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface Emall_userDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Emall_user record);

    int insertSelective(Emall_user record);

    Emall_user selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Emall_user record);

    int updateByPrimaryKey(Emall_user record);

    int checkUsername(String username);

    User login(@Param("username") String username,@Param("password") String password);
}
