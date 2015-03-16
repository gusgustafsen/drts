package gov.ed.fsa.drts.bean;

import gov.ed.fsa.drts.object.Attachment;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.mail.util.ByteArrayDataSource;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

@ManagedBean(name = "emailBean")
@ViewScoped
public class EmailBean extends PageUtil implements Serializable {

	private static final long serialVersionUID = 7226939376868215724L;
	
	private String to = null;
	private String subject = null;
	private String message_content = null;
	
	public String sendEmail(List<Attachment> attachments)
	{
		try
		{
			MultiPartEmail email = new MultiPartEmail();
			
			email.setHostName("smtp.gmail.com");
			email.setSmtpPort(587);
			email.setAuthenticator(new DefaultAuthenticator("tasanov.work@gmail.com", "400ShadyGrove"));
			email.setTLS(true);
			
			email.setFrom("drts@ed.gov");
			email.setSubject(this.subject);
			email.setMsg(this.message_content);
			
			email.addTo(this.to);
			
			if(attachments != null)
			{
				for(Attachment attachment : attachments)
				{
					email.attach(new ByteArrayDataSource(attachment.getContent(), attachment.getContentType()), attachment.getName(), "", EmailAttachment.ATTACHMENT);
				}
			}
			
			email.send();
		}
		catch(EmailException e)
		{
			e.printStackTrace();
		}
		
		return userSession.getHomePage() + "?faces-redirect=true";
	}

	public String getTo()
	{
		return to;
	}

	public void setTo(String to)
	{
		this.to = to;
	}

	public String getSubject()
	{
		return subject;
	}

	public void setSubject(String subject)
	{
		this.subject = subject;
	}

	public String getMessageContent()
	{
		return message_content;
	}

	public void setMessageContent(String message_content)
	{
		this.message_content = message_content;
	}
}
