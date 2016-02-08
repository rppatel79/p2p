package com.rp.p2p.loan.db;

import com.rp.p2p.model.OrderConfirmation;
import com.rp.util.db.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Set;


public class OrderStatusDao implements com.rp.p2p.loan.OrderStatusDao {
    private final static Logger logger_ =Logger.getLogger(OrderStatusDao.class);
    @Override
    public void save(com.rp.p2p.order_executor.OrderExecutor.OrderStatus orderStatus) {
        // save the records
        SessionFactory sessionFactory = null;
        Session session = null;
        try {
            sessionFactory = HibernateUtil.getSessionFactory(HibernateUtil.DbId.P2P);
            session = sessionFactory.openSession();

            saveOrderConfirmation(session, orderStatus.getFailed());
            saveOrderConfirmation(session, orderStatus.getSuccess());
            session.flush();
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (Exception ex) {
                    logger_.warn("Unable to close session.  Continuing without throwing exception", ex);
                }
            }
        }
    }

    private void saveOrderConfirmation(Session session, Set<OrderConfirmation> orderConfirmationSet) {
        for (OrderConfirmation orderConfirmation : orderConfirmationSet) {
            session.save(orderConfirmation);
        }
    }
}
