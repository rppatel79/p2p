package com.rp.p2p;

import com.rp.p2p.analytics.P2pPicksApi;
import com.rp.p2p.loan.db.BrowseLoansResultDao;
import com.rp.p2p.loan_selector.FilteredLoanSelector;
import com.rp.p2p.loan_selector.LoansSelector;
import com.rp.p2p.model.*;
import com.rp.p2p.order_executor.lending_club.wsdl.OrderExecutor;
import com.rp.p2p.originator.DbOriginatorApi;
import com.rp.p2p.originator.lending_club.restful.LendingClubApi;
import com.rp.p2p.originator.OriginatorApi;
import com.rp.util.ApplicationProperties;
import com.rp.util.Mailer;
import com.rp.util.db.HibernateUtil;
import org.apache.log4j.Logger;

import javax.mail.MessagingException;
import java.io.*;
import java.util.*;

public class Main
{
    private final static Logger logger_ =Logger.getLogger(Main.class);

    public static Map<String, Long> getPortfolios(OriginatorApi originatorApi) {
        //TODO
        //return originatorApi.orderGetPortfolios();
        Map<String,Long> ret = new HashMap<String, Long>();
        ret.put(SourceType.filterSource.getPortfolioName(),36242193L);
        ret.put(SourceType.P2pPicks.getPortfolioName(),36332059L);

        return ret;
    }

    public enum SourceType {
        filterSource("AutoExecution"),P2pPicks("P2pPicks");

        private String portfolioName_;
        SourceType(String portfolioName)
        {
            portfolioName_=portfolioName;
        }

        public String getPortfolioName() {
            return portfolioName_;
        }
    };

