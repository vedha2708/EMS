package com.BisagN.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.LockedException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.BisagN.models.UserAttempts;
import com.BisagN.models.UserLogin;

@Repository
public class UserAttemptsDAOImpl implements UserAttemptsDAO {
	
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	
	private static final int MAX_ATTEMPTS = 5;
	
	private UserLoginDAO login= new UserLoginDAOImpl();
	
	//@Override
	public void updateFailAttempts(String userName) {
		int userId = getUserId(userName);
		UserAttempts attempts = getUserAttempts(userId);
		System.out.println("if attempts == " + attempts);
		if (attempts == null) {
			System.out.println("if attempts");
				// if no record, insert a new attempt
				UserAttempts newAttempt = new UserAttempts(userId,0,new Date());
				Session session= this.sessionFactory.getSessionFactory().openSession();
				Transaction tx = session.beginTransaction();
				session.save(newAttempt);
				tx.commit();
				session.close();
		} else {
			System.out.println("attempts.getAttempts() ==" + attempts.getAttempts());
			
			if (attempts.getAttempts() + 1 >= MAX_ATTEMPTS) {
				// locked user
				UserLogin user = login.findUser(userName);
				user.setAccountNonLocked(0);
				login.update(user);
				// throw exception
				throw new LockedException("User Account is locked!");
			}
			// update attempts count, +1
			attempts.setAttempts(attempts.getAttempts()+1);
			attempts.setLastModified(new Date());
			
			Session session= this.sessionFactory.getSessionFactory().openSession();
			Transaction tx = session.beginTransaction();
			session.update(attempts);
			tx.commit();
			session.close();
		}
	}

	//@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=false)
	public UserAttempts getUserAttempts(int userId) {
		SimpleDateFormat desiredFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String curr_date = desiredFormat.format(new Date());
		String before30Minutes = desiredFormat.format(new Date(System.currentTimeMillis()-30*60*1000));
		Session session= this.sessionFactory.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		Query q = session.createQuery("FROM UserAttempts U where U.userId=:userId and TO_CHAR(lastModified, 'dd-mm-yyyy HH24:MI:SS') between :before30Minutes and :curr_date ");
		q.setParameter("userId",userId);
		q.setParameter("before30Minutes",before30Minutes);
		q.setParameter("curr_date",curr_date);
		List<UserAttempts> list = (List<UserAttempts>) q.list();
		tx.commit();
		session.close();
		System.out.println("list.size() == " + list.size());
		if(list.size()>0)
		{
			UserAttempts myAtttempt= (UserAttempts) list.get(0);
			return myAtttempt;
		}
		else
		{
			return null;
		}
	}

	public UserAttempts getUserAttempts(String userName) {
		int userId = getUserId(userName);
		return getUserAttempts(userId);
	}
	
	//@Override
	@Transactional(readOnly=false)
	public void resetFailAttempts(String userName) {
		int userId = getUserId(userName);
		System.out.println("getUserId == " + userId);
		UserAttempts attempts = getUserAttempts(userId);
		System.out.println("getUserAttempts == " + attempts);
		if (attempts != null) {
			attempts.setAttempts(0);
			attempts.setLastModified(new Date());
			Session session= this.sessionFactory.getSessionFactory().openSession();
			Transaction tx = session.beginTransaction();
			session.update(attempts);
			tx.commit();
			session.close();
		}
	}

	public int getUserId(String userName){
		UserLogin loginDetails = login.findUser(userName);
		if(loginDetails !=null)
		{
			return loginDetails.getUserId();
		}
		else
		{
			return 0;
		}
	}
}