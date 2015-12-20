
package com.rp.p2p.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PortfolioResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PortfolioResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *       &lt;/sequence>
 *       &lt;attribute name="aid" use="required" type="{http://www.w3.org/2001/XMLSchema}long" />
 *       &lt;attribute name="portfolioName" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="portfolioDescription" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="portfolioId" use="required" type="{http://www.w3.org/2001/XMLSchema}long" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PortfolioResult")
public class PortfolioResult {

    @XmlAttribute(name = "aid", required = true)
    protected long aid;
    @XmlAttribute(name = "portfolioName", required = true)
    protected String portfolioName;
    @XmlAttribute(name = "portfolioDescription")
    protected String portfolioDescription;
    @XmlAttribute(name = "portfolioId", required = true)
    protected long portfolioId;

    /**
     * Gets the value of the aid property.
     * 
     */
    public long getAid() {
        return aid;
    }

    /**
     * Sets the value of the aid property.
     * 
     */
    public void setAid(long value) {
        this.aid = value;
    }

    /**
     * Gets the value of the portfolioName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPortfolioName() {
        return portfolioName;
    }

    /**
     * Sets the value of the portfolioName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPortfolioName(String value) {
        this.portfolioName = value;
    }

    /**
     * Gets the value of the portfolioDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPortfolioDescription() {
        return portfolioDescription;
    }

    /**
     * Sets the value of the portfolioDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPortfolioDescription(String value) {
        this.portfolioDescription = value;
    }

    /**
     * Gets the value of the portfolioId property.
     * 
     */
    public long getPortfolioId() {
        return portfolioId;
    }

    /**
     * Sets the value of the portfolioId property.
     * 
     */
    public void setPortfolioId(long value) {
        this.portfolioId = value;
    }

}
