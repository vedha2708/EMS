package com.BisagN.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.BisagN.dao.RoleBaseMenuDAO;

@Controller
@RequestMapping(value = {"admin","/" ,"user"}) 
public class LoginController {

	@Autowired
	RoleBaseMenuDAO roleBaseDAO;
	
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@RequestMapping(value = "/admin/adminHome", method = RequestMethod.GET)
	public ModelAndView adminPage() {
		ModelAndView model = new ModelAndView();
		model.setViewName("adminHomePage");
		return model;
	}
	@RequestMapping(value = "/RegistrationUrl", method = RequestMethod.GET)
	public ModelAndView RegistrationUrl(ModelMap Mmap, HttpSession session, HttpServletRequest request) {

		return new ModelAndView("RegistrationTile");
	}
//	@RequestMapping(value = "/admin/commonDashboard", method = RequestMethod.GET)
//	public ModelAndView AllDashboard(ModelMap Mmap,HttpSession session,HttpServletRequest request) {
//		System.out.println("commonDashboard");
//		
//		return new ModelAndView("commanDashboardTiles");
//	}
	
	@RequestMapping(value = "/user/userDashboard")
	public ModelAndView userPage() {
		ModelAndView model = new ModelAndView();
		model.setViewName("userHomePage");
		return model;
	}

	@RequestMapping(value = { "/", "/login" }, method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "msg", required = false) String msg,
			@RequestParam(value = "logout", required = false) String logout, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		
		System.err.println("login sessin:"+this.sessionFactory);
		
		/*System.out.println("error=="+error);
		System.out.println("msg=="+msg);
		System.out.println("logout=="+logout);*/
		ModelAndView model = new ModelAndView();
		
		if (error != null) {
			System.out.println("Test 1");
			model.addObject("error", getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
		}
		if (logout != null) {
			System.out.println("Test 2");
			if (request.getHeader("Referer") != null) {
				model.addObject("msg", "You are logged out successfully.");
			}
		}
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String Role = "";
		if (!authentication.getName().equals("anonymousUser")) {
			System.out.println("Test 3");
			Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
			String role1 = "";
			for (String role : roles) {
				role1 = role;
			}
			System.out.println(role1);
			Role = role1;
		}
		if (!Role.equals("")) {
			return new ModelAndView("redirect:/admin/commonDashboard");
		} else {
			String layout = "";
			//List<String> msgLayout = roleBaseDAO.getLayoutlist();
			layout += "<h3>";
//			for (int m = 0; m < msgLayout.size(); m++) {
//				if (m == 0) {
//					layout += msgLayout.get(m);
//				} else {
//					layout += " | " + msgLayout.get(m);
//				}
//			}
			layout += "</h3>";
			model.addObject("layout", layout);
		//	model.addObject("capchaImage", createImage(request));
			model.addObject("visiter_count", roleBaseDAO.VisitorCounter());
			model.addObject("server",getServerIP());
			model.setViewName("login");
		}
		return model;
	}

	@RequestMapping(value = {"/logout"}, method = RequestMethod.POST)
	public ModelAndView logoutDo(HttpServletRequest request,HttpServletResponse response){
		System.out.println("logout");
	HttpSession session= request.getSession(false);
	    SecurityContextHolder.clearContext();
	         session= request.getSession(false);
	        if(session != null) {
	            session.invalidate();
	        }
	        for(Cookie cookie : request.getCookies()) {
	            cookie.setMaxAge(0);
	        }
	        ModelAndView model = new ModelAndView();
	        model.addObject("logout","logout");
			model.setViewName("login");
			
	    return model;
	}
	// customize the error message
	public static String getErrorMessage(HttpServletRequest request, String key) {
		Exception exception = (Exception) request.getSession().getAttribute(key);
		System.out.println(exception);
		String error = "";
		if (exception instanceof BadCredentialsException) {
			error = "Invalid username and password!";
		} else if (exception instanceof LockedException) {
			error = exception.getMessage();
		} else if (exception instanceof SessionAuthenticationException) {
				exception.printStackTrace();
			error = "User Already logged in";// exception.getMessage();
		}else if (exception instanceof DisabledException) {
			error = "User is disabled";
		}else {
			error = "Invalid username and password!";
		}
		return error;
	}
		
