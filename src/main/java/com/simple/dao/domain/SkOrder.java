package com.simple.dao.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhangshl
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SkOrder {
    private Long id;

    private Long userId;

    private Long orderId;

    private Long goodsId;
}