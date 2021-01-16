package com.simple.service.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zhangshaolong001
 * @Date: 2021/1/13 8:00 下午
 * @Description：
 */
public class SecKillUtils {

    /**秒杀用map，活动用布隆过滤*/
    public static Map<Long, Boolean> secKillFlag = new HashMap<>(4);
}
