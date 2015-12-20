
package com.rp.p2p.model;

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
public enum ReviewStatus {

        APPROVED,
    NOT_APPROVED;

    public String value() {
        return name();
    }

    public static ReviewStatus fromValue(String v) {
        return valueOf(v);
    }

}
