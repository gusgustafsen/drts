package gov.ed.fsa.drts.dataaccess;

import gov.ed.fsa.drts.object.Attachment;
import gov.ed.fsa.drts.object.AuditField;
import gov.ed.fsa.drts.object.AverageAgeBean;
import gov.ed.fsa.drts.object.Report2OpenClosedBean;
import gov.ed.fsa.drts.object.Report3AssignedSMEBean;
import gov.ed.fsa.drts.object.overdueReportBean;
import gov.ed.fsa.drts.object.DataRequest;
import gov.ed.fsa.drts.util.ApplicationProperties;
import gov.ed.fsa.drts.util.Utils;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

public class DataLayer {

	private static final Logger logger = Logger.getLogger(DataLayer.class);
	
	private static final String QUERY_INSERT_DATA_REQUEST = "INSERT INTO " + ApplicationProperties.DATA_REQUEST_TABLE.getStringValue() + " ("
																+ "request_number, "
																+ ApplicationProperties.DATA_REQUEST_FIELD_CREATED_DATE_TIME.getStringValue() + ", "
																+ ApplicationProperties.DATA_REQUEST_FIELD_PROCESS_INSTANCE_ID.getStringValue() + ", "
																+ ApplicationProperties.DATA_REQUEST_FIELD_CANDIDATE_GROUP.getStringValue() + ", "
																+ ApplicationProperties.DATA_REQUEST_FIELD_ASSIGNEE.getStringValue() + ", "
																+ ApplicationProperties.DATA_REQUEST_FIELD_TYPE.getStringValue() + ", "
																+ ApplicationProperties.DATA_REQUEST_FIELD_STATUS.getStringValue() + ", "
																+ ApplicationProperties.DATA_REQUEST_FIELD_CREATED_BY.getStringValue() + ", "
																+ ApplicationProperties.DATA_REQUEST_FIELD_ITERATION.getStringValue() + ", "
																+ ApplicationProperties.DATA_REQUEST_FIELD_DUE_DATE.getStringValue() + ", "
																+ ApplicationProperties.DATA_REQUEST_FIELD_URGENT.getStringValue() + ", "
																+ ApplicationProperties.DATA_REQUEST_FIELD_RELATED_REQUESTS.getStringValue() + ", "
																+ ApplicationProperties.DATA_REQUEST_FIELD_TOPIC_KEYWORDS.getStringValue() + ", "
																+ ApplicationProperties.DATA_REQUEST_FIELD_PURPOSE.getStringValue() + ", "
																+ ApplicationProperties.DATA_REQUEST_FIELD_SPECIAL_CONSIDERATIONS_ISSUES.getStringValue() + ", "
																+ ApplicationProperties.DATA_REQUEST_FIELD_DESCRIPTION.getStringValue() + ", "
																+ ApplicationProperties.DATA_REQUEST_FIELD_REQUESTOR_NAME.getStringValue() + ", "
																+ ApplicationProperties.DATA_REQUEST_FIELD_REQUESTOR_ORGANIZATION.getStringValue() + ", "
																+ ApplicationProperties.DATA_REQUEST_FIELD_REQUESTOR_PHONE.getStringValue() + ", "
																+ ApplicationProperties.DATA_REQUEST_FIELD_REQUESTOR_EMAIL.getStringValue() + ", "
																+ ApplicationProperties.DATA_REQUEST_FIELD_RECEIVER_NAME.getStringValue() + ", "
																+ ApplicationProperties.DATA_REQUEST_FIELD_RECEIVER_EMAIL.getStringValue() + ", "
																+ ApplicationProperties.DATA_REQUEST_FIELD_SYSTEM.getStringValue() + ", "
																+ "request_display_id, "
																+ ApplicationProperties.DATA_REQUEST_FIELD_PII_FLAG.getStringValue()
																+ ") "
																+ "VALUES (?, SYSDATE, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	private static final String QUERY_SELECT_DATA_REQUESTS_BY_GROUP_OR_ASSIGNEE = "SELECT * FROM(SELECT T2.*, rownum AS ROW_NUM FROM(SELECT T.* FROM(SELECT * FROM " + ApplicationProperties.DATA_REQUEST_VIEW.getStringValue() + ") T "
																					+ "WHERE " + ApplicationProperties.DATA_REQUEST_FIELD_CANDIDATE_GROUP.getStringValue() + " IN (%s) OR " + ApplicationProperties.DATA_REQUEST_FIELD_ASSIGNEE.getStringValue()
																					+ " = ? ORDER BY %s %s) T2) T3 WHERE ROW_NUM > ?  AND ROW_NUM <= ?";
	
	private static final String QUERY_SELECT_DATA_REQUESTS_BY_GROUP_OR_ASSIGNEE_COUNT = "SELECT COUNT(*) FROM " + ApplicationProperties.DATA_REQUEST_VIEW.getStringValue() + " WHERE " + ApplicationProperties.DATA_REQUEST_FIELD_CANDIDATE_GROUP.getStringValue()
																						+ " IN (%s) OR " + ApplicationProperties.DATA_REQUEST_FIELD_ASSIGNEE.getStringValue() + " = ?";
	
	private static final String QUERY_SELECT_DATA_REQUESTS_BY_CREATOR = "SELECT * FROM(SELECT T2.*, rownum AS ROW_NUM FROM(SELECT T.* FROM(SELECT * FROM " + ApplicationProperties.DATA_REQUEST_VIEW.getStringValue() + ") T "
																			+ "WHERE " + ApplicationProperties.DATA_REQUEST_FIELD_CREATED_BY.getStringValue() + " = ? ORDER BY %s %s) T2) T3 "
																			+ "WHERE ROW_NUM > ?  AND ROW_NUM <= ?";

	private static final String QUERY_SELECT_DATA_REQUESTS_BY_CREATOR_COUNT = "SELECT COUNT(*) FROM " + ApplicationProperties.DATA_REQUEST_TABLE.getStringValue() + " WHERE " + ApplicationProperties.DATA_REQUEST_FIELD_CREATED_BY.getStringValue() + " = ?";
	
	private static final String QUERY_SELECT_DATA_REQUESTS_BY_STATUS = "SELECT * FROM(SELECT T2.*, rownum AS ROW_NUM FROM(SELECT T.* FROM(SELECT * FROM " + ApplicationProperties.DATA_REQUEST_VIEW.getStringValue() + ") T "
																		+ "WHERE " + ApplicationProperties.DATA_REQUEST_FIELD_STATUS.getStringValue() + " = ? ORDER BY %s %s) T2) T3 "
																		+ "WHERE ROW_NUM > ?  AND ROW_NUM <= ?";

	private static final String QUERY_SELECT_DATA_REQUESTS_BY_STATUS_COUNT = "SELECT COUNT(*) FROM " + ApplicationProperties.DATA_REQUEST_TABLE.getStringValue() + " WHERE " + ApplicationProperties.DATA_REQUEST_FIELD_STATUS.getStringValue() + " = ?";
	
	private static final String QUERY_SELECT_DATA_REQUESTS_BY_ASSOCIATION = "SELECT * FROM(SELECT T2.*, rownum AS ROW_NUM FROM(SELECT T.* FROM(SELECT * FROM " + ApplicationProperties.DATA_REQUEST_VIEW.getStringValue() + ") T "
																				+ "WHERE " + ApplicationProperties.DATA_REQUEST_FIELD_ASSIGNED_SME.getStringValue() + " = ? OR " + ApplicationProperties.DATA_REQUEST_FIELD_ASSIGNED_VALIDATOR.getStringValue() + " = ? ORDER BY %s %s) T2) T3 "
																				+ "WHERE ROW_NUM > ?  AND ROW_NUM <= ?";

	private static final String QUERY_SELECT_DATA_REQUESTS_BY_ASSOCIATION_COUNT = "SELECT COUNT(*) FROM " + ApplicationProperties.DATA_REQUEST_TABLE.getStringValue() + " WHERE " + ApplicationProperties.DATA_REQUEST_FIELD_ASSIGNED_SME.getStringValue() + " = ? OR " + ApplicationProperties.DATA_REQUEST_FIELD_ASSIGNED_VALIDATOR.getStringValue() + " = ?";
	
	private static final String QUERY_SELECT_ALL_DATA_REQUESTS = "SELECT * FROM(SELECT T2.*, rownum AS ROW_NUM FROM(SELECT * FROM " + ApplicationProperties.DATA_REQUEST_VIEW.getStringValue() + " ORDER BY %s %s) T2) T3 WHERE ROW_NUM > ?  AND ROW_NUM <= ?";
	
	private static final String QUERY_SELECT_ALL_DATA_REQUESTS_COUNT = "SELECT COUNT(*) FROM " + ApplicationProperties.DATA_REQUEST_TABLE.getStringValue();
	
	private static final String QUERY_UPDATE_DATA_REQUEST = "UPDATE " + ApplicationProperties.DATA_REQUEST_TABLE.getStringValue() + " SET "
															+ ApplicationProperties.DATA_REQUEST_FIELD_CANDIDATE_GROUP.getStringValue() + " = ?, "
															+ ApplicationProperties.DATA_REQUEST_FIELD_ASSIGNEE.getStringValue() + " = ?, "
															+ ApplicationProperties.DATA_REQUEST_FIELD_TYPE.getStringValue() + " = ?, "
															+ ApplicationProperties.DATA_REQUEST_FIELD_STATUS.getStringValue() + " = ?, "
															+ ApplicationProperties.DATA_REQUEST_FIELD_DUE_DATE.getStringValue() + " = ?, "
															+ ApplicationProperties.DATA_REQUEST_FIELD_URGENT.getStringValue() + " = ?, "
															+ ApplicationProperties.DATA_REQUEST_FIELD_RELATED_REQUESTS.getStringValue() + " = ?, "
															+ ApplicationProperties.DATA_REQUEST_FIELD_TOPIC_KEYWORDS.getStringValue() + " = ?, "
															+ ApplicationProperties.DATA_REQUEST_FIELD_PURPOSE.getStringValue() + " = ?, "
															+ ApplicationProperties.DATA_REQUEST_FIELD_SPECIAL_CONSIDERATIONS_ISSUES.getStringValue() + " = ?, "
															+ ApplicationProperties.DATA_REQUEST_FIELD_DESCRIPTION.getStringValue() + " = ?, "
															+ ApplicationProperties.DATA_REQUEST_FIELD_REQUESTOR_NAME.getStringValue() + " = ?, "
															+ ApplicationProperties.DATA_REQUEST_FIELD_REQUESTOR_ORGANIZATION.getStringValue() + " = ?, "
															+ ApplicationProperties.DATA_REQUEST_FIELD_REQUESTOR_PHONE.getStringValue() + " = ?, "
															+ ApplicationProperties.DATA_REQUEST_FIELD_REQUESTOR_EMAIL.getStringValue() + " = ?, "
															+ ApplicationProperties.DATA_REQUEST_FIELD_RECEIVER_NAME.getStringValue() + " = ?, "
															+ ApplicationProperties.DATA_REQUEST_FIELD_RECEIVER_EMAIL.getStringValue() + " = ?, "
															+ ApplicationProperties.DATA_REQUEST_FIELD_ASSIGNED_SME.getStringValue() + " = ?, "
															+ ApplicationProperties.DATA_REQUEST_FIELD_ASSIGNED_TO_SME.getStringValue() + " = ?, "
															+ ApplicationProperties.DATA_REQUEST_FIELD_DATE_RESOLVED.getStringValue() + " = ?, "
															+ ApplicationProperties.DATA_REQUEST_FIELD_ASSIGNED_VALIDATOR.getStringValue() + " = ?, "
															+ ApplicationProperties.DATA_REQUEST_FIELD_ASSIGNED_TO_VALIDATOR.getStringValue() + " = ?, "
															+ ApplicationProperties.DATA_REQUEST_FIELD_DATE_VALIDATED.getStringValue() + " = ?, "
															+ ApplicationProperties.DATA_REQUEST_FIELD_DATE_CLOSED.getStringValue() + " = ?, "
															+ ApplicationProperties.DATA_REQUEST_FIELD_COMMENTS.getStringValue() + " = ?, "
															+ ApplicationProperties.DATA_REQUEST_FIELD_LAST_UPDATED_DATE.getStringValue() + " = SYSDATE, "
															+ ApplicationProperties.DATA_REQUEST_FIELD_SYSTEM.getStringValue() + " = ?, "
															+ ApplicationProperties.DATA_REQUEST_FIELD_PII_FLAG.getStringValue() + " = ? "
															+ "WHERE request_number = ?";
	
	private static final String QUERY_SELECT_NEXT_DATA_REQUEST_ID = "SELECT COALESCE(MAX(request_display_id), 0) + 1 FROM " + ApplicationProperties.DATA_REQUEST_TABLE.getStringValue();
	
	private static final String QUERY_INSERT_ATTACHMENT = "INSERT INTO " + ApplicationProperties.DATA_ATTACHMENT_TABLE.getStringValue() + " ("
															+ ApplicationProperties.ATTACHMENT_FIELD_ID.getStringValue() + ", "
															+ ApplicationProperties.ATTACHMENT_FIELD_REQUEST_ID.getStringValue() + ", "
															+ ApplicationProperties.ATTACHMENT_FIELD_FILE_NAME.getStringValue() + ", "
															+ ApplicationProperties.ATTACHMENT_FIELD_FILE_TYPE.getStringValue() + ", "
															+ ApplicationProperties.ATTACHMENT_FIELD_FILE_SIZE.getStringValue() + ", "
															+ ApplicationProperties.ATTACHMENT_FIELD_FILE_CONTENT.getStringValue()
															+ ") VALUES (?, ?, ?, ?, ?, ?)";
	
	private static final String QUERY_SELECT_ATTACHMENT_BY_ID = "SELECT * FROM " + ApplicationProperties.DATA_ATTACHMENT_TABLE.getStringValue() + " WHERE " + ApplicationProperties.ATTACHMENT_FIELD_ID.getStringValue() + " = ?";
	
	private static final String QUERY_SELECT_ATTACHMENT_BY_REQUEST_ID = "SELECT * FROM " + ApplicationProperties.DATA_ATTACHMENT_TABLE.getStringValue() + " WHERE " + ApplicationProperties.ATTACHMENT_FIELD_REQUEST_ID.getStringValue() + " = ?";
	
	private static final String QUERY_SELECT_NEXT_ITERATION = "SELECT COALESCE(MAX(" + ApplicationProperties.ITERATION_FIELD_ITERATION.getStringValue() + "), 2) FROM " + ApplicationProperties.DATA_ITERATION_TABLE.getStringValue() + " WHERE " + ApplicationProperties.ITERATION_FIELD_PARENT_ID.getStringValue() + " = ?";
	
	private static final String QUERY_INSERT_ITERATION_ASSOCIATION = "INSERT INTO " + ApplicationProperties.DATA_ITERATION_TABLE.getStringValue() + " ("
																		+ ApplicationProperties.ITERATION_FIELD_PARENT_ID.getStringValue() + ", "
																		+ ApplicationProperties.ITERATION_FIELD_ITERATION.getStringValue() + ", "
																		+ ApplicationProperties.ITERATION_FIELD_CHILD_ID.getStringValue()
																		+ ") VALUES (?, ?, ?)";
	
	private static final String QUERY_SELECT_DATA_REQUESTS_BY_DISPLAY_ID = "SELECT * FROM(SELECT T2.*, rownum AS ROW_NUM FROM(SELECT T.* FROM(SELECT * FROM " + ApplicationProperties.DATA_REQUEST_VIEW.getStringValue() + ") T "
																				+ "WHERE SYS_OP_C2C(TO_CHAR(\"" + ApplicationProperties.DATA_REQUEST_FIELD_CREATED_DATE_TIME.getStringValue() + "\",'YYYY')||'-'||TO_CHAR(\"REQUEST_DISPLAY_ID\")||'-')||'D' like ? "
																				+ "ORDER BY %s %s) T2) T3 WHERE ROW_NUM > ?  AND ROW_NUM <= ?";
	
	private static final String QUERY_SELECT_DATA_REQUESTS_BY_DISPLAY_ID_COUNT = "SELECT COUNT(*) FROM " + ApplicationProperties.DATA_REQUEST_TABLE.getStringValue() + " WHERE SYS_OP_C2C(TO_CHAR(\"" + ApplicationProperties.DATA_REQUEST_FIELD_CREATED_DATE_TIME.getStringValue() + "\",'YYYY')||'-'||TO_CHAR(\"REQUEST_DISPLAY_ID\")||'-')||'D' LIKE ?";
	
	private static final String QUERY_SELECT_DATA_REQUESTS_BY_FIELDS = "SELECT * FROM(SELECT T2.*, rownum AS ROW_NUM FROM(SELECT T.* FROM(SELECT * FROM " + ApplicationProperties.DATA_REQUEST_VIEW.getStringValue() + ") T "
																		+ "WHERE %s ORDER BY %s %s) T2) T3 WHERE ROW_NUM > ?  AND ROW_NUM <= ?";
	
	private static final String QUERY_SELECT_DATA_REQUESTS_BY_FIELDS_COUNT = "SELECT COUNT(*) FROM " + ApplicationProperties.DATA_REQUEST_TABLE.getStringValue() + " WHERE %s";
	
	private static final String QUERY_SELECT_DATA_REQUESTS_BY_KEYWORD = "SELECT * FROM(SELECT T2.*, rownum AS ROW_NUM FROM(SELECT T.* FROM(SELECT * FROM " + ApplicationProperties.DATA_REQUEST_VIEW.getStringValue() + ") T "
																			+ "WHERE " + ApplicationProperties.DATA_REQUEST_FIELD_DESCRIPTION.getStringValue() + " LIKE ? OR " + ApplicationProperties.DATA_REQUEST_FIELD_PURPOSE.getStringValue() + " LIKE ? ORDER BY %s %s) T2) T3 "
																			+ "WHERE ROW_NUM > ?  AND ROW_NUM <= ?";

	private static final String QUERY_SELECT_DATA_REQUESTS_BY_KEYWORD_COUNT = "SELECT COUNT(*) FROM " + ApplicationProperties.DATA_REQUEST_TABLE.getStringValue() + " WHERE " + ApplicationProperties.DATA_REQUEST_FIELD_DESCRIPTION.getStringValue() + " LIKE ? OR " 
																				+ ApplicationProperties.DATA_REQUEST_FIELD_PURPOSE.getStringValue() + " LIKE ?";
	
	private static final String QUERY_REPORT_1 = "SELECT * FROM(SELECT T2.*, rownum AS ROW_NUM FROM(SELECT T.* FROM(SELECT * FROM " + ApplicationProperties.DATA_REQUEST_VIEW.getStringValue() + ") T "
													+ "WHERE %s ORDER BY %s %s) T2) T3 WHERE ROW_NUM > ?  AND ROW_NUM <= ?";
	
	private static final String QUERY_REPORT_1_COUNT = "SELECT COUNT(*) FROM " + ApplicationProperties.DATA_REQUEST_TABLE.getStringValue() + " WHERE %s";
	
	private static final String QUERY_INSERT_AUDIT_FIELD = "INSERT INTO " + ApplicationProperties.DATA_AUDIT_TABLE.getStringValue() + " ("
															+ ApplicationProperties.AUDIT_FIELD_ID.getStringValue() + ", "
															+ ApplicationProperties.AUDIT_FIELD_REQUEST_NUMBER.getStringValue() + ", "
															+ ApplicationProperties.AUDIT_FIELD_FIELD_NAME.getStringValue() + ", "
															+ ApplicationProperties.AUDIT_FIELD_OLD_VALUE.getStringValue() + ", "
															+ ApplicationProperties.AUDIT_FIELD_NEW_VALUE.getStringValue() + ", "
															+ ApplicationProperties.AUDIT_FIELD_MODIFIED_DATE.getStringValue() + ", "
															+ ApplicationProperties.AUDIT_FIELD_MODIFIED_BY.getStringValue()
															+ ") VALUES (?, ?, ?, ?, ?, SYSDATE, ?)";
	
	private static final String QUERY_REPORT_2_OPEN_CLOSED = "SELECT * FROM OPEN_CLOSED_REQUESTS";
		
	private static final String QUERY_REPORT_3_ASSIGNED_SME = "SELECT * FROM SME_ASSIGNED_REPORT";
		
	private static final String QUERY_GET_AVERAGE_AGE_REPORT = "select (sysdate-%d) as REPORT_DATE, NUM_OPEN_REQUESTS, TOTAL_AGE, ROUND(((coalesce(TOTAL_AGE,0))/(case NUM_OPEN_REQUESTS when 0 then 1 else NUM_OPEN_REQUESTS end)),0) as AVG_AGE from(select count(REQUEST_NUMBER) as NUM_OPEN_REQUESTS, SUM(trunc(sysdate-%d) - trunc(DRT_REQUEST_DATE)) as TOTAL_AGE from DRTS_HISTORY where DRT_REQUEST_DATE < (sysdate-%d) and ((CLOSED_DATE is null) or (CLOSED_DATE > (sysdate-%d))))";
		
	private static final String QUERY_OVERDUE_REPORT = "select * from OVERDUE_REQUESTS ORDER BY %s %s";
	
	public static DataLayer getInstance()
	{
		DataLayer dl = new DataLayer();
		
		return dl;
	}
	
	public void insertDataRequest(String process_instance_id, Map<String, Object> request_variables, String candidate_group, String assignee)
		throws Exception
	{
		Connection oracle_connection = null;
		int sql_result = 0;
		int next_id = 1;
		
		try 
		{
			oracle_connection = OracleFactory.createConnection();
			
			next_id = getNextDataRequestID();
			
			PreparedStatement prepared_statement = oracle_connection.prepareStatement(QUERY_INSERT_DATA_REQUEST);
			
			// TODO optimize
			prepared_statement.setString(1, (String) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_ID.getStringValue()));
			prepared_statement.setString(2, process_instance_id);
			prepared_statement.setString(3, candidate_group);
			prepared_statement.setString(4, assignee);
			prepared_statement.setString(5, (String) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_TYPE.getStringValue()));
			prepared_statement.setString(6, (String) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_STATUS.getStringValue()));
			prepared_statement.setString(7, (String) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_CREATED_BY.getStringValue()));
			prepared_statement.setInt(8, (Integer) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_ITERATION.getStringValue()));
			if(request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_DUE_DATE.getStringValue()) != null)
			{
				prepared_statement.setDate(9, new java.sql.Date(((Date) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_DUE_DATE.getStringValue())).getTime()));
			}
			else
			{
				prepared_statement.setDate(9, null);
			}
			prepared_statement.setString(10, ((Boolean) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_URGENT.getStringValue())).toString());
			prepared_statement.setString(11, (String) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_RELATED_REQUESTS.getStringValue()));
			prepared_statement.setString(12, (String) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_TOPIC_KEYWORDS.getStringValue()));
			prepared_statement.setString(13, (String) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_PURPOSE.getStringValue()));
			prepared_statement.setString(14, (String) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_SPECIAL_CONSIDERATIONS_ISSUES.getStringValue()));
			prepared_statement.setString(15, (String) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_DESCRIPTION.getStringValue()));
			prepared_statement.setString(16, (String) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_REQUESTOR_NAME.getStringValue()));
			prepared_statement.setString(17, (String) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_REQUESTOR_ORGANIZATION.getStringValue()));
			prepared_statement.setString(18, (String) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_REQUESTOR_PHONE.getStringValue()));
			prepared_statement.setString(19, (String) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_REQUESTOR_EMAIL.getStringValue()));
			prepared_statement.setString(20, (String) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_RECEIVER_NAME.getStringValue()));
			prepared_statement.setString(21, (String) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_RECEIVER_EMAIL.getStringValue()));
			prepared_statement.setString(22, (String) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_SYSTEM.getStringValue()));
			prepared_statement.setInt(23, next_id);
			prepared_statement.setInt(24, ((Boolean) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_PII_FLAG.getStringValue())) ? 1 : 0);
			
			sql_result = prepared_statement.executeUpdate();
			
			if(sql_result != 1)
			{
				logger.error("Insert statement did not insert exactly one row. Inserted: " + sql_result);
				throw new Exception();
			}
		}
		catch(SQLException sqle) 
		{
			logger.error("A SQL exception occured in insertDataRequest().", sqle);
			throw sqle;
		} 
		catch(Exception e) 
		{
			logger.error("An exception occured in insertDataRequest().", e);
			throw e;
		}
		finally
		{
			try 
			{
				if(oracle_connection != null)
				{
					oracle_connection.close();
				}
			}
			catch(SQLException sqle) 
			{
				logger.error("A SQL exception occured while trying to close the connection in insertDataRequest().", sqle);
			}
		}
	}

	public List<DataRequest> getDataRequestsByGroupOrAssignee(String candidate_groups, String assignee, int first_row, int rows_per_page, String sort_field, boolean sort_ascending)
		throws Exception
	{
		List<DataRequest> data_requests = new ArrayList<DataRequest>();
		DataRequest request = null;
		Connection oracle_connection = null;
		ResultSet result_set = null;
		String sort_direction = null;
		String formatted_query = null;
        
        try 
		{
        	sort_direction = sort_ascending ? "ASC" : "DESC";
            formatted_query = String.format(QUERY_SELECT_DATA_REQUESTS_BY_GROUP_OR_ASSIGNEE, candidate_groups, sort_field, sort_direction);
            
			oracle_connection = OracleFactory.createConnection();
			
			PreparedStatement prepared_statement = oracle_connection.prepareStatement(formatted_query);
			prepared_statement.setString(1, assignee);
			prepared_statement.setInt(2, first_row);
			prepared_statement.setInt(3, rows_per_page);
			
			result_set = prepared_statement.executeQuery();
			
			while(result_set.next())
			{
				request = mapRequest(result_set);
				
				data_requests.add(request);
			}
		}
        catch(SQLException sqle) 
		{
			logger.error("A SQL exception occured in getDataRequestsByGroupOrAssignee().", sqle);
			throw sqle;
		} 
		catch(Exception e) 
		{
			logger.error("An exception occured in getDataRequestsByGroupOrAssignee().", e);
			throw e;
		}
		finally
		{
			try 
			{
				if(oracle_connection != null)
				{
					if(result_set != null) 
					{
						result_set.close();
					}
					
					oracle_connection.close();
				}
			}
			catch(SQLException sqle) 
			{
				logger.error("A SQL exception occured while trying to close the connection in getDataRequestsByGroupOrAssignee().", sqle);
			}
		}
        
        if(data_requests.size() > 0)
        {
        	return data_requests;
        }
        
        return null;
	}

	public int getDataRequestsByGroupOrAssigneeCount(String candidate_groups, String assignee)
		throws Exception
	{
		int count = 0;
		Connection oracle_connection = null;
		ResultSet result_set = null;
		String formatted_query = null;
		
		try 
		{
	        formatted_query = String.format(QUERY_SELECT_DATA_REQUESTS_BY_GROUP_OR_ASSIGNEE_COUNT, candidate_groups);
	        	
			oracle_connection = OracleFactory.createConnection();
			
			PreparedStatement prepared_statement = oracle_connection.prepareStatement(formatted_query);
			prepared_statement.setString(1, assignee);
			
			result_set = prepared_statement.executeQuery();
				
			if(result_set.next())
			{
				count = result_set.getInt(1);
			}
		}
		catch(SQLException sqle)
		{
			logger.error("A SQL exception occured in getDataRequestsByGroupOrAssigneeCount().", sqle);
			throw sqle;
		}
		catch(Exception e) 
		{
			logger.error("An exception occured in getDataRequestsByGroupOrAssigneeCount().", e);
			throw e;
		}
		finally
		{
			try 
			{
				if(oracle_connection != null)
				{
					if(result_set != null) 
					{
						result_set.close();
					}
						
					oracle_connection.close();
				}
			}
			catch(SQLException sqle) 
			{
				logger.error("A SQL exception occured while trying to close the connection in getDataRequestsByGroupOrAssigneeCount().", sqle);
			}
		}
		
		return count;
	}

	public List<DataRequest> getDataRequestsByCreator(String creator, int first_row, int rows_per_page, String sort_field, boolean sort_ascending)
		throws Exception
	{
		List<DataRequest> data_requests = new ArrayList<DataRequest>();
		DataRequest request = null;
		Connection oracle_connection = null;
		ResultSet result_set = null;
		String sort_direction = null;
		String formatted_query = null;
	        
		try 
		{
	       	sort_direction = sort_ascending ? "ASC" : "DESC";
	       	formatted_query = String.format(QUERY_SELECT_DATA_REQUESTS_BY_CREATOR, sort_field, sort_direction);
	       	
			oracle_connection = OracleFactory.createConnection();
				
			PreparedStatement prepared_statement = oracle_connection.prepareStatement(formatted_query);
			prepared_statement.setString(1, creator);
			prepared_statement.setInt(2, first_row);
			prepared_statement.setInt(3, rows_per_page);
				
			result_set = prepared_statement.executeQuery();
				
			while(result_set.next())
			{
				request = mapRequest(result_set);
				
				data_requests.add(request);
			}
		}
		catch(SQLException sqle)
		{
			logger.error("A SQL exception occured in getDataRequestsByCreator().", sqle);
			throw sqle;
		} 
		catch(Exception e) 
		{
			logger.error("An exception occured in getDataRequestsByCreator().", e);
			throw e;
		}
		finally
		{
			try 
			{
				if(oracle_connection != null)
				{
					if(result_set != null) 
					{
						result_set.close();
					}
					
					oracle_connection.close();
				}
			}
			catch(SQLException sqle) 
			{
				logger.error("A SQL exception occured while trying to close the connection in getDataRequestsByCreator().", sqle);
			}
		}
		
		if(data_requests.size() > 0)
		{
			return data_requests;
		}
		
		return null;
	}
	
	public int getDataRequestsByCreatorCount(String creator)
		throws Exception
	{
		int count = 0;
		Connection oracle_connection = null;
		ResultSet result_set = null;
			
		try 
		{
			oracle_connection = OracleFactory.createConnection();
				
			PreparedStatement prepared_statement = oracle_connection.prepareStatement(QUERY_SELECT_DATA_REQUESTS_BY_CREATOR_COUNT);
			prepared_statement.setString(1, creator);
				
			result_set = prepared_statement.executeQuery();
				
			if(result_set.next())
			{
				count = result_set.getInt(1);
			}
		}
		catch(SQLException sqle)
		{
			logger.error("A SQL exception occured in getDataRequestsByCreatorCount().", sqle);
			throw sqle;
		}
		catch(Exception e) 
		{
			logger.error("An exception occured in getDataRequestsByCreatorCount().", e);
			throw e;
		}
		finally
		{
			try 
			{
				if(oracle_connection != null)
				{
					if(result_set != null) 
					{
						result_set.close();
					}
							
					oracle_connection.close();
				}
			}
			catch(SQLException sqle) 
			{
				logger.error("A SQL exception occured while trying to close the connection in getDataRequestsByCreatorCount().", sqle);
			}
		}
			
		return count;
	}
	
	public List<DataRequest> getDataRequestsByStatus(String status, int first_row, int rows_per_page, String sort_field, boolean sort_ascending)
		throws Exception
	{
		List<DataRequest> data_requests = new ArrayList<DataRequest>();
		DataRequest request = null;
		Connection oracle_connection = null;
		ResultSet result_set = null;
		String sort_direction = null;
		String formatted_query = null;
		        
		try 
		{
	       	sort_direction = sort_ascending ? "ASC" : "DESC";
	       	formatted_query = String.format(QUERY_SELECT_DATA_REQUESTS_BY_STATUS, sort_field, sort_direction);
	       	
			oracle_connection = OracleFactory.createConnection();
					
			PreparedStatement prepared_statement = oracle_connection.prepareStatement(formatted_query);
			prepared_statement.setString(1, status);
			prepared_statement.setInt(2, first_row);
			prepared_statement.setInt(3, rows_per_page);
				
			result_set = prepared_statement.executeQuery();
					
			while(result_set.next())
			{
				request = mapRequest(result_set);
				
				data_requests.add(request);
			}
		}
		catch(SQLException sqle)
		{
			logger.error("A SQL exception occured in getDataRequestsByStatus().", sqle);
			throw sqle;
		} 
		catch(Exception e) 
		{
			logger.error("An exception occured in getDataRequestsByStatus().", e);
			throw e;
		}
		finally
		{
			try 
			{
				if(oracle_connection != null)
				{
					if(result_set != null) 
					{
						result_set.close();
					}
						
					oracle_connection.close();
				}
			}
			catch(SQLException sqle) 
			{
				logger.error("A SQL exception occured while trying to close the connection in getDataRequestsByStatus().", sqle);
			}
		}
		
		if(data_requests.size() > 0)
		{
			return data_requests;
		}
			
		return null;
	}
		
	public int getDataRequestsByStatusCount(String status)
		throws Exception
	{
		int count = 0;
		Connection oracle_connection = null;
		ResultSet result_set = null;
				
		try 
		{
			oracle_connection = OracleFactory.createConnection();
					
			PreparedStatement prepared_statement = oracle_connection.prepareStatement(QUERY_SELECT_DATA_REQUESTS_BY_STATUS_COUNT);
			prepared_statement.setString(1, status);
					
			result_set = prepared_statement.executeQuery();
					
			if(result_set.next())
			{
				count = result_set.getInt(1);
			}
		}
		catch(SQLException sqle)
		{
			logger.error("A SQL exception occured in getDataRequestsByStatusCount().", sqle);
			throw sqle;
		}
		catch(Exception e) 
		{
			logger.error("An exception occured in getDataRequestsByStatusCount().", e);
			throw e;
		}
		finally
		{
			try 
			{
				if(oracle_connection != null)
				{
					if(result_set != null) 
					{
						result_set.close();
					}
								
					oracle_connection.close();
				}
			}
			catch(SQLException sqle) 
			{
				logger.error("A SQL exception occured while trying to close the connection in getDataRequestsByStatusCount().", sqle);
			}
		}
				
		return count;
	}

	public List<DataRequest> getDataRequestsByAssociation(String user, int first_row, int rows_per_page, String sort_field, boolean sort_ascending)
		throws Exception
	{
		List<DataRequest> data_requests = new ArrayList<DataRequest>();
		DataRequest request = null;
		Connection oracle_connection = null;
		ResultSet result_set = null;
		String sort_direction = null;
		String formatted_query = null;
		        
		try 
		{
	       	sort_direction = sort_ascending ? "ASC" : "DESC";
	       	formatted_query = String.format(QUERY_SELECT_DATA_REQUESTS_BY_ASSOCIATION, sort_field, sort_direction);
	      
			oracle_connection = OracleFactory.createConnection();
					
			PreparedStatement prepared_statement = oracle_connection.prepareStatement(formatted_query);
			prepared_statement.setString(1, user);
			prepared_statement.setString(2, user);
			prepared_statement.setInt(3, first_row);
			prepared_statement.setInt(4, rows_per_page);
				
			result_set = prepared_statement.executeQuery();
					
			while(result_set.next())
			{
				request = mapRequest(result_set);
				
				data_requests.add(request);
			}
		}
		catch(SQLException sqle)
		{
			logger.error("A SQL exception occured in getDataRequestsByAssociation().", sqle);
			throw sqle;
		} 
		catch(Exception e) 
		{
			logger.error("An exception occured in getDataRequestsByAssociation().", e);
			throw e;
		}
		finally
		{
			try 
			{
				if(oracle_connection != null)
				{
					if(result_set != null) 
					{
						result_set.close();
					}
						
					oracle_connection.close();
				}
			}
			catch(SQLException sqle) 
			{
				logger.error("A SQL exception occured while trying to close the connection in getDataRequestsByAssociation().", sqle);
			}
		}
		
		if(data_requests.size() > 0)
		{
			return data_requests;
		}
			
		return null;
	}
		
	public int getDataRequestsByAssociationCount(String user)
		throws Exception
	{
		int count = 0;
		Connection oracle_connection = null;
		ResultSet result_set = null;
				
		try 
		{
			oracle_connection = OracleFactory.createConnection();
					
			PreparedStatement prepared_statement = oracle_connection.prepareStatement(QUERY_SELECT_DATA_REQUESTS_BY_ASSOCIATION_COUNT);
			prepared_statement.setString(1, user);
					
			result_set = prepared_statement.executeQuery();
					
			if(result_set.next())
			{
				count = result_set.getInt(1);
			}
		}
		catch(SQLException sqle)
		{
			logger.error("A SQL exception occured in getDataRequestsByAssociationCount().", sqle);
			throw sqle;
		}
		catch(Exception e) 
		{
			logger.error("An exception occured in getDataRequestsByAssociationCount().", e);
			throw e;
		}
		finally
		{
			try 
			{
				if(oracle_connection != null)
				{
					if(result_set != null) 
					{
						result_set.close();
					}
								
					oracle_connection.close();
				}
			}
			catch(SQLException sqle) 
			{
				logger.error("A SQL exception occured while trying to close the connection in getDataRequestsByAssociationCount().", sqle);
			}
		}
				
		return count;
	}
	
	public List<DataRequest> getAllDataRequests(int first_row, int rows_per_page, String sort_field, boolean sort_ascending)
		throws Exception
	{
		List<DataRequest> data_requests = new ArrayList<DataRequest>();
		DataRequest request = null;
		Connection oracle_connection = null;
		ResultSet result_set = null;
		String sort_direction = null;
		String formatted_query = null;
			        
		try 
		{
			sort_direction = sort_ascending ? "ASC" : "DESC";
			formatted_query = String.format(QUERY_SELECT_ALL_DATA_REQUESTS, sort_field, sort_direction);
			        	
			oracle_connection = OracleFactory.createConnection();
						
			PreparedStatement prepared_statement = oracle_connection.prepareStatement(formatted_query);
			prepared_statement.setInt(1, first_row);
			prepared_statement.setInt(2, rows_per_page);
					
			result_set = prepared_statement.executeQuery();
						
			while(result_set.next())
			{
				request = mapRequest(result_set);
					
				data_requests.add(request);
			}
		}
		catch(SQLException sqle)
		{
			logger.error("A SQL exception occured in getAllDataRequests().", sqle);
			throw sqle;
		} 
		catch(Exception e) 
		{
			logger.error("An exception occured in getAllDataRequests().", e);
			throw e;
		}
		finally
		{
			try 
			{
				if(oracle_connection != null)
				{
					if(result_set != null) 
					{
						result_set.close();
					}
							
					oracle_connection.close();
				}
			}
			catch(SQLException sqle) 
			{
				logger.error("A SQL exception occured while trying to close the connection in getAllDataRequests().", sqle);
			}
		}
			
		if(data_requests.size() > 0)
		{
			return data_requests;
		}
				
		return null;
	}
			
	public int getAllDataRequestsCount()
		throws Exception
	{
		int count = 0;
		Connection oracle_connection = null;
		ResultSet result_set = null;
					
		try 
		{
			oracle_connection = OracleFactory.createConnection();
						
			PreparedStatement prepared_statement = oracle_connection.prepareStatement(QUERY_SELECT_ALL_DATA_REQUESTS_COUNT);
						
			result_set = prepared_statement.executeQuery();
						
			if(result_set.next())
			{
				count = result_set.getInt(1);
			}
		}
		catch(SQLException sqle)
		{
			logger.error("A SQL exception occured in getAllDataRequestsCount().", sqle);
			throw sqle;
		}
		catch(Exception e) 
		{
			logger.error("An exception occured in getAllDataRequestsCount().", e);
			throw e;
		}
		finally
		{
			try 
			{
				if(oracle_connection != null)
				{
					if(result_set != null) 
					{
						result_set.close();
					}
									
					oracle_connection.close();
				}
			}
			catch(SQLException sqle) 
			{
				logger.error("A SQL exception occured while trying to close the connection in getAllDataRequestsCount().", sqle);
			}
		}
					
		return count;
	}
	
	public void updateDataRequest(String request_id, Map<String, Object> request_variables, String candidate_group, String assignee)
		throws Exception
	{
		Connection oracle_connection = null;
		int sql_result = 0;
		
		try 
		{
			oracle_connection = OracleFactory.createConnection();
			
			PreparedStatement prepared_statement = oracle_connection.prepareStatement(QUERY_UPDATE_DATA_REQUEST);
			
			// TODO optimize
			prepared_statement.setString(1, candidate_group);
			prepared_statement.setString(2, assignee);
			prepared_statement.setString(3, (String) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_TYPE.getStringValue()));
			prepared_statement.setString(4, (String) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_STATUS.getStringValue()));
			if(request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_DUE_DATE.getStringValue()) != null)
			{
				prepared_statement.setDate(5, new java.sql.Date(((Date) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_DUE_DATE.getStringValue())).getTime()));
			}
			else
			{
				prepared_statement.setDate(5, null);
			}
			prepared_statement.setString(6, ((Boolean) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_URGENT.getStringValue())).toString());
			prepared_statement.setString(7, (String) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_RELATED_REQUESTS.getStringValue()));
			prepared_statement.setString(8, (String) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_TOPIC_KEYWORDS.getStringValue()));
			prepared_statement.setString(9, (String) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_PURPOSE.getStringValue()));
			prepared_statement.setString(10, (String) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_SPECIAL_CONSIDERATIONS_ISSUES.getStringValue()));
			prepared_statement.setString(11, (String) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_DESCRIPTION.getStringValue()));
			prepared_statement.setString(12, (String) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_REQUESTOR_NAME.getStringValue()));
			prepared_statement.setString(13, (String) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_REQUESTOR_ORGANIZATION.getStringValue()));
			prepared_statement.setString(14, (String) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_REQUESTOR_PHONE.getStringValue()));
			prepared_statement.setString(15, (String) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_REQUESTOR_EMAIL.getStringValue()));
			prepared_statement.setString(16, (String) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_RECEIVER_NAME.getStringValue()));
			prepared_statement.setString(17, (String) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_RECEIVER_EMAIL.getStringValue()));
			prepared_statement.setString(18, (String) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_ASSIGNED_SME.getStringValue()));
			if(request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_ASSIGNED_TO_SME.getStringValue()) != null)
			{
				prepared_statement.setDate(19, new java.sql.Date(((Date) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_ASSIGNED_TO_SME.getStringValue())).getTime()));
			}
			else
			{
				prepared_statement.setDate(19, null);
			}
			if(request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_DATE_CLOSED.getStringValue()) != null)
			{
				prepared_statement.setDate(20, new java.sql.Date(((Date) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_DATE_CLOSED.getStringValue())).getTime()));
			}
			else
			{
				prepared_statement.setDate(20, null);
			}
			prepared_statement.setString(21, (String) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_ASSIGNED_VALIDATOR.getStringValue()));
			if(request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_ASSIGNED_TO_VALIDATOR.getStringValue()) != null)
			{
				prepared_statement.setDate(22, new java.sql.Date(((Date) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_ASSIGNED_TO_VALIDATOR.getStringValue())).getTime()));
			}
			else
			{
				prepared_statement.setDate(22, null);
			}
			if(request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_DATE_VALIDATED.getStringValue()) != null)
			{
				prepared_statement.setDate(23, new java.sql.Date(((Date) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_DATE_VALIDATED.getStringValue())).getTime()));
			}
			else
			{
				prepared_statement.setDate(23, null);
			}
			if(request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_DATE_CLOSED.getStringValue()) != null)
			{
				prepared_statement.setDate(24, new java.sql.Date(((Date) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_DATE_CLOSED.getStringValue())).getTime()));
			}
			else
			{
				prepared_statement.setDate(24, null);
			}
			prepared_statement.setString(25, (String) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_COMMENTS.getStringValue()));
			prepared_statement.setString(26, (String) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_SYSTEM.getStringValue()));
			prepared_statement.setInt(27, ((Boolean) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_PII_FLAG.getStringValue())) ? 1 : 0);
			prepared_statement.setString(28, request_id);
			
			sql_result = prepared_statement.executeUpdate();
			
			if(sql_result != 1)
			{
				logger.error("update statement did not update exactly one row. Updated: " + sql_result);
				throw new Exception();
			}
		}
		catch(SQLException sqle) 
		{
			logger.error("A SQL exception occured in updateDataRequest().", sqle);
			throw sqle;
		} 
		catch(Exception e) 
		{
			logger.error("An exception occured in updateDataRequest().", e);
			throw e;
		}
		finally
		{
			try 
			{
				if(oracle_connection != null)
				{
					oracle_connection.close();
				}
			}
			catch(SQLException sqle) 
			{
				logger.error("A SQL exception occured while trying to close the connection in updateDataRequest().", sqle);
			}
		}
	}

	private int getNextDataRequestID()
		throws Exception
	{
		int result = 1;
		Connection oracle_connection = null;
		ResultSet result_set = null;
		
		try 
		{
	        oracle_connection = OracleFactory.createConnection();
			
			PreparedStatement prepared_statement = oracle_connection.prepareStatement(QUERY_SELECT_NEXT_DATA_REQUEST_ID);
			
			result_set = prepared_statement.executeQuery();
				
			if(result_set.next())
			{
				result = result_set.getInt(1);
			}
		}
		catch(SQLException sqle)
		{
			logger.error("A SQL exception occured in getNextDataRequestID().", sqle);
			throw sqle;
		}
		catch(Exception e) 
		{
			logger.error("An exception occured in getNextDataRequestID().", e);
			throw e;
		}
		finally
		{
			try 
			{
				if(oracle_connection != null)
				{
					if(result_set != null) 
					{
						result_set.close();
					}
						
					oracle_connection.close();
				}
			}
			catch(SQLException sqle) 
			{
				logger.error("A SQL exception occured while trying to close the connection in getNextDataRequestID().", sqle);
			}
		}
		
		return result;
	}
	
	public void insertAttachment(String file_id, String current_request_id, FileItem file)
		throws Exception
	{
		Connection oracle_connection = null;
		int sql_result = 0;
		
		String file_name = null;
		String content_type = null;
		long file_size = 0;
		
		try 
		{
			oracle_connection = OracleFactory.createConnection();
			
			file_name = FilenameUtils.getName(file.getName());
			content_type = file.getContentType();
            file_size = file.getSize();
			
			PreparedStatement prepared_statement = oracle_connection.prepareStatement(QUERY_INSERT_ATTACHMENT);

			prepared_statement.setString(1, file_id);
			prepared_statement.setString(2, current_request_id);
			prepared_statement.setString(3, file_name);
			prepared_statement.setString(4, content_type);
			prepared_statement.setLong(5, file_size);
			prepared_statement.setBinaryStream(6, file.getInputStream(), file_size);
			
			sql_result = prepared_statement.executeUpdate();
			
			if(sql_result != 1)
			{
				logger.error("Insert statement did not insert exactly one row. Inserted: " + sql_result);
				throw new Exception();
			}
		}
		catch(SQLException sqle) 
		{
			logger.error("A SQL exception occured in insertAttachment().", sqle);
			throw sqle;
		} 
		catch(Exception e) 
		{
			logger.error("An exception occured in insertAttachment().", e);
			throw e;
		}
		finally
		{
			try 
			{
				if(oracle_connection != null)
				{
					oracle_connection.close();
				}
			}
			catch(SQLException sqle) 
			{
				logger.error("A SQL exception occured while trying to close the connection in insertAttachment().", sqle);
			}
		}
	}
	
	public Attachment getAttachmentByID(String id)
		throws Exception
	{
		Connection oracle_connection = null;
		ResultSet result_set = null;
		Attachment result = null;
			        
		try 
		{
			oracle_connection = OracleFactory.createConnection();
			
			PreparedStatement prepared_statement = oracle_connection.prepareStatement(QUERY_SELECT_ATTACHMENT_BY_ID);
			prepared_statement.setString(1, id);
			
			result_set = prepared_statement.executeQuery();
			
			if(result_set.next())
			{
				result = mapAttachment(result_set);
			}
		}
		catch(SQLException sqle)
		{
			logger.error("A SQL exception occured in getAttachmentByID().", sqle);
			throw sqle;
		} 
		catch(Exception e) 
		{
			logger.error("An exception occured in getAttachmentByID().", e);
			throw e;
		}
		finally
		{
			try 
			{
				if(oracle_connection != null)
				{
					if(result_set != null) 
					{
						result_set.close();
					}
							
					oracle_connection.close();
				}
			}
			catch(SQLException sqle) 
			{
				logger.error("A SQL exception occured while trying to close the connection in getAttachmentByID().", sqle);
			}
		}
		
		return result;
	}
	
	public List<Attachment> getAttachmentByRequestID(String request_id)
		throws Exception
	{
		Connection oracle_connection = null;
		ResultSet result_set = null;
		List<Attachment> result = new ArrayList<Attachment>();
		Attachment attachment = null;
				        
			try 
			{
				oracle_connection = OracleFactory.createConnection();
				
				PreparedStatement prepared_statement = oracle_connection.prepareStatement(QUERY_SELECT_ATTACHMENT_BY_REQUEST_ID);
				prepared_statement.setString(1, request_id);
				
				result_set = prepared_statement.executeQuery();
				
				while(result_set.next())
				{
					attachment = mapAttachment(result_set);
					
					result.add(attachment);
				}
			}
			catch(SQLException sqle)
			{
				logger.error("A SQL exception occured in getAttachmentByRequestID().", sqle);
				throw sqle;
			} 
			catch(Exception e) 
			{
				logger.error("An exception occured in getAttachmentByRequestID().", e);
				throw e;
			}
			finally
			{
				try 
				{
					if(oracle_connection != null)
					{
						if(result_set != null) 
						{
							result_set.close();
						}
								
						oracle_connection.close();
					}
				}
				catch(SQLException sqle) 
				{
					logger.error("A SQL exception occured while trying to close the connection in getAttachmentByRequestID().", sqle);
				}
			}
			
			if(result.size() > 0)
			{
				return result;
			}
			
			return null;
		}
	
	public int getNextIteration(String request_id)
		throws Exception
	{
		int iteration = 2;
		Connection oracle_connection = null;
		ResultSet result_set = null;
					
		try 
		{
			oracle_connection = OracleFactory.createConnection();
						
			PreparedStatement prepared_statement = oracle_connection.prepareStatement(QUERY_SELECT_NEXT_ITERATION);
			
			prepared_statement.setString(1, request_id);
			
			result_set = prepared_statement.executeQuery();
						
			if(result_set.next())
			{
				iteration = result_set.getInt(1);
			}
		}
		catch(SQLException sqle)
		{
			logger.error("A SQL exception occured in getNextIteration().", sqle);
			throw sqle;
		}
		catch(Exception e) 
		{
			logger.error("An exception occured in getNextIteration().", e);
			throw e;
		}
		finally
		{
			try 
			{
				if(oracle_connection != null)
				{
					if(result_set != null) 
					{
						result_set.close();
					}
									
					oracle_connection.close();
				}
			}
			catch(SQLException sqle) 
			{
				logger.error("A SQL exception occured while trying to close the connection in getNextIteration().", sqle);
			}
		}
					
		return iteration;
	}
	
	public void insertIterationAssociation(String parent_id, int iteration, String child_id)
		throws Exception
	{
		Connection oracle_connection = null;
		int sql_result = 0;
		
		try 
		{
			oracle_connection = OracleFactory.createConnection();
			
			PreparedStatement prepared_statement = oracle_connection.prepareStatement(QUERY_INSERT_ITERATION_ASSOCIATION);

			prepared_statement.setString(1, parent_id);
			prepared_statement.setInt(2, iteration);
			prepared_statement.setString(3, child_id);
			
			sql_result = prepared_statement.executeUpdate();
			
			if(sql_result != 1)
			{
				logger.error("Insert statement did not insert exactly one row. Inserted: " + sql_result);
				throw new Exception();
			}
		}
		catch(SQLException sqle) 
		{
			logger.error("A SQL exception occured in insertIterationAssociation().", sqle);
			throw sqle;
		} 
		catch(Exception e) 
		{
			logger.error("An exception occured in insertIterationAssociation().", e);
			throw e;
		}
		finally
		{
			try 
			{
				if(oracle_connection != null)
				{
					oracle_connection.close();
				}
			}
			catch(SQLException sqle) 
			{
				logger.error("A SQL exception occured while trying to close the connection in insertIterationAssociation().", sqle);
			}
		}
	}
	
	public List<DataRequest> getDataRequestsByDisplayId(String display_id, int first_row, int rows_per_page, String sort_field, boolean sort_ascending)
		throws Exception
	{
		List<DataRequest> data_requests = new ArrayList<DataRequest>();
		DataRequest request = null;
		Connection oracle_connection = null;
		ResultSet result_set = null;
		String sort_direction = null;
		String formatted_query = null;
			        
		try 
		{
			sort_direction = sort_ascending ? "ASC" : "DESC";
			formatted_query = String.format(QUERY_SELECT_DATA_REQUESTS_BY_DISPLAY_ID, sort_field, sort_direction);
			
			oracle_connection = OracleFactory.createConnection();
			
			PreparedStatement prepared_statement = oracle_connection.prepareStatement(formatted_query);
			prepared_statement.setString(1, "%" + display_id + "%");
			prepared_statement.setInt(2, first_row);
			prepared_statement.setInt(3, rows_per_page);
					
			result_set = prepared_statement.executeQuery();
						
			while(result_set.next())
			{
				request = mapRequest(result_set);
					
				data_requests.add(request);
			}
		}
		catch(SQLException sqle)
		{
			logger.error("A SQL exception occured in getDataRequestsByDisplayId().", sqle);
			throw sqle;
		} 
		catch(Exception e) 
		{
			logger.error("An exception occured in getDataRequestsByDisplayId().", e);
			throw e;
		}
		finally
		{
			try 
			{
				if(oracle_connection != null)
				{
					if(result_set != null) 
					{
						result_set.close();
					}
							
					oracle_connection.close();
				}
			}
			catch(SQLException sqle) 
			{
				logger.error("A SQL exception occured while trying to close the connection in getDataRequestsByDisplayId().", sqle);
			}
		}
			
		if(data_requests.size() > 0)
		{
			return data_requests;
		}
				
		return null;
	}
			
	public int getDataRequestsByDisplayIdCount(String display_id)
		throws Exception
	{
		int count = 0;
		Connection oracle_connection = null;
		ResultSet result_set = null;
					
		try 
		{
			oracle_connection = OracleFactory.createConnection();
						
			PreparedStatement prepared_statement = oracle_connection.prepareStatement(QUERY_SELECT_DATA_REQUESTS_BY_DISPLAY_ID_COUNT);
			prepared_statement.setString(1, "%" + display_id + "%");
						
			result_set = prepared_statement.executeQuery();
						
			if(result_set.next())
			{
				count = result_set.getInt(1);
			}
		}
		catch(SQLException sqle)
		{
			logger.error("A SQL exception occured in getDataRequestsByDisplayIdCount().", sqle);
			throw sqle;
		}
		catch(Exception e) 
		{
			logger.error("An exception occured in getDataRequestsByDisplayIdCount().", e);
			throw e;
		}
		finally
		{
			try 
			{
				if(oracle_connection != null)
				{
					if(result_set != null) 
					{
						result_set.close();
					}
									
					oracle_connection.close();
				}
			}
			catch(SQLException sqle) 
			{
				logger.error("A SQL exception occured while trying to close the connection in getDataRequestsByDisplayIdCount().", sqle);
			}
		}
					
		return count;
	}
	
	public List<DataRequest> getDataRequestsByFields(LinkedHashMap<String, String> search_parameters, String date_from, String date_to, int first_row, int rows_per_page, String sort_field, boolean sort_ascending)
		throws Exception
	{
		List<DataRequest> data_requests = new ArrayList<DataRequest>();
		DataRequest request = null;
		Connection oracle_connection = null;
		ResultSet result_set = null;
		String sort_direction = null;
		String formatted_query = null;
		int count = 1;
		boolean date_range = false;
				        
		try 
		{
			if(date_from != null && date_to != null)
			{
				date_range = true;
			}
			
			sort_direction = sort_ascending ? "ASC" : "DESC";
			
			formatted_query = String.format(QUERY_SELECT_DATA_REQUESTS_BY_FIELDS, buildWhereClause(search_parameters, date_range), sort_field, sort_direction);
			
			System.out.println("searching sql: " + formatted_query);
			
			oracle_connection = OracleFactory.createConnection();
			
			PreparedStatement prepared_statement = oracle_connection.prepareStatement(formatted_query);
			
			for(Map.Entry<String, String> entry : search_parameters.entrySet())
			{
				prepared_statement.setString(count, entry.getValue());
				count++;
			}
			
			if(date_range == true)
			{
				prepared_statement.setString(count, date_from);
				prepared_statement.setString((count + 1), date_to);
				prepared_statement.setInt((count + 2), first_row);
				prepared_statement.setInt((count + 3), rows_per_page);
			}
			else
			{
				prepared_statement.setInt(count, first_row);
				prepared_statement.setInt((count + 1), rows_per_page);
			}
			
			result_set = prepared_statement.executeQuery();
							
			while(result_set.next())
			{
				request = mapRequest(result_set);
						
				data_requests.add(request);
			}
		}
		catch(SQLException sqle)
		{
			logger.error("A SQL exception occured in getDataRequestsByFields().", sqle);
			throw sqle;
		} 
		catch(Exception e) 
		{
			logger.error("An exception occured in getDataRequestsByFields().", e);
			throw e;
		}
		finally
		{
			try 
			{
				if(oracle_connection != null)
				{
					if(result_set != null) 
					{
						result_set.close();
					}
								
					oracle_connection.close();
				}
			}
			catch(SQLException sqle) 
			{
				logger.error("A SQL exception occured while trying to close the connection in getDataRequestsByFields().", sqle);
			}
		}
				
		if(data_requests.size() > 0)
		{
			return data_requests;
		}
					
		return null;
	}
				
	public int getDataRequestsByFieldsCount(LinkedHashMap<String, String> search_parameters, String date_from, String date_to)
		throws Exception
	{
		int count = 0;
		Connection oracle_connection = null;
		ResultSet result_set = null;
		String formatted_query = null;
		int q_count = 1;
		boolean date_range = false;
					
		try 
		{
			if(date_from != null && date_to != null)
			{
				date_range = true;
			}
			
			formatted_query = String.format(QUERY_SELECT_DATA_REQUESTS_BY_FIELDS_COUNT, buildWhereClause(search_parameters, date_range));
			
			oracle_connection = OracleFactory.createConnection();
							
			PreparedStatement prepared_statement = oracle_connection.prepareStatement(formatted_query);
			
			for(Map.Entry<String, String> entry : search_parameters.entrySet())
			{
				prepared_statement.setString(q_count, entry.getValue());
				q_count++;
			}
			
			if(date_range == true)
			{
				prepared_statement.setString(q_count, date_from);
				prepared_statement.setString((q_count + 1), date_to);
			}
			
			result_set = prepared_statement.executeQuery();
							
			if(result_set.next())
			{
				count = result_set.getInt(1);
			}
		}
		catch(SQLException sqle)
		{
			logger.error("A SQL exception occured in getDataRequestsByFieldsCount().", sqle);
			throw sqle;
		}
		catch(Exception e) 
		{
			logger.error("An exception occured in getDataRequestsByFieldsCount().", e);
			throw e;
		}
		finally
		{
			try 
			{
				if(oracle_connection != null)
				{
					if(result_set != null) 
					{
						result_set.close();
					}
										
					oracle_connection.close();
				}
			}
			catch(SQLException sqle) 
			{
				logger.error("A SQL exception occured while trying to close the connection in getDataRequestsByFieldsCount().", sqle);
			}
		}
						
		return count;
	}
	
	public List<DataRequest> getDataRequestsByKeyword(String keyword, int first_row, int rows_per_page, String sort_field, boolean sort_ascending)
		throws Exception
	{
		List<DataRequest> data_requests = new ArrayList<DataRequest>();
		DataRequest request = null;
		Connection oracle_connection = null;
		ResultSet result_set = null;
		String sort_direction = null;
		String formatted_query = null;
			        
		try 
		{
			sort_direction = sort_ascending ? "ASC" : "DESC";
			formatted_query = String.format(QUERY_SELECT_DATA_REQUESTS_BY_KEYWORD, sort_field, sort_direction);
			
			oracle_connection = OracleFactory.createConnection();
						
			PreparedStatement prepared_statement = oracle_connection.prepareStatement(formatted_query);
			prepared_statement.setString(1, "%" + keyword + "%");
			prepared_statement.setString(2, "%" + keyword + "%");
			prepared_statement.setInt(3, first_row);
			prepared_statement.setInt(4, rows_per_page);
					
			result_set = prepared_statement.executeQuery();
						
			while(result_set.next())
			{
				request = mapRequest(result_set);
					
				data_requests.add(request);
			}
		}
		catch(SQLException sqle)
		{
			logger.error("A SQL exception occured in getDataRequestsByKeyword().", sqle);
			throw sqle;
		} 
		catch(Exception e) 
		{
			logger.error("An exception occured in getDataRequestsByKeyword().", e);
			throw e;
		}
		finally
		{
			try 
			{
				if(oracle_connection != null)
				{
					if(result_set != null) 
					{
						result_set.close();
					}
							
					oracle_connection.close();
				}
			}
			catch(SQLException sqle) 
			{
				logger.error("A SQL exception occured while trying to close the connection in getDataRequestsByKeyword().", sqle);
			}
		}
			
		if(data_requests.size() > 0)
		{
			return data_requests;
		}
				
		return null;
	}
			
	public int getDataRequestsByKeywordCount(String keyword)
		throws Exception
	{
		int count = 0;
		Connection oracle_connection = null;
		ResultSet result_set = null;
					
		try 
		{
			oracle_connection = OracleFactory.createConnection();
						
			PreparedStatement prepared_statement = oracle_connection.prepareStatement(QUERY_SELECT_DATA_REQUESTS_BY_KEYWORD_COUNT);
			prepared_statement.setString(1, "%" + keyword + "%");
			prepared_statement.setString(2, "%" + keyword + "%");
						
			result_set = prepared_statement.executeQuery();
						
			if(result_set.next())
			{
				count = result_set.getInt(1);
			}
		}
		catch(SQLException sqle)
		{
			logger.error("A SQL exception occured in getDataRequestsByStatusCount().", sqle);
			throw sqle;
		}
		catch(Exception e) 
		{
			logger.error("An exception occured in getDataRequestsByStatusCount().", e);
			throw e;
		}
		finally
		{
			try 
			{
				if(oracle_connection != null)
				{
					if(result_set != null) 
					{
						result_set.close();
					}
									
					oracle_connection.close();
				}
			}
			catch(SQLException sqle) 
			{
				logger.error("A SQL exception occured while trying to close the connection in getDataRequestsByStatusCount().", sqle);
			}
		}
				
		return count;
	}
	
	// TODO redo/optimize
	public List<DataRequest> report1(String display_id, String keyword, LinkedHashMap<String, String> search_parameters, String requested_due_date_from, String requested_due_date_to, String resolved_date_from,
										String resolved_date_to, String updated_date_from, String updated_date_to, int first_row, int rows_per_page, String sort_field, 
										boolean sort_ascending)
		throws Exception
	{
		StringBuilder sb = new StringBuilder();
		
		for(Map.Entry<String, String> entry : search_parameters.entrySet())
		{
			sb.append(entry.getKey() + " = ? AND ");
		}
		
		if(Utils.isStringEmpty(display_id) == false)
		{
			sb.append("SYS_OP_C2C(TO_CHAR(\"" + ApplicationProperties.DATA_REQUEST_FIELD_CREATED_DATE_TIME.getStringValue() + "\",'YYYY')||'-'||TO_CHAR(\"REQUEST_DISPLAY_ID\")||'-')||'D' LIKE ? AND ");
		}
		
		if(Utils.isStringEmpty(keyword) == false)
		{
			sb.append(ApplicationProperties.DATA_REQUEST_FIELD_DESCRIPTION.getStringValue() + " LIKE ? AND ");
		}
		
		if(requested_due_date_from != null && requested_due_date_to != null)
		{
			sb.append(ApplicationProperties.DATA_REQUEST_FIELD_DUE_DATE.getStringValue() + " >= TO_TIMESTAMP(?, 'mm-dd-yyyy') AND ");
			sb.append(ApplicationProperties.DATA_REQUEST_FIELD_DUE_DATE.getStringValue() + " <  TO_TIMESTAMP(?, 'mm-dd-yyyy') AND ");
		}
		
		if(resolved_date_from != null && resolved_date_to != null)
		{
			sb.append(ApplicationProperties.DATA_REQUEST_FIELD_DATE_RESOLVED.getStringValue() + " >= TO_TIMESTAMP(?, 'mm-dd-yyyy') AND ");
			sb.append(ApplicationProperties.DATA_REQUEST_FIELD_DATE_RESOLVED.getStringValue() + " <  TO_TIMESTAMP(?, 'mm-dd-yyyy') AND ");
		}
		
		if(updated_date_from != null && updated_date_to != null)
		{
			sb.append(ApplicationProperties.DATA_REQUEST_FIELD_LAST_UPDATED_DATE.getStringValue() + " >= TO_TIMESTAMP(?, 'mm-dd-yyyy') AND ");
			sb.append(ApplicationProperties.DATA_REQUEST_FIELD_LAST_UPDATED_DATE.getStringValue() + " <  TO_TIMESTAMP(?, 'mm-dd-yyyy') AND ");
		}
		
		sb.delete(sb.length() - 5, sb.length());
		
		
		List<DataRequest> data_requests = new ArrayList<DataRequest>();
		DataRequest request = null;
		Connection oracle_connection = null;
		ResultSet result_set = null;
		String sort_direction = null;
		String formatted_query = null;
		int count = 1;
		
		try 
		{
			sort_direction = sort_ascending ? "ASC" : "DESC";
			formatted_query = String.format(QUERY_REPORT_1, sb.toString(), sort_field, sort_direction);
			
			System.out.println("formatted: " + formatted_query);
			
			oracle_connection = OracleFactory.createConnection();
			
			PreparedStatement prepared_statement = oracle_connection.prepareStatement(formatted_query);
			
			for(Map.Entry<String, String> entry : search_parameters.entrySet())
			{
				System.out.println("set count: " + count + ", to: " + entry.getValue());
				prepared_statement.setString(count, entry.getValue());
				count++;
			}
			
			if(Utils.isStringEmpty(display_id) == false)
			{
				System.out.println("set count: " + count + ", to: " + display_id);
				prepared_statement.setString(count, "%" + display_id + "%");
				count++;
			}
			
			if(Utils.isStringEmpty(keyword) == false)
			{
				System.out.println("set count: " + count + ", to: " + keyword);
				prepared_statement.setString(count, "%" + keyword + "%");
				count++;
			}
			
			if(requested_due_date_from != null && requested_due_date_to != null)
			{
				System.out.println("set count: " + count + ", to: " + requested_due_date_from);
				prepared_statement.setString(count, requested_due_date_from);
				count++;
				System.out.println("set count: " + count + ", to: " + requested_due_date_to);
				prepared_statement.setString(count, requested_due_date_to);
				count++;
			}
			
			if(resolved_date_from != null && resolved_date_to != null)
			{
				System.out.println("set count: " + count + ", to: " + resolved_date_from);
				prepared_statement.setString(count, resolved_date_from);
				count++;
				System.out.println("set count: " + count + ", to: " + resolved_date_to);
				prepared_statement.setString(count, resolved_date_to);
				count++;
			}
			
			if(updated_date_from != null && updated_date_to != null)
			{
				System.out.println("set count: " + count + ", to: " + updated_date_from);
				prepared_statement.setString(count, updated_date_from);
				count++;
				System.out.println("set count: " + count + ", to: " + updated_date_to);
				prepared_statement.setString(count, updated_date_to);
				count++;
			}
			
			System.out.println("set count: " + count + ", to: " + first_row);
			prepared_statement.setInt(count, first_row);
			count++;
			System.out.println("set count: " + count + ", to: " + rows_per_page);
			prepared_statement.setInt(count, rows_per_page);
			
			result_set = prepared_statement.executeQuery();
			
			while(result_set.next())
			{
				request = mapRequest(result_set);
						
				data_requests.add(request);
			}
		}
		catch(SQLException sqle)
		{
			logger.error("A SQL exception occured in report1().", sqle);
			throw sqle;
		} 
		catch(Exception e) 
		{
			logger.error("An exception occured in report1().", e);
			throw e;
		}
		finally
		{
			try 
			{
				if(oracle_connection != null)
				{
					if(result_set != null) 
					{
						result_set.close();
					}
								
					oracle_connection.close();
				}
			}
			catch(SQLException sqle) 
			{
				logger.error("A SQL exception occured while trying to close the connection in report1().", sqle);
			}
		}
				
		if(data_requests.size() > 0)
		{
			return data_requests;
		}
					
		return null;
	}
	
	public int report1count(String display_id, String keyword, LinkedHashMap<String, String> search_parameters, String requested_due_date_from, String requested_due_date_to, String resolved_date_from,
								String resolved_date_to, String updated_date_from, String updated_date_to)
		throws Exception
	{
		int count = 0;
		Connection oracle_connection = null;
		ResultSet result_set = null;
		String formatted_query = null;
		int q_count = 1;
		
		StringBuilder sb = new StringBuilder();
		
		for(Map.Entry<String, String> entry : search_parameters.entrySet())
		{
			sb.append(entry.getKey() + " = ? AND ");
		}
		
		if(Utils.isStringEmpty(display_id) == false)
		{
			sb.append("SYS_OP_C2C(TO_CHAR(\"" + ApplicationProperties.DATA_REQUEST_FIELD_CREATED_DATE_TIME.getStringValue() + "\",'YYYY')||'-'||TO_CHAR(\"REQUEST_DISPLAY_ID\")||'-')||'D' LIKE ? AND ");
		}
		
		if(Utils.isStringEmpty(keyword) == false)
		{
			sb.append(ApplicationProperties.DATA_REQUEST_FIELD_DESCRIPTION.getStringValue() + " LIKE ? AND ");
		}
		
		if(requested_due_date_from != null && requested_due_date_to != null)
		{
			sb.append(ApplicationProperties.DATA_REQUEST_FIELD_DUE_DATE.getStringValue() + " >= TO_TIMESTAMP(?, 'mm-dd-yyyy') AND ");
			sb.append(ApplicationProperties.DATA_REQUEST_FIELD_DUE_DATE.getStringValue() + " <  TO_TIMESTAMP(?, 'mm-dd-yyyy') AND ");
		}
		
		if(resolved_date_from != null && resolved_date_to != null)
		{
			sb.append(ApplicationProperties.DATA_REQUEST_FIELD_DATE_RESOLVED.getStringValue() + " >= TO_TIMESTAMP(?, 'mm-dd-yyyy') AND ");
			sb.append(ApplicationProperties.DATA_REQUEST_FIELD_DATE_RESOLVED.getStringValue() + " <  TO_TIMESTAMP(?, 'mm-dd-yyyy') AND ");
		}
		
		if(updated_date_from != null && updated_date_to != null)
		{
			sb.append(ApplicationProperties.DATA_REQUEST_FIELD_LAST_UPDATED_DATE.getStringValue() + " >= TO_TIMESTAMP(?, 'mm-dd-yyyy') AND ");
			sb.append(ApplicationProperties.DATA_REQUEST_FIELD_LAST_UPDATED_DATE.getStringValue() + " <  TO_TIMESTAMP(?, 'mm-dd-yyyy') AND ");
		}
		
		sb.delete(sb.length() - 5, sb.length());
		
		try 
		{
			formatted_query = String.format(QUERY_REPORT_1_COUNT, sb.toString());
			
			oracle_connection = OracleFactory.createConnection();
						
			PreparedStatement prepared_statement = oracle_connection.prepareStatement(formatted_query);
			
			for(Map.Entry<String, String> entry : search_parameters.entrySet())
			{
				prepared_statement.setString(q_count, entry.getValue());
				q_count++;
			}
			
			if(Utils.isStringEmpty(display_id) == false)
			{
				prepared_statement.setString(q_count, "%" + display_id + "%");
				q_count++;
			}
			
			if(Utils.isStringEmpty(keyword) == false)
			{
				prepared_statement.setString(q_count, "%" + keyword + "%");
				q_count++;
			}
			
			if(requested_due_date_from != null && requested_due_date_to != null)
			{
				prepared_statement.setString(q_count, requested_due_date_from);
				q_count++;
				prepared_statement.setString(q_count, requested_due_date_to);
				q_count++;
			}
			
			if(resolved_date_from != null && resolved_date_to != null)
			{
				prepared_statement.setString(q_count, resolved_date_from);
				q_count++;
				prepared_statement.setString(q_count, resolved_date_to);
				q_count++;
			}
			
			if(updated_date_from != null && updated_date_to != null)
			{
				prepared_statement.setString(q_count, updated_date_from);
				q_count++;
				prepared_statement.setString(q_count, updated_date_to);
				q_count++;
			}
			
			result_set = prepared_statement.executeQuery();
			
			if(result_set.next())
			{
				count = result_set.getInt(1);
			}
		}
		catch(SQLException sqle)
		{
			logger.error("A SQL exception occured in report1count().", sqle);
			throw sqle;
		}
		catch(Exception e) 
		{
			logger.error("An exception occured in report1count().", e);
			throw e;
		}
		finally
		{
			try 
			{
				if(oracle_connection != null)
				{
					if(result_set != null) 
					{
						result_set.close();
					}
									
					oracle_connection.close();
				}
			}
			catch(SQLException sqle) 
			{
				logger.error("A SQL exception occured while trying to close the connection in report1count().", sqle);
			}
		}
				
		return count;
	}
	
	public void insertAuditFields(List<AuditField> fields)
	{
		Connection oracle_connection = null;
		int sql_result;
		
		try 
		{
			oracle_connection = OracleFactory.createConnection();
			
			PreparedStatement prepared_statement = null;
			
			for(AuditField field : fields)
			{
				sql_result = 0;
				
				prepared_statement = oracle_connection.prepareStatement(QUERY_INSERT_AUDIT_FIELD);
				
				prepared_statement.setString(1, field.getId());
				prepared_statement.setString(2, field.getRequestId());
				prepared_statement.setString(3, field.getColumnName());
				prepared_statement.setString(4, field.getOldValue());
				prepared_statement.setString(5, field.getNewValue());
				prepared_statement.setString(6, field.getModifiedBy());
				
				sql_result = prepared_statement.executeUpdate();
				
				if(sql_result != 1)
				{
					logger.error("Insert statement did not insert exactly one row. Inserted: " + sql_result);
					throw new Exception();
				}
			}
		}
		catch(SQLException sqle) 
		{
			logger.error("A SQL exception occured in insertAuditFields().", sqle);
		} 
		catch(Exception e) 
		{
			logger.error("An exception occured in insertAuditFields().", e);
		}
		finally
		{
			try 
			{
				if(oracle_connection != null)
				{
					oracle_connection.close();
				}
			}
			catch(SQLException sqle) 
			{
				logger.error("A SQL exception occured while trying to close the connection in insertAuditFields().", sqle);
			}
		}
	}
	
	private String buildWhereClause(LinkedHashMap<String, String> search_parameters, boolean date_range)
	{
		StringBuilder where_clause = new StringBuilder();
		
		for(Map.Entry<String, String> entry : search_parameters.entrySet())
		{
			where_clause.append(entry.getKey() + " = ? AND ");
		}
		
		if(date_range == true)
		{
			where_clause.append(ApplicationProperties.DATA_REQUEST_FIELD_CREATED_DATE_TIME.getStringValue() + " >= TO_TIMESTAMP(?, 'mm-dd-yyyy') AND ");
			where_clause.append(ApplicationProperties.DATA_REQUEST_FIELD_CREATED_DATE_TIME.getStringValue() + " <  TO_TIMESTAMP(?, 'mm-dd-yyyy') AND ");
		}
		
		where_clause.delete(where_clause.length() - 5, where_clause.length());
		
		return where_clause.toString();
	}
	
	private Attachment mapAttachment(ResultSet result_set)
		throws SQLException
	{
		Attachment attachment = new Attachment();
		attachment.setId(result_set.getString(ApplicationProperties.ATTACHMENT_FIELD_ID.getStringValue()));
		attachment.setName(result_set.getString(ApplicationProperties.ATTACHMENT_FIELD_FILE_NAME.getStringValue()));
		attachment.setContentType(result_set.getString(ApplicationProperties.ATTACHMENT_FIELD_FILE_TYPE.getStringValue()));
		attachment.setSize(result_set.getLong(ApplicationProperties.ATTACHMENT_FIELD_FILE_SIZE.getStringValue()));
		
		Blob content = result_set.getBlob(ApplicationProperties.ATTACHMENT_FIELD_FILE_CONTENT.getStringValue());
		int blob_length = (int) content.length();  
		byte[] blob_bytes = content.getBytes(1, blob_length);
		
		attachment.setContent(blob_bytes);
		
		return attachment;
	}
	
	private DataRequest mapRequest(ResultSet result_set)
		throws SQLException
	{
		DataRequest request = new DataRequest();
		request.setId(result_set.getString("REQUEST_NUMBER"));
		request.setCandidateGroup(result_set.getString(ApplicationProperties.DATA_REQUEST_FIELD_CANDIDATE_GROUP.getStringValue()));
		request.setAssignee(result_set.getString(ApplicationProperties.DATA_REQUEST_FIELD_ASSIGNEE.getStringValue()));
		request.setCreatedDateTime(result_set.getDate(ApplicationProperties.DATA_REQUEST_FIELD_CREATED_DATE_TIME.getStringValue()));
		request.setDrtsRequestor(result_set.getString(ApplicationProperties.DATA_REQUEST_FIELD_CREATED_BY.getStringValue()));
		request.setStatus(result_set.getString(ApplicationProperties.DATA_REQUEST_FIELD_STATUS.getStringValue()));
		request.setType(result_set.getString(ApplicationProperties.DATA_REQUEST_FIELD_TYPE.getStringValue()));
		request.setIteration(result_set.getInt(ApplicationProperties.DATA_REQUEST_FIELD_ITERATION.getStringValue()));
		request.setDueDate(result_set.getDate(ApplicationProperties.DATA_REQUEST_FIELD_DUE_DATE.getStringValue()));
		request.setUrgent(Boolean.parseBoolean(result_set.getString(ApplicationProperties.DATA_REQUEST_FIELD_URGENT.getStringValue())));
		request.setRelatedRequests(result_set.getString(ApplicationProperties.DATA_REQUEST_FIELD_RELATED_REQUESTS.getStringValue()));
		request.setTopicKeywords(result_set.getString(ApplicationProperties.DATA_REQUEST_FIELD_TOPIC_KEYWORDS.getStringValue()));
		request.setDescription(result_set.getString(ApplicationProperties.DATA_REQUEST_FIELD_DESCRIPTION.getStringValue()));
		request.setPurpose(result_set.getString(ApplicationProperties.DATA_REQUEST_FIELD_PURPOSE.getStringValue()));
		request.setSpecialConsiderationsIssues(result_set.getString(ApplicationProperties.DATA_REQUEST_FIELD_SPECIAL_CONSIDERATIONS_ISSUES.getStringValue()));
		request.setRequestorName(result_set.getString(ApplicationProperties.DATA_REQUEST_FIELD_REQUESTOR_NAME.getStringValue()));
		request.setRequestorOrganization(result_set.getString(ApplicationProperties.DATA_REQUEST_FIELD_REQUESTOR_ORGANIZATION.getStringValue()));
		request.setRequestorPhone(result_set.getString(ApplicationProperties.DATA_REQUEST_FIELD_REQUESTOR_PHONE.getStringValue()));
		request.setRequestorEmail(result_set.getString(ApplicationProperties.DATA_REQUEST_FIELD_REQUESTOR_EMAIL.getStringValue()));
		request.setReceiverName(result_set.getString(ApplicationProperties.DATA_REQUEST_FIELD_RECEIVER_NAME.getStringValue()));
		request.setReceiverEmail(result_set.getString(ApplicationProperties.DATA_REQUEST_FIELD_RECEIVER_EMAIL.getStringValue()));
		request.setAssignedSme(result_set.getString(ApplicationProperties.DATA_REQUEST_FIELD_ASSIGNED_SME.getStringValue()));
		request.setDateAssignedToSme(result_set.getDate(ApplicationProperties.DATA_REQUEST_FIELD_ASSIGNED_TO_SME.getStringValue()));
		request.setDateResolved(result_set.getDate(ApplicationProperties.DATA_REQUEST_FIELD_DATE_RESOLVED.getStringValue()));
		request.setCurrentTaskId(result_set.getString("CURRENT_TASK_ID"));
		request.setCurrentTaskName(result_set.getString("CURRENT_TASK_NAME"));
		request.setCurrentTaskFormKey(result_set.getString("CURRENT_TASK_FORM_KEY"));
		request.setDisplayId(result_set.getInt("REQUEST_DISPLAY_ID"));
		request.setAssignedValidator(result_set.getString(ApplicationProperties.DATA_REQUEST_FIELD_ASSIGNED_VALIDATOR.getStringValue()));
		request.setDateAssignedToValidator(result_set.getDate(ApplicationProperties.DATA_REQUEST_FIELD_ASSIGNED_TO_VALIDATOR.getStringValue()));
		request.setDateValidated(result_set.getDate(ApplicationProperties.DATA_REQUEST_FIELD_DATE_VALIDATED.getStringValue()));
		request.setDateClosed(result_set.getDate(ApplicationProperties.DATA_REQUEST_FIELD_DATE_CLOSED.getStringValue()));
		request.setComments(result_set.getString(ApplicationProperties.DATA_REQUEST_FIELD_COMMENTS.getStringValue()));
		request.setLastUpdatedDate(result_set.getDate(ApplicationProperties.DATA_REQUEST_FIELD_LAST_UPDATED_DATE.getStringValue()));
		request.setPiiFlag(result_set.getInt(ApplicationProperties.DATA_REQUEST_FIELD_PII_FLAG.getStringValue()) != 0);
		request.setSystem(result_set.getString(ApplicationProperties.DATA_REQUEST_FIELD_SYSTEM.getStringValue()));
		
		return request;
	}
	
	public List<Report2OpenClosedBean> getOpenClosedReqReport()
		throws Exception
	{
		List<Report2OpenClosedBean> rows = new ArrayList<Report2OpenClosedBean>();
		Report2OpenClosedBean row = null;
		Connection oracle_connection = null;
		ResultSet result_set = null;
				        
		try 
		{
			oracle_connection = OracleFactory.createConnection();
			PreparedStatement preparedStatement = oracle_connection.prepareStatement(QUERY_REPORT_2_OPEN_CLOSED);
			
			result_set = preparedStatement.executeQuery();			
				
			while(result_set.next())
			{
				row = new Report2OpenClosedBean();
				row.setReportDate(result_set.getDate("DATE_DAY"));
				row.setOpenedRequests(result_set.getInt("OPENED_REQS"));
				row.setClosedRequests(result_set.getInt("CLOSED_REQS"));
				
				rows.add(row);
			}
		}
		catch(SQLException sqle)
		{
			logger.error("A SQL exception occured in getOpenClosedReqReport().", sqle);
			throw sqle;
		} 
		catch(Exception e) 
		{
			logger.error("An exception occured in getOpenClosedReqReport().", e);
			throw e;
		}
		finally
		{
			try 
			{
				if(oracle_connection != null)
				{
					if(result_set != null) 
					{
						result_set.close();
					}	
					
					oracle_connection.close();
				}
			}
			catch(SQLException sqle) 
			{
				logger.error("A SQL exception occured while trying to close the connection in getOpenClosedReqReport().", sqle);
			}
		}
				
		if(rows.size() > 0)
		{
			return rows;
		}
		
		return null;
	}
	
	public List<Report3AssignedSMEBean> getAssignedSmeReport()
		throws Exception
	{
		List<Report3AssignedSMEBean> rows = new ArrayList<Report3AssignedSMEBean>();
		Report3AssignedSMEBean row = null;
		Connection oracle_connection = null;
		ResultSet result_set = null;
				        
		try 
		{   	
			oracle_connection = OracleFactory.createConnection();		
			PreparedStatement preparedStatement = oracle_connection.prepareStatement(QUERY_REPORT_3_ASSIGNED_SME);
						
			result_set = preparedStatement.executeQuery();
				
			while(result_set.next())
			{
				row = new Report3AssignedSMEBean();
				row.setName(result_set.getString("SME"));
				row.setSmeCount(result_set.getInt("SMECNT"));
				row.setValidatorCount(result_set.getInt("VALSMECNT"));
					
				rows.add(row);
			}
		}
		catch(SQLException sqle)
		{
			logger.error("A SQL exception occured in getAssignedSmeReport().", sqle);
			throw sqle;
		} 
		catch(Exception e) 
		{
			logger.error("An exception occured in getAssignedSmeReport().", e);
			throw e;
		}
		finally
		{
			try 
			{
				if(oracle_connection != null)
				{
					if(result_set != null) 
					{
						result_set.close();
					}	
					
					oracle_connection.close();
				}
			}
			catch(SQLException sqle) 
			{
				logger.error("A SQL exception occured while trying to close the connection in getAssignedSmeReport().", sqle);
			}
		}
				
		if(rows.size() > 0)
		{
			return rows;
		}		
			
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// *** Added by Denis for Reports ***
	
		// Get Assigned SME beans
//		public List<assignedSmeBean> getAssignedSmeReport(String sortField, boolean sortAsc)
//				throws Exception
//			{
//				List<assignedSmeBean> beans = new ArrayList<assignedSmeBean>();
//				
//				Connection con = null;
//				ResultSet resultSet = null;
//				String sortDir = null;
//				String formattedQuery = null;
//					        
//				try 
//				{
//					sortDir = sortAsc ? "ASC" : "DESC";
//					formattedQuery = String.format(QUERY_GET_SME_ASSIGNED_REPORT, sortField, sortDir);
//					        	
//					con = OracleFactory.createConnection();		
//					PreparedStatement preparedStatement = con.prepareStatement(formattedQuery);
//							
//					resultSet = preparedStatement.executeQuery();			
//					while(resultSet.next())
//					{
//						assignedSmeBean bean = new assignedSmeBean();
//						bean.setName(resultSet.getString("SME"));
//						bean.setSmeCount(resultSet.getInt("SMECNT"));
//						bean.setValidatorCount(resultSet.getInt("VALSMECNT"));
//						bean.setTotalCount(resultSet.getInt("TOTAL"));
//						beans.add(bean);
//					}
//				}
//				catch(SQLException sqle)
//				{
//					logger.error("A SQL exception occured in getAssignedSmeReport().", sqle);
//					throw sqle;
//				} 
//				catch(Exception e) 
//				{
//					logger.error("An exception occured in getAssignedSmeReport().", e);
//					throw e;
//				}
//				finally
//				{
//					try 
//					{
//						if(con != null)
//						{
//							if(resultSet != null) 
//							{
//								resultSet.close();
//							}	
//							con.close();
//						}
//					}
//					catch(SQLException sqle) 
//					{
//						logger.error("A SQL exception occured while trying to close the connection in getAssignedSmeReport().", sqle);
//					}
//				}
//					
//				if(beans.size() > 0)
//				{
//					return beans;
//				}		
//				return null;
//			}

		
		
		// Get Average Age Report beans
		public List<AverageAgeBean> getAverageAgeReport(int iDay)
				throws Exception
			{
				List<AverageAgeBean> beans = new ArrayList<AverageAgeBean>();
				
				Connection con = null;
				ResultSet resultSet = null;
				String formattedQuery = null;
					        
				try 
				{
					formattedQuery = String.format(QUERY_GET_AVERAGE_AGE_REPORT, iDay, iDay, iDay, iDay);
					        	
					con = OracleFactory.createConnection();		
					PreparedStatement preparedStatement = con.prepareStatement(formattedQuery);
							
					resultSet = preparedStatement.executeQuery();			
					if(resultSet.next())
					{
						AverageAgeBean bean = new AverageAgeBean();
						bean.setReportDate(resultSet.getDate("REPORT_DATE"));
						bean.setRequestNumber(resultSet.getInt("NUM_OPEN_REQUESTS"));
						bean.setTotalAge(resultSet.getInt("TOTAL_AGE"));
						bean.setAverageAge(resultSet.getInt("AVG_AGE"));
						beans.add(bean);
					}
				}
				catch(SQLException sqle)
				{
					logger.error("A SQL exception occured in getAverageAgeReport().", sqle);
					throw sqle;
				} 
				catch(Exception e) 
				{
					logger.error("An exception occured in getAverageAgeReport().", e);
					throw e;
				}
				finally
				{
					try 
					{
						if(con != null)
						{
							if(resultSet != null) 
							{
								resultSet.close();
							}	
							con.close();
						}
					}
					catch(SQLException sqle) 
					{
						logger.error("A SQL exception occured while trying to close the connection in getAverageAgeReport().", sqle);
					}
				}
					
				if(beans.size() > 0)
				{
					return beans;
				}		
				return null;
			}		

		// Get Overdue Request beans
				public List<overdueReportBean> getOverdueReport(String sortField, boolean sortAsc)
						throws Exception
					{
						List<overdueReportBean> beans = new ArrayList<overdueReportBean>();
						
						Connection con = null;
						ResultSet resultSet = null;
						String sortDir = null;
						String formattedQuery = null;
							        
						try 
						{
							sortDir = sortAsc ? "ASC" : "DESC";
							formattedQuery = String.format(QUERY_OVERDUE_REPORT, sortField, sortDir);
							        	
							con = OracleFactory.createConnection();		
							PreparedStatement preparedStatement = con.prepareStatement(formattedQuery);
									
							resultSet = preparedStatement.executeQuery();			
							while(resultSet.next())
							{
								overdueReportBean bean = new overdueReportBean();
								bean.setTrackingNumber(resultSet.getString("TRACKING_NUMBER"));
								bean.setDescription(resultSet.getString("REQUEST_DESCR"));
								bean.setRequestorName(resultSet.getString("REQUESTOR_NAME"));
								bean.setStatus(resultSet.getString("REQUEST_STATUS"));
								bean.setUrgencyFlag(resultSet.getString("URGENCY_FLAG"));
								bean.setRequestedDueDate(resultSet.getDate("REQUESTED_DUE_DATE"));
								bean.setSme(resultSet.getString("SME"));
								bean.setRequestType(resultSet.getString("REQUEST_TYPE"));
								bean.setSystemName(resultSet.getString("SYSTEM_NAME"));
								beans.add(bean);
							}
						}
						catch(SQLException sqle)
						{
							logger.error("A SQL exception occured in getOverdueReport().", sqle);
							throw sqle;
						} 
						catch(Exception e) 
						{
							logger.error("An exception occured in getOverdueReport().", e);
							throw e;
						}
						finally
						{
							try 
							{
								if(con != null)
								{
									if(resultSet != null) 
									{
										resultSet.close();
									}	
									con.close();
								}
							}
							catch(SQLException sqle) 
							{
								logger.error("A SQL exception occured while trying to close the connection in getOverdueReport().", sqle);
							}
						}
							
						if(beans.size() > 0)
						{
							return beans;
						}		
						return null;
					}		
			
}