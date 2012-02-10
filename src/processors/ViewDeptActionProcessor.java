package processors;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import exceptions.DataAccessException;

import model.DataAccessor;
import model.IDataAccessor;
import model.IDept;


public class ViewDeptActionProcessor implements ActionProcessor {
	private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(ViewDeptActionProcessor.class);
	@Override
	public void process(HttpServletRequest req, HttpServletResponse resp) {
		Integer get_id = (Integer) req.getAttribute("get_id");
		switch (get_id) {
			case ProcessorConst.GET_ALL: {
				IDataAccessor access = DataAccessor.getInstance();
				try {
					Collection<IDept> depts  = access.getAllDepts();
					req.setAttribute("depts", depts);
					try {
						resp.sendRedirect("showdept.jsp");
					} catch (IOException e) {
						log.error("can't redirect on the showdept.jsp",e);
					}
				} catch (DataAccessException e) {
					log.error("can't gets data from IDataAccessor",e);
				} break;
			}
			case ProcessorConst.GET_BY_ID: {
				IDataAccessor access = DataAccessor.getInstance();
				try {
					IDept dept = access.getDeptByID((BigInteger)req.getAttribute("id"));
					req.setAttribute("dept", dept);
					try {
						resp.sendRedirect("showdept.jsp");
					} catch (IOException e) {
						log.error("can't redirect on the showdept.jsp",e);
					}
				} catch (DataAccessException e) {
					log.error("can't gets data from IDataAccessor",e);
				}
				break;
			}
			case ProcessorConst.GET_BY_TITLE: {
				IDataAccessor access = DataAccessor.getInstance();
				try {
					IDept dept = access.getDeptByTitle((String)req.getAttribute("title"));
					req.setAttribute("dept", dept);
					try {
						resp.sendRedirect("showdept.jsp");
					} catch (IOException e) {
						log.error("can't redirect on the showdept.jsp",e);
					}
				} catch (DataAccessException e) {
					log.error("can't gets data from IDataAccessor",e);
				}
				break;
			}
		} 
	}

}
