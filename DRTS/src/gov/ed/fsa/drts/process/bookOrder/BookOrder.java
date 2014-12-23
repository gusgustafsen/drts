//package gov.ed.fsa.drts.process.bookOrder;
//
//import gov.ed.fsa.drts.bean.PageUtil;
//import gov.ed.fsa.drts.object.DRTSTask;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;¢
//
//import javax.annotation.PostConstruct;
//import javax.annotation.PreDestroy;
//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.ViewScoped;
//import javax.faces.context.FacesContext;
//
//import org.activiti.engine.ProcessEngine;
//import org.activiti.engine.ProcessEngines;
//import org.activiti.engine.RuntimeService;
//import org.activiti.engine.TaskService;
//import org.apache.commons.io.FilenameUtils;
//import org.apache.log4j.Logger;
//import org.primefaces.event.FileUploadEvent;
//import org.primefaces.model.UploadedFile;
//
//@ManagedBean(name = "bookOrder")
//@ViewScoped
//public class BookOrder extends PageUtil implements Serializable {
//
//	private static final long serialVersionUID = 4304344782169324573L;
//
//	private static final Logger logger = Logger.getLogger(BookOrder.class);
//	
//	private transient ProcessEngine process_engine = null;
//	private transient RuntimeService runtime_service = null;
//	private transient TaskService task_service = null;
//	
//	private String isbn;
//	private String book_requestor;
//	private boolean approved;
//	
//	private List<UploadedFile> attachments;
//	private List<String> order_attachments;
//	
//	private Map<String, Object> form_variables = new HashMap<String, Object>();
//	
//	private DRTSTask current_task = null;
//	
//	@SuppressWarnings("unchecked")
//	@PostConstruct
//	private void init()
//	{
//		logger.info("init BookOrder ::: " + userSession.getUser().getId());
//		
//		this.process_engine = ProcessEngines.getDefaultProcessEngine();
//		
//		if(this.process_engine != null)
//		{
//			this.runtime_service = this.process_engine.getRuntimeService();
//			
//			if(this.runtime_service == null)
//			{
//				// TODO handle error
//			}
//
//			this.task_service = this.process_engine.getTaskService();
//			
//			if(this.task_service == null)
//			{
//				// TODO handle error
//			}
//		}
//		else
//		{
//			// TODO handle error
//		}
//		
//		this.current_task = (DRTSTask) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("drtsTask");
//		
//		if(this.current_task != null)
//		{
//			Map<String, Object> variables = this.runtime_service.getVariables(this.current_task.getActivitiTask().getProcessInstanceId());
//			
//			if(variables != null)
//			{
//				logger.info("variables not null");
//				
//				Iterator<Map.Entry<String, Object>> it = variables.entrySet().iterator();
//				
//			    while(it.hasNext())
//			    {
//			    	Map.Entry<String, Object> pairs = (Map.Entry<String, Object>) it.next();
//			        logger.info("\t" + pairs.getKey() + " = " + pairs.getValue());
//			        
//			        if(pairs.getKey().equalsIgnoreCase("isbn") && pairs.getValue() != null)
//			        {
//			        	this.isbn = (String) pairs.getValue();
//			        	logger.info("set 1");
//			        }
//			        
//			        if(pairs.getKey().equalsIgnoreCase("book_requestor") && pairs.getValue() != null)
//			        {
//			        	this.book_requestor = (String) pairs.getValue();
//			        	logger.info("set 2");
//			        }
//			        
//			        if(pairs.getKey().equalsIgnoreCase("order_attachments") && pairs.getValue() != null)
//			        {
//			        	this.order_attachments = (List<String>) pairs.getValue();
//			        	logger.info("set 3");
//			        }
//			    }
//			}
//		}
//		
//		this.attachments = new ArrayList<UploadedFile>();
//		this.order_attachments = new ArrayList<String>();
//	}
//	
//	@PreDestroy
//	private void destroy(){}
//	
//	public String getIsbn() {
//  	return isbn;
//  }
//	public void setIsbn(String isbn) {
//  	this.isbn = isbn;
//  }
//	public boolean isApproved() {
//  	return approved;
//  }
//	public void setApproved(boolean approved) {
//  	this.approved = approved;
//  }
//	public String getBookRequestor() {
//	  	return book_requestor;
//	  }
//		public void setBookRequestor(String book_requestor) {
//	  	this.book_requestor = book_requestor;
//	  }
//		public List<UploadedFile> getAttachments() {
//			return attachments;
//		}
//		public void setAttachments(List<UploadedFile> attachments) {
//			this.attachments = attachments;
//		}
//		
//		
//	public void handleFileUpload(FileUploadEvent event)
//	{
//		String filename = FilenameUtils.getName(event.getFile().getFileName());
//		
//		logger.info("Uploaded: " + filename);
//		
//		this.attachments.add(event.getFile());
//	}	
//	
//	public String deleteAttachment(UploadedFile attachment)
//	{
//		this.attachments.remove(attachment);
//		
//		return "";
//	}
//	
//	private void copyFiles()
//	{
//		String destination="C:\\Users\\tima\\Desktop\\";
//		OutputStream out = null;
//		InputStream in = null;
//		int read;
//		byte[] bytes = null;
//		
//		try
//			{
//				for(UploadedFile file : this.attachments)
//				{
//					out = new FileOutputStream(new File(destination + file.getFileName()));
//					in = file.getInputstream();
//					
//					read = 0;
//					bytes = new byte[1024];
//					
//					while((read = in.read(bytes)) != -1)
//					{
//						out.write(bytes, 0, read);
//					}
//	           
//					in.close();
//					out.flush();
//					out.close();
//					
//					this.order_attachments.add("/path/to/files/" + file.getFileName());
//					logger.info("added file: /path/to/files/" + file.getFileName());
//				}
//			}
//			catch(IOException ioe)
//			{
//				logger.info(ioe);
//			}
//	}
//		
//	public String start()
//	{
//		if(this.attachments.size() > 0)
//		{
//			copyFiles();
//		}
//		
//		// TODO handle error of copying files
//		
//		this.form_variables.put("isbn", this.isbn);
//		this.form_variables.put("book_requestor", userSession.getUser().getId());
//		this.form_variables.put("order_attachments", this.order_attachments);
//		
//		this.runtime_service.startProcessInstanceByKey("bookorder", this.form_variables);
//		
//		logger.info("started process bookorder");
//		
//		return "/index?faces-redirect=true";
//	}
//	
//	public String completeOrder()
//	{
//		Map<String, Object> task_variables = new HashMap<String, Object>();
//		task_variables.put("decision_by", "tima2");
//		task_variables.put("approved_denied", this.approved);
//		task_variables.put("recipient_email", "tim.asanov2013@gmail.com");
//		
//		logger.info("approved?: " + this.approved);
//		
//		this.task_service.complete(this.current_task.getId(), task_variables);
//		
//		return "/index?faces-redirect=true";
//	}
//
//}
