package com.rp.p2p.loan.db;

import junit.framework.TestCase;

public class LoanDaoTest extends TestCase
{
    private final LoanDao loanDao_ = new LoanDao();
    public void testLoanAll()
    {
        loanDao_.loadAll();
    }
}
