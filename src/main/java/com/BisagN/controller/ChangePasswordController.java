package com.BisagN.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.BisagN.dao.HexatoAsciiDAO;
import com.BisagN.dao.HexatoAsciiDAOImpl;
import com.BisagN.dao.RoleBaseMenuDAO;
import com.BisagN.dao.UserLoginDAO;
import com.BisagN.dao.UserLoginDAOImpl;
import com.BisagN.models.UserLogin;
import com.BisagN.models.helpdesk.Tb_HTover;
import com.BisagN.validation.DateWithTimeStampController;
import com.BisagN.validation.PasswordValidator;
import com.BisagN.validation.ValidationController;

@Controller
@RequestMapping(value = {"admin","/" ,"user"}) 
public class ChangePasswordController {
	@Autowired
	UserLoginDAO userlogin =  new UserLoginDAOImpl();
	
//	@Autowired
//	HexatoAsciiDAO hex_asciiDao = new HexatoAsciiDAOImpl();
	
	ValidationController validation = new ValidationController();
	
	@Autowired
	private RoleBaseMenuDAO roledao;
	
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	
	
	@RequestMapping(value = "/changePassword",method= RequestMethod.GET)
	public ModelAndView changePassword(ModelMap model,@RequestParam(value = "msg", required = false) String msg,HttpSession session,HttpServletRequest request) {
		int userid = Integer.parseInt(session.getAttribute("userId").toString());
		String  roleid = session.getAttribute("roleid").toString();
		Boolean val = roledao.ScreenRedirect("changePassword", roleid);	
		if(val == false) {
			return new ModelAndView("AccessTiles");
		}
		if(request.getHeader("Referer") == null ) {
			msg = "";
		}
		model.put("msg", msg);		
		model.put("userDetails", userlogin.findByRoleId(userid));
		return new ModelAndView("changePasswordTile");	
	}
	
	@RequestMapping(value = "/changePassword_Action",method=RequestMethod.POST)
	public ModelAndView changePassword_Action(HttpServletRequest request,ModelMap model,HttpSession session) {
		
		int userid = Integer.parseInt(session.getAttribute("userId").toString());
		UserLogin userDetails = userlogin.findByRoleId(userid);
		String old_pass =  request.getParameter("old_pass");
		String newpass = request.getParameter("new_pass");
		String c_pass  =  request.getParameter("c_password");
		
		Session sessionHQL = this.sessionFactory.openSession();
		if(old_pass == "" || old_pass.equals("")) {
			model.put("msg", "Please Enter Old Password.");		
			return new ModelAndView("redirect:changePassword");
		}
		else if(validation.PasswordLength(old_pass) == false){
	 		model.put("msg",validation.PasswordMSG);
			return new ModelAndView("redirect:changePassword");
		}
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		if(!passwordEncoder.matches(old_pass.trim(), userDetails.getPassword())) {
			model.put("msg", "Old Password is not Correct");		
			return new ModelAndView("redirect:changePassword");
		}
		
		if(newpass == "" || newpass.equals("")) {
			model.put("msg", "Please Enter New Password.");		
			return new ModelAndView("redirect:changePassword");
		}
		else if(validation.PasswordLength(newpass) == false){ // Check New Password Pattern length 
	 		model.put("msg",validation.PasswordMSG);
			return new ModelAndView("redirect:changePassword");
		}
		else if(!PasswordValidator.validate(newpass)) { // Check New Password Pattern "((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{8,28})"
			model.put("msg",validation.PasswordPatternMSG);
			return new ModelAndView("redirect:changePassword");
		}
		
		if(c_pass == "" || c_pass.equals("")) {
			model.put("msg", "Please Enter New Password.");		
			return new ModelAndView("redirect:changePassword");
		}
		else if(validation.PasswordLength(c_pass) == false){
	 		model.put("msg",validation.PasswordMSG);
			return new ModelAndView("redirect:changePassword");
		}
		
		if(!newpass.trim().equals(c_pass.trim())) {			
			model.put("msg", "Passwords do not match.");		
			return new ModelAndView("redirect:changePassword");
		}
		else {
			String hashedOldPassword = passwordEncoder.encode(newpass.trim());
			userDetails.setPassword(hashedOldPassword);				
			sessionHQL.beginTransaction();
			sessionHQL.saveOrUpdate(userDetails);
			sessionHQL.getTransaction().commit();
			sessionHQL.close();
			model.put("msg", "Password Changed Successfully");
		}
		return new ModelAndView("redirect:changePassword");
	}

