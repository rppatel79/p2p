package com.rp.p2p.loan.dynamo;


import com.rp.p2p.model.BrowseLoansResult;
import com.rp.p2p.model.LoanListing;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class BrowseLoansResultDaoTest extends TestCase {
    public void testSave() throws Exception
    {
        com.rp.p2p.loan.BrowseLoansResultDao browseLoansResultDao = new BrowseLoansResultDao();

        List<LoanListing> loanListingList = new ArrayList<LoanListing>();
        int id = 1;
        {
            LoanListing ll = new LoanListing();
            ll.setId(id++);

            loanListingList.add(ll);
        }
        {
            LoanListing ll = new LoanListing();
            ll.setId(id++);

            loanListingList.add(ll);
        }
        {
            LoanListing ll = new LoanListing();
            ll.setId(id++);

            loanListingList.add(ll);
        }
        BrowseLoansResult browseLoansResult = new BrowseLoansResult();
        browseLoansResult.getLoans().addAll(loanListingList);

        browseLoansResultDao.save(browseLoansResult);
    }
}
