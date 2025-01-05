package com.BisagN.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@Repository
@ComponentScan
public class LeaveDAOImpl  implements LeaveDAO {
	
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	
	
	@Autowired
	private DataSource dataSource;

	@Override
	public ArrayList<ArrayList<String>> LeaveReport() {
		// TODO Auto-generated method stub
		ArrayList<ArrayList<String>> list =new ArrayList<ArrayList<String>>();
		Connection conn = null;
		String q = "";
		try {
			conn = dataSource.getConnection();
			PreparedStatement stmt = null;
			q = "select la.id,tr.name,la.leave_type from tbl_leave_application la\r\n"
					+ "inner join tbl_registration tr on la.userid=tr.id ";
			stmt = conn.prepareStatement(q);
//			int j = 1;
			

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				ArrayList<String> alist=new ArrayList<String>();
			
				String f = "";
				String View = " onclick=\" viewData("+ rs.getInt("id") + ")\"";
				f = "<i class='action_icons action_view'" + View + " title='View Application'></i>";
				
				
				alist.add(rs.getString("name"));
				alist.add(rs.getString("leave_type"));
				alist.add(f);
				list.add(alist);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	@Override
	public ArrayList<ArrayList<String>> LeaveReportById(int viewid) {
		// TODO Auto-generated method stub
		ArrayList<ArrayList<String>> list =new ArrayList<ArrayList<String>>();
		Connection conn = null;
		String q = "";
		try {
			conn = dataSource.getConnection();
			PreparedStatement stmt = null;
			q = "select la.id,tr.name as empname,la.leave_type,to_char(la.from_date,'dd/mm/yyyy') as from_date\r\n"
					+ ",to_char(la.to_date,'dd/mm/yyyy') as to_date,la.reason,la.description\r\n"
					+ "from tbl_leave_application la\r\n"
					+ "inner join tbl_registration tr on la.userid=tr.id\r\n"
					+ "where la.id=? ";
			stmt = conn.prepareStatement(q);
			stmt.setInt(1,viewid);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				ArrayList<String> alist=new ArrayList<String>();
				alist.add(rs.getString("id"));
				alist.add(rs.getString("empname"));
				alist.add(rs.getString("leave_type"));
				alist.add(rs.getString("from_date"));
				alist.add(rs.getString("to_date"));
				alist.add(rs.getString("reason"));
				alist.add(rs.getString("description"));
				list.add(alist);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

}
