package com.web.core.service;

import java.io.File;
import java.security.MessageDigest;
import java.util.*;

import com.sina.cloudstorage.services.scs.model.PutObjectResult;
import com.web.core.dao.IProductsTableDao;
import com.web.core.dao.IUsersTableDao;
import com.web.core.entity.ProductsTable;
import com.web.core.entity.UsersTable;
import com.web.core.index.TopIndex;
import com.web.core.tool.MQ.MQDelProducer;
import com.web.core.tool.SCSTool;
import com.web.core.tool.ToolClass;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import com.web.core.tool.ToolClass.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by shenzhiqiang on 16/2/2.
 */

@Service
public class AdminService {
    private static Log logger = LogFactory.getLog(AdminService.class);

    @Resource
    MQDelProducer mqDelProducer;
    @Resource
    IProductsTableDao iProductsTableDao;
    @Resource
    IUsersTableDao iUsersTableDao;
    @Resource
    TopIndex topIndex;

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

    public int updateProd(Map<String, Object> params) {
        int ret = 0;
        try {
            ProductsTable productsTable = iProductsTableDao.selectDelOneById((Integer) params.get("id"));
            String originalImgUrls = productsTable.getImage_urls();
            String imgUrls = "";
            if (originalImgUrls.equals(""))
                imgUrls = (String) params.get("image_urls");
            else
                imgUrls = originalImgUrls + ";" + (String) params.get("image_urls");
            params.put("image_urls", imgUrls);

            ret = iProductsTableDao.updateProd(params);

//            ret = iProductsTableDao.(params);
        } catch (Exception e) {
            logger.error("Error: updateProd; iProductsTableDao", e);
        }
        return ret;
    }

    public int delOneProd(Integer id) {
        int ret = 0;
        try {
            ret = iProductsTableDao.delProdById(id);
            HashMap message = new HashMap();
            message.put("del_id", id);
            mqDelProducer.sendMessage(message, "del_prod");

        } catch (Exception e) {
            logger.error("Error: delOneProd; iProductsTableDao.delProdById()", e);
        }
        return ret;
    }

    public int setCover(Integer id, String cover_img) {
        Map<String, Object> params = new HashMap<String, Object>();

        params.put("id", id);
        params.put("cover_image_url", cover_img);

        int ret = 0;
        try {
            ret = iProductsTableDao.setCover(params);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return ret;
    }

    public int delProdImg(Integer id, Integer img) {
        Map<String, Object> params = new HashMap<String, Object>();

        params.put("id", id);

        int ret = 0;
        try {
            ProductsTable productsTable = iProductsTableDao.selectDelOneById(id);
            String originalImgUrls = productsTable.getImage_urls();
            if (originalImgUrls.equals(""))
                return 0;

            String[] imgUrlArray = originalImgUrls.split(";");
            String imgUrls = "";
            for (int i = 0; i < imgUrlArray.length; i++) {
                if (i != img)
                    if (imgUrls.equals(""))
                        imgUrls += imgUrlArray[i];
                    else
                        imgUrls += ";" + imgUrlArray[i];
            }

            params.put("image_urls", imgUrls);
            if (imgUrlArray[img].equals(productsTable.getCover_image_url()))
                params.put("cover_image_url", "");

            ret = iProductsTableDao.updateImg(params);

            HashMap msg = new HashMap();
            msg.put("del_url", imgUrlArray[img]);
            mqDelProducer.sendMessage(msg, "del_img_key");

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return ret;
    }

    public int updateTop(int status, Integer id) {
        int ret = 0;
        try {
            ret = iProductsTableDao.updateIsTop(status, id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        if (status > 0)
            topIndex.addProd(id);
        else if (status == 0)
            topIndex.delProd(id);

        return ret;
    }

    public void addImgs(MultipartFile[] files, Map<String, Object> params, String path) {
        String image_urls = "";
        String cover_image_url = "";

        if (files != null && files.length > 0) {
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
                        logger.error("File save err. ", e);
                    }

                    if (putObjectResult == null) {
                        logger.error("File upload fail. " + filename);
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
    }

    public void updateImgs(MultipartFile[] files, Map<String, Object> params, String path) {
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
                        logger.error("File save err. ", e);
                    }

                    if (putObjectResult == null) {
                        logger.error("File upload fail. " + filename);
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
    }
}