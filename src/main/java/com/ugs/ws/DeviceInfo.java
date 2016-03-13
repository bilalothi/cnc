
package com.ugs.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for deviceInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="deviceInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="battery" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="deviceId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="deviceMACAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="deviceStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="deviceType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="deviceTypeChar" type="{http://www.w3.org/2001/XMLSchema}unsignedShort" minOccurs="0"/>
 *         &lt;element name="explodable" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="lastAlertTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="linkQuality" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="networkId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="primaryRouterId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="secondryRouterId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sensitivity" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="signalStrength" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="videoStreamLink" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "deviceInfo", propOrder = {
    "battery",
    "deviceId",
    "deviceMACAddress",
    "deviceStatus",
    "deviceType",
    "deviceTypeChar",
    "explodable",
    "lastAlertTime",
    "linkQuality",
    "networkId",
    "primaryRouterId",
    "secondryRouterId",
    "sensitivity",
    "signalStrength",
    "videoStreamLink"
})
public class DeviceInfo {

    protected Integer battery;
    protected String deviceId;
    protected String deviceMACAddress;
    protected String deviceStatus;
    protected String deviceType;
    @XmlSchemaType(name = "unsignedShort")
    protected Integer deviceTypeChar;
    protected Boolean explodable;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastAlertTime;
    protected Integer linkQuality;
    protected String networkId;
    protected String primaryRouterId;
    protected String secondryRouterId;
    protected Integer sensitivity;
    protected Integer signalStrength;
    protected String videoStreamLink;

    /**
     * Gets the value of the battery property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getBattery() {
        return battery;
    }

    /**
     * Sets the value of the battery property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setBattery(Integer value) {
        this.battery = value;
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
     * Gets the value of the deviceMACAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeviceMACAddress() {
        return deviceMACAddress;
    }

    /**
     * Sets the value of the deviceMACAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeviceMACAddress(String value) {
        this.deviceMACAddress = value;
    }

    /**
     * Gets the value of the deviceStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeviceStatus() {
        return deviceStatus;
    }

    /**
     * Sets the value of the deviceStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeviceStatus(String value) {
        this.deviceStatus = value;
    }

    /**
     * Gets the value of the deviceType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeviceType() {
        return deviceType;
    }

    /**
     * Sets the value of the deviceType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeviceType(String value) {
        this.deviceType = value;
    }

    /**
     * Gets the value of the deviceTypeChar property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDeviceTypeChar() {
        return deviceTypeChar;
    }

    /**
     * Sets the value of the deviceTypeChar property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDeviceTypeChar(Integer value) {
        this.deviceTypeChar = value;
    }

    /**
     * Gets the value of the explodable property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isExplodable() {
        return explodable;
    }

    /**
     * Sets the value of the explodable property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setExplodable(Boolean value) {
        this.explodable = value;
    }

    /**
     * Gets the value of the lastAlertTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastAlertTime() {
        return lastAlertTime;
    }

    /**
     * Sets the value of the lastAlertTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastAlertTime(XMLGregorianCalendar value) {
        this.lastAlertTime = value;
    }

    /**
     * Gets the value of the linkQuality property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getLinkQuality() {
        return linkQuality;
    }

    /**
     * Sets the value of the linkQuality property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setLinkQuality(Integer value) {
        this.linkQuality = value;
    }

    /**
     * Gets the value of the networkId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNetworkId() {
        return networkId;
    }

    /**
     * Sets the value of the networkId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNetworkId(String value) {
        this.networkId = value;
    }

    /**
     * Gets the value of the primaryRouterId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrimaryRouterId() {
        return primaryRouterId;
    }

    /**
     * Sets the value of the primaryRouterId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrimaryRouterId(String value) {
        this.primaryRouterId = value;
    }

    /**
     * Gets the value of the secondryRouterId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecondryRouterId() {
        return secondryRouterId;
    }

    /**
     * Sets the value of the secondryRouterId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecondryRouterId(String value) {
        this.secondryRouterId = value;
    }

    /**
     * Gets the value of the sensitivity property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSensitivity() {
        return sensitivity;
    }

    /**
     * Sets the value of the sensitivity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSensitivity(Integer value) {
        this.sensitivity = value;
    }

    /**
     * Gets the value of the signalStrength property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSignalStrength() {
        return signalStrength;
    }

    /**
     * Sets the value of the signalStrength property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSignalStrength(Integer value) {
        this.signalStrength = value;
    }

    /**
     * Gets the value of the videoStreamLink property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVideoStreamLink() {
        return videoStreamLink;
    }

    /**
     * Sets the value of the videoStreamLink property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVideoStreamLink(String value) {
        this.videoStreamLink = value;
    }

}
