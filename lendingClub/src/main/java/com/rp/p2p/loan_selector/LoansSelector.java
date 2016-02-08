package com.rp.p2p.loan_selector;

import com.rp.p2p.model.LoanListing;

import java.util.List;
import java.util.Set;

public interface LoansSelector
{
    public List<LoanListing> select(Set<Long> allInvestedLoans, List<LoanListing> loanSelector)throws Exception;
}
