<%@page import="ua.ivanchenko.eman.model.IOffice"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.math.BigInteger" %> 
<%@ page import="java.util.*" %>
<script language="JavaScript" type="text/javascript">
        function chous(name,id) {
            window.opener.window.document.forms.f1.elements.office.value = name;
            window.opener.window.document.forms.f1.elements.office_id.value = id;
        }
    </script>
    <table border="2">
        <tr>
            <th></th><th><a href="<%=request.getContextPath()%>/?action_id=view_office&sort=TITLE"><p1>Title</p1></a></th><th><a href="<%=request.getContextPath()%>/?action_id=view_office&sort=ADDRESS"><p1>Address</p1></a></th><th><a href="<%=request.getContextPath()%>/?action_id=view_office&sort=MANAGER"><p1>Manager</p1></a></th>
        </tr>
     <%
         Collection<IOffice> offices = (Collection<IOffice>) session.getAttribute("o_offices");
         HashMap<BigInteger,String> managers = (HashMap<BigInteger,String>) session.getAttribute("o_manager");
         if (offices != null) {
             for(IOffice office: offices)
                {
     %>
                    <tr><td>
                    <% if(request.getParameter("select") != null)  {%>
                        <input type="radio" name="office" value="<%= office.getID()%> " onclick="chous('<%= office.getTitle()%>','<%= office.getID()%>') ">
                    <% }%>
                        </td>
                        <td><%= office.getTitle() %> </td> 
                        <td><%= office.getAddress() %>         </td>
                        <%if (office.getManagerID() != null ) {%>
                        <td><a href="<%=request.getContextPath()%>/?action_id=view_worker&id=<%= office.getManagerID() %>"> 
                        <%= managers.get(office.getManagerID()) %>     </a>          </td> 
                        <%} else { %>
                        <td></td>
                        <%} %>
                        <%if(request.getParameter("select") == null)  { %>
	                        <td><a href="<%=request.getContextPath()%>/?action_id=edit_office_update&id=<%= office.getID() %> " ><img alt="Edit" src="<%=request.getContextPath()%>/resource/edit.png" border="0"></a></a></td>
	                        <td><a href="<%=request.getContextPath()%>/?action_id=edit_office_remove&id=<%= office.getID() %> "><img alt="Remove" src="<%=request.getContextPath()%>/resource/remove.png" border="0"></a> </td>                       
                        <%} %>
                    </tr>
    <%
                }
         } else {
             IOffice office = (IOffice) session.getAttribute("o_office"); 
    %>
                   <tr>
                        <td><%= office.getTitle() %>           </td> 
                        <td><%= office.getAddress() %>         </td>
                        <td><a href="<%=request.getContextPath()%>/?action_id=view_worker&id=<%= office.getManagerID() %>"> <%= managers.get(office.getManagerID()) %>     </a>          </td> 
                        <%if(request.getParameter("select") == null)  { %>
	                        <td><a href="<%=request.getContextPath()%>/?action_id=edit_office_edit&id=<%= office.getID() %> " ><img alt="Edit" src="<%=request.getContextPath()%>/resource/edit.png" border="0"></a></td>
	                        <td><a href="<%=request.getContextPath()%>/?action_id=edit_office_update&id=<%= office.getID() %> "><img alt="Remove" src="<%=request.getContextPath()%>/resource/remove.png" border="0"></a></td>
                        <%} %>
                    </tr>
     <% 
         }
     %>
    </table>
    <%if(request.getParameter("select") == null)  { %>
        <a href="<%=request.getContextPath()%>/?action_id=edit_office_add"><img alt="Add" src="<%=request.getContextPath()%>/resource/add.png" border="0"></a>
    <%}%>