	@RequestMapping(value = "/handingTakingOver",method= RequestMethod.GET)
	public ModelAndView handingTakingOver(ModelMap model,@RequestParam(value = "msg", required = false) String msg,HttpSession session,HttpServletRequest request) {
		int userid = Integer.parseInt(session.getAttribute("userId").toString());
		String  roleid = session.getAttribute("roleid").toString();
		Boolean val = roledao.ScreenRedirect("handingTakingOver", roleid);	
		if(val == false) {
			return new ModelAndView("AccessTiles");
		}
		if(request.getHeader("Referer") == null ) {
			msg = "";
		}
		model.put("msg", msg);		
		model.put("userDetails", userlogin.findByRoleId(userid));
		return new ModelAndView("handingTakingOverTiles");	
	}
	
	@RequestMapping(value = "/handingTakingOver_Action",method=RequestMethod.POST)
	public ModelAndView handingTakingOver_Action(HttpServletRequest request,ModelMap model,HttpSession session,
			@RequestParam(value = "auth_letter1", required = false) MultipartFile auth_letter) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException {
		int userid = Integer.parseInt(session.getAttribute("userId").toString());
		String handingTakingOverPath = session.getAttribute("handingTakingOverPath").toString();
		String  roleid = session.getAttribute("roleid").toString();
		Boolean val = roledao.ScreenRedirect("handingTakingOver", roleid);	
		if(val == false) {
			return new ModelAndView("AccessTiles");
		}
		
		UserLogin userDetails = userlogin.findByRoleId(userid);
		
		String ht_type = request.getParameter("ht_type");
		String ht_reason = request.getParameter("ht_reason");
		String to_army_no = request.getParameter("to_army_no");
		int flag = 0;
		Session sessionHQL = this.sessionFactory.openSession();
		if(ht_type == "" || ht_type.equals("")) {
			model.put("msg", "Please select H/T Type");		
			return new ModelAndView("redirect:handingTakingOver");
		}
		if(ht_reason == "" || ht_reason.equals("")) {
			model.put("msg", "Please select Reason");		
			return new ModelAndView("redirect:handingTakingOver");
		}
		if(to_army_no == "" || to_army_no.equals("")) {			
			model.put("msg", "Please enter Army No");		
			return new ModelAndView("redirect:handingTakingOver");
		}
		else {
			Tb_HTover ht = new Tb_HTover();
			sessionHQL.beginTransaction();
			ht.setHt_type(ht_type);
			ht.setHt_reason(ht_reason);
			ht.setTo_army_no(to_army_no);
			//ht.setFrom_army_no(userDetails.getArmy_no());
			ht.setFrom_userid(userDetails.getUserId());
			ht.setFrom_username(userDetails.getUserName());
			
			String fname = "";
			if (!auth_letter.isEmpty()) {
				// code modify by Paresh on 05/05/2020
				String extension = "";
				try {
					DateWithTimeStampController timestamp = new DateWithTimeStampController();
					byte[] bytes = auth_letter.getBytes();
					File dir = new File(handingTakingOverPath);
					if (!dir.exists()) {
						dir.mkdirs();
					}
					String filename = auth_letter.getOriginalFilename();
					int i = filename.lastIndexOf('.');
					if (i >= 0) {
						extension = filename.substring(i+1);
					}
					fname = dir.getAbsolutePath() + File.separator +timestamp.currentDateWithTimeStampString()+"_"+userid+"_HT."+extension;
					File serverFile = new File(fname);
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
					stream.write(bytes);	                
					stream.close();
					ht.setAuth_letter(fname);
				}
				catch (Exception e) {
		       }
			} 
			flag = (int) sessionHQL.save(ht);
			
			String newpass = "Test@123";
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String hashedPassword = passwordEncoder.encode(newpass);
			userDetails.setPassword(hashedPassword);	
			//userDetails.setArmy_no(to_army_no);
			sessionHQL.saveOrUpdate(userDetails);
			sessionHQL.getTransaction().commit();
			sessionHQL.close();
		}
		if(flag > 0) {
			HttpSession sess = request.getSession(false);
			sess.invalidate();
			return new ModelAndView("redirect:/login");
		}else {
			model.put("msg", "Error! Please Retry");		
			return new ModelAndView("redirect:handingTakingOver");
		}
	}
	
	@RequestMapping(value = "/my_profile",method= RequestMethod.GET)
	public ModelAndView my_profile(@RequestParam(value = "msg", required = false) String msg, HttpServletRequest request,HttpSession session,ModelMap Mmap) {
		if (request.getHeader("Referer") == null) {
			msg = "";
			return new ModelAndView("AccessTiles");
		}
		int userid = Integer.parseInt(session.getAttribute("userId").toString());
		Mmap.put("mp", roledao.getUserLoginbyid(userid));
		return new ModelAndView("myProfileTiles");
	}	
}