    public static final void main(String[] args) throws Exception {
        SourceType sourceType=null;
        try {
            if (args.length < 1) {
                usage();
                return;
            }
            sourceType = SourceType.valueOf(args[0]);

            Double requestAmount = null;
            if (args.length == 2)
                requestAmount  = Double.valueOf(args[1]);
            boolean execute = requestAmount != null;

            logger_.info("Executing [" + sourceType + "] with execute [" + execute + "]");

            final DbOriginatorApi originatorApi = new DbOriginatorApi(new LendingClubApi(),new BrowseLoansResultDao());
            Map<String, Long> portfolioNameToId = getPortfolios(originatorApi);

            List<LoanListing> toOrder;
            if (SourceType.filterSource == sourceType) {
                BrowseLoansResult browseLoansResult = originatorApi.getAndStoreBrowseLoansResult(false);
                FilteredLoanSelector filteredLoanSelector = new FilteredLoanSelector();
                toOrder = filteredLoanSelector.select(Collections.unmodifiableSet(originatorApi.getAllInvestedLoans()),browseLoansResult.getLoans());
            } else {
                P2pPicksApi p2pPicksApi = new P2pPicksApi();
                P2pPicksApi.PicksResponse picksResponse = p2pPicksApi.list(null, "profit-maximizer");

                BrowseLoansResult browseLoansResult = originatorApi.getBrowseLoansResult(true);
                Map<Long, LoanListing> allListings = new HashMap<Long, LoanListing>();

                for (LoanListing ll : browseLoansResult.getLoans()) {
                    if (allListings.put(ll.getId(), ll) != null)
                        logger_.info("Duplicate loan id:" + ll.getId());
                }

                //build loan
                final List<LoanListing> loanListings = new ArrayList<LoanListing>(picksResponse.response.picks.size());
                for (P2pPicksApi.Pick pick : picksResponse.response.picks) {
                    LoanListing loanListing2 = allListings.get(pick.loan_id);
                    if (loanListing2 == null)
                        logger_.info("" + pick.loan_id + " is marked to buy, but not avaliable in lending club");
                    else
                        loanListings.add(loanListing2);
                }
                LoansSelector selector = new LoansSelector() {
                    private Set<LoanGrade> VALID_GRADE = new HashSet<LoanGrade>();

                    {
                        VALID_GRADE.add(LoanGrade.A);
                        VALID_GRADE.add(LoanGrade.B);
                        VALID_GRADE.add(LoanGrade.C);
                    }

                    private Set<Integer> VALID_TERM = new HashSet<Integer>();

                    {
                        VALID_TERM.add(36);
                    }


                    @Override
                    public List<LoanListing> select(Set<Long> allInvestedLoans,List<LoanListing> loanSelector) throws Exception {
                        List<LoanListing> ret = new ArrayList<LoanListing>();

                        //final Set<Long> allInvestedLoans = originatorApi.getAllInvestedLoans();
                        for (LoanListing loan : loanSelector) {
                            if (!VALID_GRADE.contains(loan.getGrade())) {
                                logger_.info("Failed VALID_GRADE" + " " + loan.getId());
                                continue;
                            } else if (!VALID_TERM.contains(loan.getTerm())) {
                                logger_.info("Failed VALID_TERM" + " " + loan.getId());
                                continue;
                            }else if( (loan.getLoanAmnt() / loan.getAnnualInc()) > 3.5 )
                            {
                                logger_.info("Failed (loan.getLoanAmnt() / loan.getAnnualInc())" + " " + loan.getId());
                                continue;
                            }
                            else if (loan.getCreditInfo().getDti() > 40)
                            {
                                logger_.info("Failed loan.getCreditInfo().getDti()" + " " + loan.getId());
                                continue;
                            }
                            else if (loan.getCreditInfo().getPubRec() != null && loan.getCreditInfo().getPubRec() > 0)
                            {
                                logger_.info("Failed loan.getCreditInfo().getPubRec()" + " " + loan.getId());
                                continue;
                            }
                            else if (loan.getLoanAmnt() > 25000)
                            {
                                logger_.info("Failed loan.getLoanAmnt()" + " " + loan.getId());
                                continue;
                            }
                            else if (loan.getEmpTitle() == null || "N/A".equalsIgnoreCase(loan.getEmpTitle()))
                            {
                                logger_.info("Failed loan.getEmpTitle()" + " " + loan.getId());
                                continue;
                            }
                            else if (loan.getCreditInfo().getMthsSinceLastDelinq() != null && loan.getCreditInfo().getMthsSinceLastDelinq() < (12*7) )
                            {
                                logger_.info("Failed loan.getCreditInfo().getMthsSinceLastDelinq()" + " " + loan.getId());
                                continue;
                            }
                            else if (loan.getCreditInfo().getPubRec() != null && loan.getCreditInfo().getPubRec() > 0)
                            {
                                logger_.info("Failed loan.getCreditInfo().getPubRec()" + " " + loan.getId());
                                continue;
                            }
                            {
                                if (allInvestedLoans.contains(loan.getId())) {
                                    logger_.info("Failed allInvestedLoans.contains(loan.getId())" + " " + loan.getId());
                                    continue;
                                }
                            }

                            ret.add(loan);
                        }

                        return ret;
                    }
                };
                toOrder = selector.select(Collections.unmodifiableSet(originatorApi.getAllInvestedLoans()),loanListings);
            }

            if (execute) {
                // build order
                final List<Order> orders = new ArrayList<Order>(toOrder.size());
                final Map<Long, LoanListing> loanListingMap = new HashMap<Long, LoanListing>();
                {
                    for (LoanListing ll : toOrder) {
                        Order order = new Order();
                        {
                            Long portfolioId = portfolioNameToId.get(sourceType.getPortfolioName());
                            order.setPortfolioId(portfolioId);
                            order.setLoanId(ll.getId());
                            order.setRequestedAmount(requestAmount);
                        }
                        orders.add(order);
                        loanListingMap.put(ll.getId(),ll);
                    }
                }
                logger_.info("Buyings:");
                for (Order order : orders) {
                    logger_.info("\t" + order.getLoanId());
                }

                if (orders.size() > 0) {
                    com.rp.p2p.order_executor.OrderExecutor.OrderStatus orderStatus=(new OrderExecutor()).order(orders);
                    new EmailHelper().sendEmail(sourceType, orderStatus, loanListingMap);
                }
            }
        }
        catch(Exception ex)
        {
            try {
                new EmailHelper().sendException(sourceType, ex);
            }
            catch (Exception sendMsgException)
            {
                logger_.error("Unable to send message.  Logging the error, and moving forward",ex);
            }
            logger_.error("Unhandled exception",ex);
            throw ex;
        }
        finally {
            HibernateUtil.shutdownAll();
        }
    }

