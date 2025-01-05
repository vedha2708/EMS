package com.BisagN.dao;

import com.BisagN.models.UserAttempts;

public interface UserAttemptsDAO {
	public void updateFailAttempts(String userName);

	public void resetFailAttempts(String userName);

	public UserAttempts getUserAttempts(int userId);

	public UserAttempts getUserAttempts(String userName);

	public int getUserId(String userName);
}