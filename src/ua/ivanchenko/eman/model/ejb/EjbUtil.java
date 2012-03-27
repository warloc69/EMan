package ua.ivanchenko.eman.model.ejb;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import ua.ivanchenko.eman.exceptions.DataAccessException;
import ua.ivanchenko.eman.model.EjbDataAccessorConst;

public class EjbUtil {
	private static Logger log = Logger.getLogger("emanlogger");
	public static boolean canRemove(String command, BigInteger id) throws DataAccessException {
   	 	PreparedStatement prep = null;
        Connection connection = null;
        try {
            connection = getConnection();
            prep = connection.prepareStatement(command);
            prep.setBigDecimal(1, new BigDecimal(id));
            ResultSet rset = prep.executeQuery();            
            return rset.next();
        } catch (SQLException e) {
            log.error("remove bean by id sql error",e);
            throw new DataAccessException("remove bean by id sql error",e);
        } finally {
            resClean(connection,prep,null);
        }
   }
/**
 * return new connection to the DataSource.
 * @throws DataAccessException if class can't get access to DataSource.
 */
public static Connection getConnection() throws DataAccessException{
    Connection connection = null;
    try {
        Context context = new InitialContext();       
        DataSource source = (DataSource) context.lookup(EjbDataAccessorConst.DATA_SOURCE);
        connection = source.getConnection();
        connection.setAutoCommit(false);
        return connection;
    } catch (NamingException e) {
        log.error("EjbDataAccessor context error",e);
        throw new DataAccessException("EjbDataAccessor context error",e);
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
public static void resClean (Connection con, PreparedStatement prep, ResultSet rset) throws DataAccessException{
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
}
