package processors;

import java.io.IOException;
import java.math.BigInteger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.DataAccessor;
import model.Dept;
import model.IDataAccessor;
import model.IDept;
import exceptions.DataAccessException;

public class EditDeptActionProcessor implements ActionProcessor {
	private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(EditDeptActionProcessor.class);
	@Override
	public void process(HttpServletRequest req, HttpServletResponse resp) {
		Integer action_id = (Integer) req.getAttribute("action_id");
		switch (action_id) {
			case ProcessorConst.ACTION_ADD: {
				IDataAccessor access = DataAccessor.getInstance();
				try {
					IDept dept  = new Dept();
					dept.setTitle((String)req.getAttribute("title"));
					dept.setDescription((String)req.getAttribute("desc"));
					access.addDept(dept);
					try {
						resp.sendRedirect("showdepts.jsp");
					} catch (IOException e) {
						log.error("can't redirect on the showdepts.jsp",e);
					}
				} catch (DataAccessException e) {
					log.error("can't gets data from IDataAccessor",e);
				} break;
			}
			case ProcessorConst.ACTION_UPDATE: {
				IDataAccessor access = DataAccessor.getInstance();
				try {
					IDept dept  = new Dept();
					dept.setID((BigInteger)req.getAttribute("id"));
					dept.setTitle((String)req.getAttribute("title"));
					dept.setDescription((String)req.getAttribute("desc"));
					access.updateDept(dept);
					try {
						resp.sendRedirect("showdepts.jsp");
					} catch (IOException e) {
						log.error("can't redirect on the showdepts.jsp",e);
					}
				} catch (DataAccessException e) {
					log.error("can't gets data from IDataAccessor",e);
				} break;
			}
			case ProcessorConst.ACTION_REMOVE: {
				IDataAccessor access = DataAccessor.getInstance();
				try {
					access.removeDept((BigInteger)req.getAttribute("id"));
					try {
						resp.sendRedirect("showdepts.jsp");
					} catch (IOException e) {
						log.error("can't redirect on the showdepts.jsp",e);
					}
				} catch (DataAccessException e) {
					log.error("can't gets data from IDataAccessor",e);
				}
				break;
			}			
		} 

	}

}
