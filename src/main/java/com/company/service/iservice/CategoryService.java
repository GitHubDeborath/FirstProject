package com.company.service.iservice;

import com.company.action.commons.ServerResponse;
import com.company.dao.pojo.Category;

import java.util.List;

public interface CategoryService {
    ServerResponse addCategory(String categoryName, Integer parentId);

    ServerResponse updateCategoryName(String categoryName, Integer categoryId);

    ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId);

    ServerResponse<List<Integer>> selectCategoryAndDeepChildrenCategoryById(Integer categoryId);
}
