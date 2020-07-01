package com.book.bookshop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.book.bookshop.entity.Book;
import org.springframework.stereotype.Repository;

/**
 * 1、mapper 是Mybatis 操作数据库的那一层，就是dao层。
 */

/**
 * 图书借口
 */
@Repository
public interface BookMapper extends BaseMapper<Book> {

}
