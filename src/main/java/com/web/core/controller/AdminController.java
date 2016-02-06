package com.web.core.controller;

import com.web.core.common.ProductsParam;
import com.web.core.service.AdminService;
import com.web.core.service.ProductsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by shenzhiqiang on 16/2/2.
 */

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Resource
    AdminService adminService;
    @Resource
    ProductsService productsService;

    @RequestMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Integer id) {

        int status = adminService.delOneProd(id);
        return "redirect:/admin/products";
    }

    @RequestMapping("/products")
    public String defaultProductsPage() {

        return "forward:/admin/products/1";
    }

    @RequestMapping("/products/{page}")
    public ModelAndView getProductsPage(@PathVariable("page") Integer page) {
        ModelAndView ret = new ModelAndView();
        ret.setViewName("products_admin");
        ProductsParam productsParam = productsService.getProductsPageParam(page);
        productsParam.setTitle("ADMIN");
        productsParam.setSubTitle("");
        ret.addObject("productsParam", productsParam);

        return ret;
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String defaultSearchResultPage(@RequestParam("searchinfo") String searchInfo) {

        return "forward:/admin/search/1";
    }

    @RequestMapping(value = "/search/{page}", method = RequestMethod.GET)
    public ModelAndView searchResultPage(@RequestParam("searchinfo") String searchInfo, @PathVariable("page") Integer page) {
        ModelAndView ret = new ModelAndView();
        ret.setViewName("searchproducts_admin");
        ProductsParam productsParam = productsService.getSearchResult(searchInfo, page);
        String title = productsParam.getTitle();
        productsParam.setTitle("ADMIN  " + title);
        ret.addObject("productsParam", productsParam);

        return ret;
    }

    @RequestMapping(value = "/passwd", method = RequestMethod.GET)
    public ModelAndView changePasswdPage(HttpServletRequest request) {
        if (request.getSession().getAttribute("username") == null)
            return new ModelAndView("login");
        else {
            return new ModelAndView("changepasswd");
        }
    }

    @RequestMapping(value = "/passwd", method = RequestMethod.POST)
    public ModelAndView loginPagePost(HttpServletRequest request, HttpServletResponse response,
                                      @RequestParam("oldpassword") String oldpassword,
                                      @RequestParam("newpassword") String newpassword,
                                      @RequestParam("newpassword2") String newpassword2) {
        HttpSession session = request.getSession();
        ModelAndView ret = new ModelAndView();

        if (oldpassword == "" || newpassword == "" || newpassword2 == "") {
            ret.addObject("error", "Password can't NULL.");
            ret.setViewName("changepasswd");
            return ret;
        }
        if (!newpassword.equals(newpassword2) || newpassword == "") {
            ret.addObject("error", "diff New Password.");
            ret.setViewName("changepasswd");
            return ret;
        }
        if (oldpassword.equals(newpassword)) {
            ret.addObject("error", "New Password same to Old Password.");
            ret.setViewName("changepasswd");
            return ret;
        }

        String username = (String)session.getAttribute("username");
        if (username == null) {
            ret.setViewName("login");
            return ret;
        }

        if (adminService.checkUserLogin(username, oldpassword)) {
            if (adminService.changePasswd(username, newpassword)) {
                ret.setViewName("put_success");
                ret.addObject("showtext", "Change Password Success.");
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
}