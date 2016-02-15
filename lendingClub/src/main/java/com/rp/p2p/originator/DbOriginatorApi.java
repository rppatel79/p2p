package com.rp.p2p.originator;

import com.rp.p2p.loan.BrowseLoansResultDao;
import com.rp.p2p.model.BrowseLoansResult;
import com.rp.p2p.model.Order;
import com.rp.p2p.model.OrderInstructConfirmation;
import com.rp.p2p.model.OwnedNote;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.*;

public class DbOriginatorApi implements OriginatorApi
{
    private final static Logger logger_ = Logger.getLogger(DbOriginatorApi.class);
    private final OriginatorApi originatorApi_;
    private final Set<BrowseLoansResultDao> browseLoansResultDaoSet_;
    public DbOriginatorApi(OriginatorApi originatorApi,Set<BrowseLoansResultDao> browseLoansResultDaoSet)
    {
        originatorApi_ = originatorApi;
        browseLoansResultDaoSet_ =browseLoansResultDaoSet;
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

        Map<BrowseLoansResultDao,Exception> allDaoExceptions = new LinkedHashMap<BrowseLoansResultDao,Exception>();
        for (BrowseLoansResultDao browseLoansResultDao : browseLoansResultDaoSet_) {
            try {
                browseLoansResultDao.save(browseLoansResult);
            }
            catch (Exception ex)
            {
                allDaoExceptions.put(browseLoansResultDao,ex);
            }
        }

        if (allDaoExceptions.size() > 0)
        {
            // throws the first exception
            if (allDaoExceptions.size() > 1) {
                logger_.warn("Recieved multiple DAO excetions.  The first exception is being thrown, and the others are being logged");
                for (Map.Entry<BrowseLoansResultDao,Exception> entry: allDaoExceptions.entrySet())
                    logger_.warn("Exception from the following dao ["+entry.getKey()+"]",entry.getValue());
            }
            throw allDaoExceptions.entrySet().iterator().next().getValue();
        }

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
