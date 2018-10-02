package com.itheima.bos.dao;

import com.itheima.bos.dao.base.IBaseDao;
import com.itheima.bos.domain.User;

public interface IUserDao extends IBaseDao<User>{
	//根据用户名和密码查找用户
	public User findByUsernameAndPassword(String username, String password);
	//根据用户名查找用户
	public User findByUsername(String username);
}
