package com.book.bookshop.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.book.bookshop.entity.Cart;
import com.book.bookshop.entity.CartVo;
import com.book.bookshop.entity.UserCartVo;
import com.book.bookshop.mapper.CartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

@Service
public class CartService extends ServiceImpl<CartMapper, Cart> {
    @Autowired
    private CartMapper cartMapper;

    /**
     * 根据用户查询购物车记录
     */

    public List<CartVo> findCartByUser(Integer userId){
        return cartMapper.findCartListByUserId(userId);
    }

    /**
     * 根据购物车订单好查询购物记录
     */

    public List<CartVo> findCartByCartIds(List<String> ids){
        return cartMapper.findCartListIds(ids);
    }

    /**
     * 总体当前用户购物车的总价钱
     */

    public double getItemTotal(List<CartVo> list){
        DecimalFormat df = new DecimalFormat("#.00");
        double sum = 0.0;
        for (CartVo cartVo : list) {
            sum += cartVo.getCount() * cartVo.getNewPrice();
        }
        return Double.parseDouble(df.format(sum));
    }

    /**
     * 批量删除
     */

    public String batchDelete(String ids){
        if(ids != null){
            String[] idArray = ids.split(",");
            cartMapper.deleteBatchIds(Arrays.asList(idArray));
        }
        return "success";
    }

    /**
     * 包装用户购物车数据
     */
    public UserCartVo wrapperCart(List<CartVo> list){
        UserCartVo userCartVo = new UserCartVo();
        userCartVo.setNum(list.size());
        userCartVo.setTotalPrice(getItemTotal(list));
        return userCartVo;
    }
}
