
package com.ugs.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for addDeviceInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="addDeviceInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="addDeviceInfo" type="{http://ws.ugs.com/}deviceInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addDeviceInfo", propOrder = {
    "addDeviceInfo"
})
public class AddDeviceInfo {

    protected DeviceInfo addDeviceInfo;

    /**
     * Gets the value of the addDeviceInfo property.
     * 
     * @return
     *     possible object is
     *     {@link DeviceInfo }
     *     
     */
    public DeviceInfo getAddDeviceInfo() {
        return addDeviceInfo;
    }

    /**
     * Sets the value of the addDeviceInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeviceInfo }
     *     
     */
    public void setAddDeviceInfo(DeviceInfo value) {
        this.addDeviceInfo = value;
    }

}