    private static void usage() {
        logger_.info(Main.class.getName() + " <filterSource|P2pPicks> <requestedAmount>");
    }
    public static class EmailHelper
    {
        private final static String APPLICATION_NAME = "P2P Order";
        private Collection<String> to_;
        public EmailHelper() throws IOException {
            to_ = Collections.unmodifiableCollection(Collections.singleton(ApplicationProperties.getInstance().getProperty("EMAIL_TO")));
        }

        public void sendEmail(Main.SourceType sourceType, com.rp.p2p.order_executor.OrderExecutor.OrderStatus orderStatus, Map<Long, LoanListing> loanListingMap) throws MessagingException, IOException {
            String subject = APPLICATION_NAME+" [" + sourceType + "] " + (orderStatus.getFailed().size() == 0 ? "Completed" : "FAILED");

            StringBuilder msg = new StringBuilder();
            msg.append("<HTML><body>");

            if (orderStatus.getSuccess().size() > 0)
            {
                StringBuilder successTableBuilder = buildTable(orderStatus.getSuccess(), loanListingMap,true);
                msg.append(successTableBuilder);
            }
            if (orderStatus.getFailed().size() > 0) {
                StringBuilder failureTableBuilder = buildTable(orderStatus.getFailed(), loanListingMap,false);
                msg.append(failureTableBuilder);
            }
            msg.append("</body></HTML>");

            Mailer.getDefaultMailer().sendMessage(to_,subject,msg.toString());
        }

        private static StringBuilder buildTable(Set<OrderConfirmation> orderConfirmation, Map<Long, LoanListing> loanListingMap,boolean isSuccessList) {
            StringBuilder builder = new StringBuilder();
            builder.append("<TABLE BORDER=\"5\">");

            builder.append("<TR>");
            builder.append("<TH COLSPAN=\"5\">");
            builder.append("<H3><BR>").append(isSuccessList?"Successful":"Failures").append(" Execution</H3>");
            builder.append("</TH>");
            builder.append("</TR>");

            builder.append("<TR>");
            builder.append("<TH>Loan Id</TH>");
            builder.append("<TH>Invested Amount</TH>");
            builder.append("<TH>ExecutionStatus</TH>");
            builder.append("<TH>Grade</TH>");
            builder.append("<TH>Rate</TH>");
            builder.append("</TR>");

            for (OrderConfirmation confirmation : orderConfirmation)
            {
                LoanListing loanListing = loanListingMap.get(confirmation.getLoanId());

                builder.append("<TR>");
                builder.append("<TD>").append(confirmation.getLoanId()).append("</TD>");
                builder.append("<TD>").append(confirmation.getInvestedAmount()).append("</TD>");
                builder.append("<TD>").append(confirmation.getExecutionStatus()).append("</TD>");

                if (loanListing == null) {
                    builder.append("<TD>").append("</TD>");
                    builder.append("<TD>").append("</TD>");
                }
                else
                {
                    builder.append("<TD>").append(loanListing.getGrade()+"-"+loanListing.getSubGrade()).append("</TD>");
                    builder.append("<TD>").append(loanListing.getIntRate()).append("</TD>");
                }
                builder.append("</TR>");
            }
            builder.append("</TABLE>");

            return builder;
        }

        public void sendException(SourceType sourceType, Exception ex) throws MessagingException, IOException {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(baos);
            ex.printStackTrace(ps);
            ps.close();

            String subject = APPLICATION_NAME+" :Unhandled Exception";

            StringBuilder msg = new StringBuilder();
            msg.append("<html>").append("<body>");
            msg.append(baos.toString().replace("\n", "<br>").replace("\r","<br>").replace("\t","<p>"));
            msg.append("</body>").append("</html>");

            Mailer.getDefaultMailer().sendMessage(to_, subject, msg.toString());
        }
    }
}
