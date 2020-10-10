package com.rp.p2p;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.rp.p2p.model.LoanListing;
import com.rp.util.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class LambdaMain implements RequestHandler<Map<String, Object>, Object> {
    private final static Logger logger_ = Logger.getLogger(LambdaMain.class);

    public Object handleRequest(final Map<String, Object> inputMap, final Context context) {
        //Map<String, Object> inputMap = () input;
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");

        try {
            logger_.info("Running with input [" + inputMap + "]");

            Main.SourceType sourceType = Main.SourceType.valueOf((String) inputMap.get("sourceType"));
            Double requestAmount = (Double) inputMap.get("requestAmount");
            Boolean execute = (Boolean) inputMap.get("execute");

            List<LoanListing> loanListingList = Main.run(sourceType, requestAmount, execute);

            final String pageContents = this.getPageContents("https://checkip.amazonaws.com");
            String output = String.format("{ \"message\": \"hello world\", \"location\": \"%s\" }", pageContents);
            return new GatewayResponse(output, headers, 200);
        } catch (Exception ex) {
            logger_.error("Unable to complete.", ex);

            return new GatewayResponse("{" + ExceptionUtils.getStackTrace(ex) + "}", headers, 500);
        }
    }

    private String getPageContents(String address) throws IOException{
        URL url = new URL(address);
        try(BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
            return br.lines().collect(Collectors.joining(System.lineSeparator()));
        }
    }
}
