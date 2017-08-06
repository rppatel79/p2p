
package com.rp.p2p.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrderInstructConfirmation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrderInstructConfirmation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="orderConfirmations" type="{http://ws.lendingclub.com/}OrderConfirmation" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="orderInstructId" type="{http://www.w3.org/2001/XMLSchema}long" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderInstructConfirmation", propOrder = {
    "orderConfirmations"
})
public class OrderInstructConfirmation {

    @XmlElement(required = true)
    protected List<OrderConfirmation> orderConfirmations;
    @XmlAttribute(name = "orderInstructId")
    protected Long orderInstructId;

    /**
     * Gets the value of the orderConfirmations property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the orderConfirmations property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOrderConfirmations().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link com.rp.p2p.model.OrderConfirmation }
     * 
     * 
     */
    public List<OrderConfirmation> getOrderConfirmations() {
        if (orderConfirmations == null) {
            orderConfirmations = new ArrayList<OrderConfirmation>();
        }
        return this.orderConfirmations;
    }

    /**
     * Gets the value of the orderInstructId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getOrderInstructId() {
        return orderInstructId;
    }

    /**
     * Sets the value of the orderInstructId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setOrderInstructId(Long value) {
        this.orderInstructId = value;
    }

}
