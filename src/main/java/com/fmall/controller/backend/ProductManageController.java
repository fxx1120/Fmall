package com.fmall.controller.backend;

import com.fmall.common.Const;
import com.fmall.common.ServerResponse;
import com.fmall.pojo.Product;
import com.fmall.pojo.User;
import com.fmall.service.IFileService;
import com.fmall.service.IProductService;
import com.fmall.service.IUserService;
import com.fmall.util.PropertiesUtil;
import com.google.common.collect.Maps;
import com.sun.javafx.collections.MappingChange;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by fxx028 on 2019/1/9.
 */
@Controller
@RequestMapping("/manage/product/")
public class ProductManageController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IProductService iProductService;

    @Autowired
    private IFileService iFileService;
    /**
     * 保存商品
     * @param session
     * @param product
     * @return
     */
    @RequestMapping(value = "save.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse productSave(HttpSession session, Product product){
        User user = (User) session.getAttribute(Const.CURRENT_USER);

        if (user==null){
            return ServerResponse.createByErrorMessage("用户未登录！");
        }

        if (iUserService.checkAdminRole(user).isSuccess()){
            return iProductService.saveOrUpdateProduct(product);
        }else {
            return ServerResponse.createByErrorMessage("无权限!");
        }
    }


    /**
     * 修改商品状态
     * @param session
     * @param productId
     * @param status
     * @return
     */
    @RequestMapping(value = "set_sale_status.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse setSaleStatus(HttpSession session, Integer productId,Integer status){
        User user = (User) session.getAttribute(Const.CURRENT_USER);

        if (user==null){
            return ServerResponse.createByErrorMessage("用户未登录！");
        }

        if (iUserService.checkAdminRole(user).isSuccess()){
            return iProductService.setSaleStatus(productId,status);
        }else {
            return ServerResponse.createByErrorMessage("无权限!");
        }
    }


    /**
     * 产品信息
     * @param session
     * @param productId
     * @return
     */
    @RequestMapping(value = "detail.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse getDetail(HttpSession session, Integer productId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);

        if (user==null){
            return ServerResponse.createByErrorMessage("用户未登录！");
        }

        if (iUserService.checkAdminRole(user).isSuccess()){
            return iProductService.manageProductDetail(productId);
        }else {
            return ServerResponse.createByErrorMessage("无权限!");
        }
    }


    /**
     * 产品列表
     * @param session
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "list.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse getList(HttpSession session, @RequestParam(value ="pageNum",defaultValue = "1") int pageNum,@RequestParam(value ="pageSize",defaultValue = "10") int pageSize){
        User user = (User) session.getAttribute(Const.CURRENT_USER);

        if (user==null){
            return ServerResponse.createByErrorMessage("用户未登录！");
        }

        if (iUserService.checkAdminRole(user).isSuccess()){
            return iProductService.getProductList(pageNum,pageSize);
        }else {
            return ServerResponse.createByErrorMessage("无权限!");
        }
    }


    /**
     * 搜索
     * @param session
     * @param productName
     * @param productId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "search.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse productSearch(HttpSession session,String productName,Integer productId, @RequestParam(value ="pageNum",defaultValue = "1") int pageNum,@RequestParam(value ="pageSize",defaultValue = "10") int pageSize){
        User user = (User) session.getAttribute(Const.CURRENT_USER);

        if (user==null){
            return ServerResponse.createByErrorMessage("用户未登录！");
        }

        if (iUserService.checkAdminRole(user).isSuccess()){
            return iProductService.searchProductList(productName,productId,pageNum,pageSize);
        }else {
            return ServerResponse.createByErrorMessage("无权限!");
        }
    }


    @RequestMapping(value = "upload.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse upload(HttpSession session,@RequestParam(value = "upload_file",required = false) MultipartFile file, HttpServletRequest request){

        User user = (User) session.getAttribute(Const.CURRENT_USER);

        if (user==null){
            return ServerResponse.createByErrorMessage("用户未登录！");
        }

        if (iUserService.checkAdminRole(user).isSuccess()){

            String path = request.getSession().getServletContext().getRealPath("upload");
            String targeFileName = iFileService.upload(file,path);

            String url = PropertiesUtil.getProperty("ftp.server.http.prefix","http://img.happymmall.com/")+targeFileName;

            Map fileMap = Maps.newHashMap();

            fileMap.put("uri",targeFileName);
            fileMap.put("url",url);

            return ServerResponse.createBySuccess(fileMap);

        }else {
            return ServerResponse.createByErrorMessage("无权限!");
        }

    }


    @RequestMapping(value = "ricetext_img_upload.do", method = RequestMethod.POST)
    @ResponseBody
    public Map ricetextImagupload(HttpSession session, @RequestParam(value = "upload_file",required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response){

        Map resultMap = Maps.newHashMap();

        User user = (User) session.getAttribute(Const.CURRENT_USER);

        if (user==null){
            resultMap.put("success",false);
            resultMap.put("msg","用户未登录！");
            return resultMap;
        }

        if (iUserService.checkAdminRole(user).isSuccess()){

            String path = request.getSession().getServletContext().getRealPath("upload");
            String targeFileName = iFileService.upload(file,path);

            if (StringUtils.isBlank(targeFileName)){
                resultMap.put("success",false);
                resultMap.put("msg","上传失败！");
                return resultMap;
            }

            String url = PropertiesUtil.getProperty("ftp.server.http.prefix","http://img.happymmall.com/")+targeFileName;

            resultMap.put("success",true);
            resultMap.put("msg","上传成功！");
            resultMap.put("file_path",url);
            response.addHeader("Access-Control-Allow-Headers","X-File-Name");
            return resultMap;

        }else {
            resultMap.put("success",false);
            resultMap.put("msg","无权限！");
            return resultMap;
        }

    }
}
