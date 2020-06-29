package com.book.bookshop.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.book.bookshop.entity.*;
import com.book.bookshop.mapper.OrderMapper;
import com.book.bookshop.utils.OrderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService extends ServiceImpl<OrderMapper, Order> {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private CartService cartService;

    public String buy(List<CartVo> cartVos, Integer addrId, HttpSession session) {
        //1.生成订单列表
        Order order = new Order();
        order.setAddressId(addrId);
        User user = (User) session.getAttribute("user");
        order.setUserId(user.getId());
        order.setCreateDate(new Date());
        order.setOrderNum(OrderUtils.createOrderNum());
        order.setOrderStatus("1");
        orderMapper.insert(order);
        //2.生成订单明细表记录
        List<OrderItem> orderItems = new ArrayList<>();
        List<Integer> cartIds = new ArrayList<>();
        for (CartVo cartVo : cartVos) {
            OrderItem orderItem = new OrderItem();
            orderItem.setBookId(cartVo.getBookId());
            orderItem.setCount(cartVo.getCount());
            orderItem.setOrderId(order.getId());
            orderItems.add(orderItem);
            cartIds.add(cartVo.getId());
        }
        orderItemService.saveBatch(orderItems);
        //3.删除购物车中的商品
        cartService.removeByIds(cartIds);
        return "success";
    }

    /**
     * 查询用户订单
     * @param queryVo
     * @return
     */
    public List<Order> findOrderByUser(OrderQueryVo queryVo){
        Integer begin = (queryVo.getPage() - 1) * queryVo.getPageSize();
        queryVo.setBegin(begin);
        List<Order> list = orderMapper.findOrderAndOrderDetailListByUser(queryVo);
        for (Order order : list) {
            List<OrderItem> orderItems = order.getOrderItems();
            double price = 0.0;
            for (OrderItem orderItem : orderItems) {
                price += orderItem.getCount() * orderItem.getBook().getNewPrice();
            }
            order.setTotalPrice(price); //计算订单总金额
        }
        return list;
    }

    /**
     * 查询总页数
     */
    public int findUserOrderPages(OrderQueryVo queryVo){
        int count = orderMapper.findOrderCountByUser(queryVo);
        if(count == 0) return 1;
        return count % queryVo.getPageSize() == 0 ? count/queryVo.getPageSize() : count/queryVo.getPageSize()+1;
    }

}
