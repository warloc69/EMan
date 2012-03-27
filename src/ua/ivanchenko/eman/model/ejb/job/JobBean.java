package ua.ivanchenko.eman.model.ejb.job;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import org.apache.log4j.Logger;

import ua.ivanchenko.eman.exceptions.DataAccessException;
import ua.ivanchenko.eman.model.EjbDataAccessorConst;
import ua.ivanchenko.eman.model.ejb.EjbUtil;

public class JobBean implements EntityBean {
	private static final long serialVersionUID = 1L;
	private Logger log = Logger.getLogger("emanlogger");
	private BigInteger ID;
	private String title;
	private String desc;
	private EntityContext context;	
    /**
	  * Method returns department's identifier.
	  */	
    public BigInteger getID() throws RemoteException{
	 return ID;
	}
	/**
	 * returns department's title.
	 */
	public String getTitle() throws RemoteException {
		return title;
	}
	/**
	 * returns department's description.
	 */
	public String getDescription() throws RemoteException {
		return desc;
	}
	/**
	 * method sets department's title. 
	 * @param title inserting title
	 */
	public void setTitle(String title) throws RemoteException {
		this.title = title;
	}
	/**
	 * method sets department's description. 
	 * @param desc inserting description
	 */
	public void setDescription(String desc) throws RemoteException {
		this.desc = desc;
	}
	public void ejbPostCreate(String title, String desc) throws CreateException, DataAccessException {
	}
	public BigInteger ejbCreate(String title, String desc) throws CreateException, DataAccessException {
		    PreparedStatement prep = null;
	        Connection connection = null;
	        ResultSet rset = null;
	        try {
	            connection = EjbUtil.getConnection();
	            prep = connection.prepareStatement(EjbDataAccessorConst.GET_ID);
	            rset = prep.executeQuery();
	            if (rset.next()) {
	               ID = rset.getBigDecimal(1).toBigInteger();
	            }
	            prep.close();
	            prep = connection.prepareStatement(EjbDataAccessorConst.ADD_JOB);
	            prep.setBigDecimal(1, new BigDecimal(ID));
	            prep.setString(2, title);
	            prep.setString(3, desc);
	            prep.executeUpdate();
	            connection.commit();
	            this.title = title;
	            this.desc = desc;
	            return ID;
	        }catch (SQLException e) {
	            log.error("ejbCreate JobBean sql error",e);
	            try {
	                connection.rollback();
	            } catch (SQLException e1) {
	                log.error("can't rollback from job's table error",e);
	                throw new EJBException("can't close Connection",e);
	            }
	            throw new EJBException("ejbCreate JobBean sql error",e);
	        } finally {
	        	EjbUtil.resClean(connection,prep,rset);	        	 
	        }
	}
	public void ejbRemove() {
		 PreparedStatement prep = null;
	     Connection connection = null;	
		try {
			if (EjbUtil.canRemove(EjbDataAccessorConst.GET_WORKER_BY_JOB_ID,ID)) {
				log.warn("try to remove job with workers who work of this job");
				throw new EJBException("You can not remove job because the job is used");
			}	      
            connection = EjbUtil.getConnection();
            prep = connection.prepareStatement(EjbDataAccessorConst.REMOVE_JOB);
            prep.setBigDecimal(1, new BigDecimal(ID));
            prep.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            log.error("ejbRemove job sql error",e);
            try {
                connection.rollback();
            } catch (SQLException e1) {
                log.error("can't rollback commit from job's table",e1);
                throw new EJBException("can't rollback commit from job's table",e1);
            }
            throw new EJBException("ejbRemove job sql error",e);
        } catch (DataAccessException e) {
        	throw new EJBException(e);
		} finally {
            try {
            	EjbUtil.resClean(connection,prep,null);
			} catch (DataAccessException e) {
				throw new EJBException(e);
			}
        }
	}
	public void ejbStore()  {
		 PreparedStatement prep = null;
	        Connection connection = null;
	        try {
	            connection = EjbUtil.getConnection();
	            prep = connection.prepareStatement(EjbDataAccessorConst.UPDATE_JOB);
	            prep.setString(1, title);
	            prep.setString(2, desc);
	            prep.setBigDecimal(3, new BigDecimal(ID));
	            prep.executeUpdate();
	            connection.commit();
	        } catch (SQLException e) {
	            log.error("ejbStore JobBean sql error",e);
	            try {
	                connection.rollback();
	            } catch (SQLException e1) {
	                log.error("can't rollback commit job's table",e1);
	                throw new EJBException("can't rollback commit job's table",e1);
	            }
	            throw new EJBException("ejbStore job's table sql error",e);            
	        } catch (DataAccessException e) {
	        	throw new EJBException("can't get coonection",e);
			} finally {
	            try {
	            	EjbUtil.resClean(connection,prep,null);
				} catch (DataAccessException e) {
					throw new EJBException(e);
				}
	        }

	}
	public void ejbActivate() {
		ID = (BigInteger) context.getPrimaryKey();
	}
	public void ejbLoad() {
		 PreparedStatement prep = null;
	     Connection connection = null;
	        try {
	            connection = EjbUtil.getConnection();
	            prep = connection.prepareStatement(EjbDataAccessorConst.GET_JOB_BY_ID);
	            ID = (BigInteger) context.getPrimaryKey();
	            prep.setBigDecimal(1, new BigDecimal(ID));
	            ResultSet rset = prep.executeQuery();
	            if (rset.next()) {
	               this.title = rset.getString(1);
	               this.desc = rset.getString(2);
	            }
	        } catch (SQLException e) {
	            log.error("ejbLoad JobBean sql error",e);
	            throw new EJBException("ejbLoad JobBean sql error",e);
	        } catch (DataAccessException e) {
	        	 throw new EJBException(e);
			} finally {
	            try {
	            	EjbUtil.resClean(connection,prep,null);
				} catch (DataAccessException e) {
					 throw new EJBException(e);
				}
	        }
	}
	public void ejbPassivate()  {
		ID = null;
		this.title = null;
		this.desc = null;
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
	            connection = EjbUtil.getConnection();
	            prep = connection.prepareStatement(EjbDataAccessorConst.GET_JOB_PRIMARY_KEY_BY_ID);
	            prep.setBigDecimal(1, new BigDecimal(id));
	            ResultSet rset = prep.executeQuery();
	            if (rset.next()) {
	            	return id;
	            }
	        } catch (SQLException e) {
	            log.error("ejbFindByPrimaryKey job sql error",e);
	            throw new EJBException("ejbFindByPrimaryKey JobBean sql error",e);
	        } catch (DataAccessException e) {
	        	 throw new EJBException(e);
			} finally {
	            try {
	            	EjbUtil.resClean(connection,prep,null);
				} catch (DataAccessException e) {
					 throw new EJBException(e);
				}
	        }
		return null;
	}
	public BigInteger ejbFindByTitle(String title){
		PreparedStatement prep = null;
	     Connection connection = null;
	        try {
	            connection = EjbUtil.getConnection();
	            prep = connection.prepareStatement(EjbDataAccessorConst.GET_JOB_PRIMARY_KEY_BY_TITLE);
	            prep.setString(1, title);
	            ResultSet rset = prep.executeQuery();
	            if (rset.next()) {
	            	return rset.getBigDecimal(1).toBigInteger();
	            }
	        } catch (SQLException e) {
	            log.error("ejbFindByTitle JobBean by title sql error",e);
	            throw new EJBException("ejbFindByTitle JobBean by title sql error",e);
	        } catch (DataAccessException e) {
	        	 throw new EJBException(e);
			} finally {
	            try {
	            	EjbUtil.resClean(connection,prep,null);
				} catch (DataAccessException e) {
					 throw new EJBException(e);
				}
	        }
		return null;
	}
	public Collection ejbFindAll(String sort){
		PreparedStatement prep = null;
	     Connection connection = null;
	        try {
	            connection = EjbUtil.getConnection();
	            if (sort == null) {
	            	prep = connection.prepareStatement(EjbDataAccessorConst.GET_ALL_JOBS);
	            } else {
	            	prep = connection.prepareStatement(EjbDataAccessorConst.GET_ALL_JOBS+" order by "+sort);
	            }
	            ResultSet rset = prep.executeQuery();
	            ArrayList ar = new ArrayList();
	            while (rset.next()) {
	            	ar.add(rset.getBigDecimal(1).toBigInteger());
	            }
	            if(ar.size()>0)
	            	return ar;
	        } catch (SQLException e) {
	            log.error("ejbFindAll JobBean sql error",e);
	            throw new EJBException("ejbFindAll JobBean sql error",e);
	        } catch (DataAccessException e) {
	        	 throw new EJBException(e);
			} finally {
	            try {
	            	EjbUtil.resClean(connection,prep,null);
				} catch (DataAccessException e) {
					 throw new EJBException(e);
				}
	        }
		return null;
	}
}

