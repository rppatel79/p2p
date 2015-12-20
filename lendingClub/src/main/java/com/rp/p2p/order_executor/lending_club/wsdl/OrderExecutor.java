package com.rp.p2p.order_executor.lending_club.wsdl;


import com.rp.p2p.loan.LoanDao;
import com.rp.p2p.model.BrowseLoansResult;
import com.rp.p2p.model.LoanListing;
import com.rp.p2p.model.Order;
import com.rp.p2p.model.OrderInstructConfirmation;
import com.rp.p2p.originator.lending_club.restful.LendingClubApi;
import com.rp.p2p.originator.OriginatorApi;
import org.apache.log4j.Logger;

import java.util.*;

public class OrderExecutor implements com.rp.p2p.order_executor.OrderExecutor
{
    private final static Logger logger_ =Logger.getLogger(OrderExecutor.class);

    @Override
    public LoanDao.OrderStatus order(Collection<Order> orders)throws Exception
    {
        OriginatorApi originatorApi = new LendingClubApi();
        final LoanDao loanDao = new LoanDao();
        OrderInstructConfirmation orderInstructConfirmation = originatorApi.orderSubmitOrders(orders);
        return loanDao.investLoans(orders, orderInstructConfirmation.getOrderConfirmations());
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

        int argIdx= 0;
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
        }
        else if("getPortfolios".equals(cmd))
        {
            com.rp.p2p.order_executor.OrderExecutor orderExecutor = new OrderExecutor();
            Map<String,Long> portfolios = orderExecutor.getPortfolios();
            for (Map.Entry<String,Long> entry : portfolios.entrySet())
            {
                logger_.info(entry.getKey()+" -> "+entry.getValue());
            }
        }
        else if ("browseLoansResult".equals(cmd))
        {
            OrderExecutor orderExecutor = new OrderExecutor();
            BrowseLoansResult browseLoansResult = orderExecutor.getBrowseLoansResult();

            for (LoanListing loanListing : browseLoansResult.getLoans())
            {
                logger_.info(loanListing);
            }
        }
    }

    private static void usage() {
        System.err.println(OrderExecutor.class.getName()+" <execute <loanId> <amount> <|portfolioId>|getPortfolios>");
    }
}
