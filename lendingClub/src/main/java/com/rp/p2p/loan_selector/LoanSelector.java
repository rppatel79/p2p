package com.rp.p2p.loan_selector;

import com.rp.p2p.model.LoanListing;
import java.util.Set;

interface LoanSelector
{
    boolean select(Set<Long> allInvestedLoans, LoanListing ll)throws Exception;
}
