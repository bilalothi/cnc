
package com.ugs.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for alert complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="alert">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="alertAcceptedTimestamp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="alertAssignedTimestamp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="alertId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="alertReceivedTimestamp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="alertResolvedTimestamp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="alertState" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="alertType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="assignComments" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="assignedBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="assignedTo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="deviceId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="flag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="resolveComments" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "alert", propOrder = {
    "alertAcceptedTimestamp",
    "alertAssignedTimestamp",
    "alertId",
    "alertReceivedTimestamp",
    "alertResolvedTimestamp",
    "alertState",
    "alertType",
    "assignComments",
    "assignedBy",
    "assignedTo",
    "deviceId",
    "flag",
    "resolveComments"
})
public class Alert {

    protected String alertAcceptedTimestamp;
    protected String alertAssignedTimestamp;
    protected Integer alertId;
    protected String alertReceivedTimestamp;
    protected String alertResolvedTimestamp;
    protected String alertState;
    protected String alertType;
    protected String assignComments;
    protected String assignedBy;
    protected String assignedTo;
    protected String deviceId;
    protected String flag;
    protected String resolveComments;

    /**
     * Gets the value of the alertAcceptedTimestamp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlertAcceptedTimestamp() {
        return alertAcceptedTimestamp;
    }

    /**
     * Sets the value of the alertAcceptedTimestamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlertAcceptedTimestamp(String value) {
        this.alertAcceptedTimestamp = value;
    }

    /**
     * Gets the value of the alertAssignedTimestamp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlertAssignedTimestamp() {
        return alertAssignedTimestamp;
    }

    /**
     * Sets the value of the alertAssignedTimestamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlertAssignedTimestamp(String value) {
        this.alertAssignedTimestamp = value;
    }

    /**
     * Gets the value of the alertId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAlertId() {
        return alertId;
    }

    /**
     * Sets the value of the alertId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAlertId(Integer value) {
        this.alertId = value;
    }

    /**
     * Gets the value of the alertReceivedTimestamp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlertReceivedTimestamp() {
        return alertReceivedTimestamp;
    }

    /**
     * Sets the value of the alertReceivedTimestamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlertReceivedTimestamp(String value) {
        this.alertReceivedTimestamp = value;
    }

    /**
     * Gets the value of the alertResolvedTimestamp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlertResolvedTimestamp() {
        return alertResolvedTimestamp;
    }

    /**
     * Sets the value of the alertResolvedTimestamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlertResolvedTimestamp(String value) {
        this.alertResolvedTimestamp = value;
    }

    /**
     * Gets the value of the alertState property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlertState() {
        return alertState;
    }

    /**
     * Sets the value of the alertState property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlertState(String value) {
        this.alertState = value;
    }

    /**
     * Gets the value of the alertType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlertType() {
        return alertType;
    }

    /**
     * Sets the value of the alertType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlertType(String value) {
        this.alertType = value;
    }

    /**
     * Gets the value of the assignComments property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAssignComments() {
        return assignComments;
    }

    /**
     * Sets the value of the assignComments property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAssignComments(String value) {
        this.assignComments = value;
    }

    /**
     * Gets the value of the assignedBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAssignedBy() {
        return assignedBy;
    }

    /**
     * Sets the value of the assignedBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAssignedBy(String value) {
        this.assignedBy = value;
    }

    /**
     * Gets the value of the assignedTo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAssignedTo() {
        return assignedTo;
    }

    /**
     * Sets the value of the assignedTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAssignedTo(String value) {
        this.assignedTo = value;
    }

    /**
     * Gets the value of the deviceId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * Sets the value of the deviceId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeviceId(String value) {
        this.deviceId = value;
    }

    /**
     * Gets the value of the flag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlag() {
        return flag;
    }

    /**
     * Sets the value of the flag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlag(String value) {
        this.flag = value;
    }

    /**
     * Gets the value of the resolveComments property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResolveComments() {
        return resolveComments;
    }

    /**
     * Sets the value of the resolveComments property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResolveComments(String value) {
        this.resolveComments = value;
    }

}
