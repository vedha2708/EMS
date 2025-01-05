package com.BisagN.dao.helpDesk;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface HelpDAO {
	public List<Map<String, Object>> getExpectedPendingReceivedDetails(String unit,String year,String doctype);
	public List<Map<String, Object>> getSupportRequestJdbc();
	public List<Map<String, Object>> getActiveUserListJdbc(String qry);
	public List<Map<String, Object>> getUserLoginCountJdbc(String qry);
	public List<Map<String, Object>> getActiveUserCountJdbc(String qry);
	public List<Map<String, Object>> getNewTicketCountJdbc(String qry);
	public List<Map<String, Object>> getPendigTicketCountJdbc(String qry);
	public List<Map<String, Object>> getCompletedTicketCountJdbc(String qry);
	public List<Map<String, Object>> getRoleCountJdbc(String qry);
	public List<Map<String, Object>> getUserCountJdbc(String qry);
	public List<Map<String, Object>> getfeedbackrecCountJdbc(String qry);
	public List<Map<String, Object>> getFeatureReqCountJdbc(String qry);
	public List<Map<String, Object>> searchfaqList(String section);
	public ArrayList<ArrayList<String>> getNewfaqdetail1();
	public List<String> getsecList();
	public List<String> getqueList();
	public List<Map<String, Object>> loggedin_report(List<String> userlist);
	public String getSupportRequest();
	public String getActiveUserList();
	public List<Map<String, Object>> getUserLoginCount();
	public List<Map<String, Object>> getActiveUserCount();
	public List<Map<String, Object>> getRoleCount();
	public List<Map<String, Object>> getUserCount();
	public List<Map<String, Object>> getUserReport() ;
	public List<Map<String, Object>> getInmailDetails(String year);
	public List<Map<String, Object>> getRankListDetails();
	
}