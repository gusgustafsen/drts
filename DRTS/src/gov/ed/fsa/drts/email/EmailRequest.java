package gov.ed.fsa.drts.email;

import java.util.Properties;

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

import org.apache.log4j.Logger;

import gov.ed.fsa.drts.exception.DrtsException;
import gov.ed.fsa.drts.process.dataRequest.DataRequestBean;
import gov.ed.fsa.drts.util.ApplicationProperties;

public class EmailRequest {

	Logger logger = Logger.getLogger(EmailRequest.class);

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

		try {
			messageBodyPart.setText("This is message body");
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

}
