<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="ua.ivanchenko.eman.model.IWorker" %> 
<%@ page import="java.math.BigInteger" %> 
<%@ page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edit worker</title>
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
 function trim(str) {
	    var newstr = str.replace(/^\s*(.+?)\s*$/, "$1");
	    if (newstr == " ") {
	        return "";
	    }
	    return newstr;
	}
 function validate() {
	 if (trim(window.document.forms.f1.elements.fname.value) == "") {
		 alert("Write first name");
		 return false;
	 }
	 if (trim(window.document.forms.f1.elements.lname.value) == "") {
         alert("Write last name");
         return false;
     }
	 if (window.document.forms.f1.elements.job_id.value == "") {
         alert("Choose job");
         return false;
     }
	 if (window.document.forms.f1.elements.office_id.value == "") {
         alert("Choose office");
         return false;
     }
	 if (window.document.forms.f1.elements.dept_id.value == "") {
         alert("Choose department");
         return false;
     }
	 if (trim(window.document.forms.f1.elements.sal.value) == "") {
         alert("Write salegrad");
         return false;
     }
	 return true;
 }
 function clearman() {
	 window.document.forms.f1.elements.mgr_id.value = "null";
	 window.document.forms.f1.elements.man.value = "";
 }
 function clearjob() {
     window.document.forms.f1.elements.job_id.value = "";
     window.document.forms.f1.elements.job.value = "";
 }
 function cleardept() {
     window.document.forms.f1.elements.dept_id.value = "";
     window.document.forms.f1.elements.dept.value = "";
 }
 function clearoffice() {
     window.document.forms.f1.elements.office_id.value = "";
     window.document.forms.f1.elements.office.value = "";
 }
 </script>
    <table>
        <form name="f1" action="<%=request.getContextPath()%>/action" method="post" onSubmit="return validate()">  
            <%
          
            IWorker work = (IWorker) request.getSession().getAttribute("e_worker");
            HashMap<String,String> info = (HashMap<String,String>) request.getSession().getAttribute("info");
            if("edit_worker_update".equalsIgnoreCase(request.getParameterValues("action_id")[request.getParameterValues("action_id").length-1]))
                {
            %>
            <tr> 
                <td><p1>First Name:</p1></td>
                <td><input name="fname" value="<%=work.getFirstName() %>" /></td> 
                <input type="hidden" name="id" value=<%=work.getID() %>  /> 
                <input type="hidden" name="edit_id" value=1 /> 
                <input type="hidden" name="action_id" value=edit_worker_update />
            </tr>
            <tr> 
                <td><p1>Last Name:</p1></td>
                <td><input name="lname" value="<%=work.getLastName() %>" /></td> 
            </tr>
               <tr>
               <td><p1>Manager:</p1></td>
               <td>
               <input type="text" name="man" readonly value="<%= work.getManagerID() != null ? info.get("mgr_id") : " "  %> "/>
               <a onclick="butOpenWin_onclick('index.jsp?select=true', 650, 450)">&nbsp;+&nbsp;</a>
               <a onclick="clearman()">&nbsp;-&nbsp;</a>
               <input type="hidden" name="mgr_id" value="<%=work.getManagerID() %>" /> 
                </td>
                </tr> 
                 <tr>
               <td><p1>Job:</p1></td>
               <td>
                <input type="text" name="job"   readonly value="<%= info.get("job_id") %>"/>
                <a onclick="butOpenWin_onclick('index.jsp?action_id=view_job&select=true', 650, 450)">&nbsp;+&nbsp;</a>
                <a onclick="clearjob()">&nbsp;-&nbsp;</a>
                <input type="hidden" name="job_id" value="<%=work.getJobID() %>" />
                </td>
                </tr>   
                 <tr>
               <td><p1>Offices:</p1></td>
               <td>
                <input type="text" name="office"   readonly value="<%= info.get("office_id") %>"/>
                <a onclick="butOpenWin_onclick('index.jsp?action_id=view_office&select=true', 650, 450)">&nbsp;+&nbsp;</a>
                <a onclick="clearoffice()">&nbsp;-&nbsp;</a>
                <input type="hidden" name="office_id" value="<%=work.getOfficeID()%>" />
                </td>
                </tr>   
                 <tr>
               <td><p1>Department:</p1></td>
               <td>
                <input type="text" name="dept"   readonly value="<%= info.get("dept_id") %>"/>
                 <a onclick="butOpenWin_onclick('index.jsp?action_id=view_dept&select=true', 650, 450)">&nbsp;+&nbsp;</a>
                <a onclick="cleardept()">&nbsp; - &nbsp;</a>
                <input type="hidden" name="dept_id" value="<%=work.getDepartmentID() %>" />
                </td>
                </tr>   
               <tr> 
                <td><p1>Salegrade :</p1></td>
                <td><input name="sal" maxlength="10" value=<%=work.getSalegrade() %> /></td> 
              </tr>
             <%
                } else {
                	IWorker wor = (IWorker) request.getSession().getAttribute("e_wor");
             %>
            <tr> 
                <td><p1>First Name:</p1></td>
                <td><input name="fname" /></td> 
            </tr>
            <tr> 
                <td><p1>Last Name:</p1></td>
                <td><input name="lname" /></td>
                <input type="hidden" name="edit_id" value=0 /> 
                <input type="hidden" name="action_id" value=edit_worker_add /> 
            </tr>
                 <tr>
               <td><p1>Manager:</p1></td>
               <td>
                <% if(wor != null) { %>
		               <input type="text" name="man"  readonly value="<%=wor.getLastName() %>" />
		               <a onclick="butOpenWin_onclick('index.jsp?select=true', 650, 450)">&nbsp;+&nbsp;</a>
		               <a onclick="clearman()">&nbsp; - &nbsp;</a>
		               <input type="hidden" name="mgr_id" value="<%=wor.getID() %>" />
	               <%} else { %>
		               <input type="text" name="man"  readonly value="" />
	                   <a onclick="butOpenWin_onclick('index.jsp?select=true', 650, 450)">&nbsp;+&nbsp;</a>
	                   <a onclick="clearman()">&nbsp; - &nbsp;</a>
	                   <input type="hidden" name="mgr_id" value="null" />
                   <%} %>
                </td>
                </tr> 
                 <tr>
               <td><p1>Job:</p1></td>
               <td>
                <input type="text" name="job" readonly />
                 <a onclick="butOpenWin_onclick('index.jsp?action_id=view_job&select=true', 650, 450)">&nbsp;+&nbsp;</a>
                <a onclick="clearjob()">&nbsp;-&nbsp;</a>
                <input type="hidden" name="job_id" />
                </td>
                </tr>   
                 <tr>
               <td><p1>Offices:</p1></td>
               <td>
                <input type="text" name="office" readonly />
                 <a onclick="butOpenWin_onclick('index.jsp?action_id=view_office&select=true', 650, 450)">&nbsp;+&nbsp;</a>
                <a onclick="clearoffice()">&nbsp;-&nbsp;</a>
                <input type="hidden" name="office_id" />
                </td>
                </tr>   
                 <tr>
               <td><p1>Department:</p1></td>
               <td>
                <input type="text" name="dept" readonly />
                <a onclick="butOpenWin_onclick('index.jsp?action_id=view_dept&select=true', 650, 450)">&nbsp;+&nbsp;</a>
                <a onclick="cleardept()">&nbsp; - &nbsp;</a>
                <input type="hidden" name="dept_id" />
                </td>
                </tr> 
                <tr> 
                <td><p1>Salegrade :</p1></td>
                <td><input name="sal" maxlength="10" /></td> 
              </tr>  
             <%
                }
             %>
             <tr>
                <td><input type="submit" /></td>         
                </form> 
                   <form action="<%=request.getContextPath()%>/action" method="get">
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