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
        (new LoanDao()).loadAll();
    }
}
