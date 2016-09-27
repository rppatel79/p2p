package com.rp.p2p.order_executor;

import com.rp.p2p.model.Order;
import com.rp.p2p.model.OrderConfirmation;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;


public interface OrderExecutor {
    OrderStatus  order(Collection<Order> orders)throws Exception;

    Map<String,Long> getPortfolios() throws Exception;

    class OrderStatus
    {
        private final Set<OrderConfirmation> success_;
        private final Set<OrderConfirmation> failed_;

        public OrderStatus(Set<OrderConfirmation> success, Set<OrderConfirmation> failed) {
            success_ = Collections.unmodifiableSet(success);
            failed_ = Collections.unmodifiableSet(failed);
        }

        public Set<OrderConfirmation> getSuccess() {
            return success_;
        }

        public Set<OrderConfirmation> getFailed() {
            return failed_;
        }
    }


}
