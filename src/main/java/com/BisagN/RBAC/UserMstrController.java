package com.BisagN.RBAC;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.BisagN.dao.RoleBaseMenuDAO;
import com.BisagN.models.UserLogin;
import com.BisagN.models.UserRole;
import com.BisagN.validation.PasswordValidator;
import com.BisagN.validation.ValidationController;

@Controller
@RequestMapping(value = { "admin", "/", "user" })
public class UserMstrController {
	@Autowired
	private RoleBaseMenuDAO roledao;
	@Autowired
	private SessionFactory sessionFactory;
	
	ValidationController validation = new ValidationController();
	
	@Autowired
	RoleMstrController roleMstrController;
	
	
	// open User Master page
	@RequestMapping(value = "/user_mstUrl", method = RequestMethod.GET)
	public ModelAndView user_mstUrl(ModelMap Mmap, HttpSession session,
			@RequestParam(value = "msg", required = false) String msg, HttpServletRequest request) {
		String roleid = session.getAttribute("roleid").toString();

		Boolean val = roledao.ScreenRedirect("user_mstUrl", roleid);
		if (val == false) {
			return new ModelAndView("AccessTiles");
		} else {
			if (request.getHeader("Referer") == null) {
				msg = "";
			}
			Mmap.put("msg", msg);
			Mmap.put("getRoleNameList", roleMstrController.getRoleNameList());
		}
		return new ModelAndView("user_mstTiles");
	}

	// save and update User Master page
	@RequestMapping(value = "/user_mstAction", method = RequestMethod.POST)
	public ModelAndView user_mstAction(@ModelAttribute("user_mstCMD") UserLogin p, HttpServletRequest request,
			ModelMap model, HttpSession session) {
		String username = session.getAttribute("username").toString();
		String pass = request.getParameter("user_password");
		String name = request.getParameter("user_name");
		String re_pass = request.getParameter("user_re_password");
		int roll = Integer.parseInt(request.getParameter("user_role_id"));
		String army_no = request.getParameter("army_no");
		String role = request.getParameter("user_role_id");

		Session sessionHQL = this.sessionFactory.openSession();
		String roleid = session.getAttribute("roleid").toString();
		Boolean val = roledao.ScreenRedirect("user_mstUrl", roleid);
		if (val == false) {
			return new ModelAndView("AccessTiles");
		}
		try {
			boolean pass_valid = validate(pass);
			if (pass_valid == false) {
				model.put("msg", "Password pattern doesn't match.");
				return new ModelAndView("redirect:user_mstUrl");
			}
			if (p.getLogin_name() == "") {
				model.put("msg", "Please Enter User Name.");
				return new ModelAndView("redirect:user_mstUrl");
			} else if (validation.LoginNameLength(p.getLogin_name()) == false) {
				model.put("msg", validation.LoginNameMSG);
				return new ModelAndView("redirect:user_mstUrl");
			}
			if (name == "" || name.equals("")) {
				model.put("msg", "Please Enter User ID.");
				return new ModelAndView("redirect:user_mstUrl");
			} else if (validation.UserNameLength(name) == false) {
				model.put("msg", validation.UserNameMSG);
				return new ModelAndView("redirect:user_mstUrl");
			}
			if (army_no == "" || army_no.equals("")) {
				model.put("msg", "Please Enter Army No.");
				return new ModelAndView("redirect:user_mstUrl");
			} else if (validation.ArmyNoLength(army_no) == false) {
				model.put("msg", validation.ArmyNoMSG);
				return new ModelAndView("redirect:user_mstUrl");
			}
			if (pass == "" || pass.equals("")) {
				model.put("msg", "Please Enter User ID.");
				return new ModelAndView("redirect:user_mstUrl");
			} else if (validation.PasswordLength(pass) == false) {
				model.put("msg", validation.PasswordMSG);
				return new ModelAndView("redirect:user_mstUrl");
			}
			else if(!PasswordValidator.validate(pass)) { // Check New Password Pattern "((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{8,28})"
				model.put("msg",validation.PasswordPatternMSG);
				return new ModelAndView("redirect:changePassword");
			}
			
			if (!pass.trim().equals(re_pass.trim())) {
				model.put("msg", "Password and Re-Password didn't match");
				return new ModelAndView("redirect:user_mstUrl");
			}
			if (role == "" || role.equals("")) {
				model.put("msg", "Please Select Role");
				return new ModelAndView("redirect:user_mstUrl");
			}
			if (!roledao.getuserExist(name).equals(false)) {
				model.put("msg", "Data Already Exist");
				return new ModelAndView("redirect:user_mstUrl");
			} else {
				String modifydate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
				BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
				String hashedPassword = passwordEncoder.encode(pass);
				System.out.println(hashedPassword);
				p.setPassword(hashedPassword);
				p.setUserName(name);
				p.setEnabled(1);
				p.setAccountNonExpired(1);
				p.setAccountNonLocked(1);
				p.setCredentialsNonExpired(1);
				p.setAc_dc_date(modifydate);
				//p.setArmy_no(army_no);
				p.setCreated_on(new Date());
				p.setCreated_by(username);
				UserRole role_tbl = new UserRole();
				sessionHQL.beginTransaction();

				int did = (Integer) sessionHQL.save(p);
				role_tbl.setRoleId(roll);
				role_tbl.setUserId(did);
				sessionHQL.save(role_tbl);
				sessionHQL.getTransaction().commit();
				sessionHQL.close();

				roledao.userinsertdata("insert", did, roll);
				model.put("msg", "Data saved Successfully");
			}
		} catch (Exception e) {
			model.put("msg", "Data Save not Successfully");
		}
		return new ModelAndView("redirect:user_mstUrl");
	}
	

