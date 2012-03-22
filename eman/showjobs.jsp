<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="ua.ivanchenko.eman.model.IJob" %> 
<%@ page import="java.util.*" %>
    <script language="JavaScript" type="text/javascript">
        function chous(name,id) {
            window.opener.window.document.forms.f1.elements.job.value = name;
            window.opener.window.document.forms.f1.elements.job_id.value = id;
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
    <table border="2">
        <tr>
            <%if(request.getParameter("select") == null)  { %>
               <td></td><td><a href="<%=request.getContextPath()%>/action?action_id=view_job&sort=title"><p1>Title</p1></a></td><td><a href="<%=request.getContextPath()%>/?action_id=view_job&sort=description"><p1>Description</p1></a></td>
             <%} else { %>
                <td></td><td><a href="<%=request.getContextPath()%>/action?action_id=view_job&sort=title&select=true"><p1>Title</p1></a></td><td><a href="<%=request.getContextPath()%>/?action_id=view_job&sort=description&select=true"><p1>Description</p1></a></td>
             <%} %>
        </tr>
     <%
         Collection<IJob> jobs = (Collection<IJob>) session.getAttribute("j_jobs");
         if (jobs != null) {
             for(IJob job: jobs) {
     %>
                <tr>
                    <td>
                    <% if (request.getParameter("select") != null)  {%>
                         <input type="radio" name="job" value="<%= job.getID()%> " onclick="chous('<%= job.getTitle()%>','<%= job.getID()%>') ">
                    <% } %>
                    </td>
                    <td><%= job.getTitle() %> </td>
                    <% if (job.getDescription() != null)  {%>
                        <td><%= job.getDescription() %></td> 
                    <%} else { %>
                           <td> </td>
                    <%} %>
                    <%if (request.getParameter("select") == null)  { %>
	                     <td><a href="<%=request.getContextPath()%>/action?action_id=edit_job_update&id=<%= job.getID() %> "><img alt="Edit" src="<%=request.getContextPath()%>/resource/edit.png" border="0"></a></td>
	                     <td><a href="<%=request.getContextPath()%>/action?action_id=edit_job_remove&id=<%= job.getID() %> "><img alt="Remove" src="<%=request.getContextPath()%>/resource/remove.png" border="0"></a></td>
                     <%} %>
                </tr>
    <%
            }
        } else {
           IJob job = (IJob) session.getAttribute("j_job"); 
    %>
           <tr>
              <td><%= job.getTitle() %> </td> 
              <td><%= job.getDescription() %></td> 
              <%if (request.getParameter("select") == null)  { %>
	               <td><a href="<%=request.getContextPath()%>/action?action_id=edit_job_update&id=<%= job.getID() %> "><img alt="Edit" src="<%=request.getContextPath()%>/resource/edit.png" border="0"></a></td>
	               <td><a href="<%=request.getContextPath()%>/action?action_id=edit_job_remove&id=<%= job.getID() %> "><img alt="Remove" src="<%=request.getContextPath()%>/resource/remove.png" border="0"></a></td>
              <%} %>   
           </tr>
     <% 
        } 
     %>
    </table>
    <%if(request.getParameter("select") == null)  { %>
        <a href="<%=request.getContextPath()%>/action?action_id=edit_job_add"><img alt="Add" src="<%=request.getContextPath()%>/resource/add.png" border="0"></a>
     <%} else { %>
        <img alt="Close" src="<%=request.getContextPath()%>/resource/close.png" border="0" onclick="window.close()" />
     <%} %>