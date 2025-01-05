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
import com.BisagN.models.TB_LDAP_SUBMODULE_MASTER;
import com.BisagN.validation.ValidationController;

@Controller
@RequestMapping(value = { "admin", "/", "user" })
public class SubModuleMstrController {
	
	@Autowired
	private RoleBaseMenuDAO roledao;
	@Autowired
	private SessionFactory sessionFactory;
	
	ValidationController validation = new ValidationController();
	@Autowired
	ModuleMstrController module;
	
	// Sub Module Master Url
	@RequestMapping(value = "/sub_module_mstUrl", method = RequestMethod.GET)
	public ModelAndView sub_module_mstUrl(ModelMap Mmap, HttpSession session,
			@RequestParam(value = "msg", required = false) String msg, HttpServletRequest request) {
		String roleid = session.getAttribute("roleid").toString();
		Boolean val = roledao.ScreenRedirect("sub_module_mstUrl", roleid);
		if (val == false) {
			return new ModelAndView("AccessTiles");
		} 
		try {
			
		
			Mmap.put("getModuleNameList", module.getModuleNameList());
			List<Map<String, Object>> list = roledao.SubModuleSearchReport();
			Mmap.put("list", list);
			if (request.getHeader("Referer") == null) {
				msg = "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
			Mmap.put("msg", msg);
			return new ModelAndView("sub_module_mstTiles");
		
	}

	// save and update Sub Module Master Action
	@RequestMapping(value = "/sub_module_mstAction", method = RequestMethod.POST)
	public ModelAndView sub_module_mstAction(@ModelAttribute("sub_module_mstCMD") TB_LDAP_SUBMODULE_MASTER p,
			HttpServletRequest request, ModelMap model, HttpSession session) {

		Session sessionHQL = this.sessionFactory.openSession();

		String roleid = session.getAttribute("roleid").toString();
		Boolean val = roledao.ScreenRedirect("sub_module_mstUrl", roleid);
		if (val == false) {
			return new ModelAndView("AccessTiles");
		}
		try {
			if (p.getSubmodule_name() == "") {
				model.put("msg", "Please Enter Sub Module Name");
				return new ModelAndView("redirect:sub_module_mstUrl");
			} else if (validation.SubModuleNameLength(p.getSubmodule_name()) == false) {
				model.put("msg", validation.SubModuleNameMSG);
				return new ModelAndView("redirect:sub_module_mstUrl");
			}

			if (p.getModule().getId() == 0) {
				model.put("msg", "Please Enter Module Name");
				return new ModelAndView("redirect:sub_module_mstUrl");
			}
			int sm_id = 0;

			if (request.getParameter("submodule_id") == "") {
				sm_id = 0;
			} else {
				sm_id = Integer.parseInt(request.getParameter("submodule_id"));
			}

			if (sm_id == 0) {

				if (!roledao.getsubmoduleExist(p.getSubmodule_name(), p.getModule().getId(), sm_id).equals(false)) {
					model.put("msg", "Data Already Exist");
					return new ModelAndView("redirect:sub_module_mstUrl");
				}

				// String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
				sessionHQL.beginTransaction();
				p.setSubmodule_name(p.getSubmodule_name().trim());
				sessionHQL.save(p);
				sessionHQL.getTransaction().commit();
				model.put("msg", "Data saved Successfully");
			} else {

				Transaction tx = sessionHQL.beginTransaction();
				String hql = "UPDATE TB_LDAP_SUBMODULE_MASTER SET submodule_name=:submodule_name_n , module_id=:module_id_n  WHERE id =:id ";
				Query query = sessionHQL.createQuery(hql).setString("submodule_name_n", p.getSubmodule_name())
						.setInteger("module_id_n", p.getModule().getId()).setInteger("id", sm_id);
				int rowCount = query.executeUpdate();
				
				if (rowCount > 0) {
					//when change module 
						String hql1 = "UPDATE TB_LDAP_ROLEMASTER SET moduleid=:moduleid  WHERE submoduleid =:submoduleid ";
						Query query1 = sessionHQL.createQuery(hql1)
								.setInteger("moduleid", p.getModule().getId()).setInteger("submoduleid", sm_id);
						int rowCount1 = query1.executeUpdate();
					//when change module 
					if (rowCount1 > 0) {
						model.put("msg", "Updated Successfully");
					}else {
						model.put("msg", "Updated not Successfully");
					}
				} else {
					model.put("msg", "Updated not Successfully");
				}
				tx.commit();
			}

		} catch (Exception e) {
			model.put("msg", "Data Not Saved Successfully");
		} finally {
			sessionHQL.close();
		}
		return new ModelAndView("redirect:sub_module_mstUrl");
	}
}
