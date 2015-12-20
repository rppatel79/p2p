package com.rp.p2p.loan_selector;

import com.rp.p2p.model.LoanListing;
import org.apache.commons.jxpath.JXPathContext;
import org.springframework.beans.factory.annotation.Required;

import java.util.Set;

public class FieldMatchLoanSelector<I> implements LoanSelector
{
    private Set<I> validValues;
    private String loanListingXpath;

    @Required
    public void setValidValues(Set<I> validValues) {
        this.validValues = validValues;
    }

    public void setLoanListingXpath(String loanListingXpath) {
        this.loanListingXpath = loanListingXpath;
    }

    @Override
    public boolean select(LoanListing ll)throws Exception
    {
        Object value = JXPathContext.newContext(ll).getValue(loanListingXpath);

        return validValues.contains(value);
    }
}
