
package com.rp.p2p.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import javax.xml.bind.annotation.*;
import java.util.Date;


/**
 * <p>Java class for CreditInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CreditInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *       &lt;/sequence>
 *       &lt;attribute name="accNowDelinq" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="accOpenPast24Mths" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="bcOpenToBuy" type="{http://www.w3.org/2001/XMLSchema}double" />
 *       &lt;attribute name="percentBcGt75" type="{http://www.w3.org/2001/XMLSchema}double" />
 *       &lt;attribute name="bcUtil" type="{http://www.w3.org/2001/XMLSchema}double" />
 *       &lt;attribute name="dti" type="{http://www.w3.org/2001/XMLSchema}double" />
 *       &lt;attribute name="delinq2Yrs" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="delinqAmnt" type="{http://www.w3.org/2001/XMLSchema}double" />
 *       &lt;attribute name="earliestCrLine" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       &lt;attribute name="ficoRangeLow" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="ficoRangeHigh" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="inqLast6Mths" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="mthsSinceLastDelinq" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="mthsSinceLastRecord" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="mthsSinceRecentInq" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="mthsSinceRecentRevolDelinq" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="mthsSinceRecentBc" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="mortAcc" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="openAcc" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="pubRec" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="totalBalExMort" type="{http://www.w3.org/2001/XMLSchema}double" />
 *       &lt;attribute name="revolBal" type="{http://www.w3.org/2001/XMLSchema}double" />
 *       &lt;attribute name="revolUtil" type="{http://www.w3.org/2001/XMLSchema}double" />
 *       &lt;attribute name="totalBcLimit" type="{http://www.w3.org/2001/XMLSchema}double" />
 *       &lt;attribute name="totalAcc" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="totalIlHighCreditLimit" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="numRevAccts" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="mthsSinceRecentBcDlq" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="pubRecBankruptcies" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="numAcctsEver120Ppd" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="chargeoffWithin12Mths" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="collections12MthsExMed" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="taxLiens" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="mthsSinceLastMajorDerog" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="numSats" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="numTlOpPast12m" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="moSinRcntTl" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="totHiCredLim" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="totCurBal" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="avgCurBal" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="numBcTl" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="numActvBcTl" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="numBcSats" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="pctTlNvrDlq" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="numTl90gDpd24m" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="numTl30dpd" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="numTl120dpd2m" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="numIlTl" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="moSinOldIlAcct" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="numActvRevTl" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="moSinOldRevTlOp" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="moSinRcntRevTlOp" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="totalRevHiLim" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="numRevTlBalGt0" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="numOpRevTl" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="totCollAmt" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreditInfo")
@DynamoDBDocument
public class CreditInfo {

    @XmlAttribute(name = "accNowDelinq")
    @DynamoDBAttribute(attributeName="accNowDelinq")
    protected Integer accNowDelinq;
    @XmlAttribute(name = "accOpenPast24Mths")
    @DynamoDBAttribute(attributeName="accOpenPast24Mths")
    protected Integer accOpenPast24Mths;
    @XmlAttribute(name = "bcOpenToBuy")
    @DynamoDBAttribute(attributeName="bcOpenToBuy")
    protected Double bcOpenToBuy;
    @XmlAttribute(name = "percentBcGt75")
    @DynamoDBAttribute(attributeName="percentBcGt75")
    protected Double percentBcGt75;
    @XmlAttribute(name = "bcUtil")
    @DynamoDBAttribute(attributeName="bcUtil")
    protected Double bcUtil;
    @XmlAttribute(name = "dti")
    @DynamoDBAttribute(attributeName="dti")
    protected Double dti;
    @XmlAttribute(name = "delinq2Yrs")
    @DynamoDBAttribute(attributeName="delinq2Yrs")
    protected Integer delinq2Yrs;
    @XmlAttribute(name = "delinqAmnt")
    @DynamoDBAttribute(attributeName="delinqAmnt")
    protected Double delinqAmnt;
    @XmlAttribute(name = "earliestCrLine")
    @XmlSchemaType(name = "dateTime")
    @DynamoDBAttribute(attributeName="earliestCrLine")
    protected Date earliestCrLine;
    @XmlAttribute(name = "ficoRangeLow")
    @DynamoDBAttribute(attributeName="ficoRangeLow")
    protected Integer ficoRangeLow;
    @XmlAttribute(name = "ficoRangeHigh")
    @DynamoDBAttribute(attributeName="ficoRangeHigh")
    protected Integer ficoRangeHigh;
    @XmlAttribute(name = "inqLast6Mths")
    @DynamoDBAttribute(attributeName="inqLast6Mths")
    protected Integer inqLast6Mths;
    @XmlAttribute(name = "mthsSinceLastDelinq")
    @DynamoDBAttribute(attributeName="mthsSinceLastDelinq")
    protected Integer mthsSinceLastDelinq;
    @XmlAttribute(name = "mthsSinceLastRecord")
    @DynamoDBAttribute(attributeName="mthsSinceLastRecord")
    protected Integer mthsSinceLastRecord;
    @XmlAttribute(name = "mthsSinceRecentInq")
    @DynamoDBAttribute(attributeName="mthsSinceRecentInq")
    protected Integer mthsSinceRecentInq;
    @XmlAttribute(name = "mthsSinceRecentRevolDelinq")
    @DynamoDBAttribute(attributeName="mthsSinceRecentRevolDelinq")
    protected Integer mthsSinceRecentRevolDelinq;
    @XmlAttribute(name = "mthsSinceRecentBc")
    @DynamoDBAttribute(attributeName="mthsSinceRecentBc")
    protected Integer mthsSinceRecentBc;
    @XmlAttribute(name = "mortAcc")
    @DynamoDBAttribute(attributeName="mortAcc")
    protected Integer mortAcc;
    @XmlAttribute(name = "openAcc")
    @DynamoDBAttribute(attributeName="openAcc")
    protected Integer openAcc;
    @XmlAttribute(name = "pubRec")
    @DynamoDBAttribute(attributeName="pubRec")
    protected Integer pubRec;
    @XmlAttribute(name = "totalBalExMort")
    @DynamoDBAttribute(attributeName="totalBalExMort")
    protected Double totalBalExMort;
    @XmlAttribute(name = "revolBal")
    @DynamoDBAttribute(attributeName="revolBal")
    protected Double revolBal;
    @XmlAttribute(name = "revolUtil")
    @DynamoDBAttribute(attributeName="revolUtil")
    protected Double revolUtil;
    @XmlAttribute(name = "totalBcLimit")
    @DynamoDBAttribute(attributeName="totalBcLimit")
    protected Double totalBcLimit;
    @XmlAttribute(name = "totalAcc")
    @DynamoDBAttribute(attributeName="totalAcc")
    protected Integer totalAcc;
    @XmlAttribute(name = "totalIlHighCreditLimit")
    @DynamoDBAttribute(attributeName="totalIlHighCreditLimit")
    protected Integer totalIlHighCreditLimit;
    @XmlAttribute(name = "numRevAccts")
    @DynamoDBAttribute(attributeName="numRevAccts")
    protected Integer numRevAccts;
    @XmlAttribute(name = "mthsSinceRecentBcDlq")
    @DynamoDBAttribute(attributeName="mthsSinceRecentBcDlq")
    protected Integer mthsSinceRecentBcDlq;
    @XmlAttribute(name = "pubRecBankruptcies")
    @DynamoDBAttribute(attributeName="pubRecBankruptcies")
    protected Integer pubRecBankruptcies;
    @XmlAttribute(name = "numAcctsEver120Ppd")
    @DynamoDBAttribute(attributeName="numAcctsEver120Ppd")
    protected Integer numAcctsEver120Ppd;
    @XmlAttribute(name = "chargeoffWithin12Mths")
    @DynamoDBAttribute(attributeName="chargeoffWithin12Mths")
    protected Integer chargeoffWithin12Mths;
    @XmlAttribute(name = "collections12MthsExMed")
    @DynamoDBAttribute(attributeName="collections12MthsExMed")
    protected Integer collections12MthsExMed;
    @XmlAttribute(name = "taxLiens")
    @DynamoDBAttribute(attributeName="taxLiens")
    protected Integer taxLiens;
    @XmlAttribute(name = "mthsSinceLastMajorDerog")
    @DynamoDBAttribute(attributeName="mthsSinceLastMajorDerog")
    protected Integer mthsSinceLastMajorDerog;
    @XmlAttribute(name = "numSats")
    @DynamoDBAttribute(attributeName="numSats")
    protected Integer numSats;
    @XmlAttribute(name = "numTlOpPast12m")
    @DynamoDBAttribute(attributeName="numTlOpPast12M")
    protected Integer numTlOpPast12M;
    @XmlAttribute(name = "moSinRcntTl")
    @DynamoDBAttribute(attributeName="moSinRcntTl")
    protected Integer moSinRcntTl;
    @XmlAttribute(name = "totHiCredLim")
    @DynamoDBAttribute(attributeName="totHiCredLim")
    protected Integer totHiCredLim;
    @XmlAttribute(name = "totCurBal")
    @DynamoDBAttribute(attributeName="totCurBal")
    protected Integer totCurBal;
    @XmlAttribute(name = "avgCurBal")
    @DynamoDBAttribute(attributeName="avgCurBal")
    protected Integer avgCurBal;
    @XmlAttribute(name = "numBcTl")
    @DynamoDBAttribute(attributeName="numBcTl")
    protected Integer numBcTl;
    @XmlAttribute(name = "numActvBcTl")
    @DynamoDBAttribute(attributeName="numActvBcTl")
    protected Integer numActvBcTl;
    @XmlAttribute(name = "numBcSats")
    @DynamoDBAttribute(attributeName="numBcSats")
    protected Integer numBcSats;
    @XmlAttribute(name = "pctTlNvrDlq")
    @DynamoDBAttribute(attributeName="pctTlNvrDlq")
    protected Integer pctTlNvrDlq;
    @XmlAttribute(name = "numTl90gDpd24m")
    @DynamoDBAttribute(attributeName="numTl90GDpd24M")
    protected Integer numTl90GDpd24M;
    @XmlAttribute(name = "numTl30dpd")
    @DynamoDBAttribute(attributeName="numTl30Dpd")
    protected Integer numTl30Dpd;
    @XmlAttribute(name = "numTl120dpd2m")
    @DynamoDBAttribute(attributeName="numTl120Dpd2M")
    protected Integer numTl120Dpd2M;
    @XmlAttribute(name = "numIlTl")
    @DynamoDBAttribute(attributeName="numIlTl")
    protected Integer numIlTl;
    @XmlAttribute(name = "moSinOldIlAcct")
    @DynamoDBAttribute(attributeName="moSinOldIlAcct")
    protected Integer moSinOldIlAcct;
    @XmlAttribute(name = "numActvRevTl")
    @DynamoDBAttribute(attributeName="numActvRevTl")
    protected Integer numActvRevTl;
    @XmlAttribute(name = "moSinOldRevTlOp")
    @DynamoDBAttribute(attributeName="moSinOldRevTlOp")
    protected Integer moSinOldRevTlOp;
    @XmlAttribute(name = "moSinRcntRevTlOp")
    @DynamoDBAttribute(attributeName="moSinRcntRevTlOp")
    protected Integer moSinRcntRevTlOp;
    @XmlAttribute(name = "totalRevHiLim")
    @DynamoDBAttribute(attributeName="totalRevHiLim")
    protected Integer totalRevHiLim;
    @XmlAttribute(name = "numRevTlBalGt0")
    @DynamoDBAttribute(attributeName="numRevTlBalGt0")
    protected Integer numRevTlBalGt0;
    @XmlAttribute(name = "numOpRevTl")
    @DynamoDBAttribute(attributeName="numOpRevTl")
    protected Integer numOpRevTl;
    @XmlAttribute(name = "totCollAmt")
    @DynamoDBAttribute(attributeName="totCollAmt")
    protected Integer totCollAmt;

    /**
     * Gets the value of the accNowDelinq property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAccNowDelinq() {
        return accNowDelinq;
    }

    /**
     * Sets the value of the accNowDelinq property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAccNowDelinq(Integer value) {
        this.accNowDelinq = value;
    }

    /**
     * Gets the value of the accOpenPast24Mths property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAccOpenPast24Mths() {
        return accOpenPast24Mths;
    }

    /**
     * Sets the value of the accOpenPast24Mths property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAccOpenPast24Mths(Integer value) {
        this.accOpenPast24Mths = value;
    }

    /**
     * Gets the value of the bcOpenToBuy property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getBcOpenToBuy() {
        return bcOpenToBuy;
    }

    /**
     * Sets the value of the bcOpenToBuy property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setBcOpenToBuy(Double value) {
        this.bcOpenToBuy = value;
    }

    /**
     * Gets the value of the percentBcGt75 property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getPercentBcGt75() {
        return percentBcGt75;
    }

    /**
     * Sets the value of the percentBcGt75 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setPercentBcGt75(Double value) {
        this.percentBcGt75 = value;
    }

    /**
     * Gets the value of the bcUtil property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getBcUtil() {
        return bcUtil;
    }

    /**
     * Sets the value of the bcUtil property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setBcUtil(Double value) {
        this.bcUtil = value;
    }

    /**
     * Gets the value of the dti property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getDti() {
        return dti;
    }

    /**
     * Sets the value of the dti property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setDti(Double value) {
        this.dti = value;
    }

    /**
     * Gets the value of the delinq2Yrs property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDelinq2Yrs() {
        return delinq2Yrs;
    }

    /**
     * Sets the value of the delinq2Yrs property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDelinq2Yrs(Integer value) {
        this.delinq2Yrs = value;
    }

    /**
     * Gets the value of the delinqAmnt property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getDelinqAmnt() {
        return delinqAmnt;
    }

    /**
     * Sets the value of the delinqAmnt property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setDelinqAmnt(Double value) {
        this.delinqAmnt = value;
    }

    public Date getEarliestCrLine() {
        return earliestCrLine;
    }

    public void setEarliestCrLine(Date value) {
        this.earliestCrLine = value;
    }

    /**
     * Gets the value of the ficoRangeLow property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getFicoRangeLow() {
        return ficoRangeLow;
    }

    /**
     * Sets the value of the ficoRangeLow property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setFicoRangeLow(Integer value) {
        this.ficoRangeLow = value;
    }

    /**
     * Gets the value of the ficoRangeHigh property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getFicoRangeHigh() {
        return ficoRangeHigh;
    }

    /**
     * Sets the value of the ficoRangeHigh property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setFicoRangeHigh(Integer value) {
        this.ficoRangeHigh = value;
    }

    /**
     * Gets the value of the inqLast6Mths property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getInqLast6Mths() {
        return inqLast6Mths;
    }

    /**
     * Sets the value of the inqLast6Mths property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setInqLast6Mths(Integer value) {
        this.inqLast6Mths = value;
    }

    /**
     * Gets the value of the mthsSinceLastDelinq property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMthsSinceLastDelinq() {
        return mthsSinceLastDelinq;
    }

    /**
     * Sets the value of the mthsSinceLastDelinq property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMthsSinceLastDelinq(Integer value) {
        this.mthsSinceLastDelinq = value;
    }

    /**
     * Gets the value of the mthsSinceLastRecord property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMthsSinceLastRecord() {
        return mthsSinceLastRecord;
    }

    /**
     * Sets the value of the mthsSinceLastRecord property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMthsSinceLastRecord(Integer value) {
        this.mthsSinceLastRecord = value;
    }

    /**
     * Gets the value of the mthsSinceRecentInq property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMthsSinceRecentInq() {
        return mthsSinceRecentInq;
    }

    /**
     * Sets the value of the mthsSinceRecentInq property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMthsSinceRecentInq(Integer value) {
        this.mthsSinceRecentInq = value;
    }

    /**
     * Gets the value of the mthsSinceRecentRevolDelinq property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMthsSinceRecentRevolDelinq() {
        return mthsSinceRecentRevolDelinq;
    }

    /**
     * Sets the value of the mthsSinceRecentRevolDelinq property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMthsSinceRecentRevolDelinq(Integer value) {
        this.mthsSinceRecentRevolDelinq = value;
    }

    /**
     * Gets the value of the mthsSinceRecentBc property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMthsSinceRecentBc() {
        return mthsSinceRecentBc;
    }

    /**
     * Sets the value of the mthsSinceRecentBc property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMthsSinceRecentBc(Integer value) {
        this.mthsSinceRecentBc = value;
    }

    /**
     * Gets the value of the mortAcc property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMortAcc() {
        return mortAcc;
    }

    /**
     * Sets the value of the mortAcc property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMortAcc(Integer value) {
        this.mortAcc = value;
    }

    /**
     * Gets the value of the openAcc property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getOpenAcc() {
        return openAcc;
    }

    /**
     * Sets the value of the openAcc property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setOpenAcc(Integer value) {
        this.openAcc = value;
    }

    /**
     * Gets the value of the pubRec property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPubRec() {
        return pubRec;
    }

    /**
     * Sets the value of the pubRec property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPubRec(Integer value) {
        this.pubRec = value;
    }

    /**
     * Gets the value of the totalBalExMort property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getTotalBalExMort() {
        return totalBalExMort;
    }

    /**
     * Sets the value of the totalBalExMort property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setTotalBalExMort(Double value) {
        this.totalBalExMort = value;
    }

    /**
     * Gets the value of the revolBal property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getRevolBal() {
        return revolBal;
    }

    /**
     * Sets the value of the revolBal property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setRevolBal(Double value) {
        this.revolBal = value;
    }

    /**
     * Gets the value of the revolUtil property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getRevolUtil() {
        return revolUtil;
    }

    /**
     * Sets the value of the revolUtil property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setRevolUtil(Double value) {
        this.revolUtil = value;
    }

    /**
     * Gets the value of the totalBcLimit property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getTotalBcLimit() {
        return totalBcLimit;
    }

    /**
     * Sets the value of the totalBcLimit property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setTotalBcLimit(Double value) {
        this.totalBcLimit = value;
    }

    /**
     * Gets the value of the totalAcc property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTotalAcc() {
        return totalAcc;
    }

    /**
     * Sets the value of the totalAcc property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTotalAcc(Integer value) {
        this.totalAcc = value;
    }

    /**
     * Gets the value of the totalIlHighCreditLimit property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTotalIlHighCreditLimit() {
        return totalIlHighCreditLimit;
    }

    /**
     * Sets the value of the totalIlHighCreditLimit property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTotalIlHighCreditLimit(Integer value) {
        this.totalIlHighCreditLimit = value;
    }

    /**
     * Gets the value of the numRevAccts property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumRevAccts() {
        return numRevAccts;
    }

    /**
     * Sets the value of the numRevAccts property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumRevAccts(Integer value) {
        this.numRevAccts = value;
    }

    /**
     * Gets the value of the mthsSinceRecentBcDlq property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMthsSinceRecentBcDlq() {
        return mthsSinceRecentBcDlq;
    }

    /**
     * Sets the value of the mthsSinceRecentBcDlq property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMthsSinceRecentBcDlq(Integer value) {
        this.mthsSinceRecentBcDlq = value;
    }

    /**
     * Gets the value of the pubRecBankruptcies property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPubRecBankruptcies() {
        return pubRecBankruptcies;
    }

    /**
     * Sets the value of the pubRecBankruptcies property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPubRecBankruptcies(Integer value) {
        this.pubRecBankruptcies = value;
    }

    /**
     * Gets the value of the numAcctsEver120Ppd property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumAcctsEver120Ppd() {
        return numAcctsEver120Ppd;
    }

    /**
     * Sets the value of the numAcctsEver120Ppd property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumAcctsEver120Ppd(Integer value) {
        this.numAcctsEver120Ppd = value;
    }

    /**
     * Gets the value of the chargeoffWithin12Mths property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getChargeoffWithin12Mths() {
        return chargeoffWithin12Mths;
    }

    /**
     * Sets the value of the chargeoffWithin12Mths property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setChargeoffWithin12Mths(Integer value) {
        this.chargeoffWithin12Mths = value;
    }

    /**
     * Gets the value of the collections12MthsExMed property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCollections12MthsExMed() {
        return collections12MthsExMed;
    }

    /**
     * Sets the value of the collections12MthsExMed property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCollections12MthsExMed(Integer value) {
        this.collections12MthsExMed = value;
    }

    /**
     * Gets the value of the taxLiens property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTaxLiens() {
        return taxLiens;
    }

    /**
     * Sets the value of the taxLiens property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTaxLiens(Integer value) {
        this.taxLiens = value;
    }

    /**
     * Gets the value of the mthsSinceLastMajorDerog property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMthsSinceLastMajorDerog() {
        return mthsSinceLastMajorDerog;
    }

    /**
     * Sets the value of the mthsSinceLastMajorDerog property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMthsSinceLastMajorDerog(Integer value) {
        this.mthsSinceLastMajorDerog = value;
    }

    /**
     * Gets the value of the numSats property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumSats() {
        return numSats;
    }

    /**
     * Sets the value of the numSats property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumSats(Integer value) {
        this.numSats = value;
    }

    /**
     * Gets the value of the numTlOpPast12M property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumTlOpPast12M() {
        return numTlOpPast12M;
    }

    /**
     * Sets the value of the numTlOpPast12M property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumTlOpPast12M(Integer value) {
        this.numTlOpPast12M = value;
    }

    /**
     * Gets the value of the moSinRcntTl property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMoSinRcntTl() {
        return moSinRcntTl;
    }

    /**
     * Sets the value of the moSinRcntTl property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMoSinRcntTl(Integer value) {
        this.moSinRcntTl = value;
    }

    /**
     * Gets the value of the totHiCredLim property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTotHiCredLim() {
        return totHiCredLim;
    }

    /**
     * Sets the value of the totHiCredLim property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTotHiCredLim(Integer value) {
        this.totHiCredLim = value;
    }

    /**
     * Gets the value of the totCurBal property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTotCurBal() {
        return totCurBal;
    }

    /**
     * Sets the value of the totCurBal property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTotCurBal(Integer value) {
        this.totCurBal = value;
    }

    /**
     * Gets the value of the avgCurBal property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAvgCurBal() {
        return avgCurBal;
    }

    /**
     * Sets the value of the avgCurBal property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAvgCurBal(Integer value) {
        this.avgCurBal = value;
    }

    /**
     * Gets the value of the numBcTl property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumBcTl() {
        return numBcTl;
    }

    /**
     * Sets the value of the numBcTl property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumBcTl(Integer value) {
        this.numBcTl = value;
    }

    /**
     * Gets the value of the numActvBcTl property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumActvBcTl() {
        return numActvBcTl;
    }

    /**
     * Sets the value of the numActvBcTl property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumActvBcTl(Integer value) {
        this.numActvBcTl = value;
    }

    /**
     * Gets the value of the numBcSats property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumBcSats() {
        return numBcSats;
    }

    /**
     * Sets the value of the numBcSats property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumBcSats(Integer value) {
        this.numBcSats = value;
    }

    /**
     * Gets the value of the pctTlNvrDlq property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPctTlNvrDlq() {
        return pctTlNvrDlq;
    }

    /**
     * Sets the value of the pctTlNvrDlq property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPctTlNvrDlq(Integer value) {
        this.pctTlNvrDlq = value;
    }

    /**
     * Gets the value of the numTl90GDpd24M property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumTl90GDpd24M() {
        return numTl90GDpd24M;
    }

    /**
     * Sets the value of the numTl90GDpd24M property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumTl90GDpd24M(Integer value) {
        this.numTl90GDpd24M = value;
    }

    /**
     * Gets the value of the numTl30Dpd property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumTl30Dpd() {
        return numTl30Dpd;
    }

    /**
     * Sets the value of the numTl30Dpd property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumTl30Dpd(Integer value) {
        this.numTl30Dpd = value;
    }

    /**
     * Gets the value of the numTl120Dpd2M property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumTl120Dpd2M() {
        return numTl120Dpd2M;
    }

    /**
     * Sets the value of the numTl120Dpd2M property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumTl120Dpd2M(Integer value) {
        this.numTl120Dpd2M = value;
    }

    /**
     * Gets the value of the numIlTl property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumIlTl() {
        return numIlTl;
    }

    /**
     * Sets the value of the numIlTl property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumIlTl(Integer value) {
        this.numIlTl = value;
    }

    /**
     * Gets the value of the moSinOldIlAcct property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMoSinOldIlAcct() {
        return moSinOldIlAcct;
    }

    /**
     * Sets the value of the moSinOldIlAcct property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMoSinOldIlAcct(Integer value) {
        this.moSinOldIlAcct = value;
    }

    /**
     * Gets the value of the numActvRevTl property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumActvRevTl() {
        return numActvRevTl;
    }

    /**
     * Sets the value of the numActvRevTl property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumActvRevTl(Integer value) {
        this.numActvRevTl = value;
    }

    /**
     * Gets the value of the moSinOldRevTlOp property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMoSinOldRevTlOp() {
        return moSinOldRevTlOp;
    }

    /**
     * Sets the value of the moSinOldRevTlOp property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMoSinOldRevTlOp(Integer value) {
        this.moSinOldRevTlOp = value;
    }

    /**
     * Gets the value of the moSinRcntRevTlOp property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMoSinRcntRevTlOp() {
        return moSinRcntRevTlOp;
    }

    /**
     * Sets the value of the moSinRcntRevTlOp property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMoSinRcntRevTlOp(Integer value) {
        this.moSinRcntRevTlOp = value;
    }

    /**
     * Gets the value of the totalRevHiLim property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTotalRevHiLim() {
        return totalRevHiLim;
    }

    /**
     * Sets the value of the totalRevHiLim property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTotalRevHiLim(Integer value) {
        this.totalRevHiLim = value;
    }

    /**
     * Gets the value of the numRevTlBalGt0 property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumRevTlBalGt0() {
        return numRevTlBalGt0;
    }

    /**
     * Sets the value of the numRevTlBalGt0 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumRevTlBalGt0(Integer value) {
        this.numRevTlBalGt0 = value;
    }

    /**
     * Gets the value of the numOpRevTl property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumOpRevTl() {
        return numOpRevTl;
    }

    /**
     * Sets the value of the numOpRevTl property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumOpRevTl(Integer value) {
        this.numOpRevTl = value;
    }

    /**
     * Gets the value of the totCollAmt property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTotCollAmt() {
        return totCollAmt;
    }

    /**
     * Sets the value of the totCollAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTotCollAmt(Integer value) {
        this.totCollAmt = value;
    }

}
