package gov.ed.fsa.drts.email;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.apache.log4j.Logger;

import gov.ed.fsa.drts.exception.DrtsException;
import gov.ed.fsa.drts.object.Attachment;
import gov.ed.fsa.drts.process.dataRequest.DataRequestBean;
import gov.ed.fsa.drts.util.ApplicationProperties;
import gov.ed.fsa.drts.util.Utils;

public class EmailRequest {

	private static final Logger logger = Logger.getLogger(EmailRequest.class);

	private static final String nl = "\n";

	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	public boolean send(DataRequestBean dataRequest) throws DrtsException {

		Properties props = new Properties();

		props.setProperty("mail.smtp.host", "localhost");
		props.setProperty("mail.smtp.port", "25");

		Session session = Session.getDefaultInstance(props);

		Message message = new MimeMessage(session);

		try {
			message.setFrom(
					new InternetAddress(ApplicationProperties.EMAIL_LABEL_NOTIFY_ADMIN_DRT_FROM.getStringValue()));
		} catch (AddressException e) {
			String error = "Setting from address when emailing request";
			logger.error(message, e);
			throw new DrtsException(error, e);
		} catch (MessagingException e) {
			String error = "Setting from address when emailing request";
			logger.error(message, e);
			throw new DrtsException(error, e);
		}

		try {
			message.setRecipients(RecipientType.TO, InternetAddress.parse(dataRequest.getEmailTo()));
		} catch (MessagingException e) {
			String error = "Setting to address when emailing request";
			logger.error(message, e);
			throw new DrtsException(error, e);
		}

		try {
			message.setSubject("Data Request " + dataRequest.getDisplayId());
		} catch (MessagingException e) {
			String error = "Setting subject when emailing request";
			logger.error(message, e);
			throw new DrtsException(error, e);
		}

		BodyPart messageBodyPart = new MimeBodyPart();

		String content = buildContent(dataRequest);

		try {
			messageBodyPart.setText(content);
		} catch (MessagingException e) {
			String error = "Setting message body when emailing request";
			logger.error(message, e);
			throw new DrtsException(error, e);
		}
		Multipart multipart = new MimeMultipart();

		try {
			multipart.addBodyPart(messageBodyPart);
		} catch (MessagingException e) {
			String error = "Adding message body to multipart mime when emailing request";
			logger.error(message, e);
			throw new DrtsException(error, e);
		}

		for (Attachment attachment : dataRequest.getAttachments()) {
			BodyPart attachmentBodyPart = new MimeBodyPart();
			try {
				attachmentBodyPart.setFileName(attachment.getName());
			} catch (MessagingException e) {
				String error = "Setting attachment file name for " + attachment.getName();
				logger.error(message, e);
				throw new DrtsException(error, e);
			}

			try {
				attachmentBodyPart.setDataHandler(
						new DataHandler(new ByteArrayDataSource(attachment.getContent(), "text/plain")));
			} catch (MessagingException e) {
				String error = "Setting attachment content for " + attachment.getName();
				logger.error(message, e);
				throw new DrtsException(error, e);
			}

			try {
				multipart.addBodyPart(attachmentBodyPart);
			} catch (MessagingException e) {
				String error = "Adding attachment to multipart mime when emailing request";
				logger.error(message, e);
				throw new DrtsException(error, e);
			}
		}

		try {
			message.setContent(multipart);
		} catch (MessagingException e) {
			String error = "Setting multipart mime to message when emailing request";
			logger.error(message, e);
			throw new DrtsException(error, e);
		}

		// Send message
		try {
			Transport.send(message);
		} catch (MessagingException e) {
			String error = "Sending emailing request";
			logger.error(message, e);
			throw new DrtsException(error, e);
		}
		return true; // bmfind
	}

	private String buildContent(DataRequestBean dr) {
		StringBuilder sb = new StringBuilder();
		buildDataElement(sb, "ID", dr.getDisplayId());
		buildDataElement(sb, "Iteration", dr.getIteration());
		buildDataElement(sb, "Status", dr.getStatus());
		buildDataElement(sb, "Created Date", dr.getCreatedDateTime());
		buildDataElement(sb, "Original Request Date", dr.getOriginalRequestDate());
		buildDataElement(sb, "Type", dr.getType());
		buildDataElement(sb, "System", dr.getSystem());
		buildDataElement(sb, "Requested Due Date", dr.getDueDate());
		buildDataElement(sb, "Tier", dr.getTier());
		buildDataElement(sb, "Delay Reason", dr.getDelayReason());
		buildDataElement(sb, "Agreed Due Date", dr.getAgreedDueDate());
		buildDataElement(sb, "Anticipated Due Date", dr.getAnticipatedDueDate());
		buildDataElement(sb, "Date Run", dr.getDateRun());
		buildDataElement(sb, "Report Type", dr.getReportType());
		buildDataElement(sb, "Urgency Flag", dr.isUrgent());
		buildDataElement(sb, "PII Flag", dr.isPii());
		buildDataElement(sb, "Query/Report Name", dr.getQueryReportName());
		buildDataElement(sb, "Related Requests", dr.getRelatedRequests());
		buildDataElement(sb, "Topic/Keywords", dr.getTopicKeywords());
		buildDataElement(sb, "Description", dr.getDescription());
		buildDataElement(sb, "Purpose", dr.getPurpose());
		buildDataElement(sb, "Special Considerations/Issues", dr.getSpecialConsiderationsIssues());
		buildDataElement(sb, "Clarifications/Assumptions", dr.getClarificationsAssumptions());
		buildDataElement(sb, "Golden Query Library", dr.getGoldenQueryLibrary());
		buildDataElement(sb, "Business Requirements", dr.getBusinessRequirements());
		buildDataElement(sb, "Detailed Steps", dr.getDetailedSteps());
		buildDataElement(sb, "Requestor", dr.getRequestorName());
		buildDataElement(sb, "Requestor Organization", dr.getRequestorOrganization());
		buildDataElement(sb, "Requestor Phone", dr.getRequestorPhone());
		buildDataElement(sb, "Requestor Email", dr.getRequestorEmail());
		buildDataElement(sb, "Receiver", dr.getReceiverName());
		buildDataElement(sb, "Receiver Email", dr.getReceiverEmail());
		buildDataElement(sb, "SME to Resolve Request", dr.getAssignedSme());
		buildDataElement(sb, "Date Assigned to SME", dr.getDateAssignedToSme());
		buildDataElement(sb, "SME to Validate the Request", dr.getAssignedValidator());
		buildDataElement(sb, "Validation", dr.getValidationDescription());
		buildDataElement(sb, "Validation Result", dr.getValidationResult());
		buildDataElement(sb, "Date Assigned to Validator", dr.getDateAssignedToValidator());
		buildDataElement(sb, "Date Resolved", dr.getDateResolved());
		buildDataElement(sb, "Date Closed", dr.getDateClosed());
		buildDataElement(sb, "Comments", dr.getComments());

		return sb.toString();
	}

	private void buildDataElement(StringBuilder sb, String label, boolean value) {
		buildDataElement(sb, label, Boolean.toString(value));
	}

	private void buildDataElement(StringBuilder sb, String label, Date value) {
		if (value == null) {
			return;
		}
		buildDataElement(sb, label, DATE_FORMAT.format(value));
	}

	private void buildDataElement(StringBuilder sb, String label, int value) {
		buildDataElement(sb, label, Integer.toString(value));
	}

	private void buildDataElement(StringBuilder sb, String label, String value) {
		if (Utils.isStringEmpty(value)) {
			return;
		}

		sb.append(label).append(nl).append(value).append(nl).append(nl);
	}
}
