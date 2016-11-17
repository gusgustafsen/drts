package gov.ed.fsa.drts.util;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.FacesException;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import gov.ed.fsa.drts.bean.PageMsg;
import gov.ed.fsa.drts.bean.PageMsgSeverity;

public class CustomExceptionHandler extends ExceptionHandlerWrapper {
	private static final Logger logger = Logger.getLogger(CustomExceptionHandler.class);
	private final ExceptionHandler wrapped;

	public CustomExceptionHandler(ExceptionHandler wrapped) {
		this.wrapped = wrapped;
	}

	@Override
	public ExceptionHandler getWrapped() {
		return this.wrapped;

	}

	@Override
	public void handle() throws FacesException {
		final Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator();

		while (i.hasNext()) {
			ExceptionQueuedEvent event = i.next();
			ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();
			// get the exception from context
			Throwable t = context.getException();
			final FacesContext fc = FacesContext.getCurrentInstance();
			final ExternalContext externalContext = fc.getExternalContext();
			// externalContext.getFlash().setKeepMessages(true);

			if (t instanceof javax.faces.application.ViewExpiredException) {
				String contextPath = externalContext.getContextName();
				try {
					StringBuilder timeoutUrl = new StringBuilder();
					timeoutUrl.append('/').append(externalContext.getContextName()).append(NpasPages.TIMEOUT);
					fc.getExternalContext().redirect(timeoutUrl.toString());
				} catch (IOException e) {
					logger.error("Unexpected exception redirecting to timeout page on ViewExpiredException", e);
				}
				return;
			}

			final ConfigurableNavigationHandler nav =
					(ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
			// here you do what ever you want with exception
			try {
				// log error ?
				logger.error("Internal error", t);

				final HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
				final String url = request.getRequestURL().toString();

				try {
					nav.performNavigation("/index?faces-redirect=true");
				} catch (IllegalStateException ex) {
					logger.error("Attempt to perform navigation after internal error", ex);
				}
				// addMessage(Messages.TRY_AGAIN.getStringValue(),
				// FacesMessage.SEVERITY_ERROR);

				final PageMsg pageMsg = FacesContext.getCurrentInstance().getApplication()
						.evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{PageMsg}", PageMsg.class);

				pageMsg.createMsg(Messages.TRY_AGAIN, PageMsgSeverity.ERROR);

				// fc.renderResponse();
				// remove the comment below if you want to report the error in a
				// jsf error message
				// JsfUtil.addErrorMessage(t.getMessage());
			} finally {
				// remove it from queue
				i.remove();
			}
		}
		// parent hanle
		getWrapped().handle();
	}

	private void addMessage(final String msg, final Severity severityInfo) {
		if ((msg != null) && (msg.length() != 0)) {
			final FacesMessage message = new FacesMessage();
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			message.setSummary(msg);
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}

}
