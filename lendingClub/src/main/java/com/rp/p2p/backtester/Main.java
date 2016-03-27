package com.rp.p2p.backtester;

import com.rp.p2p.loan.LoanDao;
import com.rp.p2p.loan_selector.FilteredLoansSelector;
import com.rp.p2p.loan_selector.LoansSelector;
import com.rp.p2p.loan_selector.RhinoLoanSelector;
import com.rp.p2p.model.LoanListing;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.List;

public class Main
{
    private final static Logger logger_ =Logger.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        LoanDao loanDao = new com.rp.p2p.loan.db.LoanDao();
        List<LoanListing> loanListings = loanDao.loadAll();

        LoansSelector loansSelector = new FilteredLoansSelector(new RhinoLoanSelector(RhinoLoanSelector.SCRIPT_FILTER_LOAN_SELECTOR));
        List<LoanListing> selected = loansSelector.select(Collections.<Long>emptySet(),loanListings);
        logger_.info("All loans is ["+loanListings.size()+"] and selected ["+selected.size()+"]");
    }
}
