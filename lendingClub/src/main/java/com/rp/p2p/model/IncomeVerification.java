
package com.rp.p2p.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IncomeVerification.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="IncomeVerification">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="NOT_VERIFIED"/>
 *     &lt;enumeration value="SOURCE_VERIFIED"/>
 *     &lt;enumeration value="VERIFIED"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "IncomeVerification")
@XmlEnum
public enum IncomeVerification {

    NOT_VERIFIED,
    SOURCE_VERIFIED,
    VERIFIED;

    public String value() {
        return name();
    }

    public static IncomeVerification fromValue(String v) {
        return valueOf(v);
    }

}
