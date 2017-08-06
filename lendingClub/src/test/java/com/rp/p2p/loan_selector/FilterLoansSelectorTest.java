package com.rp.p2p.loan_selector;


import com.rp.p2p.model.*;
import junit.framework.TestCase;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class FilterLoansSelectorTest extends TestCase
{
    public void testMaxSizeWithUnderMaxSize() throws Exception {
        int maxSize =2;
        FilteredLoansSelector filteredLoansSelector = new FilteredLoansSelector(new LoanSelector() {
            @Override
            public boolean select(Set<Long> allInvestedLoans, LoanListing ll) throws Exception {
                return true;
            }
        }, maxSize);

        {
            List<LoanListing> loans = new LinkedList<LoanListing>();
            {
                LoanListing loanListing = new LoanListing();
                loanListing.setIntRate(10.0);
                loans.add(loanListing);
            }

            List<LoanListing> selected = filteredLoansSelector.select(Collections.EMPTY_SET, loans);
            TestCase.assertEquals(selected.size(), 1);
            TestCase.assertEquals(selected.get(0), loans.get(0));
        }
    }

    public void testMaxSizeWithMaxSize() throws Exception
    {
        int maxSize=2;
        FilteredLoansSelector filteredLoansSelector = new FilteredLoansSelector(new LoanSelector() {
            @Override
            public boolean select(Set<Long> allInvestedLoans, LoanListing ll) throws Exception {
                return true;
            }
        }, maxSize);

        {
            List<LoanListing> loans = new LinkedList<LoanListing>();
            {
                LoanListing loanListing = new LoanListing();
                loanListing.setIntRate(10.0);
                loans.add(loanListing);
            }
            {
                LoanListing loanListing = new LoanListing();
                loanListing.setIntRate(11.0);
                loans.add(loanListing);
            }

            List<LoanListing> selected = filteredLoansSelector.select(Collections.EMPTY_SET, loans);
            TestCase.assertEquals(selected.size(),loans.size());
        }
    }

    public void testMaxSizeAboveMaxSize() throws Exception
    {
        int maxSize=2;
        FilteredLoansSelector filteredLoansSelector = new FilteredLoansSelector(new LoanSelector() {
            @Override
            public boolean select(Set<Long> allInvestedLoans, LoanListing ll) throws Exception {
                return true;
            }
        }, maxSize);

        {
            List<LoanListing> loans = new LinkedList<LoanListing>();
            {
                LoanListing loanListing = new LoanListing();
                loanListing.setIntRate(10.0);
                loans.add(loanListing);
            }
            {
                LoanListing loanListing = new LoanListing();
                loanListing.setIntRate(8.0);
                loans.add(loanListing);
            }
            {
                LoanListing loanListing = new LoanListing();
                loanListing.setIntRate(11.0);
                loans.add(loanListing);
            }

            List<LoanListing> selected = filteredLoansSelector.select(Collections.EMPTY_SET, loans);
            TestCase.assertEquals(selected.size(),maxSize);
        }

    }

    public void testEmptyTitle() throws Exception
    {
        int expectedSize=1;
        FilteredLoansSelector filteredLoansSelector = new FilteredLoansSelector(new LoanSelector() {
            @Override
            public boolean select(Set<Long> allInvestedLoans, LoanListing ll) throws Exception {
                return new RhinoLoanSelector(RhinoLoanSelector.SCRIPT_FILTER_LOAN_SELECTOR).select(allInvestedLoans,ll);
            }
        }, expectedSize);

        {
            List<LoanListing> loans = new LinkedList<LoanListing>();
            {
                LoanListing loanListing = new LoanListing();
                loanListing.setIntRate(11.0);
                loanListing.setHomeOwnership(HomeOwnership.OWN);
                loanListing.setAnnualInc(90000);
                loanListing.setEmpTitle(null);
                loanListing.setPurpose(LoanPurpose.DEBT_CONSOLIDATION);
                loanListing.setGrade(LoanGrade.A);
                loanListing.setTerm(36);
                loanListing.setCreditInfo(new CreditInfo());
                loanListing.getCreditInfo().setDti(10.0);
                loans.add(loanListing);
            }

            List<LoanListing> selected = filteredLoansSelector.select(Collections.EMPTY_SET, loans);
            TestCase.assertEquals(selected.size(),0);
        }

    }
}
