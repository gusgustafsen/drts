--------------------------------------------------------
--  File created - Wednesday-January-25-2017   
--------------------------------------------------------
DROP TABLE "ACT_EVT_LOG";
DROP TABLE "ACT_GE_BYTEARRAY";
DROP TABLE "ACT_GE_PROPERTY";
DROP TABLE "ACT_HI_ACTINST";
DROP TABLE "ACT_HI_ATTACHMENT";
DROP TABLE "ACT_HI_COMMENT";
DROP TABLE "ACT_HI_DETAIL";
DROP TABLE "ACT_HI_IDENTITYLINK";
DROP TABLE "ACT_HI_PROCINST";
DROP TABLE "ACT_HI_TASKINST";
DROP TABLE "ACT_HI_VARINST";
DROP TABLE "ACT_ID_GROUP";
DROP TABLE "ACT_ID_INFO";
DROP TABLE "ACT_ID_MEMBERSHIP";
DROP TABLE "ACT_ID_USER";
DROP TABLE "ACT_RE_DEPLOYMENT";
DROP TABLE "ACT_RE_MODEL";
DROP TABLE "ACT_RE_PROCDEF";
DROP TABLE "ACT_RU_EVENT_SUBSCR";
DROP TABLE "ACT_RU_EXECUTION";
DROP TABLE "ACT_RU_IDENTITYLINK";
DROP TABLE "ACT_RU_JOB";
DROP TABLE "ACT_RU_TASK";
DROP TABLE "ACT_RU_VARIABLE";
DROP TABLE "DRTS_ATTACHMENTS";
DROP TABLE "DRTS_AUDIT";
DROP TABLE "DRTS_HISTORY";
DROP TABLE "DRTS_ITERATIONS";
DROP TABLE "DRTS_VIEW_COMMENTS_PERMISSION";
--------------------------------------------------------
--  DDL for Table ACT_EVT_LOG
--------------------------------------------------------

  CREATE TABLE "ACT_EVT_LOG" ("LOG_NR_" NUMBER(19,0), "TYPE_" NVARCHAR2(64), "PROC_DEF_ID_" NVARCHAR2(64), "PROC_INST_ID_" NVARCHAR2(64), "EXECUTION_ID_" NVARCHAR2(64), "TASK_ID_" NVARCHAR2(64), "TIME_STAMP_" TIMESTAMP (6), "USER_ID_" NVARCHAR2(255), "DATA_" BLOB, "LOCK_OWNER_" NVARCHAR2(255), "LOCK_TIME_" TIMESTAMP (6), "IS_PROCESSED_" NUMBER(3,0) DEFAULT 0) ;
--------------------------------------------------------
--  DDL for Table ACT_GE_BYTEARRAY
--------------------------------------------------------

  CREATE TABLE "ACT_GE_BYTEARRAY" ("ID_" NVARCHAR2(64), "REV_" NUMBER(*,0), "NAME_" NVARCHAR2(255), "DEPLOYMENT_ID_" NVARCHAR2(64), "BYTES_" BLOB, "GENERATED_" NUMBER(1,0)) ;
--------------------------------------------------------
--  DDL for Table ACT_GE_PROPERTY
--------------------------------------------------------

  CREATE TABLE "ACT_GE_PROPERTY" ("NAME_" NVARCHAR2(64), "VALUE_" NVARCHAR2(300), "REV_" NUMBER(*,0)) ;
--------------------------------------------------------
--  DDL for Table ACT_HI_ACTINST
--------------------------------------------------------

  CREATE TABLE "ACT_HI_ACTINST" ("ID_" NVARCHAR2(64), "PROC_DEF_ID_" NVARCHAR2(64), "PROC_INST_ID_" NVARCHAR2(64), "EXECUTION_ID_" NVARCHAR2(64), "ACT_ID_" NVARCHAR2(255), "TASK_ID_" NVARCHAR2(64), "CALL_PROC_INST_ID_" NVARCHAR2(64), "ACT_NAME_" NVARCHAR2(255), "ACT_TYPE_" NVARCHAR2(255), "ASSIGNEE_" NVARCHAR2(255), "START_TIME_" TIMESTAMP (6), "END_TIME_" TIMESTAMP (6), "DURATION_" NUMBER(19,0), "TENANT_ID_" NVARCHAR2(255) DEFAULT '') ;
--------------------------------------------------------
--  DDL for Table ACT_HI_ATTACHMENT
--------------------------------------------------------

  CREATE TABLE "ACT_HI_ATTACHMENT" ("ID_" NVARCHAR2(64), "REV_" NUMBER(*,0), "USER_ID_" NVARCHAR2(255), "NAME_" NVARCHAR2(255), "DESCRIPTION_" NVARCHAR2(2000), "TYPE_" NVARCHAR2(255), "TASK_ID_" NVARCHAR2(64), "PROC_INST_ID_" NVARCHAR2(64), "URL_" NVARCHAR2(2000), "CONTENT_ID_" NVARCHAR2(64)) ;
--------------------------------------------------------
--  DDL for Table ACT_HI_COMMENT
--------------------------------------------------------

  CREATE TABLE "ACT_HI_COMMENT" ("ID_" NVARCHAR2(64), "TYPE_" NVARCHAR2(255), "TIME_" TIMESTAMP (6), "USER_ID_" NVARCHAR2(255), "TASK_ID_" NVARCHAR2(64), "PROC_INST_ID_" NVARCHAR2(64), "ACTION_" NVARCHAR2(255), "MESSAGE_" NVARCHAR2(2000), "FULL_MSG_" BLOB) ;
--------------------------------------------------------
--  DDL for Table ACT_HI_DETAIL
--------------------------------------------------------

  CREATE TABLE "ACT_HI_DETAIL" ("ID_" NVARCHAR2(64), "TYPE_" NVARCHAR2(255), "PROC_INST_ID_" NVARCHAR2(64), "EXECUTION_ID_" NVARCHAR2(64), "TASK_ID_" NVARCHAR2(64), "ACT_INST_ID_" NVARCHAR2(64), "NAME_" NVARCHAR2(255), "VAR_TYPE_" NVARCHAR2(64), "REV_" NUMBER(*,0), "TIME_" TIMESTAMP (6), "BYTEARRAY_ID_" NVARCHAR2(64), "DOUBLE_" NUMBER(*,10), "LONG_" NUMBER(19,0), "TEXT_" NVARCHAR2(2000), "TEXT2_" NVARCHAR2(2000)) ;
--------------------------------------------------------
--  DDL for Table ACT_HI_IDENTITYLINK
--------------------------------------------------------

  CREATE TABLE "ACT_HI_IDENTITYLINK" ("ID_" NVARCHAR2(64), "GROUP_ID_" NVARCHAR2(255), "TYPE_" NVARCHAR2(255), "USER_ID_" NVARCHAR2(255), "TASK_ID_" NVARCHAR2(64), "PROC_INST_ID_" NVARCHAR2(64)) ;
