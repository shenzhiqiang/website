package com.web.core.controller;

import com.sina.cloudstorage.services.scs.model.Bucket;
import com.sina.cloudstorage.services.scs.model.ObjectListing;
import com.sina.cloudstorage.services.scs.model.PutObjectResult;
import com.web.core.kv.RedisClient;
import com.web.core.service.AdminService;
import com.web.core.service.IndexService;
import com.web.core.tool.SCSTool;
import com.web.core.tool.ToolClass;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shenzhiqiang on 16/1/25.
 */

@Controller
public class IndexController {
    private static Log logger = LogFactory.getLog(IndexController.class);

    @Resource
    RedisClient redisClient;
    @Resource
    IndexService indexService;
    @Resource
    AdminService adminService;

    @RequestMapping("/test")
    public void test(HttpServletRequest request) {

        System.out.println(request.getServletContext().getRealPath(""));
//        List < Bucket > list = SCSTool.getConn().listBuckets();
//        System.out.println("====getAllBuckets====" + list);
//        PutObjectResult putObjectResult = SCSTool.getConn().putObject("mzx-img",
//                "B.png", new File("/Users/shenzhiqiang/Desktop/website/target/mingzhixuan-1.0-SNAPSHOT/image/6D008352-1E5C-4E4D-B328-4C7E652DF9AB.png"));
//        System.out.println(putObjectResult);

    }

    @RequestMapping("/")
    public ModelAndView indexPage(HttpServletRequest request) {
        ModelAndView ret = new ModelAndView();
        ret.setViewName("index");
        String sid = ToolClass.getSidFromCookie(request);

        System.out.println("/ sid: "+sid);

        Map<String, String> sessionMap = redisClient.getMap(sid);
        ret.addObject("username", sessionMap.get("username"));
        ret.addObject("indexParam", indexService.getIndexParam());

        return ret;
    }

    @RequestMapping("/about_us")
    public ModelAndView aboutPage(HttpServletRequest request) {
        ModelAndView ret = new ModelAndView();
        ret.setViewName("about_us");
        String sid = ToolClass.getSidFromCookie(request);

        Map<String, String> sessionMap = redisClient.getMap(sid);
        ret.addObject("username", sessionMap.get("username"));

        return ret;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView loginPage(HttpServletRequest request) {
        String sid = ToolClass.getSidFromCookie(request);

        if (!redisClient.checkExists(sid))
            return new ModelAndView("login");
        else {
            return new ModelAndView("redirect:/");
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView loginPagePost(HttpServletRequest request, HttpServletResponse response, @RequestParam("username") String username, @RequestParam("passwd") String passwd) {
        ModelAndView ret = new ModelAndView();
        String sid = ToolClass.getSidFromCookie(request);

        if (adminService.checkUserLogin(username, passwd)) {
            Map<String, String> sessionMap = new HashMap<String, String>();
            sessionMap.put("username", username);
            redisClient.setMapWithExpire(sid, sessionMap, 12 * 60 * 60);

            ret.setViewName("put_success");
            ret.addObject("showtext", username);
            ret.addObject("username", username);
            logger.info("login success: " + username);
            return ret;
        } else {
            ret.setViewName("login");
            ret.addObject("error", "Wrong username or passwd.");
            logger.info("login error: " + username);
            return ret;
        }
//        RedirectView redirectView = new RedirectView("/");
//        redirectView.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
//        return new ModelAndView(redirectView);
//        return new ModelAndView("redirect:/");
    }

    @RequestMapping("/logout")
    public ModelAndView logout(HttpServletRequest request) {
        String sid = ToolClass.getSidFromCookie(request);

        redisClient.delMap(sid);

        return new ModelAndView("redirect:/");
    }
}
