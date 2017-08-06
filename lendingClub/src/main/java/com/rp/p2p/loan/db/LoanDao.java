package com.rp.p2p.loan.db;


import com.rp.p2p.model.LoanListing;
import com.rp.util.db.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class LoanDao implements com.rp.p2p.loan.LoanDao {
    private final static Logger logger_ = Logger.getLogger(LoanDao.class);

    @Override
    public void save(List<LoanListing> loans)
    {
        SessionFactory sessionFactory = null;
        Session session =null;
        try {
            sessionFactory = HibernateUtil.getSessionFactory(HibernateUtil.DbId.P2P);
            session = sessionFactory.openSession();
            for (LoanListing loanListing : loans) {
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

    @Override
    public List<LoanListing> loadAll()
    {
        SessionFactory sessionFactory = null;
        Session session =null;
        try {
            sessionFactory = HibernateUtil.getSessionFactory(HibernateUtil.DbId.P2P);
            session = sessionFactory.openSession();
            Criteria criteria = session.createCriteria(LoanListing.class);
            criteria.setReadOnly(true);

            return criteria.list();
        }
        finally
        {
            if (session != null )
                session.close();
        }
    }

    @Override
    public List<LoanListing> loadAll(int min, int numRows)
    {
        SessionFactory sessionFactory = null;
        Session session =null;
        try {
            sessionFactory = HibernateUtil.getSessionFactory(HibernateUtil.DbId.P2P);
            session = sessionFactory.openSession();
            Criteria criteria = session.createCriteria(LoanListing.class);
            criteria.setReadOnly(true);
            criteria.setMaxResults(numRows);
            criteria.setFirstResult(min);

            return criteria.list();
        }
        finally
        {
            if (session != null )
                session.close();
        }
    }

    @Override
    public List<LoanListing> load(List<Long> loanIds) {
        SessionFactory sessionFactory = null;
        Session session =null;
        try {
            sessionFactory = HibernateUtil.getSessionFactory(HibernateUtil.DbId.P2P);
            session = sessionFactory.openSession();
            List<LoanListing> ret = new ArrayList<LoanListing>(loanIds.size());
            for (Long loanId : loanIds)
                ret.add(session.get(LoanListing.class,loanId));
            return ret;
        }
        finally
        {
            if (session != null )
                session.close();
        }
    }
}
