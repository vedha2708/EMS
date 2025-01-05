package com.BisagN.Dashboard;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.BisagN.RBAC.RoleMstrController;
import com.BisagN.dao.helpDesk.HelpDAO;

@Controller
@RequestMapping(value = { "admin", "/", "user" })
public class HelpDashboardController {

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@Autowired
	RoleMstrController roleMstrCon = new RoleMstrController();

	@Autowired
	private	HelpDAO helpDash;

	

	@Autowired
	private SessionRegistry sessionRegistry;

	public List<String> getUsersFromSessionRegistry() {
		String userString = sessionRegistry.getAllPrincipals().stream()
				.filter(u -> !sessionRegistry.getAllSessions(u, false).isEmpty()).map(Object::toString)
				.collect(Collectors.toList()).toString();

		List<String> allMatches = new ArrayList<String>();
		Matcher m = Pattern.compile("Username\\:.*?\\;").matcher(userString);
		while (m.find()) {
			allMatches.add(m.group().replace("Username: ", "").replace(";", ""));
		}
		return allMatches;
	}

	@RequestMapping(value = "/admin/commonDashboard", method = RequestMethod.GET)
	public ModelAndView AllDashboard(ModelMap Mmap, HttpSession session, HttpServletRequest request) {

		return new ModelAndView("commanDashboardTiles");
	}

}