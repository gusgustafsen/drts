package gov.ed.fsa.drts.AimsDrtsServicex;

public class AimsDrtsServiceProxy implements gov.ed.fsa.drts.AimsDrtsServicex.AimsDrtsService_PortType {
	private String _endpoint = null;
	private gov.ed.fsa.drts.AimsDrtsServicex.AimsDrtsService_PortType aimsDrtsService_PortType = null;

	public AimsDrtsServiceProxy() {
		_initAimsDrtsServiceProxy();
	}

	public AimsDrtsServiceProxy(String endpoint) {
		_endpoint = endpoint;
		_initAimsDrtsServiceProxy();
	}

	private void _initAimsDrtsServiceProxy() {
		try {
			aimsDrtsService_PortType =
					(new gov.ed.fsa.drts.AimsDrtsServicex.AimsDrtsService_ServiceLocator()).getAimsDrtsServiceSOAP();
			if (aimsDrtsService_PortType != null) {
				if (_endpoint != null)
					((javax.xml.rpc.Stub) aimsDrtsService_PortType)
							._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
				else
					_endpoint = (String) ((javax.xml.rpc.Stub) aimsDrtsService_PortType)
							._getProperty("javax.xml.rpc.service.endpoint.address");
			}

		} catch (javax.xml.rpc.ServiceException serviceException) {
		}
	}

	public String getEndpoint() {
		return _endpoint;
	}

	public void setEndpoint(String endpoint) {
		_endpoint = endpoint;
		if (aimsDrtsService_PortType != null)
			((javax.xml.rpc.Stub) aimsDrtsService_PortType)._setProperty("javax.xml.rpc.service.endpoint.address",
					_endpoint);

	}

	public gov.ed.fsa.drts.AimsDrtsServicex.AimsDrtsService_PortType getAimsDrtsService_PortType() {
		if (aimsDrtsService_PortType == null)
			_initAimsDrtsServiceProxy();
		return aimsDrtsService_PortType;
	}

	public gov.ed.fsa.drts.AimsDrtsx.CreateAccountResponseType createAccount(
			gov.ed.fsa.drts.AimsDrtsx.CreateAccountRequestType createAccountRequest) throws java.rmi.RemoteException {
		if (aimsDrtsService_PortType == null)
			_initAimsDrtsServiceProxy();
		return aimsDrtsService_PortType.createAccount(createAccountRequest);
	}

	public gov.ed.fsa.drts.AimsDrtsx.DeleteAccountResponseType deleteAccount(
			gov.ed.fsa.drts.AimsDrtsx.DeleteAccountRequestType deleteAccountRequest) throws java.rmi.RemoteException {
		if (aimsDrtsService_PortType == null)
			_initAimsDrtsServiceProxy();
		return aimsDrtsService_PortType.deleteAccount(deleteAccountRequest);
	}

}