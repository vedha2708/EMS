package com.BisagN.validation;

import org.springframework.stereotype.Controller;

@Controller
public class ValidationController {

	public String image_MSG = "Screen Shot/Pdf name Should Contain Maximum 255 Characters";
	public Boolean checkImageAnmlLength(String fname) {
		if(fname.length() > 255) {
   	    	return false;
   	    }else {
   	    	return true;
   	    }
    }
	
//***************************************************START HELPDESK***************************************************************//	
   public String DescriptionLengthHelpdeskMSG = "Description Should Contain Maximum 1000 Character.";
    public boolean checkDescriptionLengthHelpdeskLength(String description) {
        if(description.length() >1000) {
               return false;
       }else {
               return true;
       }
    }
 
    public String Issue_summaryHelpdeskMSG = "Issue Summary Should Contain Maximum 100 Character.";
    public boolean checkIssue_summaryHelpdeskLength(String issue_summary) {
        if(issue_summary.length() >100) {
               return false;
       }else {
               return true;
       }
    }
    
    public String agent_nameHelpdeskMSG = "Agent Name Should Contain Maximum 30 Character.";
    public boolean checkagent_nameHelpdeskLength(String agent_name) {
        if(agent_name.length() >30) {
               return false;
       }else {
               return true;
       }
    }
    
    public String sectionHelpdeskMSG = "Section Should Contain Maximum 20 Character.";
    public boolean checksectionHelpdeskLength(String section) {
        if(section.length() >20) {
               return false;
       }else {
               return true;
       }
    }
    
    public String questionHelpdeskMSG = "Question Should Contain Maximum 255 Character.";
    public boolean checkquestionHelpdeskLength(String question) {
        if(question.length() >255) {
               return false;
       }else {
               return true;
       }
    }
    
    public String msgMarqueeMSG = "MSG Should Contain Maximum 200 Character.";
    public boolean checkmsgMarqueeLength(String msg) {
        if(msg.length() >200) {
               return false;
       }else {
               return true;
       }
    }
  //***************************************************END HELPDESK***************************************************************//
    
//***************************************************START LOGIN***************************************************************//
    
    public String ModuleNameMSG = "Module Name Should Contain Maximum 20 Character.";
    public boolean ModuleNameLength(String module_name) {
        if(module_name.length() >20) {
               return false;
       }else {
               return true;
       }
    }
    
    
    
    public String SubModuleNameMSG = "Sub Module Name Should Contain Maximum 255 Character.";
    public boolean SubModuleNameLength(String submodule_name) {
        if(submodule_name.length() >255) {
               return false;
       }else {
               return true;
       }
    }
    
    
    public String ScreenNameMSG = "Screen Name Should Contain Maximum 80 Character.";
    public boolean ScreenNameLength(String screen_name) {
        if(screen_name.length() >80) {
               return false;
       }else {
               return true;
       }
    }
    
    public String ScreenURLMSG = "Screen Url Should Contain Maximum 125 Character.";
    public boolean ScreenURLLength(String screen_url) {
        if(screen_url.length() >125) {
               return false;
       }else {
               return true;
       }
    }
    
    public String RoleMSG = "Role Should Contain Maximum 45 Character.";
    public boolean RoleLength(String role) {
        if(role.length() >45) {
               return false;
       }else {
               return true;
       }
    }    
    
    public String LoginNameMSG = "Login Name Should Contain Maximum 70 Character.";
    public boolean LoginNameLength(String login_name) {
        if(login_name.length() > 70) {
               return false;
       }else {
               return true;
       }
    }
    
    public String UserNameMSG = "User Name Should Contain Maximum 30 Character.";
    public boolean UserNameLength(String user_name) {
        if(user_name.length() > 30) {
               return false;
       }else {
               return true;
       }
    }
    
    public String ArmyNoMSG = "Army No Should Contain Maximum 9 Character.";
    public boolean ArmyNoLength(String army_no) {
        if(army_no.length() > 9) {
               return false;
       }else {
               return true;
       }
    }
    
    public String PasswordMSG = "Password Should Contain 8 to 28 Character.";
    public boolean PasswordLength(String password) {
        if(password.length() < 8 & password.length() > 28) {
               return false;
       }else {
               return true;
       }
    }
    
    public String PasswordPatternMSG = "Password Should Contain at least one number and one uppercase and lowercase letter and special character";
    //***************************************************END LOGIN***************************************************************//
}