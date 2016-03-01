package com.web.core.interceptor;

import com.web.core.entity.UsersTable;
import com.web.core.kv.RedisClient;
import com.web.core.service.AdminService;
import com.web.core.tool.ToolClass;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by shenzhiqiang on 16/2/5.
 */
public class AdminInterceptor extends HandlerInterceptorAdapter {

    @Resource
    AdminService adminService;
    @Resource
    RedisClient redisClient;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String sid = ToolClass.getSidFromCookie(request);
        Map<String, String> sessionMap = redisClient.getMap(sid);
        String username =  sessionMap.get("username");
        if (username == null) {
            request.getRequestDispatcher("/").forward(request, response);
            return false;
        } else {
            if (sessionMap.get("priority") == null) {
                UsersTable user = adminService.getUserByName(username);
                if (user != null) {
                    sessionMap.put("priority", String.valueOf(user.getPriority()));
                    redisClient.setMap(sid, sessionMap);
                }
                else
                    return false;
            }
            if (sessionMap.get("priority").equals("0")) {
                return true;
            }
            request.getRequestDispatcher("/").forward(request, response);
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }


}
