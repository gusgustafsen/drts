package gov.ed.fsa.drts.bean;

import java.text.MessageFormat;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import gov.ed.fsa.drts.util.DrtsUtils;

@ManagedBean(name = "PageMsg")
@SessionScoped
public class PageMsg {
	private String message;
	private Messages messageKey;
	private String[] messageList;
	private boolean display = false;
	private PageMsgSeverity severity;
	private boolean errMsg = false;
	private boolean warnMsg = false;
	private boolean successMsg = false;

	public void createMsg(final Messages msgKey, final PageMsgSeverity severity, final String... msgArgs) {
		messageKey = msgKey;
		int len = msgArgs.length;
		messageList = new String[len];
		for (int i = 0; i < len; i++) {
			messageList[i] = msgArgs[i];
		}
		// getMessage();
		display = true;
		setSeverityFlag(severity);
	}

	public void createMsg(final Messages msgKey, PageMsgSeverity severity) {
		messageKey = msgKey;
		display = true;
		setSeverityFlag(severity);
		message = "";
	}

	public void createMsg(final String msg, PageMsgSeverity severity) {
		message = msg;
		display = true;
		setSeverityFlag(severity);
		message = "";
	}

	public String getMessage() {
		if (messageKey == null && DrtsUtils.isEmpty(message)) {
			return "";
		}

		if (DrtsUtils.isEmpty(message)) {
			message = MessageFormat.format(messageKey.getStringValue(), (Object[]) messageList);
		}
		display = false;
		return message;
	}

	public void clearMessage() {
		messageKey = null;
		messageList = new String[0];
		message = "";
	}

	public boolean isDisplayMsg() {
		return display;
	}

	private void setSeverityFlag(PageMsgSeverity sev) {
		switch (sev) {
			case ERROR:
				errMsg = true;
				warnMsg = false;
				successMsg = false;
				break;
			case WARNING:
				errMsg = false;
				warnMsg = true;
				successMsg = false;
				break;
			case SUCCESS:
				errMsg = false;
				warnMsg = false;
				successMsg = true;
				break;
			case NONE:
			default:
				errMsg = false;
				warnMsg = false;
				successMsg = false;
		}
	}

	public boolean isErrMsg() {
		return errMsg;
	}

	public boolean isWarnMsg() {
		return warnMsg;
	}

	public boolean isSuccessMsg() {
		return successMsg;
	}

}
