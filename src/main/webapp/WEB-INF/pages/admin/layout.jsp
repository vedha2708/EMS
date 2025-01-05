<%-- <%@page import="com.itextpdf.text.log.SysoLogger"%> --%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%-- <%@ taglib prefix="dandelion" uri="http://github.com/dandelion"%>
<dandelion:asset cssExcludes="datatables" />
<dandelion:asset jsExcludes="datatables" />
<dandelion:asset jsExcludes="jquery" /> --%>
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

	
	<script type="text/javascript" src="ACR_Alljs/layout.js"></script>
<script type="text/javascript" nonce="${cspNonce}">
	var $ = jQuery; 
		var roleAccess = '${roleAccess}';
		var role = '${role}';
		var user_agent = '${user_agent}';
		var army_no = '${army_no}';
		var otpKey = '${otpKey}';
	
		
		var tbl, div;
     	function resetTimer() {
        	if (jQuery('#div_timeout').length) {  jQuery('#div_timeout').html(timeout());  }
     	}
     	function timeout() { return '900'; }
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
   		
   			$.ajaxSetup({
			    async: false
			});	
		
   			$.post("getLastLoginDate?"+key+"="+value,{},function(m) {//getInmailPreviousYearDtl
   			if(m.length > 0){
				document.getElementById("lastLoginId").innerHTML=m;
			}
 		 }); 
		
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
<script nonce="${cspNonce}">
		document.onkeydown = function(e) {
			if(e.keyCode == 123) { return false; }
			if(e.keyCode == 44) {  return false; }
			if(e.ctrlKey && e.keyCode == 'E'.charCodeAt(0)){ return false; } 
			if(e.ctrlKey && e.shiftKey && e.keyCode == 'I'.charCodeAt(0)){ return false; }
			if(e.ctrlKey && e.shiftKey && e.keyCode == 'J'.charCodeAt(0)){ return false; }
			if(e.ctrlKey && e.keyCode == 'U'.charCodeAt(0)){ return false; }
			if(e.ctrlKey && e.keyCode == 'S'.charCodeAt(0)){ return false; }
			if(e.ctrlKey && e.keyCode == 'H'.charCodeAt(0)){ return false; }
			if(e.ctrlKey && e.keyCode == 'A'.charCodeAt(0)){ return false; }
			if(e.ctrlKey && e.keyCode == 'E'.charCodeAt(0)){ return false; }
		}
	</script>
<style type="text/css" nonce="${cspNonce}">
.mt-8 {
	margin-top: 8rem !important;
}

img.logo {
	height: 46px;
}
</style>
</head>




<body  oncontextmenu="return false"><!--onFocus="parent_disable();" onclick="parent_disable();resetTimer();"  -->
	<c:if test="${not empty msg}">
		<input type="hidden" name="msg" id="msg" value="${msg}"
			disabled="disabled" />
	</c:if>
	<div class="loader"></div>
	<header
		class="navbar pcoded-header navbar-expand-lg navbar-light header-dark">
		<div class="container-fluid">
			<div class="col-lg-1">
				<div class="m-header">
					<a class="mobile-menu" id="mobile-collapse" href="#!"><span></span></a>
					<a href="#" class="b-brand">

						<div class="header_logo">
							<img src="layout_file/images/logo2.png">
				
						</div>
					</a>
 <!--  <a href="#!" class="mob-toggler"> <i -->
<!-- 						class="feather icon-more-vertical"></i> -->
<!-- 					</a> -->
				</div>
			</div>
			<div class="col-lg-9  p-0">
				<h1 class="text-hedding-top text-center">Employee Management System</h1>
			</div>
			<div class="col-lg-2">
				<div class="login-part collapse navbar-collapse">
<!-- 					<ul class="navbar-nav mr-auto"> -->
<!-- 						<li class="nav-item"> -->
<!-- 							                        <a href="#!" class="pop-search"><i class="feather icon-search"></i></a> -->
<!-- 							<div class="search-bar"> -->
<!-- 								<input type="text" class="form-control border-0 shadow-none" -->
<!-- 									placeholder="Search hear"> -->
<!-- 								<button type="button" class="close" aria-label="Close"> -->
<!-- 									<span aria-hidden="true">&times;</span> -->
<!-- 								</button> -->
<!-- 							</div> -->
<!-- 						</li> -->


<!-- 					</ul> -->
					<ul class="top-pro-body">
						<li><i class="fa fa-user"></i> <span>${roleloginName}</span>
						</li>
						<li><a href="javascript:formSubmit();" class="dropdown-item" id="lgoutbtn" ><!-- onclick="localStorage.clear();" -->
								<div class="tooltip">
									<i class="fa fa-sign-out"></i><span class="tooltiptext">Logout</span>
								</div>
						</a></li>
					</ul>

				</div>
			</div>
		</div>
		<div id="WaitLoader" class="d-none" align="center">
			<span id="">Processing Data.Please Wait ...<i
				class="fa fa-hourglass fa-spin f-18"></i></span>
		</div>
	</header>
	<!-- [ Header ] end -->

	<!-- [ Main Content ] start -->

	<div class="middle_content">
		<div class="mt-8 mx-0">
			<!-- 			<div class="row mx-0"> -->
			<div class="outer_col menubar">
				<tiles:insertAttribute name="menu" />
			</div>
			<div class="outer_col body-content">
				<tiles:insertAttribute name="body" />
			</div>
		</div>
	</div>



	<!-- Warning Section Ends -->

	<!-- Required Js -->

<!-- 	<script src="js/assets/js/bootstrap.min.js"></script> Hina-->
	
	<script src="layout_file/assets/js/pcoded.min.js"></script>
	<!--     prism Js -->

	<script nonce="${cspNonce}">
    
    jQuery(window).on('load',function() {
   	    $(".loader").fadeOut("slow");
   	});	
    
        (function() {
           /*  if ($('#layout-sidenav').hasClass('sidenav-horizontal') || window.layoutHelpers.isSmallScreen()) {
                return;
            } */
            try {
                window.layoutHelpers._getSetting("Rtl")
                window.layoutHelpers.setCollapsed(
                    localStorage.getItem('layoutCollapsed') === 'true',
                    false
                );
            } catch (e) {}
        })();
        $(function() {
          /*   $('#layout-sidenav').each(function() {
                new SideNav(this, {
                    orientation: $(this).hasClass('sidenav-horizontal') ? 'horizontal' : 'vertical'
                });
            }); */
            $('body').on('click', '.layout-sidenav-toggle', function(e) {
                e.preventDefault();
                window.layoutHelpers.toggleCollapsed();
                if (!window.layoutHelpers.isSmallScreen()) {
                    try {
                        localStorage.setItem('layoutCollapsed', String(window.layoutHelpers.isCollapsed()));
                    } catch (e) {}
                }
            });
        });
        $(document).ready(function() {
            $("#pcoded").pcodedmenu({
                themelayout: 'horizontal',
                MenuTrigger: 'hover',
                SubMenuTrigger: 'hover',
            });
            
          
        });
        function opendiv() {
        	  $(".profile-notification").toggleClass("show");
		}
        $(".show").on('blur',function(){
            $(this).fadeOut(300);
        });
    </script>
</body>
</html>
