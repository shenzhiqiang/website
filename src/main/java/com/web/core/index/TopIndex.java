package com.web.core.index;

import com.web.core.dao.IProductsTableDao;
import com.web.core.entity.ProductsTable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;

/**
 * Created by shenzhiqiang on 16/2/25.
 */

public class TopIndex {

    @Resource
    IProductsTableDao iProductsTableDao;

    private static Log logger = LogFactory.getLog(TopIndex.class);
    private static List<ProductsTable> topIndex = null;

    public TopIndex() {
//        buildIndex();
    }

    public boolean buildIndex() {
        try {
            topIndex = iProductsTableDao.getAllTopProd();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    public boolean isBuild() {
        if (topIndex != null)
            return true;
        else
            return false;
    }

    public List<ProductsTable> getTopIndex() {
        return topIndex;
    }

    public List<ProductsTable> getTopNIndex(int num) {
        List<ProductsTable> resIndex = new ArrayList<ProductsTable>();
        if (num >= 0 && num < topIndex.size()) {
            for (int i = 0; i < num; i++) {
                resIndex.add(topIndex.get(i));
            }
        }
        return resIndex;
    }

    public boolean addProd(Integer id) {
        return buildIndex();
    }

    public boolean delProd(Integer id) {
        return buildIndex();
    }
}
