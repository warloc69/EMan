/**
 * 
 */
package ua.ivanchenko.eman.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import ua.ivanchenko.eman.exceptions.DataAccessException;
import ua.ivanchenko.eman.processors.FiltreConst;


public final class OracleDataAccessor  implements IDataAccessor {
	private Logger log = Logger.getLogger("emanlogger");

    /**
     * return new connection to the DataSource.
     * @throws DataAccessException if class can't get access to DataSource.
     */
    private Connection getConnection() throws DataAccessException{
        Connection connection = null;
        try {
            Context context = new InitialContext();       
            DataSource source = (DataSource) context.lookup(OracleDataAccessorConst.DATA_SOURCE);
            connection = source.getConnection();
            connection.setAutoCommit(false);
            return connection;
        } catch (NamingException e) {
            log.error("OracleDataAccesor context error",e);
            throw new DataAccessException("OracleDataAccesor context error",e);
        } catch (SQLException e1) {
            log.error("can't get connection sql error",e1);
            throw new DataAccessException("can't get connection sql error",e1);
        }
    }
    /**
     * Free all resource after run some method.
     * @param con Connection's object to DataSource for clean 
     * @param prep PreparedStatement for clean.
     * @param rset ResultSet for clean.
     * @throws DataAccessException if can't get access to some parameter 
     */
    private void resClean (Connection con, PreparedStatement prep, ResultSet rset) throws DataAccessException{
        if(rset!=null) {
            try {
                rset.close();
            } catch (SQLException e) {
                log.error("can't close ResultSet",e);
                throw new DataAccessException("can't close ResultSet",e);
            }
        }
        if(prep!=null) {
            try {
                prep.close();
            } catch (SQLException e) {
                log.error("can't close PreparedStatemets ",e);
                throw new DataAccessException("can't close PreparedStatemets ",e);
            }
        }
        if(con!=null) {
            try {
                con.close();
            } catch (SQLException e) {
                log.error("can't close Connection",e);
                throw new DataAccessException("can't close Connection",e);
            }
        }
    }
    public OracleDataAccessor() {
    }
    /**
     * method adds department into data source.
     * @param dept adder department
     * @throws DataAccessException if got data source error.
     */
    public void addDept(IDept dept) throws DataAccessException {
        PreparedStatement prep = null;
        Connection connection = null;
        try {
            connection = getConnection();
            prep = connection.prepareStatement(OracleDataAccessorConst.ADD_DEPT);
            prep.setString(1, dept.getTitle());
            prep.setString(2, dept.getDescription());
            prep.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            log.error("add departments sql error",e);
            try {
                connection.rollback();
            } catch (SQLException e1) {
                log.error("can't rollback from department's table error",e);
                throw new DataAccessException("can't close Connection",e);
            }
            throw new DataAccessException("addDep sql error",e);
        } finally {
            resClean(connection,prep,null);
        }
    }
    /**
     * method add job into data source.
     * @param job adder job
     * @throws DataAccessException if got data source error.
     */
    public void addJob(IJob job) throws DataAccessException {
        PreparedStatement prep = null;
        Connection connection = null;
        try {
            connection = getConnection();
            prep = connection.prepareStatement(OracleDataAccessorConst.ADD_JOB);
            prep.setString(1, job.getTitle());
            prep.setString(2, job.getDescription());
            prep.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            log.error("addJob sql error",e);
            try {
                connection.rollback();
            } catch (SQLException e1) {
                log.error("addJob sql error",e);
            }
            throw new DataAccessException("addJob sql error",e);
        } finally {
            resClean(connection,prep,null);
        }
    }
    /**
    * method add office into data source.
    * @param off adder office
    * @throws DataAccessException if got data source error.
    */
    public void addOffice(IOffice off) throws DataAccessException {
        PreparedStatement prep = null;
        Connection connection = null;
        try {
            connection = getConnection();
            prep = connection.prepareStatement(OracleDataAccessorConst.ADD_OFFICE);
            prep.setString(1, off.getTitle());
            prep.setString(2, off.getAddress());
            if (off.getManagerID() != null){
            	prep.setBigDecimal(3, new BigDecimal(off.getManagerID()));
            } else {
            	prep.setNull(3, Types.NUMERIC);
            }
            prep.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            log.error("addOffice sql error",e);
            try {
                connection.rollback();
            } catch (SQLException e1) {
                log.error("addOffice sql error",e);
            }
            throw new DataAccessException("addOffice sql error",e);
        } finally {
            resClean(connection,prep,null);
        }
    }
    /**
     * method add worker into data source.
     * @param worker adder worker
     * @throws DataAccessException if got data source error.
     */
    public void addWorker(IWorker worker) throws DataAccessException {
        PreparedStatement prep = null;
        Connection connection = null;
        try {
            connection = getConnection();
            prep = connection.prepareStatement(OracleDataAccessorConst.ADD_WORKER);
            prep.setString(1, worker.getFirstName());
            prep.setString(2, worker.getLastName());
            if (worker.getManagerID() != null){
            	prep.setBigDecimal(3, new BigDecimal(worker.getManagerID()));
            } else {
            	prep.setNull(3, Types.NUMERIC);
            }
            prep.setBigDecimal(4, new BigDecimal(worker.getDepartmentID()));
            prep.setBigDecimal(5, new BigDecimal(worker.getJobID()));
            prep.setBigDecimal(6, new BigDecimal(worker.getOfficeID()));
            prep.setDouble(7, worker.getSalegrade());            
            prep.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            log.error("addWorker sql error",e);
            try {
                connection.rollback();
            } catch (SQLException e1) {
                log.error("addWorker sql error",e);
            }
            throw new DataAccessException("addWorker Dept sql error",e);
        } finally {
            resClean(connection,prep,null);
        }

    }
    /**
    * return collection contain all departments
    * @throws DataAccessException if got data source error.
    */
    public Collection<IDept> getAllDepts(String sort) throws DataAccessException {
        PreparedStatement prep = null;
        Connection connection = null;
        ResultSet rset = null;
        try {
            connection = getConnection();
            if (sort == null) {
            	prep = connection.prepareStatement(OracleDataAccessorConst.GET_ALL_DEPTS);
            } else {
            	log.info("sql : "+ OracleDataAccessorConst.GET_ALL_DEPTS+" order by "+sort);
            	prep = connection.prepareStatement(OracleDataAccessorConst.GET_ALL_DEPTS+" order by "+sort);
            }
            rset = prep.executeQuery();
            Collection<IDept> ls = new ArrayList<IDept>();
            while (rset.next()) {
                IDept dep = new Dept();
                dep.setID(rset.getBigDecimal(1).toBigInteger());
                dep.setTitle(rset.getString(2));
                dep.setDescription(rset.getString(3));
                ls.add(dep);                
            }
            return ls;
        } catch (SQLException e) {
            log.error("get all depts sql error",e);
            throw new DataAccessException("get all depts  sql error",e);
        } finally {
            resClean(connection,prep,rset);
        }
    }
    /**
    * return collection contain all jobs
    * @throws DataAccessException if got data source error.
    */
    public Collection<IJob> getAllJobs(String sort) throws DataAccessException {
        PreparedStatement prep = null;
        Connection connection = null;
        try {
            connection = getConnection();
            if (sort == null) {
            	prep = connection.prepareStatement(OracleDataAccessorConst.GET_ALL_JOBS);
            } else {
            	log.info("sql : "+ OracleDataAccessorConst.GET_ALL_JOBS+" order by "+sort);
            	prep = connection.prepareStatement(OracleDataAccessorConst.GET_ALL_JOBS+" order by "+sort);
            }
            ResultSet rset = prep.executeQuery();
            Collection<IJob> ls = new ArrayList<IJob>();
            while (rset.next()) {
                IJob job = new Job();
                job.setID(rset.getBigDecimal(1).toBigInteger());
                job.setTitle(rset.getString(2));
                job.setDescription(rset.getString(3));
                ls.add(job);
            }
            return ls;
        } catch (SQLException e) {
            log.error("get all jobs sql error",e);
            throw new DataAccessException("get all jobs sql error",e);
        } finally {
            resClean(connection,prep,null);
        }
    }
    /**
    * return collection contain all offices
    * @throws DataAccessException if got data source error.
    */
    public Collection<IOffice> getAllOffices(String sort) throws DataAccessException {
        PreparedStatement prep = null;
        Connection connection = null;
        try {
            connection = getConnection();
            if (sort == null) {
            	prep = connection.prepareStatement(OracleDataAccessorConst.GET_ALL_OFFICES);
            } else {
            	log.info("sql : "+ OracleDataAccessorConst.GET_ALL_OFFICES+" order by "+sort);
            	prep = connection.prepareStatement(OracleDataAccessorConst.GET_ALL_OFFICES+" order by "+sort);
            }
            ResultSet rset = prep.executeQuery();
            Collection<IOffice> ls = new ArrayList<IOffice>();
            while (rset.next()) {
                IOffice off = new Office();
                off.setID(rset.getBigDecimal(1).toBigInteger());
                off.setTitle(rset.getString(2));
                off.setAddress(rset.getString(3));
                if (rset.getBigDecimal(4) != null) {
                	off.setManagerID(rset.getBigDecimal(4).toBigInteger());
                } else {
                	off.setManagerID(null);
                }
                ls.add(off);
            }
            return ls;
        } catch (SQLException e) {
            log.error("addDept sql error",e);
            throw new DataAccessException("update Dept sql error",e);
        } finally {
            resClean(connection,prep,null);
        }
    }
    /**
    * return collection contain all workers
    * @throws DataAccessException if got data source error.
    */
    public Collection<IWorker> getAllWorkers(String sort) throws DataAccessException {
        PreparedStatement prep = null;
        Connection connection = null;
        try {
            connection = getConnection();
            if (sort == null) {
            	prep = connection.prepareStatement(OracleDataAccessorConst.GET_ALL_WORKERS);
            } else {
            	log.info("sql : "+ OracleDataAccessorConst.GET_ALL_WORKERS+" order by "+sort);
            	prep = connection.prepareStatement(OracleDataAccessorConst.GET_ALL_WORKERS+" order by "+sort);
            }
            ResultSet rset = prep.executeQuery();
            Collection<IWorker> ls = new ArrayList<IWorker>();
            while (rset.next()) {
                IWorker worker = new Worker();
                worker.setID(rset.getBigDecimal(1).toBigInteger());
                worker.setFirstName(rset.getString(2));
                worker.setLastName(rset.getString(3));
                if (rset.getBigDecimal(4) != null) {
                	worker.setManagerID(rset.getBigDecimal(4).toBigInteger());
                } else {
                	worker.setManagerID(null);
                }
                worker.setDepartmentID(rset.getBigDecimal(5).toBigInteger());
                worker.setJobID(rset.getBigDecimal(6).toBigInteger());
                worker.setOfficeID(rset.getBigDecimal(7).toBigInteger());
                worker.setSalegrade(rset.getDouble(8));
                ls.add(worker);
            }
            return ls;
        } catch (SQLException e) {
            log.error("get all workers sql error",e);
            throw new DataAccessException("get all workers sql error",e);
        } finally {
            resClean(connection,prep,null);
        }
    }
    /**
    * Method returns department by identifier.
    * @throws DataAccessException if got data source error.
    */
    public IDept getDeptByID(BigInteger id) throws DataAccessException {
        PreparedStatement prep = null;
        Connection connection = null;
        try {
            connection = getConnection();
            prep = connection.prepareStatement(OracleDataAccessorConst.GET_DEPTS_BY_ID);
            prep.setBigDecimal(1, new BigDecimal(id));
            ResultSet rset = prep.executeQuery();
            IDept dep = null;
            if (rset.next()) {
                dep = new Dept();
                dep.setID(rset.getBigDecimal(1).toBigInteger());
                dep.setTitle(rset.getString(2));
                dep.setDescription(rset.getString(3));
            }
            return dep;
        } catch (SQLException e) {
            log.error("GET DEPT by id sql error",e);
            throw new DataAccessException("GET DEPT by id sql error",e);
        } finally {
            resClean(connection,prep,null);
        }
    }
    /**
    * Method returns department by title.
    * @throws DataAccessException if got data source error.
    */
    public IDept getDeptByTitle(String title) throws DataAccessException {
        PreparedStatement prep = null;
        Connection connection = null;
        try {
            connection = getConnection();
            prep = connection.prepareStatement(OracleDataAccessorConst.GET_DEPTS_BY_TITLE);
            prep.setString(1, title);
            ResultSet rset = prep.executeQuery();
            IDept dep = null;
            if (rset.next()) {
                dep = new Dept();
                dep.setID(rset.getBigDecimal(1).toBigInteger());
                dep.setTitle(rset.getString(2));
                dep.setDescription(rset.getString(3));
            }
            return dep;
        } catch (SQLException e) {
            log.error("GET DEPT by title sql error",e);
            throw new DataAccessException("GET DEPT by title sql error",e);
        } finally {
            resClean(connection,prep,null);
        }
    }
    /**
    * Method returns job by identifier.
    * @throws DataAccessException if got data source error.
    */
    public IJob getJobByID(BigInteger id) throws DataAccessException {
        PreparedStatement prep = null;
        Connection connection = null;
        try {
            connection = getConnection();
            prep = connection.prepareStatement(OracleDataAccessorConst.GET_JOB_BY_ID);
            prep.setBigDecimal(1, new BigDecimal(id));
            ResultSet rset = prep.executeQuery();
            IJob job = null;
            if (rset.next()) {
                job = new Job();
                job.setID(rset.getBigDecimal(1).toBigInteger());
                job.setTitle(rset.getString(2));
                job.setDescription(rset.getString(3));
            }
            return job;
        } catch (SQLException e) {
            log.error("GET job by id  sql error",e);
            throw new DataAccessException("GET job by id  sql error",e);
        } finally {
            resClean(connection,prep,null);
        }
    }
    /**
    * Method returns job by title.
    * @throws DataAccessException if got data source error.
    */
    public IJob getJobByTitle(String title) throws DataAccessException {
        PreparedStatement prep = null;
        Connection connection = null;
        try {
            connection = getConnection();
            prep = connection.prepareStatement(OracleDataAccessorConst.GET_JOB_BY_TITLE);
            prep.setString(1, title);
            ResultSet rset = prep.executeQuery();
            IJob job = null;
            if (rset.next()) {
                job = new Job();
                job.setID(rset.getBigDecimal(1).toBigInteger());
                job.setTitle(rset.getString(2));
                job.setDescription(rset.getString(3));
            }
            return job;
        } catch (SQLException e) {
            log.error("addDept sql error",e);
            throw new DataAccessException("update Dept sql error",e);
        } finally {
            resClean(connection,prep,null);
        }
    }
    /**
    * Method returns office by identifier.
    * @throws DataAccessException if got data source error.
    */
    public IOffice getOfficeByID(BigInteger id) throws DataAccessException {
        PreparedStatement prep = null;
        Connection connection = null;
        try {
            connection = getConnection();
            prep = connection.prepareStatement(OracleDataAccessorConst.GET_OFFICE_BY_ID);
            prep.setBigDecimal(1, new BigDecimal(id));
            ResultSet rset = prep.executeQuery();
            IOffice off = null;
            if (rset.next()) {
                off = new Office();
                off.setID(rset.getBigDecimal(1).toBigInteger());
                off.setTitle(rset.getString(2));
                off.setAddress(rset.getString(3));
                if (rset.getBigDecimal(4) != null){
                	off.setManagerID(rset.getBigDecimal(4).toBigInteger());
                } else {
                	off.setManagerID(null);
                }
            }
            return off;
        } catch (SQLException e) {
            log.error("GET office by id  sql error",e);
            throw new DataAccessException("GET office by id  sql error",e);
        } finally {
            resClean(connection,prep,null);
        }
    }
    /**
    * Method returns office by title.
    * @throws DataAccessException if got data source error.
    */
    public IOffice getOfficeByTitle(String title) throws DataAccessException {
        PreparedStatement prep = null;
        Connection connection = null;
        try {
            connection = getConnection();
            prep = connection.prepareStatement(OracleDataAccessorConst.GET_OFFICE_BY_TITTLE);
            prep.setString(1, title);
            ResultSet rset = prep.executeQuery();
            IOffice off = null;
            if (rset.next()) {
                off = new Office();
                off.setID(rset.getBigDecimal(1).toBigInteger());
                off.setTitle(rset.getString(2));
                off.setAddress(rset.getString(3));
                off.setManagerID(rset.getBigDecimal(4).toBigInteger());
            }
            return off;
        } catch (SQLException e) {
            log.error("get oofice by title sql error",e);
            throw new DataAccessException("get oofice by title sql error",e);
        } finally {
            resClean(connection,prep,null);
        }
    }
    /**
    * Method returns worker by identifier.
    * @throws DataAccessException if got data source error.
    */
    public IWorker getWorkerByID(BigInteger id) throws DataAccessException {
        PreparedStatement prep = null;
        Connection connection = null;
        try {
            connection = getConnection();
            prep = connection.prepareStatement(OracleDataAccessorConst.GET_WORKER_BY_ID);
            prep.setBigDecimal(1, new BigDecimal(id));
            ResultSet rset = prep.executeQuery();
            IWorker worker = null;
            if (rset.next()) {
                worker = new Worker();
                worker.setID(rset.getBigDecimal(1).toBigInteger());
                worker.setFirstName(rset.getString(2));
                worker.setLastName(rset.getString(3));
                if (rset.getBigDecimal(4) != null){
                	worker.setManagerID(rset.getBigDecimal(4).toBigInteger());
                } else {
                	worker.setManagerID(null);
                }
                worker.setDepartmentID(rset.getBigDecimal(5).toBigInteger());
                worker.setJobID(rset.getBigDecimal(6).toBigInteger());
                worker.setOfficeID(rset.getBigDecimal(7).toBigInteger());
                worker.setSalegrade(rset.getDouble(8));                
            }
            return worker;
        } catch (SQLException e) {
            log.error("Get worker by id sql error",e);
            throw new DataAccessException("Get worker by id sql error",e);
        } finally {
            resClean(connection,prep,null);
        }
    }
    /**
    * Method returns worker by last name.
    * @throws DataAccessException if got data source error.
    */
    public Collection<IWorker> getWorkersByName(String lname) throws DataAccessException {
        PreparedStatement prep = null;
        Connection connection = null;
        try {
            connection = getConnection();
            prep = connection.prepareStatement(OracleDataAccessorConst.GET_WORKER_BY_NAME);
            prep.setString(1, lname);
            prep.setString(2, lname);
            ResultSet rset = prep.executeQuery();
            HashSet<IWorker> ls = new HashSet<IWorker>();
            while (rset.next()) {
                IWorker worker = new Worker();
                worker.setID(rset.getBigDecimal(1).toBigInteger());
                worker.setFirstName(rset.getString(2));
                worker.setLastName(rset.getString(3));
                if (rset.getBigDecimal(4) != null){
                	worker.setManagerID(rset.getBigDecimal(4).toBigInteger());
                } else {
                	worker.setManagerID(null);
                }
                worker.setDepartmentID(rset.getBigDecimal(5).toBigInteger());
                worker.setJobID(rset.getBigDecimal(6).toBigInteger());
                worker.setOfficeID(rset.getBigDecimal(7).toBigInteger());
                worker.setSalegrade(rset.getDouble(8));
                ls.add(worker);
            }            
            return ls;
        } catch (SQLException e) {
            log.error("Get worker by last name sql error",e);
            throw new DataAccessException("Get worker by last name sql error",e);
        } finally {
            resClean(connection,prep,null);
        }
    }
    /**
    * Method returns worker by identifier.
    * @throws DataAccessException if got data source error.
    */
    public Collection<IWorker> getWorkersByMgrID(BigInteger id, String sort) throws DataAccessException {
        PreparedStatement prep = null;
        Connection connection = null;
        try {
            connection = getConnection();
            if (sort == null) {
            	prep = connection.prepareStatement(OracleDataAccessorConst.GET_WORKER_BY_MGR_ID);
            } else {
            	log.info("sql : "+ OracleDataAccessorConst.GET_WORKER_BY_MGR_ID+" order by "+sort);
            	prep = connection.prepareStatement(OracleDataAccessorConst.GET_WORKER_BY_MGR_ID+" order by "+sort);
            }
            prep.setBigDecimal(1, new BigDecimal(id));
            ResultSet rset = prep.executeQuery();
            LinkedList<IWorker> ls = new LinkedList<IWorker>();
            while (rset.next()) {
                IWorker worker = new Worker();
                worker.setID(rset.getBigDecimal(1).toBigInteger());
                worker.setFirstName(rset.getString(2));
                worker.setLastName(rset.getString(3));
                worker.setManagerID(rset.getBigDecimal(4).toBigInteger());
                worker.setDepartmentID(rset.getBigDecimal(5).toBigInteger());
                worker.setJobID(rset.getBigDecimal(6).toBigInteger());
                worker.setOfficeID(rset.getBigDecimal(7).toBigInteger());
                worker.setSalegrade(rset.getDouble(8));
                ls.add(worker);
            }
            return ls;
        } catch (SQLException e) {
            log.error("Get worker by mgr id sql error",e);
            throw new DataAccessException("Get worker by mgr id sql error",e);
        } finally {
            resClean(connection,prep,null);
        }
    }
    public Collection<IWorker> getTopManagers(String sort) throws DataAccessException {
        PreparedStatement prep = null;
        Connection connection = null;
        try {
            connection = getConnection();
            if (sort == null) {
            	prep = connection.prepareStatement(OracleDataAccessorConst.GET_ALL_TOPMANAGER);
            } else {
            	log.info("sql : "+ OracleDataAccessorConst.GET_ALL_TOPMANAGER+" order by "+sort);
            	prep = connection.prepareStatement(OracleDataAccessorConst.GET_ALL_TOPMANAGER+" order by "+sort);
            }
            ResultSet rset = prep.executeQuery();
            LinkedList<IWorker> ls = new LinkedList<IWorker>();
            while (rset.next()) {
                IWorker worker = new Worker();
                worker.setID(rset.getBigDecimal(1).toBigInteger());
                worker.setFirstName(rset.getString(2));
                worker.setLastName(rset.getString(3));
                worker.setManagerID(null);
                worker.setDepartmentID(rset.getBigDecimal(5).toBigInteger());
                worker.setJobID(rset.getBigDecimal(6).toBigInteger());
                worker.setOfficeID(rset.getBigDecimal(7).toBigInteger());
                worker.setSalegrade(rset.getDouble(8));
                ls.add(worker);
            }
            return ls;
        } catch (SQLException e) {
            log.error("Get topmanager sql error",e);
            throw new DataAccessException("Get worker by mgr id sql error",e);
        } finally {
            resClean(connection,prep,null);
        }
    }
    /**
    * method remove department from data source.
    * @param id department's identifier for removing 
    * @throws DataAccessException if got data source error.
    */
    public void removeDept(BigInteger id) throws DataAccessException {
    	if (canRemove(OracleDataAccessorConst.GET_WORKER_BY_DEPT_ID,id)) {
    		throw new DataAccessException("You can not remove department because the department is used");
    	}
        PreparedStatement prep = null;
        Connection connection = null;
        try {
            connection = getConnection();
            prep = connection.prepareStatement(OracleDataAccessorConst.REMOVE_DEPT);
            prep.setBigDecimal(1, new BigDecimal(id));
            prep.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            log.error("remove department sql error",e);
            try {
                connection.rollback();
            } catch (SQLException e1) {
                log.error("can't rollback commit from department's table",e1);
                throw new DataAccessException("can't rollback commit from department's table",e1);
            }
            throw new DataAccessException("remove department sql error",e);
        } finally {
            resClean(connection,prep,null);
        }
    }
    /**
    * method remove job from data source.
    * @param id job's identifier for removing 
    * @throws DataAccessException if got data source error.
    */
    public void removeJob(BigInteger id) throws DataAccessException {
    	if (canRemove(OracleDataAccessorConst.GET_WORKER_BY_JOB_ID,id)) {
    		throw new DataAccessException("You can not remove job because the job is used");
    	}
        PreparedStatement prep = null;
        Connection connection = null;
        try {
            connection = getConnection();
            prep = connection.prepareStatement(OracleDataAccessorConst.REMOVE_JOB);
            prep.setBigDecimal(1, new BigDecimal(id));
            prep.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            log.error("remove job sql error",e);
            try {
                connection.rollback();
            } catch (SQLException e1) {
                log.error("can't rollback commit from job's table",e1);
                throw new DataAccessException("can't rollback commit from job's table",e1);
            }
            throw new DataAccessException("remove job sql error",e);
        } finally {
            resClean(connection,prep,null);
        }

    }
    /**
    * method remove office from data source.
    * @param id office's identifier for removing 
    * @throws DataAccessException if got data source error.
    */
    public void removeOffice(BigInteger id) throws DataAccessException {
    	if (canRemove(OracleDataAccessorConst.GET_WORKER_BY_OFFICE_ID,id)) {
    		throw new DataAccessException("You can not remove office because the office is used");
    	}
        PreparedStatement prep = null;
        Connection connection = null;
        try {
            connection = getConnection();
            prep = connection.prepareStatement(OracleDataAccessorConst.REMOVE_OFFICE);
            prep.setBigDecimal(1, new BigDecimal(id));
            prep.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            log.error("remove office sql error",e);
            try {
                connection.rollback();
            } catch (SQLException e1) {
                log.error("can't rollback commit from office's table",e1);
                throw new DataAccessException("can't rollback commit from office's table",e1);
            }
            throw new DataAccessException("remove office sql error",e);
        } finally {
            resClean(connection,prep,null);
        }

    }
    /**
    * method remove worker from data source.
    * @param id worker's identifier for removing 
    * @throws DataAccessException if got data source error.
    */
    public void removeWorker(BigInteger id) throws DataAccessException {
    	if (canRemove(OracleDataAccessorConst.GET_WORKER_BY_MGR_ID,id)) {
    		throw new DataAccessException("Cannot remove worker because worker has subordinate");
    	}
        PreparedStatement prep = null;
        Connection connection = null;
        try {
            connection = getConnection();
            prep = connection.prepareStatement(OracleDataAccessorConst.REMOVE_WORKER);
            prep.setBigDecimal(1, new BigDecimal(id));
            prep.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            log.error("remove worker sql error",e);
            try {
                connection.rollback();
            } catch (SQLException e1) {
                log.error("can't rollback commit from worker's table",e1);
                throw new DataAccessException("can't rollback commit from worker's table",e1);
            }
            throw new DataAccessException("remove worker sql error",e);
        } finally {
            resClean(connection,prep,null);
        }
    }
    /**
    * method update department in the data source.
    * @param id department's identifier for updating 
    * @throws DataAccessException if got data source error.
    */
    public void updateDept(IDept dept) throws DataAccessException {
        PreparedStatement prep = null;
        Connection connection = null;
        try {
            connection = getConnection();
            prep = connection.prepareStatement(OracleDataAccessorConst.UPDATE_DEPT);
            prep.setString(1, dept.getTitle());
            prep.setString(2, dept.getDescription());
            prep.setBigDecimal(3, new BigDecimal(dept.getID()));
            prep.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            log.error("update dept sql error",e);
            try {
                connection.rollback();
            } catch (SQLException e1) {
                log.error("can't rollback commit department's table",e1);
                throw new DataAccessException("can't rollback commit department's table",e1);
            }
            throw new DataAccessException("update department's table sql error",e);            
        } finally {
            resClean(connection,prep,null);
        }
    }
    /**
     * method update job in the data source.
     * @param id job's identifier for updating 
     * @throws DataAccessException if got data source error.
     */
    public void updateJob(IJob job) throws DataAccessException {
        PreparedStatement prep = null;
        Connection connection = null;
        try {
            connection = getConnection();
            prep = connection.prepareStatement(OracleDataAccessorConst.UPDATE_JOB);
            prep.setString(1, job.getTitle());
            prep.setString(2, job.getDescription());
            prep.setBigDecimal(3, new BigDecimal(job.getID()));
            prep.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            log.error("update job's table sql error",e);
            try {
                connection.rollback();
            } catch (SQLException e1) {
                log.error("can't rollback commit job's table",e1);
                throw new DataAccessException("can't rollback commit job's table",e1);
            }
            throw new DataAccessException("update job sql error",e);
        } finally {
            resClean(connection,prep,null);
        }
    }
    /**
     * method update office in the data source.
     * @param id office's identifier for updating 
     * @throws DataAccessException if got data source error.
     */
    public void updateOffice(IOffice off) throws DataAccessException {
        PreparedStatement prep = null;
        Connection connection = null;
        try {
            connection = getConnection();
            prep = connection.prepareStatement(OracleDataAccessorConst.UPDATE_OFFICE);
            prep.setString(1, off.getTitle());
            prep.setString(2, off.getAddress());
            if (off.getManagerID() != null){
            	prep.setBigDecimal(3, new BigDecimal(off.getManagerID()));
            } else {
            	prep.setNull(3, Types.NUMERIC);
            }
            prep.setBigDecimal(4, new BigDecimal(off.getID()));
            prep.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            log.error("update office's table sql error",e);
            try {
                connection.rollback();
            } catch (SQLException e1) {
                log.error("can't rollback commit office's table",e1);
                throw new DataAccessException("can't rollback commit office's table",e1);
            }
            throw new DataAccessException("update office's table sql error",e);            
        } finally {
            resClean(connection,prep,null);
        }

    }
    /**
     * method update worker in the data source.
     * @param id worker's identifier for updating 
     * @throws DataAccessException if got data source error.
     */
    public void updateWorker(IWorker worker) throws DataAccessException {
        PreparedStatement prep = null;
        Connection connection = null;
        try {
            connection = getConnection();
            prep = connection.prepareStatement(OracleDataAccessorConst.UPDATE_WORKER);
            prep.setString(1, worker.getFirstName());
            prep.setString(2, worker.getLastName());
            if (worker.getManagerID() != null){
            	prep.setBigDecimal(3, new BigDecimal(worker.getManagerID()));
            } else {
            	prep.setNull(3, Types.NUMERIC);
            }
            prep.setBigDecimal(4, new BigDecimal(worker.getDepartmentID()));
            prep.setBigDecimal(5, new BigDecimal(worker.getJobID()));
            prep.setBigDecimal(6, new BigDecimal(worker.getOfficeID()));
            prep.setDouble(7, worker.getSalegrade());
            prep.setBigDecimal(8, new BigDecimal(worker.getID()));
            prep.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            log.error("can't update worker's table ",e);
            try {
                connection.rollback();
            } catch (SQLException e1) {
                log.error("can't rollback commit from worker's table",e1);
                throw new DataAccessException("can't rollback commit from worker's table",e1);
            }
            throw new DataAccessException("update worker sql error",e);            
        } finally {
            resClean(connection,prep,null);
        }
    }
    public Collection<IWorker> getPath(BigInteger id) throws DataAccessException {
    	 PreparedStatement prep = null;
         Connection connection = null;
         try {
             connection = getConnection();
             prep = connection.prepareStatement(OracleDataAccessorConst.GET_PATH);
             prep.setBigDecimal(1, new BigDecimal(id));
             ResultSet rset = prep.executeQuery();
             LinkedList<IWorker> ls = new LinkedList<IWorker>();
             while (rset.next()) {
                 IWorker worker = new Worker();
                 worker.setID(rset.getBigDecimal(1).toBigInteger());
                 worker.setLastName(rset.getString(2));
                 ls.add(worker);
             }
             return ls;
         } catch (SQLException e) {
             log.error("Get worker by mgr id sql error",e);
             throw new DataAccessException("Get worker by mgr id sql error",e);
         } finally {
             resClean(connection,prep,null);
         }
    }
    private boolean canRemove(String command, BigInteger id) throws DataAccessException {
   	 	PreparedStatement prep = null;
        Connection connection = null;
        try {
            connection = getConnection();
            prep = connection.prepareStatement(command);
            prep.setBigDecimal(1, new BigDecimal(id));
            ResultSet rset = prep.executeQuery();            
            return rset.next();
        } catch (SQLException e) {
            log.error("Get worker by mgr id sql error",e);
            throw new DataAccessException("Get worker by mgr id sql error",e);
        } finally {
            resClean(connection,prep,null);
        }
   }
    public Collection<IWorker> filteringWorker(BigInteger id, HashMap<String,String> filters) throws DataAccessException {
   	 	PreparedStatement prep = null;
        Connection connection = null;
        try {
            connection = getConnection();
            StringBuffer sb = new StringBuffer();
            sb.append("select * from employees ");
            String job = filters.get(FiltreConst.job);
            String dept = filters.get(FiltreConst.dept);
            String office = filters.get(FiltreConst.office);
            String lname = filters.get(FiltreConst.lname);
            String fname = filters.get(FiltreConst.fname);
            boolean pref = false;
            	if (job != null) {
            		sb.append(",jobs ");
            	}
            	if (dept != null) {
            		sb.append(",departments ");
            	}
            	if (office != null) {
            		sb.append(",offices ");
            	}
            	sb.append(" where ");
            	if (job != null) {
            		sb.append("jobs.id=employees.job_id ");
            		sb.append(" and jobs.title=? ");
            		pref = true;
            	}
            	if (dept != null) {
            		if (pref) {
            			sb.append(" and ");
            		} 
            		sb.append(" departments.id = employees.department_id ");
            		sb.append(" and departments.title=? ");
            		pref = true;            		            		
            	}
            	if (office != null) {
            		if (pref) {
            			sb.append(" and ");
            		} 
            		sb.append(" offices.id = employees.office_id ");
            		sb.append(" and offices.title=? ");
            		pref = true;            		            		
            	}
            	if (fname != null) {
            		if (pref) {
            			sb.append(" and ");
            		} 
            		sb.append(" employees.firstname=? ");
            		pref = true;            		           		
            	}
            	if (lname != null) {
            		if (pref) {
            			sb.append(" and ");
            		} 
            		sb.append(" employees.lastname=? ");
            		pref = true;            		            		
            	}
            	if (id != null ) {
            		if (pref) {
            			sb.append(" and ");
            		} sb.append(" employees.mgrid=? ");            		            		
            	} else {
            		if (pref) {
            			sb.append(" and ");
            		} 
            		sb.append(" employees.mgrid is null ");
            		
            	}
            int i = 1;
            log.debug("query : "+ sb.toString());
            prep = connection.prepareStatement(sb.toString());
            if (job != null) {
            	prep.setString(i, job);
            	i++;
            }
            if (dept != null) {
            	prep.setString(i, dept);
            	i++;
            }
            if (office != null) {
            	prep.setString(i, office);
            	i++;
            }
            if (fname != null) {
            	prep.setString(i, fname);
            	i++;
            }
            if (lname != null) {
            	prep.setString(i, lname);
            	i++;
            }
            if (id != null) {
            	prep.setBigDecimal(i, new BigDecimal(id));
            }
            ResultSet rset = prep.executeQuery();
            LinkedList<IWorker> ls = new LinkedList<IWorker>();
            while (rset.next()) {
                IWorker worker = new Worker();
                worker.setFirstName(rset.getString("FIRSTNAME"));
                worker.setLastName(rset.getString("LASTNAME"));
                worker.setID(rset.getBigDecimal("ID").toBigInteger());
                worker.setJobID(rset.getBigDecimal("JOB_ID").toBigInteger());
                worker.setOfficeID(rset.getBigDecimal("OFFICE_ID").toBigInteger());
                worker.setDepartmentID(rset.getBigDecimal("DEPARTMENT_ID").toBigInteger());
                if (rset.getBigDecimal("MGRID") != null) {
                	worker.setManagerID(rset.getBigDecimal("MGRID").toBigInteger());
                } else {
                	worker.setManagerID(null);
                }
                worker.setSalegrade(rset.getDouble("SALEGRADE"));
                log.info("worker: "+ worker.getLastName());
                ls.add(worker);
            }
            log.info(ls);
            return ls;
        } catch (SQLException e) {
            log.error("Get worker by mgr id sql error",e);
            throw new DataAccessException("Get worker by mgr id sql error",e);
        } finally {
            resClean(connection,prep,null);
        }
   }
}