	String access_lvl1 = "";
	String user_name1 = "";
	
	// open Search User page
	@RequestMapping(value = "/search_user_mstUrl", method = RequestMethod.GET)
	public ModelAndView search_user_mstUrl(ModelMap Mmap, HttpSession session,
			@RequestParam(value = "msg", required = false) String msg, HttpServletRequest request) {
		String roleid = session.getAttribute("roleid").toString();
		Boolean val = roledao.ScreenRedirect("search_user_mstUrl", roleid);
		if (val == false) {
			return new ModelAndView("AccessTiles");
		} else {
			if (request.getHeader("Referer") == null) {
				msg = "";
			}
			Mmap.put("msg", msg);
			Mmap.put("getRoleNameList", roleMstrController.getRoleNameList());
			Mmap.put("access_lvl1", access_lvl1);
			Mmap.put("user_name", user_name1);
			return new ModelAndView("search_user_mstTiles");
		}
	}
	
	// Search User Redirect Url
	@RequestMapping(value = "/search_user_by_role", method = RequestMethod.POST)
	public ModelAndView search_user_by_role(ModelMap Mmap, HttpSession session,
			@RequestParam(value = "msg", required = false) String msg,
			@RequestParam(value = "access_lvl1", required = false) String access_lvl,
			@RequestParam(value = "user_name", required = false) String user_name) {

		access_lvl1 = access_lvl;
		user_name1 = user_name;
		return new ModelAndView("redirect:search_user_mstUrl");
	}
	
	// Search User Data
//	@RequestMapping(value = "/getUserReportList",method=RequestMethod.GET)
//	public @ResponseBody DatatablesResponse<Map<String, Object>> getUserReportList(@DatatablesParams DatatablesCriterias criterias, HttpSession session, HttpServletRequest request) {
//		String qry = "";
//		String roleSubAccess = session.getAttribute("roleSubAccess").toString();
//		qry = "";
//		if (access_lvl1.equals("All") && !access_lvl1.equals("Username")) {
//			qry += "";
//		}
//		if (!access_lvl1.equals("All") && access_lvl1.equals("Username")) {
//			qry += " and l.username='" + user_name1 + "' ";
//		}
//		DataSet<Map<String, Object>> dataSet = roledao.DatatablesCriteriasUserreport(criterias, qry, roleSubAccess);
//		return DatatablesResponse.build(dataSet, criterias);
//	}
	
	// update User Master Url
	@RequestMapping(value = "/update_user_mstUrl")
	public ModelAndView update_user_mstUrl(ModelMap Mmap, @ModelAttribute("updateid") int updateid,
			@RequestParam(value = "msg", required = false) String msg, HttpServletRequest request,
			HttpSession session) {
		
		if (request.getHeader("Referer") == null) {
			msg = "";
			return new ModelAndView("AccessTiles");
		}
		
		List<Map<String, Object>> list = roledao.getRole(updateid);
		Mmap.put("getRole", list);
		Mmap.put("getRoleNameList", roleMstrController.getRoleNameList());
		Mmap.put("edit_User_MstCMD", roledao.getUserLoginbyid(updateid));
		Mmap.put("msg", msg);
		return new ModelAndView("Edit_user_mstTiles");
	}

