
package com.ugs.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebService(name = "ugsWS", targetNamespace = "http://ws.ugs.com/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface UgsWS {


    /**
     * 
     * @param name
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "hello", targetNamespace = "http://ws.ugs.com/", className = "com.ugs.ws.Hello")
    @ResponseWrapper(localName = "helloResponse", targetNamespace = "http://ws.ugs.com/", className = "com.ugs.ws.HelloResponse")
    @Action(input = "http://ws.ugs.com/ugsWS/helloRequest", output = "http://ws.ugs.com/ugsWS/helloResponse")
    public String hello(
        @WebParam(name = "name", targetNamespace = "")
        String name);

    /**
     * 
     * @param alert
     * @return
     *     returns com.ugs.ws.Alert
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "addAlert", targetNamespace = "http://ws.ugs.com/", className = "com.ugs.ws.AddAlert")
    @ResponseWrapper(localName = "addAlertResponse", targetNamespace = "http://ws.ugs.com/", className = "com.ugs.ws.AddAlertResponse")
    @Action(input = "http://ws.ugs.com/ugsWS/addAlertRequest", output = "http://ws.ugs.com/ugsWS/addAlertResponse")
    public Alert addAlert(
        @WebParam(name = "alert", targetNamespace = "")
        Alert alert);

    /**
     * 
     * @param addDeviceInfo
     * @return
     *     returns com.ugs.ws.DeviceInfo
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "addDeviceInfo", targetNamespace = "http://ws.ugs.com/", className = "com.ugs.ws.AddDeviceInfo")
    @ResponseWrapper(localName = "addDeviceInfoResponse", targetNamespace = "http://ws.ugs.com/", className = "com.ugs.ws.AddDeviceInfoResponse")
    @Action(input = "http://ws.ugs.com/ugsWS/addDeviceInfoRequest", output = "http://ws.ugs.com/ugsWS/addDeviceInfoResponse")
    public DeviceInfo addDeviceInfo(
        @WebParam(name = "addDeviceInfo", targetNamespace = "")
        DeviceInfo addDeviceInfo);

}
