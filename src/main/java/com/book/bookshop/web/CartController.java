package com.book.bookshop.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.book.bookshop.entity.Cart;
import com.book.bookshop.entity.CartVo;
import com.book.bookshop.entity.User;
import com.book.bookshop.entity.UserCartVo;
import com.book.bookshop.service.CartService;
import com.book.bookshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @ResponseBody
    @RequestMapping("/add")
    public String add(Cart cart, HttpSession session){
        User user = (User)session.getAttribute("user");
        cart.setUserId(user.getId());
        //判断保存之前是否在购物车中已经存在
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",user.getId());
        queryWrapper.eq("book_id",cart.getBookId());
        Cart queryCart = cartService.getOne(queryWrapper);
        if(queryCart == null){
            cartService.save(cart);
        }
        else{
            int num = queryCart.getCount() + cart.getCount();
            queryCart.setCount(num);
            cartService.updateById(queryCart);
        }
        return "success";
    }

    /**
     * 查询当前购物车
     */
    @RequestMapping("/list")
    public String list(HttpSession session, Model model){
        System.out.println("session="+session);
        System.out.println("model="+model);
        User user = (User)session.getAttribute("user");
        List<CartVo> cartVos = cartService.findCartByUser(user.getId());


        //将用户的购物车信息存放到session中，购物车中有多少数量商品，以及总价格
        UserCartVo userCartVo = cartService.wrapperCart(cartVos);
        session.setAttribute("userCartInfo",userCartVo);

        model.addAttribute("cartList",cartVos);
        return "cart";
    }

    /**
     * 更新购物车信息
     */
    @ResponseBody
    @RequestMapping("/update")
    public String update(HttpSession session,Cart cart){
        //更新bs_cart数据表
        cartService.updateById(cart);
        //获取总价
        User user = (User)session.getAttribute("user");
        List<CartVo> cartVos = cartService.findCartByUser(user.getId());

        //将用户的购物车信息存放到session中，购物车中有多少数量商品，以及总价格
        UserCartVo userCartVo = cartService.wrapperCart(cartVos);
        session.setAttribute("userCartInfo",userCartVo);



        double totalPrice = cartService.getItemTotal(cartVos);
        return String.valueOf(totalPrice);
    }

    @ResponseBody
    @RequestMapping("/delete")
    public String delete(String ids){
        return cartService.batchDelete(ids);
    }

}
