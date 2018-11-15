package com.company.service.impl;

import com.company.action.commons.Consts;
import com.company.action.commons.ServerResponse;
import com.company.action.commons.TokenCache;
import com.company.dao.UserDao;
import com.company.dao.pojo.User;
import com.company.dao.util.MD5Util;
import com.company.service.iservice.UserService;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    @Qualifier("userDao")
    UserDao userDao;

    @Override
    public String deleteByPrimaryKey(Integer id) {
        userDao.deleteByPrimaryKey(id);
        return "success";
    }

    @Override
    public String insert(User record) {
        return (userDao.insert(record) == 1) ? "success" : "error";
    }

    @Override
    public String insertSelective(User record) {
        return (userDao.insertSelective(record) == 1) ? "success" : "error";
    }

    @Override
    public User selectByPrimaryKey(Integer id) {
        User user = userDao.selectByPrimaryKey(id);
        return user;
    }

    @Override
    public String updateByPrimaryKeySelective(User record) {
        userDao.updateByPrimaryKeySelective(record);
        return "success";
    }

    @Override
    public String updateByPrimaryKey(User record) {
        userDao.updateByPrimaryKey(record);
        return "success";
    }

    @Override
    public ServerResponse<User> login(String username, String password) {
        int resultCount = userDao.checkUsername(username);
        if (resultCount == 0) {
            return ServerResponse.createErrorMsgResponse("用户名不存在");
        }
        User user = userDao.login(username, password);
        if (user == null) {
            return ServerResponse.createErrorMsgResponse("密码不正确");
        }
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createSuccessResponse("登录成功！", user);
    }

    @Override
    public ServerResponse<String> checkValid(String str, String type) {
        if (StringUtils.isNotBlank(type)) {
            if (Consts.ValidType.USERNAME.equals(type)) {
                int resultCount = userDao.checkUsername(str);
                if (resultCount > 0) {
                    return ServerResponse.createErrorMsgResponse("用户已存在");
                }
            }
            if (Consts.ValidType.EMAIL.equals(type)) {
                int resultCount = userDao.checkEmail(str);
                if (resultCount > 0) {
                    return ServerResponse.createErrorMsgResponse("email已存在");
                }
            }
        } else {
            return ServerResponse.createErrorMsgResponse("参数类型错误！只能选择用户名和邮箱");
        }
        return ServerResponse.createSuccessMsgResponse("用户名/Email可用，校验成功");
    }

    @Override
    public ServerResponse<String> registry(User user) {
        ServerResponse validResponse = this.checkValid(user.getUsername(), Consts.ValidType.USERNAME);
        if (!validResponse.isSuccess()) {
            return ServerResponse.createErrorMsgResponse("用户已存在");
        }
        validResponse = this.checkValid(user.getEmail(), Consts.ValidType.EMAIL);
        if (!validResponse.isSuccess()) {
            return ServerResponse.createErrorMsgResponse("邮箱已存在");
        }
        user.setRole(Consts.Role.ROLE_USER);
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        int resultCount = userDao.insertSelective(user);
        if (resultCount == 0) {
            return ServerResponse.createErrorMsgResponse("注册失败");
        }
        return ServerResponse.createSuccessMsgResponse("注册成功");
    }

    @Override
    public ServerResponse<String> getQuestionByUsername(String username) {
        ServerResponse validResponse = this.checkValid(username, Consts.ValidType.USERNAME);
        if (validResponse.isSuccess()) {
            return ServerResponse.createErrorMsgResponse("用户名不存在");
        }
        String question = userDao.selectQuestionByUsername(username);
        if (StringUtils.isNotBlank(question)) {
            return ServerResponse.createSuccessResponse(question);
        }
        return ServerResponse.createErrorMsgResponse("该用户没有设置问题");
    }

    @Override
    public ServerResponse<String> checkAnswer(String username, String question, String answer) {
        int resultCount = userDao.checkAnswer(username, question, answer);
        if (resultCount > 0) {
            String forgetToken = UUID.randomUUID().toString();
            TokenCache.setKey("token_" + username, forgetToken);
            return ServerResponse.createSuccessResponse(forgetToken);
        }
        return ServerResponse.createErrorMsgResponse("预设问题回答错误，无法修改密码");
    }

    @Override
    public ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken) {
        if (StringUtils.isBlank(forgetToken)) {
            return ServerResponse.createErrorMsgResponse("参数错误，需要传递token令牌");
        }
        ServerResponse validResponse = this.checkValid(username, Consts.ValidType.USERNAME);
        if (validResponse.isSuccess()) {
            return ServerResponse.createErrorMsgResponse("用户名不存在");
        }
        String token = TokenCache.getKey(TokenCache.TOKEN_PREFIX + username);
        if (StringUtils.isBlank(token)) {
            ServerResponse.createErrorMsgResponse("Token无效或者已过期");
        }
        if (StringUtils.equals(token, forgetToken)) {
            String md5Password = MD5Util.MD5EncodeUtf8(passwordNew);
            int rowCount = userDao.updateByUsername(username, md5Password);
            if (rowCount > 0) {
                return ServerResponse.createSuccessMsgResponse("密码修改成功");
            } else {
                return ServerResponse.createErrorMsgResponse("修改密码失败");
            }
        } else {
            return ServerResponse.createErrorMsgResponse("Token无效，请重新获取");
        }
    }

    @Override
    public ServerResponse<String> resetPassword(String passwordOld, String passwordNew, User user) {
        int resultCount = userDao.checkPassword(MD5Util.MD5EncodeUtf8(passwordOld), user.getId());
        if (resultCount == 0) {
            return ServerResponse.createErrorMsgResponse("旧密码有误，请重新输入");
        }
        user.setPassword(MD5Util.MD5EncodeUtf8(passwordNew));
        int updateCount = userDao.updateByPrimaryKeySelective(user);
        if (updateCount > 0) {
            return ServerResponse.createErrorMsgResponse("更新密码成功");
        }
        return ServerResponse.createSuccessMsgResponse("更新密码失败");
    }

    @Override
    public ServerResponse<User> updateInformation(User user) {
int resultCount=userDao.checkEmailByUserId(user.getEmail(),user.getId());
if(resultCount>0){
    return  ServerResponse.createErrorMsgResponse("邮箱已被他人使用");
}
User updateUser=new User();
updateUser.setId(user.getId());
updateUser.setUsername(user.getUsername());
updateUser.setEmail(user.getEmail());
updateUser.setPhone(user.getPhone());
updateUser.setQuestion(user.getQuestion());
updateUser.setAnswer(user.getAnswer());
        int updateCount=userDao.updateByPrimaryKeySelective(updateUser);
        if(updateCount>0){
            return  ServerResponse.createSuccessResponse("更新个人信息成功",updateUser);
        }
        return ServerResponse.createErrorMsgResponse("更新个人信息失败！");
    }

    @Override
    public ServerResponse<User> getInformation(Integer id) {
        User user = userDao.selectByPrimaryKey(id);
        if(user!=null){
            user.setPassword(StringUtils.EMPTY);
            return ServerResponse.createSuccessResponse(user);
        }else {
            return ServerResponse.createErrorMsgResponse("用户不存在");
        }
    }

    @Override
    public ServerResponse checkAdminRole(User user) {
        if(user!=null && user.getRole().intValue()== Consts.Role.ROLE_ADMIN){
            return ServerResponse.createSuccessResponse();
        }
        return ServerResponse.createErrorResponse();
    }


}
