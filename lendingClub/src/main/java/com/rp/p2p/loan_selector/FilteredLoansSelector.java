package com.rp.p2p.loan_selector;

import com.rp.p2p.model.*;
import org.apache.log4j.Logger;

import java.util.*;

public class FilteredLoansSelector implements LoansSelector
{
    private final static Logger logger_ =Logger.getLogger(FilteredLoansSelector.class);
    private final LoanSelector loanSelector_;

    public FilteredLoansSelector(LoanSelector loanSelector) {
        loanSelector_ = loanSelector;
    }

    @Override
    public List<LoanListing> select(Set<Long> allInvestedLoans,List<LoanListing> loanSelector) throws Exception
    {
        List<LoanListing> ret = new ArrayList<LoanListing>(loanSelector.size());

        for (LoanListing loan : loanSelector) {
            boolean selected= loanSelector_.select(allInvestedLoans,loan);

            if (selected) {
                ret.add(loan);
            }
        }

        // sorts loans to
        Collections.sort(ret, new Comparator<LoanListing>() {
            @Override
            public int compare(LoanListing o1, LoanListing o2) {
                return Double.compare(o1.getIntRate() , o2.getIntRate());
            }
        });
        Collections.reverse(ret);


        return ret;
    }

}