--------------------------------------------------------
--  DDL for Table ACT_HI_PROCINST
--------------------------------------------------------

  CREATE TABLE "ACT_HI_PROCINST" ("ID_" NVARCHAR2(64), "PROC_INST_ID_" NVARCHAR2(64), "BUSINESS_KEY_" NVARCHAR2(255), "PROC_DEF_ID_" NVARCHAR2(64), "START_TIME_" TIMESTAMP (6), "END_TIME_" TIMESTAMP (6), "DURATION_" NUMBER(19,0), "START_USER_ID_" NVARCHAR2(255), "START_ACT_ID_" NVARCHAR2(255), "END_ACT_ID_" NVARCHAR2(255), "SUPER_PROCESS_INSTANCE_ID_" NVARCHAR2(64), "DELETE_REASON_" NVARCHAR2(2000), "TENANT_ID_" NVARCHAR2(255) DEFAULT '', "NAME_" NVARCHAR2(255)) ;
--------------------------------------------------------
--  DDL for Table ACT_HI_TASKINST
--------------------------------------------------------

  CREATE TABLE "ACT_HI_TASKINST" ("ID_" NVARCHAR2(64), "PROC_DEF_ID_" NVARCHAR2(64), "TASK_DEF_KEY_" NVARCHAR2(255), "PROC_INST_ID_" NVARCHAR2(64), "EXECUTION_ID_" NVARCHAR2(64), "PARENT_TASK_ID_" NVARCHAR2(64), "NAME_" NVARCHAR2(255), "DESCRIPTION_" NVARCHAR2(2000), "OWNER_" NVARCHAR2(255), "ASSIGNEE_" NVARCHAR2(255), "START_TIME_" TIMESTAMP (6), "CLAIM_TIME_" TIMESTAMP (6), "END_TIME_" TIMESTAMP (6), "DURATION_" NUMBER(19,0), "DELETE_REASON_" NVARCHAR2(2000), "PRIORITY_" NUMBER(*,0), "DUE_DATE_" TIMESTAMP (6), "FORM_KEY_" NVARCHAR2(255), "CATEGORY_" NVARCHAR2(255), "TENANT_ID_" NVARCHAR2(255) DEFAULT '') ;
--------------------------------------------------------
--  DDL for Table ACT_HI_VARINST
--------------------------------------------------------

  CREATE TABLE "ACT_HI_VARINST" ("ID_" NVARCHAR2(64), "PROC_INST_ID_" NVARCHAR2(64), "EXECUTION_ID_" NVARCHAR2(64), "TASK_ID_" NVARCHAR2(64), "NAME_" NVARCHAR2(255), "VAR_TYPE_" NVARCHAR2(100), "REV_" NUMBER(*,0), "BYTEARRAY_ID_" NVARCHAR2(64), "DOUBLE_" NUMBER(*,10), "LONG_" NUMBER(19,0), "TEXT_" NVARCHAR2(2000), "TEXT2_" NVARCHAR2(2000), "CREATE_TIME_" TIMESTAMP (6), "LAST_UPDATED_TIME_" TIMESTAMP (6)) ;
--------------------------------------------------------
--  DDL for Table ACT_ID_GROUP
--------------------------------------------------------

  CREATE TABLE "ACT_ID_GROUP" ("ID_" NVARCHAR2(64), "REV_" NUMBER(*,0), "NAME_" NVARCHAR2(255), "TYPE_" NVARCHAR2(255)) ;
--------------------------------------------------------
--  DDL for Table ACT_ID_INFO
--------------------------------------------------------

  CREATE TABLE "ACT_ID_INFO" ("ID_" NVARCHAR2(64), "REV_" NUMBER(*,0), "USER_ID_" NVARCHAR2(64), "TYPE_" NVARCHAR2(64), "KEY_" NVARCHAR2(255), "VALUE_" NVARCHAR2(255), "PASSWORD_" BLOB, "PARENT_ID_" NVARCHAR2(255)) ;
--------------------------------------------------------
--  DDL for Table ACT_ID_MEMBERSHIP
--------------------------------------------------------

  CREATE TABLE "ACT_ID_MEMBERSHIP" ("USER_ID_" NVARCHAR2(64), "GROUP_ID_" NVARCHAR2(64)) ;
--------------------------------------------------------
--  DDL for Table ACT_ID_USER
--------------------------------------------------------

  CREATE TABLE "ACT_ID_USER" ("ID_" NVARCHAR2(64), "REV_" NUMBER(*,0), "FIRST_" NVARCHAR2(255), "LAST_" NVARCHAR2(255), "EMAIL_" NVARCHAR2(255), "PWD_" NVARCHAR2(255), "PICTURE_ID_" NVARCHAR2(64)) ;
--------------------------------------------------------
--  DDL for Table ACT_RE_DEPLOYMENT
--------------------------------------------------------

  CREATE TABLE "ACT_RE_DEPLOYMENT" ("ID_" NVARCHAR2(64), "NAME_" NVARCHAR2(255), "CATEGORY_" NVARCHAR2(255), "TENANT_ID_" NVARCHAR2(255) DEFAULT '', "DEPLOY_TIME_" TIMESTAMP (6)) ;
--------------------------------------------------------
--  DDL for Table ACT_RE_MODEL
--------------------------------------------------------

  CREATE TABLE "ACT_RE_MODEL" ("ID_" NVARCHAR2(64), "REV_" NUMBER(*,0), "NAME_" NVARCHAR2(255), "KEY_" NVARCHAR2(255), "CATEGORY_" NVARCHAR2(255), "CREATE_TIME_" TIMESTAMP (6), "LAST_UPDATE_TIME_" TIMESTAMP (6), "VERSION_" NUMBER(*,0), "META_INFO_" NVARCHAR2(2000), "DEPLOYMENT_ID_" NVARCHAR2(64), "EDITOR_SOURCE_VALUE_ID_" NVARCHAR2(64), "EDITOR_SOURCE_EXTRA_VALUE_ID_" NVARCHAR2(64), "TENANT_ID_" NVARCHAR2(255) DEFAULT '') ;
--------------------------------------------------------
--  DDL for Table ACT_RE_PROCDEF
--------------------------------------------------------

  CREATE TABLE "ACT_RE_PROCDEF" ("ID_" NVARCHAR2(64), "REV_" NUMBER(*,0), "CATEGORY_" NVARCHAR2(255), "NAME_" NVARCHAR2(255), "KEY_" NVARCHAR2(255), "VERSION_" NUMBER(*,0), "DEPLOYMENT_ID_" NVARCHAR2(64), "RESOURCE_NAME_" NVARCHAR2(2000), "DGRM_RESOURCE_NAME_" VARCHAR2(4000), "DESCRIPTION_" NVARCHAR2(2000), "HAS_START_FORM_KEY_" NUMBER(1,0), "SUSPENSION_STATE_" NUMBER(*,0), "TENANT_ID_" NVARCHAR2(255) DEFAULT '') ;
