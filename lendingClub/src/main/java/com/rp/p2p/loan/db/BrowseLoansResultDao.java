package com.rp.p2p.loan.db;


import com.rp.p2p.model.BrowseLoansResult;
import org.apache.log4j.Logger;

public class BrowseLoansResultDao implements com.rp.p2p.loan.BrowseLoansResultDao {
    private final static Logger logger_ = Logger.getLogger(BrowseLoansResultDao.class);

    @Override
    public void save(BrowseLoansResult browseLoansResult) {
        logger_.trace("Saving..");
        (new LoanDao()).save(browseLoansResult.getLoans());
        logger_.trace("Saved..");
    }
}
