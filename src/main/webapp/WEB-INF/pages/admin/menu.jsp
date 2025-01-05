<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	HttpSession sess = request.getSession(false);
	if (sess.getAttribute("userId") == null) {
		response.sendRedirect("/login");
		return;
	}
%>
<script nonce="${cspNonce}">
	var username="${username}";
	var curDate = "${curDate}";
	
	//var addiphostna ='${ip}';
</script>
	<c:url value="/logout" var="logoutUrl" /> 
	<form action="${logoutUrl}" method="post" id="logoutForm">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	</form>
	
<!-- 	<logout logout-success-url="/" logout-url="/j_spring_security_logout" /> -->
<%-- 	<c:url value="/j_spring_security_logout" var="logoutUrl" /> --%>
<%-- 	<a href="${logoutUrl}">Log Out</a> --%>
	
<aside id="left-panel" class="left-panel">
		<%-- <nav class="navbar navbar-expand-md navbar-default">
		  <div class="container-fluid">
	    	<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#main-menu" aria-controls="main-menu" aria-expanded="false" aria-label="Toggle navigation"> <i class="fa fa-bars"></i> </button>
	     
	    	<div id="main-menu" class="main-menu collapse navbar-collapse">
				<ul class="nav navbar-nav">
					${menu}
				</ul> 
	    	</div> 
	    	</div>
	  	</nav> --%>
	  	 <nav class="pcoded-navbar theme-horizontal menu-light">
		  <div class="navbar-wrapper container">
<!-- 	    	<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#main-menu" aria-controls="main-menu" aria-expanded="false" aria-label="Toggle navigation"> <i class="fa fa-bars"></i> </button> -->
	     
	    	<div id="main-menu" class="navbar-content sidenav-horizontal">
				<ul class="nav pcoded-inner-navbar sidenav-inner">
					${menu}
				</ul> 
	    	</div> 
	    	</div>
	    	 <div class="login-name">
	    	  <p class="last-login">Last Login:   <b id="lastLoginId"></b></p><!-- style="color: red;" -->
	  	 <span class="sessiontimeout px-2 py-1 txt-clr" > Session timeout in &nbsp;
                                        <i class="fa fa-hourglass fa-spin"></i> : <b class="timecount" id="div_timeout">900</b>
                                     </span></div>
	  	</nav> 
	  	
	  	
	  <!-- 	<nav class="pcoded-navbar theme-horizontal menu-light">
        <div class="navbar-wrapper container">
            <div class="navbar-content sidenav-horizontal" id="layout-sidenav">
                <ul class="nav pcoded-inner-navbar sidenav-inner">
                    <li class="nav-item pcoded-menu-caption">
                    	<label>Navigation</label>
                    </li>
                    <li class="nav-item">
                        <a href="index.html" class="nav-link "><span class="pcoded-micon"><i class="feather icon-home"></i></span><span class="pcoded-mtext">Dashboard</span></a>
                    </li>
                    <li class="nav-item pcoded-hasmenu">
                        <a href="#!" class="nav-link "><span class="pcoded-micon"><i class="feather icon-layout"></i></span><span class="pcoded-mtext">Page layouts</span></a>
                        <ul class="pcoded-submenu">
                            <li><a href="layout-vertical.html" target="_blank">Vertical</a></li>
                            <li><a href="layout-horizontal.html" target="_blank">Horizontal</a></li>
                        </ul>
                    </li>
                    <li class="nav-item pcoded-menu-caption">
                    	<label>UI Element</label>
                    </li>
                    <li class="nav-item pcoded-hasmenu">
                    	<a href="#!" class="nav-link "><span class="pcoded-micon"><i class="feather icon-box"></i></span><span class="pcoded-mtext">Basic</span></a>
                    	<ul class="pcoded-submenu">
                    		<li><a href="bc_alert.html">Alert</a></li>
                    		<li><a href="bc_button.html">Button</a></li>
                    		<li><a href="bc_badges.html">Badges</a></li>
                    		<li><a href="bc_breadcrumb-pagination.html">Breadcrumb & paggination</a></li>
                    		<li><a href="bc_card.html">Cards</a></li>
                    		<li><a href="bc_collapse.html">Collapse</a></li>
                    		<li><a href="bc_carousel.html">Carousel</a></li>
                    		<li><a href="bc_grid.html">Grid system</a></li>
                    		<li><a href="bc_progress.html">Progress</a></li>
                    		<li><a href="bc_modal.html">Modal</a></li>
                    		<li><a href="bc_spinner.html">Spinner</a></li>
                    		<li><a href="bc_tabs.html">Tabs & pills</a></li>
                    		<li><a href="bc_typography.html">Typography</a></li>
                    		<li><a href="bc_tooltip-popover.html">Tooltip & popovers</a></li>
                    		<li><a href="bc_toasts.html">Toasts</a></li>
                    		<li><a href="bc_extra.html">Other</a></li>
                    	</ul>
                    </li>
                    <li class="nav-item pcoded-menu-caption">
                        <label>Forms &amp; table</label>
                    </li>
                    <li class="nav-item">
                        <a href="form_elements.html" class="nav-link "><span class="pcoded-micon"><i class="feather icon-file-text"></i></span><span class="pcoded-mtext">Forms</span></a>
                    </li>
                    <li class="nav-item">
                        <a href="tbl_bootstrap.html" class="nav-link "><span class="pcoded-micon"><i class="feather icon-align-justify"></i></span><span class="pcoded-mtext">Bootstrap table</span></a>
                    </li>
                    <li class="nav-item pcoded-menu-caption">
                    	<label>Chart & Maps</label>
                    </li>
                    <li class="nav-item">
                        <a href="chart-apex.html" class="nav-link "><span class="pcoded-micon"><i class="feather icon-pie-chart"></i></span><span class="pcoded-mtext">Chart</span></a>
                    </li>
                    <li class="nav-item">
                        <a href="map-google.html" class="nav-link "><span class="pcoded-micon"><i class="feather icon-map"></i></span><span class="pcoded-mtext">Maps</span></a>
                    </li>
                    <li class="nav-item pcoded-menu-caption">
                    	<label>Pages</label>
                    </li>
                    <li class="nav-item pcoded-hasmenu">
                        <a href="#!" class="nav-link "><span class="pcoded-micon"><i class="feather icon-lock"></i></span><span class="pcoded-mtext">Authentication</span></a>
                        <ul class="pcoded-submenu">
                            <li class="nav-item pcoded-hasmenu">
                            <a href="auth-signup.html" class="nav-link " target="_blank">Sign up</a>
                            	<ul class="pcoded-submenu">
                            		<li><a>111</a></li>
                            	</ul>
                            </li>
                            <li><a href="auth-signin.html" target="_blank">Sign in</a></li>
                            <li></li>
                        </ul>
                    </li>
                    
                    <li class="nav-item"><a href="sampleUrl" class="nav-link "><span class="pcoded-micon"><i class="feather icon-sidebar"></i></span><span class="pcoded-mtext">Sample page</span></a></li>

                </ul>
            </div>
        </div>
    </nav> -->
</aside>