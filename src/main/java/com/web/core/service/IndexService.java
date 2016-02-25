package com.web.core.service;

import com.web.core.common.IndexParam;
import com.web.core.dao.IProductsTableDao;
import com.web.core.entity.ProductsTable;
import com.web.core.index.TopIndex;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by shenzhiqiang on 16/1/26.
 */

@Service
public class IndexService {
    private static Log logger = LogFactory.getLog(IndexService.class);

    @Resource
    IProductsTableDao iProductsTableDao;
    @Resource
    TopIndex topIndex;

    public IndexParam getIndexParam(){
        IndexParam param = new IndexParam();

//        List<ProductsTable> ;
        List<ProductsTable> prod_list = null;
        try {
//            prod_list = iProductsTableDao.getTopProd(10);
            prod_list = topIndex.getTopIndex();
            if (prod_list == null) {
                topIndex.buildIndex();
                prod_list = topIndex.getTopIndex();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        if (prod_list != null) {
            logger.info("getIndexParam(); prod list len:" + String.valueOf(prod_list.size()));

            for (ProductsTable prod : prod_list) {
                if (prod.getCover_image_url() != null && prod.getCover_image_url().matches("^http://.*"))
                    prod.setExtra_info("outer");
                if (prod.getProd_name() != null && prod.getProd_name().length() > 15)
                    prod.setProd_name(prod.getProd_name().substring(0, 14) + "...");
                if (prod.getProd_introduction() != null && prod.getProd_introduction().length() > 30)
                    prod.setProd_introduction(prod.getProd_introduction().substring(0, 29) + "...");
            }
        }
        param.setProd_list(prod_list);
        return param;
    }

}
