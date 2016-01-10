
package com.rp.p2p.model;

import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrderConfirmation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrderConfirmation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="executionStatus" type="{http://ws.lendingclub.com/}OrderExecutionStatus" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="loanId" use="required" type="{http://www.w3.org/2001/XMLSchema}long" />
 *       &lt;attribute name="requestedAmount" use="required" type="{http://www.w3.org/2001/XMLSchema}double" />
 *       &lt;attribute name="investedAmount" use="required" type="{http://www.w3.org/2001/XMLSchema}double" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderConfirmation", propOrder = {
    "executionStatus"
})
@Entity
@Table(name= "LENDINGCLUB_ORDERCONFIRMATION")
public class OrderConfirmation {
    @Id
    @GenericGenerator(name="gen",strategy="increment")
    @GeneratedValue(generator="gen")
    @Column(name = "ID", unique = true, nullable = false, precision = 15, scale = 0)
    private long id_;
    @ElementCollection ()
    @JoinTable(name = "Order_Execution_Status_types",
            joinColumns = @JoinColumn(name = "Order_Execution_Status_id"))
    @Column(name = "Order_Execution_Status_key_id", nullable = false)
    @Enumerated(EnumType.STRING)
    protected List<OrderExecutionStatus> executionStatus;
    @XmlAttribute(name = "loanId", required = true)
    protected String loanId;
    @XmlAttribute(name = "requestedAmount", required = true)
    protected double requestedAmount;
    @XmlAttribute(name = "investedAmount", required = true)
    protected double investedAmount;

    public long getId() {
        return id_;
    }

    public void setId(long id) {
        id_ = id;
    }

    /**
     * Gets the value of the executionStatus property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the executionStatus property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExecutionStatus().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OrderExecutionStatus }
     * 
     * 
     */
    public List<OrderExecutionStatus> getExecutionStatus() {
        if (executionStatus == null) {
            executionStatus = new ArrayList<OrderExecutionStatus>();
        }
        return this.executionStatus;
    }

    /**
     * Gets the value of the loanId property.
     * 
     */
    public String getLoanId() {
        return loanId;
    }

    /**
     * Sets the value of the loanId property.
     * 
     */
    public void setLoanId(String value) {
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
     * Gets the value of the investedAmount property.
     * 
     */
    public double getInvestedAmount() {
        return investedAmount;
    }

    /**
     * Sets the value of the investedAmount property.
     * 
     */
    public void setInvestedAmount(double value) {
        this.investedAmount = value;
    }

}
