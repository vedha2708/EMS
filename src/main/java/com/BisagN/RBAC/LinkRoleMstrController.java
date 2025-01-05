package com.BisagN.RBAC;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.BisagN.dao.HexatoAsciiDAO;
import com.BisagN.dao.HexatoAsciiDAOImpl;
import com.BisagN.dao.RoleBaseMenuDAO;
import com.BisagN.models.TB_LDAP_MODULE_MASTER;
import com.BisagN.models.TB_LDAP_ROLEMASTER;
import com.BisagN.models.TB_LDAP_SCREEN_MASTER;
import com.BisagN.models.TB_LDAP_SUBMODULE_MASTER;
import com.BisagN.validation.ValidationController;
//import com.persistance.util.HibernateUtil;
//import com.persistance.util.HibernateUtilNA;

@Controller
@RequestMapping(value = { "admin", "/", "user" })
public class LinkRoleMstrController {
	
	@Autowired
	private RoleBaseMenuDAO roledao;
	ValidationController validation = new ValidationController();
	@Autowired
	RoleMstrController roleMstrController;
	HexatoAsciiDAO hex_asciiDao = new HexatoAsciiDAOImpl();
	
	@Autowired
	//@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	
	// open Link Role Master page
	@RequestMapping(value = "/rolemstnewUrl", method = RequestMethod.GET)
	public ModelAndView rolemstnewUrl(ModelMap Mmap, HttpSession session,
			@RequestParam(value = "msg", required = false) String msg, HttpServletRequest request) {
		String roleid = session.getAttribute("roleid").toString();
		Boolean val = roledao.ScreenRedirect("rolemstnewUrl", roleid);
		try {
			
	
		if (val == false) {
			return new ModelAndView("AccessTiles");
		}
		if (request.getHeader("Referer") == null) {
			msg = "";
		}
		Mmap.put("msg", msg);
		Mmap.put("getRoleNameList", roleMstrController.getRoleNameList());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("roleTiles");
	}

	// save and update Link Role Master
	@RequestMapping(value = "/roleAction", method = RequestMethod.POST)
	public ModelAndView roleAction(@ModelAttribute("roleCMD") TB_LDAP_ROLEMASTER p, HttpServletRequest request,
			ModelMap model, HttpSession session) throws ParseException {
		String roleid1 = session.getAttribute("roleid").toString();
		Boolean val = roledao.ScreenRedirect("rolemstnewUrl", roleid1);
		if (val == false) {
			return new ModelAndView("AccessTiles");
		}
		try {
			String username = session.getAttribute("username").toString();
			DateFormat formatter = new SimpleDateFormat("dd-mm-yyyy");
			String creadtedate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
			Date creadtedate1 = null;
			creadtedate1 = formatter.parse(creadtedate);
			String roleid = request.getParameter("roleid");
			String module = request.getParameter("modulehid");
			String submodule = request.getParameter("submodulehid");
			String screen = request.getParameter("screenhid");
			String screen_old = request.getParameter("screenhid_old");
			String module1 = null;
			String submodule1 = null;
			String screen1 = null;

			// String[] module_s = module.split(",");
			// String[] submodule_s = submodule.split(",");
			String[] screen_s = screen.split(",");
			String[] screen_s_old = screen_old.split(",");

			TB_LDAP_MODULE_MASTER md = new TB_LDAP_MODULE_MASTER();
			TB_LDAP_SUBMODULE_MASTER smd = new TB_LDAP_SUBMODULE_MASTER();
			TB_LDAP_SCREEN_MASTER sc = new TB_LDAP_SCREEN_MASTER();

			if (roleid == "0" || roleid.equals("0")) {
				model.put("msg", "Please Select Role.");
				return new ModelAndView("redirect:rolemstnewUrl");
			}
			if (module == "" || module.equals("")) {
				model.put("msg", "Please Select Module.");
				return new ModelAndView("redirect:rolemstnewUrl");
			}
			if (submodule == "" || submodule.equals("")) {
				model.put("msg", "Please Select Sub Module.");
				return new ModelAndView("redirect:rolemstnewUrl");
			}
			if (screen == "" || screen.equals("")) { // && screen_old.equals("")
				model.put("msg", "Please Select Screen.");
				return new ModelAndView("redirect:rolemstnewUrl");
			}
			if (screen_old.equals("")) {
				for (int i = 0; i < screen_s.length; i++) {
					String[] screen_s1 = screen_s[i].split("_");
					for (int j = 0; j < screen_s1.length; j++) {

						if (j == 0) {
							module1 = screen_s1[j];
						} else if (j == 1) {
							submodule1 = screen_s1[j];
						} else
							screen1 = screen_s1[j];
					}

					Boolean v = roledao.getlinkroleExist(Integer.parseInt(module1), Integer.parseInt(submodule1),
							Integer.parseInt(screen1), Integer.parseInt(roleid));
					if (v.equals(false)) {

						/*
						 * p.setModuleid(Integer.parseInt(module1));
						 * p.setSubmoduleid(Integer.parseInt(submodule1));
						 * p.setScreenid(Integer.parseInt(screen1));
						 */

						// TB_LDAP_MODULE_MASTER md = new TB_LDAP_MODULE_MASTER();
						md.setId(Integer.parseInt(module1.trim()));
						p.setModule(md);

						// TB_LDAP_SUBMODULE_MASTER smd = new TB_LDAP_SUBMODULE_MASTER();
						smd.setId(Integer.parseInt(submodule1.trim()));
						p.setSub_module(smd);

						// TB_LDAP_SCREEN_MASTER sc = new TB_LDAP_SCREEN_MASTER();
						sc.setId(Integer.parseInt(screen1.trim()));
						p.setScreen(sc);

						p.setRoleid(Integer.parseInt(roleid.trim()));
						p.setCreation_by(username);
						p.setCreation_date(creadtedate1);

						Session sessionHQL = this.sessionFactory.openSession();
						sessionHQL.beginTransaction();
						sessionHQL.save(p);
						sessionHQL.getTransaction().commit();
						sessionHQL.close();
						model.put("msg", "Data saved Successfully");
					}
				}
			} else {
				ArrayList<String> al1 = new ArrayList<String>();
				ArrayList<String> al2 = new ArrayList<String>();

				for (int i = 0; i < screen_s.length; i++) {
					al1.add(screen_s[i]);
				}
				for (int i = 0; i < screen_s_old.length; i++) {
					al2.add(screen_s_old[i]);
				}

				if (al1.equals(al2)) {
					model.put("msg", "Data Already Exist");
				} else {
					for (String newval : screen_s) {
						if (al2.contains(newval)) {
							al2.remove(newval);
							al1.remove(newval);
						}
					}

					String add_list = al1.toString();
					String del_list = al2.toString();

					add_list = add_list.replace("[", "").replace("]", "").trim();
					del_list = del_list.replace("[", "").replace("]", "").trim();

					if (!add_list.equals("")) {

						String[] s3 = add_list.split(",");

						for (int i = 0; i < s3.length; i++) {

							String[] screen_s1 = s3[i].split("_");

							for (int j = 0; j < screen_s1.length; j++) {

								if (j == 0) {
									module1 = screen_s1[j];
								} else if (j == 1) {
									submodule1 = screen_s1[j];
								} else
									screen1 = screen_s1[j];

							}
							Boolean v = roledao.getlinkroleExist(Integer.parseInt(module1.trim()),
									Integer.parseInt(submodule1.trim()), Integer.parseInt(screen1.trim()),
									Integer.parseInt(roleid.trim()));
							if (v.equals(false)) {
								md.setId(Integer.parseInt(module1.trim()));
								p.setModule(md);

								smd.setId(Integer.parseInt(submodule1.trim()));
								p.setSub_module(smd);

								sc.setId(Integer.parseInt(screen1.trim()));
								p.setScreen(sc);

								p.setRoleid(Integer.parseInt(roleid.trim()));
								p.setCreation_by(username);
								p.setCreation_date(creadtedate1);

								Session sessionHQL = this.sessionFactory.openSession();
								sessionHQL.beginTransaction();
								sessionHQL.save(p);
								sessionHQL.getTransaction().commit();
								sessionHQL.close();

							}
						}
					}

					if (!del_list.equals("")) {

						String[] s3 = del_list.split(",");

						for (int i = 0; i < s3.length; i++) {

							String[] screen_s1 = s3[i].split("_");

							for (int j = 0; j < screen_s1.length; j++) {

								if (j == 0) {
									module1 = screen_s1[j];
								} else if (j == 1) {
									submodule1 = screen_s1[j];
								} else
									screen1 = screen_s1[j];

							}
							Integer.parseInt(roleid);

							Session session1 = this.sessionFactory.openSession();
							Transaction tx = session1.beginTransaction();
							String hql = "DELETE FROM TB_LDAP_ROLEMASTER where moduleid=:moduleid  and submoduleid=:submoduleid and  screenid=:screenid and roleid=:roleid";
							Query query = session1.createQuery(hql)
									.setInteger("moduleid", Integer.parseInt(module1.trim()))
									.setInteger("submoduleid", Integer.parseInt(submodule1.trim()))
									.setInteger("screenid", Integer.parseInt(screen1.trim()))
									.setInteger("roleid", Integer.parseInt(roleid.trim()));
							query.executeUpdate();
							tx.commit();
							session1.close();
						}
					}
					model.put("msg", "Updated Successfully");
				}

			}
		} catch (Exception e) {
			model.put("msg", e);
		}
		return new ModelAndView("redirect:rolemstnewUrl");
	}
	
	// For role by module in rolemaster table
		@RequestMapping(value = "/getrolebymodule", method = RequestMethod.POST)
		public @ResponseBody List<String> getrolebymodule(int roleid, HttpSession sessionUserId) {
			Session session = this.sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			String nrQry = "select distinct module.id from TB_LDAP_ROLEMASTER where roleid=:roleid and module.id != 0  order by module.id";
			Query q = session.createQuery(nrQry);
			q.setParameter("roleid", roleid);
			@SuppressWarnings("unchecked")
			List<String> list = (List<String>) q.list();
			tx.commit();
			session.close();
			return list;
		}

		// For role and module by sub_module in rolemaster table
		@RequestMapping(value = "/getrolemodulebysubmod", method = RequestMethod.POST)
		public @ResponseBody List<String> getrolemodulebysubmod(int roleid, int moduleid, HttpSession sessionUserId) {
			Session session = this.sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			String nrQry = "select distinct sub_module.id from TB_LDAP_ROLEMASTER where roleid=:roleid and module.id != 0 and sub_module.id != 0 and module.id=:moduleid  order by sub_module.id";
			Query q = session.createQuery(nrQry);
			q.setParameter("roleid", roleid);
			q.setParameter("moduleid", moduleid);
			@SuppressWarnings("unchecked")
			List<String> list = (List<String>) q.list();
			tx.commit();
			session.close();
			return list;
		}

		// For role and module by sub_module in rolemaster table
		@RequestMapping(value = "/getrolemodulesubmodbyscreen", method = RequestMethod.POST)
		public @ResponseBody List<String> getrolemodulesubmodbyscreen(int moduleid, int submoduleid, int role_id,
				HttpSession sessionUserId) {
			Session session = this.sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			String nrQry = "";
			nrQry = "select distinct screen.id from TB_LDAP_ROLEMASTER where roleid=:role_id and module.id != 0 and sub_module.id != 0 and module.id=:moduleid and sub_module.id =:submoduleid order by screenid ";
			Query q = session.createQuery(nrQry);
			q.setParameter("role_id", role_id);
			q.setParameter("moduleid", moduleid);
			q.setParameter("submoduleid", submoduleid);
			@SuppressWarnings("unchecked")
			List<String> list = (List<String>) q.list();
			tx.commit();
			session.close();
			return list;
		}
		// report Submodule list
		

		// module list for link role master
		@RequestMapping(value = "/getModuleNameListlink", method = RequestMethod.POST)
		public @ResponseBody List<Map<String, Object>> getModuleNameListlink(HttpSession sessionUserId) {
			Session session = this.sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			Query q = session.createQuery("from TB_LDAP_MODULE_MASTER where id > 0");
			@SuppressWarnings("unchecked")
			List<TB_LDAP_MODULE_MASTER> list = (List<TB_LDAP_MODULE_MASTER>) q.list();
			tx.commit();

			List<Map<String, Object>> l1 = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> l2 = new LinkedHashMap<String, Object>();
				l2.put("id", String.valueOf(list.get(i).getId()));
				l2.put("module_name", list.get(i).getModule_name());
				l1.add(l2);
			}
			session.close();
			return l1;

		}