--------------------------------------------------------
--  DDL for Table ACT_RU_EVENT_SUBSCR
--------------------------------------------------------

  CREATE TABLE "ACT_RU_EVENT_SUBSCR" ("ID_" NVARCHAR2(64), "REV_" NUMBER(*,0), "EVENT_TYPE_" NVARCHAR2(255), "EVENT_NAME_" NVARCHAR2(255), "EXECUTION_ID_" NVARCHAR2(64), "PROC_INST_ID_" NVARCHAR2(64), "ACTIVITY_ID_" NVARCHAR2(64), "CONFIGURATION_" NVARCHAR2(255), "CREATED_" TIMESTAMP (6), "PROC_DEF_ID_" NVARCHAR2(64), "TENANT_ID_" NVARCHAR2(255) DEFAULT '') ;
--------------------------------------------------------
--  DDL for Table ACT_RU_EXECUTION
--------------------------------------------------------

  CREATE TABLE "ACT_RU_EXECUTION" ("ID_" NVARCHAR2(64), "REV_" NUMBER(*,0), "PROC_INST_ID_" NVARCHAR2(64), "BUSINESS_KEY_" NVARCHAR2(255), "PARENT_ID_" NVARCHAR2(64), "PROC_DEF_ID_" NVARCHAR2(64), "SUPER_EXEC_" NVARCHAR2(64), "ACT_ID_" NVARCHAR2(255), "IS_ACTIVE_" NUMBER(1,0), "IS_CONCURRENT_" NUMBER(1,0), "IS_SCOPE_" NUMBER(1,0), "IS_EVENT_SCOPE_" NUMBER(1,0), "SUSPENSION_STATE_" NUMBER(*,0), "CACHED_ENT_STATE_" NUMBER(*,0), "TENANT_ID_" NVARCHAR2(255) DEFAULT '', "NAME_" NVARCHAR2(255)) ;
--------------------------------------------------------
--  DDL for Table ACT_RU_IDENTITYLINK
--------------------------------------------------------

  CREATE TABLE "ACT_RU_IDENTITYLINK" ("ID_" NVARCHAR2(64), "REV_" NUMBER(*,0), "GROUP_ID_" NVARCHAR2(255), "TYPE_" NVARCHAR2(255), "USER_ID_" NVARCHAR2(255), "TASK_ID_" NVARCHAR2(64), "PROC_INST_ID_" NVARCHAR2(64), "PROC_DEF_ID_" NVARCHAR2(64)) ;
--------------------------------------------------------
--  DDL for Table ACT_RU_JOB
--------------------------------------------------------

  CREATE TABLE "ACT_RU_JOB" ("ID_" NVARCHAR2(64), "REV_" NUMBER(*,0), "TYPE_" NVARCHAR2(255), "LOCK_EXP_TIME_" TIMESTAMP (6), "LOCK_OWNER_" NVARCHAR2(255), "EXCLUSIVE_" NUMBER(1,0), "EXECUTION_ID_" NVARCHAR2(64), "PROCESS_INSTANCE_ID_" NVARCHAR2(64), "PROC_DEF_ID_" NVARCHAR2(64), "RETRIES_" NUMBER(*,0), "EXCEPTION_STACK_ID_" NVARCHAR2(64), "EXCEPTION_MSG_" NVARCHAR2(2000), "DUEDATE_" TIMESTAMP (6), "REPEAT_" NVARCHAR2(255), "HANDLER_TYPE_" NVARCHAR2(255), "HANDLER_CFG_" NVARCHAR2(2000), "TENANT_ID_" NVARCHAR2(255) DEFAULT '') ;
--------------------------------------------------------
--  DDL for Table ACT_RU_TASK
--------------------------------------------------------

  CREATE TABLE "ACT_RU_TASK" ("ID_" NVARCHAR2(64), "REV_" NUMBER(*,0), "EXECUTION_ID_" NVARCHAR2(64), "PROC_INST_ID_" NVARCHAR2(64), "PROC_DEF_ID_" NVARCHAR2(64), "NAME_" NVARCHAR2(255), "PARENT_TASK_ID_" NVARCHAR2(64), "DESCRIPTION_" NVARCHAR2(2000), "TASK_DEF_KEY_" NVARCHAR2(255), "OWNER_" NVARCHAR2(255), "ASSIGNEE_" NVARCHAR2(255), "DELEGATION_" NVARCHAR2(64), "PRIORITY_" NUMBER(*,0), "CREATE_TIME_" TIMESTAMP (6), "DUE_DATE_" TIMESTAMP (6), "CATEGORY_" NVARCHAR2(255), "SUSPENSION_STATE_" NUMBER(*,0), "TENANT_ID_" NVARCHAR2(255) DEFAULT '', "FORM_KEY_" NVARCHAR2(255)) ;
--------------------------------------------------------
--  DDL for Table ACT_RU_VARIABLE
--------------------------------------------------------

  CREATE TABLE "ACT_RU_VARIABLE" ("ID_" NVARCHAR2(64), "REV_" NUMBER(*,0), "TYPE_" NVARCHAR2(255), "NAME_" NVARCHAR2(255), "EXECUTION_ID_" NVARCHAR2(64), "PROC_INST_ID_" NVARCHAR2(64), "TASK_ID_" NVARCHAR2(64), "BYTEARRAY_ID_" NVARCHAR2(64), "DOUBLE_" NUMBER(*,10), "LONG_" NUMBER(19,0), "TEXT_" NVARCHAR2(2000), "TEXT2_" NVARCHAR2(2000)) ;
--------------------------------------------------------
--  DDL for Table DRTS_ATTACHMENTS
--------------------------------------------------------

  CREATE TABLE "DRTS_ATTACHMENTS" ("REQUEST_ID" VARCHAR2(256), "FILE_TYPE" VARCHAR2(256), "FILE_NAME" VARCHAR2(256), "FILE_CONTENT" BLOB, "FILE_SIZE" NUMBER, "ID" VARCHAR2(256 CHAR)) ;
--------------------------------------------------------
--  DDL for Table DRTS_AUDIT
--------------------------------------------------------

  CREATE TABLE "DRTS_AUDIT" ("AUDIT_ID" NVARCHAR2(256), "REQUEST_NUMBER" NVARCHAR2(256), "FIELD_NAME" NVARCHAR2(256), "OLD_VALUE" NVARCHAR2(256), "NEW_VALUE" NVARCHAR2(256), "MODIFIED_DATE" TIMESTAMP (6), "MODIFIED_BY" NVARCHAR2(256)) ;
