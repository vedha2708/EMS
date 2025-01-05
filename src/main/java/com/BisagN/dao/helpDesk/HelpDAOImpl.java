package com.BisagN.dao.helpDesk;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.BisagN.dao.HexatoAsciiDAO;
import com.BisagN.dao.HexatoAsciiDAOImpl;

@Service
public class HelpDAOImpl implements HelpDAO {

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@Autowired
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	HexatoAsciiDAO hex_asciiDao = new HexatoAsciiDAOImpl();
	
	public boolean checkIsIntegerValue(String Search) {
		return Search.matches("[0-9]+");
	}

	String qry_list = "";

	public List<Map<String, Object>> getExpectedPendingReceivedDetails(String unit, String year, String doctype) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Connection conn = null;
		int p = 0;
		String doc = "";
		String doc_expected = "", qry_list1 = "", whr = "", whr1 = "";

		if (!unit.equals("")) {
			whr += " and a.unit_code::text =?";
		}
		if (!year.equals("")) {
			whr += " and a.acr_cycle_year::text =?";
		}
		
		if (!unit.equals("")) {
			whr1 += "b.unit_code::text =?";
		}
		if (!year.equals("")) {
			whr1 += " and b.year::text =?";
		}
//		if(!doctype.equals("")) {
//			whr += " and a.document_type::text =?";
//		}

		List<Map<String, Object>> ListofDoc = getListofDoc();
		for (int i = 0; i < ListofDoc.size(); i++) {



//			doc_expected = doc_expected + "COUNT(CASE WHEN b.rank_code= " + ListofDoc.get(i).get("rank_code")
//					+ " THEN b.rank_code END) AS "
//					+ (ListofDoc.get(i).get("rank_subject")).toString().replaceAll("\\s", "").toLowerCase();
			
			doc_expected = doc_expected + "sum(b.expected) filter (where b.rank_code= " + ListofDoc.get(i).get("rank_code")
					+ ") AS "
					+ (ListofDoc.get(i).get("rank_subject")).toString().replaceAll("\\s", "").toLowerCase();

			doc = doc + "COUNT(CASE WHEN a.rank_code= " + ListofDoc.get(i).get("rank_code")
					+ " THEN a.rank_code END) AS "
					+ (ListofDoc.get(i).get("rank_subject")).toString().replaceAll("\\s", "").toLowerCase();

			if (p < ListofDoc.size() - 1) {
				doc = doc + ",\n";
				doc_expected = doc_expected + ",\n";
			}
			p++;
		}

