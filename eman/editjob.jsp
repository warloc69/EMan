<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="ua.ivanchenko.eman.model.IJob" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edit job</title>
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
<script language="JavaScript" type="text/javascript">
function trim(str) {
    var newstr = str.replace(/^\s*(.+?)\s*$/, "$1");
    if (newstr == " ") {
        return "";
    }
    return newstr;
}
 function validate() {
     if (trim(window.document.forms.f1.elements.title.value) == "") {
         alert("Write title name");
         return false;
     }
     if (trim(window.document.forms.f1.elements.desc.value) == "") {
         alert("Write description");
         return false;
     }
     return true;
 } 
 </script>
	<table>
		<form name="f1" action="<%=request.getContextPath()%>/action" method="post" onSubmit="return validate()" >
		    <%
		    IJob job = (IJob) request.getSession().getAttribute("e_job");
		    if("edit_job_update".equalsIgnoreCase(request.getParameterValues("action_id")[request.getParameterValues("action_id").length-1]))
            {
		    %>
			<tr> 
			    <td><p1>Title:</p1></td>
			    <td><input name="title" value="<%=job.getTitle() %>" /></td> 
			</tr>
			 <tr>
			    <td><p1>Description:</p1></td>
			    <td> <TEXTAREA NAME="desc" COLS=40 ROWS=6 ><%=job.getDescription() %></TEXTAREA></td>	             
	            <input type="hidden" name="id" value=<%=job.getID() %> /> 
	            <input type="hidden" name="edit_id" value=1  /> 
	            <input type="hidden" name="action_id" value=edit_job_update />           
	         </tr>
	         <%
		    	} else {
	         %>
	         <tr> 
                <td><p1>Title:</p1></td>
                <td><input name="title" /></td> 
            </tr>
             <tr>
                <td><p1>Description:</p1></td>
                <td> <TEXTAREA NAME="desc" COLS=40 ROWS=6 ></TEXTAREA></td>
                <input type="hidden" name="edit_id" value=0 /> 
                <input type="hidden" name="action_id" value=edit_job_add />           
             </tr>
             <%
		    	}
             %>
		     <tr>
			    <td><input type="submit" /></td> 
			  </tr>        
		        </form> 
			       <form action="<%=request.getContextPath()%>/action" method="get">
			           <td> 
				            <input type="hidden" name="action_id" value="view_job"/>
				            <input type="submit" value="cancel" />
			           </td>
			      </form>
		      </tr>
	</table>
</body>
</html>