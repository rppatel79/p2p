package com.rp.p2p.loan_selector;

import com.rp.p2p.model.LoanListing;

import java.util.List;

public interface LoansSelector
{
    public List<LoanListing> select(List<LoanListing> loanSelector)throws Exception;
}
