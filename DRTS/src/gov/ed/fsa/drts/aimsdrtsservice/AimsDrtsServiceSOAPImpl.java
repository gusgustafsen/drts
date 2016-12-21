package gov.ed.fsa.drts.aimsdrtsservice;

import gov.ed.fsa.drts.aimsdrts.CreateAccountRequestType;
import gov.ed.fsa.drts.aimsdrts.CreateAccountResponseType;
import gov.ed.fsa.drts.aimsdrts.DeleteAccountRequestType;
import gov.ed.fsa.drts.aimsdrts.DeleteAccountResponseType;
import gov.ed.fsa.drts.aimsdrts.StatusCodeType;

@javax.jws.WebService(endpointInterface = "gov.ed.fsa.drts.aimsdrtsservice.AimsDrtsService", targetNamespace = "http://drts.fsa.ed.gov/AimsDrtsService", serviceName = "AimsDrtsService", portName = "AimsDrtsServiceSOAP")
public class AimsDrtsServiceSOAPImpl {

	public CreateAccountResponseType createAccount(CreateAccountRequestType createAccountRequest) {
		CreateAccountResponseType response = new CreateAccountResponseType();
		response.setStatusCode(StatusCodeType.SUCCESS);
		return response;
	}

	public DeleteAccountResponseType deleteAccount(DeleteAccountRequestType deleteAccountRequest) {
		DeleteAccountResponseType response = new DeleteAccountResponseType();
		response.setStatusCode(StatusCodeType.SUCCESS);
		return response;
	}

}