--------------------------------------------------------
--  File created - Wednesday-January-25-2017   
--------------------------------------------------------
DROP FUNCTION "NEW_UUID";
DROP FUNCTION "RANDOMUUID";
--------------------------------------------------------
--  DDL for Function NEW_UUID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "NEW_UUID" RETURN VARCHAR2 AS
  l_seed        BINARY_INTEGER;
  l_random_num  NUMBER(5);

  l_date        VARCHAR2(25);
  l_random      VARCHAR2(4);
  l_ip_address  VARCHAR2(12);
BEGIN
  l_seed := TO_NUMBER(TO_CHAR(SYSDATE,'YYYYDDMMSS'));
  DBMS_RANDOM.initialize (val => l_seed);
  l_random_num := TRUNC(DBMS_RANDOM.value(low => 1, high => 65535));
  DBMS_RANDOM.terminate;

  l_date       := conversion_api.to_hex(TO_NUMBER(TO_CHAR(SYSTIMESTAMP,'FFSSMIHH24DDMMYYYY')));
  l_random     := RPAD(conversion_api.to_hex(l_random_num), 4, '0');
  l_ip_address := conversion_api.to_hex(TO_NUMBER(REPLACE(NVL(SYS_CONTEXT('USERENV','IP_ADDRESS'), '123.123.123.123'), '.', '')));

  RETURN SUBSTR(l_date, 1, 8)                     || '-' ||
         SUBSTR(l_date, 9, 4)                     || '-' ||
         SUBSTR(l_date, 13, 4)                    || '-' ||
         RPAD(SUBSTR(l_date, 17), 4, '0')         || '-' ||
         RPAD(l_random || l_ip_address, 12, '0');
END;

/
--------------------------------------------------------
--  DDL for Function RANDOMUUID
--------------------------------------------------------

  CREATE OR REPLACE FUNCTION "RANDOMUUID" 
RETURN VARCHAR2
AS LANGUAGE JAVA
NAME 'RandomUUID.create() return java.lang.String';

/
