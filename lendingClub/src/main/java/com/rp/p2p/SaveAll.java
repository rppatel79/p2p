package com.rp.p2p;

import com.rp.p2p.loan.BrowseLoansResultDao;
import com.rp.p2p.originator.DbOriginatorApi;
import com.rp.p2p.originator.lending_club.restful.LendingClubApi;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by RPatel on 3/19/2016.
 */
public class SaveAll {
    public static void main(String[] args) throws Exception {
        final DbOriginatorApi originatorApi = new DbOriginatorApi(new LendingClubApi()
                ,new HashSet<BrowseLoansResultDao>(
                Arrays.asList(new com.rp.p2p.loan.BrowseLoansResultDao[]{new com.rp.p2p.loan.db.BrowseLoansResultDao(),
                        new com.rp.p2p.loan.dynamo.BrowseLoansResultDao()})));
        originatorApi.getAndStoreBrowseLoansResult(true);
    }
}