--------------------------------------------------------
--  DDL for Table DRTS_HISTORY
--------------------------------------------------------

  CREATE TABLE "DRTS_HISTORY" ("REQUEST_NUMBER" NVARCHAR2(256), "DRT_REQUEST_DATE" TIMESTAMP (6), "PROC_INST_ID_" NVARCHAR2(64), "CANDIDATE_GROUP" NVARCHAR2(256), "ASSIGNEE" NVARCHAR2(256), "REQUEST_TYPE" NVARCHAR2(256), "REQUEST_STATUS" NVARCHAR2(256), "CREATED_BY" NVARCHAR2(256), "ITERATION" NUMBER(19,0), "REQUESTED_DUE_DATE" DATE, "URGENCY_FLAG" NVARCHAR2(256), "RELATED_REQUEST_NUMBERS" NVARCHAR2(256), "TOPIC_KEYWORDS" NVARCHAR2(256), "REQUESTOR_NAME" NVARCHAR2(256), "REQUESTOR_ORG" NVARCHAR2(256), "REQUESTOR_PHONE" NVARCHAR2(256), "REQUESTOR_EMAIL" NVARCHAR2(256), "RECEIVER" NVARCHAR2(256), "RECEIVER_EMAIL" NVARCHAR2(256), "SME" NVARCHAR2(256), "SME_ASSIGNED_DATE" DATE, "RESOLVED_DATE" DATE, "REQUEST_DISPLAY_ID" NUMBER, "VALIDATION_SME" NVARCHAR2(256), "ASSIGNED_TO_VALIDATOR" DATE, "VALIDATION_DATE" DATE, "CLOSED_DATE" TIMESTAMP (6), "MODIFIED_DATE" TIMESTAMP (6), "PII_FLAG" NUMBER(1,0), "REQUEST_PURPOSE" NCLOB, "SPEC_CONSIDERATIONS" NCLOB, "REQUEST_DESCR" NCLOB, "COMMENTS" NCLOB, "TRACKING_SUFFIX" NCHAR(1), "TRACKING_NUMBER" NVARCHAR2(293), "SYSTEM_NAME" NVARCHAR2(256), "DELAY_REASON" NVARCHAR2(256), "ORIGINAL_REQUEST_DATE" DATE, "AGREED_DUE_DATE" DATE, "ANTICIPATED_DUE_DATE" DATE, "DATE_RUN" DATE, "REPORT_TYPE" NVARCHAR2(256), "QUERY_REPORT_NAME" NVARCHAR2(256), "CLARIF_ASSUMPS" NCLOB, "DETAIL_STEPS" NCLOB, "VALIDATION_DESCR" NCLOB, "VALIDATION_RESULT" NCLOB, "GOLDEN_QUERY_LIBRARY" NCLOB, "BUSINESS_REQS" NVARCHAR2(2000), "TIER" NUMBER(19,0), "SME_GROUP" NVARCHAR2(256), "VALIDATION_SME_GROUP" NVARCHAR2(256), "MODIFIED_BY" NVARCHAR2(256)) ;
--------------------------------------------------------
--  DDL for Table DRTS_ITERATIONS
--------------------------------------------------------

  CREATE TABLE "DRTS_ITERATIONS" ("PARENT_ID" VARCHAR2(256), "ITERATION" NUMBER, "CHILD_ID" VARCHAR2(256)) ;
--------------------------------------------------------
--  DDL for Table DRTS_VIEW_COMMENTS_PERMISSION
--------------------------------------------------------

  CREATE TABLE "DRTS_VIEW_COMMENTS_PERMISSION" ("REQUEST_NUMBER" NVARCHAR2(256)) ;
--------------------------------------------------------
--  DDL for Index SYS_C0010266
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C0010266" ON "ACT_EVT_LOG" ("LOG_NR_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_BYTEAR_DEPL
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_BYTEAR_DEPL" ON "ACT_GE_BYTEARRAY" ("DEPLOYMENT_ID_") ;
--------------------------------------------------------
--  DDL for Index SYS_C0010237
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C0010237" ON "ACT_GE_BYTEARRAY" ("ID_") ;
--------------------------------------------------------
--  DDL for Index SYS_C0010235
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C0010235" ON "ACT_GE_PROPERTY" ("NAME_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_HI_ACT_INST_START
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_HI_ACT_INST_START" ON "ACT_HI_ACTINST" ("START_TIME_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_HI_ACT_INST_END
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_HI_ACT_INST_END" ON "ACT_HI_ACTINST" ("END_TIME_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_HI_ACT_INST_PROCINST
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_HI_ACT_INST_PROCINST" ON "ACT_HI_ACTINST" ("PROC_INST_ID_", "ACT_ID_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_HI_ACT_INST_EXEC
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_HI_ACT_INST_EXEC" ON "ACT_HI_ACTINST" ("EXECUTION_ID_", "ACT_ID_") ;
--------------------------------------------------------
--  DDL for Index SYS_C0010300
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C0010300" ON "ACT_HI_ACTINST" ("ID_") ;
--------------------------------------------------------
--  DDL for Index SYS_C0010316
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C0010316" ON "ACT_HI_ATTACHMENT" ("ID_") ;
--------------------------------------------------------
--  DDL for Index SYS_C0010314
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C0010314" ON "ACT_HI_COMMENT" ("ID_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_HI_DETAIL_PROC_INST
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_HI_DETAIL_PROC_INST" ON "ACT_HI_DETAIL" ("PROC_INST_ID_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_HI_DETAIL_ACT_INST
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_HI_DETAIL_ACT_INST" ON "ACT_HI_DETAIL" ("ACT_INST_ID_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_HI_DETAIL_TIME
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_HI_DETAIL_TIME" ON "ACT_HI_DETAIL" ("TIME_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_HI_DETAIL_NAME
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_HI_DETAIL_NAME" ON "ACT_HI_DETAIL" ("NAME_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_HI_DETAIL_TASK_ID
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_HI_DETAIL_TASK_ID" ON "ACT_HI_DETAIL" ("TASK_ID_") ;
--------------------------------------------------------
--  DDL for Index SYS_C0010311
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C0010311" ON "ACT_HI_DETAIL" ("ID_") ;
--------------------------------------------------------
--  DDL for Index SYS_C0010317
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C0010317" ON "ACT_HI_IDENTITYLINK" ("ID_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_HI_IDENT_LNK_USER
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_HI_IDENT_LNK_USER" ON "ACT_HI_IDENTITYLINK" ("USER_ID_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_HI_IDENT_LNK_TASK
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_HI_IDENT_LNK_TASK" ON "ACT_HI_IDENTITYLINK" ("TASK_ID_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_HI_IDENT_LNK_PROCINST
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_HI_IDENT_LNK_PROCINST" ON "ACT_HI_IDENTITYLINK" ("PROC_INST_ID_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_HI_PRO_INST_END
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_HI_PRO_INST_END" ON "ACT_HI_PROCINST" ("END_TIME_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_HI_PRO_I_BUSKEY
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_HI_PRO_I_BUSKEY" ON "ACT_HI_PROCINST" ("BUSINESS_KEY_") ;
--------------------------------------------------------
--  DDL for Index SYS_C0010291
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C0010291" ON "ACT_HI_PROCINST" ("ID_") ;
--------------------------------------------------------
--  DDL for Index SYS_C0010292
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C0010292" ON "ACT_HI_PROCINST" ("PROC_INST_ID_") ;
--------------------------------------------------------
--  DDL for Index SYS_C0010303
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C0010303" ON "ACT_HI_TASKINST" ("ID_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_HI_PROCVAR_PROC_INST
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_HI_PROCVAR_PROC_INST" ON "ACT_HI_VARINST" ("PROC_INST_ID_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_HI_PROCVAR_NAME_TYPE
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_HI_PROCVAR_NAME_TYPE" ON "ACT_HI_VARINST" ("NAME_", "VAR_TYPE_") ;
--------------------------------------------------------
--  DDL for Index SYS_C0010306
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C0010306" ON "ACT_HI_VARINST" ("ID_") ;
--------------------------------------------------------
--  DDL for Index SYS_C0010318
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C0010318" ON "ACT_ID_GROUP" ("ID_") ;
--------------------------------------------------------
--  DDL for Index SYS_C0010321
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C0010321" ON "ACT_ID_INFO" ("ID_") ;
--------------------------------------------------------
--  DDL for Index SYS_C0010319
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C0010319" ON "ACT_ID_MEMBERSHIP" ("USER_ID_", "GROUP_ID_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_MEMB_GROUP
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_MEMB_GROUP" ON "ACT_ID_MEMBERSHIP" ("GROUP_ID_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_MEMB_USER
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_MEMB_USER" ON "ACT_ID_MEMBERSHIP" ("USER_ID_") ;
--------------------------------------------------------
--  DDL for Index SYS_C0010320
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C0010320" ON "ACT_ID_USER" ("ID_") ;
--------------------------------------------------------
--  DDL for Index SYS_C0010238
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C0010238" ON "ACT_RE_DEPLOYMENT" ("ID_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_MODEL_SOURCE
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_MODEL_SOURCE" ON "ACT_RE_MODEL" ("EDITOR_SOURCE_VALUE_ID_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_MODEL_SOURCE_EXTRA
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_MODEL_SOURCE_EXTRA" ON "ACT_RE_MODEL" ("EDITOR_SOURCE_EXTRA_VALUE_ID_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_MODEL_DEPLOYMENT
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_MODEL_DEPLOYMENT" ON "ACT_RE_MODEL" ("DEPLOYMENT_ID_") ;
--------------------------------------------------------
--  DDL for Index SYS_C0010240
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C0010240" ON "ACT_RE_MODEL" ("ID_") ;
--------------------------------------------------------
--  DDL for Index ACT_UNIQ_PROCDEF
--------------------------------------------------------

  CREATE UNIQUE INDEX "ACT_UNIQ_PROCDEF" ON "ACT_RE_PROCDEF" ("KEY_", "VERSION_", "TENANT_ID_") ;
