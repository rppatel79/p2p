package com.rp.p2p.loan.dynamo;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.rp.p2p.model.BrowseLoansResult;
import org.apache.log4j.Logger;

import java.util.List;

public class BrowseLoansResultDao implements com.rp.p2p.loan.BrowseLoansResultDao {
    private final static Logger logger_ = Logger.getLogger(BrowseLoansResultDao.class);

    public BrowseLoansResultDao() {
    }

    @Override
    public void save(BrowseLoansResult browseLoansResult) throws Exception {
        try {
            AmazonDynamoDB client = AmazonDynamoDBClientBuilder.defaultClient();

            DynamoDBMapper mapper = new DynamoDBMapper(client);
            logger_.info("Starting to save [" + browseLoansResult.getLoans().size() + "]");
            List<DynamoDBMapper.FailedBatch> batchResponse = mapper.batchSave(browseLoansResult.getLoans());
            if (batchResponse != null && batchResponse.size() > 0) {
                // no good reason to throw the first exception
                throw (batchResponse.get(0).getException());
            } else
                logger_.info("Completed saving [" + browseLoansResult.getLoans().size() + "]");
        } catch (Exception ex) {
            logger_.error("Unable to save loan.", ex);
            throw ex;
        }
    }
}
