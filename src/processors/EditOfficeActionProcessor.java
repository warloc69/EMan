package processors;

import java.io.IOException;
import java.math.BigInteger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.DataAccessor;
import model.IDataAccessor;
import model.IOffice;
import model.Office;
import exceptions.DataAccessException;


public class EditOfficeActionProcessor implements ActionProcessor {
	private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(EditOfficeActionProcessor.class);
	@Override
	public void process(HttpServletRequest req, HttpServletResponse resp) {
		Integer action_id = (Integer) req.getAttribute("action_id");
		switch (action_id) {
			case ProcessorConst.ACTION_ADD: {
				IDataAccessor access = DataAccessor.getInstance();
				try {
					IOffice office  = new Office();
					office.setTitle((String)req.getAttribute("title"));
					office.setAddress((String)req.getAttribute("adr"));
					office.setManagerID((BigInteger)req.getAttribute("mgr_id"));
					access.addOffice(office);
					try {
						resp.sendRedirect("showoffices.jsp");
					} catch (IOException e) {
						log.error("can't redirect on the showoffices.jsp",e);
					}
				} catch (DataAccessException e) {
					log.error("can't gets data from IDataAccessor",e);
				} break;
			}
			case ProcessorConst.ACTION_UPDATE: {
				IDataAccessor access = DataAccessor.getInstance();
				try {
					IOffice office  = new Office();
					office.setTitle((String)req.getAttribute("title"));
					office.setAddress((String)req.getAttribute("adr"));
					office.setManagerID((BigInteger)req.getAttribute("mgr_id"));
					office.setID((BigInteger)req.getAttribute("id"));
					access.updateOffice(office);
					try {
						resp.sendRedirect("showoffices.jsp");
					} catch (IOException e) {
						log.error("can't redirect on the showoffice.jsp",e);
					}
				} catch (DataAccessException e) {
					log.error("can't gets data from IDataAccessor",e);
				} break;
			}
			case ProcessorConst.ACTION_REMOVE: {
				IDataAccessor access = DataAccessor.getInstance();
				try {
					access.removeOffice((BigInteger)req.getAttribute("id"));
					try {
						resp.sendRedirect("showoffices.jsp");
					} catch (IOException e) {
						log.error("can't redirect on the showoffices.jsp",e);
					}
				} catch (DataAccessException e) {
					log.error("can't gets data from IDataAccessor",e);
				}
				break;
			}			
		} 

	}

}
