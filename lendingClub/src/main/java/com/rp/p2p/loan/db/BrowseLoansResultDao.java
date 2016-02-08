package com.rp.p2p.loan.db;


import com.rp.p2p.model.BrowseLoansResult;
import com.rp.p2p.model.LoanListing;
import com.rp.util.db.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class BrowseLoansResultDao implements com.rp.p2p.loan.BrowseLoansResultDao {
    private final static Logger logger_ = Logger.getLogger(BrowseLoansResultDao.class);

    @Override
    public void save(BrowseLoansResult browseLoansResult) {

        SessionFactory sessionFactory = null;
        Session session =null;
        try {
            sessionFactory = HibernateUtil.getSessionFactory(HibernateUtil.DbId.P2P);
            session = sessionFactory.openSession();
            for (LoanListing loanListing : browseLoansResult.getLoans()) {
                session.merge(loanListing);
            }
            session.flush();
        }
        catch(Exception ex)
        {
            logger_.warn("Unable to save loan.  Continuing without failing",ex);
        }
        finally
        {
            if (session != null )
                session.close();
        }

    }
}
