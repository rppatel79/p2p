package com.rp.p2p.loan.dynamo;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.rp.p2p.model.BrowseLoansResult;
import com.rp.util.ApplicationProperties;
import org.apache.log4j.Logger;

import java.io.IOException;

public class BrowseLoansResultDao implements com.rp.p2p.loan.BrowseLoansResultDao {
    private final static Logger logger_ = Logger.getLogger(BrowseLoansResultDao.class);

    private final AWSCredentials credentials_;
    public BrowseLoansResultDao() throws IOException {
        credentials_= new BasicAWSCredentials(ApplicationProperties.getInstance().getProperty("aws.access_key_id"),
                ApplicationProperties.getInstance().getProperty("aws.secret_access_key"));
    }

    @Override
    public void save(BrowseLoansResult browseLoansResult) {

        try
        {
            AmazonDynamoDBClient client = new AmazonDynamoDBClient(credentials_);
            DynamoDBMapper mapper = new DynamoDBMapper(client);
            mapper.batchSave(browseLoansResult.getLoans());
        }
        catch(Exception ex)
        {
            logger_.warn("Unable to save loan.  Continuing without failing",ex);
        }
    }
}
