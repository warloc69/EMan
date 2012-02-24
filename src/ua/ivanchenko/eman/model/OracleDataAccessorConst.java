package ua.ivanchenko.eman.model;

public class OracleDataAccessorConst {
    //dept 
    public static final String ADD_DEPT = "INSERT INTO DEPARTMENTS(ID,TITLE,DESCRIPTION) VALUES(getid(),?,?)";
    public static final String GET_ALL_DEPTS = "SELECT ID,TITLE,DESCRIPTION FROM DEPARTMENTS";
    public static final String GET_DEPTS_BY_ID = "SELECT ID,TITLE,DESCRIPTION FROM DEPARTMENTS WHERE ID=?";
    public static final String GET_DEPTS_BY_TITLE = "SELECT ID,TITLE,DESCRIPTION FROM DEPARTMENTS WHERE TITLE=?";
    public static final String REMOVE_DEPT = "DELETE FROM DEPARTMENTS WHERE ID=?";
    public static final String UPDATE_DEPT = "UPDATE DEPARTMENTS SET TITLE=?,DESCRIPTION=? where id=?";
    //office
    public static final String ADD_OFFICE = "INSERT INTO OFFICES(ID,TITLE,ADDRESS,MANAGER) VALUES(getid(),?,?,?)";
    public static final String GET_ALL_OFFICES = "SELECT ID,TITLE,ADDRESS,MANAGER FROM OFFICES";
    public static final String GET_OFFICE_BY_ID = "SELECT ID,TITLE,ADDRESS,MANAGER FROM OFFICES WHERE ID=?";
    public static final String GET_OFFICE_BY_TITTLE = "SELECT ID,TITLE,ADDRESS,MANAGER FROM OFFICES WHERE TITLE=?";
    public static final String REMOVE_OFFICE = "DELETE FROM OFFICES WHERE ID=?";
    public static final String UPDATE_OFFICE = "UPDATE OFFICES SET TITLE=?,ADDRESS=?,MANAGER=? WHERE ID=?";
    //worker
    public static final String GET_PATH = "select ID,LASTNAME from employees connect by id = prior mgrid start with id = ? order by level desc";
    public static final String GET_ALL_TOPMANAGER = "SELECT ID,FIRSTNAME,LASTNAME,MGRID,DEPARTMENT_ID,JOB_ID,OFFICE_ID,SALEGRADE FROM EMPLOYEES WHERE MGRID IS NULL";
    public static final String ADD_WORKER = "INSERT INTO EMPLOYEES(ID,FIRSTNAME,LASTNAME,MGRID,DEPARTMENT_ID,JOB_ID,OFFICE_ID,SALEGRADE) VALUES(getid(),?,?,?,?,?,?,?)";
    public static final String GET_ALL_WORKERS = "SELECT ID,FIRSTNAME,LASTNAME,MGRID,DEPARTMENT_ID,JOB_ID,OFFICE_ID,SALEGRADE FROM EMPLOYEES";
    public static final String GET_WORKER_BY_ID = "SELECT ID,FIRSTNAME,LASTNAME,MGRID,DEPARTMENT_ID,JOB_ID,OFFICE_ID,SALEGRADE FROM EMPLOYEES WHERE ID=?";
    public static final String GET_WORKER_BY_LAST_NAME = "SELECT ID,FIRSTNAME,LASTNAME,MGRID,DEPARTMENT_ID,JOB_ID,OFFICE_ID,SALEGRADE FROM EMPLOYEES WHERE LASTNAME=?";
    public static final String GET_WORKER_BY_MGR_ID = "SELECT ID,FIRSTNAME,LASTNAME,MGRID,DEPARTMENT_ID,JOB_ID,OFFICE_ID,SALEGRADE FROM EMPLOYEES WHERE MGRID=?";    
    public static final String REMOVE_WORKER = "DELETE FROM EMPLOYEES WHERE ID=?";
    public static final String UPDATE_WORKER = "UPDATE EMPLOYEES SET FIRSTNAME=?,LASTNAME=?,MGRID=?,DEPARTMENT_ID=?,JOB_ID=?,OFFICE_ID=?,SALEGRADE=? WHERE ID=?";
    //job
    public static final String ADD_JOB = "INSERT INTO JOBS(ID,TITLE,DESCRIPTION) VALUES(getid(),?,?)";
    public static final String GET_ALL_JOBS = "SELECT ID,TITLE,DESCRIPTION FROM JOBS";
    public static final String GET_JOB_BY_ID = "SELECT ID,TITLE,DESCRIPTION FROM JOBS WHERE ID=?";
    public static final String GET_JOB_BY_TITLE = "SELECT ID,TITLE,DESCRIPTION FROM JOBS WHERE TITLE=?";
    public static final String REMOVE_JOB = "DELETE FROM JOBS WHERE ID=?";
    public static final String UPDATE_JOB = "UPDATE JOBS SET TITLE=?,DESCRIPTION=? WHERE ID=?";
    public static final String DATA_SOURCE = "java:comp/env/jdbc/eman_oracle";
    
}
