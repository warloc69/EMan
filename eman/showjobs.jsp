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
    <table border="2">
        <tr>
            <td></td><td><p1>Title</p1></td><td><p1>Description</p1></td>
        </tr>
     <%
         Collection<IJob> jobs = (Collection<IJob>) session.getAttribute("j_jobs");
         if (jobs != null) {
             for(IJob job: jobs)
                {
     %>
                    <tr><td>
                    <% if(request.getParameter("select") != null)  {%>
                        <input type="radio" name="job" value="<%= job.getID()%> " onclick="chous('<%= job.getTitle()%>','<%= job.getID()%>') ">
                    <% } %>
                    </td>
                        <td><%= job.getTitle() %> </td> 
                        <td><%= job.getDescription() %></td> 
                        <%log.info(request.getContextPath()); %>
                        <td><a href="<%=request.getContextPath()%>/?action_id=edit_job_update&id=<%= job.getID() %> ">Edit</a></td>
                        <td><a href="<%=request.getContextPath()%>/?action_id=edit_job_remove&id=<%= job.getID() %> ">Remove</a></td>
                    </tr>
    <%
                }
         } else {
             IJob job = (IJob) session.getAttribute("j_job"); 
    %>
                   <tr>
                        <td><%= job.getTitle() %> </td> 
                        <td><%= job.getDescription() %></td> 
                        <td><a href="<%=request.getContextPath()%>/?action_id=edit_job_update&id=<%= job.getID() %> ">Edit</a></td>
                        <td><a href="<%=request.getContextPath()%>/?action_id=edit_job_remove&id=<%= job.getID() %> ">Remove</a></td>
                    </tr>
     <% 
             } 
     %>
    </table>
    <a href="<%=request.getContextPath()%>/?action_id=edit_job_add">add</a>