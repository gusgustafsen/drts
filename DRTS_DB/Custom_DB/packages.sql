--------------------------------------------------------
--  File created - Wednesday-January-25-2017   
--------------------------------------------------------
DROP PACKAGE "CONVERSION_API";
--------------------------------------------------------
--  DDL for Package CONVERSION_API
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE "CONVERSION_API" AS
-- --------------------------------------------------------------------------
-- Name         : https://oracle-base.com/dba/miscellaneous/conversion_api.sql
-- Author       : Tim Hall
-- Description  : Provides some base conversion functions.
-- Ammedments   :
--   When         Who       What
--   ===========  ========  =================================================
--   10-SEP-2003  Tim Hall  Initial Creation
-- --------------------------------------------------------------------------

FUNCTION to_base(p_dec   IN  NUMBER,
                 p_base  IN  NUMBER) RETURN VARCHAR2;

FUNCTION to_dec (p_str        IN  VARCHAR2,
                 p_from_base  IN  NUMBER DEFAULT 16) RETURN NUMBER;

FUNCTION to_hex(p_dec  IN  NUMBER) RETURN VARCHAR2;

FUNCTION to_bin(p_dec  IN  NUMBER) RETURN VARCHAR2;

FUNCTION to_oct(p_dec  IN  NUMBER) RETURN VARCHAR2;

END conversion_api;

/
