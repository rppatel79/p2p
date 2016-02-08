package com.rp.p2p.loan;


public interface OrderStatusDao {
    void saveOrderStatus(com.rp.p2p.order_executor.OrderExecutor.OrderStatus orderStatus);
}
