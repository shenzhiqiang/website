package com.web.core.controller;

import com.web.core.service.AdminService;
import com.web.core.service.IndexService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by shenzhiqiang on 16/1/25.
 */

@Controller
public class IndexController {

    @Resource
    IndexService indexService;
    @Resource
    AdminService adminService;

    @RequestMapping("/")
    public ModelAndView indexPage(HttpServletRequest request) {
        ModelAndView ret = new ModelAndView();
        ret.setViewName("index");

        HttpSession session = request.getSession();
        ret.addObject("username", session.getAttribute("username"));
        ret.addObject("indexParam", indexService.getIndexParam());

        return ret;
    }

    @RequestMapping("/about_us")
    public ModelAndView aboutPage(HttpServletRequest request) {
        ModelAndView ret = new ModelAndView();
        ret.setViewName("about_us");

        HttpSession session = request.getSession();
        ret.addObject("username", session.getAttribute("username"));
        return ret;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView loginPage(HttpServletRequest request) {
        if (request.getSession().getAttribute("username") == null)
            return new ModelAndView("login");
        else {
            return new ModelAndView("redirect:/");
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView loginPagePost(HttpServletRequest request, HttpServletResponse response, @RequestParam("username") String username, @RequestParam("passwd") String passwd) {
        HttpSession session = request.getSession();
        ModelAndView ret = new ModelAndView();

        if (adminService.checkUserLogin(username, passwd)) {
            ret.setViewName("login_success");
            ret.addObject("username", username);
            session.setAttribute("username", username);
            return ret;
        } else {
            ret.setViewName("login");
            ret.addObject("error", "Wrong username or passwd.");
            return ret;
        }
//        RedirectView redirectView = new RedirectView("/");
//        redirectView.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
//        return new ModelAndView(redirectView);
//        return new ModelAndView("redirect:/");
    }

    @RequestMapping("/logout")
    public ModelAndView logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("username", null);

        return new ModelAndView("redirect:/");
    }
}
