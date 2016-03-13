
package com.ugs.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.ugs.ws package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _AddDeviceInfo_QNAME = new QName("http://ws.ugs.com/", "addDeviceInfo");
    private final static QName _DeviceInfo_QNAME = new QName("http://ws.ugs.com/", "deviceInfo");
    private final static QName _AddAlertResponse_QNAME = new QName("http://ws.ugs.com/", "addAlertResponse");
    private final static QName _AddAlert_QNAME = new QName("http://ws.ugs.com/", "addAlert");
    private final static QName _Alert_QNAME = new QName("http://ws.ugs.com/", "alert");
    private final static QName _HelloResponse_QNAME = new QName("http://ws.ugs.com/", "helloResponse");
    private final static QName _AddDeviceInfoResponse_QNAME = new QName("http://ws.ugs.com/", "addDeviceInfoResponse");
    private final static QName _Hello_QNAME = new QName("http://ws.ugs.com/", "hello");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.ugs.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Hello }
     * 
     */
    public Hello createHello() {
        return new Hello();
    }

    /**
     * Create an instance of {@link AddDeviceInfoResponse }
     * 
     */
    public AddDeviceInfoResponse createAddDeviceInfoResponse() {
        return new AddDeviceInfoResponse();
    }

    /**
     * Create an instance of {@link HelloResponse }
     * 
     */
    public HelloResponse createHelloResponse() {
        return new HelloResponse();
    }

    /**
     * Create an instance of {@link Alert }
     * 
     */
    public Alert createAlert() {
        return new Alert();
    }

    /**
     * Create an instance of {@link AddAlert }
     * 
     */
    public AddAlert createAddAlert() {
        return new AddAlert();
    }

    /**
     * Create an instance of {@link AddAlertResponse }
     * 
     */
    public AddAlertResponse createAddAlertResponse() {
        return new AddAlertResponse();
    }

    /**
     * Create an instance of {@link DeviceInfo }
     * 
     */
    public DeviceInfo createDeviceInfo() {
        return new DeviceInfo();
    }

    /**
     * Create an instance of {@link AddDeviceInfo }
     * 
     */
    public AddDeviceInfo createAddDeviceInfo() {
        return new AddDeviceInfo();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddDeviceInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.ugs.com/", name = "addDeviceInfo")
    public JAXBElement<AddDeviceInfo> createAddDeviceInfo(AddDeviceInfo value) {
        return new JAXBElement<AddDeviceInfo>(_AddDeviceInfo_QNAME, AddDeviceInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeviceInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.ugs.com/", name = "deviceInfo")
    public JAXBElement<DeviceInfo> createDeviceInfo(DeviceInfo value) {
        return new JAXBElement<DeviceInfo>(_DeviceInfo_QNAME, DeviceInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddAlertResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.ugs.com/", name = "addAlertResponse")
    public JAXBElement<AddAlertResponse> createAddAlertResponse(AddAlertResponse value) {
        return new JAXBElement<AddAlertResponse>(_AddAlertResponse_QNAME, AddAlertResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddAlert }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.ugs.com/", name = "addAlert")
    public JAXBElement<AddAlert> createAddAlert(AddAlert value) {
        return new JAXBElement<AddAlert>(_AddAlert_QNAME, AddAlert.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Alert }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.ugs.com/", name = "alert")
    public JAXBElement<Alert> createAlert(Alert value) {
        return new JAXBElement<Alert>(_Alert_QNAME, Alert.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HelloResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.ugs.com/", name = "helloResponse")
    public JAXBElement<HelloResponse> createHelloResponse(HelloResponse value) {
        return new JAXBElement<HelloResponse>(_HelloResponse_QNAME, HelloResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddDeviceInfoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.ugs.com/", name = "addDeviceInfoResponse")
    public JAXBElement<AddDeviceInfoResponse> createAddDeviceInfoResponse(AddDeviceInfoResponse value) {
        return new JAXBElement<AddDeviceInfoResponse>(_AddDeviceInfoResponse_QNAME, AddDeviceInfoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Hello }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.ugs.com/", name = "hello")
    public JAXBElement<Hello> createHello(Hello value) {
        return new JAXBElement<Hello>(_Hello_QNAME, Hello.class, null, value);
    }

}
