package com.rp.p2p.loan_selector;

import com.rp.p2p.model.LoanListing;
import org.apache.log4j.Logger;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import java.io.*;
import java.util.Set;

public class RhinoLoanSelector implements LoanSelector
{
    public static final String SCRIPT_FILTER_LOAN_SELECTOR="filterLoanSelector.js";
    public static final String SCRIPT_FILTER_LOAN_SELECTOR_PASTOR="pastor.js";

    private final String scriptName_ ;

    public RhinoLoanSelector(String scriptName) {
        scriptName_=scriptName;
    }

    @Override
    public boolean select(final Set<Long> allInvestedLoans, LoanListing ll) throws Exception {
        Context ctx = Context.enter();
        try (InputStreamReader in = new InputStreamReader(this.getClass().getClassLoader()
                .getResourceAsStream(scriptName_)))
        {
            Scriptable scope = ctx.initStandardObjects();
            scope.put("loan", scope, ll);
            scope.put("logger_", scope, Logger.getLogger(scriptName_));
            scope.put("allInvestedLoans",scope,allInvestedLoans);
            Object ret = ctx.evaluateReader(scope, in, scriptName_, 1, null);
            return new Double(0.0).equals(Double.parseDouble(ret.toString()));
        }
        finally {
            Context.exit();
        }
    }
}
