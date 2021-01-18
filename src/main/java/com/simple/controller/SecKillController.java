package com.simple.controller;

import com.simple.service.SecKillService;
import com.simple.service.limit.ApiLimit;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zhangshl
 */
@Controller
@RequestMapping("/app")
public class SecKillController {

    @Autowired
    private SecKillService secKillService;

    /**
     * 秒杀入口
     *
     * @param id
     * @return
     */
    @ApiLimit
    @RequestMapping("/secKill")
    @ResponseBody
    public String secKill(@Param("id") Long id) {
        int result = secKillService.seckill(id, 1);
        if (result == 1) {
            return "秒杀成功，进入订单页";
        }
        return "秒杀失败";
    }

    //TODO 延迟不付款后15分钟回滚库存
    /**
     * rocketmq事务消息，不支持延时和批量消息，需要在本地记录状态，并发送事务消息后，再发送另一个延时消息的topic
     * 以实现库存的回滚
     */
}
