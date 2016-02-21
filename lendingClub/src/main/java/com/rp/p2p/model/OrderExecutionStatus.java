
package com.rp.p2p.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrderExecutionStatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="OrderExecutionStatus">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ORDER_FULFILLED"/>
 *     &lt;enumeration value="LOAN_AMNT_EXCEEDED"/>
 *     &lt;enumeration value="NOT_INFUNDING_LOAN"/>
 *     &lt;enumeration value="REQUESTED_AMNT_LOW"/>
 *     &lt;enumeration value="REQUESTED_AMNT_ROUNDED"/>
 *     &lt;enumeration value="AUG_BY_MERGE"/>
 *     &lt;enumeration value="ELIM_BY_MERGE"/>
 *     &lt;enumeration value="INSUFFICIENT_CASH"/>
 *     &lt;enumeration value="MAX_AMNT_PER_LOAN_EXCEEDED"/>
 *     &lt;enumeration value="MAX_PERCENT_PER_LOAN_EXCEEDED"/>
 *     &lt;enumeration value="MAX_PERCENT_PER_LOAN_TIME_EXCEEDED"/>
 *     &lt;enumeration value="MAX_AMNT_PER_LOAN_TIME_EXCEEDED"/>
 *     &lt;enumeration value="UNKNOWN_ERROR"/>
 *     &lt;enumeration value="NOT_AN_INVESTOR"/>
 *     &lt;enumeration value="NOT_A_VALID_INVESTMENT"/>
 *     &lt;enumeration value="NOTE_ADDED_TO_PORTFOLIO"/>
 *     &lt;enumeration value="NOT_A_VALID_PORTFOLIO"/>
 *     &lt;enumeration value="ERROR_ADDING_NOTE_TO_PORTFOLIO"/>
 *     &lt;enumeration value="SYSTEM_BUSY"/>
 *     &lt;enumeration value="TEMPORARILY_UNAVAILABLE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "OrderExecutionStatus")
@XmlEnum
@DynamoDBDocument
public enum OrderExecutionStatus {

    ORDER_FULFILLED,
    LOAN_AMNT_EXCEEDED,
    NOT_INFUNDING_LOAN,
    REQUESTED_AMNT_LOW,
    REQUESTED_AMNT_ROUNDED,
    AUG_BY_MERGE,
    ELIM_BY_MERGE,
    INSUFFICIENT_CASH,
    MAX_AMNT_PER_LOAN_EXCEEDED,
    MAX_PERCENT_PER_LOAN_EXCEEDED,
    MAX_PERCENT_PER_LOAN_TIME_EXCEEDED,
    MAX_AMNT_PER_LOAN_TIME_EXCEEDED,
    UNKNOWN_ERROR,
    NOT_AN_INVESTOR,
    NOT_A_VALID_INVESTMENT,
    NOTE_ADDED_TO_PORTFOLIO,
    NOT_A_VALID_PORTFOLIO,
    ERROR_ADDING_NOTE_TO_PORTFOLIO,
    SYSTEM_BUSY,
    TEMPORARILY_UNAVAILABLE,
    NOT_AN_IN_FUNDING_LOAN,
    REQUESTED_AMOUNT_ROUNDED,
    LOAN_AMOUNT_EXCEEDED;

    public String value() {
        return name();
    }

    public static OrderExecutionStatus fromValue(String v) {
        return valueOf(v);
    }

}
