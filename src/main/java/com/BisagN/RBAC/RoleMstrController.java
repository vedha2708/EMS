package com.BisagN.RBAC;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.BisagN.dao.RoleBaseMenuDAO;
import com.BisagN.models.Role;
import com.BisagN.models.TB_LDAP_ROLE_TYPE;
import com.BisagN.validation.ValidationController;

@Controller
@RequestMapping(value = { "admin", "/", "user" })
public class RoleMstrController {

	@Autowired
	private RoleBaseMenuDAO roledao;
	
	@Autowired
	private SessionFactory sessionFactory;

	ValidationController validation = new ValidationController();
	
	// open Role master page
	@RequestMapping(value = "/role_mstUrl", method = RequestMethod.GET)
	public ModelAndView role_mstUrl(ModelMap Mmap, HttpSession session,@RequestParam(value = "msg", required = false) String msg, HttpServletRequest request) {
		String roleid = session.getAttribute("roleid").toString();
		Boolean val = roledao.ScreenRedirect("role_mstUrl", roleid);
		if (val == false) {
			return new ModelAndView("AccessTiles");
		} else {
			List<Map<String, Object>> list = roledao.RoleSearchReport();
			Mmap.put("list", list);
			Mmap.put("getRoleType", getRoleType());
			if (request.getHeader("Referer") == null) {
				msg = "";
			}
			Mmap.put("msg", msg);
			return new ModelAndView("role_mstTiles");
		}
	}
	
	// save and update role master
	@RequestMapping(value = "/role_mstAction", method = RequestMethod.POST)
	public ModelAndView role_mstAction(@ModelAttribute("role_mstCMD") Role p, TB_LDAP_ROLE_TYPE p1,
			HttpServletRequest request, ModelMap model, HttpSession session) {
		Session sessionHQL = this.sessionFactory.openSession();
		String roleid = session.getAttribute("roleid").toString();
		Boolean val = roledao.ScreenRedirect("role_mstUrl", roleid);
		if (val == false) {
			return new ModelAndView("AccessTiles");
		}
		try {
			if (p.getRole().equals("")) {
				model.put("msg", "Please Enter Role Name");
				return new ModelAndView("redirect:role_mstUrl");
			} else if (validation.RoleLength(p.getRole()) == false) {
				model.put("msg", validation.RoleMSG);
				return new ModelAndView("redirect:role_mstUrl");
			}
			if (request.getParameter("role_type").equals("0")) {
				model.put("msg", "Please Select Role Type");
				return new ModelAndView("redirect:role_mstUrl");
			}
			if (request.getParameter("access_lvl").equals("")) {
				model.put("msg", "Please Select Access Level");
				return new ModelAndView("redirect:role_mstUrl");
			}
			if (request.getParameter("access_lvl").equals("")) {
				model.put("msg", "Please Select Access Level");
				return new ModelAndView("redirect:role_mstUrl");
			}
			if ((!p.getAccess_lvl().equals("Unit") && p.getAccess_lvl().toString() != "Unit") && !p.getAccess_lvl().equals("Depot") ) {
				if (request.getParameter("sub_access_lvl").equals("")) {
					model.put("msg", "Please Select Sub Access Level");
					return new ModelAndView("redirect:role_mstUrl");
				}
			}
			if (!roledao.getroleExist(p.getRole()).equals(false)) {
				model.put("msg", "Data Already Exist");
				return new ModelAndView("redirect:role_mstUrl");
			}
			p.setRole_url("commonDashboard");
			sessionHQL.beginTransaction();
			sessionHQL.save(p);
			sessionHQL.getTransaction().commit();
			model.put("msg", "Data saved Successfully");
		} catch (Exception e) {
			model.put("msg", "Data Save not Successfully");
		} finally {
			sessionHQL.close();
		}
		return new ModelAndView("redirect:role_mstUrl");
	}
	
	// Role list
	public List<Role> getRoleNameList() {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Query q = session.createQuery("from Role order by role");
		@SuppressWarnings("unchecked")
		List<Role> list = (List<Role>) q.list();
		System.out.println("list"+ list);
		tx.commit();
		session.close();
		return list;
	}
	
	// Role Type list
	public List<TB_LDAP_ROLE_TYPE> getRoleType() {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Query q = session.createQuery("from TB_LDAP_ROLE_TYPE  ");
		@SuppressWarnings("unchecked")
		List<TB_LDAP_ROLE_TYPE> list = (List<TB_LDAP_ROLE_TYPE>) q.list();
		tx.commit();
		session.close();
		return list;
	}
	
	@RequestMapping(value = "/getLastLoginDate", method = RequestMethod.POST)
	public @ResponseBody ArrayList<ArrayList<String>> getLastLoginDate(HttpServletRequest request,HttpSession sessionU) 
	{
		int userid =Integer.parseInt(sessionU.getAttribute("userId").toString());	
		ArrayList<ArrayList<String>> list = roledao.getLastLogin(userid);
		return list;
	}
}
