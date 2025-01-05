package com.BisagN.dao;

import java.security.InvalidAlgorithmParameterException;
import javax.crypto.Cipher;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.BisagN.models.Role;
import com.BisagN.models.TB_LDAP_MODULE_MASTER;
import com.BisagN.models.TB_LDAP_ROLEMASTER;
import com.BisagN.models.TB_LDAP_SCREEN_MASTER;
import com.BisagN.models.TB_LDAP_SUBMODULE_MASTER;
import com.BisagN.models.UserLogin;
import org.apache.commons.codec.binary.Base64;

@Service
@Repository
public class RoleBaseMenuDAOImpl implements RoleBaseMenuDAO {

	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private DataSource dataSource;
	
//	private DataSource dataSource;

//	public void setDataSource(DataSource dataSource) {
//		this.dataSource = dataSource;
//	}

	HexatoAsciiDAO hex_asciiDao = new HexatoAsciiDAOImpl();
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public boolean checkIsIntegerValue(String Search) {
		return Search.matches("[0-9]+");
	}
	
	public int VisitorCounter() {
		int count = 0;
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement stmt = null;
			String sql = "SELECT nextval('login_visitor_count')";
			stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				count = rs.getInt(1);
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
		return count;
	}

	// Start Menu Query
	public ArrayList<ArrayList<String>> getModulelist(String roleid) {
		ArrayList<ArrayList<String>> aList = new ArrayList<ArrayList<String>>();
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			String sql = "select distinct b.module_name as module_name,a.moduleid  as moduleid from TB_LDAP_ROLEMASTER a, TB_LDAP_MODULE_MASTER b where a.roleid = ? and b.id > 0 and b.id = a.module.id  and  a.module.id != 0 ";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, roleid);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				ArrayList<String> list = new ArrayList<String>();
				list.add(rs.getString("module_name"));
				list.add(rs.getString("moduleid"));

