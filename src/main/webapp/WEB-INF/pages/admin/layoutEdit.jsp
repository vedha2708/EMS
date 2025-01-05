<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<%
	HttpSession sess = request.getSession(false);
	if (sess.getAttribute("userId") == null) {
		sess.invalidate();
		response.sendRedirect("/login"); return; 
	} 
	
	String user_agentWithIp = String.valueOf(sess.getAttribute("user_agentWithIp"));
	String userAgent = request.getHeader("User-Agent");
    String ip = "";

	if (request != null) {
        ip = request.getHeader("X-FORWARDED-FOR");
        if (ip == null || "".equals(ip)){
            ip = request.getRemoteAddr();
        }
    }
	String currentuser_agentWithIp = userAgent+"_"+ip;
	currentuser_agentWithIp = currentuser_agentWithIp.replace("&#40;","(");
	currentuser_agentWithIp = currentuser_agentWithIp.replace("&#41;",")");
	
	//out.print(currentuser_agentWithIp+"<=c = s=>"+user_agentWithIp);
	if(!user_agentWithIp.equals(currentuser_agentWithIp)){
		sess.invalidate();
		response.sendRedirect("/login"); return; 
	}
%>

<!doctype html>
<html>
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title><spring:message code="myapp.title" /></title>
<link rel="shortcut icon" href="layout_file/images/favicon.png">
<link rel="stylesheet" href="layout_file/css/font-awesome.min.css">
<script type="text/javascript" src="layout_file/js/jquery-3.6.0.min.js"></script>
<script src="layout_file/js/plugins.js"></script>
<script src="layout_file/js/main.js"></script>
<link rel="stylesheet" href="layout_file/assets/css/style.css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>

	
	<script nonce="${cspNonce}">
		var KeySpec ="${KeySpec}";
		var roleid="${roleid}";
		//var username="${username}";
		var susno="${sus_no}";
		var unitname="${unit_name}";
		//var curDate = "${curDate}";
		var addiphostna ='${ip}';
	</script>
	
	<script type="text/javascript" nonce="${cspNonce}">
	var $ = jQuery; 
		var roleAccess = '${roleAccess}';
		var role = '${role}';
		var user_agent = '${user_agent}';
		var army_no = '${army_no}';
		var otpKey = '${otpKey}';
	
		 jQuery(window).on('load',function() {
		   	    $(".loader").fadeOut("slow");
		   	});	
		
		var tbl, div;
     	function resetTimer() {
        	if (jQuery('#div_timeout').length) {  jQuery('#div_timeout').html(timeout());  }
     	}
     	function timeout() { return '600'; }
     	function getsubmodule(id){ localStorage.setItem("subModule", id); }
     	function getmodule(id){localStorage.setItem("Module", id); }
     	function getpagelink(id){localStorage.setItem("pagelink", id); }
     	
     	var key = "${_csrf.parameterName}";
     	var value = "${_csrf.token}";
     	
     	jQuery(document).on('keypress', function(event) {
     		localStorage.setItem("army_no", army_no);
     		
     		var regex = new RegExp("${regScript}");
     	    var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
     	    if (!regex.test(key)) {
     	       event.preventDefault();
     	       return false;
     	    } 
     	});
     	
   		jQuery(document).ready(function() {	
   			jQuery('body').bind('cut copy paste', function (e) {
	   	        e.preventDefault();
	   	    });
   			
   			// set current sub module
//    			jQuery('ul#Dropdown_'+localStorage.getItem("Module")).parent().attr("class","nav-item dropdown dropdown-item show");
//    			jQuery('ul#Dropdown_'+localStorage.getItem("subModule")).parent().attr("class","dropdown-item dropdown create_search  show");
//    			jQuery('ul#Dropdown_'+localStorage.getItem("subModule")).attr("class","dropdown-menu scrollbar show");
//    			jQuery('ul#Dropdown_'+localStorage.getItem("Module")).attr("class","dropdown-menu show");
//    			jQuery('li#Dropdown_scr'+localStorage.getItem("pagelink")).attr("class","dropdown-item active");
   			
   			setInterval(function() {
				var today = new Date();
				var date =("0" + today.getDate()).slice(-2)+'-'+ ("0" + (today.getMonth()+1)).slice(-2)+'-'+today.getFullYear();
				var time = ("0" + today.getHours()).slice(-2) + ":" + ("0" + today.getMinutes()).slice(-2);// + ":" + ("0" + today.getSeconds()).slice(-2);
				var dateTime = date+' '+time;
				jQuery("#datetime").text(dateTime);
				
				if (jQuery('#div_timeout').length) {
	            	 var tt = jQuery('#div_timeout').html();
	                 if (tt === undefined) {
	                     tt = timeout();
	                 }
	                 var ct = parseInt(tt, 10) - 1;
	                 jQuery('#div_timeout').html(ct.toString().padStart(3, '0'));
	                 if (ct === 0) {
	                	 formSubmit();
	                 }
	             } else {
	            	 formSubmit();
	             }
			}, 1000);
			try
			{
				var msg = document.getElementById("msg").value;
				if(msg != null )
				{
					alert(msg);
				}
			}
			catch (e) {
			}
		});
		function formSubmit() {
// 			alert("log")
			document.getElementById("logoutForm").submit();
		}
		popupWindow = null;
		function parent_disable() {
			if(popupWindow && !popupWindow.closed)
				popupWindow.focus();
		}
	</script>

</head>


 
        <script src="layout_file/assets/js/pcoded.min.js"></script>

    <script type="text/javascript" src="ACR_Alljs/layoutEdit.js"></script>

<body > <!-- onFocus="parent_disable();" onclick="parent_disable();resetTimer();"   oncontextmenu="return false"-->
		<c:if test="${not empty msg}">
			<input type="hidden" name="msg" id="msg" value="${msg}" />
		</c:if>
		<div class="loader"></div>
		
	<c:url value="/logout" var="logoutUrl" />
	<form action="${logoutUrl}" method="post" id="logoutForm">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	</form>
		<tiles:insertAttribute name="menu" />
	<!-- 	<a id="menuToggle" class="menutoggle pull-left" style="display: none;"><i class="fa fa fa-tasks"></i></a>
		<div id="right-panel" class="right-panel">  -->
			<div class="session_timeout d-none" align="center" ><!-- style="display: none;" -->
 				<span> Session timeout in &nbsp; <i class="fa fa-hourglass fa-spin"></i>  :&nbsp;&nbsp; <b id="div_timeout">6000</b></span>
 			</div>			
		  		  
		  	<div class="content top" > <!-- style="top:10px;" -->
		    	<!-- write your html --> 
		    	<tiles:insertAttribute name="body" />
		  	</div>
		
	</body>
</html>

 
 