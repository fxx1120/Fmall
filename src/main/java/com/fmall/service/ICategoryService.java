package com.fmall.service;

import com.fmall.common.ServerResponse;
import com.fmall.pojo.Category;

import java.util.List;

/**
 * Created by fxx028 on 2019/1/8.
 */
public interface ICategoryService {
    ServerResponse addCategory(String categoryName, Integer parentId);

    ServerResponse updateCategoryName(Integer categoryId,String categoryName);

    ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId);

    ServerResponse <List<Integer>>selectCategoryAndChildrenById(Integer categoryId);
}
