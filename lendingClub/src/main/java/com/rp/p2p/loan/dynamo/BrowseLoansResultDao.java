package com.rp.p2p.loan.dynamo;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.rp.p2p.model.BrowseLoansResult;
import com.rp.util.application_properties.ApplicationPropertiesFactory;
import org.apache.log4j.Logger;

import java.io.IOException;

public class BrowseLoansResultDao implements com.rp.p2p.loan.BrowseLoansResultDao {
    private final static Logger logger_ = Logger.getLogger(BrowseLoansResultDao.class);

    private final AWSCredentials credentials_;
    public BrowseLoansResultDao() throws IOException {
        credentials_ = new BasicAWSCredentials(ApplicationPropertiesFactory.getInstance().getProperty("access_key_id"),
                ApplicationPropertiesFactory.getInstance().getProperty("secret_access_key"));
    }

    @Override
    public void save(BrowseLoansResult browseLoansResult) {

        try
        {
            AmazonDynamoDBClient client = new AmazonDynamoDBClient(credentials_);
            DynamoDBMapper mapper = new DynamoDBMapper(client);
            logger_.info("Starting to save ["+browseLoansResult.getLoans().size()+"]");
            mapper.batchSave(browseLoansResult.getLoans());
            logger_.info("Completed saving ["+browseLoansResult.getLoans().size()+"]");
        }
        catch(Exception ex)
        {
            logger_.warn("Unable to save loan.  Continuing without failing",ex);
        }
    }
}
