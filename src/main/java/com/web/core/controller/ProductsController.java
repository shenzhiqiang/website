package com.web.core.controller;

import com.web.core.common.ProductParam;
import com.web.core.common.ProductsParam;
import com.web.core.service.ProductsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * Created by shenzhiqiang on 16/1/26.
 */

@Controller
public class ProductsController {
    @Resource
    ProductsService productsService;

    @RequestMapping("/products")
    public String defaultProductsPage() {

        return "forward:/products/1";
    }

    @RequestMapping("/products/{page}")
    public ModelAndView getProductsPage(@PathVariable("page") Integer page) {
        ModelAndView ret = new ModelAndView();
        ret.setViewName("products");
        ret.addObject("productsParam", productsService.getProductsPageParam(page));

        return ret;
    }

    @RequestMapping("/product/{id}")
    public ModelAndView productPage(@PathVariable("id") Integer id) {
        ModelAndView ret = new ModelAndView();
        ret.setViewName("single");
        ProductParam productParam = productsService.getProductParam(id);
        ret.addObject("productParam", productParam);

        return ret;
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String defaultSearchResultPage(@RequestParam("searchinfo") String searchInfo) {

        return "forward:/search/1";
    }

    @RequestMapping(value = "/search/{page}", method = RequestMethod.GET)
    public ModelAndView searchResultPage(@RequestParam("searchinfo") String searchInfo, @PathVariable("page") Integer page) {
        ModelAndView ret = new ModelAndView();
        ret.setViewName("searchproducts");
        ProductsParam productsParam = productsService.getSearchResult(searchInfo, page);
        ret.addObject("productsParam", productsParam);

        return ret;
    }

}