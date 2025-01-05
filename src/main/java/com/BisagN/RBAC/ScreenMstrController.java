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
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.BisagN.dao.RoleBaseMenuDAO;
import com.BisagN.models.TB_LDAP_MODULE_MASTER;
import com.BisagN.models.TB_LDAP_SCREEN_MASTER;
import com.BisagN.models.TB_LDAP_SUBMODULE_MASTER;
import com.BisagN.validation.ValidationController;

@Controller
@RequestMapping(value = { "admin", "/", "user" })
public class ScreenMstrController {
	
	@Autowired
	private RoleBaseMenuDAO roledao;
	@Autowired
	private SessionFactory sessionFactory;
	ValidationController validation = new ValidationController();
	
	// open Screen Master page
	@RequestMapping(value = "/screen_mstUrl", method = RequestMethod.GET)
	public ModelAndView screen_mstUrl(ModelMap Mmap, HttpSession session,
			@RequestParam(value = "msg", required = false) String msg, HttpServletRequest request) {
		String roleid = session.getAttribute("roleid").toString();
		Boolean val = roledao.ScreenRedirect("screen_mstUrl", roleid);
		
		
		if (val == false) {
			return new ModelAndView("AccessTiles");
		} else {
			List<Map<String, Object>> list = roledao.ScreenSearchReport("", "");
			Mmap.put("list", list);
			Mmap.put("getModuleNameList", getModuleNameList());
			Mmap.put("getSubModuleNameList", getSubModuleNameList());
			if (request.getHeader("Referer") == null) {
				msg = "";
			}
			Mmap.put("msg", msg);
			return new ModelAndView("screen_mstTiles");
		}
	}

	// save and update Screen Master
	@RequestMapping(value = "/screen_mstAction", method = RequestMethod.POST)
	public ModelAndView screen_mstAction(@ModelAttribute("screen_mstCMD") TB_LDAP_SCREEN_MASTER p,HttpServletRequest request, ModelMap model, HttpSession session) {
		String roleid1 = session.getAttribute("roleid").toString();
		Boolean val1 = roledao.ScreenRedirect("screen_mstUrl", roleid1);
		if (val1 == false) {
			return new ModelAndView("AccessTiles");
		}
		int sc_id = 0;
		if (request.getParameter("screen_id") == "") {
			sc_id = 0;
		} else {
			sc_id = Integer.parseInt(request.getParameter("screen_id"));
		}
		Session sessionHQL = this.sessionFactory.openSession();
		String roleid = session.getAttribute("roleid").toString();
		Boolean val = roledao.ScreenRedirect("screen_mstUrl", roleid);
		if (val == false) {
			return new ModelAndView("AccessTiles");
		}
		try {
		    if (p.getScreen_name() == "") { // || p.getScreen_name().equals("")
				model.put("msg", "Please Enter Screen Name");
				return new ModelAndView("redirect:screen_mstUrl");
			} else if (validation.ScreenNameLength(p.getScreen_name()) == false) {
				model.put("msg", validation.ScreenNameMSG);
				return new ModelAndView("redirect:screen_mstUrl");
			}
			if (p.getScreen_url() == "") { // || p.getScreen_url().equals("")
				model.put("msg", "Please Enter Screen Url");
				return new ModelAndView("redirect:screen_mstUrl");
			} else if (validation.ScreenURLLength(p.getScreen_url()) == false) {
				model.put("msg", validation.ScreenURLMSG);
				return new ModelAndView("redirect:screen_mstUrl");
			}
			if (p.getModule().getId() == 0) { // || p.getScreen_module_id().equals("") getScreen_module_id()
				model.put("msg", "Please Select Module ");
				return new ModelAndView("redirect:screen_mstUrl");
			}
			if (p.getSub_module().getId() == 0) { // || p.getScreen_submodule_id().equals("") getScreen_submodule_id()
				model.put("msg", "Please Select Sub Module ");
				return new ModelAndView("redirect:screen_mstUrl");
			}
			if (!roledao.getscreenExist(p.getScreen_name(), p.getScreen_url(), p.getModule().getId(),
					p.getSub_module().getId(), request.getParameter("screen_id")).equals(false)) {
				model.put("msg", "Data Already Exist");
				return new ModelAndView("redirect:screen_mstUrl");
			}
			if (sc_id == 0) {
				p.setScreen_name(p.getScreen_name().trim());
				sessionHQL.beginTransaction();
				sessionHQL.save(p);
				sessionHQL.getTransaction().commit();
				model.put("msg", "Data Save Successfully");
			} else {
				Transaction tx = sessionHQL.beginTransaction();
				String hql = "UPDATE TB_LDAP_SCREEN_MASTER SET screen_name=:screen_name,screen_module_id=:screen_module_id,screen_submodule_id=:screen_submodule_id WHERE id=:id ";
				Query query = sessionHQL.createQuery(hql).setString("screen_name", p.getScreen_name())
						.setInteger("screen_module_id", p.getModule().getId())
						.setInteger("screen_submodule_id", p.getSub_module().getId())
						.setInteger("id", sc_id);
				int rowCount = query.executeUpdate();
				
				if (rowCount > 0) {
					//// update when change screen Module and sub module 
					String hql1 = "UPDATE TB_LDAP_ROLEMASTER SET moduleid=:moduleid,submoduleid=:submoduleid WHERE screenid=:screenid ";
					Query query1 = sessionHQL.createQuery(hql1)
							.setInteger("moduleid", p.getModule().getId())
							.setInteger("submoduleid", p.getSub_module().getId())
							.setInteger("screenid", sc_id);
					int rowCount1 = query1.executeUpdate();
					//// update when change screen Module and sub module 
					if (rowCount1 > 0) {
						model.put("msg", "Data Updated Successfully");
					}else {
						model.put("msg", "Data Not Updated Successfully");
					}
					
				} else {
					model.put("msg", "Data Not Updated Successfully");
				}
				tx.commit();
			}
		} catch (Exception e) {
			model.put("msg", "Data Save not Successfully");
		} finally {
			sessionHQL.close();
		}
		return new ModelAndView("redirect:screen_mstUrl");
	}

