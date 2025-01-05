package com.BisagN.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.BisagN.dao.UserLoginDAO;
import com.BisagN.dao.UserLoginDAOImpl;
import com.BisagN.models.Role;
import com.BisagN.models.TB_LDAP_MODULE_MASTER;
import com.BisagN.models.TB_LDAP_SCREEN_MASTER;
import com.BisagN.models.TB_LDAP_SUBMODULE_MASTER;
import com.BisagN.models.UserLogin;


public class redirectLogin extends SavedRequestAwareAuthenticationSuccessHandler{
	
	@Autowired
	private UserLoginDAO userLoginDAO;
	
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws ServletException, IOException {
		
		String txtInput = request.getParameter("txtInput").replaceAll("\\s", "").toString();
		String capcha =  request.getSession().getAttribute("capcha").toString();
	//	if(!txtInput.equals(capcha)){
	//		response.sendRedirect("/login");
	//	}else{
			System.out.println("onAuthenticationSuccess");
			Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
		
			String role1 = null;
			for(String role:roles)
			{
				request.getSession().setAttribute("role", role);
				role1 = role;
			}
			System.out.println("role name ==" + role1);
			System.out.println(authentication.getName());
			
			int userId = userLoginDAO.getUserId(authentication.getName());
			Role roleList = userLoginDAO.findRole_url(role1);
			
			request.getSession().setAttribute("userId_for_jnlp", userId);
			request.getSession().setAttribute("username", authentication.getName());
			
			String RoleUrl =  "";
			if(roleList.getRole_url() != null) {
				RoleUrl =  roleList.getRole_url();
			}
			String RoleType = "";		
			if(roleList.getRole_type() != null) {
				RoleType =  roleList.getRole_type();		
			}
			String Acces_lvl = "";
			if(roleList.getAccess_lvl() != null) {
				Acces_lvl = roleList.getAccess_lvl();
			}
			String subAcces_lvl = "";
			if(roleList.getSub_access_lvl() != null) {
				subAcces_lvl = roleList.getSub_access_lvl();
			}
			
			String staff_lvl = "";
			if(roleList.getStaff_lvl() != null) {
				staff_lvl = roleList.getStaff_lvl();
			}
						
			request.getSession().setAttribute("roleSusNo", "");
			request.getSession().setAttribute("roleUrl", RoleUrl);
			request.getSession().setAttribute("roleType", RoleType);
			request.getSession().setAttribute("roleAccess", Acces_lvl);		
			request.getSession().setAttribute("roleSubAccess", subAcces_lvl);
			request.getSession().setAttribute("roleStaff_lvl", staff_lvl);
			
			int roleid =  roleList.getRoleId(); 
			UserLogin  addData = userLoginDAO.findByRoleId(userId);
//			request.getSession().setAttribute("army_no", addData.getArmy_no());
			if(roleid !=0) {
				request.getSession().setAttribute("roleid", roleid);
			}
			request.getSession().setAttribute("successValue", "Fail");
			
			String login_name = "";
			if(addData.getLogin_name() != null) {
				login_name = addData.getLogin_name();
			}
			
			request.getSession().setAttribute("userId", userId);
			request.getSession().setAttribute("username", addData.getUserName());
//			request.getSession().setAttribute("army_no", addData.getArmy_no());
			if(roleid !=0) {
				request.getSession().setAttribute("roleid", roleid);
			}
			request.getSession().setAttribute("roleloginName", login_name);
			//////////////////
			
			System.out.println("success 1" + role1);
			
			String ip = getClientIp(request);
			request.getSession().setAttribute("ip", ip);
			
			String userAgent = request.getHeader("User-Agent");
		    request.getSession().setAttribute("user_agentWithIp", userAgent+"_"+ip);
			request.getSession().setAttribute("user_agent", userAgent);
//			
			//request.getSession().setAttribute("otpKey", "commonPwdEncKeys");
			request.getSession().setAttribute("KeySpec", "dc0da04af8fee58593442bf834b30739");
			
			final long fileSizeLimit = 2 * 1024 * 1024; 
			request.getSession().setAttribute("fileSizeLimit", fileSizeLimit);
			
			String curDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
			request.getSession().setAttribute("curDate", curDate);
			
			request.getSession().setAttribute("regScript", "^[a-zA-Z0-9\\\\[\\\\] \\\\+ \\\\* \\\\-.,/ ~!@#$^&%_]+$");
			
			request.getSession().setAttribute("helpdeskFilePath", "/srv"+ File.separator + "HELP");
			request.getSession().setAttribute("handingTakingOverPath", "/srv"+ File.separator + "handingTakingOver");
			
			List<TB_LDAP_MODULE_MASTER> module = userLoginDAO.getModulelist(roleid);
			String menu = "";
			 
			if(!RoleUrl.equals("")) {
				 menu="<li><a href='"+RoleUrl+"' class='btn btn-danger btn-sm'>Dashboard</a></li>";
			}
			
			
			for(int mod=0;mod<module.size();mod++){
				if(module.get(mod).getModule_name().equals("ADMIN")){
					menu += "<li class='nav-item pcoded-hasmenu' id='"+module.get(mod).getModule_name() +"_menu'>";	
					menu += "<a class='nav-link' href='#' id='Dropdown_"+module.get(mod).getModule_name()+"' role='button' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'><i class='fa fa-user'></i><span class='pcoded-mtext'>"+module.get(mod).getModule_name()+"</span></a>"; //onclick='getmodule(\""+module.get(mod).getModule_name()+"\")'
					menu += "<ul class='pcoded-submenu' id='Dropdown_"+module.get(mod).getModule_name()+"' aria-labelledby='Dropdown_"+module.get(mod).getModule_name()+"' >";
						List<TB_LDAP_SUBMODULE_MASTER> submodule = userLoginDAO.getSubModulelist(module.get(mod).getId(),roleid);
						for(int submod=0;submod<submodule.size();submod++){
							menu += "<li class='nav-item pcoded-hasmenu ' >";
								menu += "<a class='nav-link' id='Dropdown_"+submodule.get(submod).getId()+"' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false' ><span class=\"\"><i class=\"\"></i></span><span class=\"pcoded-mtext\">"+submodule.get(submod).getSubmodule_name()+"</span></a>";//onclick='getsubmodule("+submodule.get(submod).getId()+")' 
								menu += "<ul class='pcoded-submenu' aria-labelledby='Dropdown_"+submodule.get(submod).getId()+"' id='Dropdown_"+submodule.get(submod).getId()+"' >"; //style='height: auto;'
									List<TB_LDAP_SCREEN_MASTER> screen = userLoginDAO.getScreenlist(module.get(mod).getId(),submodule.get(submod).getId(),roleid);
									for(int scr=0;scr<screen.size();scr++){
										menu += "<li id='Dropdown_scr"+screen.get(scr).getId()+"' class='dropdown-item'><a class='link' href='"+screen.get(scr).getScreen_url()+"' > "+screen.get(scr).getScreen_name()+"</a></li>"; //onclick='getpagelink("+screen.get(scr).getId()+")'
									}
								menu += "</ul>";
							menu += "</li>";
						}
					menu += "</ul>";
				menu += "</li>";
				}else{
					
//					menu += "<li class='nav-item pcoded-hasmenu' id='"+module.get(mod).getModule_name() +"_menu'>";	
//					menu += "<a class='nav-link' href='#' id='Dropdown_"+module.get(mod).getModule_name()+"' onclick='getmodule(\""+module.get(mod).getModule_name()+"\")' role='button' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'><span class=\"pcoded-micon\"><i class=\"feather icon-box\"></i></span><span class=\"pcoded-mtext\">"+module.get(mod).getModule_name()+"</span></a>";
//					menu += "<ul class='pcoded-submenu' id='Dropdown_"+module.get(mod).getModule_name()+"' aria-labelledby='Dropdown_"+module.get(mod).getModule_name()+"' >";
						List<TB_LDAP_SUBMODULE_MASTER> submodule = userLoginDAO.getSubModulelist(module.get(mod).getId(),roleid);
						for(int submod=0;submod<submodule.size();submod++){
							menu += "<li class='nav-item pcoded-hasmenu ' >";
								menu += "<a class='nav-link' id='Dropdown_"+submodule.get(submod).getId()+"' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false' ><i class='fa fa-layer-group'></i><span class='pcoded-mtext'>"+submodule.get(submod).getSubmodule_name()+"</span></a>";//onclick='getsubmodule("+submodule.get(submod).getId()+")' 
								menu += "<ul class='pcoded-submenu' aria-labelledby='Dropdown_"+submodule.get(submod).getId()+"' id='Dropdown_"+submodule.get(submod).getId()+"' >"; //style='height: auto;'
									List<TB_LDAP_SCREEN_MASTER> screen = userLoginDAO.getScreenlist(module.get(mod).getId(),submodule.get(submod).getId(),roleid); 
									for(int scr=0;scr<screen.size();scr++){
										menu += "<li id='Dropdown_scr"+screen.get(scr).getId()+"' class='dropdown-item'><a class='link' href='"+screen.get(scr).getScreen_url()+"' > "+screen.get(scr).getScreen_name()+"</a></li>"; //onclick='getpagelink("+screen.get(scr).getId()+")'
									}
								menu += "</ul>";
							menu += "</li>";
						}
//					menu += "</ul>";
//				menu += "</li>";
				}
			}
			
			
//			for(int mod=0;mod<module.size() ;mod++) {
//				System.out.println("module name == "+module.get(mod).getModule_name());
//				 menu += "<li class='nav-item dropdown dropdown-item show' id='"+module.get(mod).getModule_name() +"_menu'>";	
//					menu += "<a class='nav-link dropdown-toggle' href='#' id='Dropdown_"+module.get(mod).getModule_name()+"' role='button' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'><i class='fa fa-th-large'></i>"+module.get(mod).getModule_name()+"</a>";
//					menu += "<ul class='dropdown-menu show' aria-labelledby='Dropdown_"+module.get(mod).getModule_name()+"' >";
//						List<TB_LDAP_SUBMODULE_MASTER> submodule = userLoginDAO.getSubModulelist(module.get(mod).getId(),roleid);
//						
//						for(int submod=0;submod<submodule.size();submod++){
//							menu += "<li class='dropdown-item dropdown create_search' >";
//								menu += "<a class='dropdown-toggle' id='Dropdown_"+submodule.get(submod).getId()+"' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false' onclick='getsubmodule("+submodule.get(submod).getId()+")'><i class='fa fa-plus-circle'></i>"+submodule.get(submod).getSubmodule_name()+"</a>"; 
//								menu += "<ul class='dropdown-menu scrollbar' aria-labelledby='Dropdown_"+submodule.get(submod).getId()+"' id='Dropdown_"+submodule.get(submod).getId()+"' style='height: 100%;'>";
//									List<TB_LDAP_SCREEN_MASTER> screen = userLoginDAO.getScreenlist(module.get(mod).getId(),submodule.get(submod).getId(),roleid);
//									for(int scr=0;scr<screen.size();scr++){
//										menu += "<li class='dropdown-item'><a href='"+screen.get(scr).getScreen_url()+"' onclick='localStorage.Abandon();'> <i class='fa fa-arrow-circle-o-right'></i>"+screen.get(scr).getScreen_name()+"</a></li>";
//									}
//								menu += "</ul>";
//							menu += "</li>";
//						}
//					menu += "</ul>";
//				menu += "</li>";
//			}
			request.getSession().setAttribute("menu", menu);
//			
//			// User Login Status
//			
//			
//			Session session1 = HibernateUtil.getSessionFactory().openSession();
//			Transaction tx = session1.beginTransaction();
//			try {
//				HD_TB_BISAG_USER_LOGIN_COUNT_INFO N = new HD_TB_BISAG_USER_LOGIN_COUNT_INFO();
//				N.setDate(new Date());
//				N.setLoginstatus("Active");
//				N.setStatus("1");
//				N.setUserid(userId);
//				session1.save(N);
//				tx.commit();
//			}catch (Exception e) {
//				System.out.println("Exception Logged User :" + e.getMessage());
//				tx.rollback();
//			}finally {
//				session1.close();
//			}
//			// User Login Status
//			
			System.out.println("Call commonDashboard");
			response.sendRedirect("../admin/commonDashboard");
	//	}
	}	
	
	private static String getClientIp(HttpServletRequest request) {
		System.out.println(request.getRemoteAddr()+"=="+request.getRequestURI());
		String remoteAddr = "";
        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
       }
       return remoteAddr;
    }
}
