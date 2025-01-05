package com.BisagN.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.BisagN.models.Role;
import com.BisagN.models.UserLogin;

@Service
public class UserServiceDAOImpl implements UserServiceDAO {

	@Autowired
	private DataSource dataSource;

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	
	@Override
	public List<String> getRoleByuserId(String userId) {
		List<String> list = new ArrayList<String>();
		Connection conn = null;
		String q = "";
		try {
			conn = dataSource.getConnection();
			PreparedStatement stmt = null;
			q = "Select r.role::text " //
					+ " from userroleinformation ur, roleinformation r " //
					+ " where ur.role_id = r.role_id and ur.user_id::text = ? ";
			stmt = conn.prepareStatement(q);
			int j = 1;
			stmt.setString(j, userId);

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				list.add(rs.getString("role"));
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
		return list;
	}

	public UserLogin findUser(String userName)
	{
		System.out.println("findUser=" + userName);
		Session session= this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Query q = session.createQuery("FROM UserLogin U where U.userName=:userName");
		q.setParameter("userName", userName);
		List<UserLogin> list = (List<UserLogin>) q.list();
		tx.commit();
		session.close();
		if(list.size()>0)
		{
			return list.get(0);
		}
		else
		{
			return null;
		}		
	}

}
