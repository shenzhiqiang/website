package com.web.core.common;

import com.web.core.entity.ProductsTable;

import java.util.List;

/**
 * Created by shenzhiqiang on 16/1/26.
 */
public class ProductParam {
    private ProductsTable prod_table;
    private Integer count;
    private List<ImageType> img_list;
    private Integer prevId;
    private Integer nextId;

    public ProductsTable getProd_table() {
        return prod_table;
    }

    public void setProd_table(ProductsTable prod_table) {
        this.prod_table = prod_table;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<ImageType> getImg_list() {
        return img_list;
    }

    public void setImg_list(List<ImageType> img_list) {
        this.img_list = img_list;
    }

    public Integer getPrevId() {
        return prevId;
    }

    public void setPrevId(Integer prevId) {
        this.prevId = prevId;
    }

    public Integer getNextId() {
        return nextId;
    }

    public void setNextId(Integer nextId) {
        this.nextId = nextId;
    }
}
