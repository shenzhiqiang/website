package com.web.core.interceptor;

import com.web.core.tool.ToolClass;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;
import java.util.UUID.*;

/**
 * Created by shenzhiqiang on 16/3/1.
 */
public class SessionIdInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String sid = ToolClass.getSidFromCookie(request);

        if (!sid.equals("")) {
            return true;
        }

        String newSid = UUID.randomUUID().toString().replace("-","");
        Cookie cookie = new Cookie("sid", newSid);
        cookie.setMaxAge(60 * 60 * 24);
        response.addCookie(cookie);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
