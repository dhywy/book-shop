package com.book.bookshop.entity;

import lombok.Data;

/**
 *用户购物车展示信息
 */
@Data
public class UserCartVo {
    private Integer num;
    private double totalPrice;
}