		try {
			conn = dataSource.getConnection();
//			qry_list1 = "Select e.doc_type, case when others is null then '0' else others end as others, case when mcpo1 is null then '0' else mcpo1 end as mcpo1, \r\n"
//					+ "case when mcpo2 is null then '0' else mcpo2 end as mcpo2, case when cpo is null then '0' else cpo end as cpo,\r\n"
//					+ "case when po is null then '0' else po end as po, case when poaa is null then '0' else poaa end as poaa, \r\n"
//					+ "case when ls is null then '0' else ls end as ls from ( \r\n"
//					+ "select 'Expected' as doc_type,\n"
//					+ "sum(b.expected) filter (where b.rank_code= 0) AS others ,\r\n"
//					+ "sum(b.expected) filter (where b.rank_code= 1) AS mcpo1,\r\n"
//					+ "sum(b.expected) filter (where b.rank_code= 3) AS mcpo2,\r\n"
//					+ "sum(b.expected) filter (where b.rank_code= 5) AS cpo,\r\n"
//					+ "sum(b.expected) filter (where b.rank_code= 7) AS po,\r\n"
//					+ "sum(b.expected) filter (where b.rank_code= 8) AS poaa,\r\n"
//					+ "sum(b.expected) filter (where b.rank_code= 9) AS ls \r\n" + "from  q_mst_expected_cr b where " + whr1 + "\r\n"
//					+ "UNION \r\n" + "select 'Received' as doc_type,\n"
//					+ "COUNT(CASE WHEN a.rank_code= 0 THEN a.rank_code END) AS others,\r\n"
//					+ "COUNT(CASE WHEN a.rank_code= 1 THEN a.rank_code END) AS mcpo1,\r\n"
//					+ "COUNT(CASE WHEN a.rank_code= 3 THEN a.rank_code END) AS mcpo2,\r\n"
//					+ "COUNT(CASE WHEN a.rank_code= 5 THEN a.rank_code END) AS cpo,\r\n"
//					+ "COUNT(CASE WHEN a.rank_code= 7 THEN a.rank_code END) AS po,\r\n"
//					+ "COUNT(CASE WHEN a.rank_code= 8 THEN a.rank_code END) AS poaa,\r\n"
//					+ "COUNT(CASE WHEN a.rank_code= 9 THEN a.rank_code END) AS ls\r\n"
//					+ "from  q_tbl_inmail a where a.status='ACCEPTED' and document_type ='1' " + whr + " \r\n" + "\r\n"
//					+ ")e  \r\n" + "order by (case doc_type when 'Expected' then 1\r\n"
//					+ "when 'Received' then 2 end)     ";
			qry_list1 = "Select e.doc_type, case when others is null then '0' else others end as others, case when mcpoi is null then '0' else mcpoi end as mcpo1, \r\n"
					+ "case when mcpoii is null then '0' else mcpoii end as mcpo2, case when cpo is null then '0' else cpo end as cpo,\r\n"
					+ "case when po is null then '0' else po end as po, case when po_aa is null then '0' else po_aa end as poaa,  \r\n"
					+ "case when ls is null then '0' else ls end as ls from ( \r\n"
					+ "select 'Expected' as doc_type, \n" + doc_expected + " \n from  q_mst_expected_cr b where " + whr1 + "\r\n"
					+ "UNION \r\n" + "select 'Received' as doc_type, \n"+doc+"\n"
					+ "from  q_tbl_inmail a where a.status='ACCEPTED' and document_type ='1' " + whr + " \r\n" + "\r\n"
					+ ")e  \r\n" + "order by (case doc_type when 'Expected' then 1\r\n"
					+ "when 'Received' then 2 end)     ";

//			}

			PreparedStatement stmt = conn.prepareStatement(qry_list1);
			int flag = 0;
			if (unit != "0") {
				flag += 1;
				stmt.setString(flag, unit);
			}
			if (!year.equals("")) {
				flag += 1;
				stmt.setString(flag, year);
			}
			if (unit != "0") {
				flag += 1;
				stmt.setString(flag, unit);
			}
			if (!year.equals("")) {
				flag += 1;
				stmt.setString(flag, year);
			}
//			if(!doctype.equals("")) {
//				flag += 1;
//				stmt.setString(flag, doctype);
//			}
			// ResultSet rs = null;
			try {
				System.out.println("---stmt--third==ss== report--" + stmt);
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
			} catch (Exception e) {
				// TODO: handle exception
			}
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

	public ArrayList<ArrayList<String>> getNewfaqdetail1() {
		ArrayList<ArrayList<String>> alist = new ArrayList<ArrayList<String>>();
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			String sql = "";
			Statement stmt = conn.createStatement();
			sql += "  select question,answer,section,id from hd_tb_bisag_faq order by section ";
			ResultSet rs = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			while (rs.next()) {
				ArrayList<String> list = new ArrayList<String>();
				for (int i = 1; i < columnCount + 1; i++) {
					String name = rs.getString(i);
					list.add(name);
				}
				alist.add(list);
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
		return alist;
	}

	public List<String> getsecList() {
		List<String> alist = new ArrayList<>();
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			String sql = "";
			Statement stmt = conn.createStatement();
			sql += "  select distinct section from hd_tb_bisag_faq order by section ";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String name = rs.getString(1);
				alist.add(name);
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
		return alist;
	}

	public List<String> getqueList() {
		List<String> alist = new ArrayList<String>();
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			String sql = "";
			Statement stmt = conn.createStatement();
			sql += "select question,answer,id from hd_tb_bisag_faq order by section";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String name = rs.getString(1);
				alist.add(name);
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
		return alist;
	}

	public List<Map<String, Object>> getSupportRequestJdbc() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Connection conn = null;
		String qry = "";
		try {
			conn = dataSource.getConnection();
			qry += "select count(ticket_status)::int as total, ticket_status from ticket_generate where ticket_status!='' GROUP BY ticket_status ORDER BY 2";
			PreparedStatement stmt = conn.prepareStatement(qry);
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

	public List<Map<String, Object>> getActiveUserListJdbc(String qry) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement(qry);
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

	public List<Map<String, Object>> getUserLoginCountJdbc(String qry) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement(qry);
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

	public List<Map<String, Object>> getActiveUserCountJdbc(String qry) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement(qry);
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

	public List<Map<String, Object>> getNewTicketCountJdbc(String qry) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement(qry);
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

	public List<Map<String, Object>> getPendigTicketCountJdbc(String qry) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement(qry);
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

	public List<Map<String, Object>> getCompletedTicketCountJdbc(String qry) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement(qry);
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

	public List<Map<String, Object>> getRoleCountJdbc(String qry) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement(qry);
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

	public List<Map<String, Object>> getUserCountJdbc(String qry) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement(qry);
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

	public List<Map<String, Object>> getfeedbackrecCountJdbc(String qry) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement(qry);
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

	public List<Map<String, Object>> getFeatureReqCountJdbc(String qry) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement(qry);
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

	public List<Map<String, Object>> searchfaqList(String section) {
		{
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			Connection conn = null;
			String q = "";
			String qry = "";
			try {
				conn = dataSource.getConnection();
				PreparedStatement stmt = null;
				if (section != "" && section != null) {
					qry += "section like ?";
				}
				q = "select id,answer,question from hd_tb_bisag_faq where " + qry;
				stmt = conn.prepareStatement(q);
				if (section != "" && section != null) {
					stmt.setString(1, section + "%");
				}
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
					String Delete1 = "onclick=\"  if (confirm('Are you sure you want to delete?') ){deleteData("
							+ rs.getObject(1) + ")}else{ return false;}\"";
					String deleteButton = "<i class='action_icons action_delete' " + Delete1
							+ " title='Delete Data'></i>";
					String f = "";
					f += updateButton;
					f += deleteButton;
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
	}

	public List<Map<String, Object>> loggedin_report(List<String> userlist) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Connection conn = null;
		String q = "";
		try {
			String whr = "";
			for (int i = 0; i < userlist.size(); i++) {
				if (i == 0) {
					whr += " a.username = ? ";
				} else {
					whr += " or a.username = ? ";
				}
			}
			conn = dataSource.getConnection();
			PreparedStatement stmt = null;
			q = "select distinct a.userid,a.login_name,a.username\r\n" + " from logininformation a \r\n" + " where "
					+ whr;
			stmt = conn.prepareStatement(q);
			for (int i = 1; i <= userlist.size(); i++) {
				stmt.setString(i, userlist.get(i - 1));
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

	public String getSupportRequest() {
		List<Map<String, Object>> list = getSupportRequestJdbc();
		String list1 = "";
		for (int i = 0; i < list.size(); i++) {
			String doc_type = "";
			if (list.get(i).get("ticket_status").equals("0")) {
				doc_type = "New";
			}
			if (list.get(i).get("ticket_status").equals("1")) {
				doc_type = "In Progress";
			}
			if (list.get(i).get("ticket_status").equals("2")) {
				doc_type = "Completed";
			}
			if (list.get(i).get("ticket_status").equals("3")) {
				doc_type = "Feedback";
			}
			if (list.get(i).get("ticket_status").equals("4")) {
				doc_type = "Feature Request";
			}
			list1 += ",{'ticket_status' : '" + doc_type + "' ,total:\"" + list.get(i).get("total") + "\"}";
		}
		if (list1.length() > 0) {
			list1 = "[" + list1.substring(1, list1.length());
			list1 += "]";
		} else {
			list1 += "[]";
		}
		return list1;
	}

	public String getActiveUserList() {
		String qry = "select count(ltrim(TO_CHAR(date,'YYYY-MM-DD'),'0')) as total,ltrim(TO_CHAR(date,'YYYY-MM-DD'),'0') as date from userlogincountinfo GROUP BY ltrim(TO_CHAR(date,'YYYY-MM-DD'),'0') ORDER BY 2 ";
		List<Map<String, Object>> list = getActiveUserListJdbc(qry);
		String list1 = "";
		for (int i = 0; i < list.size(); i++) {
			if (!list.get(i).get("total").equals(0))
				list1 += ",{date: \"" + list.get(i).get("date").toString() + "\", total: \"" + list.get(i).get("total")
						+ "\"}";
		}
		if (list1.length() > 0) {
			list1 = "[" + list1.substring(1, list1.length());
			list1 += "]";
		}
		return list1;
	}

	public List<Map<String, Object>> getUserLoginCount() {
		String qry = "select count(userid)::int as total from userlogincountinfo where status='1' ";
		List<Map<String, Object>> list = getUserLoginCountJdbc(qry);
		return list;
	}

	public List<Map<String, Object>> getActiveUserCount() {
		String qry = "select count(distinct a.userid)::int as total from logininformation a inner join userlogincountinfo b on a.userid = b.userid and b.loginstatus='Active'";
		List<Map<String, Object>> list = getActiveUserCountJdbc(qry);
		return list;
	}

	public List<Map<String, Object>> getRoleCount() {
		String qry = "select count(role_id)::int as total from roleinformation"; // role count
		List<Map<String, Object>> list = getRoleCountJdbc(qry);
		return list;
	}

	public List<Map<String, Object>> getUserCount() {
		String qry = "select count(l.userid)::int as total from logininformation l\r\n"
				+ "left join userroleinformation u on l.userid = u.user_id join roleinformation r  on  r.role_id =  u.role_id  "
				+ "where l.accountnonlocked='1' "; // user count
		List<Map<String, Object>> list = getUserCountJdbc(qry);
		return list;
	}

	public List<Map<String, Object>> getUserReport() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement stmt = null;
			String q = "select l.userid, l.username, l.login_name, r.role " + "from logininformation l "
					+ "left join userroleinformation u on l.userid = u.user_id join roleinformation r  on  r.role_id =  u.role_id  "
					+ "where l.accountnonlocked='1'  order by l.userid ";
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
	public List<Map<String, Object>> getRankListDetails() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			String qry = "select rank_subject from q_mst_rank ";
			PreparedStatement stmt = conn.prepareStatement(qry);
			ResultSet rs = null;
			try {
				rs = stmt.executeQuery();
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
			} catch (Exception e) {
				// TODO: handle exception
			}
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
		System.out.println("--list--rank---"+list);
		return list;
	}
	public List<Map<String, Object>> getInmailDetails(String year) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Connection conn = null;
		int p = 0;
		String doc = "";
		String doc_expected = "", year_whr1 = "", year_whr = "";
		if (!year.equals("")) {
			year_whr = "and  a.acr_cycle_year::text = ?";
			year_whr1 = "where  year::text = ? ";
		}

		List<Map<String, Object>> ListofDoc = getListofDoc();
		for (int i = 0; i < ListofDoc.size(); i++) {



//			doc_expected = doc_expected + "COUNT(CASE WHEN b.rank_code= " + ListofDoc.get(i).get("rank_code")
//					+ " THEN b.rank_code END) AS "
//					+ (ListofDoc.get(i).get("rank_subject")).toString().replaceAll("\\s", "").toLowerCase();
			
			doc_expected = doc_expected + "sum(b.expected) filter (where b.rank_code= " + ListofDoc.get(i).get("rank_code")
					+ ") AS "
					+ (ListofDoc.get(i).get("rank_subject")).toString().replaceAll("\\s", "").toLowerCase();

			doc = doc + "COUNT(CASE WHEN a.rank_code= " + ListofDoc.get(i).get("rank_code")
					+ " THEN a.rank_code END) AS "
					+ (ListofDoc.get(i).get("rank_subject")).toString().replaceAll("\\s", "").toLowerCase();

			if (p < ListofDoc.size() - 1) {
				doc = doc + ",\n";
				doc_expected = doc_expected + ",\n";
			}
			p++;
		}

		try {
			conn = dataSource.getConnection();
			qry_list = "Select e.doc_type, " + "  case when others is null then '0' else others end as others,case when mcpoi is null then '0' else mcpoi end as mcpo1,"
					+ " case when mcpoii is null then '0' else mcpoii end as mcpo2,"
					+ " case when cpo is null then '0' else cpo end as cpo,"
					+ " case when po is null then '0' else po end as po,"
					+ " case when po_aa is null then '0' else po_aa end as poaa,"
					+ " case when ls is null then '0' else ls end as ls from ( \r\n"
					+ " select 'Expected' as doc_type, \r\n" + doc_expected + "\n" + "\nfrom  q_mst_expected_cr b \r\n"
					+ year_whr1 + "\nUNION \r\n" + "select 'ACR' as doc_type, \r\n" + doc
					+ "\nfrom  q_tbl_inmail a where a.document_type=1 and a.status='ACCEPTED' \r\n" + year_whr
					+ "\nUNION \r\n" + "select 'ICR' as doc_type,\r\n" + doc
					+ "\nfrom  q_tbl_inmail a where a.document_type=2  and a.status='ACCEPTED' \r\n" + year_whr
					+ "\nUNION\r\n" + "select 'Total Received' as doc_type,\r\n" + doc
					+ "\nfrom  q_tbl_inmail a where a.status='ACCEPTED' \r\n" + year_whr + "\nUNION \r\n"
					+ "select 'Rejected/RTU' as doc_type,\r\n" + doc
					+ "\nfrom  q_tbl_inmail a where (a.status ='REJECTED' or a.status ='RESUBMISSION') \r\n" + year_whr
					+ "\n"
//							+ ""
//							+ "UNION \r\n"
//					+ "select 'Not Received' as doc_type,\r\n" + doc
//					+ "\nfrom  q_tbl_inmail a where a.status !='ACCEPTED' \r\n" + year_whr + "\nUNION \r\n"
//					+ "select 'SNAP' as doc_type,\r\n" + doc + "\nfrom  q_tbl_inmail a where a.is_snap=true  \r\n"
//					+ year_whr + "\nUNION	\r\n" + "select 'Returned' as doc_type,\r\n" + doc
//					+ "\nfrom  q_tbl_inmail a where a.status !='RESUBMISSION'  " + year_whr + ""
//							+ ""
					+ ")e  \r\n" + "order by (case doc_type when 'Expected' then 1\r\n" + "when 'ACR' then 2\r\n"
					+ "when 'ICR' then 3\r\n" + "when 'Total Received' then 4\r\n" + "when 'Rejected' then 5\r\n"
					+ "when 'ended' then 9 end)  ";

			PreparedStatement stmt = conn.prepareStatement(qry_list);
			if (!year.equals("")) {
				stmt.setString(1, year);
				stmt.setString(2, year);
				stmt.setString(3, year);
				stmt.setString(4, year);
				stmt.setString(5, year);
//				stmt.setString(6, year);
//				stmt.setString(7, year);
//				stmt.setString(8, year);
			}
			try {
				System.out.println("DB Stmt======================" + stmt);
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
			} catch (Exception e) {
				// TODO: handle exception
			}
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

	public List<Map<String, Object>> getListofDoc() {
		Connection conn = null;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {

			conn = dataSource.getConnection();
			String qry = "";
			qry = "SELECT * FROM q_mst_rank\r\n" + "ORDER BY id ASC ";
			PreparedStatement stmt = conn.prepareStatement(qry);
			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();

			while (rs.next()) {
				Map<String, Object> listof_documents = new LinkedHashMap<String, Object>();
				for (int i = 1; i <= columnCount; i++) {
					listof_documents.put(metaData.getColumnLabel(i), rs.getObject(i));
				}
				list.add(listof_documents);
			}
			rs.close();

			// } catch (Exception e) {
			// TODO: handle exception
			// }

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
