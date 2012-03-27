package ua.ivanchenko.eman.model.ejb.dept;

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

public class DeptBean implements EntityBean {
	private static final long serialVersionUID = 1L;
	private Logger log = Logger.getLogger("emanlogger");
	private BigInteger ID;
	private String title;
	private String desc;
	private EntityContext context;
	private boolean changed = false;
	
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
		this.changed = true;
	}
	/**
	 * method sets department's description. 
	 * @param desc inserting description
	 */
	public void setDescription(String desc) throws RemoteException {
		this.desc = desc;
		this.changed = true;
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
	            prep = connection.prepareStatement(EjbDataAccessorConst.ADD_DEPT);
	            prep.setBigDecimal(1, new BigDecimal(ID));
	            prep.setString(2, title);
	            prep.setString(3, desc);
	            prep.executeUpdate();
	            connection.commit();
	            this.title=title;
	            this.desc=desc;
	            this.changed = true;
	            return ID;
	        }catch (SQLException e) {
	            log.error("ejbCreate DeptBean sql error",e);
	            try {
	                connection.rollback();
	            } catch (SQLException e1) {
	                log.error("can't rollback from department's table error",e);
	                throw new EJBException("can't close Connection",e);
	            }
	            throw new EJBException("ejbCreate DeptBean sql error",e);
	        } finally {
	        	EjbUtil.resClean(connection,prep,rset);	        	 
	        }
	}
	public void ejbRemove() {
		 PreparedStatement prep = null;
	     Connection connection = null;
		try {
			if (EjbUtil.canRemove(EjbDataAccessorConst.GET_WORKER_BY_DEPT_ID,ID)) {
				log.warn("try to remove departments with workers");
				throw new EJBException("You can not remove department because the department is used");
			}       
            connection = EjbUtil.getConnection();
            prep = connection.prepareStatement(EjbDataAccessorConst.REMOVE_DEPT);
            prep.setBigDecimal(1, new BigDecimal(ID));
            prep.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            log.error("ejbRemove DeptBean sql error",e);
            try {
                connection.rollback();
            } catch (SQLException e1) {
                log.error("can't rollback commit from department's table",e1);
                throw new EJBException("can't rollback commit from department's table",e1);
            }
            throw new EJBException("ejbRemove DeptBean sql error",e);
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
	        if (!this.changed) {
	        	return;
	        }
	        try {
	            connection = EjbUtil.getConnection();
	            prep = connection.prepareStatement(EjbDataAccessorConst.UPDATE_DEPT);
	            prep.setString(1, title);
	            prep.setString(2, desc);
	            prep.setBigDecimal(3, new BigDecimal(ID));
	            prep.executeUpdate();
	            connection.commit();
	            this.changed = false;
	        } catch (SQLException e) {
	            log.error("ejbStore DeptBean sql error",e);
	            try {
	                connection.rollback();
	            } catch (SQLException e1) {
	                log.error("can't rollback commit department's table",e1);
	                throw new EJBException("can't rollback commit department's table",e1);
	            }
	            throw new EJBException("ejbStore department's table sql error",e);            
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
		this.changed = false;
	}
	public void ejbLoad() {
		 PreparedStatement prep = null;
	     Connection connection = null;
	     if (this.title != null) {
	    	 return;
	     }
	        try {
	            connection = EjbUtil.getConnection();
	            prep = connection.prepareStatement(EjbDataAccessorConst.GET_DEPTS_BY_ID);
	            ID = (BigInteger) context.getPrimaryKey();
	            prep.setBigDecimal(1, new BigDecimal(ID));
	            ResultSet rset = prep.executeQuery();
	            if (rset.next()) {
	               this.title = rset.getString(1);
	               this.desc = rset.getString(2);
	            }
	        } catch (SQLException e) {
	            log.error("ejbLoad DeptBean sql error",e);
	            throw new EJBException("ejbLoad DeptBean sql error",e);
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
		this.changed = false;
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
	            prep = connection.prepareStatement(EjbDataAccessorConst.GET_DEPT_PRIMARY_KEY_BY_ID);
	            prep.setBigDecimal(1, new BigDecimal(id));
	            ResultSet rset = prep.executeQuery();
	            if (rset.next()) {
	            	return id;
	            }
	        } catch (SQLException e) {
	            log.error("ejbFindByPrimaryKey DeptBean sql error",e);
	            throw new EJBException("ejbFindByPrimaryKey DeptBean sql error",e);
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
	            prep = connection.prepareStatement(EjbDataAccessorConst.GET_DEPT_PRIMARY_KEY_BY_TITLE);
	            prep.setString(1, title);
	            ResultSet rset = prep.executeQuery();
	            if (rset.next()) {
	            	return rset.getBigDecimal(1).toBigInteger();
	            }
	        } catch (SQLException e) {
	            log.error("ejbFindByTitle DeptBean by title sql error",e);
	            throw new EJBException("ejbFindByTitle DeptBean by title  sql error",e);
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
	            	prep = connection.prepareStatement(EjbDataAccessorConst.GET_ALL_DEPTS);
	            } else {
	            	log.info("sql : "+ EjbDataAccessorConst.GET_ALL_DEPTS+" order by "+sort);
	            	prep = connection.prepareStatement(EjbDataAccessorConst.GET_ALL_DEPTS+" order by "+sort);
	            }
	            ResultSet rset = prep.executeQuery();
	            ArrayList ar = new ArrayList();
	            while (rset.next()) {
	            	ar.add(rset.getBigDecimal(1).toBigInteger());
	            }
	            if(ar.size()>0)
	            	return ar;
	        } catch (SQLException e) {
	            log.error("ejbFindAll dept sql error",e);
	            throw new EJBException("ejbFindAll dept sql error",e);
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