--------------------------------------------------------
--  DDL for Index SYS_C0010254
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C0010254" ON "ACT_RE_PROCDEF" ("ID_") ;
--------------------------------------------------------
--  DDL for Index SYS_C0010264
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C0010264" ON "ACT_RU_EVENT_SUBSCR" ("ID_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_EVENT_SUBSCR_CONFIG_
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_EVENT_SUBSCR_CONFIG_" ON "ACT_RU_EVENT_SUBSCR" ("CONFIGURATION_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_EVENT_SUBSCR
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_EVENT_SUBSCR" ON "ACT_RU_EVENT_SUBSCR" ("EXECUTION_ID_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_EXEC_BUSKEY
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_EXEC_BUSKEY" ON "ACT_RU_EXECUTION" ("BUSINESS_KEY_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_EXE_PROCINST
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_EXE_PROCINST" ON "ACT_RU_EXECUTION" ("PROC_INST_ID_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_EXE_PARENT
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_EXE_PARENT" ON "ACT_RU_EXECUTION" ("PARENT_ID_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_EXE_SUPER
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_EXE_SUPER" ON "ACT_RU_EXECUTION" ("SUPER_EXEC_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_EXE_PROCDEF
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_EXE_PROCDEF" ON "ACT_RU_EXECUTION" ("PROC_DEF_ID_") ;
--------------------------------------------------------
--  DDL for Index SYS_C0010245
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C0010245" ON "ACT_RU_EXECUTION" ("ID_") ;
--------------------------------------------------------
--  DDL for Index SYS_C0010256
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C0010256" ON "ACT_RU_IDENTITYLINK" ("ID_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_IDENT_LNK_USER
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_IDENT_LNK_USER" ON "ACT_RU_IDENTITYLINK" ("USER_ID_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_IDENT_LNK_GROUP
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_IDENT_LNK_GROUP" ON "ACT_RU_IDENTITYLINK" ("GROUP_ID_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_TSKASS_TASK
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_TSKASS_TASK" ON "ACT_RU_IDENTITYLINK" ("TASK_ID_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_ATHRZ_PROCEDEF
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_ATHRZ_PROCEDEF" ON "ACT_RU_IDENTITYLINK" ("PROC_DEF_ID_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_IDL_PROCINST
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_IDL_PROCINST" ON "ACT_RU_IDENTITYLINK" ("PROC_INST_ID_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_JOB_EXCEPTION
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_JOB_EXCEPTION" ON "ACT_RU_JOB" ("EXCEPTION_STACK_ID_") ;
--------------------------------------------------------
--  DDL for Index SYS_C0010249
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C0010249" ON "ACT_RU_JOB" ("ID_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_TASK_CREATE
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_TASK_CREATE" ON "ACT_RU_TASK" ("CREATE_TIME_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_TASK_EXEC
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_TASK_EXEC" ON "ACT_RU_TASK" ("EXECUTION_ID_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_TASK_PROCINST
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_TASK_PROCINST" ON "ACT_RU_TASK" ("PROC_INST_ID_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_TASK_PROCDEF
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_TASK_PROCDEF" ON "ACT_RU_TASK" ("PROC_DEF_ID_") ;
--------------------------------------------------------
--  DDL for Index SYS_C0010255
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C0010255" ON "ACT_RU_TASK" ("ID_") ;
--------------------------------------------------------
--  DDL for Index SYS_C0010260
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C0010260" ON "ACT_RU_VARIABLE" ("ID_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_VARIABLE_TASK_ID
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_VARIABLE_TASK_ID" ON "ACT_RU_VARIABLE" ("TASK_ID_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_VAR_EXE
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_VAR_EXE" ON "ACT_RU_VARIABLE" ("EXECUTION_ID_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_VAR_PROCINST
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_VAR_PROCINST" ON "ACT_RU_VARIABLE" ("PROC_INST_ID_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_VAR_BYTEARRAY
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_VAR_BYTEARRAY" ON "ACT_RU_VARIABLE" ("BYTEARRAY_ID_") ;
--------------------------------------------------------
--  DDL for Index DRTS_ATTACHMENTS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "DRTS_ATTACHMENTS_PK" ON "DRTS_ATTACHMENTS" ("ID") ;
--------------------------------------------------------
--  DDL for Index DRTS_HISTORY_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "DRTS_HISTORY_PK" ON "DRTS_HISTORY" ("REQUEST_NUMBER") ;
--------------------------------------------------------
--  DDL for Index DRTS_ITERATIONS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "DRTS_ITERATIONS_PK" ON "DRTS_ITERATIONS" ("PARENT_ID", "ITERATION") ;
--------------------------------------------------------
--  DDL for Index DRTS_VIEW_COMMENTS_PERM_IDX1
--------------------------------------------------------

  CREATE INDEX "DRTS_VIEW_COMMENTS_PERM_IDX1" ON "DRTS_VIEW_COMMENTS_PERMISSION" ("REQUEST_NUMBER") ;
