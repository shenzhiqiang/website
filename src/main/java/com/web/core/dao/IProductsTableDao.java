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
    List<ProductsTable> getAllTopProd();

    ProductsTable selectById(Integer id);
    ProductsTable selectDelOneById(Integer id);
    List<ProductsTable> allSearchResult(String searchInfo);
    List<ProductsTable> searchResult(Map<String, Object> searchInfo);

    int addProdSimple(Map<String, Object> params);
    int delOneById(Integer id);
    int delProdById(Integer id);

    int updateProd(Map<String, Object> params);
    int setCover(Map<String, Object> params);
    int updateImg(Map<String, Object> params);

    int updateIsTop(int status, Integer id);
}
