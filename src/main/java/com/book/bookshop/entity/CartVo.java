package com.book.bookshop.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class CartVo {

    public Integer id;
    public Integer userId;
    public Integer bookId;
    public Integer count;
    public String bookName;
    public String imgUrl;
    public double newPrice;
}
