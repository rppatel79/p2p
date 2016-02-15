
package com.rp.p2p.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;


/**
 * <p>Java class for LoanListing complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LoanListing">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="creditInfo" type="{http://ws.lendingclub.com/}CreditInfo"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}long" />
 *       &lt;attribute name="memberId" use="required" type="{http://www.w3.org/2001/XMLSchema}long" />
 *       &lt;attribute name="loanAmnt" use="required" type="{http://www.w3.org/2001/XMLSchema}double" />
 *       &lt;attribute name="fundedAmnt" use="required" type="{http://www.w3.org/2001/XMLSchema}double" />
 *       &lt;attribute name="term" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="intRate" use="required" type="{http://www.w3.org/2001/XMLSchema}double" />
 *       &lt;attribute name="expDefaultRate" use="required" type="{http://www.w3.org/2001/XMLSchema}double" />
 *       &lt;attribute name="serviceFeeRate" use="required" type="{http://www.w3.org/2001/XMLSchema}double" />
 *       &lt;attribute name="installment" type="{http://www.w3.org/2001/XMLSchema}double" />
 *       &lt;attribute name="grade" use="required" type="{http://ws.lendingclub.com/}LoanGrade" />
 *       &lt;attribute name="subGrade" use="required" type="{http://ws.lendingclub.com/}LoanSubGrade" />
 *       &lt;attribute name="empLength" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="homeOwnership" use="required" type="{http://ws.lendingclub.com/}HomeOwnership" />
 *       &lt;attribute name="otherHomeOwnership" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="annualInc" use="required" type="{http://www.w3.org/2001/XMLSchema}double" />
 *       &lt;attribute name="isIncV" use="required" type="{http://ws.lendingclub.com/}IncomeVerification" />
 *       &lt;attribute name="acceptD" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       &lt;attribute name="expD" use="required" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       &lt;attribute name="listD" use="required" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       &lt;attribute name="creditPullD" use="required" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       &lt;attribute name="reviewStatusD" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       &lt;attribute name="reviewStatus" use="required" type="{http://ws.lendingclub.com/}ReviewStatus" />
 *       &lt;attribute name="url" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="description" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="purpose" use="required" type="{http://ws.lendingclub.com/}LoanPurpose" />
 *       &lt;attribute name="title" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="addrCity" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="addrState" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="msa" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="investorCount" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="ilsExpD" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       &lt;attribute name="initialListStatus" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="empTitle" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LoanListing", propOrder = {
    "creditInfo"
})
@Entity(name= "LENDINGCLUB_LOANLISTING")
@DynamoDBTable(tableName="lendingclub_loanlisting")
public class LoanListing {

    @XmlElement(required = true)
    @Embedded
    @DynamoDBAttribute(attributeName="creditInfo")
    protected CreditInfo creditInfo;
    @Id
    @XmlAttribute(name = "id", required = true)
    @DynamoDBHashKey(attributeName="Id")
    protected long id;
    @XmlAttribute(name = "memberId", required = true)
    @DynamoDBAttribute(attributeName="memberId")
    protected String memberId;
    @XmlAttribute(name = "loanAmnt", required = true)
    @DynamoDBAttribute(attributeName="loanAmnt")
    protected double loanAmnt;
    @XmlAttribute(name = "fundedAmnt", required = true)
    @DynamoDBAttribute(attributeName="fundedAmnt")
    protected double fundedAmnt;
    @XmlAttribute(name = "term", required = true)
    @DynamoDBAttribute(attributeName="term")
    protected int term;
    @XmlAttribute(name = "intRate", required = true)
    @DynamoDBAttribute(attributeName="intRate")
    protected double intRate;
    @XmlAttribute(name = "expDefaultRate", required = true)
    @DynamoDBAttribute(attributeName="expDefaultRate")
    protected double expDefaultRate;
    @XmlAttribute(name = "serviceFeeRate", required = true)
    @DynamoDBAttribute(attributeName="serviceFeeRate")
    protected double serviceFeeRate;
    @XmlAttribute(name = "installment")
    @DynamoDBAttribute(attributeName="installment")
    protected Double installment;
    @XmlAttribute(name = "grade", required = true)
    @DynamoDBAttribute(attributeName="grade")
    protected LoanGrade grade;
    @XmlAttribute(name = "subGrade", required = true)
    @DynamoDBAttribute(attributeName="subGrade")
    protected LoanSubGrade subGrade;
    @XmlAttribute(name = "empLength")
    @DynamoDBAttribute(attributeName="empLength")
    protected Integer empLength;
    @XmlAttribute(name = "homeOwnership", required = true)
    @DynamoDBAttribute(attributeName="homeOwnership")
    protected HomeOwnership homeOwnership;
    @XmlAttribute(name = "otherHomeOwnership", required = true)
    @DynamoDBAttribute(attributeName="otherHomeOwnership")
    protected String otherHomeOwnership;
    @XmlAttribute(name = "annualInc", required = true)
    @DynamoDBAttribute(attributeName="annualInc")
    protected double annualInc;
    @XmlAttribute(name = "isIncV", required = true)
    @DynamoDBAttribute(attributeName="isIncV")
    protected IncomeVerification isIncV;
    @XmlAttribute(name = "acceptD")
    @XmlSchemaType(name = "dateTime")
    @DynamoDBAttribute(attributeName="acceptD")
    protected Date acceptD;
    @XmlAttribute(name = "expD", required = true)
    @XmlSchemaType(name = "dateTime")
    @DynamoDBAttribute(attributeName="expD")
    protected Date expD;
    @XmlAttribute(name = "listD", required = true)
    @XmlSchemaType(name = "dateTime")
    @DynamoDBAttribute(attributeName="listD")
    protected Date listD;
    @XmlAttribute(name = "creditPullD", required = true)
    @XmlSchemaType(name = "dateTime")
    @DynamoDBAttribute(attributeName="creditPullD")
    protected Date creditPullD;
    @XmlAttribute(name = "reviewStatusD")
    @XmlSchemaType(name = "dateTime")
    @DynamoDBAttribute(attributeName="reviewStatusD")
    protected Date reviewStatusD;
    @XmlAttribute(name = "reviewStatus", required = true)
    @DynamoDBAttribute(attributeName="reviewStatus")
    protected ReviewStatus reviewStatus;
    @XmlAttribute(name = "url", required = true)
    @DynamoDBAttribute(attributeName="url")
    protected String url;
    @XmlAttribute(name = "description", required = true)
    @DynamoDBAttribute(attributeName="description")
    protected String description;
    @XmlAttribute(name = "purpose", required = true)
    @DynamoDBAttribute(attributeName="purpose")
    protected LoanPurpose purpose;
    @XmlAttribute(name = "title", required = true)
    @DynamoDBAttribute(attributeName="title")
    protected String title;
    @XmlAttribute(name = "addrCity", required = true)
    @DynamoDBAttribute(attributeName="addrCity")
    protected String addrCity;
    @XmlAttribute(name = "addrState", required = true)
    @DynamoDBAttribute(attributeName="addrState")
    protected String addrState;
    @XmlAttribute(name = "msa")
    @DynamoDBAttribute(attributeName="msa")
    protected String msa;
    @XmlAttribute(name = "investorCount")
    @DynamoDBAttribute(attributeName="investorCount")
    protected Integer investorCount;
    @XmlAttribute(name = "ilsExpD")
    @XmlSchemaType(name = "dateTime")
    @DynamoDBAttribute(attributeName="ilsExpD")
    protected Date ilsExpD;
    @XmlAttribute(name = "initialListStatus")
    @DynamoDBAttribute(attributeName="initialListStatus")
    protected String initialListStatus;
    @XmlAttribute(name = "empTitle")
    @DynamoDBAttribute(attributeName="empTitle")
    protected String empTitle;

    /**
     * Gets the value of the creditInfo property.
     * 
     * @return
     *     possible object is
     *     {@link CreditInfo }
     *     
     */
    public CreditInfo getCreditInfo() {
        return creditInfo;
    }

    /**
     * Sets the value of the creditInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreditInfo }
     *     
     */
    public void setCreditInfo(CreditInfo value) {
        this.creditInfo = value;
    }

    /**
     * Gets the value of the id property.
     * 
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     */
    public void setId(long value) {
        this.id = value;
    }

    /**
     * Gets the value of the memberId property.
     * 
     */
    public String getMemberId() {
        return memberId;
    }

    /**
     * Sets the value of the memberId property.
     * 
     */
    public void setMemberId(String value) {
        this.memberId = value;
    }

    /**
     * Gets the value of the loanAmnt property.
     * 
     */
    public double getLoanAmnt() {
        return loanAmnt;
    }

    /**
     * Sets the value of the loanAmnt property.
     * 
     */
    public void setLoanAmnt(double value) {
        this.loanAmnt = value;
    }

    /**
     * Gets the value of the fundedAmnt property.
     * 
     */
    public double getFundedAmnt() {
        return fundedAmnt;
    }

    /**
     * Sets the value of the fundedAmnt property.
     * 
     */
    public void setFundedAmnt(double value) {
        this.fundedAmnt = value;
    }

    /**
     * Gets the value of the term property.
     * 
     */
    public int getTerm() {
        return term;
    }

    /**
     * Sets the value of the term property.
     * 
     */
    public void setTerm(int value) {
        this.term = value;
    }

    /**
     * Gets the value of the intRate property.
     * 
     */
    public double getIntRate() {
        return intRate;
    }

    /**
     * Sets the value of the intRate property.
     * 
     */
    public void setIntRate(double value) {
        this.intRate = value;
    }

    /**
     * Gets the value of the expDefaultRate property.
     * 
     */
    public double getExpDefaultRate() {
        return expDefaultRate;
    }

    /**
     * Sets the value of the expDefaultRate property.
     * 
     */
    public void setExpDefaultRate(double value) {
        this.expDefaultRate = value;
    }

    /**
     * Gets the value of the serviceFeeRate property.
     * 
     */
    public double getServiceFeeRate() {
        return serviceFeeRate;
    }

    /**
     * Sets the value of the serviceFeeRate property.
     * 
     */
    public void setServiceFeeRate(double value) {
        this.serviceFeeRate = value;
    }

    /**
     * Gets the value of the installment property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getInstallment() {
        return installment;
    }

    /**
     * Sets the value of the installment property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setInstallment(Double value) {
        this.installment = value;
    }

    /**
     * Gets the value of the grade property.
     * 
     * @return
     *     possible object is
     *     {@link LoanGrade }
     *     
     */
    public LoanGrade getGrade() {
        return grade;
    }

    /**
     * Sets the value of the grade property.
     * 
     * @param value
     *     allowed object is
     *     {@link LoanGrade }
     *     
     */
    public void setGrade(LoanGrade value) {
        this.grade = value;
    }

    /**
     * Gets the value of the subGrade property.
     * 
     * @return
     *     possible object is
     *     {@link LoanSubGrade }
     *     
     */
    public LoanSubGrade getSubGrade() {
        return subGrade;
    }

    /**
     * Sets the value of the subGrade property.
     * 
     * @param value
     *     allowed object is
     *     {@link LoanSubGrade }
     *     
     */
    public void setSubGrade(LoanSubGrade value) {
        this.subGrade = value;
    }

    /**
     * Gets the value of the empLength property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getEmpLength() {
        return empLength;
    }

    /**
     * Sets the value of the empLength property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setEmpLength(Integer value) {
        this.empLength = value;
    }

    /**
     * Gets the value of the homeOwnership property.
     * 
     * @return
     *     possible object is
     *     {@link HomeOwnership }
     *     
     */
    public HomeOwnership getHomeOwnership() {
        return homeOwnership;
    }

    /**
     * Sets the value of the homeOwnership property.
     * 
     * @param value
     *     allowed object is
     *     {@link HomeOwnership }
     *     
     */
    public void setHomeOwnership(HomeOwnership value) {
        this.homeOwnership = value;
    }

    /**
     * Gets the value of the otherHomeOwnership property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOtherHomeOwnership() {
        return otherHomeOwnership;
    }

    /**
     * Sets the value of the otherHomeOwnership property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOtherHomeOwnership(String value) {
        this.otherHomeOwnership = value;
    }

    /**
     * Gets the value of the annualInc property.
     * 
     */
    public double getAnnualInc() {
        return annualInc;
    }

    /**
     * Sets the value of the annualInc property.
     * 
     */
    public void setAnnualInc(double value) {
        this.annualInc = value;
    }

    /**
     * Gets the value of the isIncV property.
     * 
     * @return
     *     possible object is
     *     {@link IncomeVerification }
     *     
     */
    public IncomeVerification getIsIncV() {
        return isIncV;
    }

    /**
     * Sets the value of the isIncV property.
     * 
     * @param value
     *     allowed object is
     *     {@link IncomeVerification }
     *     
     */
    public void setIsIncV(IncomeVerification value) {
        this.isIncV = value;
    }

    /**
     * Gets the value of the acceptD property.
     * 
     * @return
     *     possible object is
     *     {@link Date }
     *     
     */
    public Date getAcceptD() {
        return acceptD;
    }

    /**
     * Sets the value of the acceptD property.
     * 
     * @param value
     *     allowed object is
     *     {@link Date }
     *     
     */
    public void setAcceptD(Date value) {
        this.acceptD = value;
    }

    /**
     * Gets the value of the expD property.
     * 
     * @return
     *     possible object is
     *     {@link Date }
     *     
     */
    public Date getExpD() {
        return expD;
    }

    /**
     * Sets the value of the expD property.
     * 
     * @param value
     *     allowed object is
     *     {@link Date }
     *     
     */
    public void setExpD(Date value) {
        this.expD = value;
    }

    /**
     * Gets the value of the listD property.
     * 
     * @return
     *     possible object is
     *     {@link Date }
     *     
     */
    public Date getListD() {
        return listD;
    }

    /**
     * Sets the value of the listD property.
     * 
     * @param value
     *     allowed object is
     *     {@link Date }
     *     
     */
    public void setListD(Date value) {
        this.listD = value;
    }

    /**
     * Gets the value of the creditPullD property.
     * 
     * @return
     *     possible object is
     *     {@link Date }
     *     
     */
    public Date getCreditPullD() {
        return creditPullD;
    }

    /**
     * Sets the value of the creditPullD property.
     * 
     * @param value
     *     allowed object is
     *     {@link Date }
     *     
     */
    public void setCreditPullD(Date value) {
        this.creditPullD = value;
    }

    /**
     * Gets the value of the reviewStatusD property.
     * 
     * @return
     *     possible object is
     *     {@link Date }
     *     
     */
    public Date getReviewStatusD() {
        return reviewStatusD;
    }

    /**
     * Sets the value of the reviewStatusD property.
     * 
     * @param value
     *     allowed object is
     *     {@link Date }
     *     
     */
    public void setReviewStatusD(Date value) {
        this.reviewStatusD = value;
    }

    /**
     * Gets the value of the reviewStatus property.
     * 
     * @return
     *     possible object is
     *     {@link ReviewStatus }
     *     
     */
    public ReviewStatus getReviewStatus() {
        return reviewStatus;
    }

    /**
     * Sets the value of the reviewStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReviewStatus }
     *     
     */
    public void setReviewStatus(ReviewStatus value) {
        this.reviewStatus = value;
    }

    /**
     * Gets the value of the url property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the value of the url property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrl(String value) {
        this.url = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the purpose property.
     * 
     * @return
     *     possible object is
     *     {@link LoanPurpose }
     *     
     */
    public LoanPurpose getPurpose() {
        return purpose;
    }

    /**
     * Sets the value of the purpose property.
     * 
     * @param value
     *     allowed object is
     *     {@link LoanPurpose }
     *     
     */
    public void setPurpose(LoanPurpose value) {
        this.purpose = value;
    }

    /**
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * Gets the value of the addrCity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddrCity() {
        return addrCity;
    }

    /**
     * Sets the value of the addrCity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddrCity(String value) {
        this.addrCity = value;
    }

    /**
     * Gets the value of the addrState property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddrState() {
        return addrState;
    }

    /**
     * Sets the value of the addrState property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddrState(String value) {
        this.addrState = value;
    }

    /**
     * Gets the value of the msa property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMsa() {
        return msa;
    }

    /**
     * Sets the value of the msa property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMsa(String value) {
        this.msa = value;
    }

    /**
     * Gets the value of the investorCount property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getInvestorCount() {
        return investorCount;
    }

    /**
     * Sets the value of the investorCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setInvestorCount(Integer value) {
        this.investorCount = value;
    }

    /**
     * Gets the value of the ilsExpD property.
     * 
     * @return
     *     possible object is
     *     {@link Date }
     *     
     */
    public Date getIlsExpD() {
        return ilsExpD;
    }

    /**
     * Sets the value of the ilsExpD property.
     * 
     * @param value
     *     allowed object is
     *     {@link Date }
     *     
     */
    public void setIlsExpD(Date value) {
        this.ilsExpD = value;
    }

    /**
     * Gets the value of the initialListStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInitialListStatus() {
        return initialListStatus;
    }

    /**
     * Sets the value of the initialListStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInitialListStatus(String value) {
        this.initialListStatus = value;
    }

    /**
     * Gets the value of the empTitle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmpTitle() {
        return empTitle;
    }

    /**
     * Sets the value of the empTitle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmpTitle(String value) {
        this.empTitle = value;
    }

}
