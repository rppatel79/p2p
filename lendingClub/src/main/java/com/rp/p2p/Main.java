package com.rp.p2p;

import com.rp.p2p.loan_selector.FilteredLoansSelector;
import com.rp.p2p.loan_selector.RhinoLoanSelector;
import com.rp.p2p.model.BrowseLoansResult;
import com.rp.p2p.model.LoanListing;
import com.rp.p2p.model.Order;
import com.rp.p2p.model.OrderConfirmation;
import com.rp.p2p.order_executor.lending_club.wsdl.OrderExecutor;
import com.rp.p2p.originator.DbOriginatorApi;
import com.rp.p2p.originator.OriginatorApi;
import com.rp.p2p.originator.lending_club.restful.LendingClubApi;
import com.rp.util.ApplicationProperties;
import com.rp.util.Mailer;
import com.rp.util.db.HibernateUtil;
import org.apache.log4j.Logger;

import javax.mail.MessagingException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

public class Main
{

    private final static Logger logger_ =Logger.getLogger(Main.class);

    public static Map<String, Long> getPortfolios(OriginatorApi originatorApi) {
        final Long FILTERSOURCE_PORTFOLIO_ID = 36242193L;
        final Long FILTERSOURCE_PASTOR_PORTFOLIO_ID = 99882052L;

        Map<String, Long> ret = new HashMap<>();
        ret.put(SourceType.filterSource.getPortfolioName(), FILTERSOURCE_PORTFOLIO_ID);
        ret.put(SourceType.filterSource_Pastor.getPortfolioName(), FILTERSOURCE_PASTOR_PORTFOLIO_ID);

        return ret;
    }

    public static void main(String[] args) throws Exception {
        SourceType sourceType = null;
        try {
            if (args.length < 1) {
                usage();
                return;
            }
            sourceType = SourceType.valueOf(args[0]);

            Double requestAmount = null;
            if (args.length == 2)
                requestAmount = Double.valueOf(args[1]);
            boolean execute = requestAmount != null;

            run(sourceType, requestAmount, execute);
        } catch (Exception ex) {
            try {
                new EmailHelper().sendException(sourceType, ex);
            } catch (Exception sendMsgException) {
                logger_.error("Unable to send message.  Logging the error, and moving forward", ex);
            }
            logger_.error("Unhandled exception", ex);
            throw ex;
        } finally {
            HibernateUtil.shutdownAll();
        }
    }

    public static List<LoanListing> run(SourceType sourceType, Double requestAmount, boolean execute) throws Exception {
        logger_.info("Executing [" + sourceType + "] with execute [" + execute + "]");

        final DbOriginatorApi originatorApi = new DbOriginatorApi(new LendingClubApi()
                , new HashSet<>(
                Arrays.asList(new com.rp.p2p.loan.db.BrowseLoansResultDao(),
                        new com.rp.p2p.loan.dynamo.BrowseLoansResultDao())));
        Map<String, Long> portfolioNameToId = getPortfolios(originatorApi);

        BrowseLoansResult browseLoansResult = originatorApi.getAndStoreBrowseLoansResult(false);
        FilteredLoansSelector filteredLoansSelector = new FilteredLoansSelector(new RhinoLoanSelector(sourceType.getFilterRhino()));
        List<LoanListing> toOrder = filteredLoansSelector.select(Collections.unmodifiableSet(originatorApi.getAllInvestedLoans()), browseLoansResult.getLoans());

        if (execute) {
            // build order
            final List<Order> orders = new ArrayList<>(toOrder.size());
            final Map<Long, LoanListing> loanListingMap = new HashMap<>();
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
                    loanListingMap.put(ll.getId(), ll);
                }
            }
            logger_.info("Buyings:");
            for (Order order : orders) {
                logger_.info("\t" + order.getLoanId());
            }

            if (orders.size() > 0) {
                com.rp.p2p.order_executor.OrderExecutor.OrderStatus orderStatus = new OrderExecutor().order(orders);
                new EmailHelper().sendEmail(sourceType, orderStatus, loanListingMap);
            }
        }

        return toOrder;
    }


    public enum SourceType {
        filterSource("AutoExecution", "filterLoanSelector.js"), filterSource_Pastor("Pastor", "pastor.js");

        private final String portfolioName_;
        private final String filterRhino_;

        SourceType(String portfolioName, String filterRhino) {
            portfolioName_ = portfolioName;
            filterRhino_ = filterRhino;
        }

        public String getPortfolioName() {
            return portfolioName_;
        }

        public String getFilterRhino() {
            return filterRhino_;
        }
    }

    private static void usage() {
        logger_.info(Main.class.getName() + " <filterSource|filterSource_Pastor> <requestedAmount>");
    }

    public static class EmailHelper {
        private final static String APPLICATION_NAME = "P2P Order";
        private final Collection<String> to_;

        public EmailHelper() throws IOException {
            to_ = Collections.unmodifiableCollection(Collections.singleton(ApplicationProperties.getInstance().getProperty("EMAIL_TO")));
        }

        private static StringBuilder buildTable(Set<OrderConfirmation> orderConfirmation, Map<Long, LoanListing> loanListingMap, boolean isSuccessList) {
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
                builder.append("<TD>").append("<a href=\"").append(loanListing.getUrl()).append("\">").append(confirmation.getLoanId()).append("</a>").append("</TD>");
                builder.append("<TD>").append(confirmation.getInvestedAmount()).append("</TD>");
                builder.append("<TD>").append(confirmation.getExecutionStatus()).append("</TD>");

                {
                    builder.append("<TD>").append(loanListing.getGrade()).append("-").append(loanListing.getSubGrade()).append("</TD>");
                    builder.append("<TD>").append(loanListing.getIntRate()).append("</TD>");
                }
                builder.append("</TR>");
            }
            builder.append("</TABLE>");

            return builder;
        }

        public void sendEmail(Main.SourceType sourceType, com.rp.p2p.order_executor.OrderExecutor.OrderStatus orderStatus, Map<Long, LoanListing> loanListingMap) throws MessagingException, IOException {
            String subject = APPLICATION_NAME + " [" + sourceType + "] " + (orderStatus.getFailed().size() == 0 ? "Completed" : "FAILED");

            StringBuilder msg = new StringBuilder();
            msg.append("<HTML><body>");

            if (orderStatus.getSuccess().size() > 0) {
                StringBuilder successTableBuilder = buildTable(orderStatus.getSuccess(), loanListingMap, true);
                msg.append(successTableBuilder);
            }
            if (orderStatus.getFailed().size() > 0) {
                StringBuilder failureTableBuilder = buildTable(orderStatus.getFailed(), loanListingMap, false);
                msg.append(failureTableBuilder);
            }
            msg.append("</body></HTML>");

            Mailer.getDefaultMailer().sendMessage(to_, subject, msg.toString());
        }

        public void sendException(SourceType sourceType, Exception ex) throws MessagingException, IOException {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(baos);
            ex.printStackTrace(ps);
            ps.close();

            String subject = APPLICATION_NAME + " :Unhandled Exception";

            String msg = "<html>" + "<body>" +
                    baos.toString().replace("\n", "<br>").replace("\r", "<br>").replace("\t", "<p>") +
                    "</body>" + "</html>";
            Mailer.getDefaultMailer().sendMessage(to_, subject, msg);
        }
    }
}
