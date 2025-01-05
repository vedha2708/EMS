package com.BisagN.Master;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = {"admin","/" ,"user"})
public class EditRegistration {

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	
	@GetMapping("/EditRegistrationUrl")
	public ModelAndView EditRegistrationUrl(ModelMap model,
			@RequestParam(value = "msg", required = false) String msg,HttpSession session,HttpServletRequest request) {
		model.put("msg", msg);		
		return new ModelAndView("EditRegistrationTile");
}
}
