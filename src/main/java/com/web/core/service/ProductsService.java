package com.web.core.service;

import com.web.core.common.ImageType;
import com.web.core.common.ProductParam;
import com.web.core.common.ProductsParam;
import com.web.core.dao.IProductsTableDao;
import com.web.core.entity.ProductsTable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by shenzhiqiang on 16/1/26.
 */

@Service
public class ProductsService {
    @Resource
    IProductsTableDao iProductsTableDao;

    public ProductParam getProductParam(Integer id){
        ProductsTable prod = iProductsTableDao.selectById(id);

        ProductParam param;
        if (prod != null) {
             param = new ProductParam();
        } else {
            return null;
        }

        if (prod.getImage_urls() != null) {
            List<ImageType> imgList = new ArrayList<ImageType>();

            String[] img_urls = prod.getImage_urls().split(";");
            List<String> img_url_list = Arrays.asList(img_urls);
            for (String url : img_url_list) {
                ImageType imgType = new ImageType();
                imgType.setImg_url(url);
                if (url.matches("^http://.*"))
                    imgType.setImg_type("outer");
                else
                    imgType.setImg_type("local");
                imgList.add(imgType);
            }
            param.setImg_list(imgList);
        }

        param.setProd_table(prod);
        if (id > 1)
            param.setPrevId(id-1);
        param.setNextId(id + 1);
//        param.setCount(iProductsTableDao.getRowCount());
        return param;
    }

    public ProductsParam getProductsPageParam(int page) {
        ProductsParam param = new ProductsParam();

        Map<String, Object> sqlParam = new HashMap<String, Object>();
        int offset = (page - 1) * param.getPageSize();
        int size = param.getPageSize();
        sqlParam.put("offset", offset);
        sqlParam.put("size", size);
        List<ProductsTable> prod_list = iProductsTableDao.getPageProd(sqlParam);

        for (ProductsTable prod: prod_list) {
            if (prod.getCover_image_url() != null && prod.getCover_image_url().matches("^http://.*")) {
                prod.setExtra_info("outer");
                String[] imgUrlSplit = prod.getCover_image_url().split("\\.");
                String imgCoverUrl;
                try {
                    imgCoverUrl = imgUrlSplit[0] + ".imgx." + imgUrlSplit[1] + "." + imgUrlSplit[2].split("/")[0] + "/c_pad,h_260,w_360,g_center,b_dddddd/" + imgUrlSplit[2].split("/")[1] + "." + imgUrlSplit[3];
                    prod.setCover_image_url(imgCoverUrl);
                } catch (Exception e) {
                    //


                }
            }
            if (prod.getProd_name() != null && prod.getProd_name().length() > 15)
                prod.setProd_name(prod.getProd_name().substring(0,14)+"...");
            if (prod.getProd_introduction() != null && prod.getProd_introduction().length() > 30)
                prod.setProd_introduction(prod.getProd_introduction().substring(0,29)+"...");
        }

        param.setCurrPage(page);
        param.setRowCount(iProductsTableDao.getRowCount());
        param.setProd_list(prod_list);
        return param;
    }

    public ProductsParam getSearchResult(String searchInfo, int page) {
        ProductsParam param = new ProductsParam();

        Map<String, Object> sqlParam = new HashMap<String, Object>();
        int offset = (page - 1) * param.getPageSize();
        int size = param.getPageSize();
        sqlParam.put("offset", offset);
        sqlParam.put("size", size);
        sqlParam.put("searchInfo", searchInfo);
        List<ProductsTable> prod_list = iProductsTableDao.searchResult(sqlParam);

        for (ProductsTable prod: prod_list) {
            if (prod.getCover_image_url() != null && prod.getCover_image_url().matches("^http://.*")) {
                prod.setExtra_info("outer");
                String[] imgUrlSplit = prod.getCover_image_url().split("\\.");
                String imgCoverUrl;
                try {
                    imgCoverUrl = imgUrlSplit[0] + ".imgx." + imgUrlSplit[1] + "." + imgUrlSplit[2].split("/")[0] + "/c_pad,h_260,w_360,g_center,b_dddddd/" + imgUrlSplit[2].split("/")[1] + "." + imgUrlSplit[3];
                    prod.setCover_image_url(imgCoverUrl);
                } catch (Exception e) {
                    //


                }
            }
            if (prod.getProd_name() != null && prod.getProd_name().length() > 15)
                prod.setProd_name(prod.getProd_name().substring(0,14)+"...");
            if (prod.getProd_introduction() != null && prod.getProd_introduction().length() > 30)
                prod.setProd_introduction(prod.getProd_introduction().substring(0,29)+"...");
        }

        param.setTitle("Search \"" + searchInfo + "\":");
        if (prod_list.size() == 0)
            param.setSubTitle("No result match " + searchInfo + ".");
        else
            param.setSubTitle(searchInfo);

        param.setCurrPage(page);
        param.setRowCount(iProductsTableDao.getSearchRowCount(searchInfo));
        param.setProd_list(prod_list);

        return param;
    }
}
