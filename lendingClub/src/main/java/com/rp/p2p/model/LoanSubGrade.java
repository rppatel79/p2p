
package com.rp.p2p.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LoanSubGrade.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="LoanSubGrade">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="A1"/>
 *     &lt;enumeration value="A2"/>
 *     &lt;enumeration value="A3"/>
 *     &lt;enumeration value="A4"/>
 *     &lt;enumeration value="A5"/>
 *     &lt;enumeration value="B1"/>
 *     &lt;enumeration value="B2"/>
 *     &lt;enumeration value="B3"/>
 *     &lt;enumeration value="B4"/>
 *     &lt;enumeration value="B5"/>
 *     &lt;enumeration value="C1"/>
 *     &lt;enumeration value="C2"/>
 *     &lt;enumeration value="C3"/>
 *     &lt;enumeration value="C4"/>
 *     &lt;enumeration value="C5"/>
 *     &lt;enumeration value="D1"/>
 *     &lt;enumeration value="D2"/>
 *     &lt;enumeration value="D3"/>
 *     &lt;enumeration value="D4"/>
 *     &lt;enumeration value="D5"/>
 *     &lt;enumeration value="E1"/>
 *     &lt;enumeration value="E2"/>
 *     &lt;enumeration value="E3"/>
 *     &lt;enumeration value="E4"/>
 *     &lt;enumeration value="E5"/>
 *     &lt;enumeration value="F1"/>
 *     &lt;enumeration value="F2"/>
 *     &lt;enumeration value="F3"/>
 *     &lt;enumeration value="F4"/>
 *     &lt;enumeration value="F5"/>
 *     &lt;enumeration value="G1"/>
 *     &lt;enumeration value="G2"/>
 *     &lt;enumeration value="G3"/>
 *     &lt;enumeration value="G4"/>
 *     &lt;enumeration value="G5"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "LoanSubGrade")
@XmlEnum
@DynamoDBDocument
public enum LoanSubGrade {

    @XmlEnumValue("A1")
    A_1("A1"),
    @XmlEnumValue("A2")
    A_2("A2"),
    @XmlEnumValue("A3")
    A_3("A3"),
    @XmlEnumValue("A4")
    A_4("A4"),
    @XmlEnumValue("A5")
    A_5("A5"),
    @XmlEnumValue("B1")
    B_1("B1"),
    @XmlEnumValue("B2")
    B_2("B2"),
    @XmlEnumValue("B3")
    B_3("B3"),
    @XmlEnumValue("B4")
    B_4("B4"),
    @XmlEnumValue("B5")
    B_5("B5"),
    @XmlEnumValue("C1")
    C_1("C1"),
    @XmlEnumValue("C2")
    C_2("C2"),
    @XmlEnumValue("C3")
    C_3("C3"),
    @XmlEnumValue("C4")
    C_4("C4"),
    @XmlEnumValue("C5")
    C_5("C5"),
    @XmlEnumValue("D1")
    D_1("D1"),
    @XmlEnumValue("D2")
    D_2("D2"),
    @XmlEnumValue("D3")
    D_3("D3"),
    @XmlEnumValue("D4")
    D_4("D4"),
    @XmlEnumValue("D5")
    D_5("D5"),
    @XmlEnumValue("E1")
    E_1("E1"),
    @XmlEnumValue("E2")
    E_2("E2"),
    @XmlEnumValue("E3")
    E_3("E3"),
    @XmlEnumValue("E4")
    E_4("E4"),
    @XmlEnumValue("E5")
    E_5("E5"),
    @XmlEnumValue("F1")
    F_1("F1"),
    @XmlEnumValue("F2")
    F_2("F2"),
    @XmlEnumValue("F3")
    F_3("F3"),
    @XmlEnumValue("F4")
    F_4("F4"),
    @XmlEnumValue("F5")
    F_5("F5"),
    @XmlEnumValue("G1")
    G_1("G1"),
    @XmlEnumValue("G2")
    G_2("G2"),
    @XmlEnumValue("G3")
    G_3("G3"),
    @XmlEnumValue("G4")
    G_4("G4"),
    @XmlEnumValue("G5")
    G_5("G5");
    private final String value;

    LoanSubGrade(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static LoanSubGrade fromValue(String v) {
        for (LoanSubGrade c: LoanSubGrade.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
