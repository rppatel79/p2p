
package com.rp.p2p.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HomeOwnership.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="HomeOwnership">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="OWN"/>
 *     &lt;enumeration value="RENT"/>
 *     &lt;enumeration value="MORTGAGE"/>
 *     &lt;enumeration value="OTHER"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "HomeOwnership")
@XmlEnum
public enum HomeOwnership {

    OWN,
    RENT,
    MORTGAGE,
    ANY,
    OTHER;

    public String value() {
        return name();
    }

    public static HomeOwnership fromValue(String v) {
        return valueOf(v);
    }

}
