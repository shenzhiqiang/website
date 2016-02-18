package com.web.core.service;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.web.core.dao.IProductsTableDao;
import com.web.core.dao.IUsersTableDao;
import com.web.core.entity.UsersTable;
import com.web.core.tool.ToolClass;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import com.web.core.tool.ToolClass.*;
/**
 * Created by shenzhiqiang on 16/2/2.
 */

@Service
public class AdminService {
    private static Log logger = LogFactory.getLog(AdminService.class);

    @Resource
    IProductsTableDao iProductsTableDao;
    @Resource
    IUsersTableDao iUsersTableDao;

    public UsersTable getUserByName(String name) {
        UsersTable user = null;
        try {
            user = iUsersTableDao.selectByName(name);
        } catch (Exception e) {
            logger.error("Error: getUserByName; iUsersTableDao.selectByName()", e);
            return null;
        }
        return user;
    }


    public boolean checkUserLogin(String name, String passwd) {
        UsersTable user = null;
        try {
            user = iUsersTableDao.selectByName(name);
        } catch (Exception e) {
            logger.error("Error: checkUserLogin; iUsersTableDao.selectByName()", e);
            return false;
        }
        if (user == null)
            return false;
        if (!ToolClass.StrToMd5L32(passwd).toLowerCase().equals(user.getPasswd().toLowerCase()))
            return false;

        return true;
    }

    public boolean changePasswd(String username, String passwd) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", username);
        params.put("passwd", ToolClass.StrToMd5L32(passwd).toLowerCase());

        try {
            iUsersTableDao.updatePasswd(params);
        } catch (Exception e) {
            logger.error("Error: changePasswd; iUsersTableDao.updatePasswd()", e);
            return false;
        }

        return true;
    }

    public int addProd(Map<String, Object> params) {
        int ret = 0;
        try {
            ret = iProductsTableDao.addProdSimple(params);
        } catch (Exception e) {
            logger.error("Error: addProd; iProductsTableDao.addProdSimple()", e);
        }
        return ret;
    }

    public int delOneProd(Integer id) {
        int ret = 0;
        try {
            ret = iProductsTableDao.delProdById(id);
        } catch (Exception e) {
            logger.error("Error: delOneProd; iProductsTableDao.delProdById()", e);
        }
        return ret;
    }
}
