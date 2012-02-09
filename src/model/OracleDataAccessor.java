/**
 * 
 */
package model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import exceptions.DataAccessException;

public final class OracleDataAccessor implements IDataAccessor {
	private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(OracleDataAccessor.class);
	private Connection getConnection() {
		Connection connection = null;
		try {
			Context context = new InitialContext();		
			DataSource source = (DataSource) context.lookup("java:comp/env/jdbc/myoracle"); 
			connection = source.getConnection();
			return connection;
		} catch (NamingException e) {
			log.error("OracleDataAccesor context error",e);
		} catch (SQLException e1) {
			log.error("OracleDateAccessor sql error",e1);
		}
		return null;
	}
	private void resClean (Connection con, PreparedStatement prep, ResultSet rset) {
		if(rset!=null) {
			try {
				rset.close();
			} catch (SQLException e) {
				log.error("can't close ResultSet",e);
			}
		}
		if(prep!=null) {
			try {
				prep.close();
			} catch (SQLException e) {
				log.error("can't close PreparedStatemets ",e);
			}
		}
		if(con!=null) {
			try {
				con.close();
			} catch (SQLException e) {
				log.error("can't close Connection",e);
			}
		}
	}
	public OracleDataAccessor() {
		org.apache.log4j.PropertyConfigurator.configure("log4j.properties");
	}
	/* (non-Javadoc)
	 * @see model.IDataAccessor#addDept(model.IDept)
	 */
	@Override
	public void addDept(IDept dept) throws DataAccessException {
		PreparedStatement prep = null;
		Connection connection = null;
		try {
			connection = getConnection();
			connection.setAutoCommit(false);
			prep = connection.prepareStatement(OraclePrepareCommands.ADD_DEPT);
			prep.setString(1, dept.getTitle());
			prep.setString(2, dept.getDescription());
			prep.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			log.error("addDept sql error",e);
			try {
				connection.rollback();
			} catch (SQLException e1) {
				log.error("addDept sql error",e);
			}
			throw new DataAccessException("addDep sql error",e);
		} finally {
			resClean(connection,prep,null);
		}
	}

