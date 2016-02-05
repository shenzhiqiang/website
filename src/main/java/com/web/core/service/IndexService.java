package com.web.core.service;

import com.web.core.common.IndexParam;
import com.web.core.dao.IProductsTableDao;
import com.web.core.entity.ProductsTable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by shenzhiqiang on 16/1/26.
 */

@Service
public class IndexService {

    @Resource
    IProductsTableDao iProductsTableDao;

    public IndexParam getIndexParam(){
        IndexParam param = new IndexParam();

        List<ProductsTable> prod_list = iProductsTableDao.getTopProd(10);

        for (ProductsTable prod: prod_list) {
            if (prod.getCover_image_url() != null && prod.getCover_image_url().matches("^http://.*"))
                prod.setExtra_info("outer");
            if (prod.getProd_name() != null && prod.getProd_name().length() > 15)
                prod.setProd_name(prod.getProd_name().substring(0,14)+"...");
            if (prod.getProd_introduction() != null && prod.getProd_introduction().length() > 30)
                prod.setProd_introduction(prod.getProd_introduction().substring(0,29)+"...");
        }

        param.setProd_list(prod_list);
        return param;
    }

}
