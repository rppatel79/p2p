
package com.rp.p2p.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LoanPurpose.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="LoanPurpose">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CREDIT_CARD"/>
 *     &lt;enumeration value="DEBT_CONSOLIDATION"/>
 *     &lt;enumeration value="MEDICAL"/>
 *     &lt;enumeration value="EDUCATIONAL"/>
 *     &lt;enumeration value="HOME_IMPROVEMENT"/>
 *     &lt;enumeration value="RENEWABLE_ENERGY"/>
 *     &lt;enumeration value="SMALL_BUSINESS"/>
 *     &lt;enumeration value="WEDDING"/>
 *     &lt;enumeration value="VACATION"/>
 *     &lt;enumeration value="MOVING"/>
 *     &lt;enumeration value="HOUSE"/>
 *     &lt;enumeration value="CAR"/>
 *     &lt;enumeration value="MAJOR_PURCHASE"/>
 *     &lt;enumeration value="OTHER"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "LoanPurpose")
@XmlEnum
public enum LoanPurpose {

    CREDIT_CARD,
    DEBT_CONSOLIDATION,
    MEDICAL,
    EDUCATIONAL,
    HOME_IMPROVEMENT,
    RENEWABLE_ENERGY,
    SMALL_BUSINESS,
    WEDDING,
    VACATION,
    MOVING,
    HOUSE,
    CAR,
    MAJOR_PURCHASE,
    OTHER;

    public String value() {
        return name();
    }

    public static LoanPurpose fromValue(String v) {
        return valueOf(v);
    }

}
