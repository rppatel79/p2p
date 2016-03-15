package com.rp.p2p.loan.loan_selector;

import com.rp.p2p.loan.db.LoanDao;
import com.rp.p2p.loan_selector.InCodeLoansSelector;
import com.rp.p2p.loan_selector.LoanSelector;
import com.rp.p2p.loan_selector.LoansSelector;
import com.rp.p2p.loan_selector.RhinoLoanSelector;
import com.rp.p2p.model.LoanListing;
import junit.framework.Test;
import junit.framework.TestCase;
import org.apache.log4j.Logger;
import org.junit.Assert;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ValidateSelector extends TestCase
{
    private final static Logger logger_ = Logger.getLogger(ValidateSelector.class);
    private enum LoanSelectorState{True,False,Exception}
    public void test()
    {
        LoanDao loanDao = new LoanDao();
        //List<LoanListing> loanListingList = loanDao.load(Collections.singletonList(68053175L));
        List<LoanListing> loanListingList = loanDao.loadAll();
        LoanSelector[] loanSelectors = {(LoanSelector) new InCodeLoansSelector()
                ,(LoanSelector)new RhinoLoanSelector()};

        Set<Long> allInvestedLoans = new HashSet<Long>();
        for (LoanListing ll : loanListingList)
        {
            logger_.info("Testing "+ll.getId());
            LoanSelectorState loanSelectorState0=select(allInvestedLoans,loanSelectors[0],ll);
            LoanSelectorState loanSelectorState1=select(allInvestedLoans,loanSelectors[1],ll);
            Assert.assertEquals("Loan ["+ll.getId()+"] ["+loanSelectors[0].getClass()+"] returned ["+loanSelectorState0+"] while ["+loanSelectors[1].getClass()+"] returned ["+loanSelectorState1+"]"
                    ,loanSelectorState0,loanSelectorState1);
        }
    }

    private LoanSelectorState select(Set<Long> allInvestedLoans,
                                     LoanSelector loanSelector,
                                     LoanListing loanListing) {
        try{
            boolean ret=loanSelector.select(allInvestedLoans,loanListing);
            if (ret)
                return LoanSelectorState.True;
            else
                return LoanSelectorState.False;
        }
        catch (Exception ex)
        {
            logger_.warn("Exception from ["+loanSelector+"]",ex);
            return LoanSelectorState.Exception;
        }
    }
}
