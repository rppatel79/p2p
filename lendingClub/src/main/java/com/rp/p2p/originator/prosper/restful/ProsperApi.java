package com.rp.p2p.originator.prosper.restful;


import com.codesnippets4all.json.parsers.JsonParserFactory;
import com.rp.p2p.model.*;
import com.rp.p2p.originator.OriginatorApi;
import com.rp.util.ApplicationProperties;
import com.rp.util.db.HibernateUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ProsperApi implements OriginatorApi
{
    private final static Logger logger_ = Logger.getLogger(ProsperApi.class);

    private static final String BASE_URL = "https://api.prosper.com/api/";

    private static enum Type {
        ACCOUNT, LOANS, ACCOUNT_POST, NOTES
    };

    private final String prosperUsr_;
    private final String prosperPasswd_;
    public ProsperApi() throws IOException
    {
        prosperUsr_ = ApplicationProperties.getInstance().getProperty("PROSPER_USR");
        prosperPasswd_ = ApplicationProperties.getInstance().getProperty("PROSPER_PASSWD");
    }

    @Override
    public OrderInstructConfirmation orderSubmitOrders(Collection<Order> orders)
    {
        List<OrderConfirmation> orderConfirmations = new ArrayList<OrderConfirmation>(orders.size());

        for (Order order : orders) {
            try {
                Request request = prepareRequest(Type.ACCOUNT_POST);
                String jsonString = createJsonRequestString(order);
                request.bodyString(jsonString, ContentType.APPLICATION_JSON);
                //request.setHeader("listingId",""+order.getLoanId());
                //request.setHeader("amount",""+order.getRequestedAmount());

                JsonParserFactory factory = JsonParserFactory.getInstance();
                com.codesnippets4all.json.parsers.JSONParser parser = factory.newJsonParser();

                Response response = request.execute();
                HttpResponse httpResponse = response.returnResponse();
                String responseStr = EntityUtils.toString(httpResponse.getEntity());
                Map orderConfirmationsMap = parser.parseJson(responseStr);

                if (orderConfirmationsMap != null) {
                    OrderConfirmation orderConfirmation = new OrderConfirmation();
                    orderConfirmation.setLoanId(Long.valueOf((String) orderConfirmationsMap.get("ListingId")));
                    orderConfirmation.setRequestedAmount(Double.parseDouble((String)orderConfirmationsMap.get("RequestedAmount")));
                    orderConfirmation.setInvestedAmount((Double.parseDouble((String)orderConfirmationsMap.get("AmountInvested"))));
                    String status = (String) orderConfirmationsMap.get("State");
                    {
                        orderConfirmation.getExecutionStatus().add(OrderExecutionStatus.fromValue(status));
                    }

                    orderConfirmations.add(orderConfirmation);
                } else {
                    throw new RuntimeException("Invalid message:" + orderConfirmationsMap );
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        OrderInstructConfirmation orderInstructConfirmation = new OrderInstructConfirmation();
        orderInstructConfirmation.getOrderConfirmations().addAll(orderConfirmations);
        return orderInstructConfirmation;
    }

    private String createJsonRequestString(Order order) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"listingId\": ").append(order.getLoanId()).append(',');
        sb.append("\"amount\": ").append(order.getRequestedAmount())
                .append("}");
        return sb.toString();

    }

    @Override
    public Collection<OwnedNote> getNotesOwned()
    {
        List<Map<String,Object>> map;
        try {
            map = toMap(new JSONArray(prepareRequest(Type.NOTES).execute().returnContent().asString()));
            return convertNotesOwned(map);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public BrowseLoansResult getBrowseLoansResult(boolean allLoans) throws Exception {
        List<Map<String, Object>> map;
        try {
            map = toMap(new JSONArray(prepareRequest(Type.LOANS).execute().returnContent().asString()));
            return convertLoans(map);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public BrowseLoansResult getAndStoreBrowseLoansResult(boolean allLoans) throws Exception {
        BrowseLoansResult browseLoansResult = getBrowseLoansResult(allLoans);

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory(HibernateUtil.DbId.P2P);
        try {
            Session session = sessionFactory.openSession();
            for (LoanListing loanListing : browseLoansResult.getLoans()) {
                session.saveOrUpdate(loanListing);
            }
        }
        catch(Exception ex)
        {
            logger_.warn("Unable to save loan.  Continuing without failing",ex);
        }

        return browseLoansResult;
    }

    @Override
    public Set<Long> getAllInvestedLoans() throws IOException {
        throw new UnsupportedOperationException("Not completed yet");
    }

    private static List<Map<String, Object>> toMap(JSONArray object) throws JSONException {
        List<Map<String, Object>> ret = new LinkedList<Map<String, Object>>();
        for ( int i = 0 ; i < object.length() ; i++)
        {
            ret.add(toMap((JSONObject) object.get(i)));
        }

        return ret;
    }

    private static Map<String, Object> toMap(JSONObject object) throws JSONException {
        Map<String, Object> map = new HashMap<String, Object>();

        Iterator<String> keysItr = object.keys();
        while(keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = object.get(key);

            if(value instanceof JSONArray) {
                value = toList((JSONArray) value);
            }

            else if(value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }

    private static List<Object> toList(JSONArray array) throws JSONException {
        List<Object> list = new ArrayList<Object>();
        for(int i = 0; i < array.length(); i++) {
            Object value = array.get(i);
            if(value instanceof JSONArray) {
                value = toList((JSONArray) value);
            }

            else if(value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }

    private Collection<OwnedNote> convertNotesOwned(List<Map<String, Object>> allNotesOwned) throws ParseException {
        //throw new UnsupportedOperationException();

        Collection<OwnedNote> ret = new ArrayList<OwnedNote>(allNotesOwned.size());
        for (Map<String,Object>  noteOwned : allNotesOwned)
        {
            OwnedNote ownedNote = new OwnedNote();
            {
                ownedNote.setLoanStatus((String)noteOwned.get("NoteStatusDescription"));
                ownedNote.setLoanId((Integer)noteOwned.get("ListingNumber"));
                ownedNote.setNoteId((String)noteOwned.get("LoanNoteID"));
                ownedNote.setGrade(LoanGrade.valueOf((noteOwned.get("ProsperRating")).equals("AA")?"A":(String)noteOwned.get("ProsperRating")));
                ownedNote.setLoanAmount((Double)noteOwned.get("TotalAmountBorrowed"));
                //ownedNote.setNoteAmount(Double.valueOf(noteOwned.get("noteAmount")));
                ownedNote.setInterestRate((Double)noteOwned.get("BorrowerRate"));
                //ownedNote.setOrderId(Long.valueOf(noteOwned.get("orderId")));
                ownedNote.setTerm((Integer)noteOwned.get("Term"));
                ownedNote.setIssueDate(DateValueOf((String)noteOwned.get("OriginationDate")));
                //ownedNote.setOrderDate(DateValueOf(noteOwned.get("orderDate")));
                //ownedNote.setLoanStatusDate(DateValueOf(noteOwned.get("loanStatusDate")));
                ownedNote.setPaymentsReceived((Double)noteOwned.get("PrincipalRepaid"));
            }

            ret.add(ownedNote);
        }

        return ret;
    }

    private BrowseLoansResult convertLoans(List<Map<String, Object>> allLoans) throws ParseException {

        BrowseLoansResult browseLoansResult = new BrowseLoansResult();
        for (Map<String,Object>  loans : allLoans)
        {
            LoanListing loan = new LoanListing();

            loan.setId((Integer)(loans.get("ListingNumber")));
            loan.setMemberId((String)loans.get("MemberKey"));
            loan.setLoanAmnt((Double)(loans.get("ListingRequestAmount")));
            loan.setFundedAmnt((Double)(loans.get("ListingAmountFunded")));
            loan.setTerm((Integer)(loans.get("ListingTerm")));
            loan.setIntRate((Double)loans.get("BorrowerRate"));

            //expDefaultRate;
            //serviceFeeRate;
            //installment;
            loan.setGrade(LoanGrade.fromValue(((String)loans.get("ProsperRating")).equals("AA")?"A":(String)loans.get("ProsperRating")));
            //subGrade;
            loan.setEmpLength((Integer)(loans.get("MonthsEmployed")));
            loan.setHomeOwnership((Boolean)(loans.get("IsHomeowner")) ? HomeOwnership.OWN : HomeOwnership.OTHER);
            //otherHomeOwnership;
            loan.setAnnualInc((Double)(loans.get("StatedMonthlyIncome"))*12.0);
            loan.setIsIncV((Boolean)(loans.get("IncomeVerifiable")) ? IncomeVerification.VERIFIED : IncomeVerification.NOT_VERIFIED);
            //acceptD;
            //expD;
            loan.setListD(DateValueOf((String)loans.get("ListingCreationDate")));
            loan.setCreditPullD(DateValueOf((String)loans.get("CreditPullDate")));
            //reviewStatusD;
            loan.setReviewStatus("Active".equals(loans.get("ListingStatusDescription"))?ReviewStatus.APPROVED:ReviewStatus.NOT_APPROVED);
            //url;
            loan.setDescription((String)loans.get("ListingStatusDescription"));
            //purpose;
            loan.setTitle((String)loans.get("ListingTitle"));
            loan.setAddrCity((String)loans.get("BorrowerCity"));
            loan.setAddrState((String)loans.get("BorrowerState"));
            //msa;
            //investorCount;
            //ilsExpD;
            //initialListStatus;

/*
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
            loan.getCreditInfo().setInqLast6Mths(Integer.valueOf(loans.get("inqLast6Mths")));//:0,
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
            loan.getCreditInfo().setNumRevAccts(Integer.valueOf(loans.get("numRevAccts")));//:28,
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
*/
            browseLoansResult.getLoans().add(loan);
        }

        return browseLoansResult;

    }

    private final static Date DateValueOf(String value) throws ParseException {

        if ("null".equals(value) )
            return null;
        else {
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
                return formatter.parse(value);
            }
            catch(ParseException ex)
            {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                return formatter.parse(value);
            }
        }
    }
    @Override
    public Map<String,Long> orderGetPortfolios()
    {
        throw new UnsupportedOperationException();
        /*
        try {
            Request req = prepareRequest("portfolios", Type.ACCOUNT);
            JsonParserFactory factory = JsonParserFactory.getInstance();
            JSONParser parser = factory.newJsonParser();
            Map map = parser.parseJson(req.execute().returnContent().asStream(), "UTF-8");
            List<Map> portfoliosList = (List<Map>) map.get("myPortfolios");
            Map<String, Long> ret = new HashMap<String, Long>();
            for (int i = 0, size = portfoliosList.size(); i < size; i++) {
                Map mapPortfolio = portfoliosList.get(i);
                long id = Long.parseLong((String) mapPortfolio.get("portfolioId"));
                String name = (String) mapPortfolio.get("portfolioName");
                String description = (String) mapPortfolio.get("portfolioDescription");

                ret.put(name, id);
            }

            return ret;
        }
        catch(Exception ex)
        {
            throw new RuntimeException(ex);
        }
        */
    }

    protected String getUrl(Type type) {
        String url = BASE_URL;
        if (type == Type.LOANS) {
            url += "Listings";
        }
        else if (type == Type.NOTES)
        {
            url+="Notes";
        }
        else if(type == Type.ACCOUNT_POST)
        {
            url+="invest";
        }
        else
        {
            throw new IllegalArgumentException("Unknown type ["+type+"]");
        }
        return url ;
    }

    protected Request prepareRequest(Type type) throws UnsupportedEncodingException {
        Request request;
        if (type == Type.ACCOUNT_POST) {
            request = Request.Post(getUrl(type));
        } else {
            request = Request.Get(getUrl(type));
        }
        request.addHeader("Authorization", "Basic " + DatatypeConverter.printBase64Binary((prosperUsr_+ ":" + prosperPasswd_).getBytes("UTF-8")));
        return request;
    }

    public static void main(String[] args) throws Exception {
        ProsperApi prosperApi = new ProsperApi();
        /*prosperApi.getBrowseLoansResult(false);*/
        /*prosperApi.getNotesOwned();*/
        Order order = new Order();
        order.setRequestedAmount(25.0);
        order.setLoanId(123l);
        prosperApi.orderSubmitOrders(Collections.singletonList(order));
    }
}
