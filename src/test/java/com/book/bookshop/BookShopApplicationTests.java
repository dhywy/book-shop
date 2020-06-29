package com.book.bookshop;

import com.book.bookshop.entity.OrderQueryVo;
import com.book.bookshop.mapper.OrderMapper;
import com.book.bookshop.service.BookService;
import com.book.bookshop.service.CartService;
import com.book.bookshop.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest
class BookShopApplicationTests {
    @Autowired
    private BookService bookService;
    @Autowired
    private CartService cartService;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderService orderService;

    @Test
    public void findBookList() {
        bookService.list().forEach(System.out::println);
    }

    @Test
    public void findCartByUser(){
        cartService.findCartByUser(1).forEach(System.out::println);
    }

    @Test
    public void find(){
        OrderQueryVo queryVo = new OrderQueryVo();
        queryVo.setUserId(2);
//        System.out.println(orderMapper.findOrderCountByUser(queryVo));
        System.out.println(orderService.findUserOrderPages(queryVo));
    }
}
