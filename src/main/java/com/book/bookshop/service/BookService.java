package com.book.bookshop.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.book.bookshop.entity.Book;
import com.book.bookshop.mapper.BookMapper;
import org.springframework.stereotype.Service;

/**
 * service层包含了serviceimpl(service接口实现类),是提供给controller使用的。
 * 针对于某些业务将 dao 的对于某些表的crud进行组合，也就是说间接的和数据库打交道。
 */

/**
 *图书业务层
 */
@Service
public class BookService extends ServiceImpl<BookMapper, Book> {


}
