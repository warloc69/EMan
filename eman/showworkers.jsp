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
     <%
      if( session.getAttribute("w_workers") == null && session.getAttribute("w_worker") == null) {
    	  log.info("showworker.jsp redirect to action?action_id=view_worker");
          response.sendRedirect(request.getContextPath()+"/?action_id=view_worker");
      }
         Collection<IWorker> workers = (Collection<IWorker>) session.getAttribute("w_workers");
         HashMap<BigInteger,String> depts = (HashMap<BigInteger,String>) session.getAttribute("w_departments");
         HashMap<BigInteger,String> off = (HashMap<BigInteger,String>) session.getAttribute("w_offices");
         HashMap<BigInteger,String> jobs = (HashMap<BigInteger,String>) session.getAttribute("w_jobs");
         HashMap<BigInteger,String> managers = (HashMap<BigInteger,String>) session.getAttribute("w_manager");
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
	            <td></td><td><p1>First Name</p1></td><td><p1>Last Name</p1></td><td><p1>Manager</p1></td><td><p1>Job</p1></td><td><p1>Office</p1></td><td><p1>Department</p1></td><td><p1>Salegrade</p1></td>
	        </tr>
          <%
             log.info("showworker.jsp show all table {URI:"+request.getRequestURI()+"}");
          if(workers.size()==0) { %>
        	<tr><td><p>No subordinate.</p></td></tr>  
        <%  }
          int i = 0;
           if(request.getParameter("select") != null)  { %>
        	 <tr>
        	 <td><input type="radio" name="worker" value="null" onclick="chous('Top manager','null') "></td><td>Top</td><td>Manager</td><td></td><td></td><td></td><td></td><td></td>
        	 </tr>
        	 <%} %>
           <%  for(IWorker worker: workers)
                {
           %>
                    <tr><td>
                    <% if(request.getParameter("select") != null)  {%>
                    <input type="radio" name="worker" value="<%= worker.getID()%> " onclick="chous('<%= worker.getLastName()%>','<%= worker.getID()%>') ">
                    <%} %>
                    </td>
                        <td><a href="<%=request.getContextPath()%>/?action_id=view_top_manager&id=<%= worker.getID() %><%= request.getParameter("select") == null ? " " : "&select=true" %>"> <%= worker.getFirstName() %>      </a>      </td> 
                        <td><%= worker.getLastName() %>                </td>
                        <td><a href="<%=request.getContextPath()%>/?action_id=view_worker&id=<%= worker.getManagerID() %>"><%                        
                        if (managers.get(worker.getManagerID()) != null) {
                        %>
                        <%=managers.get(worker.getManagerID()) %>
                        <%} %>
                         </a></td> 
                        <td><a href="<%=request.getContextPath()%>/?action_id=view_job&id=<%= worker.getJobID() %>"><%= jobs.get(worker.getJobID()) %>    </a>     </td>
                        <td><a href="<%=request.getContextPath()%>/?action_id=view_office&id=<%= worker.getOfficeID() %>"><%= off.get(worker.getOfficeID()) %> </a>     </td>
                        <td><a href="<%=request.getContextPath()%>/?action_id=view_dept&id=<%= worker.getDepartmentID() %>"><%= depts.get(worker.getDepartmentID()) %></a>  </td>
                        <td><%= worker.getSalegrade() %>               </td>
                        <td><a href="<%=request.getContextPath()%>/?action_id=edit_worker_update&id=<%= worker.getID() %>" >Edit</a></td>
                        <td><a href="<%=request.getContextPath()%>/?action_id=edit_worker_remove&id=<%= worker.getID() %> " >Remove</a></td>
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
                        <td><a href="<%=request.getContextPath()%>/?action_id=view_worker&id=<%= worker.getManagerID() %>">
                        <%if (managers.get(worker.getManagerID()) != null) {%>
                        <%=managers.get(worker.getManagerID()) %>
                        <%} %>
                         </a></td>  
                        <td><%= jobs.get(worker.getJobID()) %>         </td>
                        <td><a href="<%=request.getContextPath()%>/?action_id=view_office&id=<%= worker.getOfficeID() %>"><%= off.get(worker.getOfficeID()) %> </a>     </td>
                        <td><%= depts.get(worker.getDepartmentID()) %> </td>
                        <td><%= worker.getSalegrade() %>               </td>
                        <td><a href="<%=request.getContextPath()%>/?action_id=edit_worker_update&id=<%= worker.getID() %> " >Edit</a></td>
                        <td><a href="<%=request.getContextPath()%>/?action_id=edit_worker_remove&id=<%= worker.getID() %> " >Remove</a></td>
                    </tr>
                    </table>
     <% 
         }
     %>
    <a href="<%=request.getContextPath()%>/?action_id=edit_worker_add">add</a>