package com.book.bookshop.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.book.bookshop.entity.*;
import com.book.bookshop.service.AddressService;
import com.book.bookshop.service.CartService;
import com.book.bookshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private CartService cartService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private OrderService orderService;
    /**
     * 确认订单
     * @param ids
     * @return
     */
    @RequestMapping("/confirm")
    public String confirm(HttpSession session,String ids, Model model){
        String[] str = ids.split(",");
        int size = str.length;
        List<CartVo> cartVos = cartService.findCartByCartIds(Arrays.asList(ids.split(",")));
        model.addAttribute("cartVoList",cartVos);
        //将商品信息添加到session中
        session.setAttribute("confirm_cartVo",cartVos);

        //获取某用户设置的收货地址
        QueryWrapper<Address> addressQueryWrapper = new QueryWrapper<>();
        User user = (User) session.getAttribute("user");
        addressQueryWrapper.eq("user_id",user.getId());
        List<Address> addressList = addressService.list(addressQueryWrapper);
        //将收货地址保存草session中
        session.setAttribute("addressList",addressList);
        //将用户的购物车信息存放到session中，购物车中有多少数量商品，以及总价格
        UserCartVo userCartVo = cartService.wrapperCart(cartVos);
        session.setAttribute("confrimInfo",userCartVo);

        return "confirm_order";
    }

    /**
     * 订单提交
     */
    @RequestMapping("/commitOrder")
    public String commitOrder(HttpSession session,Integer addrId){
        List<CartVo> cartVos = (List<CartVo>) session.getAttribute("confirm_cartVo");
        String flag = orderService.buy(cartVos,addrId,session);
        if(flag.equals("success")){
            return "redirect:/order/list";
        }else {
            return "redirect:/home/index";
        }
    }

    /**
     * 订单列表展示
     */
    @RequestMapping("/list")
    public String list(HttpSession session,Model model){
        System.out.println("this is list");
        return "order_list";
    }

    /**
     * 获取用户订单信息
     */
    @RequestMapping("/getOrderListData")
    public String getOrderListData(HttpSession session,OrderQueryVo queryVo, Model model){
        User user = (User) session.getAttribute("user");
        queryVo.setUserId(user.getId());
        List<Order> orders = orderService.findOrderByUser(queryVo);
        model.addAttribute("orders",orders);
        model.addAttribute("cur",queryVo.getPage());
        model.addAttribute("pre",queryVo.getPage()-1);
        model.addAttribute("next",queryVo.getPage()+1);
        model.addAttribute("pages",orderService.findUserOrderPages(queryVo));   //由pageSize来确定
        model.addAttribute("pageSize",queryVo.getPageSize());
        return "orderData";
    }
}
