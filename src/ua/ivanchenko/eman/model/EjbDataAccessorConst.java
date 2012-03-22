package ua.ivanchenko.eman.model;

public class EjbDataAccessorConst {
    //dept
    public static final String ADD_DEPT = "INSERT INTO DEPARTMENTS(ID,TITLE,DESCRIPTION) VALUES(?,?,?)";
    public static final String GET_ID = "SELECT getid() from dual";
    public static final String GET_ALL_DEPTS = "SELECT ID FROM DEPARTMENTS";
    public static final String GET_DEPTS_BY_ID = "SELECT TITLE,DESCRIPTION FROM DEPARTMENTS WHERE ID=?";
    public static final String GET_DEPTS_BY_TITLE = "SELECT ID,TITLE,DESCRIPTION FROM DEPARTMENTS WHERE TITLE=?";
    public static final String REMOVE_DEPT = "DELETE FROM DEPARTMENTS WHERE ID=?";
    public static final String UPDATE_DEPT = "UPDATE DEPARTMENTS SET TITLE=?,DESCRIPTION=? where id=?";
    public static final String GET_DEPT_PRIMARY_KEY_BY_ID = "SELECT ID FROM DEPARTMENTS WHERE ID=?";
    public static final String GET_DEPT_PRIMARY_KEY_BY_TITLE = "SELECT ID FROM DEPARTMENTS WHERE TITLE=?";
    
    //office
    public static final String ADD_OFFICE = "INSERT INTO OFFICES(ID,TITLE,ADDRESS,MANAGER) VALUES(?,?,?,?)";
    public static final String GET_ALL_OFFICES = "SELECT ID FROM OFFICES";
    public static final String GET_OFFICE_BY_ID = "SELECT TITLE,ADDRESS,MANAGER FROM OFFICES WHERE ID=?";
    public static final String GET_OFFICE_BY_TITTLE = "SELECT ID,TITLE,ADDRESS,MANAGER FROM OFFICES WHERE TITLE=?";
    public static final String REMOVE_OFFICE = "DELETE FROM OFFICES WHERE ID=?";
    public static final String UPDATE_OFFICE = "UPDATE OFFICES SET TITLE=?,ADDRESS=?,MANAGER=? WHERE ID=?";
    public static final String GET_OFFICE_PRIMARY_KEY_BY_ID = "SELECT ID FROM OFFICES WHERE ID=?";
    public static final String GET_OFFICE_PRIMARY_KEY_BY_TITLE = "SELECT ID FROM OFFICES WHERE TITLE=?";
    //worker
    public static final String GET_PATH = "select ID from employees connect by id = prior mgrid start with id = ? order by level desc";
    public static final String ADD_WORKER = "INSERT INTO EMPLOYEES(ID,FIRSTNAME,LASTNAME,MGRID,DEPARTMENT_ID,JOB_ID,OFFICE_ID,SALEGRADE) VALUES(?,?,?,?,?,?,?,?)";
    public static final String GET_WORKER_BY_ID = "SELECT FIRSTNAME,LASTNAME,MGRID,DEPARTMENT_ID,JOB_ID,OFFICE_ID,SALEGRADE FROM EMPLOYEES WHERE ID=?";
    public static final String GET_WORKER_BY_MGR_ID = "SELECT FIRSTNAME,LASTNAME,MGRID,DEPARTMENT_ID,JOB_ID,OFFICE_ID,SALEGRADE FROM EMPLOYEES WHERE MGRID=?";
    public static final String GET_WORKER_BY_JOB_ID = "SELECT ID,FIRSTNAME,LASTNAME,MGRID,DEPARTMENT_ID,JOB_ID,OFFICE_ID,SALEGRADE FROM EMPLOYEES WHERE JOB_ID=?";
    public static final String GET_WORKER_BY_DEPT_ID = "SELECT ID,FIRSTNAME,LASTNAME,MGRID,DEPARTMENT_ID,JOB_ID,OFFICE_ID,SALEGRADE FROM EMPLOYEES WHERE DEPARTMENT_ID=?";
    public static final String GET_WORKER_BY_OFFICE_ID = "SELECT ID,FIRSTNAME,LASTNAME,MGRID,DEPARTMENT_ID,JOB_ID,OFFICE_ID,SALEGRADE FROM EMPLOYEES WHERE OFFICE_ID=?";
    public static final String REMOVE_WORKER = "DELETE FROM EMPLOYEES WHERE ID=?";
    public static final String UPDATE_WORKER = "UPDATE EMPLOYEES SET FIRSTNAME=?,LASTNAME=?,MGRID=?,DEPARTMENT_ID=?,JOB_ID=?,OFFICE_ID=?,SALEGRADE=? WHERE ID=?";
    public static final String GET_WORKER_PRIMARY_KEY_BY_ID = "SELECT ID FROM EMPLOYEES WHERE ID=?";
    public static final String GET_WORKER_PRIMARY_KEY_BY_NAME = "SELECT ID FROM EMPLOYEES WHERE FIRSTNAME=? OR LASTNAME=?";
    public static final String GET_WORKER_PRIMARY_KEY_BY_MGR_ID = "SELECT ID FROM EMPLOYEES WHERE MGRID=?";
    public static final String GET_ALL_WORKERS_PRIMARY_KEY = "SELECT ID FROM EMPLOYEES";
    public static final String GET_TOP_MANAGER_PRIMARY_KEY = "SELECT ID FROM EMPLOYEES WHERE MGRID IS NULL";
    public static final String GET_WORKER_BY_NULL = "SELECT ID FROM EMPLOYEES WHERE ID IS NULL";
    //job
    public static final String ADD_JOB = "INSERT INTO JOBS(ID,TITLE,DESCRIPTION) VALUES(?,?,?)";
    public static final String GET_ALL_JOBS = "SELECT ID FROM JOBS";
    public static final String GET_JOB_BY_ID = "SELECT TITLE,DESCRIPTION FROM JOBS WHERE ID=?";
    public static final String REMOVE_JOB = "DELETE FROM JOBS WHERE ID=?";
    public static final String UPDATE_JOB = "UPDATE JOBS SET TITLE=?,DESCRIPTION=? WHERE ID=?";
    public static final String GET_JOB_PRIMARY_KEY_BY_ID = "SELECT ID FROM JOBS WHERE ID=?";
    public static final String GET_JOB_PRIMARY_KEY_BY_TITLE = "SELECT ID FROM JOBS WHERE TITLE=?";
    public static final String DATA_SOURCE = "java:/eman_oracle";
}
