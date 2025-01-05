package com.BisagN.validation;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = {"admin","/" ,"user"}) 
public class ErrorController {

	@RequestMapping(value = "/400",method=RequestMethod.GET)
	public ModelAndView Get400() {
		ModelAndView model = new ModelAndView();
		model.setViewName("400");
		return model;
	}
	@RequestMapping(value = "/404",method=RequestMethod.GET)
	public ModelAndView get404(HttpSession sessionA) {
		//sessionA.invalidate();
		//return new ModelAndView("redirect:../login");
		ModelAndView model = new ModelAndView();
		model.setViewName("404");
		return model;
	}
	
	@RequestMapping(value = "/405",method=RequestMethod.GET)
	public ModelAndView get405(HttpSession sessionA) {
		ModelAndView model = new ModelAndView();
		model.setViewName("405");
		return model;
	}
	@RequestMapping(value = "/500",method=RequestMethod.GET)
	public ModelAndView get500() {
		ModelAndView model = new ModelAndView();
		model.setViewName("500");
		return model;
	}
	@RequestMapping(value = "/505",method=RequestMethod.GET)
	public ModelAndView get505() {
		ModelAndView model = new ModelAndView();
		model.setViewName("505");
		return model;
	}
}
