package gov.ed.fsa.drts.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;

import gov.ed.fsa.drts.bean.UserSession;
import gov.ed.fsa.drts.dataaccess.OracleFactory;
import gov.ed.fsa.drts.security.DRTSRealm;

public class RequestValidator implements PhaseListener {

	private static final long serialVersionUID = -2282664829406618457L;

	private static final Logger logger = Logger.getLogger(RequestValidator.class);

	@Override
	public void afterPhase(PhaseEvent phase_event) {
	}

	@Override
	public void beforePhase(PhaseEvent phase_event) {
		FacesContext faces_context = phase_event.getFacesContext();

		HttpServletResponse http_response = (HttpServletResponse) faces_context.getExternalContext().getResponse();
		HttpServletRequest http_request = (HttpServletRequest) faces_context.getExternalContext().getRequest();

		String request_url = http_request.getRequestURL().toString();

		Map<String, String> request_header_map = faces_context.getExternalContext().getRequestHeaderMap();

		final String incoming_user = ApplicationProperties.USER_HEADER_OVERRIDE.getBooleanValue()
				? ApplicationProperties.USER_HEADER_OVERRIDE_VALUE.getStringValue()
				: request_header_map.get(ApplicationProperties.USER_HEADER.getStringValue());

		UserSession temp_user_session = (UserSession) faces_context.getExternalContext().getSessionMap()
				.get(ApplicationProperties.USER_SESSION_HEADER.getStringValue());

		String destination_url = null;

		/*
		 * Cross-Frame Scripting (XFS) vulnerability can allow an attacker to load the vulnerable application inside an
		 * HTML iframe tag. Fixed by setting the X-Frame-Options header to SAMEORIGIN- The page can be framed by another
		 * page only if it belongs to the same origin as the page being framed
		 */
		http_response.addHeader("X-FRAME-OPTIONS", "SAMEORIGIN");

		if (request_url.contains("/newRequest") == true) {
			faces_context.getExternalContext().getFlash().put("drtsDataRequest", null);
		}

		if (!request_url.contains("/error") && !request_url.contains("/Aims")) {
			if (Utils.isStringEmpty(incoming_user) == false) {
				logger.debug("user header: " + incoming_user);

				if (temp_user_session == null) {
					logger.info("user session is null");

					IdentityService identity_service = OracleFactory.getProcessEngine().getIdentityService();

					if (identity_service != null) {
						User activiti_user = identity_service.createUserQuery().userId(incoming_user).singleResult();

						if (activiti_user != null) {
							List<Group> security_groups = identity_service.createGroupQuery()
									.groupType(ApplicationProperties.GROUP_TYPE_SECURITY.getStringValue())
									.groupMember(activiti_user.getId()).list();

							List<String> string_groups = new ArrayList<String>();

							for (Group group : security_groups) {
								string_groups.add(group.getName().toLowerCase());
							}

							if (security_groups != null && security_groups.size() > 0) {
								Set<String> roles = new HashSet<String>(string_groups);

								final DRTSRealm realm = new DRTSRealm(roles);

								final DefaultSecurityManager securityManager = new DefaultSecurityManager(realm);

								Subject subject = new Subject.Builder(securityManager).buildSubject();

								subject.login(new UsernamePasswordToken(DRTSRealm.DEFAULT_USERNAME,
										DRTSRealm.DEFAULT_PASSWORD));

								UserSession user_session = new UserSession();
								user_session.setUser(activiti_user);
								user_session.setSubject(subject);

								faces_context.getExternalContext().getSessionMap()
										.put(ApplicationProperties.USER_SESSION_HEADER.getStringValue(), user_session);

								logger.info("user session created for user " + incoming_user);

								if (request_url.contains("index.htm") == true) {
									destination_url = ApplicationProperties.CONTEXT_ROOT.getStringValue()
											+ user_session.getHomePage();
								}
							} else {
								logger.error("User " + incoming_user + " does not have any groups.");
								destination_url = ApplicationProperties.CONTEXT_ROOT.getStringValue()
										+ ApplicationProperties.PAGE_ERROR_UNAUTHORIZED.getStringValue();
							}
						} else {
							logger.error("User " + incoming_user + " was not found in Activiti.");
							destination_url = ApplicationProperties.CONTEXT_ROOT.getStringValue()
									+ ApplicationProperties.PAGE_ERROR_UNAUTHORIZED.getStringValue();
						}
					} else {
						logger.error("Unable to create an Identity Service. User " + incoming_user);
						destination_url = ApplicationProperties.CONTEXT_ROOT.getStringValue()
								+ ApplicationProperties.PAGE_ERROR_APPLICATION.getStringValue();
					}
				} else {
					logger.debug("user session already exists. User" + incoming_user);
				}
			} else {
				logger.error("User header is empty.");
				destination_url = ApplicationProperties.CONTEXT_ROOT.getStringValue()
						+ ApplicationProperties.PAGE_ERROR_UNAUTHORIZED.getStringValue();
			}

			if (destination_url != null) {
				logger.info("Redirecting to: " + destination_url);

				try {
					faces_context.getExternalContext().redirect(destination_url);
				} catch (IOException ioe) {
					logger.error("Unable redirect to appropriate page.", ioe);
				} catch (Exception e) {
					logger.error("Unable redirect to appropriate page.", e);
				}
			}
		}
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}
}
