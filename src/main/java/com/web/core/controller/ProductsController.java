package com.web.core.controller;

import com.web.core.common.ProductParam;
import com.web.core.common.ProductsParam;
import com.web.core.kv.RedisClient;
import com.web.core.service.ProductsService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by shenzhiqiang on 16/1/26.
 */

@Controller
public class ProductsController {
    private static Log logger = LogFactory.getLog(ProductsController.class);

    @Resource
    ProductsService productsService;
    @Resource
    RedisClient redisClient;

    @RequestMapping("/products")
    public String defaultProductsPage() {

        return "forward:/products/1";
    }

    @RequestMapping("/products/{page}")
    public ModelAndView getProductsPage(@PathVariable("page") Integer page, HttpServletRequest request) {
        ModelAndView ret = new ModelAndView();
        ret.setViewName("products");
        ret.addObject("productsParam", productsService.getProductsPageParam(page));

        HttpSession session = request.getSession();
        Map<String, String> sessionMap = redisClient.getMap(session.getId());
        ret.addObject("username", sessionMap.get("username"));

        logger.info("Products. Page: " + page);
        return ret;
    }

    @RequestMapping("/product/{id}")
    public ModelAndView productPage(@PathVariable("id") Integer id, HttpServletRequest request) {
        ModelAndView ret = new ModelAndView();
        ret.setViewName("single");
        ProductParam productParam = productsService.getProductParam(id);
        ret.addObject("productParam", productParam);

        HttpSession session = request.getSession();
        Map<String, String> sessionMap = redisClient.getMap(session.getId());
        ret.addObject("username", sessionMap.get("username"));

        logger.info("Product. ID: " + id);

        return ret;
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String defaultSearchResultPage(@RequestParam("searchinfo") String searchInfo) {

        return "forward:/search/1";
    }

    @RequestMapping(value = "/search/{page}", method = RequestMethod.GET)
    public ModelAndView searchResultPage(@RequestParam("searchinfo") String searchInfo, @PathVariable("page") Integer page, HttpServletRequest request) {
        ModelAndView ret = new ModelAndView();
        ret.setViewName("searchproducts");
        ProductsParam productsParam = productsService.getSearchResult(searchInfo, page);
        ret.addObject("productsParam", productsParam);

        HttpSession session = request.getSession();
        Map<String, String> sessionMap = redisClient.getMap(session.getId());
        ret.addObject("username", sessionMap.get("username"));

        logger.info("Search: " + searchInfo + ". Page: " + page);
        return ret;
    }

}