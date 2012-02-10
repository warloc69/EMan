drop table emp;
drop table office;
drop table dept;
drop table jobs;
drop sequence counter;
create table emp (
	ID  NUMBER PRIMARY KEY NOT NULL,
	FIRSTNAME VARCHAR2(50),
	LASTNAME VARCHAR2(50),
	MGRID NUMBER(17,0),
	DEPARTMENT_ID NUMBER(17,0),
	JOB_ID NUMBER(17,0),
	OFFICE_ID NUMBER(17,0),
	SALEGRADE NUMBER(10,2)
);
CREATE TABLE OFFICE (
	ID NUMBER(16) PRIMARY KEY NOT NULL,
	TITLE VARCHAR2(100),
	ADDRESS VARCHAR2(1000),
	MANAGER NUMBER(17,0)
);
CREATE TABLE JOBS (
	ID NUMBER PRIMARY KEY NOT NULL,
	TITLE VARCHAR2(300),
	DESCRIPTION VARCHAR2(1000)
);
CREATE TABLE DEPT (
	ID NUMBER PRIMARY KEY NOT NULL,
	TITLE VARCHAR2(300),
	DESCRIPTION VARCHAR2(1000)
);
ALTER TABLE EMP ADD CONSTRAINT ID_JOB FOREIGN KEY(JOB_ID) REFERENCES JOBS(ID);
ALTER TABLE EMP ADD CONSTRAINT ID_DEP FOREIGN KEY(DEPARTMENT_ID) REFERENCES DEPT(ID);
ALTER TABLE EMP ADD CONSTRAINT ID_LOC FOREIGN KEY(OFFICE_ID) REFERENCES OFFICE(ID);
CREATE SEQUENCE COUNTER 
  MINVALUE 1
  MAXVALUE 9999
  START WITH 1
  INCREMENT BY 1
  CYCLE;
CREATE OR REPLACE FUNCTION getid RETURN VARCHAR2 is
  IDCHAR VARCHAR2(17);
BEGIN 
  SELECT TO_CHAR(SYSDATE,'yyyymmddSSSS')||TO_CHAR(COUNTER.NEXTVAL,'FM0000') into IDCHAR FROM dual;
  RETURN IDCHAR;
END getid;
/
SHOW ERROR