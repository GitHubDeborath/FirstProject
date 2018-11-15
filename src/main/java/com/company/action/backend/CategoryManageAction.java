package com.company.action.backend;

import com.company.action.commons.Consts;
import com.company.action.commons.ResponseCode;
import com.company.action.commons.ServerResponse;
import com.company.dao.pojo.User;
import com.company.service.iservice.CategoryService;
import com.company.service.iservice.UserService;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/manage/category")
public class CategoryManageAction {
    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @Autowired
    @Qualifier("categoryService")
    private CategoryService categoryService;

    @RequestMapping("add_category.do")
    @ResponseBody
    public ServerResponse addCategory(HttpSession session, String categoryName,
                                      @RequestParam(value = "parentId", defaultValue = "0") int parentId) {
        User user = (User) session.getAttribute(Consts.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createErrorResponse(ResponseCode.NEED_LOGIN.getCode(), "用户未登录请登录");
        }
        if (userService.checkAdminRole(user).isSuccess()) {
            return categoryService.addCategory(categoryName, parentId);
        } else {
            return ServerResponse.createErrorMsgResponse("用户没有权限");
        }


    }
    @RequestMapping("set_category_name.do")
    @ResponseBody
    public ServerResponse setCategoryName(HttpSession session, Integer categoryId,String categoryName) {
        User user = (User) session.getAttribute(Consts.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createErrorResponse(ResponseCode.NEED_LOGIN.getCode(), "用户未登录请登录");
        }
        if (userService.checkAdminRole(user).isSuccess()) {
            return categoryService.updateCategoryName(categoryName, categoryId);
        } else {
            return ServerResponse.createErrorMsgResponse("用户没有权限");
        }


    }

    @RequestMapping("get_category.do")
    @ResponseBody
    public ServerResponse getChildrenParallelCategory(HttpSession session,
                                                      @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
        User user = (User) session.getAttribute(Consts.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createErrorResponse(ResponseCode.NEED_LOGIN.getCode(), "用户未登录请登录");
        }
        if (userService.checkAdminRole(user).isSuccess()) {
            return categoryService.getChildrenParallelCategory(categoryId);
        } else {
            return ServerResponse.createErrorMsgResponse("用户没有权限");
        }
    }

    @RequestMapping("get_deep_category.do")
    @ResponseBody
    public ServerResponse getCategoryAndDeepChildrenCategory(HttpSession session,
                                                      @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
        User user = (User) session.getAttribute(Consts.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createErrorResponse(ResponseCode.NEED_LOGIN.getCode(), "用户未登录请登录");
        }
        if (userService.checkAdminRole(user).isSuccess()) {
            return categoryService.selectCategoryAndDeepChildrenCategoryById(categoryId);
        } else {
            return ServerResponse.createErrorMsgResponse("用户没有权限");
        }
    }

}
