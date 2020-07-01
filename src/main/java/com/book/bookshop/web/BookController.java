package com.book.bookshop.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.book.bookshop.entity.Book;
import com.book.bookshop.mapper.BookMapper;
import com.book.bookshop.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * controller 通过调用service来完成业务逻辑。
 */
@Controller
@RequestMapping("/home")
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private BookMapper bookMapper;

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    /**
     * 获取图书信息
     */
    @RequestMapping("/getBookData")
    public String getBookData(Model model, Integer page, Integer category){
        //mybatis分页
        QueryWrapper<Book> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category",category);
        IPage<Book> iPage = bookService.page(new Page<>(page,4),queryWrapper);
        model.addAttribute("bookList",iPage.getRecords());
        model.addAttribute("pre",iPage.getCurrent()-1);
        model.addAttribute("next",iPage.getCurrent()+1);
        model.addAttribute("cur",iPage.getCurrent());
        model.addAttribute("last",iPage.getPages());
        model.addAttribute("category",category);
        return "bookData";  //返回的值对应了html页面
    }

    /**
     * 获取图书列表信息
     */
    @RequestMapping("bookList")
    public String bookList(String category, Model model){
        model.addAttribute("category",category);    //将从index.html中发送过来的信息保存到model中
        return "books_list";
    }

    /**
     * 获取图书列表数据
     */
    @RequestMapping("/getBookListData")
    public String getBookListData(Integer page, Integer pageSize ,String category, Model model){
        //mybatis分页
        QueryWrapper<Book> queryWrapper = new QueryWrapper<>();
        System.out.println("category="+category);
        System.out.println("page="+page);
        Integer i = Integer.parseInt(category);
        queryWrapper.eq("category", i);


        IPage<Book> iPage = bookService.page(new Page<Book>(page,pageSize),queryWrapper);
        model.addAttribute("bookList",iPage.getRecords());
        model.addAttribute("pre",iPage.getCurrent()-1);
        model.addAttribute("next",iPage.getCurrent()+1);
        model.addAttribute("cur",iPage.getCurrent());
        model.addAttribute("pages",iPage.getPages());
        model.addAttribute("category",category);
        model.addAttribute("pageSize",pageSize);
        return "booksListData"; //将数据返回到具体的html中来进行数据的渲染
    }

    /**
     * 图书详情信息
     */
    @RequestMapping("/detail")
    public String detail(Integer id, Model model){
        Book book = bookService.getById(id);
        model.addAttribute("book",book);
        return "details";
    }

}
