package com.rp.p2p.loan;

import com.rp.p2p.model.LoanListing;

import java.util.List;

public interface LoanDao {
    void save(List<LoanListing> loans);

    List<LoanListing> loadAll();
    List<LoanListing> loadAll(int min, int numRows);

    List<LoanListing> load(List<Long> loanIds);
}
