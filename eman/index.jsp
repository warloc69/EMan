<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="org.apache.log4j.Logger" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Eman</title>
    <style type="text/css">
     body {
        background-color: #888888;
    }
    table {  
        background: #888888;
        border: 2px;
    }
    p1 { 
         text-align: center;
         color: #880000;
    }
    p { text-align: center;
        color: white;
    }
    td, th {
      text-align: center;
	  border: 2px;
	  padding: .8em;
	  color: #000000;
    }
    a {
        color: #000000;
        text-decoration: none;
    }
    a:hover {
      background: transparent;
      color: #00FF0C;
      text-decoration: none;
    }
    thead th, tfoot th {
	  font: bold 10px helvetica, verdana, arial, sans-serif;
	  border: 2px;
	  text-align: center;
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
    <% String path_dept ="/action?action_id=view_dept";  %>
    <% String path_job ="/action?action_id=view_job";  %>
    <% String path_office = "/action?action_id=view_office";  %>
    <% String path_worker = "/action?action_id=view_worker";  %>
    <% String path_manager = "/action?action_id=view_top_manager";  %>
    <% Logger log = Logger.getLogger("emanlogger"); 
    log.info("from index.jsp {URI:"+request.getRequestURI()+"}");
    if (request.getParameter("select") == null) {
    %>
        <%if ("view_dept".equalsIgnoreCase(request.getParameter("action_id"))) {%>
	       <%if(request.getParameter("sort")== null) {%> 
	            <jsp:include page="<%= path_dept %>" />
	        <% }%>
	        <%@  include file="showdepts.jsp" %>
	    <%} else if ("view_office".equalsIgnoreCase(request.getParameter("action_id"))) {%>
	       <%if(request.getParameter("sort")== null) {%> 
	           <jsp:include page="<%= path_office %>" />
	       <%} %>
	        <%@ include file="showoffices.jsp" %>
	    <%} else if ("view_job".equalsIgnoreCase(request.getParameter("action_id"))) {%>
	       <%if(request.getParameter("sort")== null) {%> 
	           <jsp:include page="<%= path_job %>" />
	       <%} %>
	        <%@ include file="showjobs.jsp" %> 
	    <%} else if ("view_worker".equalsIgnoreCase(request.getParameter("action_id"))) {%>
			   <% if (request.getParameter("id") != null ) { %>
			      <jsp:include page="<%= path_worker %>" />
			   <%} %>  
			<%@ include file="showworkers.jsp" %> 
	   <%} else {  %>
	       <%if (!"search".equalsIgnoreCase(request.getParameter("action_id"))) {%>
		        <% if (!"view_top_manager".equalsIgnoreCase(request.getParameter("action_id")) ) { %>
		            <jsp:include page="<%= path_manager %>" />
		        <%}  else if (request.getParameter("id") != null && request.getParameter("filtre") == null) {%>
		           <jsp:include page="<%= path_manager %>" />
		        <%} %>		        
	           <%@ include file="showworkers.jsp" %>
	       <%} else { %>
	           <%@ include file="showworkers.jsp" %>
	       <%} %>
	    <%} %>
    <%} else { %>
        <%if ("view_dept".equalsIgnoreCase(request.getParameter("action_id"))) {%>
            <jsp:include page="<%= path_dept + \"&select=true\"%>" />
            <%@ include file="showdepts.jsp" %>
        <%} else if ("view_office".equalsIgnoreCase(request.getParameter("action_id"))) {%>
            <jsp:include page="<%= path_office + \"&select=true\"%>" />
            <%@ include file="showoffices.jsp" %>
        <%} else if ("view_job".equalsIgnoreCase(request.getParameter("action_id"))) {%>
            <jsp:include page="<%= path_job + \"&select=true\"%>" />
            <%@ include file="showjobs.jsp" %> 
        <%} else if ("view_worker".equalsIgnoreCase(request.getParameter("action_id"))) {%>
               <% if (request.getParameter("id") != null ) { %>
                  <jsp:include page="<%= path_worker + \"&select=true\"%>" />
               <%} %>  
            <%@ include file="showworkers.jsp" %> 
        <%} else {  %>
            <%if (!"search".equalsIgnoreCase(request.getParameter("action_id"))) {%>
	            <% if (!"view_top_manager".equalsIgnoreCase(request.getParameter("action_id")) ) { %>
	                <jsp:include page="<%= path_manager + \"&select=true\"%>" />
	            <%} %>
	            <%@ include file="showworkers.jsp" %>
	        <%} else { %>
               <%@ include file="showworkers.jsp" %>
           <%} %>
        <%} 
     }%>
</body>
</html>