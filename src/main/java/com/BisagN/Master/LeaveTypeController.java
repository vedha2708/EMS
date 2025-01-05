package com.BisagN.Master;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.BisagN.Model.Master.TBL_LEAVE_APPLICATION;

@Controller
@RequestMapping(value = {"admin","/" ,"user"}) 
public class LeaveTypeController {


	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	
	@GetMapping("/LeaveTypeUrl")
	public ModelAndView LeaveTypeUrl(ModelMap model,@RequestParam(value = "msg", required = false) String msg,HttpSession session,HttpServletRequest request) {
		model.put("msg", msg);		
		return new ModelAndView("LeaveTypeTiles");
	}
	
	
	@RequestMapping(value = "/LeaveType_mstAction",method=RequestMethod.POST)
	public ModelAndView LeaveType_mstAction(HttpServletRequest request,
			TBL_LEAVE_APPLICATION t,ModelMap model,HttpSession session) {

		Session sessionHQL = this.sessionFactory.openSession();
		 SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
		 
		 
				 
		try {
			t.setFrom_date(format.parse(request.getParameter("from_date")));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			t.setTo_date(format.parse(request.getParameter("to_date")));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		t.setStatus(0);
	    sessionHQL.save(t);
	    model.put("msg", "Data Save Successfully");
		return new ModelAndView("redirect:LeaveTypeUrl");
	}

}
