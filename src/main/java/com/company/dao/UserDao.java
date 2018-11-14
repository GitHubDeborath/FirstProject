package com.company.dao;

import com.company.action.commons.ServerResponse;
import com.company.dao.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserDao {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int checkUsername(String username);

    User login(@Param("username") String username, @Param("password") String password);

    int checkEmail(String str);

    String selectQuestionByUsername(String username);

    int checkAnswer(@Param("username") String username, @Param("question") String question,@Param("answer") String answer);

    int updateByUsername(@Param("username") String username,@Param("md5Password") String md5Password);

    int checkPassword(@Param("password") String password, @Param("userId") Integer userId);

    int checkEmailByUserId(@Param("email") String email, @Param("userId") Integer id);
}
