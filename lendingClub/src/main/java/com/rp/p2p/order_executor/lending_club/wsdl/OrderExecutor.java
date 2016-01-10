package com.rp.p2p.order_executor.lending_club.wsdl;


import com.rp.p2p.loan.LoanDao;
import com.rp.p2p.model.*;
import com.rp.p2p.originator.lending_club.restful.LendingClubApi;
import com.rp.p2p.originator.OriginatorApi;
import com.rp.util.db.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

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
        LoanDao.OrderStatus orderStatus = loanDao.investLoans(orders, orderInstructConfirmation.getOrderConfirmations());

        // save the records
        SessionFactory sessionFactory=null;
        Session session =null;
        try
        {
            sessionFactory = HibernateUtil.getSessionFactory(HibernateUtil.DbId.P2P);
            session = sessionFactory.openSession();

            saveOrderConfirmation(session, orderStatus.getFailed());
            saveOrderConfirmation(session, orderStatus.getSuccess());
            session.flush();
        }
        finally {
            if (session != null)
            {
                try{
                    session.close();
                }
                catch (Exception ex)
                {
                    logger_.warn("Unable to close session.  Continuing without throwing exception",ex);
                }
            }
        }

        return orderStatus;
    }

    private void saveOrderConfirmation(Session session, Set<OrderConfirmation> orderConfirmationSet) {
        for (OrderConfirmation orderConfirmation : orderConfirmationSet) {
            session.save(orderConfirmation);
        }
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