	// for 403 access denied page
	@RequestMapping(value = "/user/403", method = RequestMethod.GET)
	public ModelAndView accesssDenied() {
		ModelAndView model = new ModelAndView();
		// check if user is login
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			model.addObject("username", userDetail.getUsername());
		}
		model.setViewName("403");
		return model;
	}

	// Create Capcha Code
	/*@RequestMapping(value = "/capchaCode", method = RequestMethod.POST)
	public @ResponseBody List<String> capchaCode(HttpServletRequest request) {
		List<String> capchaList = new ArrayList<String>();
		capchaList.add(getRandomInteger(10, 1));
		capchaList.add(getRandomInteger(10, 1));
		capchaList.add(getRandomInteger(10, 1));
		capchaList.add(getRandomInteger(10, 1));
		capchaList.add(getRandomCharacter());

		String capcha = "";
		for (int i = 0; i < capchaList.size(); i++) {
			capcha += capchaList.get(i);
		}
		HttpSession session = request.getSession();
		session.setAttribute("capcha", capcha);
		return capchaList;
	}*/
	
/*	@RequestMapping(value = "/genCapchaCode", method = RequestMethod.POST)
	public @ResponseBody byte[] genCapchaCode(HttpServletRequest request) {
		byte[] image = createImage(request);
		if(!image.equals("")) {
			return image;
		}else {
			return null;
		}
	}*/
	
	//@SuppressWarnings("unlikely-arg-type")
	@RequestMapping(value = "/genCapchaCode")
	public @ResponseBody  byte[] genCapchaCode1(HttpServletRequest request) {
		byte[]  image = createImage(request);
		if(!image.toString().equals("")) {
			return image;
		}else {
			return null;
		}
	}
	
	
	@RequestMapping(value = "/checkCapchaCode", method = RequestMethod.POST)
	public @ResponseBody boolean checkCapchaCode(HttpServletRequest request,String iCapcha) {
		String txtInput = iCapcha.replaceAll("\\s","").toString();
		String capcha = request.getSession().getAttribute("capcha").toString();
		if(txtInput.equals(capcha)){
			 return true;
		}else {
			return false;
		}
	}
	
	String captchaString = "";
	private  byte[] createImage(HttpServletRequest request) {
		try {
            Color backgroundColor = Color.white;
            Color borderColor = Color.black;
            Color textColor = Color.black;
            Color circleColor = new Color(190, 160, 150);
            Font textFont = new Font("Verdana", Font.BOLD, 30);
            int charsToPrint = 5;
            int width = 150;
            int height = 50;
            int circlesToDraw = 25;
            float horizMargin = 10.0f;
            double rotationRange = 0.7; 
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = (Graphics2D) bufferedImage.getGraphics();
            g.setColor(backgroundColor);
            g.fillRect(0, 0, width, height);

            // lets make some noisey circles
            g.setColor(circleColor);
            for (int i = 0; i < circlesToDraw; i++) {
                int L = (int) (Math.random() * height / 2.0);
                int X = (int) (Math.random() * width - L);
                int Y = (int) (Math.random() * height - L);
                g.draw3DRect(X, Y, L * 2, L * 2, true);
            }
            g.setColor(textColor);
            g.setFont(textFont);
            FontMetrics fontMetrics = g.getFontMetrics();
            int maxAdvance = fontMetrics.getMaxAdvance();
            int fontHeight = fontMetrics.getHeight();

            // i removed 1 and l and i because there are confusing to users...
            // Z, z, and N also get confusing when rotated
            // this should ideally be done for every language...
            // 0, O and o removed because there are confusing to users...
            // i like controlling the characters though because it helps prevent confusion
            String elegibleChars = "ABCDEFGHJKLMNPQRSTUVWXYabcdefghjkmnpqrstuvwxy23456789";
            char[] chars = elegibleChars.toCharArray();
            float spaceForLetters = -horizMargin * 3 + width;
            float spacePerChar = spaceForLetters / (charsToPrint - 1.0f);
            StringBuffer finalString = new StringBuffer();
            for (int i = 0; i < charsToPrint; i++) {
                double randomValue = Math.random();
                int randomIndex = (int) Math.round(randomValue * (chars.length - 1));
                char characterToShow = chars[randomIndex];
                finalString.append(characterToShow);

                // this is a separate canvas used for the character so that
                // we can rotate it independently
                int charWidth = fontMetrics.charWidth(characterToShow);
                int charDim = Math.max(maxAdvance, fontHeight);
                int halfCharDim = (int) (charDim / 2);
                BufferedImage charImage = new BufferedImage(charDim, charDim, BufferedImage.TYPE_INT_ARGB);
                Graphics2D charGraphics = charImage.createGraphics();
                charGraphics.translate(halfCharDim, halfCharDim);
                double angle = (Math.random() - 0.5) * rotationRange;
                charGraphics.transform(AffineTransform.getRotateInstance(angle));
                charGraphics.translate(-halfCharDim, -halfCharDim);
                charGraphics.setColor(textColor);
                charGraphics.setFont(textFont);
                int charX = (int) (0.5 * charDim - 0.5 * charWidth);
                charGraphics.drawString("" + characterToShow, charX, (int) ((charDim - fontMetrics.getAscent()) / 2 + fontMetrics.getAscent()));
                float x = horizMargin + spacePerChar * (i) - charDim / 2.0f;
                int y = (int) ((height - charDim) / 2);
                g.drawImage(charImage, (int) x, y, charDim, charDim, null, null);
                charGraphics.dispose();
            }
            g.setColor(borderColor);
            g.drawRect(0, 0, width - 1, height - 1);
            g.dispose();
            captchaString = finalString.toString();
            
           // return bufferedImage;
            ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
            try {
    			ImageIO.write(bufferedImage, "jpg", baos1);
    			baos1.flush();
    			baos1.close();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
            byte[] imageInByteArray = baos1.toByteArray();
            HttpSession session = request.getSession();
            System.out.println(captchaString);
    		session.setAttribute("capcha", captchaString);
    		return imageInByteArray;
    	} catch (Exception ioe) {
        	byte[] imageInByteArray = null;
            return imageInByteArray;
        }
	}
	
	/*public static String getRandomInteger(int maximum, int minimum) {
		return String.valueOf(((int) (Math.random() * (maximum - minimum))) + minimum);
	}

	public static String getRandomCharacter() {
		String AlphaNumericString = "ABCDEFGHIJKMNOPQRSTUVWXYZabcdefghjklmnopqrstuvxyz";
		int index = (int) (AlphaNumericString.length() * Math.random());
		return String.valueOf(AlphaNumericString.charAt(index));
	}*/
	
	/*@RequestMapping(value = "/JnlpDashboard", method = RequestMethod.GET)
	public ModelAndView dashboard(ModelMap Mmap,HttpSession session,HttpServletRequest request) {
		System.out.println("JnlpDashboard");
		return new ModelAndView("JnlpDashboardTiles");
	}*/
	
	public String getServerIP() {
		try(final  DatagramSocket s = new DatagramSocket()){
			try {
				s.connect(InetAddress.getByName("8.8.8.8"),10002);
				String hadd = s.getLocalAddress().getHostAddress();
				System.out.println("IP="+hadd);
				if(hadd.equals("152.1.13.51")) {
					return "Server 1";
				}else if(hadd.equals("152.1.13.52")) {
					return "Server 2";
				}else if(hadd.equals("152.1.13.53")) {
					return "Server 3";
				}else {
					return "Unknown Server";
				}
			} catch (UnknownHostException e) {
				return "Unknown Server";
			}
		} catch (SocketException e1) {
			return "Unknown Server";
		}
	}
	

}