	// update User Master Action
	@RequestMapping(value = "/edit_User_Mst_Action")
	@ResponseBody
	public ModelAndView edit_User_Mst_Action(@ModelAttribute("edit_User_MstCMD") UserLogin updateid,
			HttpServletRequest request, ModelMap model, HttpSession session) {
		try {
			int roll = Integer.parseInt(request.getParameter("user_role_id"));
			String access_lve1 = request.getParameter("access_lve1");
			String sub_access_lve1 = request.getParameter("sub_access_lve1");
			String roll1 = request.getParameter("user_role_id");
			if (updateid.getLogin_name() == "") {
				model.put("msg", "Please Enter User Name.");
				model.put("updateid", updateid.getUserId());
				return new ModelAndView("redirect:update_user_mstUrl");
			}
			boolean pass_valid = validate(request.getParameter("password"));
			if (pass_valid == false) {
				model.put("msg", "Password pattern doesn't match.");
				model.put("updateid", updateid.getUserId());
				return new ModelAndView("redirect:update_user_mstUrl");
			}
			if (request.getParameter("password") == "" || request.getParameter("password").equals("")) {
				model.put("msg", "Please Enter Password.");
				model.put("updateid", updateid.getUserId());
				return new ModelAndView("redirect:update_user_mstUrl");
			}
			if (!request.getParameter("password").trim().equals(request.getParameter("re_password").trim())) {
				model.put("msg", "Password and Re-Password didn't match");
				model.put("updateid", updateid.getUserId());
				return new ModelAndView("redirect:update_user_mstUrl");
			}
			if (roll1 == "" || roll1.equals("")) {
				model.put("msg", "Please Select Role");
				model.put("updateid", updateid.getUserId());
				return new ModelAndView("redirect:update_user_mstUrl");
			}
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String hashedPassword = passwordEncoder.encode(updateid.getPassword());
			updateid.setPassword(hashedPassword);
			model.put("updateid", updateid.getUserId());
			model.put("msg", roledao.UpdateUserMst(updateid, roll,access_lve1, sub_access_lve1));
		} catch (Exception e) {

		}
		return new ModelAndView("redirect:search_user_mstUrl");
	}
	
	// Start User Activation or Deactivation Status
	// open User Active/Deactive Status Url
	String status1 = "";
	String login_name1 = "";
	@RequestMapping(value = "/user_statusUrl", method = RequestMethod.GET)
	public ModelAndView user_statusUrl(ModelMap Mmap, HttpSession session,
			@RequestParam(value = "msg", required = false) String msg, HttpServletRequest request) {
		String roleid = session.getAttribute("roleid").toString();
		Boolean val = roledao.ScreenRedirect("user_statusUrl", roleid);
		if (val == false) {
			return new ModelAndView("AccessTiles");
		} else {
			Mmap.put("getRoleType", roleMstrController.getRoleType());
			Mmap.put("status1", status1);
			Mmap.put("login_name1", login_name1);
			if (request.getHeader("Referer") == null) {
				msg = "";
			}
			Mmap.put("msg", msg);
			return new ModelAndView("user_statusTiles");
		}
	}
	// redirect User Active/Deactive Status Url
	@RequestMapping(value = "/search_user_statusReport")
	public ModelAndView search_user_statusReport(ModelMap Mmap, HttpSession session,
			@RequestParam(value = "msg", required = false) String msg, Authentication authentication,
			@RequestParam(value = "status1", required = false) String status,
			@RequestParam(value = "login_name1", required = false) String login_name, HttpServletRequest request) {
		String roleid = session.getAttribute("roleid").toString();
		Boolean val = roledao.ScreenRedirect("user_statusUrl", roleid);
		if (val == false) {
			return new ModelAndView("AccessTiles");
		}
		if (request.getHeader("Referer") == null) {
			return new ModelAndView("AccessTiles");
		}
		status1 = status;
		login_name1 = login_name;
		return new ModelAndView("redirect:user_statusUrl");
	}
	// get User Active/Deactive Status Data
//	@RequestMapping(value = "/getUserReportactiveList1")
//	public @ResponseBody DatatablesResponse<Map<String, Object>> getUserReportactiveList1(
//			@DatatablesParams DatatablesCriterias criterias, HttpSession session, HttpServletRequest request) {
//		String qry = "";
//		String roleSubAccess = session.getAttribute("roleSubAccess").toString();
//		qry += "";
//		if (status1 != "") {
//			qry += "d.enabled = '" + status1 + "'";
//		}
//		if (login_name1 != "") {
//			qry += "and d.userName = '" + login_name1 + "'";
//		}
//		DataSet<Map<String, Object>> dataSet = roledao.DatatablesCriteriasActiveUserreport(criterias, qry,
//				roleSubAccess, status1);
//		return DatatablesResponse.build(dataSet, criterias);
//	}
	// End User Activation or Deactivation Status
	
	// for User Activation
	@RequestMapping(value = "/ActiveDataURl",method =RequestMethod.POST)
	public ModelAndView ActiveDataURl(ModelMap Mmap, @RequestParam(value = "acid1", required = false) String Activeid,
			@RequestParam(value = "status1", required = false) String status, HttpServletRequest request) {
		Mmap.put("status1", status);
		Mmap.put("login_name1", login_name1);
		Mmap.put("msg", roledao.getActiveData(Activeid));
		return new ModelAndView("redirect:search_user_statusReport");
	}