				aList.add(list);
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return aList;
	}

	public ArrayList<ArrayList<String>> getSubModulelist(String moduleid, String roleid) {
		ArrayList<ArrayList<String>> aList = new ArrayList<ArrayList<String>>();
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			String sql = "select distinct b.submodule_name as submodule_name ,a.submoduleid  as submoduleid from TB_LDAP_ROLEMASTER a, TB_LDAP_SUBMODULE_MASTER b where a.roleid= ? and b.module_id = ? and  a.submoduleid = b.id";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, roleid);
			stmt.setString(2, moduleid);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				ArrayList<String> list = new ArrayList<String>();
				list.add(rs.getString("submodule_name"));
				list.add(rs.getString("submoduleid"));

				aList.add(list);
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return aList;
	}

	public ArrayList<ArrayList<String>> getScreenlist(String moduleid, String submoduleid, String roleid) {
		ArrayList<ArrayList<String>> aList = new ArrayList<ArrayList<String>>();
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			String sql = "select distinct b.screen_name,b.screen_url , a.screenid from TB_LDAP_ROLEMASTER a , TB_LDAP_SCREEN_MASTER b where a.roleid=? and  a.module.id = ? and a.sub_module.id = ? and a.screen.id =  b.id ";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, roleid);
			stmt.setString(2, moduleid);
			stmt.setString(3, submoduleid);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				ArrayList<String> list = new ArrayList<String>();

				list.add(rs.getString("screen_name"));
				list.add(rs.getString("screen_url"));
				list.add(rs.getString("screenid"));

				aList.add(list);
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return aList;
	}

	// End Menu Query

	@SuppressWarnings("unchecked")
	public Boolean getmoduleExist(String v) {
		String hql = "from TB_LDAP_MODULE_MASTER where module_name=:module_name ";
		List<TB_LDAP_MODULE_MASTER> users = null;
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			Query query = session.createQuery(hql);
			query.setParameter("module_name", v);
			users = (List<TB_LDAP_MODULE_MASTER>) query.list();
			tx.commit();
			session.close();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			return null;
		}
		if (users.size() > 0) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public Boolean getroleExist(String v) {
		String hql = "from Role where role=:role ";
		List<Role> users = null;
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			Query query = session.createQuery(hql);
			query.setParameter("role", v);
			users = (List<Role>) query.list();
			tx.commit();
			session.close();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			return null;
		}
		if (users.size() > 0) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public Boolean getsubmoduleExist(String name, int m_id, int sm_id) {
		String hql = "";
		if (sm_id != 0) {
			hql = "from TB_LDAP_SUBMODULE_MASTER where (submodule_name=:submodule_name or module_id=:module_id) and id!=:id ";
		} else {
			hql = "from TB_LDAP_SUBMODULE_MASTER where submodule_name=:submodule_name  ";
		}
		List<TB_LDAP_SUBMODULE_MASTER> users = null;
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			Query query = session.createQuery(hql);

			query.setParameter("submodule_name", name);

			if (sm_id != 0) {
				query.setParameter("module_id", m_id);
				query.setParameter("id", sm_id);
			}
			users = (List<TB_LDAP_SUBMODULE_MASTER>) query.list();
			tx.commit();
			session.close();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			return null;
		}

		if (sm_id != 0) {
			if (users.size() > 1) {
				return true;
			}
			return false;
		} else {
			if (users.size() > 0) {
				return true;
			}
			return false;

		}

	}

	@SuppressWarnings("unchecked")
	public Boolean getscreenExist(String name, String url, int module_id, int sm_id, String sc_id) {
		String hql = "";
		if (sc_id.equals("") || sc_id == "" && url != null) {
			hql = "from TB_LDAP_SCREEN_MASTER where screen_name=:screen_name and screen_url=:screen_url and screen_module_id=:screen_module_id and screen_submodule_id=:screen_submodule_id ";
		} else {
			hql = "from TB_LDAP_SCREEN_MASTER where screen_name=:screen_name and screen_module_id=:screen_module_id and screen_submodule_id=:screen_submodule_id and id!=:id";
		}
		List<TB_LDAP_SCREEN_MASTER> users = null;
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			Query query = session.createQuery(hql);
			query.setParameter("screen_name", name);
			query.setParameter("screen_module_id", module_id);
			query.setParameter("screen_submodule_id", sm_id);

			if (sc_id != "" || !sc_id.equals("") && url == null) {
				query.setParameter("id", Integer.parseInt(sc_id));
			} else
				query.setParameter("screen_url", url);

			users = (List<TB_LDAP_SCREEN_MASTER>) query.list();
			tx.commit();
			session.close();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			return null;
		}
		if (users.size() > 0) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public Boolean getscreenSubExist(String name, String url, String module_id, String screen_submodule_id) {
		String hql = "from TB_LDAP_SCREEN_MASTER where screen_name=:screen_name and screen_url=:screen_url and screen_module_id=:screen_module_id  and screen_submodule_id=:screen_submodule_id ";
		List<TB_LDAP_SCREEN_MASTER> users = null;
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			Query query = session.createQuery(hql);
			query.setParameter("screen_name", name);
			query.setParameter("screen_url", url);
			query.setParameter("screen_module_id", module_id);
			query.setParameter("screen_submodule_id", screen_submodule_id);
			users = (List<TB_LDAP_SCREEN_MASTER>) query.list();
			tx.commit();
			session.close();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			return null;
		}
		if (users.size() > 0) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public Boolean getuserExist(String name) {
		String hql = "from UserLogin where userName=:userName ";
		List<UserLogin> users = null;
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			Query query = session.createQuery(hql);
			query.setParameter("userName", name);
			users = (List<UserLogin>) query.list();
			tx.commit();
			session.close();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			return null;
		}
		if (users.size() > 0) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public Boolean ScreenRedirect(String name, String roleid) {
		String hql = "from TB_LDAP_ROLEMASTER ro , TB_LDAP_SCREEN_MASTER sc where ro.screen.id = sc.id and sc.screen_url=:screen_url and ro.roleid=:roleid ";
		List<TB_LDAP_ROLEMASTER> users = null;
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			Query query = session.createQuery(hql);
			query.setParameter("screen_url", name);
			query.setParameter("roleid", Integer.parseInt(roleid));
			users = (List<TB_LDAP_ROLEMASTER>) query.list();
			tx.commit();
			session.close();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			return null;
		}
		if (users.size() > 0) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public Boolean CheckDashboard(String name, String roleid) {
		String hql = "from Role r where r.role_url=:screen_url and r.roleId=:roleid ";
		List<TB_LDAP_ROLEMASTER> users = null;
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			Query query = session.createQuery(hql);
			query.setParameter("screen_url", name);
			query.setParameter("roleid", Integer.parseInt(roleid));
			users = (List<TB_LDAP_ROLEMASTER>) query.list();
			tx.commit();
			session.close();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			return null;
		}
		if (users.size() > 0) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public Boolean getlinkroleExist(int m_id, int sm_id, int s_id, int rid) {
		String hql = "from TB_LDAP_ROLEMASTER where module.id=:moduleid and sub_module.id=:submoduleid and screen.id=:screenid and roleid=:roleid ";
		List<TB_LDAP_SCREEN_MASTER> users = null;
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			Query query = session.createQuery(hql);
			query.setParameter("moduleid", m_id);
			query.setParameter("submoduleid", sm_id);
			query.setParameter("screenid", s_id);
			query.setParameter("roleid", rid);
			users = (List<TB_LDAP_SCREEN_MASTER>) query.list();
			tx.commit();
			session.close();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			return null;
		}
		if (users.size() > 0) {
			return true;
		}
		return false;
	}

	/*
	 * public List<Map<String, Object>> getReportSubmoduleList() {
	 * 
	 * List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	 * Connection conn = null; String q=null; try{ conn =
	 * dataSource.getConnection();
	 * 
	 * q="SELECT sub.id, sub.submodule_name , sub.submodule_role_id, sub.module_id,m.module_name FROM tb_ldap_submodule_master sub , tb_ldap_module_master m where  sub.module_id = m.id and m.id > 0"
	 * ;
	 * 
	 * PreparedStatement stmt=conn.prepareStatement(q); ResultSet rs =
	 * stmt.executeQuery(); ResultSetMetaData metaData = rs.getMetaData();
	 * 
	 * int columnCount = metaData.getColumnCount(); while (rs.next()) { Map<String,
	 * Object> columns = new LinkedHashMap<String, Object>();
	 * 
	 * for (int i = 1; i <= columnCount; i++) {
	 * columns.put(metaData.getColumnLabel(i), rs.getObject(i)); }
	 * list.add(columns); } rs.close(); stmt.close(); conn.close(); }catch
	 * (SQLException e) { e.printStackTrace(); } finally { if (conn != null) { try {
	 * conn.close(); } catch (SQLException e) { } } } return list; }
	 */

	///////////////// screen delete

	public void selectChild(String name, String url, String m_id, String sm_id) {
		String q1 = "select id FROM tb_ldap_screen_master  WHERE screen_name='" + name + "' and screen_url='" + url
				+ "' and screen_module_id='" + m_id + "' and screen_submodule_id='" + sm_id + "'";
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement(q1);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				deleteChild(rs.getInt("id"));
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

	}

	public void deleteChild(int sid) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String hql = "DELETE FROM TB_LDAP_SCREEN_MASTER where id=:id ";
		Query q = session.createQuery(hql).setInteger("id", sid);
		q.executeUpdate();
		String hql1 = "DELETE FROM TB_LDAP_ROLEMASTER where screen.id=:id ";
		Query q1 = session.createQuery(hql1).setInteger("id", sid);
		q1.executeUpdate();
		tx.commit();
		session.close();
	}

	/////////////// Module Report//////////
	public List<Map<String, Object>> ModuleSearchReport() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Connection conn = null;
		String q = "";

		try {
			conn = dataSource.getConnection();
			PreparedStatement stmt = null;
			q = "Select id,module_name from tb_ldap_module_master where id > 0 order by id";
			stmt = conn.prepareStatement(q);
			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			while (rs.next()) {
				Map<String, Object> columns = new LinkedHashMap<String, Object>();
				columns.put(metaData.getColumnLabel(2), rs.getObject(2));
				String Update = "onclick=\"  if (confirm('Are you sure you want to update?') ){Update('"
						+ rs.getObject(1) + "','" + rs.getObject(2) + "')}else{ return false;}\"";
				String updateButton = "<i class='action_icons action_update' " + Update + " title='Edit Data'></i>";

				columns.put(metaData.getColumnLabel(1), updateButton);

				list.add(columns);
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// throw new RuntimeException(e);
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

	/////////// Sub Module Report //////////

	public List<Map<String, Object>> SubModuleSearchReport() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Connection conn = null;
		String q = "";

		try {
			conn = dataSource.getConnection();
			PreparedStatement stmt = null;
			q = "SELECT sub.id, sub.submodule_name, sub.module_id,m.module_name FROM tb_ldap_submodule_master sub , tb_ldap_module_master m where  sub.module_id = m.id and m.id > 0";

			stmt = conn.prepareStatement(q);
			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			while (rs.next()) {
				Map<String, Object> columns = new LinkedHashMap<String, Object>();

				columns.put(metaData.getColumnLabel(2), rs.getObject(2));
				columns.put(metaData.getColumnLabel(4), rs.getObject(4));

				String Update = "onclick=\"  if (confirm('Are you sure you want to update?') ){Update('"
						+ rs.getObject(1) + "','" + rs.getObject(2) + "','" + rs.getObject(3)
						+ "')}else{ return false;}\"";
				String updateButton = "<i class='action_icons action_update' " + Update + " title='Edit Data'></i>";

				columns.put(metaData.getColumnLabel(1), updateButton);

				list.add(columns);
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// throw new RuntimeException(e);
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

	//////// Screen report /////////////////

	public List<Map<String, Object>> ScreenSearchReport(String m_id, String sm_id) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Connection conn = null;
		String qry = "";
		String q = "";

		try {
			conn = dataSource.getConnection();
			PreparedStatement stmt = null;

			if (m_id != "" && m_id != null && m_id != "null" && !m_id.equals("0")) {
				qry += "and screen_module_id = ? ";
			}
			if (sm_id != "" && sm_id != null && sm_id != "null" && sm_id != "0" && !sm_id.equals("0")) {
				qry += " and screen_submodule_id = ? ";
			}

			if (qry == "") {
				q = " select DISTINCT ON( s.screen_name)screen_name as screen_name,  s.id as id, s.screen_module_id as screen_module_id, s.screen_submodule_id as screen_submodule_id,m.module_name as module_name,sub.submodule_name as submodule_name,s.screen_url as screen_url from tb_ldap_screen_master s,tb_ldap_module_master m,tb_ldap_submodule_master sub where   cast(s.screen_module_id as int) = m.id and  cast(s.screen_submodule_id as int) = sub.id and m.id > 0";
			} else {
				q = "select DISTINCT ON( s.screen_name)screen_name as screen_name, s.id as id, s.screen_module_id as screen_module_id, s.screen_submodule_id as screen_submodule_id,m.module_name as module_name,sub.submodule_name as submodule_name ,s.screen_url as screen_url from tb_ldap_screen_master s,tb_ldap_module_master m,tb_ldap_submodule_master sub  where    cast(s.screen_module_id as int) = m.id and  cast(s.screen_submodule_id as int) = sub.id and m.id > 0"
						+ qry;
			}
			stmt = conn.prepareStatement(q);

			int j = 1;
			/*
			 * if(m_id != "" && m_id != null && m_id !="null" && !m_id.equals("0") ) {
			 * stmt.setString(j, m_id); j += 1; } if(sm_id != "" && sm_id !=null && sm_id
			 * !="null" && sm_id != "0" && !sm_id.equals("0") ) { stmt.setString(j, sm_id);
			 * j += 1; }
			 */

			if (m_id != "" && m_id != null && m_id != "null" && !m_id.equals("0")) {
				stmt.setInt(j, Integer.parseInt(m_id));
				j += 1;
			}
			if (sm_id != "" && sm_id != null && sm_id != "null" && sm_id != "0" && !sm_id.equals("0")) {
				stmt.setInt(j, Integer.parseInt(sm_id));
				j += 1;
			}

			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();

			int columnCount = metaData.getColumnCount();
			while (rs.next()) {
				Map<String, Object> columns = new LinkedHashMap<String, Object>();

				for (int i = 1; i <= columnCount; i++) {
					columns.put(metaData.getColumnLabel(i), rs.getObject(i));
				}

				String Update = "onclick=\"  if (confirm('Are you sure you want to update?') ){Update('"
						+ rs.getObject(2) + "','" + rs.getObject(1) + "','" + rs.getObject(7) + "','" + rs.getObject(3)
						+ "','" + rs.getObject(4) + "')}else{ return false;}\"";
				String updateButton = "<i class='action_icons action_update' " + Update + " title='Edit Data'></i>";

				columns.put(metaData.getColumnLabel(2), updateButton);

				list.add(columns);
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// throw new RuntimeException(e);
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

	public List<Map<String, Object>> RoleSearchReport() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Connection conn = null;
		String q = "";

		try {
			conn = dataSource.getConnection();
			PreparedStatement stmt = null;
			q = "SELECT * FROM roleinformation ";

			stmt = conn.prepareStatement(q);
			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();

			int columnCount = metaData.getColumnCount();
			while (rs.next()) {
				Map<String, Object> columns = new LinkedHashMap<String, Object>();

				for (int i = 1; i <= columnCount; i++) {
					columns.put(metaData.getColumnLabel(i), rs.getObject(i));
				}
				list.add(columns);
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// throw new RuntimeException(e);
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

	@SuppressWarnings("unchecked")
	public Role getRoleNameListbyid(int role_id) {
		String hql = "FROM Role where roleId=:role_id order by id";
		List<Role> Role = null;
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			Query query = session.createQuery(hql);
			query.setParameter("role_id", role_id);
			Role = (List<Role>) query.list();
			tx.commit();
			session.close();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			return null;
		}
		if (Role.size() > 0) {
			return Role.get(0);
		} else {
			return null;
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////

	public List<Map<String, Object>> SearchUserBbyRole1(String access_lvl, String subaccess_lvl, String user_name,
			String user_sus_no) {
		{

			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			Connection conn = null;
			String q = "";
			String qry = "";
			try {
				conn = dataSource.getConnection();
				PreparedStatement stmt = null;

				if (access_lvl != "" && access_lvl != null && !access_lvl.equals("All")
						&& !access_lvl.equals("Username")) {
					qry += " and r.access_lvl = ?";
				}
				if (subaccess_lvl != "" && subaccess_lvl != null && !subaccess_lvl.equals("All")
						&& !subaccess_lvl.equals("Username")) {
					qry += " and r.sub_access_lvl  = ?";
				}
				if (user_sus_no != "" && user_sus_no != null && !user_sus_no.equals("All")
						&& !user_sus_no.equals("Username")) {
					if (access_lvl.equals("Line_dte")) {
						if (!user_sus_no.equals("0")) {
							qry += " and l.user_arm_code  = ?";
						}

					} else
						qry += " and l.user_sus_no  = ?";
				}

				if (access_lvl.equals("All")) {
					q = "select l.username, l.user_sus_no, l.user_formation_no, r. access_lvl,r.sub_access_lvl, r.role from logininformation l "
							+ " left join userroleinformation u on l.userid = u.user_id join roleinformation r  on  r.role_id =  u.role_id ";
				} else if (access_lvl.equals("Username")) {
					q = "select l.username, l.user_sus_no, l.user_formation_no, r. access_lvl,r.sub_access_lvl, r.role from logininformation l "
							+ " left join userroleinformation u on l.userid = u.user_id and l.username='" + user_name
							+ "' join roleinformation r  on  r.role_id =  u.role_id ";
				} else {
					q = "select l.username, l.user_sus_no, l.user_formation_no, r. access_lvl,r.sub_access_lvl, r.role from logininformation l "
							+ " left join userroleinformation u on l.userid = u.user_id join roleinformation r  on  r.role_id =  u.role_id "
							+ qry;
				}

				stmt = conn.prepareStatement(q);

				int j = 1;
				if (access_lvl != "" && access_lvl != null && !access_lvl.equals("All")
						&& !access_lvl.equals("Username")) {
					stmt.setString(j, access_lvl);
					j += 1;
				}
				if (subaccess_lvl != "" && subaccess_lvl != null && !subaccess_lvl.equals("All")
						&& !subaccess_lvl.equals("Username")) {
					stmt.setString(j, subaccess_lvl);
					j += 1;
				}
				if (user_sus_no != "" && user_sus_no != null && !user_sus_no.equals("All")
						&& !user_sus_no.equals("Username") && !user_sus_no.equals("0")) {
					stmt.setString(j, user_sus_no);
				}

				ResultSet rs = stmt.executeQuery();
				ResultSetMetaData metaData = rs.getMetaData();

				int columnCount = metaData.getColumnCount();
				while (rs.next()) {
					Map<String, Object> columns = new LinkedHashMap<String, Object>();

					for (int i = 1; i <= columnCount; i++) {
						columns.put(metaData.getColumnLabel(i), rs.getObject(i));
					}

					list.add(columns);
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
	}

	////////////////////////////////////////////////////////////////////////////////////

	public String getActiveData(String activeid) {
		Connection conn = null;
		Statement stmt;

		try {
			conn = dataSource.getConnection();
			stmt = (Statement) conn.createStatement();
			String sql = null;
			String modifydate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
			sql = "update logininformation set enabled ='1',modified_on ='"+new Date()+"',ac_dc_date = '"+ modifydate+"' where userId ='" + activeid + "' ";
			stmt.executeUpdate(sql);
			stmt.close();
			Statement stmt1 = (Statement) conn.createStatement();
			String sql1 = "INSERT INTO temp.logininformation(userid,username,password,enabled,accountnonexpired,accountnonlocked,credentialsnonexpired,login_name,created_on,modified_on,ac_dc_date) SELECT  * FROM public.logininformation WHERE public.logininformation.userid='"
					+ activeid + "'  ";
			stmt1.executeUpdate(sql1);
			stmt1.close();
			conn.close();
			return "User Actived Successfully";
		} catch (SQLException e) {
			System.out.println("CATCH : "+ e.getMessage());
			return "User Activation Failed!!";
		}
	}

	public String getDeactiveData(String userid) {
		Connection conn = null;
		Statement stmt;
		try {
			conn = dataSource.getConnection();
			stmt = (Statement) conn.createStatement();
			String sql = null;
			String modifydate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
			sql = "update logininformation set enabled ='0',modified_on = '" + new Date() + "',ac_dc_date = '" + modifydate + "' where userId ='" + userid + "' ";
			stmt.executeUpdate(sql);
			stmt.close();
			Statement stmt1 = (Statement) conn.createStatement();
			String sql1 = "INSERT INTO temp.logininformation(userid,username,password,enabled,accountnonexpired,accountnonlocked,credentialsnonexpired,login_name,created_on,modified_on,ac_dc_date) SELECT * FROM public.logininformation WHERE public.logininformation.userid='" + userid + "'  ";
			stmt1.executeUpdate(sql1);
			stmt1.close();
			conn.close();
			return "User Deactived Successfully";
		} catch (SQLException e) {
			e.printStackTrace();
			return "User Deactivation Failed!!";
		}
	}

	// status of inactive user
	public List<Map<String, Object>> getReportStatusOfInactiveUserList() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Connection conn = null;
		String q = null;
		try {
			conn = dataSource.getConnection();
			q = " SELECT t.* FROM ("
					+ "   select  DISTINCT l.userid,l.username , max(u.date)::date as date,( now()::date -max(u.date)::date ) as day1 from userlogincountinfo u inner join logininformation l on  u.userid=l.userid  group by 1,2  order by l.userid\r\n"
					+ ") t WHERE t.day1 > '30'";
			PreparedStatement stmt = conn.prepareStatement(q);
			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			while (rs.next()) {
				Map<String, Object> columns = new LinkedHashMap<String, Object>();

				for (int i = 1; i <= columnCount; i++) {
					columns.put(metaData.getColumnLabel(i), rs.getObject(i));
				}
				list.add(columns);
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

	///// user_mst update monika//////
	public UserLogin getUserLoginbyid(int id) {
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		Query q = session.createQuery("from UserLogin where id=:id");
		q.setParameter("id", id);
		UserLogin list = (UserLogin) q.list().get(0);
		session.getTransaction().commit();
		session.close();
		return list;
	}

	public List<Map<String, Object>> getRole(int updateid) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Connection conn = null;
		try {
			conn = dataSource.getConnection();

			PreparedStatement stmt = conn.prepareStatement(
					"SELECT l.userid,  l.username,  u_r.role_id,  r.role,  l.password,l.login_name FROM  logininformation l, roleinformation r,  userroleinformation u_r WHERE  l.userid = u_r.user_id AND u_r.role_id = r.role_id and userid= ? ");
			stmt.setInt(1, updateid);
			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();

			int columnCount = metaData.getColumnCount();
			while (rs.next()) {
				Map<String, Object> columns = new LinkedHashMap<String, Object>();
				for (int i = 1; i <= columnCount; i++) {
					columns.put(metaData.getColumnLabel(i), rs.getObject(i));
				}
				list.add(columns);
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

	public String UpdateUserMst(UserLogin updateid, int roll,String access_lve1, String sub_access_lve1) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String hql = "update UserLogin set login_name=:login_name,password=:password,modified_on='" + new Date()
				+ "' where userid =:userid";
		Query query = session.createQuery(hql).setString("password", updateid.getPassword()).setString("login_name", updateid.getLogin_name()).setInteger("userid", updateid.getUserId());
		query.executeUpdate();
		String hql1 = "update UserRole set role_id=:role_id,modified_on='" + new Date() + "' where user_id =:user_id";
		Query query1 = session.createQuery(hql1).setInteger("role_id", roll).setInteger("user_id",updateid.getUserId());
		int rowCount1 = query1.executeUpdate();
		tx.commit();
		session.close();
		String in = userinsertdata("update", updateid.getUserId(), roll);
		if (rowCount1 > 0 && in.equals("0")) {
			return "Updated Successfully";
		} else {
			return "Updated not Successfully";
		}
	}

	public String userinsertdata(String type, int i, int roll) {
		Connection conn = null;
		Statement stmt;
		try {
			conn = dataSource.getConnection();
			stmt = (Statement) conn.createStatement();
			String sql = null;
			String modifydate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
			if (type.equals("insert")) {
				sql = "INSERT INTO temp.logininformation(userid,username, password, enabled,accountnonexpired, accountnonlocked, credentialsnonexpired"
						+ ", login_name, created_on,ac_dc_date,army_no)"
						+ " SELECT userid,username,password,enabled,accountnonexpired,accountnonlocked,credentialsnonexpired,login_name,'"
						+ new Date() + "','" + modifydate+ "',army_no  "
						+ "FROM public.logininformation where public.logininformation.userid ='" + i + "'";
			} else if (type.equals("update")) {
				sql = "INSERT INTO temp.logininformation(userid,username, password, enabled,accountnonexpired, accountnonlocked, credentialsnonexpired"
						+ ",login_name, created_on,ac_dc_date,army_no)"
						+ " SELECT userid,username,password,enabled,accountnonexpired,accountnonlocked,credentialsnonexpired,login_name,'"
						+ new Date() + "','" + modifydate+ "',army_no   FROM public.logininformation where public.logininformation.userid ='" + i
						+ "'";
			}
			stmt.executeUpdate(sql);
			stmt.close();

			Statement stmt1 = (Statement) conn.createStatement();
			String sql1 = "INSERT INTO temp.userroleinformation(user_role_id,user_id, role_id, created_on ) "
					+ " SELECT user_role_id, " + i + ",'" + roll + "','" + new Date()
					+ "' FROM public.userroleinformation where public.userroleinformation.user_id ='" + i + "' ";
			stmt1.executeUpdate(sql1);
			stmt1.close();
			conn.close();
			return "0";
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "1";
	}

	public List<Map<String, Object>> getUserReport() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement stmt = null;

			String q = "select l.userid,l.username, l.user_sus_no, l.user_formation_no,r.role_id,"
					+ "l.password,l.login_name, r.role, r. access_lvl,r.sub_access_lvl, r.role from logininformation l "
					+ " left join userroleinformation u on l.userid = u.user_id"
					+ " join roleinformation r  on  r.role_id =  u.role_id  where l.accountnonlocked='1'";

			stmt = conn.prepareStatement(q);
			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			while (rs.next()) {
				Map<String, Object> columns = new LinkedHashMap<String, Object>();

				for (int i = 1; i <= columnCount; i++) {
					columns.put(metaData.getColumnLabel(i), rs.getObject(i));
				}

				String Update = "onclick=\"  if (confirm('Are you sure you want to update?') ){editData("
						+ rs.getObject(1) + ")}else{ return false;}\"";
				String updateButton = "<i class='action_icons action_update' " + Update + " title='Edit Data'></i>";
				String f = "";

				f += updateButton;
				columns.put(metaData.getColumnLabel(1), f);
				list.add(columns);
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// throw new RuntimeException(e);
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

	@SuppressWarnings("unchecked")
	public Boolean DashboardRedirect(String name, String roleid) {
		String hql = "from TB_LDAP_ROLEMASTER ro  where ro.role_url=:role_url and ro.role_id=:role_id ";
		List<TB_LDAP_ROLEMASTER> users = null;
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			Query query = session.createQuery(hql);
			query.setParameter("role_url", name);
			query.setParameter("role_id", Integer.parseInt(roleid));
			users = (List<TB_LDAP_ROLEMASTER>) query.list();
			tx.commit();
			session.close();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			return null;
		}
		if (users.size() > 0) {
			return true;
		}
		return false;
	}

	////////////
//	public DataSet<Map<String, Object>> DatatablesCriteriasUserreport(DatatablesCriterias criterias, String qry,
//			String roleSubAccess) {
//		List<Map<String, Object>> metadata = findDepartmentCriteriasforma1(criterias, qry, roleSubAccess);
//		Long countFiltered = getFilteredCountfo(criterias, qry);
//		Long count = getTotalCountfo(qry); //
//		return new DataSet<Map<String, Object>>(metadata, count, countFiltered);
//	}

//	private List<Map<String, Object>> findDepartmentCriteriasforma1(DatatablesCriterias criterias, String qry,
//			String roleSubAccess) {
//		String q = null;
//
//		/*
//		 * q.setFirstResult(criterias.getDisplayStart());
//		 * q.setMaxResults(criterias.getDisplaySize());
//		 */
//
//		System.out.println("criterias.getDisplayStart() == " + criterias.getDisplayStart());
//		System.out.println("criterias.getDisplaySize() == " + criterias.getDisplaySize());
//
//		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//
//		Connection conn = null;
//		try {
//			conn = dataSource.getConnection();
//			PreparedStatement stmt = null;
//
//			if (qry.equals("")) {
//				q = "select l.userid,l.username," + " l.login_name, r.role, r.access_lvl,r.sub_access_lvl "
//						+ " from logininformation l " + " left join userroleinformation u on l.userid = u.user_id"
//						+ " join roleinformation r  on  r.role_id =  u.role_id  where l.accountnonlocked='1'  order by l.userid limit "
//						+ criterias.getDisplaySize() + " OFFSET " + criterias.getDisplayStart();
//			} else {
//				q = "select l.userid,l.username," + " l.login_name, r.role, r.access_lvl,r.sub_access_lvl "
//						+ " from logininformation l " + " left join userroleinformation u on l.userid = u.user_id"
//						+ " join roleinformation r  on  r.role_id =  u.role_id  where l.accountnonlocked='1' " + qry
//						+ " order by l.userid limit " + criterias.getDisplaySize() + " OFFSET "
//						+ criterias.getDisplayStart();
//			}
//
//			q += getFilterQueryfo(criterias, q);
//
//			if (criterias.hasOneSortedColumn()) {
//				List<String> orderParams = new ArrayList<String>();
//
//				Iterator<String> itr2 = orderParams.iterator();
//				while (itr2.hasNext()) {
//					q += itr2.next();
//					if (itr2.hasNext()) {
//						q += " , ";
//					}
//				}
//			}
//
//			stmt = conn.prepareStatement(q);
//			ResultSet rs = stmt.executeQuery();
//			ResultSetMetaData metaData = rs.getMetaData();
//			int columnCount = metaData.getColumnCount();
//			int count = 0;
//			while (rs.next()) {
//				count += 1;
//				Map<String, Object> columns = new LinkedHashMap<String, Object>();
//				columns.put("sr", criterias.getDisplayStart() + count);
//				for (int i = 1; i <= columnCount; i++) {
//					columns.put(metaData.getColumnLabel(i), rs.getObject(i));
//				}
//
//				String Update = "onclick=\"  if (confirm('Are you sure you want to update?') ){editData("
//						+ rs.getObject(1) + ")}else{ return false;}\"";
//				String updateButton = "<i   class='action_icons action_update' " + Update + " title='Edit Data'></i>";
//				String f = "";
//
//				f += updateButton;
//				columns.put("Action", f);
//				list.add(columns);
//
//				System.err.println("columns---->>>>" + columns);
//			}
//			rs.close();
//			stmt.close();
//			conn.close();
//		} catch (SQLException e) {
//			// throw new RuntimeException(e);
//			e.printStackTrace();
//		} finally {
//			if (conn != null) {
//				try {
//					conn.close();
//				} catch (SQLException e) {
//				}
//			}
//		}
//		return list;
//	}

//	private Long getFilteredCountfo(DatatablesCriterias criterias, String qry) {
//		String q = null;
//
//		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//
//		Connection conn = null;
//		try {
//			conn = dataSource.getConnection();
//			PreparedStatement stmt = null;
//			if (qry.equals("")) {
//				q = "select l.userid,l.username,r.role_id,"
//						+ "l.password,l.login_name, r.role, r. access_lvl,r.sub_access_lvl, r.role from logininformation l "
//						+ " left join userroleinformation u on l.userid = u.user_id"
//						+ " join roleinformation r  on  r.role_id =  u.role_id  where l.accountnonlocked='1'  order by l.userid";
//			} else {
//				q = "select l.userid,l.username,r.role_id,"
//						+ "l.password,l.login_name, r.role, r. access_lvl,r.sub_access_lvl, r.role  from logininformation l "
//						+ " left join userroleinformation u on l.userid = u.user_id"
//						+ " join roleinformation r  on  r.role_id =  u.role_id  where l.accountnonlocked='1' " + qry
//						+ " order by l.userid";
//			}
//			q += getFilterQueryfo(criterias, q);
//
//			stmt = conn.prepareStatement(q);
//			ResultSet rs = stmt.executeQuery();
//			ResultSetMetaData metaData = rs.getMetaData();
//			int columnCount = metaData.getColumnCount();
//			while (rs.next()) {
//				Map<String, Object> columns = new LinkedHashMap<String, Object>();
//
//				for (int i = 1; i <= columnCount; i++) {
//					columns.put(metaData.getColumnLabel(i), rs.getObject(i));
//				}
//				list.add(columns);
//			}
//			rs.close();
//			stmt.close();
//			conn.close();
//		} catch (SQLException e) {
//			// throw new RuntimeException(e);
//			e.printStackTrace();
//		} finally {
//			if (conn != null) {
//				try {
//					conn.close();
//				} catch (SQLException e) {
//				}
//			}
//		}
//
//		return Long.parseLong(String.valueOf(list.size()));
//	}
//
//	private static StringBuilder getFilterQueryfo(DatatablesCriterias criterias, String queryBuilder2) {
//		StringBuilder queryBuilder = new StringBuilder();
//		List<String> paramList = new ArrayList<String>();
//		if (StringUtils.isNotBlank(criterias.getSearch()) && criterias.hasOneFilterableColumn()) {
//			if (!queryBuilder2.toString().contains("where")) {
//				queryBuilder.append(" WHERE ");
//			} else {
//				queryBuilder.append(" AND (");
//			}
//			for (ColumnDef columnDef : criterias.getColumnDefs()) {
//				if (columnDef.isFilterable() && StringUtils.isBlank(columnDef.getSearch())) {
//					if (columnDef.getName().equalsIgnoreCase("userid")) {
//						if (criterias.getSearch().matches("[0-9]+")) {
//							paramList.add(" " + columnDef.getName()
//									+ " = '?'".replace("?", criterias.getSearch().toLowerCase()));
//						}
//					} else {
//						paramList.add(" LOWER(" + columnDef.getName()
//								+ ") LIKE '%?%'".replace("?", criterias.getSearch().toLowerCase()));
//					}
//				}
//			}
//			Iterator<String> itr = paramList.iterator();
//			while (itr.hasNext()) {
//				queryBuilder.append(itr.next());
//				if (itr.hasNext()) {
//					queryBuilder.append(" OR ");
//				}
//			}
//			queryBuilder.append(")");
//		}
//		return queryBuilder;
//	}

	private Long getTotalCountfo(String qry) {

		int columnCount = 0;
		String q = null;
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement stmt = null;
			if (qry.equals("")) {
				q = "select COUNT(l.userid) from logininformation l "
						+ " left join userroleinformation u on l.userid = u.user_id"
						+ " join roleinformation r  on  r.role_id =  u.role_id  where l.accountnonlocked='1'  ";
			} else {
				q = "select COUNT(l.userid) from logininformation l "
						+ " left join userroleinformation u on l.userid = u.user_id"
						+ " join roleinformation r  on  r.role_id =  u.role_id  where l.accountnonlocked='1' " + qry;
			}

			stmt = conn.prepareStatement(q);
			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			columnCount = metaData.getColumnCount();
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
		return (long) columnCount;
	}

	/////// Active Deavtive report////////////

//	public DataSet<Map<String, Object>> DatatablesCriteriasActiveUserreport(DatatablesCriterias criterias, String qry,
//			String roleSubAccess, String Status) { //
//
//		List<Map<String, Object>> metadata = findDepartmentCriteriasActive(criterias, qry, roleSubAccess, Status); //
//		Long countFiltered = getFilteredCountActive(criterias, qry); //
//		Long count = getTotalCountfoactive(qry); //
//		return new DataSet<Map<String, Object>>(metadata, count, countFiltered);
//	}

//	private List<Map<String, Object>> findDepartmentCriteriasActive(DatatablesCriterias criterias, String qry,
//			String roleSubAccess, String Status) {
//
//		StringBuilder queryBuilder = null;
//
//		if (qry.equals("")) {
//			queryBuilder = new StringBuilder("FROM UserLogin d ");
//		} else {
//			queryBuilder = new StringBuilder("FROM UserLogin d where " + qry);
//		}
//
//		queryBuilder.append(getFilterQueryfoactive(criterias, queryBuilder));
//
//		if (criterias.hasOneSortedColumn()) {
//			List<String> orderParams = new ArrayList<String>();
//
//			Iterator<String> itr2 = orderParams.iterator();
//			while (itr2.hasNext()) {
//				queryBuilder.append(itr2.next());
//				if (itr2.hasNext()) {
//					queryBuilder.append(" , ");
//				}
//			}
//		}
//		Session session = this.sessionFactory.openSession();
//		Transaction tx = session.beginTransaction();
//		Query q = session.createQuery(queryBuilder.toString());
//
//		/**
//		 * Step 3: paging
//		 */
//		q.setFirstResult(criterias.getDisplayStart());
//		q.setMaxResults(criterias.getDisplaySize());
//
//		@SuppressWarnings("unchecked")
//		List<UserLogin> list1 = (List<UserLogin>) q.list();
//		tx.commit();
//
//		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//		for (int i = 0; i < list1.size(); i++) {
//			Map<String, Object> columns = new LinkedHashMap<String, Object>();
//			columns.put("sr", criterias.getDisplayStart() + i + 1);
//			/* columns.put("userid", list1.get(i).getUserId()); */
//			columns.put("login_name", list1.get(i).getLogin_name());
//			columns.put("userName", list1.get(i).getUserName());
//			columns.put("ac_dc_date", list1.get(i).getAc_dc_date());
//
//			String ActiveButton = "";
//			String DeactiveButton = "";
//
//			if (Status.equals("0")) {
//				String Active = "onclick=\"  if (confirm('Are you sure you want to Active?') ){ActiveData("
//						+ list1.get(i).getUserId() + ")}else{ return false;}\"";
//				ActiveButton = "<i id='thAction1' style='color: blue; text-decoration: underline;font-weight:bold;' "
//						+ Active + " title='Active Data'>Active</i>";
//			} else if (Status.equals("1")) {
//				String Deactive = "onclick=\"  if (confirm('Are you sure you want to Deactive?') ){DeactiveData("
//						+ list1.get(i).getUserId() + ")}else{ return false;}\"";
//				DeactiveButton = "<i id='thAction1' style='color: blue; text-decoration: underline;font-weight:bold;' "
//						+ Deactive + " title='Active Data'>Deactive</i>";
//			}
//
//			String f = "";
//			if (Status.equals("0")) {
//				f += ActiveButton;
//			} else if (Status.equals("1")) {
//				f += DeactiveButton;
//			}
//			columns.put("Action", f);
//
//			list.add(columns);
//		}
//		session.close();
//		return list;
//	}
//
//	@SuppressWarnings("unchecked")
//	private Long getFilteredCountActive(DatatablesCriterias criterias, String qry) { //
//		StringBuilder queryBuilder = null;
//
//		if (qry.equals("")) {
//			queryBuilder = new StringBuilder("SELECT d FROM UserLogin d ");
//		} else {
//			queryBuilder = new StringBuilder("SELECT d FROM UserLogin d where " + qry + " ");
//		}
//
//		queryBuilder.append(getFilterQueryfoactive(criterias, queryBuilder));
//
//		Session session = this.sessionFactory.openSession();
//		Transaction tx = session.beginTransaction();
//		Query q = session.createQuery(queryBuilder.toString());
//		/**
//		 * paging
//		 */
//		/*
//		 * q.setFirstResult(criterias.getDisplayStart());
//		 * q.setMaxResults(criterias.getDisplaySize());
//		 */
//		List<UserLogin> list = (List<UserLogin>) q.list();
//		tx.commit();
//		session.close();
//		return Long.parseLong(String.valueOf(list.size()));
//	}
//
//	private static StringBuilder getFilterQueryfoactive(DatatablesCriterias criterias, StringBuilder queryBuilder1) {
//		StringBuilder queryBuilder = new StringBuilder();
//		List<String> paramList = new ArrayList<String>();
//		if (StringUtils.isNotBlank(criterias.getSearch()) && criterias.hasOneFilterableColumn()) {
//			if (!queryBuilder1.toString().contains("where")) {
//				queryBuilder.append(" WHERE ");
//			} else {
//				queryBuilder.append(" AND (");
//			}
//			for (ColumnDef columnDef : criterias.getColumnDefs()) {
//				if (columnDef.isFilterable() && StringUtils.isBlank(columnDef.getSearch())) {
//					if (columnDef.getName().equalsIgnoreCase("ac_dc_date") || columnDef.getName().equalsIgnoreCase("sr")
//							|| columnDef.getName().equalsIgnoreCase("Action")) {
//						if (criterias.getSearch().matches("[0-9]+")) {
//							paramList.add(" d." + columnDef.getName()
//									+ " = '?'".replace("?", criterias.getSearch().toLowerCase()));
//						}
//					} else {
//						paramList.add(" LOWER(d." + columnDef.getName()
//								+ ") LIKE '%?%'".replace("?", criterias.getSearch().toLowerCase()));
//					}
//				}
//			}
//			Iterator<String> itr = paramList.iterator();
//			while (itr.hasNext()) {
//				queryBuilder.append(itr.next());
//				if (itr.hasNext()) {
//					queryBuilder.append(" OR ");
//				}
//			}
//			queryBuilder.append(")");
//		}
//		return queryBuilder;
//	}

	private Long getTotalCountfoactive(String qry) { //
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Query q = null;
		if (qry.equals("")) {
			q = session.createQuery("SELECT COUNT(d) FROM UserLogin d ");
		} else {
			q = session.createQuery("SELECT COUNT(d) FROM UserLogin d where " + qry);
		}
		Long count = (Long) q.list().get(0);
		tx.commit();
		session.close();
		return count;
	}

	/// Start Search User Mst
		public List<Map<String, Object>> getUserReportList1(int startPage, String pageLength, String Search,
				String orderColunm, String orderType, String qry, String roleSubAccess, HttpSession session, String roleid)
				throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException,
				InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
			String SearchValue = GenerateQueryWhereClause_SQL(Search, qry);
			if (pageLength.equals("-1")) {
				pageLength = "ALL";
			}

			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			Connection conn = null;
			String q = "";
			try {
				conn = dataSource.getConnection();
				if (qry.equals("")) {
					q = "select l.userid,l.username," + " l.login_name, r.role, r.access_lvl,r.sub_access_lvl "
							+ " from logininformation l " + " left join userroleinformation u on l.userid = u.user_id\n"
							+ " join roleinformation r  on  r.role_id =  u.role_id  where l.accountnonlocked='1' \n"
							+ SearchValue + " order by l.userid \n " + orderType + " limit " + pageLength + " OFFSET "
							+ startPage;

				} else {
					q = "select l.userid,l.username," + " l.login_name, r.role, r.access_lvl,r.sub_access_lvl \n"
							+ " from logininformation l " + " left join userroleinformation u on l.userid = u.user_id\n"
							+ " join roleinformation r  on  r.role_id =  u.role_id  where l.accountnonlocked='1' " + qry
							+ SearchValue + " order by l.userid \n " + orderType + " limit " + pageLength + " OFFSET "
							+ startPage;
				}

				PreparedStatement stmt = conn.prepareStatement(q);
				stmt = setQueryWhereClause_SQL(stmt, Search, qry);
				ResultSet rs = stmt.executeQuery();
				ResultSetMetaData metaData = rs.getMetaData();
				int columnCount = metaData.getColumnCount();
				while (rs.next()) {
					Map<String, Object> columns = new LinkedHashMap<String, Object>();
					for (int i = 1; i <= columnCount; i++) {
						columns.put(metaData.getColumnLabel(i), rs.getObject(i));
					}
					String f = "";
					String Update = "onclick=\"  if (confirm('Are you sure you want to update?') ){editData("
							+ rs.getObject(1) + ")}else{ return false;}\"";
					String updateButton = "<i   class='action_icons action_update' " + Update + " title='Edit Data'></i>";
					f += updateButton;
					columns.put(metaData.getColumnLabel(1), f);
					list.add(columns);
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

		public long getUserReportListTotalCount1(String Search, String qry) {
			String SearchValue = GenerateQueryWhereClause_SQL(Search, qry);
			int total = 0;
			String q = null, whr = "";
			Connection conn = null;
			try {
				conn = dataSource.getConnection();

				if (qry.equals("")) {
					q = "select count(*) from (select l.userid,l.username, l.login_name, r.role, r.access_lvl,r.sub_access_lvl  \r\n"
							+ "from logininformation l  left join userroleinformation u on l.userid = u.user_id join roleinformation r  on  r.role_id =  u.role_id \r\n"
							+ "where l.accountnonlocked='1' " + whr + SearchValue + ") e ";
				} else {
					q = "select count(*) from (select l.userid,l.username, l.login_name, r.role, r.access_lvl,r.sub_access_lvl  \r\n"
							+ "from logininformation l  left join userroleinformation u on l.userid = u.user_id join roleinformation r  on  r.role_id =  u.role_id \r\n"
							+ "where l.accountnonlocked='1' " + qry + SearchValue + ") e ";
				}

				PreparedStatement stmt = conn.prepareStatement(q);
				stmt = setQueryWhereClause_SQL(stmt, Search, qry);
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					total = rs.getInt(1);
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
			return (long) total;
		}

		public String GenerateQueryWhereClause_SQL(String Search, String qry) {
			String SearchValue = "";
			if (!Search.equals("")) {
				SearchValue = "and ";
				Search = Search.toLowerCase();
				if (checkIsIntegerValue(Search)) {
					SearchValue += "  (l.username::text like ?)";
				} else if (Search.contains("-")) {
					SearchValue += "  (to_char(im.inward_dt,'dd-mm-yyyy') like ? "
							+ "or to_char(im.letter_date,'dd-mm-yyyy') like ? )";
				} else {
					SearchValue += "  ( lower(l.login_name) like ? " + " or lower(l.username) like ? "
							+ " or lower(r.access_lvl) like ? " + "or lower(r.sub_access_lvl) like ? "
							+ "or lower(r.role) like ?  )";
				}
			}
			return SearchValue;
		}

		public PreparedStatement setQueryWhereClause_SQL(PreparedStatement stmt, String Search, String qry) {
			int flag = 0;
			try {
				if (!Search.equals("")) {
					if (checkIsIntegerValue(Search)) {
						flag += 1;
						stmt.setString(flag, "%" + Search + "%");
					} else if (Search.contains("-")) {
						flag += 1;
						stmt.setString(flag, "%" + Search + "%");

						flag += 1;
						stmt.setString(flag, "%" + Search + "%");

					} else {
						flag += 1;
						stmt.setString(flag, "%" + Search.toLowerCase() + "%");

						flag += 1;
						stmt.setString(flag, "%" + Search.toLowerCase() + "%");

						flag += 1;
						stmt.setString(flag, "%" + Search.toLowerCase() + "%");

						flag += 1;
						stmt.setString(flag, "%" + Search.toLowerCase() + "%");

						flag += 1;
						stmt.setString(flag, "%" + Search.toLowerCase() + "%");

					}
				}
			} catch (Exception e) {
			}
			return stmt;
		}

		/// EnD Search User Mst
	
//	public List<String> getLayoutlist() {
//		List<String> list = new ArrayList<>();
//		Connection conn = null;
//		try {
//			conn = dataSource.getConnection();
//			PreparedStatement stmt = null;
//			String sql = "SELECT t.msg from tb_hd_mercuries t WHERE t.valid_upto >= now() ";
//			stmt = conn.prepareStatement(sql);
//			ResultSet rs = stmt.executeQuery();
//			while (rs.next()) {
//				list.add(rs.getString("msg"));
//			}
//			rs.close();
//			stmt.close();
//			conn.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if (conn != null) {
//				try {
//					conn.close();
//				} catch (SQLException e) {
//				}
//			}
//		}
//		return list;
//	}
		
		// Using DataTable With MockJax Report for SQL
		@SuppressWarnings("unchecked")
		public ArrayList<ArrayList<String>> SearchUserStatus_DataTableMockJaxList_SQL(int startPage, int pageLength,
				String Search, String orderColunm, String orderType, String access_lvl, String login_name, String export,
				HttpSession sessionUserId)
				throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException,
				InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {

			String pageL = "";
			if (pageLength == -1) {
				pageL = "ALL";
			} else {
				pageL = String.valueOf(pageLength);
			}

			String SearchValue = GenerateQueryWhereClause_SQL1(Search);
			ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
			Connection conn = null;
			String q = "", whr = "";
			try {
				conn = dataSource.getConnection();

				if (export.equals("")) {
					if (!access_lvl.equals("")) {
						whr += "  l.accountNonLocked=?";
					}
					if (!login_name.equals("")) {
						whr += " and  l.username=?";
					}
				}
				q = "SELECT * FROM(select  ROW_NUMBER() OVER(order by l.userid DESC) as srno,l.userid,l.username,to_char(l.created_on,'dd-mm-yyyy') as created_on,l.accountNonLocked, r.role_type,r.sub_access_lvl,l.userid from logininformation l \r\n"
						+ "inner join userroleinformation ur on l.userid=ur.user_id\r\n"
						+ "left join roleinformation r on r.role_id=ur.role_id \r\n" + "where  " + whr + " " + SearchValue
						+ " ORDER BY " + orderColunm + " " + orderType + " limit " + pageL + " OFFSET " + startPage
						+ ") e ";

				PreparedStatement stmt = conn.prepareStatement(q);

				int flag = 0;
				if (export.equals("")) {
					if (!access_lvl.equals("")) {
						flag += 1;
						stmt.setInt(flag, Integer.parseInt(access_lvl));
					}
					if (!login_name.equals("")) {
						flag += 1;
						stmt.setString(flag, login_name);
					}
				}
				stmt = setQueryWhereClause_SQL1(stmt, Search, access_lvl, login_name);
				ResultSet rs = stmt.executeQuery();
				int j = 1;
				ResultSetMetaData metaData = rs.getMetaData();
				int columnCount = metaData.getColumnCount();
				while (rs.next()) { /*-------------------------HEENA---------------------*/
					ArrayList<String> alist = new ArrayList<String>();
					alist.add(rs.getString("srno"));// 0
					alist.add(rs.getString("sub_access_lvl"));// 1
					alist.add(rs.getString("role_type"));// 2
					// alist.add(rs.getString("unit_name"));//3
					alist.add(rs.getString("username"));// 4
					alist.add(rs.getString("created_on"));// 5

					String ActiveButton = "";
					String DeactiveButton = "";
					String enckey = "commonPwdEncKeys";
					Cipher c = hex_asciiDao.EncryptionSHA256Algo(sessionUserId, enckey);
					String EncryptedPk = new String(
							Base64.encodeBase64(c.doFinal(rs.getString("userid").toString().getBytes())));

					String hidden = "<input type='hidden' id='hid" + j + "' value='" + EncryptedPk + "'/> ";
					System.err.println("access_lvl-user--" + access_lvl);
					if (access_lvl.equals("0")) {
						String Active = "onclick=\"  if (confirm('Are you sure you want to Active?') ){ActiveData("
								+ rs.getString("userid") + ")}else{ return false;}\"";
						ActiveButton = "<i class='action_icons action_approve actvt'  title='Active Data'></i>";
					} else if (access_lvl.equals("1")) {
						String Deactive = "onclick=\"  if (confirm('Are you sure you want to Deactive?') ){DeactiveData("
								+ rs.getString("userid") + ")}else{ return false;}\"";
						DeactiveButton = "<i class='action_icons action_cancel dactvt' title='DeActive Data'></i>";
					} else {
						String Deactive = "onclick=\"  if (confirm('Are you sure you want to Deactive?') ){DeactiveData("
								+ rs.getString("userid") + ")}else{ return false;}\"";
						DeactiveButton = "<i class='action_icons action_cancel dactvt' title='DeActive Data'></i>";
					}

					String f = "";
					if (access_lvl.equals("0")) {
						f += ActiveButton;
					} else if (access_lvl.equals("1")) {
						f += DeactiveButton;
					} else {
						f += DeactiveButton;
					}
					alist.add(f + hidden);// 7
					list.add(alist);
					j++;
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

		public long SearchUserStatus_DataTableMockJaxTotalCount_SQL(String Search, String access_lvl, String login_name) {
			ArrayList<ArrayList<String>> list1 = new ArrayList<ArrayList<String>>();
			String SearchValue = GenerateQueryWhereClause_SQL1(Search);
			int total = 0;
			String q = "", whr = "";
			Connection conn = null;
			try {
				conn = dataSource.getConnection();

				if (!access_lvl.equals("")) {
					whr += " l.accountNonLocked=?";
				}
				if (!login_name.equals("")) {
					whr += " and  l.username=?";
				}

				q = "SELECT COUNT(*) FROM(select  ROW_NUMBER() OVER(order by l.userid DESC) as srno,l.username,to_char(l.created_on,'dd-mm-yyyy') as created_on,l.accountNonLocked, r.role_type,r.sub_access_lvl from logininformation l \r\n"
						+ "inner join userroleinformation ur on l.userid=ur.user_id\r\n"
						+ "left join roleinformation r on r.role_id=ur.role_id \r\n" + "where  " + whr + " " + SearchValue
						+ ") e ";

				PreparedStatement stmt = conn.prepareStatement(q);
				int flag = 0;
				if (!access_lvl.equals("")) {
					flag += 1;
					stmt.setInt(flag, Integer.parseInt(access_lvl));
				}
				if (!login_name.equals("")) {
					flag += 1;
					stmt.setString(flag, login_name);
				}
				stmt = setQueryWhereClause_SQL1(stmt, Search, access_lvl, login_name);
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					total = rs.getInt(1);
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
			return total;
		}

		public String GenerateQueryWhereClause_SQL1(String Search) {
			String SearchValue = "";

			if (!Search.equals("")) { // for Input Filter
				Search = Search.toLowerCase();
				if (checkIsIntegerValue(Search)) {
					SearchValue += "and (l.userid=? or to_char(l.created_on,'dd-mm-yyyy') like ? ) ";
				} else if (Search.contains("-")) {
					SearchValue += "and (to_char(l.created_on,'dd-mm-yyyy') like ?  or lower(r.sub_access_lvl) like ?) ";
				} else {
					SearchValue += "and ( lower(l.username) like ? " + " or lower(r.role_type) like ? "
							+ " or lower(r.sub_access_lvl) like ? " + "or lower(m.unit_name) like ?) ";
				}
			}

			return SearchValue;
		}

		public PreparedStatement setQueryWhereClause_SQL1(PreparedStatement stmt, String Search, String access_lvl,
				String login_name) {
			int flag = 0;
			try {
				if (!access_lvl.equals("")) {
					flag += 1;
					stmt.setInt(flag, Integer.parseInt(access_lvl));
				}
				if (!login_name.equals("")) {
					flag += 1;
					stmt.setString(flag, login_name);
				}
				if (!Search.equals("")) {
					if (checkIsIntegerValue(Search)) {
						flag += 1;
						stmt.setInt(flag, Integer.parseInt(Search));
						flag += 1;
						stmt.setString(flag, "%" + Search + "%");
					} else if (Search.contains("-")) {
						flag += 1;
						stmt.setString(flag, "%" + Search + "%");
						flag += 1;
						stmt.setString(flag, "%" + Search + "%");
					} else {
						flag += 1;
						stmt.setString(flag, "%" + Search.toLowerCase() + "%");
						flag += 1;
						stmt.setString(flag, "%" + Search.toLowerCase() + "%");
						flag += 1;
						stmt.setString(flag, "%" + Search.toLowerCase() + "%");
						flag += 1;
						stmt.setString(flag, "%" + Search.toLowerCase() + "%");
					}
				}

			} catch (Exception e) {
			}
			return stmt;
		}
		
		public ArrayList<ArrayList<String>> getLastLogin(int userId) {
			ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();

			Connection conn = null;
			String q = "";
			String qry = "";
			try {
				conn = dataSource.getConnection();
				PreparedStatement stmt = null;

				if (userId != 0) {
					qry += " WHERE userid = ?";
				}

				q = "SELECT TO_CHAR(date, 'dd-mm-yyyy HH24:MI:SS') as lastlogin FROM userlogincountinfo " + qry
						+ " ORDER BY date DESC limit 1 OFFSET 1 ";
				stmt = conn.prepareStatement(q);
				if (userId != 0) {
					stmt.setInt(1, userId);
				}

				ResultSet rs = stmt.executeQuery();
				ResultSetMetaData metaData = rs.getMetaData();

				int columnCount = metaData.getColumnCount();
				while (rs.next()) {
					ArrayList<String> alist = new ArrayList<String>();
					alist.add(rs.getString("lastlogin"));

					list.add(alist);
				}
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// throw new RuntimeException(e);
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
}
