package com.itheima.bos.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.RegionDao;
import com.itheima.bos.domain.Region;
import com.itheima.bos.service.RegionService;
import com.itheima.bos.utils.PageBean;
@Service
@Transactional
public class RegionServiceImpl implements RegionService {
	@Resource
	private RegionDao regionDao;
	public void saveBatch(List<Region> list) {
		// TODO Auto-generated method stub
		for(Region region:list) {
			regionDao.save(region);
		}
	}
	public List<Region> findAll() {
		// TODO Auto-generated method stub
		return regionDao.findAll();
	}
	public List<Region> findByQ(String q) {
		// TODO Auto-generated method stub
		return regionDao.findByQ(q);
	}
	public void pageBean(PageBean pageBean) {
		// TODO Auto-generated method stub
		regionDao.queryPage(pageBean);
	}
	public void save(Region region) {
		// TODO Auto-generated method stub
		regionDao.save(region);
	}
	public Region findById(String id) {
		// TODO Auto-generated method stub
		return regionDao.findById(id);
	}
	public void update(Region region) {
		// TODO Auto-generated method stub
		regionDao.update(region);
	}
	public void deleteBatch(String ids) {
		// TODO Auto-generated method stub
		String[] regionid = ids.split(",");
		for(String id:regionid) {
			regionDao.executeUpdate("Region.delete",id);
		}
	}

}
