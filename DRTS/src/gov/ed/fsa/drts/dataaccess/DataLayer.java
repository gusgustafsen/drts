package gov.ed.fsa.drts.dataaccess;

import gov.ed.fsa.drts.object.DataRequest;
import gov.ed.fsa.drts.util.ApplicationProperties;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class DataLayer {

	private static final Logger logger = Logger.getLogger(DataLayer.class);
	
	// TODO move columns, tables, etc. to properties
	private static final String QUERY_INSERT_DATA_REQUEST = "INSERT INTO DRTS_HISTORY ("
																+ "request_number, request_created_date_time, proc_inst_id, candidate_group, assignee, request_type, request_status, request_created_by, "
																+ "request_iteration, request_due_date, request_urgent, request_related_requests, request_topic_keywords, request_purpose, "
																+ "request_special_considerations, request_description, requestor_name, requestor_organization, requestor_phone, requestor_email, "
																+ "receiver_name, receiver_email, request_display_id"
																+ ") "
																+ "VALUES ("
																+ "?, SYSDATE, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?"
																+ ")";
	
	private static final String QUERY_SELECT_DATA_REQUESTS_BY_GROUP_OR_ASSIGNEE = "SELECT * FROM(SELECT T2.*, rownum AS ROW_NUM FROM(SELECT T.* FROM(SELECT * FROM VIEW_CURRENT_REQUESTS_TASKS) T "
																					+ "WHERE CANDIDATE_GROUP IN (%s) OR ASSIGNEE = ? ORDER BY %s %s) T2) T3 "
																					+ "WHERE ROW_NUM > ?  AND ROW_NUM <= ?";
	
	private static final String QUERY_SELECT_DATA_REQUESTS_BY_GROUP_OR_ASSIGNEE_COUNT = "SELECT COUNT(*) FROM VIEW_CURRENT_REQUESTS_TASKS WHERE CANDIDATE_GROUP IN (%s) OR ASSIGNEE = ?";
	
	private static final String QUERY_SELECT_DATA_REQUESTS_BY_CREATOR = "SELECT * FROM(SELECT T2.*, rownum AS ROW_NUM FROM(SELECT T.* FROM(SELECT * FROM VIEW_CURRENT_REQUESTS_TASKS) T "
																			+ "WHERE REQUEST_CREATED_BY = ? ORDER BY %s %s) T2) T3 "
																			+ "WHERE ROW_NUM > ?  AND ROW_NUM <= ?";

	private static final String QUERY_SELECT_DATA_REQUESTS_BY_CREATOR_COUNT = "SELECT COUNT(*) FROM DRTS_HISTORY WHERE REQUEST_CREATED_BY = ?";
	
	private static final String QUERY_SELECT_DATA_REQUESTS_BY_STATUS = "SELECT * FROM(SELECT T2.*, rownum AS ROW_NUM FROM(SELECT T.* FROM(SELECT * FROM VIEW_CURRENT_REQUESTS_TASKS) T "
																		+ "WHERE REQUEST_STATUS = ? ORDER BY %s %s) T2) T3 "
																		+ "WHERE ROW_NUM > ?  AND ROW_NUM <= ?";

	private static final String QUERY_SELECT_DATA_REQUESTS_BY_STATUS_COUNT = "SELECT COUNT(*) FROM DRTS_HISTORY WHERE REQUEST_STATUS = ?";
	
	private static final String QUERY_SELECT_DATA_REQUESTS_BY_ASSOCIATION = "SELECT * FROM(SELECT T2.*, rownum AS ROW_NUM FROM(SELECT T.* FROM(SELECT * FROM VIEW_CURRENT_REQUESTS_TASKS) T "
																				+ "WHERE assigned_sme = ? OR assigned_validator = ? ORDER BY %s %s) T2) T3 "
																				+ "WHERE ROW_NUM > ?  AND ROW_NUM <= ?";

	private static final String QUERY_SELECT_DATA_REQUESTS_BY_ASSOCIATION_COUNT = "SELECT COUNT(*) FROM DRTS_HISTORY WHERE assigned_sme = ? OR assigned_validator = ?";
	
	private static final String QUERY_SELECT_ALL_DATA_REQUESTS = "SELECT * FROM(SELECT T2.*, rownum AS ROW_NUM FROM(SELECT * FROM VIEW_CURRENT_REQUESTS_TASKS ORDER BY %s %s) T2) T3 WHERE ROW_NUM > ?  AND ROW_NUM <= ?";
	
	private static final String QUERY_SELECT_ALL_DATA_REQUESTS_COUNT = "SELECT COUNT(*) FROM DRTS_HISTORY";
	
	private static final String QUERY_UPDATE_DATA_REQUEST = "UPDATE DRTS_HISTORY SET "
															+ "candidate_group = ?, assignee = ?, request_type = ?, request_status = ?, request_iteration = ?, "
															+ "request_due_date = ?, request_urgent = ?, request_related_requests = ?, request_topic_keywords = ?, request_purpose = ?, "
															+ "request_special_considerations = ?, request_description = ?, requestor_name = ?, requestor_organization = ?, requestor_phone = ?, requestor_email = ?, "
															+ "receiver_name = ?, receiver_email = ?, assigned_sme = ?, assigned_to_sme = ?, admin_comments = ?, date_resolved = ?, resolution = ?, sme_comments = ?, "
															+ "assigned_validator = ?, assigned_to_validator = ?, date_validated = ?, date_closed = ?"
															+ "WHERE request_number = ?";
	
	private static final String QUERY_SELECT_NEXT_DATA_REQUEST_ID = "SELECT COALESCE(MAX(request_display_id), 0) + 1 FROM DRTS_HISTORY";
	
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
			
			// TODO change
			prepared_statement.setString(1, (String) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_ID.getStringValue()));
			prepared_statement.setString(2, process_instance_id);
			prepared_statement.setString(3, candidate_group);
			prepared_statement.setString(4, assignee);
			prepared_statement.setString(5, (String) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_TYPE.getStringValue()));
			prepared_statement.setString(6, (String) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_STATUS.getStringValue()));
			prepared_statement.setString(7, (String) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_CREATED_BY.getStringValue()));
			prepared_statement.setInt(8, (Integer) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_ITERATION.getStringValue()));
			prepared_statement.setTimestamp(9, new Timestamp(((Date) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_DUE_DATE.getStringValue())).getTime()));
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
			prepared_statement.setInt(22, next_id);
			
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
            
            System.out.println("executing sql: " + formatted_query);
        	
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
	       	
	       	System.out.println("executing sql: " + formatted_query);
	        	
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
	       	
	       	System.out.println("executing sql: " + formatted_query);
		        	
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
			
			prepared_statement.setString(1, candidate_group);
			prepared_statement.setString(2, assignee);
			prepared_statement.setString(3, (String) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_TYPE.getStringValue()));
			prepared_statement.setString(4, (String) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_STATUS.getStringValue()));
			prepared_statement.setInt(5, (Integer) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_ITERATION.getStringValue()));
			if(request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_DUE_DATE.getStringValue()) != null)
			{
				prepared_statement.setTimestamp(6, new Timestamp(((Date) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_DUE_DATE.getStringValue())).getTime()));
			}
			else
			{
				prepared_statement.setTimestamp(6, null);
			}
			prepared_statement.setString(7, ((Boolean) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_URGENT.getStringValue())).toString());
			prepared_statement.setString(8, (String) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_RELATED_REQUESTS.getStringValue()));
			prepared_statement.setString(9, (String) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_TOPIC_KEYWORDS.getStringValue()));
			prepared_statement.setString(10, (String) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_PURPOSE.getStringValue()));
			prepared_statement.setString(11, (String) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_SPECIAL_CONSIDERATIONS_ISSUES.getStringValue()));
			prepared_statement.setString(12, (String) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_DESCRIPTION.getStringValue()));
			prepared_statement.setString(13, (String) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_REQUESTOR_NAME.getStringValue()));
			prepared_statement.setString(14, (String) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_REQUESTOR_ORGANIZATION.getStringValue()));
			prepared_statement.setString(15, (String) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_REQUESTOR_PHONE.getStringValue()));
			prepared_statement.setString(16, (String) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_REQUESTOR_EMAIL.getStringValue()));
			prepared_statement.setString(17, (String) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_RECEIVER_NAME.getStringValue()));
			prepared_statement.setString(18, (String) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_RECEIVER_EMAIL.getStringValue()));
			prepared_statement.setString(19, (String) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_ASSIGNED_SME.getStringValue()));
			if(request_variables.get("ASSIGNED_TO_SME") != null)
			{
				prepared_statement.setTimestamp(20, new Timestamp(((Date) request_variables.get("ASSIGNED_TO_SME")).getTime()));
			}
			else
			{
				prepared_statement.setTimestamp(20, null);
			}
			prepared_statement.setString(21, (String) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_ADMIN_COMMENTS.getStringValue()));
			if(request_variables.get("DATE_RESOLVED") != null)
			{
				prepared_statement.setTimestamp(22, new Timestamp(((Date) request_variables.get("DATE_RESOLVED")).getTime()));
			}
			else
			{
				prepared_statement.setTimestamp(22, null);
			}
			prepared_statement.setString(23, (String) request_variables.get("RESOLUTION"));
			prepared_statement.setString(24, (String) request_variables.get(ApplicationProperties.DATA_REQUEST_FIELD_SME_COMMENTS.getStringValue()));
			prepared_statement.setString(25, (String) request_variables.get("ASSIGNED_VALIDATOR"));
			if(request_variables.get("ASSIGNED_TO_VALIDATOR") != null)
			{
				prepared_statement.setTimestamp(26, new Timestamp(((Date) request_variables.get("ASSIGNED_TO_VALIDATOR")).getTime()));
			}
			else
			{
				prepared_statement.setTimestamp(26, null);
			}
			if(request_variables.get("DATE_VALIDATED") != null)
			{
				prepared_statement.setTimestamp(27, new Timestamp(((Date) request_variables.get("DATE_VALIDATED")).getTime()));
			}
			else
			{
				prepared_statement.setTimestamp(27, null);
			}
			if(request_variables.get("DATE_CLOSED") != null)
			{
				prepared_statement.setTimestamp(28, new Timestamp(((Date) request_variables.get("DATE_CLOSED")).getTime()));
			}
			else
			{
				prepared_statement.setTimestamp(28, null);
			}
			prepared_statement.setString(29, request_id);
			
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
	
	private DataRequest mapRequest(ResultSet result_set)
		throws SQLException
	{
		DataRequest request = new DataRequest();
		request.setId(result_set.getString("REQUEST_NUMBER"));
		request.setCandidateGroup(result_set.getString("CANDIDATE_GROUP"));
		request.setAssignee(result_set.getString("ASSIGNEE"));
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
		request.setAdministratorComments(result_set.getString(ApplicationProperties.DATA_REQUEST_FIELD_ADMIN_COMMENTS.getStringValue()));
		request.setDateResolved(result_set.getDate(ApplicationProperties.DATA_REQUEST_FIELD_DATE_RESOLVED.getStringValue()));
		request.setResolution(result_set.getString(ApplicationProperties.DATA_REQUEST_FIELD_RESOLUTION.getStringValue()));
		request.setSmeComments(result_set.getString(ApplicationProperties.DATA_REQUEST_FIELD_SME_COMMENTS.getStringValue()));
		request.setCurrentTaskId(result_set.getString("CURRENT_TASK_ID"));
		request.setCurrentTaskName(result_set.getString("CURRENT_TASK_NAME"));
		request.setCurrentTaskFormKey(result_set.getString("CURRENT_TASK_FORM_KEY"));
		request.setDisplayId(result_set.getInt("REQUEST_DISPLAY_ID"));
		request.setAssignedValidator(result_set.getString(ApplicationProperties.DATA_REQUEST_FIELD_ASSIGNED_VALIDATOR.getStringValue()));
		request.setDateAssignedToValidator(result_set.getDate(ApplicationProperties.DATA_REQUEST_FIELD_ASSIGNED_TO_VALIDATOR.getStringValue()));
		request.setDateValidated(result_set.getDate(ApplicationProperties.DATA_REQUEST_FIELD_DATE_VALIDATED.getStringValue()));
		request.setDateClosed(result_set.getDate(ApplicationProperties.DATA_REQUEST_FIELD_DATE_CLOSED.getStringValue()));
		
		return request;
	}
}