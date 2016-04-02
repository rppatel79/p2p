
package com.rp.p2p.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReviewStatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ReviewStatus">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="APPROVED"/>
 *     &lt;enumeration value="NOT_APPROVED"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ReviewStatus")
@XmlEnum
@DynamoDBDocument
public enum ReviewStatus {

        APPROVED,
    NOT_APPROVED,
    CURRENT,
    FULLY_PAID,
    IN_GRACE_PERIOD,
    LATE_16_30,
    LATE_31_120,
    DEFAULT,
    ISSUED,
    CHARGED_OFF;

    public String value() {
        return name();
    }

    public static ReviewStatus fromValue(String v) {
        if ("LATE_(16-30_DAYS)".equals(v))
            return LATE_31_120;
        else if ("LATE_(31-120_DAYS)".equals(v))
            return LATE_31_120;
        else
            return valueOf(v);
    }

}
