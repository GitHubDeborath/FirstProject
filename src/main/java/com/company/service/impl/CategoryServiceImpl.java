package com.company.service.impl;

import com.company.action.commons.ServerResponse;
import com.company.dao.CategoryDao;
import com.company.dao.pojo.Category;
import com.company.service.iservice.CategoryService;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {
    private Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    CategoryDao categoryDao;

    @Override
    public ServerResponse addCategory(String categoryName, Integer parentId) {
        if (parentId == null || StringUtils.isBlank(categoryName)) {
            return ServerResponse.createErrorMsgResponse("添加品类参数错误");
        }
        Category category = new Category();
        category.setName(categoryName);
        category.setParentId(parentId);
        category.setStatus(true);
        int rowNum = categoryDao.insert(category);
        if (rowNum > 0) {
            return ServerResponse.createSuccessMsgResponse("添加品类成功");
        }
        return ServerResponse.createErrorMsgResponse("添加品类失败");
    }

    @Override
    public ServerResponse updateCategoryName(String categoryName, Integer categoryId) {
        if (categoryId == null || StringUtils.isBlank(categoryName)) {
            return ServerResponse.createErrorMsgResponse("添加品类参数错误");
        }
        Category category = new Category();
        category.setId(categoryId);
        category.setName(categoryName);

        int rowNum = categoryDao.updateByPrimaryKeySelective(category);
        if (rowNum > 0) {
            return ServerResponse.createSuccessMsgResponse("更新品类名称成功");
        }
        return ServerResponse.createErrorMsgResponse("更新品类名称失败");
    }

    @Override
    public ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId) {
        List<Category> categoryList = categoryDao.selectCategoryChildrenByParentId(categoryId);
        if (CollectionUtils.isEmpty(categoryList)) {
            logger.info("未找到子分类");
        }
        return ServerResponse.createSuccessResponse(categoryList);
    }

    @Override
    public ServerResponse<List<Integer>> selectCategoryAndDeepChildrenCategoryById(Integer categoryId) {
        Set<Category> categorySet = Sets.newHashSet();
        findChildCategory(categorySet, categoryId);
        List<Integer> categoryList = Lists.newArrayList();
        if (categoryId != null) {
            for (Category categoryItem : categorySet) {
                categoryList.add(categoryItem.getId());
            }

        }
        return ServerResponse.createSuccessResponse(categoryList);
    }

    private void findChildCategory(Set<Category> categorySet, Integer categoryId) {

    }


}
