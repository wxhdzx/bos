package com.itheima.bos.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.SubareaDao;
import com.itheima.bos.domain.Subarea;
import com.itheima.bos.service.SubareaService;
import com.itheima.bos.utils.PageBean;
@Service
@Transactional
public class SubareaServiceImpl implements SubareaService {
	@Resource
	private SubareaDao subareaDao;
	public void save(Subarea subarea) {
		// TODO Auto-generated method stub
		subareaDao.save(subarea);
	}
	public void pageQuery(PageBean pageBean) {
		// TODO Auto-generated method stub
		subareaDao.queryPage(pageBean);
	}
	public List<Subarea> findAll() {
		// TODO Auto-generated method stub
		return subareaDao.findAll();
	}
	public Subarea findById(String id) {
		// TODO Auto-generated method stub
		return subareaDao.findById(id);
	}
	public void update(Subarea subarea) {
		// TODO Auto-generated method stub
		subareaDao.update(subarea);
	}
	public void deleteBatch(String ids) {
		// TODO Auto-generated method stub
		String[] subids= ids.split(",");
		for(String id:subids) {
			subareaDao.executeUpdate("Subarea.delete",id);
		}
	}
	public List<Subarea> findListNotAssiociation() {
		DetachedCriteria detachedCriteria=DetachedCriteria.forClass(Subarea.class);
		// TODO Auto-generated method stub
		//添加离线条件
		detachedCriteria.add(Restrictions.isNull("decidedzone"));
		return subareaDao.findByCriteria(detachedCriteria);
		
	}

}
