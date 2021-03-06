
package com.ugs.ws;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "ugsWS", targetNamespace = "http://ws.ugs.com/", wsdlLocation = "http://20.60.201.200:8080/ugs2/ugsWS?wsdl")
public class UgsWS_Service
    extends Service
{

    private final static URL UGSWS_WSDL_LOCATION;
    private final static WebServiceException UGSWS_EXCEPTION;
    private final static QName UGSWS_QNAME = new QName("http://ws.ugs.com/", "ugsWS");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://20.60.201.200:8080/ugs2/ugsWS?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        UGSWS_WSDL_LOCATION = url;
        UGSWS_EXCEPTION = e;
    }

    public UgsWS_Service() {
        super(__getWsdlLocation(), UGSWS_QNAME);
    }

    public UgsWS_Service(WebServiceFeature... features) {
        super(__getWsdlLocation(), UGSWS_QNAME, features);
    }

    public UgsWS_Service(URL wsdlLocation) {
        super(wsdlLocation, UGSWS_QNAME);
    }

    public UgsWS_Service(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, UGSWS_QNAME, features);
    }

    public UgsWS_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public UgsWS_Service(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns UgsWS
     */
    @WebEndpoint(name = "ugsWSPort")
    public UgsWS getUgsWSPort() {
        return super.getPort(new QName("http://ws.ugs.com/", "ugsWSPort"), UgsWS.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns UgsWS
     */
    @WebEndpoint(name = "ugsWSPort")
    public UgsWS getUgsWSPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://ws.ugs.com/", "ugsWSPort"), UgsWS.class, features);
    }

    private static URL __getWsdlLocation() {
        if (UGSWS_EXCEPTION!= null) {
            throw UGSWS_EXCEPTION;
        }
        return UGSWS_WSDL_LOCATION;
    }

}
