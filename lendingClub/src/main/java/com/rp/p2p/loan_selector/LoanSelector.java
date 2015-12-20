package com.rp.p2p.loan_selector;

import com.rp.p2p.model.LoanListing;

public interface LoanSelector
{
    public boolean select(LoanListing ll)throws Exception;
}
