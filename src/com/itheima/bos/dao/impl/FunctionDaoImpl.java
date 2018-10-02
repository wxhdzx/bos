package com.itheima.bos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.itheima.bos.dao.FunctionDao;
import com.itheima.bos.dao.base.impl.BaseDaoImpl;
import com.itheima.bos.domain.Function;
@Repository
public class FunctionDaoImpl extends BaseDaoImpl<Function> implements FunctionDao{
	//根据用户id查询用户权限
	public List<Function> findListByUserId(String id) {
		// TODO Auto-generated method stub
		String hql="SELECT DISTINCT f FROM Function f left outer join f.roles r left outer join r.users u where u.id=? ";
		return this.getHibernateTemplate().find(hql,id);
		
	}

	public List<Function> findMenuByUserId(String id) {
		// TODO Auto-generated method stub
		String hql="SELECT DISTINCT f FROM Function f left outer join f.roles r left outer join r.users u where u.id=?"+
		"and f.generatemenu='1' order by f.zindex desc";
		
		return this.getHibernateTemplate().find(hql,id);
	}

	public List<Function> findAllMenu() {
		// TODO Auto-generated method stub
		String hql="From Function f where f.generatemenu='1' order by f.zindex desc";

		return this.getHibernateTemplate().find(hql);
	}

}