	// report for Screen Master
	@RequestMapping(value = "/screen_report")
	public ModelAndView screen_report(ModelMap Mmap, HttpSession session,
			@RequestParam(value = "msg", required = false) String msg, Authentication authentication,
			@RequestParam(value = "module_id1", required = false) String module_id,
			@RequestParam(value = "sub_module_id1", required = false) String sub_module_id,
			HttpServletRequest request) {

		String roleid1 = session.getAttribute("roleid").toString();
		Boolean val1 = roledao.ScreenRedirect("screen_mstUrl", roleid1);
		if (val1 == false) {
			return new ModelAndView("AccessTiles");
		}
		if (request.getHeader("Referer") == null) {
			msg = "";
		}

		Mmap.put("module_id1", module_id);
		Mmap.put("sub_module_id1", sub_module_id);
		Mmap.put("getModuleNameList", getModuleNameList());
		Mmap.put("getSubModuleNameList", getSubModuleNameList());
		List<Map<String, Object>> list = roledao.ScreenSearchReport(module_id, sub_module_id);
		if (list.size() == 0) {
			Mmap.put("msg", "Data Not Available");
		} else {
			Mmap.put("list", list);
		}
		Mmap.put("list.size()", list.size());
		return new ModelAndView("screen_mstTiles");
	}
	
	// Sub Module list
	public List<TB_LDAP_SUBMODULE_MASTER> getSubModuleNameList() {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Query q = session.createQuery("from TB_LDAP_SUBMODULE_MASTER order by id");
		@SuppressWarnings("unchecked")
		List<TB_LDAP_SUBMODULE_MASTER> list = (List<TB_LDAP_SUBMODULE_MASTER>) q.list();
		tx.commit();
		session.close();
		return list;
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
