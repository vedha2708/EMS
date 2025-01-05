package com.BisagN.dao;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpSession;

import com.BisagN.models.Role;
import com.BisagN.models.UserLogin;

public interface RoleBaseMenuDAO {
	public int VisitorCounter();
	public ArrayList<ArrayList<String>> getModulelist(String roleid);
	public ArrayList<ArrayList<String>> getSubModulelist(String  moduleid,String roleid);
	public ArrayList<ArrayList<String>> getScreenlist(String  moduleid,String  submoduleid,String roleid);
	
	public Boolean getmoduleExist(String v);
	public Boolean getroleExist(String v);
	public Boolean getsubmoduleExist(String name,int m_id,int sm_id);
	public Boolean getscreenExist(String name,String url,int i,int j,String sc_id);
	public Boolean getscreenSubExist(String name,String url,String module_id,String screen_submodule_id);
	public Boolean getuserExist(String name);
	public Boolean ScreenRedirect(String name,String roleid);
	public Boolean CheckDashboard(String name,String roleid);
	public Boolean getlinkroleExist(int m_id,int sm_id,int s_id,int rid);
	//public  List<Map<String, Object>> getReportSubmoduleList();
	public void selectChild(String name,String url,String m_id,String sm_id);
	public void deleteChild(int sid);
	
	
	
	public  List<Map<String, Object>> RoleSearchReport() ;
	public  List<Map<String, Object>> ModuleSearchReport() ;
	public  List<Map<String, Object>> SubModuleSearchReport() ;
	public  List<Map<String, Object>> ScreenSearchReport(String m_id,String sm_id);
	
	public Role getRoleNameListbyid(int role_id);

	public  List<Map<String, Object>> SearchUserBbyRole1(String access_lvl,String subaccess_lvl,String user_name,String user_sus_no);
	

	public String getActiveData(String userid); // user Active 
	public String getDeactiveData(String userid); // user Deactive 

	 public  List<Map<String, Object>> getReportStatusOfInactiveUserList();
	 public String  userinsertdata(String type,int did,int roll);
	 
	 public List<Map<String, Object>> getUserReport();
		public UserLogin getUserLoginbyid(int id);
		public List<Map<String, Object>> getRole(int updateid);
		//public String UpdateUserMst(UserLogin updateid, int roll,String user_arm_code,String user_sus_no,String formation_code,String access_lve1,String sub_access_lve1);
		public String UpdateUserMst(UserLogin updateid, int roll,String access_lve1, String sub_access_lve1);
		
		 public Boolean DashboardRedirect(String name,String roleid);
		 
		// public DataSet<Map<String, Object>> DatatablesCriteriasUserreport(DatatablesCriterias criterias , String qry,String roleSubAccess);
		 
	//	 public DataSet<Map<String, Object>> DatatablesCriteriasActiveUserreport(DatatablesCriterias criterias , String qry,String roleSubAccess,String Status);
		 
		 /*public String submodulenamebyscreen(String roleid, String url);*/
		 
		// public List<String> getLayoutlist();
		 
		 public List<Map<String, Object>> getUserReportList1(int startPage, String pageLength, String Search,
					String orderColunm, String orderType, String qry, String roleSubAccess, HttpSession session, String roleid)
					throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException,
					InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException;

			public long getUserReportListTotalCount1(String Search, String qry);
			
			// Using DataTable With MockJax Report for SQL
			public ArrayList<ArrayList<String>> SearchUserStatus_DataTableMockJaxList_SQL(int startPage, int pageLength,
					String Search, String orderColunm, String orderType, String access_lvl, String login_name, String export,
					HttpSession sessionUserId) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
					InvalidKeySpecException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException;

			public long SearchUserStatus_DataTableMockJaxTotalCount_SQL(String Search, String access_lvl, String login_name);
			
			public ArrayList<ArrayList<String>> getLastLogin(int userId);
	
}

