package com.rp.p2p.order_executor;

import com.rp.p2p.loan.LoanDao;
import com.rp.p2p.model.Order;

import java.util.Collection;
import java.util.Map;


public interface OrderExecutor {
    LoanDao.OrderStatus  order(Collection<Order> orders)throws Exception;

    Map<String,Long> getPortfolios() throws Exception;
}
