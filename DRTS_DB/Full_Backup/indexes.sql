--------------------------------------------------------
--  File created - Wednesday-January-25-2017   
--------------------------------------------------------
DROP INDEX "ACT_IDX_ATHRZ_PROCEDEF";
DROP INDEX "ACT_IDX_BYTEAR_DEPL";
DROP INDEX "ACT_IDX_EVENT_SUBSCR";
DROP INDEX "ACT_IDX_EVENT_SUBSCR_CONFIG_";
DROP INDEX "ACT_IDX_EXE_PARENT";
DROP INDEX "ACT_IDX_EXE_PROCDEF";
DROP INDEX "ACT_IDX_EXE_PROCINST";
DROP INDEX "ACT_IDX_EXE_SUPER";
DROP INDEX "ACT_IDX_EXEC_BUSKEY";
DROP INDEX "ACT_IDX_HI_ACT_INST_END";
DROP INDEX "ACT_IDX_HI_ACT_INST_EXEC";
DROP INDEX "ACT_IDX_HI_ACT_INST_PROCINST";
DROP INDEX "ACT_IDX_HI_ACT_INST_START";
DROP INDEX "ACT_IDX_HI_DETAIL_ACT_INST";
DROP INDEX "ACT_IDX_HI_DETAIL_NAME";
DROP INDEX "ACT_IDX_HI_DETAIL_PROC_INST";
DROP INDEX "ACT_IDX_HI_DETAIL_TASK_ID";
DROP INDEX "ACT_IDX_HI_DETAIL_TIME";
DROP INDEX "ACT_IDX_HI_IDENT_LNK_PROCINST";
DROP INDEX "ACT_IDX_HI_IDENT_LNK_TASK";
DROP INDEX "ACT_IDX_HI_IDENT_LNK_USER";
DROP INDEX "ACT_IDX_HI_PRO_I_BUSKEY";
DROP INDEX "ACT_IDX_HI_PRO_INST_END";
DROP INDEX "ACT_IDX_HI_PROCVAR_NAME_TYPE";
DROP INDEX "ACT_IDX_HI_PROCVAR_PROC_INST";
DROP INDEX "ACT_IDX_IDENT_LNK_GROUP";
DROP INDEX "ACT_IDX_IDENT_LNK_USER";
DROP INDEX "ACT_IDX_IDL_PROCINST";
DROP INDEX "ACT_IDX_JOB_EXCEPTION";
DROP INDEX "ACT_IDX_MEMB_GROUP";
DROP INDEX "ACT_IDX_MEMB_USER";
DROP INDEX "ACT_IDX_MODEL_DEPLOYMENT";
DROP INDEX "ACT_IDX_MODEL_SOURCE";
DROP INDEX "ACT_IDX_MODEL_SOURCE_EXTRA";
DROP INDEX "ACT_IDX_TASK_CREATE";
DROP INDEX "ACT_IDX_TASK_EXEC";
DROP INDEX "ACT_IDX_TASK_PROCDEF";
DROP INDEX "ACT_IDX_TASK_PROCINST";
DROP INDEX "ACT_IDX_TSKASS_TASK";
DROP INDEX "ACT_IDX_VAR_BYTEARRAY";
DROP INDEX "ACT_IDX_VAR_EXE";
DROP INDEX "ACT_IDX_VAR_PROCINST";
DROP INDEX "ACT_IDX_VARIABLE_TASK_ID";
DROP INDEX "ACT_UNIQ_PROCDEF";
DROP INDEX "DRTS_ATTACHMENTS_PK";
DROP INDEX "DRTS_HISTORY_PK";
DROP INDEX "DRTS_ITERATIONS_PK";
DROP INDEX "DRTS_VIEW_COMMENTS_PERM_IDX1";
--------------------------------------------------------
--  DDL for Index ACT_IDX_ATHRZ_PROCEDEF
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_ATHRZ_PROCEDEF" ON "ACT_RU_IDENTITYLINK" ("PROC_DEF_ID_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_BYTEAR_DEPL
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_BYTEAR_DEPL" ON "ACT_GE_BYTEARRAY" ("DEPLOYMENT_ID_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_EVENT_SUBSCR
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_EVENT_SUBSCR" ON "ACT_RU_EVENT_SUBSCR" ("EXECUTION_ID_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_EVENT_SUBSCR_CONFIG_
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_EVENT_SUBSCR_CONFIG_" ON "ACT_RU_EVENT_SUBSCR" ("CONFIGURATION_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_EXE_PARENT
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_EXE_PARENT" ON "ACT_RU_EXECUTION" ("PARENT_ID_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_EXE_PROCDEF
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_EXE_PROCDEF" ON "ACT_RU_EXECUTION" ("PROC_DEF_ID_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_EXE_PROCINST
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_EXE_PROCINST" ON "ACT_RU_EXECUTION" ("PROC_INST_ID_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_EXE_SUPER
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_EXE_SUPER" ON "ACT_RU_EXECUTION" ("SUPER_EXEC_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_EXEC_BUSKEY
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_EXEC_BUSKEY" ON "ACT_RU_EXECUTION" ("BUSINESS_KEY_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_HI_ACT_INST_END
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_HI_ACT_INST_END" ON "ACT_HI_ACTINST" ("END_TIME_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_HI_ACT_INST_EXEC
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_HI_ACT_INST_EXEC" ON "ACT_HI_ACTINST" ("EXECUTION_ID_", "ACT_ID_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_HI_ACT_INST_PROCINST
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_HI_ACT_INST_PROCINST" ON "ACT_HI_ACTINST" ("PROC_INST_ID_", "ACT_ID_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_HI_ACT_INST_START
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_HI_ACT_INST_START" ON "ACT_HI_ACTINST" ("START_TIME_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_HI_DETAIL_ACT_INST
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_HI_DETAIL_ACT_INST" ON "ACT_HI_DETAIL" ("ACT_INST_ID_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_HI_DETAIL_NAME
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_HI_DETAIL_NAME" ON "ACT_HI_DETAIL" ("NAME_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_HI_DETAIL_PROC_INST
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_HI_DETAIL_PROC_INST" ON "ACT_HI_DETAIL" ("PROC_INST_ID_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_HI_DETAIL_TASK_ID
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_HI_DETAIL_TASK_ID" ON "ACT_HI_DETAIL" ("TASK_ID_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_HI_DETAIL_TIME
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_HI_DETAIL_TIME" ON "ACT_HI_DETAIL" ("TIME_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_HI_IDENT_LNK_PROCINST
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_HI_IDENT_LNK_PROCINST" ON "ACT_HI_IDENTITYLINK" ("PROC_INST_ID_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_HI_IDENT_LNK_TASK
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_HI_IDENT_LNK_TASK" ON "ACT_HI_IDENTITYLINK" ("TASK_ID_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_HI_IDENT_LNK_USER
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_HI_IDENT_LNK_USER" ON "ACT_HI_IDENTITYLINK" ("USER_ID_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_HI_PRO_I_BUSKEY
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_HI_PRO_I_BUSKEY" ON "ACT_HI_PROCINST" ("BUSINESS_KEY_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_HI_PRO_INST_END
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_HI_PRO_INST_END" ON "ACT_HI_PROCINST" ("END_TIME_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_HI_PROCVAR_NAME_TYPE
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_HI_PROCVAR_NAME_TYPE" ON "ACT_HI_VARINST" ("NAME_", "VAR_TYPE_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_HI_PROCVAR_PROC_INST
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_HI_PROCVAR_PROC_INST" ON "ACT_HI_VARINST" ("PROC_INST_ID_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_IDENT_LNK_GROUP
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_IDENT_LNK_GROUP" ON "ACT_RU_IDENTITYLINK" ("GROUP_ID_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_IDENT_LNK_USER
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_IDENT_LNK_USER" ON "ACT_RU_IDENTITYLINK" ("USER_ID_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_IDL_PROCINST
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_IDL_PROCINST" ON "ACT_RU_IDENTITYLINK" ("PROC_INST_ID_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_JOB_EXCEPTION
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_JOB_EXCEPTION" ON "ACT_RU_JOB" ("EXCEPTION_STACK_ID_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_MEMB_GROUP
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_MEMB_GROUP" ON "ACT_ID_MEMBERSHIP" ("GROUP_ID_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_MEMB_USER
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_MEMB_USER" ON "ACT_ID_MEMBERSHIP" ("USER_ID_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_MODEL_DEPLOYMENT
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_MODEL_DEPLOYMENT" ON "ACT_RE_MODEL" ("DEPLOYMENT_ID_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_MODEL_SOURCE
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_MODEL_SOURCE" ON "ACT_RE_MODEL" ("EDITOR_SOURCE_VALUE_ID_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_MODEL_SOURCE_EXTRA
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_MODEL_SOURCE_EXTRA" ON "ACT_RE_MODEL" ("EDITOR_SOURCE_EXTRA_VALUE_ID_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_TASK_CREATE
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_TASK_CREATE" ON "ACT_RU_TASK" ("CREATE_TIME_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_TASK_EXEC
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_TASK_EXEC" ON "ACT_RU_TASK" ("EXECUTION_ID_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_TASK_PROCDEF
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_TASK_PROCDEF" ON "ACT_RU_TASK" ("PROC_DEF_ID_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_TASK_PROCINST
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_TASK_PROCINST" ON "ACT_RU_TASK" ("PROC_INST_ID_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_TSKASS_TASK
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_TSKASS_TASK" ON "ACT_RU_IDENTITYLINK" ("TASK_ID_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_VAR_BYTEARRAY
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_VAR_BYTEARRAY" ON "ACT_RU_VARIABLE" ("BYTEARRAY_ID_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_VAR_EXE
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_VAR_EXE" ON "ACT_RU_VARIABLE" ("EXECUTION_ID_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_VAR_PROCINST
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_VAR_PROCINST" ON "ACT_RU_VARIABLE" ("PROC_INST_ID_") ;
--------------------------------------------------------
--  DDL for Index ACT_IDX_VARIABLE_TASK_ID
--------------------------------------------------------

  CREATE INDEX "ACT_IDX_VARIABLE_TASK_ID" ON "ACT_RU_VARIABLE" ("TASK_ID_") ;
--------------------------------------------------------
--  DDL for Index ACT_UNIQ_PROCDEF
--------------------------------------------------------

  CREATE UNIQUE INDEX "ACT_UNIQ_PROCDEF" ON "ACT_RE_PROCDEF" ("KEY_", "VERSION_", "TENANT_ID_") ;
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
