package com.rp.p2p.loan;

import com.rp.p2p.model.BrowseLoansResult;
import com.rp.p2p.model.LoanListing;

import java.util.List;

public class CopyDbToDynamo
{
    public static void main(String[] args) throws Exception
    {
        int BATCH_SIZE= 1000;
        int rowCount = 212380;

        int iterations =rowCount/ BATCH_SIZE +1;

        for (int i=0; i < iterations ; i++)
        {
            LoanDao loanDao = new com.rp.p2p.loan.db.LoanDao();
            List<LoanListing> loans = loanDao.loadAll(i*BATCH_SIZE, BATCH_SIZE);

            BrowseLoansResult browseLoansResult = new BrowseLoansResult();
            browseLoansResult.getLoans().addAll(loans);

            BrowseLoansResultDao browseLoansResultDao = new com.rp.p2p.loan.dynamo.BrowseLoansResultDao();
            browseLoansResultDao.save(browseLoansResult);
        }
    }
}