		// sub module list for link role master
		@RequestMapping(value = "/getSubModuleNameListlink", method = RequestMethod.POST)
		public @ResponseBody List<Map<String, Object>> getSubModuleNameListlink(@RequestParam String valu1,
				HttpSession sessionUserId) {
			Session session = this.sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			Query q = session.createQuery("from TB_LDAP_SUBMODULE_MASTER where module.id=:module_id order by id");
			q.setParameter("module_id", Integer.parseInt(valu1));
			@SuppressWarnings("unchecked")
			List<TB_LDAP_SUBMODULE_MASTER> list = (List<TB_LDAP_SUBMODULE_MASTER>) q.list();
			tx.commit();
			List<Map<String, Object>> l1 = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> l2 = new LinkedHashMap<String, Object>();
				l2.put("id", String.valueOf(list.get(i).getId()));
				l2.put("module_id", String.valueOf(list.get(i).getModule().getId()));
				l2.put("submodule_name", list.get(i).getSubmodule_name());
				l1.add(l2);
			}
			session.close();
			return l1;
		}

		/// screen list based sub module id and module id
		@RequestMapping(value = "/getScreenName_mod_submodList", method = RequestMethod.POST)
		public @ResponseBody List<Map<String, Object>> getScreenName_mod_submodList(HttpServletRequest request) {
			Query q;
			int moduleid = Integer.parseInt(request.getParameter("moduleid"));
			int submoduleid = Integer.parseInt(request.getParameter("submoduleid"));

			Session session = this.sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			q = session.createQuery(
					"from TB_LDAP_SCREEN_MASTER where module.id=:moduleid and sub_module.id=:submoduleid  order by id");
			q.setParameter("moduleid", moduleid);
			q.setParameter("submoduleid", submoduleid);
			@SuppressWarnings("unchecked")
			List<TB_LDAP_SCREEN_MASTER> list = (List<TB_LDAP_SCREEN_MASTER>) q.list();
			tx.commit();
			List<Map<String, Object>> l1 = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> l2 = new LinkedHashMap<String, Object>();
				l2.put("id", String.valueOf(list.get(i).getId()));
				l2.put("screen_module_id", String.valueOf(list.get(i).getModule().getId()));
				l2.put("screen_submodule_id", list.get(i).getSub_module().getId());
				l2.put("screen_name", list.get(i).getScreen_name());
				l1.add(l2);
			}
			session.close();
			return l1;
		}

		// autocomplete for User Name
		@RequestMapping(value = "/getUsernameList", method = RequestMethod.POST)
		public @ResponseBody List<String> getUsernameList(String userName, HttpSession sessionUserId) {
			Session session = this.sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			Query q = session.createQuery("select distinct userName from UserLogin where accountNonLocked='1' and userName like :userName order by userName ").setMaxResults(10);
			q.setParameter("userName", "%"+userName+"%");
			@SuppressWarnings("unchecked")
			List<String> list = (List<String>) q.list();
			tx.commit();
			session.close();
			String enckey = hex_asciiDao.getAlphaNumericString();
			Cipher c = null;
			try {
				c = hex_asciiDao.EncryptionSHA256Algo(sessionUserId, enckey);
			} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeySpecException
					| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			List<String> FinalList = new ArrayList<String>();
			for (int i = 0; i < list.size(); i++) {
				byte[] encCode = null;
				try {
					encCode = c.doFinal(list.get(i).getBytes());
				} catch (IllegalBlockSizeException | BadPaddingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String base64EncodedEncryptedCode = new String(Base64.encodeBase64(encCode));
				FinalList.add(base64EncodedEncryptedCode);
			}
			// Enc Key Append Last value of List
			FinalList.add(enckey + "4bsjyg==");
			return FinalList;
		}
}
