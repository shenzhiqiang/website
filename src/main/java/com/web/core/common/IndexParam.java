package com.web.core.common;

import com.web.core.entity.ProductsTable;

import java.util.List;

/**
 * Created by shenzhiqiang on 16/1/26.
 */
public class IndexParam {
    private String title = "TITLE";
    private String subTitle = "subsubsub title";
    private List<ProductsTable> prod_list;

    public List<ProductsTable> getProd_list() {
        return prod_list;
    }

    public void setProd_list(List<ProductsTable> prod_list) {
        this.prod_list = prod_list;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
