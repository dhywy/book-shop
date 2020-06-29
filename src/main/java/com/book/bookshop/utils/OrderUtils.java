package com.book.bookshop.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 订单工具类
 */
public class OrderUtils {
    private static long orderNum = 0l;
    private static String date;

    /**
     * 生成订单号
     */
    public static synchronized String createOrderNum(){
        String str = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
        if(date == null || !date.equals(str)){
            date = str;
            orderNum = 0l;  //每次从0开始
        }
        orderNum++;
        long orderNo = Long.parseLong(str) * 10000;
        orderNo += orderNum;
        return String.valueOf(orderNo);
    }
}
