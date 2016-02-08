package com.rp.p2p.originator;

import com.rp.p2p.loan.db.BrowseLoansResultDao;
import com.rp.p2p.model.BrowseLoansResult;
import com.rp.p2p.model.Order;
import com.rp.p2p.model.OrderInstructConfirmation;
import com.rp.p2p.model.OwnedNote;
import org.codehaus.jackson.map.deser.std.FromStringDeserializer;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class DbOriginatorApi implements OriginatorApi
{
    private final OriginatorApi originatorApi_;
    private final BrowseLoansResultDao browseLoansResultDao_;
    public DbOriginatorApi(OriginatorApi originatorApi,BrowseLoansResultDao browseLoansResultDao)
    {
        originatorApi_ = originatorApi;
        browseLoansResultDao_ =browseLoansResultDao;
    }

    @Override
    public Set<Long> getAllInvestedLoans() throws IOException {
        return originatorApi_.getAllInvestedLoans();
    }

    @Override
    public BrowseLoansResult getBrowseLoansResult(boolean allLoans) throws Exception {
        return originatorApi_.getBrowseLoansResult(allLoans);
    }

    public BrowseLoansResult getAndStoreBrowseLoansResult(boolean allLoans) throws Exception {
        BrowseLoansResult browseLoansResult=originatorApi_.getBrowseLoansResult(allLoans);
        browseLoansResultDao_.save(browseLoansResult);
        return browseLoansResult;
    }

    @Override
    public Collection<OwnedNote> getNotesOwned() {
        return originatorApi_.getNotesOwned();
    }

    @Override
    public Map<String, Long> orderGetPortfolios() throws Exception {
        return originatorApi_.orderGetPortfolios();
    }

    @Override
    public OrderInstructConfirmation orderSubmitOrders(Collection<Order> orders) throws Exception {
        return originatorApi_.orderSubmitOrders(orders);
    }
}
