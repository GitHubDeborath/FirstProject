package com.company.action.backend;

import com.company.action.commons.Consts;
import com.company.action.commons.ResponseCode;
import com.company.action.commons.ServerResponse;
import com.company.dao.pojo.User;
import com.company.service.iservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class UserManageAction {
    @Autowired
    @Qualifier("userService")
    UserService userService;

    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session) {
        ServerResponse<User> resultResponse = userService.login(username, password);
        if (resultResponse.isSuccess()) {
            User user = resultResponse.getData();
            if (user.getRole() == Consts.Role.ROLE_ADMIN) {
                session.setAttribute(Consts.CURRENT_USER, user);
            } else {
                return ServerResponse.createErrorMsgResponse("该用户没有权限，无法登录管理后台");
            }
        }
        return resultResponse;
    }
}
