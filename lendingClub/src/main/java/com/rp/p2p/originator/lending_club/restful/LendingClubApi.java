package com.rp.p2p.originator.lending_club.restful;

import com.codesnippets4all.json.parsers.JSONParser;
import com.codesnippets4all.json.parsers.JsonParserFactory;
import com.rp.p2p.model.*;
import com.rp.p2p.originator.OriginatorApi;
import com.rp.util.ApplicationProperties;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class LendingClubApi implements OriginatorApi
{
    private final static Logger logger_ = Logger.getLogger(LendingClubApi.class);

    private  static final String BASE_URL = "https://api.lendingclub.com/api/investor/v1/";

    private static enum Type {
        ACCOUNT, LOANS, ACCOUNT_POST, NOTES
    };

    private final char[] investorId;
    private final char[] apiKey;

    public LendingClubApi() throws IOException {
        String LENDING_CLUB_API = ApplicationProperties.getInstance().getProperty("LENDING_CLUB_API");
        String LENDINGCLUB_API_KEY = LENDING_CLUB_API;
        long INVESTOR_ID=Long.parseLong(ApplicationProperties.getInstance().getProperty("INVESTOR_ID"));

        investorId= (""+ INVESTOR_ID ).toCharArray();
        apiKey= LENDINGCLUB_API_KEY.toCharArray();
    }

    @Override
    public OrderInstructConfirmation  orderSubmitOrders(Collection<Order> orders)
    {
        Request request = prepareRequest("orders", Type.ACCOUNT_POST);
        String jsonString = createJsonRequestString(orders);
        request.bodyString(jsonString, ContentType.DEFAULT_TEXT);
        JsonParserFactory factory = JsonParserFactory.getInstance();
        JSONParser parser = factory.newJsonParser();
        try {
            Response response = request.execute();
            HttpResponse httpResponse = response.returnResponse();
            String responseStr = EntityUtils.toString(httpResponse.getEntity());
            Map d = parser.parseJson(responseStr);

            List<String> errors = (List<String>)d.get("errors");
            List<Map<String,Object>> orderConfirmationsMap = (List<Map<String,Object>>)d.get("orderConfirmations");
            if (orderConfirmationsMap != null) {
                List<OrderConfirmation> orderConfirmations = new ArrayList<OrderConfirmation>(orderConfirmationsMap.size());
                for (Map<String, Object> record : orderConfirmationsMap) {
                    OrderConfirmation orderConfirmation = new OrderConfirmation();
                    orderConfirmation.setLoanId(Long.valueOf((String)record.get("loanId")));
                    orderConfirmation.setRequestedAmount(Double.valueOf((String) record.get("requestedAmount")));
                    orderConfirmation.setInvestedAmount(Double.valueOf((String) record.get("investedAmount")));
                    List<String> executionStatus = (List<String>) record.get("executionStatus");
                    for (String value : executionStatus) {
                        orderConfirmation.getExecutionStatus().add(OrderExecutionStatus.fromValue(value));
                    }

                    orderConfirmations.add(orderConfirmation);
                }

                OrderInstructConfirmation orderInstructConfirmation = new OrderInstructConfirmation();
                orderInstructConfirmation.getOrderConfirmations().addAll(orderConfirmations);

                return orderInstructConfirmation;
            }
            else if(errors != null)
            {
                List<String> errorList = (List<String>)errors;
                throw new RuntimeException(errorList+"");
            }
            else
            {
                throw new RuntimeException("Invalid message:"+d);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String createJsonRequestString(Collection<Order> orders)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("{");sb.append("\"aid\": ");
        sb.append(new String(investorId()));
        sb.append(",");
        sb.append("\"orders\":[");
        int i = 0;
        for (Order order : orders) {
            if (i++ > 0) {
                sb.append(",");
            }
            sb.append("{");
            sb.append("\"loanId\": ");
            sb.append(order.getLoanId());
            sb.append(",");
            sb.append("\"requestedAmount\": ");
            sb.append(order.getRequestedAmount());
            if (order.getPortfolioId() != null) {
                sb.append(",");
                sb.append("\"portfolioId\":");
                sb.append(order.getPortfolioId());
            }
            sb.append("}");
        }
        sb.append("]}");
        return sb.toString();
    }

    @Override
    public Collection<OwnedNote> getNotesOwned()
    {
        JsonParserFactory factory = JsonParserFactory.getInstance();
        JSONParser parser = factory.newJsonParser();
        Map map;
        try {
            map = parser.parseJson(prepareRequest("notes", Type.NOTES).execute().returnContent().asStream(), "UTF-8");
//            if (!map.containsKey("loans")) {
//                log.error("Response doesn't have any 'loans' attribute, response string: " + map);
//            }
            return convertNotesOwned((List<Map<String, String>>) map.get("myNotes"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public BrowseLoansResult getBrowseLoansResult(boolean allLoans) throws Exception {
        JsonParserFactory factory = JsonParserFactory.getInstance();
        JSONParser parser = factory.newJsonParser();
        Map map;
        try {
            map = parser.parseJson(prepareRequest("listing" + (allLoans ? "?showAll=true" : ""), Type.LOANS).execute().returnContent().asStream(), "UTF-8");
//            if (!map.containsKey("loans")) {
//                log.error("Response doesn't have any 'loans' attribute, response string: " + map);
//            }
            List<Map<String, String>> loansMap = (List<Map<String, String>>) map.get("loans");
            if (loansMap == null) {
                logger_.warn("Made RESTful request but did not get any results.");
                return new BrowseLoansResult();//return an empty shell
            }
            else
                return convertLoans(loansMap);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Collection<OwnedNote> convertNotesOwned(List<Map<String, String>> allNotesOwned) throws ParseException {
        Collection<OwnedNote> ret = new ArrayList<OwnedNote>(allNotesOwned.size());
        for (Map<String,String>  noteOwned : allNotesOwned)
        {
            OwnedNote ownedNote = new OwnedNote();
            {
                ownedNote.setLoanStatus(noteOwned.get("loanStatus"));
                ownedNote.setLoanId(Long.valueOf(noteOwned.get("loanId")));
                ownedNote.setNoteId((noteOwned.get("noteId")));
                ownedNote.setGrade(LoanGrade.valueOf(noteOwned.get("grade")));
                ownedNote.setLoanAmount(Double.valueOf(noteOwned.get("loanAmount")));
                ownedNote.setNoteAmount(Double.valueOf(noteOwned.get("noteAmount")));
                ownedNote.setInterestRate(Double.valueOf(noteOwned.get("interestRate")));
                ownedNote.setOrderId(Long.valueOf(noteOwned.get("orderId")));
                ownedNote.setTerm(Integer.valueOf(noteOwned.get("loanLength")));
                ownedNote.setIssueDate(DateValueOf(noteOwned.get("issueDate")));
                ownedNote.setOrderDate(DateValueOf(noteOwned.get("orderDate")));
                ownedNote.setLoanStatusDate(DateValueOf(noteOwned.get("loanStatusDate")));
                ownedNote.setPaymentsReceived(Double.valueOf(noteOwned.get("paymentsReceived")));
            }

            ret.add(ownedNote);
        }

        return ret;
    }

    private BrowseLoansResult convertLoans(List<Map<String, String>> allLoans) {
        BrowseLoansResult browseLoansResult = new BrowseLoansResult();
        for (Map<String,String>  loans : allLoans)
        {
            LoanListing loan = new LoanListing();

            loan.setId(Long.valueOf(loans.get("id")));
            loan.setMemberId((loans.get("memberId")));

            loan.setLoanAmnt(Double.valueOf(loans.get("loanAmount")));
            loan.setFundedAmnt(Double.valueOf(loans.get("fundedAmount")));
            loan.setTerm(Integer.valueOf(loans.get("term")));
            loan.setIntRate(Double.valueOf(loans.get("intRate")));
            loan.setExpDefaultRate(Double.valueOf(loans.get("expDefaultRate")));
            loan.setServiceFeeRate(Double.valueOf(loans.get("serviceFeeRate")));
            loan.setInstallment(Double.valueOf(loans.get("installment")));
            loan.setGrade(LoanGrade.valueOf(loans.get("grade")));
            loan.setSubGrade(LoanSubGrade.fromValue(loans.get("subGrade")));
            loan.setEmpLength(IntegerValueOf(loans.get("empLength")));
            loan.setHomeOwnership(HomeOwnership.valueOf(loans.get("homeOwnership")));
            loan.setAnnualInc(Double.valueOf(loans.get("annualInc")));
            loan.setIsIncV("null".equals(loans.get("isIncV"))? null : IncomeVerification.valueOf(loans.get("isIncV")));

            /*
            loan.setAcceptD(loans.get("acceptD"));
                    loan.setExpD(loans.get("expD"));
                    loan.setListD(loans.get("listD"));
                    loan.setCreditPullD(loans.get("creditPullD"));
                    loan.setReviewStatusD(loans.get("reviewStatusD"));
            */



            loan.setReviewStatus(ReviewStatus.valueOf(loans.get("reviewStatus")));
            loan.setDescription(loans.get("description"));
            loan.setPurpose(LoanPurpose.fromValue(loans.get("purpose").toUpperCase()));
            //loan.setAddrZip(loans.get("addrZip"));
            loan.setAddrState(loans.get("addrState"));
            loan.setInvestorCount(IntegerValueOf(loans.get("investorCount")));
            //loan.setIlsExpD(loans.get("ilsExpD"));
            loan.setInitialListStatus(loans.get("initialListStatus"));
            loan.setEmpTitle(loans.get("empTitle"));
            loan.setCreditInfo(new CreditInfo());
            loan.getCreditInfo().setAccNowDelinq(IntegerValueOf(loans.get("accNowDelinq")));
            loan.getCreditInfo().setAccOpenPast24Mths(Integer.valueOf(loans.get("accOpenPast24Mths")));
            loan.getCreditInfo().setBcOpenToBuy(DoubleValueOf(loans.get("bcOpenToBuy")));//:30000,
            loan.getCreditInfo().setPercentBcGt75(DoubleValueOf(loans.get("percentBcGt75")));//:23.0,
            loan.getCreditInfo().setBcUtil(DoubleValueOf(loans.get("bcUtil")));//:23.0,
            loan.getCreditInfo().setDti(Double.valueOf(loans.get("dti")));//:0.0,
            loan.getCreditInfo().setDelinq2Yrs(Integer.valueOf(loans.get("delinq2Yrs")));//:1,
            loan.getCreditInfo().setDelinqAmnt(Double.valueOf(loans.get("delinqAmnt")));//:0.0,
            //loan.getCreditInfo().setEarliestCrLine(loans.get("earliestCrLine"));//:"1984-09-15T00:00:00.000-07:00",
            loan.getCreditInfo().setFicoRangeLow(Integer.valueOf(loans.get("ficoRangeLow")));//:750,
            loan.getCreditInfo().setFicoRangeHigh(Integer.valueOf(loans.get("ficoRangeHigh")));//:754,
            loan.getCreditInfo().setInqLast6Mths(IntegerValueOf(loans.get("inqLast6Mths")));//:0,
            loan.getCreditInfo().setMthsSinceLastDelinq(IntegerValueOf(loans.get("mthsSinceLastDelinq")));//:90,
            loan.getCreditInfo().setMthsSinceLastRecord(IntegerValueOf(loans.get("mthsSinceLastRecord")));//:0,
            loan.getCreditInfo().setMthsSinceRecentInq(IntegerValueOf(loans.get("mthsSinceRecentInq")));//:14,
            loan.getCreditInfo().setMthsSinceRecentRevolDelinq(IntegerValueOf(loans.get("mthsSinceRecentRevolDelinq")));//:23,
            loan.getCreditInfo().setMthsSinceRecentBc(IntegerValueOf(loans.get("mthsSinceRecentBc")));//:23,
            loan.getCreditInfo().setMortAcc(Integer.valueOf(loans.get("mortAcc")));//:23,
            loan.getCreditInfo().setOpenAcc(Integer.valueOf(loans.get("openAcc")));//:3,
            loan.getCreditInfo().setPubRec(Integer.valueOf(loans.get("pubRec")));//:0,
            loan.getCreditInfo().setTotalBalExMort(DoubleValueOf(loans.get("totalBalExMort")));//:13944,
            loan.getCreditInfo().setRevolBal(Double.valueOf(loans.get("revolBal")));//:1.0,
            loan.getCreditInfo().setRevolUtil(DoubleValueOf(loans.get("revolUtil")));//:0.0,
            loan.getCreditInfo().setTotalBcLimit(Double.valueOf(loans.get("totalBcLimit")));//:23,
            loan.getCreditInfo().setTotalAcc(Integer.valueOf(loans.get("totalAcc")));//:4,
            loan.getCreditInfo().setTotalIlHighCreditLimit(Integer.valueOf(loans.get("totalIlHighCreditLimit")));//:12,
            loan.getCreditInfo().setNumRevAccts(IntegerValueOf(loans.get("numRevAccts")));//:28,
            loan.getCreditInfo().setMthsSinceRecentBcDlq(IntegerValueOf(loans.get("mthsSinceRecentBcDlq")));//:52,
            loan.getCreditInfo().setPubRecBankruptcies(Integer.valueOf(loans.get("pubRecBankruptcies")));//:0,
            loan.getCreditInfo().setNumAcctsEver120Ppd(Integer.valueOf(loans.get("numAcctsEver120Ppd")));//:12,
            loan.getCreditInfo().setChargeoffWithin12Mths(Integer.valueOf(loans.get("chargeoffWithin12Mths")));//:0,
            loan.getCreditInfo().setCollections12MthsExMed(Integer.valueOf(loans.get("collections12MthsExMed")));//:0,
            loan.getCreditInfo().setTaxLiens(Integer.valueOf(loans.get("taxLiens")));//:0,
            loan.getCreditInfo().setMthsSinceLastMajorDerog(IntegerValueOf(loans.get("mthsSinceLastMajorDerog")));//:12,
            loan.getCreditInfo().setNumSats(Integer.valueOf(loans.get("numSats")));//:8,
            loan.getCreditInfo().setNumTlOpPast12M(Integer.valueOf(loans.get("numTlOpPast12m")));//:0,
            loan.getCreditInfo().setMoSinRcntTl(Integer.valueOf(loans.get("moSinRcntTl")));//:12,
            loan.getCreditInfo().setTotHiCredLim(Integer.valueOf(loans.get("totHiCredLim")));//:12,
            loan.getCreditInfo().setTotCurBal(Integer.valueOf(loans.get("totCurBal")));//:12,
            loan.getCreditInfo().setAvgCurBal(Integer.valueOf(loans.get("avgCurBal")));//:12,
            loan.getCreditInfo().setNumBcTl(Integer.valueOf(loans.get("numBcTl")));//:12,
            loan.getCreditInfo().setNumActvBcTl(Integer.valueOf(loans.get("numActvBcTl")));//:12,
            loan.getCreditInfo().setNumBcSats(Integer.valueOf(loans.get("numBcSats")));//:7,
            loan.getCreditInfo().setPctTlNvrDlq(Integer.valueOf(loans.get("pctTlNvrDlq")));//:12,
            //loan.getCreditInfo().setNumTl90gDpd24m("numTl90gDpd24m"));//:12,
            //loan.getCreditInfo().setNum130dpd(loans.get("numTl30dpd"));//:12,
            loan.getCreditInfo().setNumTl120Dpd2M(IntegerValueOf(loans.get("numTl120dpd2m")));//:12,
            loan.getCreditInfo().setNumIlTl(Integer.valueOf(loans.get("numIlTl")));//:12,
            loan.getCreditInfo().setMoSinOldIlAcct(IntegerValueOf(loans.get("moSinOldIlAcct")));//:12,
            loan.getCreditInfo().setNumActvRevTl(Integer.valueOf(loans.get("numActvRevTl")));//:12,
            loan.getCreditInfo().setMoSinOldRevTlOp(Integer.valueOf(loans.get("moSinOldRevTlOp")));//:12,
            loan.getCreditInfo().setMoSinRcntRevTlOp(Integer.valueOf(loans.get("moSinRcntRevTlOp")));//:11,
            loan.getCreditInfo().setTotalRevHiLim(Integer.valueOf(loans.get("totalRevHiLim")));//:12,
            //loan.getCreditInfo().setNumRevT1BalGt0(loans.get("numRevTlBalGt0"));//:12,
            loan.getCreditInfo().setNumOpRevTl(Integer.valueOf(loans.get("numOpRevTl")));//:12,
            loan.getCreditInfo().setTotCollAmt(Integer.valueOf(loans.get("totCollAmt")));//:12

            browseLoansResult.getLoans().add(loan);
        }

        return browseLoansResult;
    }

    private final static Date DateValueOf(String value) throws ParseException {
        if ("null".equals(value) )
            return null;
        else {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            return formatter.parse(value);
        }
    }

    private final static Integer IntegerValueOf(String investorCount) {
        //if (investorCount == null)return null;
        return "null".equals(investorCount) ? null : Integer.valueOf(investorCount);
    }

    private final static Double DoubleValueOf(String investorCount) {
        //if (investorCount == null)return null;
        return "null".equals(investorCount) ? null : Double.valueOf(investorCount);
    }

    @Override
    public Map<String,Long> orderGetPortfolios()
    {
        try {
            Request req = prepareRequest("portfolios", Type.ACCOUNT);
            JsonParserFactory factory = JsonParserFactory.getInstance();
            JSONParser parser = factory.newJsonParser();
            Map map = parser.parseJson(executeWithRetry(req), "UTF-8");
            List<Map> portfoliosList = (List<Map>) map.get("myPortfolios");
            Map<String, Long> ret = new HashMap<String, Long>();
            for (int i = 0, size = portfoliosList.size(); i < size; i++) {
                Map mapPortfolio = portfoliosList.get(i);
                long id = Long.parseLong((String) mapPortfolio.get("portfolioId"));
                String name = (String) mapPortfolio.get("portfolioName");
                String desc = (String) mapPortfolio.get("portfolioDescription");

                ret.put(name, id);
            }

            return ret;
        }
        catch(Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }

    protected String getUrl(String reqSuffix, Type type) {
        String url = BASE_URL;
        if (type == Type.ACCOUNT || type == Type.ACCOUNT_POST || type == Type.NOTES) {
            url += "accounts/" + new String(investorId) + "/";
        } else if (type == Type.LOANS) {
            url += "loans/";
        }
        return url + reqSuffix;
    }

    protected char[] investorId() {
        return investorId;
    }

    protected Request prepareRequest(String reqSuffix, Type type) {
        Request request;
        if (type == Type.ACCOUNT_POST) {
            request = Request.Post(getUrl(reqSuffix, type));
        } else {
            request = Request.Get(getUrl(reqSuffix, type));
        }
        request.addHeader("Authorization", new String(apiKey));
        // All requests we are making would be of type json.
        request.addHeader("Content-Type", "application/json");
        return request;
    }

    public InputStream executeWithRetry(Request request)throws Exception
    {
        int numRetry =5;
        Exception lastEx=null;
        for (int i =0 ;i < 5 ; i++)
        {
            try {
                return request.execute().returnContent().asStream();
            }
            catch (Exception ex)
            {
                lastEx = ex;
                logger_.warn("Failed on attempt ["+i+"/"+numRetry+"]",ex);
            }
        }
        throw lastEx;
    }

    @Override
    public Set<Long> getAllInvestedLoans() throws IOException
    {
        LendingClubApi lendingClubApi = new LendingClubApi();
        Collection<OwnedNote> allOwnedNotes = lendingClubApi.getNotesOwned();
        Set<Long> ret = new HashSet<Long>();
        for (OwnedNote note : allOwnedNotes)
        {
            ret.add(note.getLoanId());
        }

        return ret;
    }


    public static void main(String[] args) throws Exception {
        LendingClubApi lendingClubApi = new LendingClubApi();
        lendingClubApi.getNotesOwned();
    }
}
