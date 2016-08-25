package gov.ed.fsa.drts.util;

import gov.ed.fsa.drts.dataaccess.DataLayer;
import gov.ed.fsa.drts.object.Attachment;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class FileServlet extends HttpServlet {

	private static final long serialVersionUID = 1726560878580768502L;

	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
		throws IOException, ServletException
	{
		DiskFileItemFactory disk_file_item_factory = null;
		ServletFileUpload servlet_file_upload = null;
		String file_name = null;
		String file_id = null;
		FileItem upload_file = null;
		String current_request_id = null;
		String error = null;
		
		try
		{
			disk_file_item_factory = new DiskFileItemFactory();
			
			servlet_file_upload = new ServletFileUpload(disk_file_item_factory);
			
			List<FileItem> items = servlet_file_upload.parseRequest(request);
			
	        for (FileItem item : items)
	        {
	            if(item.isFormField() == false)
	            {
	            	upload_file = item;
	            }
	            else
	            {
	            	String field_name = item.getFieldName();
	                String field_value = item.getString();
	                
	                if(field_name.equalsIgnoreCase("current_request_id") == true)
	                {
	                	current_request_id = field_value;
	                }
	            }
	        }
	        
	        file_id = UUID.randomUUID().toString();
	        file_name = FilenameUtils.getName(upload_file.getName());
	        
	        DataLayer.getInstance().insertAttachment(file_id, current_request_id, upload_file);
	    }
		catch(FileUploadException fue)
		{
			fue.printStackTrace();
	    }
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		final JsonObject json_return_object = new JsonObject();
		json_return_object.addProperty("uploaded_file_name", file_name);
		json_return_object.addProperty("uploaded_file_id", file_id);
		json_return_object.addProperty("error", error);
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(new Gson().toJson(json_return_object));
	}
	
	@Override
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
		throws IOException, ServletException
	{
		String file_id = null;
		Attachment file = null;
		ServletOutputStream output_stream = null;
		
		try
		{
			file_id = request.getParameter("file_id");
			
			if(file_id != null)
			{
				file = DataLayer.getInstance().getAttachmentByID(file_id);
				
				response.setContentType(file.getContentType());
				response.setContentLength((int) file.getSize());
				response.setHeader("Content-disposition", "attachment; filename=\"" + file.getName() + "\"");
				
				output_stream = response.getOutputStream();
				
				output_stream.write(file.getContent(), 0, (int) file.getSize());
				
				output_stream.flush();
				output_stream.close();
			}
			else
			{
				response.getWriter().print("File not found for the id: " + file_id);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
