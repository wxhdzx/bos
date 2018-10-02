package com.itheima.bos.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.itheima.bos.dao.RegionDao;
import com.itheima.bos.dao.base.impl.BaseDaoImpl;
import com.itheima.bos.domain.Region;
import com.itheima.bos.utils.PageBean;
@Repository
public class RegionDaoImpl extends BaseDaoImpl<Region> implements RegionDao {

	public List<Region> findByQ(String q) {
		// TODO Auto-generated method stub
		String hql="from Region where province like ? or city like ? or district like ?";
		return this.getHibernateTemplate().find(hql,"%"+q+"%","%"+q+"%","%"+q+"%");
	}

	public void deleteById(String id) {
		// TODO Auto-generated method stub
		String hql="delete from Region where id=?";
		this.getHibernateTemplate().delete(hql,id);
	}
}
