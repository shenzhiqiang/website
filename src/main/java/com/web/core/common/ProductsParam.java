package com.web.core.common;

import com.web.core.entity.ProductsTable;

import java.util.List;

/**
 * Created by shenzhiqiang on 16/1/26.
 */
public class ProductsParam {
    private String title = "PROD TITLE";
    private String subTitle = "subsubsub title";
    private List<ProductsTable> prod_list;
    private int currPage ; // 当前页
    private int totalPage ; // 总页
    private int rowCount ; // 总记录数
    public static int pageSize = 15; // 页大小

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public List<ProductsTable> getProd_list() {
        return prod_list;
    }

    public void setProd_list(List<ProductsTable> prod_list) {
        this.prod_list = prod_list;
    }

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        int totalPage = rowCount / pageSize;
        if (rowCount % pageSize > 0) {
            totalPage += 1;
        }
        setTotalPage(totalPage);
        this.rowCount = rowCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
