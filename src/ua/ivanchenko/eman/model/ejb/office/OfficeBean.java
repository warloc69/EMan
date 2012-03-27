package ua.ivanchenko.eman.model.ejb.office;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
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

public class OfficeBean implements EntityBean {
	private static final long serialVersionUID = 1L;
	private Logger log = Logger.getLogger("emanlogger");
	private BigInteger ID;	
	private EntityContext context;
    private String title = null;
    private String address = null;
    private BigInteger mgrid = null;
    private boolean changed = false;
    /**
     * Method returns address.
     */
    public String getAddress() {
        return address;
    }
    /**
     * method inserts into the object the address.
     * @param adr inserting address. 
     */
    public void setAddress(String address) {
        this.address = address;
        this.changed = true;
    }
    /**
     * Method returns manager's identifier.
     */
    public BigInteger getManagerID() {
        return mgrid;
    }
    /**
     * method sets manager's identifier 
     * @param id inserting id.
     */
    public void setManagerID(BigInteger mgrid) {
        this.mgrid = mgrid;
        this.changed = true;
    }

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
	 * method sets department's title. 
	 * @param title inserting title
	 */
	public void setTitle(String title) throws RemoteException {
		this.title = title;
		this.changed = true;
	}
	public void ejbPostCreate(String title,String address,BigInteger mgrid) throws CreateException, DataAccessException {
		
	}
	public BigInteger ejbCreate(String title,String address,BigInteger mgrid) throws CreateException, DataAccessException {
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
	            prep = connection.prepareStatement(EjbDataAccessorConst.ADD_OFFICE);
	            prep.setBigDecimal(1, new BigDecimal(ID));
	            prep.setString(2, title);
	            prep.setString(3, address);
	            if(mgrid != null) {
	            	prep.setBigDecimal(4, new BigDecimal(mgrid));
	            } else {
	            	prep.setNull(4, Types.NUMERIC);
	            }
	            prep.executeUpdate();
	            connection.commit();
	            this.address = address;
	            this.mgrid = mgrid;
	            this.title = title;
	            this.changed = true;
	            return ID;
	        }catch (SQLException e) {
	            log.error("ejbCreate OfficeBean sql error",e);
	            try {
	                connection.rollback();
	            } catch (SQLException e1) {
	                log.error("can't rollback from office's table error",e);
	                throw new EJBException("can't close Connection",e);
	            }
	            throw new EJBException("ejbCreate OfficeBean sql error",e);
	        } finally {
	        	EjbUtil.resClean(connection,prep,rset);	        	 
	        }
	}
	public void ejbRemove() {
		PreparedStatement prep = null;
        Connection connection = null;	
		try {			
			if (EjbUtil.canRemove(EjbDataAccessorConst.GET_WORKER_BY_OFFICE_ID,ID)) {
				log.warn("try to remove office with worker");
				throw new EJBException("You can not remove office because the office is used");
			} 
            connection = EjbUtil.getConnection();
            prep = connection.prepareStatement(EjbDataAccessorConst.REMOVE_OFFICE);
            prep.setBigDecimal(1, new BigDecimal(ID));
            prep.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            log.error("ejbRemove OfficeBean sql error",e);
            try {
                connection.rollback();
            } catch (SQLException e1) {
                log.error("can't rollback commit from office's table",e1);
                throw new EJBException("can't rollback commit from office's table",e1);
            }
            throw new EJBException("ejbRemove OfficeBean sql error",e);
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
	            prep = connection.prepareStatement(EjbDataAccessorConst.UPDATE_OFFICE);
	            prep.setString(1, title);
	            prep.setString(2, address);
	            if(mgrid != null) {
	            	prep.setBigDecimal(3, new BigDecimal(mgrid));
	            } else {
	            	prep.setNull(3, Types.NUMERIC);
	            }
	            prep.setBigDecimal(4, new BigDecimal(ID));
	            prep.executeUpdate();
	            connection.commit();
	            this.changed = false;
	           
	        } catch (SQLException e) {
	            log.error("ejbStore OfficeBean sql error",e);
	            try {
	                connection.rollback();
	            } catch (SQLException e1) {
	                log.error("can't rollback commit office's table",e1);
	                throw new EJBException("can't rollback commit office's table",e1);
	            }
	            throw new EJBException("ejbStore office's table sql error",e);            
	        } catch (DataAccessException e) {
	        	throw new EJBException("can't get conection",e);
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
	            prep = connection.prepareStatement(EjbDataAccessorConst.GET_OFFICE_BY_ID);
	            ID = (BigInteger) context.getPrimaryKey();
	            prep.setBigDecimal(1, new BigDecimal(ID));
	            ResultSet rset = prep.executeQuery();
	            if (rset.next()) {
	               this.title = rset.getString(1);
	               this.address = rset.getString(2);
	               if (rset.getBigDecimal(3) != null) {
	            	   this.mgrid = rset.getBigDecimal(3).toBigInteger();
	               } else {
	            	   this.mgrid = null;
	               }
	               this.changed = false;
	            }
	        } catch (SQLException e) {
	            log.error("ejbLoad OfficeBean sql error",e);
	            throw new EJBException("ejbLoad OfficeBean sql error",e);
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
		this.address = null;
		this.mgrid = null;
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
	            prep = connection.prepareStatement(EjbDataAccessorConst.GET_OFFICE_PRIMARY_KEY_BY_ID);
	            prep.setBigDecimal(1, new BigDecimal(id));
	            ResultSet rset = prep.executeQuery();
	            if (rset.next()) {
	            	return id;
	            }
	        } catch (SQLException e) {
	            log.error("ejbFindByPrimaryKey OfficeBean sql error",e);
	            throw new EJBException("ejbFindByPrimaryKey OfficeBean sql error",e);
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
	            prep = connection.prepareStatement(EjbDataAccessorConst.GET_OFFICE_PRIMARY_KEY_BY_TITLE);
	            prep.setString(1, title);
	            ResultSet rset = prep.executeQuery();
	            if (rset.next()) {
	            	return rset.getBigDecimal(1).toBigInteger();
	            }
	        } catch (SQLException e) {
	            log.error("ejbFindByTitle OfficeBean by Title sql error",e);
	            throw new EJBException("ejbFindByTitle OfficeBean by Title sql error",e);
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
	            	prep = connection.prepareStatement(EjbDataAccessorConst.GET_ALL_OFFICES);
	            } else {
	            	log.info("sql : "+ EjbDataAccessorConst.GET_ALL_OFFICES+" order by "+sort);
	            	prep = connection.prepareStatement(EjbDataAccessorConst.GET_ALL_OFFICES+" order by "+sort);
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
	            log.error("ejbFindAll office sql error",e);
	            throw new EJBException("ejbFindAll office sql error",e);
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
