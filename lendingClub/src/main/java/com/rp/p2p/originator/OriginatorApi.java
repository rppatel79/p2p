package com.rp.p2p.originator;

import com.rp.p2p.model.BrowseLoansResult;
import com.rp.p2p.model.Order;
import com.rp.p2p.model.OrderInstructConfirmation;
import com.rp.p2p.model.OwnedNote;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface OriginatorApi
{
    Set<Long> getAllInvestedLoans() throws IOException;

    BrowseLoansResult getBrowseLoansResult(boolean allLoans) throws Exception;

    Collection<OwnedNote> getNotesOwned();

    Map<String,Long> orderGetPortfolios() throws Exception;

    OrderInstructConfirmation orderSubmitOrders(Collection<Order> orders) throws Exception;
}
