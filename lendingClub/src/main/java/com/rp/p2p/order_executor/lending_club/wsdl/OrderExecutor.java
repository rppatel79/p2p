package com.rp.p2p.order_executor.lending_club.wsdl;


import com.rp.p2p.loan.db.OrderStatusDao;
import com.rp.p2p.model.*;
import com.rp.p2p.originator.lending_club.restful.LendingClubApi;
import com.rp.p2p.originator.OriginatorApi;
import com.rp.util.db.HibernateUtil;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.*;

public class OrderExecutor implements com.rp.p2p.order_executor.OrderExecutor
{
    private final static Logger logger_ =Logger.getLogger(OrderExecutor.class);

    @Override
    public OrderStatus order(Collection<Order> orders)throws Exception
    {
        OriginatorApi originatorApi = new LendingClubApi();
        OrderInstructConfirmation orderInstructConfirmation = originatorApi.orderSubmitOrders(orders);
        OrderStatus orderStatus = investLoans(orders, orderInstructConfirmation.getOrderConfirmations());
        (new OrderStatusDao()).save(orderStatus);


        return orderStatus;
    }

    private OrderStatus investLoans(Collection<Order> orders, Collection<OrderConfirmation> orderConfirmations) throws IOException
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

    @Override
    public Map<String,Long> getPortfolios() throws Exception {
        OriginatorApi originatorApi = new LendingClubApi();
        return originatorApi.orderGetPortfolios();
    }

    public BrowseLoansResult getBrowseLoansResult() throws Exception {
        OriginatorApi originatorApi = new LendingClubApi();
        return originatorApi.getBrowseLoansResult(true);
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 1 )
        {
            usage();
            return;
        }

        try
            {
            int argIdx = 0;
            String cmd = args[argIdx++];

            if ("execute".equals(cmd)) {
                long loanId = Long.valueOf(args[argIdx++]);
                double amount = Double.valueOf(args[argIdx++]);

                Long portfolioId = null;
                if (args.length >= 4)
                    portfolioId = Long.valueOf(args[argIdx++]);

                Order order = new Order();
                {
                    order.setLoanId(loanId);
                    order.setRequestedAmount(amount);
                    order.setPortfolioId(portfolioId);
                }

                com.rp.p2p.order_executor.OrderExecutor orderExecutor = new com.rp.p2p.order_executor.lending_club.wsdl.OrderExecutor();
                orderExecutor.order(Collections.singleton(order));
            } else if ("getPortfolios".equals(cmd)) {
                com.rp.p2p.order_executor.OrderExecutor orderExecutor = new OrderExecutor();
                Map<String, Long> portfolios = orderExecutor.getPortfolios();
                for (Map.Entry<String, Long> entry : portfolios.entrySet()) {
                    logger_.info(entry.getKey() + " -> " + entry.getValue());
                }
            } else if ("browseLoansResult".equals(cmd)) {
                OrderExecutor orderExecutor = new OrderExecutor();
                BrowseLoansResult browseLoansResult = orderExecutor.getBrowseLoansResult();

                for (LoanListing loanListing : browseLoansResult.getLoans()) {
                    logger_.info(loanListing);
                }
            }
        }
        finally {
            HibernateUtil.shutdownAll();
        }
    }

    private static void usage() {
        System.err.println(OrderExecutor.class.getName()+" <execute <loanId> <amount> <|portfolioId>|getPortfolios>");
    }
}
