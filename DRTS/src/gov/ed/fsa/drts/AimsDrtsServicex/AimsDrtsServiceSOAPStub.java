/**
 * AimsDrtsServiceSOAPStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gov.ed.fsa.drts.AimsDrtsServicex;

public class AimsDrtsServiceSOAPStub extends org.apache.axis.client.Stub
		implements gov.ed.fsa.drts.AimsDrtsServicex.AimsDrtsService_PortType {
	private java.util.Vector cachedSerClasses = new java.util.Vector();
	private java.util.Vector cachedSerQNames = new java.util.Vector();
	private java.util.Vector cachedSerFactories = new java.util.Vector();
	private java.util.Vector cachedDeserFactories = new java.util.Vector();

	static org.apache.axis.description.OperationDesc[] _operations;

	static {
		_operations = new org.apache.axis.description.OperationDesc[2];
		_initOperationDesc1();
	}

	private static void _initOperationDesc1() {
		org.apache.axis.description.OperationDesc oper;
		org.apache.axis.description.ParameterDesc param;
		oper = new org.apache.axis.description.OperationDesc();
		oper.setName("CreateAccount");
		param = new org.apache.axis.description.ParameterDesc(
				new javax.xml.namespace.QName("http://drts.fsa.ed.gov/AimsDrts", "CreateAccountRequest"),
				org.apache.axis.description.ParameterDesc.IN,
				new javax.xml.namespace.QName("http://drts.fsa.ed.gov/AimsDrts", "CreateAccountRequestType"),
				gov.ed.fsa.drts.AimsDrtsx.CreateAccountRequestType.class, false, false);
		oper.addParameter(param);
		oper.setReturnType(
				new javax.xml.namespace.QName("http://drts.fsa.ed.gov/AimsDrts", "CreateAccountResponseType"));
		oper.setReturnClass(gov.ed.fsa.drts.AimsDrtsx.CreateAccountResponseType.class);
		oper.setReturnQName(new javax.xml.namespace.QName("http://drts.fsa.ed.gov/AimsDrts", "CreateAccountResponse"));
		oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		_operations[0] = oper;

		oper = new org.apache.axis.description.OperationDesc();
		oper.setName("DeleteAccount");
		param = new org.apache.axis.description.ParameterDesc(
				new javax.xml.namespace.QName("http://drts.fsa.ed.gov/AimsDrts", "DeleteAccountRequest"),
				org.apache.axis.description.ParameterDesc.IN,
				new javax.xml.namespace.QName("http://drts.fsa.ed.gov/AimsDrts", "DeleteAccountRequestType"),
				gov.ed.fsa.drts.AimsDrtsx.DeleteAccountRequestType.class, false, false);
		oper.addParameter(param);
		oper.setReturnType(
				new javax.xml.namespace.QName("http://drts.fsa.ed.gov/AimsDrts", "DeleteAccountResponseType"));
		oper.setReturnClass(gov.ed.fsa.drts.AimsDrtsx.DeleteAccountResponseType.class);
		oper.setReturnQName(new javax.xml.namespace.QName("http://drts.fsa.ed.gov/AimsDrts", "DeleteAccountResponse"));
		oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		_operations[1] = oper;

	}

	public AimsDrtsServiceSOAPStub() throws org.apache.axis.AxisFault {
		this(null);
	}

	public AimsDrtsServiceSOAPStub(java.net.URL endpointURL, javax.xml.rpc.Service service)
			throws org.apache.axis.AxisFault {
		this(service);
		super.cachedEndpoint = endpointURL;
	}

	public AimsDrtsServiceSOAPStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
		if (service == null) {
			super.service = new org.apache.axis.client.Service();
		} else {
			super.service = service;
		}
		((org.apache.axis.client.Service) super.service).setTypeMappingVersion("1.2");
		java.lang.Class cls;
		javax.xml.namespace.QName qName;
		javax.xml.namespace.QName qName2;
		java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
		java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
		java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
		java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
		java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
		java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
		java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
		java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
		java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
		java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
		qName = new javax.xml.namespace.QName("http://drts.fsa.ed.gov/AimsDrts", "CommonResponseType");
		cachedSerQNames.add(qName);
		cls = gov.ed.fsa.drts.AimsDrtsx.CommonResponseType.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new javax.xml.namespace.QName("http://drts.fsa.ed.gov/AimsDrts", "CreateAccountRequestType");
		cachedSerQNames.add(qName);
		cls = gov.ed.fsa.drts.AimsDrtsx.CreateAccountRequestType.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new javax.xml.namespace.QName("http://drts.fsa.ed.gov/AimsDrts", "CreateAccountResponseType");
		cachedSerQNames.add(qName);
		cls = gov.ed.fsa.drts.AimsDrtsx.CreateAccountResponseType.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new javax.xml.namespace.QName("http://drts.fsa.ed.gov/AimsDrts", "DeleteAccountRequestType");
		cachedSerQNames.add(qName);
		cls = gov.ed.fsa.drts.AimsDrtsx.DeleteAccountRequestType.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new javax.xml.namespace.QName("http://drts.fsa.ed.gov/AimsDrts", "DeleteAccountResponseType");
		cachedSerQNames.add(qName);
		cls = gov.ed.fsa.drts.AimsDrtsx.DeleteAccountResponseType.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new javax.xml.namespace.QName("http://drts.fsa.ed.gov/AimsDrts", "EmailAddressType");
		cachedSerQNames.add(qName);
		cls = java.lang.String.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory
				.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
		cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory
				.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

		qName = new javax.xml.namespace.QName("http://drts.fsa.ed.gov/AimsDrts", "FirstNameType");
		cachedSerQNames.add(qName);
		cls = java.lang.String.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory
				.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
		cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory
				.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

		qName = new javax.xml.namespace.QName("http://drts.fsa.ed.gov/AimsDrts", "GroupType");
		cachedSerQNames.add(qName);
		cls = java.lang.String.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory
				.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
		cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory
				.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

		qName = new javax.xml.namespace.QName("http://drts.fsa.ed.gov/AimsDrts", "LastNameType");
		cachedSerQNames.add(qName);
		cls = java.lang.String.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory
				.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
		cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory
				.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

		qName = new javax.xml.namespace.QName("http://drts.fsa.ed.gov/AimsDrts", "ReasonCodeDescriptionType");
		cachedSerQNames.add(qName);
		cls = java.lang.String.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory
				.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
		cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory
				.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

		qName = new javax.xml.namespace.QName("http://drts.fsa.ed.gov/AimsDrts", "ReasonCodeType");
		cachedSerQNames.add(qName);
		cls = gov.ed.fsa.drts.AimsDrtsx.ReasonCodeType.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(enumsf);
		cachedDeserFactories.add(enumdf);

		qName = new javax.xml.namespace.QName("http://drts.fsa.ed.gov/AimsDrts", "StatusCodeType");
		cachedSerQNames.add(qName);
		cls = gov.ed.fsa.drts.AimsDrtsx.StatusCodeType.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(enumsf);
		cachedDeserFactories.add(enumdf);

		qName = new javax.xml.namespace.QName("http://drts.fsa.ed.gov/AimsDrts", "UserIdType");
		cachedSerQNames.add(qName);
		cls = java.lang.String.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory
				.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
		cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory
				.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

	}

	protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
		try {
			org.apache.axis.client.Call _call = super._createCall();
			if (super.maintainSessionSet) {
				_call.setMaintainSession(super.maintainSession);
			}
			if (super.cachedUsername != null) {
				_call.setUsername(super.cachedUsername);
			}
			if (super.cachedPassword != null) {
				_call.setPassword(super.cachedPassword);
			}
			if (super.cachedEndpoint != null) {
				_call.setTargetEndpointAddress(super.cachedEndpoint);
			}
			if (super.cachedTimeout != null) {
				_call.setTimeout(super.cachedTimeout);
			}
			if (super.cachedPortName != null) {
				_call.setPortName(super.cachedPortName);
			}
			java.util.Enumeration keys = super.cachedProperties.keys();
			while (keys.hasMoreElements()) {
				java.lang.String key = (java.lang.String) keys.nextElement();
				_call.setProperty(key, super.cachedProperties.get(key));
			}
			// All the type mapping information is registered
			// when the first call is made.
			// The type mapping information is actually registered in
			// the TypeMappingRegistry of the service, which
			// is the reason why registration is only needed for the first call.
			synchronized (this) {
				if (firstCall()) {
					// must set encoding style before registering serializers
					_call.setEncodingStyle(null);
					for (int i = 0; i < cachedSerFactories.size(); ++i) {
						java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
						javax.xml.namespace.QName qName = (javax.xml.namespace.QName) cachedSerQNames.get(i);
						java.lang.Object x = cachedSerFactories.get(i);
						if (x instanceof Class) {
							java.lang.Class sf = (java.lang.Class) cachedSerFactories.get(i);
							java.lang.Class df = (java.lang.Class) cachedDeserFactories.get(i);
							_call.registerTypeMapping(cls, qName, sf, df, false);
						} else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
							org.apache.axis.encoding.SerializerFactory sf =
									(org.apache.axis.encoding.SerializerFactory) cachedSerFactories.get(i);
							org.apache.axis.encoding.DeserializerFactory df =
									(org.apache.axis.encoding.DeserializerFactory) cachedDeserFactories.get(i);
							_call.registerTypeMapping(cls, qName, sf, df, false);
						}
					}
				}
			}
			return _call;
		} catch (java.lang.Throwable _t) {
			throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
		}
	}

	public gov.ed.fsa.drts.AimsDrtsx.CreateAccountResponseType createAccount(
			gov.ed.fsa.drts.AimsDrtsx.CreateAccountRequestType createAccountRequest) throws java.rmi.RemoteException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
		_call.setOperation(_operations[0]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("http://aims.fsa.ed.gov/AimsDrtsService/CreateAccount");
		_call.setEncodingStyle(null);
		_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
		_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new javax.xml.namespace.QName("", "CreateAccount"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			java.lang.Object _resp = _call.invoke(new java.lang.Object[] { createAccountRequest });

			if (_resp instanceof java.rmi.RemoteException) {
				throw(java.rmi.RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (gov.ed.fsa.drts.AimsDrtsx.CreateAccountResponseType) _resp;
				} catch (java.lang.Exception _exception) {
					return (gov.ed.fsa.drts.AimsDrtsx.CreateAccountResponseType) org.apache.axis.utils.JavaUtils
							.convert(_resp, gov.ed.fsa.drts.AimsDrtsx.CreateAccountResponseType.class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public gov.ed.fsa.drts.AimsDrtsx.DeleteAccountResponseType deleteAccount(
			gov.ed.fsa.drts.AimsDrtsx.DeleteAccountRequestType deleteAccountRequest) throws java.rmi.RemoteException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
		_call.setOperation(_operations[1]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("http://aims.fsa.ed.gov/AimsDrtsService/DeleteAccount");
		_call.setEncodingStyle(null);
		_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
		_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new javax.xml.namespace.QName("", "DeleteAccount"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			java.lang.Object _resp = _call.invoke(new java.lang.Object[] { deleteAccountRequest });

			if (_resp instanceof java.rmi.RemoteException) {
				throw(java.rmi.RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (gov.ed.fsa.drts.AimsDrtsx.DeleteAccountResponseType) _resp;
				} catch (java.lang.Exception _exception) {
					return (gov.ed.fsa.drts.AimsDrtsx.DeleteAccountResponseType) org.apache.axis.utils.JavaUtils
							.convert(_resp, gov.ed.fsa.drts.AimsDrtsx.DeleteAccountResponseType.class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

}
