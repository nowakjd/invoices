//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.01.10 at 08:44:55 PM CET 
//


package pl.coderstrust.service.soap.bindingClasses;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for company complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="company">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="companyId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="companyName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="address" type="{http://invoiceapp-service.com}address"/>
 *         &lt;element name="taxIdentificationNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="accountNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "company", propOrder = {
    "companyId",
    "companyName",
    "address",
    "taxIdentificationNumber",
    "accountNumber"
})
public class Company {

  protected long companyId;
  @XmlElement(required = true)
  protected String companyName;
  @XmlElement(required = true)
  protected Address address;
  @XmlElement(required = true)
  protected String taxIdentificationNumber;
  @XmlElement(required = true)
  protected String accountNumber;

  /**
   * Gets the value of the companyId property.
   */
  public long getCompanyId() {
    return companyId;
  }

  /**
   * Sets the value of the companyId property.
   */
  public void setCompanyId(long value) {
    this.companyId = value;
  }

  /**
   * Gets the value of the companyName property.
   *
   * @return possible object is {@link String }
   */
  public String getCompanyName() {
    return companyName;
  }

  /**
   * Sets the value of the companyName property.
   *
   * @param value allowed object is {@link String }
   */
  public void setCompanyName(String value) {
    this.companyName = value;
  }

  /**
   * Gets the value of the address property.
   *
   * @return possible object is {@link Address }
   */
  public Address getAddress() {
    return address;
  }

  /**
   * Sets the value of the address property.
   *
   * @param value allowed object is {@link Address }
   */
  public void setAddress(Address value) {
    this.address = value;
  }

  /**
   * Gets the value of the taxIdentificationNumber property.
   *
   * @return possible object is {@link String }
   */
  public String getTaxIdentificationNumber() {
    return taxIdentificationNumber;
  }

  /**
   * Sets the value of the taxIdentificationNumber property.
   *
   * @param value allowed object is {@link String }
   */
  public void setTaxIdentificationNumber(String value) {
    this.taxIdentificationNumber = value;
  }

  /**
   * Gets the value of the accountNumber property.
   *
   * @return possible object is {@link String }
   */
  public String getAccountNumber() {
    return accountNumber;
  }

  /**
   * Sets the value of the accountNumber property.
   *
   * @param value allowed object is {@link String }
   */
  public void setAccountNumber(String value) {
    this.accountNumber = value;
  }

}