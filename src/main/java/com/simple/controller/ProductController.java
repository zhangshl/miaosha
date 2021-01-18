package com.simple.controller;

import com.simple.dao.domain.SkGoods;
import com.simple.service.ProductService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

/**
 * @author zhangshl
 */
@Controller
@RequestMapping("/app")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * 查询秒杀商品
     *
     * @param id
     * @return
     * @ApiLimit 为本地限流
     */
    @RequestMapping("/queryProduct")
    @ResponseBody
    public SkGoods queryProduct(@Param("id") Long id) {
        return productService.selectByPrimaryKey(id);
    }

    /**
     * 增加商品
     *
     * @param id
     * @return
     */
    @RequestMapping("/insertProduct")
    @ResponseBody
    public int insertProduct(@Param("id") Long id) {
        SkGoods skGoods = SkGoods.builder().goodsTitle("苹果手机").goodsName("iPhone12").goodsPrice(new BigDecimal(1)).goodsStock(100).build();
        return productService.insert(skGoods);
    }

    /**
     * 重置商品库存
     *
     * @param id
     * @return
     */
    @RequestMapping("/reset")
    @ResponseBody
    public SkGoods reset(@Param("id") Long id) {
        return productService.selectByPrimaryKey(id);
    }

}
