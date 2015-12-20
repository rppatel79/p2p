package com.rp.p2p.analytics;

import com.rp.util.ApplicationProperties;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class P2pPicksApi
{
    private final static Logger logger_ =Logger.getLogger(P2pPicksApi.class);
    private static final String BASE_URL="https://www.p2p-picks.com/api/v1";

    private final String apiSecret_;
    private final String apiKey_;

    public P2pPicksApi() throws IOException
    {
        apiSecret_ =ApplicationProperties.getInstance().getProperty("P2P_PICKS_API_SECRET");
        apiKey_ =ApplicationProperties.getInstance().getProperty("P2P_PICKS_API_KEY");
    }

    public PicksResponse list(String sid,String product) throws Exception
    {
        final String method="picks";
        final String action="list";


        TreeMap<String,String> parameters = new TreeMap<String, String>();
        parameters.put("api_key", apiKey_);
        parameters.put("p2p_product",product);
        parameters.put("secret", apiSecret_);

        String string = execute(method, action, parameters);
        logger_.info(string);
        PicksResponse ob = new ObjectMapper().readValue(string, PicksResponse.class);
        return ob;
    }


    private String execute(String method,String action,NavigableMap<String,String> parameters) throws Exception
    {
        StringBuilder urlSb = new StringBuilder(BASE_URL);
        urlSb.append('/').append(method);
        urlSb.append('/').append(action);

        URL obj = new URL(urlSb.toString());
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        //con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("Content-Type","application/x-www-form-urlencoded");

        StringBuilder builder = new StringBuilder();
        {
            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                builder.append(entry.getKey()).append('=').append(entry.getValue()).append("&");
            }
            builder.append("sig").append("=").append(generateSig(method, action, parameters));
        }


        logger_.info("\nSending " + con.getRequestMethod() + " request to URL : " + obj.toString());

        logger_.info("\tParameter : ");
        for (Map.Entry<String,List<String>> entry : con.getRequestProperties().entrySet())
        {
            logger_.info("\t\t" + entry.getKey() + " : " + entry.getValue().get(0));
        }

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(builder.toString());
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        logger_.info("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

    private static String generateSig(String method, String action, Map<String, String> requestProperties) throws NoSuchAlgorithmException {
        StringBuilder parameter = new StringBuilder();
        parameter.append(method).append('-').append(action);

        for (Map.Entry<String,String> entry : requestProperties.entrySet())
        {
            parameter.append("&").append(entry.getKey()).append(entry.getValue());
        }

        return Md5Generator.getInstance().convert(parameter.toString());
    }

    public static class PicksResponse
    {
        public Meta meta;
        public Response response;
    }
    public static class Meta
    {
        public int status;
        public String msg;
        public Response response;
    }
    public static class Response
    {
        public String timestamp;
        public String product;
        public List<Pick> picks;
    }
    public static class Pick
    {
        public long loan_id;
        public String grade;
        public String term;
        public String top;
    }

    public static void main(String args[]) throws Exception
    {
        (new P2pPicksApi()).list(null, "profit-maximizer");

/*
        String value ="{\"meta\":{\"status\":200,\"msg\":\"OK\"},\"response\":{\"timestamp\":\"2015-01-01 18:00:05\",\"product\":\"profit-maximizer\",\"picks\":[{\"loan_id\":\"38587234\",\"grade\":\"D\",\"term\":\"36\",\"top\":\"10%\"},{\"loan_id\":\"38517641\",\"grade\":\"C\",\"term\":\"36\",\"top\":\"25%\"},{\"loan_id\":\"38457427\",\"grade\":\"C\",\"term\":\"36\",\"top\":\"25%\"},{\"loan_id\":\"38697496\",\"grade\":\"D\",\"term\":\"36\",\"top\":\"25%\"},{\"loan_id\":\"38567360\",\"grade\":\"C\",\"term\":\"36\",\"top\":\"25%\"},{\"loan_id\":\"38617173\",\"grade\":\"C\",\"term\":\"36\",\"top\":\"25%\"},{\"loan_id\":\"38657173\",\"grade\":\"C\",\"term\":\"36\",\"top\":\"25%\"},{\"loan_id\":\"38637434\",\"grade\":\"C\",\"term\":\"36\",\"top\":\"25%\"},{\"loan_id\":\"38577320\",\"grade\":\"C\",\"term\":\"36\",\"top\":\"25%\"},{\"loan_id\":\"38507407\",\"grade\":\"C\",\"term\":\"36\",\"top\":\"25%\"},{\"loan_id\":\"38416158\",\"grade\":\"E\",\"term\":\"36\",\"top\":\"5%\"},{\"loan_id\":\"38657174\",\"grade\":\"C\",\"term\":\"36\",\"top\":\"5%\"}]}}";
        PicksResponse ob = new ObjectMapper().readValue(value, PicksResponse.class);
*/
    }
}
