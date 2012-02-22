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
    <table border="2">
        <tr>
            <td></td><td><p1>Title</p1></td><td><p1>Description</p1></td>
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
                        <td><a href="<%=request.getContextPath()%>/?action_id=edit_dept_update&id=<%= dept.getID() %> " >Edit</a></td>
                        <td><a href="<%=request.getContextPath()%>/?action_id=edit_dept_remove&id=<%= dept.getID() %> " >Remove</a></td>
                    </tr>
    <%
                }
         } else {
             IDept dept = (IDept) session.getAttribute("d_dept"); 
    %>
                   <tr>
                        <td><%= dept.getTitle() %> </td> 
                        <td><%= dept.getDescription() %></td> 
                        <td><a href="<%=request.getContextPath()%>/?action_id=edit_dept_update&id=<%= dept.getID() %> " >Edit</a></td>
                        <td><a href="<%=request.getContextPath()%>/?action_id=edit_dept_remove&id=<%= dept.getID() %> " >Remove</a></td>
                    </tr>
     <% 
         }
     %>
    </table>
    <a href="<%=request.getContextPath()%>/?action_id=edit_dept_add" >Add</a>
   