	/* (non-Javadoc)
	 * @see model.IDataAccessor#addJob(model.IJob)
	 */
	@Override
	public void addJob(IJob job) throws DataAccessException {
		PreparedStatement prep = null;
		Connection connection = null;
		try {
			connection = getConnection();
			connection.setAutoCommit(false);
			prep = connection.prepareStatement(OraclePrepareCommands.ADD_JOB);
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

	/* (non-Javadoc)
	 * @see model.IDataAccessor#addOffice(model.IOffice)
	 */
	@Override
	public void addOffice(IOffice off) throws DataAccessException {
		PreparedStatement prep = null;
		Connection connection = null;
		try {
			connection = getConnection();
			connection.setAutoCommit(false);
			prep = connection.prepareStatement(OraclePrepareCommands.ADD_OFFICE);
			prep.setString(1, off.getTitle());
			prep.setString(2, off.getAddress());
			prep.setBigDecimal(3, new BigDecimal(off.getManagerID()));
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

	/* (non-Javadoc)
	 * @see model.IDataAccessor#addWorker(model.IWorker)
	 */
	@Override
	public void addWorker(IWorker worker) throws DataAccessException {
		PreparedStatement prep = null;
		Connection connection = null;
		try {
			connection = getConnection();
			connection.setAutoCommit(false);
			prep = connection.prepareStatement(OraclePrepareCommands.ADD_WORKER);
			prep.setString(1, worker.getFirstName());
			prep.setString(2, worker.getLastName());
			prep.setBigDecimal(3, new BigDecimal(worker.getManagerID()));
			prep.setBigDecimal(4, new BigDecimal(worker.getDepartmentID()));
			prep.setBigDecimal(5, new BigDecimal(worker.getJobID()));
			prep.setBigDecimal(6, new BigDecimal(worker.getOfficeID()));
			prep.setInt(7, worker.getSalegrade());			
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

	/* (non-Javadoc)
	 * @see model.IDataAccessor#getAllDept()
	 */
	@Override
	public Collection<IDept> getAllDepts() throws DataAccessException {
		PreparedStatement prep = null;
		Connection connection = null;
		ResultSet rset = null;
		try {
			connection = getConnection();
			prep = connection.prepareStatement(OraclePrepareCommands.GET_ALL_DEPTS);
			rset = prep.executeQuery();
			LinkedList<IDept> ls = new LinkedList<IDept>();
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

	/* (non-Javadoc)
	 * @see model.IDataAccessor#getAllJobs()
	 */
	@Override
	public Collection<IJob> getAllJobs() throws DataAccessException {
		PreparedStatement prep = null;
		Connection connection = null;
		try {
			connection = getConnection();
			prep = connection.prepareStatement(OraclePrepareCommands.GET_ALL_JOBS);
			ResultSet rset = prep.executeQuery();
			LinkedList<IJob> ls = new LinkedList<IJob>();
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

	/* (non-Javadoc)
	 * @see model.IDataAccessor#getAllOffice()
	 */
	@Override
	public Collection<IOffice> getAllOffices() throws DataAccessException {
		PreparedStatement prep = null;
		Connection connection = null;
		try {
			connection = getConnection();
			prep = connection.prepareStatement(OraclePrepareCommands.GET_ALL_OFFICES);
			ResultSet rset = prep.executeQuery();
			LinkedList<IOffice> ls = new LinkedList<IOffice>();
			while (rset.next()) {
				IOffice off = new Office();
				off.setID(rset.getBigDecimal(1).toBigInteger());
				off.setTitle(rset.getString(2));
				off.setAddress(rset.getString(3));
				off.setManagerID(rset.getBigDecimal(4).toBigInteger());
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

	/* (non-Javadoc)
	 * @see model.IDataAccessor#getAllWorker()
	 */
	@Override
	public Collection<IWorker> getAllWorker() throws DataAccessException {
		PreparedStatement prep = null;
		Connection connection = null;
		try {
			connection = getConnection();
			prep = connection.prepareStatement(OraclePrepareCommands.GET_ALL_WORKERS);
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
				worker.setSalegrade(rset.getInt(8));
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

	/* (non-Javadoc)
	 * @see model.IDataAccessor#getDeptByID(java.math.BigInteger)
	 */
	@Override
	public IDept getDeptByID(BigInteger id) throws DataAccessException {
		PreparedStatement prep = null;
		Connection connection = null;
		try {
			connection = getConnection();
			prep = connection.prepareStatement(OraclePrepareCommands.GET_DEPTS_BY_ID);
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

	/* (non-Javadoc)
	 * @see model.IDataAccessor#getDeptByTitle(java.lang.String)
	 */
	@Override
	public IDept getDeptByTitle(String title) throws DataAccessException {
		PreparedStatement prep = null;
		Connection connection = null;
		try {
			connection = getConnection();
			prep = connection.prepareStatement(OraclePrepareCommands.GET_DEPTS_BY_TITLE);
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

	/* (non-Javadoc)
	 * @see model.IDataAccessor#getJobByID(java.math.BigInteger)
	 */
	@Override
	public IJob getJobByID(BigInteger id) throws DataAccessException {
		PreparedStatement prep = null;
		Connection connection = null;
		try {
			connection = getConnection();
			prep = connection.prepareStatement(OraclePrepareCommands.GET_JOB_BY_ID);
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

	/* (non-Javadoc)
	 * @see model.IDataAccessor#getJobByTitle(java.lang.String)
	 */
	@Override
	public IJob getJobByTitle(String title) throws DataAccessException {
		PreparedStatement prep = null;
		Connection connection = null;
		try {
			connection = getConnection();
			prep = connection.prepareStatement(OraclePrepareCommands.GET_JOB_BY_TITLE);
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

	/* (non-Javadoc)
	 * @see model.IDataAccessor#getOfficeByID(java.math.BigInteger)
	 */
	@Override
	public IOffice getOfficeByID(BigInteger id) throws DataAccessException {
		PreparedStatement prep = null;
		Connection connection = null;
		try {
			connection = getConnection();
			prep = connection.prepareStatement(OraclePrepareCommands.GET_OFFICE_BY_ID);
			prep.setBigDecimal(1, new BigDecimal(id));
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
			log.error("GET office by id  sql error",e);
			throw new DataAccessException("GET office by id  sql error",e);
		} finally {
			resClean(connection,prep,null);
		}
	}

	/* (non-Javadoc)
	 * @see model.IDataAccessor#getOfficeByTitle(java.lang.String)
	 */
	@Override
	public IOffice getOfficeByTitle(String title) throws DataAccessException {
		PreparedStatement prep = null;
		Connection connection = null;
		try {
			connection = getConnection();
			prep = connection.prepareStatement(OraclePrepareCommands.GET_OFFICE_BY_TITTLE);
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

	/* (non-Javadoc)
	 * @see model.IDataAccessor#getWorkerByID(java.math.BigInteger)
	 */
	@Override
	public IWorker getWorkerByID(BigInteger id) throws DataAccessException {
		PreparedStatement prep = null;
		Connection connection = null;
		try {
			connection = getConnection();
			prep = connection.prepareStatement(OraclePrepareCommands.GET_WORKER_BY_ID);
			prep.setBigDecimal(1, new BigDecimal(id));
			ResultSet rset = prep.executeQuery();
			IWorker worker = null;
			if (rset.next()) {
				worker = new Worker();
				worker.setID(rset.getBigDecimal(1).toBigInteger());
				worker.setFirstName(rset.getString(2));
				worker.setLastName(rset.getString(3));
				worker.setManagerID(rset.getBigDecimal(4).toBigInteger());
				worker.setDepartmentID(rset.getBigDecimal(5).toBigInteger());
				worker.setJobID(rset.getBigDecimal(6).toBigInteger());
				worker.setOfficeID(rset.getBigDecimal(7).toBigInteger());
				worker.setSalegrade(rset.getInt(8));				
			}
			return worker;
		} catch (SQLException e) {
			log.error("Get worker by id sql error",e);
			throw new DataAccessException("Get worker by id sql error",e);
		} finally {
			resClean(connection,prep,null);
		}
	}

	/* (non-Javadoc)
	 * @see model.IDataAccessor#getWorkerByLastName(java.lang.String)
	 */
	@Override
	public Collection<IWorker> getWorkerByLastName(String lname) throws DataAccessException {
		PreparedStatement prep = null;
		Connection connection = null;
		try {
			connection = getConnection();
			prep = connection.prepareStatement(OraclePrepareCommands.GET_WORKER_BY_LAST_NAME);
			prep.setString(1, lname);
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
				worker.setSalegrade(rset.getInt(8));
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

	/* (non-Javadoc)
	 * @see model.IDataAccessor#getWorkerByMgrID(java.math.BigInteger)
	 */
	@Override
	public Collection<IWorker> getWorkerByMgrID(BigInteger id) throws DataAccessException {
		PreparedStatement prep = null;
		Connection connection = null;
		try {
			connection = getConnection();
			prep = connection.prepareStatement(OraclePrepareCommands.GET_WORKER_BY_MGR_ID);
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
				worker.setSalegrade(rset.getInt(8));
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

	/* (non-Javadoc)
	 * @see model.IDataAccessor#removeDept(java.math.BigInteger)
	 */
	@Override
	public void removeDept(BigInteger id) throws DataAccessException {
		PreparedStatement prep = null;
		Connection connection = null;
		try {
			connection = getConnection();
			connection.setAutoCommit(false);
			prep = connection.prepareStatement(OraclePrepareCommands.REMOVE_DEPT);
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

	/* (non-Javadoc)
	 * @see model.IDataAccessor#removeJob(java.math.BigInteger)
	 */
	@Override
	public void removeJob(BigInteger id) throws DataAccessException {
		PreparedStatement prep = null;
		Connection connection = null;
		try {
			connection = getConnection();
			connection.setAutoCommit(false);
			prep = connection.prepareStatement(OraclePrepareCommands.REMOVE_JOB);
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

	/* (non-Javadoc)
	 * @see model.IDataAccessor#removeOffice(java.math.BigInteger)
	 */
	@Override
	public void removeOffice(BigInteger id) throws DataAccessException {
		PreparedStatement prep = null;
		Connection connection = null;
		try {
			connection = getConnection();
			connection.setAutoCommit(false);
			prep = connection.prepareStatement(OraclePrepareCommands.REMOVE_OFFICE);
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

	/* (non-Javadoc)
	 * @see model.IDataAccessor#removeWorker(java.math.BigInteger)
	 */
	@Override
	public void removeWorker(BigInteger id) throws DataAccessException {
		PreparedStatement prep = null;
		Connection connection = null;
		try {
			connection = getConnection();
			connection.setAutoCommit(false);
			prep = connection.prepareStatement(OraclePrepareCommands.REMOVE_WORKER);
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

	/* (non-Javadoc)
	 * @see model.IDataAccessor#updateDept(model.IDept)
	 */
	@Override
	public void updateDept(IDept dept) throws DataAccessException {
		PreparedStatement prep = null;
		Connection connection = null;
		try {
			connection = getConnection();
			connection.setAutoCommit(false);
			prep = connection.prepareStatement(OraclePrepareCommands.UPDATE_DEPT);
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

	/* (non-Javadoc)
	 * @see model.IDataAccessor#updateJob(model.IJob)
	 */
	@Override
	public void updateJob(IJob job) throws DataAccessException {
		PreparedStatement prep = null;
		Connection connection = null;
		try {
			connection = getConnection();
			connection.setAutoCommit(false);
			prep = connection.prepareStatement(OraclePrepareCommands.UPDATE_JOB);
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

	/* (non-Javadoc)
	 * @see model.IDataAccessor#updateOffice(model.IOffice)
	 */
	@Override
	public void updateOffice(IOffice off) throws DataAccessException {
		PreparedStatement prep = null;
		Connection connection = null;
		try {
			connection = getConnection();
			connection.setAutoCommit(false);
			prep = connection.prepareStatement(OraclePrepareCommands.UPDATE_OFFICE);
			prep.setString(1, off.getTitle());
			prep.setString(2, off.getAddress());
			prep.setBigDecimal(3, new BigDecimal(off.getManagerID()));
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

	/* (non-Javadoc)
	 * @see model.IDataAccessor#updateWorker(model.IWorker)
	 */
	@Override
	public void updateWorker(IWorker worker) throws DataAccessException {
		PreparedStatement prep = null;
		Connection connection = null;
		try {
			connection = getConnection();
			connection.setAutoCommit(false);
			prep = connection.prepareStatement(OraclePrepareCommands.UPDATE_WORKER);
			prep.setString(1, worker.getFirstName());
			prep.setString(2, worker.getLastName());
			prep.setBigDecimal(3, new BigDecimal(worker.getManagerID()));
			prep.setBigDecimal(4, new BigDecimal(worker.getDepartmentID()));
			prep.setBigDecimal(5, new BigDecimal(worker.getJobID()));
			prep.setBigDecimal(6, new BigDecimal(worker.getOfficeID()));
			prep.setInt(7, worker.getSalegrade());
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
}