--------------------------------------------------------
--  Constraints for Table ACT_EVT_LOG
--------------------------------------------------------

  ALTER TABLE "ACT_EVT_LOG" ADD PRIMARY KEY ("LOG_NR_") ENABLE;
  ALTER TABLE "ACT_EVT_LOG" MODIFY ("TIME_STAMP_" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ACT_GE_BYTEARRAY
--------------------------------------------------------

  ALTER TABLE "ACT_GE_BYTEARRAY" ADD PRIMARY KEY ("ID_") ENABLE;
  ALTER TABLE "ACT_GE_BYTEARRAY" ADD CHECK (GENERATED_ IN (1,0)) ENABLE;
--------------------------------------------------------
--  Constraints for Table ACT_GE_PROPERTY
--------------------------------------------------------

  ALTER TABLE "ACT_GE_PROPERTY" ADD PRIMARY KEY ("NAME_") ENABLE;
--------------------------------------------------------
--  Constraints for Table ACT_HI_ACTINST
--------------------------------------------------------

  ALTER TABLE "ACT_HI_ACTINST" ADD PRIMARY KEY ("ID_") ENABLE;
  ALTER TABLE "ACT_HI_ACTINST" MODIFY ("START_TIME_" NOT NULL ENABLE);
  ALTER TABLE "ACT_HI_ACTINST" MODIFY ("ACT_TYPE_" NOT NULL ENABLE);
  ALTER TABLE "ACT_HI_ACTINST" MODIFY ("ACT_ID_" NOT NULL ENABLE);
  ALTER TABLE "ACT_HI_ACTINST" MODIFY ("EXECUTION_ID_" NOT NULL ENABLE);
  ALTER TABLE "ACT_HI_ACTINST" MODIFY ("PROC_INST_ID_" NOT NULL ENABLE);
  ALTER TABLE "ACT_HI_ACTINST" MODIFY ("PROC_DEF_ID_" NOT NULL ENABLE);
  ALTER TABLE "ACT_HI_ACTINST" MODIFY ("ID_" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ACT_HI_ATTACHMENT
--------------------------------------------------------

  ALTER TABLE "ACT_HI_ATTACHMENT" ADD PRIMARY KEY ("ID_") ENABLE;
  ALTER TABLE "ACT_HI_ATTACHMENT" MODIFY ("ID_" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ACT_HI_COMMENT
--------------------------------------------------------

  ALTER TABLE "ACT_HI_COMMENT" ADD PRIMARY KEY ("ID_") ENABLE;
  ALTER TABLE "ACT_HI_COMMENT" MODIFY ("TIME_" NOT NULL ENABLE);
  ALTER TABLE "ACT_HI_COMMENT" MODIFY ("ID_" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ACT_HI_DETAIL
--------------------------------------------------------

  ALTER TABLE "ACT_HI_DETAIL" ADD PRIMARY KEY ("ID_") ENABLE;
  ALTER TABLE "ACT_HI_DETAIL" MODIFY ("TIME_" NOT NULL ENABLE);
  ALTER TABLE "ACT_HI_DETAIL" MODIFY ("NAME_" NOT NULL ENABLE);
  ALTER TABLE "ACT_HI_DETAIL" MODIFY ("TYPE_" NOT NULL ENABLE);
  ALTER TABLE "ACT_HI_DETAIL" MODIFY ("ID_" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ACT_HI_IDENTITYLINK
--------------------------------------------------------

  ALTER TABLE "ACT_HI_IDENTITYLINK" ADD PRIMARY KEY ("ID_") ENABLE;
--------------------------------------------------------
--  Constraints for Table ACT_HI_PROCINST
--------------------------------------------------------

  ALTER TABLE "ACT_HI_PROCINST" ADD UNIQUE ("PROC_INST_ID_") ENABLE;
  ALTER TABLE "ACT_HI_PROCINST" ADD PRIMARY KEY ("ID_") ENABLE;
  ALTER TABLE "ACT_HI_PROCINST" MODIFY ("START_TIME_" NOT NULL ENABLE);
  ALTER TABLE "ACT_HI_PROCINST" MODIFY ("PROC_DEF_ID_" NOT NULL ENABLE);
  ALTER TABLE "ACT_HI_PROCINST" MODIFY ("PROC_INST_ID_" NOT NULL ENABLE);
  ALTER TABLE "ACT_HI_PROCINST" MODIFY ("ID_" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ACT_HI_TASKINST
--------------------------------------------------------

  ALTER TABLE "ACT_HI_TASKINST" ADD PRIMARY KEY ("ID_") ENABLE;
  ALTER TABLE "ACT_HI_TASKINST" MODIFY ("START_TIME_" NOT NULL ENABLE);
  ALTER TABLE "ACT_HI_TASKINST" MODIFY ("ID_" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ACT_HI_VARINST
--------------------------------------------------------

  ALTER TABLE "ACT_HI_VARINST" ADD PRIMARY KEY ("ID_") ENABLE;
  ALTER TABLE "ACT_HI_VARINST" MODIFY ("NAME_" NOT NULL ENABLE);
  ALTER TABLE "ACT_HI_VARINST" MODIFY ("ID_" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ACT_ID_GROUP
--------------------------------------------------------

  ALTER TABLE "ACT_ID_GROUP" ADD PRIMARY KEY ("ID_") ENABLE;
--------------------------------------------------------
--  Constraints for Table ACT_ID_INFO
--------------------------------------------------------

  ALTER TABLE "ACT_ID_INFO" ADD PRIMARY KEY ("ID_") ENABLE;
--------------------------------------------------------
--  Constraints for Table ACT_ID_MEMBERSHIP
--------------------------------------------------------

  ALTER TABLE "ACT_ID_MEMBERSHIP" ADD PRIMARY KEY ("USER_ID_", "GROUP_ID_") ENABLE;
--------------------------------------------------------
--  Constraints for Table ACT_ID_USER
--------------------------------------------------------

  ALTER TABLE "ACT_ID_USER" ADD PRIMARY KEY ("ID_") ENABLE;
--------------------------------------------------------
--  Constraints for Table ACT_RE_DEPLOYMENT
--------------------------------------------------------

  ALTER TABLE "ACT_RE_DEPLOYMENT" ADD PRIMARY KEY ("ID_") ENABLE;
--------------------------------------------------------
--  Constraints for Table ACT_RE_MODEL
--------------------------------------------------------

  ALTER TABLE "ACT_RE_MODEL" ADD PRIMARY KEY ("ID_") ENABLE;
  ALTER TABLE "ACT_RE_MODEL" MODIFY ("ID_" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ACT_RE_PROCDEF
--------------------------------------------------------

  ALTER TABLE "ACT_RE_PROCDEF" ADD CONSTRAINT "ACT_UNIQ_PROCDEF" UNIQUE ("KEY_", "VERSION_", "TENANT_ID_") ENABLE;
  ALTER TABLE "ACT_RE_PROCDEF" ADD PRIMARY KEY ("ID_") ENABLE;
  ALTER TABLE "ACT_RE_PROCDEF" ADD CHECK (HAS_START_FORM_KEY_ IN (1,0)) ENABLE;
  ALTER TABLE "ACT_RE_PROCDEF" MODIFY ("VERSION_" NOT NULL ENABLE);
  ALTER TABLE "ACT_RE_PROCDEF" MODIFY ("KEY_" NOT NULL ENABLE);
  ALTER TABLE "ACT_RE_PROCDEF" MODIFY ("ID_" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ACT_RU_EVENT_SUBSCR
--------------------------------------------------------

  ALTER TABLE "ACT_RU_EVENT_SUBSCR" ADD PRIMARY KEY ("ID_") ENABLE;
  ALTER TABLE "ACT_RU_EVENT_SUBSCR" MODIFY ("CREATED_" NOT NULL ENABLE);
  ALTER TABLE "ACT_RU_EVENT_SUBSCR" MODIFY ("EVENT_TYPE_" NOT NULL ENABLE);
  ALTER TABLE "ACT_RU_EVENT_SUBSCR" MODIFY ("ID_" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ACT_RU_EXECUTION
--------------------------------------------------------

  ALTER TABLE "ACT_RU_EXECUTION" ADD PRIMARY KEY ("ID_") ENABLE;
  ALTER TABLE "ACT_RU_EXECUTION" ADD CHECK (IS_EVENT_SCOPE_ IN (1,0)) ENABLE;
  ALTER TABLE "ACT_RU_EXECUTION" ADD CHECK (IS_SCOPE_ IN (1,0)) ENABLE;
  ALTER TABLE "ACT_RU_EXECUTION" ADD CHECK (IS_CONCURRENT_ IN (1,0)) ENABLE;
  ALTER TABLE "ACT_RU_EXECUTION" ADD CHECK (IS_ACTIVE_ IN (1,0)) ENABLE;
--------------------------------------------------------
--  Constraints for Table ACT_RU_IDENTITYLINK
--------------------------------------------------------

  ALTER TABLE "ACT_RU_IDENTITYLINK" ADD PRIMARY KEY ("ID_") ENABLE;
--------------------------------------------------------
--  Constraints for Table ACT_RU_JOB
--------------------------------------------------------

  ALTER TABLE "ACT_RU_JOB" ADD PRIMARY KEY ("ID_") ENABLE;
  ALTER TABLE "ACT_RU_JOB" ADD CHECK (EXCLUSIVE_ IN (1,0)) ENABLE;
  ALTER TABLE "ACT_RU_JOB" MODIFY ("TYPE_" NOT NULL ENABLE);
  ALTER TABLE "ACT_RU_JOB" MODIFY ("ID_" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ACT_RU_TASK
--------------------------------------------------------

  ALTER TABLE "ACT_RU_TASK" ADD PRIMARY KEY ("ID_") ENABLE;
--------------------------------------------------------
--  Constraints for Table ACT_RU_VARIABLE
--------------------------------------------------------

  ALTER TABLE "ACT_RU_VARIABLE" ADD PRIMARY KEY ("ID_") ENABLE;
  ALTER TABLE "ACT_RU_VARIABLE" MODIFY ("NAME_" NOT NULL ENABLE);
  ALTER TABLE "ACT_RU_VARIABLE" MODIFY ("TYPE_" NOT NULL ENABLE);
  ALTER TABLE "ACT_RU_VARIABLE" MODIFY ("ID_" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table DRTS_ATTACHMENTS
--------------------------------------------------------

  ALTER TABLE "DRTS_ATTACHMENTS" ADD CONSTRAINT "DRTS_ATTACHMENTS_PK" PRIMARY KEY ("ID") ENABLE;
  ALTER TABLE "DRTS_ATTACHMENTS" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "DRTS_ATTACHMENTS" MODIFY ("FILE_SIZE" NOT NULL ENABLE);
  ALTER TABLE "DRTS_ATTACHMENTS" MODIFY ("FILE_CONTENT" NOT NULL ENABLE);
  ALTER TABLE "DRTS_ATTACHMENTS" MODIFY ("FILE_NAME" NOT NULL ENABLE);
  ALTER TABLE "DRTS_ATTACHMENTS" MODIFY ("FILE_TYPE" NOT NULL ENABLE);
  ALTER TABLE "DRTS_ATTACHMENTS" MODIFY ("REQUEST_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table DRTS_AUDIT
--------------------------------------------------------

  ALTER TABLE "DRTS_AUDIT" MODIFY ("REQUEST_NUMBER" NOT NULL ENABLE);
  ALTER TABLE "DRTS_AUDIT" MODIFY ("AUDIT_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table DRTS_HISTORY
--------------------------------------------------------

  ALTER TABLE "DRTS_HISTORY" ADD CONSTRAINT "DRTS_HISTORY_PK" PRIMARY KEY ("REQUEST_NUMBER") ENABLE;
  ALTER TABLE "DRTS_HISTORY" MODIFY ("DRT_REQUEST_DATE" NOT NULL ENABLE);
  ALTER TABLE "DRTS_HISTORY" MODIFY ("REQUEST_NUMBER" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table DRTS_ITERATIONS
--------------------------------------------------------

  ALTER TABLE "DRTS_ITERATIONS" ADD CONSTRAINT "DRTS_ITERATIONS_PK" PRIMARY KEY ("PARENT_ID", "ITERATION") ENABLE;
  ALTER TABLE "DRTS_ITERATIONS" MODIFY ("CHILD_ID" NOT NULL ENABLE);
  ALTER TABLE "DRTS_ITERATIONS" MODIFY ("ITERATION" NOT NULL ENABLE);
  ALTER TABLE "DRTS_ITERATIONS" MODIFY ("PARENT_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table DRTS_VIEW_COMMENTS_PERMISSION
--------------------------------------------------------

  ALTER TABLE "DRTS_VIEW_COMMENTS_PERMISSION" ADD CONSTRAINT "DRTS_VIEW_COMMENTS_PERMISS_PK" PRIMARY KEY ("REQUEST_NUMBER") ENABLE;
  ALTER TABLE "DRTS_VIEW_COMMENTS_PERMISSION" MODIFY ("REQUEST_NUMBER" NOT NULL ENABLE);
--------------------------------------------------------
--  Ref Constraints for Table ACT_GE_BYTEARRAY
--------------------------------------------------------

  ALTER TABLE "ACT_GE_BYTEARRAY" ADD CONSTRAINT "ACT_FK_BYTEARR_DEPL" FOREIGN KEY ("DEPLOYMENT_ID_") REFERENCES "ACT_RE_DEPLOYMENT" ("ID_") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table ACT_ID_MEMBERSHIP
--------------------------------------------------------

  ALTER TABLE "ACT_ID_MEMBERSHIP" ADD CONSTRAINT "ACT_FK_MEMB_GROUP" FOREIGN KEY ("GROUP_ID_") REFERENCES "ACT_ID_GROUP" ("ID_") ENABLE;
  ALTER TABLE "ACT_ID_MEMBERSHIP" ADD CONSTRAINT "ACT_FK_MEMB_USER" FOREIGN KEY ("USER_ID_") REFERENCES "ACT_ID_USER" ("ID_") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table ACT_RE_MODEL
--------------------------------------------------------

  ALTER TABLE "ACT_RE_MODEL" ADD CONSTRAINT "ACT_FK_MODEL_DEPLOYMENT" FOREIGN KEY ("DEPLOYMENT_ID_") REFERENCES "ACT_RE_DEPLOYMENT" ("ID_") ENABLE;
  ALTER TABLE "ACT_RE_MODEL" ADD CONSTRAINT "ACT_FK_MODEL_SOURCE" FOREIGN KEY ("EDITOR_SOURCE_VALUE_ID_") REFERENCES "ACT_GE_BYTEARRAY" ("ID_") ENABLE;
  ALTER TABLE "ACT_RE_MODEL" ADD CONSTRAINT "ACT_FK_MODEL_SOURCE_EXTRA" FOREIGN KEY ("EDITOR_SOURCE_EXTRA_VALUE_ID_") REFERENCES "ACT_GE_BYTEARRAY" ("ID_") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table ACT_RU_EVENT_SUBSCR
--------------------------------------------------------

  ALTER TABLE "ACT_RU_EVENT_SUBSCR" ADD CONSTRAINT "ACT_FK_EVENT_EXEC" FOREIGN KEY ("EXECUTION_ID_") REFERENCES "ACT_RU_EXECUTION" ("ID_") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table ACT_RU_EXECUTION
--------------------------------------------------------

  ALTER TABLE "ACT_RU_EXECUTION" ADD CONSTRAINT "ACT_FK_EXE_PARENT" FOREIGN KEY ("PARENT_ID_") REFERENCES "ACT_RU_EXECUTION" ("ID_") ENABLE;
  ALTER TABLE "ACT_RU_EXECUTION" ADD CONSTRAINT "ACT_FK_EXE_PROCDEF" FOREIGN KEY ("PROC_DEF_ID_") REFERENCES "ACT_RE_PROCDEF" ("ID_") ENABLE;
  ALTER TABLE "ACT_RU_EXECUTION" ADD CONSTRAINT "ACT_FK_EXE_PROCINST" FOREIGN KEY ("PROC_INST_ID_") REFERENCES "ACT_RU_EXECUTION" ("ID_") ENABLE;
  ALTER TABLE "ACT_RU_EXECUTION" ADD CONSTRAINT "ACT_FK_EXE_SUPER" FOREIGN KEY ("SUPER_EXEC_") REFERENCES "ACT_RU_EXECUTION" ("ID_") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table ACT_RU_IDENTITYLINK
--------------------------------------------------------

  ALTER TABLE "ACT_RU_IDENTITYLINK" ADD CONSTRAINT "ACT_FK_ATHRZ_PROCEDEF" FOREIGN KEY ("PROC_DEF_ID_") REFERENCES "ACT_RE_PROCDEF" ("ID_") ENABLE;
  ALTER TABLE "ACT_RU_IDENTITYLINK" ADD CONSTRAINT "ACT_FK_IDL_PROCINST" FOREIGN KEY ("PROC_INST_ID_") REFERENCES "ACT_RU_EXECUTION" ("ID_") ENABLE;
  ALTER TABLE "ACT_RU_IDENTITYLINK" ADD CONSTRAINT "ACT_FK_TSKASS_TASK" FOREIGN KEY ("TASK_ID_") REFERENCES "ACT_RU_TASK" ("ID_") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table ACT_RU_JOB
--------------------------------------------------------

  ALTER TABLE "ACT_RU_JOB" ADD CONSTRAINT "ACT_FK_JOB_EXCEPTION" FOREIGN KEY ("EXCEPTION_STACK_ID_") REFERENCES "ACT_GE_BYTEARRAY" ("ID_") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table ACT_RU_TASK
--------------------------------------------------------

  ALTER TABLE "ACT_RU_TASK" ADD CONSTRAINT "ACT_FK_TASK_EXE" FOREIGN KEY ("EXECUTION_ID_") REFERENCES "ACT_RU_EXECUTION" ("ID_") ENABLE;
  ALTER TABLE "ACT_RU_TASK" ADD CONSTRAINT "ACT_FK_TASK_PROCDEF" FOREIGN KEY ("PROC_DEF_ID_") REFERENCES "ACT_RE_PROCDEF" ("ID_") ENABLE;
  ALTER TABLE "ACT_RU_TASK" ADD CONSTRAINT "ACT_FK_TASK_PROCINST" FOREIGN KEY ("PROC_INST_ID_") REFERENCES "ACT_RU_EXECUTION" ("ID_") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table ACT_RU_VARIABLE
--------------------------------------------------------

  ALTER TABLE "ACT_RU_VARIABLE" ADD CONSTRAINT "ACT_FK_VAR_BYTEARRAY" FOREIGN KEY ("BYTEARRAY_ID_") REFERENCES "ACT_GE_BYTEARRAY" ("ID_") ENABLE;
  ALTER TABLE "ACT_RU_VARIABLE" ADD CONSTRAINT "ACT_FK_VAR_EXE" FOREIGN KEY ("EXECUTION_ID_") REFERENCES "ACT_RU_EXECUTION" ("ID_") ENABLE;
  ALTER TABLE "ACT_RU_VARIABLE" ADD CONSTRAINT "ACT_FK_VAR_PROCINST" FOREIGN KEY ("PROC_INST_ID_") REFERENCES "ACT_RU_EXECUTION" ("ID_") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table DRTS_VIEW_COMMENTS_PERMISSION
--------------------------------------------------------

  ALTER TABLE "DRTS_VIEW_COMMENTS_PERMISSION" ADD CONSTRAINT "DRTS_VIEW_COMMENTS_PERMIS_FK1" FOREIGN KEY ("REQUEST_NUMBER") REFERENCES "DRTS_HISTORY" ("REQUEST_NUMBER") ON DELETE CASCADE ENABLE;
