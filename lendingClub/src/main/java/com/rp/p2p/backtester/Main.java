package com.rp.p2p.backtester;

import com.rp.p2p.loan.LoanDao;
import com.rp.p2p.loan_selector.FilteredLoansSelector;
import com.rp.p2p.loan_selector.LoansSelector;
import com.rp.p2p.loan_selector.RhinoLoanSelector;
import com.rp.p2p.model.CreditInfo;
import com.rp.p2p.model.LoanListing;
import com.rp.util.db.HibernateUtil;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.List;

public class Main
{
    private final static Logger logger_ =Logger.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        /*
        try {
            LoanDao loanDao = new com.rp.p2p.loan.db.LoanDao();
            List<LoanListing> loanListings = loanDao.loadAll();

            LoansSelector loansSelector = new FilteredLoansSelector(new RhinoLoanSelector(RhinoLoanSelector.SCRIPT_FILTER_LOAN_SELECTOR));
            List<LoanListing> selected = loansSelector.select(Collections.<Long>emptySet(), loanListings);
            logger_.info("All loans is [" + loanListings.size() + "] and selected [" + selected.size() + "]");
        }
        finally {
            HibernateUtil.shutdownAll();
        }*/

        LoanListing loanListing1 = new LoanListing();
        loanListing1.setId(1);
        loanListing1.setEmpTitle("Senior Mastor");
        loanListing1.setTerm(36);
        loanListing1.setLoanAmnt(5000);
        loanListing1.setIntRate(10);
        loanListing1.setCreditInfo(new CreditInfo());
        loanListing1.getCreditInfo().setPubRec(0);
        loanListing1.getCreditInfo().setDti(20.0);
        loanListing1.setAnnualInc(50000);

        LoansSelector loansSelector = new FilteredLoansSelector(new RhinoLoanSelector(RhinoLoanSelector.SCRIPT_FILTER_LOAN_SELECTOR_PASTOR));
        List<LoanListing> selected1 = loansSelector.select(Collections.<Long>emptySet(), Collections.singletonList(loanListing1));


        List<LoanListing> selected2 = loansSelector.select(Collections.singleton(loanListing1.getId()), Collections.singletonList(loanListing1));
        System.out.println("selected1.size()=["+selected1.size()+"] selected2.size()="+selected2.size());
    }
}
