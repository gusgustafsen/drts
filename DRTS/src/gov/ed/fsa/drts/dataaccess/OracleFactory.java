package gov.ed.fsa.drts.dataaccess;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import oracle.jdbc.pool.OracleDataSource;

import org.apache.log4j.Logger;

public class OracleFactory {

	private static final Logger logger = Logger.getLogger(OracleFactory.class);
	
	private static String JNDI_ENV_NAME_DRTS = null;
	private static DataSource ds_drts = null;
	private static OracleDataSource ods_drts = null;
	private static int connection_type = -1;
	
	private static final int CONNECTION_DIRECT_TYPE = 1;
	private static final int CONNECTION_JNDI_TYPE = 2;
	
	private static String ORACLE_URL = null;
	private static String ORACLE_USER = null;
	private static String ORACLE_PWD = null;
	
	static 
	{
		// TODO move to properties
		ORACLE_URL = "jdbc:oracle:thin:@127.0.0.1:1521:XE";
		ORACLE_USER = "tima";
		ORACLE_PWD = "xmmwoxak47";
		
//		ORACLE_URL = "jdbc:oracle:thin:@10.0.0.149:1521:XE";
//		ORACLE_USER = "drts_admin";
//		ORACLE_PWD = "drts123";
		
		// TODO move to properties
		String type = "direct";
		
		// make a direct connection
		if(type.equalsIgnoreCase("direct"))
		{
			connection_type = CONNECTION_DIRECT_TYPE;
			getConnected();
		}
		// JNDI connection
		else if(type.equalsIgnoreCase("jndi"))
		{
			connection_type = CONNECTION_JNDI_TYPE;
		}
	}
	
	public static Connection createConnection() 
		throws SQLException 
	{
		Connection connection = null;
		
		switch(connection_type)
		{
			// get direct connection
			case CONNECTION_DIRECT_TYPE:
				
				connection = ods_drts.getConnection();
				
				break;
				
			// get JNDI connection
			case CONNECTION_JNDI_TYPE:
				
				getConnected();
				
				connection = ds_drts.getConnection();
				
				break;
				
			default:
				break;
		}
		
		return connection;
	}

	private static final void getConnected()
	{
		switch(connection_type)
		{
			// direct connection
			case CONNECTION_DIRECT_TYPE:
				
				try
				{
					logger.info("Attempting to initialize the Data Source using direct connection.");
					
					ods_drts = new OracleDataSource();
						    
					ods_drts.setURL(ORACLE_URL);
					ods_drts.setUser(ORACLE_USER);
					ods_drts.setPassword(ORACLE_PWD);
				    
				    logger.info("Data source initialized using direct connection.");
				}
				catch(Exception ex)
				{
					logger.error("Data Source failed to initialize using direct connection.", ex);
					throw new RuntimeException(ex);
				}
				
				break;
				
			// JNDI connection
			case CONNECTION_JNDI_TYPE:
				
				try 
				{
					logger.info("Attempting to initialize the Data Source using JNDI.");
					
					if(ds_drts == null)
					{
						// TODO move to properties
						JNDI_ENV_NAME_DRTS = "jdbc/drts";
						
						InitialContext ctx_aims = new InitialContext();
						ds_drts = (DataSource) ctx_aims.lookup(JNDI_ENV_NAME_DRTS);
						
						logger.info("Data source AIMS initialized using JNDI.");
					}
					
				}
				catch(NamingException ne) 
				{
					logger.error("Data Source failed to initialize using jndi.", ne);
					throw new RuntimeException(ne);
				}
				catch(Exception ex)
				{
					logger.error("Data Source failed to initialize using jndi.", ex);
					throw new RuntimeException(ex);
				}
				
				break;
				
			default:
				break;
		}
	}
}