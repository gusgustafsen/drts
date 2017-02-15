/**
 * AimsDrtsService_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gov.ed.fsa.drts.AimsDrtsService;

public class AimsDrtsService_ServiceLocator extends org.apache.axis.client.Service implements gov.ed.fsa.drts.AimsDrtsService.AimsDrtsService_Service {

    public AimsDrtsService_ServiceLocator() {
    }


    public AimsDrtsService_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public AimsDrtsService_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for AimsDrtsServiceSOAP
    private java.lang.String AimsDrtsServiceSOAP_address = "http://localhost:9080/drts/services/AimsDrtsServiceSOAP";

    public java.lang.String getAimsDrtsServiceSOAPAddress() {
        return AimsDrtsServiceSOAP_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String AimsDrtsServiceSOAPWSDDServiceName = "AimsDrtsServiceSOAP";

    public java.lang.String getAimsDrtsServiceSOAPWSDDServiceName() {
        return AimsDrtsServiceSOAPWSDDServiceName;
    }

    public void setAimsDrtsServiceSOAPWSDDServiceName(java.lang.String name) {
        AimsDrtsServiceSOAPWSDDServiceName = name;
    }

    public gov.ed.fsa.drts.AimsDrtsService.AimsDrtsService_PortType getAimsDrtsServiceSOAP() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(AimsDrtsServiceSOAP_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getAimsDrtsServiceSOAP(endpoint);
    }

    public gov.ed.fsa.drts.AimsDrtsService.AimsDrtsService_PortType getAimsDrtsServiceSOAP(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            gov.ed.fsa.drts.AimsDrtsService.AimsDrtsServiceSOAPStub _stub = new gov.ed.fsa.drts.AimsDrtsService.AimsDrtsServiceSOAPStub(portAddress, this);
            _stub.setPortName(getAimsDrtsServiceSOAPWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setAimsDrtsServiceSOAPEndpointAddress(java.lang.String address) {
        AimsDrtsServiceSOAP_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (gov.ed.fsa.drts.AimsDrtsService.AimsDrtsService_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                gov.ed.fsa.drts.AimsDrtsService.AimsDrtsServiceSOAPStub _stub = new gov.ed.fsa.drts.AimsDrtsService.AimsDrtsServiceSOAPStub(new java.net.URL(AimsDrtsServiceSOAP_address), this);
                _stub.setPortName(getAimsDrtsServiceSOAPWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("AimsDrtsServiceSOAP".equals(inputPortName)) {
            return getAimsDrtsServiceSOAP();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://drts.fsa.ed.gov/AimsDrtsService", "AimsDrtsService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://drts.fsa.ed.gov/AimsDrtsService", "AimsDrtsServiceSOAP"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("AimsDrtsServiceSOAP".equals(portName)) {
            setAimsDrtsServiceSOAPEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
