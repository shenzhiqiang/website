package com.web.core.controller;

import com.web.core.service.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by shenzhiqiang on 16/2/2.
 */

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Resource
    AdminService adminService;

    @RequestMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Integer id) {

        int status = adminService.delOneProd(id);
        System.out.println(status);
        return "redirect:/products";
    }
}
