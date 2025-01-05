package com.BisagN.RBAC;

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
import org.springframework.web.servlet.ModelAndView;

import com.BisagN.dao.RoleBaseMenuDAO;
import com.BisagN.models.TB_LDAP_MODULE_MASTER;
import com.BisagN.validation.ValidationController;

@Controller
@RequestMapping(value = { "admin", "/", "user" })
public class ModuleMstrController {
	
	@Autowired
	private RoleBaseMenuDAO roledao;
	@Autowired
	private SessionFactory sessionFactory;
	
	ValidationController validation = new ValidationController();
	// Module Master Url
	@RequestMapping(value = "/module_mstUrl", method = RequestMethod.GET)
	public ModelAndView module_mstUrl(ModelMap Mmap, HttpSession session,
			@RequestParam(value = "msg", required = false) String msg, HttpServletRequest request) {
		String roleid = session.getAttribute("roleid").toString();
		Boolean val = roledao.ScreenRedirect("module_mstUrl", roleid);
		if (val == false) {
			return new ModelAndView("AccessTiles");
		} else {
			List<Map<String, Object>> list = roledao.ModuleSearchReport();
			Mmap.put("list", list);
			if (request.getHeader("Referer") == null) {
				msg = "";
			}
			Mmap.put("msg", msg);
			return new ModelAndView("module_mstTiles");
		}
	}

	// save and update Module Master Action
	@RequestMapping(value = "/module_mstAction", method = RequestMethod.POST)
	public ModelAndView module_mstAction(@ModelAttribute("module_mstCMD") TB_LDAP_MODULE_MASTER p,
			HttpServletRequest request, ModelMap model, HttpSession session) {
		String roleid = session.getAttribute("roleid").toString();
		Boolean val1 = roledao.ScreenRedirect("module_mstUrl", roleid);
		if (val1 == false) {
			return new ModelAndView("AccessTiles");
		}

		String module1 = request.getParameter("module_name");
		String module2 = request.getParameter("module_old_name");
		// Boolean val = true;
		Session sessionHQL = this.sessionFactory.openSession();
		try {
			if (module1 == "" || module1.equals("")) {
				model.put("msg", "Please Enter Module Name");
				return new ModelAndView("redirect:module_mstUrl");
			} else if (validation.ModuleNameLength(module1) == false) {
				model.put("msg", validation.ModuleNameMSG);
				return new ModelAndView("redirect:module_mstUrl");
			}
			if (!roledao.getmoduleExist(module1).equals(false)) {
				model.put("msg", "Data Already Exist");
				return new ModelAndView("redirect:module_mstUrl");
			}
			if (module2 == "") {
				p.setModule_name(module1.trim());
				sessionHQL.beginTransaction();
				sessionHQL.save(p);
				sessionHQL.getTransaction().commit();
				model.put("msg", "Data saved Successfully");
			} else {
				Transaction tx = sessionHQL.beginTransaction();
				String hql = "UPDATE TB_LDAP_MODULE_MASTER SET module_name=:module_name  WHERE id=:module_name2 ";
				Query query = sessionHQL.createQuery(hql).setString("module_name", module1).setInteger("module_name2",
						Integer.parseInt(module2));
				int rowCount = query.executeUpdate();
				tx.commit();
				if (rowCount > 0) {
					model.put("msg", "Data Updated Successfully");
				} else {
					model.put("msg", "Data Not Updated Successfully");
				}
			}
		} catch (Exception e) {
			model.put("msg", "Data Save not Successfully");
		} finally {
			sessionHQL.close();
		}

		return new ModelAndView("redirect:module_mstUrl");
	}
	
	// Module list
	public List<TB_LDAP_MODULE_MASTER> getModuleNameList() {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Query q = session.createQuery("from TB_LDAP_MODULE_MASTER  where id > 0 order by id  ");
		@SuppressWarnings("unchecked")
		List<TB_LDAP_MODULE_MASTER> list = (List<TB_LDAP_MODULE_MASTER>) q.list();
		tx.commit();
		session.close();
		return list;
	}
}
