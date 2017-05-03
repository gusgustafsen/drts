package gov.ed.fsa.drts.dataaccess;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.apache.log4j.Logger;

import gov.ed.fsa.drts.util.ApplicationProperties;

import oracle.jdbc.pool.OracleDataSource;

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

	private static ProcessEngine processEngine = null;

	static {
		ORACLE_URL = ApplicationProperties.ORACLE_URL.getStringValue();
		ORACLE_USER = ApplicationProperties.ORACLE_USER.getStringValue();
		ORACLE_PWD = ApplicationProperties.ORACLE_PASSWORD.getStringValue();

		String type = ApplicationProperties.ORACLE_CONNECTION_TYPE.getStringValue();

		// make a direct connection
		if (type.equalsIgnoreCase(ApplicationProperties.ORACLE_CONNECTION_TYPE_DIRECT.getStringValue())) {
			connection_type = CONNECTION_DIRECT_TYPE;
			getConnected();
		}
		// JNDI connection
		else if (type.equalsIgnoreCase(ApplicationProperties.ORACLE_CONNECTION_TYPE_JNDI.getStringValue())) {
			connection_type = CONNECTION_JNDI_TYPE;
		}
	}

	public static Connection createConnection() throws SQLException {
		Connection connection = null;

		switch (connection_type) {
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

	private static final void getConnected() {

		ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromResourceDefault().setJdbcDriver("oracle.jdbc.OracleDriver")
				.setDatabaseSchemaUpdate("true").setJobExecutorActivate(false).setMailServerHost("localhost")
				.setMailServerPort(25)
				.setMailServerDefaultFrom(ApplicationProperties.EMAIL_NOTIFY_ADMIN_DRT_FROM.getStringValue());

		switch (connection_type) {
		// direct connection
		case CONNECTION_DIRECT_TYPE:

			try {
				logger.info("Attempting to initialize the Data Source using direct connection.");

				ods_drts = new OracleDataSource();

				ods_drts.setURL(ORACLE_URL);
				ods_drts.setUser(ORACLE_USER);
				ods_drts.setPassword(ORACLE_PWD);

				processEngine = processEngineConfiguration.setJdbcUrl(ApplicationProperties.ORACLE_URL.getStringValue())
						.setJdbcUsername(ApplicationProperties.ORACLE_USER.getStringValue())
						.setJdbcPassword(ApplicationProperties.ORACLE_PASSWORD.getStringValue()).buildProcessEngine();

				logger.info("Data source initialized using direct connection.");
			} catch (Exception ex) {
				logger.error("Data Source failed to initialize using direct connection.", ex);
				throw new RuntimeException(ex);
			}

			break;

		// JNDI connection
		case CONNECTION_JNDI_TYPE:

			try {
				logger.info("Attempting to initialize the Data Source using JNDI.");

				if (ds_drts == null) {
					// TODO move to properties
					JNDI_ENV_NAME_DRTS = ApplicationProperties.ORACLE_JNDI.getStringValue();

					InitialContext ctx_aims = new InitialContext();
					ds_drts = (DataSource) ctx_aims.lookup(JNDI_ENV_NAME_DRTS);

					logger.info("Data source AIMS initialized using JNDI.");

					processEngine = processEngineConfiguration
							.setDataSourceJndiName(ApplicationProperties.ORACLE_JNDI.getStringValue())
							.buildProcessEngine();
				}

			} catch (NamingException ne) {
				logger.error("Data Source failed to initialize using jndi.", ne);
				throw new RuntimeException(ne);
			} catch (Exception ex) {
				logger.error("Data Source failed to initialize using jndi.", ex);
				throw new RuntimeException(ex);
			}

			break;

		default:
			break;
		}
	}

	public static ProcessEngine getProcessEngine() {
		if (processEngine == null) {
			try {
				createConnection();
			} catch (SQLException e) {
				logger.error("Creating db connection", e);
			}
		}
		return processEngine;
	}
}
