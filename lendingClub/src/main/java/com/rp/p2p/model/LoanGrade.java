
package com.rp.p2p.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LoanGrade.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="LoanGrade">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="A"/>
 *     &lt;enumeration value="B"/>
 *     &lt;enumeration value="C"/>
 *     &lt;enumeration value="D"/>
 *     &lt;enumeration value="E"/>
 *     &lt;enumeration value="F"/>
 *     &lt;enumeration value="G"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "LoanGrade")
@XmlEnum
@DynamoDBDocument
public enum LoanGrade {

    A,
    B,
    C,
    D,
    E,
    F,
    G;

    public String value() {
        return name();
    }

    public static LoanGrade fromValue(String v) {
        return valueOf(v);
    }

}
