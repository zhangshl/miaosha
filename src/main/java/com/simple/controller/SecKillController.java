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
        try {
            int result = secKillService.seckill(id, 1);
            if (result == 1) {
                return "进入提交订单页协议";
            }
        }catch (Exception e){
            e.getMessage();
        }

        return "秒杀失败";
    }
}
