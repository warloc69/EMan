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
		function clear() {
		     window.document.forms.f3.elements.fname.value = "";
		     window.document.forms.f3.elements.lname.value = "";
		     window.document.forms.f3.elements.job.value = "";
		     window.document.forms.f3.elements.office.value = "";
		     window.document.forms.f3.elements.dept.value = "";
		 }
	</script>
	<%if (request.getParameter("select") == null) { %>
		<table border="0">
	        <tr>
	            <th><a href="index.jsp">Workers</a></th>
	            <th><a href="index.jsp?action_id=view_job">Jobs</a></th>
	            <th><a href="index.jsp?action_id=view_office">Offices</a></th>
	            <th><a href="index.jsp?action_id=view_dept">Departments</a></th>
	        </tr>
	    </table>
    <%}%>
     <%
      if( session.getAttribute("w_workers") == null ) {
          response.sendRedirect(request.getContextPath()+"/action?action_id=view_top_manager&id="+request.getParameter("id"));
      }
         Collection<IWorker> workers = (Collection<IWorker>) session.getAttribute("w_workers");
         String id = null;
         Collection<IWorker> path = (Collection<IWorker>) session.getAttribute("path");
         HashMap<BigInteger,String> depts = (HashMap<BigInteger,String>) session.getAttribute("w_departments");
         HashMap<BigInteger,String> off = (HashMap<BigInteger,String>) session.getAttribute("w_offices");
         HashMap<BigInteger,String> jobs = (HashMap<BigInteger,String>) session.getAttribute("w_jobs");
         HashMap<BigInteger,String> managers = (HashMap<BigInteger,String>) session.getAttribute("w_manager");
         if (request.getParameter("id") != null) {
        	 if (request.getParameter("select") == null ) {%>
	        	 <a href="<%=request.getContextPath()%>/action?action_id=view_top_manager"> Top</a>
	       <%} else {%>
                 <a href="<%=request.getContextPath()%>/action?action_id=view_top_manager&select=true"> Top</a>
           <%}
	         if(path != null && !"search".equalsIgnoreCase(request.getParameterValues("action_id")[request.getParameterValues("action_id").length-1])) {
	        	 if(path.size() != 0) {
	        		 for (IWorker wor : path) {
	        			 %> 
	        			 &nbsp;>&nbsp;
	        			 <% 
	        			 if(request.getParameter("select") == null) {%>
	        		         <a href="<%=request.getContextPath()%>/action?action_id=view_top_manager&id=<%=wor.getID()%>"><%=wor.getLastName()%></a>
	        		   <%} else {%>
	        				 <a href="<%=request.getContextPath()%>/action?action_id=view_top_manager&select=true&id=<%=wor.getID()%>"><%=wor.getLastName()%></a>
	        		   <%}
	        		 }
	        	 }
	         }%>
        <%}
         if (workers != null && request.getParameter("tab") == null) {%>
       <table border="2">  
	        <tr>   
		        <%if (request.getParameter("id") != null && request.getParameter("select") == null) {%>
		           <th colspan="7"></th><td><a href="<%=request.getContextPath()%>/action?action_id=view_top_manager&id=<%= request.getParameter("id") %>">subordinate</a></td>
		           <td><a href="<%=request.getContextPath()%>/action?action_id=view_top_manager&tab=details&id=<%= request.getParameter("id") %>">details</a></td>
		        <%}%>
	        </tr>    
	        <tr>
	           <% if(request.getParameter("select") == null)  {%>
		            <td></td><td><a href="<%=request.getContextPath()%>/action?action_id=view_top_manager&sort=FIRSTNAME<%= request.getParameter("id") != null? "&id="+request.getParameter("id"):"" %>"><p1>First Name</p1></a></td>
		            <td><a href="<%=request.getContextPath()%>/action?action_id=view_top_manager&sort=LASTNAME<%= request.getParameter("id") != null? "&id="+request.getParameter("id"):"" %>"><p1>Last Name</p1></a></td>
		            <td><a href="<%=request.getContextPath()%>/action?action_id=view_top_manager&sort=MGRID<%= request.getParameter("id") != null? "&id="+request.getParameter("id"):"" %>"><p1>Manager</p1></a></td>
		            <td><a href="<%=request.getContextPath()%>/action?action_id=view_top_manager&sort=JOB_ID<%= request.getParameter("id") != null? "&id="+request.getParameter("id"):"" %>"><p1>Job</p1></a></td>
		            <td><a href="<%=request.getContextPath()%>/action?action_id=view_top_manager&sort=OFFICE_ID<%= request.getParameter("id") != null? "&id="+request.getParameter("id"):"" %>"><p1>Office</p1></a></td>
		            <td><a href="<%=request.getContextPath()%>/action?action_id=view_top_manager&sort=DEPARTMENT_ID<%= request.getParameter("id") != null? "&id="+request.getParameter("id"):"" %>"><p1>Department</p1></a></td>
		            <td><a href="<%=request.getContextPath()%>/action?action_id=view_top_manager&sort=SALEGRADE<%= request.getParameter("id") != null? "&id="+request.getParameter("id"):"" %>"><p1>Salegrade</p1></a></td>
	           <% } else { %>
		           <td></td><td><a href="<%=request.getContextPath()%>/action?action_id=view_top_manager&select=true&sort=FIRSTNAME<%= request.getParameter("id") != null ? "&id="+request.getParameter("id"):"" %>"><p1>First Name</p1></a></td>
	                <td><a href="<%=request.getContextPath()%>/action?action_id=view_top_manager&select=true&sort=LASTNAME<%= request.getParameter("id") != null? "&id="+request.getParameter("id"):"" %>"><p1>Last Name</p1></a></td>
	                <td><a href="<%=request.getContextPath()%>/action?action_id=view_top_manager&select=true&sort=MGRID<%= request.getParameter("id") != null? "&id="+request.getParameter("id"):"" %>"><p1>Manager</p1></a></td>
	                <td><a href="<%=request.getContextPath()%>/action?action_id=view_top_manager&select=true&sort=JOB_ID<%= request.getParameter("id") != null? "&id="+request.getParameter("id"):"" %>"><p1>Job</p1></a></td>
	                <td><a href="<%=request.getContextPath()%>/action?action_id=view_top_manager&select=true&sort=OFFICE_ID<%= request.getParameter("id") != null? "&id="+request.getParameter("id"):"" %>"><p1>Office</p1></a></td>
	                <td><a href="<%=request.getContextPath()%>/action?action_id=view_top_manager&select=true&sort=DEPARTMENT_ID<%= request.getParameter("id") != null? "&id="+request.getParameter("id"):"" %>"><p1>Department</p1></a></td>
	                <td><a href="<%=request.getContextPath()%>/action?action_id=view_top_manager&select=true&sort=SALEGRADE<%= request.getParameter("id") != null? "&id="+request.getParameter("id"):"" %>"><p1>Salegrade</p1></a></td>
	            <%}%>
	        </tr>
		        <form name="f3" action="<%=request.getContextPath()%>/action" method="get"  >
		        <tr>
		          <td> </td>
		          <%  if (request.getParameter("fname") != null && !"null".equalsIgnoreCase(request.getParameter("fname"))) { %>
		              <td><input type="text" name="fname" value="<%=request.getParameter("fname") %>" /></td>
		          <%} else {%>
		              <td><input type="text" name="fname" value="" /></td>
		          <%} %>
		          <%  if (request.getParameter("lname") != null && !"null".equalsIgnoreCase(request.getParameter("lname"))) { %>
		           <td><input type="text" name="lname" value="<%=request.getParameter("lname") %>" /></td>
                  <%} else {%>
                      <td><input type="text" name="lname" value="" /></td>
                  <%} %>
		          <td></td>
		          <%  if (request.getParameter("job") != null && !"null".equalsIgnoreCase(request.getParameter("job"))) { %>
                   <td><input type="text" name="job" value="<%=request.getParameter("job") %>" /></td>
                  <%} else {%>
                      <td><input type="text" name="job" value="" /></td>
                  <%} %>
                  <%  if (request.getParameter("office") != null && !"null".equalsIgnoreCase(request.getParameter("office"))) { %>
                   <td><input type="text" name="office" value="<%=request.getParameter("office") %>" /></td>
                  <%} else {%>
                      <td><input type="text" name="office" value="" /></td>
                  <%} %>
                   <%  if (request.getParameter("dept") != null && !"null".equalsIgnoreCase(request.getParameter("dept"))) { %>
                   <td><input type="text" name="dept" value="<%=request.getParameter("dept") %>" /></td>
                  <%} else {%>
                      <td><input type="text" name="dept" value="" /></td>
                  <%} %>
		          <td></td>
		          <% if (request.getParameter("select") != null) {%>
		              <input type="hidden" name="select" value="true" />
		          <%} %>    
		          <%if (request.getParameter("id") != null) {%>		        
		              <input type="hidden" name="id" value="<%=request.getParameter("id") %>" />
		          <%} %>
		          <input type="hidden" name="action_id" value="filter" /> 
		          <td><input type="submit" value="filtering" /></td>
		          <%if (request.getParameter("id") != null) {
		        	  try { 
                          new BigInteger(request.getParameter("id"));  
			              if (request.getParameter("select") == null) { %>               
	                         <td><a href="<%=request.getContextPath()%>/action?action_id=view_top_manager&id=<%=request.getParameter("id")%>" onclick="clear()">&nbsp; Clear &nbsp;</a></td>   
	                      <%} else { %>  
	                        <td><a href="<%=request.getContextPath()%>/action?action_id=view_top_manager&select=true&id=<%=request.getParameter("id")%>" onclick="clear()">&nbsp; Clear &nbsp;</a></td>   
	                      <%} 
		        	  } catch (NumberFormatException e) {
		        		  %>
		        		  <%if (request.getParameter("select") == null) {%>
                            <td><a href="<%=request.getContextPath()%>/action?action_id=view_top_manager" onclick="clear()">&nbsp; Clear &nbsp;</a></td>
                       <%} else { %>
                            <td><a href="<%=request.getContextPath()%>/action?action_id=view_top_manager&select=true" onclick="clear()">&nbsp; Clear &nbsp;</a></td>
                       <%} %> 
		        		  <%
		        	  }
                      %>
                  <%}  else  {%>
                        <%if (request.getParameter("select") == null) {%>
                            <td><a href="<%=request.getContextPath()%>/action?action_id=view_top_manager" onclick="clear()">&nbsp; Clear &nbsp;</a></td>
                       <%} else { %>
                            <td><a href="<%=request.getContextPath()%>/action?action_id=view_top_manager&select=true" onclick="clear()">&nbsp; Clear &nbsp;</a></td>
                       <%} %> 
                  <%} %>	
		                
	        </tr>    
	        </form> 
          <%
          if(workers.size()==0) {%>
        	<tr><td><p>No subordinate.</p></td></tr>  
         <%}
          int i = 0;%>
        <%for(IWorker worker: workers)
           {
         %>
             <tr>
                <td>
                  <%if (request.getParameter("select") != null)  {%>
                       <input type="radio" name="worker" value="<%= worker.getID()%> " onclick="chous('<%= worker.getLastName()%>','<%= worker.getID()%>') ">
                   <%}%>
               </td>
               <td><a href="<%=request.getContextPath()%>/action?action_id=view_top_manager&id=<%= worker.getID() %><%= request.getParameter("select") == null ? " " : "&select=true" %>"> <%= worker.getFirstName() %>      </a>      </td> 
               <td><%= worker.getLastName() %></td>
               <%if (request.getParameter("select") == null) {%>
		           <td>
			          <a href="<%=request.getContextPath()%>/action?action_id=view_top_manager&id=<%= worker.getManagerID() %>"><%                        
			          if (managers.get(worker.getManagerID()) != null) {%>
			             <%=managers.get(worker.getManagerID()) %>
			         <%}%></a>
		           </td> 
		           <td><a href="<%=request.getContextPath()%>/action?action_id=view_job&id=<%= worker.getJobID() %>"><%= jobs.get(worker.getJobID()) %>    </a>     </td>
		           <td><a href="<%=request.getContextPath()%>/action?action_id=view_office&id=<%= worker.getOfficeID() %>"><%= off.get(worker.getOfficeID()) %> </a>     </td>
		           <td><a href="<%=request.getContextPath()%>/action?action_id=view_dept&id=<%= worker.getDepartmentID() %>"><%= depts.get(worker.getDepartmentID()) %></a>  </td>
		           <td><%= worker.getSalegrade() %></td>
		           <% if(request.getParameter("select") == null)  { %>
			            <td><a href="<%=request.getContextPath()%>/action?action_id=edit_worker_update&id=<%= worker.getID() %>" ><img alt="Edit" src="<%=request.getContextPath()%>/resource/edit.png" border="0"></a></td>
			            <td><a href="<%=request.getContextPath()%>/action?action_id=edit_worker_remove&id=<%= worker.getID() %> " ><img alt="Remove" src="<%=request.getContextPath()%>/resource/remove.png" border="0"></a></td>
		           <%}%>
               <%} else { %>
                   <td><%if (managers.get(worker.getManagerID()) != null) {%>
                          <%=managers.get(worker.getManagerID()) %>
                       <%}%>
                   </td> 
                   <td><%= jobs.get(worker.getJobID()) %> </td>
                   <td><%= off.get(worker.getOfficeID()) %>    </td>
                   <td><%= depts.get(worker.getDepartmentID()) %> </td>
                   <td><%= worker.getSalegrade() %></td>
               <%} %>
             </tr>
         <%}%>
      </table>
   <%} else if ("details".equalsIgnoreCase(request.getParameter("tab"))) {
        IWorker wor = (IWorker) session.getAttribute("wor");%>
         <table border="2">
			  <tr>
			    <td><p1>First Name</p1></td>
			    <td><a href="<%=request.getContextPath()%>/action?action_id=view_top_manager&id=<%= wor.getID() %>"><%= wor.getFirstName() %></a></td>
			    <td>  
			        <th colspan="7"></th><td><a href="<%=request.getContextPath()%>/?action_id=view_top_manager&id=<%= wor.getID() %>">subordinate</a></td>
			    <td>
			        <a href="<%=request.getContextPath()%>/action?action_id=view_top_manager&tab=details&id=<%= request.getParameter("id") %>">details</a></td>
			    </td>
			  </tr>
			  <tr>
			    <td><p1>Last Name</p1></td>
			    <td><%=wor.getLastName() %></td>
			  </tr>
			  <tr>
			    <td><p1>Manager</p1></td>
			    <td><a href="<%=request.getContextPath()%>/action?action_id=view_top_manager&id=<%= wor.getManagerID() %>" >
				    <% if (managers.get(wor.getManagerID()) != null) {%>
				          <%=managers.get(wor.getManagerID()) %>
				    <% } %></a>
			    </td>
			  </tr>
			  <tr>
			    <td><p1>Job</p1></td>
			    <td><a href="<%=request.getContextPath()%>/action?action_id=view_job&id=<%= wor.getJobID() %>"><%= jobs.get(wor.getJobID()) %></a></td>
			  </tr>
			  <tr>
			    <td><p1>Office</p1></td>
			    <td><a href="<%=request.getContextPath()%>/action?action_id=view_office&id=<%= wor.getOfficeID() %>"><%= off.get(wor.getOfficeID()) %></a></td>
			  </tr>
			  <tr>
			    <td><p1>Department</p1></td>
			    <td><a href="<%=request.getContextPath()%>/action?action_id=view_dept&id=<%= wor.getDepartmentID() %>"><%= depts.get(wor.getDepartmentID()) %></a></td>
			  </tr>
			  <tr>
			    <td><p1>Salegrade</p1></td>
			    <td><%= wor.getSalegrade() %></td>
			  </tr>
        </table>
        <a href="<%=request.getContextPath()%>/action?action_id=edit_worker_update&id=<%= wor.getID() %>" ><img alt="Edit" src="<%=request.getContextPath()%>/resource/edit.png" border="0"></a>
    <%
       } else {
             IWorker worker = (IWorker) session.getAttribute("w_worker"); 
    %>        <table border="2">   
                 <tr>   
                     <th colspan="7"></th><td><a href="<%=request.getContextPath()%>/action?action_id=view_top_manager&id=
                     <%= worker.getID() %>">subordinate</a></td>
                     <td><a href="<%=request.getContextPath()%>/action?action_id=view_top_manager&tab=details&id=<%= worker.getID() %>">details</a></td>
                 </tr> 
			     <tr>
			         <td><p1>First Name</p1></td><td><p1>Last Name</p1></td><td><p1>Manager</p1></td><td><p1>Job</p1></td><td><p1>Office</p1></td><td><p1>Department</p1></td><td><p1>Salegrade</p1></td>
			     </tr>
		         <tr>
	                <td><a href="<%=request.getContextPath()%>/action?action_id=view_top_manager&id=<%= worker.getID() %>"> <%= worker.getFirstName() %>      </a>      </td> 
	                <td><%= worker.getLastName() %>                </td>
	                <td><a href="<%=request.getContextPath()%>/action?action_id=view_top_manager&id=<%= worker.getManagerID() %>">
	                   <%if (managers.get(worker.getManagerID()) != null) {%>
	                       <%=managers.get(worker.getManagerID()) %>
	                   <%} %>
	                   </a>
	                </td>  
	                <td><%= jobs.get(worker.getJobID()) %>         </td>
	                <td><a href="<%=request.getContextPath()%>/action?action_id=view_office&id=<%= worker.getOfficeID() %>"><%= off.get(worker.getOfficeID()) %> </a>     </td>
	                <td><%= depts.get(worker.getDepartmentID())%> </td>
	                <td><%= worker.getSalegrade() %>               </td>
	                   <%if(request.getParameter("select") == null)  {%>
		                   <td><a href="<%=request.getContextPath()%>/action?action_id=edit_worker_update&id=<%= worker.getID() %> " ><img alt="Edit" src="<%=request.getContextPath()%>/resource/edit.png" border="0"></a></td>
		                   <td><a href="<%=request.getContextPath()%>/action?action_id=edit_worker_remove&id=<%= worker.getID() %> " ><img alt="Remove" src="<%=request.getContextPath()%>/resource/remove.png" border="0"></a></td>
	                   <%}%>
	             </tr>
            </table>
     <% 
         }
     %>
       <%if (request.getParameter("tab") == null) {
	        if(request.getParameter("select") == null)  {%>
	            <form name="f2" action="<%=request.getContextPath()%>/action" method="get">
	                <input type="hidden" name="action_id" value="search"/>
	                <input type="text" name="id"/>
	                <input type="submit" value="Search"/>
	             </form>
	             <%if (request.getParameter("id") != null) {%>
	                   <a href="<%=request.getContextPath()%>/action?action_id=edit_worker_add&id=<%= request.getParameter("id") %>" ><img alt="Add" src="<%=request.getContextPath()%>/resource/add.png" border="0"></a>
	               <%} else { %>
	                <a href="<%=request.getContextPath()%>/action?action_id=edit_worker_add" ><img alt="Add" src="<%=request.getContextPath()%>/resource/add.png" border="0"></a>
	               <%} %>
	        <%} else {%>
	            <form name="f2" action="<%=request.getContextPath()%>/action" method="get">
	                <input type="hidden" name="action_id" value="search"/>
	                <input type="hidden" name="select" value="true"/>
	                <input type="text" name="id"/>
	                <input type="submit" value="Search"/>
	            </form>
	            <img alt="Close" src="<%=request.getContextPath()%>/resource/close.png" border="0" onclick="window.close()" />
	        <%}
        }%>