package cn.heikaqiu.booktt.service;

import cn.heikaqiu.booktt.bean.Order;

import java.util.List;

/**
 * @author HeiKaQiu
 * @create 2020-02-06 13:45
 */
public interface OrderService {
    /**
     * 获取此用户的所有订单信息 但不包含详细
     * @param id
     * @return
     */
    List<Order> getAllOrderByUserId(Integer id);

    /**
     * 删除订单
     * @param orderid
     * @return
     */
    boolean deleteOrder(Long orderid);

    /**
     * 通过userid 与 orderid查找 订单信息
     * @param order_id
     * @param user_id
     * @return
     */
    Order getOrderInfo(Long order_id, Integer user_id);


    /**
     * 查看此用户下的未支付订单如果超时就把订单取消
     */
    void updateOrderStateIfOutTime(Integer userId);

    /**
     * 从未付款订单  完成付款
     * @param order
     * @param order_user_id
     * @param paypassword_pass
     * @return
     */
    Integer toBuyOrder(Long order_id, Integer order_user_id, String paypassword_pass);


    /**
     * 关闭订单
     * @param order
     * @return
     */
    boolean closeOrder(Long order_id);
}