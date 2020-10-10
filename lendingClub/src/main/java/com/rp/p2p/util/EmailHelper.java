package com.rp.p2p.util;

import com.rp.p2p.Main;
import com.rp.p2p.model.LoanListing;
import com.rp.p2p.model.OrderConfirmation;
import com.rp.p2p.order_executor.OrderExecutor;
import com.rp.util.ApplicationProperties;
import com.rp.util.Mailer;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class EmailHelper
{
    public static void sendEmail(Main.SourceType sourceType, OrderExecutor.OrderStatus orderStatus, List<LoanListing> toOrder) throws MessagingException,IOException {
        String subject = "P2P Order [" + sourceType + "] " + (orderStatus.getFailed().size() == 0 ? "Completed" : "FAILED");

        StringBuilder msg = new StringBuilder();
        msg.append("<HTML><body>");

        if (orderStatus.getSuccess().size() > 0) {
            StringBuilder successTableBuilder = buildTable(orderStatus.getSuccess(), true);
            msg.append(successTableBuilder);
        }
        if (orderStatus.getFailed().size() > 0) {
            StringBuilder failureTableBuilder = buildTable(orderStatus.getFailed(), false);
            msg.append(failureTableBuilder);
        }
        msg.append("</body></HTML>");

        Mailer.getDefaultMailer().sendMessage(Collections.singleton(ApplicationProperties.getInstance().getProperty("EMAIL_TO")),subject,msg.toString());
    }

    private static StringBuilder buildTable(Set<OrderConfirmation> orderConfirmation, boolean isSuccessList) {
        StringBuilder builder = new StringBuilder();
        builder.append("<TABLE BORDER=\"5\">");
        builder.append("<TH COLSPAN=\"5\">");
        builder.append("<H3><BR>").append(isSuccessList?"Successful":"Failures").append("Execution</H3>");
        builder.append("</TH>");
        builder.append("<TD>");
        builder.append("<TH>Loan Id</TH>");
        builder.append("<TH>Invested Amount</TH>");
        builder.append("<TH>ExecutionStatus</TH>");
        builder.append("<TH>Grade</TH>");
        builder.append("<TH>Rate</TH>");
        builder.append("</TD>");
        for (OrderConfirmation confirmation : orderConfirmation) {
            builder.append("<TD>");
            builder.append("<TR>").append(confirmation.getLoanId()).append("</TR>");
            builder.append("<TR>").append(confirmation.getInvestedAmount()).append("</TR>");
            builder.append("<TR>").append(confirmation.getExecutionStatus()).append("</TR>");
            builder.append("<TR>").append("</TR>");
            builder.append("<TR>").append("</TR>");
            builder.append("</TD>");
        }
        builder.append("</TABLE>");

        return builder;
    }
}
