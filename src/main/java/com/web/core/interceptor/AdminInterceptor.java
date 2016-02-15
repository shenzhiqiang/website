package com.web.core.interceptor;

import com.web.core.entity.UsersTable;
import com.web.core.service.AdminService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by shenzhiqiang on 16/2/5.
 */
public class AdminInterceptor extends HandlerInterceptorAdapter {

    @Resource
    AdminService adminService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String username =  (String)session.getAttribute("username");
        if (username == null) {
            request.getRequestDispatcher("/").forward(request, response);
            return false;
        } else {
            if (session.getAttribute("priority") == null) {
                UsersTable user = adminService.getUserByName(username);
                if (user != null)
                    session.setAttribute("priority", String.valueOf(user.getPriority()));
                else
                    return false;
            }
            if (session.getAttribute("priority").equals("0")) {
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
