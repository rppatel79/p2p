package com.rp.p2p.model;

import java.util.Date;

public class OwnedNote {
    private String loanStatus_;
    private long loanId_;
    private String noteId_;
    private LoanGrade grade_;
    private double loanAmount_;
    private double noteAmount_;
    private double interestRate_;
    private long orderId_;
    private int term_;
    private Date issueDate_;
    private Date orderDate_;
    private Date loanStatusDate_;
    private double paymentsReceived_;

    public String getLoanStatus() {
        return loanStatus_;
    }

    public void setLoanStatus(String loanStatus) {
        loanStatus_ = loanStatus;
    }

    public long getLoanId() {
        return loanId_;
    }

    public void setLoanId(long loanId) {
        loanId_ = loanId;
    }

    public String getNoteId() {
        return noteId_;
    }

    public void setNoteId(String noteId) {
        noteId_ = noteId;
    }

    public LoanGrade getGrade() {
        return grade_;
    }

    public void setGrade(LoanGrade grade) {
        grade_ = grade;
    }

    public double getLoanAmount() {
        return loanAmount_;
    }

    public void setLoanAmount(double loanAmount) {
        loanAmount_ = loanAmount;
    }

    public double getNoteAmount() {
        return noteAmount_;
    }

    public void setNoteAmount(double noteAmount) {
        noteAmount_ = noteAmount;
    }

    public double getInterestRate() {
        return interestRate_;
    }

    public void setInterestRate(double interestRate) {
        interestRate_ = interestRate;
    }

    public long getOrderId() {
        return orderId_;
    }

    public void setOrderId(long orderId) {
        orderId_ = orderId;
    }

    public int getTerm() {
        return term_;
    }

    public void setTerm(int term) {
        term_ = term;
    }

    public Date getIssueDate() {
        return issueDate_;
    }

    public void setIssueDate(Date issueDate) {
        issueDate_ = issueDate;
    }

    public Date getOrderDate() {
        return orderDate_;
    }

    public void setOrderDate(Date orderDate) {
        orderDate_ = orderDate;
    }

    public Date getLoanStatusDate() {
        return loanStatusDate_;
    }

    public void setLoanStatusDate(Date loanStatusDate) {
        loanStatusDate_ = loanStatusDate;
    }

    public double getPaymentsReceived() {
        return paymentsReceived_;
    }

    public void setPaymentsReceived(double paymentsReceived) {
        paymentsReceived_ = paymentsReceived;
    }
}
