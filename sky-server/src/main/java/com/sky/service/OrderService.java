package com.sky.service;

import com.sky.dto.*;
import com.sky.result.PageResult;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;

public interface OrderService {

    /**
     * 用户下单
     * @param ordersSubmitDTO
     * @return
     */
    OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);
    /**
     * 订单支付
     * @param ordersPaymentDTO
     * @return
     */
    OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception;

    /**
     * 支付成功，修改订单状态
     * @param outTradeNo
     */
    void paySuccess(String outTradeNo);
    //用户端订单分页查询
    PageResult pageQuery4User(int pageNum, int pageSize, Integer status);
    //查询订单详情
    OrderVO details(Long id);
    //用户取消订单
    void userCancelById(Long id);
    //再来一单
    void repetition(Long id);
    //条件搜索订单
    PageResult conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO);
    //各个状态的订单数量统计
    OrderStatisticsVO statistics();
    //商家接单
    void confirm(OrdersConfirmDTO ordersConfirmDTO);
    //商家拒单
    void rejection(OrdersRejectionDTO ordersRejectionDTO);
    //商家取消订单
    void cancel(OrdersCancelDTO ordersCancelDTO);
    //商家派送订单
    void delivery(Long id);
    //商家完成派送订单
    void complete(Long id);
    //用户催单
    void reminder(Long id);
}