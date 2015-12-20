
package com.rp.p2p.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Order complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Order">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *       &lt;/sequence>
 *       &lt;attribute name="loanId" use="required" type="{http://www.w3.org/2001/XMLSchema}long" />
 *       &lt;attribute name="requestedAmount" use="required" type="{http://www.w3.org/2001/XMLSchema}double" />
 *       &lt;attribute name="portfolioId" type="{http://www.w3.org/2001/XMLSchema}long" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Order")
public class Order {

    @XmlAttribute(name = "loanId", required = true)
    protected long loanId;
    @XmlAttribute(name = "requestedAmount", required = true)
    protected double requestedAmount;
    @XmlAttribute(name = "portfolioId")
    protected Long portfolioId;

    /**
     * Gets the value of the loanId property.
     * 
     */
    public long getLoanId() {
        return loanId;
    }

    /**
     * Sets the value of the loanId property.
     * 
     */
    public void setLoanId(long value) {
        this.loanId = value;
    }

    /**
     * Gets the value of the requestedAmount property.
     * 
     */
    public double getRequestedAmount() {
        return requestedAmount;
    }

    /**
     * Sets the value of the requestedAmount property.
     * 
     */
    public void setRequestedAmount(double value) {
        this.requestedAmount = value;
    }

    /**
     * Gets the value of the portfolioId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getPortfolioId() {
        return portfolioId;
    }

    /**
     * Sets the value of the portfolioId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setPortfolioId(Long value) {
        this.portfolioId = value;
    }

}
