package com.web.core.controller;

import com.sina.cloudstorage.services.scs.model.PutObjectResult;
import com.web.core.common.ProductParam;
import com.web.core.common.ProductsParam;
import com.web.core.kv.RedisClient;
import com.web.core.service.AdminService;
import com.web.core.service.ProductsService;
import com.web.core.tool.SCSTool;
import com.web.core.tool.ToolClass;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shenzhiqiang on 16/2/2.
 */

@Controller
@RequestMapping("/admin")
public class AdminController {
    private static Log logger = LogFactory.getLog(AdminController.class);

    @Resource
    AdminService adminService;
    @Resource
    ProductsService productsService;
    @Resource
    RedisClient redisClient;

    @RequestMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Integer id) {

        int status = adminService.delOneProd(id);
        if (status < 1)
            logger.error("Admin Delete fail. ID: " + id + ", status: " + status);
        else
            logger.info("Admin Delete prod. ID: " + id + ", status: " + status);
        return "redirect:/admin/products";
    }

    @RequestMapping("/products")
    public String defaultProductsPage() {

        return "forward:/admin/products/1";
    }

    @RequestMapping("/products/{page}")
    public ModelAndView getProductsPage(@PathVariable("page") Integer page, HttpServletRequest request) {
        ModelAndView ret = new ModelAndView();
        ret.setViewName("products_admin");
        ProductsParam productsParam = productsService.getProductsPageParam(page);
        productsParam.setTitle("ADMIN");
        productsParam.setSubTitle("");
        ret.addObject("productsParam", productsParam);

        String sid = ToolClass.getSidFromCookie(request);
        Map<String, String> sessionMap = redisClient.getMap(sid);
        ret.addObject("username", sessionMap.get("username"));

        logger.info("Admin Products. Page: " + page);

        return ret;
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String defaultSearchResultPage(@RequestParam("searchinfo") String searchInfo) {

        return "forward:/admin/search/1";
    }

    @RequestMapping(value = "/search/{page}", method = RequestMethod.GET)
    public ModelAndView searchResultPage(@RequestParam("searchinfo") String searchInfo, @PathVariable("page") Integer page, HttpServletRequest request) {
        ModelAndView ret = new ModelAndView();
        ret.setViewName("searchproducts_admin");
        ProductsParam productsParam = productsService.getSearchResult(searchInfo, page);
        String title = productsParam.getTitle();
        productsParam.setTitle("ADMIN  " + title);
        ret.addObject("productsParam", productsParam);

        String sid = ToolClass.getSidFromCookie(request);
        Map<String, String> sessionMap = redisClient.getMap(sid);
        ret.addObject("username", sessionMap.get("username"));

        logger.info("Admin Search: " + searchInfo + ". Page: " + page);

        return ret;
    }


    /* Change password */
    @RequestMapping(value = "/passwd", method = RequestMethod.GET)
    public ModelAndView changePasswdPage(HttpServletRequest request) {
        String sid = ToolClass.getSidFromCookie(request);

        if (!redisClient.checkExists(sid))
                return new ModelAndView("login");
        else {
            ModelAndView ret = new ModelAndView("changepasswd");

            Map<String, String> sessionMap = redisClient.getMap(sid);
            ret.addObject("username", sessionMap.get("username"));

            return ret;
        }
    }

    @RequestMapping(value = "/passwd", method = RequestMethod.POST)
    public ModelAndView changePasswdPagePost(HttpServletRequest request, HttpServletResponse response,
                                      @RequestParam("oldpassword") String oldpassword,
                                      @RequestParam("newpassword") String newpassword,
                                      @RequestParam("newpassword2") String newpassword2) {
        String sid = ToolClass.getSidFromCookie(request);
        ModelAndView ret = new ModelAndView();

        Map<String, String> sessionMap = redisClient.getMap(sid);
        ret.addObject("username", sessionMap.get("username"));

        if (oldpassword.equals("") || newpassword.equals("") || newpassword2.equals("")) {
            ret.addObject("error", "Password can't NULL.");
            ret.setViewName("changepasswd");
            return ret;
        }
        if (!newpassword.equals(newpassword2) || newpassword.equals("")) {
            ret.addObject("error", "diff New Password.");
            ret.setViewName("changepasswd");
            return ret;
        }
        if (oldpassword.equals(newpassword)) {
            ret.addObject("error", "New Password same to Old Password.");
            ret.setViewName("changepasswd");
            return ret;
        }

        String username =  sessionMap.get("username");
        if (username == null) {
            ret.setViewName("login");
            return ret;
        }

        if (adminService.checkUserLogin(username, oldpassword)) {
            if (adminService.changePasswd(username, newpassword)) {
                ret.setViewName("put_success");
                ret.addObject("showtext", "Change Password Success.");
                logger.info("Change passwd success. USER: " + username);
                return ret;
            } else {
                ret.setViewName("changepasswd");
                ret.addObject("error", "Change Error, retry.");
                return ret;
            }
        } else {
            ret.setViewName("changepasswd");
            ret.addObject("error", "Wrong Old Password.");
            return ret;
        }
    }


    /* ADD prod */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView addProdPage(HttpServletRequest request) {
        String sid = ToolClass.getSidFromCookie(request);
        Map<String, String> sessionMap = redisClient.getMap(sid);
        String username =  sessionMap.get("username");
        if (username == null)
            return new ModelAndView("login");
        else {
            ModelAndView ret = new ModelAndView("addprod_admin");
            ret.addObject("username", sessionMap.get("username"));
            return ret;
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView addProdPagePost(HttpServletRequest request, HttpServletResponse response,
                                        @RequestParam("prod_name") String prod_name,
                                        @RequestParam("prod_introduction") String prod_introduction,
                                        @RequestParam("imgs") MultipartFile[] files) {
        ModelAndView ret = new ModelAndView();
        Map<String, Object> params = new HashMap<String, Object>();
        String path = request.getServletContext().getRealPath("");
        String sid = ToolClass.getSidFromCookie(request);

        Map<String, String> sessionMap = redisClient.getMap(sid);
        ret.addObject("username", sessionMap.get("username"));

        if (prod_name.equals("")) {
            ret.setViewName("addprod_admin");
            logger.info("add prod fail. prod_name is \"\"");
            return ret;
        }

        params.put("prod_name", prod_name);
        params.put("prod_introduction", prod_introduction);
        String image_urls = "";
        String cover_image_url = "";

        if(files != null && files.length > 0) {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    String filename = file.getOriginalFilename();
                    filename = ToolClass.getImgFilename(filename);
                    PutObjectResult putObjectResult = null;

                    try {
                        File f = new File(path + "image/" + filename);
                        file.transferTo(f);
                        putObjectResult = SCSTool.putObject(filename, path + "image/" + filename);
                        f.delete();
                    } catch (Exception e) {
                        logger.error("File save err. prod_name: " + prod_name, e);
                    }

                    if (putObjectResult == null) {
                        logger.error("File upload fail. " + filename);
                        if (image_urls.equals("")) {
                            image_urls += "/image/" + filename;
                        } else {
                            image_urls += ";/image/" + filename;
                        }
                        if (cover_image_url.equals("")) {
                            cover_image_url = "/image/" + filename;
                        }
                    } else {
                        if (image_urls.equals("")) {
                            image_urls += "http://mzx-img.sinacloud.net/" + filename;
                        } else {
                            image_urls += ";http://mzx-img.sinacloud.net/" + filename;
                        }
                        if (cover_image_url.equals("")) {
                            cover_image_url = "http://mzx-img.sinacloud.net/" + filename;
                        }
                    }
                }
            }
        }

        params.put("image_urls", image_urls);
        params.put("cover_image_url", cover_image_url);

        adminService.addProd(params);

        logger.info("add prod success. prod_name: " + prod_name);
        ret.setViewName("put_success");
        return ret;
    }

//    Update prod
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public ModelAndView updateProdPage(@PathVariable("id") Integer id, HttpServletRequest request) {
        String sid = ToolClass.getSidFromCookie(request);
        Map<String, String> sessionMap = redisClient.getMap(sid);
        String username =  sessionMap.get("username");
        if (username == null)
            return new ModelAndView("login");
        else {
            ModelAndView ret = new ModelAndView("updateprod_admin");
            ret.addObject("username", sessionMap.get("username"));

            ProductParam productParam = productsService.getProductParam(id);
            ret.addObject("productParam", productParam);


            return ret;
        }
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public ModelAndView updateProdPagePost(@PathVariable("id") Integer id,
                                           HttpServletRequest request, HttpServletResponse response,
                                           @RequestParam("prod_name") String prod_name,
                                           @RequestParam("prod_introduction") String prod_introduction,
                                           @RequestParam("imgs") MultipartFile[] files) {
        ModelAndView ret = new ModelAndView();
        Map<String, Object> params = new HashMap<String, Object>();
        String path = request.getServletContext().getRealPath("");
        String sid = ToolClass.getSidFromCookie(request);

        Map<String, String> sessionMap = redisClient.getMap(sid);
        ret.addObject("username", sessionMap.get("username"));

        if (prod_name.equals("")) {
            ret.setViewName("addprod_admin");
            logger.info("add prod fail. prod_name is \"\"");
            return ret;
        }

        params.put("id", id);
        params.put("prod_name", prod_name);
        params.put("prod_introduction", prod_introduction);
        String image_urls = "";

        if(files != null && files.length > 0) {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    String filename = file.getOriginalFilename();
                    filename = ToolClass.getImgFilename(filename);
                    PutObjectResult putObjectResult = null;

                    try {
                        File f = new File(path + "image/" + filename);
                        file.transferTo(f);
                        putObjectResult = SCSTool.putObject(filename, path + "image/" + filename);
                        f.delete();
                    } catch (Exception e) {
                        logger.error("File save err. prod_name: " + prod_name, e);
                    }

                    if (putObjectResult == null) {
                        logger.error("File upload fail. " + filename);
                        if (image_urls.equals("")) {
                            image_urls += "/image/" + filename;
                        } else {
                            image_urls += ";/image/" + filename;
                        }
                    } else {
                        if (image_urls.equals("")) {
                            image_urls += "http://mzx-img.sinacloud.net/" + filename;
                        } else {
                            image_urls += ";http://mzx-img.sinacloud.net/" + filename;
                        }
                    }
                }
            }
        }

        params.put("image_urls", image_urls);

        adminService.updateProd(params);

        logger.info("update prod. prod_name: " + prod_name);
        ret.setViewName("put_success");
        return ret;
    }

    @RequestMapping("/setcover/{id}")
    public String setCover(@PathVariable("id") Integer id, @RequestParam("cover_img") String cover_img) {

        int status = adminService.setCover(id, cover_img);
        if (status < 1)
            logger.error("Admin setCover fail. ID: " + id + ", status: " + status);
        else
            logger.info("Admin setCover prod. ID: " + id + ", status: " + status);
        return "redirect:/admin/update/" + String.valueOf(id);
    }

    @RequestMapping("/deleteimg/{id}/{img}")
    public String delImgInProd(@PathVariable("id") Integer id, @PathVariable("img") Integer img) {

        int status = adminService.delProdImg(id, img);
        if (status < 1)
            logger.error("Admin delImgInProd fail. ID: " + id + ", status: " + status);
        else
            logger.info("Admin delImgInProd prod. ID: " + id + ", status: " + status);
        return "redirect:/admin/update/" + String.valueOf(id);
    }

    // Top
    @RequestMapping("/settop/{id}")
    public String setTop(@PathVariable("id") Integer id) {
        adminService.updateTop(1, id);

        return "redirect:/admin/products";
    }

    @RequestMapping("/untop/{id}")
    public String unTop(@PathVariable("id") Integer id) {
        adminService.updateTop(0, id);

        return "redirect:/admin/products";
    }
}