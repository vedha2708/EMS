package com.BisagN.dao;

import java.util.List;

import com.BisagN.models.UserLogin;

public interface UserServiceDAO {

	public  List<String>  getRoleByuserId(String userId);
	public UserLogin findUser(String userName);
}
