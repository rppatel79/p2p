package com.rp.p2p.loan_selector;

import com.rp.p2p.model.*;
import org.apache.log4j.Logger;
import java.util.*;

public class FilteredLoansSelector implements LoansSelector
{
    private final static Logger logger_ =Logger.getLogger(FilteredLoansSelector.class);
    private final static int DEFAULT_MAX_LOANS_TO_PURCHASE=6;
    private final Integer maxLoansToPurchase_;
    private final LoanSelector loanSelector_;

    public FilteredLoansSelector(LoanSelector loanSelector)
    {
        this(loanSelector,DEFAULT_MAX_LOANS_TO_PURCHASE);
    }

    public FilteredLoansSelector(LoanSelector loanSelector, Integer maxLoansToPurchase) {

        loanSelector_ = loanSelector;
        maxLoansToPurchase_=maxLoansToPurchase;
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

        if(ret==null) {
            logger_.trace("Returning no records");
            return ret;
        }
        else {
            List<LoanListing> subList = ret.subList(0, Math.min(ret.size(), maxLoansToPurchase_));
            logger_.trace("Returning ["+subList.size()+"] records");
            return subList;
        }
    }

    public static void main(String[] args) throws Exception {
    }
}
