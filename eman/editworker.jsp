<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="ua.ivanchenko.eman.model.IWorker" %> 
<%@ page import="java.math.BigInteger" %> 
<%@ page import="java.util.*" %>
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
<script language="JavaScript" type="text/javascript">
   var manager_id;
 function butOpenWin_onclick(winUrl, winWidth, winHeight) {
    var winTop = (screen.height / 2) - 125;
    var winLeft = (screen.width / 2) - 125;
    var windowFeatures = "scrollbars=yes,resizable=yes,";
    windowFeatures += "width=" + winWidth + ",";
    windowFeatures += "height=" + winHeight + ",";
    windowFeatures += "left=" + winLeft + ",";
    windowFeatures += "top=" + winTop;
    newWindow = window.open(winUrl, "myWindow", windowFeatures);
  }
 </script>
  <a href="javascript: alert(window.document.forms.f1.elements.mgr_id.value);">var worker_id</a><br>
    <table>
        <form name="f1" action="<%=request.getContextPath()%>/" method="post">  
            <%
            IWorker work = (IWorker) request.getSession().getAttribute("e_worker");
            HashMap<String,String> info = (HashMap<String,String>) request.getSession().getAttribute("info");
            if("edit_worker_update".equals(request.getParameter("action_id")))
                {
            %>
            <tr> 
                <td>First Name:</td>
                <td><input name="fname" value="<%=work.getFirstName() %>" /></td> 
                <input type="hidden" name="id" value=<%=work.getID() %>  /> 
                <input type="hidden" name="edit_id" value=1 /> 
                <input type="hidden" name="action_id" value=edit_worker_update />
            </tr>
            <tr> 
                <td>Last Name:</td>
                <td><input name="lname" value="<%=work.getLastName() %>" /></td> 
            </tr>
               <tr>
               <td>Manager:</td>
               <td>
               <input type="text" name="man"  onclick="butOpenWin_onclick('index.jsp?select=true', 650, 450)" readonly value="<%= work.getManagerID() != null ? info.get("mgr_id") : "manager"  %> "/>
               <input type="hidden" name="mgr_id" value="<%=work.getManagerID() %>" /> 
                </td>
                </tr> 
                 <tr>
               <td>Job:</td>
               <td>
                <input type="text" name="job"  onclick="butOpenWin_onclick('index.jsp?action_id=view_job&select=true', 650, 450)" readonly value="<%= info.get("job_id") %>"/>
                <input type="hidden" name="job_id" value="<%=work.getJobID() %>" />
                </td>
                </tr>   
                 <tr>
               <td>Offices:</td>
               <td>
                <input type="text" name="office"  onclick="butOpenWin_onclick('index.jsp?action_id=view_office&select=true', 650, 450)" readonly value="<%= info.get("office_id") %>"/>
                <input type="hidden" name="office_id" value="<%=work.getOfficeID()%>" />
                </td>
                </tr>   
                 <tr>
               <td>Department:</td>
               <td>
                <input type="text" name="dept"  onclick="butOpenWin_onclick('index.jsp?action_id=view_dept&select=true', 650, 450)" readonly value="<%= info.get("dept_id") %>"/>
                <input type="hidden" name="dept_id" value="<%=work.getDepartmentID() %>" />
                </td>
                </tr>   
               <tr> 
                <td>Salegrade :</td>
                <td><input name="sal" value=<%=work.getSalegrade() %> /></td> 
              </tr>
             <%
                } else {
             %>
            <tr> 
                <td>First Name:</td>
                <td><input name="fname" /></td> 
            </tr>
            <tr> 
                <td>Last Name:</td>
                <td><input name="lname" /></td>
                <input type="hidden" name="edit_id" value=0 /> 
                <input type="hidden" name="action_id" value=edit_worker_add /> 
            </tr>
                 <tr>
               <td>Manager:</td>
               <td>
	               <input type="text" name="man"  onclick="butOpenWin_onclick('index.jsp?select=true', 650, 450)" readonly value="manager"/>
	               <input type="hidden" name="mgr_id" value=1 />
                </td>
                </tr> 
                 <tr>
               <td>Job:</td>
               <td>
                <input type="text" name="job"  onclick="butOpenWin_onclick('index.jsp?action_id=view_job&select=true', 650, 450)" readonly value="job"/>
                <input type="hidden" name="job_id" value=1 />
                </td>
                </tr>   
                 <tr>
               <td>Offices:</td>
               <td>
                <input type="text" name="office"  onclick="butOpenWin_onclick('index.jsp?action_id=view_office&select=true', 650, 450)" readonly value="office"/>
                <input type="hidden" name="office_id" value=1 />
                </td>
                </tr>   
                 <tr>
               <td>Department:</td>
               <td>
                <input type="text" name="dept"  onclick="butOpenWin_onclick('index.jsp?action_id=view_dept&select=true', 650, 450)" readonly value="dept"/>
                <input type="hidden" name="dept_id" value=1 />
                </td>
                </tr> 
                <tr> 
                <td>Salegrade :</td>
                <td><input name="sal" /></td> 
              </tr>  
             <%
                }
             %>
             <tr>
                <td><input type="submit" /></td>         
                </form> 
                   <form action="<%=request.getContextPath()%>/" method="get">
                       <td> 
                            <input type="hidden" name="action_id" value="view_top_manager"/>
                            <% if (work != null) { %>
                            <input type="hidden" name="id" value="<%= work.getManagerID() %>" />
                            <% } %>
                            <input type="submit" value="cancel" />
                       </td>
                  </form>
              </tr>
    </table>
</body>
</html>