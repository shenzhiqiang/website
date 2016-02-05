package com.web.core.service;

import java.security.MessageDigest;
import com.web.core.dao.IProductsTableDao;
import com.web.core.dao.IUsersTableDao;
import com.web.core.entity.UsersTable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by shenzhiqiang on 16/2/2.
 */

@Service
public class AdminService {

    @Resource
    IProductsTableDao iProductsTableDao;
    @Resource
    IUsersTableDao iUsersTableDao;

    public UsersTable getUserByName(String name) {
        UsersTable user = iUsersTableDao.selectByName(name);
        return user;
    }

    public String StrToMd5L32(String str){
        String reStr = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(str.getBytes());
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : bytes){
                int bt = b&0xff;
                if (bt < 16){
                    stringBuffer.append(0);
                }
                stringBuffer.append(Integer.toHexString(bt));
            }
            reStr = stringBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reStr;
    }

    public boolean checkUserLogin(String name, String passwd) {
        UsersTable user = iUsersTableDao.selectByName(name);
        if (user == null)
            return false;
        if (!StrToMd5L32(passwd).toLowerCase().equals(user.getPasswd().toLowerCase()))
            return false;

        return true;
    }

    public int delOneProd(Integer id) {
        return iProductsTableDao.delOneById(id);
    }
}
