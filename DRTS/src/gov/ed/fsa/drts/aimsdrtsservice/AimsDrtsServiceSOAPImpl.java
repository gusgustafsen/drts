package gov.ed.fsa.drts.aimsdrtsservice;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.apache.log4j.Logger;

import gov.ed.fsa.drts.aimsdrts.CreateAccountRequestType;
import gov.ed.fsa.drts.aimsdrts.CreateAccountResponseType;
import gov.ed.fsa.drts.aimsdrts.DeleteAccountRequestType;
import gov.ed.fsa.drts.aimsdrts.DeleteAccountResponseType;
import gov.ed.fsa.drts.aimsdrts.ReasonCodeType;
import gov.ed.fsa.drts.aimsdrts.StatusCodeType;
import gov.ed.fsa.drts.bean.UserBean;
import gov.ed.fsa.drts.dataaccess.OracleFactory;

@javax.jws.WebService(endpointInterface = "gov.ed.fsa.drts.aimsdrtsservice.AimsDrtsService", targetNamespace = "http://drts.fsa.ed.gov/AimsDrtsService", serviceName = "AimsDrtsService", portName = "AimsDrtsServiceSOAP")
public class AimsDrtsServiceSOAPImpl {

	private static final Logger logger = Logger.getLogger(AimsDrtsServiceSOAPImpl.class);

	private static Map<String, String> groupNames;

	static {

		groupNames = new HashMap<>();
		groupNames.put("DRTS_ADMIN", "admin");
		groupNames.put("DRTS_DRT", "drt");
		groupNames.put("DRTS_REPORTER", "reporter");
		groupNames.put("DRTS_REQUESTOR", "requestor");
		groupNames.put("DRTS_SME", "sme");
	}

	public CreateAccountResponseType createAccount(CreateAccountRequestType createAccountRequest) {
		CreateAccountResponseType response = new CreateAccountResponseType();
		response.setStatusCode(StatusCodeType.SUCCESS);

		String group = groupNames.get(createAccountRequest.getGroup());

		if (group == null) {
			response.setStatusCode(StatusCodeType.FAIL);
			response.setReasonCode(ReasonCodeType.GENERAL_ERROR);
			response.setReasonCodeDescription(
					"Error on add user, found unexpected group " + createAccountRequest.getGroup());
			logger.error("Error adding user " + createAccountRequest.getUserId() + ", found unexpected group "
					+ createAccountRequest.getGroup());
		} else {

			try {
				UserBean user = new UserBean(createAccountRequest.getUserId(), createAccountRequest.getFirstName(),
						createAccountRequest.getLastName(), createAccountRequest.getEmailAddress(), group);
				user.save(true);
			} catch (RuntimeException e) {
				response.setStatusCode(StatusCodeType.FAIL);
				response.setReasonCode(ReasonCodeType.GENERAL_ERROR);
				response.setReasonCodeDescription(e.toString());
				logger.error("Error adding user " + createAccountRequest.getUserId(), e);
			}
		}

		return response;
	}

	public DeleteAccountResponseType deleteAccount(DeleteAccountRequestType deleteAccountRequest) {
		DeleteAccountResponseType response = new DeleteAccountResponseType();

		ProcessEngine process_engine = OracleFactory.getProcessEngine();
		IdentityService identity_service = process_engine.getIdentityService();
		try {
			identity_service.deleteUser(deleteAccountRequest.getUserId());
			response.setStatusCode(StatusCodeType.SUCCESS);
		} catch (RuntimeException e) {
			response.setStatusCode(StatusCodeType.FAIL);
			response.setReasonCode(ReasonCodeType.GENERAL_ERROR);
			response.setReasonCodeDescription(e.toString());
			logger.error("Error deleting user " + deleteAccountRequest.getUserId(), e);
		}

		return response;
	}

}