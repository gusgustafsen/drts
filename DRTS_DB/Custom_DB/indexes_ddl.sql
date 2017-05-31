--------------------------------------------------------
--  File created - Wednesday-January-25-2017   
--------------------------------------------------------
DROP INDEX "DRTS_ATTACHMENTS_PK";
DROP INDEX "DRTS_HISTORY_PK";
DROP INDEX "DRTS_ITERATIONS_PK";
DROP INDEX "DRTS_VIEW_COMMENTS_PERM_IDX1";
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
