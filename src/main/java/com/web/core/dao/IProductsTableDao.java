package com.web.core.dao;

import com.web.core.entity.ProductsTable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by shenzhiqiang on 16/1/26.
 */

@Repository
public interface IProductsTableDao {

    Integer getRowCount();
    Integer getSearchRowCount(String searchInfo);
    List<ProductsTable> getAllProd();
    List<ProductsTable> getPageProd(Map<String, Object> pageParam);
    List<ProductsTable> getTopProd(int num);
    ProductsTable selectById(Integer id);
    List<ProductsTable> allSearchResult(String searchInfo);
    List<ProductsTable> searchResult(Map<String, Object> searchInfo);

    int addProdSimple(Map<String, Object> params);
    int delOneById(Integer id);

}
