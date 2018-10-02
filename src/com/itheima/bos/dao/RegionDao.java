package com.itheima.bos.dao;

import java.io.Serializable;
import java.util.List;

import com.itheima.bos.dao.base.IBaseDao;
import com.itheima.bos.domain.Region;
import com.itheima.bos.utils.PageBean;

public interface RegionDao extends IBaseDao<Region>{

	List<Region> findByQ(String q);

	public void deleteById(String id);
	
}
