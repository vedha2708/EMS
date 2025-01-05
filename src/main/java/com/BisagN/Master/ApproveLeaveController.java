package com.BisagN.Master;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.BisagN.Model.Master.TBL_LEAVE_APPLICATION;
import com.BisagN.dao.LeaveDAO;

@Controller
@RequestMapping(value = {"admin","/" ,"user"}) 
public class ApproveLeaveController {
	
	@Autowired
	private LeaveDAO leavDao;
	
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	
	
	@GetMapping("/Approve_LeaveUrl")
	public ModelAndView Approve_LeaveUrl(ModelMap model,@RequestParam(value = "msg", required = false) String msg
			,HttpSession session,HttpServletRequest request) {
		ArrayList<ArrayList<String>> list=leavDao.LeaveReport();
		model.put("list", list);
		model.put("msg", msg);		
		return new ModelAndView("Approve_leaveTiles");
	}
	
	@PostMapping("/view_Leave")
	public ModelAndView view_Leave(ModelMap model,@RequestParam(value = "msg", required = false) String msg,
			@RequestParam(value = "vid", required = false) int viewid,
			HttpSession session,HttpServletRequest request) {
		System.out.println(viewid+"--------------viewid");
		ArrayList<ArrayList<String>> list=leavDao.LeaveReportById(viewid);
		model.put("list", list);
		model.put("vid", viewid);
		model.put("msg", msg);		
		return new ModelAndView("View_leaveTiles");
	}
	
	@RequestMapping(value="/ViewLeaveAction")
	public ModelAndView ViewLeaveAction(ModelMap model
			,@RequestParam(value = "msg", required = false) String msg,
			@RequestParam(value = "vid", required = false) int viewid,
			HttpSession session,HttpServletRequest request) {
		System.out.println(viewid+"--------------viewid");
		Session session1 = this.sessionFactory.openSession();
		Transaction tx1 = session1.beginTransaction();
		Query qry = session1.createQuery(
				"update  TBL_LEAVE_APPLICATION set status=:status where id=:id");
		qry.setParameter("status", Integer.parseInt(request.getParameter("h_status")));
		qry.setParameter("id", Integer.parseInt(request.getParameter("h_id")));
		int row=qry.executeUpdate();
		if(row > 0) {
			if(request.getParameter("h_status").equals("1")) {
				model.put("msg","Leave Approved.");
			}
			else if(request.getParameter("h_status").equals("2")) {
				model.put("msg","Leave Rejected.");
			}
			
		}else {
			if(request.getParameter("h_status")=="1") {
				model.put("msg","Leave Not Approved.");
			}
			else if(request.getParameter("h_status")=="2") {
				model.put("msg","Leave Not Rejected.");
			}
			
		}
		tx1.commit();
		session1.close();
		return new ModelAndView("redirect:Approve_LeaveUrl");
	}
}
