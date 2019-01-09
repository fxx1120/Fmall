package com.fmall.service;

import com.fmall.common.ServerResponse;
import com.fmall.pojo.Product;
import com.fmall.vo.ProductDetailVo;
import com.github.pagehelper.PageInfo;

/**
 * Created by fxx028 on 2019/1/9.
 */
public interface IProductService {
    ServerResponse saveOrUpdateProduct(Product product);

    ServerResponse<String> setSaleStatus(Integer productId,Integer status);

    ServerResponse<ProductDetailVo> manageProductDetail(Integer productId);

    ServerResponse<PageInfo> getProductList(int pageNum, int pageSize);

    ServerResponse<PageInfo> searchProductList(String productName,Integer productId,int pageNum,int pageSize);
}
