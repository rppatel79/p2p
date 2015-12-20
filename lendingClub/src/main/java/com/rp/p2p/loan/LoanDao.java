package com.rp.p2p.loan;


import com.rp.p2p.model.Order;
import com.rp.p2p.model.OrderConfirmation;
import com.rp.p2p.model.OrderExecutionStatus;
import com.rp.p2p.model.OwnedNote;
import com.rp.p2p.originator.lending_club.restful.LendingClubApi;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.*;

public class LoanDao
{
    private final static Logger logger_ =Logger.getLogger(LoanDao.class);

    public static class OrderStatus
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

    public OrderStatus investLoans(Collection<Order> orders, Collection<OrderConfirmation> orderConfirmations) throws IOException
    {
        Map<Long, Order> requestedOrderMap = new HashMap<Long,Order>();
        for (Order order : orders)
        {
            requestedOrderMap.put(order.getLoanId(),order);
        }

        final Set<OrderConfirmation> success= new HashSet<OrderConfirmation>();
        final Set<OrderConfirmation> failed = new HashSet<OrderConfirmation>();
        for (OrderConfirmation orderConfirmation : orderConfirmations)
        {
            if (orderConfirmation.getExecutionStatus().contains(OrderExecutionStatus.ORDER_FULFILLED))
            {
                success.add(orderConfirmation);
            }
            else
            {
                failed.add(orderConfirmation);
            }
        }

        for (OrderConfirmation orderConfirmation : failed)
        {
            logger_.info("The loan:" + orderConfirmation.getLoanId() + " failed because " + orderConfirmation.getExecutionStatus());
        }

        return new OrderStatus(success,failed);
    }

    public Set<Long> getAllInvestedLoans() throws IOException
    {
        LendingClubApi lendingClubApi = new LendingClubApi();
        Collection<OwnedNote> allOwnedNotes = lendingClubApi.getNotesOwned();
        Set<Long> ret = new HashSet<Long>();
        for (OwnedNote note : allOwnedNotes)
        {
            ret.add(note.getLoanId());
        }

        return ret;
    }
}
