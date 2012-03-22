package ua.ivanchenko.eman.model.ejb.worker;

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
import java.util.LinkedList;
import java.util.Properties;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import ua.ivanchenko.eman.exceptions.DataAccessException;
import ua.ivanchenko.eman.model.EjbDataAccessorConst;
import ua.ivanchenko.eman.model.OracleDataAccessorConst;
import ua.ivanchenko.eman.processors.FiltreConst;

public class WorkerBean implements EntityBean {
	private static final long serialVersionUID = 1L;
	private Logger log = Logger.getLogger(WorkerBean.class);
	private BigInteger ID = null;
    private String fname = null;
    private String lname = null;
    private BigInteger mgrid = null;
    private BigInteger depid = null;
    private BigInteger jobid = null;
    private BigInteger officeid = null;
    private double salegrade = 0;
	private EntityContext context;
    
	public String getFirstName() throws java.rmi.RemoteException{
		return fname;
	}
	public void setFirstName(String fname) throws java.rmi.RemoteException{
		this.fname = fname;
	}
	public String getLastName() throws java.rmi.RemoteException{
		return lname;
	}
	public void setLastName(String lname) throws java.rmi.RemoteException{
		this.lname = lname;
	}
	public BigInteger getManagerID() throws java.rmi.RemoteException{
		return mgrid;
	}
	public void setManagerID(BigInteger id) throws java.rmi.RemoteException{
		this.mgrid = id;
	}
	public BigInteger getDepartmentID() throws java.rmi.RemoteException{
		return depid;
	}
	public void setDepartmentID(BigInteger id) throws java.rmi.RemoteException{
		this.depid = id;
	}
	public BigInteger getJobID() throws java.rmi.RemoteException{
		return jobid;
	}
	public void setJobID(BigInteger id) throws java.rmi.RemoteException{
		this.jobid = id;
	}
	public BigInteger getOfficeID() throws java.rmi.RemoteException{
		return officeid;
	}
	public void setOfficeID(BigInteger id) throws java.rmi.RemoteException{
		this.officeid = id;
	}
	public double getSalegrade() throws java.rmi.RemoteException{
		return salegrade;
	}
	public void setSalegrade(double sale) throws java.rmi.RemoteException{
		this.salegrade = sale;
	}
	public BigInteger getID() throws java.rmi.RemoteException{
		return ID;
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
    /**
     * return new connection to the DataSource.
     * @throws DataAccessException if class can't get access to DataSource.
     */
    private Connection getConnection() throws DataAccessException{
        Connection connection = null;
        try {
            Context context = new InitialContext();      
            DataSource source = (DataSource) context.lookup(EjbDataAccessorConst.DATA_SOURCE);
            connection = source.getConnection();
            connection.setAutoCommit(false);
            return connection;
        } catch (NamingException e) {
            log.error("OracleDataAccesor context error",e);
            throw new DataAccessException("OracleDataAccesor context error",e);
        } catch (SQLException e1) {
            log.error("can't get connection sql error {connection = "+ connection+ "}",e1);
            throw new DataAccessException("can't get connection sql error {connection = "+ connection+ "}",e1);
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
    public void ejbPostCreate(String fname, String lname, BigInteger mgr_id, BigInteger office_id, BigInteger job_id, BigInteger dept_id, double sale) 
			throws CreateException, DataAccessException {
    	
    }
	
	public BigInteger ejbCreate(String fname, String lname, BigInteger mgr_id, BigInteger office_id, BigInteger job_id, BigInteger dept_id, double sale) 
				throws CreateException, DataAccessException {
		    PreparedStatement prep = null;
	        Connection connection = null;
	        ResultSet rset = null;
	        try {	
	            connection = getConnection();
	            prep = connection.prepareStatement(EjbDataAccessorConst.GET_ID);
	            rset = prep.executeQuery();
	            if (rset.next()) {
	               ID = rset.getBigDecimal(1).toBigInteger();
	            }
	            prep.close();
	            prep = connection.prepareStatement(EjbDataAccessorConst.ADD_WORKER);
	            prep.setBigDecimal(1, new BigDecimal(ID));
	            prep.setString(2, fname);
	            prep.setString(3, lname);
	            if(mgrid != null) {
	            	prep.setBigDecimal(4, new BigDecimal(mgr_id));
	            } else {
	            	prep.setNull(4, Types.NUMERIC);
	            }
	            prep.setBigDecimal(5, new BigDecimal(dept_id));
	            prep.setBigDecimal(6, new BigDecimal(job_id));
	            prep.setBigDecimal(7, new BigDecimal(office_id));
	            prep.setDouble(8, sale);
	            prep.executeUpdate();
	            connection.commit();
	            this.fname = fname;
	            this.lname = lname;
	            this.mgrid = mgr_id;
	            this.officeid = office_id;
	            this.jobid = job_id;
	            this.depid = dept_id;
	            this.salegrade = sale;
	            return ID;
	        }catch (SQLException e) {
	            log.error("add worker sql error",e);
	            try {
	                connection.rollback();
	            } catch (SQLException e1) {
	                log.error("can't rollback from worker's table error",e);
	                throw new EJBException("can't close Connection",e);
	            }
	            throw new EJBException("add worker sql error",e);
	        } finally {
	        	resClean(connection,prep,rset);	        	 
	        }
	}
	public void ejbRemove() throws java.rmi.RemoteException{
		try {
			if (canRemove(EjbDataAccessorConst.GET_WORKER_BY_MGR_ID,ID)) {
				throw new EJBException("You can not remove worker because the office is used");
			}
		} catch (DataAccessException e2) {
			log.error("cannot remove worker",e2);
		}
        PreparedStatement prep = null;
        Connection connection = null;
        try {
            connection = getConnection();
            prep = connection.prepareStatement(EjbDataAccessorConst.REMOVE_WORKER);
            prep.setBigDecimal(1, new BigDecimal(ID));
            prep.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            log.error("remove worker sql error",e);
            try {
                connection.rollback();
            } catch (SQLException e1) {
                log.error("can't rollback commit from worker's table",e1);
                throw new EJBException("can't rollback commit from worker's table",e1);
            }
            throw new EJBException("remove worker sql error",e);
        } catch (DataAccessException e) {
        	log.error("ejbRemove id= "+ID,e);
        	throw new EJBException(e);
		} finally {
            try {
				resClean(connection,prep,null);
			} catch (DataAccessException e) {
				log.error("ejbRemove id= "+ID,e);
				throw new EJBException(e);
			}
        }
	}
	public void ejbStore()  throws java.rmi.RemoteException{
		 PreparedStatement prep = null;
	        Connection connection = null;
	        try {
	            connection = getConnection();
	            prep = connection.prepareStatement(EjbDataAccessorConst.UPDATE_WORKER);
	            prep.setString(1, this.fname);
	            prep.setString(2, this.lname);
	            if(mgrid != null) {
	            	prep.setBigDecimal(3, new BigDecimal(mgrid));
	            } else {
	            	prep.setNull(3, Types.NUMERIC);
	            }
	            prep.setBigDecimal(4, new BigDecimal(this.depid));
	            prep.setBigDecimal(5, new BigDecimal(this.jobid));
	            prep.setBigDecimal(6, new BigDecimal(this.officeid));
	            prep.setDouble(7, this.salegrade);
	            prep.setBigDecimal(8, new BigDecimal(ID));
	            prep.executeUpdate();
	            connection.commit();
	        } catch (SQLException e) {
	            log.error("update worker sql error",e);
	            try {
	                connection.rollback();
	            } catch (SQLException e1) {
	                log.error("can't rollback commit worker's table",e1);
	                throw new EJBException("can't rollback commit worker's table",e1);
	            }
	            throw new EJBException("update worker's table sql error",e);            
	        } catch (DataAccessException e) {
	        	log.error("ejbStore id= "+ID,e);
	        	throw new EJBException("can't get conection",e);
			} finally {
	            try {
					resClean(connection,prep,null);
				} catch (DataAccessException e) {
					log.error("EjbStore id= "+ID,e);
					throw new EJBException(e);
				}
	        }

	}
	public void ejbActivate() throws java.rmi.RemoteException{		
		ID = (BigInteger) context.getPrimaryKey();
	}
	
	public void ejbLoad() throws java.rmi.RemoteException{
		 PreparedStatement prep = null;
	     Connection connection = null;
	        try {
	            connection = getConnection();
	            prep = connection.prepareStatement(EjbDataAccessorConst.GET_WORKER_BY_ID);
	            prep.setBigDecimal(1, new BigDecimal(ID));
	            ResultSet rset = prep.executeQuery();
	            if (rset.next()) {
	               this.fname = rset.getString(1);
	               this.lname = rset.getString(2);
	               if (rset.getBigDecimal(3) != null) {
	            	   this.mgrid = rset.getBigDecimal(3).toBigInteger();
	               } else {
	            	   this.mgrid = null;
	               }
	               this.depid = rset.getBigDecimal(4).toBigInteger();
	               this.jobid = rset.getBigDecimal(5).toBigInteger();	               
	               this.officeid = rset.getBigDecimal(6).toBigInteger();
	               this.salegrade = rset.getDouble(7);
	            }
	        } catch (SQLException e) {
	            log.error("GET worker by id sql error",e);
	            throw new EJBException("GET worker by id sql error",e);
	        } catch (DataAccessException e) {
	        	log.error("ejbLoad id= "+ID,e);
	        	 throw new EJBException(e);
			} finally {
	            try {
					resClean(connection,prep,null);
				} catch (DataAccessException e) {
					log.error("EjbLoad res clin error id= "+ID,e);
					throw new EJBException(e);
				}
	        }
	}
	public void ejbPassivate()  throws java.rmi.RemoteException{
		ID = null;
		this.fname = null;
        this.lname = null;
        this.mgrid = null;
        this.officeid = null;
        this.jobid = null;
        this.depid = null;
        this.salegrade = 0;
		
	}
	public void setEntityContext(EntityContext entityContext)  {
		this.context = entityContext;
	}
	public void unsetEntityContext()  {
		this.context = null;
	}
	public BigInteger ejbFindByPrimaryKey(BigInteger id) {
		PreparedStatement prep = null;
	     Connection connection = null;
	        try {
	            connection = getConnection();
	            prep = connection.prepareStatement(EjbDataAccessorConst.GET_WORKER_PRIMARY_KEY_BY_ID);
	            prep.setBigDecimal(1, new BigDecimal(id));
	            ResultSet rset = prep.executeQuery();
	            if (rset.next()) {
	            	return id;
	            }
	        } catch (SQLException e) {
	            log.error("GET worker by id sql error",e);
	            throw new EJBException("GET worker by id sql error",e);
	        } catch (DataAccessException e) {
	        	log.error("ejbFindbyId id= "+ID,e);
	        	 throw new EJBException(e);
			} finally {
	            try {
					resClean(connection,prep,null);
				} catch (DataAccessException e) {
					log.error("ejbFindById id= "+ID,e);
					 throw new EJBException(e);
				}
	        }
		return null;
	}
	public Collection ejbFindByName(String name){
		PreparedStatement prep = null;
	     Connection connection = null;
	        try {
	            connection = getConnection();
	            prep = connection.prepareStatement(EjbDataAccessorConst.GET_WORKER_PRIMARY_KEY_BY_NAME);
	            prep.setString(1, name);
	            prep.setString(2, name);
	            ResultSet rset = prep.executeQuery();
	            ArrayList ar = new ArrayList();
	            while (rset.next()) {
	            	ar.add(rset.getBigDecimal(1).toBigInteger());
	            }
	            if(ar.size()>0) {
	            	return ar;
	            }
	        } catch (SQLException e) {
	            log.error("GET worker by id sql error",e);
	            throw new EJBException("GET worker by id sql error",e);
	        } catch (DataAccessException e) {
	        	log.error("EjbFindByName id= "+ID,e);
	        	 throw new EJBException(e);
			} finally {
	            try {
					resClean(connection,prep,null);
				} catch (DataAccessException e) {
					log.error("ejbFindByName id= "+ID,e);
					 throw new EJBException(e);
				}
	        }
		return null;
	}
	public Collection ejbFindByManagerID(BigInteger id){
		PreparedStatement prep = null;
	     Connection connection = null;
	        try {
	            connection = getConnection();
	            prep = connection.prepareStatement(EjbDataAccessorConst.GET_WORKER_PRIMARY_KEY_BY_MGR_ID);
	            prep.setBigDecimal(1, new BigDecimal(id));
	            ResultSet rset = prep.executeQuery();
	            ArrayList ar = new ArrayList();
	            while (rset.next()) {
	            	ar.add(rset.getBigDecimal(1).toBigInteger());
	            }
	            if(ar.size()>0) {
	            	return ar;
	            }
	        } catch (SQLException e) {
	            log.error("GET worker by id sql error",e);
	            throw new EJBException("GET worker by id sql error",e);
	        } catch (DataAccessException e) {
	        	log.error("EjbFindByMgr id= "+ID,e);
	        	 throw new EJBException(e);
			} finally {
	            try {
					resClean(connection,prep,null);
				} catch (DataAccessException e) {
					log.error("ejbFindbyMgr id= "+ID,e);
					 throw new EJBException(e);
				}
	        }
		return null;
	}
	public Collection ejbFindAll(String sort){
		PreparedStatement prep = null;
	     Connection connection = null;
	        try {
	            connection = getConnection();
	            if (sort == null) {
	            	prep = connection.prepareStatement(EjbDataAccessorConst.GET_ALL_WORKERS_PRIMARY_KEY);
	            } else {
	            	log.info("sql : "+ EjbDataAccessorConst.GET_ALL_WORKERS_PRIMARY_KEY+" order by "+sort);
	            	prep = connection.prepareStatement(EjbDataAccessorConst.GET_ALL_WORKERS_PRIMARY_KEY+" order by "+sort);
	            }
	            ResultSet rset = prep.executeQuery();
	            ArrayList ar = new ArrayList();
	            while (rset.next()) {
	            	ar.add(rset.getBigDecimal(1).toBigInteger());
	            }
	            if(ar.size()>0) {
	            	return ar;
	            }
	        } catch (SQLException e) {
	            log.error("GET worker sql error",e);
	            throw new EJBException("GET worker sql error",e);
	        } catch (DataAccessException e) {
	        	log.error("ejbFind all id= "+ID,e);
	        	 throw new EJBException(e);
			} finally {
	            try {
					resClean(connection,prep,null);
				} catch (DataAccessException e) {
					log.error("ejbFind all id= "+ID,e);
					 throw new EJBException(e);
				}
	        }
		return null;
	}
	public Collection ejbFindTopManager(String sort) {
		PreparedStatement prep = null;
	     Connection connection = null;
	        try {
	            connection = getConnection();
	            if (sort == null) {
	            	prep = connection.prepareStatement(EjbDataAccessorConst.GET_TOP_MANAGER_PRIMARY_KEY);
	            } else {
	            	prep = connection.prepareStatement(EjbDataAccessorConst.GET_TOP_MANAGER_PRIMARY_KEY+" order by "+sort);
	            }
	            ResultSet rset = prep.executeQuery();
	            ArrayList ar = new ArrayList();
	            while (rset.next()) {
	            	ar.add(rset.getBigDecimal(1).toBigInteger());
	            }
	             return ar;
	        } catch (SQLException e) {
	            log.error("GET worker sql error",e);
	            throw new EJBException("GET worker sql error",e);
	        } catch (DataAccessException e) {
	        	log.error("ejbFindTopManager id= "+ID,e);
	        	 throw new EJBException(e);
			} finally {
	            try {
					resClean(connection,prep,null);
				} catch (DataAccessException e) {
					log.error("EjbFindbyTopmanager id= "+ID,e);
					 throw new EJBException(e);
				}
	        }
	}
	public Collection ejbFindPath(BigInteger id) {
		PreparedStatement prep = null;
	     Connection connection = null;
	        try {
	            connection = getConnection();
	            prep = connection.prepareStatement(EjbDataAccessorConst.GET_PATH);
	            prep.setBigDecimal(1, new BigDecimal(id));
	            ResultSet rset = prep.executeQuery();
	            LinkedList ar = new LinkedList();
	            while (rset.next()) {
	            	ar.add(rset.getBigDecimal(1).toBigInteger());
	            }
	            if(ar.size()>0) {
	            	return ar;
	            }
	        } catch (SQLException e) {
	            log.error("GET worker sql error",e);
	            throw new EJBException("GET worker sql error",e);
	        } catch (DataAccessException e) {
	        	log.error("ejbFind Path id= "+ID,e);
	        	 throw new EJBException(e);
			} finally {
	            try {
					resClean(connection,prep,null);
				} catch (DataAccessException e) {
					log.error("ejbFindPath id= "+ID,e);
					 throw new EJBException(e);
				}
	        }
		return null;
	}
	public Collection ejbFindFilteringWorker(BigInteger id, HashMap<String,String> filters) {
		PreparedStatement prep = null;
        Connection connection = null;
        try {
        	connection = getConnection();
            StringBuffer sb = new StringBuffer();
            sb.append("select employees.ID from employees ");
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
            log.debug("ejbFilteringWorker query : " +  sb.toString());
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
            LinkedList<BigInteger> ls = new LinkedList<BigInteger>();
            while (rset.next()) {               
                ls.add(rset.getBigDecimal("ID").toBigInteger());
            }
            log.info(ls);
            return ls;
        } catch (SQLException e) {
            log.error("Get worker by mgr id sql error",e);
            throw new EJBException("Get worker by mgr id sql error",e);
        } catch (DataAccessException e) {
        	log.error("Ejb filtre id= "+ID,e);
        	throw new EJBException("Get worker by mgr id sql error",e);
		} finally {
            try {
				resClean(connection,prep,null);
			} catch (DataAccessException e) {
				log.error("ejb Filtre id= "+ID,e);
				throw new EJBException("Get worker by mgr id sql error",e);
			}
        }
	}
}