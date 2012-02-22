<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="org.apache.log4j.Logger" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Insert title here</title>
    <style type="text/css">
     body {
        background-color: #888888;
    }
    table {  
        background: #888888;
        border: 2px;
    }
    p1 {
         color: #880000;
    }
    p {
        color: white;
    }
    td, th {
	  border: 2px;
	  padding: .8em;
	  color: #000000;
    }
    a {
        color: #00FF0C;
    }
    thead th, tfoot th {
	  font: bold 10px helvetica, verdana, arial, sans-serif;
	  border: 2px;
	  text-align: left;
	  background: #888888;
	  color: #00FF0C;
	  padding-top:4px;
    }
    tbody td a {
	  background: transparent;
	  text-decoration: none;
	  color: #000000;
    }
    tbody td a:hover {
	  background: transparent;
	  color: #00FF0C;
    }
    tbody th a {
        border: 2px;
	  font: bold 11px helvetica, verdana, arial, sans-serif;
	  background: transparent;
	  text-decoration: none;
	  font-weight:normal;
	  color: #000000;
    }  
  
    tbody td+td+td+td a {
	    padding-right: 14px;
	    
    }
    tbody td+td+td+td a:hover {
        padding-right: 14px;
    }
    tbody th a:hover {
    border: 2px;
	  background: transparent;
	  color: #00FF0C;
    }
    tbody th, tbody td {
    border: 2px;
	  vertical-align: top;
	  text-align: left;
    }
    tbody tr:hover {
      background: #888888;
     }
    
    </style>
    
</head>
<body>
    <table border="0">
        <tr>
               <th><a href="index.jsp">Workers</a></th>
               <th><a href="index.jsp?action_id=view_job">Jobs</a></th>
               <th><a href="index.jsp?action_id=view_office">Offices</a></th>
               <th><a href="index.jsp?action_id=view_dept">Departments</a></th>
        </tr>
    </table>
    <% String path_dept = request.getContextPath()+ "/?action_id=view_dept";  %>
    <% String path_job = request.getContextPath()+ "/?action_id=view_job";  %>
    <% String path_office = request.getContextPath()+ "/?action_id=view_office";  %>
    <% String path_worker = request.getContextPath()+ "/?action_id=view_worker";  %>
    <% String path_manager = request.getContextPath()+ "/?action_id=view_top_manager";  %>
    <% Logger log = Logger.getLogger("emanlogger"); 
    log.info("from index.jsp {URI:"+request.getRequestURI()+"}");
    if (request.getParameter("select") == null) {
    %>
    
	    <%if ("view_dept".equals(request.getParameter("action_id"))) {%>
	        <jsp:include page="<%= path_dept %>" />
	        <%@ include file="showdepts.jsp" %>
	    <%} else if ("view_office".equals(request.getParameter("action_id"))) {%>
	        <jsp:include page="<%= path_office %>" />
	        <%@ include file="showoffices.jsp" %>
	    <%} else if ("view_job".equals(request.getParameter("action_id"))) {%>
	        <jsp:include page="<%= path_job %>" />
	        <%@ include file="showjobs.jsp" %> 
	    <%} else if ("view_worker".equals(request.getParameter("action_id"))) {%>
			   <% if (request.getParameter("id") != null ) { %>
			      <jsp:include page="<%= path_worker %>" />
			   <%} %>  
			<%@ include file="showworkers.jsp" %> 
	    <%} else {  %>
	        <% if (!"view_top_manager".equals(request.getParameter("action_id")) ) { %>
	            <jsp:include page="<%= path_manager %>" />
	        <%} %>
	        <%@ include file="showworkers.jsp" %>
	    <%} %>
    <%} else { %>
        <%if ("view_dept".equals(request.getParameter("action_id"))) {%>
            <jsp:include page="<%= path_dept + \"&select=true\"%>" />
            <%@ include file="showdepts.jsp" %>
        <%} else if ("view_office".equals(request.getParameter("action_id"))) {%>
            <jsp:include page="<%= path_office + \"&select=true\"%>" />
            <%@ include file="showoffices.jsp" %>
        <%} else if ("view_job".equals(request.getParameter("action_id"))) {%>
            <jsp:include page="<%= path_job + \"&select=true\"%>" />
            <%@ include file="showjobs.jsp" %> 
        <%} else if ("view_worker".equals(request.getParameter("action_id"))) {%>
               <% if (request.getParameter("id") != null ) { %>
                  <jsp:include page="<%= path_worker + \"&select=true\"%>" />
               <%} %>  
            <%@ include file="showworkers.jsp" %> 
        <%} else {  %>
            <% if (!"view_top_manager".equals(request.getParameter("action_id")) ) { %>
                <jsp:include page="<%= path_manager + \"&select=true\"%>" />
            <%} %>
            <%@ include file="showworkers.jsp" %>
        <%} }%>
</body>
</html>