	// for User Deactivation
	@RequestMapping(value = "/DeactiveDataURl",method=RequestMethod.POST)
	public ModelAndView DeactiveDataURl(ModelMap Mmap,
			@RequestParam(value = "dcid1", required = false) String Deactiveid,
			@RequestParam(value = "status1", required = false) String status) throws SQLException {
		Mmap.put("status1", status);
		Mmap.put("login_name1", login_name1);
		Mmap.put("msg", roledao.getDeactiveData(Deactiveid));
		return new ModelAndView("redirect:search_user_statusReport");
	}
	

	// status of inactive user Url
	@RequestMapping(value = "/status_of_inactive_user_reportUrl", method = RequestMethod.GET)
	public ModelAndView status_of_inactive_user_reportUrl(ModelMap Mmap, HttpSession session,
			@RequestParam(value = "msg", required = false) String msg, HttpServletRequest request)
			throws ParseException {
		if (request.getHeader("Referer") == null) {
			msg = "";
		}
		String roleid = session.getAttribute("roleid").toString();
		Boolean val = roledao.ScreenRedirect("status_of_inactive_user_reportUrl", roleid);
		if (val == false) {
			return new ModelAndView("AccessTiles");
		} else {
			List<Map<String, Object>> list = roledao.getReportStatusOfInactiveUserList();
			Mmap.put("list", list);
			return new ModelAndView("status_of_inactive_user_reportTiles");
		}
	}
	
	
	
	////////////////PASSWORD_PATTERN //////////
	private Pattern pattern;
	private Matcher matcher;
	
	private static final String PASSWORD_PATTERN = "((?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[$#^@&%_.~!*])(?!.*\\s).{8,28})";
	
	public UserMstrController() {
	pattern = Pattern.compile(PASSWORD_PATTERN);
	}
	
	public boolean validate(final String password) {
	matcher = pattern.matcher(password);
	return matcher.matches();
	}
	
	@RequestMapping(value = "/getUserReportList1", method = RequestMethod.POST)
	 public @ResponseBody List<Map<String, Object>> getUserReportList1(int startPage,String pageLength,String Search,String orderColunm,String orderType,String user_name1,HttpSession sessionUserId) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException{
		String roleid = sessionUserId.getAttribute("roleid").toString();
		String qry = "";
		String roleSubAccess = sessionUserId.getAttribute("roleSubAccess").toString();
		if (access_lvl1.equals("All") && !access_lvl1.equals("Username")) {
			qry += "";
		}
		if (!access_lvl1.equals("All") && access_lvl1.equals("Username")) {
			qry += " and l.username='" + user_name1 + "' ";
		}
		
	 	return roledao.getUserReportList1(startPage,pageLength,Search,orderColunm,orderType,qry,roleSubAccess,sessionUserId,roleid);
		}

  @RequestMapping(value = "/getUserReportListTotalCount", method = RequestMethod.POST)
	public @ResponseBody long getUserReportListTotalCount(HttpSession sessionUserId,String Search){
  	String roleid = sessionUserId.getAttribute("roleid").toString();
		String qry = "";
		String roleSubAccess = sessionUserId.getAttribute("roleSubAccess").toString();
		
  	if (access_lvl1.equals("All") && !access_lvl1.equals("Username")) {
			qry += "";
		}
		if (!access_lvl1.equals("All") && access_lvl1.equals("Username")) {
			qry += " and l.username='" + user_name1 + "' ";
		}
			 return roledao.getUserReportListTotalCount1(Search,qry);
	}
  
//Datatable With SQL
	@RequestMapping(value = "/searchUserStatus_getFilteredDataList_SQL", method = RequestMethod.POST)
	public @ResponseBody ArrayList<ArrayList<String>> searchUserStatus_getFilteredDataList_SQL(HttpServletRequest request,int startPage,int pageLength,String Search,String orderColunm,String orderType,HttpSession sessionUserId,String access_lvl,String login_name) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException{
		status1= access_lvl;
		return roledao.SearchUserStatus_DataTableMockJaxList_SQL(startPage,pageLength,Search,orderColunm,orderType,access_lvl,login_name,"",sessionUserId);	
	}
	
	@RequestMapping(value = "/searchUserStatus_getTotalCount_SQL", method = RequestMethod.POST)
	public @ResponseBody long searchUserStatus_getTotalCount_SQL(HttpServletRequest request,HttpSession sessionUserId,String Search,String access_lvl,String login_name){
		status1= access_lvl;
		return roledao.SearchUserStatus_DataTableMockJaxTotalCount_SQL(Search, access_lvl,login_name);
		
	}
}
