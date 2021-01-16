package com.simple.controller;

import com.simple.dao.domain.SkGoods;
import com.simple.service.SecKillService;
import com.simple.service.limit.ApiLimit;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

@Controller
@RequestMapping("/app")
public class TestController {

    @Autowired
    private SecKillService secKillService;

    @RequestMapping("/test")
    @ResponseBody
    public String testDemo() {
        return "Hello World!";
    }


//    @ApiLimit
    @RequestMapping("/queryProduct")
    @ResponseBody
    public SkGoods queryProduct(@Param("id") Long id) {
        return secKillService.selectByPrimaryKey(id);
    }

    @RequestMapping("/secKill")
    @ResponseBody
    public String secKill(@Param("id") Long id) {
        int result = secKillService.seckill(id, 1);
        if (result == 1){
            return "订单生成中。。。";
        }
        return "秒杀失败";
    }

    @RequestMapping("/insert")
    @ResponseBody
    public int insert(@Param("id") Long id) {
        SkGoods skGoods = SkGoods.builder().goodsTitle("苹果手机").goodsName("iPhone12").goodsPrice(new BigDecimal(1)).goodsStock(100).build();
        return secKillService.insert(skGoods);
    }

    //TODO 延迟不付款后15分钟回滚库存
    /**
     * rocketmq事务消息，不支持延时和批量消息，需要在本地记录状态，并发送事务消息后，再发送另一个延时消息的topic
     * 以实现库存的回滚
     */
}
