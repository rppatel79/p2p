package com.rp.p2p.loan_selector;

import com.rp.p2p.model.CreditInfo;
import com.rp.p2p.model.HomeOwnership;
import com.rp.p2p.model.LoanListing;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class RhinoLoanSelector implements LoanSelector
{
    @Override
    public boolean select(Set<Long> allInvestedLoans, LoanListing ll) throws Exception {
        final String scriptName="filterLoanSelector.js";

        Context ctx = Context.enter();
        try {
            InputStreamReader in = new InputStreamReader(this.getClass().getClassLoader()
                    .getResourceAsStream(scriptName));
            Scriptable scope = ctx.initStandardObjects();
            scope.put("loan", scope, ll);
            scope.put("logger_", scope, Logger.getLogger(scriptName));
            scope.put("allInvestedLoans",scope,allInvestedLoans);
            Object ret = ctx.evaluateReader(scope, in, scriptName, 1, null);
            return new Double(0.0).equals(Double.parseDouble(ret.toString()));
        }
        finally {
            ctx.exit();
        }
    }

    public static void main(String[] args) throws Exception
    {
        LoanSelector loanSelector = new InCodeLoansSelector();
        LoanListing ll = new LoanListing();
        ll.setId(123);
        ll.setLoanAmnt(1);
        ll.setAnnualInc(1);
        ll.setHomeOwnership(HomeOwnership.ANY);
        ll.setCreditInfo(new CreditInfo());
        ll.getCreditInfo().setDti(0.0);
        loanSelector.select(new HashSet<Long>(),ll);
    }
}
