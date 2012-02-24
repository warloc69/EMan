<%@page import="ua.ivanchenko.eman.model.IOffice"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="ua.ivanchenko.eman.model.IDept" %> 
<%@ page import="java.util.*" %>
<script language="JavaScript" type="text/javascript">
        function chous(name,id) {
            window.opener.window.document.forms.f1.elements.dept.value = name;
            window.opener.window.document.forms.f1.elements.dept_id.value = id;
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
            <td colspan="1"></td><td><a href="<%=request.getContextPath()%>/?action_id=view_dept&sort=title"><p1>Title</p1></a></td><td><a href="<%=request.getContextPath()%>/?action_id=view_dept&sort=description"><p1>Description</p1></a></td>
        </tr>
     <%
         Collection<IDept> depts = (Collection<IDept>) session.getAttribute("d_depts");
         if (depts != null) {
             for(IDept dept: depts)
                {
     %>
                    <tr><td>
                    <% if(request.getParameter("select") != null)  {%>
                        <input type="radio" name="dept" value="<%= dept.getID()%> " onclick="chous('<%= dept.getTitle()%>','<%= dept.getID()%>') ">
                     <%} %>   
                        </td>
                        <td><%= dept.getTitle() %> </td> 
                        <td><%= dept.getDescription() %></td>
                        <%if(request.getParameter("select") == null)  { %>
	                        <td><a href="<%=request.getContextPath()%>/?action_id=edit_dept_update&id=<%= dept.getID() %> " ><img alt="Edit" src="<%=request.getContextPath()%>/resource/edit.png" border="0"></a></td>
	                        <td><a href="<%=request.getContextPath()%>/?action_id=edit_dept_remove&id=<%= dept.getID() %> " ><img alt="Remove" src="<%=request.getContextPath()%>/resource/remove.png" border="0"></a></td>
                        <%} %>   
                    </tr>
    <%
                }
         } else {
             IDept dept = (IDept) session.getAttribute("d_dept"); 
    %>
                   <tr>
                        <td><%= dept.getTitle() %> </td> 
                        <td><%= dept.getDescription() %></td> 
                        <%if(request.getParameter("select") == null)  { %>
	                        <td><a href="<%=request.getContextPath()%>/?action_id=edit_dept_update&id=<%= dept.getID() %> " ><img alt="Edit" src="<%=request.getContextPath()%>/resource/edit.png" border="0"></a></td>
	                        <td><a href="<%=request.getContextPath()%>/?action_id=edit_dept_remove&id=<%= dept.getID() %> " ><img alt="Remove" src="<%=request.getContextPath()%>/resource/remove.png" border="0"></a></td>
                        <%} %>
                    </tr>
     <% 
         }
     %>
    </table>
    <%if(request.getParameter("select") == null)  { %>
    <a href="<%=request.getContextPath()%>/?action_id=edit_dept_add" ><img alt="Add" src="<%=request.getContextPath()%>/resource/add.png" border="0"></a>
    <%} else { %>
            <img alt="Close" src="<%=request.getContextPath()%>/resource/close.png" border="0" onclick="window.close()" />
        <%} %>