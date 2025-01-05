package com.BisagN.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.BisagN.Model.Master.TBL_LEAVE_APPLICATION;
import com.BisagN.Model.Registration.TBL_REGISTRATION;
import com.BisagN.models.UserLogin;


@Controller
@RequestMapping(value = {"admin","/" ,"user"}) 
public class RegistrationController {
		
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	
//	@RequestMapping(value = "/RegistrationUrl",method= RequestMethod.GET)
//	public ModelAndView RegistrationUrl(ModelMap model,@RequestParam(value = "msg", required = false) String msg,HttpSession session,HttpServletRequest request) {
//		model.put("msg", msg);		
//		return new ModelAndView("RegistrationTile");
//	}


	@RequestMapping(value = "/RegistrationAction",method=RequestMethod.POST)
	public ModelAndView RegistrationAction(HttpServletRequest request,
			TBL_REGISTRATION r,ModelMap model,HttpSession session) {

		Session sessionHQL = this.sessionFactory.openSession();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
		UserLogin l = new UserLogin();
		String password1 = request.getParameter("cnf_password");
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String password = passwordEncoder.encode(password1);
		
		l.setUserName(request.getParameter("username"));
		l.setPassword(password);
		l.setLogin_name(request.getParameter("username"));
		l.setEnabled(1);
		l.setAccountNonExpired(1);
		l.setAccountNonLocked(1);
		l.setCredentialsNonExpired(1);
		sessionHQL.save(l);
		sessionHQL.flush();
		sessionHQL.clear();
		
		
		
		r.setPassword(request.getParameter("cnf_password"));
		r.setMobile_no(request.getParameter("mobile_no"));
		 
				 
		try {
			r.setDob(format.parse(request.getParameter("dob")));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			r.setJoining_date(format.parse(request.getParameter("joining_date")));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    sessionHQL.save(r);
	    model.put("msg", "Data Save Successfully");
		return new ModelAndView("redirect:RegistrationUrl");
	}

}
