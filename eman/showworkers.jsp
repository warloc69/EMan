<%@page import="ua.ivanchenko.eman.model.IOffice"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="ua.ivanchenko.eman.model.IWorker" %> 
<%@ page import="java.math.BigInteger" %> 
<%@ page import="java.util.*, org.apache.log4j.Logger" %>
    <script language="JavaScript" type="text/javascript">
		function chous(name,id) {
			window.opener.window.document.forms.f1.elements.man.value = name;
			window.opener.window.document.forms.f1.elements.mgr_id.value = id;
		}
	</script>
	<%if(request.getParameter("select") == null)  { %>
	<table border="0">
        <tr>
               <th><a href="index.jsp">Workers</a></th>
               <th><a href="index.jsp?action_id=view_job">Jobs</a></th>
               <th><a href="index.jsp?action_id=view_office">Offices</a></th>
               <th><a href="index.jsp?action_id=view_dept">Departments</a></th>
        </tr>
    </table>
    <%} %>
     <%
     log.info("show worker first");
      if( session.getAttribute("w_workers") == null ) {
    	  log.info("showworker.jsp redirect to action?action_id=view_worker");
          response.sendRedirect(request.getContextPath()+"/?action_id=view_top_manager&id="+request.getParameter("id"));
      }
         Collection<IWorker> workers = (Collection<IWorker>) session.getAttribute("w_workers");
         String id = null;
         Collection<IWorker> path = (Collection<IWorker>) session.getAttribute("path");
         log.info("path collection : "+ path);
         HashMap<BigInteger,String> depts = (HashMap<BigInteger,String>) session.getAttribute("w_departments");
         HashMap<BigInteger,String> off = (HashMap<BigInteger,String>) session.getAttribute("w_offices");
         HashMap<BigInteger,String> jobs = (HashMap<BigInteger,String>) session.getAttribute("w_jobs");
         HashMap<BigInteger,String> managers = (HashMap<BigInteger,String>) session.getAttribute("w_manager");
         
         if (request.getParameter("id") != null){
        	 %>
        	 <a href="index.jsp">Top</a>&nbsp;>&nbsp;
        	 <% 
         if(path != null) {
        	 if(path.size() != 0) {
        		 for (IWorker wor : path) {
        		 %>
        		 <a href="<%=request.getContextPath()%>/?action_id=view_top_manager&id=<%=wor.getID()%>"><%=wor.getLastName()%></a>&nbsp;>&nbsp;
        		 <% 
        		 }
        	 }
         }
         %>
         
         <%}
         if (workers != null && request.getParameter("tab") == null) {
      %>
               
       <table border="2">  
	        <tr>   
	        <%if (request.getParameter("id") != null){ %>
	           <th colspan="7"></th><td><a href="<%=request.getContextPath()%>/?action_id=view_top_manager&id=<%= request.getParameter("id") %>">subordinate</a></td>
	           <td><a href="<%=request.getContextPath()%>/?action_id=view_top_manager&tab=details&id=<%= request.getParameter("id") %>">details</a></td>
	        <%} %>
	        </tr>    
	        <tr>
	            <td></td><td><a href="<%=request.getContextPath()%>/?action_id=view_top_manager&sort=FIRSTNAME<%= request.getParameter("id") != null? "&id="+request.getParameter("id"):"" %>"><p1>First Name</p1></a></td>
	            <td><a href="<%=request.getContextPath()%>/?action_id=view_top_manager&sort=LASTNAME<%= request.getParameter("id") != null? "&id="+request.getParameter("id"):"" %>"><p1>Last Name</p1></a></td>
	            <td><a href="<%=request.getContextPath()%>/?action_id=view_top_manager&sort=MGRID<%= request.getParameter("id") != null? "&id="+request.getParameter("id"):"" %>"><p1>Manager</p1></a></td>
	            <td><a href="<%=request.getContextPath()%>/?action_id=view_top_manager&sort=JOB_ID<%= request.getParameter("id") != null? "&id="+request.getParameter("id"):"" %>"><p1>Job</p1></a></td>
	            <td><a href="<%=request.getContextPath()%>/?action_id=view_top_manager&sort=OFFICE_ID<%= request.getParameter("id") != null? "&id="+request.getParameter("id"):"" %>"><p1>Office</p1></a></td>
	            <td><a href="<%=request.getContextPath()%>/?action_id=view_top_manager&sort=DEPARTMENT_ID<%= request.getParameter("id") != null? "&id="+request.getParameter("id"):"" %>"><p1>Department</p1></a></td>
	            <td><a href="<%=request.getContextPath()%>/?action_id=view_top_manager&sort=SALEGRADE<%= request.getParameter("id") != null? "&id="+request.getParameter("id"):"" %>"><p1>Salegrade</p1></a></td>
	        </tr>
          <%
             log.info("showworker.jsp show all table {URI:"+request.getRequestURI()+"}");
          if(workers.size()==0) { %>
        	<tr><td><p>No subordinate.</p></td></tr>  
        <%  }
          int i = 0;
           %>
           <%  for(IWorker worker: workers)
                { log.info(worker.getLastName());
           %>
                    <tr><td>
                    <% if(request.getParameter("select") != null)  {%>
                    <input type="radio" name="worker" value="<%= worker.getID()%> " onclick="chous('<%= worker.getLastName()%>','<%= worker.getID()%>') ">
                    <%} %>
                    </td>
                        <td><a href="<%=request.getContextPath()%>/?action_id=view_top_manager&id=<%= worker.getID() %><%= request.getParameter("select") == null ? " " : "&select=true" %>"> <%= worker.getFirstName() %>      </a>      </td> 
                        <td><%= worker.getLastName() %>                </td>
                        <td><a href="<%=request.getContextPath()%>/?action_id=view_top_manager&id=<%= worker.getManagerID() %>"><%                        
                        if (managers.get(worker.getManagerID()) != null) {
                        %>
                        <%=managers.get(worker.getManagerID()) %>
                        <%} %>
                         </a></td> 
                        <td><a href="<%=request.getContextPath()%>/?action_id=view_job&id=<%= worker.getJobID() %>"><%= jobs.get(worker.getJobID()) %>    </a>     </td>
                        <td><a href="<%=request.getContextPath()%>/?action_id=view_office&id=<%= worker.getOfficeID() %>"><%= off.get(worker.getOfficeID()) %> </a>     </td>
                        <td><a href="<%=request.getContextPath()%>/?action_id=view_dept&id=<%= worker.getDepartmentID() %>"><%= depts.get(worker.getDepartmentID()) %></a>  </td>
                        <td><%= worker.getSalegrade() %></td>
                        <% if(request.getParameter("select") == null)  { %>
                        <td><a href="<%=request.getContextPath()%>/?action_id=edit_worker_update&id=<%= worker.getID() %>" ><img alt="Edit" src="<%=request.getContextPath()%>/resource/edit.png" border="0"></a></td>
                        <td><a href="<%=request.getContextPath()%>/?action_id=edit_worker_remove&id=<%= worker.getID() %> " ><img alt="Remove" src="<%=request.getContextPath()%>/resource/remove.png" border="0"></a></td>
                        <%}%>
                    </tr>
    <%
                }
             %>
             </table>
             <%
         } else if ("details".equals(request.getParameter("tab"))) {
        	 IWorker wor = (IWorker) session.getAttribute("wor");
    %>
         <table border="2">
  <tr>
    <td><p1>First Name</p1></td>
    <td><a href="<%=request.getContextPath()%>/?action_id=view_top_manager&id=<%= wor.getID() %>"><%= wor.getFirstName() %></a></td>
    <td>  
        <th colspan="7"></th><td><a href="<%=request.getContextPath()%>/?action_id=view_top_manager&id=<%= wor.getID() %>">subordinate</a></td>
        <td><a href="<%=request.getContextPath()%>/?action_id=view_top_manager&tab=details&id=<%= request.getParameter("id") %>">details</a></td>
    </td>
  </tr>
  <tr>
    <td><p1>Last Name</p1></td>
    <td><%=wor.getLastName() %></td>
  </tr>
  <tr>
    <td><p1>Manager</p1></td>
    <td><a href="<%=request.getContextPath()%>/?action_id=view_top_manager&id=<%= wor.getManagerID() %>" >
	    <% if (managers.get(wor.getManagerID()) != null) {%>
	          <%=managers.get(wor.getManagerID()) %>
	    <% } %></a>
    </td>
  </tr>
  <tr>
    <td><p1>Job</p1></td>
    <td><a href="<%=request.getContextPath()%>/?action_id=view_job&id=<%= wor.getJobID() %>"><%= jobs.get(wor.getJobID()) %></a></td>
  </tr>
  <tr>
    <td><p1>Office</p1></td>
    <td><a href="<%=request.getContextPath()%>/?action_id=view_office&id=<%= wor.getOfficeID() %>"><%= off.get(wor.getOfficeID()) %></a></td>
  </tr>
  <tr>
    <td><p1>Department</p1></td>
    <td><a href="<%=request.getContextPath()%>/?action_id=view_dept&id=<%= wor.getDepartmentID() %>"><%= depts.get(wor.getDepartmentID()) %></a></td>
  </tr>
  <tr>
    <td><p1>Salegrade</p1></td>
    <td><%= wor.getSalegrade() %></td>
  </tr>
</table>
    <%
       } else {
             IWorker worker = (IWorker) session.getAttribute("w_worker"); 
    %>        <table border="2">   
         <tr>   
               <th colspan="7"></th><td><a href="<%=request.getContextPath()%>/?action_id=view_top_manager&id=<%= worker.getID() %>">subordinate</a></td>
               <td><a href="<%=request.getContextPath()%>/?action_id=view_top_manager&tab=details&id=<%= worker.getID() %>">details</a></td>
            </tr> 
        <tr>
            <td><p1>First Name</p1></td><td><p1>Last Name</p1></td><td><p1>Manager</p1></td><td><p1>Job</p1></td><td><p1>Office</p1></td><td><p1>Department</p1></td><td><p1>Salegrade</p1></td>
        </tr>
	                  <tr>
                        <td><a href="<%=request.getContextPath()%>/?action_id=view_top_manager&id=<%= worker.getID() %>"> <%= worker.getFirstName() %>      </a>      </td> 
                        <td><%= worker.getLastName() %>                </td>
                        <td><a href="<%=request.getContextPath()%>/?action_id=view_top_manager&id=<%= worker.getManagerID() %>">
                        <%if (managers.get(worker.getManagerID()) != null) {%>
                        <%=managers.get(worker.getManagerID()) %>
                        <%} %>
                         </a></td>  
                        <td><%= jobs.get(worker.getJobID()) %>         </td>
                        <td><a href="<%=request.getContextPath()%>/?action_id=view_office&id=<%= worker.getOfficeID() %>"><%= off.get(worker.getOfficeID()) %> </a>     </td>
                        <td><%= depts.get(worker.getDepartmentID())%> </td>
                        <td><%= worker.getSalegrade() %>               </td>
                        <%if(request.getParameter("select") == null)  {%>
                        <td><a href="<%=request.getContextPath()%>/?action_id=edit_worker_update&id=<%= worker.getID() %> " ><img alt="Edit" src="<%=request.getContextPath()%>/resource/edit.png" border="0"></a></td>
                        <td><a href="<%=request.getContextPath()%>/?action_id=edit_worker_remove&id=<%= worker.getID() %> " ><img alt="Remove" src="<%=request.getContextPath()%>/resource/remove.png" border="0"></a></td>
                        <%}%>
                    </tr>
                    </table>
     <% 
         }
     %>
       <%if (request.getParameter("tab") == null) {
        if(request.getParameter("select") == null)  {%>
            <a href="<%=request.getContextPath()%>/?action_id=edit_worker_add&id=<%= request.getParameter("id") %>" ><img alt="Add" src="<%=request.getContextPath()%>/resource/add.png" border="0"></a>
        <%} else {%>
            <img alt="Close" src="<%=request.getContextPath()%>/resource/close.png" border="0" onclick="window.close()" />
        <%}}%>