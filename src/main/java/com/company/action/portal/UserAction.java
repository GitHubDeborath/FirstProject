package com.company.action.portal;

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
@RequestMapping("/user/")
public class UserAction {
    @Autowired
    @Qualifier("userService")
    UserService userService;

    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session) {
        ServerResponse<User> responseResult = userService.login(username, password);
        if (responseResult.isSuccess()) {
            session.setAttribute(Consts.CURRENT_USER, responseResult.getData());
        }
        return responseResult;
    }

    @RequestMapping(value = "logout.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> logout(HttpSession session) {
        session.removeAttribute(Consts.CURRENT_USER);
        return ServerResponse.createSuccessResponse();
    }

    @RequestMapping(value = "check_valid.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> checkValid(String str, String type) {
        return userService.checkValid(str, type);
    }

    @RequestMapping(value = "registry.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> registry(User user) {
        return userService.registry(user);
    }

    @RequestMapping(value = "get_user_info.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<User> getUserInfo(HttpSession session) {
        User user = (User) session.getAttribute(Consts.CURRENT_USER);
        if (user != null) {
            return ServerResponse.createSuccessResponse(user);
        }
        return ServerResponse.createErrorMsgResponse("用户端未登录");
    }

    @RequestMapping(value = "forget_get_question.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> forgetPasswordGetQuetion(String username) {

        return userService.getQuestionByUsername(username);
    }

    @RequestMapping(value = "forget_check_answer.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> forgetPasswordCheckAnswer(String username, String question, String answer) {
        return userService.checkAnswer(username, question, answer);
    }

    @RequestMapping(value = "forget_reset_password.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken) {
        return userService.forgetResetPassword(username, passwordNew, forgetToken);
    }

    @RequestMapping(value = "reset_password.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> resetPassword(HttpSession session, String passwordOld, String passwordNew) {
        User user = (User) session.getAttribute(Consts.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createErrorMsgResponse("用户尚未登录");
        } else {
            return userService.resetPassword(passwordOld, passwordNew, user);
        }
    }

    @RequestMapping(value = "update_information.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> updateUserInformation(User user, HttpSession session) {
        User currentUser = (User) session.getAttribute(Consts.CURRENT_USER);
        if (currentUser == null) {
            return ServerResponse.createErrorMsgResponse("用户尚未登录");
        }
        user.setId(currentUser.getId());
        user.setUsername(currentUser.getUsername());
        ServerResponse<User> resultResponse = userService.updateInformation(user);
        if (resultResponse.isSuccess()) {
            session.setAttribute(Consts.CURRENT_USER, resultResponse.getData());
        }
        return resultResponse;
    }

    @RequestMapping(value = "get_information.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<User> getInformation(HttpSession session) {
        User currentUser = (User) session.getAttribute(Consts.CURRENT_USER);
        if (currentUser == null) {
            return ServerResponse.createErrorResponse(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，返回状态码10，前端判断状态码并实现强制登录");
        }
        return userService.getInformation(currentUser.getId());
    }






}
